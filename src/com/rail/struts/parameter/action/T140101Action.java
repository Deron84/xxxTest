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
import com.rail.bo.parameter.T140101BO;
import com.rail.bo.parameter.T140101BO;
import com.rail.po.access.RailAccessEquipType;
import com.rail.po.org.RailMfrsOrg;
import com.rail.po.org.RailFormOrg;

/**
 * 供应商管理
 * @author lipeng
 *
 */
public class T140101Action extends BaseAction {

	private static final long serialVersionUID = 1L;
	private long id;
	private String mfrsOrgName;
	private String delStatus;
	private String mfrsAddress;
	private String mfrsPic;
	private String mfrsTel;
	private String mfrsFax;
	private String addUser;
	private Date addDate;
	private String updUser;
	private Date updDate;
	private String enableStatus;
	
	//供应商信息维护
	private T140101BO t140101BO = (T140101BO) ContextUtil.getBean("T140101BO");
	public T140101BO getT140101BO() {
		return t140101BO;
	}
	public void setT140101BO(T140101BO t140101bo) {
		t140101BO = t140101bo;
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
            log("操作员：" + operator.getOprId() + "，对供应商进行：" + getMethod() + "，失败，失败原因为：" + e);
        }
        return rspCode;
	}
	
	/**
	 * 获得单个供应商信息
	 * @Description: TODO
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public String getData() {
		System.out.println("----------------获得单个供应商信息-----------------");
        try {
        	RailMfrsOrg railMfrsOrg = t140101BO.getByCode(String.valueOf(getId()));
            Map<String, Object> midMap = new HashMap<String, Object>();
            midMap.put("success", true);
            midMap.put("msg", railMfrsOrg);
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
	 * 新增供应商
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception{
		System.out.println("----------------新增供应商-----------------");
		Map<String, String> params = new HashMap<String, String>();
		params.put("MFRS_ORG_NAME", getMfrsOrgName());
		List<RailMfrsOrg> rcoInfos = t140101BO.getByParam(params);
		if(rcoInfos != null && rcoInfos.size() > 0){
			Map<String, Object> errorMap = new HashMap<String, Object>();
			errorMap.put("success", false);
			errorMap.put("msg", "供应商名称已存在！");
            String str = JSONObject.toJSONString(errorMap);
            PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
            printWriter.write(str);
            printWriter.flush();
            printWriter.close();
            return null;
		}
		RailMfrsOrg railMfrsOrg = new RailMfrsOrg();
		railMfrsOrg.setMfrsOrgName(getMfrsOrgName());
		railMfrsOrg.setMfrsAddress(getMfrsAddress());
		railMfrsOrg.setMfrsPic(getMfrsPic());
		railMfrsOrg.setMfrsTel(getMfrsTel());
		railMfrsOrg.setMfrsFax(getMfrsFax());
		railMfrsOrg.setDelStatus("0");
		railMfrsOrg.setEnableStatus("0");
		railMfrsOrg.setAddUser(operator.getOprId());
		railMfrsOrg.setAddDate(new Date());
		railMfrsOrg.setUpdUser(operator.getOprId());
		railMfrsOrg.setUpdDate(new Date());
		
		String rCode = t140101BO.add(railMfrsOrg);
		String msg = "添加供应商成功";
		boolean success = true;
		if(!rCode.equals(Constants.SUCCESS_CODE)){
			msg = "添加供应商失败";
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
		log("添加供应商成功。操作员编号：" + operator.getOprId());
        return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 修改供应商
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception{
		System.out.println("----------------修改供应商-----------------");
		RailMfrsOrg railMfrsOrg = t140101BO.getByCode(String.valueOf(getId()));
		if(railMfrsOrg == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该供应商");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}else{
			if(!getMfrsOrgName().equals(railMfrsOrg.getMfrsOrgName())){
				Map<String, String> params = new HashMap<String, String>();
				params.put("MFRS_ORG_NAME", getMfrsOrgName());
				List<RailMfrsOrg> rcoInfos = t140101BO.getByParam(params);
				if(rcoInfos != null && rcoInfos.size() > 0){
					Map<String, Object> errorMap = new HashMap<String, Object>();
					errorMap.put("success", false);
					errorMap.put("msg", "供应商名称已存在！");
		            String str = JSONObject.toJSONString(errorMap);
		            PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
		            printWriter.write(str);
		            printWriter.flush();
		            printWriter.close();
		            return Constants.FAILURE_CODE;
				}
		  }
		}
		railMfrsOrg.setMfrsOrgName(getMfrsOrgName());
		railMfrsOrg.setMfrsAddress(getMfrsAddress());
		railMfrsOrg.setMfrsPic(getMfrsPic());
		railMfrsOrg.setMfrsTel(getMfrsTel());
		railMfrsOrg.setMfrsFax(getMfrsFax());
		railMfrsOrg.setEnableStatus(getEnableStatus());
		railMfrsOrg.setUpdUser(operator.getOprId());
		railMfrsOrg.setUpdDate(new Date());
        String rcode =t140101BO.update(railMfrsOrg);
		
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
        
		log("更新供应商信息成功。操作员编号：" + operator.getOprId());
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 删除供应商
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception{
		System.out.println("----------------删除供应商-----------------");
		RailMfrsOrg railMfrsOrg =  t140101BO.getByCode(String.valueOf(getId()));
		if(railMfrsOrg == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该供应商");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}
		railMfrsOrg.setDelStatus("1");
		railMfrsOrg.setUpdUser(operator.getOprId());
		railMfrsOrg.setUpdDate(new Date());
		   String rcode =t140101BO.update(railMfrsOrg);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(rcode);
	        printWriter.flush();
	        printWriter.close();
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 启用供应商
	 * @return
	 */
	public String open() throws Exception {
		System.out.println("----------------启用供应商-----------------");
		RailMfrsOrg railMfrsOrg =  t140101BO.getByCode(String.valueOf(getId()));
		if(railMfrsOrg == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该供应商");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}
		railMfrsOrg.setEnableStatus("0");
		railMfrsOrg.setUpdUser(operator.getOprId());
		railMfrsOrg.setUpdDate(new Date());
		   String rcode =t140101BO.update(railMfrsOrg);
			
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(rcode);
	        printWriter.flush();
	        printWriter.close();
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 停用供应商
	 * @return
	 */
	public String close() throws Exception {
		System.out.println("----------------停用供应商-----------------");
		RailMfrsOrg railMfrsOrg =  t140101BO.getByCode(String.valueOf(getId()));
		if(railMfrsOrg == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该供应商");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}
		railMfrsOrg.setEnableStatus("1");
		railMfrsOrg.setUpdUser(operator.getOprId());
		railMfrsOrg.setUpdDate(new Date());
		   String rcode =t140101BO.update(railMfrsOrg);
			
		   PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(rcode);
	        printWriter.flush();
	        printWriter.close();
		return Constants.SUCCESS_CODE;
	}

	public String getMfrsOrgName() {
		return mfrsOrgName;
	}

	public void setMfrsOrgName(String mfrsOrgName) {
		this.mfrsOrgName = mfrsOrgName;
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
	
	public String getMfrsAddress() {
		return mfrsAddress;
	}
	public void setMfrsAddress(String mfrsAddress) {
		this.mfrsAddress = mfrsAddress;
	}
	public String getMfrsPic() {
		return mfrsPic;
	}
	public void setMfrsPic(String mfrsPic) {
		this.mfrsPic = mfrsPic;
	}
	public String getMfrsTel() {
		return mfrsTel;
	}
	public void setMfrsTel(String mfrsTel) {
		this.mfrsTel = mfrsTel;
	}
	public String getMfrsFax() {
		return mfrsFax;
	}
	public void setMfrsFax(String mfrsFax) {
		this.mfrsFax = mfrsFax;
	}

	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

}
