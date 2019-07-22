package com.huateng.common.log;

import java.util.MissingResourceException;
import java.util.ResourceBundle;


/**
 * Log4j 配置参数 
 * 
 * @author shen_antonio
 * 
 */
public class LoggerConfig {
	  private static final String BUNDLE_NAME = "LoggerConfig"; //$NON-NLS-1$
	  
	  public static final String TXNRELATIVEDIR = "Txn.RelativeDir";
	  public static final String TXNLAYOUTCP = "Txn.Logout.ConversionPattern";
	  public static final String TXNDATAPATTERN = "Txn.DatePattern";
	  public static final String TXNAPPEND = "Txn.Append";
	  public static final String TXNLOGGERLEVEL = "Txn.Logger.Level";
	  public static final String TXNLOGGERDEFAULT = "Txn.Logger.Default";
	  public static final String TXNLOGGERBUFFERIO = "Txn.Logger.BufferIO";
	  public static final String TXNLOGGERBUFFERSIZE = "Txn.Logger.BufferSize";
	  
	  public static final String ASLOGFILE = "ASLogFile";
	  

	    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	    private LoggerConfig() {
	    }

	    public static String getConfig(String key) {
	        try {
	            return RESOURCE_BUNDLE.getString(key);
	        } catch (MissingResourceException e) {
	            return key ;
	        }
	    }
}
