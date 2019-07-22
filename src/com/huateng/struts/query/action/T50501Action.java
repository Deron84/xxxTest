package com.huateng.struts.query.action;

import java.io.FileOutputStream;
import java.util.List;

import com.huateng.common.Constants;
import com.huateng.common.SysParamConstants;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.SysParamUtil;

public class T50501Action extends ReportBaseAction {


	private static final long serialVersionUID = -4859661940334301671L;
	private String reportType;
	private String actNo;
//	private String startDate;
//	private String endDate;
	
	private String actMon;
	
	
	public String getActMon() {
		return actMon;
	}

	public void setActMon(String actMon) {
		this.actMon = actMon;
	}

	public String getActNo() {
		return actNo;
	}

	public void setActNo(String actNo) {
		this.actNo = actNo;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	@Override
	protected String genSql() {
//		StringBuffer sb = new StringBuffer("select t5.DESCR,t1.card_accp_id,t3.MCHT_NM,t7.disc_nm,substr(t6.settle_acct,2),t4.ACT_FEE,")
//			.append("sum(t1.amt_trans/100) as amount,sum(t2.fee_d_out-t2.fee_c_out)/100 as fee")
//			.append(" from TBL_MCHNT_PARTICIPAT t4 left join ")
//			.append("(TBL_MCHT_BASE_INF t3 left join TBL_INF_MCHNT_TP_GRP t5 on t3.MCHT_GRP=t5.MCHNT_TP_GRP) on  t3.MCHT_NO=t4.MCHNT_NO")
//			.append(" left join bth_gc_txn_succ t1 left join tbl_algo_dtl t2 on t2.txn_key=t1.key_rsp on t1.card_accp_id=t4.MCHNT_NO")
//			.append(" left join TBL_MCHT_SETTLE_INF t6 left join TBL_INF_DISC_CD t7 on t6.fee_rate=t7.disc_cd on t4.MCHNT_NO=t6.MCHT_NO")
//			.append(" where t2.date_settlmt>'").append(startDate).append("'")
//			.append(" and t2.date_settlmt<'").append(endDate).append("'")
//			.append("and t1.card_accp_id in (select MCHNT_NO from TBL_MCHNT_PARTICIPAT where ACT_NO='")
//			.append(actNo).append("' and state='0') group by t1.card_accp_id,t3.MCHT_NM,t5.DESCR,t7.disc_nm,t6.settle_acct,t4.ACT_FEE");
		
		StringBuffer sb = new StringBuffer();
		sb.append("select t3.DESCR,MCHNT_NO,t1.MCHT_NM,t4.DISC_NM,t1.MCC,t1.AGR_BR,t1.OPER_NO,t1.OPER_NM,substr(t2.SETTLE_ACCT,2),");
		sb.append("t0.ACT_AT,t0.TXN_AT,t0.FEE_AT,t0.AMOUNT01,t0.MARKET_AT,t0.TOTAL,(case t5.ACT_CONTENT ")
		.append("when '0' then '算法1：应收商户手续费*费率' when '1' then '算法2：应付银联手续费*费率' when '2' then '算法3：手续费净额*费率' end) from TBL_MARKETING_STATISTICS t0 ");
		sb.append("left outer join TBL_MCHT_BASE_INF t1 on (t0.MCHNT_NO = t1.MCHT_NO) ");
		sb.append("left outer join TBL_MCHT_SETTLE_INF t2 on (t0.MCHNT_NO = t2.MCHT_NO) ");
		sb.append("left outer join TBL_INF_MCHNT_TP_GRP t3 on (t1.MCHT_GRP = t3.MCHNT_TP_GRP) ");
		sb.append("left outer join TBL_INF_DISC_CD t4 on (t2.FEE_RATE = t4.DISC_CD) ");
		sb.append("left outer join TBL_MARKET_ACT t5 on (t5.ACT_NO = t0.ACT_NO) ");
		sb.append("where trim(t0.SETTLMT_DATE) = '" + actMon.trim() + "' ");
		
		sb.append("and trim(t0.ACT_NO) = '" + actNo.trim() + "'");
		
		return sb.toString();
	}

	@Override
	protected void reportAction() throws Exception {
		ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
		List list = commQueryDAO.findBySQLQuery("select act_no,act_name,start_date,end_date from tbl_market_act where act_no='"+actNo+"'");
		
		if(list == null || list.isEmpty())
		{
			writeNoDataMsg("没有找到符合条件的记录");
			return;
		}
		Object[] object = (Object[])list.get(0);
		
		parameters.put("actNo", object[0].toString());
		parameters.put("actName", object[1].toString());
		parameters.put("startDate", object[2].toString());
		parameters.put("endDate", object[3].toString());
		
		String[] title = {"mchntGrp","mchntNo","mchntName","feeType","mcc","agrBr","operNo","operNm","settleAcct"
				,"actFee","txnAt","feeAt","amount01","marketAt","total","actContent"};
		Object[][] data = reportCreator.process(genSql(), title);
		
		if(data.length == 0) {
			writeNoDataMsg("没有找到符合条件的记录");
			return;
		}
		
		
		
		reportModel.setData(data);
		reportModel.setTitle(title);
	
		parameters.put("actMon", actMon);
		reportCreator.initReportData(getJasperInputSteam("T50501.jasper"), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN50501RN_" + 
						operator.getOprBrhId() + "_" + actMon + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN50501RN_" + 
						operator.getOprBrhId() + "_" + actMon + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN50501RN"));
		
		writeUsefullMsg(fileName);
		
		return;
	}

}
