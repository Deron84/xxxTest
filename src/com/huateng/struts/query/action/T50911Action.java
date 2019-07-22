package com.huateng.struts.query.action;

import java.util.List;

import com.huateng.common.StringUtil;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.struts.query.BaseExcelQueryAction;
import com.huateng.struts.query.ExcelName;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.InformationUtil;

/**
 * project JSBConsole date 2013-4-2
 * 
 * @author 樊东东
 */
public class T50911Action extends BaseExcelQueryAction {

	private String year;

	private String mon;

	public String getMon() {
		return mon;
	}

	public void setMon(String mon) {
		this.mon = mon;
	}
	
	
	@Override
	protected void deal() {
		year = mon.substring(0, 4);
		String monStart = mon + "01";
		int m = Integer.valueOf(mon.substring(4, 6));
		int y = Integer.valueOf(year);
		String monEnd = getMonEnd(m,y,mon);
		
		c = sheet.getRow(0).getCell(3);
		if(c == null){
			sheet.getRow(0).createCell(3);
		}
		if(!StringUtil.isNull(mon)){
			c.setCellValue(mon);
		}
		
		//地区按分行统计
		String brhSql = "select brh_id from tbl_brh_info where brh_level = '1' order by brh_id";
		
		ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
		List brhList = commQueryDAO.findBySQLQuery(brhSql);
		
		int rowNum = 3, cellNum = 0, columnNum = 13,count=0;
		for(int i = 0;i <= brhList.size();i++){
			String whereSql = " where substr(STOR_DATE,0,6) <='"+ mon +"' "; 
			String brh = null;
			if(i==brhList.size()){
				brh = "全辖";
			}else{
				whereSql += " and BRH_ID = '"+ brhList.get(i).toString().trim() +"' "; 
				brh = InformationUtil.getBrhName((String) brhList.get(i)).trim();
			}
			
			String sql = "select brh_id,(select VALUE from CST_SYS_PARAM where TRIM(OWNER) = substr(terminal_type,0,instr(terminal_type,'-',1)-1)  and KEY = substr(terminal_type,instr(terminal_type,'-',1)+1)) terminal_type," +
					  			   "sum(1) as c1," +
					  			   "sum(case when t.mech_no is not null " +
					  			   			 "and OUT_STORE_DATE <= '"+ monEnd +"' then 1 else 0 end) as c2," +
					  			   "sum(case when t.mech_no is not null and floor(to_date(t.out_store_date,'yyyymmdd')-to_date(t.stor_date,'yyyymmdd'))<=183 then 1 else 0 end) as c3," +
					  			   "sum(case when t.mech_no is not null and floor(to_date(t.out_store_date,'yyyymmdd')-to_date(t.stor_date,'yyyymmdd'))<=365 then 1 else 0 end) as c4," +
					  			   "sum(case when t.mech_no is not null and floor(to_date(t.out_store_date,'yyyymmdd')-to_date(t.stor_date,'yyyymmdd'))<=730 then 1 else 0 end) as c5," +
					  			   "sum(case when t.mech_no is null and floor(to_date(t.out_store_date,'yyyymmdd')-to_date(t.stor_date,'yyyymmdd'))>730 then 1 else 0 end) as c6 " +
					  		"from TBL_TERM_MANAGEMENT t " +
					  			 whereSql +
					  			 " group by brh_id,terminal_type order by brh_id";
			sql = "select '"+brh+"',terminal_type,c1,c2," +
					  	 	 "round((case when t1.c1 =0 and t1.c2=0 then 0 when t1.c1 =0 and t1.c2 !=0 then 100 when t1.c1 !=0 then t1.c2*100/t1.c1 end),2) as s3,t1.c3 as s4," +
							 "round((case when t1.c1 =0 and t1.c3=0 then 0 when t1.c1 =0 and t1.c3 !=0 then 100 when t1.c1 !=0 then t1.c3*100/t1.c1 end),2) as s5,t1.c4 as s6," +
							 "round((case when t1.c1 =0 and t1.c4=0 then 0 when t1.c1 =0 and t1.c4 !=0 then 100 when t1.c1 !=0 then t1.c4*100/t1.c1 end),2) as s7,t1.c5 as s8," +
							 "round((case when t1.c1 =0 and t1.c5=0 then 0 when t1.c1 =0 and t1.c5 !=0 then 100 when t1.c1 !=0 then t1.c5*100/t1.c1 end),2) as s9,t1.c6 as s10," +
							 "round((case when t1.c1 =0 and t1.c6=0 then 0 when t1.c1 =0 and t1.c6 !=0 then 100 when t1.c1 !=0 then t1.c6*100/t1.c1 end),2) as s11 " +
					  "from ("+ sql +") t1 ";
			count = fillData(sql, rowNum, cellNum, columnNum);
			rowNum += count;
		}
		
		//重复的行 合并单元格
		int[] cellNums = new int[]{0};
		consolidate(cellNums);
		
	}

	@Override
	protected String getFileKey() {
		return ExcelName.EN_511;
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
