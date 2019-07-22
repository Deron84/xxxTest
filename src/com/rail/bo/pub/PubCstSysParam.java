package com.rail.bo.pub;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huateng.system.util.CommonFunction;

/**  
* @author syl
* @version 创建时间：2019年4月23日 下午7:07:43  
* @desc
*/
public class PubCstSysParam {
	public static List<Object[]> getSysParam(String owner) {
		String sql =" select t.KEY,t.VALUE from CST_SYS_PARAM t where t.OWNER = '"+owner+"'";
		List<Object[]> list =  CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		return list;
	}
	public static Map<String,Object> getSysParamMap(String owner) {
		List<Object[]> list = PubCstSysParam.getSysParam(owner);
		Map<String,Object> map = new HashMap<String,Object>();
		for(Object[] o : list){
			map.put(String.valueOf(o[0]), o[1]);
		}
		return map;
	}
	//获取统一级别仓库的list
	public static List<Object[]> getTJwhList(String whCode) {
		String endSql = "seelct * from PARENT_WHSE_CODE  ";
		String sqlwh ="select PARENT_WHSE_CODE from RAIL_WHSE_INFO t where t.WHSE_CODE ='"+whCode+"'";
		List<Object[]> listWs = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlwh);
		if(listWs==null || listWs.size()==0){
			endSql = endSql +"  where PARENT_WHSE_CODE is null";
		}else{
			endSql = endSql + " where PARENT_WHSE_CODE = '"+listWs.get(0)[0]+"'";
		}
		List<Object[]> listWsend = CommonFunction.getCommQueryDAO().findBySQLQuery(endSql);
		return listWsend;
	}
	
	
}

