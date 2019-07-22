<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.BufferedOutputStream"%>

<%@page import="com.huateng.common.Constants"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	String path = request.getParameter("path");
	
	String fileName = path.substring(path.lastIndexOf("/") + 1);
	String fileType = "";
	if (path.indexOf(".") != -1) {
		fileType = path.substring(path.lastIndexOf(".") + 1);
	}
	
	if ("txt".equalsIgnoreCase(fileType)){
		response.setContentType("text/plain");
	} else if ("xls".equalsIgnoreCase(fileType)){
		response.setContentType("application/vnd.ms-excel");
	} else if ("pdf".equalsIgnoreCase(fileType)){
		response.setContentType("application/pdf");
	} else {
		response.setContentType("application/octet-stream");
	}
	
	fileName = new String(fileName.getBytes("ISO-8859-1"),"UTF-8");
	
	response.setHeader("Content-Disposition", "attachment; filename=\""
	        + new String(fileName.getBytes("gb2312"), "ISO-8859-1") + "\"");

	FileInputStream fileInputStream = new FileInputStream(path);
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
</body>
</html>