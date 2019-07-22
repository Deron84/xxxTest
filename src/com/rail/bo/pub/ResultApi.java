package com.rail.bo.pub;  
/**  
* @author syl
* @version 创建时间：2019年4月17日 下午3:30:01  
* @desc
*/
public class ResultApi {
	private int code;
	private String desc;
	private Object Data;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Object getData() {
		return Data;
	}
	public void setData(Object data) {
		Data = data;
	}
	public ResultApi(int code, String desc, Object data) {
		super();
		this.code = code;
		this.desc = desc;
		Data = data;
	}
	public ResultApi(String desc, Object data) {
		super();
		this.code = 200;
		this.desc = desc;
		Data = data;
	}
	public ResultApi(String erowMsg) {
		super();
		this.code = 500;
		this.desc = erowMsg;
	}
	
	
	
	

}

