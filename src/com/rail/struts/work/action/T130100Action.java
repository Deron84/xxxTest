package com.rail.struts.work.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSONObject;
import com.huateng.common.Constants;
import com.huateng.common.ErrorCode;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.ContextUtil;
import com.rail.bo.enums.ApiSendMsgFlagEnums;
import com.rail.bo.tools.TSendMsgSocketBO;
import com.rail.bo.work.T130100BO;
import com.rail.po.work.RailWorkInfo;

public class T130100Action extends BaseAction {
	private static final long serialVersionUID = 1L;
	private String workCode;//工单编号
	private String workName;//作业名称
	private long constOrg;//施工单位
	private String dispatchCode;//调度好
	private String registerType;//作业类型
	private Date workStart;
	private Date workEnd;
	private Date skylightStart;
	private Date skylightEnd;
	private String accessInCode;
	private String accessOutCode;
	private long workTeam;
	private String workPic;
	private String workTel;
	private String workCount;
	private String inPatrol;
	private String outPatrol;
	private String workAddress;
	private String workMileage;
	private long employeeCount;//作业人数
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
	private long formOrg;
	private String regStation;
	private long patrol;
	private String residentLiaison;
	private String residentStation;
	private String residentOnline;
	private String interphone;
	private String targetingEmployeeCode;
	private String dept;
	private String auditStatus;
	private String whseCode;
	//工单信息
	private T130100BO t130100BO = (T130100BO) ContextUtil.getBean("T130100BO");
	@Override
	protected String subExecute() throws Exception {
		try {
            if ("add".equals(getMethod())) {
                rspCode = add();
            }else if("edit".equals(getMethod())){
            	rspCode = editWork();
            }
        } catch (Exception e) {
        	//e.printStackTrace();
            log("操作员：" + operator.getOprId() + "，对工单进行：" + getMethod() + "，失败，失败原因为：" + e);
        }
        return rspCode;
	}
	private String editWork() throws Exception{
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("WORK_CODE", getWorkCode());
		List<RailWorkInfo> rwInfos = t130100BO.getByParam(paramMap);
		RailWorkInfo railWorkInfo = new RailWorkInfo();
		if(rwInfos==null||rwInfos.isEmpty()){
			String msg = "该工单不存在，请刷新后重新修改";
			boolean success=false;
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", success);
	        midMap.put("msg", msg);
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
		}else{
			railWorkInfo = rwInfos.get(0);
			railWorkInfo.setWorkCode(getWorkCode());
			railWorkInfo.setWorkName(getWorkName());
			railWorkInfo.setConstOrg(getConstOrg());
			railWorkInfo.setDispatchCode(getDispatchCode());
			railWorkInfo.setRegisterType(getRegisterType());
			railWorkInfo.setWorkStart(getWorkStart());
			railWorkInfo.setWorkEnd(getWorkEnd());
			railWorkInfo.setSkylightStart(getSkylightStart());
			railWorkInfo.setSkylightEnd(getSkylightEnd());
			railWorkInfo.setAccessInCode(getAccessInCode());
			railWorkInfo.setAccessOutCode(getAccessOutCode());
			railWorkInfo.setWorkTeam(getWorkTeam());
			railWorkInfo.setWorkPic(getWorkPic());
			railWorkInfo.setWorkTel(getWorkTel());
			railWorkInfo.setWorkCount(getWorkCount());
			railWorkInfo.setInPatrol(getInPatrol());
			railWorkInfo.setOutPatrol(getOutPatrol());
			railWorkInfo.setWorkAddress(getWorkAddress());
			railWorkInfo.setWorkMileage(getWorkMileage());
			railWorkInfo.setEmployeeCount(getEmployeeCount());
			railWorkInfo.setDelStatus("0");
			railWorkInfo.setWorkStatus("0");
			railWorkInfo.setWorkStandard(getWorkStandard());
			railWorkInfo.setRiskControl(getRiskControl());
			railWorkInfo.setApplyMsg(getApplyMsg());
			railWorkInfo.setWhseCode(getWhseCode());
			railWorkInfo.setWhseCode(getWhseCode());
			railWorkInfo.setLineLevel(getLineLevel());
			railWorkInfo.setRowLevel(getRowLevel());
			railWorkInfo.setMaintenceType(getMaintenceType());
			railWorkInfo.setSkylightType(getSkylightType());
			railWorkInfo.setStation(getStation());
			railWorkInfo.setFormOrg(getFormOrg());
			railWorkInfo.setRegStation(getRegStation());
			railWorkInfo.setPatrol(Long.valueOf(getPatrol()));
			railWorkInfo.setResidentLiaison(getResidentLiaison());
			railWorkInfo.setResidentStation(getResidentStation());
			railWorkInfo.setResidentOnline(getResidentOnline());
			railWorkInfo.setInterphone(getInterphone());
			railWorkInfo.setTargetingEmployeeCode(getTargetingEmployeeCode());
			railWorkInfo.setDept(getDept());
			railWorkInfo.setAuditStatus("0");
			String rcode=t130100BO.updateRailWorkInfo(railWorkInfo);
			String msg = "操作失败";
			boolean success = false;
			if(rcode.equals(Constants.SUCCESS_CODE)){
				success=true;
				msg = "操作成功";
			}else{
				msg = "操作失败";
				success = false;
			}
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", success);
	        midMap.put("msg", msg);
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			log("修改工单信息成功。操作员编号：" + operator.getOprId());
		}
        return Constants.SUCCESS_CODE;
		
	}
	/**
	 * 新增工单操作
	 * @Description: TODO
	 * @param @return
	 * @param @throws Exception   
	 * @return String  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月16日
	 */
	private String add() throws Exception {
		System.out.println("------------------新增工单---------------");
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("WORK_CODE", getWorkCode());
		List<RailWorkInfo> rwInfos = t130100BO.getByParam(paramMap);
		//判断机构是否存在getWhseCode()
        if (rwInfos!=null&&!rwInfos.isEmpty()) {
        	Map<String, Object> midMap = new HashMap<String, Object>();
            midMap.put("success", false);
            midMap.put("msg", "该工单编码已存在！");
            String str = JSONObject.toJSONString(midMap);
            PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
            printWriter.write(str);
            printWriter.flush();
            printWriter.close();
            return ErrorCode.T100100_01;
        }
		RailWorkInfo railWorkInfo = new RailWorkInfo();
		railWorkInfo.setWorkCode(getWorkCode());
		railWorkInfo.setWorkName(getWorkName());
		railWorkInfo.setConstOrg(getConstOrg());
		railWorkInfo.setDispatchCode(getDispatchCode());
		railWorkInfo.setRegisterType(getRegisterType());
		railWorkInfo.setWorkStart(getWorkStart());
		railWorkInfo.setWorkEnd(getWorkEnd());
		railWorkInfo.setSkylightStart(getSkylightStart());
		railWorkInfo.setSkylightEnd(getSkylightEnd());
		railWorkInfo.setAccessInCode(getAccessInCode());
		railWorkInfo.setAccessOutCode(getAccessOutCode());
		railWorkInfo.setWorkTeam(getWorkTeam());
		railWorkInfo.setWorkPic(getWorkPic());
		railWorkInfo.setWorkTel(getWorkTel());
		railWorkInfo.setWorkCount(getWorkCount());
		railWorkInfo.setInPatrol(getInPatrol());
		railWorkInfo.setOutPatrol(getOutPatrol());
		railWorkInfo.setWorkAddress(getWorkAddress());
		railWorkInfo.setWorkMileage(getWorkMileage());
		railWorkInfo.setEmployeeCount(getEmployeeCount());
		railWorkInfo.setDelStatus("0");
		railWorkInfo.setWorkStatus("0");
		railWorkInfo.setWorkStandard(getWorkStandard());
		railWorkInfo.setRiskControl(getRiskControl());
		railWorkInfo.setApplyMsg(getApplyMsg());
		railWorkInfo.setApplyUser(operator.getOprId());
		railWorkInfo.setApplyDate(new Date());
		railWorkInfo.setLineLevel(getLineLevel());
		railWorkInfo.setRowLevel(getRowLevel());
		railWorkInfo.setMaintenceType(getMaintenceType());
		railWorkInfo.setSkylightType(getSkylightType());
		railWorkInfo.setStation(getStation());
		railWorkInfo.setFormOrg(getFormOrg());
		railWorkInfo.setRegStation(getRegStation());
		railWorkInfo.setPatrol(getPatrol());
		railWorkInfo.setResidentLiaison(getResidentLiaison());
		railWorkInfo.setResidentStation(getResidentStation());
		railWorkInfo.setResidentOnline(getResidentOnline());
		railWorkInfo.setInterphone(getInterphone());
		railWorkInfo.setTargetingEmployeeCode(getTargetingEmployeeCode());
		railWorkInfo.setDept(getDept());
		railWorkInfo.setAuditStatus("0");
		railWorkInfo.setWhseCode(getWhseCode());
		String rcode=t130100BO.add(railWorkInfo);
		String msg = "操作失败";
		boolean success = false;
		if(rcode.equals(Constants.SUCCESS_CODE)){
//			TSendMsgSocketBO tSendMsgSocketBO = (TSendMsgSocketBO) ContextUtil.getBean("TSendMsgSocketBO");
//			int flag  = ApiSendMsgFlagEnums.TOOL.getCode();
//			//发送信息 更新app工单列表
//			tSendMsgSocketBO.sendSocketMsg(railWorkInfo.getDept(), railWorkInfo.getId(), ApiSendMsgFlagEnums.WORK_ORDER.getCode());
			success=true;
			msg = "操作成功";
		}else{
			msg = "操作失败";
			success = false;
		}
		Map<String, Object> midMap = new HashMap<String, Object>();
        midMap.put("success", success);
        midMap.put("msg", msg);
        String str = JSONObject.toJSONString(midMap);
        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
        printWriter.write(str);
        printWriter.flush();
        printWriter.close();
		log("添加工单信息成功。操作员编号：" + operator.getOprId());

        return Constants.SUCCESS_CODE;
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
	public void setWorkName(String workName) {
		this.workName = workName;
	}
	public long getConstOrg() {
		return constOrg;
	}
	public void setConstOrg(long constOrg) {
		this.constOrg = constOrg;
	}
	public String getDispatchCode() {
		return dispatchCode;
	}
	public void setDispatchCode(String dispatchCode) {
		this.dispatchCode = dispatchCode;
	}
	public String getRegisterType() {
		return registerType;
	}
	public void setRegisterType(String registerType) {
		this.registerType = registerType;
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
	public String getAccessInCode() {
		return accessInCode;
	}
	public void setAccessInCode(String accessInCode) {
		this.accessInCode = accessInCode;
	}
	public String getAccessOutCode() {
		return accessOutCode;
	}
	public void setAccessOutCode(String accessOutCode) {
		this.accessOutCode = accessOutCode;
	}
	public long getWorkTeam() {
		return workTeam;
	}
	public void setWorkTeam(long workTeam) {
		this.workTeam = workTeam;
	}
	public String getWorkPic() {
		return workPic;
	}
	public void setWorkPic(String workPic) {
		this.workPic = workPic;
	}
	public String getWorkTel() {
		return workTel;
	}
	public void setWorkTel(String workTel) {
		this.workTel = workTel;
	}
	public String getWorkCount() {
		return workCount;
	}
	public void setWorkCount(String workCount) {
		this.workCount = workCount;
	}
	public String getInPatrol() {
		return inPatrol;
	}
	public void setInPatrol(String inPatrol) {
		this.inPatrol = inPatrol;
	}
	public String getOutPatrol() {
		return outPatrol;
	}
	public void setOutPatrol(String outPatrol) {
		this.outPatrol = outPatrol;
	}
	public String getWorkAddress() {
		return workAddress;
	}
	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}
	public String getWorkMileage() {
		return workMileage;
	}
	public void setWorkMileage(String workMileage) {
		this.workMileage = workMileage;
	}
	public long getEmployeeCount() {
		return employeeCount;
	}
	public void setEmployeeCount(long employeeCount) {
		this.employeeCount = employeeCount;
	}
	public String getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(String delStatus) {
		this.delStatus = delStatus;
	}
	public String getWorkStatus() {
		return workStatus;
	}
	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}
	public String getWorkStandard() {
		return workStandard;
	}
	public void setWorkStandard(String workStandard) {
		this.workStandard = workStandard;
	}
	public String getRiskControl() {
		return riskControl;
	}
	public void setRiskControl(String riskControl) {
		this.riskControl = riskControl;
	}
	public String getCompletionStatus() {
		return completionStatus;
	}
	public void setCompletionStatus(String completionStatus) {
		this.completionStatus = completionStatus;
	}
	public String getApplyMsg() {
		return applyMsg;
	}
	public void setApplyMsg(String applyMsg) {
		this.applyMsg = applyMsg;
	}
	public String getApplyUser() {
		return applyUser;
	}
	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public String getAuditUser() {
		return auditUser;
	}
	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}
	public Date getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
	public String getAuditMsg() {
		return auditMsg;
	}
	public void setAuditMsg(String auditMsg) {
		this.auditMsg = auditMsg;
	}
	public Date getToolExpiration() {
		return toolExpiration;
	}
	public void setToolExpiration(Date toolExpiration) {
		this.toolExpiration = toolExpiration;
	}
	public String getProcessTracking() {
		return processTracking;
	}
	public void setProcessTracking(String processTracking) {
		this.processTracking = processTracking;
	}
	public String getNote1() {
		return note1;
	}
	public void setNote1(String note1) {
		this.note1 = note1;
	}
	public String getNote2() {
		return note2;
	}
	public void setNote2(String note2) {
		this.note2 = note2;
	}
	public String getNote3() {
		return note3;
	}
	public void setNote3(String note3) {
		this.note3 = note3;
	}
	public String getNote4() {
		return note4;
	}
	public void setNote4(String note4) {
		this.note4 = note4;
	}
	public String getNote5() {
		return note5;
	}
	public void setNote5(String note5) {
		this.note5 = note5;
	}
	public String getLineLevel() {
		return lineLevel;
	}
	public void setLineLevel(String lineLevel) {
		this.lineLevel = lineLevel;
	}
	public String getRowLevel() {
		return rowLevel;
	}
	public void setRowLevel(String rowLevel) {
		this.rowLevel = rowLevel;
	}
	public String getMaintenceType() {
		return maintenceType;
	}
	public void setMaintenceType(String maintenceType) {
		this.maintenceType = maintenceType;
	}
	public String getSkylightType() {
		return skylightType;
	}
	public void setSkylightType(String skylightType) {
		this.skylightType = skylightType;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public long getFormOrg() {
		return formOrg;
	}
	public void setFormOrg(long formOrg) {
		this.formOrg = formOrg;
	}
	public String getRegStation() {
		return regStation;
	}
	public void setRegStation(String regStation) {
		this.regStation = regStation;
	}
	public long getPatrol() {
		return patrol;
	}
	public void setPatrol(long patrol) {
		this.patrol = patrol;
	}
	public String getResidentLiaison() {
		return residentLiaison;
	}
	public void setResidentLiaison(String residentLiaison) {
		this.residentLiaison = residentLiaison;
	}
	public String getResidentStation() {
		return residentStation;
	}
	public void setResidentStation(String residentStation) {
		this.residentStation = residentStation;
	}
	public String getResidentOnline() {
		return residentOnline;
	}
	public void setResidentOnline(String residentOnline) {
		this.residentOnline = residentOnline;
	}
	public String getInterphone() {
		return interphone;
	}
	public void setInterphone(String interphone) {
		this.interphone = interphone;
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
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	public String getWhseCode() {
		return whseCode;
	}
	public void setWhseCode(String whseCode) {
		this.whseCode = whseCode;
	}
	public T130100BO getT130100BO() {
		return t130100BO;
	}
	public void setT130100BO(T130100BO t130100bo) {
		t130100BO = t130100bo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}