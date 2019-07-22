package com.rail.zWebSocket;

import java.io.Serializable;

/**
 * Title: EquipStateData
 * Description:  用来管理设备的目前状态
 * @author Zhaozc 
 * @date 下午5:45:35 
 */
public class EquipStateData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String equipCode;//设备编码，例如8604 2位组成 通过页面终端等设定
	private int acsOpenState;//门闸开关门状态  1标识开门状态；0标识关门状态；
	public String getEquipCode() {
		return equipCode;
	}
	public void setEquipCode(String equipCode) {
		this.equipCode = equipCode;
	}
	public int getAcsOpenState() {
		return acsOpenState;
	}
	public void setAcsOpenState(int acsOpenState) {
		this.acsOpenState = acsOpenState;
	}
	public EquipStateData(String equipCode, int acsOpenState) {
		super();
		this.equipCode = equipCode;
		this.acsOpenState = acsOpenState;
	}
	
}
