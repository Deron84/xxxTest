package com.rail.po.vo;  
/**  
* @author syl
* @version 创建时间：2019年4月16日 下午3:47:32  
* @desc
*/
public class RailEmployeeVo2 {
	private long id;
	private String employeeCode;
	private String employeeName;
	private String employeeImg;
	private String employeeTel;
	private String note1;
	private String dept;
	
	
	
	private String detpName;
	private String workStatus;
	
	public RailEmployeeVo2(long id, String employeeCode, String employeeName, String employeeImg, String employeeTel, String note1,
			String dept) {
		super();
		this.id = id;
		this.employeeCode = employeeCode;
		this.employeeName = employeeName;
		this.employeeImg = employeeImg;
		this.employeeTel = employeeTel;
		this.note1 = note1;
		this.dept = dept;
	}

	
	
	public String getDetpName() {
		return detpName;
	}



	public void setDetpName(String detpName) {
		this.detpName = detpName;
	}



	public String getWorkStatus() {
		return workStatus;
	}



	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}



	public String getEmployeeTel() {
		return employeeTel;
	}



	public void setEmployeeTel(String employeeTel) {
		this.employeeTel = employeeTel;
	}



	public String getNote1() {
		return note1;
	}



	public void setNote1(String note1) {
		this.note1 = note1;
	}



	public String getDept() {
		return dept;
	}



	public void setDept(String dept) {
		this.dept = dept;
	}



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

	public RailEmployeeVo2(long id, String employeeCode, String employeeName, String employeeImg) {
		super();
		this.id = id;
		this.employeeCode = employeeCode;
		this.employeeName = employeeName;
		this.employeeImg = employeeImg;
	}

	

}

