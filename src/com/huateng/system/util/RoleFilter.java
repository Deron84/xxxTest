package com.huateng.system.util;

import java.io.IOException;
import java.util.HashSet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.huateng.common.Constants;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-9-16
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @version 1.0
 */
public class RoleFilter implements Filter{

	private static Logger log = Logger.getLogger(RoleFilter.class);

	public void doFilter(ServletRequest arg0, ServletResponse arg1,FilterChain arg2) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest)arg0;
		HttpServletResponse response = (HttpServletResponse)arg1;
		
		String url = request.getRequestURL().toString();
		String res = CommonFunction.urlToRoleId(url);
		System.out.println(url+"<<>>>url");
		if(res.matches("T\\d+$"))
		{
			//1.判断请求来源
		/*	if (null == request.getHeader("referer")) {
				//判断是否为子页面，该系统定义子页面为编号+两位数字
				//这里将子页面自动放行，适用于window.open()方式打开的窗口
				if (!res.substring(6, 8).matches("[0-9]{2}$")) {
					log.info("illegal access(referer)!");
					response.sendRedirect(request.getContextPath()+"/redirect.asp");
				}
			}*/
			
			//2.判断请求合法性
			HttpSession session = request.getSession();
			HashSet<String> set = (HashSet<String>)session.getAttribute(Constants.USER_AUTH_SET);
			//原代码为res.substring(1,6)，此处因为如果菜单超过一级10个时，截取位数不够，现修改为res.substring(1,res.length())；修改人：LiuJH
			/*if(set == null || (!set.contains(res.substring(1,res.length())))&&(!"T00001".equals(res))) {
				log.info("illegal access!");
				response.sendRedirect(request.getContextPath()+"/redirect.asp");
			} else {*/
				arg2.doFilter(request, response);
			/*}*/
		}
		else
			arg2.doFilter(request, response);
	}

	public void destroy() {}
	public void init(FilterConfig arg0) throws ServletException {}
}
