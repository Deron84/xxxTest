/*
 * Copyright (C), 2012-2013, 上海华腾软件系统有限公司
 * FileName: T21100Action.java
 * Author:   Feihong247
 * Date:     2013-10-16 上午9:27:42
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.huateng.struts.mchnt.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.huateng.bo.mchnt.T21100BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.po.TblBankBinInf;
import com.huateng.po.mchnt.TblInCardManagent;
import com.huateng.po.mchnt.TblInCardManagentPK;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.GenerateNextId;

/**
 * 转出卡管理<br> 
 * 〈功能详细描述〉
 * 〈方法简述 - 方法描述〉
 *  
 * @author Feihong247
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class T21100Action extends BaseAction {

    /* (non-Javadoc)
     * @see com.huateng.struts.system.action.BaseAction#subExecute()
     */
    private T21100BO t21100BO = (T21100BO) ContextUtil.getBean("T21100BOTarget");
//    private ITblInCardManagentDAO tblInCardManagentDAO ;
//    T21100BO t21100BO = new T21100BOTarget();
    @Override
    protected String subExecute() throws Exception {
        // TODO Auto-generated method stub
        try{
            if("add".equals(method)){
                rspCode = add();
            }else if("delete".equals(method)){
                rspCode = delete();
            }else if("upload".equals(method)){
                rspCode = upload();
            }else if("update".equals(method)){
                rspCode = update();
            }
        } catch (Exception e) {
            log("操作员编号：" + operator.getOprId()+ "，商户转入卡管理操作失败" + getMethod() + "失败，失败原因为："+e.getMessage());
        }
        return rspCode;
    }
    
    public String add() throws IllegalAccessException, InvocationTargetException{
        
        TblInCardManagent tblInCardManagent = new TblInCardManagent();
        
        TblInCardManagentPK tblInCardManagentPK = new TblInCardManagentPK();
        
        tblInCardManagentPK.setMchntCd(mchtNo);
        tblInCardManagentPK.setInCardNum(inCardNum);
        tblInCardManagentPK.setInOutFlag(inOutFlag);

        String inCardNumQ = GenerateNextId.getInCardNum(tblInCardManagentPK);
        if(Constants.SUCCESS_CODE.equals(inCardNumQ)){
            tblInCardManagent.setId(tblInCardManagentPK);
            tblInCardManagent.setContactName(contactName);
            tblInCardManagent.setContactTel(contactTel);
            tblInCardManagent.setContactIdentify(contactIdentify);
            tblInCardManagent.setDealBeginTime(dealBeginTime);
            tblInCardManagent.setDealEndTime(dealEndTime);
            tblInCardManagent.setTermId(termId);
            tblInCardManagent.setInFlag(inFlag);
            tblInCardManagent.setMessageLimit(messageLimit);
            tblInCardManagent.setUseFlag(useFlag);
            
            tblInCardManagent.setDealBeginTime(dealBeginTime);
            tblInCardManagent.setDealEndTime(dealEndTime);
            
            t21100BO.addInCardMcht(tblInCardManagent);
            
            return Constants.SUCCESS_CODE ;
        }else{
            return "您所要添加的信息已经存在，请重新选择添加";
        }
            
    }
    
    public String delete() throws IllegalAccessException, InvocationTargetException{
        
        String returnCode = null;
        
        TblInCardManagentPK id = new TblInCardManagentPK(mchtNo, inCardNum,inOutFlag);
        TblInCardManagent tblInCardManagent = t21100BO.get(id);
        if(inCardNum != null && mchtNo != null && inOutFlag != null){
            t21100BO.delete(tblInCardManagent);
            returnCode = Constants.SUCCESS_CODE;
        }else{
            returnCode = "请选择您要删除的转入卡信息……";
        }
        return returnCode;
    }
    
    public String upload() throws IllegalAccessException, InvocationTargetException{
        
        List<TblInCardManagent> tblInCardManagentList = new ArrayList<TblInCardManagent>();
//      Map<String, String> map = new HashMap<String, String>();
        try {
            TblInCardManagent tblInCardManagent = null;
            TblInCardManagentPK tblInCardManagentPK = null;
            for(File file : files) {
                BufferedReader reader = 
                    new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
                while(reader.ready()){
                    String tmp = reader.readLine();
                    if (!StringUtil.isNull(tmp)) {
                        
                        String[] data = tmp.split(",");
                        tblInCardManagent = new TblInCardManagent();
                        tblInCardManagentPK = new TblInCardManagentPK();
                        
                        tblInCardManagentPK.setMchntCd(data[0]);
                        tblInCardManagentPK.setInCardNum(data[2]);
                        tblInCardManagentPK.setInOutFlag(data[8]);
                        
                        tblInCardManagent.setId(tblInCardManagentPK);
                        tblInCardManagent.setTermId(data[1]);
                        tblInCardManagent.setContactName(data[3]);
                        tblInCardManagent.setContactTel(data[4]);
                        tblInCardManagent.setContactIdentify(data[5]);
                        tblInCardManagent.setInFlag(data[6]);
                        tblInCardManagent.setUseFlag(data[7]);
                        tblInCardManagent.setMessageLimit(data[9]);
                        
                        tblInCardManagent.setDealBeginTime(data[10]);
                        tblInCardManagent.setDealEndTime(data[11]);
                        
                        tblInCardManagentList.add(tblInCardManagent);
                    }
                }
                reader.close();
            }
            
            int success = 0;
            int fail = 0;
            
            for(TblInCardManagent inf:tblInCardManagentList){
                
                String sql = "SELECT COUNT(1) FROM TBL_IN_CARD_MANAGENT WHERE " +
                            "MCHT_NO = '" + inf.getId().getMchntCd() + 
                            "' AND IN_CARDNUM = '" + inf.getId().getInCardNum()+ "'";
                BigDecimal count = (BigDecimal) CommonFunction.getCommQueryDAO().findBySQLQuery(sql).get(0);
                if (count.intValue() != 0) {
                    fail++;
                    continue;
                }
                t21100BO.addInCardMcht(tblInCardManagent);
                success++;
            }
            return Constants.SUCCESS_CODE_CUSTOMIZE + 
                "成功录入条目：" + String.valueOf(success) + ",已存在的条目：" + String.valueOf(fail);
        } catch (Exception e) {
            e.printStackTrace();
            return "解析文件失败";
        }
    }
    
    public String update() throws IllegalAccessException, InvocationTargetException{
TblInCardManagent tblInCardManagent = new TblInCardManagent();
        
        TblInCardManagentPK tblInCardManagentPK = new TblInCardManagentPK();
        
        tblInCardManagentPK.setMchntCd(mchtNo);
        tblInCardManagentPK.setInCardNum(inCardNum);
        tblInCardManagentPK.setInOutFlag(inOutFlag);
        
        tblInCardManagent.setId(tblInCardManagentPK);
        tblInCardManagent.setContactName(contactName);
        tblInCardManagent.setContactTel(contactTel);
        tblInCardManagent.setContactIdentify(contactIdentify);
        tblInCardManagent.setDealBeginTime(dealBeginTime);
        tblInCardManagent.setDealEndTime(dealEndTime);
        tblInCardManagent.setTermId(termId);
        tblInCardManagent.setInFlag(inFlag);
        tblInCardManagent.setMessageLimit(messageLimit);
        tblInCardManagent.setUseFlag(useFlag);
        
        tblInCardManagent.setDealBeginTime(dealBeginTime);
        tblInCardManagent.setDealEndTime(dealEndTime);
        
        t21100BO.update(tblInCardManagent);
        
        return Constants.SUCCESS_CODE;
    }
 // primary key
    private java.lang.String mchtNo;
    private java.lang.String inCardNum;
    private java.lang.String inOutFlag;
    // fields
    private java.lang.String termId;
    private java.lang.String contactTel;
    private java.lang.String  contactName;
    private java.lang.String contactIdentify;
    private java.lang.String inFlag;
    private java.lang.String useFlag;
    private java.lang.String messageLimit;
    private java.lang.String dealBeginTime;
    private java.lang.String dealEndTime;

    // 文件集合
    private List<File> files;
    // 文件名称集合
    private List<String> filesFileName;
    /**
     * @return the files
     */
    public List<File> getFiles() {
        return files;
    }

    /**
     * @param files the files to set
     */
    public void setFiles(List<File> files) {
        this.files = files;
    }

    /**
     * @return the filesFileName
     */
    public List<String> getFilesFileName() {
        return filesFileName;
    }

    /**
     * @param filesFileName the filesFileName to set
     */
    public void setFilesFileName(List<String> filesFileName) {
        this.filesFileName = filesFileName;
    }

    /**
     * @return the t21100BO
     */
    public T21100BO getT21100BO() {
        return t21100BO;
    }
    /**
     * @param t21100bo the t21100BO to set
     */
    public void setT21100BO(T21100BO t21100bo) {
        t21100BO = t21100bo;
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
     * @return the mchtNo
     */
    public java.lang.String getMchtNo() {
        return mchtNo;
    }
    /**
     * @param mchtNo the mchtNo to set
     */
    public void setMchtNo(java.lang.String mchtNo) {
        this.mchtNo = mchtNo;
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
