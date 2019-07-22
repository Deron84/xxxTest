package com.rail.zWebSocket;

import java.io.Serializable;

import org.java_websocket.WebSocketImpl;

public class EquipConInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	private WebSocketImpl webSocketImpl;
	private int[] defenceState;//各分区状态；这里对应1-6分区
	private BaseData baseData;//心跳时表示心跳相关的信息保存 后面会根据baseData来做相应的预警
	private long conTime;//链接时间
	
	public EquipConInfo() {
		super();
	}
	public EquipConInfo(WebSocketImpl webSocketImpl, int[] defenceState, BaseData baseData, long conTime) {
		super();
		this.webSocketImpl = webSocketImpl;
		this.defenceState = defenceState;
		this.baseData = baseData;
		this.conTime = conTime;
	}
	public WebSocketImpl getWebSocketImpl() {
		return webSocketImpl;
	}
	public void setWebSocketImpl(WebSocketImpl webSocketImpl) {
		this.webSocketImpl = webSocketImpl;
	}

	public long getConTime() {
		return conTime;
	}
	public void setConTime(long conTime) {
		this.conTime = conTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public BaseData getBaseData() {
		return baseData;
	}
	public void setBaseData(BaseData baseData) {
		this.baseData = baseData;
	}
	public int[] getDefenceState() {
		return defenceState;
	}
	public void setDefenceState(int[] defenceState) {
		this.defenceState = defenceState;
	}
	
	
}
