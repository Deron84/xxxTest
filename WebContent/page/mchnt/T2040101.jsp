<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/page/system/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="No-cache">
<meta http-equiv="Cache-Control"
	content="no-cache, must-revalidate,text/html; charset=UTF-8">
<meta http-equiv="Expires" content="0">
<title>添加商户信息</title>
<script type="text/javascript"
	src="<%= request.getContextPath()%>/ext/ux/SpinnerField.js"></script>
<script type="text/javascript"
	src="<%= request.getContextPath()%>/ext/ux/Spinner.js"></script>
<script type="text/javascript"
	src="<%= request.getContextPath()%>/ext/upload/swfupload.js"></script>
<script type="text/javascript"
	src="<%= request.getContextPath()%>/ext/upload/UploadButton.js"></script>
<script type="text/javascript"
	src="<%= request.getContextPath()%>/dwr/interface/T20101.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%= request.getContextPath()%>/ext/resources/css/Spinner.css">
<script type="text/javascript"
	src="<%= request.getContextPath()%>/ui/mchnt/T2040101.js"></script>
<script type="text/javascript">
//分期参数信息
var mchntDivArr = new Array();
var productDivArr = new Array();
var regExp = new RegExp(/^[0-9a-zA-Z]{20}$/);
</script>
</head>
<body>
	<!-- 商户信息添加 -->
</body>
</html>