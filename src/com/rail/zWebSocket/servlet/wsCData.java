package com.rail.zWebSocket.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
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

import com.huateng.system.util.ContextUtil;
import com.rail.bo.access.T120101BO;
import com.rail.bo.tools.TSendMsgSocketBO;
import com.rail.dao.iface.access.RailAccessWarnDao;
import com.rail.po.access.RailAccessInfo;
import com.rail.po.access.RailAccessOptlog;
import com.rail.po.access.RailAccessWarn;
import com.rail.zWebSocket.EquipConInfo;
import com.rail.zWebSocket.PubTcp;
import com.rail.zWebSocket.PubUtil;

public class wsCData extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
		 * Constructor of the object.
		 */
	public wsCData() {
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
		TSendMsgSocketBO tSendMsgSocketBO = (TSendMsgSocketBO) ContextUtil.getBean("TSendMsgSocketBO");
		String equipCode="8808";
		RailAccessInfo railAccessInfo=tSendMsgSocketBO.getAccessCodeFromEquipCode(equipCode);
		String accessCode=railAccessInfo.getAccessCode();
		
		RailAccessWarn railAccessWarn = new RailAccessWarn(accessCode, "1", "门禁意外打开", "0",new Date());
		RailAccessWarnDao railAccessWarnDao = (RailAccessWarnDao) ContextUtil.getBean("RailAccessWarnDao");
		railAccessWarnDao.add(railAccessWarn);
		//TODO snedsocket(railAccessWarn.getId,4)
		//开门记录
		RailAccessOptlog railAccessOptlog=new RailAccessOptlog();
		railAccessOptlog.setAccessCode(accessCode);
		railAccessOptlog.setEquipCode(equipCode);
		railAccessOptlog.setOpenSign("3");
		railAccessOptlog.setAddDate(new Date());
		T120101BO t120101BO=(T120101BO) ContextUtil.getBean("T120101BO");
		t120101BO.saveRailAccessOptlog(railAccessOptlog);	
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
