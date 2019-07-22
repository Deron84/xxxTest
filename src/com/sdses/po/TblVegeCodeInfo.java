package com.sdses.po;

import java.io.Serializable;

public class TblVegeCodeInfo implements Serializable{
    private static final long serialVersionUID = 1L;
    // 菜品编码
    private String vegCode; 
    // 菜品名称
    private String vegName;
    
    public String getVegCode() {
        return vegCode;
    }
    public void setVegCode(String vegCode) {
        this.vegCode = vegCode;
    }
    public String getVegName() {
        return vegName;
    }
    public void setVegName(String vegName) {
        this.vegName = vegName;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
