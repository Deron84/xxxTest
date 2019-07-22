package com.huateng.po.mchtSrv;

public class TblProfessionalOrgan {

	private static final long serialVersionUID = -5292218668667373453L;

	public static String REF = "TblProfessionalOrgan";

	public static String PROP_LAST_UPD_TS = "LastUpdTs";

	public static String PROP_BRANCH = "Branch";

	public static String PROP_CRT_OPR = "CrtOpr";

	public static String PROP_MISC = "Misc";

	public static String PROP_ORG_ID = "OrgId";

	public static String PROP_CRT_TS = "CrtTs";

	public static String PROP_LAST_UPD_OPR = "LastUpdOpr";

	public static String PROP_REMARKS = "Remarks";

	public static String PROP_ORG_NAME = "OrgName";

//	public static String PROP_RATE_TYPE = "RateType";

	public static String PROP_RATE = "Rate";

	public static String AREA_CODE = "areaCode";

	// constructors
	public TblProfessionalOrgan() {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public TblProfessionalOrgan(java.lang.String orgId) {
		this.setOrgId(orgId);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public TblProfessionalOrgan(java.lang.String orgId,
			java.lang.String branch, java.lang.String orgName,
			java.math.BigDecimal rate) {

		this.setOrgId(orgId);
		this.setBranch(branch);
		this.setOrgName(orgName);
		this.setRate(rate);
		initialize();
	}

	protected void initialize() {
	}

	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String orgId;

	// fields
	private java.lang.String branch;

	private java.lang.String orgName;
	
	private java.lang.String principal;
	private java.lang.String linkman;
	private java.lang.String linkTel;

	private java.math.BigDecimal rate;

//	private java.lang.String rateType;

	private java.lang.String remarks;

	private java.lang.String misc;

	private java.lang.String crtOpr;

	private java.lang.String crtTs;

	private java.lang.String lastUpdOpr;

	private java.lang.String lastUpdTs;

	// 地区码
	private String areaCode;

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**
	 * Return the unique identifier of this class
	 * 
	 * @hibernate.id generator-class="assigned" column="ORG_ID"
	 */
	public java.lang.String getOrgId() {
		return orgId;
	}

	/**
	 * Set the unique identifier of this class
	 * 
	 * @param orgId
	 *            the new ID
	 */
	public void setOrgId(java.lang.String orgId) {
		this.orgId = orgId;
		this.hashCode = Integer.MIN_VALUE;
	}

	/**
	 * Return the value associated with the column: BRANCH
	 */
	public java.lang.String getBranch() {
		return branch;
	}

	/**
	 * Set the value related to the column: BRANCH
	 * 
	 * @param branch
	 *            the BRANCH value
	 */
	public void setBranch(java.lang.String branch) {
		this.branch = branch;
	}

	/**
	 * Return the value associated with the column: ORG_NAME
	 */
	public java.lang.String getOrgName() {
		return orgName;
	}

	/**
	 * Set the value related to the column: ORG_NAME
	 * 
	 * @param orgName
	 *            the ORG_NAME value
	 */
	public void setOrgName(java.lang.String orgName) {
		this.orgName = orgName;
	}

	/**
	 * Return the value associated with the column: RATE
	 */
	public java.math.BigDecimal getRate() {
		return rate;
	}

	/**
	 * Set the value related to the column: RATE
	 * 
	 * @param rate
	 *            the RATE value
	 */
	public void setRate(java.math.BigDecimal rate) {
		this.rate = rate;
	}

	

	public java.lang.String getPrincipal() {
		return principal;
	}

	public void setPrincipal(java.lang.String principal) {
		this.principal = principal;
	}

	public java.lang.String getLinkman() {
		return linkman;
	}

	public void setLinkman(java.lang.String linkman) {
		this.linkman = linkman;
	}

	public java.lang.String getLinkTel() {
		return linkTel;
	}

	public void setLinkTel(java.lang.String linkTel) {
		this.linkTel = linkTel;
	}

	/**
	 * Return the value associated with the column: REMARKS
	 */
	public java.lang.String getRemarks() {
		return remarks;
	}

	/**
	 * Set the value related to the column: REMARKS
	 * 
	 * @param remarks
	 *            the REMARKS value
	 */
	public void setRemarks(java.lang.String remarks) {
		this.remarks = remarks;
	}

	/**
	 * Return the value associated with the column: MISC
	 */
	public java.lang.String getMisc() {
		return misc;
	}

	/**
	 * Set the value related to the column: MISC
	 * 
	 * @param misc
	 *            the MISC value
	 */
	public void setMisc(java.lang.String misc) {
		this.misc = misc;
	}

	/**
	 * Return the value associated with the column: CRT_OPR
	 */
	public java.lang.String getCrtOpr() {
		return crtOpr;
	}

	/**
	 * Set the value related to the column: CRT_OPR
	 * 
	 * @param crtOpr
	 *            the CRT_OPR value
	 */
	public void setCrtOpr(java.lang.String crtOpr) {
		this.crtOpr = crtOpr;
	}

	/**
	 * Return the value associated with the column: CRT_TS
	 */
	public java.lang.String getCrtTs() {
		return crtTs;
	}

	/**
	 * Set the value related to the column: CRT_TS
	 * 
	 * @param crtTs
	 *            the CRT_TS value
	 */
	public void setCrtTs(java.lang.String crtTs) {
		this.crtTs = crtTs;
	}

	/**
	 * Return the value associated with the column: LAST_UPD_OPR
	 */
	public java.lang.String getLastUpdOpr() {
		return lastUpdOpr;
	}

	/**
	 * Set the value related to the column: LAST_UPD_OPR
	 * 
	 * @param lastUpdOpr
	 *            the LAST_UPD_OPR value
	 */
	public void setLastUpdOpr(java.lang.String lastUpdOpr) {
		this.lastUpdOpr = lastUpdOpr;
	}

	/**
	 * Return the value associated with the column: LAST_UPD_TS
	 */
	public java.lang.String getLastUpdTs() {
		return lastUpdTs;
	}

	/**
	 * Set the value related to the column: LAST_UPD_TS
	 * 
	 * @param lastUpdTs
	 *            the LAST_UPD_TS value
	 */
	public void setLastUpdTs(java.lang.String lastUpdTs) {
		this.lastUpdTs = lastUpdTs;
	}

	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof com.huateng.po.mchtSrv.TblProfessionalOrgan))
			return false;
		else {
			com.huateng.po.mchtSrv.TblProfessionalOrgan tblProfessionalOrgan = (com.huateng.po.mchtSrv.TblProfessionalOrgan) obj;
			if (null == this.getOrgId()
					|| null == tblProfessionalOrgan.getOrgId())
				return false;
			else
				return (this.getOrgId().equals(tblProfessionalOrgan.getOrgId()));
		}
	}

	public int hashCode() {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getOrgId())
				return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":"
						+ this.getOrgId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}

	public String toString() {
		return super.toString();
	}
}