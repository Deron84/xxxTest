package com.huateng.struts.pos.action;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

/** 终端修改 */
public class T3010102Action extends BaseAction {

    private static final long serialVersionUID = 8817290734735660318L;

    private HqlDao dao;

    private T3010BO t3010BO = (T3010BO) ContextUtil.getBean("t3010BO");

    private String termIdUpd;

    private String mchnNoUpd;

    private String brhIdUpd;

    private String termIdIdUpd;

    private String termFactoryUpd;

    private String termMachTpUpd;

    private String termMccUpd;

    private String termVerUpd;

    private String termTpUpd;

    private String paramDownSignUpd;

    private String icDownSignUpd;

    private String contTelUpd;

    private String propTpUpd;

    private String propInsNmUpd;

    private String termPara1Upd;

    private String termBatchNmUpd;

    private String termStlmDtUpd;

    private String connectModeUpd;

    private String equipInvIdUpd;

    private String equipInvNmUpd;

    private String bindTel1Upd;

    private String bindTel2Upd;

    private String bindTel3Upd;

    private String termAddrUpd;

    private String termPlaceUpd;

    private String oprNmUpd;

    private String keyDownSignUpd;

    private String productCdUpd;

    private String reserveFlag1Upd;

    private String licenceNo;

    private String busTypeUpd;

    private String cardTypeUpd;

    private String depositFlagUpd;

    private String depositAmtUpd;

    private String depositStateUpd;

    private String leaseFeeUpd;

    private String checkCardNoUpd;

    //终端单笔限额
    private String termSingleLimitUpd;

    // 第三方服务机构分润比例 prop_ins_rate
    private String propInsRateUpd;

    // 终端租金（占场地的费用、付给商户）
    private String rentFeeUpd;

    private String termNameUpd;

    private String mappingTermidOneUpd;

    private String mappingTermidTwoUpd;
  //银联商户号
    private String mappingMchntcdTwoUpd;

    //固话POS版本号
    private String termVersion;

    private static Set<String> staset;
    static {
        staset = new HashSet<String>();
        staset.add(TblTermInfConstants.TERM_ST_INIT);
        staset.add(TblTermInfConstants.TERM_ST_INST_WAIT);
        staset.add(TblTermInfConstants.TERM_ST_INST);
        staset.add(TblTermInfConstants.TERM_ST_UPD_UNCHECK);
        staset.add(TblTermInfConstants.TERM_ST_ADD_BACK);
    }

    @Override
    protected String subExecute() throws Exception {
        try {
            TblTermInfTmpPK id = new TblTermInfTmpPK(CommonFunction.fillString(termIdUpd, ' ', 12, true),
                    CommonFunction.fillString(mchnNoUpd, ' ', 15, true));
            // System.out.println(mchnNoUpd);
            TblTermInfTmp tmp = dao.get(TblTermInfTmp.class, id);
            if (tmp == null) {
                return "不存在指定记录";
            }
         /* //收单方终端号不为空时，不能重复。
            List list1 = t3010BO.qryTermInfosByOne(mappingTermidOneUpd, mappingMchntcdTwoUpd);
            if (list1 != null && list1.size() > 0) {
                return "该商户下此终端号" + mappingTermidOneUpd + "已存在";
            }
            List list2 = t3010BO.qryTermInfosByTwo(mappingTermidTwoUpd, mappingMchntcdTwoUpd);
            if (list2 != null && list2.size() > 0) {
                return "该商户下此终端号" + mappingTermidTwoUpd + "已存在";
            }*/

            String termSta = tmp.getTermSta();
            if (!staset.contains(termSta)) {
                return "不是可修改状态";
            }
            // tblTermInf.setTermBranch(brhIdUpd);
            tmp.setTermAddr(termAddrUpd);
            tmp.setTermTp(termTpUpd);
            tmp.setTermName(termNameUpd);
            tmp.setMappingTermidOne(mappingTermidOneUpd);
        //    tmp.setMappingTermidTwo(mappingTermidTwoUpd);
            tmp.setReserveFlag1(isChecked(reserveFlag1Upd));

            tmp.setPropTp(propTpUpd);
            tmp.setPropInsNm(propInsNmUpd);
            tmp.setPropInsRate(propInsRateUpd);
            tmp.setBindTel1(bindTel1Upd);
            tmp.setBindTel2(bindTel2Upd);
            tmp.setBindTel3(bindTel3Upd);
            tmp.setRecUpdOpr(operator.getOprId());
            String nowDate = CommonFunction.getCurrentDate();
            tmp.setRecUpdTs(nowDate);
            tmp.setDepositFlag(depositFlagUpd);// 是否收取押金
            //终端单笔限额
            tmp.setTermSingleLimit(CommonFunction.transYuanToFen(termSingleLimitUpd));
            tmp.setDepositState(depositStateUpd);
            tmp.setMisc2(termVersion);
            if (depositAmtUpd != null && !depositAmtUpd.equals("")) {
                BigDecimal depAmt = BigDecimal.valueOf(Long.parseLong(depositAmtUpd));
                tmp.setDepositAmt(depAmt);
            }
            if (StringUtils.isNotEmpty(rentFeeUpd) && !rentFeeUpd.equals("")) {
                BigDecimal rentFeeUpdB = BigDecimal.valueOf(Long.parseLong(rentFeeUpd));// 租金
                tmp.setRentFee(rentFeeUpdB);
            }
            if (leaseFeeUpd != null && !leaseFeeUpd.equals("")) {// 终端租凭费用(天)
                BigDecimal fee = BigDecimal.valueOf(Double.valueOf(leaseFeeUpd));
                tmp.setLeaseFee(fee);
            }
            String termTxnSup = getTxnSup();// 支持的交易
            tmp.setTermTxnSup(termTxnSup);

            if (TblTermInfConstants.TERM_ST_INIT.equals(termSta) || TblTermInfConstants.TERM_ST_ADD_BACK.equals(termSta)
                    || TblTermInfConstants.TERM_ST_INST_WAIT.equals(termSta)) {
                tmp.setTermSta(TblTermInfConstants.TERM_ST_INIT);
            } else {
                tmp.setTermSta(TblTermInfConstants.TERM_ST_UPD_UNCHECK);
            }

            dao.update(tmp);
            return Constants.SUCCESS_CODE;

        } catch (Exception e) {
            e.printStackTrace();
            return "运行时错误";
        }
    }

    private String getTxnSup() {
        StringBuffer sb = new StringBuffer("");
        HttpServletRequest request = ServletActionContext.getRequest();
        // 总共十一个交易 以后若还有再加、和后台约定
        for (int i = 1; i <= 11; i++) {
            String txnSup = request.getParameter("txnSupUpd" + i);
            sb.append(isChecked(txnSup));
        }
        return sb.toString();
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

    public String getTermVersion() {
        return termVersion;
    }

    public void setTermVersion(String termVersion) {
        this.termVersion = termVersion;
    }

    public String getRentFeeUpd() {
        return rentFeeUpd;
    }

    public String getTermSingleLimitUpd() {
        return termSingleLimitUpd;
    }

    public void setTermSingleLimitUpd(String termSingleLimitUpd) {
        this.termSingleLimitUpd = termSingleLimitUpd;
    }

    public void setRentFeeUpd(String rentFeeUpd) {
        this.rentFeeUpd = rentFeeUpd;
    }

    public HqlDao getDao() {
        return dao;
    }

    public void setDao(HqlDao dao) {
        this.dao = dao;
    }

    public T3010BO getT3010BO() {
        return t3010BO;
    }

    public void setT3010BO(T3010BO t3010bo) {
        t3010BO = t3010bo;
    }

    public String getTermIdUpd() {
        return termIdUpd;
    }

    public void setTermIdUpd(String termIdUpd) {
        this.termIdUpd = termIdUpd;
    }

    public String getMchnNoUpd() {
        return mchnNoUpd;
    }

    public void setMchnNoUpd(String mchnNoUpd) {
        this.mchnNoUpd = mchnNoUpd;
    }

    public String getBrhIdUpd() {
        return brhIdUpd;
    }

    public void setBrhIdUpd(String brhIdUpd) {
        this.brhIdUpd = brhIdUpd;
    }

    public String getTermIdIdUpd() {
        return termIdIdUpd;
    }

    public void setTermIdIdUpd(String termIdIdUpd) {
        this.termIdIdUpd = termIdIdUpd;
    }

    public String getTermFactoryUpd() {
        return termFactoryUpd;
    }

    public void setTermFactoryUpd(String termFactoryUpd) {
        this.termFactoryUpd = termFactoryUpd;
    }

    public String getTermMachTpUpd() {
        return termMachTpUpd;
    }

    public void setTermMachTpUpd(String termMachTpUpd) {
        this.termMachTpUpd = termMachTpUpd;
    }

    public String getTermMccUpd() {
        return termMccUpd;
    }

    public void setTermMccUpd(String termMccUpd) {
        this.termMccUpd = termMccUpd;
    }

    public String getTermVerUpd() {
        return termVerUpd;
    }

    public void setTermVerUpd(String termVerUpd) {
        this.termVerUpd = termVerUpd;
    }

    public String getTermTpUpd() {
        return termTpUpd;
    }

    public void setTermTpUpd(String termTpUpd) {
        this.termTpUpd = termTpUpd;
    }

    public String getParamDownSignUpd() {
        return paramDownSignUpd;
    }

    public void setParamDownSignUpd(String paramDownSignUpd) {
        this.paramDownSignUpd = paramDownSignUpd;
    }

    public String getIcDownSignUpd() {
        return icDownSignUpd;
    }

    public void setIcDownSignUpd(String icDownSignUpd) {
        this.icDownSignUpd = icDownSignUpd;
    }

    public String getContTelUpd() {
        return contTelUpd;
    }

    public void setContTelUpd(String contTelUpd) {
        this.contTelUpd = contTelUpd;
    }

    public String getPropTpUpd() {
        return propTpUpd;
    }

    public void setPropTpUpd(String propTpUpd) {
        this.propTpUpd = propTpUpd;
    }

    public String getPropInsNmUpd() {
        return propInsNmUpd;
    }

    public void setPropInsNmUpd(String propInsNmUpd) {
        this.propInsNmUpd = propInsNmUpd;
    }

    public String getTermPara1Upd() {
        return termPara1Upd;
    }

    public void setTermPara1Upd(String termPara1Upd) {
        this.termPara1Upd = termPara1Upd;
    }

    public String getTermBatchNmUpd() {
        return termBatchNmUpd;
    }

    public void setTermBatchNmUpd(String termBatchNmUpd) {
        this.termBatchNmUpd = termBatchNmUpd;
    }

    public String getTermStlmDtUpd() {
        return termStlmDtUpd;
    }

    public void setTermStlmDtUpd(String termStlmDtUpd) {
        this.termStlmDtUpd = termStlmDtUpd;
    }

    public String getConnectModeUpd() {
        return connectModeUpd;
    }

    public void setConnectModeUpd(String connectModeUpd) {
        this.connectModeUpd = connectModeUpd;
    }

    public String getEquipInvIdUpd() {
        return equipInvIdUpd;
    }

    public void setEquipInvIdUpd(String equipInvIdUpd) {
        this.equipInvIdUpd = equipInvIdUpd;
    }

    public String getEquipInvNmUpd() {
        return equipInvNmUpd;
    }

    public void setEquipInvNmUpd(String equipInvNmUpd) {
        this.equipInvNmUpd = equipInvNmUpd;
    }

    public String getBindTel1Upd() {
        return bindTel1Upd;
    }

    public void setBindTel1Upd(String bindTel1Upd) {
        this.bindTel1Upd = bindTel1Upd;
    }

    public String getBindTel2Upd() {
        return bindTel2Upd;
    }

    public void setBindTel2Upd(String bindTel2Upd) {
        this.bindTel2Upd = bindTel2Upd;
    }

    public String getBindTel3Upd() {
        return bindTel3Upd;
    }

    public void setBindTel3Upd(String bindTel3Upd) {
        this.bindTel3Upd = bindTel3Upd;
    }

    public String getTermAddrUpd() {
        return termAddrUpd;
    }

    public void setTermAddrUpd(String termAddrUpd) {
        this.termAddrUpd = termAddrUpd;
    }

    public String getTermPlaceUpd() {
        return termPlaceUpd;
    }

    public void setTermPlaceUpd(String termPlaceUpd) {
        this.termPlaceUpd = termPlaceUpd;
    }

    public String getOprNmUpd() {
        return oprNmUpd;
    }

    public void setOprNmUpd(String oprNmUpd) {
        this.oprNmUpd = oprNmUpd;
    }

    public String getKeyDownSignUpd() {
        return keyDownSignUpd;
    }

    public void setKeyDownSignUpd(String keyDownSignUpd) {
        this.keyDownSignUpd = keyDownSignUpd;
    }

    public String getProductCdUpd() {
        return productCdUpd;
    }

    public void setProductCdUpd(String productCdUpd) {
        this.productCdUpd = productCdUpd;
    }

    public String getReserveFlag1Upd() {
        return reserveFlag1Upd;
    }

    public void setReserveFlag1Upd(String reserveFlag1Upd) {
        this.reserveFlag1Upd = reserveFlag1Upd;
    }

    public String getLicenceNo() {
        return licenceNo;
    }

    public void setLicenceNo(String licenceNo) {
        this.licenceNo = licenceNo;
    }

    public String getBusTypeUpd() {
        return busTypeUpd;
    }

    public void setBusTypeUpd(String busTypeUpd) {
        this.busTypeUpd = busTypeUpd;
    }

    public String getCardTypeUpd() {
        return cardTypeUpd;
    }

    public void setCardTypeUpd(String cardTypeUpd) {
        this.cardTypeUpd = cardTypeUpd;
    }

    public String getDepositFlagUpd() {
        return depositFlagUpd;
    }

    public void setDepositFlagUpd(String depositFlagUpd) {
        this.depositFlagUpd = depositFlagUpd;
    }

    public String getDepositAmtUpd() {
        return depositAmtUpd;
    }

    public void setDepositAmtUpd(String depositAmtUpd) {
        this.depositAmtUpd = depositAmtUpd;
    }

    public String getDepositStateUpd() {
        return depositStateUpd;
    }

    public void setDepositStateUpd(String depositStateUpd) {
        this.depositStateUpd = depositStateUpd;
    }

    public String getLeaseFeeUpd() {
        return leaseFeeUpd;
    }

    public void setLeaseFeeUpd(String leaseFeeUpd) {
        this.leaseFeeUpd = leaseFeeUpd;
    }

    public String getCheckCardNoUpd() {
        return checkCardNoUpd;
    }

    public void setCheckCardNoUpd(String checkCardNoUpd) {
        this.checkCardNoUpd = checkCardNoUpd;
    }

    public String getPropInsRateUpd() {
        return propInsRateUpd;
    }

    public void setPropInsRateUpd(String propInsRateUpd) {
        this.propInsRateUpd = propInsRateUpd;
    }

    public String getTermNameUpd() {
        return termNameUpd;
    }

    public void setTermNameUpd(String termNameUpd) {
        this.termNameUpd = termNameUpd;
    }

    public String getMappingTermidOneUpd() {
        return mappingTermidOneUpd;
    }

    public void setMappingTermidOneUpd(String mappingTermidOneUpd) {
        this.mappingTermidOneUpd = mappingTermidOneUpd;
    }

    public String getMappingTermidTwoUpd() {
        return mappingTermidTwoUpd;
    }

    public void setMappingTermidTwoUpd(String mappingTermidTwoUpd) {
        this.mappingTermidTwoUpd = mappingTermidTwoUpd;
    }

	public String getMappingMchntcdTwoUpd() {
		return mappingMchntcdTwoUpd;
	}

	public void setMappingMchntcdTwoUpd(String mappingMchntcdTwoUpd) {
		this.mappingMchntcdTwoUpd = mappingMchntcdTwoUpd;
	}

}
