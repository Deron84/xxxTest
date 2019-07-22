package com.rail.bo.impl.api.app;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.alibaba.fastjson.JSON;

/**
 * @Auther: Administrator
 * @Date: 2018/8/10 0010 14:27
 * @Description:
 */
public class BaseController {
	private Log log = LogFactory.getLog(this.getClass());


    /**
     * httpServletRequest,httpServletResponse 线程安全直接引入。
     */
    @Autowired
    protected HttpServletRequest httpServletRequest;

    @Autowired
    protected HttpServletResponse httpServletResponse;
    @ExceptionHandler
    public void exp(HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException {
        log.error("异常：",ex);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter pw = response.getWriter();
        String msg  = "服务器异常";
        if(ex instanceof RuntimeException){
        	RuntimeException exception = (RuntimeException) ex;
            msg =  exception.getMessage();
            pw.print(JSON.toJSONString(com.rail.bo.pub.ResultUtils.errorCode(500,msg)));
        }else{
            pw.print(JSON.toJSONString(com.rail.bo.pub.ResultUtils.errorCode(500,msg)));
        }
        pw.flush();
        pw.close();
    }
    /**
     * 获取token
     *
     * @return
     */
    protected String getToken() {
        String token = httpServletRequest.getHeader("Authorization");
        return token;
    }
}
