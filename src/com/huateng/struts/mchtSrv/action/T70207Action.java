package com.huateng.struts.mchtSrv.action;


import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.huateng.common.Constants;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.mchtSrv.MarketActConstants;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.SocketConnect;
import com.huateng.system.util.SysParamUtil;

public class T70207Action extends BaseSupport {

	private static final long serialVersionUID = 1295004469258601975L;
	private static Logger log = Logger.getLogger(T70207Action.class);
	private String actNo;
	private String date;
	
	
	
	public String getActNo() {
		return actNo;
	}

	public void setActNo(String actNo) {
		this.actNo = actNo;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * 下载商户入账文件
	 * 
	 * @return
	 * 2011-8-2下午02:23:26
	 */
	public String download(){
		
		try {
			String brhId = getOperator().getOprBrhId();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String today = sdf.format(new Date());
			
			String path = SysParamUtil.getParam(SysParamConstants.FILE_PATH_SETTLE_DOWNLOAD);

			path += today;
			path += "/";
			path += brhId;
			path += "/";
			path += "MCHT_INFILE_";
			path += brhId;
			path += "_";
			path += today;
			path = path.replace("\\", "/");
			log("GET FILE:" + path);
			File down = new File(path);
			if (down.exists()) {
				return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + path);
			} else {
				return returnService("您所请求的商户代发文件不存在!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return returnService("对不起，本次操作失败!", e);
		}
	}
	
	/**
	 * 生成文件
	 * 
	 * @return
	 * 2011-8-2下午02:23:22
	 */
	public String makefile(){
		try {
//			交易码
			StringBuffer req = new StringBuffer("8231010");
			req.append(getOperator().getOprBrhId()).append(date);
			String len = Integer.toHexString(req.toString().length());
			len = new String(CommonFunction.hexStringToByte(len));
			len = new String(CommonFunction.hexStringToByte("00"))+len;
			
			SocketConnect socket = null;
			try {
				log.info("报文:[" + len + req.toString() + "]");
				socket = new SocketConnect(len + req.toString());
				socket.run("GBK");
				String resp = socket.getRsp();
				
				if(resp.substring(4,6).equals(Constants.SUCCESS_CODE))
					return returnService(Constants.SUCCESS_CODE);
				else
					return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + "生成商户营销活动代发文件失败，返回码:" + resp.substring(4,6));
			} catch (UnknownHostException e) {
				log.error(e);
			} catch (IOException e) {
				log.error(e);
			} catch (Exception e) {
				log.error(e);
			} finally {
				socket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnService(MarketActConstants.T70207_01);
	}
	@Override
	public String getMsg() {
		return msg;
	}

	@Override
	public boolean isSuccess() {
		return success;
	}

}
