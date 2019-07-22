package com.huateng.struts.query.action;

import com.huateng.common.StringUtil;
import com.huateng.struts.query.BaseExcelQueryAction;
import com.huateng.struts.query.ExcelName;
import com.huateng.system.util.InformationUtil;

/**
 * project JSBConsole date 2013-4-17
 * 
 * @author 樊东东
 */
public class T50902Action extends BaseExcelQueryAction {

	private String brhId;
	
	private String mon;
	
	private String year;

	@Override
	protected void deal() {
		year = mon.substring(0,4);
		
		String monStart = mon.substring(0,6) + "01";
		String monEnd = getMonEnd(Integer.valueOf(mon.substring(4,6)),Integer.valueOf(year),mon);
		
		String whereSql = " where substr(DATE_SETTLMT,0,6) = '"+ mon +"' ";
		brhId = operator.getOprBrhId();
		
		c = sheet.getRow(0).getCell(3);
		if(c == null){
			sheet.getRow(0).createCell(3);
		}
		c.setCellValue(mon);

		//按对账单的逻辑查询清算信息表作为基础数据出报表
		String sqlAlgo = "select date_settlmt,MCHT_AT_C,MCHT_AT_D,MCHT_FEE_C,MCHT_FEE_D," +
						"FEE_D_OUT,FEE_C_OUT,mcht_cd,term_id,acq_ins_id_cd,txn_num," +
						"(select TXN_NAME from TBL_TXN_NAME b where a.txn_num =b.TXN_NUM and DC_FLAG ='0') txnNm " +
					"from TBL_ALGO_DTL a " +
					"where txn_num not in('5171', '9103') and C_D_FLG <> '1'  and SUBSTR(TXN_NUM,1,1) <> '3' " +
				" union all " +
					"select trim(REC_UPD_TS) date_settlmt,MCHT_AT_C,MCHT_AT_D,MCHT_FEE_C,MCHT_FEE_D," +
						"FEE_D_OUT,FEE_C_OUT,mcht_cd,term_id,acq_ins_id_cd,txn_num," +
						"(select TXN_NAME from TBL_TXN_NAME b where a.txn_num =b.TXN_NUM and DC_FLAG ='0') txnNm " +
					"from TBL_ALGO_DTL a " +
					"where TXN_NUM in ('5171', '9103') and OLD_TXN_FLG = '1' ";
		
		String sql = "select t.termBrh,p.org_name,t.term_id,t.TERM_STA,t.mcht_cd,m.MCHT_NM,m.mcht_flag1,m.mcht_flag2," +
							 "dayRent,NVL(t.PROP_INS_RATE,0) PROP_INS_RATE,nvl(r.LEASE_FEE,0) LEASE_FEE," +
							 "sum(case when TXN_NUM not in('9103','9205') then NVL((MCHT_FEE_D - MCHT_FEE_C),0)-NVL((FEE_D_OUT-FEE_C_OUT),0) "
									+ "when TXN_NUM = '9103' then nvl((MCHT_AT_D - MCHT_AT_C),0) "
									+ "when TXN_NUM = '9205' then nvl((MCHT_AT_D - MCHT_AT_C),0) else 0 end) clAmt " +
					 "from (select * from ("+sqlAlgo+") "+whereSql+" ) a " 
						  + "right outer join (select mcht_cd,term_id,TERM_STA,prop_ins_nm,nvl(PROP_INS_RATE,0) PROP_INS_RATE,LEASE_FEE dayRent," +
						  			    			"(select (case when BRH_LEVEL='1' then brh_id when BRH_LEVEL='2' then UP_BRH_ID end) " +
						  			    			"from tbl_brh_info g where brh_id =term_branch) termBrh " +
						  			    	  "from tbl_term_inf where prop_ins_nm is not null) t " +
						  			    	  "on t.mcht_cd=a.mcht_cd and t.term_id=a.term_id "
						  + "join (select mcht_no,MCHT_NM,mcht_flag1,mcht_flag2 from tbl_mcht_base_inf where CONN_TYPE = 'J') m on t.mcht_cd=m.mcht_no " 
						  + "join TBL_PROFESSIONAL_ORGAN p on t.prop_ins_nm=p.org_id "
						  +	"left outer join (select MCHT_NO,TERM_ID,sum(LEASE_FEE) LEASE_FEE " +
						  		"from (select * from tbl_term_rent where INST_DATE >= '"+ monStart +"' and INST_DATE <= '"+ monEnd +"' " +
						  			  "union all " +
						  			  "select * from TBL_TERM_RENT_HIS where INST_DATE >= '"+ monStart +"' and INST_DATE <= '"+ monEnd +"') " +
						  		"group by MCHT_NO,TERM_ID) r on t.mcht_cd=r.mcht_no and t.term_id=r.term_id "
					+" group by t.termBrh,p.org_name,t.term_id,t.TERM_STA,t.mcht_cd,m.MCHT_NM,m.mcht_flag1, m.mcht_flag2,t.PROP_INS_RATE,dayRent,r.LEASE_FEE " 
					+ "order by t.termBrh,p.org_name,t.term_id,t.mcht_cd,m.mcht_flag1,t.PROP_INS_RATE";

		sql = "select (select brh_name from tbl_brh_info where brh_id=termBrh) brh," +
					 "org_name,term_id," +
					 "(case TERM_STA when '1' then '待装机' " +
								   "when '2' then '已装机' " +
								   "when '4' then '冻结' " +
								   "when '7' then '注销' end) termSt,mcht_cd," +
					 "(case mcht_flag1 when '0' then '收款商户' " +
					                 "when '1' then '老板通商户' " +
					                 "when '2' then '分期商户' " +
					                 "when '3' then '财务转账商户' " +
					                 "when '4' then '项目形式商户' " +
					                 "when '5' then '积分业务' " +
					                 "when '6' then '其他业务' " +
					                 "when '7' then '固话POS商户' end) mchtFlag1," +
					 "(case mcht_flag1||mcht_flag2 when '00' then '线上商户' " +
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
									 "when '42' then '其他项目' end) mchtFlag2,MCHT_NM, " +
			         "(case mcht_flag1 when '0' then '收款商户' " +
							         "when '1' then '老板通商户' " +
							         "when '2' then '分期商户' " +
							         "when '3' then '财务转账商户' " +
							         "when '4' then '项目形式商户' " +
							         "when '5' then '积分业务' " +
							         "when '6' then '其他业务' " +
							         "when '7' then '固话POS商户' end)," +
			"dayRent,PROP_INS_RATE,clAmt,LEASE_FEE," + 
			"round(clAmt*PROP_INS_RATE/100,2) as org_fee," +
			"round(clAmt*PROP_INS_RATE/100 + nvl(LEASE_FEE,0),2) orgSum," + 
			"round(clAmt*(100-PROP_INS_RATE)/100 - LEASE_FEE,2) as pure_fee " + 
			"from ("+sql+")";
		
		// 行标准2 列标准0 16列
		int rowNum = 2, cellNum = 0, columnNum = 16;
		fillData(sql, rowNum, cellNum, columnNum);// 更新最后一行(待填充数据的)

//		int[] cellNums = new int[]{0};
//		consolidate(cellNums);
//		cellNums = new int[]{1};
//		consolidate(cellNums);
	}


	public String getMon() {
		return mon;
	}

	public void setMon(String mon) {
		this.mon = mon;
	}

	@Override
	protected String getFileKey() {
		return ExcelName.EN_52;
	}


	@Override
	public String getMsg() {
		return msg;
	}


	@Override
	public boolean isSuccess() {
		return success;
	}

	public String getBrhId() {
		return brhId;
	}

	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}

}
