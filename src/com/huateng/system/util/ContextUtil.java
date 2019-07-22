package com.huateng.system.util;

import org.directwebremoting.util.Logger;
import org.springframework.context.ApplicationContext;

/**
 * Title:上下文调用工具
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-3-8
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class ContextUtil {
	
	private static ApplicationContext context;
	private static Logger log = Logger.getLogger(ContextUtil.class);
	
	public static void setContext(ApplicationContext ctx) {
		context = ctx;
	}
	/**
	 * 获得sping对象
	 * @param id
	 * @return
	 */
	public static Object getBean(String id) {
		Object obj = context.getBean(id);
		if(obj == null) {
			log.info("bean id [ " + id + " ] not found in context path");
		}
		return obj;
	}

}
