package com.sdses.po;

import java.io.Serializable;

public class TMsPayChannel implements Serializable {

	private static final long serialVersionUID = -3708349486483092766L;
	
	// 联合主键
    private TMsPayChannelPK tMsPayChannelPK;
	private String txnSeq;        //流水号
	private String operId;        //拓展人编号
	private String apiCode;       //支付通道
	private String industryId;    //商户类别
	private String operateType;   //接入类型
	private String dayLimit;      //日限额
	private String monthLimit;    //月限额
	private String rateFlag;      //费率标识
	private String feeRate;       //费率
	private String account;       //结算账号
	private String pbcBankId;     //开户行号
	private String acctName;      //开户人
	private String acctType;      //开户类型
	private String cmbcSignId;    //民生签约编号
	private String createTime;    //创建时间
	private String modifyTime;    //修改时间
	private String code;          //民生返回码
	private String message;       //民生返回信息
	
	public TMsPayChannelPK gettMsPayChannelPK() {
		return tMsPayChannelPK;
	}
	public void settMsPayChannelPK(TMsPayChannelPK tMsPayChannelPK) {
		this.tMsPayChannelPK = tMsPayChannelPK;
	}
	public String getTxnSeq() {
		return txnSeq;
	}
	public void setTxnSeq(String txnSeq) {
		this.txnSeq = txnSeq;
	}
	public String getOperId() {
		return operId;
	}
	public void setOperId(String operId) {
		this.operId = operId;
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
