/*
 * Copyright (C), 2012-2013, 上海华腾软件系统有限公司
 * FileName: TblInCardManagent.java
 * Author:   Feihong247
 * Date:     2013-10-16 上午9:36:43
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.po.mchnt;

import java.io.Serializable;

/**
 * @author Feihong247
 * 
 * @hibernate.class table="TBL_IN_CARD_MANAGENT"
 */
public class TblInCardManagent implements Serializable {

    /**
     */
    private static final long serialVersionUID = 1L;
    
    public static String MCHT_NO = "MchtNo";
    public static String TERM_ID = "TermId";
    public static String IN_CARDNUM = "InCardNum";
    public static String CONTACT_NAME = "ContactName";
    public static String CONTACT_TEL = "ContactTel";
    public static String IN_FLAG = "InFlag";
    public static String USE_FLAG = "UseFlag";
    public static String MESSAGE_LIMIT = "MessageLimit";
    public static String DEAL_BEGINTIME = "DealBeginTime";
    public static String DEAL_ENDTIME = "DealEndTime";
    
    // primary key
    private TblInCardManagentPK id ;
    // fields
    private java.lang.String termId;
    private java.lang.String contactTel;
    private java.lang.String contactName;
    private java.lang.String contactIdentify;
    private java.lang.String inFlag;
    private java.lang.String useFlag;
    private java.lang.String messageLimit;
    private java.lang.String dealBeginTime;
    private java.lang.String dealEndTime;
    
    public TblInCardManagent() {
        super();
    }

    /**
     * @param id
     * @param termId
     * @param contactTel
     * @param contactName
     * @param contactIdentify
     * @param inFlag
     * @param useFlag
     * @param messageLimit
     * @param dealBeginTime
     * @param dealEndTime
     */
    public TblInCardManagent(TblInCardManagentPK id, String termId, String contactTel, String contactName,
            String contactIdentify, String inFlag, String useFlag, String messageLimit, String dealBeginTime,
            String dealEndTime) {
        super();
        this.id = id;
        this.termId = termId;
        this.contactTel = contactTel;
        this.contactName = contactName;
        this.contactIdentify = contactIdentify;
        this.inFlag = inFlag;
        this.useFlag = useFlag;
        this.messageLimit = messageLimit;
        this.dealBeginTime = dealBeginTime;
        this.dealEndTime = dealEndTime;
    }

    /**
     * @return the id
     */
    public TblInCardManagentPK getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(TblInCardManagentPK id) {
        this.id = id;
    }

    /**
     * @return the termId
     */
    public java.lang.String getTermId() {
        return termId;
    }

    /**
     * @param termId the termId to set
     */
    public void setTermId(java.lang.String termId) {
        this.termId = termId;
    }

    /**
     * @return the contactTel
     */
    public java.lang.String getContactTel() {
        return contactTel;
    }

    /**
     * @param contactTel the contactTel to set
     */
    public void setContactTel(java.lang.String contactTel) {
        this.contactTel = contactTel;
    }

    /**
     * @return the contactName
     */
    public java.lang.String getContactName() {
        return contactName;
    }

    /**
     * @param contactName the contactName to set
     */
    public void setContactName(java.lang.String contactName) {
        this.contactName = contactName;
    }

    /**
     * @return the contactIdentify
     */
    public java.lang.String getContactIdentify() {
        return contactIdentify;
    }

    /**
     * @param contactIdentify the contactIdentify to set
     */
    public void setContactIdentify(java.lang.String contactIdentify) {
        this.contactIdentify = contactIdentify;
    }

    /**
     * @return the inFlag
     */
    public java.lang.String getInFlag() {
        return inFlag;
    }

    /**
     * @param inFlag the inFlag to set
     */
    public void setInFlag(java.lang.String inFlag) {
        this.inFlag = inFlag;
    }

    /**
     * @return the useFlag
     */
    public java.lang.String getUseFlag() {
        return useFlag;
    }

    /**
     * @param useFlag the useFlag to set
     */
    public void setUseFlag(java.lang.String useFlag) {
        this.useFlag = useFlag;
    }

    /**
     * @return the messageLimit
     */
    public java.lang.String getMessageLimit() {
        return messageLimit;
    }

    /**
     * @param messageLimit the messageLimit to set
     */
    public void setMessageLimit(java.lang.String messageLimit) {
        this.messageLimit = messageLimit;
    }

    /**
     * @return the dealBeginTime
     */
    public java.lang.String getDealBeginTime() {
        return dealBeginTime;
    }

    /**
     * @param dealBeginTime the dealBeginTime to set
     */
    public void setDealBeginTime(java.lang.String dealBeginTime) {
        this.dealBeginTime = dealBeginTime;
    }

    /**
     * @return the dealEndTime
     */
    public java.lang.String getDealEndTime() {
        return dealEndTime;
    }

    /**
     * @param dealEndTime the dealEndTime to set
     */
    public void setDealEndTime(java.lang.String dealEndTime) {
        this.dealEndTime = dealEndTime;
    }

}
