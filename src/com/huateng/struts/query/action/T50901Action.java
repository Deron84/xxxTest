package com.huateng.struts.query.action;

import java.util.List;

import com.huateng.common.StringUtil;
import com.huateng.struts.query.BaseExcelQueryAction;
import com.huateng.struts.query.ExcelName;
import com.huateng.struts.query.QueryExcelUtil;
import com.huateng.system.util.InformationUtil;

/**
 * project JSBConsole date 2013-4-1
 * 
 * @author 樊东东
 */
public class T50901Action extends BaseExcelQueryAction {

	private String year;

	private String mon;

	private String brhBelow;

	private String brhId;

	private String date;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getBrhId() {
		return brhId;
	}

	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}

	@Override
	protected void deal() {
		StringBuffer sql = null;

		year = date.substring(0, 4);
		mon = date.substring(0, 6);
		String monStart = mon + "01";
		String yearStart = year + "0101";

		c = sheet.getRow(0).getCell(5);
		if (c == null) {
			sheet.getRow(0).createCell(5);
		}
		c.setCellValue(date);

		brhBelow = InformationUtil.getBrhGroupString(brhId);
		List brhNameList = commQueryDAO
				.findBySQLQuery("select BRH_NAME from TBL_BRH_INFO where BRH_ID='"
						+ brhId + "'");
		r = sheet.getRow(3);
		c = r.getCell(0);
		c.setCellValue(brhNameList.get(0).toString());

		List<Integer[]> flagList = QueryExcelUtil.flagList;

		// 统计终端状态为“已装机、修改待审核、冻结、冻结待审核、解冻待审核、注销待审核”
		for (int i = 0, listSize = flagList.size(); i < listSize; i++) {
			Integer flag1 = flagList.get(i)[0];
			Integer flag2 = flagList.get(i)[1];
			
			if(flag1 != null && flag2 != null && (flag1 == 7) && flag2 == 4){
				sql = new StringBuffer(
					    "select sum(case when term_sta in ('2','3','4','5','6','8') then 1 else 0 end) as c0,"
							    + "sum(case when t1.USING_DATE = '"
						    	+ date
							    + "' then 1 else 0 end) as c1,"
							    + "sum(case when substr(t1.USING_DATE,0,6) = '"
							    + mon
							    + "' then 1 else 0 end) as c2,"
							    + "sum(case when substr(t1.USING_DATE,0,4) = '"
							    + year
							    + "' then 1 else 0 end) as c3, "
							    + "sum(case when (t1.term_sta = '7' or t1.term_sta = '9') and substr(t1.rec_upd_ts,0,4) = '"
							    + year
							    + "' then 1 else 0 end) as c4"
							    + " from tbl_term_inf t1 inner join tbl_mcht_base_inf t2 on trim(t1.mcht_cd)=t2.mcht_no join tbl_n_txn_his t3 on t3.card_accp_id = t2.mcht_no "
							    + " where CONN_TYPE = 'J' and t3.txn_num = '1301' and substr(t1.USING_DATE,0,8) <= '"
							    + date + "'  and t2.mcht_flag1='" + flag1 + "'");
				
			}else {
			    sql = new StringBuffer(
					    "select sum(case when term_sta in ('2','3','4','5','6','8') then 1 else 0 end) as c0,"
							    + "sum(case when t1.USING_DATE = '"
							    + date
							    + "' then 1 else 0 end) as c1,"
							    + "sum(case when substr(t1.USING_DATE,0,6) = '"
							    + mon
							    + "' then 1 else 0 end) as c2,"
							    + "sum(case when substr(t1.USING_DATE,0,4) = '"
							    + year
							    + "' then 1 else 0 end) as c3, "
							    + "sum(case when (t1.term_sta = '7' or t1.term_sta = '9') and substr(t1.rec_upd_ts,0,4) = '"
							    + year
							    + "' then 1 else 0 end) as c4"
							    + " from tbl_term_inf t1 inner join tbl_mcht_base_inf t2 on trim(t1.mcht_cd)=t2.mcht_no "
							    + " where CONN_TYPE = 'J' and substr(t1.USING_DATE,0,8) <= '"
							    + date + "' " );
			    if (flag1 != null) {
				    sql.append(" and t2.mcht_flag1='" + flag1 + "'");
			    }
			    if (flag2 != null) {
				    sql.append(" and t2.mcht_flag2='" + flag2 + "'");
		    	}

			    if (!"0000".equals(brhId) && !StringUtil.isNull(brhId)) {
			    	sql.append(" and t1.term_branch in " + brhBelow);
			    }
			}
			// 填充数据 row基准从3开始 cell基准是3,共5列
			int rowNum = 3, cellNum = 3, columnNum = 5;
			fillData(sql.toString(), rowNum + i, cellNum, columnNum);
		}

		// 合作客户数（按商户代码计）
		for (int i = 0, listSize = flagList.size(); i < listSize; i++) {
			Integer flag1 = flagList.get(i)[0];
			Integer flag2 = flagList.get(i)[1];
			// 累计：正常、修改待审核、冻结待审核、冻结、解冻待审核、注销待审核；状态正常算新增 注销算退出
			sql = new StringBuffer(
					"select sum(case when MCHT_STATUS in('0','3','5','6','7','8') then 1 else 0 end) as c0,"
							+ "sum(case when substr(rec_crt_ts,0,8)='"
							+ date
							+ "' then 1 else 0 end) as c1,"
							+ "sum(case when substr(rec_crt_ts,0,6)='"
							+ mon
							+ "' then 1 else 0  end) as c2,"
							+ "sum(case when substr(rec_crt_ts,0,4)='"
							+ year
							+ "' then 1 else 0 end) as c3,"
							+ "sum(case when substr(rec_upd_ts,0,4)='"
							+ year
							+ "' and MCHT_STATUS = '9' then 1 else 0 end) as c4"
							+ " from tbl_mcht_base_inf where CONN_TYPE = 'J' and substr(rec_crt_ts,0,8) <= '"
							+ date + "' ");
			if (flag1 != null) {
				sql.append(" and mcht_flag1='" + flag1 + "'");
			}
			if (flag2 != null) {
				sql.append(" and mcht_flag2='" + flag2 + "'");
			}

			if (!"0000".equals(brhId) && !StringUtil.isNull(brhId)) {
				sql.append(" and AGR_BR in " + brhBelow);
			}

			// 填充数据 row基准从3开始 cell基准是8,共5列
			int rowNum = 3, cellNum = 8, columnNum = 5;

			fillData(sql.toString(), rowNum + i, cellNum, columnNum);

		}

		// 交易金额（万元） tbl_algo_dtl --清算明细表 mcht_at_d 减去 mcht_at_c 时间date_settlmt
		// 交易笔数
		for (int i = 0, listSize = flagList.size(); i < listSize; i++) {
			// 按对账单的逻辑查询清算信息表作为基础数据出报表
			String sqlAlgo = "select date_settlmt,MCHT_AT_C,MCHT_AT_D,MCHT_FEE_C,MCHT_FEE_D,"
					+ "FEE_D_OUT,FEE_C_OUT,mcht_cd,term_id,acq_ins_id_cd,txn_num,"
					+ "(select TXN_NAME from TBL_TXN_NAME b where a.txn_num =b.TXN_NUM and DC_FLAG ='0') txnNm "
					+ "from TBL_ALGO_DTL a "
					+ "where txn_num not in('5171', '9103') and C_D_FLG <> '1'  and SUBSTR(TXN_NUM,1,1) <> '3' "
					+ " union all "
					+ "select trim(REC_UPD_TS) date_settlmt,MCHT_AT_C,MCHT_AT_D,MCHT_FEE_C,MCHT_FEE_D,"
					+ "FEE_D_OUT,FEE_C_OUT,mcht_cd,term_id,acq_ins_id_cd,txn_num,"
					+ "(select TXN_NAME from TBL_TXN_NAME b where a.txn_num =b.TXN_NUM and DC_FLAG ='0') txnNm "
					+ "from TBL_ALGO_DTL a "
					+ "where TXN_NUM in ('5171', '9103') and OLD_TXN_FLG = '1' ";

			Integer flag1 = flagList.get(i)[0];
			Integer flag2 = flagList.get(i)[1];
			sql = new StringBuffer(
					"select sum(case when t1.date_settlmt='"
							+ date
							+ "' then t1.mcht_at_c-t1.mcht_at_d else 0 end) as c0,"
							+ "sum(case when substr(t1.date_settlmt,0,6)='"
							+ mon
							+ "' then t1.mcht_at_c-t1.mcht_at_d else 0 end) as c1,"
							+ "sum(case when substr(t1.date_settlmt,0,4)='"
							+ year
							+ "' then t1.mcht_at_c-t1.mcht_at_d else 0 end) as c2,"
							+ "sum(case when t1.date_settlmt='"
							+ date
							+ "' then 1 else 0 end) as c3,"
							+ "sum(case when substr(t1.date_settlmt,0,6)='"
							+ mon
							+ "' then 1 else 0 end)as c4,"
							+ "sum(case when substr(t1.date_settlmt,0,4)='"
							+ year
							+ "' then 1 else 0 end)as c5,"
							+ "sum(case when t1.date_settlmt='"+ date + "' and TXN_NUM not in('9103','9205') then NVL((MCHT_FEE_D - MCHT_FEE_C), 0)-NVL((FEE_D_OUT-FEE_C_OUT), 0) "
							+ "when t1.date_settlmt='"+ date + "' and TXN_NUM = '9103' then nvl((MCHT_AT_D - MCHT_AT_C),0) "
							+ "when t1.date_settlmt='"+ date + "' and TXN_NUM = '9205' then nvl((MCHT_AT_D - MCHT_AT_C),0) else 0 end) c6,"
							+ "sum(case when substr(t1.date_settlmt,0,6)='"+ mon + "' and TXN_NUM not in('9103','9205') then NVL((MCHT_FEE_D - MCHT_FEE_C), 0)-NVL((FEE_D_OUT-FEE_C_OUT), 0) "
							+ "when substr(t1.date_settlmt,0,6)='"+ mon + "' and TXN_NUM = '9103' then nvl((MCHT_AT_D - MCHT_AT_C),0) "
							+ "when substr(t1.date_settlmt,0,6)='"+ mon + "' and TXN_NUM = '9205' then nvl((MCHT_AT_D - MCHT_AT_C),0) else 0 end) c7,"
							+ "sum(case when substr(t1.date_settlmt,0,4)='"+ year + "' and TXN_NUM not in('9103','9205') then NVL((MCHT_FEE_D - MCHT_FEE_C), 0)-NVL((FEE_D_OUT-FEE_C_OUT), 0) "
							+ "when substr(t1.date_settlmt,0,4)='"+ year + "' and TXN_NUM = '9103' then nvl((MCHT_AT_D - MCHT_AT_C),0) "
							+ "when substr(t1.date_settlmt,0,4)='"+ year + "' and TXN_NUM = '9205' then nvl((MCHT_AT_D - MCHT_AT_C),0) else 0 end) c8 "
							+ " from ("
							+ sqlAlgo
							+ ") t1 "
							+ "inner join tbl_mcht_base_inf t2 on t1.mcht_cd=t2.mcht_no "
							+ "where CONN_TYPE = 'J' and date_settlmt <= '"
							+ date + "' ");
			if (flag1 != null) {
				sql.append(" and t2.mcht_flag1='" + flag1 + "'");
			}
			if (flag2 != null) {
				sql.append(" and t2.mcht_flag2='" + flag2 + "'");
			}

			if (!"0000".equals(brhId) && !StringUtil.isNull(brhId)) {
				sql.append(" and t2.AGR_BR in " + brhBelow);
			}

			// 填充数据 row基准从3开始 cell基准是8,共9列
			int rowNum = 3, cellNum = 13, columnNum = 9;
			fillData(sql.toString(), rowNum + i, cellNum, columnNum);
		}

	}

	@Override
	protected String getFileKey() {
		return ExcelName.EN_51;
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
