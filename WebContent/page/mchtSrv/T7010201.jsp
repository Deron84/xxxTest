<%@page import="java.util.Iterator"%>
<%@page import="com.huateng.system.util.CommonFunction"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商户问卷</title>
<style type="text/css">
body {
	font-size: 12px;
	font-family: "Microsoft Yahei", Tahoma, "SimSun";
	color: #333;
	background: #fff;
}

.caption {
	font-size: 24px;
	background-image: url(../../ext/resources/images/Title_logo.gif);
	background-repeat: no-repeat;
	height: 50px;
}

.nav_li {
	border: #D0D0D0 1px solid;
	box-shadow: 0 0 1px #ccc;
}

.tr_li {
	border-bottom: black 1px dotted;
}

.btn_2k3 {
	BORDER-RIGHT: #002D96 1px solid;
	PADDING-RIGHT: 2px;
	BORDER-TOP: #002D96 1px solid;
	PADDING-LEFT: 2px;
	FONT-SIZE: 12px;
	FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0,
		StartColorStr=#FFFFFF, EndColorStr=#9DBCEA);
	BORDER-LEFT: #002D96 1px solid;
	CURSOR: hand;
	COLOR: black;
	PADDING-TOP: 2px;
	BORDER-BOTTOM: #002D96 1px solid
}
</style>
</head>
<body>

	<table WIDTH="90%" align="center">
		<tbody>
			<tr>
				<td colspan='3' class='caption' align="center" valign="middle">商户问卷</td>
			</tr>
			<tr>
				<td colspan='3'>&nbsp;</td>
			<tr>
			<tr>
				<td width="80px">商户编号：</td>
				<td style="border-bottom: black 1px solid;" align="center">XXXXXXXXXXXXXXX</td>
				<td width="45%"></td>
			</tr>
			<tr>
				<td width="80px">商户名称：</td>
				<td style="border-bottom: black 1px solid;" align="center">商户问卷预览商户</td>
				<td width="45%"></td>
			</tr>
		</tbody>
	</table>
	<br />
	<table WIDTH='90%' class="nav_li" align="center">
		<tbody>
			<%
			String sql = "select PAPER_ID,QUES_ID,QUESTION from TBL_PAPER_DEF_INF where PAPER_ID != 'PAPER_STATUS' order by QUES_INDEX ";
			List list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
			Iterator it = list.iterator();
			int index = 1;
			int total = list.size();
			while(it.hasNext()){
				Object[] obj = (Object[])it.next();
				String question = "Q" + String.valueOf(index++) + "：" + obj[2].toString();
				%>
			<tr>
				<td><strong><%=question %></strong></td>
			</tr>
			<tr>
				<td <% if((index - 1) != total ){ %> class='tr_li' <% } %>>
					<%
						String id = obj[1].toString();
						String subSql = "select OPT_ID,OPT from TBL_PAPER_OPT_INF where QUES_ID = '" + id + "' ORDER BY POINT DESC";
						List subList = CommonFunction.getCommQueryDAO().findBySQLQuery(subSql);
						Iterator its = subList.iterator();
						while(its.hasNext()){
							Object[] opt = (Object[])its.next();
							%> <input type="radio" name="<%=id %>"
					value="<%=opt[0].toString() %>"><%=opt[1].toString() %> <%
						}
					%>
				</td>
			</tr>
			<%
			}
		%>
		</tbody>
	</table>

	<br />
	<br />
	<div align="center">
		<button class="btn_2k3"
			onclick="javascript:window.opener=null;window.open('', '_self', '');window.close();">关闭</button>
	</div>
</body>
</html>