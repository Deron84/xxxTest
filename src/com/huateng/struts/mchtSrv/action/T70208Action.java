package com.huateng.struts.mchtSrv.action;

import java.io.File;

import org.apache.log4j.Logger;

import com.huateng.common.Constants;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.mchtSrv.MarketActConstants;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.SysParamUtil;

public class T70208Action extends BaseSupport {

	private static final long serialVersionUID = 6092933580471078023L;
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

	public String download(){
		
		try {
			StringBuffer path = new StringBuffer(SysParamUtil.getParam(SysParamConstants.FILE_PATH_MARKET_DOWNLOAD));
			path.append(date);
			path.append("/");
			path.append(getOperator().getOprBrhId());
			path.append("/");
			path.append("MARKET_INFILE_");
			path.append(actNo);
			path.append("_");
			path.append(date);
			String filePath = path.toString().replace("\\", "/");
			log.info("GET FILE:" + filePath.toString());
			File down = new File(filePath.toString());
			if (down.exists()) {
				return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + filePath);
			} else {
				return returnService(MarketActConstants.T70208_01);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return returnService("对不起，本次操作失败!", e);
		}
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
