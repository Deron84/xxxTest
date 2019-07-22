package com.rail.struts.parameter.action;

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
import com.rail.bo.parameter.T140106BO;
import com.rail.po.access.RailAccessEquipType;
import com.rail.po.tool.RailToolModel;
/**
 *工具型号管理
 * @author qiufulong
 *
 */
public class T140106Action extends BaseAction {

	private static final long serialVersionUID = 1L;
	private long id;
	private String modelName;
	private String delStatus;
	private String addUser;
	private Date addDate;
	private String updUser;
	private Date updDate;
	private String enableStatus;
	private long formOrg;
	
	//工具型号信息维护
	private T140106BO t140106BO = (T140106BO) ContextUtil.getBean("T140106BO");
	public T140106BO getT140106BO() {
		return t140106BO;
	}
	public void setT140106BO(T140106BO t140106bo) {
		t140106BO = t140106bo;
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
            log("操作员：" + operator.getOprId() + "，对工具型号进行：" + getMethod() + "，失败，失败原因为：" + e);
        }
        return rspCode;
	}
	
	/**
	 * 获得单个工具型号信息
	 * @Description: TODO
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public String getData() {
		System.out.println("----------------获得单个工具型号信息-----------------");
        try {
        	RailToolModel railToolModel = t140106BO.getByCode(String.valueOf(getId()));
            Map<String, Object> midMap = new HashMap<String, Object>();
            midMap.put("success", true);
            midMap.put("msg", railToolModel);
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
	 * 新增工具型号
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception{
		System.out.println("----------------新增工具型号-----------------");
		Map<String, String> params = new HashMap<String, String>();
		params.put("MODEL_NAME", getModelName());
		List<RailToolModel> rcoInfos = t140106BO.getByParam(params);
		if(rcoInfos != null && rcoInfos.size() > 0){
			Map<String, Object> errorMap = new HashMap<String, Object>();
			errorMap.put("success", false);
			errorMap.put("msg", "工具型号名称已存在！");
            String str = JSONObject.toJSONString(errorMap);
            PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
            printWriter.write(str);
            printWriter.flush();
            printWriter.close();
            return null;
		}
		RailToolModel railToolModel = new RailToolModel();
		railToolModel.setModelName(getModelName());
		railToolModel.setDelStatus("0");
		railToolModel.setEnableStatus("0");
		railToolModel.setAddUser(operator.getOprId());
		railToolModel.setAddDate(new Date());
		railToolModel.setUpdUser(operator.getOprId());
		railToolModel.setUpdDate(new Date());
		
		String rCode = t140106BO.add(railToolModel);
		String msg = "添加工具型号成功";
		boolean success = true;
		if(!rCode.equals(Constants.SUCCESS_CODE)){
			msg = "添加工具型号失败";
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
		log("添加工具型号成功。操作员编号：" + operator.getOprId());
        return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 修改工具型号
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception{
		System.out.println("----------------修改工具型号-----------------");
		RailToolModel railToolModel = t140106BO.getByCode(String.valueOf(getId()));
		if(railToolModel == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该工具型号");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}else{
			if(!getModelName().equals(railToolModel.getModelName())){
				Map<String, String> params = new HashMap<String, String>();
				params.put("MODEL_NAME", getModelName());
				List<RailToolModel> rcoInfos = t140106BO.getByParam(params);
				if(rcoInfos != null && rcoInfos.size() > 0){
					Map<String, Object> errorMap = new HashMap<String, Object>();
					errorMap.put("success", false);
					errorMap.put("msg", "工具型号名称已存在！");
		            String str = JSONObject.toJSONString(errorMap);
		            PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
		            printWriter.write(str);
		            printWriter.flush();
		            printWriter.close();
		        	return Constants.FAILURE_CODE;
				}
		  }
		}
		railToolModel.setModelName(getModelName());
		railToolModel.setUpdUser(operator.getOprId());
		railToolModel.setUpdDate(new Date());
        String rcode =t140106BO.update(railToolModel);
		
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
        
		log("更新工具型号信息成功。操作员编号：" + operator.getOprId());
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 删除工具型号
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception{
		System.out.println("----------------删除工具型号-----------------");
		RailToolModel railToolModel =  t140106BO.getByCode(String.valueOf(getId()));
		if(railToolModel == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该工具型号");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}
		railToolModel.setDelStatus("1");
		railToolModel.setUpdUser(operator.getOprId());
		railToolModel.setUpdDate(new Date());
		   String rcode =t140106BO.update(railToolModel);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(rcode);
	        printWriter.flush();
	        printWriter.close();
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 启用工具型号
	 * @return
	 */
	public String open() throws Exception {
		System.out.println("----------------启用工具型号-----------------");
		RailToolModel railToolModel =  t140106BO.getByCode(String.valueOf(getId()));
		if(railToolModel == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该工具型号");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}
		railToolModel.setEnableStatus("0");
		railToolModel.setUpdUser(operator.getOprId());
		railToolModel.setUpdDate(new Date());
		   String rcode =t140106BO.update(railToolModel);
			
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(rcode);
	        printWriter.flush();
	        printWriter.close();
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 停用工具型号
	 * @return
	 */
	public String close() throws Exception {
		System.out.println("----------------停用工具型号-----------------");
		RailToolModel railToolModel =  t140106BO.getByCode(String.valueOf(getId()));
		if(railToolModel == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该工具型号");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}
		railToolModel.setEnableStatus("1");
		railToolModel.setUpdUser(operator.getOprId());
		railToolModel.setUpdDate(new Date());
		   String rcode =t140106BO.update(railToolModel);
			
		   PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(rcode);
	        printWriter.flush();
	        printWriter.close();
		return Constants.SUCCESS_CODE;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
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

	public long getFormOrg() {
		return formOrg;
	}

	public void setFormOrg(long formOrg) {
		this.formOrg = formOrg;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

}

