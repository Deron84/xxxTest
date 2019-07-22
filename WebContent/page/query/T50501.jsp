<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/page/system/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商户营销活动报表</title>
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/custom/monthfield.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/query/T50501.js"></script>
</head>
<body>
<!-- 商户营销活动报表 -->
<div id="report" style="position: absolute;left: 25%;top: 10%"></div>
<form id="reportForm">
</form>
</body>
</html>