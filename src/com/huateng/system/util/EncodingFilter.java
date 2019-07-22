package com.huateng.system.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Title:字符集过滤器
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
public class EncodingFilter implements Filter {
	
	protected String encoding = null;
	
	public void destroy() {
		
		this.encoding = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		if (encoding != null) {
			request.setCharacterEncoding(encoding);
			response.setCharacterEncoding(encoding);
		}
		chain.doFilter(request, response);
	}
	
	public void init(FilterConfig filterConfig) throws ServletException {

	    encoding = filterConfig.getInitParameter("encoding");
	}
}
