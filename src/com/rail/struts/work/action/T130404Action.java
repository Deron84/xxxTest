package com.rail.struts.work.action;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSONObject;
import com.huateng.common.Constants;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.ContextUtil;
import com.rail.bo.work.T130404BO;
import com.rail.po.access.RailAccessEquipType;
import com.rail.po.base.RailEmployeeType;

public class T130404Action extends BaseAction {

	private static final long serialVersionUID = 1L;
	private long id;
	private String typeName;
	private String delStatus;
	private String addUser;
	private Date addDate;
	private String updUser;
	private Date updDate;
	private String enableStatus;
	
	//人员类型信息维护
	private T130404BO t130404BO = (T130404BO) ContextUtil.getBean("T130404BO");
	public T130404BO getT130404BO() {
		return t130404BO;
	}
	public void setT130404BO(T130404BO t130404bo) {
		t130404BO = t130404bo;
	}
		
	@Override
	protected String subExecute() throws Exception {
		System.out.println(getMethod()+"  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		try {
			 if ("open".equals(getMethod())) {
	                rspCode = open();
	            }else if("close".equals(getMethod())){
	            	rspCode = close();
	            }else if("edit".equals(getMethod())){
	            	rspCode = edit();
	            }else if("add".equals(getMethod())){
	            	rspCode = add();
	            }else if("delete".equals(getMethod())){
	            	rspCode = delete();
	            }
        } catch (Exception e) {
        	//e.printStackTrace();
            log("操作员：" + operator.getOprId() + "，对人员类型进行：" + getMethod() + "，失败，失败原因为：" + e);
        }
        return rspCode;
	}
	
	/**
	 * 获得单个人员类型信息
	 * @Description: TODO
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public String getData() {
		System.out.println("----------------获得单个人员类型信息-----------------");
        try {
        	RailEmployeeType railEmployeeType = t130404BO.getByCode(String.valueOf(getId()));
        	System.out.println("根据参数查询人员类型railAccessEquipInfo:" + railEmployeeType);
            Map<String, Object> midMap = new HashMap<String, Object>();
            midMap.put("success", true);
            midMap.put("msg", railEmployeeType);
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
	
	/**
	 * 新增人员类型
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception{
		System.out.println("----------------新增人员类型-----------------");
		Map<String, String> params = new HashMap<String, String>();
		params.put("TYPE_NAME", getTypeName());
		List<RailEmployeeType> rcoInfos = t130404BO.getByParam(params);
		if(rcoInfos != null && rcoInfos.size() > 0){
			Map<String, Object> errorMap = new HashMap<String, Object>();
			errorMap.put("success", false);
			errorMap.put("msg", "人员类型名称已存在！");
            String str = JSONObject.toJSONString(errorMap);
            PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
            printWriter.write(str);
            printWriter.flush();
            printWriter.close();
            return null;
		}
		RailEmployeeType railEmployeeType = new RailEmployeeType();
		railEmployeeType.setTypeName(getTypeName());
		railEmployeeType.setDelStatus("0");
		railEmployeeType.setEnableStatus("0");
		railEmployeeType.setAddUser(operator.getOprId());
		railEmployeeType.setAddDate(new Date());
		railEmployeeType.setUpdUser(operator.getOprId());
		railEmployeeType.setUpdDate(new Date());
		
		String rCode = t130404BO.add(railEmployeeType);
		String msg = "添加人员类型成功";
		boolean success = true;
		if(!rCode.equals(Constants.SUCCESS_CODE)){
			msg = "添加人员类型失败";
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
		log("添加人员类型成功。操作员编号：" + operator.getOprId());
        return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 修改人员类型
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception{
		System.out.println("----------------修改人员类型-----------------");
		RailEmployeeType railEmployeeType = t130404BO.getByCode(String.valueOf(getId()));
		if(railEmployeeType == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该人员类型");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}else{
			if(!getTypeName().equals(railEmployeeType.getTypeName())){
				Map<String, String> params = new HashMap<String, String>();
				params.put("TYPE_NAME", getTypeName());
				List<RailEmployeeType> rcoInfos = t130404BO.getByParam(params);
				if(rcoInfos != null && rcoInfos.size() > 0){
					Map<String, Object> errorMap = new HashMap<String, Object>();
					errorMap.put("success", false);
					errorMap.put("msg", "人员类型名称已存在！");
		            String str = JSONObject.toJSONString(errorMap);
		            PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
		            printWriter.write(str);
		            printWriter.flush();
		            printWriter.close();
		            return Constants.FAILURE_CODE;
				}
		  }
		}
		railEmployeeType.setTypeName(getTypeName());
		railEmployeeType.setEnableStatus(getEnableStatus());
		railEmployeeType.setUpdUser(operator.getOprId());
		railEmployeeType.setUpdDate(new Date());
        String rcode =t130404BO.update(railEmployeeType);
		
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
        
		log("更新人员类型信息成功。操作员编号：" + operator.getOprId());
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 删除人员类型
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception{
		System.out.println("----------------删除人员类型-----------------");
		RailEmployeeType railEmployeeType =  t130404BO.getByCode(String.valueOf(getId()));
		if(railEmployeeType == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该人员类型");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}
		railEmployeeType.setDelStatus("1");
		railEmployeeType.setUpdUser(operator.getOprId());
		railEmployeeType.setUpdDate(new Date());
		   String rcode =t130404BO.update(railEmployeeType);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(rcode);
	        printWriter.flush();
	        printWriter.close();
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 启用人员类型
	 * @return
	 */
	public String open() throws Exception {
		System.out.println("----------------启用人员类型-----------------");
		RailEmployeeType railEmployeeType =  t130404BO.getByCode(String.valueOf(getId()));
		if(railEmployeeType == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该人员类型");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}
		railEmployeeType.setEnableStatus("0");
		railEmployeeType.setUpdUser(operator.getOprId());
		railEmployeeType.setUpdDate(new Date());
		   String rcode =t130404BO.update(railEmployeeType);
			
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(rcode);
	        printWriter.flush();
	        printWriter.close();
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 停用人员类型
	 * @return
	 */
	public String close() throws Exception {
		System.out.println("----------------停用人员类型-----------------");
		RailEmployeeType railEmployeeType =  t130404BO.getByCode(String.valueOf(getId()));
		if(railEmployeeType == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该人员类型");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}
		railEmployeeType.setEnableStatus("1");
		railEmployeeType.setUpdUser(operator.getOprId());
		railEmployeeType.setUpdDate(new Date());
		   String rcode =t130404BO.update(railEmployeeType);
			
		   PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(rcode);
	        printWriter.flush();
	        printWriter.close();
		return Constants.SUCCESS_CODE;
	}

	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(String delStatus) {
		this.delStatus = delStatus;
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

	public String getEnableStatus() {
		return enableStatus;
	}

	public void setEnableStatus(String enableStatus) {
		this.enableStatus = enableStatus;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

}

