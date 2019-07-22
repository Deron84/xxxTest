package com.rail.struts.work.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.validator.Var;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.components.Else;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.common.StringUtil;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.ContextUtil;
import com.rail.bo.enums.ApiSendMsgFlagEnums;
import com.rail.bo.tools.TSendMsgSocketBO;
import com.rail.bo.work.T130100BO;
import com.rail.po.tool.RailToolInfo;
import com.rail.po.work.RailWorkInfo;
import com.rail.po.work.RailWorkTool;

public class T130103Action extends BaseAction {
	private static final long serialVersionUID = 1L;
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	private String workCode;
	private String auditStatus;
	private String auditMsg;
	private String dataList;
	private String nameID;
	private String tools;

	public String getTools() {
		return tools;
	}
	public void setTools(String tools) {
		this.tools = tools;
	}
	private T130100BO t130100BO = (T130100BO) ContextUtil.getBean("T130100BO");
	
	public T130100BO getT130100BO() {
		return t130100BO;
	}
	public void setT130100BO(T130100BO t130100bo) {
		t130100BO = t130100bo;
	}

	@Override
	protected String subExecute() throws Exception {
		try {
			if ("addWorkTool".equals(getMethod())) {
				rspCode = addWorkTool();
			}else if("delWorkTool".equals(getMethod())){
				rspCode = delWorkTool();
			}else if ("update".equals(getMethod())) {
               rspCode = auditWorkData();
            }else if("updateWorkTool".equals(getMethod())){
            	rspCode = updateWorkTool();
            }else if("getWhseToolNum".equals(getMethod())){
            	rspCode = getWhseToolNum();
            }
        } catch (Exception e) {
            log("操作员：" + operator.getOprId() + "，对工单进行：" + getMethod() + "，失败，失败原因为：" + e);
        }
        return rspCode;
	}
	public String delWorkTool(){
		try {
			 t130100BO.delWorkToolByWorkCode(getWorkCode());
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
	public String addWorkTool() {
		 try {
			 
			 JSONArray tools = JSONArray.parseArray(getTools());
			 List<RailWorkTool> rwts = new ArrayList<RailWorkTool>();
			 for(Object obj:tools){
				JSONObject jobj = JSONObject.parseObject(obj.toString());
				String toolCode = jobj.getString("toolCode");
				RailToolInfo railToolInfo = t130100BO.getToolInfoByToolCode(toolCode);
				t130100BO.delWorkToolByWorkCode(StringUtil.toString(jobj.get("workCode")));
				RailWorkTool rwt = new RailWorkTool();
				rwt.setToolCode(jobj.getString("toolCode"));
				rwt.setWorkCode(jobj.getString("workCode"));
				rwt.setToolName(railToolInfo.getToolName());
				rwt.setUpdDate(new Date());
				rwt.setUpdUser(operator.getOprId());
				rwt.setAddDate(new Date());
				rwt.setAddUser(operator.getOprId());
				rwt.setDelStatus("0");
				rwt.setInfoSign("0");
				rwts.add(rwt);
			}
			String msg = t130100BO.addWorkTool(rwts);
			 
			Map<String,String> paramMap = new HashMap<String, String>();
			paramMap.put("WORK_CODE", getWorkCode());
			List<RailWorkInfo> list = t130100BO.getByParam(paramMap);
			RailWorkInfo railWorkInfo = new RailWorkInfo();
			if (list!=null&&!list.isEmpty()) {
				railWorkInfo = list.get(0);
				railWorkInfo.setAuditStatus("0");
				t130100BO.updateRailWorkInfo(railWorkInfo);	
			}
				
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
	private String getWhseToolNum() throws IOException{
		RailWorkInfo railWorkInfo = t130100BO.getWorkInfoByCode(getWorkCode());
		String whseCode = railWorkInfo.getWhseCode();
		int toolNum = t130100BO.getWhseToolNum(whseCode,getNameID());
    	Map<String, Object> midMap = new HashMap<String, Object>();
        midMap.put("success", true);
        midMap.put("msg", toolNum);
		String str = JSONObject.toJSONString(midMap);
		PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
		printWriter.write(str);
		printWriter.flush();
		printWriter.close();
		return "success";
	}
	private String updateWorkTool() throws IOException{
		String datasStr=getDataList();
		JSONArray jsons = JSONArray.parseArray(datasStr);
//		System.out.println(jsons+" >>>>>>>>>>>>>>>>");
		List<RailWorkTool> rwts = new ArrayList<RailWorkTool>();
		for(int i = 0;i<jsons.size();i++){
			JSONObject jsonObj = jsons.getJSONObject(i);
			String toolCode = jsonObj.getString("toolNameId");
			String workCode = jsonObj.getString("workCode");
			RailToolInfo railToolInfo = t130100BO.getToolInfoByToolCode(toolCode);
			t130100BO.delWorkToolByWorkCode(workCode);
			int toolNum = 1;
			for(int y=0;y<toolNum;y++){
				RailWorkTool railWorkTool = new RailWorkTool();
				railWorkTool.setAddDate(new Date());
				railWorkTool.setAddUser(operator.getOprId());
				railWorkTool.setDelStatus("0");
				railWorkTool.setInfoSign("0");
				railWorkTool.setToolCode(toolCode);
				railWorkTool.setToolName(railToolInfo.getToolName());
				railWorkTool.setWorkCode(workCode);
				railWorkTool.setUpdDate(new Date());
				railWorkTool.setUpdUser(operator.getOprId());
				rwts.add(railWorkTool);
			}
		}
		String rs=t130100BO.addWorkTool(rwts);
		boolean success = false;
		String msg = "操作失败";
		if("00".equals(rs)){
			success = true;
			msg = "操作成功";
		}
    	Map<String, Object> midMap = new HashMap<String, Object>();
        midMap.put("success", success);
        midMap.put("msg", msg);
		String str = JSONObject.toJSONString(midMap);
		PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
		printWriter.write(str);
		printWriter.flush();
		printWriter.close();
		return "success";
	}
	/**
	 * 查询工单信息根据工单编码
	 * @Description: TODO
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月27日
	 */
	public String getWorkInfoData() throws Exception {
		 try {
			 RailWorkInfo railWorkInfo = t130100BO.getWorkInfoByCode(getWorkCode());
			 Map<String, Object> midMap = new HashMap<String, Object>();
			 midMap.put("success", true);
			 midMap.put("msg", railWorkInfo);
			 String str = JSONObject.toJSONString(midMap);
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
	 * 审核操作
	 * @Description: TODO
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月27日
	 */
	private String auditWorkData() throws Exception {
		String msg = "操作失败";
		boolean success = false;
		try {
			Map<String,String> paramMap = new HashMap<String, String>();
			paramMap.put("WORK_CODE", getWorkCode());
			List<RailWorkInfo> list = t130100BO.getByParam(paramMap);
			RailWorkInfo railWorkInfo = new RailWorkInfo();
			if (list!=null&&!list.isEmpty()) {
				railWorkInfo = list.get(0);
				railWorkInfo.setAuditStatus(getAuditStatus());
				railWorkInfo.setAuditMsg(getAuditMsg());
				railWorkInfo.setAuditDate(new Date());
				railWorkInfo.setAuditUser(operator.getOprId());
				String result = t130100BO.updateRailWorkInfo(railWorkInfo);
				if(result.equals(Constants.SUCCESS_CODE)){
					if("1".equals(getAuditStatus())){
						TSendMsgSocketBO tSendMsgSocketBO = (TSendMsgSocketBO) ContextUtil.getBean("TSendMsgSocketBO");
						int flag  = ApiSendMsgFlagEnums.TOOL.getCode();
						//发送信息 更新app工单列表
						tSendMsgSocketBO.sendSocketMsg(railWorkInfo.getDept(), railWorkInfo.getId(), ApiSendMsgFlagEnums.WORK_ORDER.getCode());
					}else{
						t130100BO.delWorkEmployeesByWorkCode(getWorkCode());
						t130100BO.delWorkToolByWorkCode(getWorkCode());
					}
					success=true;
					msg = "操作成功";
				}else{
					msg = "操作失败";
					success = false;
				}
			}else{
				msg = "该记录不存在！";
				success = false;
			}
			
		} catch (Exception e) {
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
	public String getWorkCode() {
		return workCode;
	}
	public void setWorkCode(String workCode) {
		this.workCode = workCode;
	}
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	public String getAuditMsg() {
		return auditMsg;
	}
	public void setAuditMsg(String auditMsg) {
		this.auditMsg = auditMsg;
	}
	public String getDataList() {
		return dataList;
	}
	public void setDataList(String dataList) {
		this.dataList = dataList;
	}
	public String getNameID() {
		return nameID;
	}
	public void setNameID(String nameID) {
		this.nameID = nameID;
	}
}