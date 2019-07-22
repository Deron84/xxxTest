package com.rail.zWebSocket.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Else;
import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketImpl;

import com.rail.zWebSocket.EquipConInfo;
import com.rail.zWebSocket.PubTcp;
import com.rail.zWebSocket.PubUtil;

public class wsOrder extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
		 * Constructor of the object.
		 */
	public wsOrder() {
		super();
	}

	/**
		 * Destruction of the servlet. <br>
		 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
		 * The doGet method of the servlet. <br>
		 *
		 * This method is called when a form has its tag value method equals to get.
		 * 
		 * @param request the request send by the client to the server
		 * @param response the response send by the server to the client
		 * @throws ServletException if an error occurred
		 * @throws IOException if an error occurred
		 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	/**
		 * The doPost method of the servlet. <br>
		 *
		 * This method is called when a form has its tag value method equals to post.
		 * 
		 * @param request the request send by the client to the server
		 * @param response the response send by the server to the client
		 * @throws ServletException if an error occurred
		 * @throws IOException if an error occurred
		 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//在这里进行相应的业务操作
//		String wsOrder=request.getParameter("wsOrder");
//		String equipCode=PubUtil.parOderEquipCode(wsOrder);
//		EquipConInfo equipConInfo=PubUtil.EQUIP_IN_CON.get(equipCode);
//		int writeNum=PubUtil.wsWriteDataFromChannel(wsOrder, equipConInfo.getWebSocketImpl());
//		System.out.println(writeNum);

		//设备信息的连接信息情况
		Map<String,Boolean> equipConStats=new HashMap<String,Boolean>();
		if (PubUtil.EQUIP_IN_CON!=null) {
			for (Entry<String, EquipConInfo> tmp : PubUtil.EQUIP_IN_CON.entrySet()) {
				equipConStats.put(tmp.getKey(), PubUtil.wsChannelIsAvailable(tmp.getKey()));
			}
		}
		//APP连接信息
		Map<String,Boolean> appConStats=new HashMap<String,Boolean>();
		if (PubTcp.PUB_TCT_CON!=null) {
			for (Entry<String, WebSocket> tmp : PubTcp.PUB_TCT_CON.entrySet()) {
				appConStats.put(tmp.getKey(), PubTcp.equipInConnect(tmp.getKey()));
			}
		}
		if(equipConStats.size()==0){
			request.setAttribute("flag","终端暂无连接");
		}
		if(appConStats.size()==0){
			request.setAttribute("flag1","app暂无连接");
		}
		//这里用来发送数据
		if (request.getParameter("cTest")!=null&&(request.getParameter("equipCode")!=null && !"".equalsIgnoreCase(request.getParameter("equipCode")))) {
			String equipCode=request.getParameter("equipCode");
			String order=request.getParameter("order");
			if (PubTcp.PUB_TCT_CON.get(equipCode)!=null) {
				if (PubTcp.equipInConnect(equipCode)) {
					if (PubTcp.sendMessage(equipCode, order, true)) {
						request.setAttribute("Msg","发送信息成功！");
					}else{
						request.setAttribute("Msg","异常发送信息失败！");
					}
				}else {
					request.setAttribute("Msg","该端口的连接已被关闭！");
				}
			}else {
				request.setAttribute("Msg","没有该端口的连接！");
			}
			
		}
		
		request.setAttribute("equipConStats",equipConStats);
		request.setAttribute("appConStats",appConStats);
		request.getRequestDispatcher("/page/tools/T1.jsp").forward(request,response);
	}

	/**
		 * Initialization of the servlet. <br>
		 *
		 * @throws ServletException if an error occurs
		 */
	public void init() throws ServletException {
		// Put your code here
	}

}
