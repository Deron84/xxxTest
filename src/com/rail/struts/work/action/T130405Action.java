package com.rail.struts.work.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.components.Else;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.ContextUtil;
import com.rail.bo.work.T130403BO;
import com.rail.po.access.RailAccessEquipType;
import com.rail.po.base.RailPatrol;
import com.rail.po.base.RailTeam;
import com.rail.po.work.RailTeamEmployee;
import com.rail.po.work.RailWorkEmployee;

/**
 * 班组维护
 * @author qiufulon
 *
 */
public class T130405Action extends BaseAction {

	private static final long serialVersionUID = 1L;
	private long id;
	private String patrolName;

	
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
			if ("add".equals(getMethod())) {
				rspCode = addPatrol();
			}else if("del".equals(getMethod())){
				rspCode = delete();
			}else if("edit".equals(getMethod())){
				rspCode = edit();
			}
        } catch (Exception e) {
        	//e.printStackTrace();
            log("操作员：" + operator.getOprId() + "，对巡护中队进行：" + getMethod() + "，失败，失败原因为：" + e);
        }
        return rspCode;
	}
	
	/**
	 * 获得单个巡护中队信息
	 * @Description: TODO
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public String getData() {
		System.out.println("----------------获得单个巡护中队信息-----------------");
        try {
        	RailPatrol railPatrol = t130403BO.getPatrolById(getId());
            Map<String, Object> midMap = new HashMap<String, Object>();
            midMap.put("success", true);
            midMap.put("msg", railPatrol);
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
	 * 新增巡护中队
	 * @return
	 * @throws Exception
	 */
	public String addPatrol() throws Exception{
		System.out.println("----------------新增巡护中队-----------------");
		Map<String, String> params = new HashMap<String, String>();
		params.put("PATROL_NAME", getPatrolName());
		List<RailPatrol> rcoInfos = t130403BO.getPatrolByParam(params);
		if(rcoInfos != null && rcoInfos.size() > 0){
			Map<String, Object> errorMap = new HashMap<String, Object>();
			errorMap.put("success", false);
			errorMap.put("msg", "巡护中队名称已存在！");
            String str = JSONObject.toJSONString(errorMap);
            PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
            printWriter.write(str);
            printWriter.flush();
            printWriter.close();
            return null;
		}
		RailPatrol railPatrol = new RailPatrol();
		railPatrol.setPatrolName(getPatrolName());
		railPatrol.setAddDate(new Date());
		railPatrol.setAddUser(operator.getOprId());
		railPatrol.setDelStatus("0");
		railPatrol.setEnableStatus("0");
		
		String rCode = t130403BO.addPatrol(railPatrol);
		String msg = "添加巡护中队成功";
		boolean success = true;
		if(!rCode.equals(Constants.SUCCESS_CODE)){
			msg = "添加巡护中队失败";
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
		log("添加巡护中队成功。操作员编号：" + operator.getOprId());
        return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 修改班组
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception{
		System.out.println("----------------修改巡护中队-----------------");
		RailPatrol railPatrol = t130403BO.getPatrolById(getId());
		if(railPatrol == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该巡护中队");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}else{
			if(!getPatrolName().equals(railPatrol.getPatrolName())){
				Map<String, String> params = new HashMap<String, String>();
				params.put("PATROL_NAME", getPatrolName());
				List<RailPatrol> rcoInfos = t130403BO.getPatrolByParam(params);
				if(rcoInfos != null && rcoInfos.size() > 0){
					Map<String, Object> errorMap = new HashMap<String, Object>();
					errorMap.put("success", false);
					errorMap.put("msg", "巡护中队名称已存在！");
		            String str = JSONObject.toJSONString(errorMap);
		            PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
		            printWriter.write(str);
		            printWriter.flush();
		            printWriter.close();
		            return Constants.FAILURE_CODE;
				}
		  }
		}
		railPatrol.setPatrolName(getPatrolName());
		railPatrol.setUpdUser(operator.getOprId());
		railPatrol.setUpdDate(new Date());
        String rcode =t130403BO.updatePatrol(railPatrol);
		
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
        
		log("更新巡护中队信息成功。操作员编号：" + operator.getOprId());
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 删除巡护中队
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception{
		System.out.println("----------------删除巡护中队-----------------");
		RailPatrol railPatrol =  t130403BO.getPatrolById(getId());
		if(railPatrol == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该巡护中队");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}
		railPatrol.setDelStatus("1");
		railPatrol.setUpdUser(operator.getOprId());
		railPatrol.setUpdDate(new Date());
		String rcode =t130403BO.updatePatrol(railPatrol);
		PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
		printWriter.write(rcode);
		printWriter.flush();
		printWriter.close();
		return Constants.SUCCESS_CODE;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPatrolName() {
		return patrolName;
	}
	public void setPatrolName(String patrolName) {
		this.patrolName = patrolName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
