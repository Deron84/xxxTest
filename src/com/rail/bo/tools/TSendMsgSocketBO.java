package com.rail.bo.tools;

import java.util.List;

import com.rail.po.access.RailAccessInfo;
import com.rail.po.work.RailWorkTool;

public interface TSendMsgSocketBO {

	public List<String> getWhseCodeThreeLevel(String whseCode);
	/**
	 * @param params  dept
	 *  工具  工单 人员 所属部门  
	 *  id  主键
	 *  type ApiSendMsgFlagEnums 枚举的类型【 1 工具 2 人员  3 工单 4 预警】
	 * @return
	 */
	boolean sendSocketMsg(String dept,long id,int type);
	
	boolean sendSocketMsgSpeWhse(String whseCode,long id,int type);
	
	boolean sendSocketMsgSpeAccess(String whseCode,long id,int type);
	
	RailAccessInfo getAccessCodeFromEquipCode(String equipCode) ;
	
	RailAccessInfo getEquipCodeFromAccessCode(String AccessCode) ;
	RailWorkTool getWorkToolInfo(String toolCode);
}