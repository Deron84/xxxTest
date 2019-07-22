package com.huateng.po.mchnt.base;

import java.io.Serializable;

import com.huateng.po.mchnt.TblMchtNetTmp;


/**
 * This is an object that contains data related to the tbl_mcht_net_tmp table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="tbl_mcht_net_tmp"
 */

@SuppressWarnings("serial")
public abstract class BaseTblMchtNetTmp  implements Serializable {

	public static String REF = "TblMchtNetTmp";
	public static String PROP_BRH_ID = "BrhId";
	public static String PROP_CERT_NO = "CertNo";
	public static String PROP_STATUS = "Status";
	public static String PROP_LEGAL_NO = "LegalNo";
	public static String PROP_LAST_UPD_TIME = "LastUpdTime";
	public static String PROP_LEGAL_NM = "LegalNm";
	public static String PROP_MCHT_ID = "MchtId";
	public static String PROP_LAST_OPR_ID = "LastOprId";
	public static String PROP_CREATE_TIME = "CreateTime";
	public static String PROP_ORG_URL = "OrgUrl";
	public static String PROP_LEGAL_URL = "LegalUrl";
	public static String PROP_ID = "Id";
	public static String PROP_MANAGER_NO = "ManagerNo";
	public static String PROP_CERT_URL = "CertUrl";
	public static String PROP_TAX_URL = "TaxUrl";
	public static String PROP_REJECT = "Reject";
	public static String PROP_OPER_NO = "OperNo";
	public static String PROP_OPER_NM = "OperNm";
	public static String PROP_OPER_PH = "OperPh";

	public java.lang.String getMchtNm() {
		return mchtNm;
	}

	public void setMchtNm(java.lang.String mchtNm) {
		this.mchtNm = mchtNm;
	}

	// constructors
	public BaseTblMchtNetTmp () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTblMchtNetTmp (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String brhId;
	private java.lang.String mchtId;
	private java.lang.String status;
	private java.lang.String certNo;
	private java.lang.String managerNo;
	private java.lang.String legalNo;
	private java.lang.String legalNm;
	private java.lang.String certUrl;
	private java.lang.String orgUrl;
	private java.lang.String taxUrl;
	private java.lang.String legalUrl;
	private java.lang.String createTime;
	private java.lang.String lastUpdTime;
	private java.lang.String lastOprId;
	private java.lang.String mchtNm;
	private java.lang.String reject;
	private java.lang.String operNo;
	private java.lang.String operNm;
	private java.lang.String operPh;


	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="id"
     */
	public java.lang.String getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.String id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: brh_id
	 */
	public java.lang.String getBrhId () {
		return brhId;
	}

	/**
	 * Set the value related to the column: brh_id
	 * @param brhId the brh_id value
	 */
	public void setBrhId (java.lang.String brhId) {
		this.brhId = brhId;
	}



	/**
	 * Return the value associated with the column: mcht_id
	 */
	public java.lang.String getMchtId () {
		return mchtId;
	}

	/**
	 * Set the value related to the column: mcht_id
	 * @param mchtId the mcht_id value
	 */
	public void setMchtId (java.lang.String mchtId) {
		this.mchtId = mchtId;
	}



	/**
	 * Return the value associated with the column: status
	 */
	public java.lang.String getStatus () {
		return status;
	}

	/**
	 * Set the value related to the column: status
	 * @param status the status value
	 */
	public void setStatus (java.lang.String status) {
		this.status = status;
	}



	/**
	 * Return the value associated with the column: cert_no
	 */
	public java.lang.String getCertNo () {
		return certNo;
	}

	/**
	 * Set the value related to the column: cert_no
	 * @param certNo the cert_no value
	 */
	public void setCertNo (java.lang.String certNo) {
		this.certNo = certNo;
	}



	/**
	 * Return the value associated with the column: manager_no
	 */
	public java.lang.String getManagerNo () {
		return managerNo;
	}

	/**
	 * Set the value related to the column: manager_no
	 * @param managerNo the manager_no value
	 */
	public void setManagerNo (java.lang.String managerNo) {
		this.managerNo = managerNo;
	}



	/**
	 * Return the value associated with the column: legal_no
	 */
	public java.lang.String getLegalNo () {
		return legalNo;
	}

	/**
	 * Set the value related to the column: legal_no
	 * @param legalNo the legal_no value
	 */
	public void setLegalNo (java.lang.String legalNo) {
		this.legalNo = legalNo;
	}



	/**
	 * Return the value associated with the column: legal_nm
	 */
	public java.lang.String getLegalNm () {
		return legalNm;
	}

	/**
	 * Set the value related to the column: legal_nm
	 * @param legalNm the legal_nm value
	 */
	public void setLegalNm (java.lang.String legalNm) {
		this.legalNm = legalNm;
	}



	/**
	 * Return the value associated with the column: cert_url
	 */
	public java.lang.String getCertUrl () {
		return certUrl;
	}

	/**
	 * Set the value related to the column: cert_url
	 * @param certUrl the cert_url value
	 */
	public void setCertUrl (java.lang.String certUrl) {
		this.certUrl = certUrl;
	}



	/**
	 * Return the value associated with the column: org_url
	 */
	public java.lang.String getOrgUrl () {
		return orgUrl;
	}

	/**
	 * Set the value related to the column: org_url
	 * @param orgUrl the org_url value
	 */
	public void setOrgUrl (java.lang.String orgUrl) {
		this.orgUrl = orgUrl;
	}



	/**
	 * Return the value associated with the column: tax_url
	 */
	public java.lang.String getTaxUrl () {
		return taxUrl;
	}

	/**
	 * Set the value related to the column: tax_url
	 * @param taxUrl the tax_url value
	 */
	public void setTaxUrl (java.lang.String taxUrl) {
		this.taxUrl = taxUrl;
	}



	/**
	 * Return the value associated with the column: legal_url
	 */
	public java.lang.String getLegalUrl () {
		return legalUrl;
	}

	/**
	 * Set the value related to the column: legal_url
	 * @param legalUrl the legal_url value
	 */
	public void setLegalUrl (java.lang.String legalUrl) {
		this.legalUrl = legalUrl;
	}



	/**
	 * Return the value associated with the column: create_time
	 */
	public java.lang.String getCreateTime () {
		return createTime;
	}

	/**
	 * Set the value related to the column: create_time
	 * @param createTime the create_time value
	 */
	public void setCreateTime (java.lang.String createTime) {
		this.createTime = createTime;
	}



	/**
	 * Return the value associated with the column: last_upd_time
	 */
	public java.lang.String getLastUpdTime () {
		return lastUpdTime;
	}

	/**
	 * Set the value related to the column: last_upd_time
	 * @param lastUpdTime the last_upd_time value
	 */
	public void setLastUpdTime (java.lang.String lastUpdTime) {
		this.lastUpdTime = lastUpdTime;
	}



	/**
	 * Return the value associated with the column: last_opr_id
	 */
	public java.lang.String getLastOprId () {
		return lastOprId;
	}

	/**
	 * Set the value related to the column: last_opr_id
	 * @param lastOprId the last_opr_id value
	 */
	public void setLastOprId (java.lang.String lastOprId) {
		this.lastOprId = lastOprId;
	}


	public java.lang.String getReject() {
		return reject;
	}

	public void setReject(java.lang.String reject) {
		this.reject = reject;
	}

	public java.lang.String getOperNo() {
		return operNo;
	}

	public void setOperNo(java.lang.String operNo) {
		this.operNo = operNo;
	}

	public java.lang.String getOperNm() {
		return operNm;
	}

	public void setOperNm(java.lang.String operNm) {
		this.operNm = operNm;
	}

	public java.lang.String getOperPh() {
		return operPh;
	}

	public void setOperPh(java.lang.String operPh) {
		this.operPh = operPh;
	}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof TblMchtNetTmp)) return false;
		else {
			TblMchtNetTmp tblMchtNetTmp = (TblMchtNetTmp) obj;
			if (null == this.getId() || null == tblMchtNetTmp.getId()) return false;
			else return (this.getId().equals(tblMchtNetTmp.getId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}