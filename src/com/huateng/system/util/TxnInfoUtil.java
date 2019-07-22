package com.huateng.system.util;

import java.util.ResourceBundle;

/**
 * Title: 系统交易信息
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-7-16
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */
public class TxnInfoUtil {
	
private static String TXN_FILE = "txn";
	
	private static ResourceBundle BUNDLE = ResourceBundle.getBundle(TXN_FILE);
	
	/**
	 * 获得交易信息
	 * @param key
	 * @return
	 */
	public static String getTxnInfo(String key) {
		return BUNDLE.getString(key);
	}
}
