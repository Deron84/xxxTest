<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/page/system/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test wsOrder</title>

</head>
<body style="text-align: center;line-height: 200px;">
	<%--<form action="<%= request.getContextPath()%>/servlet/wsOrder" method="POST">
		指令: <input type="text" name="wsOrder"> <br />
		<input style="width:100px;height:50px;" type="submit" value="测试" />
	</form>--%>
	<%--<input value="${equipConStats}">--%>
	<%--<div>--%>
		<%--<table border="1">--%>
			<%--<tr>--%>
				<%--<th>终端号</th>--%>
				<%--<th>连接状态</th>--%>
			<%--</tr>--%>
			<%--<tr>--%>
				<%--<c:forEach  var="y" items="${list}">--%>
					<%--<td>${y.equipCode} </td>--%>
					<%--<td> ${y.status} </td>--%>
				<%--</c:forEach >--%>
			<%--</tr>--%>
		<%--</table>--%>
	<%--</div>--%>

	<%--${list}--%>
	<p style="height: 30px;margin-top: 10px;">终端的连接信息：</p>
	<ol>
		<c:forEach  var="y" items="${equipConStats}">
			<li style="height: 30px;margin-top: 10px;">设备号：${y.key}  ||  连接状态：${y.value}</li>
		</c:forEach >
	</ol>
	<li style="height: 30px;margin-top: 10px;">${flag}</li>
	<p style="height: 30px;margin-top: 10px;">========================================</p>
	<p style="height: 30px;margin-top: 10px;">app的连接信息：</p>
	<ol>
		<c:forEach  var="y" items="${appConStats}">
			<li style="height: 30px;margin-top: 10px;">设备号：${y.key}  ||  连接状态：${y.value}</li>
		</c:forEach >
	</ol>
	<li style="height: 30px;margin-top: 10px;">${flag1}</li>

	<form style="margin-top: 100px;line-height: 30px;" action="<%= request.getContextPath()%>/servlet/wsOrder" method="POST">
		<input type="hidden" name="cTest" value="111"> <br />
		设备号: <input type="text" style="margin-top: 10px;" name="equipCode"> <br />
		指令: <input type="text" style="margin-top: 10px;" name="order"> <br />
		<input style="width:100px;height:50px;margin-top: 10px;" type="submit" value="发送" />
	</form>
	<li style="height: 30px;margin-top: 10px;">${Msg}</li>
</body>
</html>