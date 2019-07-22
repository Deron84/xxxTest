package com.huateng.struts.query.action;



import java.util.List;

import com.huateng.common.StringUtil;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.struts.query.BaseExcelQueryAction;
import com.huateng.struts.query.ExcelName;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.InformationUtil;

/**
 * project JSBConsole date 2013-5-22
 * 
 * @author 高浩
 */
public class T50912Action extends BaseExcelQueryAction {

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

		String nowDate = CommonFunction.getCurrentDate();
		String monStart = mon + "01";
		
		int m = Integer.valueOf(mon.substring(4, 6));
		int y = Integer.valueOf(year);
		String monEnd = getMonEnd(m,y,mon);
		
		int mBefore = Integer.valueOf(mon.substring(4, 6))-1;
		int yearBefore = Integer.valueOf(year);
		if(m == 1){
			mBefore = 12;
			yearBefore = Integer.valueOf(year) - 1;
		}
		String monBefore = yearBefore + "" + CommonFunction.fillString(mBefore + "", '0', 2, false);
		String monBeforeStart = monBefore + "01";
		String monBeforeEnd = getMonEnd(mBefore,yearBefore,monBefore);
		
		
		//地区按分行统计
		String brhSql = "select brh_id from tbl_brh_info where brh_level = '1' order by brh_id";
		
		ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
		List brhList = commQueryDAO.findBySQLQuery(brhSql);
		
		if(brhList == null){
			System.out.println("找不到机构！");
			return ;
		}
        
		if(!StringUtil.isNull(mon)){
			c = sheet.getRow(0).getCell(5);
			if(c == null){
				sheet.getRow(0).createCell(5);
			}
			c.setCellValue(mon.substring(0, 4) + "年" + mon.substring(4, 6) + "月");
		}
		
		for(int i = 0;i <= brhList.size(); i++){
			String wheresql = null; 
			String brh = null;
			if(i==brhList.size()){
				wheresql = "where 1=1 ";
				brh = "全辖";
			}else{
				wheresql = "where agr_br in "+ InformationUtil.getBrhGroupString(brhList.get(i).toString()); 
				brh = InformationUtil.getBrhName((String) brhList.get(i)).trim();
			}
			
			String sql = "select nvl(sum(case when RCT_CHECK_DATE <= '"+ monEnd +"' and RCT_CHECK_DATE >= to_char((to_date(" + monEnd + ",'yyyymmdd') - CHECK_FRQC),'yyyymmdd') then 1 else 0 end),0) allNum," +
								"nvl(sum(case when RCT_CHECK_DATE >= to_char((to_date(" + monEnd + ",'yyyymmdd') - 15),'yyyymmdd') and RCT_CHECK_DATE<= '"+ monEnd +"' then 1 else 0 end),0) num15," +
								"nvl(sum(case when RCT_CHECK_DATE >= to_char((to_date(" + monBeforeEnd + ",'yyyymmdd') - 15),'yyyymmdd') and RCT_CHECK_DATE<= '"+ monBeforeEnd +"' then 1 else 0 end),0) num15Bf," +
								"nvl(sum(case when RCT_CHECK_DATE >= to_char((to_date(" + monEnd + ",'yyyymmdd') - 30),'yyyymmdd') and RCT_CHECK_DATE<= '"+ monEnd +"' then 1 else 0 end),0) num30," +
								"nvl(sum(case when RCT_CHECK_DATE >= to_char((to_date(" + monBeforeEnd + ",'yyyymmdd') - 30),'yyyymmdd') and RCT_CHECK_DATE<= '"+ monBeforeEnd +"' then 1 else 0 end),0) num30Bf," +
								"nvl(sum(case when RCT_CHECK_DATE >= to_char((to_date(" + monEnd + ",'yyyymmdd') - 60),'yyyymmdd') and RCT_CHECK_DATE<= '"+ monEnd +"' then 1 else 0 end),0) num60," +
								"nvl(sum(case when RCT_CHECK_DATE >= to_char((to_date(" + monBeforeEnd + ",'yyyymmdd') - 60),'yyyymmdd') and RCT_CHECK_DATE<= '"+ monBeforeEnd +"' then 1 else 0 end),0) num60Bf," +
								"nvl(sum(case when RCT_CHECK_DATE < to_char((to_date(" + monEnd + ",'yyyymmdd') - 60),'yyyymmdd') then 1 else 0 end),0) num60Ag," +
								"nvl(sum(case when RCT_CHECK_DATE < to_char((to_date(" + monBeforeEnd + ",'yyyymmdd') - 60),'yyyymmdd') then 1 else 0 end),0) num60AgBf," +
								"nvl(sum(case when RCT_CHECK_DATE is null or (RCT_CHECK_DATE is not null and RCT_CHECK_DATE < to_char((to_date(" + nowDate + ",'yyyymmdd') - CHECK_FRQC),'yyyymmdd')) then 1 else 0 end),0) allUn," +
								"nvl(sum(case when RCT_CHECK_DATE is null or (RCT_CHECK_DATE is not null and RCT_CHECK_DATE < to_char((to_date(" + monEnd + ",'yyyymmdd') - CHECK_FRQC),'yyyymmdd')) then 1 else 0 end),0) monUn," +
								"nvl(sum(case when RCT_CHECK_DATE is null or (RCT_CHECK_DATE is not null and RCT_CHECK_DATE < to_char((to_date(" + monBeforeEnd + ",'yyyymmdd') - CHECK_FRQC),'yyyymmdd')) then 1 else 0 end),0) monUnBf " +
						 "from TBL_MCHT_BASE_INF " +
						 wheresql +
						 " and CONN_TYPE = 'J'"; 
			
			sql = "select '"+ brh +"'," +
						 "allNum," +
						 "num15," +
						 "(case when allNum!=0 then round(num15*100/allNum,2) when allNum=0 and num15=0 then 0 else 100 end)," +
						 "(case when num15Bf!=0 then round(num15*100/num15Bf,2) when num15Bf=0 and num15=0 then 0 else 100 end)," +
						 "num30," +
						 "(case when allNum!=0 then round(num30*100/allNum,2) when allNum=0 and num30=0 then 0 else 100 end)," +
						 "(case when num30Bf!=0 then round(num30*100/num30Bf,2) when num30Bf=0 and num30=0 then 0 else 100 end)," +
						 "num60," +
						 "(case when allNum!=0 then round(num60*100/allNum,2) when allNum=0 and num60=0 then 0 else 100 end)," +
						 "(case when num60Bf!=0 then round(num60*100/num60Bf,2) when num60Bf=0 and num60=0 then 0 else 100 end)," +
						 "num60Ag," +
						 "(case when allNum!=0 then round(num60Ag*100/allNum,2) when allNum=0 and num60Ag=0 then 0 else 100 end)," +
						 "(case when num60AgBf!=0 then round(num60Ag*100/num60AgBf,2) when num60AgBf=0 and num60Ag=0 then 0 else 100 end)," +
						 "monUn," +
						 "(case when allUn!=0 then round(monUn*100/allUn,2) when monUn=0 and allUn=0 then 0 else 100 end)," +
						 "(case when monUnBf!=0 then round(monUn*100/monUnBf,2) when monUn=0 and monUnBf=0 then 0 else 100 end) " +
					"from (" + sql + ")";
			
			int rowNum =3+i, cellNum = 0,columnNum = 17;
			fillData(sql, rowNum, cellNum, columnNum);
		}
	}
	   


	@Override
	protected String getFileKey() {
		return ExcelName.EN_512;
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