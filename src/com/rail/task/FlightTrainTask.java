package com.rail.task;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.TaskInfoUtil;
import com.rail.bo.task.TaskBO;
import com.rail.zWebSocket.EquipConInfo;
import com.rail.zWebSocket.PubTcp;
import com.rail.zWebSocket.PubUtil;
import com.rail.zWebSocket.ServerManager;
import com.rail.zWebSocket.TestClient;

@Component
public class FlightTrainTask {
	
	private static final  String task1 = TaskInfoUtil.getTxnInfo("task1");//1159
	private static Logger logger = Logger.getLogger(FlightTrainTask.class);
    //@Scheduled(cron = "0/30 * * * * ? ") // 间隔30秒执行
	//@Scheduled(cron = "0 */1 * * * ?") // 间隔1分钟秒执行
	
	
  // @Scheduled(cron = task1) //  每天一点触发
   @Scheduled(cron = "0 0 1 * * ?") //  每天一点触发
//   @Scheduled(cron = "0 */1 * * * ?") // 间隔1分钟秒执行
    public void taskCycle() {
	   TaskBO taskBO = (TaskBO)ContextUtil.getBean("TaskBO");
    	logger.info("定时任务每天执行一次");
        taskBO.railToolMaintainWarnTaskForFei();
        taskBO.railToolMaintainWarnTaskForRepaire();
        taskBO.railAccessWarnTaskForRepaire();
    }
  @Scheduled(cron = "0 */3 * * * ?") // 间隔3分钟秒执行
  //@Scheduled(cron = "0 */1 * * * ?") // 间隔1分钟秒执行
   public void taskCycle2() {
	   TaskBO taskBO = (TaskBO)ContextUtil.getBean("TaskBO");
	   logger.info("定时任务每三分钟执行一次");
	   taskBO.railWorkWarn();
	   taskBO.railWorkWarnBefore();
	   taskBO.railWorkWarnInNet();
	   taskBO.railWorkWarnOutNet();
	   
	   //这里执行如果长时间未关闭的链接删除连接状态
	   if (PubUtil.EQUIP_IN_CON!=null&&PubUtil.EQUIP_IN_CON.size()>0) {
		   HashMap<String, EquipConInfo> tmpHashMap=PubUtil.EQUIP_IN_CON;
		   for (Entry<String, EquipConInfo> entry:tmpHashMap.entrySet()) {
			   EquipConInfo tmpConInfo=entry.getValue();
			   //如果当前连接3分钟没有心跳就进行显示已经断开连接
			   if ((new Date().getTime()-tmpConInfo.getConTime())>180000l) {
				   PubUtil.EQUIP_IN_CON.remove(entry.getKey());
			   }
		   }
			
		}
	}
  
   class runEquip implements Runnable   { 
	   public void run()   { 
		   TestClient tClient=new TestClient();
		   tClient.socketClient=null;
		   Integer port1 = Integer.valueOf(TaskInfoUtil.getTxnInfo("port1"));//1159
		   boolean equipFlag=tClient.TestConnect("ws://127.0.0.1:"+port1+"/");
		   if (!equipFlag) {
			   ServerManager serverManager=new ServerManager();
			   serverManager.Start(port1,true);//1159
			   System.out.println("ReStart--------->>wsServer for port Restart "+port1+"<<-----------End!");
		   }
//		   logger.info("定时任务每10秒执行一次 equipFlag>"+equipFlag);
	   }  
	 } 
  
   class runApp implements Runnable   { 
	   public void run()   { 
		   Integer port2 = Integer.valueOf(TaskInfoUtil.getTxnInfo("port2"));//1170
		   TestClient tClient2=new TestClient();
		   tClient2.socketClient=null;
		   boolean appFlag=tClient2.TestConnect("ws://127.0.0.1:"+port2+"/");
		   if (!appFlag) {
			   ServerManager serverManager=new ServerManager();
			   serverManager.Start(port2,false);//1170
			   System.out.println("ReStart--------->>wsServer for port Restart "+port2+"<<-----------End!");
		   }
//		   logger.info("定时任务每10秒执行一次 appFlag>"+appFlag);
	   }  
	 } 
   	@Scheduled(cron = "0 */3 * * * ?") // 间隔3分钟秒执行
	public void taskCycle3() {
		// 判断websocket的两个端口号的执行情况
		Runnable runEquip = new runEquip();
		Thread oneThread = new Thread(runEquip);
		oneThread.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
//			e.printStackTrace();
		}
		Runnable runApp = new runApp();
		Thread twoThread = new Thread(runApp);
		twoThread.start();

	}
	
  @Scheduled(cron = "0 */3 * * * ?") // 间隔3分钟秒执行
  //@Scheduled(cron = "0 */1 * * * ?") // 间隔1分钟秒执行
   public void taskCycle4() {
	   logger.info("定时任务每三分钟执行一次 发送未推送的消息！");
	   Map<String, List<String>> pubMessageMap=PubTcp.PUB_MESSAGE;
	   if (pubMessageMap!=null&&pubMessageMap.size()>0) {
		   for (Entry<String, List<String>> entry:pubMessageMap.entrySet()) {
			   String equipCode=entry.getKey();
			   List<String> megInRam=entry.getValue();
			   if (PubTcp.equipInConnect(equipCode)) {
				   for (int i = 0; i < megInRam.size(); i++) {
					   PubTcp.sendMessage(equipCode,megInRam.get(i),false);
				   }
			   }
		   }
	   }
	}

}