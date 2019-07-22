package com.rail.struts.warehouse.action;

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
import com.rail.bo.warehouse.T100300BO;
import com.rail.po.warehouse.RailWhseToolWarn;
/**
 *仓库工具预警阈值管理
 * @author qiufulong
 *
 */
public class T100300Action extends BaseAction {

	private static final long serialVersionUID = 1L;
	private long id;
	private String whseCode;
	private String toolName;
	private String lowerThreshold;
	private String  delStatus;
	private Date addDate;
	private String addUser;
	private String note1;
	private String note2;
	private String note3;
	
	//工具预警阈值信息维护
	private T100300BO t100300BO = (T100300BO) ContextUtil.getBean("T100300BO");
	public T100300BO getT100300BO() {
		return t100300BO;
	}
	public void setT100300BO(T100300BO t100300bo) {
		t100300BO = t100300bo;
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
            log("操作员：" + operator.getOprId() + "，对工具预警阈值进行：" + getMethod() + "，失败，失败原因为：" + e);
        }
        return rspCode;
	}
	
	/**
	 * 获得单个工具预警阈值信息
	 * @Description: TODO
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public String getData() {
		System.out.println("----------------获得单个工具预警阈值信息-----------------");
        try {
        	RailWhseToolWarn railWhseToolWarn = t100300BO.getByCode(String.valueOf(getId()));
        	System.out.println("根据参数查询工具预警阈值railAccessEquipInfo:" + railWhseToolWarn);
            Map<String, Object> midMap = new HashMap<String, Object>();
            midMap.put("success", true);
            midMap.put("msg", railWhseToolWarn);
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
	 * 新增工具预警阈值
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception{
		System.out.println("----------------新增工具预警阈值-----------------");
		Map<String, String> params = new HashMap<String, String>();
		params.put("WHSE_CODE", getWhseCode());
		params.put("TOOL_NAME", getToolName());
		List<RailWhseToolWarn> rcoInfos = t100300BO.getByParam(params);
		if(rcoInfos != null && rcoInfos.size() > 0){
			Map<String, Object> errorMap = new HashMap<String, Object>();
			errorMap.put("success", false);
			errorMap.put("msg", "工具预警阈值名称已存在！");
            String str = JSONObject.toJSONString(errorMap);
            PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
            printWriter.write(str);
            printWriter.flush();
            printWriter.close();
            return null;
		}
		RailWhseToolWarn railWhseToolWarn = new RailWhseToolWarn();
		railWhseToolWarn.setWhseCode(getWhseCode());
		railWhseToolWarn.setToolName(getToolName());
		railWhseToolWarn.setLowerThreshold(getLowerThreshold());
		railWhseToolWarn.setDelStatus("0");
		railWhseToolWarn.setAddUser(operator.getOprId());
		railWhseToolWarn.setAddDate(new Date());
		
		String rCode = t100300BO.add(railWhseToolWarn);
		String msg = "添加工具预警阈值成功";
		boolean success = true;
		if(!rCode.equals(Constants.SUCCESS_CODE)){
			msg = "添加工具预警阈值失败";
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
		log("添加工具预警阈值成功。操作员编号：" + operator.getOprId());
        return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 修改工具预警阈值
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception{
		System.out.println("----------------修改工具预警阈值-----------------");
		RailWhseToolWarn railWhseToolWarn = t100300BO.getByCode(String.valueOf(getId()));
		if(railWhseToolWarn == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该工具预警阈值");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}else{
			if(!(getWhseCode().equals(railWhseToolWarn.getWhseCode())&&getToolName().equals(railWhseToolWarn.getToolName()))){
			Map<String, String> params = new HashMap<String, String>();
			params.put("WHSE_CODE", getWhseCode());
			params.put("TOOL_NAME", getToolName());
			List<RailWhseToolWarn> rcoInfos = t100300BO.getByParam(params);
			if(rcoInfos != null && rcoInfos.size() > 0){
				Map<String, Object> errorMap = new HashMap<String, Object>();
				errorMap.put("success", false);
				errorMap.put("msg", "工具预警阈值名称已存在！");
	            String str = JSONObject.toJSONString(errorMap);
	            PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	            printWriter.write(str);
	            printWriter.flush();
	            printWriter.close();
	            return Constants.FAILURE_CODE;
			}
		  }
		}
		railWhseToolWarn.setWhseCode(getWhseCode());
		railWhseToolWarn.setToolName(getToolName());
		railWhseToolWarn.setLowerThreshold(getLowerThreshold());
        String rcode =t100300BO.update(railWhseToolWarn);
		
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
        
		log("更新工具预警阈值信息成功。操作员编号：" + operator.getOprId());
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 删除工具预警阈值
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception{
		System.out.println("----------------删除工具预警阈值-----------------");
		RailWhseToolWarn railWhseToolWarn =  t100300BO.getByCode(String.valueOf(getId()));
		if(railWhseToolWarn == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该工具预警阈值");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}
		railWhseToolWarn.setDelStatus("1");
		   String rcode =t100300BO.update(railWhseToolWarn);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(rcode);
	        printWriter.flush();
	        printWriter.close();
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 启用工具预警阈值
	 * @return
	 */
	public String open() throws Exception {
		System.out.println("----------------启用工具预警阈值-----------------");
		RailWhseToolWarn railWhseToolWarn =  t100300BO.getByCode(String.valueOf(getId()));
		if(railWhseToolWarn == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该工具预警阈值");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}
		   String rcode =t100300BO.update(railWhseToolWarn);
			
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(rcode);
	        printWriter.flush();
	        printWriter.close();
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 停用工具预警阈值
	 * @return
	 */
	public String close() throws Exception {
		System.out.println("----------------停用工具预警阈值-----------------");
		RailWhseToolWarn railWhseToolWarn =  t100300BO.getByCode(String.valueOf(getId()));
		if(railWhseToolWarn == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该工具预警阈值");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}
		   String rcode =t100300BO.update(railWhseToolWarn);
			
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
	public String getWhseCode() {
		return whseCode;
	}
	public void setWhseCode(String whseCode) {
		this.whseCode = whseCode;
	}
	public String getToolName() {
		return toolName;
	}
	public void setToolName(String toolName) {
		this.toolName = toolName;
	}
	public String getLowerThreshold() {
		return lowerThreshold;
	}
	public void setLowerThreshold(String lowerThreshold) {
		this.lowerThreshold = lowerThreshold;
	}
	public String getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(String delStatus) {
		this.delStatus = delStatus;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	public String getAddUser() {
		return addUser;
	}
	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}
	public String getNote1() {
		return note1;
	}
	public void setNote1(String note1) {
		this.note1 = note1;
	}
	public String getNote2() {
		return note2;
	}
	public void setNote2(String note2) {
		this.note2 = note2;
	}
	public String getNote3() {
		return note3;
	}
	public void setNote3(String note3) {
		this.note3 = note3;
	}

	

}

