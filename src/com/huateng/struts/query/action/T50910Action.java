package com.huateng.struts.query.action;

import java.util.List;

import com.huateng.common.StringUtil;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.struts.query.BaseExcelQueryAction;
import com.huateng.struts.query.ExcelName;
import com.huateng.struts.query.QueryExcelUtil;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.InformationUtil;

/**
 * project JSBConsole date 2013-4-19
 * 
 * @author dats.xu
 */
public class T50910Action extends BaseExcelQueryAction {

	private String year;

	private String mon;

	private String brhBelow;

	private String brhId;

	private String date;

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
	protected void deal() {
		year = mon.substring(0, 4);
		
		
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
		
		int rowNum = 3, cellNum = 0, columnNum = 11,count =0;
		for(int i = 0;i <= brhList.size();i++){
			String whereSql = " where CONNECT_MODE = 'J' and substr(USING_DATE, 0, 6) = '"+mon+"' "; 
			String brh = null;
			if(i==brhList.size()){
				brh = "全辖";
			}else{
				whereSql += " and TERM_BRANCH in "+InformationUtil.getBrhGroupString(brhList.get(i).toString()); 
				brh = InformationUtil.getBrhName((String) brhList.get(i)).trim();
			}
			
			String sql ="select (case when prop_ins_nm is null then '本行' else (select ORG_NAME from TBL_PROFESSIONAL_ORGAN b where a.prop_ins_nm=b.ORG_ID) end) propNm," +
						"sum(1) allNum," +
						"sum(case when USING_DATE >=REC_CRT_TS and USING_DATE <= to_char((to_date(REC_CRT_TS,'yyyymmdd') + 3),'yyyymmdd') then 1 else 0 end) in3," +
						"sum(case when USING_DATE >=REC_CRT_TS and USING_DATE <= to_char((to_date(REC_CRT_TS,'yyyymmdd') + 7),'yyyymmdd') then 1 else 0 end) in7," +
						"sum(case when USING_DATE >=REC_CRT_TS and USING_DATE <= to_char((to_date(REC_CRT_TS,'yyyymmdd') + 15),'yyyymmdd') then 1 else 0 end) in15," +
						"sum(case when USING_DATE > to_char((to_date(REC_CRT_TS,'yyyymmdd') + 15),'yyyymmdd') then 1 else 0 end) ot15 " +
					"from tbl_term_inf a " +
					whereSql +
					" group by prop_ins_nm order by prop_ins_nm";
			
			sql = "select '"+brh+"',propNm,allNum," +
					"in3,(case when allNum=0 and in3=0 then 0 when allNum=0 and in3!=0 then 100 when allNum!=0 then round(in3*100/allNum,2) end)," +
					"in7,(case when allNum=0 and in7=0 then 0 when allNum=0 and in7!=0 then 100 when allNum!=0 then round(in7*100/allNum,2) end)," +
					"in15,(case when allNum=0 and in15=0 then 0 when allNum=0 and in15!=0 then 100 when allNum!=0 then round(in15*100/allNum,2) end)," +
					"ot15,(case when allNum=0 and ot15=0 then 0 when allNum=0 and ot15!=0 then 100 when allNum!=0 then round(ot15*100/allNum,2) end) " +
				"from ("+sql+")";

			count =fillData(sql, rowNum, cellNum, columnNum);
			rowNum += count;
		}
		int[] cellNums = new int[]{0};
		consolidate(cellNums);
	}

	@Override
	protected String getFileKey() {
		return ExcelName.EN_510;
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
