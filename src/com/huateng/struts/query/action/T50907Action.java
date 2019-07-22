package com.huateng.struts.query.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
public class T50907Action extends BaseExcelQueryAction {
	
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
		if(sheet.getRow(2).getCell(3) == null){
			sheet.getRow(2).createCell(3);
		}
		sheet.getRow(2).getCell(3).setCellValue(startMon.substring(0, 4)+"年"
											   +startMon.substring(4, 6)+"月"
											   +"—"
											   +endMon.substring(0, 4)+"年"
											   +endMon.substring(4, 6)+"月");
		
		String actSql = "select distinct MCHT_CD " +
				"from (select DATE_SETTLMT,MCHT_CD from TBL_ALGO_DTL where txn_num not in('5171', '9103') and C_D_FLG <> '1' and SUBSTR(TXN_NUM,1,1) <> '3' " +
					  "union all " +
					  "select trim(REC_UPD_TS) DATE_SETTLMT,MCHT_CD from TBL_ALGO_DTL where TXN_NUM in ('5171', '9103') and OLD_TXN_FLG = '1') "
				+ "where DATE_SETTLMT >= '" + startDate
				+ "' and DATE_SETTLMT <= '" + endDate + "'";
		List list = commQueryDAO.findBySQLQuery(actSql);

		String mchtAct = "";
		if (null != list && !list.isEmpty()) {
			Iterator it = list.iterator();
			while (it.hasNext()) {
				Object o = it.next();
				if (!StringUtil.isNull(o)) {
					mchtAct += "'" + o + "',";
				}
			}
		}
		mchtAct = "(" + mchtAct.substring(0,mchtAct.length()-1) + ")";
		int rowNum =0, cellNum = 0,columnNum = 0;
		
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
		
		/*************境内************/
		//#############银行卡收单业务量###########################################
		String sql1 = null;
		for(int i =0;i<=212;i++){
			if(map.get(i+"") != null){
				sql1 = "select count(1) inCount," +
							"sum(MCHT_AT_C - MCHT_AT_D) inSum," +
							"0,0," +
							"count(1) allCount," +
							"sum(MCHT_AT_C - MCHT_AT_D) allSum " +
						"from " +
							"("+sqlAlgo+") a left outer join " +
							"TBL_MCHT_BASE_INF b " +
							"on(a.MCHT_CD = b.MCHT_NO) " +
						"where CONN_TYPE = 'J' and b.mcht_flag1 in('0','1','7') and b.SIGN_INST_ID in " 
							+ InformationUtil.getBrhGroupString(map.get(i+"")) +
							"and DATE_SETTLMT >= '"+ startDate +"' " +
							"and DATE_SETTLMT <= '"+ endDate +"'"; 
		
				rowNum =6 + i; cellNum = 3;columnNum = 6;
				fillData(sql1, rowNum, cellNum, columnNum);
				
				//浙江省只包含一个城市，小计时直接插入
				if(i == 93){
					rowNum =110;
					fillData(sql1, rowNum, cellNum, columnNum);
				}
				
				//广东省只包含一个城市，小计时直接插入
				if(i == 212){
					rowNum =237;
					fillData(sql1, rowNum, cellNum, columnNum);
				}
			}
		}
		
		//江苏省小计
		for(int i =0;i<=5;i++){
			double count = 0;
			for(int j =0;j<=12;j++){
				if(sheet.getRow(98).getCell(3+i) == null){
					sheet.getRow(98).createCell(3+i);
				}
				count += sheet.getRow(85+j).getCell(3+i).getNumericCellValue();
			}
			sheet.getRow(98).getCell(3+i).setCellValue(count);
		}
		
		//合计
		for(int i =0;i<=5;i++){
			double count = 0;
			if(sheet.getRow(372).getCell(3+i) == null){
				sheet.getRow(372).createCell(3+i);
			}
			count = sheet.getRow(6).getCell(3+i).getNumericCellValue() 
				  + sheet.getRow(84).getCell(3+i).getNumericCellValue()
				  + sheet.getRow(98).getCell(3+i).getNumericCellValue() 
				  + sheet.getRow(110).getCell(3+i).getNumericCellValue() 
				  + sheet.getRow(237).getCell(3+i).getNumericCellValue();
			
			sheet.getRow(372).getCell(3+i).setCellValue(count);
		}
		
		//################特约商户数量#########################################
		for(int i =0;i<=212;i++){
			if(map.get(i+"") != null){
				String sql2 = "select sum(case when b.MCHT_FLAG1 in ('0','1','7') and  a.TERM_STA in ('2','3','4','5','6','R') then 1 else 0 end) allNum," +
							"sum(case when substr(b.REC_CRT_TS,0,8) >= '"+ startDate +"' " +
									  "then 1 else 0 end) addNum," +
							"sum(case when MCHT_NO in "+ mchtAct +" then 1 else 0 end) actNum," +
							"sum(case when MCHT_NO not in "+ mchtAct +" then 1 else 0 end) sleep," +
							"sum(case when b.MCHT_FLAG1 in ('0','1','7') and MCHT_STATUS = '9' then 1 else 0 end) cancle " +
						"from TBL_MCHT_BASE_INF b join TBL_TERM_INF a on (a.MCHT_CD=b.MCHT_NO) " +
						"where CONN_TYPE = 'J' and b.mcht_flag1 in('0','1','7') and substr(b.REC_CRT_TS,0,8) <= '"+ endDate +"' and " +
								"b.SIGN_INST_ID in " + InformationUtil.getBrhGroupString(map.get(i+""));
		
				rowNum =6 + i; cellNum = 9;columnNum = 5;
				fillData(sql2, rowNum, cellNum, columnNum);
				
				//浙江省只包含一个城市，小计时直接插入
				if(i == 93){
					rowNum =110;
					fillData(sql2, rowNum, cellNum, columnNum);
				}
				
				//广东省只包含一个城市，小计时直接插入
				if(i == 212){
					rowNum =237;
					fillData(sql2, rowNum, cellNum, columnNum);
				}
			}
		}
		
		//江苏省小计
		for(int i =0;i<=4;i++){
			double count = 0;
			for(int j =0;j<=12;j++){
				if(sheet.getRow(98).getCell(9 + i) == null){
					sheet.getRow(98).createCell(9 + i);
				}
				count += sheet.getRow(85+j).getCell(9 + i).getNumericCellValue();
			}
			sheet.getRow(98).getCell(9+i).setCellValue(count);
		}
		
		//合计
		for(int i =0;i <= 4;i++){
			double count = 0;
			if(sheet.getRow(372).getCell(9 + i) == null){
				sheet.getRow(372).createCell(9 + i);
			}
			count = sheet.getRow(6).getCell(9+i).getNumericCellValue() 
				  + sheet.getRow(84).getCell(9+i).getNumericCellValue()
				  + sheet.getRow(98).getCell(9+i).getNumericCellValue() 
				  + sheet.getRow(110).getCell(9+i).getNumericCellValue() 
				  + sheet.getRow(237).getCell(9+i).getNumericCellValue();
			
			sheet.getRow(372).getCell(9 + i).setCellValue(count);
		}
		
		
		/*************商户类别************/
		//#############银行卡收单业务量###########################################
		for(int i=0;i<=12;i++){
			String sql6 = "select count(1) inCount," +
								"sum(MCHT_AT_C - MCHT_AT_D) inSum," +
								"0,0," +
								"count(1) allCount," +
								"sum(MCHT_AT_C - MCHT_AT_D) allSum " +
						  "from " +
								"("+sqlAlgo+") a left outer join " +
								"TBL_MCHT_BASE_INF b " +
								"on(a.MCHT_CD = b.MCHT_NO) " +
						  "where CONN_TYPE = 'J' and b.mcht_flag1 in('0','1','7') and b.MCC in (select MCHNT_TP " +
						  					 "from TBL_INF_MCHNT_TP " +
						  					 "where MCHNT_TP_GRP ='" +
						  					 CommonFunction.fillString(i+1+"", '0', 2, false) +"') " +
						  		"and DATE_SETTLMT >= '"+ startDate +"' " +
								"and DATE_SETTLMT <= '"+ endDate +"'";

			rowNum =382 + i; cellNum = 3;columnNum = 6;
			fillData(sql6, rowNum, cellNum, columnNum);
		}
		
		//合计
		for(int i=0;i <= 5;i++){
			double count = 0;
			for(int j =0;j<=12;j++){
				if(sheet.getRow(382 + j).getCell(3 + i) == null){
					sheet.getRow(382 + j).createCell(3 + i);
				}
				count += sheet.getRow(382 + j).getCell(3 + i).getNumericCellValue();
			}
			sheet.getRow(395).getCell(3 + i).setCellValue(count);
		}
		
		
		//################特约商户数量#########################################
		for(int i=0;i<=12;i++){
			String sql7 =  "select sum(case when b.MCHT_FLAG1 in ('0','1','7') and  a.TERM_STA in ('2','3','4','5','6','R') then 1 else 0 end) allNum," +
								"sum(case when substr(b.REC_CRT_TS,0,8) >= '"+ startDate +"' " +
										  "and substr(b.REC_CRT_TS,0,8) <= '"+ endDate +"' " +
										  "then 1 else 0 end) addNum," +
								"sum(case when MCHT_NO in "+ mchtAct +" then 1 else 0 end) actNum," +
								"sum(case when MCHT_NO not in "+ mchtAct +" then 1 else 0 end) sleep," +
								"sum(case when b.MCHT_FLAG1 in ('0','1','7') and MCHT_STATUS = '9' then 1 else 0 end) cancle " +
							"from TBL_MCHT_BASE_INF b join TBL_TERM_INF a on (a.MCHT_CD=b.MCHT_NO) " +
							"where CONN_TYPE = 'J' and b.mcht_flag1 in('0','1','7') and substr(b.REC_CRT_TS,0,8) <= '"+ endDate +"' " +
									"and b.MCC in (select MCHNT_TP " +
						  					 "from TBL_INF_MCHNT_TP " +
						  					 "where MCHNT_TP_GRP ='" +
						  					 CommonFunction.fillString(i+1+"", '0', 2, false) +"') ";

			rowNum =382 + i; cellNum = 9;columnNum = 5;
			fillData(sql7, rowNum, cellNum, columnNum);
		}
		
		//合计
		for(int i=0;i <= 4;i++){
			double count = 0;
			for(int j =0;j<=12;j++){
				if(sheet.getRow(382 + j).getCell(9 + i) == null){
					sheet.getRow(382 + j).createCell(9 + i);
				}
				count += sheet.getRow(382 + j).getCell(9 + i).getNumericCellValue();
			}
			sheet.getRow(395).getCell(9 + i).setCellValue(count);
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
		return ExcelName.EN_57;
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