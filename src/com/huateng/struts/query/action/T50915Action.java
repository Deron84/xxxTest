package com.huateng.struts.query.action;



import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.common.StringUtil;
import com.huateng.struts.query.BaseExcelQueryAction;
import com.huateng.struts.query.ExcelName;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.InformationUtil;


public class T50915Action extends BaseExcelQueryAction {

	/*
	 * (non-Javadoc)
	 * phj
	 * @see com.huateng.struts.query.BaseExcelQueryAction#deal()
	 */
	@Override
	
	protected void deal() {
		
		String date = CommonFunction.getCurrentDate();

		c = sheet.getRow(1).getCell(1);
		if(!StringUtil.isNull(date)){
			if(c == null){
				sheet.getRow(1).createCell(1);
			}
			c.setCellValue(date);
		}
//		List countList= getMchntBaseInfoAll();
		List countList= (List)ServletActionContext.getRequest().getSession().getAttribute("queryList");
		//释放资源
		//ServletActionContext.getRequest().getSession().removeAttribute("queryList");
		int rowNum =3, cellNum = 0,columnNum = 161;
//		String sql = (String) ServletActionContext.getRequest().getSession().getAttribute("querySql");
		
//		fillData(sql.toString(), rowNum, cellNum, columnNum);
		fillData( countList, rowNum, cellNum, columnNum);
		int[] cellNums = new int[]{0};
		consolidate(cellNums);
	}
	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isNotEmpty(String str) {
		if (str != null && !"".equals(str.trim()))
			return true;
		else
			return false;
	}
	
	public static List getMchntBaseInfoAll() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Operator operator = (Operator) request.getSession().getAttribute(
				Constants.OPERATOR_INFO);
		String and = " WHERE CONN_TYPE IN ('J','Z')";
		if (isNotEmpty(request.getParameter("mchntId"))) {
			and += (" AND a.MCHT_NO = '" + request.getParameter("mchntId") + "' ");
		}
		if (isNotEmpty(request.getParameter("mchtStatus"))) {
			and += (" AND a.MCHT_STATUS = '"
					+ request.getParameter("mchtStatus") + "' ");
		}
		if (isNotEmpty(request.getParameter("mchtGrp"))) {
			and += (" AND a.MCHT_GRP = '" + request.getParameter("mchtGrp") + "' ");
		}
		if (isNotEmpty(request.getParameter("startDate"))) {
			and += (" AND substr(a.REC_CRT_TS,0,8) >= '"
					+ request.getParameter("startDate") + "' ");
		}
		if (isNotEmpty(request.getParameter("endDate"))) {
			and += (" AND substr(a.REC_CRT_TS,0,8) <= '" + request.getParameter("endDate") + "' ");
		}
		if (isNotEmpty(request.getParameter("MchtFlag1"))) {
			and += (" AND MCHT_FLAG1 = '"
					+ request.getParameter("MchtFlag1") + "' ");
		}
		if (isNotEmpty(request.getParameter("connType"))) {
			and += (" AND CONN_TYPE = '"
					+ request.getParameter("connType") + "' ");
		}
		if (isNotEmpty(request.getParameter("brhId"))) {
			and += (" AND a.AGR_BR = '" + request.getParameter("brhId") + "' ");
		} else {
			if (!"0000".equals(operator.getOprBrhId())) {
				and += (" AND a.AGR_BR IN " + operator.getBrhBelowId() + " ");
			}
		}

		StringBuilder sqlList = new StringBuilder();
		sqlList.append("select ");
		sqlList
				.append("c.mcht_no,c.mcht_nm,c.risl_lvl,c.mcht_lvl,c.mcht_status,c.manu_auth_flag,c.part_num,");
		sqlList
				.append("c.disc_cons_flg,c.disc_cons_rebate,c.pass_flag,c.open_days,c.sleep_days,c.mcht_cn_abbr,c.spell_name,c.eng_name,"); // 9
		// 个字段
		sqlList
				.append("c.mcht_en_abbr,c.area_no,c.settle_area_no,c.addr,c.home_page,c.mcc,c.tcc,");
		sqlList
				.append("c.etps_attr,c.mng_mcht_id,c.mcht_grp,c.mcht_attr,c.mcht_group_flag,c.mcht_group_id,c.mcht_eng_nm,");
		sqlList
				.append("c.mcht_eng_addr,c.mcht_eng_city_name,c.otsc_name,c.otsc_addr,c.otsc_phone,c.otsc_div_rate,c.marketer_name,");
		sqlList
				.append("c.marketer_contact,c.check_frqc,c.mcht_post_email,c.sa_limit_amt,c.sa_action,c.psam_num,c.cd_mac_num,");
		sqlList
				.append("c.pos_num,c.conn_type,c.mcht_mng_mode,c.mcht_function,c.licence_no,c.licence_end_date,c.bank_licence_no,");
		sqlList
				.append("c.bus_type,c.bus_amt,c.mcht_cre_lvl,c.contact,c.post_code,c.comm_email,c.comm_mobil,");
		sqlList
				.append("c.comm_tel,c.contact_snd,c.comm_email_snd,c.comm_mobil_snd,c.comm_tel_snd,c.manager,c.artif_certif_tp,");
		sqlList
				.append("c.identity_no,c.manager_tel,c.fax,c.electrofax,c.reg_addr,c.apply_date,c.enable_date,");
		sqlList
				.append("c.pre_aud_nm,c.confirm_nm,c.protocal_id,c.sign_inst_id,c.net_nm,c.agr_br,c.net_tel,");
		sqlList
				.append("c.prol_date,c.prol_tlr,c.close_date,c.close_tlr,c.main_tlr,c.check_tlr,c.oper_no,");
		sqlList
				.append("c.oper_nm,c.proc_flag,c.set_cur,c.print_inst_id,c.acq_inst_id,c.acq_bk_name,c.bank_no,");
		sqlList
				.append("c.orgn_no,c.subbrh_no,c.subbrh_nm,c.open_time,c.close_time,c.vis_act_flg,c.vis_mcht_id,");
		sqlList
				.append("c.mst_act_flg,c.mst_mcht_id,c.amx_act_flg,c.amx_mcht_id,c.dnr_act_flg,c.dnr_mcht_id,c.jcb_act_flg,");
		sqlList
				.append("c.jcb_mcht_id,c.cup_mcht_flg,c.deb_mcht_flg,c.cre_mcht_flg,c.cdc_mcht_flg,c.reserved,c.upd_opr_id,");
		sqlList
				.append("c.crt_opr_id,c.rec_upd_ts,c.rec_crt_ts,c.rct_check_date,c.plan_check_date,c.mcht_flag1,c.mcht_flag2,");
		sqlList
				.append("b.settle_type,b.rate_flag,b.settle_chn,b.bat_time,b.auto_stl_flg,b.part_num,b.fee_type,");
		sqlList
				.append("b.fee_fixed,b.fee_max_amt,b.fee_min_amt,b.fee_rate,b.fee_div_1,b.fee_div_2,b.fee_div_3,");
		sqlList
				.append("b.settle_mode,b.fee_cycle,b.settle_rpt,b.whitelist_flag,b.acct_settle_type,b.acct_settle_rate,b.acct_settle_limit,");
		sqlList
				.append("b.settle_bank_no,b.settle_bank_nm,b.settle_acct_nm,b.settle_acct,b.settle_bank_no_snd,b.settle_bank_nm_snd,b.settle_acct_nm_snd,");
		sqlList
				.append("b.settle_acct_snd,b.fee_acct_nm,b.fee_acct,b.group_flag,b.open_stlno,b.change_stlno,b.fee_back_flg,");
		sqlList
				.append("b.reserved,b.rec_upd_ts,b.rec_crt_ts,b.person_settle_flg ");
		sqlList
				.append("from ( select a.mcht_no,a.mcht_nm,a.risl_lvl,a.mcht_lvl,a.mcht_status,a.manu_auth_flag,a.part_num,");
		sqlList
				.append("a.disc_cons_flg,a.disc_cons_rebate,a.pass_flag,a.open_days,a.sleep_days,a.mcht_cn_abbr,a.spell_name,a.eng_name,"); // 9
		// 个字段
		sqlList
				.append("a.mcht_en_abbr,a.area_no,a.settle_area_no,a.addr,a.home_page,a.mcc,a.tcc,");
		sqlList
				.append("a.etps_attr,a.mng_mcht_id,a.mcht_grp,a.mcht_attr,a.mcht_group_flag,a.mcht_group_id,a.mcht_eng_nm,");
		sqlList
				.append("a.mcht_eng_addr,a.mcht_eng_city_name,a.otsc_name,a.otsc_addr,a.otsc_phone,a.otsc_div_rate,a.marketer_name,");
		sqlList
				.append("a.marketer_contact,a.check_frqc,a.mcht_post_email,a.sa_limit_amt,a.sa_action,a.psam_num,a.cd_mac_num,");
		sqlList
				.append("a.pos_num,a.conn_type,a.mcht_mng_mode,a.mcht_function,a.licence_no,a.licence_end_date,a.bank_licence_no,");
		sqlList
				.append("a.bus_type,a.bus_amt,a.mcht_cre_lvl,a.contact,a.post_code,a.comm_email,a.comm_mobil,");
		sqlList
				.append("a.comm_tel,a.contact_snd,a.comm_email_snd,a.comm_mobil_snd,a.comm_tel_snd,a.manager,a.artif_certif_tp,");
		sqlList
				.append("a.identity_no,a.manager_tel,a.fax,a.electrofax,a.reg_addr,a.apply_date,a.enable_date,");
		sqlList
				.append("a.pre_aud_nm,a.confirm_nm,a.protocal_id,a.sign_inst_id,a.net_nm,a.agr_br,a.net_tel,");
		sqlList
				.append("a.prol_date,a.prol_tlr,a.close_date,a.close_tlr,a.main_tlr,a.check_tlr,a.oper_no,");
		sqlList
				.append("a.oper_nm,a.proc_flag,a.set_cur,a.print_inst_id,a.acq_inst_id,a.acq_bk_name,a.bank_no,");
		sqlList
				.append("a.orgn_no,a.subbrh_no,a.subbrh_nm,a.open_time,a.close_time,a.vis_act_flg,a.vis_mcht_id,");
		sqlList
				.append("a.mst_act_flg,a.mst_mcht_id,a.amx_act_flg,a.amx_mcht_id,a.dnr_act_flg,a.dnr_mcht_id,a.jcb_act_flg,");
		sqlList
				.append("a.jcb_mcht_id,a.cup_mcht_flg,a.deb_mcht_flg,a.cre_mcht_flg,a.cdc_mcht_flg,a.reserved,a.upd_opr_id,");
		sqlList
				.append("a.crt_opr_id,a.rec_upd_ts,a.rec_crt_ts,a.rct_check_date,a.plan_check_date,a.mcht_flag1,a.mcht_flag2");
		// sqlList.append("b.settle_type,b.rate_flag,b.settle_chn,b.bat_time,b.auto_stl_flg,b.part_num,b.fee_type,");
		// sqlList.append("b.fee_fixed,b.fee_max_amt,b.fee_min_amt,b.fee_rate,b.fee_div_1,b.fee_div_2,b.fee_div_3,");
		// sqlList.append("b.settle_mode,b.fee_cycle,b.settle_rpt,b.whitelist_flag,b.acct_settle_type,b.acct_settle_rate,b.acct_settle_limit,");
		// sqlList.append("b.settle_bank_no,b.settle_bank_nm,b.settle_acct_nm,b.settle_acct,b.settle_bank_no_snd,b.settle_bank_nm_snd,b.settle_acct_nm_snd,");
		// sqlList.append("b.settle_acct_snd,b.fee_acct_nm,b.fee_acct,b.group_flag,b.open_stlno,b.change_stlno,b.fee_back_flg,");
		// sqlList.append("b.reserved,b.rec_upd_ts,b.rec_crt_ts,b.person_settle_flg FROM TBL_MCHT_BASE_INF a");
		sqlList.append(" FROM TBL_MCHT_BASE_INF a  ");
		sqlList.append(and);
		sqlList.append(" ) c left outer join TBL_MCHT_SETTLE_INF b on c.mcht_no = b.mcht_no");
		List list = CommonFunction.getCommQueryDAO().findBySQLQuery(
				sqlList.toString());
		return list;
	}
	private String startDate;
	private String endDate;
	private String mchtStatus;
	private String idacqInstId;
	private String mchtNo;
	private String mchtGrp;

	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getMchtStatus() {
		return mchtStatus;
	}
	public void setMchtStatus(String mchtStatus) {
		this.mchtStatus = mchtStatus;
	}
	public String getIdacqInstId() {
		return idacqInstId;
	}
	public void setIdacqInstId(String idacqInstId) {
		this.idacqInstId = idacqInstId;
	}
	public String getMchtNo() {
		return mchtNo;
	}
	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}
	public String getMchtGrp() {
		return mchtGrp;
	}
	public void setMchtGrp(String mchtGrp) {
		this.mchtGrp = mchtGrp;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.huateng.struts.query.BaseExcelQueryAction#getFileKey()
	 */
	@Override
	protected String getFileKey() {
		return ExcelName.EN_515;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.huateng.struts.system.action.BaseSupport#getMsg()
	 */
	@Override
	public String getMsg() {
		return msg;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.huateng.struts.system.action.BaseSupport#isSuccess()
	 */
	@Override
	public boolean isSuccess() {
		return success;
	}

}