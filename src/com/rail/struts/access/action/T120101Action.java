package com.rail.struts.access.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.components.Else;
import org.java_websocket.WebSocketImpl;

import com.alibaba.fastjson.JSONObject;
import com.huateng.common.Constants;
import com.huateng.common.ErrorCode;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.ContextUtil;
import com.rail.bo.access.T120101BO;
import com.rail.po.access.RailAccessEquipInfo;
import com.rail.po.access.RailAccessInfo;
import com.rail.po.access.RailAccessMaintain;
import com.rail.po.access.RailAccessOptlog;
import com.rail.po.warehouse.RailWhseInfo;
import com.rail.zWebSocket.EquipConInfo;
import com.rail.zWebSocket.PubUtil;
import com.sun.xml.internal.bind.v2.TODO;

public class T120101Action  extends BaseAction {
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
	//门禁信息维护
	private T120101BO t120101BO = (T120101BO) ContextUtil.getBean("T120101BO");
	public T120101BO getT120101BO() {
		return t120101BO;
	}
	public void setT120101BO(T120101BO t120101bo) {
		t120101BO = t120101bo;
	}
	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
		System.out.println(getMethod()+"  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		try {
            if ("open".equals(getMethod())) {
                rspCode = openAccess();
            }else if("close".equals(getMethod())){
            	rspCode = closeAccess();
            }else if("edit".equals(getMethod())){
            	rspCode = editAccess();
            }else if("open2".equals(getMethod())){
            	 rspCode = openAccess2();
            }else if("close2".equals(getMethod())){
            	rspCode = closeAccess2();
            }
            else if("openAccessAlarm".equals(getMethod())){
            	rspCode = openAccessAlarm();
            }
            else if("closeAccessAlarm".equals(getMethod())){
            	rspCode = closeAccessAlarm();
            }else if("getVido".equals(getMethod())){
				rspCode = getVido();
			}
        } catch (Exception e) {
        	//e.printStackTrace();
            log("操作员：" + operator.getOprId() + "，对门禁进行：" + getMethod() + "，失败，失败原因为：" + e);
        }
        return rspCode;
	}
	public String getVido() throws IOException{
		List<RailAccessEquipInfo> railAccessEquipInfos = t120101BO.getEqInfoByCodeType(getAccessCode(),3);
//		System.out.println(railAccessEquipInfo.getEquipIp()+"  >>>>>>>>>>>EquipIp>>>>>>>>>>>");
		
		Map<String, Object> midMap = new HashMap<String, Object>();
        midMap.put("success", true);
        midMap.put("msg", railAccessEquipInfos);
        String str = JSONObject.toJSONString(midMap);
        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
        printWriter.write(str);
        printWriter.flush();
        printWriter.close();
        
		log("更新门禁信息成功。操作员编号：" + operator.getOprId());
		return Constants.SUCCESS_CODE;
	}
	/**
	 * 更新门禁信息
	 * @Description: TODO
	 * @param @return
	 * @param @throws Exception   
	 * @return String  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public String editAccess()throws Exception{
		System.out.println("------------------更新门禁信息---------------");
		RailAccessInfo railAccessInfo = t120101BO.getByCode(getAccessCode());
		//判断机构是否存在getWhseCode()
        if (railAccessInfo==null) {
            return ErrorCode.T100100_01;
        }
        railAccessInfo.setAccessCode(getAccessCode());
        railAccessInfo.setAccessName(getAccessName());
        railAccessInfo.setAccessType(getAccessType());
        railAccessInfo.setAccessRoute(getAccessRoute());
        railAccessInfo.setAccessAddress(getAccessAddress());
        railAccessInfo.setAccessDept(getAccessDept());
        railAccessInfo.setAccessPic(getAccessPic());
        railAccessInfo.setAccessTel(getAccessTel());
        railAccessInfo.setPoliceOffice(getPoliceOffice());
        railAccessInfo.setExamPeriod(getExamPeriod());
      //  railAccessInfo.setLastExam(new Date());
        railAccessInfo.setAccessStatus(getAccessStatus());
        railAccessInfo.setOpenStatus(getOpenStatus());
        railAccessInfo.setWarnSystem(getWarnSystem());
        railAccessInfo.setWarnWeixin(getWarnWeixin());
        railAccessInfo.setMileage(getMileage());
        railAccessInfo.setMileagePrevious(getMileagePrevious());
        railAccessInfo.setMileageNext(getMileageNext());
        //railAccessInfo.setInstallDate(new Date());
        railAccessInfo.setLongitude(getLongitude());
        railAccessInfo.setLatitude(getLatitude());
        railAccessInfo.setWhseCode(getWhseCode());
        railAccessInfo.setAddUser(operator.getOprId());
        railAccessInfo.setAddDate(new Date());
  
		String rcode = t120101BO.updateAccess(railAccessInfo);
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
        
		log("更新门禁信息成功。操作员编号：" + operator.getOprId());
		return Constants.SUCCESS_CODE;
	}
	
	
	/**
	 * 启用门禁
	 * @Description: TODO
	 * @param @return
	 * @param @throws Exception   
	 * @return String  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	private String openAccess2() throws Exception {
		System.out.println("------------------启用门禁---------------");
		RailAccessInfo railAccessInfo = t120101BO.getByCode(getAccessCode());
		railAccessInfo.setAccessStatus("0");
		String rcode = t120101BO.updateAccess(railAccessInfo);
        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
        printWriter.write(rcode);
        printWriter.flush();
        printWriter.close();
        return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 停用门禁
	 * @Description: TODO
	 * @param @return
	 * @param @throws Exception   
	 * @return String  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	private String closeAccess2() throws Exception {
		System.out.println("------------------停用门禁---------------");
		RailAccessInfo railAccessInfo = t120101BO.getByCode(getAccessCode());
		railAccessInfo.setAccessStatus("1");
		String rcode = t120101BO.updateAccess(railAccessInfo);
        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
        printWriter.write(rcode);
        printWriter.flush();
        printWriter.close();
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 开启门禁
	 * @Description: TODO
	 * @param @return
	 * @param @throws Exception   
	 * @return String  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	private String openAccess() throws Exception {
		System.out.println("------------------开启门禁---------------");
		RailAccessInfo railAccessInfo = t120101BO.getByCode(getAccessCode());
		railAccessInfo.setOpenStatus("0");
		String rcode = t120101BO.updateAccess(railAccessInfo);

		String retMsg="";
		//首先先判断当前设备是否连接的上
		if (PubUtil.wsChannelIsAvailable(railAccessInfo.getNote1())) {
			String accessOrder=PubUtil.wsGetAccessOrder(railAccessInfo.getNote1(), 0);
			int wirteNum= PubUtil.wsWriteDataFromChannel(railAccessInfo.getNote1(), accessOrder);
			if (wirteNum>0) {
				retMsg="发送指令成功！";
				//系统发送的开门指令
				PubUtil.ACCESS_OPEN.put(railAccessInfo.getNote1(), new Date().getTime());
				//TODO 增加关门记录
				RailAccessOptlog railAccessOptlog=new RailAccessOptlog();
				railAccessOptlog.setAccessCode(railAccessInfo.getAccessCode());
				railAccessOptlog.setEquipCode(railAccessInfo.getNote1());
				railAccessOptlog.setEmployeeCode(operator.getOprId());
//				railAccessOptlog.setInfoSign("1");
				railAccessOptlog.setOpenSign("1");
				railAccessOptlog.setAddDate(new Date());
				railAccessOptlog.setAddUser(railAccessInfo.getAddUser());
				t120101BO.saveRailAccessOptlog(railAccessOptlog);
			}
		}else {
			retMsg="无法连接到门禁终端，请重试！";
		}
		
        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
        printWriter.write(retMsg);
        printWriter.flush();
        printWriter.close();
        
        return Constants.SUCCESS_CODE;
	}
	
	
	/**
	 * 开警报
	 * @Description: TODO
	 * @param @return
	 * @param @throws Exception   
	 * @return String  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	private String openAccessAlarm() throws Exception {
		System.out.println("------------------开启警报---------------");
		RailAccessInfo railAccessInfo = t120101BO.getByCode(getAccessCode());
//		railAccessInfo.setOpenStatus("0");
//		String rcode = t120101BO.updateAccess(railAccessInfo);

		String retMsg="";
		//首先先判断当前设备是否连接的上
		if (PubUtil.wsChannelIsAvailable(railAccessInfo.getNote1())) {
			String accessOrder=PubUtil.wsGetElectricOrder(railAccessInfo.getNote1(), 0);
			int wirteNum= PubUtil.wsWriteDataFromChannel(railAccessInfo.getNote1(), accessOrder);
			if (wirteNum>0) {
				retMsg="发送指令成功！";
			}
		}else {
			retMsg="无法连接到门禁终端，请重试！";
		}
		
        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
        printWriter.write(retMsg);
        printWriter.flush();
        printWriter.close();
        
        return Constants.SUCCESS_CODE;
	}
	/**
	 * 获得单个门禁信息
	 * @Description: TODO
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public String getData() {
		System.out.println("------------------获得单个门禁信息---------------");
        try {
        	RailAccessInfo railAccessInfo = t120101BO.getByCode(getAccessCode());
            Map<String, Object> midMap = new HashMap<String, Object>();
            midMap.put("success", true);
            midMap.put("msg", railAccessInfo);
            String str = JSONObject.toJSONString(midMap);//.fromObject(midMap).toString();
            PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
            printWriter.write(str);
            printWriter.flush();
            printWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }
	public static Map<String, Object> nullToEmpty(Map<String, Object> map) {
		Set<String> set = map.keySet();
		if(set != null && !set.isEmpty()) {
			for(String key : set) {
				if(map.get(key) == null) { 
					map.put(key, ""); 
				}
			}
		}
		return map;
	}
	/**
	 * 关闭门禁
	 * @Description: TODO
	 * @param @return
	 * @param @throws Exception   
	 * @return String  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	private String closeAccess() throws Exception {
		System.out.println("------------------关闭门禁---------------");
		RailAccessInfo railAccessInfo = t120101BO.getByCode(getAccessCode());
		railAccessInfo.setOpenStatus("1");
		String rcode = t120101BO.updateAccess(railAccessInfo);
		
		String retMsg="";
		//首先先判断当前设备是否连接的上
		if (PubUtil.wsChannelIsAvailable(railAccessInfo.getNote1())) {
			String accessOrder=PubUtil.wsGetAccessOrder(railAccessInfo.getNote1(), 1);
			int wirteNum= PubUtil.wsWriteDataFromChannel(railAccessInfo.getNote1(), accessOrder);
			if (wirteNum>0) {
				retMsg="发送指令成功！";
				//系统发送的开门指令
//				PubUtil.ACCESS_OPEN.put(railAccessInfo.getNote1(), new Date().getTime());
				//TODO  增加关门记录
				
				RailAccessOptlog railAccessOptlog=new RailAccessOptlog();
				railAccessOptlog.setAccessCode(railAccessInfo.getAccessCode());
				railAccessOptlog.setEmployeeCode(railAccessInfo.getAddUser());
				railAccessOptlog.setInfoSign("2");
				railAccessOptlog.setOpenSign("1");
				railAccessOptlog.setAddDate(new Date());
				railAccessOptlog.setAddUser(railAccessInfo.getAddUser());
				t120101BO.saveRailAccessOptlog(railAccessOptlog);
			}
		}else {
			retMsg="无法连接到门禁终端，请重试！";
		}
		
        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
        printWriter.write(retMsg);
        printWriter.flush();
        printWriter.close();
		return Constants.SUCCESS_CODE;
	}
	/**
	 * 关闭警报
	 * @Description: TODO
	 * @param @return
	 * @param @throws Exception   
	 * @return String  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	private String closeAccessAlarm() throws Exception {
		System.out.println("------------------关闭门禁---------------");
		RailAccessInfo railAccessInfo = t120101BO.getByCode(getAccessCode());
//		railAccessInfo.setOpenStatus("1");
//		String rcode = t120101BO.updateAccess(railAccessInfo);
		
		String retMsg="";
		//首先先判断当前设备是否连接的上
		if (PubUtil.wsChannelIsAvailable(railAccessInfo.getNote1())) {
			String accessOrder=PubUtil.wsGetElectricOrder(railAccessInfo.getNote1(), 1);
			int wirteNum= PubUtil.wsWriteDataFromChannel(railAccessInfo.getNote1(), accessOrder);
			if (wirteNum>0) {
				retMsg="发送指令成功！";				
			}
		}else {
			retMsg="无法连接到门禁终端，请重试！";
		}
		
        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
        printWriter.write(retMsg);
        printWriter.flush();
        printWriter.close();
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
	
}
