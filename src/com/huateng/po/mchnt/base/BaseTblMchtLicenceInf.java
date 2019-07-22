package com.huateng.po.mchnt.base;

import java.io.Serializable;

import com.huateng.po.mchnt.TblMchtBaseInfTmp;
import com.huateng.po.mchnt.TblMchtLicenceInfTmp;


/**
 * This is an object that contains data related to the TBL_MCHT_BASE_INF_TMP table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="TBL_MCHT_LICENCE_INF"
 */

public abstract class BaseTblMchtLicenceInf  implements Serializable {

	public static String REF = "TblMchtLicenceInf";
	public static String PROP_LICENCE_NO = "LicenceNo";
	public static String PROP_ETPS_NM = "EtpsNm";
	public static String PROP_LICENCE_END_DATE = "LicenceEndDate";
	public static String PROP_BANK_LICENCE_NO = "BankLicenceNo";
	public static String PROP_ETPS_TYPE = "EtpsType";
	public static String PROP_FAX_NO = "FaxNo";
	public static String PROP_BUS_AMT = "BusAmt";
	public static String PROP_MCHT_CRE_LVL = "MchtCreLvl";
	public static String PROP_MANAGER = "Manager";
	public static String PROP_ARTIF_CERTIF_TP = "ArtifCertifTp";
	public static String PROP_IDENTITY_NO = "IdentityNO";
	public static String PROP_MANAGER_TEL = "ManagerTel";
	public static String PROP_FAX = "Fax";
	public static String PROP_REG_ADDR = "RegAddr";
	public static String PROP_APPLY_DATE = "ApplyDate";
	public static String PROP_ENABLE_DATE = "EnableDate";
	public static String PROP_PRE_AUD_NM = "PreAudNm";
	public static String PROP_CONFIRM_NM = "ConfirmNm";
	public static String PROP_RESERVED = "Reserved";
	public static String PROP_RESERVED_SND = "ReservedSnd";
	public static String PROP_ETPS_STATE = "EtpsState";
	public static String PROP_REFUSE_MSG1 = "RefuseMsg1";
	public static String PROP_REFUSE_MSG2 = "RefuseMsg2";
	public static String PROP_UPD_OPR_ID = "UpdOprId";
	public static String PROP_CRT_OPR_ID = "CrtOprId";
	public static String PROP_REC_UPD_TS = "RecUpdTs";
	public static String PROP_REC_CRT_TS = "RecCrtTs";


	private int hashCode = Integer.MIN_VALUE;
	
	//primary key
	private String licenceNo;
	//fields
	private String etpsNm;
	private String licenceEndDate;
	private String bankLicenceNo;
	private String etpsType;
	private String faxNo;
	private String busAmt;
	private String mchtCreLvl;
	private String manager;
	private String artifCertifTp;
	private String identityNo;
	private String managerTel;
	private String fax;
	private String regAddr;
	private String applyDate;
	private String enableDate;
	private String preAudNm;
	private String confirmNm;
	private String reserved;
	private String reservedSnd;
	private String etpsState;
	private String refuseMsg1;
	private String refuseMsg2;
	private String updOprId;
	private String crtOprId;
	private String recUpdTs;
	private String recCrtTs;
	
	// constructors
	public BaseTblMchtLicenceInf () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTblMchtLicenceInf (String licenceNo) {
		this.setLicenceNo(licenceNo);
		initialize();
	}

	protected void initialize () {}

	/**
	 * @return the licenceNo
	 */
	public String getLicenceNo() {
		return licenceNo;
	}

	/**
	 * @param licenceNo the licenceNo to set
	 */
	public void setLicenceNo(String licenceNo) {
		this.licenceNo = licenceNo;
	}

	/**
	 * @return the etpsNm
	 */
	public String getEtpsNm() {
		return etpsNm;
	}

	/**
	 * @param etpsNm the etpsNm to set
	 */
	public void setEtpsNm(String etpsNm) {
		this.etpsNm = etpsNm;
	}

	/**
	 * @return the licenceEndDate
	 */
	public String getLicenceEndDate() {
		return licenceEndDate;
	}

	/**
	 * @param licenceEndDate the licenceEndDate to set
	 */
	public void setLicenceEndDate(String licenceEndDate) {
		this.licenceEndDate = licenceEndDate;
	}

	/**
	 * @return the bankLicenceNo
	 */
	public String getBankLicenceNo() {
		return bankLicenceNo;
	}

	/**
	 * @param bankLicenceNo the bankLicenceNo to set
	 */
	public void setBankLicenceNo(String bankLicenceNo) {
		this.bankLicenceNo = bankLicenceNo;
	}

	/**
	 * @return the etpsType
	 */
	public String getEtpsType() {
		return etpsType;
	}

	/**
	 * @param etpsType the etpsType to set
	 */
	public void setEtpsType(String etpsType) {
		this.etpsType = etpsType;
	}

	/**
	 * @return the faxNo
	 */
	public String getFaxNo() {
		return faxNo;
	}

	/**
	 * @param faxNo the faxNo to set
	 */
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	/**
	 * @return the busAmt
	 */
	public String getBusAmt() {
		return busAmt;
	}

	/**
	 * @param busAmt the busAmt to set
	 */
	public void setBusAmt(String busAmt) {
		this.busAmt = busAmt;
	}

	/**
	 * @return the mchtCreLvl
	 */
	public String getMchtCreLvl() {
		return mchtCreLvl;
	}

	/**
	 * @param mchtCreLvl the mchtCreLvl to set
	 */
	public void setMchtCreLvl(String mchtCreLvl) {
		this.mchtCreLvl = mchtCreLvl;
	}

	/**
	 * @return the manager
	 */
	public String getManager() {
		return manager;
	}

	/**
	 * @param manager the manager to set
	 */
	public void setManager(String manager) {
		this.manager = manager;
	}

	/**
	 * @return the artifCertifTp
	 */
	public String getArtifCertifTp() {
		return artifCertifTp;
	}

	/**
	 * @param artifCertifTp the artifCertifTp to set
	 */
	public void setArtifCertifTp(String artifCertifTp) {
		this.artifCertifTp = artifCertifTp;
	}

	/**
	 * @return the identityNo
	 */
	public String getIdentityNo() {
		return identityNo;
	}

	/**
	 * @param identityNo the identityNo to set
	 */
	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}

	/**
	 * @return the managerTel
	 */
	public String getManagerTel() {
		return managerTel;
	}

	/**
	 * @param managerTel the managerTel to set
	 */
	public void setManagerTel(String managerTel) {
		this.managerTel = managerTel;
	}

	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @return the regAddr
	 */
	public String getRegAddr() {
		return regAddr;
	}

	/**
	 * @param regAddr the regAddr to set
	 */
	public void setRegAddr(String regAddr) {
		this.regAddr = regAddr;
	}

	/**
	 * @return the applyDate
	 */
	public String getApplyDate() {
		return applyDate;
	}

	/**
	 * @param applyDate the applyDate to set
	 */
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	/**
	 * @return the enableDate
	 */
	public String getEnableDate() {
		return enableDate;
	}

	/**
	 * @param enableDate the enableDate to set
	 */
	public void setEnableDate(String enableDate) {
		this.enableDate = enableDate;
	}

	/**
	 * @return the preAudNm
	 */
	public String getPreAudNm() {
		return preAudNm;
	}

	/**
	 * @param preAudNm the preAudNm to set
	 */
	public void setPreAudNm(String preAudNm) {
		this.preAudNm = preAudNm;
	}

	/**
	 * @return the confirmNm
	 */
	public String getConfirmNm() {
		return confirmNm;
	}

	/**
	 * @param confirmNm the confirmNm to set
	 */
	public void setConfirmNm(String confirmNm) {
		this.confirmNm = confirmNm;
	}

	/**
	 * @return the reserved
	 */
	public String getReserved() {
		return reserved;
	}

	/**
	 * @param reserved the reserved to set
	 */
	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

	/**
	 * @return the reservedSnd
	 */
	public String getReservedSnd() {
		return reservedSnd;
	}

	/**
	 * @param reservedSnd the reservedSnd to set
	 */
	public void setReservedSnd(String reservedSnd) {
		this.reservedSnd = reservedSnd;
	}

	/**
	 * @return the etpsState
	 */
	public String getEtpsState() {
		return etpsState;
	}

	/**
	 * @param etpsState the etpsState to set
	 */
	public void setEtpsState(String etpsState) {
		this.etpsState = etpsState;
	}

	/**
	 * @return the refuseMsg1
	 */
	public String getRefuseMsg1() {
		return refuseMsg1;
	}

	/**
	 * @param refuseMsg1 the refuseMsg1 to set
	 */
	public void setRefuseMsg1(String refuseMsg1) {
		this.refuseMsg1 = refuseMsg1;
	}

	/**
	 * @return the refuseMsg2
	 */
	public String getRefuseMsg2() {
		return refuseMsg2;
	}

	/**
	 * @param refuseMsg2 the refuseMsg2 to set
	 */
	public void setRefuseMsg2(String refuseMsg2) {
		this.refuseMsg2 = refuseMsg2;
	}

	/**
	 * @return the updOprId
	 */
	public String getUpdOprId() {
		return updOprId;
	}

	/**
	 * @param updOprId the updOprId to set
	 */
	public void setUpdOprId(String updOprId) {
		this.updOprId = updOprId;
	}

	/**
	 * @return the crtOprId
	 */
	public String getCrtOprId() {
		return crtOprId;
	}

	/**
	 * @param crtOprId the crtOprId to set
	 */
	public void setCrtOprId(String crtOprId) {
		this.crtOprId = crtOprId;
	}

	/**
	 * @return the recUpdTs
	 */
	public String getRecUpdTs() {
		return recUpdTs;
	}

	/**
	 * @param recUpdTs the recUpdTs to set
	 */
	public void setRecUpdTs(String recUpdTs) {
		this.recUpdTs = recUpdTs;
	}

	/**
	 * @return the recCrtTs
	 */
	public String getRecCrtTs() {
		return recCrtTs;
	}

	/**
	 * @param recCrtTs the recCrtTs to set
	 */
	public void setRecCrtTs(String recCrtTs) {
		this.recCrtTs = recCrtTs;
	}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof TblMchtBaseInfTmp)) return false;
		else {
			TblMchtLicenceInfTmp tblMchtLicenceInfTmp = (TblMchtLicenceInfTmp) obj;
			if (null == this.getLicenceNo() || null == tblMchtLicenceInfTmp.getLicenceNo()) return false;
			else return (this.getLicenceNo().equals(tblMchtLicenceInfTmp.getLicenceNo()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getLicenceNo()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getLicenceNo().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}

	public String toString () {
		return super.toString();
	}

}