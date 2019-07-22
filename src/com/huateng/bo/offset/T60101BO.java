package com.huateng.bo.offset;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * project JSBConsole
 * date 2013-4-7
 * @author 樊东东
 */
public interface T60101BO {


	/**
	 * @param params 
	 * @return
	 */
	String add(Map<String, String> params,HttpServletRequest request)throws Exception;

	/**
	 * @param params
	 * @return
	 */
	String delete(Map<String, String> params)throws Exception;

}
