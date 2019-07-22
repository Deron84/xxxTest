package com.huateng.po;

import java.io.Serializable;

public class AlipayShareParam implements Serializable{

	private static final long serialVersionUID = 1L;


//	private String paramId;
	private String brhId;
	private int shareStandardTotal;
	private float singleShareMoney;
	private float shareThreshold;
	private String share_ruleid;
	private String share_rulename;
	private String freeratio;
	private String sharetype;
	private String mcht_shareval;
	private String orgshareval_first;
	private String orgshareval_second;
	private String orgshareval_three;
	private String share_rulestatus;
//	public String getParamId() {
//		return paramId;
//	}
//	public void setParamId(String paramId) {
//		this.paramId = paramId;
//	}
	
	public String getBrhId() {
		return brhId;
	}
	public String getShare_rulestatus() {
		return share_rulestatus;
	}
	public void setShare_rulestatus(String share_rulestatus) {
		this.share_rulestatus = share_rulestatus;
	}
	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}
	public int getShareStandardTotal() {
		return shareStandardTotal;
	}
	public void setShareStandardTotal(int shareStandardTotal) {
		this.shareStandardTotal = shareStandardTotal;
	}
	public float getSingleShareMoney() {
		return singleShareMoney;
	}
	public void setSingleShareMoney(float singleShareMoney) {
		this.singleShareMoney = singleShareMoney;
	}
	public float getShareThreshold() {
		return shareThreshold;
	}
	public void setShareThreshold(float shareThreshold) {
		this.shareThreshold = shareThreshold;
	}
	
	public String getShare_ruleid() {
		return share_ruleid;
	}
	public void setShare_ruleid(String share_ruleid) {
		this.share_ruleid = share_ruleid;
	}
	public String getShare_rulename() {
		return share_rulename;
	}
	public void setShare_rulename(String share_rulename) {
		this.share_rulename = share_rulename;
	}
	public String getFreeratio() {
		return freeratio;
	}
	public void setFreeratio(String freeratio) {
		this.freeratio = freeratio;
	}
	public String getSharetype() {
		return sharetype;
	}
	public void setSharetype(String sharetype) {
		this.sharetype = sharetype;
	}
	public String getMcht_shareval() {
		return mcht_shareval;
	}
	public void setMcht_shareval(String mcht_shareval) {
		this.mcht_shareval = mcht_shareval;
	}
	public String getOrgshareval_first() {
		return orgshareval_first;
	}
	public void setOrgshareval_first(String orgshareval_first) {
		this.orgshareval_first = orgshareval_first;
	}
	public String getOrgshareval_second() {
		return orgshareval_second;
	}
	public void setOrgshareval_second(String orgshareval_second) {
		this.orgshareval_second = orgshareval_second;
	}
	public String getOrgshareval_three() {
		return orgshareval_three;
	}
	public void setOrgshareval_three(String orgshareval_three) {
		this.orgshareval_three = orgshareval_three;
	}
	
}
