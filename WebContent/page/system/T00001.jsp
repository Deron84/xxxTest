<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/page/system/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/page/system/welcome.css" />
    <script type="application/javascript">

        function urlRedirect(url){
            parent.Ext.get("mainUI").dom.src = url;
        }
        Ext.onReady(function(){
            var menuTree = parent.menuTree.getRootNode().lastChild.attributes.children;
            for(var i=0; i<menuTree.length; i++) {
                var content = "";
                content += "<div class=\"welcome_img\">" +
                    "<div><image class=\"welcome_image_size\" src=\"" +
                    "<%= request.getContextPath()%>/images/welcome_" + menuTree[i].id +
                    ".png" +
                    "\"></image></div>" +
                    "<div class=\"welcome_des\">" +
                    menuTree[i].text +
                    "</div>";
                var menutext = menuTree[i].children;
                console.log(menutext);
                for(var j=0; j<menutext.length; j++) {
                    content += "<div class=\"welcome_redirect\" onclick=\"urlRedirect('" +
                        menutext[j].url +
                        "')\">" +
                        menutext[j].text +
                        "</div>";
                }
                content += "</div>";
                Ext.DomHelper.append('welcome_container',content,true);
            }

        });
    </script>
<title>Insert title here</title>
</head>
<body>
<div class="welcome_container" id="welcome_container">
</div>
</body>
</html>