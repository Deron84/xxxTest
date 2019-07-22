package com.rail.dao.impl.work;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.system.util.CommonFunction;
import com.rail.dao.iface.work.RailWorkInfoDao;
import com.rail.po.base.RailEmployee;
import com.rail.po.warehouse.RailWhseInfo;
import com.rail.po.work.RailWorkEmployee;
import com.rail.po.work.RailWorkInfo;
import com.rail.po.work.RailWorkTool;
import com.sdses.dao._RootDAO;

public class RailWorkInfoDaoTarget extends _RootDAO<RailWhseInfo> implements RailWorkInfoDao{
	
	@Override
	protected Class<RailWorkInfo> getReferenceClass() {
		return RailWorkInfo.class;
	}
	/**
	 * 根据不同参数条件查询工单
	 */
	@Override
	public List<RailWorkInfo> getByParam(Map<String, String> paramMap) {
		String sql = "from RailWorkInfo where 1=1 ";
		for(String key : paramMap.keySet()){
			String value = paramMap.get(key);
			sql+=" and "+key+"='"+value+"'";
		}
		return getHibernateTemplate().find(sql);
	}
	
	/**
	 * 按照特定条件来查询对应的工单信息
	 */
	public List<RailWorkInfo> getByParam(String accessCode) {
		String sql = "from RailWorkInfo t where 1=1 ";
		sql+=" and (t.accessInCode='"+accessCode+"' or t.accessOutCode='"+accessCode+"') ";
		sql+=" and t.skylightStart<= '"+new Date()+"'";
		sql+=" and t.skylightEnd>= '"+new Date()+"'";
		sql+=" and t.workStatus= '1'";
		return getHibernateTemplate().find(sql);
	}
	/**
	 * 新增工单信息
	 */
	@Override
	public String add(RailWorkInfo railWorkInfo) {
		try {
			super.save(railWorkInfo);
			return Constants.SUCCESS_CODE;
		} catch (Exception e) {
			return Constants.FAILURE_CODE;
		}
	}
	/**
	 * 修改工单信息
	 */
	@Override
	public String updateWork(RailWorkInfo railWorkInfo) {
		try {
			super.update(railWorkInfo);
			return Constants.SUCCESS_CODE;
		} catch (Exception e) {
			return Constants.FAILURE_CODE;
		}
	}
	/**
	 * 根据工单编码查询工单
	 */
	@Override
	public List getByCode(String workCode) {
		String sql = "SELECT rwi.WORK_CODE,rwi.WORK_NAME,rco.COST_ORG_NAME,rwi.DISPATCH_CODE,rwi.REGISTER_TYPE,"
				+ "TO_CHAR(rwi.SKYLIGHT_START,'yyyy-mm-dd hh24:mi:ss') as SKYLIGHT_START,TO_CHAR(rwi.SKYLIGHT_END,'yyyy-mm-dd hh24:mi:ss') as SKYLIGHT_END,rwi.ACCESS_IN_CODE,rwi.ACCESS_OUT_CODE,rwi.WORK_PIC,rwi.WORK_TEL,"
				+ "rwi.WORK_COUNT,rwi.IN_PATROL,rwi.OUT_PATROL,rwi.WORK_ADDRESS,rwi.WORK_MILEAGE,rwi.EMPLOYEE_COUNT,"
				+ "rwi.WORK_STANDARD,rwi.RISK_CONTROL,rwi.LINE_LEVEL,rwi.ROW_LEVEL,rwi.STATION,rfo.FORM_ORG_NAME,rp.PATROL_NAME,rwi.REG_STATION,rwi.RESIDENT_LIAISON,rwi.RESIDENT_STATION,rwi.RESIDENT_ONLINE,"
				+ "re.EMPLOYEE_NAME,tbi.BRH_NAME,rwi.APPLY_MSG,rwi.AUDIT_STATUS,rwi.AUDIT_MSG,rwi.AUDIT_USER,rwi.INTERPHONE,rwi.WORK_TEAM,rwi.FORM_ORG,rwi.MAINTENCE_TYPE,rwi.PATROL,"
				+ "rwi.TARGETING_EMPLOYEE_CODE,rwi.DEPT,raiIn.ACCESS_NAME as inAccessName,raiOut.ACCESS_NAME as outAccessName,rt.WORK_TEAM_NAME,TO_CHAR(rwi.AUDIT_DATE,'yyyy-mm-dd hh24:mi:ss') as AUDIT_DATE,rwi.WHSE_CODE,rwi2.WHSE_NAME "
				+ " , rwi.id FROM RAIL_WORK_INFO rwi "
				+ "LEFT JOIN RAIL_CONST_ORG rco  ON rco.ID = rwi.CONST_ORG "
				+ "LEFT JOIN RAIL_FORM_ORG rfo  ON rfo.ID = rwi.FORM_ORG  "
				+ "LEFT JOIN RAIL_PATROL rp ON rp.ID = rwi.PATROL "
				+ "LEFT JOIN RAIL_EMPLOYEE re ON rwi.TARGETING_EMPLOYEE_CODE = re.EMPLOYEE_CODE "
				+ "LEFT JOIN TBL_BRH_INFO tbi ON rwi.DEPT = tbi.BRH_ID "
				+ "LEFT JOIN RAIL_ACCESS_INFO raiIn ON rwi.ACCESS_IN_CODE = raiIn.ACCESS_CODE "
				+ "LEFT JOIN RAIL_ACCESS_INFO raiOut ON rwi.ACCESS_OUT_CODE = raiOut.ACCESS_CODE "
				+ "LEFT JOIN RAIL_TEAM rt ON rt.ID = rwi.WORK_TEAM "
				+ "LEFT JOIN RAIL_WHSE_INFO rwi2 ON rwi.WHSE_CODE = rwi2.WHSE_CODE "
				+ "WHERE 1=1 AND rwi.WORK_CODE = '"+workCode+"'";
		return  CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
	}
	/**
	 * 根据teamID查询员工列表
	 */
	@Override
	public List<RailEmployee> getEmpByTeamId(long teamId) {
		String sql = "select re.EMPLOYEE_CODE,re.EMPLOYEE_NAME,re.EMPLOYEE_IMG FROM RAIL_TEAM_EMPLOYEE rte LEFT JOIN RAIL_EMPLOYEE re ON rte.EMPLOYEE_CODE=re.EMPLOYEE_CODE WHERE 1=1 and rte.WORK_TEAM="+teamId;
		List list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
//		System.out.println(list.size()+"  >>>>>>>>>>>>>>");
		return list;
	}
	/**
	 * 为工单分配员工
	 */
	@Override
	public String addWorkEmployees(RailWorkEmployee rwe) {
		try {
			super.save(rwe);
			return Constants.SUCCESS_CODE;
		} catch (Exception e) {
			return Constants.FAILURE_CODE;
		}
	}
	@Override
	public void delWorkEmployeesByWorkCode(String workCode) {
		String sql = "delete from RAIL_WORK_EMPLOYEE where WORK_CODE = '"+workCode+"'";
		CommonFunction.getCommQueryDAO().excute(sql);
	}
	@Override
	public List<RailWorkEmployee> getWorkEmployeeByParam(Map<String, String> paramMap) {
		String querySql = "from RailWorkEmployee where 1=1 ";
		for (String key : paramMap.keySet()) {
			String value = paramMap.get(key);
			querySql += " and " + key + "='" + value + "'";
		}
		System.out.println("根据参数查询门禁querySql:" + querySql);
		return getHibernateTemplate().find(querySql + "");	
	}
	@Override
	public String updateRailWorkEmployee(RailWorkEmployee railWorkEmployee) {
		try {
			super.update(railWorkEmployee);
			return Constants.SUCCESS_CODE;
		} catch (Exception e) {
			return Constants.FAILURE_CODE;
		}
	}
	@Override
	public void delWorkToolByWorkCode(String workCode) {
		String sql = "delete from RAIL_WORK_TOOL where WORK_CODE = '"+workCode+"'";
		CommonFunction.getCommQueryDAO().excute(sql);
	}
	@Override
	public String addWorkTool(RailWorkTool rwt) {
		try {
			super.save(rwt);
			return Constants.SUCCESS_CODE;
		} catch (Exception e) {
			return Constants.FAILURE_CODE;
		}
	}
	@Override
	public List<Map<String,Object>> getWorkWarning(Map<String, Object> paramMap) {
		String workSql="SELECT t.WORK_CODE, t1.WORK_NAME FROM RAIL_WORK_WARN t, RAIL_WORK_INFO t1 "
				+ " WHERE  1= 1 AND t.WORK_CODE = t1.WORK_CODE AND t.INFO_SIGN = 0  AND t1.DEL_STATUS = 0 "
				+ " AND t.ADD_DATE >= to_date( '"+paramMap.get("beginTime")+"', 'yyyy-mm-dd hh24:mi:ss' ) AND t.ADD_DATE < to_date('"+paramMap.get("endTime")+"', 'yyyy-mm-dd hh24:mi:ss' ) ";
		List worklist = CommonFunction.getCommQueryDAO().findBySQLQuery(workSql);//工单预警
		
		String toolSql = "SELECT t.TOOL_CODE,t2.TOOL_NAME "
				+ " FROM RAIL_TOOL_MAINTAIN_WARN t,RAIL_TOOL_INFO t1,RAIL_TOOL_NAME t2 "
				+ " WHERE 1=1 AND t.TOOL_CODE = t1.TOOL_CODE AND t1.TOOL_NAME = t2.ID AND t.INFO_SIGN = 0 AND t1.DEL_STATUS = 0 "
				+ " AND t.ADD_DATE >= to_date( '"+paramMap.get("beginTime")+"', 'yyyy-mm-dd hh24:mi:ss' ) AND t.ADD_DATE < to_date('"+paramMap.get("endTime")+"', 'yyyy-mm-dd hh24:mi:ss' ) ";
		List toolList = CommonFunction.getCommQueryDAO().findBySQLQuery(toolSql);//工具保养预警
		
		String accessSql = "SELECT t.ACCESS_CODE,t1.ACCESS_NAME "
				+ " FROM RAIL_ACCESS_WARN t,RAIL_ACCESS_INFO t1 "
				+ " WHERE 1=1 AND t.ACCESS_CODE = t1.ACCESS_CODE AND t.INFO_SIGN = 0 AND t1.DEL_STATUS = 0"
				+ " AND t.ADD_DATE >= to_date( '"+paramMap.get("beginTime")+"', 'yyyy-mm-dd hh24:mi:ss' ) AND t.ADD_DATE < to_date('"+paramMap.get("endTime")+"', 'yyyy-mm-dd hh24:mi:ss' ) ";
		List accessList = CommonFunction.getCommQueryDAO().findBySQLQuery(accessSql);//门禁预警
		
		String accessWhSql = "SELECT t.ACCESS_CODE,t1.ACCESS_NAME "
				+ " FROM RAIL_ACCESS_MAINTAIN_WARN t,RAIL_ACCESS_INFO t1 "
				+ " WHERE 1=1 AND t.ACCESS_CODE = t1.ACCESS_CODE AND t.INFO_SIGN = 0 AND t1.DEL_STATUS = 0"
				+ " AND t.ADD_DATE >= to_date( '"+paramMap.get("beginTime")+"', 'yyyy-mm-dd hh24:mi:ss' ) AND t.ADD_DATE < to_date('"+paramMap.get("endTime")+"', 'yyyy-mm-dd hh24:mi:ss' ) ";
		List accessWhList = CommonFunction.getCommQueryDAO().findBySQLQuery(accessWhSql);//门禁保养预警
		
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
		Map<String,Object> resultMap = new HashMap<String, Object>();
		if(worklist.size()>0){
			resultMap = new HashMap<String, Object>();
			resultMap.put("warningName", "工单预警");
			resultMap.put("warningNum", worklist.size());
			resultMap.put("warningType", 13);
			results.add(resultMap);
		}
		if(toolList.size()>0){
			resultMap = new HashMap<String, Object>();
			resultMap.put("warningName", "工具预警");
			resultMap.put("warningNum", toolList.size());
			resultMap.put("warningType", 11);
			results.add(resultMap);
		}
		if(accessList.size()>0){
			resultMap = new HashMap<String, Object>();
			resultMap.put("warningName", "门禁预警");
			resultMap.put("warningNum", accessList.size());
			resultMap.put("warningType", 12);
			results.add(resultMap);
		}
		if(accessWhList.size()>0){
			resultMap = new HashMap<String, Object>();
			resultMap.put("warningName", "门禁检修保养预警");
			resultMap.put("warningNum", accessWhList.size());
			resultMap.put("warningType", -12);
			results.add(resultMap);
		}
		return results;
	}
	@Override
	public int getWhseToolNum(String whseCode, String nameID) {
		String sql = "SELECT COUNT(*) FROM RAIL_TOOL_INFO t WHERE t.WHSE_CODE='"+whseCode+"' AND t.TOOL_NAME = "+nameID+" AND t.IN_WHSE = 1 AND t.DEL_STATUS=0 AND t.TOOL_STATUS = 0";
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql);
		
		return StringUtil.toInt(count);
	}
	@Override
	public List getToolInfoByToolCode(String toolCode) {
		String sql = " FROM RailToolInfo WHERE TOOL_CODE = '"+toolCode+"'";
		return  getHibernateTemplate().find(sql + "");//CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
	}
	@Override
	public List<Map<String, Object>> getAllWarnings() {
		String workSql="SELECT t.WORK_CODE, t1.WORK_NAME FROM RAIL_WORK_WARN t, RAIL_WORK_INFO t1 "
				+ " WHERE  1= 1 AND t.WORK_CODE = t1.WORK_CODE AND t.INFO_SIGN = 0  AND t1.DEL_STATUS = 0 ";
				//+ " AND t.ADD_DATE >= to_date( '"+paramMap.get("beginTime")+"', 'yyyy-mm-dd hh24:mi:ss' ) AND t.ADD_DATE < to_date('"+paramMap.get("endTime")+"', 'yyyy-mm-dd hh24:mi:ss' ) ";
		List worklist = CommonFunction.getCommQueryDAO().findBySQLQuery(workSql);//工单预警
		
		String toolSql = "SELECT t.TOOL_CODE,t2.TOOL_NAME "
				+ " FROM RAIL_TOOL_MAINTAIN_WARN t,RAIL_TOOL_INFO t1,RAIL_TOOL_NAME t2 "
				+ " WHERE 1=1 AND t.TOOL_CODE = t1.TOOL_CODE AND t1.TOOL_NAME = t2.ID AND t.INFO_SIGN = 0 AND t1.DEL_STATUS = 0 ";
				//+ " AND t.ADD_DATE >= to_date( '"+paramMap.get("beginTime")+"', 'yyyy-mm-dd hh24:mi:ss' ) AND t.ADD_DATE < to_date('"+paramMap.get("endTime")+"', 'yyyy-mm-dd hh24:mi:ss' ) ";
		List toolList = CommonFunction.getCommQueryDAO().findBySQLQuery(toolSql);//工具保养预警
		
		String accessSql = "SELECT t.ACCESS_CODE,t1.ACCESS_NAME "
				+ " FROM RAIL_ACCESS_WARN t,RAIL_ACCESS_INFO t1 "
				+ " WHERE 1=1 AND t.ACCESS_CODE = t1.ACCESS_CODE AND t.INFO_SIGN = 0 AND t1.DEL_STATUS = 0";
				//+ " AND t.ADD_DATE >= to_date( '"+paramMap.get("beginTime")+"', 'yyyy-mm-dd hh24:mi:ss' ) AND t.ADD_DATE < to_date('"+paramMap.get("endTime")+"', 'yyyy-mm-dd hh24:mi:ss' ) ";
		List accessList = CommonFunction.getCommQueryDAO().findBySQLQuery(accessSql);//门禁预警
		
		String accessWhSql = "SELECT t.ACCESS_CODE,t1.ACCESS_NAME "
				+ " FROM RAIL_ACCESS_MAINTAIN_WARN t,RAIL_ACCESS_INFO t1 "
				+ " WHERE 1=1 AND t.ACCESS_CODE = t1.ACCESS_CODE AND t.INFO_SIGN = 0 AND t1.DEL_STATUS = 0";
				//+ " AND t.ADD_DATE >= to_date( '"+paramMap.get("beginTime")+"', 'yyyy-mm-dd hh24:mi:ss' ) AND t.ADD_DATE < to_date('"+paramMap.get("endTime")+"', 'yyyy-mm-dd hh24:mi:ss' ) ";
		List accessWhList = CommonFunction.getCommQueryDAO().findBySQLQuery(accessWhSql);//门禁保养预警
		
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
//		Map<String,Object> resultMap = new HashMap<String, Object>();
		if(worklist.size()>0){
			Map<String,Object> resultMap = new HashMap<String, Object>();
			resultMap.put("warningName", "工单预警");
			resultMap.put("warningNum", worklist.size());
			resultMap.put("warningType", 13);
			results.add(resultMap);
		}
		if(toolList.size()>0){
			Map<String,Object> resultMap = new HashMap<String, Object>();
			resultMap.put("warningName", "工具预警");
			resultMap.put("warningNum", toolList.size());
			resultMap.put("warningType", 11);
			results.add(resultMap);
		}
		if(accessList.size()>0){
			Map<String,Object> resultMap = new HashMap<String, Object>();
			resultMap.put("warningName", "门禁预警");
			resultMap.put("warningNum", accessList.size());
			resultMap.put("warningType", 12);
			results.add(resultMap);
		}
		if(accessWhList.size()>0){
			Map<String,Object> resultMap = new HashMap<String, Object>();
			resultMap.put("warningName", "门禁检修保养预警");
			resultMap.put("warningNum", accessWhList.size());
			resultMap.put("warningType", -12);
			results.add(resultMap);
		}
		return results;
	}
}
