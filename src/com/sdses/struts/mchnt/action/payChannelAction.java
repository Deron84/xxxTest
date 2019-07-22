package com.sdses.struts.mchnt.action;

import java.util.HashMap;
import java.util.ResourceBundle;

import com.alibaba.fastjson.JSON;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.BaseAction;
import com.sdses.struts.util.APIHttpClient;
import com.sdses.struts.util.ResponseBean;
import com.sdses.struts.util.StaticUtils;

public class payChannelAction extends BaseAction{

	private static final long serialVersionUID = -5454151705559046974L;
	
	ResourceBundle bundle = ResourceBundle.getBundle(SysParamConstants.CKEY);
	String MSURl = bundle.getString("MSURl");
	String payChannelAddUrl = bundle.getString("payChannelAddUrl"); 
	String payChannelModifyUrl = bundle.getString("payChannelModifyUrl");
	String payChannelQueryUrl = bundle.getString("payChannelQueryUrl");
	
	//民生支付渠道添加参数
	private String operId;
	private String outMchntId;
	private String cmbcMchntId;
	private String apiCode;
	private String industryId;
	private String operateType;
	private String dayLimit;
	private String monthLimit;
	private String rateFlag;
	private String feeRate;
	private String account;
	private String pbcBankId;
	private String acctName;
	private String acctType;
	private String cmbcSignId;

	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
		String postStr = null;
		try {
            if ("add".equals(getMethod())) {
            	postStr = add();
            } else if ("update".equals(getMethod())) {
            	postStr=update();	
			} else if ("query".equals(getMethod())) {
            	postStr=query();	
			}
        } catch (Exception e) {
            log("操作员编号：" + operator.getOprId() + "，对机构的维护操作" + getMethod() + "失败，失败原因为：" + e.getMessage());
        }
        return postStr;
	}
	 /**
     * 
     * //TODO 商户支付通道绑定信息添加
     *
     * @param args
     * @throws Exception
     */
	private String add() throws Exception {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		String responseMsg=null;
        hashMap.put("operId",operId);
        hashMap.put("outMchntId",outMchntId);
        hashMap.put("cmbcMchntId", cmbcMchntId);
        hashMap.put("apiCode", apiCode);
        hashMap.put("industryId",industryId);
        hashMap.put("operateType",operateType);
        hashMap.put("dayLimit",dayLimit);
        hashMap.put("monthLimit",monthLimit);
        hashMap.put("rateFlag",rateFlag);
        hashMap.put("feeRate", feeRate);
        hashMap.put("account", account);
        hashMap.put("pbcBankId",pbcBankId);
        hashMap.put("acctName",acctName);
        hashMap.put("acctType", acctType);
        hashMap.put("cmbcSignId",cmbcSignId);
        String paramStr = StaticUtils.mapToStr(hashMap);
        System.out.println("-------"+paramStr);
        String postStr = APIHttpClient.httpsRequest(MSURl+payChannelAddUrl, "POST", paramStr.toString());
        System.out.println("-------"+postStr);
        ResponseBean bean = JSON.parseObject(postStr, ResponseBean.class);
        responseMsg=StaticUtils.ListToSt2(bean);
        System.out.println("-------"+responseMsg);
        return responseMsg;
	}

    /**
     * 
     * //TODO 商户支付通道绑定信息修改
     *
     * @param args
     * @throws Exception
     */
	private String update() throws Exception{
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        String responseMsg=null;
        hashMap.put("operId",operId);
        hashMap.put("outMchntId",outMchntId);
        hashMap.put("cmbcMchntId",cmbcMchntId);
        hashMap.put("cmbcSignId",cmbcSignId);
        hashMap.put("apiCode",apiCode);
        hashMap.put("dayLimit",dayLimit);
        hashMap.put("monthLimit",monthLimit);
        hashMap.put("rateFlag",rateFlag);
        hashMap.put("feeRate",feeRate);
        hashMap.put("account",account);
        hashMap.put("pbcBankId",pbcBankId);
        hashMap.put("acctName",acctName);
        hashMap.put("acctType",acctType);
        String paramStr = StaticUtils.mapToStr(hashMap);
        System.out.println("-------"+paramStr);
        String postStr = APIHttpClient.httpsRequest(MSURl+payChannelModifyUrl, "POST", paramStr.toString());
        System.out.println("-------"+postStr);
        ResponseBean bean = JSON.parseObject(postStr, ResponseBean.class);
        responseMsg=StaticUtils.ListToSt2(bean);
        System.out.println("-------"+responseMsg);
        return responseMsg;
	}
	/**
     * 
     * //TODO 商户支付通道绑定信息查询
     *
     * @param args
     * @throws Exception
     */
	private String query() throws Exception{
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		String responseMsg=null;
        hashMap.put("outMchntId",outMchntId);
        hashMap.put("cmbcMchntId",cmbcMchntId);
        hashMap.put("apiCode",apiCode);
        String paramStr = StaticUtils.mapToStr(hashMap);
        String postStr = APIHttpClient.httpsRequest(MSURl+payChannelQueryUrl, "POST", paramStr.toString());
        System.out.println("-------"+postStr);
        ResponseBean bean = JSON.parseObject(postStr, ResponseBean.class);
        responseMsg=StaticUtils.ListToSt2(bean);
        System.out.println("-------"+responseMsg);
        return responseMsg;
	}
	
	public String getOperId() {
		return operId;
	}
	public void setOperId(String operId) {
		this.operId = operId;
	}
	public String getOutMchntId() {
		return outMchntId;
	}
	public void setOutMchntId(String outMchntId) {
		this.outMchntId = outMchntId;
	}
	public String getCmbcMchntId() {
		return cmbcMchntId;
	}
	public void setCmbcMchntId(String cmbcMchntId) {
		this.cmbcMchntId = cmbcMchntId;
	}
	public String getApiCode() {
		return apiCode;
	}
	public void setApiCode(String apiCode) {
		this.apiCode = apiCode;
	}
	public String getIndustryId() {
		return industryId;
	}
	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public String getDayLimit() {
		return dayLimit;
	}
	public void setDayLimit(String dayLimit) {
		this.dayLimit = dayLimit;
	}
	public String getMonthLimit() {
		return monthLimit;
	}
	public void setMonthLimit(String monthLimit) {
		this.monthLimit = monthLimit;
	}
	public String getRateFlag() {
		return rateFlag;
	}
	public void setRateFlag(String rateFlag) {
		this.rateFlag = rateFlag;
	}
	public String getFeeRate() {
		return feeRate;
	}
	public void setFeeRate(String feeRate) {
		this.feeRate = feeRate;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPbcBankId() {
		return pbcBankId;
	}
	public void setPbcBankId(String pbcBankId) {
		this.pbcBankId = pbcBankId;
	}
	public String getAcctName() {
		return acctName;
	}
	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}
	public String getAcctType() {
		return acctType;
	}
	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}
	public String getCmbcSignId() {
		return cmbcSignId;
	}
	public void setCmbcSignId(String cmbcSignId) {
		this.cmbcSignId = cmbcSignId;
	}	
	
}
