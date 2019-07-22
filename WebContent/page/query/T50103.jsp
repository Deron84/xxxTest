<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.huateng.system.util.CommonFunction"  %>
<%@ include file="/page/system/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/query/T50103.js"></script>
<script type="text/javascript">
	var agoDate = '<%=CommonFunction.get180DaysAgo()%>';
	var nowDate = '<%=CommonFunction.getCurrentDate()%>';
	<%
		Integer recordNum = Integer.parseInt((String)application.getAttribute("recordNum"));
	%>
	var recordNum = <%= recordNum %>;
</script>
</head>
<body>
<!-- 历史交易查询 -->
</body>
</html>