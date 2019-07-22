package com.huateng.struts.query.action;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.List;

import com.huateng.common.Constants;
import com.huateng.common.SysParamConstants;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.SysParamUtil;

public class T50502Action extends ReportBaseAction {


	private static final long serialVersionUID = -4859661940334301671L;
	private String reportType;
	private String brhId;
	private String date;
	private String reportDateType;
	
	
	public String getReportDateType() {
		return reportDateType;
	}

	public void setReportDateType(String reportDateType) {
		this.reportDateType = reportDateType;
	}

	public String getBrhId() {
		return brhId;
	}

	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	@Override
	protected String genSql() {
		StringBuffer sb = new StringBuffer("select t1.ORG_ID,t1.ORG_NAME,t2.MCHT_NO,t2.MCHT_NM,t2.MCC,t2.AGR_BR,t2.OPER_NO,t2.OPER_NM,0,0,0,0,t1.RATE,0 ")
		.append("from TBL_PROFESSIONAL_ORGAN t1 ")
		.append("left join  TBL_TERM_INF t3 left join TBL_MCHT_BASE_INF t2 on t2.MCHT_NO = t3.MCHT_CD ")
		.append("on t3.PROP_INS_NM=t1.ORG_ID " ).append("where t1.branch in ").append(operator.getBrhBelowId());
		if(brhId!=null && !brhId.equals("0000"))
			sb.append(" and branch ='").append(brhId).append("'");
		sb.append("group by t1.ORG_ID,t1.ORG_NAME,t2.MCHT_NO,t2.MCHT_NM,t2.MCC,t2.AGR_BR,t2.OPER_NO,t2.OPER_NM,t2.MCHT_NO,t1.RATE order by t1.ORG_ID");
		return sb.toString();
	}

	@Override
	protected void reportAction() throws Exception {
		ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");

//		                     0        1         2       3       4      5       6        7       8	     9		10		 11		  12      13
		String[] title = {"orgId","orgName","mchtNo","mchtNm","mcc","agrBr","operNo","operNm","total","txnAt","feeAt","mchntAt","rate","amount"};
		Object[][] data = reportCreator.process(genSql(), title);
		if(data.length == 0) {
			writeNoDataMsg("没有找到符合条件的记录");
			return;
		}

		
		for(int i=0;i<data.length;i++)
		{
//			if(data[i][2] == null)
//			{
//				writeNoDataMsg("没有找到符合条件商户的记录");
//				return;
//			}
					
			StringBuffer sql = new StringBuffer("select sum(ACQ_INS_AT_D-ACQ_INS_AT_C)")
			.append(",sum(CUPS_FILE_FEE_D-CUPS_FILE_FEE_C),sum(MCHT_FEE_D-MCHT_FEE_C) from TBL_TERM_ALGO_SUM t1 ")
			.append("left join tbl_term_inf t2 on t2.term_id=t1.term_id ")
			.append("where t1.mcht_cd ='").append(data[i][2]).append("' and t2.PROP_INS_NM='").append(data[i][0]);
			if(reportDateType != null && reportDateType.equals("1"))
				sql.append("' and t1.DATE_SETTLMT = '").append(date);
			else
				sql.append("' and substr(t1.DATE_SETTLMT,0,6) = '").append(date.substring(0, 6));
			sql.append("' group by t2.PROP_INS_NM");
			List tmp = commQueryDAO.findBySQLQuery(sql.toString());
			
			StringBuffer sql0 = new StringBuffer("select  nvl(sum(to_number(t1.AMT_TRANS))/100,0),")
			.append("nvl(sum(to_number(t1.FEE_DEBIT)- to_number(t1.FEE_CREDIT))/100,0),nvl(sum(to_number(trim(substr(t1.misc_1,55,13))))/100,0) ")
			.append("from bth_gc_txn_succ t1 ")
			.append("left join tbl_term_inf t2 on t2.term_id=t1.card_accp_term_id ")
			.append(" where t2.PROP_INS_NM='").append(data[i][0])
			.append("' and t2.mcht_cd ='").append(data[i][2]).append("' and (stlm_flag='0' or stlm_flag='A')")
			.append(" and substr(t1.MISC_FLAG,1,1)<>'F' and t1.T_0_FLAG='1");
			if(reportDateType != null && reportDateType.equals("1"))
				sql0.append("' and t1.DATE_SETTLMT_8 = '").append(date);
			else
				sql0.append("' and substr(t1.DATE_SETTLMT_8,0,6) = '").append(date.substring(0, 6));
			sql0.append("' group by t2.PROP_INS_NM");
			List tmp0 = commQueryDAO.findBySQLQuery(sql0.toString());
			
			String countSql1 = null;
			String countSql2 = null;
			if(reportDateType != null && reportDateType.equals("1"))
			{
				countSql1 = "select count(1) from bth_gc_txn_succ t1 left join tbl_term_inf t2 on t2.term_id = t1.card_accp_term_id where t1.date_settlmt_8 = '"+date+
				"' and t1.txn_num = '1501' and t1.trans_state ='1' and t1.card_accp_id = '"+data[i][2]+"' and t2.prop_ins_nm = '"+data[i][0]+"' and (stlm_flag='0' or stlm_flag='A')";
				countSql2 = "select count(1) from tbl_algo_dtl t1 left join tbl_term_inf t2 on t2.term_id = t1.term_id where t1.date_settlmt ='"+date+
				"' and t1.txn_num <> '1501' and t1.mcht_cd='"+data[i][2]+"' and t2.prop_ins_nm = '"+data[i][0]+"'";
			}
			else
			{
				countSql1 = "select count(1) from bth_gc_txn_succ t1 left join tbl_term_inf t2 on t2.term_id = t1.card_accp_term_id where substr(t1.date_settlmt_8,0,6) = '"+date.substring(0, 6)+
				"' and t1.txn_num = '1501' and t1.trans_state ='1' and t1.card_accp_id = '"+data[i][2]+"' and t2.prop_ins_nm = '"+data[i][0]+"' and (stlm_flag='0' or stlm_flag='A')";
				countSql2 = "select count(1) from tbl_algo_dtl t1 left join tbl_term_inf t2 on t2.term_id = t1.term_id where substr(t1.date_settlmt,0,6) ='"+date.substring(0, 6)+
				"' and t1.txn_num <> '1501' and t1.mcht_cd='"+data[i][2]+"' and t2.prop_ins_nm = '"+data[i][0]+"'";
			}
			List tmp1 = commQueryDAO.findBySQLQuery(countSql1);
			List tmp2 = commQueryDAO.findBySQLQuery(countSql2);
			BigDecimal bTmp1 = null;
			BigDecimal bTmp2 = null;
			try{
				bTmp1 = new BigDecimal(tmp1.get(0).toString());
				bTmp2 = new BigDecimal(tmp2.get(0).toString());
			}catch(NumberFormatException e){
				bTmp1 = new BigDecimal(0);
				bTmp2 = new BigDecimal(0);
			}
			data[i][8] = bTmp1.add(bTmp2);
			
			Object[] result = null;
			Object[] result0 = null;
			
			if(tmp != null&& !tmp.isEmpty())
			{
				result = (Object[])tmp.get(0);
			}
			if(tmp0 != null&& !tmp0.isEmpty())
			{
				result0 = (Object[])tmp0.get(0); 
			}
			
			tmp = null;
			tmp0 = null;
			
			BigDecimal[] bResult = new BigDecimal[3];
			BigDecimal[] bResult0 = new BigDecimal[3];
			try{
				bResult[0] = new BigDecimal(result[0].toString());  
				bResult[1] = new BigDecimal(result[1].toString());  
				bResult[2] = new BigDecimal(result[2].toString());  
			}catch(NumberFormatException e){
				bResult[0] = new BigDecimal(0);  
				bResult[1] = new BigDecimal(0);  
				bResult[2] = new BigDecimal(0);  
			}catch(NullPointerException e){
				bResult[0] = new BigDecimal(0);  
				bResult[1] = new BigDecimal(0);  
				bResult[2] = new BigDecimal(0);  
			}
			
			try{
				bResult0[0] = new BigDecimal(result0[0].toString());  
				bResult0[1] = new BigDecimal(result0[1].toString());  
				bResult0[2] = new BigDecimal(result0[2].toString());
			}catch(NumberFormatException e){
				bResult0[0] = new BigDecimal(0);  
				bResult0[1] = new BigDecimal(0);  
				bResult0[2] = new BigDecimal(0);  
			}catch(NullPointerException e){
				bResult0[0] = new BigDecimal(0);  
				bResult0[1] = new BigDecimal(0);  
				bResult0[2] = new BigDecimal(0);
			}
			
			data[i][9] = bResult[0].add(bResult0[0]);
			data[i][10] = bResult[1].add(bResult0[1]);
			data[i][11] = bResult[2].add(bResult0[2]);
			try{
				if(data[i][12] == null || data[i][12].toString().equals("0"))
				{
					data[i][13] = bResult[0].add(bResult0[0]).multiply(new BigDecimal(data[i][12].toString()));
				}
				if(data[i][12] != null && data[i][12].toString().equals("1"))
				{
					data[i][13] = bResult[1].add(bResult0[1]).multiply(new BigDecimal(data[i][12].toString()));
				}
				if(data[i][12] != null && data[i][12].toString().equals("2"))
				{
					data[i][13] = (new BigDecimal(data[i][9].toString()).subtract(new BigDecimal(data[i][11].toString())))
						.multiply(new BigDecimal(data[i][12].toString()));
				}
			}catch(NumberFormatException e){
				data[i][13] = new BigDecimal(0);
			}
		}
		
		
		
		reportModel.setData(data);
		reportModel.setTitle(title);
		if(reportDateType != null && reportDateType.equals("1"))
			parameters.put("date", date);
		else
			parameters.put("date", date.substring(0, 6));	

		parameters.put("BRH_NAME", operator.getOprBrhName());
		reportCreator.initReportData(getJasperInputSteam("T50502.jasper"), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN50502RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN50502RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";

		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN50502RN"));
		
		writeUsefullMsg(fileName);
		
		return;
	}

}
