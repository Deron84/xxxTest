<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/page/system/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.huateng.po.TblDivMchntTmp"%>
<%@page import="java.util.Iterator"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改商户信息</title>
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
<script type="text/javascript"
	src="<%= request.getContextPath()%>/dwr/interface/T20100.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%= request.getContextPath()%>/ext/resources/css/Spinner.css">

<script type="text/javascript"
	src="<%= request.getContextPath()%>/ext/upload/swfupload.js"></script>
<script type="text/javascript"
	src="<%= request.getContextPath()%>/ext/upload/UploadDialog.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%= request.getContextPath()%>/ext/resources/css/UploadDialog.css">

<script type="text/javascript"
	src="<%= request.getContextPath()%>/ui/mchnt/T2010102.js"></script>
<script type="text/javascript"
	src="<%= request.getContextPath()%>/ext/ux/Radiogroup.js"></script>

<link rel="stylesheet" type="text/css"
	href="<%= request.getContextPath()%>/ext/ux/animated-dataview.css">
<script type="text/javascript"
	src="<%= request.getContextPath()%>/ext/ux/DataViewTransition.js"></script>


<script type="text/javascript">
	var mchntId = '<%=request.getParameter("mchntId") %>';
	var imagesId = mchntId;
</script>
<style type="text/css">
#load-mask {
	position: absolute;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	z-index: 20000;
	background-color: white;
}

#load {
	position: absolute;
	left: 45%;
	top: 45%;
	z-index: 20001;
	vertical-align: middle;
	text-align: center;
	text-valign: middle;
	outline-width: 2;
	font-family: sans-serif;
}

.upload {
	background-image: url(<%= request.getContextPath ()%>/ext/resources/images/upload_16.png)
		!important;
}

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
	<div id="load-mask">
		<div id="load">
			<img
				src="<%= request.getContextPath()%>/ext/resources/images/loading_data.png"
				width='20' height="20">正在为您准备数据，请稍后......
		</div>
	</div>
	<!-- 商户信息修改 -->
</body>
</html>