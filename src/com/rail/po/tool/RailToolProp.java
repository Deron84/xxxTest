package com.rail.po.tool;

public class RailToolProp implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private long toolTypeCode;
	private String toolTypeName;
	private String toolName;
	private RailToolModel railToolModel;
	private String toolUnitName;
	
	
	public RailToolProp(long toolTypeCode, String toolTypeName, String toolName, RailToolModel railToolModel,
			String toolUnitName) {
		super();
		this.toolTypeCode = toolTypeCode;
		this.toolTypeName = toolTypeName;
		this.toolName = toolName;
		this.railToolModel = railToolModel;
		this.toolUnitName = toolUnitName;
	}
	public long getToolTypeCode() {
		return toolTypeCode;
	}
	public void setToolTypeCode(long toolTypeCode) {
		this.toolTypeCode = toolTypeCode;
	}
	public String getToolTypeName() {
		return toolTypeName;
	}
	public void setToolTypeName(String toolTypeName) {
		this.toolTypeName = toolTypeName;
	}
	public String getToolName() {
		return toolName;
	}
	public void setToolName(String toolName) {
		this.toolName = toolName;
	}
	public RailToolModel getRailToolModel() {
		return railToolModel;
	}
	public void setRailToolModel(RailToolModel railToolModel) {
		this.railToolModel = railToolModel;
	}
	public String getToolUnitName() {
		return toolUnitName;
	}
	public void setToolUnitName(String toolUnitName) {
		this.toolUnitName = toolUnitName;
	}
	
}
