package com.rail.struts.api.app.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.huateng.common.DataUtil;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.ContextUtil;
import com.rail.bo.api.app.ApiAppBO;
import com.rail.bo.pub.ResultUtils;

/**
 * 
 * ClassName: ToolsAction 
 * @Description: TODO
 * @author syl
 * @date 2019年4月15日
 */
public class ApiAppAction2 extends  BaseAction {
	
	private List list;
	private Map result;
	
	//出入门禁网 单个 接收
	private String accessCode;
	private String employeeCode;
	private String workCode;
	private String infoSign;
	private Date addDate;
	//出入库房 接收
	private String equipCode;
	private String toolCode;
	
	private String toolCodes;
	private String moreOrderLessSign;
	
	
	public String apiAppActionAllEmplayeeImgList() throws Exception{
		ApiAppBO bo = (ApiAppBO) ContextUtil.getBean("ApiAppBO");
		String name = super.getRequest().getParameter("paraName");  
		String method =  super.getRequest().getParameter("method");
		if(equipCode==null){
			result = ResultUtils.fail("equipCode 不能为空" );
			return SUCCESS;
		}
		Map<String,String> params = DataUtil.getParamMapFromRequest(super.getRequest());
		list = bo.apiAppActionAllEmplayeeImgList(params);
		if(null!=list && list.size()>0){
			result =  ResultUtils.success(list);
		}else{
			result =  ResultUtils.fail("查询失败");
		}
		//writeMessage(String.valueOf));
		return SUCCESS;       
	}
	public String apiAppActionAllToolsImgList() throws Exception{
		if(equipCode==null){
			result = ResultUtils.fail("equipCode 不能为空" );
			return SUCCESS;
		}
		ApiAppBO bo = (ApiAppBO) ContextUtil.getBean("ApiAppBO");
		list = bo.apiAppActionAllToolsImgList(equipCode);
		if(null!=list && list.size()>0){
			result =  ResultUtils.success(list);
		}else{
			result =  ResultUtils.fail("查询失败");
		}
		//writeMessage(String.valueOf));
		return SUCCESS;       
	}
/*	public String apiAppActionAllToolsImgList() throws Exception{
		ApiAppBO bo = (ApiAppBO) ContextUtil.getBean("ApiAppBO");
		String name = super.getRequest().getParameter("paraName");  
		String method =  super.getRequest().getParameter("method");
		Map<String,String> params = DataUtil.getParamMapFromRequest(super.getRequest());
		list = bo.apiAppActionAllToolsImgList(params);
		if(null!=list && list.size()>0){
			result =  ResultUtils.success(list);
		}else{
			result =  ResultUtils.fail("查询失败");
		}
		//writeMessage(String.valueOf));
		return SUCCESS;       
	}
*/	public String apiAppActionAllWorkInfoList() throws Exception{
		ApiAppBO bo = (ApiAppBO) ContextUtil.getBean("ApiAppBO");
		String name = super.getRequest().getParameter("paraName");  
		String method =  super.getRequest().getParameter("method");
		Map<String,String> params = DataUtil.getParamMapFromRequest(super.getRequest());
		list = bo.apiAppActionAllWorkInfoList(params);
		if(null!=list && list.size()>0){
			result =  ResultUtils.success(list);
		}else{
			result =  ResultUtils.fail("查询失败");
		}
		//writeMessage(String.valueOf));
		return SUCCESS;       
	}
	
	/**
	 * 
	 * @Description: 人脸开门 
	 * @param @return
	 * @param @throws Exception   
	 * @return String  
	 * @author syl
	 * @date 2019年4月22日
	 */

	public String openAccess() throws Exception{
		if(employeeCode==null){
			result = ResultUtils.fail("employeeCode 不能为空" );
			return SUCCESS;
		}
		if(equipCode==null){
			result = ResultUtils.fail("equipCode 不能为空" );
			return SUCCESS;
		}
		
		ApiAppBO bo = (ApiAppBO) ContextUtil.getBean("ApiAppBO");
		result =  bo.openAccess(employeeCode, equipCode,0);
		return SUCCESS;       
	}
	
//	
//	/**
//	 * 
//	 * @Description: 人员出入库
//	 * @param @return
//	 * @param @throws Exception   
//	 * @return String  
//	 * @author syl
//	 * @date 2019年4月20日
//	 */
//	public String addRailWhseEmployee() throws Exception{
//		if(employeeCode==null){
//			result = ResultUtils.fail("employeeCode 不能为空" );
//			return SUCCESS;
//		}
//		if(infoSign==null){
//			result = ResultUtils.fail("infoSign 不能为空" );
//			return SUCCESS;
//		}
//		if(workCode==null){
//			result = ResultUtils.fail("workCode 不能为空" );
//			return SUCCESS;
//		}
//		if(equipCode==null){
//			result = ResultUtils.fail("equipCode 不能为空" );
//			return SUCCESS;
//		}
//		
//		RailWhseEmployee accessEmployee = new RailWhseEmployee();
//		accessEmployee.setEmployeeCode(employeeCode);
//		accessEmployee.setInfoSign(infoSign);
//		accessEmployee.setWorkCode(workCode);
//		accessEmployee.setAddDate(new Date());
//		ApiAppBO bo = (ApiAppBO) ContextUtil.getBean("ApiAppBO");
//		bo.addRailWhseEmployee(accessEmployee,equipCode);
//		result =  ResultUtils.success(1);
//		return SUCCESS;       
//	}
//	/**
//	 * 
//	 * @Description: 工具出入库 传入toolList 多或者少 moreOrderLessSign1 少 2 多 
//	 * @param @return
//	 * @param @throws Exception   
//	 * @return String  
//	 * @author syl
//	 * @date 2019年4月22日
//	 */
//	public String addRailWhseTools() throws Exception{
//		
//		if(toolCodes==null){
//			result = ResultUtils.fail("toolCodes 工具列表不能为空" );
//			return SUCCESS;
//		}
//		if(moreOrderLessSign==null){
//			result = ResultUtils.fail("moreOrderLessSign 工具列表不能为空" );
//			return SUCCESS;
//		}
//		if(toolCodes==null){
//			result = ResultUtils.fail("toolCodes 工具列表不能为空" );
//			return SUCCESS;
//		}
//		if(infoSign==null){
//			result = ResultUtils.fail("infoSign 不能为空" );
//			return SUCCESS;
//		}
//		if(workCode==null){
//			result = ResultUtils.fail("workCode 不能为空" );
//			return SUCCESS;
//		}
//		if(equipCode==null){
//			result = ResultUtils.fail("equipCode 不能为空" );
//			return SUCCESS;
//		}
//		String[] toolCodeList = toolCodes.split(",");
//		RailWhseTool railWhseTool = new RailWhseTool();
//		railWhseTool.setToolCode(toolCode);
//		railWhseTool.setInfoSign(infoSign);
//		railWhseTool.setWorkCode(workCode);
//		railWhseTool.setAddDate(new Date());
//		ApiAppBO bo = (ApiAppBO) ContextUtil.getBean("ApiAppBO");
//		bo.addRailWhseTools(railWhseTool,toolCodeList,equipCode,infoSign,moreOrderLessSign);
//		result =  ResultUtils.success(1);
//		return SUCCESS;       
//	}
	/**
	 * 
	 * @Description: 出入库新增 工具和人员
	 * @param @return
	 * @param @throws Exception   
	 * @return String  
	 * @author syl
	 * @date 2019年4月25日
	 */
	public String addRailWhseSingle() throws Exception{
		if(infoSign==null){
			result = ResultUtils.fail("infoSign 不能为空" );
			return SUCCESS;
		}
		if(workCode==null){
			result = ResultUtils.fail("workCode 不能为空" );
			return SUCCESS;
		}
		if(equipCode==null){
			result = ResultUtils.fail("equipCode 不能为空" );
			return SUCCESS;
		}
		ApiAppBO bo = (ApiAppBO) ContextUtil.getBean("ApiAppBO");
		result =  bo.addRailWhseSingle(workCode, equipCode, infoSign);
		return SUCCESS;       
	}
//	public String addRailWhseTool() throws Exception{
//		if(toolCode==null){
//			result = ResultUtils.fail("toolCode 不能为空" );
//			return SUCCESS;
//		}
//		if(infoSign==null){
//			result = ResultUtils.fail("infoSign 不能为空" );
//			return SUCCESS;
//		}
//		if(workCode==null){
//			result = ResultUtils.fail("workCode 不能为空" );
//			return SUCCESS;
//		}
//		if(equipCode==null){
//			result = ResultUtils.fail("equipCode 不能为空" );
//			return SUCCESS;
//		}
//		
//		RailWhseTool railWhseTool = new RailWhseTool();
//		railWhseTool.setToolCode(toolCode);
//		railWhseTool.setInfoSign(infoSign);
//		railWhseTool.setWorkCode(workCode);
//		railWhseTool.setAddDate(new Date());
//		ApiAppBO bo = (ApiAppBO) ContextUtil.getBean("ApiAppBO");
//		bo.addRailWhseTool(railWhseTool,equipCode);
//		result =  ResultUtils.success(1);
//		return SUCCESS;       
//	}
	/**
	 * 
	 * @Description: 出入网结束 检查是否存入预警信息
	 * @param @return
	 * @param @throws Exception   
	 * @return String  
	 * @author syl
	 * @date 2019年4月22日
	 */
//	public String addRailAccesssWhseEnd() throws Exception{
//		if(infoSign==null){
//			result = ResultUtils.fail("infoSign 不能为空" );
//			return SUCCESS;
//		}
//		if(workCode==null){
//			result = ResultUtils.fail("workCode 不能为空" );
//			return SUCCESS;
//		}
//		if(equipCode==null){
//			result = ResultUtils.fail("equipCode 不能为空" );
//			return SUCCESS;
//		}
//		
//		ApiAppBO bo = (ApiAppBO) ContextUtil.getBean("ApiAppBO");
//		bo.addRailAccesssWhseEnd(infoSign, workCode, equipCode);;
//		result =  ResultUtils.success(1);
//		return SUCCESS;       
//	}
	/**
	 * 
	 * @Description:出入网
	 * @param @return
	 * @param @throws Exception   
	 * @return String  
	 * @author syl
	 * @date 2019年4月20日
	 */
	public String addRailAccesss() throws Exception{
		if(infoSign==null){
			result = ResultUtils.fail("infoSign 不能为空" );
			return SUCCESS;
		}
		if(workCode==null){
			result = ResultUtils.fail("workCode 不能为空" );
			return SUCCESS;
		}
		if(equipCode==null){
			result = ResultUtils.fail("equipCode 不能为空" );
			return SUCCESS;
		}
		ApiAppBO bo = (ApiAppBO) ContextUtil.getBean("ApiAppBO");
//		bo.addRailAccesss(infoSign, workCode, equipCode);;
		result =  ResultUtils.success(1);
		return SUCCESS;       
	}
	
	
	public List getList() {
		return list;
	}
	
	public Map getResult() {
		return result;
	}
	public void setResult(Map result) {
		this.result = result;
	}
	
	@Override
	protected String subExecute() throws Exception {
		ApiAppBO bo = (ApiAppBO) ContextUtil.getBean("ApiAppBO");
		System.out.println("re:"+request);
		Object method = request.getParameter("method");
		Map<String,String> params = DataUtil.getParamMapFromRequest(request);
//		re = bo.get(params);
//		writeMessage(re);
		return SUCCESS;   
	}
	public String getAccessCode() {
		return accessCode;
	}
	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}
	public String getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	public String getWorkCode() {
		return workCode;
	}
	public void setWorkCode(String workCode) {
		this.workCode = workCode;
	}
	public String getInfoSign() {
		return infoSign;
	}
	public void setInfoSign(String infoSign) {
		this.infoSign = infoSign;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	public void setList(List list) {
		this.list = list;
	}
	public String getEquipCode() {
		return equipCode;
	}
	public void setEquipCode(String equipCode) {
		this.equipCode = equipCode;
	}
	public String getToolCode() {
		return toolCode;
	}
	public void setToolCode(String toolCode) {
		this.toolCode = toolCode;
	}

	

}

