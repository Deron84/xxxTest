package com.rail.po.vo;

import java.util.List;

/**  
* @author syl
* @version 创建时间：2019年4月16日 下午3:06:52  
* @desc
*/
public class RailToolInfoVo {
	private long id;
	private String toolCode;
	private String rfid;
	private String modelCode;
	private String whseCode;
	private String inWhse;
	
	
	private String whseName;
	private String modelName;
	
	private String toolImg;
	private String toolName;
	private String toolNameId;
	private String position;
	private String toolTypeName;
	private Long count;
	private String[] rfids;
	private String[] tooCodes;
	private String[] infoSigns;
	
	private String location;
	private List<String> locations;

	public RailToolInfoVo() {
		super();
	}

	public RailToolInfoVo(long id, String toolCode, String toolName, String toolImg, String rfid, String modelCode,
			String whseCode, String inWhse, String whseName) {
		super();
		this.id = id;
		this.toolCode = toolCode;
		this.toolName = toolName;
		this.toolImg = toolImg;
		this.rfid = rfid;
		this.modelCode = modelCode;
		this.whseCode = whseCode;
		this.inWhse = inWhse;
		this.whseName = whseName;
	}

	public RailToolInfoVo(long id, String toolCode, String toolName, String toolImg, String rfid, String modelCode,
			String whseCode, String inWhse, String whseName, String modelName, String toolTypeName) {
		super();
		this.id = id;
		this.toolCode = toolCode;
		this.toolName = toolName;
		this.toolImg = toolImg;
		this.rfid = rfid;
		this.modelCode = modelCode;
		this.whseCode = whseCode;
		this.inWhse = inWhse;
		this.whseName = whseName;
		this.modelName = modelName;
		this.toolTypeName = toolTypeName;
	}

	public String getWhseCode() {
		return whseCode;
	}



	public List<String> getLocations() {
		return locations;
	}

	public void setLocations(List<String> locations) {
		this.locations = locations;
	}

	public String getLocation() {
		return location;
	}

	public String[] getInfoSigns() {
		return infoSigns;
	}

	public void setInfoSigns(String[] infoSigns) {
		this.infoSigns = infoSigns;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String[] getRfids() {
		return rfids;
	}

	public void setRfids(String[] rfids) {
		this.rfids = rfids;
	}

	public String[] getTooCodes() {
		return tooCodes;
	}

	public void setTooCodes(String[] tooCodes) {
		this.tooCodes = tooCodes;
	}

	public Long getCount() {
		return count;
	}
	

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getToolNameId() {
		return toolNameId;
	}

	public void setToolNameId(String toolNameId) {
		this.toolNameId = toolNameId;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public void setWhseCode(String whseCode) {
		this.whseCode = whseCode;
	}

	public String getInWhse() {
		return inWhse;
	}

	public void setInWhse(String inWhse) {
		this.inWhse = inWhse;
	}

	public String getWhseName() {
		return whseName;
	}

	public void setWhseName(String whseName) {
		this.whseName = whseName;
	}

	public String getToolTypeName() {
		return toolTypeName;
	}

	public void setToolTypeName(String toolTypeName) {
		this.toolTypeName = toolTypeName;
	}

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	

	public String getToolImg() {
		return toolImg;
	}



	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
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



	public String getRfid() {
		return rfid;
	}



	public void setRfid(String rfid) {
		this.rfid = rfid;
	}



	public void setToolImg(String toolImg) {
		this.toolImg = toolImg;
	}



	public RailToolInfoVo(long id, String toolCode, String toolName, String toolImg, String rfid) {
		super();
		this.id = id;
		this.toolCode = toolCode;
		this.toolName = toolName;
		this.toolImg = toolImg;
		this.rfid = rfid;
	}

	public RailToolInfoVo(long id, String toolCode, String toolName, String toolImg, String rfid, String modelCode) {
		super();
		this.id = id;
		this.toolCode = toolCode;
		this.toolName = toolName;
		this.toolImg = toolImg;
		this.rfid = rfid;
		this.modelCode = modelCode;
	}



	

	
	

}

