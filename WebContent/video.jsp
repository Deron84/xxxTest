<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>SEE VIDEO</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
<style>
.plugin {
    width: 600px;
    height: 400px;
}
</style>
<body>
<div>
    <div id="divPlugin" class="plugin"></div>
</div>
<input id="param" type="hidden" value="<%=request.getParameter("Param")%>"/>
</body>
<script src="<%=basePath%>ui/access/jquery-1.7.1.min.js"></script>
<script src="<%=basePath%>ui/access/webVideoCtrl.js"></script>
<script>

$(function () {
	var param=$("#param").val();
	var params=param.split("*");
    // 检查插件是否已经安装过
    var iRet = WebVideoCtrl.I_CheckPluginInstall();
    if (-1 == iRet) {
        alert("您还未安装过插件，双击开发包目录里的WebComponentsKit.exe安装！");
        return;
    }
    var oPlugin = {
        iWidth: 600,             // plugin width
        iHeight: 400             // plugin height
    };

    var oLiveView = {
        iProtocol: 1,            // protocol 1：http, 2:https
        szIP: params[0]+"."+params[1]+"."+params[2]+"."+params[3],    // protocol ip
        szPort: "80",            // protocol port
        szUsername: params[4],     // device username
        szPassword: params[5], // device password
        iStreamType: 1,          // stream 1：main stream  2：sub-stream  3：third stream  4：transcode stream
        iChannelID: 1,           // channel no
        bZeroChannel: false      // zero channel
    };
    openVedio(oPlugin,oLiveView,"divPlugin");
    // 关闭浏览器
    $(window).unload(function () {
        WebVideoCtrl.I_Stop();
    });
});
function openVedio(oPlugin,oLiveView,divPlugin){
    // 初始化插件参数及插入插件
    WebVideoCtrl.I_InitPlugin(oPlugin.iWidth, oPlugin.iHeight, {
        bWndFull: true,//是否支持单窗口双击全屏，默认支持 true:支持 false:不支持
        iWndowType: 1,
        cbInitPluginComplete: function () {
            WebVideoCtrl.I_InsertOBJECTPlugin(divPlugin);
            // 检查插件是否最新
            //if (-1 == WebVideoCtrl.I_CheckPluginVersion()) {
            //    alert("检测到新的插件版本，双击开发包目录里的WebComponentsKit.exe升级！");
            //    return;
            //}
            // 登录设备
            WebVideoCtrl.I_Login(oLiveView.szIP, oLiveView.iProtocol, oLiveView.szPort, oLiveView.szUsername, oLiveView.szPassword, {
                success: function (xmlDoc) {
                    // 开始预览
                    var szDeviceIdentify = oLiveView.szIP + "_" + oLiveView.szPort;
                    setTimeout(function () {
                        WebVideoCtrl.I_StartRealPlay(szDeviceIdentify, {
                            iStreamType: oLiveView.iStreamType,
                            iChannelID: oLiveView.iChannelID,
                            bZeroChannel: oLiveView.bZeroChannel
                        });
                    }, 1000);
                }
            });
        }
    });
}

</script>
</html>
