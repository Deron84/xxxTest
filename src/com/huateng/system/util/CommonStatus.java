/**
 * 
 */
package com.huateng.system.util;

/**
 * @author Administrator
 *
 */
public enum CommonStatus {

	SUCCESS(10, "成功"),
	FAIL(11, "失败"),
	
	ACTIVE(1, "有效"),
	UNACTIVE(0, "无效")
	;
	
	private int code;
	private String desc;

	private CommonStatus(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getDesc() {
		return this.desc;
	}

	public int getCode() {
		return this.code;
	}
}
