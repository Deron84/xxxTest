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
 * @author 高浩
 */
public class T50909Action extends BaseExcelQueryAction {
	
	private String year;

	private String mon;
	
	private String mchtFlag1;
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
		c.setCellValue(mon);
        
		//地区按分行统计
		String brhSql = "select brh_id from tbl_brh_info where brh_level = '1' order by brh_id";
		
		ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
		List brhList = commQueryDAO.findBySQLQuery(brhSql);
		
		if(brhList == null){
			System.out.println("找不到机构！");
			return ;
		}
		
		for(int i = 0;i <= brhList.size();i++){
			
			String wheresql = "where c.REC_CRT_TS <= '"+ monEnd +"' " +
			"and CONN_TYPE = 'J' " ;
			System.out.println(mchtFlag1);
			if(!StringUtil.isNull(mchtFlag1)){
				wheresql += " and c.mcht_flag1 = '"+ mchtFlag1 +"' "; 
			}
		    
		    String brh = null;
		    if(i==brhList.size()){
		    	brh = "全辖";
		    }else{
		    	brh = InformationUtil.getBrhName((String) brhList.get(i)).trim();
		    	wheresql += " and c.AGR_BR in " + InformationUtil.getBrhGroupString((String) brhList.get(i)); 
		    }
			
			String sql = "select '"+ brh +"'," +
								 "count(1) allNum," +
								 "sum(case when substr(SETTLE_ACCT,0,1) in('A','P') " +
								 			"or substr(SETTLE_ACCT_SND,0,1) in('A','P') then 1 else 0 end) signMcht," +
								 "round(sum(case when substr(SETTLE_ACCT,0,1) in('A','P') " +
								 			"or substr(SETTLE_ACCT_SND,0,1) in('A','P') then 1 else 0 end)*100" +
								 			"/count(1),2) signPer " +
						  "from TBL_MCHT_SETTLE_INF b left outer join " +
						  	   "TBL_MCHT_BASE_INF c on(b.MCHT_NO=c.MCHT_NO) "; 
			sql += wheresql;
			
			int rowNum =3 + i, cellNum = 0,columnNum = 4;
			fillData(sql, rowNum, cellNum, columnNum);
			
			
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
			
			String sql1 = "select round(sum(case when substr(SETTLE_ACCT,0,1) in('A','P') " +
											"or substr(SETTLE_ACCT_SND,0,1) in('A','P') " +
											"then MCHT_AT_C-MCHT_AT_D else 0 end)*100/sum(MCHT_AT_C-MCHT_AT_D),2) transAmtPer," +
								 "round(sum(case when substr(SETTLE_ACCT,0,1) in('A','P') " +
								 			"or substr(SETTLE_ACCT_SND,0,1) in('A','P') " +
								 			"then 1 else 0 end)*100/count(1),2) transPer " +
						  "from TBL_MCHT_SETTLE_INF b join ("+ sqlAlgo +") a on(a.MCHT_CD=b.MCHT_NO) " +
						  							 "left outer join TBL_MCHT_BASE_INF c on(b.MCHT_NO=c.MCHT_NO) ";
			sql1 += wheresql;
			sql1 += " and DATE_SETTLMT >= '"+ monStart +"' and DATE_SETTLMT<= '"+ monEnd +"' ";
			
			rowNum =3 + i; cellNum = 4;columnNum = 2;
			fillData(sql1, rowNum, cellNum, columnNum);
		}
	}
	   


	@Override
	protected String getFileKey() {
		return ExcelName.EN_59;
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