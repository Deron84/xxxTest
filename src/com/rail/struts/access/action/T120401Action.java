package com.rail.struts.access.action;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSONObject;
import com.huateng.common.Constants;
import com.huateng.common.ErrorCode;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.ContextUtil;
import com.rail.bo.access.T120401BO;
import com.rail.po.access.RailAccessMaintainWarn;

public class T120401Action extends BaseAction {
	private static final long serialVersionUID = 1L;
	private long id;
	private String accessCode;
	private String equipCode;
	private String warnMsg;
	private String infoSign;
	private Date addDate;
	private String verifyUser;
	private Date verifyDate;
	private String verifyMsg;
	private String note1;
	private String note2;
	private String note3;
	//门禁信息维护
		private T120401BO t120401BO = (T120401BO) ContextUtil.getBean("T120401BO");
		public T120401BO getT120401BO() {
			return t120401BO;
		}

		public void setT120401BO(T120401BO t120401BO) {
			t120401BO = t120401BO;
		}
		
	@Override
	protected String subExecute() throws Exception {
				System.out.println(getMethod()+"  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				try {
		            if ("edit".equals(getMethod())) {
		            	rspCode = editAccess();
		            }
		        } catch (Exception e) {
		        	//e.printStackTrace();
		            log("操作员：" + operator.getOprId() + "，对门禁进行：" + getMethod() + "，失败，失败原因为：" + e);
		        }
		        return rspCode;
	}
	
	/**
	 * 更新门禁检修保养预警信息
	 * @Description: TODO
	 * @param @return
	 * @param @throws Exception   
	 * @return String  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public String editAccess()throws Exception{
		System.out.println("------------------更新门禁检修保养预警---------------");
		RailAccessMaintainWarn railAccessMaintainWarn  = t120401BO.getByCode(String.valueOf(getId()));
        if (railAccessMaintainWarn==null) {
            return ErrorCode.T100100_01;
        }
        railAccessMaintainWarn.setVerifyMsg(getVerifyMsg());
        railAccessMaintainWarn.setInfoSign("1");
        railAccessMaintainWarn.setVerifyUser(operator.getOprId());
        railAccessMaintainWarn.setVerifyDate(new Date());
		String rcode = t120401BO.updateAccess(railAccessMaintainWarn);
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
	 * 获得单个门禁检修保养预警信息
	 * @Description: TODO
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public String getData() {
		System.out.println("------------------获得单个门禁检修保养预警信息---------------");
        try {
        	RailAccessMaintainWarn railAccessMaintainWarn = t120401BO.getByCode(getAccessCode());
            Map<String, Object> midMap = new HashMap<String, Object>();
            midMap.put("success", true);
            midMap.put("msg", railAccessMaintainWarn);
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getWarnMsg() {
		return warnMsg;
	}

	public void setWarnMsg(String warnMsg) {
		this.warnMsg = warnMsg;
	}

	public String getInfoSign() {
		return infoSign;
	}

	public void setInfoSign(String infoSign) {
		this.infoSign = infoSign;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public String getVerifyUser() {
		return verifyUser;
	}

	public void setVerifyUser(String verifyUser) {
		this.verifyUser = verifyUser;
	}

	public Date getVerifyDate() {
		return verifyDate;
	}

	public void setVerifyDate(Date verifyDate) {
		this.verifyDate = verifyDate;
	}

	public String getVerifyMsg() {
		return verifyMsg;
	}

	public void setVerifyMsg(String verifyMsg) {
		this.verifyMsg = verifyMsg;
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

	

}

