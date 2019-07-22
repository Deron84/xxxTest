package com.rail.struts.access.action;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSONObject;
import com.huateng.common.Constants;
import com.huateng.common.ErrorCode;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.ContextUtil;
import com.rail.bo.access.T120101BO;
import com.rail.bo.access.T120103BO;
import com.rail.po.access.RailAccessEquipInfo;
import com.rail.po.access.RailAccessInfo;

public class T120103Action  extends BaseAction {
	private static final long serialVersionUID = 1L;
	
	private String equipId;
	private String accessCode;
	private String equipCode;
	private String equipName;
	private String equipName11;
	private long equipType;
	private String equipIp;
	private long mfrsOrg;
	private String mfrsTel;
	private long examPeriod;
	private String equipStatus;
	private String addUser;
	private Date addDate;
	private String updUser;
	private Date updDate;
	private String note1;
	private String note2;
	private String note3;
	//门禁信息维护
	private T120103BO t120103BO = (T120103BO) ContextUtil.getBean("T120103BO");
	public T120103BO getT120103BO() {
		return t120103BO;
	}
	public void setT120103BO(T120103BO t120103bo) {
		t120103BO = t120103bo;
	}
	
	private T120101BO t120101BO = (T120101BO) ContextUtil.getBean("T120101BO");
	public T120101BO getT120101BO() {
		return t120101BO;
	}
	public void setT120101BO(T120101BO t120101Bo) {
		t120101BO = t120101Bo;
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
            }else if("edit2".equals(getMethod())){
            	rspCode = editAccess2();
            }else if("add".equals(getMethod())){
            	rspCode = add();
            }else if("delete".equals(getMethod())){
            	rspCode = delete();
            }
        } catch (Exception e) {
        	//e.printStackTrace();
            log("操作员：" + operator.getOprId() + "，对门禁进行：" + getMethod() + "，失败，失败原因为：" + e);
        }
        return rspCode;
	}
	
	/**
	 * 新增设备终端
	 * @Description: TODO
	 * @param @return
	 * @param @throws Exception   
	 * @return String  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月16日
	 */
	private String add() throws Exception {
		System.out.println("------------------新增终端设备---------------");
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("EQUIP_CODE", getEquipCode());
//		paramMap.put("ACCESS_CODE", getAccessCode());
		List<RailAccessEquipInfo> rwInfos = t120103BO.getByParam(paramMap);
		//判断机构是否存在getWhseCode()
        if (rwInfos!=null&&!rwInfos.isEmpty()) {
        	Map<String, Object> midMap = new HashMap<String, Object>();
            midMap.put("success", false);
            midMap.put("msg", "该门禁下设备编码已存在！");
            String str = JSONObject.toJSONString(midMap);
            PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
            printWriter.write(str);
            printWriter.flush();
            printWriter.close();
            return ErrorCode.T100100_01;
        }
        String typenum=getEquipType()+"";
        String rcode="";
        System.out.println("------"+typenum+"------");
        if(typenum.equals("2")||typenum.equals("3")){
        RailAccessEquipInfo railAccessEquipInfo = new RailAccessEquipInfo();
        railAccessEquipInfo.setAccessCode(getAccessCode());
        railAccessEquipInfo.setEquipCode(getEquipCode());
        railAccessEquipInfo.setEquipName(getEquipName());
        railAccessEquipInfo.setEquipIp(getEquipIp());
        railAccessEquipInfo.setNote1(getNote1());
        railAccessEquipInfo.setNote2(getNote2());
        railAccessEquipInfo.setEquipType(getEquipType());
        railAccessEquipInfo.setInstallDate(new Date());
        railAccessEquipInfo.setEquipStatus("0");
        railAccessEquipInfo.setAddUser(operator.getOprId());
        railAccessEquipInfo.setAddDate(new Date());
        
		rcode=t120103BO.add(railAccessEquipInfo);
        }else if(typenum.equals("1")){
        	String[] strArray=null;
        	if(getNote3()!=null&&getNote3()!=""){
      		strArray=(getNote3()).split(",");
      		for (int i = 0; i < strArray.length; i++) {
      			System.out.println("----------------"+strArray[i]);
      			RailAccessEquipInfo railAccessEquipInfo=new RailAccessEquipInfo();
      			railAccessEquipInfo.setAccessCode(getAccessCode());
      			railAccessEquipInfo.setEquipStatus("0");
      			railAccessEquipInfo.setEquipCode(getEquipCode());
      			railAccessEquipInfo.setAddDate(new Date());
      			railAccessEquipInfo.setInstallDate(new Date());
      			railAccessEquipInfo.setAddUser(operator.getOprId());
      			railAccessEquipInfo.setEquipType(1l);
      			//8604主机防区1烟感，防区2烟感，防区3红外，防区4红外，防区5防拆，防区6门磁
      			switch (strArray[i]) {
      			case " 1":
      				railAccessEquipInfo.setEquipName("烟感-外");
      				railAccessEquipInfo.setDefence(1l);
      				break;
      			case "1":
      				railAccessEquipInfo.setEquipName("烟感-外");
      				railAccessEquipInfo.setDefence(1l);
      				break;
      			case " 2":
      				railAccessEquipInfo.setEquipName("烟感-内");
      				railAccessEquipInfo.setDefence(2l);
      				break;
      			case "2":
      				railAccessEquipInfo.setEquipName("烟感-内");
      				railAccessEquipInfo.setDefence(2l);
      				break;
      			case " 3":
      				railAccessEquipInfo.setEquipName("红外-外");
      				railAccessEquipInfo.setDefence(3l);
      				break;
      			case "3":
      				railAccessEquipInfo.setEquipName("红外-外");
      				railAccessEquipInfo.setDefence(3l);
      				break;
      			case " 4":
      				railAccessEquipInfo.setEquipName("红外-内");
      				railAccessEquipInfo.setDefence(4l);
      				break;
      			case "4":
      				railAccessEquipInfo.setEquipName("红外-内");
      				railAccessEquipInfo.setDefence(4l);
      				break;
      			case " 5":
      				railAccessEquipInfo.setEquipName("防拆");
      				railAccessEquipInfo.setDefence(5l);
      				break;
      			case "5":
      				railAccessEquipInfo.setEquipName("防拆");
      				railAccessEquipInfo.setDefence(5l);
      				break;
      			case " 6":
      				railAccessEquipInfo.setEquipName("门磁");
      				railAccessEquipInfo.setDefence(6l);
      				break;
      			case "6":
      				railAccessEquipInfo.setEquipName("门磁");
      				railAccessEquipInfo.setDefence(6l);
      				break;
      			}
      			rcode=t120103BO.add(railAccessEquipInfo);
      		}
        	}
      		RailAccessInfo railAccessInfo = t120101BO.getByCode(getAccessCode());
  	        if (railAccessInfo==null) {
  	            return ErrorCode.T100100_01;
  	        }
  	        railAccessInfo.setNote1(getEquipCode());
  			rcode = t120101BO.updateAccess(railAccessInfo);
        }
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
		log("添加门禁信息成功。操作员编号：" + operator.getOprId());

        return Constants.SUCCESS_CODE;
	}
	
	
	/**
	 * 更新终端信息(配套设备)
	 * @Description: TODO
	 * @param @return
	 * @param @throws Exception   
	 * @return String  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public String editAccess2()throws Exception{
		System.out.println("------------------更新终端设备(配套设备)---------------");
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("EQUIP_CODE", getEquipCode());
		paramMap.put("ACCESS_CODE", getAccessCode());
		List<RailAccessEquipInfo> rwInfos = t120103BO.getByParam(paramMap);
		//判断机构是否存在getWhseCode()
        if (rwInfos!=null&&!rwInfos.isEmpty()) {
        	for(int i = 0; i < rwInfos.size(); i++){
        	String rcode = t120103BO.delete(rwInfos.get(i));
        	}
        }
        
        String[] strArray=null;
        if(getEquipName11()!=null&&getEquipName11()!=""){
		strArray=(getEquipName11()).split(",");
		for (int i = 0; i < strArray.length; i++) {
			RailAccessEquipInfo railAccessEquipInfo=new RailAccessEquipInfo();
			railAccessEquipInfo.setAccessCode(getAccessCode());
			railAccessEquipInfo.setEquipStatus("0");
			railAccessEquipInfo.setEquipCode(getEquipCode());
			railAccessEquipInfo.setAddDate(new Date());
			railAccessEquipInfo.setInstallDate(new Date());
			railAccessEquipInfo.setAddUser(operator.getOprId());
			railAccessEquipInfo.setEquipType(1l);
			//8604主机防区1烟感，防区2烟感，防区3红外，防区4红外，防区5防拆，防区6门磁
			switch (strArray[i]) {
			case " 1":
				railAccessEquipInfo.setEquipName("烟感-外");
				railAccessEquipInfo.setDefence(1l);
				break;
			case "1":
				railAccessEquipInfo.setEquipName("烟感-外");
				railAccessEquipInfo.setDefence(1l);
				break;
			case " 2":
				railAccessEquipInfo.setEquipName("烟感-内");
				railAccessEquipInfo.setDefence(2l);
				break;
			case "2":
				railAccessEquipInfo.setEquipName("烟感-内");
				railAccessEquipInfo.setDefence(2l);
				break;
			case " 3":
				railAccessEquipInfo.setEquipName("红外-外");
				railAccessEquipInfo.setDefence(3l);
				break;
			case "3":
				railAccessEquipInfo.setEquipName("红外-外");
				railAccessEquipInfo.setDefence(3l);
				break;
			case " 4":
				railAccessEquipInfo.setEquipName("红外-内");
				railAccessEquipInfo.setDefence(4l);
				break;
			case "4":
				railAccessEquipInfo.setEquipName("红外-内");
				railAccessEquipInfo.setDefence(4l);
				break;
			case " 5":
				railAccessEquipInfo.setEquipName("防拆");
				railAccessEquipInfo.setDefence(5l);
				break;
			case "5":
				railAccessEquipInfo.setEquipName("防拆");
				railAccessEquipInfo.setDefence(5l);
				break;
			case " 6":
				railAccessEquipInfo.setEquipName("门磁");
				railAccessEquipInfo.setDefence(6l);
				break;
			case "6":
				railAccessEquipInfo.setEquipName("门磁");
				railAccessEquipInfo.setDefence(6l);
				break;
			}
			String rcode=t120103BO.add(railAccessEquipInfo);
		}
		}
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 更新终端信息(摄像头和安卓设备)
	 * @Description: TODO
	 * @param @return
	 * @param @throws Exception   
	 * @return String  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public String editAccess()throws Exception{
		System.out.println("------------------修改终端设备---------------");
		RailAccessEquipInfo railAccessEquipInfo = t120103BO.getByCode(getEquipId());
		//判断机构是否存在getWhseCode()
        if (railAccessEquipInfo==null) {
            return ErrorCode.T100100_01;
        }
        railAccessEquipInfo.setAccessCode(getAccessCode());
        railAccessEquipInfo.setEquipCode(getEquipCode());
        railAccessEquipInfo.setEquipName(getEquipName());
        railAccessEquipInfo.setEquipType(getEquipType());
        railAccessEquipInfo.setEquipIp(getEquipIp());
        railAccessEquipInfo.setNote1(getNote1());
        railAccessEquipInfo.setNote2(getNote2());
        railAccessEquipInfo.setInstallDate(new Date());
        //railAccessEquipInfo.setEquipStatus(getEquipStatus());
        railAccessEquipInfo.setAddUser(operator.getOprId());
        railAccessEquipInfo.setAddDate(new Date());
   
		String rcode = t120103BO.updateAccess(railAccessEquipInfo);
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
	 * 删除
	 * @Description: TODO
	 * @param @return
	 * @param @throws Exception   
	 * @return String  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	private String delete() throws Exception {
		System.out.println("------------------删除终端设备---------------");
		RailAccessEquipInfo railAccessEquipInfo = t120103BO.getByCode(getEquipId());
//		railAccessEquipInfo.setEquipStatus("4");
//		String rcode = t120103BO.updateAccess(railAccessEquipInfo);
		String rcode = t120103BO.delete(railAccessEquipInfo);
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
		System.out.println("------------------开启终端设备---------------");
		RailAccessEquipInfo railAccessEquipInfo = t120103BO.getByCode(getEquipId());
		railAccessEquipInfo.setEquipStatus("0");
		String rcode = t120103BO.updateAccess(railAccessEquipInfo);
        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
        printWriter.write(rcode);
        printWriter.flush();
        printWriter.close();
        
        return Constants.SUCCESS_CODE;
	}
	/**
	 * 获得单个终端信息
	 * @Description: TODO
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public String getData() {
		System.out.println("------------------获得单个终端信息---------------");
        try {
        	RailAccessEquipInfo railAccessEquipInfo = t120103BO.getByCode(getEquipId());
            Map<String, Object> midMap = new HashMap<String, Object>();
            midMap.put("success", true);
            midMap.put("msg", railAccessEquipInfo);
            String str = JSONObject.toJSONString(midMap);
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
	 * 关闭终端
	 * @Description: TODO
	 * @param @return
	 * @param @throws Exception   
	 * @return String  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	private String closeAccess() throws Exception {
		System.out.println("------------------关闭终端设备---------------");
		RailAccessEquipInfo railAccessEquipInfo = t120103BO.getByCode(getEquipId());
		railAccessEquipInfo.setEquipStatus("1");
		String rcode = t120103BO.updateAccess(railAccessEquipInfo);
        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
        printWriter.write(rcode);
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

	
	public String getEquipCode() {
		return equipCode;
	}
	public void setEquipCode(String equipCode) {
		this.equipCode = equipCode;
	}
	public String getEquipName() {
		return equipName;
	}
	public void setEquipName(String equipName) {
		this.equipName = equipName;
	}
	public long getEquipType() {
		return equipType;
	}
	public void setEquipType(long equipType) {
		this.equipType = equipType;
	}
	public String getEquipIp() {
		return equipIp;
	}
	public void setEquipIp(String equipIp) {
		this.equipIp = equipIp;
	}
	public long getMfrsOrg() {
		return mfrsOrg;
	}
	public void setMfrsOrg(long mfrsOrg) {
		this.mfrsOrg = mfrsOrg;
	}
	public String getMfrsTel() {
		return mfrsTel;
	}
	public void setMfrsTel(String mfrsTel) {
		this.mfrsTel = mfrsTel;
	}
	
	public long getExamPeriod() {
		return examPeriod;
	}
	public void setExamPeriod(long examPeriod) {
		this.examPeriod = examPeriod;
	}
	public String getEquipStatus() {
		return equipStatus;
	}
	public void setEquipStatus(String equipStatus) {
		this.equipStatus = equipStatus;
	}
	public String getAddUser() {
		return addUser;
	}
	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	public String getUpdUser() {
		return updUser;
	}
	public void setUpdUser(String updUser) {
		this.updUser = updUser;
	}
	public Date getUpdDate() {
		return updDate;
	}
	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
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
	public String getEquipId() {
		return equipId;
	}
	public void setEquipId(String equipId) {
		this.equipId = equipId;
	}
	public String getEquipName11() {
		return equipName11;
	}
	public void setEquipName11(String equipName11) {
		this.equipName11 = equipName11;
	}
	public String getNote3() {
		return note3;
	}
	public void setNote3(String note3) {
		this.note3 = note3;
	}
	
	
}
