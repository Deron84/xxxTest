package com.huateng.struts.query.action;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.huateng.common.StringUtil;
import com.huateng.struts.query.BaseExcelQueryAction;
import com.huateng.struts.query.ExcelName;
import com.huateng.struts.query.QueryExcelUtil;
import com.huateng.system.util.InformationUtil;

/**
 * project JSBConsole date 2013-4-22
 * 
 * @author 高浩
 */
public class T50905Action extends BaseExcelQueryAction {

	@Override
	protected void deal() {
		year = mon.substring(0, 4);

		String yearStart = year +"0101";
		String monStart = mon + "01";
		int m = Integer.valueOf(mon.substring(4, 6));
		int y = Integer.valueOf(year);
		String monEnd = getMonEnd(m,y,mon);
        
        c = sheet.getRow(1).getCell(1);
        if(c == null){
			sheet.getRow(1).createCell(1);
		}
        if(sheet.getRow(1).getCell(4) == null){
        	sheet.getRow(1).createCell(4);
        }
        String wheresql = " where CONN_TYPE = 'J' and DATE_SETTLMT_8 >= '"+ monStart +"' and DATE_SETTLMT_8 <= '"+ monEnd +"' "; 
        
		if(!StringUtil.isNull(mon)){
			c.setCellValue(mon);
		}
		
		
		String sql = "select (case MCHT_FLAG1 when '0' then '收款商户' " +
											 "when '1' then '老板通商户' " +
											 "when '2' then '分期商户' " +
											 "when '3' then '财务转账商户' " +
											 "when '4' then '项目形式商户' " +
											 "when '5' then '积分业务' " +
											 "when '6' then '其他业务' " +
											 "when '7' then '固话POS商户' end) mchtTp," +
						"thNum,allNum,round(thNum*100/allNum,2) per " +
				"from (select MCHT_FLAG1," +
							 "sum(case when STLM_FLAG in('0','7','A','D','9','8') then 0 else 1 end) thNum," +
							 "count(1) allNum " +
					  "from (select * from BTH_GC_TXN_SUCC " +
					  		"union all " +
					  		"select * from BTH_GC_TXN_SUCC_HIS) a " +
					  "left outer join TBL_MCHT_BASE_INF b on(a.CARD_ACCP_ID=b.MCHT_NO) "; 
		sql += wheresql;
		sql += " group by MCHT_FLAG1 order by MCHT_FLAG1)";
		
		int rowNum =3, cellNum = 0,columnNum = 4;
		fillData(sql, rowNum, cellNum, columnNum);
	}
	   
//	  /*对账平结果标志*/
//	#define STLM_FLG_OK             "0" /*-- 对账结果平*/
//	#define STLM_FLG_SUSPICIOUS_OK  "A" /*-- CUPS可疑交易对平;*/
//
//	/*对账不平结果标志 */
//	#define STLM_CUP_FLG_POSP       "1" /*-- POSP有，CUPS没有*/
//	#define STLM_CUP_FLG_CUPS       "2" /*-- POSP无，CUPS有*/
//	#define STLM_CUP_FLG_DISTRUST   "3" /*-- POSP与CUPS金额不一致;*/
//	#define STLM_HOST_FLG_POSP      "4" /*-- POSP有，HOST没有;*/
//	#define STLM_HOST_FLG_HOST      "5" /*-- POSP无， HOST有;*/
//	#define STLM_HOST_FLG_DISTRUST  "6" /*-- POSP与HOST金额不一致;*/
//	#define STLM_UPD_FLG_POSP      "R" /*-- POSP有，UPD没有;*/
//	#define STLM_UPD_FLG_UPD      "S" /*-- POSP无，UPD有;*/
//	#define STLM_UPD_FLG_DISTRUST  "T" /*-- POSP与UPD金额不一致;*/
//	#define STLM_CUP_FLG_SUSPICIOUS "7" /*-- CUPS可疑交易;*/
//	#define STLM_OFF_FLG_AMT        'Z' /*脱机交易中标志两者金额不同*/
//	#define STLM_OFF_FLG_HEART      'W' /*脱机交易中标志是本地端有的*/
//	#define STLM_OFF_FLG_CUPS       'X' /*脱机交易中标志是银联端有的*/
//	#define STLM_FLG_NOCHK          "8" /*-- 导入未核对交易;*/
//	#define STLM_FLG_RVSL           "9" /* 银联冲正及原交易交易标志;*/
//	#define STLM_FLG_DEL            "D" /* 清理数据标志 */

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
		return ExcelName.EN_55;
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