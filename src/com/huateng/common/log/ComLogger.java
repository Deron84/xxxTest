package com.huateng.common.log;

/**
 * Title: 通用日志处理类
 *
 * Description: 
 *
 * Copyright: Copyright (c) 2006
 *
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 *
 * @author robertbao
 *
 * @version 1.0, 2007-12-18
 */
public class ComLogger {
	
	public static final String LOGGER_COM="logger.com";
	
	public static final String LOG_PATH="LOG_PATH";

	public static final String LOGPATH="f:/log";
	
	public static final String LOG_BEGIN_PREFEX="########## ";
	public static final String LOG_BEGIN_TAIL=" begin ##########";
	
	public static final String LOG_END_PREFEX="########## ";
	public static final String LOG_END_TAIL=" end ##########";
	
	public static final String LOG_PREFEX="########## ";
	public static final String LOG_TAIL=" ##########";
	
	public static String beginLog(String logStr){
		return LOG_BEGIN_PREFEX + logStr + LOG_BEGIN_TAIL;
	}
	public static String endLog(String logStr){
		return LOG_END_PREFEX + logStr + LOG_END_TAIL;
	}
	
	public static String log(String logStr){
		return LOG_PREFEX + logStr + LOG_TAIL;
	}
	
	public static String ulog(String logStr){
		
		return ""+logStr;
		
	}
	
}
