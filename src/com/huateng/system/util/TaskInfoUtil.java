package com.huateng.system.util;

import java.util.ResourceBundle;

/**	
 * 
 * ClassName: TaskInfoUtil 
 * @Description: TODO
 * @author syl
 * @date 2019年5月29日
 */
public class TaskInfoUtil {
	
private static String TASK = "task";
	
	private static ResourceBundle BUNDLE = ResourceBundle.getBundle(TASK);
	
	/**
	 * 获得交易信息
	 * @param key
	 * @return
	 */
	public static String getTxnInfo(String key) {
		return BUNDLE.getString(key);
	}
}
