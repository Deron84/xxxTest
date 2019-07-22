package com.rail.zWebSocket;

import java.io.Serializable;

/**
 * Title: WarnData
 * Description:  后续通过主动提交对应的预警信息
 * @author Zhaozc 
 * @date 下午7:10:34 
 */
public class WarnData implements Serializable{
	//1111 8607 FF 1234 40 000B 0A 01 5CC11BA7 004A 01 1110 73
	private static final long serialVersionUID = 1L;
	//1111 8607 FF 1234 40 000B 0A 01
	private BaseData baseData;
	//5CC11BA7
	private String timeStamp;//时间戳
	//004A
	private String eventSerial;//事件号
	//01
	private String defenceArea;//防区
	//1110
	private String cid;//对应的CID码
	//73
	private String xor;//xor异或码
	public BaseData getBaseData() {
		return baseData;
	}
	public void setBaseData(BaseData baseData) {
		this.baseData = baseData;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getEventSerial() {
		return eventSerial;
	}
	public void setEventSerial(String eventSerial) {
		this.eventSerial = eventSerial;
	}
	public String getDefenceArea() {
		return defenceArea;
	}
	public void setDefenceArea(String defenceArea) {
		this.defenceArea = defenceArea;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getXor() {
		return xor;
	}
	public void setXor(String xor) {
		this.xor = xor;
	}
	public WarnData(BaseData baseData, String timeStamp, String eventSerial, String defenceArea, String cid,
			String xor) {
		super();
		this.baseData = baseData;
		this.timeStamp = timeStamp;
		this.eventSerial = eventSerial;
		this.defenceArea = defenceArea;
		this.cid = cid;
		this.xor = xor;
	}
	
}