package com.rail.struts.work.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.ContextUtil;
import com.rail.bo.work.T130403BO;
import com.rail.po.access.RailAccessEquipType;
import com.rail.po.base.RailTeam;
import com.rail.po.work.RailTeamEmployee;
import com.rail.po.work.RailWorkEmployee;

/**
 * 班组维护
 * @author qiufulon
 *
 */
public class T130403Action extends BaseAction {

	private static final long serialVersionUID = 1L;
	private long id;
	private String workTeamName;
	private String delStatus;
	private String addUser;
	private Date addDate;
	private String updUser;
	private Date updDate;
	private String enableStatus;
	private String teamId;
	private String em;
	private String dept;

	public String getEm() {
		return em;
	}
	public void setEm(String em) {
		this.em = em;
	}
	
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	//班组信息维护
	private T130403BO t130403BO = (T130403BO) ContextUtil.getBean("T130403BO");
	public T130403BO getT130403BO() {
		return t130403BO;
	}
	public void setT130403BO(T130403BO t130403bo) {
		t130403BO = t130403bo;
	}
		
	@Override
	protected String subExecute() throws Exception {
		System.out.println(getMethod()+"  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		try {
			 if ("open".equals(getMethod())) {
	                rspCode = open();
	            }else if("close".equals(getMethod())){
	            	rspCode = close();
	            }else if("delTeamEmployee".equals(getMethod())){
	            	rspCode = delTeamEmployee();
	            }else if("edit".equals(getMethod())){
	            	rspCode = edit();
	            }else if("add".equals(getMethod())){
	            	rspCode = add();
	            }else if("delete".equals(getMethod())){
	            	rspCode = delete();
	            }else if ("addTeamEmployee".equals(getMethod())) {
	                rspCode = addTeamEmployee();
	            }
        } catch (Exception e) {
        	//e.printStackTrace();
            log("操作员：" + operator.getOprId() + "，对班组进行：" + getMethod() + "，失败，失败原因为：" + e);
        }
        return rspCode;
	}
	
	public String delTeamEmployee(){
		try {
			 t130403BO.delTeamEmployeesByTeamId(getTeamId());
			 Map<String, Object> midMap = new HashMap<String, Object>();
			 midMap.put("success", true);
			 midMap.put("msg", "删除成功");
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
	
	public String addTeamEmployee() {
		 try {
			 
			 JSONArray emsArray = JSONArray.parseArray(getEm());
			 List<RailTeamEmployee> rwes = new ArrayList<RailTeamEmployee>();
			 for(Object obj:emsArray){
				 JSONObject jobj = JSONObject.parseObject(obj.toString());
				 System.out.println(jobj.get("teamId")+"><"+jobj.get("employeeCode"));
				 t130403BO.delTeamEmployeesByTeamId(StringUtil.toString(jobj.get("teamId")));
				 RailTeamEmployee rwe = new RailTeamEmployee();
				 rwe.setEmployeeCode(jobj.getString("employeeCode"));
				 rwe.setWorkTeam(Long.valueOf(jobj.getString("teamId")));
				 
				 rwe.setAddDate(new Date());
				 rwe.setAddUser(operator.getOprId());
				 rwes.add(rwe);
			 }
			 String msg = t130403BO.addTeamEmployees(rwes);
			 Map<String, Object> midMap = new HashMap<String, Object>();
			 midMap.put("success", true);
			 midMap.put("msg", msg);
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
	 * 获得单个班组信息
	 * @Description: TODO
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public String getData() {
		System.out.println("----------------获得单个班组信息-----------------");
        try {
        	RailTeam railTeam = t130403BO.getByCode(String.valueOf(getId()));
            Map<String, Object> midMap = new HashMap<String, Object>();
            midMap.put("success", true);
            midMap.put("msg", railTeam);
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
	 * 新增班组
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception{
		System.out.println("----------------新增班组-----------------");
		Map<String, String> params = new HashMap<String, String>();
		params.put("WORK_TEAM_NAME", getWorkTeamName());
		List<RailTeam> rcoInfos = t130403BO.getByParam(params);
		if(rcoInfos != null && rcoInfos.size() > 0){
			Map<String, Object> errorMap = new HashMap<String, Object>();
			errorMap.put("success", false);
			errorMap.put("msg", "班组名称已存在！");
            String str = JSONObject.toJSONString(errorMap);
            PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
            printWriter.write(str);
            printWriter.flush();
            printWriter.close();
            return null;
		}
		RailTeam railTeam = new RailTeam();
		railTeam.setWorkTeamName(getWorkTeamName());
		railTeam.setDept(getDept());
		railTeam.setDelStatus("0");
		railTeam.setEnableStatus("0");
		railTeam.setAddUser(operator.getOprId());
		railTeam.setAddDate(new Date());
		railTeam.setUpdUser(operator.getOprId());
		railTeam.setUpdDate(new Date());
		
		String rCode = t130403BO.add(railTeam);
		String msg = "添加班组成功";
		boolean success = true;
		if(!rCode.equals(Constants.SUCCESS_CODE)){
			msg = "添加班组失败";
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
		log("添加班组成功。操作员编号：" + operator.getOprId());
        return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 修改班组
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception{
		System.out.println("----------------修改班组-----------------");
		RailTeam railTeam = t130403BO.getByCode(String.valueOf(getId()));
		if(railTeam == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该班组");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}else{
			if(!getWorkTeamName().equals(railTeam.getWorkTeamName())){
				Map<String, String> params = new HashMap<String, String>();
				params.put("WORK_TEAM_NAME", getWorkTeamName());
				List<RailTeam> rcoInfos = t130403BO.getByParam(params);
				if(rcoInfos != null && rcoInfos.size() > 0){
					Map<String, Object> errorMap = new HashMap<String, Object>();
					errorMap.put("success", false);
					errorMap.put("msg", "班组名称已存在！");
		            String str = JSONObject.toJSONString(errorMap);
		            PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
		            printWriter.write(str);
		            printWriter.flush();
		            printWriter.close();
		            return Constants.FAILURE_CODE;
				}
		  }
		}
		railTeam.setWorkTeamName(getWorkTeamName());
		railTeam.setDept(getDept());
		railTeam.setEnableStatus(getEnableStatus());
		railTeam.setUpdUser(operator.getOprId());
		railTeam.setUpdDate(new Date());
        String rcode =t130403BO.update(railTeam);
		
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
        
		log("更新班组信息成功。操作员编号：" + operator.getOprId());
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 删除班组
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception{
		System.out.println("----------------删除班组-----------------");
		RailTeam railTeam =  t130403BO.getByCode(String.valueOf(getId()));
		if(railTeam == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该班组");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}
		railTeam.setDelStatus("1");
		railTeam.setUpdUser(operator.getOprId());
		railTeam.setUpdDate(new Date());
		   String rcode =t130403BO.update(railTeam);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(rcode);
	        printWriter.flush();
	        printWriter.close();
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 启用班组
	 * @return
	 */
	public String open() throws Exception {
		System.out.println("----------------启用班组-----------------");
		RailTeam railTeam =  t130403BO.getByCode(String.valueOf(getId()));
		if(railTeam == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该班组");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}
		railTeam.setEnableStatus("0");
		railTeam.setUpdUser(operator.getOprId());
		railTeam.setUpdDate(new Date());
		   String rcode =t130403BO.update(railTeam);
			
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(rcode);
	        printWriter.flush();
	        printWriter.close();
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 停用班组
	 * @return
	 */
	public String close() throws Exception {
		System.out.println("----------------停用班组-----------------");
		RailTeam railTeam =  t130403BO.getByCode(String.valueOf(getId()));
		if(railTeam == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该班组");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}
		railTeam.setEnableStatus("1");
		railTeam.setUpdUser(operator.getOprId());
		railTeam.setUpdDate(new Date());
		   String rcode =t130403BO.update(railTeam);
			
		   PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(rcode);
	        printWriter.flush();
	        printWriter.close();
		return Constants.SUCCESS_CODE;
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

	
	public String getWorkTeamName() {
		return workTeamName;
	}
	public void setWorkTeamName(String workTeamName) {
		this.workTeamName = workTeamName;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}

}
