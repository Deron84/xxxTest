<%@page import="com.huateng.system.util.EncryptUtils"%>
<%@page import="com.huateng.common.Constants"%>
<%@page import="com.huateng.system.util.InformationUtil"%>
<%@page import="com.huateng.common.StringUtil"%>
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

<script type="text/javascript">
	//变量定义
	var questIds = new Array();
	var checks = new Array();
	function beforeprint () {
		document.getElementById('buttons').style.display="none";
	}
	
	function afterprint(){
		document.getElementById('buttons').style.display="";
	}
	
	window.onbeforeprint = beforeprint;
	window.onafterprint = afterprint;
	
	function reClick(){
		for(var i=0;i<checks.length;i++){
			document.getElementById(checks[i]).checked = true;
		}
	}
	
	function radioOnclick(t){
		t.checked = false;
		reClick();
	}
</script>


</head>
<body>

	<% 
		String[] params = EncryptUtils.decrypt(request.getParameter("s")).split("\\|");
		
		String mchtId = params[0];
		String mchtName = InformationUtil.getMchtName(mchtId);
		String paperId = params[1];
		String selMchtId = params[2];
		String point = InformationUtil.getCurPaperPoint(selMchtId);
		String level = InformationUtil.getCurPaperLevel(selMchtId);
	%>
	<table WIDTH="90%" align="center">
		<tbody>
			<tr>
				<td colspan='4' class='caption' align="center" valign="middle">商户问卷</td>
			</tr>
			<tr>
				<td colspan='4'>&nbsp;</td>
			<tr>
			<tr>
				<td width="80px">商户编号：</td>
				<td style="border-bottom: black 1px solid;" align="center"><div
						id=mchtId></div></td>
				<td width="30%" align="right">得分：</td>
				<td width="15%" align="center"><div id=point></div></td>
			</tr>
			<tr>
				<td width="80px">商户名称：</td>
				<td style="border-bottom: black 1px solid;" align="center"><div
						id=mchtName></div></td>
				<td width="30%" align="right">级别：</td>
				<td width="15%" align="center"><div id=level></div></td>
			</tr>
		</tbody>
	</table>
	<br />
	<div style="overflow: visible; enabled: false;">
		<table WIDTH='90%' class="nav_li" align="center">
			<tbody>
				<%
			String sql = "select PAPER_ID,QUES_ID,QUESTION from TBL_PAPER_HIS_INF where PAPER_ID = '" + paperId + "' order by QUES_INDEX ";
			List list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
			Iterator it = list.iterator();
			int index = 1;
			int total = list.size();
			while(it.hasNext()){
				Object[] obj = (Object[])it.next();
				String question = "Q" + String.valueOf(index++) + "：" + obj[2].toString();
				%>
				<script type="text/javascript">questIds.push("<%=obj[1].toString()%>")</script>
				<tr>
					<td><strong><%=question %></strong></td>
				</tr>
				<tr>
					<td <% if((index - 1) != total ){ %> class='tr_li' <% } %>>
						<%
						String id = obj[1].toString();
						String subSql = "select OPT_ID,OPT,POINT from TBL_PAPER_OPT_HIS where QUES_ID = '" + id + "' and PAPER_ID = '" + paperId + "' ORDER BY POINT DESC";
						List subList = CommonFunction.getCommQueryDAO().findBySQLQuery(subSql);
						Iterator its = subList.iterator();
						while(its.hasNext()){
							Object[] opt = (Object[])its.next();
							%> <input type="radio" name="<%=id %>"
						id="<%=opt[0].toString() %>" value="<%=opt[0].toString() %>"
						onclick="radioOnclick(this);"><%=opt[1].toString() %>&nbsp;&nbsp;
						<%
						}
					%>
					</td>
				</tr>
				<%
			}
		%>
			</tbody>
		</table>
	</div>
	<br />
	<div align="center" id="waiting">&nbsp;</div>
	<br />

	<div align="center" id="buttons">
		<button class="btn_2k3" onclick="javascript:window.print()">打印</button>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<button class="btn_2k3"
			onclick="javascript:window.opener=null;window.open('', '_self', '');window.close();">关闭</button>
	</div>

	<br />
	<br />


	<script type="text/javascript">
		var mchtId = "<%=mchtId %>";
		var mchtName = "<%=mchtName %>";
		var paperId = "<%=paperId %>";
		var point = "<%=point %>";
		var level = "<%=level %>";
		
		document.getElementById('mchtId').innerHTML = mchtId;
		document.getElementById('mchtName').innerHTML = mchtName;
		document.getElementById('point').innerHTML = '<font color="red">'+point+'</font>';
		if(Number(point)>=60){
			document.getElementById('level').innerHTML = '<font color="green">'+level+'</font>';
		}else{
			document.getElementById('level').innerHTML = '<font color="red">'+level+'</font>';
		}
	</script>

	<%
		String sel = "select SEL_OPT_ID from TBL_PAPER_SEL_INF where SEL_MCHT_ID = '" + selMchtId + "'";
		List l = CommonFunction.getCommQueryDAO().findBySQLQuery(sel);
		Iterator iterator = l.iterator();
		while (iterator.hasNext()) {
			String select = (String)iterator.next();
			%>
	<script type="text/javascript">
					var id = '<%=select%>';
					checks.push(id);
					document.getElementById(id).checked = true;
				</script>
	<%
		}
	%>
</body>
</html>