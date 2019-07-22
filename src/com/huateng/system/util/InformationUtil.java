/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ --------- ---------------------------------------------------
 *   Gavin        2011-8-10       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2011 Huateng Software, Inc. All rights reserved.
 *
 *       This software is the confidential and proprietary information of
 *       Shanghai HUATENG Software Co., Ltd. ("Confidential Information").
 *       You shall not disclose such Confidential Information and shall use it
 *       only in accordance with the terms of the license agreement you entered
 *       into with Huateng.
 *
 * Warning:
 * =============================================================================
 *
 */
package com.huateng.system.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.common.StringUtil;
import com.huateng.commquery.dao.ICommQueryDAO;

/**
 * Title:
 * 
 * File: InformationUtil.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-8-10
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class InformationUtil {

	/**
	 * 组装title
	 * 
	 * @param base
	 * @param size
	 * @return 2011-8-10下午04:23:46
	 */
	public static String[] createTitles(String base, int size) {
		String[] s = new String[size];
		for (int i = 0; i < size; i++) {
			s[i] = base + String.valueOf(i + 1);
		}
		return s;
	}
	
	
	/**
	 * 根据机构编号获取机构名称
	 * 
	 * @return
	 * 2011-8-13下午12:04:32
	 */
	public static String getBrhName(String brhId){
		try {
			String sql = "select brh_name from tbl_brh_info where brh_id = '" + brhId + "'";
				List list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
			if (null != list && !list.isEmpty() && !StringUtil.isNull(list.get(0))) {
				return list.get(0).toString();
			} else {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	
	/**
	 * 根据商户编号获取商户名称（主表）
	 * 
	 * @return
	 * 2011-8-13下午12:04:32
	 */
	public static String getMchtName(String id){
		try {
			String sql = "select MCHT_NM from TBL_MCHT_BASE_INF where MCHT_NO = '" + id + "'";
				List list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
			if (null != list && !list.isEmpty() && !StringUtil.isNull(list.get(0))) {
				return list.get(0).toString();
			} else {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 根据机构编号获取银联编号
	 * 
	 * @return
	 * 2011-8-13下午12:04:32
	 */
	public static String getCupBrhId(String brhId){
		try {
			String sql = "select cup_brh_id from tbl_brh_info where brh_id = '" + brhId + "'";
				List list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
			if (null != list && !list.isEmpty() && !StringUtil.isNull(list.get(0))) {
				return list.get(0).toString();
			} else {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 根据商户号获得其收单机构号
	 * 
	 * @return
	 * 2011-8-13下午12:04:32
	 */
	public static String getBrhIdByMchntNo(String mchntNo){
		try {
			String sql = "select ACQ_INST_ID from TBL_MCHT_BASE_INF where MCHT_NO = '" + mchntNo + "'";
				List list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
			if (null != list && !list.isEmpty() && !StringUtil.isNull(list.get(0))) {
				return list.get(0).toString();
			} else {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static String changeMonth(String date, int amount, boolean back){
		int curMonth = Integer.parseInt(date.substring(4, 6));
		int changeYear = 0;
		int changeMonth = 0;
		if (amount > curMonth - 1) {
			changeYear = (amount - curMonth + 1)/12 + 1;
			changeMonth = amount/12 + 1;
		} else {
			changeMonth = amount;
		}
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, Integer.parseInt(date.substring(0, 4)));
		c.set(Calendar.MONTH, Integer.parseInt(date.substring(4, 6)) - 1);
		System.out.println(changeMonth + "||" + changeYear);
		if (back) {
			c.roll(Calendar.MONTH, -1 * changeMonth);
			c.roll(Calendar.YEAR, -1 * changeYear);
		} else {
			c.roll(Calendar.MONTH, changeMonth);
			c.roll(Calendar.YEAR, changeYear);
		}
		SimpleDateFormat date8 = new SimpleDateFormat("yyyyMMdd");
		return date8.format(c.getTime());
	}
	
	
	/**
	 * 根据当前机构获得当前和下级BRH_ID
	 * @param id 行内机构号
	 * @return
	 */
	public static String getBrhGroupString(String id) {
		if (StringUtil.isNull(id)) {
			return "('" + id + "')";
		}
		try {
			
			Map<String, String> map = new HashMap<String, String>();
			
			map.put(id, id);
			
			map = CommonFunction.getBelowBrhMap(map);
			
			return CommonFunction.getBelowBrhInfo(map);
			
		} catch (Exception e) {
			e.printStackTrace();
			return "('" + id + "')";
		}
	}

	public static void getBrhBelow(String id, Set set){
		set.add(id);
		String sql = "select BRH_ID from TBL_BRH_INFO where UP_BRH_ID = '" + id + "'";
		List list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		if (null != list && !list.isEmpty()) {
			Iterator it = list.iterator();
			while (it.hasNext()) {
				Object o = it.next();
				if (!StringUtil.isNull(o)) {
					getBrhBelow(o.toString(), set);
				}
			}
		}
	}
	
	
	/**
	 * 根据当前机构获得当前和下级CUP_BRH_ID
	 * @param id 行内机构号
	 * @return
	 */
	public static String getCupBrhGroupString(String id) {
		if (StringUtil.isNull(id)) {
			return "('')";
		}
		try {
			
			Set set = new HashSet<String>();
			set.add(getCupBrhId(id));
			getCupBrhBelow(id, set);
			Iterator<String> it = set.iterator();
			StringBuffer sb = new StringBuffer("(");
			while(it.hasNext()){
				sb.append("'");
				sb.append(it.next());
				sb.append("'");
				sb.append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append(")");
			
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "('" + id + "')";
	}

	public static void getCupBrhBelow(String id, Set set){
		String sql = "select BRH_ID,CUP_BRH_ID from TBL_BRH_INFO where UP_BRH_ID = '" + id + "'";
		List list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		if (null != list && !list.isEmpty()) {
			Iterator it = list.iterator();
			while (it.hasNext()) {
				Object[] o = (Object[]) it.next();
				if (!StringUtil.isNull(o[0])) {
					if (!StringUtil.isNull(o[1])) {
						set.add(o[1]);
					}
					getCupBrhBelow(o[0].toString(), set);
				}
			}
		}
	}
	
	
	public static String getQuestionId(){
		return "Q" + 
			CommonFunction.getCurrentDateTime() + 
			CommonFunction.fillString(String.valueOf(new Random().nextInt(999999)), '0', 6, false);
	}
	
	public static String getMchtUserId(){
		return "MU" + 
			CommonFunction.getCurrentDateTime() + 
			CommonFunction.fillString(String.valueOf(new Random().nextInt(999999)), '0', 6, false);
	}
	
	public static String getOptionId(int index){
		return "O" + 
			CommonFunction.getCurrentDateTime() + 
			CommonFunction.fillString(String.valueOf(index), '0', 6, false);
	}
	
	public static String getPaperId(){
		return "P" + 
			CommonFunction.getCurrentDateTime() + 
			CommonFunction.fillString(String.valueOf(new Random().nextInt(999999)), '0', 6, false);
	}
	
	public static String getSelMchtId(){
		return "M" + 
			CommonFunction.getCurrentDateTime() + 
			CommonFunction.fillString(String.valueOf(new Random().nextInt(999999)), '0', 6, false);
	}
	
	
	public static int getMaxQuestionIndex(){
		String sql = "select nvl(max(QUES_INDEX),0) from TBL_PAPER_DEF_INF";
		return Integer.parseInt(CommonFunction.getCommQueryDAO().findBySQLQuery(sql).get(0).toString()) + 1;
	}
	
	
	public static boolean checkStates(String oprId){
		
		try {
			String sql = "select RESERVE1,RESERVE2 from TBL_PAPER_DEF_INF where PAPER_ID = 'PAPER_STATUS'";
			List list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
			if (null != list && !list.isEmpty()) {
				Object[] obj = (Object[]) list.get(0);
				//正常状态-0
				//锁定状态-1
				if (obj[0].equals("0")) {
					return false;
				} else if (obj[0].equals("1")) {
					//判断是否为当前柜员锁定
					if (obj[1].equals(oprId)) {
						return true;
					} else {
						return false;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 * 获取当前试卷的ID
	 * 
	 * @return
	 * 2011-10-9下午02:30:24
	 */
	public static String getCurPaperId(){
		try {
			String sql = "select QUESTION from TBL_PAPER_DEF_INF where PAPER_ID = 'PAPER_STATUS'";
				List list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
			if (null != list && !list.isEmpty() && !StringUtil.isNull(list.get(0))) {
				return list.get(0).toString();
			} else {
				return "NA";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "NA";
		}
	}
	
	/**
	 * 获取当前问卷的分数
	 * 
	 * @return
	 * 2011-10-9下午02:30:24
	 */
	public static String getCurPaperPoint(String id){
		try {
			String sql = "select MCHT_POINT FROM TBL_PAPER_LEL_INF WHERE SEL_MCHT_ID = '" + id + "'";
				List list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
			if (null != list && !list.isEmpty() && !StringUtil.isNull(list.get(0))) {
				return list.get(0).toString();
			} else {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 获取当前问卷的级别
	 * 
	 * @return
	 * 2011-10-9下午02:30:24
	 */
	public static String getCurPaperLevel(String id){
		try {
			String sql = "select MCHT_LEVEL FROM TBL_PAPER_LEL_INF WHERE SEL_MCHT_ID = '" + id + "'";
				List list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
			if (null != list && !list.isEmpty() && !StringUtil.isNull(list.get(0))) {
				return list.get(0).toString();
			} else {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 获取当前商户的级别
	 * 
	 * @return
	 * 2011-10-9下午02:30:24
	 */
	public static String getMchtLevel(int point){
		try {
			String sql = "select value from CST_SYS_PARAM where OWNER = 'MCHT_LEVEL' and " +
					"(KEY > " + String.valueOf(point) + " or key = 100) order by key*1 asc";
			List list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
			if (null != list && !list.isEmpty() && !StringUtil.isNull(list.get(0))) {
				return list.get(0).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "级别定义异常";
	}
	
	
	/**
	 * 问卷的问题
	 * 
	 * @param paperId
	 * @param map
	 * 2011-10-18下午02:57:33
	 */
	public static void getPaperQues(String paperId, List<Object[]> list){
		
		String sql = "select QUES_ID,QUESTION from TBL_PAPER_HIS_INF where PAPER_ID = '" + paperId + "' order by QUES_INDEX ";
		list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
	}
	
	/**
	 * 问卷的问题和选项
	 * 
	 * @param paperId
	 * @param map
	 * 2011-10-18下午02:57:33
	 */
	public static List<Object[]> getPaperOpts(String paperId, LinkedHashMap<String, List<Object[]>> map){
		
		String sql = "select QUES_ID,QUESTION from TBL_PAPER_HIS_INF where PAPER_ID = '" + paperId + "' order by QUES_INDEX ";
		List<Object[]> list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		Iterator<Object[]> it = list.iterator();
		while (it.hasNext()) {
			Object[] obj = it.next();
			sql = "select OPT_ID,OPT from TBL_PAPER_OPT_HIS where QUES_ID = '" + obj[0] + "' " +
					"and PAPER_ID = '" + paperId + "' ORDER BY POINT DESC";
			List<Object[]> l = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
			map.put(obj[0].toString(), l);
		}
		return list;
	}
	
	/**
	 * 结果集
	 * 
	 * @param selMchtId
	 * @param set
	 * 2011-10-18下午02:57:49
	 */
	public static void getAnswers(String selMchtId, Set<String> set){
		String sql = "select SEL_OPT_ID from TBL_PAPER_SEL_INF where SEL_MCHT_ID = '" + selMchtId + "'";
		List list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		Iterator<String> it = list.iterator();
		while (it.hasNext()) {
			set.add(it.next());
		}
	}
	
	public static String getSettleAcct(String brhId){
		try {
			String sql = "select BRH_ID,INNER_ACCT2 from SETTLE_DOC_INFO";
			List list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
			if (null != list && !list.isEmpty()) {
				Object[] obj = (Object[]) list.get(0);
				return String.valueOf(obj[1]);
			} else {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	// 生成终端映射表的主键
	public static String getMchtMappId() {
		return "MA"
				+ CommonFunction.getCurrentDateTime()
				+ CommonFunction.fillString(
						String.valueOf(new Random().nextInt(999999)), '0', 6,
						false);
	}

	// 生成优惠规则表的主键
	public static String getDiscountRuleId() {
		return "DR"
				+ CommonFunction.getCurrentDateTime()
				+ CommonFunction.fillString(
						String.valueOf(new Random().nextInt(999999)), '0', 6,
						false);
	}

	// 生成优惠规则绑定表的主键
	public static String getDiscountRuleBindId() {
		return "BD"
				+ CommonFunction.getCurrentDateTime()
				+ CommonFunction.fillString(
						String.valueOf(new Random().nextInt(9999)), '0', 4,
						false);
	}

}