package com.huateng.system.util;

public class StringUtil {
	/**
	 * 自动在左侧填充制定字符
	 * 
	 * @param para
	 *            需要填充的字段
	 * @param length
	 *            指定长度
	 * @param fillChar
	 *            制定填充字符
	 * @return
	 */
	public static String fillString(String para, int length, String fillChar) {
		if (para == null) {
			para = "";
		}
		if (para.length() >= length) {
			return para;
		}
		for (int i = para.length(); i < length; i++) {
			para = fillChar + para;
		}
		return para;
	}
}
