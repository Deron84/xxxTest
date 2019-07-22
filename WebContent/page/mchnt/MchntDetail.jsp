<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/page/system/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>

<%@page import="java.util.List"%>
<%@page import="com.huateng.po.TblDivMchntTmp"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商户详细信息</title>
</head>
<body>
	<script type="text/javascript">
	var mchntInfo = new Array();
	var mchntDivInfoArr = new Array();
	<%
	Map<String,String> mchntInfo = (Map)session.getAttribute("mchntInfo");
	List<TblDivMchntTmp> mchntDivInfo = (List)session.getAttribute("mchntDivInfo");
	Iterator<String> iter = mchntInfo.keySet().iterator();
	String key = null;
	String value = null;
	while(iter.hasNext()) {
		key = iter.next();
		value = mchntInfo.get(key);
	%>
	mchntInfo['<%= key%>'] = '<%= value%>';
	<%	
	}
	Iterator<TblDivMchntTmp> divIter = mchntDivInfo.iterator();
	TblDivMchntTmp tblDivMchntTmp = null;
	while(divIter.hasNext()) {
		tblDivMchntTmp = divIter.next();
	%>
	mchntDivInfoArr['<%= tblDivMchntTmp.getId().getDivNo()%>'] = '<%= tblDivMchntTmp.getId().getDivNo()%>';
	<%
	}
	%>
	// 设置为同步，使下拉列表信息能够回显
	dwr.engine.setAsync(false);
</script>
	<script type="text/javascript"
		src="<%= request.getContextPath()%>/ui/mchnt/MchntDetail.js"></script>
	<!-- 商户信息维护 -->
</body>
</html>