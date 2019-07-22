package com.huateng.po;

import java.io.Serializable;



public class TblMchtCupInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	protected void initialize () {}

	/**
	 * Constructor for primary key
	 */
	public TblMchtCupInfo (java.lang.String mcht_no) {
		this.setMcht_no(mcht_no);
		initialize();
	}

	private int hashCode = Integer.MIN_VALUE;

	private java.lang.String mcht_no;
	private java.lang.String inner_acq_inst_id;
	private java.lang.String acq_inst_id_code;
	private java.lang.String inst_id;
	private java.lang.String mcht_nm;
	private java.lang.String mcht_short_cn ;
	private java.lang.String area_no;
	private java.lang.String inner_stlm_ins_id;
	private java.lang.String mcht_type;
	private java.lang.String mcht_status;
	private java.lang.String acq_area_cd;
	private java.lang.String settle_flg;
	private java.lang.String conn_inst_cd;
	private java.lang.String mchnt_tp_grp;
	private java.lang.String mchnt_srv_tp;
	private java.lang.String real_mcht_tp;
	private java.lang.String settle_area_cd;
	private java.lang.String mcht_acq_curr;
	private java.lang.String conn_type;
	private java.lang.String currcy_cd;
	private java.lang.String deposit_flag;
	private java.lang.String res_pan_flag;
	private java.lang.String res_track_flag;
	private java.lang.String process_flag;
	private java.lang.String cntry_code;
	private java.lang.String ch_store_flag;
	private java.lang.String mcht_tp_reason;
	private java.lang.String mcc_md;
	private java.lang.String rebate_flag;
	private java.lang.String mcht_acq_rebate;
	private java.lang.String rebate_stlm_cd;
	private java.lang.String reason_code;
	private java.lang.String rate_type;
	private java.lang.String fee_cycle;
	private java.lang.String fee_type;
	private java.lang.String limit_flag;
	private java.lang.String fee_rebate;
	private java.lang.String settle_amt;
	private java.lang.String amt_top;
	private java.lang.String amt_bottom;
	private java.lang.String disc_cd;
	private java.lang.String fee_type_m;
	private java.lang.String limit_flag_m;
	private java.lang.String settle_amt_m;
	private java.lang.String amt_top_m;
	private java.lang.String amt_bottom_m;
	private java.lang.String disc_cd_m;
	private java.lang.String fee_rate_type;
	private java.lang.String settle_bank_no;
	private java.lang.String settle_bank_type;
	private java.lang.String advanced_flag;
	private java.lang.String prior_flag;
	private java.lang.String open_stlno;
	private java.lang.String feerate_index;
	private java.lang.String rate_role;
	private java.lang.String rate_disc;
	private java.lang.String cycle_mcht;
	private java.lang.String mac_chk_flag;
	private java.lang.String fee_div_mode;
	private java.lang.String settle_mode;
	private java.lang.String attr_bmp;
	private java.lang.String cycle_settle_type;
	private java.lang.String report_bmp;
	private java.lang.String day_stlm_flag;
	private java.lang.String cup_stlm_flag;
	private java.lang.String adv_ret_flag;
	private java.lang.String mcht_file_flag;
	private java.lang.String fee_act;
	private java.lang.String licence_no;
	private java.lang.String licence_add;
	private java.lang.String  principal;
	private java.lang.String comm_tel;
	private java.lang.String manager;
	private java.lang.String mana_cred_tp;
	private java.lang.String mana_cred_no;
	private java.lang.String capital_sett_cycle;
	private java.lang.String card_in_start_date;
	private java.lang.String settle_acct;
	private java.lang.String mcht_e_nm;
	private java.lang.String settle_bank;
	private java.lang.String rate_no;
	private java.lang.String card_in_settle_bank;
	private java.lang.String fee_spe_type;
	private java.lang.String fee_spe_gra;
	private java.lang.String reserved;
	private java.lang.String reserved1;
	private java.lang.String reserved2;
	private java.lang.String upd_opr_id;
	private java.lang.String upd_ts;
	private java.lang.String crt_opr_id;
	private java.lang.String crt_ts;
	
	
	/**
	 * 
	 */
	public TblMchtCupInfo() {
		super();
	}

	public int getHashCode() {
		return hashCode;
	}

	public void setHashCode(int hashCode) {
		this.hashCode = hashCode;
	}

	public java.lang.String getMcht_no() {
		return mcht_no;
	}

	public void setMcht_no(java.lang.String mcht_no) {
		this.mcht_no = mcht_no;
	}

	public java.lang.String getInner_acq_inst_id() {
		return inner_acq_inst_id;
	}

	public void setInner_acq_inst_id(java.lang.String inner_acq_inst_id) {
		this.inner_acq_inst_id = inner_acq_inst_id;
	}

	public java.lang.String getAcq_inst_id_code() {
		return acq_inst_id_code;
	}

	public void setAcq_inst_id_code(java.lang.String acq_inst_id_code) {
		this.acq_inst_id_code = acq_inst_id_code;
	}

	public java.lang.String getInst_id() {
		return inst_id;
	}

	public void setInst_id(java.lang.String inst_id) {
		this.inst_id = inst_id;
	}

	public java.lang.String getMcht_nm() {
		return mcht_nm;
	}

	public void setMcht_nm(java.lang.String mcht_nm) {
		this.mcht_nm = mcht_nm;
	}

	public java.lang.String getMcht_short_cn() {
		return mcht_short_cn;
	}

	public void setMcht_short_cn(java.lang.String mcht_short_cn) {
		this.mcht_short_cn = mcht_short_cn;
	}

	public java.lang.String getArea_no() {
		return area_no;
	}

	public void setArea_no(java.lang.String area_no) {
		this.area_no = area_no;
	}

	public java.lang.String getInner_stlm_ins_id() {
		return inner_stlm_ins_id;
	}

	public void setInner_stlm_ins_id(java.lang.String inner_stlm_ins_id) {
		this.inner_stlm_ins_id = inner_stlm_ins_id;
	}

	public java.lang.String getMcht_type() {
		return mcht_type;
	}

	public void setMcht_type(java.lang.String mcht_type) {
		this.mcht_type = mcht_type;
	}

	public java.lang.String getMcht_status() {
		return mcht_status;
	}

	public void setMcht_status(java.lang.String mcht_status) {
		this.mcht_status = mcht_status;
	}

	public java.lang.String getAcq_area_cd() {
		return acq_area_cd;
	}

	public void setAcq_area_cd(java.lang.String acq_area_cd) {
		this.acq_area_cd = acq_area_cd;
	}

	public java.lang.String getSettle_flg() {
		return settle_flg;
	}

	public void setSettle_flg(java.lang.String settle_flg) {
		this.settle_flg = settle_flg;
	}

	public java.lang.String getConn_inst_cd() {
		return conn_inst_cd;
	}

	public void setConn_inst_cd(java.lang.String conn_inst_cd) {
		this.conn_inst_cd = conn_inst_cd;
	}

	public java.lang.String getMchnt_tp_grp() {
		return mchnt_tp_grp;
	}

	public void setMchnt_tp_grp(java.lang.String mchnt_tp_grp) {
		this.mchnt_tp_grp = mchnt_tp_grp;
	}

	public java.lang.String getMchnt_srv_tp() {
		return mchnt_srv_tp;
	}

	public void setMchnt_srv_tp(java.lang.String mchnt_srv_tp) {
		this.mchnt_srv_tp = mchnt_srv_tp;
	}

	public java.lang.String getReal_mcht_tp() {
		return real_mcht_tp;
	}

	public void setReal_mcht_tp(java.lang.String real_mcht_tp) {
		this.real_mcht_tp = real_mcht_tp;
	}

	public java.lang.String getSettle_area_cd() {
		return settle_area_cd;
	}

	public void setSettle_area_cd(java.lang.String settle_area_cd) {
		this.settle_area_cd = settle_area_cd;
	}

	public java.lang.String getMcht_acq_curr() {
		return mcht_acq_curr;
	}

	public void setMcht_acq_curr(java.lang.String mcht_acq_curr) {
		this.mcht_acq_curr = mcht_acq_curr;
	}

	public java.lang.String getConn_type() {
		return conn_type;
	}

	public void setConn_type(java.lang.String conn_type) {
		this.conn_type = conn_type;
	}

	public java.lang.String getCurrcy_cd() {
		return currcy_cd;
	}

	public void setCurrcy_cd(java.lang.String currcy_cd) {
		this.currcy_cd = currcy_cd;
	}

	public java.lang.String getDeposit_flag() {
		return deposit_flag;
	}

	public void setDeposit_flag(java.lang.String deposit_flag) {
		this.deposit_flag = deposit_flag;
	}

	public java.lang.String getRes_pan_flag() {
		return res_pan_flag;
	}

	public void setRes_pan_flag(java.lang.String res_pan_flag) {
		this.res_pan_flag = res_pan_flag;
	}

	public java.lang.String getRes_track_flag() {
		return res_track_flag;
	}

	public void setRes_track_flag(java.lang.String res_track_flag) {
		this.res_track_flag = res_track_flag;
	}

	public java.lang.String getProcess_flag() {
		return process_flag;
	}

	public void setProcess_flag(java.lang.String process_flag) {
		this.process_flag = process_flag;
	}

	public java.lang.String getCntry_code() {
		return cntry_code;
	}

	public void setCntry_code(java.lang.String cntry_code) {
		this.cntry_code = cntry_code;
	}

	public java.lang.String getCh_store_flag() {
		return ch_store_flag;
	}

	public void setCh_store_flag(java.lang.String ch_store_flag) {
		this.ch_store_flag = ch_store_flag;
	}

	public java.lang.String getMcht_tp_reason() {
		return mcht_tp_reason;
	}

	public void setMcht_tp_reason(java.lang.String mcht_tp_reason) {
		this.mcht_tp_reason = mcht_tp_reason;
	}

	public java.lang.String getMcc_md() {
		return mcc_md;
	}

	public void setMcc_md(java.lang.String mcc_md) {
		this.mcc_md = mcc_md;
	}

	public java.lang.String getRebate_flag() {
		return rebate_flag;
	}

	public void setRebate_flag(java.lang.String rebate_flag) {
		this.rebate_flag = rebate_flag;
	}

	public java.lang.String getMcht_acq_rebate() {
		return mcht_acq_rebate;
	}

	public void setMcht_acq_rebate(java.lang.String mcht_acq_rebate) {
		this.mcht_acq_rebate = mcht_acq_rebate;
	}

	public java.lang.String getRebate_stlm_cd() {
		return rebate_stlm_cd;
	}

	public void setRebate_stlm_cd(java.lang.String rebate_stlm_cd) {
		this.rebate_stlm_cd = rebate_stlm_cd;
	}

	public java.lang.String getReason_code() {
		return reason_code;
	}

	public void setReason_code(java.lang.String reason_code) {
		this.reason_code = reason_code;
	}

	public java.lang.String getRate_type() {
		return rate_type;
	}

	public void setRate_type(java.lang.String rate_type) {
		this.rate_type = rate_type;
	}

	public java.lang.String getFee_cycle() {
		return fee_cycle;
	}

	public void setFee_cycle(java.lang.String fee_cycle) {
		this.fee_cycle = fee_cycle;
	}

	public java.lang.String getFee_type() {
		return fee_type;
	}

	public void setFee_type(java.lang.String fee_type) {
		this.fee_type = fee_type;
	}

	public java.lang.String getLimit_flag() {
		return limit_flag;
	}

	public void setLimit_flag(java.lang.String limit_flag) {
		this.limit_flag = limit_flag;
	}

	public java.lang.String getFee_rebate() {
		return fee_rebate;
	}

	public void setFee_rebate(java.lang.String fee_rebate) {
		this.fee_rebate = fee_rebate;
	}

	public java.lang.String getSettle_amt() {
		return settle_amt;
	}

	public void setSettle_amt(java.lang.String settle_amt) {
		this.settle_amt = settle_amt;
	}

	public java.lang.String getAmt_top() {
		return amt_top;
	}

	public void setAmt_top(java.lang.String amt_top) {
		this.amt_top = amt_top;
	}

	public java.lang.String getAmt_bottom() {
		return amt_bottom;
	}

	public void setAmt_bottom(java.lang.String amt_bottom) {
		this.amt_bottom = amt_bottom;
	}

	public java.lang.String getDisc_cd() {
		return disc_cd;
	}

	public void setDisc_cd(java.lang.String disc_cd) {
		this.disc_cd = disc_cd;
	}

	public java.lang.String getFee_type_m() {
		return fee_type_m;
	}

	public void setFee_type_m(java.lang.String fee_type_m) {
		this.fee_type_m = fee_type_m;
	}

	public java.lang.String getLimit_flag_m() {
		return limit_flag_m;
	}

	public void setLimit_flag_m(java.lang.String limit_flag_m) {
		this.limit_flag_m = limit_flag_m;
	}

	public java.lang.String getSettle_amt_m() {
		return settle_amt_m;
	}

	public void setSettle_amt_m(java.lang.String settle_amt_m) {
		this.settle_amt_m = settle_amt_m;
	}

	public java.lang.String getAmt_top_m() {
		return amt_top_m;
	}

	public void setAmt_top_m(java.lang.String amt_top_m) {
		this.amt_top_m = amt_top_m;
	}

	public java.lang.String getAmt_bottom_m() {
		return amt_bottom_m;
	}

	public void setAmt_bottom_m(java.lang.String amt_bottom_m) {
		this.amt_bottom_m = amt_bottom_m;
	}

	public java.lang.String getDisc_cd_m() {
		return disc_cd_m;
	}

	public void setDisc_cd_m(java.lang.String disc_cd_m) {
		this.disc_cd_m = disc_cd_m;
	}

	public java.lang.String getFee_rate_type() {
		return fee_rate_type;
	}

	public void setFee_rate_type(java.lang.String fee_rate_type) {
		this.fee_rate_type = fee_rate_type;
	}

	public java.lang.String getSettle_bank_no() {
		return settle_bank_no;
	}

	public void setSettle_bank_no(java.lang.String settle_bank_no) {
		this.settle_bank_no = settle_bank_no;
	}

	public java.lang.String getSettle_bank_type() {
		return settle_bank_type;
	}

	public void setSettle_bank_type(java.lang.String settle_bank_type) {
		this.settle_bank_type = settle_bank_type;
	}

	public java.lang.String getAdvanced_flag() {
		return advanced_flag;
	}

	public void setAdvanced_flag(java.lang.String advanced_flag) {
		this.advanced_flag = advanced_flag;
	}

	public java.lang.String getPrior_flag() {
		return prior_flag;
	}

	public void setPrior_flag(java.lang.String prior_flag) {
		this.prior_flag = prior_flag;
	}

	public java.lang.String getOpen_stlno() {
		return open_stlno;
	}

	public void setOpen_stlno(java.lang.String open_stlno) {
		this.open_stlno = open_stlno;
	}

	public java.lang.String getFeerate_index() {
		return feerate_index;
	}

	public void setFeerate_index(java.lang.String feerate_index) {
		this.feerate_index = feerate_index;
	}

	public java.lang.String getRate_role() {
		return rate_role;
	}

	public void setRate_role(java.lang.String rate_role) {
		this.rate_role = rate_role;
	}

	public java.lang.String getRate_disc() {
		return rate_disc;
	}

	public void setRate_disc(java.lang.String rate_disc) {
		this.rate_disc = rate_disc;
	}

	public java.lang.String getCycle_mcht() {
		return cycle_mcht;
	}

	public void setCycle_mcht(java.lang.String cycle_mcht) {
		this.cycle_mcht = cycle_mcht;
	}

	public java.lang.String getMac_chk_flag() {
		return mac_chk_flag;
	}

	public void setMac_chk_flag(java.lang.String mac_chk_flag) {
		this.mac_chk_flag = mac_chk_flag;
	}

	public java.lang.String getFee_div_mode() {
		return fee_div_mode;
	}

	public void setFee_div_mode(java.lang.String fee_div_mode) {
		this.fee_div_mode = fee_div_mode;
	}

	public java.lang.String getSettle_mode() {
		return settle_mode;
	}

	public void setSettle_mode(java.lang.String settle_mode) {
		this.settle_mode = settle_mode;
	}

	public java.lang.String getAttr_bmp() {
		return attr_bmp;
	}

	public void setAttr_bmp(java.lang.String attr_bmp) {
		this.attr_bmp = attr_bmp;
	}

	public java.lang.String getCycle_settle_type() {
		return cycle_settle_type;
	}

	public void setCycle_settle_type(java.lang.String cycle_settle_type) {
		this.cycle_settle_type = cycle_settle_type;
	}

	public java.lang.String getReport_bmp() {
		return report_bmp;
	}

	public void setReport_bmp(java.lang.String report_bmp) {
		this.report_bmp = report_bmp;
	}

	public java.lang.String getDay_stlm_flag() {
		return day_stlm_flag;
	}

	public void setDay_stlm_flag(java.lang.String day_stlm_flag) {
		this.day_stlm_flag = day_stlm_flag;
	}

	public java.lang.String getCup_stlm_flag() {
		return cup_stlm_flag;
	}

	public void setCup_stlm_flag(java.lang.String cup_stlm_flag) {
		this.cup_stlm_flag = cup_stlm_flag;
	}

	public java.lang.String getAdv_ret_flag() {
		return adv_ret_flag;
	}

	public void setAdv_ret_flag(java.lang.String adv_ret_flag) {
		this.adv_ret_flag = adv_ret_flag;
	}

	public java.lang.String getMcht_file_flag() {
		return mcht_file_flag;
	}

	public void setMcht_file_flag(java.lang.String mcht_file_flag) {
		this.mcht_file_flag = mcht_file_flag;
	}

	public java.lang.String getFee_act() {
		return fee_act;
	}

	public void setFee_act(java.lang.String fee_act) {
		this.fee_act = fee_act;
	}

	public java.lang.String getLicence_no() {
		return licence_no;
	}

	public void setLicence_no(java.lang.String licence_no) {
		this.licence_no = licence_no;
	}

	public java.lang.String getLicence_add() {
		return licence_add;
	}

	public void setLicence_add(java.lang.String licence_add) {
		this.licence_add = licence_add;
	}

	public java.lang.String getPrincipal() {
		return principal;
	}

	public void setPrincipal(java.lang.String principal) {
		this.principal = principal;
	}

	public java.lang.String getComm_tel() {
		return comm_tel;
	}

	public void setComm_tel(java.lang.String comm_tel) {
		this.comm_tel = comm_tel;
	}

	public java.lang.String getManager() {
		return manager;
	}

	public void setManager(java.lang.String manager) {
		this.manager = manager;
	}

	public java.lang.String getMana_cred_tp() {
		return mana_cred_tp;
	}

	public void setMana_cred_tp(java.lang.String mana_cred_tp) {
		this.mana_cred_tp = mana_cred_tp;
	}

	public java.lang.String getMana_cred_no() {
		return mana_cred_no;
	}

	public void setMana_cred_no(java.lang.String mana_cred_no) {
		this.mana_cred_no = mana_cred_no;
	}

	public java.lang.String getCapital_sett_cycle() {
		return capital_sett_cycle;
	}

	public void setCapital_sett_cycle(java.lang.String capital_sett_cycle) {
		this.capital_sett_cycle = capital_sett_cycle;
	}

	public java.lang.String getCard_in_start_date() {
		return card_in_start_date;
	}

	public void setCard_in_start_date(java.lang.String card_in_start_date) {
		this.card_in_start_date = card_in_start_date;
	}

	public java.lang.String getSettle_acct() {
		return settle_acct;
	}

	public void setSettle_acct(java.lang.String settle_acct) {
		this.settle_acct = settle_acct;
	}

	public java.lang.String getMcht_e_nm() {
		return mcht_e_nm;
	}

	public void setMcht_e_nm(java.lang.String mcht_e_nm) {
		this.mcht_e_nm = mcht_e_nm;
	}

	public java.lang.String getSettle_bank() {
		return settle_bank;
	}

	public void setSettle_bank(java.lang.String settle_bank) {
		this.settle_bank = settle_bank;
	}

	public java.lang.String getRate_no() {
		return rate_no;
	}

	public void setRate_no(java.lang.String rate_no) {
		this.rate_no = rate_no;
	}

	public java.lang.String getCard_in_settle_bank() {
		return card_in_settle_bank;
	}

	public void setCard_in_settle_bank(java.lang.String card_in_settle_bank) {
		this.card_in_settle_bank = card_in_settle_bank;
	}

	public java.lang.String getFee_spe_type() {
		return fee_spe_type;
	}

	public void setFee_spe_type(java.lang.String fee_spe_type) {
		this.fee_spe_type = fee_spe_type;
	}

	public java.lang.String getFee_spe_gra() {
		return fee_spe_gra;
	}

	public void setFee_spe_gra(java.lang.String fee_spe_gra) {
		this.fee_spe_gra = fee_spe_gra;
	}

	public java.lang.String getReserved() {
		return reserved;
	}

	public void setReserved(java.lang.String reserved) {
		this.reserved = reserved;
	}

	public java.lang.String getReserved1() {
		return reserved1;
	}

	public void setReserved1(java.lang.String reserved1) {
		this.reserved1 = reserved1;
	}

	public java.lang.String getReserved2() {
		return reserved2;
	}

	public void setReserved2(java.lang.String reserved2) {
		this.reserved2 = reserved2;
	}

	public java.lang.String getUpd_opr_id() {
		return upd_opr_id;
	}

	public void setUpd_opr_id(java.lang.String upd_opr_id) {
		this.upd_opr_id = upd_opr_id;
	}

	public java.lang.String getUpd_ts() {
		return upd_ts;
	}

	public void setUpd_ts(java.lang.String upd_ts) {
		this.upd_ts = upd_ts;
	}

	public java.lang.String getCrt_opr_id() {
		return crt_opr_id;
	}

	public void setCrt_opr_id(java.lang.String crt_opr_id) {
		this.crt_opr_id = crt_opr_id;
	}

	public java.lang.String getCrt_ts() {
		return crt_ts;
	}

	public void setCrt_ts(java.lang.String crt_ts) {
		this.crt_ts = crt_ts;
	}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.huateng.po.TblMchtCupInfo)) return false;
		else {
			com.huateng.po.TblMchtCupInfo TblMchtCupInfo = (com.huateng.po.TblMchtCupInfo) obj;
			if (null == this.getMcht_no() || null == TblMchtCupInfo.getMcht_no()) return false;
			else return (this.getMcht_no().equals(TblMchtCupInfo.getMcht_no()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getMcht_no()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getMcht_no().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}