package com.rail.zWebSocket;

import java.io.Serializable;

public class BaseData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String equipTypeVersion;//设备类型版本，例如  1111 2位组成
	private String equipCode;//设备编码，例如8604 2位组成 通过页面终端等设定
	private String equipGrop;//设备组号，例如01
	private String equipPwd;//设备密码，例如1234
	private String packSerial;//包序号，例如BC
	//以上信息为发送时特定的数据值
	private String dataLength;//数据长度，例如00 12
	private String dataALength;//数据1长度，例如09
	private String dataAOrderCode;//命令编码00表示心跳包 检测主要检测这个值的；01表示预警上传
	
	public String getEquipTypeVersion() {
		return equipTypeVersion;
	}
	public void setEquipTypeVersion(String equipTypeVersion) {
		this.equipTypeVersion = equipTypeVersion;
	}
	public String getEquipCode() {
		return equipCode;
	}
	public void setEquipCode(String equipCode) {
		this.equipCode = equipCode;
	}
	public String getEquipGrop() {
		return equipGrop;
	}
	public void setEquipGrop(String equipGrop) {
		this.equipGrop = equipGrop;
	}
	public String getEquipPwd() {
		return equipPwd;
	}
	public void setEquipPwd(String equipPwd) {
		this.equipPwd = equipPwd;
	}
	public String getPackSerial() {
		return packSerial;
	}
	public void setPackSerial(String packSerial) {
		this.packSerial = packSerial;
	}
	public String getDataLength() {
		return dataLength;
	}
	public void setDataLength(String dataLength) {
		this.dataLength = dataLength;
	}
	public String getDataALength() {
		return dataALength;
	}
	public void setDataALength(String dataALength) {
		this.dataALength = dataALength;
	}
	public String getDataAOrderCode() {
		return dataAOrderCode;
	}
	public void setDataAOrderCode(String dataAOrderCode) {
		this.dataAOrderCode = dataAOrderCode;
	}
	public BaseData(String equipTypeVersion, String equipCode, String equipGrop, String equipPwd, String packSerial,
			String dataLength, String dataALength, String dataAOrderCode) {
		super();
		this.equipTypeVersion = equipTypeVersion;
		this.equipCode = equipCode;
		this.equipGrop = equipGrop;
		this.equipPwd = equipPwd;
		this.packSerial = packSerial;
		this.dataLength = dataLength;
		this.dataALength = dataALength;
		this.dataAOrderCode = dataAOrderCode;
	}
	/**
	 * @Description: 返回发送指令的头信息 
	 * @param @return
	 * @return String
	 * @throws
	 */
	public String getBaseHeaderData(){
		return getEquipTypeVersion()+getEquipCode()+getEquipGrop()+getEquipPwd()+getPackSerial();
	}

}
