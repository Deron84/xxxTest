<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/page/system/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>终端信息便捷添加</title>
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/ux/RowExpander.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/upload/swfupload.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/upload/UploadDialog.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/common/common.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/pos/T30102.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/dwr/interface/T30101.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/mchnt/SelectTermInfo.js"></script>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/ext/resources/css/UploadDialog.css">
<style type="text/css">
	.pos {
		background-image: url(<%= request.getContextPath()%>/ext/resources/images/pos_16.png) !important;
	}
	.edit {
		background-image: url(<%= request.getContextPath()%>/ext/resources/images/edit_16.png) !important;
	}
	.copy {
		background-image: url(<%= request.getContextPath()%>/ext/resources/images/copy.png) !important;
	}
	.stop {
		background-image: url(<%= request.getContextPath()%>/ext/resources/images/stop_16.png) !important;
	}
	.recover {
		background-image: url(<%= request.getContextPath()%>/ext/resources/images/reset_16.png) !important;
	}
	.upload {
		background-image: url(<%= request.getContextPath()%>/ext/resources/images/upload_16.png) !important;
	}
</style>
</head>
<body>
<!-- 终端便捷添加 -->
</body>
</html>