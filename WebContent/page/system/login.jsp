<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/system/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<title>铁路上道作业安全智能管理系统</title>
<%
//	String oprId = (String)request.getSession().getAttribute("oprId");
//	if(oprId != null&&!oprId.equals(""))
//		response.sendRedirect(request.getContextPath()+"/redirect.asp");
 %>
<link rel="Shortcut Icon" href="<%= request.getContextPath()%>/ext/resources/images/tielu.ico" type="image/x-icon" />
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/system/login.js"></script>
<style type="text/css">
	.key {
		background-image: url(<%= request.getContextPath()%>/ext/resources/images/key_16.png) !important;
	}
    body {
     height: 100%;width: 100%;
     background: url(<%= request.getContextPath()%>/images/login-bg.png) no-repeat;
     background-size:cover;
    }
</style>
</head>
<body>
<div></div>
</body>
</html>