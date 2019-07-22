package com.rail.po.vo;  
/**  
* @author syl
* @version 创建时间：2019年4月16日 下午3:47:32  
* @desc
*/
public class RailEmployeeVo {
	private long id;
	private String employeeCode;
	private String employeeName;
	private String employeeImg;

	public String getEmployeeImg() {
		return employeeImg;
	}

	public void setEmployeeImg(String employeeImg) {
		this.employeeImg = employeeImg;
	}
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public RailEmployeeVo(long id, String employeeCode, String employeeName, String employeeImg) {
		super();
		this.id = id;
		this.employeeCode = employeeCode;
		this.employeeName = employeeName;
		this.employeeImg = employeeImg;
	}

	

}

