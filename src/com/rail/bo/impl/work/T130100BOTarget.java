package com.rail.bo.impl.work;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huateng.common.StringUtil;
import com.huateng.system.util.DateUtil;
import com.informix.util.dateUtil;
import com.rail.bo.work.T130100BO;
import com.rail.dao.iface.work.RailEmployeeDao;
import com.rail.dao.iface.work.RailWorkEmployeeDao;
import com.rail.dao.iface.work.RailWorkInfoDao;
import com.rail.po.base.RailEmployee;
import com.rail.po.tool.RailToolInfo;
import com.rail.po.work.RailWorkEmployee;
import com.rail.po.work.RailWorkInfo;
import com.rail.po.work.RailWorkTool;

/**  
* @author zhaozc
* @version 创建时间：2019年4月15日 上午9:23:37  
* @desc 新增工单
*/
public class T130100BOTarget implements T130100BO {
	private RailWorkInfoDao railWorkInfoDao;
	

	@Override
	public List<RailWorkInfo> getByParam(Map<String, String> paramMap) {
		return this.railWorkInfoDao.getByParam(paramMap);
	}

	@Override
	public String add(RailWorkInfo railWorkInfo) {
		return railWorkInfoDao.add(railWorkInfo);
	}

	public RailWorkInfoDao getRailWorkInfoDao() {
		return railWorkInfoDao;
	}

	public void setRailWorkInfoDao(RailWorkInfoDao railWorkInfoDao) {
		this.railWorkInfoDao = railWorkInfoDao;
	}

	@Override
	public List<RailEmployee> getEmpByTeamId(long teamId) {
		return this.railWorkInfoDao.getEmpByTeamId(teamId);
	}

	@Override
	public String addWorkEmployees(List<RailWorkEmployee> rwes) {
		String result="";
		for(RailWorkEmployee rwe:rwes){
			result=railWorkInfoDao.addWorkEmployees(rwe);
		}
		return result;
	}
	/**
	 * 根据工单编码获得工单信息
	 */
	@Override
	public RailWorkInfo getWorkInfoByCode(String workCode) {
		List list = railWorkInfoDao.getByCode(workCode);
		RailWorkInfo railWorkInfo = new RailWorkInfo();
		if(!list.isEmpty()&list.size()>0){
			Object[] obj = (Object[])list.get(0);
			railWorkInfo.setWorkCode(StringUtil.toString(obj[0]));
			railWorkInfo.setWorkName(StringUtil.toString(obj[1]));
			railWorkInfo.setCostOrgName(StringUtil.toString(obj[2]));
			railWorkInfo.setDispatchCode(StringUtil.toString(obj[3]));
			railWorkInfo.setRegisterType(StringUtil.toString(obj[4]));
			Date sks = DateUtil.strToDate(StringUtil.toString(obj[5]), DateUtil.DATEFORMAT_DEFAULT); 
			railWorkInfo.setSkylightStartStr(DateUtil.dateToStr(sks, DateUtil.DATEFORMAT_DEFAULT));
			Date ske = DateUtil.strToDate(StringUtil.toString(obj[6]), DateUtil.DATEFORMAT_DEFAULT); 
			railWorkInfo.setSkylightEndStr(DateUtil.dateToStr(ske, DateUtil.DATEFORMAT_DEFAULT));
			railWorkInfo.setAccessInCode(StringUtil.toString(obj[7]));
			railWorkInfo.setAccessOutCode(StringUtil.toString(obj[8]));
			railWorkInfo.setWorkPic(StringUtil.toString(obj[9]));
			railWorkInfo.setWorkTel(StringUtil.toString(obj[10]));
			railWorkInfo.setWorkCount(StringUtil.toString(obj[11]));
			railWorkInfo.setInPatrol(StringUtil.toString(obj[12]));
			railWorkInfo.setOutPatrol(StringUtil.toString(obj[13]));
			railWorkInfo.setWorkAddress(StringUtil.toString(obj[14]));
			railWorkInfo.setWorkMileage(StringUtil.toString(obj[15]));
			railWorkInfo.setEmployeeCount(Long.parseLong(StringUtil.toString(obj[16])));
			railWorkInfo.setWorkStandard(StringUtil.toString(obj[17]));
			railWorkInfo.setRiskControl(StringUtil.toString(obj[18]));
			railWorkInfo.setLineLevel(StringUtil.toString(obj[19]));
			railWorkInfo.setRowLevel(StringUtil.toString(obj[20]));
			railWorkInfo.setStation(StringUtil.toString(obj[21]));
			railWorkInfo.setFormOrgName(StringUtil.toString(obj[22]));
			railWorkInfo.setPatrolName(StringUtil.toString(obj[23]));
			railWorkInfo.setRegStation(StringUtil.toString(obj[24]));
			railWorkInfo.setResidentLiaison(StringUtil.toString(obj[25]));
			railWorkInfo.setResidentStation(StringUtil.toString(obj[26]));
			railWorkInfo.setResidentOnline(StringUtil.toString(obj[27]));
			railWorkInfo.setTargetingEmployeeName(StringUtil.toString(obj[28]));
			railWorkInfo.setBrhName(StringUtil.toString(obj[29]));
			railWorkInfo.setApplyMsg(StringUtil.toString(obj[30]));
			railWorkInfo.setAuditStatus(StringUtil.toString(obj[31],"-1"));
			railWorkInfo.setAuditMsg(StringUtil.toString(obj[32]));
			railWorkInfo.setAuditUser(StringUtil.toString(obj[33]));
			railWorkInfo.setInterphone(StringUtil.toString(obj[34]));
			railWorkInfo.setWorkTeam((Long.parseLong(StringUtil.toString(obj[35]))));
			railWorkInfo.setFormOrg((Long.parseLong(StringUtil.toString(obj[36]))));
			railWorkInfo.setMaintenceType(StringUtil.toString(obj[37]));
			railWorkInfo.setPatrol((Long.parseLong(StringUtil.toString(obj[38]))));
			railWorkInfo.setTargetingEmployeeCode(StringUtil.toString(obj[39]));
			railWorkInfo.setDept(StringUtil.toString(obj[40]));
			railWorkInfo.setAccessInName(StringUtil.toString(obj[41]));
			railWorkInfo.setAccessOutName(StringUtil.toString(obj[42]));
			railWorkInfo.setTeamName(StringUtil.toString(obj[43]));
			Date auditDate = DateUtil.strToDate(StringUtil.toString(obj[44]), DateUtil.DATEFORMAT_DEFAULT); 
			railWorkInfo.setAuditDateStr(DateUtil.dateToStr(auditDate, DateUtil.DATEFORMAT_DEFAULT));
			railWorkInfo.setWhseCode(StringUtil.toString(obj[45]));
			railWorkInfo.setWhseName(StringUtil.toString(obj[46]));
			railWorkInfo.setId(Long.parseLong(StringUtil.toString(obj[47])));
		}
		return railWorkInfo;
	}
	/**
	 * 更新工单信息
	 */
	@Override
	public String updateRailWorkInfo(RailWorkInfo railWorkInfo) {
		return railWorkInfoDao.updateWork(railWorkInfo);
	}

	@Override
	public void delWorkEmployeesByWorkCode(String workCode) {
		railWorkInfoDao.delWorkEmployeesByWorkCode(workCode);
	}

	@Override
	public List<RailWorkEmployee> getWorkEmployeeByParam(Map<String, String> paramMap) {
		return railWorkInfoDao.getWorkEmployeeByParam(paramMap);
	}

	@Override
	public String updateRailWorkEmployee(RailWorkEmployee railWorkEmployee) {
		return railWorkInfoDao.updateRailWorkEmployee(railWorkEmployee);
	}

	@Override
	public void delWorkToolByWorkCode(String workCode) {
		railWorkInfoDao.delWorkToolByWorkCode(workCode);
	}

	@Override
	public String addWorkTool(List<RailWorkTool> rwts) {
		String result="";
		for(RailWorkTool rwt:rwts){
			result=railWorkInfoDao.addWorkTool(rwt);
		}
		return result;
	}

	@Override
	public List<Map<String,Object>> getWarnings(Date begin, Date end) {
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("beginTime",DateUtil.dateToStr(begin, DateUtil.DATEFORMAT_DEFAULT));
		paramMap.put("endTime",DateUtil.dateToStr(end, DateUtil.DATEFORMAT_DEFAULT));
		List<Map<String,Object>> workWarning = railWorkInfoDao.getWorkWarning(paramMap);
		return workWarning;
	}

	@Override
	public int getWhseToolNum(String whseCode, String nameID) {
		return railWorkInfoDao.getWhseToolNum(whseCode,nameID);
	}

	@Override
	public RailToolInfo getToolInfoByToolCode(String toolCode) {
		List list = railWorkInfoDao.getToolInfoByToolCode(toolCode);
		RailToolInfo railToolInfo = new RailToolInfo();
		if(!list.isEmpty()&list.size()>0){
			railToolInfo =(RailToolInfo)list.get(0);
		}
		return railToolInfo;
	}

	@Override
	public List<Map<String, Object>> getAllWarnings() {
		List<Map<String,Object>> workWarning = railWorkInfoDao.getAllWarnings();
		return workWarning;
	}


}

