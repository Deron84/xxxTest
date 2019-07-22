package com.huateng.startup.listener;

import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.dom4j.DocumentException;

import com.huateng.common.Constants;
import com.huateng.common.SysParamConstants;
import com.huateng.common.grid.GridConfigUtil;
import com.huateng.common.select.SelectOptionUnit;
import com.huateng.startup.init.MenuInfoUtil;
import com.huateng.system.util.SysParamUtil;
import com.huateng.system.util.SystemDictionaryUnit;
import com.huateng.system.util.TaskInfoUtil;
import com.rail.zWebSocket.ServerManager;

/**
 * Title: 系统服务监听
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
public class SystemListener implements ServletContextListener {

    private static Logger log = Logger.getLogger(SystemListener.class);

    @Override
    public void contextDestroyed(ServletContextEvent contextEvent) {
    }

    /**
     * 初始化系统资源
     */
    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {

        ServletContext context = contextEvent.getServletContext();
        init(context);
    }

    /**
     * 系统初始化
     * @param context
     */
    protected void init(ServletContext context) {

        log.info("系统启动时间");

        try {

            //这里看是开发模式，还是上线后正式使用模式。false是开发模式。
            System.setProperty(SysParamConstants.PRODUCTION_MODE, SysParamUtil.getParam(SysParamConstants.PRODUCTION_MODE));
            //初始化上下文路径
            Constants.CONTEXTPATH = context.getRealPath("");
            //初始化日志
            initLog4J();
            //初始化菜单
            MenuInfoUtil.init();
            SystemDictionaryUnit.initSysDic(context);
            SelectOptionUnit.initSelectOptions(context);
            GridConfigUtil.initGirdConfig(context);

            ResourceBundle bundle = ResourceBundle.getBundle(SysParamConstants.CKEY);
            context.setAttribute("recordNum", bundle.getString("recordNum"));
            
            //开机启动监听 启动1159的
            ServerManager serverManager=new ServerManager();
            Integer port1 = Integer.valueOf(TaskInfoUtil.getTxnInfo("port1"));//1159
            Integer port2 = Integer.valueOf(TaskInfoUtil.getTxnInfo("port2"));//1170
            System.out.println("Start--------->>wsServer for port "+port1+"<<-----------End!");
//            System.out.println("Start--------->>wsServer for port 1159<<-----------End!");
            serverManager.Start(port1,true);//1159
            serverManager=new ServerManager();
            serverManager.Start(port2,false);//1170
            System.out.println("Start--------->>wsServer for port "+port2+"<<-----------End!");
            //System.out.println("Start--------->>wsServer for port 1170<<-----------End!");

        } catch (DocumentException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

    }

    /**
     * 初始化系统日志
     */
    private void initLog4J() {
        String logConfigFile = "/log4j.properties";
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getResourceAsStream(logConfigFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        PropertyConfigurator.configure(properties);
    }

}
