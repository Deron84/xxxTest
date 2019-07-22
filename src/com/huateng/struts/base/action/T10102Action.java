package com.huateng.struts.base.action;

import java.util.ArrayList;
import java.util.List;

import com.huateng.bo.base.T10102BO;
import com.huateng.common.Constants;
import com.huateng.po.SettleDocInfo;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

public class T10102Action extends BaseAction {
	
	private static final long serialVersionUID = 1L;

	private T10102BO t10102BO = (T10102BO) ContextUtil.getBean("T10102BO");

	@Override
	protected String subExecute(){
		try {
			if("add".equals(getMethod())) {			
					rspCode = add();			
			} else if("delete".equals(getMethod())) {
				rspCode = delete();
			} else if("update".equals(getMethod())) {
				rspCode = update();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，对清算凭证的维护操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}

	private String add() throws Exception {

		SettleDocInfo settleDocInfo = new SettleDocInfo();
		settleDocInfo.setId(brhId);
		settleDocInfo.setExchangeNo(exchangeNo);
		settleDocInfo.setInBankSettleNo(inBankSettleNo);
		settleDocInfo.setPayBankNo(payBankNo);
		settleDocInfo.setPayerAccountNo(payerAcctNo);
		settleDocInfo.setPayerName(payerName);
		settleDocInfo.setSettleBusNo(settleBusNo);
		settleDocInfo.setInnerAcct(innerAcct);
		settleDocInfo.setInnerAcct1(innerAcct1);
		settleDocInfo.setInnerAcct2(innerAcct2);
		settleDocInfo.setRecCrtTs(CommonFunction.getCurrentDateTime());
		settleDocInfo.setRecUpdTs(CommonFunction.getCurrentDateTime());
		settleDocInfo.setRecUpdUser(operator.getOprId());
		
		return t10102BO.add(settleDocInfo);
	}

	private String delete() throws Exception {
		return t10102BO.delete(brhId);
	}

	private String update() throws Exception {

		jsonBean.parseJSONArrayData(getParameterList());
		
		int len = jsonBean.getArray().size();
		
		
		SettleDocInfo settleDocInfo = null;
				
		List<SettleDocInfo> settleDocInfoList = new ArrayList<SettleDocInfo>(len);
		
		for(int i = 0; i < len; i++) {
			
			settleDocInfo = new SettleDocInfo();						
			jsonBean.setObject(jsonBean.getJSONDataAt(i));			
			BeanUtils.setObjectWithPropertiesValue(settleDocInfo, jsonBean,true);		
			BeanUtils.setNullValueWithValue(settleDocInfo, "-", 0);
			settleDocInfo.setPayBankNo(settleDocInfo.getPayBankNo());
			settleDocInfo.setPayerAccountNo(settleDocInfo.getPayerAccountNo());
			settleDocInfo.setRecUpdTs(CommonFunction.getCurrentDateTime());
			settleDocInfo.setRecUpdUser(operator.getOprId());
			settleDocInfoList.add(settleDocInfo);
		}
		
		t10102BO.update(settleDocInfoList);		
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * @return the brhId
	 */
	public String getBrhId() {
		return brhId;
	}

	/**
	 * @param brhId the brhId to set
	 */
	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}

	/**
	 * @return the exchangeNo
	 */
	public String getExchangeNo() {
		return exchangeNo;
	}

	/**
	 * @param exchangeNo the exchangeNo to set
	 */
	public void setExchangeNo(String exchangeNo) {
		this.exchangeNo = exchangeNo;
	}

	/**
	 * @return the payerName
	 */
	public String getPayerName() {
		return payerName;
	}

	/**
	 * @param payerName the payerName to set
	 */
	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	/**
	 * @return the payerAcctNo
	 */
	public String getPayerAcctNo() {
		return payerAcctNo;
	}

	/**
	 * @param payerAcctNo the payerAcctNo to set
	 */
	public void setPayerAcctNo(String payerAcctNo) {
		this.payerAcctNo = payerAcctNo;
	}

	/**
	 * @return the payerBankNo
	 */
	public String getPayBankNo() {
		return payBankNo;
	}

	/**
	 * @param payerBankNo the payerBankNo to set
	 */
	public void setPayBankNo(String payBankNo) {
		this.payBankNo = payBankNo;
	}

	/**
	 * @return the inBankSettleNo
	 */
	public String getInBankSettleNo() {
		return inBankSettleNo;
	}

	/**
	 * @param inBankSettleNo the inBankSettleNo to set
	 */
	public void setInBankSettleNo(String inBankSettleNo) {
		this.inBankSettleNo = inBankSettleNo;
	}

	/**
	 * @return the settleBusNo
	 */
	public String getSettleBusNo() {
		return settleBusNo;
	}

	/**
	 * @param settleBusNo the settleBusNo to set
	 */
	public void setSettleBusNo(String settleBusNo) {
		this.settleBusNo = settleBusNo;
	}

	private String brhId;
	private String exchangeNo;
	private String payerName;
	private String payerAcctNo;
	private String payBankNo;
	private String inBankSettleNo;
	private String settleBusNo;
	private String parameterList;
	private String innerAcct;
	private String innerAcct1;
	private String innerAcct2;

	/**
	 * @return the innerAcct1
	 */
	public String getInnerAcct1() {
		return innerAcct1;
	}

	/**
	 * @param innerAcct1 the innerAcct1 to set
	 */
	public void setInnerAcct1(String innerAcct1) {
		this.innerAcct1 = innerAcct1;
	}

	/**
	 * @return the innerAcct2
	 */
	public String getInnerAcct2() {
		return innerAcct2;
	}

	/**
	 * @param innerAcct2 the innerAcct2 to set
	 */
	public void setInnerAcct2(String innerAcct2) {
		this.innerAcct2 = innerAcct2;
	}

	/**
	 * @return the innerAcct
	 */
	public String getInnerAcct() {
		return innerAcct;
	}

	/**
	 * @param innerAcct the innerAcct to set
	 */
	public void setInnerAcct(String innerAcct) {
		this.innerAcct = innerAcct;
	}

	/**
	 * @return the parameterList
	 */
	public String getParameterList() {
		return parameterList;
	}

	/**
	 * @param parameterList the parameterList to set
	 */
	public void setParameterList(String parameterList) {
		this.parameterList = parameterList;
	}
}
