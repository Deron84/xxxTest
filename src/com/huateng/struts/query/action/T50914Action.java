package com.huateng.struts.query.action;



import com.huateng.common.StringUtil;
import com.huateng.struts.query.BaseExcelQueryAction;
import com.huateng.struts.query.ExcelName;

/**
 * project JSBConsole date 2013-4-22
 * 
 * @author 高浩
 */
public class T50914Action extends BaseExcelQueryAction {

	private String year;

	private String mon;
	
	private String mchtFlag2;

	public String getMon() {
		return mon;
	}

	public void setMon(String mon) {
		this.mon = mon;
	}

	public String getMchtFlag2() {
		return mchtFlag2;
	}

	public void setMchtFlag2(String mchtFlag2) {
		this.mchtFlag2 = mchtFlag2;
	}

	@Override
	protected void deal() {
		year = mon.substring(0, 4);
		String singleMon = mon.substring(4, 6);

		String monStart = mon + "01";
		String thisMonEnd = getMonEnd(Integer.valueOf(singleMon),Integer.valueOf(year),mon);

		String oneMonBf = null;
		String oneMonEnd = null;
		String twoMonBf = null;
		String twoMonEnd = null;
		String threeMonBf = null;
		String threeMonEnd = null;
        if(Integer.valueOf(singleMon) == 1){
        	oneMonBf = Integer.valueOf(year) - 1 + "" + "1201";
        	oneMonEnd = Integer.valueOf(year) - 1 + "" + "1231";
        	twoMonBf = Integer.valueOf(year) - 1 + "" + "1101";
        	twoMonEnd = Integer.valueOf(year) - 1 + "" + "1130";
    		threeMonBf = Integer.valueOf(year) - 1 + "" + "1001";
    		threeMonEnd = Integer.valueOf(year) - 1 + "" + "1031";
        }else if(Integer.valueOf(singleMon) == 2){
        	oneMonBf = year + "0101";
        	oneMonEnd = year + "0131";
        	twoMonBf = Integer.valueOf(year) - 1 + "" + "1201";
        	twoMonEnd = Integer.valueOf(year) - 1 + "" + "1231";
    		threeMonBf = Integer.valueOf(year) - 1 + "" + "1101";
    		threeMonEnd = Integer.valueOf(year) - 1 + "" + "1130";
        }else if(Integer.valueOf(singleMon) == 3){
        	oneMonBf = year + "0201";
        	oneMonEnd = getMonEnd(2,Integer.valueOf(year),year+"02");
        	twoMonBf = year + "0101";
        	twoMonEnd = year + "0131";
    		threeMonBf = Integer.valueOf(year) - 1 + "" + "1201";
    		threeMonEnd = Integer.valueOf(year) - 1 + "" + "1231";
        }else{
        	oneMonBf = Integer.valueOf(mon) - 1 + "01";
        	oneMonEnd = getMonEnd(Integer.valueOf(singleMon) - 1,Integer.valueOf(year),Integer.valueOf(mon) - 1 +"");
        	twoMonBf = Integer.valueOf(mon) - 2 + "01";
        	twoMonEnd = getMonEnd(Integer.valueOf(singleMon) - 2,Integer.valueOf(year),Integer.valueOf(mon) - 2 +"");
    		threeMonBf = Integer.valueOf(mon) - 3 + "01";
    		threeMonEnd = getMonEnd(Integer.valueOf(singleMon) - 3,Integer.valueOf(year),Integer.valueOf(mon) - 3 +"");
        }
		
        c = sheet.getRow(1).getCell(1);
        String wheresql = " where 1=1 "; 
//        wheresql += " and b.agr_br in " + operator.getBrhBelowId(); 
		if(!StringUtil.isNull(mon)){
			if(c == null){
				sheet.getRow(1).createCell(1);
			}
			c.setCellValue(monStart + "-" + thisMonEnd);
			wheresql += " and DATE_SETTLMT >= '"+ monStart +"' and DATE_SETTLMT<= '"+ thisMonEnd +"' ";
		}
		if(!StringUtil.isNull(mchtFlag2)){
			wheresql += " and b.MCHT_FLAG1 = '"+ mchtFlag2.substring(0, 1) +"' and b.MCHT_FLAG2 = '"+ mchtFlag2.substring(1, 2) +"'";
		}
		
		String sql = "select a.TERM_ID,(case c.PROP_TP when '0' then '本行' when '1' then '第三方' end) proTp,a.MCHT_CD," +
							"(select BRH_NAME from tbl_brh_info d where d.brh_id =b.SIGN_INST_ID) upBrh," +
							"b.MCHT_NM,(case '"+ mchtFlag2 +"' when '00' then '线上商户' " +
															  "when '01' then '线下商户' " +
															  "when '10' then '业主收款商户' " +
															  "when '11' then '业主付款商户' " +
															  "when '20' then '汽车分期商户' " +
															  "when '21' then '停车位分期商户' " +
															  "when '22' then '家装分期商户' " +
															  "when '23' then 'POS分期商户' " +
															  "when '24' then '其他分期商户' " +
															  "when '30' then '本行财务转账商户' " +
															  "when '31' then '跨行财务转账商户' " +
															  "when '40' then '交通罚没项目' " +
															  "when '41' then '体彩购额项目' " +
															  "when '42' then '其他项目' end) mchtFlag2," +
						    "b.CONTACT,nvl(COMM_MOBIL,'-'),ADDR,(case c.PROP_TP when '0' then '本行' when '1' then c.PROP_INS_NM end) epi," +
						    "sum((MCHT_FEE_D - MCHT_FEE_C)-(FEE_D_OUT - FEE_C_OUT) - SVC_FEE) inFee," +
						    "sum(case when c.PROP_TP = '1' then nvl(((MCHT_FEE_D - MCHT_FEE_C)-(FEE_D_OUT - FEE_C_OUT) - SVC_FEE)*c.PROP_INS_RATE/100,0) else 0 end) " +
						    	"+ nvl((select sum(LEASE_FEE + RENT_FEE) from TBL_TERM_RENT rent where a.MCHT_CD =rent.MCHT_NO and a.TERM_ID=rent.TERM_ID and INST_DATE >='"+ monStart +"' and INST_DATE<='"+ thisMonEnd +"'),0) otFee," +
						    "sum((MCHT_FEE_D - MCHT_FEE_C)-(FEE_D_OUT - FEE_C_OUT) - SVC_FEE) " +
						    	"- sum(case when c.PROP_TP = '1' then nvl(((MCHT_FEE_D - MCHT_FEE_C)-(FEE_D_OUT - FEE_C_OUT) - SVC_FEE)*c.PROP_INS_RATE/100,0) else 0 end) " +
						    	"- nvl((select sum(LEASE_FEE + RENT_FEE) from TBL_TERM_RENT rent where a.MCHT_CD =rent.MCHT_NO and a.TERM_ID=rent.TERM_ID and INST_DATE >='"+ monStart +"' and INST_DATE<='"+ thisMonEnd +"'),0) setInFee," +
						    "sum(case when DATE_SETTLMT >= '"+ monStart +"' and DATE_SETTLMT<= '"+ thisMonEnd +"' then MCHT_AT_C - MCHT_AT_D else 0 end) monAmt," +
						    "sum(case when DATE_SETTLMT >= '"+ oneMonBf +"' and DATE_SETTLMT<= '"+ oneMonEnd +"' then MCHT_AT_C - MCHT_AT_D else 0 end) monAmt1," +
						    "sum(case when DATE_SETTLMT >= '"+ twoMonBf +"' and DATE_SETTLMT<= '"+ twoMonEnd +"' then MCHT_AT_C - MCHT_AT_D else 0 end) monAmt2," +
						    "sum(case when DATE_SETTLMT >= '"+ threeMonBf +"' and DATE_SETTLMT<= '"+ threeMonEnd +"' then MCHT_AT_C - MCHT_AT_D else 0 end) monAmt3," +
						    "sum(case when DATE_SETTLMT >= '"+ monStart +"' and DATE_SETTLMT<= '"+ thisMonEnd +"' then 1 else 0 end) monCount," +
						    "sum(case when DATE_SETTLMT >= '"+ oneMonBf +"' and DATE_SETTLMT<= '"+ oneMonEnd +"' then 1 else 0 end) monCount1," +
						    "sum(case when DATE_SETTLMT >= '"+ twoMonBf +"' and DATE_SETTLMT<= '"+ twoMonEnd +"' then 1 else 0 end) monCount2," +
						    "sum(case when DATE_SETTLMT >= '"+ threeMonBf +"' and DATE_SETTLMT<= '"+ threeMonEnd +"' then 1 else 0 end) monCount3 " +
					"from TBL_ALGO_DTL a left outer join TBL_MCHT_BASE_INF b on(a.MCHT_CD=b.MCHT_NO) " +
									"left outer join TBL_TERM_INF c on(a.TERM_ID=c.TERM_ID and a.MCHT_CD =c.MCHT_CD) "; 
		sql += wheresql;
		sql += "group by a.TERM_ID,c.PROP_TP,a.MCHT_CD,b.BANK_NO,b.MCHT_NM,b.CONTACT,COMM_MOBIL,ADDR,c.PROP_INS_NM " +
			   "order by a.TERM_ID,a.MCHT_CD,setInFee";
		
		sql ="select * from (" + sql +") where setInFee <= 0";
		int rowNum =4, cellNum = 0,columnNum = 21;
		fillData(sql, rowNum, cellNum, columnNum);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.huateng.struts.query.BaseExcelQueryAction#getFileKey()
	 */
	@Override
	protected String getFileKey() {
		return ExcelName.EN_514;
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