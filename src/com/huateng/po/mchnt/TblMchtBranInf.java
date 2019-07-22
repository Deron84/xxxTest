package com.huateng.po.mchnt;

import java.io.Serializable;


/**
 * This is an object that contains data related to the tbl_mcht_bran_inf table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="tbl_mcht_bran_inf"
 */

@SuppressWarnings("serial")
public class TblMchtBranInf implements Serializable {

	public static String REF = "TblMchtBranInf";
	public static String PROP_BUS_RANGE = "BusRange";
	public static String PROP_CITY_CD = "CityCd";
	public static String PROP_BRANCH_FAX = "BranchFax";
	public static String PROP_CUST_MOBILE = "CustMobile";
	public static String PROP_BRANCH_ADDR = "BranchAddr";
	public static String PROP_BRANCH_AREA = "BranchArea";
	public static String PROP_REC_CRT_TS = "RecCrtTs";
	public static String PROP_BRANCH_TEL = "BranchTel";
	public static String PROP_CUST_TEL = "CustTel";
	public static String PROP_REC_UPD_OPR = "RecUpdOpr";
	public static String PROP_BRANCH_SVR_LVL = "BranchSvrLvl";
	public static String PROP_BREAK_MAN = "BreakMan";
	public static String PROP_PROV_CD = "ProvCd";
	public static String PROP_REC_OPR_ID = "RecOprId";
	public static String PROP_BRANCH_NM = "BranchNm";
	public static String PROP_CUST_MNGER = "CustMnger";
	public static String PROP_BRANCH_NM_EN = "BranchNmEn";
	public static String PROP_REC_UPD_TS = "RecUpdTs";
	public static String PROP_HEAD_PROV_PAPER = "HeadProvPaper";
	public static String PROP_SIGN_DATE = "SignDate";
	public static String PROP_BREAK_DATE = "BreakDate";
	public static String PROP_BRANCH_STA = "BranchSta";
	public static String PROP_AREA_CD = "AreaCd";
	public static String PROP_ID = "Id";
	public static String PROP_OPR_NM = "OprNm";
	public static String PROP_BRANCH_CONT_MAN = "BranchContMan";


	// constructors
	public TblMchtBranInf () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public TblMchtBranInf (TblMchtBranInfPK id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private TblMchtBranInfPK id;

	// fields
	private java.lang.String branchNm;
	private java.lang.String branchNmEn;
	private java.lang.String branchAddr;
	private java.lang.String branchArea;
	private java.lang.String branchSvrLvl;
	private java.lang.String branchContMan;
	private java.lang.String branchTel;
	private java.lang.String branchFax;
	private java.lang.String busRange;
	private java.lang.String oprNm;
	private java.lang.String signDate;
	private java.lang.String branchSta;
	private java.lang.String breakDate;
	private java.lang.String breakMan;
	private java.lang.String provCd;
	private java.lang.String cityCd;
	private java.lang.String areaCd;
	private java.lang.String custMnger;
	private java.lang.String custTel;
	private java.lang.String custMobile;
	private java.lang.String headProvPaper;
	private java.lang.String recOprId;
	private java.lang.String recUpdOpr;
	private java.lang.String recCrtTs;
	private java.lang.String recUpdTs;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public TblMchtBranInfPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (TblMchtBranInfPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: branch_nm
	 */
	public java.lang.String getBranchNm () {
		return branchNm;
	}

	/**
	 * Set the value related to the column: branch_nm
	 * @param branchNm the branch_nm value
	 */
	public void setBranchNm (java.lang.String branchNm) {
		this.branchNm = branchNm;
	}



	/**
	 * Return the value associated with the column: branch_nm_en
	 */
	public java.lang.String getBranchNmEn () {
		return branchNmEn;
	}

	/**
	 * Set the value related to the column: branch_nm_en
	 * @param branchNmEn the branch_nm_en value
	 */
	public void setBranchNmEn (java.lang.String branchNmEn) {
		this.branchNmEn = branchNmEn;
	}



	/**
	 * Return the value associated with the column: branch_addr
	 */
	public java.lang.String getBranchAddr () {
		return branchAddr;
	}

	/**
	 * Set the value related to the column: branch_addr
	 * @param branchAddr the branch_addr value
	 */
	public void setBranchAddr (java.lang.String branchAddr) {
		this.branchAddr = branchAddr;
	}



	/**
	 * Return the value associated with the column: branch_area
	 */
	public java.lang.String getBranchArea () {
		return branchArea;
	}

	/**
	 * Set the value related to the column: branch_area
	 * @param branchArea the branch_area value
	 */
	public void setBranchArea (java.lang.String branchArea) {
		this.branchArea = branchArea;
	}



	/**
	 * Return the value associated with the column: branch_svr_lvl
	 */
	public java.lang.String getBranchSvrLvl () {
		return branchSvrLvl;
	}

	/**
	 * Set the value related to the column: branch_svr_lvl
	 * @param branchSvrLvl the branch_svr_lvl value
	 */
	public void setBranchSvrLvl (java.lang.String branchSvrLvl) {
		this.branchSvrLvl = branchSvrLvl;
	}



	/**
	 * Return the value associated with the column: branch_cont_man
	 */
	public java.lang.String getBranchContMan () {
		return branchContMan;
	}

	/**
	 * Set the value related to the column: branch_cont_man
	 * @param branchContMan the branch_cont_man value
	 */
	public void setBranchContMan (java.lang.String branchContMan) {
		this.branchContMan = branchContMan;
	}



	/**
	 * Return the value associated with the column: branch_tel
	 */
	public java.lang.String getBranchTel () {
		return branchTel;
	}

	/**
	 * Set the value related to the column: branch_tel
	 * @param branchTel the branch_tel value
	 */
	public void setBranchTel (java.lang.String branchTel) {
		this.branchTel = branchTel;
	}



	/**
	 * Return the value associated with the column: branch_fax
	 */
	public java.lang.String getBranchFax () {
		return branchFax;
	}

	/**
	 * Set the value related to the column: branch_fax
	 * @param branchFax the branch_fax value
	 */
	public void setBranchFax (java.lang.String branchFax) {
		this.branchFax = branchFax;
	}



	/**
	 * Return the value associated with the column: bus_range
	 */
	public java.lang.String getBusRange () {
		return busRange;
	}

	/**
	 * Set the value related to the column: bus_range
	 * @param busRange the bus_range value
	 */
	public void setBusRange (java.lang.String busRange) {
		this.busRange = busRange;
	}



	/**
	 * Return the value associated with the column: opr_nm
	 */
	public java.lang.String getOprNm () {
		return oprNm;
	}

	/**
	 * Set the value related to the column: opr_nm
	 * @param oprNm the opr_nm value
	 */
	public void setOprNm (java.lang.String oprNm) {
		this.oprNm = oprNm;
	}



	/**
	 * Return the value associated with the column: sign_date
	 */
	public java.lang.String getSignDate () {
		return signDate;
	}

	/**
	 * Set the value related to the column: sign_date
	 * @param signDate the sign_date value
	 */
	public void setSignDate (java.lang.String signDate) {
		this.signDate = signDate;
	}



	/**
	 * Return the value associated with the column: branch_sta
	 */
	public java.lang.String getBranchSta () {
		return branchSta;
	}

	/**
	 * Set the value related to the column: branch_sta
	 * @param branchSta the branch_sta value
	 */
	public void setBranchSta (java.lang.String branchSta) {
		this.branchSta = branchSta;
	}



	/**
	 * Return the value associated with the column: break_date
	 */
	public java.lang.String getBreakDate () {
		return breakDate;
	}

	/**
	 * Set the value related to the column: break_date
	 * @param breakDate the break_date value
	 */
	public void setBreakDate (java.lang.String breakDate) {
		this.breakDate = breakDate;
	}



	/**
	 * Return the value associated with the column: break_man
	 */
	public java.lang.String getBreakMan () {
		return breakMan;
	}

	/**
	 * Set the value related to the column: break_man
	 * @param breakMan the break_man value
	 */
	public void setBreakMan (java.lang.String breakMan) {
		this.breakMan = breakMan;
	}



	/**
	 * Return the value associated with the column: prov_cd
	 */
	public java.lang.String getProvCd () {
		return provCd;
	}

	/**
	 * Set the value related to the column: prov_cd
	 * @param provCd the prov_cd value
	 */
	public void setProvCd (java.lang.String provCd) {
		this.provCd = provCd;
	}



	/**
	 * Return the value associated with the column: city_cd
	 */
	public java.lang.String getCityCd () {
		return cityCd;
	}

	/**
	 * Set the value related to the column: city_cd
	 * @param cityCd the city_cd value
	 */
	public void setCityCd (java.lang.String cityCd) {
		this.cityCd = cityCd;
	}



	/**
	 * Return the value associated with the column: area_cd
	 */
	public java.lang.String getAreaCd () {
		return areaCd;
	}

	/**
	 * Set the value related to the column: area_cd
	 * @param areaCd the area_cd value
	 */
	public void setAreaCd (java.lang.String areaCd) {
		this.areaCd = areaCd;
	}



	/**
	 * Return the value associated with the column: cust_mnger
	 */
	public java.lang.String getCustMnger () {
		return custMnger;
	}

	/**
	 * Set the value related to the column: cust_mnger
	 * @param custMnger the cust_mnger value
	 */
	public void setCustMnger (java.lang.String custMnger) {
		this.custMnger = custMnger;
	}



	/**
	 * Return the value associated with the column: cust_tel
	 */
	public java.lang.String getCustTel () {
		return custTel;
	}

	/**
	 * Set the value related to the column: cust_tel
	 * @param custTel the cust_tel value
	 */
	public void setCustTel (java.lang.String custTel) {
		this.custTel = custTel;
	}



	/**
	 * Return the value associated with the column: cust_mobile
	 */
	public java.lang.String getCustMobile () {
		return custMobile;
	}

	/**
	 * Set the value related to the column: cust_mobile
	 * @param custMobile the cust_mobile value
	 */
	public void setCustMobile (java.lang.String custMobile) {
		this.custMobile = custMobile;
	}



	/**
	 * Return the value associated with the column: head_prov_paper
	 */
	public java.lang.String getHeadProvPaper () {
		return headProvPaper;
	}

	/**
	 * Set the value related to the column: head_prov_paper
	 * @param headProvPaper the head_prov_paper value
	 */
	public void setHeadProvPaper (java.lang.String headProvPaper) {
		this.headProvPaper = headProvPaper;
	}



	/**
	 * Return the value associated with the column: rec_opr_id
	 */
	public java.lang.String getRecOprId () {
		return recOprId;
	}

	/**
	 * Set the value related to the column: rec_opr_id
	 * @param recOprId the rec_opr_id value
	 */
	public void setRecOprId (java.lang.String recOprId) {
		this.recOprId = recOprId;
	}



	/**
	 * Return the value associated with the column: rec_upd_opr
	 */
	public java.lang.String getRecUpdOpr () {
		return recUpdOpr;
	}

	/**
	 * Set the value related to the column: rec_upd_opr
	 * @param recUpdOpr the rec_upd_opr value
	 */
	public void setRecUpdOpr (java.lang.String recUpdOpr) {
		this.recUpdOpr = recUpdOpr;
	}



	/**
	 * Return the value associated with the column: rec_crt_ts
	 */
	public java.lang.String getRecCrtTs () {
		return recCrtTs;
	}

	/**
	 * Set the value related to the column: rec_crt_ts
	 * @param recCrtTs the rec_crt_ts value
	 */
	public void setRecCrtTs (java.lang.String recCrtTs) {
		this.recCrtTs = recCrtTs;
	}



	/**
	 * Return the value associated with the column: rec_upd_ts
	 */
	public java.lang.String getRecUpdTs () {
		return recUpdTs;
	}

	/**
	 * Set the value related to the column: rec_upd_ts
	 * @param recUpdTs the rec_upd_ts value
	 */
	public void setRecUpdTs (java.lang.String recUpdTs) {
		this.recUpdTs = recUpdTs;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof TblMchtBranInf)) return false;
		else {
			TblMchtBranInf tblMchtBranInf = (TblMchtBranInf) obj;
			if (null == this.getId() || null == tblMchtBranInf.getId()) return false;
			else return (this.getId().equals(tblMchtBranInf.getId()));
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