package com.huateng.system.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

public class SocketConnect 
{
	private static Logger log = Logger.getLogger(SocketConnect.class);
	private String msg = null;
	private Socket socket = null;
	private String rsp;
	private OutputStream outputStream = null;
	private InputStream inputstream = null;
	public boolean isOver = false;
		
	public SocketConnect()
	{
		try{
			this.msg = "Hello World";
			//发送地址IP
			String ip = SysParamUtil.getParam("IP");
			//发送地址端口
			int port = Integer.parseInt(SysParamUtil.getParam("PORT"));
			socket = new Socket(ip,port);
			//设置超时
			socket.setSoTimeout(Integer.parseInt(SysParamUtil.getParam("TIMEOUT")));
			outputStream = socket.getOutputStream();
			inputstream = socket.getInputStream();
		}
		catch(ConnectException e) {
			log.error(e.getMessage(),e);
		}
		catch(IOException e) {
			log.error(e.getMessage(),e);
		}
	}
	/**
	 * 发送商户验证报文	
	 * @param txnCode
	 * @param mchntCd
	 * @param msg
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public SocketConnect(String msg)throws UnknownHostException, IOException {
		try{
			this.msg = msg;
			//发送地址IP
			String ip = SysParamUtil.getParam("IP");
			//发送地址端口
			int port = Integer.parseInt(SysParamUtil.getParam("PORT"));
			socket = new Socket(ip,port);
			//设置超时
			socket.setSoTimeout(Integer.parseInt(SysParamUtil.getParam("TIMEOUT")));
			outputStream = socket.getOutputStream();
			inputstream = socket.getInputStream();
		} catch(ConnectException e) {
			log.error(e.getMessage(),e);
		}
	}
			
	public void run() {
		try {
			sendMessage(msg);
			this.rsp = getMessage();
			log.info(rsp);
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
	}
			
	public void run(String encoding) {
		try {
			sendMessage(msg);
			this.rsp = getMessage(encoding);
			log.info(rsp);
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 发送报文
	 * @param req
	 * @throws IOException
	 */
	private void sendMessage(String req) throws IOException {
		byte[] reqByte = req.getBytes();
		outputStream.write(reqByte);
		outputStream.flush();
	}
			
	/**
	 * 获取报文
	 * @return
	 * @throws IOException 
	 */
	private String getMessage() throws IOException{
		BufferedReader bf = new BufferedReader(new InputStreamReader(inputstream));
		String str = null;
		while(!isOver)
		{
			str = bf.readLine();
			if(str != null)
				isOver = true;
		}
		return str;
	}	
	
	private String getMessage(String encoding) throws IOException{
		byte[] rspBytes = new byte[1024];
		int offsize = 0;
		int len = 0;
		while((len = inputstream.read()) != -1) {
			inputstream.read(rspBytes, offsize, len);
			offsize += len;
		}
		return new String(rspBytes,encoding);
	}
	/**
	 * @return the rsp
	 */
	public String getRsp() {
		return rsp;
	}
	/**
	 * @param rsp the rsp to set
	 */
	public void setRsp(String rsp) {
		this.rsp = rsp;
	}
	
	public void close() throws IOException
	{
		if(!socket.isClosed())
		{
			socket.shutdownInput();
			socket.shutdownOutput();
			socket.close();
		}
	}
}

