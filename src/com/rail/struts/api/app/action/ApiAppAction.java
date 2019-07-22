package com.rail.struts.api.app.action;

import com.huateng.common.DataUtil;
import com.huateng.common.StringUtil;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.ContextUtil;
import com.rail.bo.api.app.ApiAppBO;
import com.rail.bo.pub.ResultUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * ClassName: ToolsAction 
 * @Description: TODO
 * @author syl
 * @date 2019年4月15日
 */
public class ApiAppAction extends  BaseAction  {
	
	private List list;
	private Map result;
	
	//出入门禁网 单个 接收
	private String accessCode;
	private String employeeCode;
	private String workCode;
	private String infoSign;
	private Integer openSign;
	private Date addDate;
	//出入库房 接收
	private String equipCode;
	private String toolCode;
	
	
	private String id;
	private String type;
	/**
	 * 
	 * @Description:  所有人脸
	 * @param @return
	 * @param @    
	 * @return String  
	 * @author syl
	 * @date 2019年5月6日
	 */
	public String apiAppActionAllEmplayeeImgList()  {
		ApiAppBO bo = (ApiAppBO) ContextUtil.getBean("ApiAppBO");
		if(StringUtil.isEmpty(equipCode)){
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
		return SUCCESS;       
	}
	/**
	 * 
	 * @Description: 所有工具图片
	 * @param @return
	 * @param @    
	 * @return String  
	 * @author syl
	 * @date 2019年5月6日
	 */
	public String apiAppActionAllToolsImgList()  {
		if(StringUtil.isEmpty(equipCode)){
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
		return SUCCESS;       
	}
	/**
	 * 
	 * @Description: 所有工单
	 * @param @return   
	 * @return String  
	 * @author syl
	 * @date 2019年5月6日
	 */
	public String apiAppActionAllWorkInfoList() {
		if(StringUtil.isEmpty(equipCode)){
			result = ResultUtils.fail("equipCode 不能为空" );
			return SUCCESS;
		}
		ApiAppBO bo = (ApiAppBO) ContextUtil.getBean("ApiAppBO");
		Map<String,String> params = DataUtil.getParamMapFromRequest(super.getRequest());
		list = bo.apiAppActionAllWorkInfoList(params);
		if(null!=list && list.size()>0){
			result =  ResultUtils.success(list);
		}else{
			result =  ResultUtils.fail("查询失败");
		}
		return SUCCESS;       
	}
	/**
	 * 
	 * @Description: 人脸开门 
	 * @param @return
	 * @param @    
	 * @return String  
	 * @author syl
	 * @date 2019年4月22日
	 */

	public String openAccess()  {
		if(StringUtil.isEmpty(employeeCode)){
			result = ResultUtils.fail("employeeCode 不能为空" );
			return SUCCESS;
		}
		if(StringUtil.isEmpty(equipCode)){
			result = ResultUtils.fail("equipCode 不能为空" );
			return SUCCESS;
		}
		
		ApiAppBO bo = (ApiAppBO) ContextUtil.getBean("ApiAppBO");
		result =  bo.openAccess(employeeCode, equipCode,openSign);
		return SUCCESS;       
	}
	/**
	 * 
	 * @Description: 入库新增 ---- 工具和人员
	 * @param @return
	 * @param @    
	 * @return String  
	 * @author syl
	 * @date 2019年4月25日
	 */
	public String addRailWhseSingle()  {
		if(StringUtil.isEmpty(infoSign)){
			result = ResultUtils.fail("infoSign 不能为空" );
			return SUCCESS;
		}
		if(StringUtil.isEmpty(workCode)){
			result = ResultUtils.fail("workCode 不能为空" );
			return SUCCESS;
		}
		if(StringUtil.isEmpty(equipCode)){
			result = ResultUtils.fail("equipCode 不能为空" );
			return SUCCESS;
		}
		ApiAppBO bo = (ApiAppBO) ContextUtil.getBean("ApiAppBO");
		result =  bo.addRailWhseSingle(workCode, equipCode, infoSign);
		return SUCCESS;       
	}
	/**
	 * 
	 * @Description: 出库 
	 * @param @return  toolCode "001,0002" 
	 * 
	 * @param @    
	 * @return String  
	 * @author syl
	 * @date 2019年4月28日
	 */
	public String addRailWhseOut()  {
		if(StringUtil.isEmpty(toolCode)){
			result = ResultUtils.fail("toolCode 不能为空" );
			return SUCCESS;
		}
		if(StringUtil.isEmpty(infoSign)){
			result = ResultUtils.fail("infoSign 不能为空" );
			return SUCCESS;
		}
		if(StringUtil.isEmpty(workCode)){
			result = ResultUtils.fail("workCode 不能为空" );
			return SUCCESS;
		}
		if(StringUtil.isEmpty(equipCode)){
			result = ResultUtils.fail("equipCode 不能为空" );
			return SUCCESS;
		}
		ApiAppBO bo = (ApiAppBO) ContextUtil.getBean("ApiAppBO");
		result =  bo.addRailWhseOut(toolCode, workCode, equipCode, infoSign);
		return SUCCESS;       
	}
	/**
	 * 
	 * @Description:出入网
	 * @param @return
	 * @param @    
	 * @return String  
	 * @author syl
	 * @date 2019年4月20日
	 */
	public String addRailAccesssInRecord()  {
		if(StringUtil.isEmpty(infoSign)){
			result = ResultUtils.fail("infoSign 不能为空" );
			return SUCCESS;
		}
		if(StringUtil.isEmpty(toolCode)){
			result = ResultUtils.fail("toolCode 不能为空" );
			return SUCCESS;
		}
		if(StringUtil.isEmpty(employeeCode)){
			result = ResultUtils.fail("employeeCode 不能为空" );
			return SUCCESS;
		}
		if(StringUtil.isEmpty(workCode)){
			result = ResultUtils.fail("workCode 不能为空" );
			return SUCCESS;
		}
		if(StringUtil.isEmpty(equipCode)){
			result = ResultUtils.fail("equipCode 不能为空" );
			return SUCCESS;
		}
		ApiAppBO bo = (ApiAppBO) ContextUtil.getBean("ApiAppBO");
		bo.addRailAccesssInRecord(employeeCode,toolCode,infoSign, workCode, equipCode);
		result =  ResultUtils.success(1);
		return SUCCESS;       
	}
	public String addRailAccesssOutRecord()  {
		if(StringUtil.isEmpty(infoSign)){
			result = ResultUtils.fail("infoSign 不能为空" );
			return SUCCESS;
		}
		if(StringUtil.isEmpty(workCode)){
			result = ResultUtils.fail("workCode 不能为空" );
			return SUCCESS;
		}
		if(StringUtil.isEmpty(equipCode)){
			result = ResultUtils.fail("equipCode 不能为空" );
			return SUCCESS;
		}
		ApiAppBO bo = (ApiAppBO) ContextUtil.getBean("ApiAppBO");
		bo.addRailAccesssOutRecord(infoSign, workCode, equipCode);;
		result =  ResultUtils.success(1);
		return SUCCESS;
	}
	public String getPresentBean()  {
		if(id==null){
			result = ResultUtils.fail("id 不能为空" );
			return SUCCESS;
		}
		if(type==null){
			result = ResultUtils.fail("type[1工具 2员工 3 工单 4 预警] 不能为空" );
			return SUCCESS;
		}
		ApiAppBO bo = (ApiAppBO) ContextUtil.getBean("ApiAppBO");
		Object obj = bo.getPresentBean(id,type);
		if(null == obj){
			result =  ResultUtils.fail("信息不存在");
		}
		result =  ResultUtils.success(obj);
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
	protected String subExecute()   {
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
	
	public Integer getOpenSign() {
		return openSign;
	}
	public void setOpenSign(Integer openSign) {
		this.openSign = openSign;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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

