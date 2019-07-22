package com.huateng.dwr.settle;

import java.util.ArrayList;
import java.util.List;

import com.huateng.common.StringUtil;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.system.util.ContextUtil;

/**
 *date: 2013-6-3
 *author: dats.xu
 */
public class CheckTask {
	
@SuppressWarnings("unchecked")
public String sendMsg(String date) throws Exception{
	
	   ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
	   String sql="SELECT nvl(b.BAT_STATE,0) " +
	   			  "FROM TBL_BAT_MAIN_CTL a left outer join " +
	   				   "TBL_BAT_MAIN_CTL_DTL b " +
	   				   "on (a.BAT_ID = b.BAT_ID and trim(b.DATE_SETTLMT) = '"+date+"') " +
	   				   "where a.BAT_ID = 'B0009'";
	   List<String[]> list=commQueryDAO.findBySQLQuery(sql);

	   if(!"2".equals(list.get(0))){
		   return "E";
	   }
	   return "D";
   }
}
