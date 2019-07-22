package com.huateng.struts.query.action;



import com.huateng.common.StringUtil;
import com.huateng.struts.query.BaseExcelQueryAction;
import com.huateng.struts.query.ExcelName;
import com.huateng.system.util.InformationUtil;

/**
 * project JSBConsole date 2013-4-22
 * 
 * @author 高浩
 */
public class T50908Action extends BaseExcelQueryAction {

	@Override
	protected void deal() {
		year = mon.substring(0, 4);

		String yearStart = year +"0101";
		String monStart = mon + "01";
		
		int m = Integer.valueOf(mon.substring(4, 6));
		int y = Integer.valueOf(year);
		String monEnd = getMonEnd(m,y,mon);
		
		c = sheet.getRow(0).getCell(3);
		
		if(c == null){
			sheet.getRow(0).createCell(3);
		}
		if(sheet.getRow(0).getCell(5) == null){
			sheet.getRow(0).createCell(5);
		}
        
        String wheresql = " where CONN_TYPE = 'J' "; 
        
		if(!StringUtil.isNull(mon)){
			c.setCellValue(mon);
		}
		
		if(StringUtil.isNull(brhId)){
			brhId = operator.getOprBrhId();
		}
		if(!"0000".equals(brhId)){
			wheresql += "and c.AGR_BR in" + InformationUtil.getBrhGroupString(brhId);
		}
		sheet.getRow(0).getCell(5).setCellValue(InformationUtil.getBrhName(brhId));
		
		String sqlAlgo = "select date_settlmt,MCHT_AT_C,MCHT_AT_D,MCHT_FEE_C,MCHT_FEE_D," +
							"FEE_D_OUT,FEE_C_OUT,mcht_cd,term_id,acq_ins_id_cd," +
							"(select TXN_NAME from TBL_TXN_NAME b where a.txn_num =b.TXN_NUM and DC_FLAG ='0') txnNm " +
						"from TBL_ALGO_DTL a " +
						"where txn_num not in('5171', '9103') and C_D_FLG <> '1'  and SUBSTR(TXN_NUM,1,1) <> '3' " +
					" union all " +
						"select trim(REC_UPD_TS) date_settlmt,MCHT_AT_C,MCHT_AT_D,MCHT_FEE_C,MCHT_FEE_D," +
							"FEE_D_OUT,FEE_C_OUT,mcht_cd,term_id,acq_ins_id_cd," +
							"(select TXN_NAME from TBL_TXN_NAME b where a.txn_num =b.TXN_NUM and DC_FLAG ='0') txnNm " +
						"from TBL_ALGO_DTL a " +
						"where TXN_NUM in ('5171', '9103') and OLD_TXN_FLG = '1' ";
		
		String sql = "select b.ACT_NO,b.MCHNT_NO,d.MARKETER_NAME," +
						"(select BRH_NAME from TBL_BRH_INFO e where d.AGR_BR=e.BRH_ID) brhNm," +
						"(select BRH_NAME from TBL_BRH_INFO f where d.SIGN_INST_ID=f.BRH_ID) upBrhNm," +
						"d.MCHT_NM,c.START_DATE,c.END_DATE," +
						"sum(nvl((MCHT_AT_C - MCHT_AT_D),0)) monAmt," +
						"count(a.date_settlmt) monCount," +
						"sum(nvl((MCHT_FEE_D - MCHT_FEE_C),0)) monFee," +
						"sum(nvl((MCHT_AT_C - MCHT_AT_D),0) - nvl((MCHT_FEE_D - MCHT_FEE_C),0)) monSettAmt " +
				"from ("+sqlAlgo+") a right outer join TBL_MCHNT_PARTICIPAT b " +
							"on(a.MCHT_CD=b.MCHNT_NO and DATE_SETTLMT >= '"+ monStart +"' and DATE_SETTLMT<= '"+ monEnd +"') " +
						"left outer join TBL_MARKET_ACT c on(b.ACT_NO=c.ACT_NO) " +
						"left outer join TBL_MCHT_BASE_INF d on(b.MCHNT_NO=d.MCHT_NO) " +
				wheresql +
				" group by b.ACT_NO,b.MCHNT_NO,d.MARKETER_NAME,d.AGR_BR,d.SIGN_INST_ID,d.MCHT_NM,c.START_DATE,c.END_DATE";
		
		sql = "select ACT_NO,START_DATE,END_DATE,t.MCHNT_NO,MARKETER_NAME,brhNm,upBrhNm,MCHT_NM," +
						"monAmt,sum(nvl((e.MCHT_AT_C - e.MCHT_AT_D),0)) allAmt," +
						"monCount,count(DATE_SETTLMT) allCount," +
						"monFee,sum(nvl((MCHT_FEE_D - MCHT_FEE_C),0)) allFee," +
						"monSettAmt,sum(nvl((MCHT_AT_C - MCHT_AT_D),0) - nvl((MCHT_FEE_D - MCHT_FEE_C),0)) allSettle " +
				"from ("+ sql +") t left outer join ("+sqlAlgo+") e " +
						"on(t.MCHNT_NO=e.MCHT_CD and DATE_SETTLMT >= t.START_DATE and DATE_SETTLMT<= '"+ monEnd +"') " +
				"where t.END_DATE >= '"+ monStart +"' and t.START_DATE <= '"+ monEnd +"' " +
				"group by ACT_NO,START_DATE,END_DATE,t.MCHNT_NO,MARKETER_NAME,brhNm,upBrhNm,MCHT_NM,monAmt,monCount,monFee,monSettAmt " +
				"order by ACT_NO,t.MCHNT_NO";
		
		int rowNum =4, cellNum = 0,columnNum = 16;
		fillData(sql, rowNum, cellNum, columnNum);
//		int[] cellNums3 = new int[]{3};
//		consolidate(cellNums3);
//		int[] cellNums4 = new int[]{4};
//		consolidate(cellNums4);
		
	}
	   
	
	private String year;

	private String mon;
	
	private String brhId;

	public String getMon() {
		return mon;
	}

	public void setMon(String mon) {
		this.mon = mon;
	}
	
	public String getBrhId() {
		return brhId;
	}

	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}

	@Override
	protected String getFileKey() {
		return ExcelName.EN_58;
	}

	@Override
	public String getMsg() {
		return msg;
	}

	@Override
	public boolean isSuccess() {
		return success;
	}

}