<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/page/system/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>全辖零交易和零（负）收益终端明细月报表（终端收益-终端支出≦0）</title>
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/query/T50914.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/custom/monthfield.js"></script>
</head>
<body>
<!-- 全辖零交易和零（负）收益终端明细月报表（终端收益-终端支出≦0） -->
<div id="report" style="position: absolute;left: 25%;top: 10%"></div>
<form id="reportForm">
</form>
</body>
</html>