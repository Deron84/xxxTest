package com.huateng.struts.settle.action;

import java.io.FileOutputStream;
import java.util.HashMap;

import org.apache.commons.lang.xwork.StringUtils;

import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.SysParamUtil;


public class T80402Action extends ReportBaseAction{

	private static final long serialVersionUID = 1L;
	

	@Override
	protected void reportAction() throws Exception {
		
		reportType = "EXCEL";
		
		title = InformationUtil.createTitles("V_", 16);
		
		data = reportCreator.process(genSql(), title);
		
		for (int i = 0; i < data.length; i++) {
			data[i][4] = map1.get(data[i][4]);
			data[i][5] = map2.get(data[i][5]);
			data[i][15] = (Double.parseDouble(CommonFunction.transFenToYuan(data[i][15].toString())));
		}

		
		reportModel.setData(data);
		reportModel.setTitle(title);
		
		String dateStart = "/";
		String dateEnd = "/";
		
		if(!StringUtil.isNull(startDate)){
			dateStart = startDate.substring(0, 4) +
				   "/" + 
				   startDate.substring(4, 6) +
				   "/" + 
				   startDate.substring(6, 8);
		}
		
		
		if(!StringUtil.isNull(endDate)){
			dateEnd = endDate.substring(0, 4) +
			   "/" + 
			   endDate.substring(4, 6) +
			   "/" + 
			   endDate.substring(6, 8);
		}

		
		
		parameters.put("P_1", dateStart+" - "+dateEnd);
		parameters.put("P_2", mchtCd);
		
		reportCreator.initReportData(getJasperInputSteam("T80402.jasper"), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN80402RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN80402RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN80402RN"));
		
		writeUsefullMsg(fileName);
		return;
	}
	
	@Override
	protected String genSql() {
		
		String whereSql = " ";
		
		if(!"0000".equals(operator.getOprBrhId())){
			whereSql += " and b.AGR_BR in " + operator.getBrhBelowId();
		}
		if (isNotEmpty(mchtCd)) {
			whereSql += " AND trim(mcht_cd)='"+mchtCd+"'";
		}
		
		if (isNotEmpty(termId)) {
			whereSql += " AND trim(term_id)='" + termId + "'";
		}
		if (isNotEmpty(mchtGrp)) {
			whereSql += " AND trim(MCHT_GRP)='" + mchtGrp + "'";
		}
		if (isNotEmpty(mchtFlag1)) {
			whereSql += " AND trim(MCHT_FLAG1)='" + mchtFlag1 + "'";
		}
		
		
		if (isNotEmpty(startDate)) {
			whereSql += " and trim(a.INST_DATE)>='"+startDate+"000000"+"'";
		}
		if (isNotEmpty(endDate)) {
			whereSql += " and trim(a.INST_DATE)<='"+endDate+"999999"+"'";
		}
        String sql = "";
        
		String sql2 = "select substr(a.inst_date,1,8)," +
				"a.trans_date_time," +
				"a.card_accp_id as MCHT_CD," +
				"(select DESCR from TBL_INF_MCHNT_TP_GRP g where b.MCHT_GRP = g.MCHNT_TP_GRP) as MCHT_GRP," +
				"b.MCHT_FLAG1," +
				"b.MCHT_FLAG1 || b.MCHT_FLAG2," +
				"a.card_accp_term_id," +
				"b.agr_br," +
				"d.prop_ins_nm," +
				"a.sys_seq_num," +
				"a.retrivl_ref," +
				"a.fwd_inst_id_code," +
				"a.pan," +
				"a.acct_id2," +
				"'商户付款'," +
				"a.amt_trans " +
				"from tbl_n_txn a, tbl_mcht_base_inf b, tbl_mcht_settle_inf c, tbl_term_inf d " +
				"where a.card_accp_id = b.mcht_no " +
				"and b.mcht_no = c.mcht_no " +
				"and a.txn_num = '1301' " +
				"and a.card_accp_term_id = d.term_id " +
				whereSql;
		
		String sql3 = "select substr(a.inst_date,1,8)," +
		"a.trans_date_time," +
		"a.card_accp_id as MCHT_CD," +
		"(select DESCR from TBL_INF_MCHNT_TP_GRP g where b.MCHT_GRP = g.MCHNT_TP_GRP) as MCHT_GRP," +
		"b.MCHT_FLAG1," +
		"b.MCHT_FLAG1 || b.MCHT_FLAG2," +
		"a.card_accp_term_id," +
		"b.agr_br," +
		"d.prop_ins_nm," +
		"a.sys_seq_num," +
		"a.retrivl_ref," +
		"a.fwd_inst_id_code," +
		"a.pan," +
		"a.acct_id2," +
		"'商户付款'," +
		"a.amt_trans " +
		"from tbl_n_txn_his a, tbl_mcht_base_inf b, tbl_mcht_settle_inf c, tbl_term_inf d " +
		"where a.card_accp_id = b.mcht_no " +
		"and b.mcht_no = c.mcht_no " +
		"and a.txn_num = '1301' " +
		"and a.card_accp_term_id = d.term_id " +
		whereSql;
		
		sql = sql2 + " union all " + sql3 +"order by MCHT_CD";
		
		return sql;
	}
	
	private static final HashMap<String, String> map1 = new HashMap<String, String>();
	private static final HashMap<String, String> map2 = new HashMap<String, String>();
	static {
		map1.put("0", "收款商户");
		map1.put("1", "老板通商户");
		map1.put("2", "分期商户");
		map1.put("3", "财务转账商户");
		map1.put("4", "项目形式商户");
		map1.put("5", "积分业务");
		map1.put("6", "其他业务");
		map1.put("7", "固话POS商户");
	}
	static {
		map2.put("00", "线上商户");
		map2.put("01", "线下商户");
		map2.put("10", "业主收款商户");
		map2.put("11", "业主付款商户");
		map2.put("20", "汽车分期商户");
		map2.put("21", "停车位分期商户");
		map2.put("22", "家装分期商户");
		map2.put("23", "POS分期商户");
		map2.put("24", "其他分期商户");
		map2.put("30", "本行财务转账商户");
		map2.put("31", "跨行财务转账商户");
		map2.put("40", "交通罚没项目");
		map2.put("41", "体彩购额项目");
		map2.put("42", "其他项目");
	}
	private String startDate;
	private String endDate;
	private String mchtCd;
	private String termId;
	private String mchtGrp;
	private String mchtFlag1;

	public String getTermId() {
		return termId;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

	public String getMchtGrp() {
		return mchtGrp;
	}

	public void setMchtGrp(String mchtGrp) {
		this.mchtGrp = mchtGrp;
	}

	public String getMchtFlag1() {
		return mchtFlag1;
	}

	public void setMchtFlag1(String mchtFlag1) {
		this.mchtFlag1 = mchtFlag1;
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


	public String getMchtCd() {
		return mchtCd;
	}

	public void setMchtCd(String mchtCd) {
		this.mchtCd = mchtCd;
	}
}