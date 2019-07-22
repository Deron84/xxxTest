package com.sdses.struts.util;

public class ResponseBean {
	/*List<ResponseMsg> responseMsgs;
	
	public List<ResponseMsg> getResponseMsgs() {
		return responseMsgs;
	}
	public void setResponseMsgs(List<ResponseMsg> responseMsgs) {
		this.responseMsgs = responseMsgs;
	}*/
	private String result_code;
	private String apiCode;
	private String cmbcMchntId;
	private String result_msg;
	private String outMchntId;
	private String cmbcSignId;
	private String error_msg;
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public String getApiCode() {
		return apiCode;
	}
	public void setApiCode(String apiCode) {
		this.apiCode = apiCode;
	}
	public String getCmbcMchntId() {
		return cmbcMchntId;
	}
	public void setCmbcMchntId(String cmbcMchntId) {
		this.cmbcMchntId = cmbcMchntId;
	}
	public String getResult_msg() {
		return result_msg;
	}
	public void setResult_msg(String result_msg) {
		this.result_msg = result_msg;
	}
	public String getOutMchntId() {
		return outMchntId;
	}
	public void setOutMchntId(String outMchntId) {
		this.outMchntId = outMchntId;
	}
	public String getCmbcSignId() {
		return cmbcSignId;
	}
	public void setCmbcSignId(String cmbcSignId) {
		this.cmbcSignId = cmbcSignId;
	}
	public String getError_msg() {
		return error_msg;
	}
	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}
	
	
}
