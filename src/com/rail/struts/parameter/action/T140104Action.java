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
import com.rail.bo.parameter.T140104BO;
import com.rail.po.access.RailAccessEquipType;
import com.rail.po.tool.RailToolType;
/**
 *工具分类管理
 * @author qiufulong
 *
 */
public class T140104Action extends BaseAction {

	private static final long serialVersionUID = 1L;
	private long id;
	private String toolTypeName;
	private String delStatus;
	private String addUser;
	private Date addDate;
	private String updUser;
	private Date updDate;
	private String enableStatus;
	private long formOrg;
	
	//工具分类信息维护
	private T140104BO t140104BO = (T140104BO) ContextUtil.getBean("T140104BO");
	public T140104BO getT140104BO() {
		return t140104BO;
	}
	public void setT140104BO(T140104BO t140104bo) {
		t140104BO = t140104bo;
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
            log("操作员：" + operator.getOprId() + "，对工具分类进行：" + getMethod() + "，失败，失败原因为：" + e);
        }
        return rspCode;
	}
	
	/**
	 * 获得单个工具分类信息
	 * @Description: TODO
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public String getData() {
		System.out.println("----------------获得单个工具分类-----------------");
        try {
        	RailToolType railToolType = t140104BO.getByCode(String.valueOf(getId()));
            Map<String, Object> midMap = new HashMap<String, Object>();
            midMap.put("success", true);
            midMap.put("msg", railToolType);
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
	 * 新增工具分类
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception{
		System.out.println("----------------新增工具分类-----------------");
		Map<String, String> params = new HashMap<String, String>();
		params.put("TOOL_TYPE_NAME", getToolTypeName());
		List<RailToolType> rcoInfos = t140104BO.getByParam(params);
		if(rcoInfos != null && rcoInfos.size() > 0){
			Map<String, Object> errorMap = new HashMap<String, Object>();
			errorMap.put("success", false);
			errorMap.put("msg", "工具分类名称已存在！");
            String str = JSONObject.toJSONString(errorMap);
            PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
            printWriter.write(str);
            printWriter.flush();
            printWriter.close();
            return null;
		}
		RailToolType railToolType = new RailToolType();
		railToolType.setToolTypeName(getToolTypeName());
		railToolType.setDelStatus("0");
		railToolType.setEnableStatus("0");
		railToolType.setAddUser(operator.getOprId());
		railToolType.setAddDate(new Date());
		railToolType.setUpdUser(operator.getOprId());
		railToolType.setUpdDate(new Date());
		
		String rCode = t140104BO.add(railToolType);
		String msg = "添加工具分类成功";
		boolean success = true;
		if(!rCode.equals(Constants.SUCCESS_CODE)){
			msg = "添加工具分类失败";
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
		log("添加工具分类成功。操作员编号：" + operator.getOprId());
        return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 修改工具分类
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception{
		System.out.println("----------------修改工具分类-----------------");
		RailToolType railToolType = t140104BO.getByCode(String.valueOf(getId()));
		if(railToolType == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该工具分类");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}else{
			if(!getToolTypeName().equals(railToolType.getToolTypeName())){
				Map<String, String> params = new HashMap<String, String>();
				params.put("TOOL_TYPE_NAME", getToolTypeName());
				List<RailToolType> rcoInfos = t140104BO.getByParam(params);
				if(rcoInfos != null && rcoInfos.size() > 0){
					Map<String, Object> errorMap = new HashMap<String, Object>();
					errorMap.put("success", false);
					errorMap.put("msg", "工具分类名称已存在！");
		            String str = JSONObject.toJSONString(errorMap);
		            PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
		            printWriter.write(str);
		            printWriter.flush();
		            printWriter.close();
		            return Constants.FAILURE_CODE;
				}
		  }
		}
		railToolType.setToolTypeName(getToolTypeName());
		railToolType.setEnableStatus(getEnableStatus());
		railToolType.setUpdUser(operator.getOprId());
		railToolType.setUpdDate(new Date());
        String rcode =t140104BO.update(railToolType);
		
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
        
		log("更新工具分类信息成功。操作员编号：" + operator.getOprId());
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 删除工具分类
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception{
		System.out.println("----------------删除工具分类-----------------");
		RailToolType railToolType =  t140104BO.getByCode(String.valueOf(getId()));
		if(railToolType == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该工具分类");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}
		railToolType.setDelStatus("1");
		railToolType.setUpdUser(operator.getOprId());
		railToolType.setUpdDate(new Date());
		   String rcode =t140104BO.update(railToolType);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(rcode);
	        printWriter.flush();
	        printWriter.close();
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 启用工具分类
	 * @return
	 */
	public String open() throws Exception {
		System.out.println("----------------启用工具分类-----------------");
		RailToolType railToolType =  t140104BO.getByCode(String.valueOf(getId()));
		if(railToolType == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该工具分类");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}
		railToolType.setEnableStatus("0");
		railToolType.setUpdUser(operator.getOprId());
		railToolType.setUpdDate(new Date());
		   String rcode =t140104BO.update(railToolType);
			
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(rcode);
	        printWriter.flush();
	        printWriter.close();
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 停用工具分类
	 * @return
	 */
	public String close() throws Exception {
		System.out.println("----------------停用工具分类-----------------");
		RailToolType railToolType =  t140104BO.getByCode(String.valueOf(getId()));
		if(railToolType == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该工具分类");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}
		railToolType.setEnableStatus("1");
		railToolType.setUpdUser(operator.getOprId());
		railToolType.setUpdDate(new Date());
		   String rcode =t140104BO.update(railToolType);
			
		   PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(rcode);
	        printWriter.flush();
	        printWriter.close();
		return Constants.SUCCESS_CODE;
	}

	public String getToolTypeName() {
		return toolTypeName;
	}

	public void setToolTypeName(String toolTypeName) {
		this.toolTypeName = toolTypeName;
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
