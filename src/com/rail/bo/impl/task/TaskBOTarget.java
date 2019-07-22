package com.rail.bo.impl.task;

import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.SysParamUtil;
import com.rail.bo.enums.*;
import com.rail.bo.task.TaskBO;
import com.rail.bo.tools.TSendMsgSocketBO;
import com.rail.po.access.RailAccessInfo;
import com.rail.po.access.RailAccessMaintainWarn;
import com.rail.po.tool.RailToolInfo;
import com.rail.po.tool.RailToolMaintainWarn;
import com.rail.po.work.RailWorkInfo;
import com.rail.po.work.RailWorkWarn;
import com.rail.zWebSocket.PubUtil;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**  
* @author syl
* @version 创建时间：2019年4月15日 上午9:23:37  
* @desc
*/
public class TaskBOTarget extends HibernateDaoSupport implements TaskBO {
	private static Logger logger = Logger.getLogger(TaskBOTarget.class);
	// 报废定时任务
	public void railToolMaintainWarnTaskForFei(){
		System.out.println("工具报废定时任务扫描~~~~~~~~~~~~~~~~~~~");
		//查询 报废时间 小于 系统时间 的工具数据
		String hsql = "from RailToolInfo t "
				+ "where  t.delStatus='"+DelStatusEnums.NO.getCode()+"'  and TO_NUMBER(sysdate - t.toolExpiration)*24*60*60 > 0 and t.toolStatus = 0 ";
		List<RailToolInfo> railToolInfos = this.getHibernateTemplate().find(hsql);
		List<RailToolMaintainWarn> list = new ArrayList<>();
		for(RailToolInfo tool :railToolInfos ){
			RailToolMaintainWarn maintainWarn = new RailToolMaintainWarn(); 
			maintainWarn.setToolCode(tool.getToolCode());
			maintainWarn.setInfoSign(String.valueOf(InfoSignEnums2.NO.getCode()));
			maintainWarn.setAddDate(new Date());
			maintainWarn.setWarnMsg(ToolWarnTypeEnums.FEI.getDesc());
			maintainWarn.setNote1(String.valueOf(ToolWarnTypeEnums.FEI.getCode()));
			list.add(maintainWarn);
//			this.getHibernateTemplate().save(maintainWarn);
			logger.info("工具编码为:"+tool.getToolCode()+"报废预警 ");
		}
		this.getHibernateTemplate().saveOrUpdateAll(list);
	}
	// 维修定时任务
	public void railToolMaintainWarnTaskForRepaire(){
		logger.info("工具维修定时任务扫描~~~~~~~~~~~~~~~~~~~");
		//查询 系统时间 - 最后的检修时间 大于 检修周期的  比如 1.1号 那 2.1号以后 才报警
		String hsql = "from RailToolInfo t "
				+ "where   t.delStatus='"+DelStatusEnums.NO.getCode()+"' and TO_NUMBER(sysdate-t.lastExam) > t.examPeriod and t.toolStatus = 0  ";
		List<RailToolInfo> railToolInfos = this.getHibernateTemplate().find(hsql);
		List<RailToolMaintainWarn> list = new ArrayList<RailToolMaintainWarn>();
		for(RailToolInfo tool :railToolInfos ){
			RailToolMaintainWarn maintainWarn = new RailToolMaintainWarn(); 
			maintainWarn.setToolCode(tool.getToolCode());
			maintainWarn.setInfoSign(String.valueOf(InfoSignEnums2.NO.getCode()));
			maintainWarn.setAddDate(new Date());
			maintainWarn.setWarnMsg(ToolWarnTypeEnums.REPAIRE.getDesc());
			maintainWarn.setNote1(String.valueOf(ToolWarnTypeEnums.REPAIRE.getCode()));
			
			logger.info("工具编码为:"+tool.getToolCode()+"----维修预警 ");
			list.add(maintainWarn);
		}
		this.getHibernateTemplate().saveOrUpdateAll(list);
	}
	@Override
	public void railAccessWarnTaskForRepaire() {
		logger.info("门禁维修定时任务扫描~~~~~~~~~~~~~~~~~~~");
		//查询 系统时间 - 最后的检修时间 大于 检修周期的  比如 1.1号 那 2.1号以后 才报警
		String hsql = "from RailAccessInfo t "
				+ "where t.delStatus='"+DelStatusEnums.NO.getCode()+"'  and TO_NUMBER(sysdate-t.lastExam) > t.examPeriod  and t.accessStatus = 0 ";
		List<RailAccessInfo> accessInfos = this.getHibernateTemplate().find(hsql);
		List<RailAccessMaintainWarn> list = new ArrayList<RailAccessMaintainWarn>();
		for(RailAccessInfo item :accessInfos ){
			RailAccessMaintainWarn maintainWarn = new RailAccessMaintainWarn(); 
			maintainWarn.setAccessCode(item.getAccessCode());
			maintainWarn.setInfoSign(String.valueOf(InfoSignEnums2.NO.getCode()));
			maintainWarn.setAddDate(new Date());
			maintainWarn.setWarnMsg(ToolWarnTypeEnums.REPAIRE.getDesc());
			maintainWarn.setNote1(String.valueOf(ToolWarnTypeEnums.REPAIRE.getCode()));
			logger.info("门禁编码为:"+maintainWarn.getAccessCode()+"----维修预警 ");
			list.add(maintainWarn);
		}
		this.getHibernateTemplate().saveOrUpdateAll(list);
		
		
	}
	@Override
	public void railWorkWarn() {
		logger.info("工单作业天窗预警定时任务扫描~~~~~~~~预警类型为：作业天窗已结束未还库");
		//查询 系统时间 - 最后的检修时间 大于 检修周期的  比如 1.1号 那 2.1号以后 才报警
		String hsql = "from RailWorkInfo t "
				+ "where t.delStatus='"+DelStatusEnums.NO.getCode()+"'  and TO_NUMBER(sysdate-t.skylightEnd)*60*60*24 > 0 and "
						+ " and to_number(t.workStatus)<4 ";
		List<RailWorkInfo> accessInfos = this.getHibernateTemplate().find(hsql);
		List<RailWorkWarn> list = new ArrayList<RailWorkWarn>();
		for(RailWorkInfo item :accessInfos ){
			List<RailWorkWarn> isList = new ArrayList<RailWorkWarn>();
			if(checkWarnExit(item.getWorkCode(),isList)){
				for(RailWorkWarn ii : isList){
					if(Integer.valueOf(ii.getInfoSign())== InfoSignEnums2.NO.getCode()){
						ii.setAddDate(new Date());
						this.getHibernateTemplate().update(ii);
//						TSendMsgSocketBO tSendMsgSocketBO = (TSendMsgSocketBO) ContextUtil.getBean("TSendMsgSocketBO");
//						tSendMsgSocketBO.sendSocketMsg(ii.getNote3(), ii.getId(), ApiSendMsgFlagEnums.WARN.getCode());
						logger.info("工单编码为:"+ii.getWorkCode()+"----预警类型为：作业天窗已结束未还库");
						
						TSendMsgSocketBO tSendMsgSocketBO = (TSendMsgSocketBO) ContextUtil.getBean("TSendMsgSocketBO");
						tSendMsgSocketBO.sendSocketMsgSpeWhse(item.getWhseCode(), ii.getId(), ApiSendMsgFlagEnums.WARN.getCode());
						logger.info("工单编码为:"+ii.getWorkCode()+"----预警类型为：作业天窗已开始未执行");
					}
				}
			}else{
				RailWorkWarn maintainWarn = new RailWorkWarn(); 
				maintainWarn.setWorkCode(item.getWorkCode());
				maintainWarn.setInfoSign(String.valueOf(InfoSignEnums2.NO.getCode()));
				maintainWarn.setAddDate(new Date());
				maintainWarn.setWarnMsg(WorkWarnTypeEnums.SKY.getDesc());
				maintainWarn.setWarnType(String.valueOf(WorkWarnTypeEnums.SKY.getCode()));
				maintainWarn.setNote1(String.valueOf(WorkWarnTypeEnums.SKY.getCode()));
				maintainWarn.setNote3(item.getDept());
				maintainWarn.setNote2(item.getWhseCode());
				logger.info("工单编码为:"+maintainWarn.getWorkCode()+"----预警类型为：作业天窗已结束未还库");
				list.add(maintainWarn);
			}
			
		}
		this.getHibernateTemplate().saveOrUpdateAll(list);
		for(RailWorkWarn maintainWarn : list){
//			TSendMsgSocketBO tSendMsgSocketBO = (TSendMsgSocketBO) ContextUtil.getBean("TSendMsgSocketBO");
//			tSendMsgSocketBO.sendSocketMsg(maintainWarn.getNote3(), maintainWarn.getId(), ApiSendMsgFlagEnums.WARN.getCode());
			TSendMsgSocketBO tSendMsgSocketBO = (TSendMsgSocketBO) ContextUtil.getBean("TSendMsgSocketBO");
			tSendMsgSocketBO.sendSocketMsgSpeWhse(maintainWarn.getNote2(), maintainWarn.getId(), ApiSendMsgFlagEnums.WARN.getCode());
		}
		
	}
	
	
	@Override
	public void railWorkWarnBefore() {
		logger.info("工单作业天窗预警定时任务扫描~~~预警类型为：作业天窗已开始未执行");
		int warnBeforeS=Integer.parseInt(SysParamUtil.getParam("WORKBEFORETIME"));
		String hsql = "from RailWorkInfo t "
				+ "where t.delStatus='"+DelStatusEnums.NO.getCode()+"' and t.auditStatus='1' and "
						+ "TO_NUMBER(t.skylightStart-sysdate)*60*60*24 < "+warnBeforeS+" and to_number(t.workStatus)<1 ";
		List<RailWorkInfo> accessInfos = this.getHibernateTemplate().find(hsql);
		List<RailWorkWarn> list = new ArrayList<RailWorkWarn>();
		for(RailWorkInfo item :accessInfos ){
			List<RailWorkWarn> isList = new ArrayList<RailWorkWarn>();
			if(checkWarnExitBefore(item.getWorkCode(),isList)){
				for(RailWorkWarn ii : isList){
					if(Integer.valueOf(ii.getInfoSign())== InfoSignEnums2.NO.getCode()){
						ii.setAddDate(new Date());
						this.getHibernateTemplate().update(ii);
						logger.info("工单编码为:"+ii.getWorkCode()+"----预警类型为：作业天窗已开始未执行 ");
						
						TSendMsgSocketBO tSendMsgSocketBO = (TSendMsgSocketBO) ContextUtil.getBean("TSendMsgSocketBO");
						tSendMsgSocketBO.sendSocketMsgSpeWhse(item.getWhseCode(), ii.getId(), ApiSendMsgFlagEnums.WARN.getCode());
						
					}
				}
			}else{
				RailWorkWarn maintainWarn = new RailWorkWarn(); 
				maintainWarn.setWorkCode(item.getWorkCode());
				maintainWarn.setInfoSign(String.valueOf(InfoSignEnums2.NO.getCode()));
				maintainWarn.setAddDate(new Date());
				maintainWarn.setWarnMsg(WorkWarnTypeEnums.SKYBEFORE.getDesc());
				maintainWarn.setWarnType(String.valueOf(WorkWarnTypeEnums.SKYBEFORE.getCode()));
				maintainWarn.setNote1(String.valueOf(WorkWarnTypeEnums.SKYBEFORE.getCode()));
				maintainWarn.setNote3(item.getDept());
				maintainWarn.setNote2(item.getWhseCode());
				
				logger.info("工单编码为:"+maintainWarn.getWorkCode()+"----预警类型为：作业天窗已开始未执行 ");
				list.add(maintainWarn);
			};
			
		}
		this.getHibernateTemplate().saveOrUpdateAll(list);
		
		for(RailWorkWarn maintainWarn : list){
			TSendMsgSocketBO tSendMsgSocketBO = (TSendMsgSocketBO) ContextUtil.getBean("TSendMsgSocketBO");
			tSendMsgSocketBO.sendSocketMsgSpeWhse(maintainWarn.getNote2(), maintainWarn.getId(), ApiSendMsgFlagEnums.WARN.getCode());
		}
		
	}
	
	
	private boolean checkWarnExit(String workCode,List<RailWorkWarn>  list){
		String sql = "from RailWorkWarn t where t.workCode = '"+workCode+"' "
				+ "and t.warnType='"+WorkWarnTypeEnums.SKY.getCode()+"' ORDER BY t.addDate DESC";
		List<RailWorkWarn> listRailWorkWarns = this.getHibernateTemplate().find(sql);
		if(null!=listRailWorkWarns &&listRailWorkWarns.size()>0){
			list.add(listRailWorkWarns.get(0));
			return true;
		}
		return false;
	}
	
	private boolean checkWarnExitBefore(String workCode,List<RailWorkWarn>  list){
		String sql = "from RailWorkWarn t where t.workCode = '"+workCode+"' "
				+ "and t.warnType='"+WorkWarnTypeEnums.SKYBEFORE.getCode()+"' ORDER BY t.addDate DESC";
		List<RailWorkWarn> listRailWorkWarns = this.getHibernateTemplate().find(sql);
		if(null!=listRailWorkWarns &&listRailWorkWarns.size()>0){
			list.add(listRailWorkWarns.get(0));
			return true;
		}
		return false;
	}
	@Override
	public void railWorkWarnInNet() {
		logger.info("工单作业天窗预警定时任务扫描~~~预警类型为：作业天窗已开始未入网");
		String hsql = "from RailWorkInfo t "
				+ "where t.delStatus='"+DelStatusEnums.NO.getCode()+"' and t.auditStatus='1' and "
						+ "TO_NUMBER(t.skylightStart-sysdate)*60*60*24 <0 and to_number(t.workStatus)<2 ";
		List<RailWorkInfo> accessInfos = this.getHibernateTemplate().find(hsql);
		List<RailWorkWarn> list = new ArrayList<RailWorkWarn>();
		for(RailWorkInfo item :accessInfos ){
			String equip=null;
			//查询对应的对接的设备信息
			TSendMsgSocketBO tSendMsgSocketBO = (TSendMsgSocketBO) ContextUtil.getBean("TSendMsgSocketBO");
			RailAccessInfo railAccessInfo=tSendMsgSocketBO.getEquipCodeFromAccessCode(item.getAccessInCode());
			if(railAccessInfo!=null)
				equip= railAccessInfo.getNote1();
			
			List<RailWorkWarn> isList = new ArrayList<RailWorkWarn>();
			if(checkWarnExitBefore(item.getWorkCode(),isList)){
				for(RailWorkWarn ii : isList){
					if(Integer.valueOf(ii.getInfoSign())== InfoSignEnums2.NO.getCode()){
						ii.setAddDate(new Date());
						this.getHibernateTemplate().update(ii);
						logger.info("工单编码为:"+ii.getWorkCode()+"----预警类型为：作业天窗已开始未入网 ");
						
						tSendMsgSocketBO = (TSendMsgSocketBO) ContextUtil.getBean("TSendMsgSocketBO");
						tSendMsgSocketBO.sendSocketMsgSpeAccess(item.getAccessInCode(), ii.getId(), ApiSendMsgFlagEnums.WARN.getCode());
						
						//首先先判断当前设备是否连接的上
						if (PubUtil.wsChannelIsAvailable(equip)) {
							String accessOrder=PubUtil.wsGetElectricOrder(equip, 0);
							int wirteNum= PubUtil.wsWriteDataFromChannel(equip, accessOrder);
							if (wirteNum>0) {
								logger.info("工单编码为:"+ii.getWorkCode()+"----预警类型为：作业天窗已开始未入网   发送警报信息成功！ ");
							}
						}else {
							logger.info("工单编码为:"+ii.getWorkCode()+"----预警类型为：作业天窗已开始未入网  发送警报信息失败！ ");
						}
					}
				}
			}else{
				RailWorkWarn maintainWarn = new RailWorkWarn(); 
				maintainWarn.setWorkCode(item.getWorkCode());
				maintainWarn.setInfoSign(String.valueOf(InfoSignEnums2.NO.getCode()));
				maintainWarn.setAddDate(new Date());
				maintainWarn.setWarnMsg(WorkWarnTypeEnums.SKYINNET.getDesc());
				maintainWarn.setWarnType(String.valueOf(WorkWarnTypeEnums.SKYINNET.getCode()));
				maintainWarn.setNote1(String.valueOf(WorkWarnTypeEnums.SKYINNET.getCode()));
				maintainWarn.setNote3(equip);
				maintainWarn.setNote2(item.getAccessInCode());
				
				logger.info("工单编码为:"+maintainWarn.getWorkCode()+"----预警类型为：作业天窗已开始未入网 ");
				list.add(maintainWarn);
			};
			
		}
		this.getHibernateTemplate().saveOrUpdateAll(list);
		
		for(RailWorkWarn maintainWarn : list){
			TSendMsgSocketBO tSendMsgSocketBO = (TSendMsgSocketBO) ContextUtil.getBean("TSendMsgSocketBO");
			tSendMsgSocketBO.sendSocketMsgSpeAccess(maintainWarn.getNote2(), maintainWarn.getId(), ApiSendMsgFlagEnums.WARN.getCode());
			
			//首先先判断当前设备是否连接的上
			if (PubUtil.wsChannelIsAvailable(maintainWarn.getNote3())) {
				String accessOrder=PubUtil.wsGetElectricOrder(maintainWarn.getNote3(), 0);
				int wirteNum= PubUtil.wsWriteDataFromChannel(maintainWarn.getNote3(), accessOrder);
				if (wirteNum>0) {
					logger.info("工单编码为:"+maintainWarn.getWorkCode()+"----预警类型为：作业天窗已开始未入网   发送警报信息成功！ ");
				}
			}else {
				logger.info("工单编码为:"+maintainWarn.getWorkCode()+"----预警类型为：作业天窗已开始未入网  发送警报信息失败！ ");
			}
		}
	}
	@Override
	public void railWorkWarnOutNet() {
		logger.info("工单作业天窗预警定时任务扫描~~~预警类型为：作业天窗已结束未出网");
		String hsql = "from RailWorkInfo t "
				+ "where t.delStatus='"+DelStatusEnums.NO.getCode()+"' and t.auditStatus='1' and "
						+ "TO_NUMBER(t.skylightEnd-sysdate)*60*60*24 <0 and to_number(t.workStatus)<3 ";
		List<RailWorkInfo> accessInfos = this.getHibernateTemplate().find(hsql);
		List<RailWorkWarn> list = new ArrayList<RailWorkWarn>();
		for(RailWorkInfo item :accessInfos ){
			String equip=null;
			//查询对应的对接的设备信息
			TSendMsgSocketBO tSendMsgSocketBO = (TSendMsgSocketBO) ContextUtil.getBean("TSendMsgSocketBO");
			RailAccessInfo railAccessInfo=tSendMsgSocketBO.getEquipCodeFromAccessCode(item.getAccessInCode());
			if(railAccessInfo!=null)
				equip= railAccessInfo.getNote1();
			
			List<RailWorkWarn> isList = new ArrayList<RailWorkWarn>();
			if(checkWarnExitBefore(item.getWorkCode(),isList)){
				for(RailWorkWarn ii : isList){
					if(Integer.valueOf(ii.getInfoSign())== InfoSignEnums2.NO.getCode()){
						ii.setAddDate(new Date());
						this.getHibernateTemplate().update(ii);
						logger.info("工单编码为:"+ii.getWorkCode()+"----预警类型为：作业天窗已结束未出网 ");
						
						tSendMsgSocketBO = (TSendMsgSocketBO) ContextUtil.getBean("TSendMsgSocketBO");
						tSendMsgSocketBO.sendSocketMsgSpeAccess(item.getAccessOutCode(), ii.getId(), ApiSendMsgFlagEnums.WARN.getCode());
						
						//首先先判断当前设备是否连接的上
						if (PubUtil.wsChannelIsAvailable(equip)) {
							String accessOrder=PubUtil.wsGetElectricOrder(equip, 0);
							int wirteNum= PubUtil.wsWriteDataFromChannel(equip, accessOrder);
							if (wirteNum>0) {
								logger.info("工单编码为:"+ii.getWorkCode()+"----预警类型为：作业天窗已结束未出网   发送警报信息成功！ ");
							}
						}else {
							logger.info("工单编码为:"+ii.getWorkCode()+"----预警类型为：作业天窗已结束未出网  发送警报信息失败！ ");
						}
					}
				}
			}else{
				RailWorkWarn maintainWarn = new RailWorkWarn(); 
				maintainWarn.setWorkCode(item.getWorkCode());
				maintainWarn.setInfoSign(String.valueOf(InfoSignEnums2.NO.getCode()));
				maintainWarn.setAddDate(new Date());
				maintainWarn.setWarnMsg(WorkWarnTypeEnums.SKYOUTNET.getDesc());
				maintainWarn.setWarnType(String.valueOf(WorkWarnTypeEnums.SKYOUTNET.getCode()));
				maintainWarn.setNote1(String.valueOf(WorkWarnTypeEnums.SKYOUTNET.getCode()));
				maintainWarn.setNote3(equip);
				maintainWarn.setNote2(item.getAccessOutCode());
				
				logger.info("工单编码为:"+maintainWarn.getWorkCode()+"----预警类型为：作业天窗已结束未出网 ");
				list.add(maintainWarn);
			};
			
		}
		this.getHibernateTemplate().saveOrUpdateAll(list);
		
		for(RailWorkWarn maintainWarn : list){
			TSendMsgSocketBO tSendMsgSocketBO = (TSendMsgSocketBO) ContextUtil.getBean("TSendMsgSocketBO");
			tSendMsgSocketBO.sendSocketMsgSpeAccess(maintainWarn.getNote2(), maintainWarn.getId(), ApiSendMsgFlagEnums.WARN.getCode());
			
			//首先先判断当前设备是否连接的上
			if (PubUtil.wsChannelIsAvailable(maintainWarn.getNote3())) {
				String accessOrder=PubUtil.wsGetElectricOrder(maintainWarn.getNote3(), 0);
				int wirteNum= PubUtil.wsWriteDataFromChannel(maintainWarn.getNote3(), accessOrder);
				if (wirteNum>0) {
					logger.info("工单编码为:"+maintainWarn.getWorkCode()+"----预警类型为：作业天窗已结束未出网   发送警报信息成功！ ");
				}
			}else {
				logger.info("工单编码为:"+maintainWarn.getWorkCode()+"----预警类型为：作业天窗已结束未出网  发送警报信息失败！ ");
			}
		}
	}

}

