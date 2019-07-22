package com.rail.po.vo;

import java.util.Date;
import java.util.List;

import com.rail.po.base.RailEmployee;

/**
 * RailWorkInfo entity. @author MyEclipse Persistence Tools
 */

public class RailWorkInfoVo implements java.io.Serializable {

	// Fields

	private Long id;
	private String workCode;
	private String workName;
	private Long constOrg;
	private String dispatchCode;
	private Date workStart;
	private Date workEnd;
	private Long workTeam;
	private Long employeeCount;
	private Long   formOrg;
	private String targetingEmployeeCode;
	private String dept;
	private String workStatus;
	private Date skylightStart;
	private Date skylightEnd;
	// Constructors

	private String targetingEmployeeName;
	private String workTeamName;
	private String costOrgName;
	private String formOrgName;
	private List<RailEmployeeVo2> employeeList;
	private List<RailToolInfoVo2> toolsList;
	private List<RailEmployeeVo2> listRailteams;
	private Object  TargetingEmployeeInfo;//盯控干部 详细信息
	
	private int toolsCount;
	private String employeeName;
	
	/** default constructor */
	public RailWorkInfoVo() {
	}

	public RailWorkInfoVo(Long id, String workCode, String workName, Long constOrg, String dispatchCode, Date workStart,
			Date workEnd, Long workTeam, Long employeeCount, Long formOrg, String dept,String targetingEmployeeCode,Long ConstOrg) {
		super();
		this.id = id;
		this.workCode = workCode;
		this.workName = workName;
		this.constOrg = constOrg;
		this.dispatchCode = dispatchCode;
		this.workStart = workStart;
		this.workEnd = workEnd;
		this.workTeam = workTeam;
		this.formOrg = formOrg;
		this.dept = dept;
		this.targetingEmployeeCode = targetingEmployeeCode;
		this.constOrg = constOrg;
	}
	public RailWorkInfoVo(Long id, String workCode, String workName, Long constOrg, String dispatchCode, Date workStart,
			Date workEnd, Long workTeam, Long employeeCount, Long formOrg, String dept,String targetingEmployeeCode,Long ConstOrg,String workStatus) {
		super();
		this.id = id;
		this.workCode = workCode;
		this.workName = workName;
		this.constOrg = constOrg;
		this.dispatchCode = dispatchCode;
		this.workStart = workStart;
		this.workEnd = workEnd;
		this.workTeam = workTeam;
		this.formOrg = formOrg;
		this.dept = dept;
		this.targetingEmployeeCode = targetingEmployeeCode;
		this.constOrg = constOrg;
		this.workStatus = workStatus;
	}

	public Long getId() {
		return id;
	}
	

	public Object getTargetingEmployeeInfo() {
		return TargetingEmployeeInfo;
	}

	public void setTargetingEmployeeInfo(Object targetingEmployeeInfo) {
		TargetingEmployeeInfo = targetingEmployeeInfo;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWorkCode() {
		return workCode;
	}

	public void setWorkCode(String workCode) {
		this.workCode = workCode;
	}

	public String getWorkName() {
		return workName;
	}

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public Long getConstOrg() {
		return constOrg;
	}
	

	public void setConstOrg(Long constOrg) {
		this.constOrg = constOrg;
	}

	public String getDispatchCode() {
		return dispatchCode;
	}

	public void setDispatchCode(String dispatchCode) {
		this.dispatchCode = dispatchCode;
	}

	public Date getWorkStart() {
		return workStart;
	}

	public void setWorkStart(Date workStart) {
		this.workStart = workStart;
	}

	public Date getWorkEnd() {
		return workEnd;
	}

	public void setWorkEnd(Date workEnd) {
		this.workEnd = workEnd;
	}

	public Long getWorkTeam() {
		return workTeam;
	}

	public void setWorkTeam(Long workTeam) {
		this.workTeam = workTeam;
	}

	public Long getEmployeeCount() {
		return employeeCount;
	}

	public void setEmployeeCount(Long employeeCount) {
		this.employeeCount = employeeCount;
	}

	public Long getFormOrg() {
		return formOrg;
	}

	public void setFormOrg(Long formOrg) {
		this.formOrg = formOrg;
	}

	public String getTargetingEmployeeCode() {
		return targetingEmployeeCode;
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


	public List<RailEmployeeVo2> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<RailEmployeeVo2> employeeList) {
		this.employeeList = employeeList;
	}

	public List<RailToolInfoVo2> getToolsList() {
		return toolsList;
	}

	public void setToolsList(List<RailToolInfoVo2> toolsList) {
		this.toolsList = toolsList;
	}

	public List<RailEmployeeVo2> getListRailteams() {
		return listRailteams;
	}

	public void setListRailteams(List<RailEmployeeVo2> listRailteams) {
		this.listRailteams = listRailteams;
	}

	public int getToolsCount() {
		return toolsCount;
	}

	public void setToolsCount(int toolsCount) {
		this.toolsCount = toolsCount;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Date getSkylightStart() {
		return skylightStart;
	}

	public void setSkylightStart(Date skylightStart) {
		this.skylightStart = skylightStart;
	}

	public Date getSkylightEnd() {
		return skylightEnd;
	}

	public void setSkylightEnd(Date skylightEnd) {
		this.skylightEnd = skylightEnd;
	}
	


}