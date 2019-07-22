<%@page import="com.huateng.system.util.CommonFunction"%>
<%@page import="com.huateng.system.util.InformationUtil"%>
<%@page import="com.huateng.common.Operator"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/page/system/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>清算入账</title>
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/upload/swfupload.js"></script>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/ext/resources/css/UploadDialog.css">
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/upload/UploadDialog.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/dwr/interface/T80205.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/settle/T80205.js"></script>

<script type="text/javascript">

</script>

</head>


<body>
<!-- 清算入账 -->
<div id="reportPanel" style="position: absolute;left: 25%;top: 10%"></div>
<form id="reportForm">
</form>
</body>
</html>