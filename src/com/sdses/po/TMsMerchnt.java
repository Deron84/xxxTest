package com.sdses.po;

import java.io.Serializable;

public class TMsMerchnt implements Serializable {
	
	private static final long serialVersionUID = -3778754274614772459L;
	
	// 联合主键
    private TMsMerchntPK tMsMerchntPK;
	private String txnSeq;        //流水号
	private String operId;        //拓展人编号
	private String mchntName;     //商户简称
	private String mchntFullName; //商户全称
	private String devType;       //拓展模式
	private String acdCode;       //地区编码
	private String province;      //省份
	private String city;          //城市
	private String address;       //地址
	private String isCert;        //是否持证
	private String licId;         //营业执证
	private String idTCard;       //省份证件号
	private String corpName;      //联系人
	private String telephone;     //联系电话
	private String autoSettle;    //结算方式
	private String status;         //商户状态
	private String cmbcMchntId;    //民生商户号
	private String createTime;    //创建时间
	private String modifyTime;    //修改时间
	private String code;          //民生返回码
	private String message;       //民生返回信息
	
	public TMsMerchntPK gettMsMerchntPK() {
		return tMsMerchntPK;
	}
	public void settMsMerchntPK(TMsMerchntPK tMsMerchntPK) {
		this.tMsMerchntPK = tMsMerchntPK;
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
	public String getMchntName() {
		return mchntName;
	}
	public void setMchntName(String mchntName) {
		this.mchntName = mchntName;
	}
	public String getMchntFullName() {
		return mchntFullName;
	}
	public void setMchntFullName(String mchntFullName) {
		this.mchntFullName = mchntFullName;
	}
	public String getDevType() {
		return devType;
	}
	public void setDevType(String devType) {
		this.devType = devType;
	}
	public String getAcdCode() {
		return acdCode;
	}
	public void setAcdCode(String acdCode) {
		this.acdCode = acdCode;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getIsCert() {
		return isCert;
	}
	public void setIsCert(String isCert) {
		this.isCert = isCert;
	}
	public String getLicId() {
		return licId;
	}
	public void setLicId(String licId) {
		this.licId = licId;
	}
	public String getIdTCard() {
		return idTCard;
	}
	public void setIdTCard(String idTCard) {
		this.idTCard = idTCard;
	}
	public String getCorpName() {
		return corpName;
	}
	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getAutoSettle() {
		return autoSettle;
	}
	public void setAutoSettle(String autoSettle) {
		this.autoSettle = autoSettle;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCmbcMchntId() {
		return cmbcMchntId;
	}
	public void setCmbcMchntId(String cmbcMchntId) {
		this.cmbcMchntId = cmbcMchntId;
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
