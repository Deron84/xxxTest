<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/page/system/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/ux/RowExpander.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/mchnt/T20105.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/mchnt/MchntBaseDetailS.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/ux/Radiogroup.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/mchnt/SelectTermInfo.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/dwr/interface/T3010106.js"></script>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/ext/ux/animated-dataview.css">
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/ux/DataViewTransition.js"></script>

</head>
<body>
<!-- 商品正式表导出 -->
<div id="report" style="position: absolute;left: 25%;top: 10%"></div>
<form id="reportForm">
</form>
</body>
</html>                                                                                                                                                                             