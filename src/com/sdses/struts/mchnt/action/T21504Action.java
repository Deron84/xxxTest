package com.sdses.struts.mchnt.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.huateng.common.Constants;
import com.huateng.common.ErrorCode;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.ContextUtil;
import com.sdses.bo.mchnt.T21503BO;
import com.sdses.bo.mchnt.T21504BO;
import com.sdses.po.TblMchtMarkSubsidy;
import com.sdses.po.TblMchtMarkSubsidyBind;

/**
 * 
 * //TODO 营销规则补贴
 * //TODO 添加类/接口功能描述
 * 
 * @author hanyongqing
 * @version
 */
public class T21504Action extends BaseAction {
    private static Logger logger = Logger.getLogger(T21504Action.class);

    T21504BO t21504BO = (T21504BO) ContextUtil.getBean("T21504BO");

    //  记录ID
    private String recordID;

    //  是否开启
    private String isOpen;

    //规则ID
    private String ruleID;

    //  商户号
    private String mchntNO;

    //  终端号
    private String termNO;

    private String all;

    @Override
    protected String subExecute() throws Exception {
        if ("add".equals(method)) {
            rspCode = add();
        } else if ("delete".equals(method)) {
            rspCode = delete();
        } else if ("update".equals(method)) {
            rspCode = update();
        }
        return rspCode;
    }

    private String update() {
        TblMchtMarkSubsidyBind tblMchtMarkSubsidyBind = t21504BO.get(recordID);
        if (tblMchtMarkSubsidyBind == null) {
            return ErrorCode.T21504_02;
        }
        tblMchtMarkSubsidyBind.setOpenFlag(isOpen);
        t21504BO.update(tblMchtMarkSubsidyBind);
        return Constants.SUCCESS_CODE;
    }

    private String delete() {
        TblMchtMarkSubsidyBind tblMchtMarkSubsidyBind = t21504BO.get(recordID);
        if (tblMchtMarkSubsidyBind != null) {
            t21504BO.delete(tblMchtMarkSubsidyBind);
        } else {
            return ErrorCode.T21504_02;
        }
        return Constants.SUCCESS_CODE;
    }

    /**
     * 
     * //TODO 添加记录信息
     *
     * @return
     */
    private String add() {
        if (StringUtils.isNotBlank(all)) {

            T21503BO t21503BO = (T21503BO) ContextUtil.getBean("T21503BO");
            TblMchtMarkSubsidy rule = null;
            if (StringUtils.isNotBlank(ruleID)) {
                rule = t21503BO.get(ruleID);
            }
            List<Object[]> dataList = new ArrayList<Object[]>();
            if (StringUtils.equals(all, "1")) {
                dataList.addAll(t21504BO.findTermListAllTwo());
            } else if (StringUtils.equals(all, "2")) {
                dataList.addAll(t21504BO.findTermListAllTwoMchntStr(mchntNO));
            } else if (StringUtils.equals(all, "3")) {
                dataList.addAll(t21504BO.findTermListAllTwoTermStr(termNO));
            }
            for (Object[] obj : dataList) {
                //       校验该终端号是否已被绑定且所选规则的时间是否冲突
                List<Object[]> binds = t21504BO.findByTermIdTimes(obj[1].toString(), rule.getEndTime());
                if (binds != null && binds.size() > 0) {
                    return "该终端号:" + obj[1] + "当前绑定的优惠规则有效，不能再次绑定!";
                }
            }
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String format = sdf.format(date);
            for (Object[] obj : dataList) {
                TblMchtMarkSubsidyBind bean = new TblMchtMarkSubsidyBind();
                Random rand = new Random();
                int midTmp = rand.nextInt(899999);
                midTmp = midTmp + 100000;
                String midVar = format + midTmp + "000000";
                midVar = midVar.substring(0, 20);
                bean.setRecordID(midVar);
                bean.setRuleID(ruleID);
                bean.setMchntNO(obj[0].toString());
                bean.setTermNO(obj[1].toString());
                bean.setOpenFlag(isOpen);
                bean.setBindTime(format);
                bean.setModifyTime(format);
                t21504BO.update(bean);
            }
            return Constants.SUCCESS_CODE;
        } else {
            return "传入后台系统参数丢失，请联系管理员!";
        }
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        T21504Action.logger = logger;
    }

    public T21504BO getT21504BO() {
        return t21504BO;
    }

    public void setT21504BO(T21504BO t21504bo) {
        t21504BO = t21504bo;
    }

    public String getRecordID() {
        return recordID;
    }

    public void setRecordID(String recordID) {
        this.recordID = recordID;
    }

    public String getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    public String getMchntNO() {
        return mchntNO;
    }

    public void setMchntNO(String mchntNO) {
        this.mchntNO = mchntNO;
    }

    public String getTermNO() {
        return termNO;
    }

    public void setTermNO(String termNO) {
        this.termNO = termNO;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public String getRuleID() {
        return ruleID;
    }

    public void setRuleID(String ruleID) {
        this.ruleID = ruleID;
    }
}
