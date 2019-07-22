Ext.onReady(function () {
    //初始化标签中的Ext:Qtip属性。
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';
    //提交按钮处理方法
    var btnsubmitclick = function () {
    	Ext.Ajax.request({
            url: 'upGrade.asp',
            headers: {
                'userHeader': 'userMsg'
            },
       //     params: { a: 10, b: 20 },
            method: 'POST',
            success: function (response, options) {
                Ext.MessageBox.alert('成功', '从服务端获取结果: ' + response.responseText);
                win.hide();
            },
            failure: function (response, options) {
                Ext.MessageBox.alert('失败', '请求超时或网络故障,错误编号：' + response.status);
            }
        });
    //    Ext.MessageBox.alert('提示', '你点了确定按钮!');
    }
    //提交按钮
    var btnsubmit = new Ext.Button({
        text: '升级',
        handler: btnsubmitclick
    });
    //表单
    var form = new Ext.form.FormPanel({
    	frame: true,
		autoHeight: true,
		autoWidth: true,
	//	labelWidth: 90,
	//	waitMsgTarget: true,       
        style: 'margin:20px',
        html: '<div style="padding:20px">升级扫码付商户绑定表，增加主键<br><br>请点击升级按钮</div><hr>',
     //   items: [txtusername, txtpassword],
        buttons: [btnsubmit]
    });
    //窗体
    var win = new Ext.Window({
        title: '窗口',
        width: 476,
        height: 374,
        resizable: true,
        modal: true,
        closable: true,
        maximizable: true,
        minimizable: true,
        buttonAlign: 'center',
        items: form
    });
    win.show();
});