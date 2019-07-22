<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/page/system/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="<%= request.getContextPath()%>/ui/query/T51008.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath()%>/ext/ux/monthPick.js"></script>
		<title>支付宝分润统计查询</title>
		<script type="text/javascript">
		<%
			Integer recordNum = Integer.parseInt((String)application.getAttribute("recordNum"));
		%>
		var recordNum = <%= recordNum %>;
		</script>
	</head>
	<body>
		<!-- 支付宝分润统计查询 -->
	</body>
</html>