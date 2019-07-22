package com.huateng.po.base;

import java.io.Serializable;

public abstract class BaseTblBrhTlrInfoPK implements Serializable {

    protected int hashCode = Integer.MIN_VALUE;

    private java.lang.String tlrId;

    private java.lang.String brhId;

    public BaseTblBrhTlrInfoPK() {
    }

    public BaseTblBrhTlrInfoPK(java.lang.String tlrId, java.lang.String brhId) {

        this.setTlrId(tlrId);
        this.setBrhId(brhId);
    }

    /**
     * Return the value associated with the column: TLR_ID
     */
    public java.lang.String getTlrId() {
        return tlrId;
    }

    /**
     * Set the value related to the column: TLR_ID
     * @param tlrId the TLR_ID value
     */
    public void setTlrId(java.lang.String tlrId) {
        this.tlrId = tlrId;
    }

    /**
     * Return the value associated with the column: BRH_ID
     */
    public java.lang.String getBrhId() {
        return brhId;
    }

    /**
     * Set the value related to the column: BRH_ID
     * @param brhId the BRH_ID value
     */
    public void setBrhId(java.lang.String brhId) {
        this.brhId = brhId;
    }

    public boolean equals(Object obj) {
        if (null == obj) return false;
        if (!(obj instanceof com.huateng.po.TblBrhTlrInfoPK))
            return false;
        else {
            com.huateng.po.TblBrhTlrInfoPK mObj = (com.huateng.po.TblBrhTlrInfoPK) obj;
            if (null != this.getTlrId() && null != mObj.getTlrId()) {
                if (!this.getTlrId().equals(mObj.getTlrId())) {
                    return false;
                }
            } else {
                return false;
            }
            if (null != this.getBrhId() && null != mObj.getBrhId()) {
                if (!this.getBrhId().equals(mObj.getBrhId())) {
                    return false;
                }
            } else {
                return false;
            }
            return true;
        }
    }

    public int hashCode() {
        if (Integer.MIN_VALUE == this.hashCode) {
            StringBuilder sb = new StringBuilder();
            if (null != this.getTlrId()) {
                sb.append(this.getTlrId().hashCode());
                sb.append(":");
            } else {
                return super.hashCode();
            }
            if (null != this.getBrhId()) {
                sb.append(this.getBrhId().hashCode());
                sb.append(":");
            } else {
                return super.hashCode();
            }
            this.hashCode = sb.toString().hashCode();
        }
        return this.hashCode;
    }

}