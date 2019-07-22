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
import com.rail.bo.parameter.T140100BO;
import com.rail.po.access.RailAccessEquipType;
import com.rail.po.org.RailConstOrg;

/**
 * 施工单位维护
 * @author lipeng
 *
 */
public class T140100Action extends BaseAction {

	private static final long serialVersionUID = 1L;
	private long id;
	private String costOrgName;
	private String delStatus;
	private String addUser;
	private Date addDate;
	private String updUser;
	private Date updDate;
	private String enableStatus;
	private long formOrg;
	
	//施工单位信息维护
	private T140100BO t140100BO = (T140100BO) ContextUtil.getBean("T140100BO");
	public T140100BO getT140100BO() {
		return t140100BO;
	}
	public void setT140100BO(T140100BO t140100bo) {
		t140100BO = t140100bo;
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
            log("操作员：" + operator.getOprId() + "，对施工单位进行：" + getMethod() + "，失败，失败原因为：" + e);
        }
        return rspCode;
	}
	
	/**
	 * 获得单个施工单位信息
	 * @Description: TODO
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public String getData() {
		System.out.println("----------------获得单个施工单位信息-----------------");
        try {
        	RailConstOrg railConstOrg = t140100BO.getByCode(String.valueOf(getId()));
            Map<String, Object> midMap = new HashMap<String, Object>();
            midMap.put("success", true);
            midMap.put("msg", railConstOrg);
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
	 * 新增施工单位
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception{
		System.out.println("----------------新增施工单位-----------------");
		Map<String, String> params = new HashMap<String, String>();
		params.put("COST_ORG_NAME", getCostOrgName());
		List<RailConstOrg> rcoInfos = t140100BO.getByParam(params);
		if(rcoInfos != null && rcoInfos.size() > 0){
			Map<String, Object> errorMap = new HashMap<String, Object>();
			errorMap.put("success", false);
			errorMap.put("msg", "施工单位名称已存在！");
            String str = JSONObject.toJSONString(errorMap);
            PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
            printWriter.write(str);
            printWriter.flush();
            printWriter.close();
            return null;
		}
		RailConstOrg railConstOrg = new RailConstOrg();
		railConstOrg.setCostOrgName(getCostOrgName());
		railConstOrg.setDelStatus("0");
		railConstOrg.setEnableStatus("0");
		railConstOrg.setAddUser(operator.getOprId());
		railConstOrg.setAddDate(new Date());
		railConstOrg.setUpdUser(operator.getOprId());
		railConstOrg.setUpdDate(new Date());
		railConstOrg.setFormOrg(getFormOrg());
		
		String rCode = t140100BO.add(railConstOrg);
		String msg = "添加施工单位成功";
		boolean success = true;
		if(!rCode.equals(Constants.SUCCESS_CODE)){
			msg = "添加施工单位失败";
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
		log("添加施工单位成功。操作员编号：" + operator.getOprId());
        return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 修改施工单位
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception{
		System.out.println("----------------修改施工单位-----------------");
		RailConstOrg railConstOrg = t140100BO.getByCode(String.valueOf(getId()));
		if(railConstOrg == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该施工单位");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}else{
			if(!getCostOrgName().equals(railConstOrg.getCostOrgName())){
				Map<String, String> params = new HashMap<String, String>();
				params.put("COST_ORG_NAME", getCostOrgName());
				List<RailConstOrg> rcoInfos = t140100BO.getByParam(params);
				if(rcoInfos != null && rcoInfos.size() > 0){
					Map<String, Object> errorMap = new HashMap<String, Object>();
					errorMap.put("success", false);
					errorMap.put("msg", "施工单位名称已存在！");
		            String str = JSONObject.toJSONString(errorMap);
		            PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
		            printWriter.write(str);
		            printWriter.flush();
		            printWriter.close();
		            return Constants.FAILURE_CODE;
		    }
		  }
		}
	
		railConstOrg.setCostOrgName(getCostOrgName());
		railConstOrg.setFormOrg(getFormOrg());
		railConstOrg.setEnableStatus(getEnableStatus());
		railConstOrg.setUpdUser(operator.getOprId());
		railConstOrg.setUpdDate(new Date());
        String rcode =t140100BO.update(railConstOrg);
		
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
        
		log("更新施工单位信息成功。操作员编号：" + operator.getOprId());
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 删除施工单位
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception{
		System.out.println("----------------删除施工单位-----------------");
		RailConstOrg railConstOrg =  t140100BO.getByCode(String.valueOf(getId()));
		if(railConstOrg == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该施工单位");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}
		railConstOrg.setDelStatus("1");
		railConstOrg.setUpdUser(operator.getOprId());
		railConstOrg.setUpdDate(new Date());
		   String rcode =t140100BO.update(railConstOrg);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(rcode);
	        printWriter.flush();
	        printWriter.close();
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 启用施工单位
	 * @return
	 */
	public String open() throws Exception {
		System.out.println("----------------启用施工单位-----------------");
		RailConstOrg railConstOrg =  t140100BO.getByCode(String.valueOf(getId()));
		if(railConstOrg == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该施工单位");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}
		railConstOrg.setEnableStatus("0");
		railConstOrg.setUpdUser(operator.getOprId());
		railConstOrg.setUpdDate(new Date());
		   String rcode =t140100BO.update(railConstOrg);
			
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(rcode);
	        printWriter.flush();
	        printWriter.close();
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 停用施工单位
	 * @return
	 */
	public String close() throws Exception {
		System.out.println("----------------停用施工单位-----------------");
		RailConstOrg railConstOrg =  t140100BO.getByCode(String.valueOf(getId()));
		if(railConstOrg == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该施工单位");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}
		railConstOrg.setEnableStatus("1");
		railConstOrg.setUpdUser(operator.getOprId());
		railConstOrg.setUpdDate(new Date());
		   String rcode =t140100BO.update(railConstOrg);
			
		   PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(rcode);
	        printWriter.flush();
	        printWriter.close();
		return Constants.SUCCESS_CODE;
	}

	public String getCostOrgName() {
		return costOrgName;
	}

	public void setCostOrgName(String costOrgName) {
		this.costOrgName = costOrgName;
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
