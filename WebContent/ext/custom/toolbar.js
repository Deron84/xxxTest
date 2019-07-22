//工具栏对象
var toolBarStr;
//菜单树对象
var menuTree;
var initMask = new Ext.LoadMask(Ext.getBody(),{});
//父工具栏调用方法
function changeTree(button) {
	initMask.msg = '正在加载菜单栏，请稍后......';
	initMask.show();
	Ext.get("mainUI").dom.src = Ext.contextPath + "/page/system/T00001.jsp";
	menuTree.getRootNode().attributes.loader.dataUrl = 'tree.asp?id=' + button.id;
	menuTree.getRootNode().reload(function(){
		hideToolInitMask.defer(1500);
	});
	menuTree.getRootNode().expand(true);
}

//子工具栏调用方法
function changeTxn(button) {//alert(button+"   >>>>>>>>>>>>>>");
	initMask.msg = '系统界面加载中，请稍后......';
	initMask.show();
	Ext.get("mainUI").dom.src = button.url;
	var id;
	var bid = button.id;
	if(bid.length <= 5){
		id = bid.substring(0,1)
	}else{
		id = bid.substring(0,2);
	}
	menuTree.getRootNode().attributes.loader.dataUrl = 'tree.asp?id=' + id;
	menuTree.getRootNode().reload(function(){
		hideToolInitMask.defer(1500);
	});
	menuTree.getRootNode().expand(true);
}
//隐藏加载图层
function hideToolInitMask() {
	initMask.hide();
}