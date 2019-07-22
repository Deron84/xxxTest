package com.huateng.struts.mchnt.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Collection;
import java.util.Collections;

import net.sf.jasperreports.engine.export.ExporterFilter;

import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.SysParamUtil;

/**
 * Title: 
 * 
 * File: T20106Action.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-8-10
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class T20106Action extends ReportBaseAction{

	private static final long serialVersionUID = 1L;

	@Override
	protected void reportAction() throws Exception {
		
		reportType = "TXT";
		
		title = InformationUtil.createTitles("V_", 93);
		
		data = reportCreator.process(genSql(), title);
		
		if(data.length == 0) {
			writeNoDataMsg("没有找符合条件的商户记录,无法生成文件");
			return;
		}
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "mchnt" + 
						"_" + CommonFunction.getCurrentDate() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "mchnt" + 
						"_" + CommonFunction.getCurrentDate() + ".pdf";
		else if(Constants.REPORT_TXTMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "mchnt" + 
						"_" + CommonFunction.getCurrentDate() + ".txt";
		
		File file = new File(fileName);
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		int lenD = data.length;
		int lenT = title.length;
		
		for(int i = 0;i < lenD; i++){
			
			for(int j = 0;j < lenT;j++ ){
				if(j != lenT -1){
					if(data[i][j] != null){
						writer.write(data[i][j].toString().trim()+",");
					}else{
						writer.write(""+",");
					}
				}else{
					if(data[i][j] != null){
						writer.write(data[i][j].toString().trim());
					}else{
						writer.write("");
					}
				}
			}
			
			if(i != lenD-1){
				writer.newLine();
			}
		}
		writer.flush();
		writer.close();
				
		writeUsefullMsg(fileName);
		return;
	}
	
	@Override
	protected String genSql() {
		
		
		String whereSql = " WHERE 1=1 ";
		
		if(!"0000".equals(operator.getOprBrhId())){
			whereSql += " and b.AGR_BR in" + operator.getBrhBelowId() + " ";
		}
		
		if (isNotEmpty(brhId) && !"0805083000".equals(brhId)) {
			whereSql += " AND INNER_ACQ_INST_ID = '" + brhId + "' ";
		}
		
		if (!StringUtil.isNull(mchtNo)) {
			whereSql += "AND a.mcht_no = '" + mchtNo + "' ";
		}
		
		if (!StringUtil.isNull(acmchntId)) {
			whereSql += " AND (a.MAPPING_MCHNTCDONE like '%" + acmchntId+ "%' ";
			whereSql += " or a.MAPPING_MCHNTCDTWO like '%" + acmchntId+ "%') ";
		}
		
		if (!StringUtil.isNull(mchtStatus)) {
			whereSql += "AND a.mcht_status = '" + mchtStatus + "' ";
		}
		if (!StringUtil.isNull(mchtType)) {
			whereSql += "AND a.mchnt_srv_tp = '" + mchtType + "' ";
		}
		if (!StringUtil.isNull(startDate)) {
			whereSql += "AND substr(a.crt_ts,0,8) >= '" + startDate + "' ";
		}
		if (!StringUtil.isNull(endDate)) {
			whereSql += "AND substr(a.crt_ts,0,8) <= '" + endDate + "' ";
		}
		if (!StringUtil.isNull(startDateU)) {
			whereSql += "AND substr(a.upd_ts,0,8) >= '" + startDateU + "' ";
		}
		if (!StringUtil.isNull(endDateU)) {
			whereSql += "AND substr(a.upd_ts,0,8) <= '" + endDateU + "' ";
		}
		

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT (case when a.mcht_status != 0 AND a.crt_ts = a.upd_ts then 'I' when a.mcht_status != 0 AND a.crt_ts < a.upd_ts then 'U' WHEN  a.mcht_status = 0 THEN 'D' END) AS INSET_FLAG," +
				  "a.mcht_no,a.inner_acq_inst_id,a.acq_inst_id_code,a.inst_id,a.mcht_nm,a.mcht_short_cn,a.area_no,a.inner_stlm_ins_id,a.mcht_type," +
				  "a.mcht_status,a.acq_area_cd,1 AS SETTLE_FLAG,a.conn_inst_cd,a.mchnt_tp_grp,a.mchnt_srv_tp,a.real_mcht_tp,a.settle_area_cd,a.mcht_acq_curr,a.conn_type," +
				  "a.currcy_cd,a.deposit_flag,a.res_pan_flag,a.res_track_flag,a.process_flag,a.cntry_code,a.ch_store_flag,a.mcht_tp_reason,a.mcc_md,a.rebate_flag," +
				  "a.mcht_acq_rebate,a.rebate_stlm_cd,a.reason_code,a.rate_type,a.fee_cycle,a.fee_type,a.limit_flag,a.fee_rebate,a.settle_amt,a.amt_top," +
				  "a.amt_bottom,a.disc_cd,a.fee_type_m,a.limit_flag_m,a.settle_amt_m,a.amt_top_m,a.amt_bottom_m,a.disc_cd_m,a.fee_rate_type,a.settle_bank_no," +
				  "a.settle_bank_type,a.advanced_flag,a.prior_flag,a.open_stlno,a.feerate_index,a.rate_role,a.rate_disc AS XX,a.mac_chk_flag,a.fee_div_mode+0," +
				  "a.settle_mode,a.attr_bmp,a.cycle_settle_type,a.report_bmp,a.day_stlm_flag,a.cup_stlm_flag,a.adv_ret_flag,a.mcht_file_flag AS XX2," +
				  "0,a.LICENCE_ADD,a.LICENCE_NO,a.LICENCE_NO,a.LICENCE_ADD,a.PRINCIPAL,a.COMM_TEL,b.POST_CODE,a.ACQ_INST_ID_CODE,0,0,0," +
				  "a.settle_acct,a.mcht_nm,156,a.settle_bank,0,a.rate_no,0,1,null,null,a.fee_act,a.ACQ_INST_ID_CODE,a.fee_spe_type," +
				  "(case when fee_spe_gra in('0','2','4','7') then '0' when fee_spe_gra in('1','3','5') then '1' when fee_spe_gra ='6' then '2' end) " +
				  "FROM tbl_mcht_cup_info a left outer join TBL_MCHT_BASE_INF b on(a.MCHT_NO = b.MCHT_NO) ");
		
		sb.append(whereSql);
		sb.append(" ORDER BY a.mcht_no,a.area_no,a.mchnt_srv_tp,a.mcht_type ");
		
		return sb.toString();
	}
	
	private String brhId;
	private String mchtNo;
	private String mchtStatus;
	private String mchtType;
	private String startDate;
	private String endDate;
	private String startDateU;
	private String endDateU;
	private String acmchntId;

	public String getBrhId() {
		return brhId;
	}

	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}

	public String getMchtNo() {
		return mchtNo;
	}

	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public String getMchtStatus() {
		return mchtStatus;
	}

	public void setMchtStatus(String mchtStatus) {
		this.mchtStatus = mchtStatus;
	}

	public String getMchtType() {
		return mchtType;
	}

	public void setMchtType(String mchtType) {
		this.mchtType = mchtType;
	}

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

	public String getStartDateU() {
		return startDateU;
	}

	public void setStartDateU(String startDateU) {
		this.startDateU = startDateU;
	}

	public String getEndDateU() {
		return endDateU;
	}

	public void setEndDateU(String endDateU) {
		this.endDateU = endDateU;
	}

	public String getAcmchntId() {
		return acmchntId;
	}

	public void setAcmchntId(String acmchntId) {
		this.acmchntId = acmchntId;
	}

	
	
}