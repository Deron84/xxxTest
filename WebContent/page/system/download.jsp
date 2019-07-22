<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.BufferedOutputStream"%>

<%@page import="com.huateng.common.Constants"%>
<%
	
	String downLoadFile = request.getParameter("downLoadFile");
	String downLoadFileName = request.getParameter("downLoadFileName");
	String downLoadFileType = request.getParameter("downLoadFileType");
	
	downLoadFileName = new String(downLoadFileName.getBytes("ISO-8859-1"),"UTF-8");
	
	if(Constants.REPORT_EXCELMODE.equals(downLoadFileType))
		response.setContentType("application/vnd.ms-excel");
	else if(Constants.REPORT_PDFMODE.equals(downLoadFileType))
		response.setContentType("application/pdf");
	
	response.setHeader("Content-Disposition", "attachment; filename=\""
	        + new String(downLoadFileName.getBytes("gb2312"), "ISO-8859-1") + "\"");

	FileInputStream fileInputStream = new FileInputStream(downLoadFile);
	BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
	
	byte[] buffer = new byte[8192];
	int bytesRead = 0;
	while ((bytesRead = fileInputStream.read(buffer, 0, 8192)) != -1) {
		bufferedOutputStream.write(buffer,0,bytesRead);
	}
	bufferedOutputStream.flush();
	fileInputStream.close();
	bufferedOutputStream.close();
	out.clear();
	out = pageContext.pushBody();
%>