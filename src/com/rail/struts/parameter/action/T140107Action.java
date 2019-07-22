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
import com.rail.bo.parameter.T140107BO;
import com.rail.po.access.RailAccessEquipType;
/**
 *终端类型终端类型管理
 * @author qiufulong
 *
 */
public class T140107Action extends BaseAction {

	private static final long serialVersionUID = 1L;
	private long id;
	private String equipTypeName;
	private String delStatus;
	private String addUser;
	private Date addDate;
	private String updUser;
	private Date updDate;
	private String enableStatus;
	private long formOrg;
	
	//终端类型信息维护
	private T140107BO t140107BO = (T140107BO) ContextUtil.getBean("T140107BO");
	public T140107BO getT140107BO() {
		return t140107BO;
	}
	public void setT140107BO(T140107BO t140107bo) {
		t140107BO = t140107bo;
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
            log("操作员：" + operator.getOprId() + "，对终端类型进行：" + getMethod() + "，失败，失败原因为：" + e);
        }
        return rspCode;
	}
	
	/**
	 * 获得单个终端类型信息
	 * @Description: TODO
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public String getData() {
		System.out.println("----------------获得单个终端类型信息-----------------");
        try {
        	RailAccessEquipType railAccessEquipType = t140107BO.getByCode(String.valueOf(getId()));
        	System.out.println("根据参数查询终端类型railAccessEquipInfo:" + railAccessEquipType);
            Map<String, Object> midMap = new HashMap<String, Object>();
            midMap.put("success", true);
            midMap.put("msg", railAccessEquipType);
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
	 * 新增终端类型
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception{
		System.out.println("----------------新增终端类型-----------------");
		Map<String, String> params = new HashMap<String, String>();
		params.put("EQUIP_TYPE_NAME", getEquipTypeName());
		List<RailAccessEquipType> rcoInfos = t140107BO.getByParam(params);
		if(rcoInfos != null && rcoInfos.size() > 0){
			Map<String, Object> errorMap = new HashMap<String, Object>();
			errorMap.put("success", false);
			errorMap.put("msg", "终端类型名称已存在！");
            String str = JSONObject.toJSONString(errorMap);
            PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
            printWriter.write(str);
            printWriter.flush();
            printWriter.close();
            return null;
		}
		RailAccessEquipType railAccessEquipType = new RailAccessEquipType();
		railAccessEquipType.setEquipTypeName(getEquipTypeName());
		railAccessEquipType.setDelStatus("0");
		railAccessEquipType.setEnableStatus("0");
		railAccessEquipType.setAddUser(operator.getOprId());
		railAccessEquipType.setAddDate(new Date());
		railAccessEquipType.setUpdUser(operator.getOprId());
		railAccessEquipType.setUpdDate(new Date());
		
		String rCode = t140107BO.add(railAccessEquipType);
		String msg = "添加终端类型成功";
		boolean success = true;
		if(!rCode.equals(Constants.SUCCESS_CODE)){
			msg = "添加终端类型失败";
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
		log("添加终端类型成功。操作员编号：" + operator.getOprId());
        return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 修改终端类型
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception{
		System.out.println("----------------修改终端类型-----------------");
		RailAccessEquipType railAccessEquipType = t140107BO.getByCode(String.valueOf(getId()));
		if(railAccessEquipType == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该终端类型");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}else{
			if(!getEquipTypeName().equals(railAccessEquipType.getEquipTypeName())){
			Map<String, String> params = new HashMap<String, String>();
			params.put("EQUIP_TYPE_NAME", getEquipTypeName());
			List<RailAccessEquipType> rcoInfos = t140107BO.getByParam(params);
			if(rcoInfos != null && rcoInfos.size() > 0){
				Map<String, Object> errorMap = new HashMap<String, Object>();
				errorMap.put("success", false);
				errorMap.put("msg", "终端类型名称已存在！");
	            String str = JSONObject.toJSONString(errorMap);
	            PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	            printWriter.write(str);
	            printWriter.flush();
	            printWriter.close();
	            return Constants.FAILURE_CODE;
			}
		  }
		}
		railAccessEquipType.setEquipTypeName(getEquipTypeName());
		railAccessEquipType.setEnableStatus(getEnableStatus());
		railAccessEquipType.setUpdUser(operator.getOprId());
		railAccessEquipType.setUpdDate(new Date());
        String rcode =t140107BO.update(railAccessEquipType);
		
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
        
		log("更新终端类型信息成功。操作员编号：" + operator.getOprId());
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 删除终端类型
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception{
		System.out.println("----------------删除终端类型-----------------");
		RailAccessEquipType railAccessEquipType =  t140107BO.getByCode(String.valueOf(getId()));
		if(railAccessEquipType == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该终端类型");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}
		railAccessEquipType.setDelStatus("1");
		railAccessEquipType.setUpdUser(operator.getOprId());
		railAccessEquipType.setUpdDate(new Date());
		   String rcode =t140107BO.update(railAccessEquipType);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(rcode);
	        printWriter.flush();
	        printWriter.close();
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 启用终端类型
	 * @return
	 */
	public String open() throws Exception {
		System.out.println("----------------启用终端类型-----------------");
		RailAccessEquipType railAccessEquipType =  t140107BO.getByCode(String.valueOf(getId()));
		if(railAccessEquipType == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该终端类型");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}
		railAccessEquipType.setEnableStatus("0");
		railAccessEquipType.setUpdUser(operator.getOprId());
		railAccessEquipType.setUpdDate(new Date());
		   String rcode =t140107BO.update(railAccessEquipType);
			
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(rcode);
	        printWriter.flush();
	        printWriter.close();
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 停用终端类型
	 * @return
	 */
	public String close() throws Exception {
		System.out.println("----------------停用终端类型-----------------");
		RailAccessEquipType railAccessEquipType =  t140107BO.getByCode(String.valueOf(getId()));
		if(railAccessEquipType == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该终端类型");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}
		railAccessEquipType.setEnableStatus("1");
		railAccessEquipType.setUpdUser(operator.getOprId());
		railAccessEquipType.setUpdDate(new Date());
		   String rcode =t140107BO.update(railAccessEquipType);
			
		   PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(rcode);
	        printWriter.flush();
	        printWriter.close();
		return Constants.SUCCESS_CODE;
	}

	public String getEquipTypeName() {
		return equipTypeName;
	}

	public void setEquipTypeName(String equipTypeName) {
		this.equipTypeName = equipTypeName;
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

