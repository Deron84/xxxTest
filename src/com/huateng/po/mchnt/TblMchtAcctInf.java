package com.huateng.po.mchnt;

import java.io.Serializable;


/**
 * This is an object that contains data related to the tbl_mcht_acct_inf table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="tbl_mcht_acct_inf"
 */

@SuppressWarnings("serial")
public class TblMchtAcctInf  implements Serializable {

	public static String REF = "TblMchtAcctInf";
	public static String PROP_MCHT_STLM_DTL_FLG = "MchtStlmDtlFlg";
	public static String PROP_MCHT_STLM_INS_ID = "MchtStlmInsId";
	public static String PROP_MCHT_HOLI_FLG = "MchtHoliFlg";
	public static String PROP_MCHT_FE_STLM_MD = "MchtFeStlmMd";
	public static String PROP_MCHT_STLM_D_NM = "MchtStlmDNm";
	public static String PROP_MCHT_TYPE = "MchtType";
	public static String PROP_MCHT_ALO_STLM_CY = "MchtAloStlmCy";
	public static String PROP_MCHT_FE_STLM_CYCLE = "MchtFeStlmCycle";
	public static String PROP_MCHT_STLM_C_NM = "MchtStlmCNm";
	public static String PROP_REC_CRT_TS = "RecCrtTs";
	public static String PROP_MCHT_STLM_CHNL = "MchtStlmChnl";
	public static String PROP_MCHT_STLM_STYLE = "MchtStlmStyle";
	public static String PROP_MCHT_ALO_STLM_MD = "MchtAloStlmMd";
	public static String PROP_MCHT_CURRCY_CD = "MchtCurrcyCd";
	public static String PROP_MCHT_ACC_DT = "MchtAccDt";
	public static String PROP_REC_UPD_OPR = "RecUpdOpr";
	public static String PROP_MCHT_OUT_IN_MD = "MchtOutInMd";
	public static String PROP_MCHT_STLM_D_ACCT = "MchtStlmDAcct";
	public static String PROP_CHK_SND_CYCLE = "ChkSndCycle";
	public static String PROP_MCHT_CITY_TRAN = "MchtCityTran";
	public static String PROP_MCHT_STLM_MD = "MchtStlmMd";
	public static String PROP_MCHT_STLM_C_ACCT = "MchtStlmCAcct";
	public static String PROP_MCHT_CD = "MchtCd";
	public static String PROP_REC_OPR_ID = "RecOprId";
	public static String PROP_STLM_GROUP_FLG = "StlmGroupFlg";
	public static String PROP_MCHT_STLM_INS_NM = "MchtStlmInsNm";
	public static String PROP_MCHT_STLM_WAY = "MchtStlmWay";
	public static String PROP_REC_UPD_TS = "RecUpdTs";
	public static String PROP_ACQ_INS_ID_CD = "AcqInsIdCd";
	public static String PROP_MCHT_SHORT_CN = "MchtShortCn";
	public static String PROP_MCHT_CP_STLM_MD = "MchtCpStlmMd";
	public static String PROP_MCHT_PAY_SYS_ACCT = "MchtPaySysAcct";
	public static String PROP_CHK_SND_MD = "ChkSndMd";
	public static String PROP_MCHT_CP_STLM_CYCLE = "MchtCpStlmCycle";


	// constructors
	public TblMchtAcctInf () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public TblMchtAcctInf (java.lang.String mchtCd) {
		this.setMchtCd(mchtCd);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public TblMchtAcctInf (
		java.lang.String mchtCd,
		java.lang.String stlmGroupFlg,
		java.lang.String acqInsIdCd) {

		this.setMchtCd(mchtCd);
		this.setStlmGroupFlg(stlmGroupFlg);
		this.setAcqInsIdCd(acqInsIdCd);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String mchtCd;

	// fields
	private java.lang.String mchtType;
	private java.lang.String mchtStlmMd;
	private java.lang.String mchtStlmChnl;
	private java.lang.String mchtCpStlmCycle;
	private java.lang.String mchtCpStlmMd;
	private java.lang.String mchtFeStlmCycle;
	private java.lang.String mchtFeStlmMd;
	private java.lang.String mchtAloStlmCy;
	private java.lang.String mchtAloStlmMd;
	private java.lang.String mchtStlmInsId;
	private java.lang.String mchtStlmInsNm;
	private java.lang.String mchtOutInMd;
	private java.lang.String mchtStlmCNm;
	private java.lang.String mchtStlmCAcct;
	private java.lang.String mchtStlmDNm;
	private java.lang.String mchtStlmDAcct;
	private java.lang.String mchtCityTran;
	private java.lang.String mchtPaySysAcct;
	private java.lang.String mchtCurrcyCd;
	private java.lang.String mchtStlmWay;
	private java.lang.String stlmGroupFlg;
	private java.lang.String mchtHoliFlg;
	private java.lang.String mchtAccDt;
	private java.lang.String chkSndCycle;
	private java.lang.String chkSndMd;
	private java.lang.String mchtStlmDtlFlg;
	private java.lang.String mchtStlmStyle;
	private java.lang.String mchtShortCn;
	private java.lang.String acqInsIdCd;
	private java.lang.String recOprId;
	private java.lang.String recUpdOpr;
	private java.lang.String recCrtTs;
	private java.lang.String recUpdTs;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="mcht_cd"
     */
	public java.lang.String getMchtCd () {
		return mchtCd;
	}

	/**
	 * Set the unique identifier of this class
	 * @param mchtCd the new ID
	 */
	public void setMchtCd (java.lang.String mchtCd) {
		this.mchtCd = mchtCd;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: mcht_type
	 */
	public java.lang.String getMchtType () {
		return mchtType;
	}

	/**
	 * Set the value related to the column: mcht_type
	 * @param mchtType the mcht_type value
	 */
	public void setMchtType (java.lang.String mchtType) {
		this.mchtType = mchtType;
	}



	/**
	 * Return the value associated with the column: mcht_stlm_md
	 */
	public java.lang.String getMchtStlmMd () {
		return mchtStlmMd;
	}

	/**
	 * Set the value related to the column: mcht_stlm_md
	 * @param mchtStlmMd the mcht_stlm_md value
	 */
	public void setMchtStlmMd (java.lang.String mchtStlmMd) {
		this.mchtStlmMd = mchtStlmMd;
	}



	/**
	 * Return the value associated with the column: mcht_stlm_chnl
	 */
	public java.lang.String getMchtStlmChnl () {
		return mchtStlmChnl;
	}

	/**
	 * Set the value related to the column: mcht_stlm_chnl
	 * @param mchtStlmChnl the mcht_stlm_chnl value
	 */
	public void setMchtStlmChnl (java.lang.String mchtStlmChnl) {
		this.mchtStlmChnl = mchtStlmChnl;
	}



	/**
	 * Return the value associated with the column: mcht_cp_stlm_cycle
	 */
	public java.lang.String getMchtCpStlmCycle () {
		return mchtCpStlmCycle;
	}

	/**
	 * Set the value related to the column: mcht_cp_stlm_cycle
	 * @param mchtCpStlmCycle the mcht_cp_stlm_cycle value
	 */
	public void setMchtCpStlmCycle (java.lang.String mchtCpStlmCycle) {
		this.mchtCpStlmCycle = mchtCpStlmCycle;
	}



	/**
	 * Return the value associated with the column: mcht_cp_stlm_md
	 */
	public java.lang.String getMchtCpStlmMd () {
		return mchtCpStlmMd;
	}

	/**
	 * Set the value related to the column: mcht_cp_stlm_md
	 * @param mchtCpStlmMd the mcht_cp_stlm_md value
	 */
	public void setMchtCpStlmMd (java.lang.String mchtCpStlmMd) {
		this.mchtCpStlmMd = mchtCpStlmMd;
	}



	/**
	 * Return the value associated with the column: mcht_fe_stlm_cycle
	 */
	public java.lang.String getMchtFeStlmCycle () {
		return mchtFeStlmCycle;
	}

	/**
	 * Set the value related to the column: mcht_fe_stlm_cycle
	 * @param mchtFeStlmCycle the mcht_fe_stlm_cycle value
	 */
	public void setMchtFeStlmCycle (java.lang.String mchtFeStlmCycle) {
		this.mchtFeStlmCycle = mchtFeStlmCycle;
	}



	/**
	 * Return the value associated with the column: mcht_fe_stlm_md
	 */
	public java.lang.String getMchtFeStlmMd () {
		return mchtFeStlmMd;
	}

	/**
	 * Set the value related to the column: mcht_fe_stlm_md
	 * @param mchtFeStlmMd the mcht_fe_stlm_md value
	 */
	public void setMchtFeStlmMd (java.lang.String mchtFeStlmMd) {
		this.mchtFeStlmMd = mchtFeStlmMd;
	}



	/**
	 * Return the value associated with the column: mcht_alo_stlm_cy
	 */
	public java.lang.String getMchtAloStlmCy () {
		return mchtAloStlmCy;
	}

	/**
	 * Set the value related to the column: mcht_alo_stlm_cy
	 * @param mchtAloStlmCy the mcht_alo_stlm_cy value
	 */
	public void setMchtAloStlmCy (java.lang.String mchtAloStlmCy) {
		this.mchtAloStlmCy = mchtAloStlmCy;
	}



	/**
	 * Return the value associated with the column: mcht_alo_stlm_md
	 */
	public java.lang.String getMchtAloStlmMd () {
		return mchtAloStlmMd;
	}

	/**
	 * Set the value related to the column: mcht_alo_stlm_md
	 * @param mchtAloStlmMd the mcht_alo_stlm_md value
	 */
	public void setMchtAloStlmMd (java.lang.String mchtAloStlmMd) {
		this.mchtAloStlmMd = mchtAloStlmMd;
	}



	/**
	 * Return the value associated with the column: mcht_stlm_ins_id
	 */
	public java.lang.String getMchtStlmInsId () {
		return mchtStlmInsId;
	}

	/**
	 * Set the value related to the column: mcht_stlm_ins_id
	 * @param mchtStlmInsId the mcht_stlm_ins_id value
	 */
	public void setMchtStlmInsId (java.lang.String mchtStlmInsId) {
		this.mchtStlmInsId = mchtStlmInsId;
	}



	/**
	 * Return the value associated with the column: mcht_stlm_ins_nm
	 */
	public java.lang.String getMchtStlmInsNm () {
		return mchtStlmInsNm;
	}

	/**
	 * Set the value related to the column: mcht_stlm_ins_nm
	 * @param mchtStlmInsNm the mcht_stlm_ins_nm value
	 */
	public void setMchtStlmInsNm (java.lang.String mchtStlmInsNm) {
		this.mchtStlmInsNm = mchtStlmInsNm;
	}



	/**
	 * Return the value associated with the column: mcht_out_in_md
	 */
	public java.lang.String getMchtOutInMd () {
		return mchtOutInMd;
	}

	/**
	 * Set the value related to the column: mcht_out_in_md
	 * @param mchtOutInMd the mcht_out_in_md value
	 */
	public void setMchtOutInMd (java.lang.String mchtOutInMd) {
		this.mchtOutInMd = mchtOutInMd;
	}



	/**
	 * Return the value associated with the column: mcht_stlm_c_nm
	 */
	public java.lang.String getMchtStlmCNm () {
		return mchtStlmCNm;
	}

	/**
	 * Set the value related to the column: mcht_stlm_c_nm
	 * @param mchtStlmCNm the mcht_stlm_c_nm value
	 */
	public void setMchtStlmCNm (java.lang.String mchtStlmCNm) {
		this.mchtStlmCNm = mchtStlmCNm;
	}



	/**
	 * Return the value associated with the column: mcht_stlm_c_acct
	 */
	public java.lang.String getMchtStlmCAcct () {
		return mchtStlmCAcct;
	}

	/**
	 * Set the value related to the column: mcht_stlm_c_acct
	 * @param mchtStlmCAcct the mcht_stlm_c_acct value
	 */
	public void setMchtStlmCAcct (java.lang.String mchtStlmCAcct) {
		this.mchtStlmCAcct = mchtStlmCAcct;
	}



	/**
	 * Return the value associated with the column: mcht_stlm_d_nm
	 */
	public java.lang.String getMchtStlmDNm () {
		return mchtStlmDNm;
	}

	/**
	 * Set the value related to the column: mcht_stlm_d_nm
	 * @param mchtStlmDNm the mcht_stlm_d_nm value
	 */
	public void setMchtStlmDNm (java.lang.String mchtStlmDNm) {
		this.mchtStlmDNm = mchtStlmDNm;
	}



	/**
	 * Return the value associated with the column: mcht_stlm_d_acct
	 */
	public java.lang.String getMchtStlmDAcct () {
		return mchtStlmDAcct;
	}

	/**
	 * Set the value related to the column: mcht_stlm_d_acct
	 * @param mchtStlmDAcct the mcht_stlm_d_acct value
	 */
	public void setMchtStlmDAcct (java.lang.String mchtStlmDAcct) {
		this.mchtStlmDAcct = mchtStlmDAcct;
	}



	/**
	 * Return the value associated with the column: mcht_city_tran
	 */
	public java.lang.String getMchtCityTran () {
		return mchtCityTran;
	}

	/**
	 * Set the value related to the column: mcht_city_tran
	 * @param mchtCityTran the mcht_city_tran value
	 */
	public void setMchtCityTran (java.lang.String mchtCityTran) {
		this.mchtCityTran = mchtCityTran;
	}



	/**
	 * Return the value associated with the column: mcht_pay_sys_acct
	 */
	public java.lang.String getMchtPaySysAcct () {
		return mchtPaySysAcct;
	}

	/**
	 * Set the value related to the column: mcht_pay_sys_acct
	 * @param mchtPaySysAcct the mcht_pay_sys_acct value
	 */
	public void setMchtPaySysAcct (java.lang.String mchtPaySysAcct) {
		this.mchtPaySysAcct = mchtPaySysAcct;
	}



	/**
	 * Return the value associated with the column: mcht_currcy_cd
	 */
	public java.lang.String getMchtCurrcyCd () {
		return mchtCurrcyCd;
	}

	/**
	 * Set the value related to the column: mcht_currcy_cd
	 * @param mchtCurrcyCd the mcht_currcy_cd value
	 */
	public void setMchtCurrcyCd (java.lang.String mchtCurrcyCd) {
		this.mchtCurrcyCd = mchtCurrcyCd;
	}



	/**
	 * Return the value associated with the column: mcht_stlm_way
	 */
	public java.lang.String getMchtStlmWay () {
		return mchtStlmWay;
	}

	/**
	 * Set the value related to the column: mcht_stlm_way
	 * @param mchtStlmWay the mcht_stlm_way value
	 */
	public void setMchtStlmWay (java.lang.String mchtStlmWay) {
		this.mchtStlmWay = mchtStlmWay;
	}



	/**
	 * Return the value associated with the column: stlm_group_flg
	 */
	public java.lang.String getStlmGroupFlg () {
		return stlmGroupFlg;
	}

	/**
	 * Set the value related to the column: stlm_group_flg
	 * @param stlmGroupFlg the stlm_group_flg value
	 */
	public void setStlmGroupFlg (java.lang.String stlmGroupFlg) {
		this.stlmGroupFlg = stlmGroupFlg;
	}



	/**
	 * Return the value associated with the column: mcht_holi_flg
	 */
	public java.lang.String getMchtHoliFlg () {
		return mchtHoliFlg;
	}

	/**
	 * Set the value related to the column: mcht_holi_flg
	 * @param mchtHoliFlg the mcht_holi_flg value
	 */
	public void setMchtHoliFlg (java.lang.String mchtHoliFlg) {
		this.mchtHoliFlg = mchtHoliFlg;
	}



	/**
	 * Return the value associated with the column: mcht_acc_dt
	 */
	public java.lang.String getMchtAccDt () {
		return mchtAccDt;
	}

	/**
	 * Set the value related to the column: mcht_acc_dt
	 * @param mchtAccDt the mcht_acc_dt value
	 */
	public void setMchtAccDt (java.lang.String mchtAccDt) {
		this.mchtAccDt = mchtAccDt;
	}



	/**
	 * Return the value associated with the column: chk_snd_cycle
	 */
	public java.lang.String getChkSndCycle () {
		return chkSndCycle;
	}

	/**
	 * Set the value related to the column: chk_snd_cycle
	 * @param chkSndCycle the chk_snd_cycle value
	 */
	public void setChkSndCycle (java.lang.String chkSndCycle) {
		this.chkSndCycle = chkSndCycle;
	}



	/**
	 * Return the value associated with the column: chk_snd_md
	 */
	public java.lang.String getChkSndMd () {
		return chkSndMd;
	}

	/**
	 * Set the value related to the column: chk_snd_md
	 * @param chkSndMd the chk_snd_md value
	 */
	public void setChkSndMd (java.lang.String chkSndMd) {
		this.chkSndMd = chkSndMd;
	}



	/**
	 * Return the value associated with the column: mcht_stlm_dtl_flg
	 */
	public java.lang.String getMchtStlmDtlFlg () {
		return mchtStlmDtlFlg;
	}

	/**
	 * Set the value related to the column: mcht_stlm_dtl_flg
	 * @param mchtStlmDtlFlg the mcht_stlm_dtl_flg value
	 */
	public void setMchtStlmDtlFlg (java.lang.String mchtStlmDtlFlg) {
		this.mchtStlmDtlFlg = mchtStlmDtlFlg;
	}



	/**
	 * Return the value associated with the column: mcht_stlm_style
	 */
	public java.lang.String getMchtStlmStyle () {
		return mchtStlmStyle;
	}

	/**
	 * Set the value related to the column: mcht_stlm_style
	 * @param mchtStlmStyle the mcht_stlm_style value
	 */
	public void setMchtStlmStyle (java.lang.String mchtStlmStyle) {
		this.mchtStlmStyle = mchtStlmStyle;
	}



	/**
	 * Return the value associated with the column: mcht_short_cn
	 */
	public java.lang.String getMchtShortCn () {
		return mchtShortCn;
	}

	/**
	 * Set the value related to the column: mcht_short_cn
	 * @param mchtShortCn the mcht_short_cn value
	 */
	public void setMchtShortCn (java.lang.String mchtShortCn) {
		this.mchtShortCn = mchtShortCn;
	}



	/**
	 * Return the value associated with the column: acq_ins_id_cd
	 */
	public java.lang.String getAcqInsIdCd () {
		return acqInsIdCd;
	}

	/**
	 * Set the value related to the column: acq_ins_id_cd
	 * @param acqInsIdCd the acq_ins_id_cd value
	 */
	public void setAcqInsIdCd (java.lang.String acqInsIdCd) {
		this.acqInsIdCd = acqInsIdCd;
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
		if (!(obj instanceof TblMchtAcctInf)) return false;
		else {
			TblMchtAcctInf tblMchtAcctInf = (TblMchtAcctInf) obj;
			if (null == this.getMchtCd() || null == tblMchtAcctInf.getMchtCd()) return false;
			else return (this.getMchtCd().equals(tblMchtAcctInf.getMchtCd()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getMchtCd()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getMchtCd().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}