package com.huateng.struts.query.action;

import java.util.List;

import com.huateng.common.StringUtil;
import com.huateng.struts.query.BaseExcelQueryAction;
import com.huateng.struts.query.ExcelName;
import com.huateng.struts.query.QueryExcelUtil;
import com.huateng.system.util.InformationUtil;

/**
 * project JSBConsole date 2013-4-19
 * 
 * @author 高浩
 */
public class T50903Action extends BaseExcelQueryAction {

	private String year;

	private String mon;
	
	private String name;

	private String date;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Override
	protected void deal() {
		year = date.substring(0, 4);
		mon = date.substring(0, 6);

		String yearStart = year +"0101";
		String monStart = mon + "01";
		String wheresql = "where b.MARKETER_NAME is not null and CONN_TYPE = 'J' ";
		
		c = sheet.getRow(1).getCell(4);
		if(c == null){
			sheet.getRow(1).createCell(4);
		}
		if(sheet.getRow(1).getCell(7) == null){
			sheet.getRow(1).createCell(7);
		}
		
		if(StringUtil.isNull(brhId)){
			brhId = operator.getOprBrhId();
		}
		if(!"0000".equals(brhId)){
			wheresql += " and b.AGR_BR in"+ InformationUtil.getBrhGroupString(brhId);
		}
		sheet.getRow(1).getCell(7).setCellValue(InformationUtil.getBrhName(brhId));
		
		if(!StringUtil.isNull(date)){
			c.setCellValue(date);
		}
		
		if(!StringUtil.isNull(name)){
			sheet.getRow(1).getCell(2).setCellValue(name);
			wheresql += " and b.MARKETER_NAME = '"+ name +"' ";
		}
		
		String sql = "select b.MARKETER_NAME,b.MCHT_NO,b.mcht_flag1,b.mcht_flag2,b.MCHT_NM,b.LICENCE_NO,c.brh_name," +
						"(select d.brh_name from tbl_brh_info d where b.SIGN_INST_ID=d.BRH_ID) upBrh," +
						"(select count(*) from TBL_TERM_INF d where d.MCHT_CD = b.MCHT_NO) termNum,b.REC_CRT_TS," +
						"sum(TXN_AMT) dayAmt," +
						"sum(TXN_NUM) dayNum," +
						"sum(POSP_PROCEEDS) daySettle " +
				"from (select * from TBL_MCHNT_INFILE_DTL where DATE_SETTLMT = '"+ date +"' " +
					  "union all " +
					  "select * from TBL_MCHNT_INFILE_DTL_HIS where DATE_SETTLMT = '"+ date +"') a right outer join " +
					 "TBL_MCHT_BASE_INF b on(a.MCHT_NO=b.MCHT_NO) left outer join " +
					 "tbl_brh_info c on(b.AGR_BR=c.BRH_ID) "; 
		sql += wheresql;
		sql += " group by b.MCHT_NO,MARKETER_NAME,b.mcht_flag1,b.mcht_flag2,b.MCHT_NM,b.LICENCE_NO,c.brh_name,b.SIGN_INST_ID,b.REC_CRT_TS ";
		
		
		sql = "select MARKETER_NAME,t.MCHT_NO," +
		      "(case t.mcht_flag1 when '0' then '收款商户' " +
              "when '1' then '老板通商户' " +
              "when '2' then '分期商户' " +
              "when '3' then '财务转账商户' " +
              "when '4' then '项目形式商户' " +
              "when '5' then '积分业务' " +
              "when '6' then '其他业务' " +
              "when '7' then '固话POS商户' end) mchtFlag1," +
              "(case t.mcht_flag2 when '00' then '线上商户' " +
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
				 "when '42' then '其他项目' end) mchtFlag2, " +
				"t.MCHT_NM,LICENCE_NO,brh_name,upBrh,termNum,t.REC_CRT_TS," +
						"dayAmt,dayNum,daySettle," +
						"sum(case when DATE_SETTLMT >= '"+ monStart +"' then d.TXN_AMT else 0 end) monAmt," +
						"sum(case when DATE_SETTLMT >= '"+ monStart +"' then d.TXN_NUM else 0 end) monNum," +
						"sum(case when DATE_SETTLMT >= '"+ monStart +"' then d.POSP_PROCEEDS else 0 end) monMon," +
						"sum(d.TXN_AMT) yearAmt," +
						"sum(d.TXN_NUM) yearNum," +
						"sum(d.POSP_PROCEEDS) yearMon " +
				"from ("+ sql +") t " +
				"left outer join (select * from TBL_MCHNT_INFILE_DTL " +
								  "union all " +
								  "select * from TBL_MCHNT_INFILE_DTL_HIS) d " +
					"on (t.MCHT_NO=d.MCHT_NO and d.DATE_SETTLMT >= '"+ yearStart +"' and d.DATE_SETTLMT <= '"+ date +"') " +
				"group by MARKETER_NAME,t.MCHT_NO,t.mcht_flag1,t.mcht_flag2,t.MCHT_NM,LICENCE_NO,brh_name,upBrh,termNum,t.REC_CRT_TS,dayAmt,dayNum,daySettle " +
				"order by MARKETER_NAME,t.MCHT_NO,t.brh_name,t.upBrh";
		
		int rowNum =3, cellNum = 0,columnNum = 19;
		fillData(sql, rowNum, cellNum, columnNum);
	}

	private String brhId;
	
	public String getBrhId() {
		return brhId;
	}

	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}

	@Override
	protected String getFileKey() {
		return ExcelName.EN_53;
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