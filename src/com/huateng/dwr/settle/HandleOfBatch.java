/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ --------- ---------------------------------------------------
 *   Gavin        2011-7-28       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2011 Huateng Software, Inc. All rights reserved.
 *
 *       This software is the confidential and proprietary information of
 *       Shanghai HUATENG Software Co., Ltd. ("Confidential Information").
 *       You shall not disclose such Confidential Information and shall use it
 *       only in accordance with the terms of the license agreement you entered
 *       into with Huateng.
 *
 * Warning:
 * =============================================================================
 *
 */
package com.huateng.dwr.settle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.huateng.common.StringUtil;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.dao.common.SqlDao;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.SocketConnect;
import com.huateng.system.util.SysParamUtil;

/**
 * Title: 
 * 
 * File: HandleOfBatch.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-7-28
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class HandleOfBatch {
	
	ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
	ICommQueryDAO dao=CommonFunction.getCommQueryDAO();
	private static Logger log = Logger.getLogger(HandleOfBatch.class);
	/**
	 * 发送任务启动报文给后台系统
	 * 
	 * @return
	 * 2011-7-28上午11:38:49
	 * @throws IOException 
	 */
	public String sendMsg(String date) throws IOException{
		
		if (StringUtil.isNull(date)) {
			return "没有正确获取日终日期";
		}
		
		String batId ="";
		String bacthState="";//批量处理状态
		String recDate="";//批量处理对应的清算日期
		String yestoday=CommonFunction.getDateYestoday(date);

		
		String sql1="select bat_state,DATE_SETTLMT from tbl_bat_main_ctl_dtl " +
					"where bat_id = 'S0004' and bat_state='2' and task_assign_state ='2' " +
					"and DATE_SETTLMT ='"+yestoday+"'";
		List<Object[]> list1=dao.findBySQLQuery(sql1);
		if(list1.isEmpty()){
			return  "在清算日期 ："+yestoday+" "+"存在未完成的批量操作";
		}
		
		
        String sql="select a.BAT_ID,a.BAT_DSP,NVL(b.BAT_STATE,0),a.BAT_NOTE,NVL(b.BAT_STATE,0) from" +
        		" ((SELECT BAT_ID,BAT_DSP,BAT_STATE,BAT_NOTE,BAT_CLS FROM TBL_BAT_MAIN_CTL where substr(BAT_ID,1,1) = 'B' and TASK_START_FLG = '0') a left outer join" +
        		" (select DATE_SETTLMT,BAT_ID,BAT_STATE from TBL_BAT_MAIN_CTL_DTL where trim(DATE_SETTLMT) = '"+date+"' and bat_state in ('0','3')) b on (a.BAT_ID = b.BAT_ID))" +
        		" ORDER BY a.BAT_ID";
        List<Object[]> list=new ArrayList<Object[]>();
        list=(ArrayList<Object[]>)commQueryDAO.findBySQLQuery(sql);
        
        for(int i=0;i<list.size();i++){
        	batId = list.get(i)[0].toString();
    		
    		String sqlBf="select bat_state,DATE_SETTLMT from tbl_bat_main_ctl_dtl " +
			 			 "where bat_id = '"+batId+"' and DATE_SETTLMT ='"+yestoday+"'";
			List<Object[]> listBf=dao.findBySQLQuery(sqlBf);
			
			Iterator<Object[]> it=listBf.iterator();
			while(it.hasNext()){
				Object[] tmp =it.next();
				if(tmp[0].toString().trim().equals("0")){
					recDate=tmp[1].toString().trim();
					bacthState="批处理未运行";
					return  "任务编号  "+batId+" 在清算日期 ："+recDate+" "+bacthState;
				}
				if(tmp[0].toString().trim().equals("1")){
					recDate=tmp[1].toString().trim();
					bacthState="批处理正在运行中";
					return  "任务编号  "+batId+" 在清算日期 ："+recDate+" "+bacthState;
				}
				if(tmp[0].toString().trim().equals("3")){
					recDate=tmp[1].toString().trim();
					bacthState="批处理运行失败";
					return  "任务编号  "+batId+" 在清算日期 ："+recDate+" "+bacthState;
				}
			}

			//当天任务开始前判断是否成功，不成功的初始化
    		String sqlToday="select bat_state,DATE_SETTLMT from tbl_bat_main_ctl_dtl " +
							"where bat_id = '"+batId+"' and bat_state='2' and task_assign_state ='2' " +
							"and DATE_SETTLMT ='" + date + "'";
			List<Object[]> list3=dao.findBySQLQuery(sqlToday);
			
			//确定当日未跑批时先清空当日信息
			if(list3.isEmpty()){
				String sqlClean ="delete from tbl_bat_main_ctl_dtl " +
								 "where date_settlmt='" + date + "' " +
								 "and bat_id = '"+batId+"'";
				String sqlClean2 ="delete from tbl_bat_task " +
								  "where date_settlmt='" + date + "' " +
								  "and bat_id = '"+batId+"'";
				String sqlClean3 ="update tbl_bat_main_ctl set bat_state='0', TASK_ASSIGN_STATE='0' " +
								  "where bat_id = '"+batId+"'";
				dao.excute(sqlClean);
				dao.excute(sqlClean2);
				dao.excute(sqlClean3);
				
				if(i == list.size()-1){
					String sqlCleanS ="delete from tbl_bat_main_ctl_dtl " +
									  "where date_settlmt='" + date + "' " +
									  "and substr(bat_id,0,1) = 'S'";
					String sqlCleanS2 ="delete from tbl_bat_task " +
									  "where date_settlmt='" + date + "' " +
									  "and substr(bat_id,0,1) = 'S'";
					String sqlCleanS3 ="update tbl_bat_main_ctl set bat_state='0', TASK_ASSIGN_STATE='0' " +
									  "where substr(bat_id,0,1) = 'S'";
					dao.excute(sqlCleanS);
					dao.excute(sqlCleanS2);
					dao.excute(sqlCleanS3);
				}
			}else{
				continue;
			}
    		
        	StringBuffer message = new StringBuffer();
    		message.append(batId);/* 任务编号 */
    		message.append(date); /* 日终日期 */
    		message.append(CommonFunction.fillString("  ", ' ', 2 + 256 + 2 + 256, true));
    		
    		String ip = SysParamUtil.getParam("IP");
    		int port = Integer.parseInt(SysParamUtil.getParam("BATCH_PORT"));
    		
    		String sendMessage = CommonFunction.fillString(String.valueOf(message.toString().length()), '0', 4, false);
    		sendMessage += message.toString();
    		log.info("send["+i+"]:" + sendMessage);
    		String ret = "";
    		
    		Socket socket = null;
    		OutputStream outputStream = null;
    		InputStream inputstream = null;
    		
    		try {
    			//发送
    			log.info("Connection["+i+"]: " + ip + ":" + port);
    			socket = new Socket(ip,port);
    			socket.setSoTimeout(1000 * 60);
    			outputStream = socket.getOutputStream();
    			outputStream.write(sendMessage.getBytes());
    			outputStream.flush();
    			
    			BufferedReader br = new BufferedReader(new InputStreamReader(socket
    					.getInputStream()));
    			
    			String temp;
    			while(true){
    				temp = br.readLine();
    				if(!StringUtil.isNull(temp)){
    					ret += temp;
    				}else{
    					break;
    				}
    			}
    			log.info("应答码["+i+"]："+ret.substring(275, 277));

    			String sqlDoing = "select bat_state from TBL_BAT_MAIN_CTL where bat_id = '"+batId+"'";
				
				long beginTime=System.currentTimeMillis();
				long time=0;
				
    			if (!StringUtil.isNull(ret) && ret.length() > 273) {
    				
    				if("00".equals(ret.substring(275, 277))){
    					while(true){
    						List listDoing = dao.findBySQLQuery(sqlDoing);
    						String accept = listDoing.get(0).toString();
    						
    						//休眠3秒后再操作
							Thread.sleep(3000);
    						
    						time = (System.currentTimeMillis()-beginTime)/1000;
        					
    						if(time >= 5){
        						if("2".equals(accept)){
        							log.info("任务["+batId+"]:启动成功！");
        							//如果是最后一步则返回S；其他跳出判断，继续循环。
        							if(i==list.size()-1){
        								log.info("全部任务启动成功！");
        								return "S";
        							}
        							break;
        						}
        						if("3".equals(accept)){
        							return "任务["+batId+"]:启动失败!";
        						}
        					}
    						
    						if(time >= 60){
    							return "超时，请重新刷新页面！";
    						}
        					continue;
    					}
    					continue;
    				}else {
    					return "任务["+batId+"]:应答不成功！";
    				}
    			}
    			
    			return batId+":启动任务失败D";
    		} catch (UnknownHostException e) {
    			e.printStackTrace();
    			break;
    		} catch (IOException e) {
    			e.printStackTrace();
    			break;
    		} catch (Exception e) {
    			e.printStackTrace();
    			break;
    		} finally {
    			try{
    				if (null != outputStream) {
    					outputStream.close();
    				}
    				if (null != inputstream) {
    					inputstream.close();
    				}
    				if (null != socket) {
    					socket.close();
    				}
    			} catch (Exception ex) {
    				ex.printStackTrace();
    				break;
    			}
    		}
        }
		
		return "启动任务失败E";
	}

	/**
	 * 商户清算
	 * */
	public String sendMchtMsg(String batId, String date) throws IOException{

		if (StringUtil.isNull(batId)) {
			return "没有正确获取任务编号";
		}
		if (StringUtil.isNull(date)) {
			return "没有正确获取日终日期";
		}
		
		String sql1="select bat_state,DATE_SETTLMT from tbl_bat_main_ctl_dtl " +
				"where bat_id = 'B0009' and bat_state='2' and task_assign_state ='2' " +
				"and DATE_SETTLMT ='"+date+"'";
		
		List<Object[]> list1=dao.findBySQLQuery(sql1);
		
		if(list1.isEmpty()){
			return  "请先处理完本日银联对账再进行商户清算！";
		}

		//组装报文
		StringBuffer message = new StringBuffer();
		message.append(batId);/* 任务编号 */
		message.append(date); /* 日终日期 */
		message.append(CommonFunction.fillString("  ", ' ', 2 + 256 + 2 + 256, true));
		

		String ip = SysParamUtil.getParam("IP");
		int port = Integer.parseInt(SysParamUtil.getParam("BATCH_PORT"));
//		int port = 30001;
		
		
		String sendMessage = CommonFunction.fillString(String.valueOf(message.toString().length()), '0', 4, false);
		sendMessage += message.toString();
		System.out.println("send:" + sendMessage);
		String ret = "";
		
		Socket socket = null;
		OutputStream outputStream = null;
		InputStream inputstream = null;
		
		try {
			//发送
			socket = new Socket(ip,port);
			socket.setSoTimeout(1000 * 60);
			outputStream = socket.getOutputStream();
			outputStream.write(sendMessage.getBytes());
			outputStream.flush();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(socket
					.getInputStream()));
			
			String temp;
			while(true){
				temp = br.readLine();
				if(!StringUtil.isNull(temp)){
					ret += temp;
				}else{
					break;
				}
			}
			System.out.println(ret);

			if (!StringUtil.isNull(ret) && ret.length() > 273) {
				if ("00".equals(ret.substring(275, 277))) {
					return "S";
				} else {
					return "启动任务失败";
				}
			}
			return "失败";
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if (null != outputStream) {
					outputStream.close();
				}
				if (null != inputstream) {
					inputstream.close();
				}
				if (null != socket) {
					socket.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return "失败";
	}
	public String sendReqMsg(String dateSettlm,String brhId,String money) throws IOException{
		
//		4位交易码(1531)
//		3位长度（062）
		StringBuffer req = new StringBuffer();
//		14位交易时间
		req.append(CommonFunction.getCurrentDateTime());
//		4位流水号
		Random r = new Random();
		int c = r.nextInt(9999);
		req.append(CommonFunction.fillString(String.valueOf(c), '0', 4, false));
//		4位机构号
		req.append(CommonFunction.fillString(brhId, '0', 4, false));
//		交易金额（第一位是符号位,共12位，在符号位和有效数据间补0，到分）
		//比如（12.34转换成+00000001234）（-12.34转换成-00000001234）
		String moneyTmp;
		money = CommonFunction.transYuanToFen(money);
		if(money.indexOf("-") == -1) { //money是正值
			moneyTmp = "+" + CommonFunction.fillString(money, '0', 11, false);
		} else { //money是负值
			moneyTmp = "-" + CommonFunction.fillString(money.substring(1), '0', 11, false);
		}
		req.append(moneyTmp);
		String request = "1531"+CommonFunction.fillString(String.valueOf(req.toString().length()), '0', 3, false)+req.toString();
		log.info(request);
		String len = Integer.toHexString(request.length());
		len = new String(CommonFunction.hexStringToByte(len));
		len = new String(CommonFunction.hexStringToByte("00"))+len;
		SocketConnect socket = null;
		String flag = "0";
		String desp = "";
		ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
		try {
			log.info("报文:["+len+request+"]");
			socket = new SocketConnect(len+request);
			socket.run("GBK");
			
			String rsp = socket.getRsp().substring(4, 6);
			
			if ("00".equals(rsp)) {
				flag = "2";
				desp = "SUCCESS:划拨成功";
			} else {
				flag = "1";
				String key = req.substring(0, 22);
				System.out.println(key);
	            String sql = "select SWITCH_DATA from TBL_N_TXN where substr(addtnl_data,1,22) = '" + key + "'";
				List list = commQueryDAO.findBySQLQuery(sql);
				if (null != list && !list.isEmpty() && !StringUtil.isNull(list.get(0))) {
					desp = list.get(0).toString().trim();
					if (desp.length() > 7) {
						desp = desp.substring(0, 7) + ":" + desp.substring(7);
					}
				}
			}
		} catch (UnknownHostException e) {
			flag = "1";
			desp = "E203055:POSP找不到主机";
			log.error(e);
		} catch (IOException e) {
			flag = "1";
			desp = "E203056:POSP请求超时";
			log.error(e);
		} catch (Exception e) {
			flag = "1";
			desp = "E203056:POSP请求超时";
			log.error(e);
		} finally {
			socket.close();
		}
		
		commQueryDAO.excute("update TBL_BRH_INFILE_DTL set SETTLE_FLAG = '" + flag + "',FAIL_RESN = '" + desp + "' " +
				"where DATE_SETTLMT = '" + dateSettlm + "' and SETTLE_FLAG ='0' and BRH_CODE = '" + brhId + "'");
		
		return flag;
	}
}
