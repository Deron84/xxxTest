<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/page/system/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/upload/swfupload.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/upload/UploadDialog.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/dwr/interface/T40202.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/risk/T40202.js"></script>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/ext/resources/css/UploadDialog.css">
<style type="text/css">
	.mchnt {
		background-image: url(<%= request.getContextPath()%>/ext/resources/images/risk_mchnt_16.png) !important;
	}
	.upload {
		background-image: url(<%= request.getContextPath()%>/ext/resources/images/upload_16.png) !important;
	}
</style>
</head>
<body>
<!-- 商户黑名单管理 -->
</body>
</html>