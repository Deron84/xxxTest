package com.huateng.common;
public class StaticMethod{
	public static Object exec(String arg) {
		String result = "";
		if (isNotEmpty(arg)) {
			if ("00".equals(arg.trim())) {
				result = "他行银联卡";
			} else if ("01".equals(arg.trim())) {
				result = "本行借记卡";
			} else if ("03".equals(arg.trim())) {
				result = "本行一帐通";
			} else if ("04".equals(arg.trim())) {
				result = "本行贷记卡";
			} else
				result = "未知卡";
		}
		return result;
	}
	
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	private static boolean isNotEmpty(String str) {
		if(str != null && !"".equals(str.trim()))
			return true;
		else 
			return false;
	}

}
