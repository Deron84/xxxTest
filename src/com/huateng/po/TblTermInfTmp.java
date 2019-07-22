package com.huateng.po;

import java.io.Serializable;
import java.math.BigDecimal;

import com.huateng.common.Constants;

public class TblTermInfTmp implements Serializable {

	private static final long serialVersionUID = 1L;

	public static String REF = "TblTermInfTmp";

	public static String PROP_TERM_ADDR = "TermAddr";

	public static String PROP_RESERVE_AMOUNT2 = "ReserveAmount2";

	public static String PROP_RESERVE_AMOUNT1 = "ReserveAmount1";

	public static String PROP_EQUIP_INV_NM = "EquipInvNm";

	public static String PROP_TERM_VER_TP = "TermVerTp";

	public static String PROP_ZONE_NUM = "ZoneNum";

	public static String PROP_TERM_BANK = "TermBank";

	public static String PROP_RECORD_STA = "RecordSta";

	public static String PROP_REC_UPD_OPR = "RecUpdOpr";

	public static String PROP_TERM_STA = "TermSta";

	public static String PROP_CONT_TEL = "ContTel";

	public static String PROP_IC_DOWN_SIGN = "IcDownSign";

	public static String PROP_FINANCE_CARD1 = "FinanceCard1";

	public static String PROP_FINANCE_CARD2 = "FinanceCard2";

	public static String PROP_PSAM_ID = "PsamId";

	public static String PROP_FINANCE_CARD3 = "FinanceCard3";

	public static String PROP_PAY_STAGE_LIMIT = "PayStageLimit";

	public static String PROP_F_CARD_SUP_FLAG = "FCardSupFlag";

	public static String PROP_OTH_SVR_ID = "OthSvrId";

	public static String PROP_REC_DEL_TS = "RecDelTs";

	public static String PROP_CONNECT_MODE = "ConnectMode";

	public static String PROP_RESERVE_DATE = "ReserveDate";

	public static String PROP_RUN_MAIN_ID2 = "RunMainId2";

	public static String PROP_RUN_MAIN_ID1 = "RunMainId1";

	public static String PROP_TERM_PARA = "TermPara";

	public static String PROP_PROP_TP = "PropTp";

	public static String PROP_TERM_MACH_TP = "TermMachTp";

	public static String PROP_RUN_MAIN_NM1 = "RunMainNm1";

	public static String PROP_RUN_MAIN_NM2 = "RunMainNm2";

	public static String PROP_TERM_VER = "TermVer";

	public static String PROP_PAY_STAGE_NUM = "PayStageNum";

	public static String PROP_DEPOSIT_FLAG = "DepositFlag";

	public static String PROP_TERM_STLM_DT = "TermStlmDt";

	public static String PROP_TERM_SEND_FLAG = "TermSendFlag";

	public static String PROP_TERM_BRANCH = "TermBranch";

	public static String PROP_CHK_STA = "ChkSta";

	public static String PROP_TERM_BATCH_NM = "TermBatchNm";

	public static String PROP_ID = "Id";

	public static String PROP_TERM_SIGN_STA = "TermSignSta";

	public static String PROP_EQUIP_INV_ID = "EquipInvId";

	public static String PROP_DIAL_TP = "DialTp";

	public static String PROP_REC_CHE_OPR = "RecCheOpr";

	// public static String PROP_PRODUCT_CD = "ProductCd";
	// public static String PROP_DEPOSIT = "Deposit";
	public static String PROP_TERM_PLACE = "TermPlace";

	public static String PROP_TERM_TP = "TermTp";

	public static String PROP_KEY_DOWN_SIGN = "KeyDownSign";

	public static String PROP_F_CARD_COMPANY = "FCardCompany";

	public static String PROP_REC_CRT_OPR = "RecCrtOpr";

	public static String PROP_TERM_FACTORY = "TermFactory";

	public static String PROP_TERM_MCC = "TermMcc";

	public static String PROP_MCHT_CD = "MchtCd";

	public static String PROP_REC_OPR_ID = "RecOprId";

	public static String PROP_TERM_ID_ID = "TermIdId";

	public static String PROP_BIND_TEL1 = "BindTel1";

	public static String PROP_TERM_SET_CUR = "TermSetCur";

	public static String PROP_REC_UPD_TS = "RecUpdTs";

	public static String PROP_OTH_SVR_NM = "OthSvrNm";

	public static String PROP_PARAM_DOWN_SIGN = "ParamDownSign";

	public static String PROP_TERM_TXN_SUP = "TermTxnSup";

	public static String PROP_BIND_TEL3 = "BindTel3";

	public static String PROP_BIND_TEL2 = "BindTel2";

	public static String PROP_SUPPORT_IC = "SupportIc";

	public static String PROP_TERM_SINGLE_LIMIT = "TermSingleLimit";

	public static String PROP_RESERVE_FLAG4 = "ReserveFlag4";

	public static String PROP_MISC2 = "Misc2";

	public static String PROP_RESERVE_FLAG3 = "ReserveFlag3";

	public static String PROP_MISC3 = "Misc3";

	public static String PROP_TERM_I_CARD_FLAG = "TermICardFlag";

	public static String PROP_RESERVE_FLAG2 = "ReserveFlag2";

	public static String PROP_RESERVE_FLAG1 = "ReserveFlag1";

	public static String PROP_TERM_INS = "TermIns";

	public static String PROP_MISC1 = "Misc1";

	public static String PROP_TERM_PARA1 = "TermPara1";

	public static String PROP_TERM_PARA2 = "TermPara2";

	public static String PROP_OPR_NM = "OprNm";

	public static String PROP_PARAM1_DOWN_SIGN = "Param1DownSign";

	public static String PROP_PROP_INS_NM = "PropInsNm";

	// public static String PROP_LICENCE_NO = "LicenceNo";
	public static String PROP_BUS_TYPE = "BusType";

	// public static String PROP_CARD_TYPE = "CardType";
	public static String PROP_DEPOSIT_AMT = "DepositAmt";

	public static String PROP_DEPOSIT_STATE = "DepositState";

	public static String PROP_LEASE_FEE = "LeaseFee";

	public static String PROP_CHECK_CARD_NO = "CheckCardNo";

	// constructors
	public TblTermInfTmp() {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public TblTermInfTmp(com.huateng.po.TblTermInfTmpPK id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public TblTermInfTmp(com.huateng.po.TblTermInfTmpPK id,
			java.lang.String mchtCd, java.lang.String termIdId,
			java.lang.String termSta, java.lang.String termMcc,
			java.lang.String termTp) {

		this.setId(id);

		this.setTermSta(termSta);
		this.setTermMcc(termMcc);
		this.setTermTp(termTp);
		initialize();
	}

	protected void initialize() {

		this.setTermSta(Constants.DEFAULT);
		this.setTermMcc(Constants.DEFAULT);
		this.setTermTp(Constants.DEFAULT);
	}

	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.huateng.po.TblTermInfTmpPK id;

	// fields
	private java.lang.String recordSta;

	private java.lang.String termSta;

	private java.lang.String termSignSta;

	private java.lang.String chkSta;

	private java.lang.String termSendFlag;

	private java.lang.String termICardFlag;

	private java.lang.String reserveFlag1;

	private java.lang.String reserveFlag2;

	private java.lang.String reserveFlag3;

	private java.lang.String reserveFlag4;

	private java.lang.String termSetCur;

	private java.lang.String termMcc;

	private java.lang.String termFactory;

	private java.lang.String termMachTp;

	private java.lang.String termVer;

	private java.lang.String termSingleLimit;

	private java.lang.Integer payStageNum;

	private java.math.BigDecimal payStageLimit;

	private java.lang.String financeCard1;

	private java.lang.String financeCard2;

	private java.lang.String financeCard3;

	private java.math.BigDecimal reserveAmount1;

	private java.math.BigDecimal reserveAmount2;

	private java.lang.String termTp;

	private java.lang.String paramDownSign;

	private java.lang.String param1DownSign;

	private java.lang.String icDownSign;

	private java.lang.String keyDownSign;

	private java.lang.String propTp;

	private java.lang.String propInsNm;

	private java.lang.String fCardSupFlag;

	private java.lang.String fCardCompany;

	private java.lang.String supportIc;

	private java.lang.String psamId;

	private java.lang.String termPlace;

	private java.lang.String connectMode;

	private java.lang.String dialTp;

	private java.lang.String termBranch;

	private java.lang.String termBank;

	private java.lang.String termIns;

	private java.lang.String termVerTp;

	private java.lang.String termBatchNm;

	private java.lang.String termStlmDt;

	private java.lang.String termTxnSup;

	private java.lang.String termPara;

	private java.lang.String termPara1;

	private java.lang.String termPara2;

	private java.lang.String bindTel1;

	private java.lang.String bindTel2;

	private java.lang.String bindTel3;

	private java.lang.String zoneNum;

	private java.lang.String termAddr;

	private java.lang.String oprNm;

	private java.lang.String contTel;

	private java.lang.String equipInvId;

	private java.lang.String equipInvNm;

	private java.lang.String depositFlag;

	// private java.math.BigDecimal deposit;
	private java.lang.String runMainId1;

	private java.lang.String runMainNm1;

	private java.lang.String runMainId2;

	private java.lang.String runMainNm2;

	private java.lang.String othSvrId;

	private java.lang.String othSvrNm;

	private java.lang.String recOprId;

	private java.lang.String recUpdOpr;

	private java.lang.String recCrtOpr;

	private String recCrtTs;

	private java.lang.String recCheOpr;

	private java.lang.String reserveDate;

	private java.lang.String recUpdTs;

	private java.lang.String recDelTs;

	private java.lang.String misc1;

	private java.lang.String misc2;

	private java.lang.String misc3;

	// private java.lang.String productCd;

	private String licenceNo;

	// private String busType;
	// private String cardType;
	private BigDecimal depositAmt;

	private String depositState;

	private BigDecimal leaseFee = BigDecimal.valueOf(0);

	private String checkCardNo;

	// 第三方服务机构分润比例 prop_ins_rate
	private String propInsRate;

	private String termIdId;

	// 租金（租用商户场地付给商户）
	private BigDecimal rentFee = BigDecimal.valueOf(0);
	
	private String termName;
	private String mappingMchntTypeOne;
	private String mappingMchntcdOne;
	private String mappingMchntTypeTwo;
	private String mappingMchntcdTwo;	
	private String mappingTermidOne;
	private String mappingTermidTwo;
	

	public BigDecimal getRentFee() {
		return rentFee;
	}

	public void setRentFee(BigDecimal rentFee) {
		this.rentFee = rentFee;
	}

	public String getRecCrtTs() {
		return recCrtTs;
	}

	public void setRecCrtTs(String recCrtTs) {
		this.recCrtTs = recCrtTs;
	}

	public String getTermIdId() {
		return termIdId;
	}

	public void setTermIdId(String termIdId) {
		this.termIdId = termIdId;
	}

	public String getPropInsRate() {
		return propInsRate;
	}

	public void setPropInsRate(String propInsRate) {
		this.propInsRate = propInsRate;
	}

	/**
	 * Return the unique identifier of this class
	 * 
	 * @hibernate.id
	 */
	public com.huateng.po.TblTermInfTmpPK getId() {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * 
	 * @param id
	 *            the new ID
	 */
	public void setId(com.huateng.po.TblTermInfTmpPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}

	/**
	 * Return the value associated with the column: RECORD_STA
	 */
	public java.lang.String getRecordSta() {
		return recordSta;
	}

	/**
	 * Set the value related to the column: RECORD_STA
	 * 
	 * @param recordSta
	 *            the RECORD_STA value
	 */
	public void setRecordSta(java.lang.String recordSta) {
		this.recordSta = recordSta;
	}

	/**
	 * Return the value associated with the column: TERM_STA
	 */
	public java.lang.String getTermSta() {
		return termSta;
	}

	/**
	 * Set the value related to the column: TERM_STA
	 * 
	 * @param termSta
	 *            the TERM_STA value
	 */
	public void setTermSta(java.lang.String termSta) {
		this.termSta = termSta;
	}

	/**
	 * Return the value associated with the column: TERM_SIGN_STA
	 */
	public java.lang.String getTermSignSta() {
		return termSignSta;
	}

	/**
	 * Set the value related to the column: TERM_SIGN_STA
	 * 
	 * @param termSignSta
	 *            the TERM_SIGN_STA value
	 */
	public void setTermSignSta(java.lang.String termSignSta) {
		this.termSignSta = termSignSta;
	}

	/**
	 * Return the value associated with the column: CHK_STA
	 */
	public java.lang.String getChkSta() {
		return chkSta;
	}

	/**
	 * Set the value related to the column: CHK_STA
	 * 
	 * @param chkSta
	 *            the CHK_STA value
	 */
	public void setChkSta(java.lang.String chkSta) {
		this.chkSta = chkSta;
	}

	/**
	 * Return the value associated with the column: TERM_SEND_FLAG
	 */
	public java.lang.String getTermSendFlag() {
		return termSendFlag;
	}

	/**
	 * Set the value related to the column: TERM_SEND_FLAG
	 * 
	 * @param termSendFlag
	 *            the TERM_SEND_FLAG value
	 */
	public void setTermSendFlag(java.lang.String termSendFlag) {
		this.termSendFlag = termSendFlag;
	}

	/**
	 * Return the value associated with the column: TERM_I_CARD_FLAG
	 */
	public java.lang.String getTermICardFlag() {
		return termICardFlag;
	}

	/**
	 * Set the value related to the column: TERM_I_CARD_FLAG
	 * 
	 * @param termICardFlag
	 *            the TERM_I_CARD_FLAG value
	 */
	public void setTermICardFlag(java.lang.String termICardFlag) {
		this.termICardFlag = termICardFlag;
	}

	/**
	 * Return the value associated with the column: RESERVE_FLAG1
	 */
	public java.lang.String getReserveFlag1() {
		return reserveFlag1;
	}

	/**
	 * Set the value related to the column: RESERVE_FLAG1
	 * 
	 * @param reserveFlag1
	 *            the RESERVE_FLAG1 value
	 */
	public void setReserveFlag1(java.lang.String reserveFlag1) {
		this.reserveFlag1 = reserveFlag1;
	}

	/**
	 * Return the value associated with the column: RESERVE_FLAG2
	 */
	public java.lang.String getReserveFlag2() {
		return reserveFlag2;
	}

	/**
	 * Set the value related to the column: RESERVE_FLAG2
	 * 
	 * @param reserveFlag2
	 *            the RESERVE_FLAG2 value
	 */
	public void setReserveFlag2(java.lang.String reserveFlag2) {
		this.reserveFlag2 = reserveFlag2;
	}

	/**
	 * Return the value associated with the column: RESERVE_FLAG3
	 */
	public java.lang.String getReserveFlag3() {
		return reserveFlag3;
	}

	/**
	 * Set the value related to the column: RESERVE_FLAG3
	 * 
	 * @param reserveFlag3
	 *            the RESERVE_FLAG3 value
	 */
	public void setReserveFlag3(java.lang.String reserveFlag3) {
		this.reserveFlag3 = reserveFlag3;
	}

	/**
	 * Return the value associated with the column: RESERVE_FLAG4
	 */
	public java.lang.String getReserveFlag4() {
		return reserveFlag4;
	}

	/**
	 * Set the value related to the column: RESERVE_FLAG4
	 * 
	 * @param reserveFlag4
	 *            the RESERVE_FLAG4 value
	 */
	public void setReserveFlag4(java.lang.String reserveFlag4) {
		this.reserveFlag4 = reserveFlag4;
	}

	/**
	 * Return the value associated with the column: TERM_SET_CUR
	 */
	public java.lang.String getTermSetCur() {
		return termSetCur;
	}

	/**
	 * Set the value related to the column: TERM_SET_CUR
	 * 
	 * @param termSetCur
	 *            the TERM_SET_CUR value
	 */
	public void setTermSetCur(java.lang.String termSetCur) {
		this.termSetCur = termSetCur;
	}

	/**
	 * Return the value associated with the column: TERM_MCC
	 */
	public java.lang.String getTermMcc() {
		return termMcc;
	}

	/**
	 * Set the value related to the column: TERM_MCC
	 * 
	 * @param termMcc
	 *            the TERM_MCC value
	 */
	public void setTermMcc(java.lang.String termMcc) {
		this.termMcc = termMcc;
	}

	/**
	 * Return the value associated with the column: TERM_FACTORY
	 */
	public java.lang.String getTermFactory() {
		return termFactory;
	}

	/**
	 * Set the value related to the column: TERM_FACTORY
	 * 
	 * @param termFactory
	 *            the TERM_FACTORY value
	 */
	public void setTermFactory(java.lang.String termFactory) {
		this.termFactory = termFactory;
	}

	/**
	 * Return the value associated with the column: TERM_MACH_TP
	 */
	public java.lang.String getTermMachTp() {
		return termMachTp;
	}

	/**
	 * Set the value related to the column: TERM_MACH_TP
	 * 
	 * @param termMachTp
	 *            the TERM_MACH_TP value
	 */
	public void setTermMachTp(java.lang.String termMachTp) {
		this.termMachTp = termMachTp;
	}

	/**
	 * Return the value associated with the column: TERM_VER
	 */
	public java.lang.String getTermVer() {
		return termVer;
	}

	/**
	 * Set the value related to the column: TERM_VER
	 * 
	 * @param termVer
	 *            the TERM_VER value
	 */
	public void setTermVer(java.lang.String termVer) {
		this.termVer = termVer;
	}

	/**
	 * Return the value associated with the column: TERM_SINGLE_LIMIT
	 */
	public java.lang.String getTermSingleLimit() {
		return termSingleLimit;
	}

	/**
	 * Set the value related to the column: TERM_SINGLE_LIMIT
	 * 
	 * @param termSingleLimit
	 *            the TERM_SINGLE_LIMIT value
	 */
	public void setTermSingleLimit(java.lang.String termSingleLimit) {
		this.termSingleLimit = termSingleLimit;
	}

	/**
	 * Return the value associated with the column: PAY_STAGE_NUM
	 */
	public java.lang.Integer getPayStageNum() {
		return payStageNum;
	}

	/**
	 * Set the value related to the column: PAY_STAGE_NUM
	 * 
	 * @param payStageNum
	 *            the PAY_STAGE_NUM value
	 */
	public void setPayStageNum(java.lang.Integer payStageNum) {
		this.payStageNum = payStageNum;
	}

	/**
	 * Return the value associated with the column: PAY_STAGE_LIMIT
	 */
	public java.math.BigDecimal getPayStageLimit() {
		return payStageLimit;
	}

	/**
	 * Set the value related to the column: PAY_STAGE_LIMIT
	 * 
	 * @param payStageLimit
	 *            the PAY_STAGE_LIMIT value
	 */
	public void setPayStageLimit(java.math.BigDecimal payStageLimit) {
		this.payStageLimit = payStageLimit;
	}

	/**
	 * Return the value associated with the column: FINANCE_CARD1
	 */
	public java.lang.String getFinanceCard1() {
		return financeCard1;
	}

	/**
	 * Set the value related to the column: FINANCE_CARD1
	 * 
	 * @param financeCard1
	 *            the FINANCE_CARD1 value
	 */
	public void setFinanceCard1(java.lang.String financeCard1) {
		this.financeCard1 = financeCard1;
	}

	/**
	 * Return the value associated with the column: FINANCE_CARD2
	 */
	public java.lang.String getFinanceCard2() {
		return financeCard2;
	}

	/**
	 * Set the value related to the column: FINANCE_CARD2
	 * 
	 * @param financeCard2
	 *            the FINANCE_CARD2 value
	 */
	public void setFinanceCard2(java.lang.String financeCard2) {
		this.financeCard2 = financeCard2;
	}

	/**
	 * Return the value associated with the column: FINANCE_CARD3
	 */
	public java.lang.String getFinanceCard3() {
		return financeCard3;
	}

	/**
	 * Set the value related to the column: FINANCE_CARD3
	 * 
	 * @param financeCard3
	 *            the FINANCE_CARD3 value
	 */
	public void setFinanceCard3(java.lang.String financeCard3) {
		this.financeCard3 = financeCard3;
	}

	/**
	 * Return the value associated with the column: RESERVE_AMOUNT1
	 */
	public java.math.BigDecimal getReserveAmount1() {
		return reserveAmount1;
	}

	/**
	 * Set the value related to the column: RESERVE_AMOUNT1
	 * 
	 * @param reserveAmount1
	 *            the RESERVE_AMOUNT1 value
	 */
	public void setReserveAmount1(java.math.BigDecimal reserveAmount1) {
		this.reserveAmount1 = reserveAmount1;
	}

	/**
	 * Return the value associated with the column: RESERVE_AMOUNT2
	 */
	public java.math.BigDecimal getReserveAmount2() {
		return reserveAmount2;
	}

	/**
	 * Set the value related to the column: RESERVE_AMOUNT2
	 * 
	 * @param reserveAmount2
	 *            the RESERVE_AMOUNT2 value
	 */
	public void setReserveAmount2(java.math.BigDecimal reserveAmount2) {
		this.reserveAmount2 = reserveAmount2;
	}

	/**
	 * Return the value associated with the column: TERM_TP
	 */
	public java.lang.String getTermTp() {
		return termTp;
	}

	/**
	 * Set the value related to the column: TERM_TP
	 * 
	 * @param termTp
	 *            the TERM_TP value
	 */
	public void setTermTp(java.lang.String termTp) {
		this.termTp = termTp;
	}

	/**
	 * Return the value associated with the column: PARAM_DOWN_SIGN
	 */
	public java.lang.String getParamDownSign() {
		return paramDownSign;
	}

	/**
	 * Set the value related to the column: PARAM_DOWN_SIGN
	 * 
	 * @param paramDownSign
	 *            the PARAM_DOWN_SIGN value
	 */
	public void setParamDownSign(java.lang.String paramDownSign) {
		this.paramDownSign = paramDownSign;
	}

	/**
	 * Return the value associated with the column: PARAM1_DOWN_SIGN
	 */
	public java.lang.String getParam1DownSign() {
		return param1DownSign;
	}

	/**
	 * Set the value related to the column: PARAM1_DOWN_SIGN
	 * 
	 * @param param1DownSign
	 *            the PARAM1_DOWN_SIGN value
	 */
	public void setParam1DownSign(java.lang.String param1DownSign) {
		this.param1DownSign = param1DownSign;
	}

	/**
	 * Return the value associated with the column: IC_DOWN_SIGN
	 */
	public java.lang.String getIcDownSign() {
		return icDownSign;
	}

	/**
	 * Set the value related to the column: IC_DOWN_SIGN
	 * 
	 * @param icDownSign
	 *            the IC_DOWN_SIGN value
	 */
	public void setIcDownSign(java.lang.String icDownSign) {
		this.icDownSign = icDownSign;
	}

	/**
	 * Return the value associated with the column: KEY_DOWN_SIGN
	 */
	public java.lang.String getKeyDownSign() {
		return keyDownSign;
	}

	/**
	 * Set the value related to the column: KEY_DOWN_SIGN
	 * 
	 * @param keyDownSign
	 *            the KEY_DOWN_SIGN value
	 */
	public void setKeyDownSign(java.lang.String keyDownSign) {
		this.keyDownSign = keyDownSign;
	}

	/**
	 * Return the value associated with the column: PROP_TP
	 */
	public java.lang.String getPropTp() {
		return propTp;
	}

	/**
	 * Set the value related to the column: PROP_TP
	 * 
	 * @param propTp
	 *            the PROP_TP value
	 */
	public void setPropTp(java.lang.String propTp) {
		this.propTp = propTp;
	}

	/**
	 * Return the value associated with the column: PROP_INS_NM
	 */
	public java.lang.String getPropInsNm() {
		return propInsNm;
	}

	/**
	 * Set the value related to the column: PROP_INS_NM
	 * 
	 * @param propInsNm
	 *            the PROP_INS_NM value
	 */
	public void setPropInsNm(java.lang.String propInsNm) {
		this.propInsNm = propInsNm;
	}

	/**
	 * Return the value associated with the column: F_CARD_SUP_FLAG
	 */
	public java.lang.String getFCardSupFlag() {
		return fCardSupFlag;
	}

	/**
	 * Set the value related to the column: F_CARD_SUP_FLAG
	 * 
	 * @param fCardSupFlag
	 *            the F_CARD_SUP_FLAG value
	 */
	public void setFCardSupFlag(java.lang.String fCardSupFlag) {
		this.fCardSupFlag = fCardSupFlag;
	}

	/**
	 * Return the value associated with the column: F_CARD_COMPANY
	 */
	public java.lang.String getFCardCompany() {
		return fCardCompany;
	}

	/**
	 * Set the value related to the column: F_CARD_COMPANY
	 * 
	 * @param fCardCompany
	 *            the F_CARD_COMPANY value
	 */
	public void setFCardCompany(java.lang.String fCardCompany) {
		this.fCardCompany = fCardCompany;
	}

	/**
	 * Return the value associated with the column: SUPPORT_IC
	 */
	public java.lang.String getSupportIc() {
		return supportIc;
	}

	/**
	 * Set the value related to the column: SUPPORT_IC
	 * 
	 * @param supportIc
	 *            the SUPPORT_IC value
	 */
	public void setSupportIc(java.lang.String supportIc) {
		this.supportIc = supportIc;
	}

	/**
	 * Return the value associated with the column: PSAM_ID
	 */
	public java.lang.String getPsamId() {
		return psamId;
	}

	/**
	 * Set the value related to the column: PSAM_ID
	 * 
	 * @param psamId
	 *            the PSAM_ID value
	 */
	public void setPsamId(java.lang.String psamId) {
		this.psamId = psamId;
	}

	/**
	 * Return the value associated with the column: TERM_PLACE
	 */
	public java.lang.String getTermPlace() {
		return termPlace;
	}

	/**
	 * Set the value related to the column: TERM_PLACE
	 * 
	 * @param termPlace
	 *            the TERM_PLACE value
	 */
	public void setTermPlace(java.lang.String termPlace) {
		this.termPlace = termPlace;
	}

	/**
	 * Return the value associated with the column: CONNECT_MODE
	 */
	public java.lang.String getConnectMode() {
		return connectMode;
	}

	/**
	 * Set the value related to the column: CONNECT_MODE
	 * 
	 * @param connectMode
	 *            the CONNECT_MODE value
	 */
	public void setConnectMode(java.lang.String connectMode) {
		this.connectMode = connectMode;
	}

	/**
	 * Return the value associated with the column: DIAL_TP
	 */
	public java.lang.String getDialTp() {
		return dialTp;
	}

	/**
	 * Set the value related to the column: DIAL_TP
	 * 
	 * @param dialTp
	 *            the DIAL_TP value
	 */
	public void setDialTp(java.lang.String dialTp) {
		this.dialTp = dialTp;
	}

	/**
	 * Return the value associated with the column: TERM_BRANCH
	 */
	public java.lang.String getTermBranch() {
		return termBranch;
	}

	/**
	 * Set the value related to the column: TERM_BRANCH
	 * 
	 * @param termBranch
	 *            the TERM_BRANCH value
	 */
	public void setTermBranch(java.lang.String termBranch) {
		this.termBranch = termBranch;
	}

	/**
	 * Return the value associated with the column: TERM_BANK
	 */
	public java.lang.String getTermBank() {
		return termBank;
	}

	/**
	 * Set the value related to the column: TERM_BANK
	 * 
	 * @param termBank
	 *            the TERM_BANK value
	 */
	public void setTermBank(java.lang.String termBank) {
		this.termBank = termBank;
	}

	/**
	 * Return the value associated with the column: TERM_INS
	 */
	public java.lang.String getTermIns() {
		return termIns;
	}

	/**
	 * Set the value related to the column: TERM_INS
	 * 
	 * @param termIns
	 *            the TERM_INS value
	 */
	public void setTermIns(java.lang.String termIns) {
		this.termIns = termIns;
	}

	/**
	 * Return the value associated with the column: TERM_VER_TP
	 */
	public java.lang.String getTermVerTp() {
		return termVerTp;
	}

	/**
	 * Set the value related to the column: TERM_VER_TP
	 * 
	 * @param termVerTp
	 *            the TERM_VER_TP value
	 */
	public void setTermVerTp(java.lang.String termVerTp) {
		this.termVerTp = termVerTp;
	}

	/**
	 * Return the value associated with the column: TERM_BATCH_NM
	 */
	public java.lang.String getTermBatchNm() {
		return termBatchNm;
	}

	/**
	 * Set the value related to the column: TERM_BATCH_NM
	 * 
	 * @param termBatchNm
	 *            the TERM_BATCH_NM value
	 */
	public void setTermBatchNm(java.lang.String termBatchNm) {
		this.termBatchNm = termBatchNm;
	}

	/**
	 * Return the value associated with the column: TERM_STLM_DT
	 */
	public java.lang.String getTermStlmDt() {
		return termStlmDt;
	}

	/**
	 * Set the value related to the column: TERM_STLM_DT
	 * 
	 * @param termStlmDt
	 *            the TERM_STLM_DT value
	 */
	public void setTermStlmDt(java.lang.String termStlmDt) {
		this.termStlmDt = termStlmDt;
	}

	/**
	 * Return the value associated with the column: TERM_TXN_SUP
	 */
	public java.lang.String getTermTxnSup() {
		return termTxnSup;
	}

	/**
	 * Set the value related to the column: TERM_TXN_SUP
	 * 
	 * @param termTxnSup
	 *            the TERM_TXN_SUP value
	 */
	public void setTermTxnSup(java.lang.String termTxnSup) {
		this.termTxnSup = termTxnSup;
	}

	/**
	 * Return the value associated with the column: TERM_PARA
	 */
	public java.lang.String getTermPara() {
		return termPara;
	}

	/**
	 * Set the value related to the column: TERM_PARA
	 * 
	 * @param termPara
	 *            the TERM_PARA value
	 */
	public void setTermPara(java.lang.String termPara) {
		this.termPara = termPara;
	}

	/**
	 * Return the value associated with the column: TERM_PARA_1
	 */
	public java.lang.String getTermPara1() {
		return termPara1;
	}

	/**
	 * Set the value related to the column: TERM_PARA_1
	 * 
	 * @param termPara1
	 *            the TERM_PARA_1 value
	 */
	public void setTermPara1(java.lang.String termPara1) {
		this.termPara1 = termPara1;
	}

	/**
	 * Return the value associated with the column: TERM_PARA_2
	 */
	public java.lang.String getTermPara2() {
		return termPara2;
	}

	/**
	 * Set the value related to the column: TERM_PARA_2
	 * 
	 * @param termPara2
	 *            the TERM_PARA_2 value
	 */
	public void setTermPara2(java.lang.String termPara2) {
		this.termPara2 = termPara2;
	}

	/**
	 * Return the value associated with the column: BIND_TEL1
	 */
	public java.lang.String getBindTel1() {
		return bindTel1;
	}

	/**
	 * Set the value related to the column: BIND_TEL1
	 * 
	 * @param bindTel1
	 *            the BIND_TEL1 value
	 */
	public void setBindTel1(java.lang.String bindTel1) {
		this.bindTel1 = bindTel1;
	}

	/**
	 * Return the value associated with the column: BIND_TEL2
	 */
	public java.lang.String getBindTel2() {
		return bindTel2;
	}

	/**
	 * Set the value related to the column: BIND_TEL2
	 * 
	 * @param bindTel2
	 *            the BIND_TEL2 value
	 */
	public void setBindTel2(java.lang.String bindTel2) {
		this.bindTel2 = bindTel2;
	}

	/**
	 * Return the value associated with the column: BIND_TEL3
	 */
	public java.lang.String getBindTel3() {
		return bindTel3;
	}

	/**
	 * Set the value related to the column: BIND_TEL3
	 * 
	 * @param bindTel3
	 *            the BIND_TEL3 value
	 */
	public void setBindTel3(java.lang.String bindTel3) {
		this.bindTel3 = bindTel3;
	}

	/**
	 * Return the value associated with the column: ZONE_NUM
	 */
	public java.lang.String getZoneNum() {
		return zoneNum;
	}

	/**
	 * Set the value related to the column: ZONE_NUM
	 * 
	 * @param zoneNum
	 *            the ZONE_NUM value
	 */
	public void setZoneNum(java.lang.String zoneNum) {
		this.zoneNum = zoneNum;
	}

	/**
	 * Return the value associated with the column: TERM_ADDR
	 */
	public java.lang.String getTermAddr() {
		return termAddr;
	}

	/**
	 * Set the value related to the column: TERM_ADDR
	 * 
	 * @param termAddr
	 *            the TERM_ADDR value
	 */
	public void setTermAddr(java.lang.String termAddr) {
		this.termAddr = termAddr;
	}

	/**
	 * Return the value associated with the column: OPR_NM
	 */
	public java.lang.String getOprNm() {
		return oprNm;
	}

	/**
	 * Set the value related to the column: OPR_NM
	 * 
	 * @param oprNm
	 *            the OPR_NM value
	 */
	public void setOprNm(java.lang.String oprNm) {
		this.oprNm = oprNm;
	}

	/**
	 * Return the value associated with the column: CONT_TEL
	 */
	public java.lang.String getContTel() {
		return contTel;
	}

	/**
	 * Set the value related to the column: CONT_TEL
	 * 
	 * @param contTel
	 *            the CONT_TEL value
	 */
	public void setContTel(java.lang.String contTel) {
		this.contTel = contTel;
	}

	/**
	 * Return the value associated with the column: EQUIP_INV_ID
	 */
	public java.lang.String getEquipInvId() {
		return equipInvId;
	}

	/**
	 * Set the value related to the column: EQUIP_INV_ID
	 * 
	 * @param equipInvId
	 *            the EQUIP_INV_ID value
	 */
	public void setEquipInvId(java.lang.String equipInvId) {
		this.equipInvId = equipInvId;
	}

	/**
	 * Return the value associated with the column: EQUIP_INV_NM
	 */
	public java.lang.String getEquipInvNm() {
		return equipInvNm;
	}

	/**
	 * Set the value related to the column: EQUIP_INV_NM
	 * 
	 * @param equipInvNm
	 *            the EQUIP_INV_NM value
	 */
	public void setEquipInvNm(java.lang.String equipInvNm) {
		this.equipInvNm = equipInvNm;
	}

	/**
	 * Return the value associated with the column: DEPOSIT_FLAG
	 */
	public java.lang.String getDepositFlag() {
		return depositFlag;
	}

	/**
	 * Set the value related to the column: DEPOSIT_FLAG
	 * 
	 * @param depositFlag
	 *            the DEPOSIT_FLAG value
	 */
	public void setDepositFlag(java.lang.String depositFlag) {
		this.depositFlag = depositFlag;
	}

	// /**
	// * Return the value associated with the column: DEPOSIT
	// */
	// public java.math.BigDecimal getDeposit () {
	// return deposit;
	// }
	//
	// /**
	// * Set the value related to the column: DEPOSIT
	// * @param deposit the DEPOSIT value
	// */
	// public void setDeposit (java.math.BigDecimal deposit) {
	// this.deposit = deposit;
	// }

	/**
	 * Return the value associated with the column: RUN_MAIN_ID_1
	 */
	public java.lang.String getRunMainId1() {
		return runMainId1;
	}

	/**
	 * Set the value related to the column: RUN_MAIN_ID_1
	 * 
	 * @param runMainId1
	 *            the RUN_MAIN_ID_1 value
	 */
	public void setRunMainId1(java.lang.String runMainId1) {
		this.runMainId1 = runMainId1;
	}

	/**
	 * Return the value associated with the column: RUN_MAIN_NM_1
	 */
	public java.lang.String getRunMainNm1() {
		return runMainNm1;
	}

	/**
	 * Set the value related to the column: RUN_MAIN_NM_1
	 * 
	 * @param runMainNm1
	 *            the RUN_MAIN_NM_1 value
	 */
	public void setRunMainNm1(java.lang.String runMainNm1) {
		this.runMainNm1 = runMainNm1;
	}

	/**
	 * Return the value associated with the column: RUN_MAIN_ID_2
	 */
	public java.lang.String getRunMainId2() {
		return runMainId2;
	}

	/**
	 * Set the value related to the column: RUN_MAIN_ID_2
	 * 
	 * @param runMainId2
	 *            the RUN_MAIN_ID_2 value
	 */
	public void setRunMainId2(java.lang.String runMainId2) {
		this.runMainId2 = runMainId2;
	}

	/**
	 * Return the value associated with the column: RUN_MAIN_NM_2
	 */
	public java.lang.String getRunMainNm2() {
		return runMainNm2;
	}

	/**
	 * Set the value related to the column: RUN_MAIN_NM_2
	 * 
	 * @param runMainNm2
	 *            the RUN_MAIN_NM_2 value
	 */
	public void setRunMainNm2(java.lang.String runMainNm2) {
		this.runMainNm2 = runMainNm2;
	}

	/**
	 * Return the value associated with the column: OTH_SVR_ID
	 */
	public java.lang.String getOthSvrId() {
		return othSvrId;
	}

	/**
	 * Set the value related to the column: OTH_SVR_ID
	 * 
	 * @param othSvrId
	 *            the OTH_SVR_ID value
	 */
	public void setOthSvrId(java.lang.String othSvrId) {
		this.othSvrId = othSvrId;
	}

	/**
	 * Return the value associated with the column: OTH_SVR_NM
	 */
	public java.lang.String getOthSvrNm() {
		return othSvrNm;
	}

	/**
	 * Set the value related to the column: OTH_SVR_NM
	 * 
	 * @param othSvrNm
	 *            the OTH_SVR_NM value
	 */
	public void setOthSvrNm(java.lang.String othSvrNm) {
		this.othSvrNm = othSvrNm;
	}

	/**
	 * Return the value associated with the column: REC_OPR_ID
	 */
	public java.lang.String getRecOprId() {
		return recOprId;
	}

	/**
	 * Set the value related to the column: REC_OPR_ID
	 * 
	 * @param recOprId
	 *            the REC_OPR_ID value
	 */
	public void setRecOprId(java.lang.String recOprId) {
		this.recOprId = recOprId;
	}

	/**
	 * Return the value associated with the column: REC_UPD_OPR
	 */
	public java.lang.String getRecUpdOpr() {
		return recUpdOpr;
	}

	/**
	 * Set the value related to the column: REC_UPD_OPR
	 * 
	 * @param recUpdOpr
	 *            the REC_UPD_OPR value
	 */
	public void setRecUpdOpr(java.lang.String recUpdOpr) {
		this.recUpdOpr = recUpdOpr;
	}

	/**
	 * Return the value associated with the column: REC_CRT_OPR
	 */
	public java.lang.String getRecCrtOpr() {
		return recCrtOpr;
	}

	/**
	 * Set the value related to the column: REC_CRT_OPR
	 * 
	 * @param recCrtOpr
	 *            the REC_CRT_OPR value
	 */
	public void setRecCrtOpr(java.lang.String recCrtOpr) {
		this.recCrtOpr = recCrtOpr;
	}

	/**
	 * Return the value associated with the column: REC_CHE_OPR
	 */
	public java.lang.String getRecCheOpr() {
		return recCheOpr;
	}

	/**
	 * Set the value related to the column: REC_CHE_OPR
	 * 
	 * @param recCheOpr
	 *            the REC_CHE_OPR value
	 */
	public void setRecCheOpr(java.lang.String recCheOpr) {
		this.recCheOpr = recCheOpr;
	}

	/**
	 * Return the value associated with the column: RESERVE_DATE
	 */
	public java.lang.String getReserveDate() {
		return reserveDate;
	}

	/**
	 * Set the value related to the column: RESERVE_DATE
	 * 
	 * @param reserveDate
	 *            the RESERVE_DATE value
	 */
	public void setReserveDate(java.lang.String reserveDate) {
		this.reserveDate = reserveDate;
	}

	/**
	 * Return the value associated with the column: REC_UPD_TS
	 */
	public java.lang.String getRecUpdTs() {
		return recUpdTs;
	}

	/**
	 * Set the value related to the column: REC_UPD_TS
	 * 
	 * @param recUpdTs
	 *            the REC_UPD_TS value
	 */
	public void setRecUpdTs(java.lang.String recUpdTs) {
		this.recUpdTs = recUpdTs;
	}

	/**
	 * Return the value associated with the column: REC_DEL_TS
	 */
	public java.lang.String getRecDelTs() {
		return recDelTs;
	}

	/**
	 * Set the value related to the column: REC_DEL_TS
	 * 
	 * @param recDelTs
	 *            the REC_DEL_TS value
	 */
	public void setRecDelTs(java.lang.String recDelTs) {
		this.recDelTs = recDelTs;
	}

	/**
	 * Return the value associated with the column: MISC_1
	 */
	public java.lang.String getMisc1() {
		return misc1;
	}

	/**
	 * Set the value related to the column: MISC_1
	 * 
	 * @param misc1
	 *            the MISC_1 value
	 */
	public void setMisc1(java.lang.String misc1) {
		this.misc1 = misc1;
	}

	/**
	 * Return the value associated with the column: MISC_2
	 */
	public java.lang.String getMisc2() {
		return misc2;
	}

	/**
	 * Set the value related to the column: MISC_2
	 * 
	 * @param misc2
	 *            the MISC_2 value
	 */
	public void setMisc2(java.lang.String misc2) {
		this.misc2 = misc2;
	}

	/**
	 * Return the value associated with the column: MISC_3
	 */
	public java.lang.String getMisc3() {
		return misc3;
	}

	/**
	 * Set the value related to the column: MISC_3
	 * 
	 * @param misc3
	 *            the MISC_3 value
	 */
	public void setMisc3(java.lang.String misc3) {
		this.misc3 = misc3;
	}

	/**
	 * @return the licenceNo
	 */
	public String getLicenceNo() {
		return licenceNo;
	}

	/**
	 * @param licenceNo
	 *            the licenceNo to set
	 */
	public void setLicenceNo(String licenceNo) {
		this.licenceNo = licenceNo;
	}

	/**
	 * @return the depositAmt
	 */
	public BigDecimal getDepositAmt() {
		return depositAmt;
	}

	/**
	 * @param depositAmt
	 *            the depositAmt to set
	 */
	public void setDepositAmt(BigDecimal depositAmt) {
		this.depositAmt = depositAmt;
	}

	/**
	 * @return the depositState
	 */
	public String getDepositState() {
		return depositState;
	}

	/**
	 * @param depositState
	 *            the depositState to set
	 */
	public void setDepositState(String depositState) {
		this.depositState = depositState;
	}

	/**
	 * @return the leaseFee
	 */
	public BigDecimal getLeaseFee() {
		return leaseFee;
	}

	/**
	 * @param leaseFee
	 *            the leaseFee to set
	 */
	public void setLeaseFee(BigDecimal leaseFee) {
		this.leaseFee = leaseFee;
	}

	/**
	 * @return the checkCardNo
	 */
	public String getCheckCardNo() {
		return checkCardNo;
	}

	/**
	 * @param checkCardNo
	 *            the checkCardNo to set
	 */
	public void setCheckCardNo(String checkCardNo) {
		this.checkCardNo = checkCardNo;
	}

	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof com.huateng.po.TblTermInfTmp))
			return false;
		else {
			com.huateng.po.TblTermInfTmp tblTermInfTmp = (com.huateng.po.TblTermInfTmp) obj;
			if (null == this.getId() || null == tblTermInfTmp.getId())
				return false;
			else
				return (this.getId().equals(tblTermInfTmp.getId()));
		}
	}

	public int hashCode() {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId())
				return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":"
						+ this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}

	public String toString() {
		return super.toString();
	}

	public Serializable clone() {
		TblTermInf tblTermInf = new TblTermInf();

		tblTermInf.setRecordSta(this.recordSta);
		tblTermInf.setTermSta(this.termSta);
		tblTermInf.setTermSignSta(this.termSignSta);
		tblTermInf.setChkSta(this.chkSta);
		tblTermInf.setTermSendFlag(this.termSendFlag);
		tblTermInf.setTermICardFlag(this.termICardFlag);
		tblTermInf.setReserveFlag1(this.reserveFlag1);
		tblTermInf.setReserveFlag2(this.reserveFlag2);
		tblTermInf.setReserveFlag3(this.reserveFlag3);
		tblTermInf.setReserveFlag4(this.reserveFlag4);
		tblTermInf.setTermSetCur(this.termSetCur);
		tblTermInf.setTermMcc(this.termMcc);
		tblTermInf.setTermFactory(this.termFactory);
		tblTermInf.setTermMachTp(this.termMachTp);
		tblTermInf.setTermVer(this.termVer);
		tblTermInf.setTermSingleLimit(this.termSingleLimit);
		tblTermInf.setPayStageNum(payStageNum);
		tblTermInf.setPayStageLimit(payStageLimit);
		tblTermInf.setFinanceCard1(this.financeCard1);
		tblTermInf.setFinanceCard2(this.financeCard2);
		tblTermInf.setFinanceCard3(this.financeCard3);
		tblTermInf.setReserveAmount1(reserveAmount1);
		tblTermInf.setReserveAmount2(reserveAmount2);
		tblTermInf.setTermTp(this.termTp);
		tblTermInf.setParamDownSign(this.paramDownSign);
		tblTermInf.setParam1DownSign(this.param1DownSign);
		tblTermInf.setIcDownSign(this.icDownSign);
		tblTermInf.setKeyDownSign(this.keyDownSign);
		tblTermInf.setPropTp(this.propTp);
		tblTermInf.setPropInsNm(this.propInsNm);
		tblTermInf.setFCardSupFlag(this.fCardSupFlag);
		tblTermInf.setFCardCompany(this.fCardCompany);
		tblTermInf.setSupportIc(this.supportIc);
		tblTermInf.setPsamId(this.psamId);
		tblTermInf.setTermPlace(this.termPlace);
		tblTermInf.setConnectMode(this.connectMode);
		tblTermInf.setDialTp(this.dialTp);
		tblTermInf.setTermBranch(this.termBranch);
		tblTermInf.setTermBank(this.termBank);
		tblTermInf.setTermIns(this.termIns);
		tblTermInf.setTermVerTp(this.termVerTp);
		tblTermInf.setTermBatchNm(this.termBatchNm);
		tblTermInf.setTermStlmDt(this.termStlmDt);
		tblTermInf.setTermTxnSup(this.termTxnSup);
		tblTermInf.setTermPara(this.termPara);
		tblTermInf.setTermPara1(this.termPara1);
		tblTermInf.setTermPara2(this.termPara2);
		tblTermInf.setBindTel1(this.bindTel1);
		tblTermInf.setBindTel2(this.bindTel2);
		tblTermInf.setBindTel3(this.bindTel3);
		tblTermInf.setZoneNum(this.zoneNum);
		tblTermInf.setTermAddr(this.termAddr);
		tblTermInf.setOprNm(this.oprNm);
		tblTermInf.setContTel(this.contTel);
		tblTermInf.setEquipInvId(this.equipInvId);
		tblTermInf.setEquipInvNm(this.equipInvNm);
		tblTermInf.setDepositFlag(this.depositFlag);
		tblTermInf.setDepositAmt(this.depositAmt);
		tblTermInf.setRunMainId1(this.runMainId1);
		tblTermInf.setRunMainNm1(this.runMainNm1);
		tblTermInf.setRunMainId2(this.runMainId2);
		tblTermInf.setRunMainNm2(this.runMainNm2);
		tblTermInf.setOthSvrId(this.othSvrId);
		tblTermInf.setOthSvrNm(this.othSvrNm);
		tblTermInf.setRecOprId(this.recOprId);
		tblTermInf.setRecUpdOpr(this.recUpdOpr);
		tblTermInf.setRecCrtOpr(this.recCrtOpr);
		tblTermInf.setRecCheOpr(this.recCheOpr);
		tblTermInf.setReserveDate(this.reserveDate);
		tblTermInf.setRecUpdTs(this.recUpdTs);
		tblTermInf.setRecDelTs(this.recDelTs);
		tblTermInf.setMisc1(this.misc1);
		tblTermInf.setMisc2(this.misc2);
		tblTermInf.setMisc3(this.misc3);
		// tblTermInf.setProductCd(this.productCd);
		// tblTermInf.setId(this.getId().getTermId());
		// tblTermInf.setRecCrtTs(this.getId().getRecCrtTs());

		tblTermInf.setLicenceNo(this.licenceNo);
		// tblTermInf.setBusType(this.busType);
		// tblTermInf.setCardType(this.cardType);
		tblTermInf.setCheckCardNo(this.checkCardNo);
		tblTermInf.setDepositState(this.depositState);
		tblTermInf.setLeaseFee(this.leaseFee);

		return tblTermInf;
	}

	public String getTermName() {
		return termName;
	}

	public void setTermName(String termName) {
		this.termName = termName;
	}

	public String getMappingMchntTypeOne() {
		return mappingMchntTypeOne;
	}

	public void setMappingMchntTypeOne(String mappingMchntTypeOne) {
		this.mappingMchntTypeOne = mappingMchntTypeOne;
	}

	public String getMappingMchntcdOne() {
		return mappingMchntcdOne;
	}

	public void setMappingMchntcdOne(String mappingMchntcdOne) {
		this.mappingMchntcdOne = mappingMchntcdOne;
	}

	public String getMappingMchntTypeTwo() {
		return mappingMchntTypeTwo;
	}

	public void setMappingMchntTypeTwo(String mappingMchntTypeTwo) {
		this.mappingMchntTypeTwo = mappingMchntTypeTwo;
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