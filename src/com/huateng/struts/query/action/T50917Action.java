package com.huateng.struts.query.action;

import java.util.List;

import com.huateng.common.Operator;
import com.huateng.common.StringUtil;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.struts.query.BaseExcelQueryAction;
import com.huateng.struts.query.ExcelName;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.InformationUtil;

/**
 * project JSBConsole date 2013-4-22
 * 
 * @author qinbai
 */
public class T50917Action extends BaseExcelQueryAction {
	
	private String year;

	private String mon;
	
	private String mchtFlag1;
	
	private String date;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public String getMon() {
		return mon;
	}

	public void setMon(String mon) {
		this.mon = mon;
	}

	
	public String getMchtFlag1() {
		return mchtFlag1;
	}

	public void setMchtFlag1(String mchtFlag1) {
		this.mchtFlag1 = mchtFlag1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.huateng.struts.query.BaseExcelQueryAction#deal()
	 */
	@Override
	protected void deal() {
		year = date.substring(0, 4);
		mon = date.substring(0, 6);
		String monStart = mon + "01";
		String yearStart = year + "0101";
		
		c = sheet.getRow(0).getCell(5);
		if(c == null){
			sheet.getRow(0).createCell(5);
		}
		c.setCellValue(date);
        
		//地区按分行统计
		String brhSql = "select brh_id from tbl_brh_info where brh_level = '1' order by brh_id";
		
		ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
		List brhList = commQueryDAO.findBySQLQuery(brhSql);
		
		if(brhList == null){
			System.out.println("找不到机构！");
			return ;
		}
		
		for(int i = 0;i < brhList.size();i++){
			
			String wheresql = "where a.REC_CRT_TS <= '"+ date +"' " +
			"and CONN_TYPE = 'Z' " ;
			System.out.println(mchtFlag1);
			if(!StringUtil.isNull(mchtFlag1)){
				wheresql += " and a.mcht_flag1 = '"+ mchtFlag1 +"' "; 
			}
		    
		    String brh = null;
		   
		    brh = InformationUtil.getBrhName((String) brhList.get(i)).trim();
		    wheresql += " and a.AGR_BR in " + InformationUtil.getBrhGroupString((String) brhList.get(i)); 
		    
			
			String sql = "SELECT '"+ brh +"',count(mcht_no) FROM tbl_mcht_base_inf a   "; 
			sql += wheresql;
			sql +=" and MCHT_STATUS in('0','3','5','6','7','8')";
			int rowNum =4 + i, cellNum = 0,columnNum = 2;
			fillData(sql, rowNum, cellNum, columnNum);
			
			String sql1 = "SELECT nvl(sum(case when MCHT_STATUS in ('0', '3', '5', '6', '7', '8') and a.MCHT_NO in (select distinct MCHT_NO from TBL_MCHT_SETTLE_INF) then 1 else 0 end ),0), " +
					" nvl(sum(case when MCHT_STATUS  in ('0', '3', '5', '6', '7', '8') and a.MCHT_NO not in (select distinct MCHT_NO from TBL_MCHT_SETTLE_INF) then 1 else 0 end ),0)  from tbl_mcht_base_inf a     ";
			sql1 += wheresql;
			rowNum =4 + i; cellNum = 2;columnNum = 2;
			fillData(sql1, rowNum, cellNum, columnNum);
			
/*			String sql2 = "SELECT to_charnvl(sum(case when MCHT_STATUS in ('0', '3', '5', '6', '7', '8') then 1 else 0 end ),0)) FROM  tbl_mcht_base_inf a   ";
			sql2 += wheresql + " and a.MCHT_NO not in  (select mcht_no,count(mcht_no) from TBL_MCHT_SETTLE_INF group by mcht_no )";
			rowNum =4 + i; cellNum = 3;columnNum = 1;
			fillData(sql1, rowNum, cellNum, columnNum);*/
			
			String sql4 = " select nvl(sum(case when term_sta in ('2', '3', '4', '5', '6', '8') then 1 else 0 end),0)," +
					"  nvl(sum(case when term_sta in ('2', '3', '4', '5', '6', '8') and t1.term_id in (select CARD_ACCP_TERM_ID from tbl_n_txn where RESP_CODE = '00' union all  select CARD_ACCP_TERM_ID from tbl_n_txn_his where RESP_CODE = '00') then 1 else 0 end),0)," +
					"  nvl(sum(case when term_sta in ('2', '3', '4', '5', '6', '8') and t1.term_id not in (select CARD_ACCP_TERM_ID from tbl_n_txn where RESP_CODE = '00' union all  select CARD_ACCP_TERM_ID from tbl_n_txn_his where RESP_CODE = '00') then 1 else 0 end),0)" +
					"  from tbl_term_inf t1 inner join tbl_mcht_base_inf a on trim(t1.mcht_cd) = a.mcht_no  ";
			sql4 += "where a.CONN_TYPE = 'Z'" + " and a.mcht_flag1 = '"+ mchtFlag1 +"' AND substr(t1.USING_DATE, 0, 8) <= '"+ date+"'";
			sql4 += " and a.MCHT_STATUS in('0','3','5','6','7','8') " + " and a.AGR_BR in " + InformationUtil.getBrhGroupString((String) brhList.get(i)); 
			rowNum =4 + i; cellNum = 4;columnNum = 3;
			fillData(sql4, rowNum, cellNum, columnNum);
			
			/*String sql3 = "  select nvl(sum(case when term_sta in ('2', '3', '4', '5', '6', '8') then 1 else 0 end),0) from tbl_term_inf t1 inner join tbl_mcht_base_inf a on trim(t1.mcht_cd) = a.mcht_no where t1.term_id in (select CARD_ACCP_TERM_ID from tbl_n_txn where RESP_CODE = '00' union all  select CARD_ACCP_TERM_ID from tbl_n_txn_his where RESP_CODE = '00')   ";
			sql3 += "and a.CONN_TYPE = 'Z'" + " and a.mcht_flag1 = '"+ mchtFlag1 +"' AND substr(t1.USING_DATE, 0, 8) <= '"+ date+"'";
			sql3 += " and a.MCHT_STATUS in('0','3','5','6','7','8') " + " and a.AGR_BR in " + InformationUtil.getBrhGroupString((String) brhList.get(i)); 
			rowNum =4 + i; cellNum = 5;columnNum = 1;
			fillData(sql3, rowNum, cellNum, columnNum);
			
			String sql5 = "  select to_char(nvl(sum(case when term_sta in ('2', '3', '4', '5', '6', '8') then 1 else 0 end),0)) from tbl_term_inf t1 inner join tbl_mcht_base_inf a on trim(t1.mcht_cd) = a.mcht_no where t1.term_id not in (select CARD_ACCP_TERM_ID from tbl_n_txn where RESP_CODE = '00' union all  select CARD_ACCP_TERM_ID from tbl_n_txn_his where RESP_CODE = '00')   ";
			sql5 += "where a.CONN_TYPE = 'Z'" + " and a.mcht_flag1 = '"+ mchtFlag1 +"' AND substr(t1.USING_DATE, 0, 8) <= '"+ date+"'";
			sql5 += " and a.MCHT_STATUS in('0','3','5','6','7','8') " + " and a.AGR_BR in " + InformationUtil.getBrhGroupString((String) brhList.get(i)); 
			rowNum =4 + i; cellNum = 6;columnNum = 1;
			fillData(sql5, rowNum, cellNum, columnNum);*/
			
			String sql6 = " select nvl(sum(case when term_sta in ('2', '3', '4', '5', '6', '8')  and TERM_TP = '0' then 1 else 0 end),0), " +
			        "nvl(sum(case when term_sta in ('2', '3', '4', '5', '6', '8')  and TERM_TP = '1' then 1 else 0 end),0), " +
			        "nvl(sum(case when term_sta in ('2', '3', '4', '5', '6', '8')  and TERM_TP = '2' then 1 else 0 end),0), " +
			        "nvl(sum(case when term_sta in ('2', '3', '4', '5', '6', '8')  and TERM_TP = '3' then 1 else 0 end),0), " +
			        "nvl(sum(case when term_sta in ('2', '3', '4', '5', '6', '8')  and TERM_TP = '4' then 1 else 0 end),0), " +
			        "nvl(sum(case when term_sta in ('2', '3', '4', '5', '6', '8')  and TERM_TP = '5' then 1 else 0 end),0), " +
			        "nvl(sum(case when term_sta in ('2', '3', '4', '5', '6', '8')  and TERM_TP = '6' then 1 else 0 end),0) " +
					"from tbl_term_inf t1 inner join tbl_mcht_base_inf a on trim(t1.mcht_cd) = a.mcht_no  ";
			sql6 += "where a.CONN_TYPE = 'Z'" + " and a.mcht_flag1 = '"+ mchtFlag1 +"' AND substr(t1.USING_DATE, 0, 8) <= '"+ date+"'";
			sql6 += " and a.MCHT_STATUS in('0','3','5','6','7','8') " + " and a.AGR_BR in " + InformationUtil.getBrhGroupString((String) brhList.get(i)); 
			rowNum =4 + i; cellNum = 7;columnNum = 7;
			fillData(sql6, rowNum, cellNum, columnNum);
			
			String sql7 = "  select nvl(sum(AMT_TRANS)/10000,0) C1,nvl(count(CARD_ACCP_ID),0) C2,nvl(sum(FEE_MCHT),0) C3,nvl(sum(FEE_CREDIT - FEE_DEBIT),0) C4,nvl(sum(FEE_DEBIT),0) C5 from BTH_CUP_COMA b  inner join tbl_mcht_base_inf a on a.mcht_no = b.card_accp_id " ;

			sql7 += "where a.CONN_TYPE = 'Z'" + " and a.mcht_flag1 = '"+ mchtFlag1 +"' and a.REC_CRT_TS <= '"+ date +"' " ;
			sql7 += " and a.MCHT_STATUS in('0','3','5','6','7','8') " + " and a.AGR_BR in " + InformationUtil.getBrhGroupString((String) brhList.get(i)) + "and b.CANCEL_FLAG not in ('R', 'C')  and substr(b.TXN_NUM, 0, 1) != '3' "; 
			rowNum =4 + i; cellNum = 14;columnNum = 5;
			String sql8 = "SELECT C1,C2,C3,C4,C5 FROM ("+sql7+")";
			fillData(sql8, rowNum, cellNum, columnNum);
		}
	}
	   


	@Override
	protected String getFileKey() {
		return ExcelName.EN_517;
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