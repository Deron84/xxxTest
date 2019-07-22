package com.huateng.exception;

import com.huateng.exception.HuatengException;

/**
 * Title: DomainException extends HuatengException
 *
 * Description: 
 *
 * Copyright: Copyright (c) 2006
 *
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 *
 * @author shen_antonio
 *
 * @version 1.0, 2007-1-16
 */
public class DomainException
    extends HuatengException {

	private static final long serialVersionUID = 520444917967365429L;
	protected String moduleName = null;
	protected String errCode = null;
	protected String errorPage = null;
	protected String errMessage = null;
	protected String errBackPage=null;

    public DomainException(){}

    public DomainException(String message){
    	super(message);
    }
    public DomainException(String moduleName, String errCode) {
		this(moduleName, errCode, null, null);
	}

    public DomainException(String moduleName, String message, Exception exception) {
        this(moduleName,null,message,exception);
    }

    public DomainException(String moduleName, String errCode, String message) {
        this(moduleName,errCode,message,null);
    }

    public DomainException(String moduleName, String errCode, String message,String errorPage,Exception exception) {
    	this(moduleName,errCode,message,exception);
    	this.errorPage = errorPage;
    }
    public DomainException(String moduleName, String errCode, String message,
                    Exception exception) {
        super(message,exception);
        this.moduleName = moduleName;
        this.errCode = errCode;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getErrBackPage() {
		return errBackPage;
	}

	public void setErrBackPage(String errBackPage) {
		this.errBackPage = errBackPage;
	}

	/**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     * @todo Implement this java.lang.Object method
     */
    public String toString() {
        return moduleName + ":" + errCode ;
    }

	public String getErrorPage() {
		return errorPage;
	}

	public void setErrorPage(String errorPage) {
		this.errorPage = errorPage;
	}

}
