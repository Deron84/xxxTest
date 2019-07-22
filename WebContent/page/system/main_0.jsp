<%@ page import="java.util.LinkedList" %>
<%@ page import="java.util.Map" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/page/system/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/page/system/welcome.css" />
    <script type="application/javascript">
        var initMask = new Ext.LoadMask(Ext.getBody(),{});
        //隐藏加载图层
        function hideToolInitMask() {
            initMask.hide();
        }
        function menuReloadFunc(mark){
            initMask.msg = '正在加载菜单栏，请稍后......';
            initMask.show();
            parent.Ext.get("mainUI").dom.src = Ext.contextPath + "/page/system/T00001.jsp";
            parent.menuTree.getRootNode().attributes.loader.dataUrl = 'tree.asp?id=' + mark;
            parent.menuTree.getRootNode().reload(function(){
                hideToolInitMask.defer(1500);
            });
            parent.menuTree.getRootNode().expand(true);
        }
        Ext.Ajax.request({
			url : 'T130101Action.asp?method=getAllWarningInfos',
			success : function(rsp, opt) {//lert(JSON.stringify(rsp));
				var rspObj = Ext.decode(rsp.responseText);
				if(rspObj.success){
					var msgs = rspObj.msg;
//					alert(msgs);
					if(msgs!=""){
						var html = '';
						for(var i = 0;i<msgs.length;i++){
							
							html += '<div onclick="urlRedirect('+msgs[i].warningType+')" style="cursor:pointer;">';
							html += '<p style="font-size:36px; font-weight: bold;color:red">'+msgs[i].warningNum+'<span style="font-size:18px;">条</span></p>';
							html += '<p class="welcome_des">未确认'+msgs[i].warningName+'</p>';
							html += '</div>';
						}
						document.getElementById("warningBox").innerHTML = html;
					}
				}
			}
		});
    </script>
    <style type="text/css">
    	.header-title{
    		color: #15428b;
			height:25px;
			line-height: 25px;
			padding:5px 15px;
    	}
    	.header-content{
    		color: #15428b;
			padding:5px 15px;
			height:150px;
    	}
    	
    	.header-content div{
    		float: left;
    		width:15%;
    		border:#000 solide 1px;
    		text-align: center;
    		padding-top:30px;
    	}
    </style>
<title></title>
</head>
<body>
<div class="welcome_container">

    <%
        LinkedList<Map<String, Object>> menu_list = (LinkedList<Map<String, Object>>)session.getAttribute("menu_list");
        for (Map<String, Object> map : menu_list) {
    %>
    <div onclick="menuReloadFunc(<%= map.get("id")%>)" class="welcome_img">
        <div>
            <image class="welcome_image_size" src="<%= request.getContextPath()%>/images/welcome_<%= map.get("id")%>.png"></image>
        </div>
        <div class="welcome_des">
            <%= map.get("text")%>
        </div>
    </div>
    <%
        }
    %>
    <div style="clear: both;"></div>
</div>
<div class="welcome_container" style="margin-top:100px;">
	<div class="header-title"></div>
	<div class="header-content" id="warningBox">
		
		<div style="clear: both;"></div>
	</div>
</div>

</body>
</html>