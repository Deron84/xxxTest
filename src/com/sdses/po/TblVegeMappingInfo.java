package com.sdses.po;

import java.io.Serializable;

public class TblVegeMappingInfo implements Serializable{
    private static final long serialVersionUID = 1L;
    
    // 主键类
    private TblVegeMappingInfoPK tblVegeMappingInfoPK;
    // 菜品编码
    private String vegeCode;
    
    public TblVegeMappingInfoPK getTblVegeMappingInfoPK() {
        return tblVegeMappingInfoPK;
    }
    public void setTblVegeMappingInfoPK(TblVegeMappingInfoPK tblVegeMappingInfoPK) {
        this.tblVegeMappingInfoPK = tblVegeMappingInfoPK;
    }
    public String getVegeCode() {
        return vegeCode;
    }
    public void setVegeCode(String vegeCode) {
        this.vegeCode = vegeCode;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
