package com.rail.zWebSocket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.java_websocket.WebSocket;

public class PubTcp {
	//这里存放在库的所有连接信息
	public static Map<String, WebSocket> PUB_TCT_CON=new HashMap<String, WebSocket>();
	//这里存储所有未发送的信息列表
	public static Map<String, List<String>> PUB_MESSAGE = new HashMap<String, List<String>>();
	
	/**
	 * @Description: 判断当前PDA设备的连接状态 
	 * @param @param equipCode
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public static boolean equipInConnect(String equipCode){
		if (PUB_TCT_CON!=null &&PUB_TCT_CON.size()>0) {
			if (PUB_TCT_CON.get(equipCode)!=null) {
				WebSocket wSocket=PUB_TCT_CON.get(equipCode);
				if (wSocket.isOpen()) {
					System.out.println(equipCode+" socket  连接成功");
					return true;
				}else{
					System.out.println(equipCode+" socket  未连接");
				}
			}
		}
		System.out.println(equipCode+" socket  未连接==== Sever");
		return false;
	}
	
	/**
	 * @Description: 这个方法用来执行对应的发送信息的指令 
	 * @param @param equipCode
	 * @param @param message
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public static boolean sendMessage(String equipCode,String message,boolean saveFlag){//saveFlage=true一般情况下
		//常规保存操作
		if (saveFlag) {
			if (PUB_MESSAGE.get(equipCode)!=null) {
				List<String> messageList=PUB_MESSAGE.get(equipCode);
				//不重复保存
				if (!messageList.contains(message)) {
					messageList.add(message);
					PUB_MESSAGE.put(equipCode, messageList);
				}
			}else {
				List<String> messageList=new ArrayList<String>();
				messageList.add(message);
				PUB_MESSAGE.put(equipCode, messageList);
			}
		}
		
		if (equipInConnect(equipCode)) {
			WebSocket wSocket=PUB_TCT_CON.get(equipCode);
			wSocket.send(message);
			List<String> messageList=PUB_MESSAGE.get(equipCode);
			messageList.remove(message);
			PUB_MESSAGE.put(equipCode, messageList);
			System.out.println(equipCode+" socket 发送成功，发送信息>>>"+message);
			return true;
		}
		return false;
	}
	public static void main(String[] args) {
		List<String> tStrList=new ArrayList<String>();
		tStrList.add("12");
		tStrList.add("12");
		tStrList.add("23");
		tStrList.remove("23");
		System.out.println(tStrList.toString());
	}
}
