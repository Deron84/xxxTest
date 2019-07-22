<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/page/system/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>民生支付渠道维护</title>
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/mchnt/T20109.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/ux/LovCombo.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ui/common/common.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/ext/ux/RowExpander.js"></script>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/ext/resources/css/LovCombo.css">
<script>
		var disid = '<%=request.getParameter("outMchntId") %>';
		if(disid=='null'){
			disid='';
		}
</script>
</head>
<body>
<!-- 民生支付渠道维护 -->
</body>
</html>