package com.huateng.struts.query.action;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.SysParamUtil;

public class T50206Action extends ReportBaseAction {

	private static final long serialVersionUID = -7247671759196591242L;

	private String startDate;
	private String endDate;
	private String brhId;
	
	
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

	public String getBrhId() {
		return brhId;
	}

	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}

	@Override
	protected String genSql() {
		StringBuffer sql = new StringBuffer("select t1.BRH_ID,t3.BRH_NAME,t1.MCHT_CD,t2.MCHT_NM,t2.MCC")
		.append(",(case t2.MCHT_GRP when '01' then '宾馆娱乐类' when '02' then '房产批发类' ")
		.append("when '03' then '超市加油类' when '04' then '医院学校类' when '05' then '一般商户类' ")
		.append("when '06' then '新兴行业类' when '07' then '县乡优惠' when '08' then '财务POS商户' ")
		.append("when '09' then '本行签约POS' end),case t2.AGR_BR when null then '' end,t2.OPER_NO,t2.OPER_NM")
		.append(",t4.SETTLE_TYPE,substr(t4.SETTLE_ACCT,0,1),t4.SETTLE_ACCT_NM,substr(t4.SETTLE_ACCT,2),t5.DISC_NM")
		.append(",count(1),sum(t1.AMT_TRANS),sum(t1.CUPS_FEE),sum(t1.MCHT_FEE),sum(t1.T_1_AMT+t1.T_0_AMT),sum(t1.ACQ_INST_ID_FEE) ")
		.append("from TBL_MCHT_TXN t1 ")
		.append("left join TBL_MCHT_BASE_INF t2 on t1.MCHT_CD = t2.MCHT_NO ")
		.append("left join TBL_BRH_INFO t3 on t1.BRH_ID=t3.BRH_ID ")
		.append("left join TBL_MCHT_SETTLE_INF t4 on t4.MCHT_NO = t1.MCHT_CD ")
		.append("left join TBL_INF_DISC_CD t5 on (t4.FEE_RATE = t5.DISC_CD) ")
		.append("where t1.BRH_ID in ").append(operator.getBrhBelowId());
		if(brhId != null && !brhId.equals("") && !brhId.equals("9900"))
			sql.append("and t1.BRH_ID = '").append(brhId).append("'");
		if(startDate != null && !startDate.equals(""))
			sql.append(" and t1.DATE_SETTLMT >=").append(startDate);
		if(endDate != null && !endDate.equals(""))
			sql.append(" and t1.DATE_SETTLMT <=").append(endDate);
		sql.append(" group by  t1.BRH_ID,t3.BRH_NAME,t1.MCHT_CD,t2.MCHT_NM,t2.MCC,t2.AGR_BR,t2.OPER_NO,")
		.append("t2.OPER_NM,t4.SETTLE_TYPE,t4.SETTLE_ACCT_NM")
		.append(",t4.SETTLE_ACCT,t5.DISC_NM,t2.MCHT_GRP ORDER BY t1.BRH_ID");
		return sql.toString();
	}

	@Override
	protected void reportAction() throws Exception {
		
		
		//计算时间跨度
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		Date start = sdf.parse(startDate);
		Date end = sdf.parse(endDate);
		
		long bet = Math.abs(start.getTime() - end.getTime());
		
		if (bet/(60*60*24*1000) > 90) {
			writeNoDataMsg("统计查询超过跨度(90天)，请重新选择.");
			return;
		}

		String[] title = InformationUtil.createTitles("field", 20);
		Object[][] data = reportCreator.process(genSql(), title);
		
		reportType = "EXCEL";
		
		if(data.length == 0) {
			writeNoDataMsg("没有找到符合条件的记录");
			return;
		}
		
		reportModel.setData(data);
		reportModel.setTitle(title);
	
		
		parameters.put("BRH_ID", brhId);
		parameters.put("BRH_NAME", InformationUtil.getBrhName(brhId)); 
		parameters.put("startDate", startDate);
		parameters.put("endDate", endDate);
		
		reportCreator.initReportData(getJasperInputSteam("T50206.jasper"), parameters, 
				reportModel.wrapReportDataSource(), reportType);
		
		fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN50206RN.xls";
		
		outputStream = new FileOutputStream(fileName);
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN50206RN"));
		writeUsefullMsg(fileName);
		return;
		
	}

}
