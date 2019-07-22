package com.rail.zWebSocket;

import java.net.URISyntaxException;

public class TestClient {

	public static MsgWebSocketClient socketClient = null;
	
	//是否要心跳检测连接
	private static boolean flag = false;
	public static void initClient(MsgWebSocketClient client) {
		socketClient = client;
		if(socketClient!=null) {
			socketClient.connect();
		}
		int i=1000;
		while(flag) {
			socketClient.send("测试websocket。。。"+(i--));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(i == 0) {
				flag = false;
			}
		}
	}
	
	

	/**
	 * @Description: 通过当前方法来执行连接操作，心跳正常true表示正常连接；false表示无法连接服务器 
	 * @param @param wsAddress
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public static boolean initConnect(String wsAddress,String equipCode){
		//首先判断跟Server端的连接是否正常
		//已经断开的情况下进行重新连接
		if (socketClient==null) {
	        try {
	        	initClient(new MsgWebSocketClient(wsAddress));
	    		int i=10;//10秒尝试连接
	    		boolean sendMsgInit=true;
	    		while(sendMsgInit) {
	    			if (socketClient.isOpen()) {
	    				socketClient.send("equipCode:"+equipCode);
	    				sendMsgInit = false;
					}else {
						i--;
						Thread.sleep(1000);
					}
	    			if(i == 0) {
	    				sendMsgInit = false;
	    			}
	    		}
	        	
	        	return true;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }			
		}else {
			if (socketClient.isClosed()) {
		        try {
		        	initClient(new MsgWebSocketClient(wsAddress));
		    		int i=10;//30秒尝试连接
		    		boolean sendMsgInit=true;
		    		while(sendMsgInit) {
		    			if (socketClient.isOpen()) {
		    				socketClient.send("equipCode:"+equipCode);
		    				flag = false;
						}else {
							i--;
							Thread.sleep(1000);
						}
		    			if(i == 0) {
		    				flag = false;
		    			}
		    		}
		        	return true;
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
			}
		}
		return false;//异常情况无法连接主服务器
	}
	
	/**
	 * @Description: 通过当前方法来执行连接操作，心跳正常true表示正常连接；false表示无法连接服务器 
	 * @param @param wsAddress
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public boolean TestConnect(String wsAddress){
		//首先判断跟Server端的连接是否正常
		//已经断开的情况下进行重新连接
		if (socketClient==null) {
	        try {
	        	initClient(new MsgWebSocketClient(wsAddress));
	    		int i=3;//3秒尝试连接
	    		boolean sendMsgInit=true;
	    		while(sendMsgInit) {
	    			if (socketClient.isOpen()) {
	    				socketClient.close();
	    				return sendMsgInit;
					}else {
						i--;
						Thread.sleep(500);
					}
	    			if(i == 0) {
	    				sendMsgInit = false;
	    			}
	    		}
	        	return sendMsgInit;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }			
		}else {
			if (socketClient.isClosed()) {
		        try {
		        	initClient(new MsgWebSocketClient(wsAddress));
		    		int i=3;//3秒尝试连接
		    		boolean sendMsgInit=true;
		    		while(sendMsgInit) {
		    			if (socketClient.isOpen()) {
		    				socketClient.close();
		    				return sendMsgInit;
						}else {
							i--;
							Thread.sleep(1000);
						}
		    			if(i == 0) {
		    				sendMsgInit = false;
		    			}
		    		}
		        	return sendMsgInit;
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
			}
		}
		return false;//异常情况无法连接主服务器
	}
	public static void main(String[] args) {
		String uri = "ws://127.0.0.1:1169/";
        String input = "2700";
//        initConnect(uri,input);
        TestClient testClient=new TestClient();
        System.out.println("》》》》》》》》》"+testClient.TestConnect(uri));
//        try {
//        	//开启初始化测试端口
//        	initClient(new MsgWebSocketClient(uri));
//        	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//            do {
//                input = br.readLine();
//                if (!input.equals("exit"))
//                	socketClient.send(input);
//                	socketClient.send(new byte[5]);
//                	socketClient.send(ByteBuffer.allocate(10));
//                	socketClient.sendPing();
//            } while (!input.equals("exit"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        
	}

}
