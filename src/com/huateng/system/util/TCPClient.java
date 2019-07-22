/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-8-19       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2010 Huateng Software, Inc. All rights reserved.
 *
 *       This software is the confidential and proprietary information of
 *       Shanghai HUATENG Software Co., Ltd. ("Confidential Information").
 *       You shall not disclose such Confidential Information and shall use it
 *       only in accordance with the terms of the license agreement you entered
 *       into with Huateng.
 *
 * Warning:
 * =============================================================================
 *
 */
package com.huateng.system.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Title:socket通信客户端
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-19
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class TCPClient {
	
	/** socket 通信实例*/
	private Socket socket = null;
	/** 默认超时时间 */
	private int timeout = 10000;
	/** socket通信输出流 */
	private OutputStream outputStream = null;
	/** socket输入流 */
	private InputStream inputStream = null;
	/** 返回数据 */
	private byte[] rspBytes = new byte[256];
	
	public TCPClient() {}
	
	public TCPClient(String ip,int port) throws UnknownHostException, IOException {
		socket = new Socket(ip,port);
		socket.setKeepAlive(true);
		socket.setSoTimeout(timeout);
	}
	
	/**
	 * 设置通信超时时间
	 * @param timeout
	 * @throws SocketException
	 * 2010-8-19下午01:35:37
	 */
	public void setTimeOut(int timeout) throws SocketException {
		this.socket.setSoTimeout(timeout);
	}
	
	/**
	 * 发送信息
	 * @param message
	 * @param len
	 * @throws Exception
	 * 2010-8-19下午01:35:50
	 */
	public void sendMessage(byte[] message,int len) throws Exception {

		if(socket == null) {
			throw new Exception("socket对象未初始化");
		}
		
		outputStream = socket.getOutputStream();
		
		outputStream.write(message,0,len);
		
		outputStream.flush();
	}
	
	/**
	 * 接收信息
	 * @return
	 * @throws Exception
	 * 2010-8-19下午01:35:57
	 */
	public byte[] receiveMessage() throws Exception {
		
		if(socket == null) {
			throw new Exception("socket对象未初始化");
		}
		
		inputStream = socket.getInputStream();
		
		inputStream.read(rspBytes);
		
		inputStream.close();
		
		return rspBytes;
	}
	
	/**
	 * 销毁对象
	 * 
	 * 2010-8-19下午01:36:04
	 */
	public void destroy() {
		this.outputStream = null;
		this.inputStream = null;
		this.socket = null;
	}
}
