package com.huateng.bo.accident;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * project JSBConsole
 * date 2013-3-19
 * @author 樊东东
 */
public interface T90301BO {

	/**业务逻辑处理
	 * @param params 
	 * @return
	 */
	String deal(Map<String, String> params,HttpServletRequest request)throws Exception;

}
