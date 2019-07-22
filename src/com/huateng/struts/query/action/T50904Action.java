
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
public class T50904Action extends BaseExcelQueryAction {

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
		year = date.substring(0, 4);
		mon = date.substring(0, 6);
		
		if(sheet.getRow(0).getCell(3) == null){
			sheet.getRow(0).createCell(3);
		}
		sheet.getRow(0).getCell(3).setCellValue(mon);

		//地区按分行统计
		String brhSql = "select brh_id from tbl_brh_info where brh_level = '1' order by brh_id";
		
		ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
		List brhList = commQueryDAO.findBySQLQuery(brhSql);
		
		if(brhList == null){
			System.out.println("找不到机构！");
			return ;
		}
		
		for(int i = 0;i <= brhList.size(); i++){
			String wheresql = "where 1=1 "; 
			String brh = "";
			
			if(!StringUtil.isNull(mon)){
				wheresql += " and ABS_MON='"+mon+"' "; 
			}
			
			if(i==brhList.size()){
				brh = "全辖";
			}else{
				//ABS_MCHT_TRADE_MON表中BRH_NO记录的是归属机构
				wheresql += "and BRH_NO in "+ InformationUtil.getBrhGroupString(brhList.get(i).toString());
				brh = InformationUtil.getBrhName((String) brhList.get(i)).trim();
			}
			
			/** FLAG标识
			 * 0 触发风险待处理; 
			 * 1 手工清算已办结;
			 * 2 正常清算已办结;
			 * 3 暂不清算等待处理;
			 * 4 押款已办结;
			 * **/
			String sql = "select '"+ brh +"',sum(1) ms1," +
							"sum(nvl(SINGLE_AMT/100,0)) sa1," +
							"sum(nvl(SINGLE_COUNT,0)) sc1," +
							"sum(case when FLAG in('0','3') and b.WHITELIST_FLAG !='1' then 1 else 0 end) ms2," +
							"sum(case when FLAG in('0','3') and b.WHITELIST_FLAG !='1' then nvl(SINGLE_AMT/100,0) else 0 end) sa2," +
							"sum(case when FLAG in('0','3') and b.WHITELIST_FLAG !='1' then nvl(SINGLE_COUNT,0) else 0 end) sc2," +
							"sum(case when FLAG='4' and b.WHITELIST_FLAG !='1' then 1 else 0 end) ms3," +
							"sum(case when FLAG='4' and b.WHITELIST_FLAG !='1' then nvl(SINGLE_AMT/100,0) else 0 end) sa3," +
							"sum(case when FLAG='4' and b.WHITELIST_FLAG !='1' then nvl(SINGLE_COUNT,0) else 0 end) sc3," +
							"sum(case when (FLAG IN ('1','2') and b.WHITELIST_FLAG !='1') or b.WHITELIST_FLAG ='1' then 1 else 0 end) ms4," +
							"sum(case when (FLAG IN ('1','2') and b.WHITELIST_FLAG !='1') or b.WHITELIST_FLAG ='1' then nvl(SINGLE_AMT/100,0) else 0 end) sa4," +
							"sum(case when (FLAG IN ('1','2') and b.WHITELIST_FLAG !='1') or b.WHITELIST_FLAG ='1' then nvl(SINGLE_COUNT,0) else 0 end) sc4 " +
					"from ABS_MCHT_TRADE_MON a left outer join TBL_MCHT_SETTLE_INF b on(a.MCHT_NO=b.MCHT_NO) " +
					wheresql;
			int rowNum = 4+i, cellNum = 0, columnNum = 13;
			fillData(sql, rowNum, cellNum, columnNum);
		}
		int[] cellNums = new int[]{0};
		consolidate(cellNums);
	}


	@Override
	protected String getFileKey() {
		return ExcelName.EN_54;
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
