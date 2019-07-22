package com.rail.po.vo;

import java.util.List;

/**  
* @author syl
* @version 创建时间：2019年4月16日 下午3:06:52  
* @desc api 开门时用
*/
public class ApiAllTollsVo {
	private String toolCode;
	private String toolName;
	private String toolNameId;
	private String toolImg;
	private String rfid;
	private List<Object> rfids;
	private List<Object> toolCodes;
	private List<Object> locations;
	
	
	
	
	public ApiAllTollsVo() {
		super();
	}
	public ApiAllTollsVo(String rfid, String toolName, String toolImg, String toolCode) {
		super();
		this.toolCode = toolCode;
		this.toolName = toolName;
		this.toolImg = toolImg;
		this.rfid = rfid;
	}
	public String getToolCode() {
		return toolCode;
	}
	public void setToolCode(String toolCode) {
		this.toolCode = toolCode;
	}
	public String getToolName() {
		return toolName;
	}
	public void setToolName(String toolName) {
		this.toolName = toolName;
	}
	
	public String getToolNameId() {
		return toolNameId;
	}
	public void setToolNameId(String toolNameId) {
		this.toolNameId = toolNameId;
	}
	public String getToolImg() {
		return toolImg;
	}
	public void setToolImg(String toolImg) {
		this.toolImg = toolImg;
	}
	public String getRfid() {
		return rfid;
	}
	public void setRfid(String rfid) {
		this.rfid = rfid;
	}
	public List<Object> getRfids() {
		return rfids;
	}
	public void setRfids(List<Object> rfids) {
		this.rfids = rfids;
	}
	public List<Object> getToolCodes() {
		return toolCodes;
	}
	public void setToolCodes(List<Object> toolCodes) {
		this.toolCodes = toolCodes;
	}
	public List<Object> getLocations() {
		return locations;
	}
	public void setLocations(List<Object> locations) {
		this.locations = locations;
	}

	
	
}

