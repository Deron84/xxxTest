package com.rail.bo.pub;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

/**
 * Title: PubPath
 * Description:  通用的查询通用类
 * @author syl 
 * @date 上午10:49:47 
 */
public class PubPath {
	private Log log = LogFactory.getLog(this.getClass());
	
	/**
	 * @Description: TODO 
	 * @param @param param
	 * @throws
	 */
	public static String getRealPath(String uploadPath,ServletActionContext servletActionContext){
		String projectName = ServletActionContext.getServletContext().getContextPath();
		String realPath = ServletActionContext.getServletContext().getRealPath(uploadPath);
		realPath = realPath.substring(0, realPath.length() - projectName.length() -uploadPath.length());
        realPath = realPath +uploadPath;
        return realPath;
	}

}
