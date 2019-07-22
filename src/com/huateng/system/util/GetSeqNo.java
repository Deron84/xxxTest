package com.huateng.system.util;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.huateng.common.StringUtil;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.exception.AppException;

public class GetSeqNo extends HibernateDaoSupport {
	public static String getSeqNo(String key) throws Exception {
		int int_L_SEQ_VAL = 0; // // 当前流水号
		int int_L_SEQ_MAX = 0; // // 序号最大值
		int int_L_SEQ_INC = 0; // // 每次序号的增加值
		String string_L_SEQ_VAL; // //用于转换成字符流水号的变量
		String strSql = "";
		String result = "";// 返回值
		ICommQueryDAO commQueryDao = null;
		int keyValue = -1;

		try {
			keyValue = Integer.parseInt(key);
			commQueryDao = (ICommQueryDAO) ContextUtil
					.getBean("CommQueryDAOCopy");
			if (keyValue > 4) {
				throw new Exception("没有对应产生序号操作");
			}
		} catch (AppException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new Exception("输入不合法的key值");
		}
		// 从流水中号表 tbl_seq_inf 表中取 流水号
		strSql = "select L_SEQ_VAL,L_SEQ_MAX,L_SEQ_INC from tbl_seq_inf where l_seq_key='"
				+ key.trim() + "'";
		List<Object[]> list = commQueryDao.findBySQLQuery(strSql);
		if (list.size() <= 0) {
			// 默认初始化
			int_L_SEQ_VAL = 0;
			int_L_SEQ_MAX = 999999;
			int_L_SEQ_INC = 1;
		} else {
			int_L_SEQ_VAL = Integer.parseInt(String.valueOf(list.get(0)[0])); // 当前流水号
			int_L_SEQ_MAX = Integer.parseInt(String.valueOf(list.get(0)[1])); // 序号最大值
			int_L_SEQ_INC = Integer.parseInt(String.valueOf(list.get(0)[2])); // 每次序号的增加值
		}
		string_L_SEQ_VAL = Integer.toString(int_L_SEQ_VAL);

		switch (keyValue) {
		case 0: {
			// 如果需求得到的是四位的商户号，
			// 不足四位用0补齐四位
			result = add0(string_L_SEQ_VAL, 4);
		}
			break;
		case 1: {
			// 差错查询序号
			// 增加RA前缀
			result = addPre("RA", add0(string_L_SEQ_VAL, 6));
		}
			break;
		case 2: {
			// 差错调单序号，增加RB
			result = addPre("RB", add0(string_L_SEQ_VAL, 6));
		}
			break;
		case 3: {
			// 请款序号,增加RC
			result = addPre("RC", add0(string_L_SEQ_VAL, 6));
		}
			break;
		case 4: {
			result = addPre("RD", add0(string_L_SEQ_VAL, 6));
		}
			break;
		default:
			break;
		}
		if (int_L_SEQ_VAL == int_L_SEQ_MAX) {
			int_L_SEQ_VAL = 1;
		} else {
			int_L_SEQ_VAL = int_L_SEQ_VAL + int_L_SEQ_INC;
		}

		// 更新 流水号表 tbl_seq_inf
		if (list.size() <= 0) {
			strSql = "insert into tbl_seq_inf values("+key+","+int_L_SEQ_VAL+",0,999999,1,'')";
		} else {
			strSql = "update tbl_seq_inf set L_SEQ_VAL=" + int_L_SEQ_VAL
					+ " where l_seq_key='" + key + "'";
		}
		commQueryDao.excute(strSql);

		return result;
	}
	//add by huateng.zhangtong@20100130
	//应客户需要，商户编号最后四位流水号只有在提交以后，再更新，以节省编号资源
	public static String getSeqNoNoUpd(String key) throws Exception {
		int int_L_SEQ_VAL = 0; // // 当前流水号
		int int_L_SEQ_MAX = 0; // // 序号最大值
		int int_L_SEQ_INC = 0; // // 每次序号的增加值
		String string_L_SEQ_VAL; // //用于转换成字符流水号的变量
		String strSql = "";
		String result = "";// 返回值
		ICommQueryDAO commQueryDao = null;
		int keyValue = -1;

		try {
			keyValue = Integer.parseInt(key);
			commQueryDao = (ICommQueryDAO) ContextUtil
					.getBean("CommQueryDAOCopy");
			if (keyValue > 4) {
				throw new Exception("没有对应产生序号操作");
			}
		} catch (AppException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new Exception("输入不合法的key值");
		}
		// 从流水中号表 tbl_seq_inf 表中取 流水号
		strSql = "select L_SEQ_VAL,L_SEQ_MAX,L_SEQ_INC from tbl_seq_inf where l_seq_key='"
				+ key.trim() + "'";
		List<Object[]> list = commQueryDao.findBySQLQuery(strSql);
		if (list.size() <= 0) {
			// 默认初始化
			int_L_SEQ_VAL = 0;
			int_L_SEQ_MAX = 999999;
			int_L_SEQ_INC = 1;
		} else {
			int_L_SEQ_VAL = Integer.parseInt(String.valueOf(list.get(0)[0])); // 当前流水号
			int_L_SEQ_MAX = Integer.parseInt(String.valueOf(list.get(0)[1])); // 序号最大值
			int_L_SEQ_INC = Integer.parseInt(String.valueOf(list.get(0)[2])); // 每次序号的增加值
		}
		string_L_SEQ_VAL = Integer.toString(int_L_SEQ_VAL);

		switch (keyValue) {
		case 0: {
			// 如果需求得到的是四位的商户号，
			// 不足四位用0补齐四位
			result = add0(string_L_SEQ_VAL, 4);
		}
			break;
		case 1: {
			// 差错查询序号
			// 增加RA前缀
			result = addPre("RA", add0(string_L_SEQ_VAL, 6));
		}
			break;
		case 2: {
			// 差错调单序号，增加RB
			result = addPre("RB", add0(string_L_SEQ_VAL, 6));
		}
			break;
		case 3: {
			// 请款序号,增加RC
			result = addPre("RC", add0(string_L_SEQ_VAL, 6));
		}
			break;
		case 4: {
			result = addPre("RD", add0(string_L_SEQ_VAL, 6));
		}
			break;
		default:
			break;
		}
		if (int_L_SEQ_VAL == int_L_SEQ_MAX) {
			int_L_SEQ_VAL = 1;
		} else {
			int_L_SEQ_VAL = int_L_SEQ_VAL + int_L_SEQ_INC;
		}

/*		// 更新 流水号表 tbl_seq_inf
		if (list.size() <= 0) {
			strSql = "insert into tbl_seq_inf values("+key+","+int_L_SEQ_VAL+",0,999999,1,'')";
		} else {
			strSql = "update tbl_seq_inf set L_SEQ_VAL=" + int_L_SEQ_VAL
					+ " where l_seq_key='" + key + "'";
		}
		commQueryDao.excute(strSql);*/

		return result;
	}
	private static String addPre(String string, String add0) {

		return string + add0;
	}

	/**
	 * 
	 * @param string_L_SEQ_VAL
	 * @param len
	 *            指定长度
	 * @return
	 */
	private static String add0(String string_L_SEQ_VAL, int len) {
		String result = "";
		int length = string_L_SEQ_VAL.length();
		if (length < len) {
			for (int i = len; i > length; i--)
				string_L_SEQ_VAL = "0" + string_L_SEQ_VAL;
			result = string_L_SEQ_VAL;
		}
		return result;
	}
	
	public static String getInterMchntNoPT(){
		String sql0 = "SELECT MAX(SUBSTR(internal_no,3,7)) FROM tbl_mcht_base_tmp";
		String sql1 = "SELECT MAX(SUBSTR(internal_no,3,7)) FROM tbl_mcht_base_inf";
		try {
			ICommQueryDAO commQueryDao = (ICommQueryDAO) ContextUtil
				.getBean("CommQueryDAOCopy");
			Object res0 = commQueryDao.findBySQLQuery(sql0).get(0);
			Object res1 = commQueryDao.findBySQLQuery(sql1).get(0);
			String tmp = "0";
			String inf = "0";
			if (!StringUtil.isNull(res0)){
				tmp = res0.toString();
			}
			if (!StringUtil.isNull(res1)){
				inf = res1.toString();
			}
			if (Integer.valueOf(tmp) >= Integer.valueOf(inf)){
				return "PT" + CommonFunction.fillString(String.valueOf(Integer.valueOf(tmp) + 1), '0', 6, false);
			} else {
				return "PT" + CommonFunction.fillString(String.valueOf(Integer.valueOf(inf) + 1), '0', 6, false);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return "";
	}
	
	public static String getInterMchntNoJT(){
		String sql = "SELECT MAX(SUBSTR(internal_no,3,7)) FROM tbl_group_mcht_inf";
		try {
			ICommQueryDAO commQueryDao = (ICommQueryDAO) ContextUtil
				.getBean("CommQueryDAOCopy");
			Object res = commQueryDao.findBySQLQuery(sql).get(0);
			String tmp = "0";
			if (!StringUtil.isNull(res)){
				tmp = res.toString();
			}

			return "JT" + CommonFunction.fillString(String.valueOf(Integer.valueOf(tmp) + 1), '0', 4, false);
			
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return "";
	}

}
