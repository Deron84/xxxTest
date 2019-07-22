package com.huateng.struts.settle.action;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.commons.lang.xwork.StringUtils;

import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.SysParamUtil;


public class T80401Action extends ReportBaseAction{

	private static final long serialVersionUID = 1L;
	

	@Override
	protected void reportAction() throws Exception {
		
		reportType = "EXCEL";
		
		title = InformationUtil.createTitles("V_", 11);
		
		data = reportCreator.process(genSql(), title);
		
		for (int i = 0; i < data.length; i++) {
			data[i][3] = map1.get(data[i][3]);
			data[i][4] = map2.get(data[i][4]);
//			data[i][10] = CommonFunction.isMoney(data[i][10].toString());
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

		String brhNmAgr = InformationUtil.getBrhName(agrBr);
		String brhNmsign = InformationUtil.getBrhName(signInstId);
		
		parameters.put("P_1", dateStart+" - "+dateEnd);
		parameters.put("P_2", mchtCd);
		parameters.put("P_3", brhNmAgr);
		parameters.put("P_4", brhNmsign);
		
		
		reportCreator.initReportData(getJasperInputSteam("T80401.jasper"), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN80401RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN80401RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN80401RN"));
		writeUsefullMsg(fileName);
		return;
	}
	
	@Override
	protected String genSql() {
		String sql2 = "select a.card_accp_id as db_1," +
		"a.card_accp_name as db_2," +
		"(select DESCR from TBL_INF_MCHNT_TP_GRP g where b.MCHT_GRP = g.MCHNT_TP_GRP) as db_3," +
		"b.MCHT_FLAG1 as db_4," +
		"b.MCHT_FLAG1 || b.MCHT_FLAG2 as db_5," +
		"b.agr_br as db_6," +
		"b.sign_inst_id as db_7," +
		"substr(c.settle_acct,2) as db_8," +
		"c.settle_bank_nm as db_9," +
		"count(a.sys_seq_num) as db_10," +
		"sum(to_number(a.amt_trans))/100 as db_11 "  +
		" from tbl_n_txn a, tbl_mcht_base_inf b, tbl_mcht_settle_inf c " +
		" where a.card_accp_id = b.mcht_no and  b.mcht_no = c.mcht_no " +
		"and a.txn_num = '1301' " ;
		
		String sql3 = "select a.card_accp_id as db_1, " +
        "a.card_accp_name as db_2, " +
        "(select DESCR from TBL_INF_MCHNT_TP_GRP g where b.MCHT_GRP = g.MCHNT_TP_GRP) as db_3, " +
        "b.MCHT_FLAG1 as db_4, " +
        "b.MCHT_FLAG1 || b.MCHT_FLAG2 as db_5, " +
        "b.agr_br as db_6, " +
        "b.sign_inst_id as db_7, " +
        "substr(c.settle_acct,2) as db_8, " +
        "c.settle_bank_nm as db_9," +
        "count(a.sys_seq_num) as db_10, " +
        "sum(to_number(a.amt_trans)) /100 as db_11 "  +
        " from tbl_n_txn_his a, tbl_mcht_base_inf b, tbl_mcht_settle_inf c " +
        " where a.card_accp_id = b.mcht_no and  b.mcht_no = c.mcht_no " +
        "and a.txn_num = '1301' " ;

		if(!"0000".equals(operator.getOprBrhId())){
			sql2 += " and b.AGR_BR in " + operator.getBrhBelowId();
			sql3 += " and b.AGR_BR in " + operator.getBrhBelowId();
		}
		
		if (isNotEmpty(mchtCd)) {
			sql2 += " AND trim(a.card_accp_id)='"+mchtCd+"'";
			sql3 += " AND trim(a.card_accp_id)='"+mchtCd+"'";
		}
		if (isNotEmpty(mchtGrp)) {
			sql2 += " AND b.MCHT_GRP='" + mchtGrp + "'";
			sql2 += " AND b.MCHT_GRP='" + mchtGrp + "'";
		}
		if (isNotEmpty(mchtFlag1)) {
			sql2 += " AND b.MCHT_FLAG1='" + mchtFlag1 + "'";
			sql3 += " AND b.MCHT_FLAG1='" + mchtFlag1 + "'";
		}
		if (isNotEmpty(signInstId) && !"0000".equals(signInstId)) {
			sql2 += " AND trim(b.SIGN_INST_ID)='"+signInstId+"'";
			sql3 += " AND trim(b.SIGN_INST_ID)='"+signInstId+"'";
		}
		if (isNotEmpty(agrBr) && !"0000".equals(agrBr)) {
			sql2 += " AND trim(b.AGR_BR) in"+ InformationUtil.getBrhGroupString(agrBr);
			sql3 += " AND trim(b.AGR_BR) in"+ InformationUtil.getBrhGroupString(agrBr);
		}
		if (isNotEmpty(startDate)) {
			sql2 += " and trim(a.INST_DATE)>='"+startDate+"00000000"+"'";
			sql3 += " and trim(a.INST_DATE)>='"+startDate+"00000000"+"'";
		}
		if (isNotEmpty(endDate)) {
			sql2 += " and trim(a.INST_DATE)<='"+endDate+"99999999"+"'";
			sql3 += " and trim(a.INST_DATE)<='"+endDate+"99999999"+"'";
		}
		sql2 += " group by a.card_accp_id,a.card_accp_name," +
				"b.MCHT_FLAG1,b.MCHT_FLAG1 || b.MCHT_FLAG2," +
				"b.agr_br,b.sign_inst_id,c.settle_acct,c.settle_bank_nm, b.MCHT_GRP "; 
		sql3 += " group by a.card_accp_id,a.card_accp_name," +
		"b.MCHT_FLAG1,b.MCHT_FLAG1 || b.MCHT_FLAG2," +
		"b.agr_br,b.sign_inst_id,c.settle_acct,c.settle_bank_nm, b.MCHT_GRP "; 
		
		String sql = "select db_1,db_2,db_3,db_4,db_5,db_6,db_7,db_8,db_9,sum(db_10),sum(db_11) from " +
		"("+sql2 + " union all " + sql3 +") " +
		"group by db_1,db_2,db_3,db_4,db_5,db_6,db_7,db_8,db_9 order by db_1 desc";
				
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
	private String signInstId;
	private String agrBr;
	private String mchtGrp;
	private String mchtFlag1;


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

	public String getSignInstId() {
		return signInstId;
	}

	public void setSignInstId(String signInstId) {
		this.signInstId = signInstId;
	}

	public String getAgrBr() {
		return agrBr;
	}

	public void setAgrBr(String agrBr) {
		this.agrBr = agrBr;
	}
	
}