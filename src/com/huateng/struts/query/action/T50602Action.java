package com.huateng.struts.query.action;

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


public class T50602Action extends ReportBaseAction{

	private static final long serialVersionUID = 1L;
	

	@Override
	protected void reportAction() throws Exception {
		
		reportType = "EXCEL";
		
		title = InformationUtil.createTitles("V_", 12);
		
		data = reportCreator.process(genSql(), title);
		
		for (int i = 0; i < data.length; i++) {
			data[i][3] = map1.get(data[i][3]);
			data[i][4] = map2.get(data[i][4]);
			data[i][7] = Double.parseDouble(CommonFunction.transFenToYuan(data[i][7].toString()));
			data[i][8] = Double.parseDouble(CommonFunction.transFenToYuan(data[i][8].toString()));
			data[i][9] = Double.parseDouble(CommonFunction.transFenToYuan(data[i][9].toString()));
			data[i][10] = Double.parseDouble(CommonFunction.transFenToYuan(data[i][10].toString()));
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
		
		
		reportCreator.initReportData(getJasperInputSteam("T50602.jasper"), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN80401RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN80401RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN50602RN"));
		writeUsefullMsg(fileName);
		return;
	}
	
	@Override
	protected String genSql() {
		String sqlAlgo = "select date_settlmt,MCHT_AT_C,MCHT_AT_D,MCHT_FEE_C,MCHT_FEE_D," +
								"FEE_D_OUT,FEE_C_OUT,mcht_cd,term_id,acq_ins_id_cd,TXN_NUM," +
								"(select TXN_NAME from TBL_TXN_NAME b where a.txn_num =b.TXN_NUM and DC_FLAG ='0') txnNm ,pan " +
							"from TBL_ALGO_DTL a " +
							"where txn_num not in('5171', '9103') and C_D_FLG <> '1'  and SUBSTR(TXN_NUM,1,1) <> '3' " +
						" union all " +
							"select trim(REC_UPD_TS) date_settlmt,MCHT_AT_C,MCHT_AT_D,MCHT_FEE_C,MCHT_FEE_D," +
								"FEE_D_OUT,FEE_C_OUT,mcht_cd,term_id,acq_ins_id_cd,TXN_NUM," +
								"(select TXN_NAME from TBL_TXN_NAME b where a.txn_num =b.TXN_NUM and DC_FLAG ='0') txnNm,pan " +
							"from TBL_ALGO_DTL a " +
							"where TXN_NUM in ('5171', '9103') and OLD_TXN_FLG = '1' ";
		
		String sql = "select (select DESCR from TBL_INF_MCHNT_TP_GRP g where b.MCHT_GRP=g.MCHNT_TP_GRP)," +
					"trim(mcht_cd),b.MCHT_NM,b.MCHT_FLAG1," +
					"b.MCHT_FLAG1||b.MCHT_FLAG2,c.FEE_RATE,count(1)," +
					"sum(MCHT_AT_C - MCHT_AT_D)*100," +
					"sum(MCHT_FEE_C-MCHT_FEE_D)*100," +
					"(sum(MCHT_AT_C - MCHT_AT_D)-sum(MCHT_FEE_D-MCHT_FEE_C))*100," +
					 "sum(case when TXN_NUM not in('9103','9205') then NVL((a.MCHT_FEE_D - a.MCHT_FEE_C), 0)-NVL((FEE_D_OUT-FEE_C_OUT), 0) "
							+ "when TXN_NUM = '9103' then nvl((MCHT_AT_D - MCHT_AT_C),0) "
							+ "when TXN_NUM = '9205' then nvl((MCHT_AT_D - MCHT_AT_C),0) end)*100 , d.brh_name  "	+		
			"from ("+sqlAlgo+") a left outer join TBL_MCHT_BASE_INF b on (a.mcht_cd =b.MCHT_NO) " +
								"left outer join TBL_MCHT_SETTLE_INF c on(a.mcht_cd =c.MCHT_NO) " +
								"left outer join tbl_brh_info d on (b.agr_br = d.brh_id)  " +
			"where 1=1";

		if(!"0000".equals(operator.getOprBrhId())){
			sql += " and b.AGR_BR in " + operator.getBrhBelowId();
		}
		
		if (isNotEmpty(mchtCd)) {
			sql += " AND trim(mcht_cd)='"+mchtCd+"'";
		}
		if (isNotEmpty(mchtGrp)) {
			sql += " AND MCHT_GRP='" + mchtGrp + "'";
		}
		if (isNotEmpty(mchtFlag1)) {
			sql += " AND MCHT_FLAG1='" + mchtFlag1 + "'";
		}
		if (isNotEmpty(signInstId) && !"0000".equals(signInstId)) {
			sql += " AND trim(b.SIGN_INST_ID)='"+signInstId+"'";
		}
		if (isNotEmpty(agrBr) && !"0000".equals(agrBr)) {
			sql += " AND trim(b.AGR_BR) in"+ InformationUtil.getBrhGroupString(agrBr);
		}
		if (isNotEmpty(startDate)) {
			sql += " and trim(DATE_SETTLMT)>='"+startDate+"'";
		}
		if (isNotEmpty(endDate)) {
			sql += " and trim(DATE_SETTLMT)<='"+endDate+"'";
		}
		sql += " group by mcht_cd,b.MCHT_NM,MCHT_GRP,b.MCHT_FLAG1,b.MCHT_FLAG2,c.FEE_RATE,d.brh_name " +
				"order by mcht_cd,b.MCHT_FLAG1,b.MCHT_FLAG2";
				
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