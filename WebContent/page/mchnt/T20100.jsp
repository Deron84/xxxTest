<%@page import="java.util.Random"%>
<%@page import="com.huateng.system.util.CommonFunction"%>
<%@page import="com.huateng.common.Operator"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/page/system/include.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/ext/ux/RowExpander.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/ext/upload/swfupload.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/ext/resources/css/UploadDialog.css">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/ext/upload/UploadDialog.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/ui/mchnt/T20100.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/ext/ux/Radiogroup.js"></script>


<script type="text/javascript"
	src="<%=request.getContextPath()%>/dwr/interface/T20100.js"></script>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/ext/ux/animated-dataview.css">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/ext/ux/DataViewTransition.js"></script>


<script type="text/javascript">
	<%Operator operator = (Operator) request.getSession().getAttribute("operator");%>
		
	var imagesId;
	
	var opr = "<%=operator.getOprId()%>";

	function resetImagesId() {
		var date = new Date();
		var full = date.getFullYear().toString() + date.getMonth().toString()
				+ date.getDay().toString() + date.getHours().toString()
				+ date.getMinutes().toString() + date.getSeconds().toString()
				+ date.getUTCMilliseconds();
		imagesId = opr + full;
	}

	resetImagesId();
</script>

<style type="text/css">
.ext-ie .x-form-check-wrap INPUT {
	height: 20px;
}

.x-form-check-wrap {
	height: 24px;
	line-height: 22px;
}
</style>

</head>
<body>
	<img id="showBigPic" src="" width="0" height="0"
		style="position: absolute; left: 0; top: 0;" />
	<!-- 新增商户信息 -->
</body>
</html>