package com.sdses.po;

import java.io.Serializable;

public class TAirpayMap implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// 联合主键
	private TAirpayMapPK tAirpayMapPK;
	
	// 应用ID 
	private String appId;
	
	// 商户号
	private String mchId;
	
	// 支付宝公钥/微信密钥
	private String cKey1;
	
	// 支付宝私钥
	private String cKey2;
	
	// 签名类型
	private String signType;
	
	// 状态标识
	private String flag;
	
	// 授权标识
	private String authorityFlag;

	public TAirpayMapPK gettAirpayMapPK() {
		return tAirpayMapPK;
	}

	public void settAirpayMapPK(TAirpayMapPK tAirpayMapPK) {
		this.tAirpayMapPK = tAirpayMapPK;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getcKey1() {
		return cKey1;
	}

	public void setcKey1(String cKey1) {
		this.cKey1 = cKey1;
	}

	public String getcKey2() {
		return cKey2;
	}

	public void setcKey2(String cKey2) {
		this.cKey2 = cKey2;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getAuthorityFlag() {
		return authorityFlag;
	}

	public void setAuthorityFlag(String authorityFlag) {
		this.authorityFlag = authorityFlag;
	}

}
