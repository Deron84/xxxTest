package com.huateng.struts.pos.action;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.huateng.bo.term.T3010BO;
import com.huateng.bo.term.TermService;
import com.huateng.common.Constants;
import com.huateng.dao.common.HqlDao;
import com.huateng.po.TblTermInfTmp;
import com.huateng.po.TblTermInfTmpPK;
import com.huateng.struts.pos.TblTermInfConstants;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.GenerateNextId;

/**
 * Title: 终端新增、在原终端基础上增加、即同一个终端实体、不同商户号
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-16
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @version 1.0
 */
public class T30102Action extends BaseAction {
    T3010BO t3010BO = (T3010BO) ContextUtil.getBean("t3010BO");

    /** 终端新增 */
    @Override
    protected String subExecute() throws Exception {
        if ("saveOne".equals(method)) {
            return saveOne();
        }
        if ("batchSave".equals(method)) {
            return batchSave();
        }
        return "无效请求";

    }

    /**
     * @return
     */
    private String batchSave() {
        // TODO Auto-generated method stub
        TblTermInfTmpPK id = new TblTermInfTmpPK(termIdSave, mchtCdSave);
        TblTermInfTmp tmp = dao.get(TblTermInfTmp.class, id);
        tmp.setTermSta(TblTermInfConstants.TERM_ST_INIT);
        String brhId = tmp.getTermBranch();
        int c = Integer.parseInt(count);
        String date = CommonFunction.getCurrentDate();
        String oprId = this.operator.getOprId();
        // long termId = Long.parseLong(GenerateNextId.getTermId(brhId));

        for (int i = 1; i <= c; i++) {

            String termId = GenerateNextId.getTermId(brhId);
            // System.out.println(termId);
            id.setTermId(termId);
            tmp.setRecCrtTs(date);
            tmp.setRecUpdTs(date);
            tmp.setRecCrtOpr(oprId);
            tmp.setRecUpdOpr(oprId);
            dao.save(tmp);// 不能放到service里面执行。hibernate不能立即执行save操作、id获取会有问题。在service里面操作事务也会有问题
            // 所以这里没法事务管理、至少用hibernate的话还没找打什么办法
            // TblTermInfTmp ntmp = new TblTermInfTmp();
            // BeanUtils.copyProperties(tmp,
            // ntmp);//不是新的对象hibernate不会插入、它会update、下面膜还要把缓存清了
            // dao.save(ntmp);
            // dao.flush();
            // dao.clear();
            // dao.commit();
        }

        return Constants.SUCCESS_CODE;
    }

    /**
     * 
     */
    private String saveOne() {

        try {
            //收单方终端号不为空时，不能重复。
            List list1 = t3010BO.qryTermInfosByOne(mappingTermidOneN, mappingMchntcdTwoN);
            if (list1 != null && list1.size() > 0) {
                return "此终端号" + mappingTermidOneN + "已存在";
            }
            List list2 = t3010BO.qryTermInfosByTwo(mappingTermidTwoN, mappingMchntcdTwoN);
            if (list2 != null && list2.size() > 0) {
                return "此终端号" + mappingTermidTwoN + "已存在";
            }

            TblTermInfTmp tblTermInf = new TblTermInfTmp();
            TblTermInfTmpPK pk = new TblTermInfTmpPK();
            if ("1".equals(termIdSaveFlag)) {
                // 不同实体。要获取终端号
                String termId = GenerateNextId.getTermId(brhIdNew);
                if (Constants.FAILURE_CODE.equals(termId)) {
                    return Constants.FAILURE_CODE;
                } else {
                    termIdSave = termId;
                }

            }
            pk.setTermId(termIdSave);
            pk.setMchtCd(mchnNoNew);
            TblTermInfTmp existObj = dao.get(TblTermInfTmp.class, pk);
            if (existObj != null) {
                return "请选择其他商户号";
            }

            tblTermInf.setId(pk);
            tblTermInf.setTermName(termNameNew);

            tblTermInf.setMappingMchntTypeOne(Constants.TYPE_ONE);
            tblTermInf.setMappingMchntTypeTwo(Constants.TYPE_TWO);
            tblTermInf.setMappingMchntcdOne(mappingMchntcdOneN);
            tblTermInf.setMappingMchntcdTwo(mappingMchntcdTwoN);

            tblTermInf.setMappingTermidOne(mappingTermidOneN);
            tblTermInf.setMappingTermidTwo(mappingTermidTwoN);
            System.out.println(brhIdNew);
            tblTermInf.setTermBranch(brhIdNew);
            tblTermInf.setTermAddr(termAddrNew);

            tblTermInf.setTermSta(TblTermInfConstants.TERM_ST_INIT);
            tblTermInf.setTermSignSta(TblTermInfConstants.TERM_SIGN_DEFAULT);
            tblTermInf.setTermMcc(termMccNew);
            // supportIC这个为默认项，必填不可见。IC卡参数下载标志由IcDownSign标志位控制
            // tblTermInf.setKeyDownSign(isChecked(keyDownSignN));
            // tblTermInf.setParamDownSign(isChecked(paramDownSignN));
            // tblTermInf.setSupportIc(isChecked(supportICN));
            tblTermInf.setKeyDownSign("1");
            tblTermInf.setParamDownSign(TblTermInfConstants.UN_CHECKED);
            tblTermInf.setSupportIc("1");
            tblTermInf.setIcDownSign("1");
            tblTermInf.setReserveFlag1(isChecked(reserveFlag1New));

            tblTermInf.setConnectMode(connectModeNew);
            // tblTermInf.setContTel(contTelN);
            // tblTermInf.setOprNm(oprNmN);
            // tblTermInf.setEquipInvId(equipInvIdN);
            // tblTermInf.setEquipInvNm(equipInvNmN);
            tblTermInf.setPropTp(propTpNew);
            tblTermInf.setPropInsNm(propInsNmNew);
            // tblTermInf.setTermPara1(termPara1N);
            tblTermInf.setTermBatchNm("000001");// 版本批次号
            tblTermInf.setTermTp(termTpNew);
            tblTermInf.setMisc2(termVersion);
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
                BigDecimal depAmt = BigDecimal.valueOf(Double.valueOf(depositAmtNew));// 押金金额
                tblTermInf.setDepositAmt(depAmt);
            }
            if (StringUtils.isNotEmpty(rentFeeNew)) {
                BigDecimal rentFeeNewB = BigDecimal.valueOf(Double.valueOf(rentFeeNew));// 租金
                tblTermInf.setRentFee(rentFeeNewB);
            }
            if (leaseFeeNew != null && !leaseFeeNew.trim().equals("")) {// 终端租凭费用(天)
                BigDecimal fee = BigDecimal.valueOf(Double.valueOf(leaseFeeNew));
                tblTermInf.setLeaseFee(fee);
            }
            tblTermInf.setTermIdId("-");// 这个epos用的，暂不用
            // 终端单笔限额
            tblTermInf.setTermSingleLimit(CommonFunction.transYuanToFen(termSingleLimitN));
            tblTermInf.setTermTxnSup(this.getTxnSup());
            tblTermInf.setPropInsRate(propInsRateNew);
            tblTermInf.setBindTel1(bindTel1New);
            tblTermInf.setBindTel2(bindTel2New);
            tblTermInf.setBindTel3(bindTel3New);
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
        // 总共十九个交易 以后若还有再加、和后台约定
        for (int i = 1; i <= 11; i++) {
            String txnSup = request.getParameter("txnSupNew" + i);
            sb.append(isChecked(txnSup));
        }
        return sb.toString();
    }

    private String method;

    private String termIdSave;

    private String mchtCdSave;

    private HqlDao dao;

    private TermService service;

    private String termIdN;

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
    private String termSingleLimitN;

    /** 批量添加的数量 */
    private String count;

    /** 是不是相同实体 */
    private String termIdSaveFlag;

    /**固话pos版本号*/
    private String termVersion;

    // 租金（占商户场地、付给商户）
    private String rentFeeNew;

    private String termNameNew;

    private String mappingTermidOneN;

    private String mappingTermidTwoN;

    private String mappingMchntcdOneN;

    private String mappingMchntcdTwoN;

    public String getTermSingleLimitN() {
        return termSingleLimitN;
    }

    public void setTermSingleLimitN(String termSingleLimitN) {
        this.termSingleLimitN = termSingleLimitN;
    }

    public String getRentFeeNew() {
        return rentFeeNew;
    }

    public void setRentFeeNew(String rentFeeNew) {
        this.rentFeeNew = rentFeeNew;
    }

    public TermService getService() {
        return service;
    }

    public void setService(TermService service) {
        this.service = service;
    }

    public String getTermIdSaveFlag() {
        return termIdSaveFlag;
    }

    public void setTermIdSaveFlag(String termIdSaveFlag) {
        this.termIdSaveFlag = termIdSaveFlag;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    // 第三方服务机构分润比例 prop_ins_rate
    private String propInsRateNew;

    public String getMchtCdSave() {
        return mchtCdSave;
    }

    public void setMchtCdSave(String mchtCdSave) {
        this.mchtCdSave = mchtCdSave;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public void setMethod(String method) {
        this.method = method;
    }

    public String getTermIdSave() {
        return termIdSave;
    }

    public void setTermIdSave(String termIdSave) {
        this.termIdSave = termIdSave;
    }

    public HqlDao getDao() {
        return dao;
    }

    public void setDao(HqlDao dao) {
        this.dao = dao;
    }

    public String getTermIdN() {
        return termIdN;
    }

    public void setTermIdN(String termIdN) {
        this.termIdN = termIdN;
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

    public String getProductCdNew() {
        return productCdNew;
    }

    public void setProductCdNew(String productCdNew) {
        this.productCdNew = productCdNew;
    }

    public String getReserveFlag1New() {
        return reserveFlag1New;
    }

    public void setReserveFlag1New(String reserveFlag1New) {
        this.reserveFlag1New = reserveFlag1New;
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

    public String getPropInsRateNew() {
        return propInsRateNew;
    }

    public void setPropInsRateNew(String propInsRateNew) {
        this.propInsRateNew = propInsRateNew;
    }

    public String getTermVersion() {
        return termVersion;
    }

    public void setTermVersion(String termVersion) {
        this.termVersion = termVersion;
    }

    public String getTermNameNew() {
        return termNameNew;
    }

    public void setTermNameNew(String termNameNew) {
        this.termNameNew = termNameNew;
    }

    public String getMappingTermidOneN() {
        return mappingTermidOneN;
    }

    public void setMappingTermidOneN(String mappingTermidOneN) {
        this.mappingTermidOneN = mappingTermidOneN;
    }

    public String getMappingTermidTwoN() {
        return mappingTermidTwoN;
    }

    public void setMappingTermidTwoN(String mappingTermidTwoN) {
        this.mappingTermidTwoN = mappingTermidTwoN;
    }

    public String getMappingMchntcdOneN() {
        return mappingMchntcdOneN;
    }

    public void setMappingMchntcdOneN(String mappingMchntcdOneN) {
        this.mappingMchntcdOneN = mappingMchntcdOneN;
    }

    public String getMappingMchntcdTwoN() {
        return mappingMchntcdTwoN;
    }

    public void setMappingMchntcdTwoN(String mappingMchntcdTwoN) {
        this.mappingMchntcdTwoN = mappingMchntcdTwoN;
    }

}
