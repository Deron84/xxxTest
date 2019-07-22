package com.rail.bo.impl.tools;

import java.util.ArrayList;
import java.util.List;

import com.huateng.system.util.ContextUtil;
import com.rail.bo.api.app.ApiAppBO;
import com.rail.bo.tools.T110100BO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.rail.bo.tools.TSendMsgSocketBO;
import com.rail.po.access.RailAccessEquipInfo;
import com.rail.po.access.RailAccessInfo;
import com.rail.po.tool.RailToolInfo;
import com.rail.po.warehouse.RailWhseInfo;
import com.rail.po.work.RailWorkTool;
import com.rail.zWebSocket.PubTcp;

/**  
* @author syl
* @version 创建时间：2019年5月5日 下午3:57:24  
* @desc
*/
public class TSendMsgSocketBOTarget extends HibernateDaoSupport implements TSendMsgSocketBO{
	private Log log = LogFactory.getLog(this.getClass());
	
	@Override
	public List<String> getWhseCodeThreeLevel(String whseCode){
		List<String> retList=new ArrayList<String>();
		if (whseCode!=null &&!"".equalsIgnoreCase(whseCode.trim())) {
			retList.add(whseCode);
			String sqlString="FROM RailWhseInfo t where t.parentWhseCode='"+whseCode+"'";
			List<RailWhseInfo> listWhses = this.getHibernateTemplate().find(sqlString);
			if (listWhses!=null && listWhses.size()>0) {
				for (int i = 0; i < listWhses.size(); i++) {
					RailWhseInfo railWhseInfo=listWhses.get(i);
					String whseDCode=railWhseInfo.getWhseCode();
					if (whseDCode!=null && !"".equalsIgnoreCase(whseDCode.trim())) {
						retList.add(whseDCode);
					}
				}
			}
		}
		return retList;
	}
	@Override
	/**
	 * dept 工具  工单 人员 所属部门
	 */
	public boolean sendSocketMsg(String dept, long id, int type) {

		List<String> listDept = new ArrayList<>();
		listDept.add(dept);

		ApiAppBO apiAppBO = (ApiAppBO) ContextUtil.getBean("ApiAppBO");
		String where  = apiAppBO.getWhere(listDept,dept);

		String message = id+","+type;
		/*String hsql = "select new com.rail.po.access.RailAccessEquipInfo(t.accessCode,t.equipCode)
		from RailAccessEquipInfo t, RailAccessInfo t1,RailWorkInfo t2 "*/
		String hsql = "select new com.rail.po.access.RailAccessEquipInfo(t.accessCode,t.equipCode) " +
				"from RailAccessEquipInfo t, RailAccessInfo t1  "
				+ " where t.accessCode = t1.accessCode "
				+ " and t1.accessDept in "+where;

		List<RailAccessEquipInfo> lAccessEquipInfos = this.getHibernateTemplate().find(hsql);
		for(RailAccessEquipInfo item :lAccessEquipInfos ){
			String  equipCode = item.getEquipCode();
			if(!PubTcp.sendMessage(equipCode, message, true)){
				log.error(equipCode+"未发送"+message);
				continue;
			}else{
				log.info(equipCode+"发送成功"+message);
			}
		}
		return true;
	}
	
	
	@Override
	/**
	 * WhseCode 发送某仓库及所属下级仓库的门禁信息
	 */
	public boolean sendSocketMsgSpeWhse(String WhseCode, long id, int type) {

		String message = id+","+type;
		/*String hsql = "select new com.rail.po.access.RailAccessEquipInfo(t.accessCode,t.equipCode)
		from RailAccessEquipInfo t, RailAccessInfo t1,RailWorkInfo t2 "*/
		String hsql = "select new com.rail.po.access.RailAccessEquipInfo(t2.accessCode,t2.equipCode) " +
				"from RailWhseInfo t,RailAccessInfo t1,RailAccessEquipInfo t2  "
				+ " where (t.whseCode='"+WhseCode+"' or t.parentWhseCode='"+WhseCode+"')"
				+ " and t.whseCode=t1.whseCode and t1.accessCode=t2.accessCode "
				+ " and t2.accessType=3";

		List<RailAccessEquipInfo> lAccessEquipInfos = this.getHibernateTemplate().find(hsql);
		for(RailAccessEquipInfo item :lAccessEquipInfos ){
			String  equipCode = item.getEquipCode();
			if(!PubTcp.sendMessage(equipCode, message, true)){
				log.error(equipCode+"未发送"+message);
				continue;
			}else{
				log.info(equipCode+"发送成功"+message);
			}
		}
		return true;
	}
	
	
	@Override
	/**
	 * AccessCode 发送信息到对应通道门 Android设备
	 */
	public boolean sendSocketMsgSpeAccess(String AccessCode, long id, int type) {

		String message = id+","+type;
		/*String hsql = "select new com.rail.po.access.RailAccessEquipInfo(t.accessCode,t.equipCode)
		from RailAccessEquipInfo t, RailAccessInfo t1,RailWorkInfo t2 "*/
		String hsql = "select new com.rail.po.access.RailAccessEquipInfo(t2.accessCode,t2.equipCode) " +
				"from RailAccessInfo t1,RailAccessEquipInfo t2  "
				+ " where t1.accessCode='"+AccessCode+"' and t1.accessCode=t2.accessCode "
				+ " and t2.accessType=3";

		List<RailAccessEquipInfo> lAccessEquipInfos = this.getHibernateTemplate().find(hsql);
		for(RailAccessEquipInfo item :lAccessEquipInfos ){
			String  equipCode = item.getEquipCode();
			if(!PubTcp.sendMessage(equipCode, message, true)){
				log.error(equipCode+"未发送"+message);
				continue;
			}else{
				log.info(equipCode+"发送成功"+message);
			}
		}
		return true;
	}
	//TODO
	public boolean sendSocketMsg(long id, int type, String workCode){
		String message = id+","+type;
		String whseCode ;
		String accessInCodeCode ;
		String accessOutCodeCode ;
		List<String> sendList = new ArrayList<>();

		List<RailAccessInfo> accessInfoList = this.getHibernateTemplate().find("");

		for(String equipCode :sendList ){
			if(!PubTcp.sendMessage(equipCode, message, true)){
				log.error(equipCode+"未发送"+message);
				continue;
			}else{
				log.info(equipCode+"发送成功"+message);
			}
		}

		return true;
	}
	/**
	 * @Description: 通过equipCode得到对应的AccessCode 
	 * @param @param equipCode
	 * @param @return
	 * @return String
	 * @throws
	 */
	public RailAccessInfo getAccessCodeFromEquipCode(String equipCode) {
		String hsql = "FROM RailAccessInfo WHERE note1='"+equipCode+"'";
		List<RailAccessInfo> list_depts= this.getHibernateTemplate().find(hsql);
		if(list_depts==null || list_depts.size()==0){
			return null;
		}
		return list_depts.get(0);
	}
	
	
	/**
	 * @Description: 通过AccessCode得到对应的EquipCode 
	 * @param @param equipCode
	 * @param @return
	 * @return String
	 * @throws
	 */
	public RailAccessInfo getEquipCodeFromAccessCode(String AccessCode) {
		String hsql = "FROM RailAccessInfo WHERE accessCode='"+AccessCode+"'";
		List<RailAccessInfo> list_depts= this.getHibernateTemplate().find(hsql);
		if(list_depts==null || list_depts.size()==0){
			return null;
		}
		return list_depts.get(0);
	}
	/**
	 * @Description: 查询在未开始或者进行中的工单工具 
	 * @param @param toolCode
	 * @param @return
	 * @return RailToolInfo
	 * @throws
	 */
	public RailWorkTool getWorkToolInfo(String toolCode){
		String hsql = "select t1 FROM RailWorkTool t1,RailWorkInfo t2 WHERE t1.workCode=t2.workCode "
				+ "and t1.toolCode='"+toolCode+"' and t2.workStatus<2 ";
		List<RailWorkTool> list_tools= this.getHibernateTemplate().find(hsql);
		if(list_tools==null || list_tools.size()==0){
			return null;
		}
		return list_tools.get(0);
	}	
}

