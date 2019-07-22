<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.io.*"%>
<%@page import="com.huateng.common.Constants"%>
<%@page import="com.huateng.system.util.CommonFunction"%>
<%@page import="com.huateng.struts.query.ExcelName"%>
<%@page import="java.net.URLEncoder"%>
<%
	String key = request.getParameter("key");//配置文件的key
	String downLoadFileName = CommonFunction.getCurrentDateTime()+Constants.DEFAULT+ExcelName.getValue(key);
	String downLoadFile = request.getParameter("path");
	String encoding = System.getProperty("file.encoding"); 
	File file = null;
	try {

		response.setContentType("application/vnd.ms-excel");

		response.setHeader("Content-Disposition",
				"attachment; filename=\""
						+ URLEncoder.encode(downLoadFileName, "UTF-8")
						+ "\"");
		file = new File(downLoadFile);
		
		FileInputStream fileInputStream = new FileInputStream(file);
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
				response.getOutputStream());

		byte[] buffer = new byte[8192];
		int bytesRead = 0;
		while ((bytesRead = fileInputStream.read(buffer, 0, 8192)) != -1) {
			bufferedOutputStream.write(buffer, 0, bytesRead);
		}
		bufferedOutputStream.flush();
		fileInputStream.close();
		bufferedOutputStream.close();
		out.clear();
		out = pageContext.pushBody();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if (file != null && file.exists()) {
			file.delete();//删除临时文件
		}
	}
%>