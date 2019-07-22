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
import com.rail.bo.parameter.T140102BO;
import com.rail.po.access.RailAccessEquipType;
import com.rail.po.base.RailArea;
/**
 *仓库区管理管理
 * @author qiufulong
 *
 */
public class T140102Action extends BaseAction {

	private static final long serialVersionUID = 1L;
	private long id;
	private String whseAreaName;
	private String delStatus;
	private String addUser;
	private Date addDate;
	private String updUser;
	private Date updDate;
	private String enableStatus;
	private long formOrg;
	
	//仓库区信息维护
	private T140102BO t140102BO = (T140102BO) ContextUtil.getBean("T140102BO");
	public T140102BO getT140102BO() {
		return t140102BO;
	}
	public void setT140102BO(T140102BO t140102bo) {
		t140102BO = t140102bo;
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
            log("操作员：" + operator.getOprId() + "，对仓库区进行：" + getMethod() + "，失败，失败原因为：" + e);
        }
        return rspCode;
	}
	
	/**
	 * 获得单个仓库区信息
	 * @Description: TODO
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public String getData() {
		System.out.println("----------------获得单个仓库区-----------------");
        try {
        	RailArea railArea = t140102BO.getByCode(String.valueOf(getId()));
            Map<String, Object> midMap = new HashMap<String, Object>();
            midMap.put("success", true);
            midMap.put("msg", railArea);
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
	 * 新增仓库区
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception{
		System.out.println("----------------新增仓库区-----------------");
		Map<String, String> params = new HashMap<String, String>();
		params.put("WHSE_AREA_NAME", getWhseAreaName());
		List<RailArea> rcoInfos = t140102BO.getByParam(params);
		if(rcoInfos != null && rcoInfos.size() > 0){
			Map<String, Object> errorMap = new HashMap<String, Object>();
			errorMap.put("success", false);
			errorMap.put("msg", "仓库区名称已存在！");
            String str = JSONObject.toJSONString(errorMap);
            PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
            printWriter.write(str);
            printWriter.flush();
            printWriter.close();
            return null;
		}
		RailArea railArea = new RailArea();
		railArea.setWhseAreaName(getWhseAreaName());
		railArea.setDelStatus("0");
		railArea.setEnableStatus("0");
		railArea.setAddUser(operator.getOprId());
		railArea.setAddDate(new Date());
		railArea.setUpdUser(operator.getOprId());
		railArea.setUpdDate(new Date());
		
		String rCode = t140102BO.add(railArea);
		String msg = "添加仓库区成功";
		boolean success = true;
		if(!rCode.equals(Constants.SUCCESS_CODE)){
			msg = "添加仓库区失败";
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
		log("添加仓库区成功。操作员编号：" + operator.getOprId());
        return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 修改仓库区
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception{
		System.out.println("----------------修改仓库区-----------------");
		RailArea railArea = t140102BO.getByCode(String.valueOf(getId()));
		if(railArea == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该仓库区");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}else{
			if(!getWhseAreaName().equals(railArea.getWhseAreaName())){
				Map<String, String> params = new HashMap<String, String>();
				params.put("WHSE_AREA_NAME", getWhseAreaName());
				List<RailArea> rcoInfos = t140102BO.getByParam(params);
				if(rcoInfos != null && rcoInfos.size() > 0){
					Map<String, Object> errorMap = new HashMap<String, Object>();
					errorMap.put("success", false);
					errorMap.put("msg", "仓库区名称已存在！");
		            String str = JSONObject.toJSONString(errorMap);
		            PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
		            printWriter.write(str);
		            printWriter.flush();
		            printWriter.close();
		            return null;
				}
		  }
		}
		
		railArea.setWhseAreaName(getWhseAreaName());
		railArea.setEnableStatus(getEnableStatus());
		railArea.setUpdUser(operator.getOprId());
		railArea.setUpdDate(new Date());
        String rcode =t140102BO.update(railArea);
		
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
        
		log("更新仓库区信息成功。操作员编号：" + operator.getOprId());
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 删除仓库区
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception{
		System.out.println("----------------删除仓库区-----------------");
		RailArea railArea =  t140102BO.getByCode(String.valueOf(getId()));
		if(railArea == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该仓库区");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}
		railArea.setDelStatus("1");
		railArea.setUpdUser(operator.getOprId());
		railArea.setUpdDate(new Date());
		   String rcode =t140102BO.update(railArea);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(rcode);
	        printWriter.flush();
	        printWriter.close();
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 启用仓库区
	 * @return
	 */
	public String open() throws Exception {
		System.out.println("----------------启用仓库区-----------------");
		RailArea railArea =  t140102BO.getByCode(String.valueOf(getId()));
		if(railArea == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该仓库区");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}
		railArea.setEnableStatus("0");
		railArea.setUpdUser(operator.getOprId());
		railArea.setUpdDate(new Date());
		   String rcode =t140102BO.update(railArea);
			
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(rcode);
	        printWriter.flush();
	        printWriter.close();
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 停用仓库区
	 * @return
	 */
	public String close() throws Exception {
		System.out.println("----------------停用仓库区-----------------");
		RailArea railArea =  t140102BO.getByCode(String.valueOf(getId()));
		if(railArea == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该仓库区");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}
		railArea.setEnableStatus("1");
		railArea.setUpdUser(operator.getOprId());
		railArea.setUpdDate(new Date());
		   String rcode =t140102BO.update(railArea);
			
		   PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(rcode);
	        printWriter.flush();
	        printWriter.close();
		return Constants.SUCCESS_CODE;
	}

	public String getWhseAreaName() {
		return whseAreaName;
	}

	public void setWhseAreaName(String whseAreaName) {
		this.whseAreaName = whseAreaName;
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
