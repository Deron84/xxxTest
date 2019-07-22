package com.huateng.exception;

import com.huateng.common.Constants;


/**
 * Title: AppException extends DomainException
 * 
 * Copyright: Copyright (c) 2006
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author shen_antonio
 * 
 * @version 1.0, 2007-1-16
 */
public class AppException extends DomainException {

	public AppException(String moduleName, String errCode, String message) {
		super(moduleName, errCode, message);
	}

	public AppException() {
	}

	public AppException(String message) {
		super(message);
	}

	public AppException(String moduleName, String errCode) {
		this(moduleName, errCode, null, null);
	}

	public AppException(String moduleName, String errCode, Exception exception) {
		this(moduleName, errCode, null, exception);
	}

	public AppException(String moduleName, String errCode, String message, String errorPage, Exception exception) {
		this(moduleName, errCode, message, exception);
		this.errorPage = errorPage;
	}

	public AppException(String moduleName, String errCode, String message, Exception exception) {
		super(moduleName, errCode, message, exception);
		this.moduleName = moduleName;
		this.errCode = errCode;
		this.errMessage = message;
	}
	public AppException(String moduleName, String errCode, String message, Exception exception,String errBackPage) {
		super(moduleName, errCode, message, exception);
		this.moduleName = moduleName;
		this.errCode = errCode;
		this.errMessage = message;
		this.errBackPage=errBackPage;
		
	}
	public String getErrCd() {
		if (moduleName == null || errCode == null) {
			return Constants.DEFAULT_ERROR;
		}
		return moduleName + errCode;
	}

	public String getErrMessage() {
		if (this.errMessage == null)
			return "unknown error";
		return this.errMessage;
	}
}
