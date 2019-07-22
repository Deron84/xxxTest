package com.sdses.struts.mchnt.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.huateng.common.Constants;
import com.huateng.common.ErrorCode;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.ContextUtil;
import com.sdses.bo.mchnt.T21503BO;
import com.sdses.bo.mchnt.T21504BO;
import com.sdses.po.TblMchtMarkSubsidy;

import net.sf.json.JSONObject;

/**
 * 
 * //TODO 营销规则补贴
 * //TODO 添加类/接口功能描述
 * 
 * @author hanyongqing
 * @version
 */
public class T21503Action extends BaseAction {
    private static Logger logger = Logger.getLogger(T21503Action.class);

    T21503BO t21503BO = (T21503BO) ContextUtil.getBean("T21503BO");

    //  规则ID
    private String ruleID;

    //  规则名称
    private String ruleName;

    //  补贴类型
    private String subsidyType;

    //  开启方式
    private String openType;

    //  每笔消费满
    private String subsidyRuleFull;

    //  补金额
    private String subsidyRuleFill;

    //  限制额度
    private String subsidyRuleLimit;

    //  折扣
    private String subsidyRuleDist;

    //  卡BIN 
    private String cardBin;

    //  开始时间
    private String startTime;

    //  结束时间
    private String endTime;

    //  备注
    private String remark;

    //  按卡限次
    private String cardLimit;

    //  按终端限次
    private String termLimit;

    //  按商户限次
    private String mchtLimit;

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
        List<Object[]> resultList = t21503BO.findRuleListAll(ruleID);
        if (resultList.size() > 0) {
            return ErrorCode.T21503_04;
        }
        TblMchtMarkSubsidy tblMchtMarkSudy = new TblMchtMarkSubsidy();
        tblMchtMarkSudy.setRuleID(ruleID.replaceAll(" ", ""));
        tblMchtMarkSudy.setRuleName(ruleName.replaceAll(" ", ""));
        tblMchtMarkSudy.setSubsidyType(subsidyType.replaceAll(" ", ""));
        String midStr = "";
        if (subsidyType.equals("00")) {
            midStr = fillString(subsidyRuleFull, subsidyRuleFill);
        } else if (subsidyType.equals("01")) {
            midStr = fillString2(subsidyRuleLimit, subsidyRuleDist);
        } else {
            midStr = "未知";
        }
        tblMchtMarkSudy.setSubsidyRule(midStr);
        tblMchtMarkSudy.setOpenType(openType.replaceAll(" ", ""));
        tblMchtMarkSudy.setCardBin(cardBin.replaceAll(" ", ""));
        tblMchtMarkSudy.setCardLimit(fillString3(cardLimit).replaceAll(" ", ""));
        tblMchtMarkSudy.setTermLimit(fillString3(termLimit).replaceAll(" ", ""));
        tblMchtMarkSudy.setMchtLimit(fillString3(mchtLimit).replaceAll(" ", ""));
        tblMchtMarkSudy.setRemark(remark.replaceAll(" ", ""));
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");//yyyy-MM-dd HH:mm:ss
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMMddHHmmss");
        if (startTime != null && endTime != null) {
            try {
                Date sTime = sdf2.parse(startTime);
                Date eTime = sdf2.parse(endTime);
                tblMchtMarkSudy.setStartTime(sdf.format(sTime));
                tblMchtMarkSudy.setEndTime(sdf.format(eTime));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            tblMchtMarkSudy.setStartTime(sdf.format(new Date()));
            tblMchtMarkSudy.setEndTime(sdf.format(new Date()));
        }
        tblMchtMarkSudy.setAddTime(sdf3.format(new Date()));
        tblMchtMarkSudy.setModifyTime(sdf3.format(new Date()));
        TblMchtMarkSubsidy tblMchtMarkSubsidy = t21503BO.get(ruleID);
        if (tblMchtMarkSubsidy == null) {
            t21503BO.save(tblMchtMarkSudy);
        } else {
            tblMchtMarkSudy.setAddTime(tblMchtMarkSubsidy.getAddTime());
            t21503BO.update(tblMchtMarkSudy);
        }
        return Constants.SUCCESS_CODE;

    }

    private String delete() {
        // 判断此规则是否被使用
        T21504BO t21504BO = (T21504BO) ContextUtil.getBean("T21504BO");
        List<Object[]> binds = t21504BO.findBindListByDiscountId(ruleID);
        if (binds != null && binds.size() > 0) {
            return "此补贴规则已经使用，不能删除！";
        }
        TblMchtMarkSubsidy tblMchtMarkSubsidy = t21503BO.get(ruleID);
        if (tblMchtMarkSubsidy != null) {
            t21503BO.delete(tblMchtMarkSubsidy);
        } else {
            return ErrorCode.T21503_02;
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
        //判断：营销编码唯一
        TblMchtMarkSubsidy tblMchtMarkSubsidy = t21503BO.get(ruleID);
        if (tblMchtMarkSubsidy != null) {
            //重新生成代码，如果还存在则返回错误
            ruleID = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + ruleID.substring(17, 20);
            tblMchtMarkSubsidy = t21503BO.get(ruleID);
            if (tblMchtMarkSubsidy != null) {
                return ErrorCode.T21503_01;
            }
        }
        TblMchtMarkSubsidy tblMchtMarkSudy = new TblMchtMarkSubsidy();
        tblMchtMarkSudy.setRuleID(ruleID.replaceAll(" ", ""));
        tblMchtMarkSudy.setRuleName(ruleName.replaceAll(" ", ""));
        tblMchtMarkSudy.setSubsidyType(subsidyType.replaceAll(" ", ""));
        String midStr = "";
        if (subsidyType.equals("00")) {
            midStr = fillString(subsidyRuleFull, subsidyRuleFill);
        } else if (subsidyType.equals("01")) {
            midStr = fillString2(subsidyRuleLimit, subsidyRuleDist);
        } else {
            midStr = "未知";
        }
        tblMchtMarkSudy.setSubsidyRule(midStr);
        tblMchtMarkSudy.setOpenType(openType.replaceAll(" ", ""));
        tblMchtMarkSudy.setCardBin(cardBin.replaceAll(" ", ""));
        tblMchtMarkSudy.setCardLimit(fillString3(cardLimit).replaceAll(" ", ""));
        tblMchtMarkSudy.setTermLimit(fillString3(termLimit).replaceAll(" ", ""));
        tblMchtMarkSudy.setMchtLimit(fillString3(mchtLimit).replaceAll(" ", ""));
        tblMchtMarkSudy.setRemark(remark.replaceAll(" ", ""));
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");//yyyy-MM-dd HH:mm:ss
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMMddHHmmss");
        if (startTime != null && endTime != null) {
            try {
                Date sTime = sdf2.parse(startTime);
                Date eTime = sdf2.parse(endTime);
                tblMchtMarkSudy.setStartTime(sdf.format(sTime));
                tblMchtMarkSudy.setEndTime(sdf.format(eTime));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            tblMchtMarkSudy.setStartTime(sdf.format(new Date()));
            tblMchtMarkSudy.setEndTime(sdf.format(new Date()));
        }
        tblMchtMarkSudy.setAddTime(sdf3.format(new Date()));
        tblMchtMarkSudy.setModifyTime(sdf3.format(new Date()));
        t21503BO.save(tblMchtMarkSudy);
        return Constants.SUCCESS_CODE;
    }

    public String getData() {
        try {
            TblMchtMarkSubsidy tblMchtMarkSubsidy = t21503BO.get(ruleID);
            String subsidyType2 = tblMchtMarkSubsidy.getSubsidyType();
            if (subsidyType2.equals("00")) {
                if (tblMchtMarkSubsidy.getSubsidyRule() != null && !tblMchtMarkSubsidy.getSubsidyRule().equals("")) {
                    String replaceAll = tblMchtMarkSubsidy.getSubsidyRule().substring(0, 12).replaceAll("^(0+)", "");
                    tblMchtMarkSubsidy.setSubsidyRuleFull(replaceAll);
                    String replaceAll2 = tblMchtMarkSubsidy.getSubsidyRule().substring(12, 24).replaceAll("^(0+)", "");
                    tblMchtMarkSubsidy.setSubsidyRuleFill(replaceAll2);
                }
            } else if (subsidyType2.equals("01")) {
                if (tblMchtMarkSubsidy.getSubsidyRule() != null && !tblMchtMarkSubsidy.getSubsidyRule().equals("")) {
                    String replaceAll = tblMchtMarkSubsidy.getSubsidyRule().substring(0, 3).replaceAll("^(0+)", "");
                    tblMchtMarkSubsidy.setSubsidyRuleDist(replaceAll);
                    String replaceAll2 = tblMchtMarkSubsidy.getSubsidyRule().substring(3, 15).replaceAll("^(0+)", "");
                    tblMchtMarkSubsidy.setSubsidyRuleLimit(replaceAll2);
                }
            }

            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date tm = sdf.parse(tblMchtMarkSubsidy.getStartTime());
            String format = sdf2.format(tm);
            tblMchtMarkSubsidy.setStartTime(format);
            Date tm2 = sdf.parse(tblMchtMarkSubsidy.getEndTime());
            String format2 = sdf2.format(tm2);
            tblMchtMarkSubsidy.setEndTime(format2);

            Map<String, Object> midMap = new HashMap<String, Object>();
            midMap.put("success", true);
            midMap.put("msg", tblMchtMarkSubsidy);
            String str = JSONObject.fromObject(midMap).toString();
            PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
            printWriter.write(str);
            printWriter.flush();
            printWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 
     * //TODO 组成24位字符串
     *
     * @param fullStr 每笔交易的金额数
     * @param fillStr 补助的金额
     * @return
     */
    private String fillString(String fullStr, String fillStr) {
        if (fullStr != null && !fullStr.equals("")) {
            int fullLgh = fullStr.length();
            for (int i = fullLgh; i < 12; i++) {
                fullStr = "0" + fullStr;
            }
        } else {
            fullStr = "000000000000";
        }
        if (fillStr != null && !fillStr.equals("")) {
            int fillLgh = fillStr.length();
            for (int i = fillLgh; i < 12; i++) {
                fillStr = "0" + fillStr;
            }
        } else {
            fillStr = "000000000000";
        }
        return fullStr + fillStr;
    }

    /**
     * 
     * //TODO 组成15位字符串
     *
     * @param limitStr 限定每笔交易额度
     * @param distStr 折扣比例
     * @return
     */
    private String fillString2(String limitStr, String distStr) {
        if (limitStr != null && !limitStr.equals("")) {
            int limitLgh = limitStr.length();
            for (int i = limitLgh; i < 12; i++) {
                limitStr = "0" + limitStr;
            }
        } else {
            limitStr = "000000000000";
        }
        if (distStr != null && !distStr.equals("")) {
            int distLgh = distStr.length();
            for (int i = distLgh; i < 3; i++) {
                distStr = "0" + distStr;
            }
        } else {
            distStr = "000";
        }
        return distStr + limitStr;
    }

    /**
    * 
    * //TODO 组成4位字符串
    *
    * @param limitStr
    * @return
    */
    private String fillString3(String limitStr) {
        if (limitStr != null && !limitStr.equals("")) {
            int limitLgh = limitStr.length();
            for (int i = limitLgh; i < 4; i++) {
                limitStr = "0" + limitStr;
            }
        } else {
            limitStr = "0000";
        }
        return limitStr;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        T21503Action.logger = logger;
    }

    public T21503BO getT21503BO() {
        return t21503BO;
    }

    public void setT21503BO(T21503BO t21503bo) {
        t21503BO = t21503bo;
    }

    public String getRuleID() {
        return ruleID;
    }

    public void setRuleID(String ruleID) {
        this.ruleID = ruleID;
    }

    public String getSubsidyType() {
        return subsidyType;
    }

    public void setSubsidyType(String subsidyType) {
        this.subsidyType = subsidyType;
    }

    public String getSubsidyRuleFull() {
        return subsidyRuleFull;
    }

    public void setSubsidyRuleFull(String subsidyRuleFull) {
        this.subsidyRuleFull = subsidyRuleFull;
    }

    public String getSubsidyRuleFill() {
        return subsidyRuleFill;
    }

    public void setSubsidyRuleFill(String subsidyRuleFill) {
        this.subsidyRuleFill = subsidyRuleFill;
    }

    public String getSubsidyRuleLimit() {
        return subsidyRuleLimit;
    }

    public void setSubsidyRuleLimit(String subsidyRuleLimit) {
        this.subsidyRuleLimit = subsidyRuleLimit;
    }

    public String getSubsidyRuleDist() {
        return subsidyRuleDist;
    }

    public void setSubsidyRuleDist(String subsidyRuleDist) {
        this.subsidyRuleDist = subsidyRuleDist;
    }

    public String getCardBin() {
        return cardBin;
    }

    public void setCardBin(String cardBin) {
        this.cardBin = cardBin;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCardLimit() {
        return cardLimit;
    }

    public void setCardLimit(String cardLimit) {
        this.cardLimit = cardLimit;
    }

    public String getTermLimit() {
        return termLimit;
    }

    public void setTermLimit(String termLimit) {
        this.termLimit = termLimit;
    }

    public String getMchtLimit() {
        return mchtLimit;
    }

    public void setMchtLimit(String mchtLimit) {
        this.mchtLimit = mchtLimit;
    }

    public String getOpenType() {
        return openType;
    }

    public void setOpenType(String openType) {
        this.openType = openType;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

}
