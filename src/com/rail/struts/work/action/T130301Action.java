package com.rail.struts.work.action;

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
import com.rail.bo.work.T130301BO;
import com.rail.bo.work.T130301BO;
import com.rail.po.access.RailAccessEmployee;
import com.rail.po.access.RailAccessMaintain;

public class T130301Action extends BaseAction {
private static final long serialVersionUID = 1L;
	
	private long  id;
	private String  accessCode;
	private String  employeeCode;
	private String  workCode;
	private String  infoSign;
	private Date  addDate;
	private String  note1;
	private String  note2;
	private String  note3;
	
	private T130301BO t130301BO = (T130301BO) ContextUtil.getBean("T130301BO");
	public T130301BO getT130301BO() {
		return t130301BO;
	}

	public void setT130301BO(T130301BO t130301BO) {
		t130301BO = t130301BO;
	}
		
	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
				System.out.println(getMethod()+"  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				try {
		            if ("edit".equals(getMethod())) {
		            	rspCode = editAccess();
		            }else if ("add".equals(getMethod())) {
		                rspCode = add();
		            }
		        } catch (Exception e) {
		        	//e.printStackTrace();
		            log("操作员：" + operator.getOprId() + "，对门禁进行：" + getMethod() + "，失败，失败原因为：" + e);
		        }
		        return rspCode;
	}
	
	/**
	 * 新增门禁检修保养
	 * @Description: TODO
	 * @param @return
	 * @param @throws Exception   
	 * @return String  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月16日
	 */
	private String add() throws Exception {
		System.out.println("------------------新增门禁检修保养---------------");
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("ACCESS_CODE", getAccessCode());
		List<RailAccessEmployee> rwInfos = t130301BO.getByParam(paramMap);
		RailAccessEmployee railAccessEmployee = new RailAccessEmployee();
//		railAccessEmployee.setAccessCode(getAccessCode());
//		railAccessEmployee.setEquipCode(getEquipCode());
//		railAccessEmployee.setAddDate(new Date());
//		railAccessEmployee.setMaintainUser(operator.getOprId());
		String rcode=t130301BO.add(railAccessEmployee);
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
		log("添加仓库信息成功。操作员编号：" + operator.getOprId());
        return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 更新门禁检修保养信息
	 * @Description: TODO
	 * @param @return
	 * @param @throws Exception   
	 * @return String  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public String editAccess()throws Exception{
		System.out.println("------------------更新门禁检修保养信息---------------");
		RailAccessEmployee railAccessEmployee  = t130301BO.getByCode(String.valueOf(getId()));
        if (railAccessEmployee==null) {
            return ErrorCode.T100100_01;
        }
		String rcode = t130301BO.updateAccess(railAccessEmployee);
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
	 * 获得单个门禁检修保养信息
	 * @Description: TODO
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public String getData() {
		System.out.println("------------------获得单个门禁检修保养信息---------------");
        try {
        	RailAccessEmployee railAccessEmployee = t130301BO.getByCode(getAccessCode());
            Map<String, Object> midMap = new HashMap<String, Object>();
            midMap.put("success", true);
            midMap.put("msg", railAccessEmployee);
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


	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getWorkCode() {
		return workCode;
	}

	public void setWorkCode(String workCode) {
		this.workCode = workCode;
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

