/**
 * 
 * @param {} url			上传文件的url
 * @param {} fileName		文件在服务端的变量
 * @param {} id				渲染的控件
 * @param {} fileType		文件类型
 * @param {} fileTypeDesc	文件类型描述
 */
function UploadButton(url,fileName,id,fileType,fileTypeDesc,handler) {
	var swf;
	var progress;
	var em = Ext.getCmp(id).el.child('em');
    var placeHolderId = Ext.id();
    
    em.setStyle({
        position: 'relative',
        display: 'block'
    });
    em.createChild({
        tag: 'div',
        id: placeHolderId
    });
	            
	var swfSettings = {
		file_post_name: fileName,
		upload_url : url,
		flash_url : Ext.contextPath + "/ext/upload/swfupload.swf",
		file_size_limit : "10MB",
		file_queue_limit: 1,
		button_placeholder_id : placeHolderId,
		file_types_description: fileTypeDesc,
		file_types: fileType,
		button_cursor:SWFUpload.CURSOR.HAND,
		button_window_mode : SWFUpload.WINDOW_MODE.TRANSPARENT,
		use_query_string:true,
		debug : false,
		button_width : '70',
		button_height : '20',
		custom_settings : {
			scope_handler : this
		},
		upload_success_handler: function(file, serverData) {
			if(Ext.decode(serverData).success) {
				showMsg(Ext.decode(serverData).msg,Ext.getCmp(id));
				handler();
			} else {
				showErrorMsg(Ext.decode(serverData).msg,Ext.getCmp(id));
			}
			
		},
		upload_progress_handler: function(file,complete,total) {
			progress.updateProgress(complete/total,Math.ceil((complete/total) * 100) + '%','上传中，请等待......');
			if(complete/total == 1) {
				progress.hide();
			}
		}
	}
	
	this.swf = new SWFUpload(swfSettings);
	Ext.get(this.swf.movieName).setStyle({
        position: 'absolute',
        top: 0,
        left: -2
    })
	
    // 开始上传
	this.start = function() {
		progress = Ext.MessageBox.show({
			progress: true,
			progressText: '0%',
			closeable: false,
			modal: true,
			msg: '上传中，请等待......',
			title: '上传进度',
			width: 500
		});
		this.swf.startUpload();
	}
	// 待上传文件队列
	this.fileSequence = function() {
		return this.swf.getStats().files_queued;
	}
	// 添加上传参数
	this.setPostParams = function(postParams) {
		this.swf.setPostParams(postParams);
	}
}