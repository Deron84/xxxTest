package com.rail.po.work;

import java.util.Date;
import java.util.List;

import com.rail.po.base.RailEmployee;
import com.rail.po.tool.RailToolInfo;
import com.rail.po.vo.RailToolInfoVo;

/**
 * RailWorkInfo entity. @author MyEclipse Persistence Tools
 */

public class RailWorkInfo implements java.io.Serializable {

	// Fields

	private Long id;
	private String workCode;
	private String workName;
	private Long constOrg;
	private String dispatchCode;
	private String registerType;
	private Date workStart;
	private Date workEnd;
	private Date skylightStart;
	private Date skylightEnd;
	private String accessInCode;
	private String accessOutCode;
	private Long workTeam;
	private String workPic;
	private String workTel;
	private String workCount;
	private String inPatrol;
	private String outPatrol;
	private String workAddress;
	private String workMileage;
	private Long employeeCount;//作业人数
	private String delStatus;
	private String workStatus;
	private String workStandard;
	private String riskControl;
	private String completionStatus;
	private String applyMsg;
	private String applyUser;
	private Date applyDate;
	private String auditUser;
	private Date auditDate;
	private String auditMsg;
	private Date toolExpiration;
	private String processTracking;
	private String note1;
	private String note2;
	private String note3;
	private String note4;
	private String note5;
	private String lineLevel;
	private String rowLevel;
	private String maintenceType;
	private String skylightType;
	private String station;
	private Long formOrg;
	private String regStation;
	private Long patrol;
	private String residentLiaison;
	private String residentStation;
	private String residentOnline;
	private String interphone;
	private String targetingEmployeeCode;
	private String dept;
	private String auditStatus;
	private String whseCode;
	private String whseName;
	
	private List<RailEmployee> employeeList;
	private List<RailToolInfoVo> toolsList; //工具列表
	private List<RailEmployee> listRailteams;//小组成员
	private int toolsCount;
	private String employeeName;
	private String targetingEmployeeName;//盯控干部
	private String workTeamName;
	private String costOrgName;//施工单位
	private String formOrgName;//组织单位（配合单位）
	private String patrolName;//巡护中队名称
	private String brhName;//部门名称
	
	private String skylightStartStr;
	private String skylightEndStr;
	private String auditDateStr;
	
	public String getAuditDateStr() {
		return auditDateStr;
	}

	public void setAuditDateStr(String auditDateStr) {
		this.auditDateStr = auditDateStr;
	}

	private String accessInName;
	private String accessOutName;
	
	private String teamName;
	
	// Constructors
	

	public List<RailEmployee> getListRailteams() {
		return listRailteams;
	}

	public int getToolsCount() {
		return toolsCount;
	}

	public void setToolsCount(int toolsCount) {
		this.toolsCount = toolsCount;
	}

	public void setListRailteams(List<RailEmployee> listRailteams) {
		this.listRailteams = listRailteams;
	}

	public List<RailEmployee> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<RailEmployee> employeeList) {
		this.employeeList = employeeList;
	}

	public List<RailToolInfoVo> getToolsList() {
		return toolsList;
	}
	


	public void setToolsList(List<RailToolInfoVo> toolsList) {
		this.toolsList = toolsList;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getTargetingEmployeeName() {
		return targetingEmployeeName;
	}

	public void setTargetingEmployeeName(String targetingEmployeeName) {
		this.targetingEmployeeName = targetingEmployeeName;
	}

	public String getWorkTeamName() {
		return workTeamName;
	}

	public void setWorkTeamName(String workTeamName) {
		this.workTeamName = workTeamName;
	}

	public String getCostOrgName() {
		return costOrgName;
	}

	public void setCostOrgName(String costOrgName) {
		this.costOrgName = costOrgName;
	}

	public String getFormOrgName() {
		return formOrgName;
	}

	public void setFormOrgName(String formOrgName) {
		this.formOrgName = formOrgName;
	}

	/** default constructor */
	public RailWorkInfo() {
	}

	/** minimal constructor */
	public RailWorkInfo(Long id) {
		this.id = id;
	}

	/** full constructor */
	public RailWorkInfo(Long id, String workCode, String workName, Long constOrg, String dispatchCode,
			String registerType, Date workStart, Date workEnd, Date skylightStart, Date skylightEnd,
			String accessInCode, String accessOutCode, Long workTeam, String workPic, String workTel, String workCount,
			String inPatrol, String outPatrol, String workAddress, String workMileage, Long employeeCount,
			String delStatus, String workStatus, String workStandard, String riskControl, String completionStatus,
			String applyMsg, String applyUser, Date applyDate, String auditUser, Date auditDate, String auditMsg,
			Date toolExpiration, String processTracking, String note1, String note2, String note3, String note4,
			String note5, String lineLevel, String rowLevel, String maintenceType, String skylightType, String station,
			Long formOrg, String regStation, Long patrol, String residentLiaison, String residentStation,
			String residentOnline, String interphone, String targetingEmployeeCode,String dept,String auditStatus) {
		this.id = id;
		this.workCode = workCode;
		this.workName = workName;
		this.constOrg = constOrg;
		this.dispatchCode = dispatchCode;
		this.registerType = registerType;
		this.workStart = workStart;
		this.workEnd = workEnd;
		this.skylightStart = skylightStart;
		this.skylightEnd = skylightEnd;
		this.accessInCode = accessInCode;
		this.accessOutCode = accessOutCode;
		this.workTeam = workTeam;
		this.workPic = workPic;
		this.workTel = workTel;
		this.workCount = workCount;
		this.inPatrol = inPatrol;
		this.outPatrol = outPatrol;
		this.workAddress = workAddress;
		this.workMileage = workMileage;
		this.employeeCount = employeeCount;
		this.delStatus = delStatus;
		this.workStatus = workStatus;
		this.workStandard = workStandard;
		this.riskControl = riskControl;
		this.completionStatus = completionStatus;
		this.applyMsg = applyMsg;
		this.applyUser = applyUser;
		this.applyDate = applyDate;
		this.auditUser = auditUser;
		this.auditDate = auditDate;
		this.auditMsg = auditMsg;
		this.toolExpiration = toolExpiration;
		this.processTracking = processTracking;
		this.note1 = note1;
		this.note2 = note2;
		this.note3 = note3;
		this.note4 = note4;
		this.note5 = note5;
		this.lineLevel = lineLevel;
		this.rowLevel = rowLevel;
		this.maintenceType = maintenceType;
		this.skylightType = skylightType;
		this.station = station;
		this.formOrg = formOrg;
		this.regStation = regStation;
		this.patrol = patrol;
		this.residentLiaison = residentLiaison;
		this.residentStation = residentStation;
		this.residentOnline = residentOnline;
		this.interphone = interphone;
		this.targetingEmployeeCode = targetingEmployeeCode;
		this.dept=dept;
		this.auditStatus=auditStatus;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWorkCode() {
		return this.workCode;
	}

	public void setWorkCode(String workCode) {
		this.workCode = workCode;
	}

	public String getWorkName() {
		return this.workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public Long getConstOrg() {
		return this.constOrg;
	}

	public void setConstOrg(Long constOrg) {
		this.constOrg = constOrg;
	}

	public String getDispatchCode() {
		return this.dispatchCode;
	}

	public void setDispatchCode(String dispatchCode) {
		this.dispatchCode = dispatchCode;
	}

	public String getRegisterType() {
		return this.registerType;
	}

	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}

	public Date getWorkStart() {
		return this.workStart;
	}

	public void setWorkStart(Date workStart) {
		this.workStart = workStart;
	}

	public Date getWorkEnd() {
		return this.workEnd;
	}

	public void setWorkEnd(Date workEnd) {
		this.workEnd = workEnd;
	}

	public Date getSkylightStart() {
		return this.skylightStart;
	}

	public void setSkylightStart(Date skylightStart) {
		this.skylightStart = skylightStart;
	}

	public Date getSkylightEnd() {
		return this.skylightEnd;
	}

	public void setSkylightEnd(Date skylightEnd) {
		this.skylightEnd = skylightEnd;
	}

	public String getAccessInCode() {
		return this.accessInCode;
	}

	public void setAccessInCode(String accessInCode) {
		this.accessInCode = accessInCode;
	}

	public String getAccessOutCode() {
		return this.accessOutCode;
	}

	public void setAccessOutCode(String accessOutCode) {
		this.accessOutCode = accessOutCode;
	}

	public Long getWorkTeam() {
		return this.workTeam;
	}

	public void setWorkTeam(Long workTeam) {
		this.workTeam = workTeam;
	}

	public String getWorkPic() {
		return this.workPic;
	}

	public void setWorkPic(String workPic) {
		this.workPic = workPic;
	}

	public String getWorkTel() {
		return this.workTel;
	}

	public void setWorkTel(String workTel) {
		this.workTel = workTel;
	}

	public String getWorkCount() {
		return this.workCount;
	}

	public void setWorkCount(String workCount) {
		this.workCount = workCount;
	}

	public String getInPatrol() {
		return this.inPatrol;
	}

	public void setInPatrol(String inPatrol) {
		this.inPatrol = inPatrol;
	}

	public String getOutPatrol() {
		return this.outPatrol;
	}

	public void setOutPatrol(String outPatrol) {
		this.outPatrol = outPatrol;
	}

	public String getWorkAddress() {
		return this.workAddress;
	}

	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}

	public String getWorkMileage() {
		return this.workMileage;
	}

	public void setWorkMileage(String workMileage) {
		this.workMileage = workMileage;
	}

	public Long getEmployeeCount() {
		return this.employeeCount;
	}

	public void setEmployeeCount(Long employeeCount) {
		this.employeeCount = employeeCount;
	}

	public String getDelStatus() {
		return this.delStatus;
	}

	public void setDelStatus(String delStatus) {
		this.delStatus = delStatus;
	}

	public String getWorkStatus() {
		return this.workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	public String getWorkStandard() {
		return this.workStandard;
	}

	public void setWorkStandard(String workStandard) {
		this.workStandard = workStandard;
	}

	public String getRiskControl() {
		return this.riskControl;
	}

	public void setRiskControl(String riskControl) {
		this.riskControl = riskControl;
	}

	public String getCompletionStatus() {
		return this.completionStatus;
	}

	public void setCompletionStatus(String completionStatus) {
		this.completionStatus = completionStatus;
	}

	public String getApplyMsg() {
		return this.applyMsg;
	}

	public void setApplyMsg(String applyMsg) {
		this.applyMsg = applyMsg;
	}

	public String getApplyUser() {
		return this.applyUser;
	}

	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}

	public Date getApplyDate() {
		return this.applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getAuditUser() {
		return this.auditUser;
	}

	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}

	public Date getAuditDate() {
		return this.auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public String getAuditMsg() {
		return this.auditMsg;
	}

	public void setAuditMsg(String auditMsg) {
		this.auditMsg = auditMsg;
	}

	public Date getToolExpiration() {
		return this.toolExpiration;
	}

	public void setToolExpiration(Date toolExpiration) {
		this.toolExpiration = toolExpiration;
	}

	public String getProcessTracking() {
		return this.processTracking;
	}

	public void setProcessTracking(String processTracking) {
		this.processTracking = processTracking;
	}

	public String getNote1() {
		return this.note1;
	}

	public void setNote1(String note1) {
		this.note1 = note1;
	}

	public String getNote2() {
		return this.note2;
	}

	public void setNote2(String note2) {
		this.note2 = note2;
	}

	public String getNote3() {
		return this.note3;
	}

	public void setNote3(String note3) {
		this.note3 = note3;
	}

	public String getNote4() {
		return this.note4;
	}

	public void setNote4(String note4) {
		this.note4 = note4;
	}

	public String getNote5() {
		return this.note5;
	}

	public void setNote5(String note5) {
		this.note5 = note5;
	}

	public String getLineLevel() {
		return this.lineLevel;
	}

	public void setLineLevel(String lineLevel) {
		this.lineLevel = lineLevel;
	}

	public String getRowLevel() {
		return this.rowLevel;
	}

	public void setRowLevel(String rowLevel) {
		this.rowLevel = rowLevel;
	}

	public String getMaintenceType() {
		return this.maintenceType;
	}

	public void setMaintenceType(String maintenceType) {
		this.maintenceType = maintenceType;
	}

	public String getSkylightType() {
		return this.skylightType;
	}

	public void setSkylightType(String skylightType) {
		this.skylightType = skylightType;
	}

	public String getStation() {
		return this.station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public Long getFormOrg() {
		return this.formOrg;
	}

	public void setFormOrg(Long formOrg) {
		this.formOrg = formOrg;
	}

	public String getRegStation() {
		return this.regStation;
	}

	public void setRegStation(String regStation) {
		this.regStation = regStation;
	}

	public Long getPatrol() {
		return this.patrol;
	}

	public void setPatrol(Long patrol) {
		this.patrol = patrol;
	}

	public String getResidentLiaison() {
		return this.residentLiaison;
	}

	public void setResidentLiaison(String residentLiaison) {
		this.residentLiaison = residentLiaison;
	}

	public String getResidentStation() {
		return this.residentStation;
	}

	public void setResidentStation(String residentStation) {
		this.residentStation = residentStation;
	}

	public String getResidentOnline() {
		return this.residentOnline;
	}

	public void setResidentOnline(String residentOnline) {
		this.residentOnline = residentOnline;
	}

	public String getInterphone() {
		return this.interphone;
	}

	public void setInterphone(String interphone) {
		this.interphone = interphone;
	}

	public String getTargetingEmployeeCode() {
		return this.targetingEmployeeCode;
	}

	public void setTargetingEmployeeCode(String targetingEmployeeCode) {
		this.targetingEmployeeCode = targetingEmployeeCode;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getPatrolName() {
		return patrolName;
	}

	public void setPatrolName(String patrolName) {
		this.patrolName = patrolName;
	}

	public String getBrhName() {
		return brhName;
	}

	public void setBrhName(String brhName) {
		this.brhName = brhName;
	}

	public String getSkylightStartStr() {
		return skylightStartStr;
	}

	public void setSkylightStartStr(String skylightStartStr) {
		this.skylightStartStr = skylightStartStr;
	}

	public String getSkylightEndStr() {
		return skylightEndStr;
	}

	public void setSkylightEndStr(String skylightEndStr) {
		this.skylightEndStr = skylightEndStr;
	}

	public String getAccessInName() {
		return accessInName;
	}

	public void setAccessInName(String accessInName) {
		this.accessInName = accessInName;
	}

	public String getAccessOutName() {
		return accessOutName;
	}

	public void setAccessOutName(String accessOutName) {
		this.accessOutName = accessOutName;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getWhseCode() {
		return whseCode;
	}

	public void setWhseCode(String whseCode) {
		this.whseCode = whseCode;
	}

	public String getWhseName() {
		return whseName;
	}

	public void setWhseName(String whseName) {
		this.whseName = whseName;
	}

}