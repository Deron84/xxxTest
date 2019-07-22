package com.rail.struts.access.action;

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
import com.rail.bo.access.T120100BO;
import com.rail.po.access.RailAccessInfo;
import com.rail.po.warehouse.RailWhseInfo;

public class T120100Action extends BaseAction{
	
	private static final long serialVersionUID = 1L;
	private String accessCode;
	private String accessName;
	private String accessType;
	private String accessRoute;
	private String accessAddress;
	private String accessDept;
	private String accessPic;
	private String accessTel;
	private String policeOffice;
	private long examPeriod;
	private String accessStatus;
	private String openStatus;
	private String delStatus;
	private String warnSystem;
	private String warnWeixin;
	private String mileage;
	private String mileagePrevious;
	private String mileageNext;
	private String longitude;
	private String latitude;
	private String whseCode;
	private String addUser;
	private String note1;
	private String note2;
	private Date lastExam;
	private Date installDate;
	public Date getInstallDate() {
		return installDate;
	}
	public void setInstallDate(Date installDate) {
		this.installDate = installDate;
	}
	public Date getLastExam() {
		return lastExam;
	}
	public void setLastExam(Date lastExam) {
		this.lastExam = lastExam;
	}
	//门禁信息维护
	private T120100BO t120100BO = (T120100BO) ContextUtil.getBean("T120100BO");
	public T120100BO getT120100BO() {
		return t120100BO;
	}
	public void setT120100BO(T120100BO t120100bo) {
		t120100BO = t120100bo;
	}
	

	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
		System.out.println(getMethod()+"  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		try {
            if ("add".equals(getMethod())) {
                rspCode = add();
            }
        } catch (Exception e) {
        	//e.printStackTrace();
            log("操作员：" + operator.getOprId() + "，对门禁进行：" + getMethod() + "，失败，失败原因为：" + e);
        }
        return rspCode;
	}
	/**
	 * 新增门禁
	 * @Description: TODO
	 * @param @return
	 * @param @throws Exception   
	 * @return String  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月16日
	 */
	private String add() throws Exception {
		System.out.println("------------------新增门禁---------------");
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("ACCESS_CODE", getAccessCode());
		List<RailAccessInfo> rwInfos = t120100BO.getByParam(paramMap);
		//判断机构是否存在getWhseCode()
        if (rwInfos!=null&&!rwInfos.isEmpty()) {
        	Map<String, Object> errorMap = new HashMap<String, Object>();
			errorMap.put("success", false);
			errorMap.put("msg", "该门禁编码已存在！");
            String str = JSONObject.toJSONString(errorMap);
            PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
            printWriter.write(str);
            printWriter.flush();
            printWriter.close();
            return null;
        }
        RailAccessInfo railAccessInfo = new RailAccessInfo();
        railAccessInfo.setAccessCode(getAccessCode());
        railAccessInfo.setAccessName(getAccessName());
        railAccessInfo.setAccessType(getAccessType());
        log("getAccessType()getAccessType()getAccessType()======" +getAccessType());
        railAccessInfo.setAccessRoute(getAccessRoute());
        railAccessInfo.setAccessAddress(getAccessAddress());
        railAccessInfo.setAccessDept(getAccessDept());
        railAccessInfo.setAccessPic(getAccessPic());
        railAccessInfo.setAccessTel(getAccessTel());
        railAccessInfo.setPoliceOffice(getPoliceOffice());
        railAccessInfo.setExamPeriod(getExamPeriod());
        railAccessInfo.setLastExam(getLastExam());
        railAccessInfo.setAccessStatus("0");
        railAccessInfo.setOpenStatus("0");
        railAccessInfo.setWarnSystem("0");
        railAccessInfo.setDelStatus("0");
        railAccessInfo.setWarnWeixin(getWarnWeixin());
        railAccessInfo.setMileage(getMileage());
        railAccessInfo.setMileagePrevious(getMileagePrevious());
        railAccessInfo.setMileageNext(getMileageNext());
        railAccessInfo.setInstallDate(getInstallDate());
        railAccessInfo.setLongitude(getLongitude());
        railAccessInfo.setLatitude(getLatitude());
        railAccessInfo.setWhseCode(getWhseCode());
        railAccessInfo.setAddUser(operator.getOprId());
        railAccessInfo.setAddDate(new Date());
		String rcode=t120100BO.add(railAccessInfo);
		String msg = "添加门禁成功";
		boolean success = true;
		if(!rcode.equals(Constants.SUCCESS_CODE)){
			msg = "添加门禁失败";
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
		log("添加门禁信息成功。操作员编号：" + operator.getOprId());

        return Constants.SUCCESS_CODE;
	}
	public String getAccessCode() {
		return accessCode;
	}
	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}
	public String getAccessName() {
		return accessName;
	}
	public void setAccessName(String accessName) {
		this.accessName = accessName;
	}
	public String getAccessType() {
		return accessType;
	}
	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}
	public String getAccessRoute() {
		return accessRoute;
	}
	public void setAccessRoute(String accessRoute) {
		this.accessRoute = accessRoute;
	}
	public String getAccessAddress() {
		return accessAddress;
	}
	public void setAccessAddress(String accessAddress) {
		this.accessAddress = accessAddress;
	}
	public String getAccessDept() {
		return accessDept;
	}
	public void setAccessDept(String accessDept) {
		this.accessDept = accessDept;
	}
	public String getAccessPic() {
		return accessPic;
	}
	public void setAccessPic(String accessPic) {
		this.accessPic = accessPic;
	}
	public String getAccessTel() {
		return accessTel;
	}
	public void setAccessTel(String accessTel) {
		this.accessTel = accessTel;
	}
	public String getPoliceOffice() {
		return policeOffice;
	}
	public void setPoliceOffice(String policeOffice) {
		this.policeOffice = policeOffice;
	}
	public long getExamPeriod() {
		return examPeriod;
	}
	public void setExamPeriod(long examPeriod) {
		this.examPeriod = examPeriod;
	}
	public String getAccessStatus() {
		return accessStatus;
	}
	public void setAccessStatus(String accessStatus) {
		this.accessStatus = accessStatus;
	}
	public String getOpenStatus() {
		return openStatus;
	}
	public void setOpenStatus(String openStatus) {
		this.openStatus = openStatus;
	}
	public String getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(String delStatus) {
		this.delStatus = delStatus;
	}
	public String getWarnSystem() {
		return warnSystem;
	}
	public void setWarnSystem(String warnSystem) {
		this.warnSystem = warnSystem;
	}
	public String getWarnWeixin() {
		return warnWeixin;
	}
	public void setWarnWeixin(String warnWeixin) {
		this.warnWeixin = warnWeixin;
	}
	public String getMileage() {
		return mileage;
	}
	public void setMileage(String mileage) {
		this.mileage = mileage;
	}
	public String getMileagePrevious() {
		return mileagePrevious;
	}
	public void setMileagePrevious(String mileagePrevious) {
		this.mileagePrevious = mileagePrevious;
	}
	public String getMileageNext() {
		return mileageNext;
	}
	public void setMileageNext(String mileageNext) {
		this.mileageNext = mileageNext;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getWhseCode() {
		return whseCode;
	}
	public void setWhseCode(String whseCode) {
		this.whseCode = whseCode;
	}
	public String getAddUser() {
		return addUser;
	}
	public void setAddUser(String addUser) {
		this.addUser = addUser;
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

	
}
