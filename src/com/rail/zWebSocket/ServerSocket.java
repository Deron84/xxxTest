package com.rail.zWebSocket;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import groovy.ui.Console;
import sun.util.logging.resources.logging;

public class ServerSocket extends WebSocketServer{

    private ServerManager _serverManager;
    public boolean channelData=false;
    public ServerSocket(ServerManager serverManager,int port) throws UnknownHostException{
    	super(new InetSocketAddress(port));     
        _serverManager=serverManager;
    }
    @Override
    public void onClose(WebSocket socket, int message,
            String reason, boolean remote) {
        _serverManager.UserLeave(socket);
    }

    @Override
    public void onError(WebSocket socket, Exception message) {
        System.out.println("Socket Exception:"+message.toString());
    }

    @Override
    public void onMessage(WebSocket socket, String message) {
//        System.out.println("OnMessage:"+message.toString());
    	//连接之后默认发送一条信息，发送的是equipCode的信息
    	try {
    		if (message.contains("equipCode")) {
    			String equip[]=message.split(":");
    			PubTcp.PUB_TCT_CON.put(equip[1], socket);
    			//这里要做轮询处理
    			if (PubTcp.PUB_MESSAGE.get(equip[1])!=null) {
    				List<String> tmpList=PubTcp.PUB_MESSAGE.get(equip[1]);
    				if (tmpList!=null && tmpList.size()>0) {
    					for (int i = 0; i < tmpList.size(); i++) {
    						PubTcp.sendMessage(equip[1], tmpList.get(i),false);
    					}
    				}
    			}
    		}else if (message.contains("msgRev")) {
    			String revInfo[]=message.split(":");//接收到的消息格式 msgRev:equipCode:msg
    			List<String> tmpList=PubTcp.PUB_MESSAGE.get(revInfo[1]);
    			tmpList.remove(revInfo[2]);
    			PubTcp.PUB_MESSAGE.put(revInfo[1], tmpList);
			}
		} catch (Exception e) {
			System.out.println("接受APP信息出错！"+new Date());
		}

    }
    
    @Override
    public void onOpen(WebSocket socket, ClientHandshake handshake) {
    	//这里把所有跟我连接的放到一个公用的变量里吧
        System.out.println("Some one Connected...！！！！！！！！");
    }

	@Override
	public void onStart() {
		System.out.println("someOneBeginStart....");
		// TODO Auto-generated method stub
	}

	@Override
	protected void queue(WebSocketImpl ws) throws InterruptedException {
		//如果是chanelData的时候进行通过channel里面获取连接否则是tcp长连接指令
		if (channelData) {
			BlockingQueue<ByteBuffer> queneBlockingQueue = new LinkedBlockingQueue<ByteBuffer>(); 
			if (ws.inQueue!=null) {
				for (ByteBuffer tmp:ws.inQueue) {
					queneBlockingQueue.add(tmp);
				}
				ByteBuffer tmpBuffer=queneBlockingQueue.poll();
				try {
					System.out.println(">>Get Channel Data Time : "+new Date());
					PubUtil.parReqWsData(tmpBuffer, ws);					
				} catch (Exception e) {
					System.out.println(">>记录出错情况！"+new Date());
				}
//				System.out.println("当前已经储存的连接数量为："+PubUtil.EQUIP_IN_CON.size());
//				byte[] tmpBufferArray=tmpBuffer.array();
//				byte[] tmpBytes=new byte[tmpBuffer.remaining()];
//				for (int i = 0; i < tmpBuffer.remaining(); i++) {
//					tmpBytes[i]=tmpBufferArray[i];
//				}
//				String hexStr=PubUtil.parseByte2HexStr(tmpBytes);
//				System.out.println(">>>"+hexStr);
//				String tmpString=new String( tmpBuffer.array(), tmpBuffer.position(), tmpBuffer.remaining() );
//				System.out.println("!!!!>>>"+tmpString);
			}
		}
		super.queue(ws);
	}


	@Override
	public void onMessage(WebSocket conn, ByteBuffer message) {
		// TODO Auto-generated method stub
		super.onMessage(conn, message);
	}

}