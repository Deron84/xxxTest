package com.huateng.struts.settle.action;

import java.io.File;

import com.huateng.common.Constants;
import com.huateng.ftp.FtpUtil;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.SysParamUtil;

public class TFileLoadAction extends BaseSupport {

	private static final long serialVersionUID = 4822110756831919798L;


	public String downloadreport() {
		// 下载密钥
		try {
			
			// 报表名称
			String filename1 = "caozuoshouce.doc";

			// 路径
			String path1 = "/home/posp/kztfile/";
			String path = path1 + filename1;

			path = path.replace("\\", "/");

			log("GET FILE:" + path);
			FtpUtil ftp=new FtpUtil(SysParamUtil.getParam("ftpIP"),SysParamUtil.getParam("ftpPort"),
					SysParamUtil.getParam("ftpName"),SysParamUtil.getParam("ftpPwd"));
			try{
				ftp.connectServer();
				ftp.downloadFile(path, SysParamUtil.getParam("ftpPath_shouce")+filename1);
				ftp.closeServer();
			}catch(Exception e){
				e.printStackTrace();
				ftp.closeServer();
				return returnService("FTP 到报表服务器异常");
			}
			File down = new File(SysParamUtil.getParam("ftpPath_shouce")+"/"+filename1);
			if (down.exists()) {
				return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + SysParamUtil.getParam("ftpPath_shouce")+"/"+filename1);
			} else {
				return returnService("您所请求的报表文件不存在!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return returnService("对不起，本次操作失败!", e);
		}
	}

	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.BaseSupport#getMsg()
	 */
	@Override
	public String getMsg() {
		return msg;
	}

	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.BaseSupport#isSuccess()
	 */
	@Override
	public boolean isSuccess() {
		return success;
	}
}
