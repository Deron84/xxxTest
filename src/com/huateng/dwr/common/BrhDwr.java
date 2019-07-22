package com.huateng.dwr.common;

import com.huateng.dao.common.SqlDao;
import com.huateng.system.util.ContextUtil;

/**
 * project JSBConsole date 2013-3-18
 * 
 * @author 樊东东
 */
public class BrhDwr {

	private SqlDao sqlDao = (SqlDao) ContextUtil.getBean("sqlDao");

	public String getCupBrhIdByBrhId(String brhId) {
		String cupId = "00";// 返回00表示失败
		try {
			String sql = "select cup_brh_id from tbl_brh_info where trim(brh_id)='"
					+ brhId + "'";
			cupId = sqlDao.queryForObjectAndCast(sql, String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println(cupId);
		return cupId;
	}
}
