package com.huateng.struts.pos.action;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.huateng.bo.term.T3010BO;
import com.huateng.common.Constants;
import com.huateng.dao.common.HqlDao;
import com.huateng.po.TblTermInfTmp;
import com.huateng.po.TblTermInfTmpPK;
import com.huateng.struts.pos.TblTermInfConstants;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.AESUtil;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.GenerateNextId;
import com.huateng.system.util.SysParamUtil;

/**
 * Title:终端维护
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-17
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @version 1.0
 */
public class T30101Action extends BaseAction {

    private static final long serialVersionUID = -4511548416739218414L;

    private HqlDao dao;

    T3010BO t3010BO = (T3010BO) ContextUtil.getBean("t3010BO");

    private String termIdNew;

    private String mchnNoNew;

    private String brhIdNew;

    private String termIdIdNew;

    private String termFactoryNew;

    private String termMachTpNew;

    private String termMccNew;

    private String termVerNew;

    private String termTpNew;

    private String paramDownSignNew;

    private String icDownSignNew;

    private String contTelNew;

    private String propTpNew;

    private String propInsNmNew;

    private String termPara1New;

    private String termBatchNmNew;

    private String termStlmDtNew;

    private String connectModeNew;

    private String equipInvIdNew;

    private String equipInvNmNew;

    private String bindTel1New;

    private String bindTel2New;

    private String bindTel3New;

    private String termAddrNew;

    private String termPlaceNew;

    private String oprNmNew;

    private String keyDownSignNew;

    private String productCdNew;

    private String reserveFlag1New;

    private String licenceNo;

    private String busTypeNew;

    private String cardTypeNew;

    private String depositFlagNew;

    private String depositAmtNew;

    private String depositStateNew;

    private String leaseFeeNew;

    private String checkCardNoNew;

    /** 单笔限额 */
    private String termSingleLimitNew;

    // 第三方服务机构分润比例 prop_ins_rate
    private String propInsRateNew;

    // 租金(终端占商户场地、付给商户)
    private String rentFeeNew;

    private String termNameNew;

    //private String mappingMchntTypeOne;
    private String mappingMchntcdOne;

    //private String mappingMchntTypeTwo;
    private String mappingMchntcdTwo;

    private String mappingTermidOne;

    private String mappingTermidTwo;

    /** 终端新增 */
    @Override
    protected String subExecute() throws Exception {
        try {
            String termId = GenerateNextId.getTermId(brhIdNew);
            if (Constants.FAILURE_CODE.equals(termId)) {
                return Constants.FAILURE_CODE;
            }
            //收单方终端号不为空时，不能重复。
            List list1 = t3010BO.qryTermInfosByOne(mappingTermidOne, mappingMchntcdTwo);
            if (list1 != null && list1.size() > 0) {
                return "此终端号" + mappingTermidOne + "已存在";
            }
            List list2 = t3010BO.qryTermInfosByTwo(mappingTermidTwo, mappingMchntcdTwo);
            if (list2 != null && list2.size() > 0) {
                return "此终端号" + mappingTermidTwo + "已存在";
            }

            List list4 = t3010BO.queryMchntInfo(mchnNoNew);
            if (list4 != null && list4.size() > 0) {
                String status = (String) list4.get(0);
                if (!status.equals("0")) {
                    return "该商户处于非正常状态,不能添加终端";
                }
            }

            String info = SysParamUtil.getParam("INFO");
            System.out.println(info);
            AESUtil aes = new AESUtil();
            info = aes.getRealCount(info);
            List list3 = t3010BO.getTermCount();
            if (list3 == null) {
                return "获取终端数量信息不成功，请稍后重试";
            }
            if (list3 != null && list3.size() > 0) {
                String currCount = list3.get(0).toString();
                if (Integer.parseInt(currCount) >= Integer.parseInt(info)) {
                    return "已经达到最大终端数量，不能继续添加终端";
                }
            }

            TblTermInfTmp tblTermInf = new TblTermInfTmp();
            TblTermInfTmpPK pk = new TblTermInfTmpPK();
            if (StringUtils.isNotEmpty(brhIdNew)) {
                pk.setTermId(termId);
                pk.setMchtCd(mchnNoNew);
                tblTermInf.setId(pk);
            } else {
                return TblTermInfConstants.T30101_01;
            }
            //终端名称
            tblTermInf.setTermName(termNameNew);
            tblTermInf.setTermBranch(brhIdNew);
            tblTermInf.setTermAddr(termAddrNew);
            tblTermInf.setTermSta(TblTermInfConstants.TERM_ST_INIT);
            tblTermInf.setTermSignSta(TblTermInfConstants.TERM_SIGN_DEFAULT);
            tblTermInf.setTermMcc(termMccNew);

            tblTermInf.setKeyDownSign("1");
            tblTermInf.setParamDownSign(TblTermInfConstants.UN_CHECKED);
            tblTermInf.setSupportIc("1");
            tblTermInf.setIcDownSign("1");
            tblTermInf.setReserveFlag1(isChecked(reserveFlag1New));
            tblTermInf.setConnectMode(connectModeNew);
            tblTermInf.setMisc2(termVerNew);
            tblTermInf.setPropTp(propTpNew);
            tblTermInf.setPropInsNm(propInsNmNew);
            tblTermInf.setTermBatchNm("000001");// 版本批次号
            tblTermInf.setTermTp(termTpNew);
            tblTermInf.setRecUpdOpr(operator.getOprId());
            tblTermInf.setRecCrtOpr(operator.getOprId());
            String nowDate = CommonFunction.getCurrentDate();
            tblTermInf.setTermStlmDt(nowDate);
            tblTermInf.setRecCrtTs(nowDate);
            tblTermInf.setRecUpdTs(nowDate);
            tblTermInf.setLicenceNo(licenceNo != null ? licenceNo.trim() : "");
            tblTermInf.setCheckCardNo(checkCardNoNew != null ? checkCardNoNew.trim() : "");
            tblTermInf.setDepositFlag(depositFlagNew);// 是否收取押金
            tblTermInf.setDepositState(depositStateNew);// 押金收取状态
            if (StringUtils.isNotEmpty(depositAmtNew)) {
                BigDecimal depAmt = BigDecimal.valueOf(Long.parseLong(depositAmtNew));// 押金金额
                tblTermInf.setDepositAmt(depAmt);
            }
            if (StringUtils.isNotEmpty(rentFeeNew)) {
                BigDecimal rentFeeB = BigDecimal.valueOf(Long.parseLong(rentFeeNew));// 租金金额
                tblTermInf.setRentFee(rentFeeB);
            }
            if (leaseFeeNew != null && !leaseFeeNew.trim().equals("")) {// 终端租凭费用(天)
                BigDecimal fee = BigDecimal.valueOf(Double.valueOf(leaseFeeNew));
                tblTermInf.setLeaseFee(fee);
            }
            tblTermInf.setTermIdId(Constants.DEFAULT);// 这个是库存编号
            tblTermInf.setTermTxnSup(this.getTxnSup());
            // 终端单笔限额       插入数据库中中转化为分
            tblTermInf.setTermSingleLimit(CommonFunction.transYuanToFen(termSingleLimitNew));
            tblTermInf.setPropInsRate(propInsRateNew);
            tblTermInf.setBindTel1(bindTel1New);
            tblTermInf.setBindTel2(bindTel2New);
            tblTermInf.setBindTel3(bindTel3New);
            //新添字段
            tblTermInf.setMappingMchntTypeOne(Constants.TYPE_ONE);
            tblTermInf.setMappingMchntcdOne(mappingMchntcdOne);
            tblTermInf.setMappingTermidOne(mappingTermidOne);
            tblTermInf.setMappingMchntTypeTwo(Constants.TYPE_TWO);
            tblTermInf.setMappingMchntcdTwo(mappingMchntcdTwo);
            tblTermInf.setMappingTermidTwo(mappingTermidTwo);
            dao.save(tblTermInf);
            return Constants.SUCCESS_CODE_CUSTOMIZE + "新增终端号：" + pk.getTermId();

        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAILURE_CODE;
        }

    }

    public String isChecked(String param) {
        return param == null ? "0" : "1";
    }

    public int checkTxn(String param) {
        return param == null ? 0 : 1;
    }

    public String translate(String money) {
        if (money.contains("."))
            return CommonFunction.fillString(money.replaceAll("\\.", ""), ' ', 12, true);
        else
            money = CommonFunction.fillString(money, '0', money.length() + 2, true);
        return CommonFunction.fillString(money, ' ', 12, true);
    }

    private String getTxnSup() {
        StringBuffer sb = new StringBuffer("");
        HttpServletRequest request = ServletActionContext.getRequest();
        // 总共十一个交易 以后若还有再加、和后台约定
        for (int i = 1; i <= 11; i++) {
            String txnSup = request.getParameter("txnSupNew" + i);
            sb.append(isChecked(txnSup));
        }
        return sb.toString();
    }

    public String getTermSingleLimitNew() {
        return termSingleLimitNew;
    }

    public void setTermSingleLimitNew(String termSingleLimitNew) {
        this.termSingleLimitNew = termSingleLimitNew;
    }

    public String getRentFeeNew() {
        return rentFeeNew;
    }

    public void setRentFeeNew(String rentFeeNew) {
        this.rentFeeNew = rentFeeNew;
    }

    public T3010BO getT3010BO() {
        return t3010BO;
    }

    public void setT3010BO(T3010BO t3010bo) {
        t3010BO = t3010bo;
    }

    public HqlDao getDao() {
        return dao;
    }

    public void setDao(HqlDao dao) {
        this.dao = dao;
    }

    public String getProductCdNew() {
        return productCdNew;
    }

    public void setProductCdNew(String productCdNew) {
        this.productCdNew = productCdNew;
    }

    public String getLicenceNo() {
        return licenceNo;
    }

    public void setLicenceNo(String licenceNo) {
        this.licenceNo = licenceNo;
    }

    public String getBusTypeNew() {
        return busTypeNew;
    }

    public void setBusTypeNew(String busTypeNew) {
        this.busTypeNew = busTypeNew;
    }

    public String getCardTypeNew() {
        return cardTypeNew;
    }

    public void setCardTypeNew(String cardTypeNew) {
        this.cardTypeNew = cardTypeNew;
    }

    public String getDepositFlagNew() {
        return depositFlagNew;
    }

    public void setDepositFlagNew(String depositFlagNew) {
        this.depositFlagNew = depositFlagNew;
    }

    public String getDepositAmtNew() {
        return depositAmtNew;
    }

    public void setDepositAmtNew(String depositAmtNew) {
        this.depositAmtNew = depositAmtNew;
    }

    public String getDepositStateNew() {
        return depositStateNew;
    }

    public void setDepositStateNew(String depositStateNew) {
        this.depositStateNew = depositStateNew;
    }

    public String getLeaseFeeNew() {
        return leaseFeeNew;
    }

    public void setLeaseFeeNew(String leaseFeeNew) {
        this.leaseFeeNew = leaseFeeNew;
    }

    public String getCheckCardNoNew() {
        return checkCardNoNew;
    }

    public void setCheckCardNoNew(String checkCardNoNew) {
        this.checkCardNoNew = checkCardNoNew;
    }

    public String getTermIdNew() {
        return termIdNew;
    }

    public void setTermIdNew(String termIdNew) {
        this.termIdNew = termIdNew;
    }

    public String getMchnNoNew() {
        return mchnNoNew;
    }

    public void setMchnNoNew(String mchnNoNew) {
        this.mchnNoNew = mchnNoNew;
    }

    public String getBrhIdNew() {
        return brhIdNew;
    }

    public void setBrhIdNew(String brhIdNew) {
        this.brhIdNew = brhIdNew;
    }

    public String getTermIdIdNew() {
        return termIdIdNew;
    }

    public void setTermIdIdNew(String termIdIdNew) {
        this.termIdIdNew = termIdIdNew;
    }

    public String getTermFactoryNew() {
        return termFactoryNew;
    }

    public void setTermFactoryNew(String termFactoryNew) {
        this.termFactoryNew = termFactoryNew;
    }

    public String getTermMachTpNew() {
        return termMachTpNew;
    }

    public void setTermMachTpNew(String termMachTpNew) {
        this.termMachTpNew = termMachTpNew;
    }

    public String getTermMccNew() {
        return termMccNew;
    }

    public void setTermMccNew(String termMccNew) {
        this.termMccNew = termMccNew;
    }

    public String getTermVerNew() {
        return termVerNew;
    }

    public void setTermVerNew(String termVerNew) {
        this.termVerNew = termVerNew;
    }

    public String getTermTpNew() {
        return termTpNew;
    }

    public void setTermTpNew(String termTpNew) {
        this.termTpNew = termTpNew;
    }

    public String getParamDownSignNew() {
        return paramDownSignNew;
    }

    public void setParamDownSignNew(String paramDownSignNew) {
        this.paramDownSignNew = paramDownSignNew;
    }

    public String getIcDownSignNew() {
        return icDownSignNew;
    }

    public void setIcDownSignNew(String icDownSignNew) {
        this.icDownSignNew = icDownSignNew;
    }

    public String getContTelNew() {
        return contTelNew;
    }

    public void setContTelNew(String contTelNew) {
        this.contTelNew = contTelNew;
    }

    public String getPropTpNew() {
        return propTpNew;
    }

    public void setPropTpNew(String propTpNew) {
        this.propTpNew = propTpNew;
    }

    public String getPropInsNmNew() {
        return propInsNmNew;
    }

    public void setPropInsNmNew(String propInsNmNew) {
        this.propInsNmNew = propInsNmNew;
    }

    public String getTermPara1New() {
        return termPara1New;
    }

    public void setTermPara1New(String termPara1New) {
        this.termPara1New = termPara1New;
    }

    public String getTermBatchNmNew() {
        return termBatchNmNew;
    }

    public void setTermBatchNmNew(String termBatchNmNew) {
        this.termBatchNmNew = termBatchNmNew;
    }

    public String getTermStlmDtNew() {
        return termStlmDtNew;
    }

    public void setTermStlmDtNew(String termStlmDtNew) {
        this.termStlmDtNew = termStlmDtNew;
    }

    public String getConnectModeNew() {
        return connectModeNew;
    }

    public void setConnectModeNew(String connectModeNew) {
        this.connectModeNew = connectModeNew;
    }

    public String getEquipInvIdNew() {
        return equipInvIdNew;
    }

    public void setEquipInvIdNew(String equipInvIdNew) {
        this.equipInvIdNew = equipInvIdNew;
    }

    public String getEquipInvNmNew() {
        return equipInvNmNew;
    }

    public void setEquipInvNmNew(String equipInvNmNew) {
        this.equipInvNmNew = equipInvNmNew;
    }

    public String getBindTel1New() {
        return bindTel1New;
    }

    public void setBindTel1New(String bindTel1New) {
        this.bindTel1New = bindTel1New;
    }

    public String getBindTel2New() {
        return bindTel2New;
    }

    public void setBindTel2New(String bindTel2New) {
        this.bindTel2New = bindTel2New;
    }

    public String getBindTel3New() {
        return bindTel3New;
    }

    public void setBindTel3New(String bindTel3New) {
        this.bindTel3New = bindTel3New;
    }

    public String getTermAddrNew() {
        return termAddrNew;
    }

    public void setTermAddrNew(String termAddrNew) {
        this.termAddrNew = termAddrNew;
    }

    public String getTermPlaceNew() {
        return termPlaceNew;
    }

    public void setTermPlaceNew(String termPlaceNew) {
        this.termPlaceNew = termPlaceNew;
    }

    public String getOprNmNew() {
        return oprNmNew;
    }

    public void setOprNmNew(String oprNmNew) {
        this.oprNmNew = oprNmNew;
    }

    public String getKeyDownSignNew() {
        return keyDownSignNew;
    }

    public void setKeyDownSignNew(String keyDownSignNew) {
        this.keyDownSignNew = keyDownSignNew;
    }

    public String getReserveFlag1New() {
        return reserveFlag1New;
    }

    public void setReserveFlag1New(String reserveFlag1New) {
        this.reserveFlag1New = reserveFlag1New;
    }

    public String getPropInsRateNew() {
        return propInsRateNew;
    }

    public void setPropInsRateNew(String propInsRateNew) {
        this.propInsRateNew = propInsRateNew;
    }

    public String getTermNameNew() {
        return termNameNew;
    }

    public void setTermNameNew(String termNameNew) {
        this.termNameNew = termNameNew;
    }

    public String getMappingMchntcdOne() {
        return mappingMchntcdOne;
    }

    public void setMappingMchntcdOne(String mappingMchntcdOne) {
        this.mappingMchntcdOne = mappingMchntcdOne;
    }

    public String getMappingMchntcdTwo() {
        return mappingMchntcdTwo;
    }

    public void setMappingMchntcdTwo(String mappingMchntcdTwo) {
        this.mappingMchntcdTwo = mappingMchntcdTwo;
    }

    public String getMappingTermidOne() {
        return mappingTermidOne;
    }

    public void setMappingTermidOne(String mappingTermidOne) {
        this.mappingTermidOne = mappingTermidOne;
    }

    public String getMappingTermidTwo() {
        return mappingTermidTwo;
    }

    public void setMappingTermidTwo(String mappingTermidTwo) {
        this.mappingTermidTwo = mappingTermidTwo;
    }

}
