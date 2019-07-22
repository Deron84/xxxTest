package com.huateng.system.util;

import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import com.huateng.common.StringUtil;



public class RequestUtil {
	
	/**
	 * 检测页面过来的必输项是否传递完整（不含空值）并去空格
	 * 
	 * @param names
	 * @param map
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean  checkAndTrim(String[] names, HashMap<String, String> map, HttpServletRequest request){
		
		
		Iterator<String> it = request.getParameterMap().keySet().iterator();
		while (it.hasNext()){
			String key = it.next();
			if (!StringUtil.isNull(request.getParameter(key))){
				map.put(key, request.getParameter(key).trim());
			}
		}
		for (String tmp : names) {
			if (StringUtil.isNull(map.get(tmp))) {
				return false;
			}
		}
		return true;
	}
}
