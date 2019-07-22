package com.huateng.exception;

/**
 * <p>Title: Hermes -- Post MIS/DSS System</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Shanghai Huateng Software Systems Co., Ltd.</p>
 * @author Charles Zhang
 * @version 3.0
 */

public class HuatengException extends Exception {

  static final long serialVersionUID = -2398001218377041500L;

public HuatengException() {
  }

  public HuatengException(String message) {
    super(message);
  }

  public HuatengException(String message, Throwable cause) {
    super(message, cause);
  }

  public HuatengException(Throwable cause) {
    super(cause);
  }
}