/*
 * Copyright (C), 2012-2013, 上海华腾软件系统有限公司
 * FileName: TblInCardManagentPK.java
 * Author:   Feihong247
 * Date:     2013-10-16 上午9:47:42
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.po.mchnt;

import java.io.Serializable;

/**
 *  
 * @author Feihong247
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class TblInCardManagentPK implements Serializable{
    protected int hashCode = Integer.MIN_VALUE;
   
    private java.lang.String mchntCd;
    private java.lang.String inCardNum;
    private java.lang.String inOutFlag;
    
    public TblInCardManagentPK() {
        super();
    }

    /**
     * @param mchntCd
     * @param inCardNum
     */
    public TblInCardManagentPK(String mchntCd, String inCardNum,String inOutFlag) {
        super();
        this.inOutFlag = inOutFlag;
        this.mchntCd = mchntCd;
        this.inCardNum = inCardNum;
    }

    
    /**
     * @return the inOutFlag
     */
    public java.lang.String getInOutFlag() {
        return inOutFlag;
    }

    /**
     * @param inOutFlag the inOutFlag to set
     */
    public void setInOutFlag(java.lang.String inOutFlag) {
        this.inOutFlag = inOutFlag;
    }

    /**
     * @return the mchntCd
     */
    public java.lang.String getMchntCd() {
        return mchntCd;
    }

    /**
     * @param mchntCd the mchntCd to set
     */
    public void setMchntCd(java.lang.String mchntCd) {
        this.mchntCd = mchntCd;
    }

    /**
     * @return the inCardNum
     */
    public java.lang.String getInCardNum() {
        return inCardNum;
    }

    /**
     * @param inCardNum the inCardNum to set
     */
    public void setInCardNum(java.lang.String inCardNum) {
        this.inCardNum = inCardNum;
    }

    public boolean equals (Object obj) {
        if (null == obj) return false;
        if (!(obj instanceof TblInCardManagentPK)) return false;
        else {
            TblInCardManagentPK mObj = (TblInCardManagentPK) obj;
            if (null != this.getMchntCd() && null != mObj.getMchntCd()) {
                if (!this.getMchntCd().equals(mObj.getMchntCd())) {
                    return false;
                }
            }
            else {
                return false;
            }
            if (null != this.getInCardNum() && null != mObj.getInCardNum()) {
                if (!this.getInCardNum().equals(mObj.getInCardNum())) {
                    return false;
                }
            }
            else {
                return false;
            }
            return true;
        }
    }

    public int hashCode () {
        if (Integer.MIN_VALUE == this.hashCode) {
            StringBuffer sb = new StringBuffer();
            if (null != this.getMchntCd()) {
                sb.append(this.getMchntCd().hashCode());
                sb.append(":");
            }
            else {
                return super.hashCode();
            }
            if (null != this.getInCardNum()) {
                sb.append(this.getInCardNum().hashCode());
                sb.append(":");
            }
            else {
                return super.hashCode();
            }
            this.hashCode = sb.toString().hashCode();
        }
        return this.hashCode;
    }
}

