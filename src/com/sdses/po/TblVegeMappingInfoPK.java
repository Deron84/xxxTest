package com.sdses.po;

import java.io.Serializable;

public class TblVegeMappingInfoPK implements Serializable{
    private static final long serialVersionUID = 1L;
    
    // 商户号
    private String mchntNo; 
    // 终端号
    private String termNo;
    // 按键号
    private String keyCode;
    
    public String getMchntNo() {
        return mchntNo;
    }
    public void setMchntNo(String mchntNo) {
        this.mchntNo = mchntNo;
    }
    public String getTermNo() {
        return termNo;
    }
    public void setTermNo(String termNo) {
        this.termNo = termNo;
    }
    public String getKeyCode() {
        return keyCode;
    }
    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
