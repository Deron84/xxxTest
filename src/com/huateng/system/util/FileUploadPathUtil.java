package com.huateng.system.util;

import java.util.ResourceBundle;

/**	
 * 
 * ClassName: fileUploadPath 
 * @Description: TODO 获取配置的文件上传路径
 * @author syl
 * @date 2019年5月29日
 */
public class FileUploadPathUtil {
	
private static String TASK = "fileUploadPath";
	
	private static ResourceBundle BUNDLE = ResourceBundle.getBundle(TASK);
	
	/**
	 * @param key
	 * @return
	 */
	public static String getTxnInfo(String key) {
		return BUNDLE.getString(key);
	}
}
