package com.huateng.struts.query.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.huateng.common.StringUtil;
import com.huateng.struts.query.BaseExcelQueryAction;
import com.huateng.struts.query.ExcelName;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.InformationUtil;

/**
 * project JSBConsole date 2013-4-26
 * 
 * @author 高浩
 */
public class T50906Action extends BaseExcelQueryAction {

	private String year;
	
	private String startMon;

	private String endMon;

	public String getStartMon() {
		return startMon;
	}

	public void setStartMon(String startMon) {
		this.startMon = startMon;
	}

	public String getEndMon() {
		return endMon;
	}

	public void setEndMon(String endMon) {
		this.endMon = endMon;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.huateng.struts.query.BaseExcelQueryAction#deal()
	 */
	@Override
	protected void deal() {
		String startDate = startMon + "01";
		String endDate = getMonEnd(Integer.valueOf(endMon.substring(4, 6)),Integer.valueOf(endMon.substring(0, 4)),endMon); 
		
		//生成日期
		if(sheet.getRow(2).getCell(1) == null){
			sheet.getRow(2).createCell(1);
		}
		sheet.getRow(2).getCell(1).setCellValue(CommonFunction.getCurrentDate().substring(0, 4)+"年"
											   +CommonFunction.getCurrentDate().substring(4, 6)+"月"
											   +CommonFunction.getCurrentDate().substring(6, 8)+"日");
		
		//统计时间段
		if(sheet.getRow(2).getCell(5) == null){
			sheet.getRow(2).createCell(5);
		}
		sheet.getRow(2).getCell(5).setCellValue(startMon.substring(0, 4)+"年"
											   +startMon.substring(4, 6)+"月"
											   +"—"
											   +endMon.substring(0, 4)+"年"
											   +endMon.substring(4, 6)+"月");
		
		String actSql = "select distinct id " +
				"from (select DATE_SETTLMT,MCHT_CD||'-'||TERM_ID id from TBL_ALGO_DTL where txn_num not in('5171', '9103') and C_D_FLG <> '1'  and SUBSTR(TXN_NUM,1,1) <> '3'" +
					  "union all " +
					  "select trim(REC_UPD_TS) DATE_SETTLMT,MCHT_CD||'-'||TERM_ID id from TBL_ALGO_DTL where TXN_NUM in ('5171', '9103') and OLD_TXN_FLG = '1') " +
				"where DATE_SETTLMT >= '"+ startDate +"' and DATE_SETTLMT <= '"+ endDate +"'";
		List list = commQueryDAO.findBySQLQuery(actSql);
		
		String termAct = "";
		if (null != list && !list.isEmpty()) {
			Iterator it = list.iterator();
			while (it.hasNext()) {
				Object o = it.next();
				if (!StringUtil.isNull(o)) {
					termAct += "'" + o + "',";
				}
			}
		}
		termAct = "(" + termAct.substring(0,termAct.length()-1) + ")";
		
		int rowNum =0, cellNum = 0,columnNum = 0;
		
		
		/*************境内************/
		String sql1 = null;
		for(int i =0;i<=212;i++){
			if(map.get(i+"") != null){
				sql1 = "select sum(case when b.MCHT_FLAG1 in ('0','1','7') and  a.TERM_STA in ('2','3','4','5','6','R') then 1 else 0 end) allNum," +
							  "sum(case when a.USING_DATE >= '"+ startDate +"' " +
							  			"and a.USING_DATE <= '"+ endDate +"' then 1 else 0 end) addNum," +
							  "sum(case when a.TERM_STA in ('7','9') and b.MCHT_FLAG1 in ('0','1','7')" +
							  			"and a.REC_UPD_TS >= '"+ startDate +"' " +
							  			"and a.REC_UPD_TS <= '"+ endDate +"' then 1 else 0 end) rp," +
							  "sum(case when TERM_TP !='2' and b.MCHT_FLAG1 in ('0','1','7') and  a.TERM_STA in ('2','3','4','5','6','R') then 1 else 0 end) allPosNum," +
							  "sum(case when TERM_TP !='2' and b.MCHT_FLAG1 in ('0','1','7') and a.TERM_STA in ('2','3','4','5','6','R') " +
							  			"and a.USING_DATE >= '"+ startDate +"' " +
							  			"and a.USING_DATE <= '"+ endDate +"' then 1 else 0 end) addPosNum," +
							  "sum(case when TERM_TP !='2' and b.MCHT_FLAG1 in ('0','1','7') and  a.TERM_STA in ('2','3','4','5','6','R') and trim(MCHT_CD)||'-'||trim(TERM_ID) in "+ termAct +" " +
							  	  "then 1 else 0 end) actPosNum," +
							  "sum(case when TERM_TP !='2' and b.MCHT_FLAG1 in ('0','1','7') and  a.TERM_STA in ('2','3','4','5','6','R') and trim(MCHT_CD)||'-'||trim(TERM_ID) not in "+ termAct +" " +
							  	  "then 1 else 0 end) sleepPos," +
							  "sum(case when TERM_TP ='2' and b.MCHT_FLAG1 = '7' and  a.TERM_STA in ('2','3','4','5','6','R') then 1 else 0 end) eposAll," +
							  "sum(case when TERM_TP ='2' and b.MCHT_FLAG1 = '7' and  a.TERM_STA in ('2','3','4','5','6','R') " +
							  			"and a.USING_DATE >= '"+ startDate +"' " +
							  			"and a.USING_DATE <= '"+ endDate +"' then 1 else 0 end) eposAdd " +
						"from " +
							  "TBL_TERM_INF a join TBL_MCHT_BASE_INF b on(a.MCHT_CD=b.MCHT_NO) " +
						"where b.mcht_flag1 in('0','1','7') and CONN_TYPE = 'J' and a.USING_DATE <= '"+ endDate +"' " +
							  "and b.SIGN_INST_ID in "+ InformationUtil.getBrhGroupString(map.get(i+"")) +" "; 
		
				rowNum =5 + i; cellNum = 3;columnNum = 9;
				fillData(sql1, rowNum, cellNum, columnNum);
				
				//浙江省只包含一个城市，小计时直接插入
				if(i == 93){
					rowNum =109;
					fillData(sql1, rowNum, cellNum, columnNum);
				}
				
				//广东省只包含一个城市，小计时直接插入
				if(i == 212){
					rowNum =236;
					fillData(sql1, rowNum, cellNum, columnNum);
				}
			}
		}
		
		//江苏省小计
		for(int i =0;i<=8;i++){
			double count = 0;
			for(int j =0;j<=12;j++){
				if(sheet.getRow(97).getCell(3+i) == null){
					sheet.getRow(97).createCell(3+i);
				}
				count += sheet.getRow(84+j).getCell(3+i).getNumericCellValue();
			}
			sheet.getRow(97).getCell(3+i).setCellValue(count);
		}
		
		
		
		/*************受理卡介质************
		 * 因为都是可受理芯片卡终端，直接
		 * 
		 * 
		 * 
		 * *****/
		String sql2 = "select sum(case when b.MCHT_FLAG1 in ('0','1','7') and  a.TERM_STA in ('2','3','4','5','6','R') then 1 else 0 end) allNum," +
							 "sum(case when a.USING_DATE >= '"+ startDate +"' " +
							 		   "and a.USING_DATE <= '"+ endDate +"' then 1 else 0 end) addNum," +
							 "sum(case when a.TERM_STA in ('7','9') and b.MCHT_FLAG1 in ('0','1') " +
							  			"and a.REC_UPD_TS >= '"+ startDate +"' " +
							  			"and a.REC_UPD_TS <= '"+ endDate +"' then 1 else 0 end) rp," +
							 "sum(case when TERM_TP !='2' and b.MCHT_FLAG1 in ('0','1','7') and  a.TERM_STA in ('2','3','4','5','6','R') then 1 else 0 end) allPosNum," +
							 "sum(case when TERM_TP !='2' and b.MCHT_FLAG1 in ('0','1','7') and  a.TERM_STA in ('2','3','4','5','6','R') " +
							 		   "and a.USING_DATE >= '"+ startDate +"' " +
							 		   "and a.USING_DATE <= '"+ endDate +"' then 1 else 0 end) addPosNum," +
							 "sum(case when TERM_TP !='2' and b.MCHT_FLAG1 in ('0','1','7') and  a.TERM_STA in ('2','3','4','5','6','R') and trim(MCHT_CD)||'-'||trim(TERM_ID) in "+ termAct +" then 1 else 0 end) actPosNum," +
							 "sum(case when TERM_TP !='2' and b.MCHT_FLAG1 in ('0','1','7') and  a.TERM_STA in ('2','3','4','5','6','R') and trim(MCHT_CD)||'-'||trim(TERM_ID) not in "+ termAct +" then 1 else 0 end) sleepPos," +
							 "sum(case when TERM_TP ='2' and b.MCHT_FLAG1 = '7' and a.TERM_STA in ('2','3','4','5','6','R') then 1 else 0 end) eposAll," +
							 "sum(case when TERM_TP ='2' and b.MCHT_FLAG1 = '7' and a.TERM_STA in ('2','3','4','5','6','R') " +
							 		   "and a.USING_DATE >= '"+ startDate +"' " +
							 		   "and a.USING_DATE <= '"+ endDate +"' then 1 else 0 end) eposAdd " +
					  "from TBL_TERM_INF a join TBL_MCHT_BASE_INF b on(a.MCHT_CD=b.MCHT_NO) " +
						"where CONN_TYPE = 'J' and b.mcht_flag1 in('0','1','7') and a.USING_DATE <= '"+ endDate +"' "; 

		rowNum =382; cellNum = 3;columnNum = 9;
		fillData(sql2, rowNum, cellNum, columnNum);
		
		//小计，仅一类所以直接插入
		rowNum =383; cellNum = 3;columnNum = 9;
		fillData(sql2, rowNum, cellNum, columnNum);
		//境内统计
		rowNum =371; cellNum = 3;columnNum = 9;
		fillData(sql2, rowNum, cellNum, columnNum);
		
		/*************POS通讯方式************/
		//固定POS-1
		String sql3 = "select sum(case when TERM_TP ='1' and b.MCHT_FLAG1 in ('0','1','7') and  a.TERM_STA in ('2','3','4','5','6','R') then 1 else 0 end) allPosNum," +
							 "sum(case when TERM_TP ='1' and b.MCHT_FLAG1 in ('0','1','7') and  a.TERM_STA in ('2','3','4','5','6','R') " +
							 		   "and a.USING_DATE >= '"+ startDate +"' " +
							 		   "and a.USING_DATE <= '"+ endDate +"' then 1 else 0 end) addPosNum," +
							 "sum(case when TERM_TP ='1' and b.MCHT_FLAG1 in ('0','1','7') and  a.TERM_STA in ('2','3','4','5','6','R') and trim(MCHT_CD)||'-'||trim(TERM_ID) in "+ termAct +" then 1 else 0 end) actPosNum," +
							 "sum(case when TERM_TP ='1' and b.MCHT_FLAG1 in ('0','1','7') and  a.TERM_STA in ('2','3','4','5','6','R') and trim(MCHT_CD)||'-'||trim(TERM_ID) not in "+ termAct +" then 1 else 0 end) sleepPos " +
					  "from TBL_TERM_INF a join TBL_MCHT_BASE_INF b on(a.MCHT_CD=b.MCHT_NO) " +
						"where CONN_TYPE = 'J' and b.mcht_flag1 in('0','1','7') and a.USING_DATE <= '"+ endDate +"'"; 

		rowNum =384; cellNum = 6;columnNum = 4;
		fillData(sql3, rowNum, cellNum, columnNum);
		
		//移动POS-0
		String sql4 = "select sum(case when TERM_TP ='0' and b.MCHT_FLAG1 in ('0','1','7') and  a.TERM_STA in ('2','3','4','5','6','R') then 1 else 0 end) allPosNum," +
							 "sum(case when TERM_TP ='0' and b.MCHT_FLAG1 in ('0','1','7') and  a.TERM_STA in ('2','3','4','5','6','R') " +
							 		   "and a.USING_DATE >= '"+ startDate +"' " +
							 		   "and a.USING_DATE <= '"+ endDate +"' then 1 else 0 end) addPosNum," +
							 "sum(case when TERM_TP ='0' and b.MCHT_FLAG1 in ('0','1','7') and  a.TERM_STA in ('2','3','4','5','6','R') and trim(MCHT_CD)||'-'||trim(TERM_ID) in "+ termAct +" then 1 else 0 end) actPosNum," +
							 "sum(case when TERM_TP ='0' and b.MCHT_FLAG1 in ('0','1','7') and  a.TERM_STA in ('2','3','4','5','6','R') and trim(MCHT_CD)||'-'||trim(TERM_ID) not in "+ termAct +" then 1 else 0 end) sleepPos " +
					  "from TBL_TERM_INF a join TBL_MCHT_BASE_INF b on(a.MCHT_CD=b.MCHT_NO) " +
						"where CONN_TYPE = 'J' and b.mcht_flag1 in('0','1','7') and a.USING_DATE <= '"+ endDate +"'"; 

		rowNum =385; cellNum = 6;columnNum = 4;
		fillData(sql4, rowNum, cellNum, columnNum);
		
		//小计
		for(int i = 0;i<=3;i++){
			if(sheet.getRow(386).getCell(6+i) == null){
				sheet.getRow(386).createCell(6+i);
			}
			double count = sheet.getRow(384).getCell(6+i).getNumericCellValue() 
			  			 + sheet.getRow(385).getCell(6+i).getNumericCellValue();
			sheet.getRow(386).getCell(6+i).setCellValue(count);
		}
		
		/*************电话终端类型************/
		//Ⅱ型电话支付终端
		String sql5 = "select sum(case when TERM_TP ='2' and a.TERM_STA in ('2','3','4','5','6','R') then 1 else 0 end) allPosNum," +
							 "sum(case when TERM_TP ='2' and a.TERM_STA in ('2','3','4','5','6','R') " +
										   "and a.USING_DATE >= '"+ startDate +"' " +
										   "and a.USING_DATE <= '"+ endDate +"' then 1 else 0 end) addPosNum " +
					  "from TBL_TERM_INF a join TBL_MCHT_BASE_INF b on(a.MCHT_CD=b.MCHT_NO) " +
						"where CONN_TYPE = 'J' and b.mcht_flag1 = '7' and a.USING_DATE <= '"+ endDate +"'"; 

		rowNum =388; cellNum = 10;columnNum = 2;
		fillData(sql5, rowNum, cellNum, columnNum);
		
		//小计
		rowNum =389; cellNum = 10;columnNum = 2;
		fillData(sql5, rowNum, cellNum, columnNum);
		
		/*************商户类别************/
		for(int i=0;i<=12;i++){
			String sql6 = "select sum(case when TERM_TP !='2' and b.MCHT_FLAG1 in ('0','1','7') and  a.TERM_STA in ('2','3','4','5','6','R') then 1 else 0 end) allPosNum," +
								 "sum(case when TERM_TP !='2' and b.MCHT_FLAG1 in ('0','1','7') and  a.TERM_STA in ('2','3','4','5','6','R') " +
								 		   "and a.USING_DATE >= '"+ startDate +"' " +
								 		   "and a.USING_DATE <= '"+ endDate +"' then 1 else 0 end) addPosNum," +
								 "sum(case when TERM_TP !='2' and b.MCHT_FLAG1 in ('0','1','7') and  a.TERM_STA in ('2','3','4','5','6','R') and trim(MCHT_CD)||'-'||trim(TERM_ID) in "+ termAct +" then 1 else 0 end) actPosNum," +
								 "sum(case when TERM_TP !='2' and b.MCHT_FLAG1 in ('0','1','7') and  a.TERM_STA in ('2','3','4','5','6','R') and trim(MCHT_CD)||'-'||trim(TERM_ID) not in "+ termAct +" then 1 else 0 end) sleepPos " +
						  "from " +
						  		 "TBL_TERM_INF a join TBL_MCHT_BASE_INF b on(a.MCHT_CD=b.MCHT_NO) " +
						  "where CONN_TYPE = 'J' and b.mcht_flag1 in('0','1','7') and a.USING_DATE <= '"+ endDate +"' " +
						  		"and TERM_MCC in (select MCHNT_TP " +
						  					 "from TBL_INF_MCHNT_TP " +
						  					 "where MCHNT_TP_GRP ='" +
						  					 CommonFunction.fillString(i+1+"", '0', 2, false) +"') "; 

			rowNum =390 + i; cellNum = 6;columnNum = 4;
			fillData(sql6, rowNum, cellNum, columnNum);
		}
		
		//合计
		for(int i=0;i<=3;i++){
			double count = 0;
			for(int j =0;j<=12;j++){
				if(sheet.getRow(390 + j).getCell(6+i) == null){
					sheet.getRow(390 + j).createCell(6+i);
				}
				count += sheet.getRow(390 + j).getCell(6+i).getNumericCellValue();
			}
			sheet.getRow(403).getCell(6+i).setCellValue(count);
		}
		
	}

	private static final HashMap<String, String> map = new HashMap<String, String>();
	static {
		map.put("0", "3201");//北京市
		map.put("78", "1801");//上海市
		map.put("79", "9801");//南京市
		map.put("80", "0201");//无锡市
		map.put("81", "0601");//徐州市
		map.put("82", "0801");//常州市
		map.put("83", "0301");//苏州市
		map.put("84", "0501");//南通市
		map.put("85", "1101");//连云港市
		map.put("86", "1001");//淮安市
		map.put("87", "1201");//盐城市
		map.put("88", "0901");//扬州市
		map.put("89", "0701");//镇江市
		map.put("90", "1601");//泰州市
		map.put("91", "1501");//宿迁市
		map.put("93", "3301");//杭州市
		map.put("212", "1901");//深圳市
	}
	

	@Override
	protected String getFileKey() {
		return ExcelName.EN_56;
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