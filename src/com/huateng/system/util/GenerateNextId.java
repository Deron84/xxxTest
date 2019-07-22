/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-7-18       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2010 Huateng Software, Inc. All rights reserved.
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

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;

import com.huateng.common.Constants;
import com.huateng.common.SysParamConstants;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.dao.common.SqlDao;
import com.huateng.po.mchnt.TblInCardManagentPK;

/**
 * Title:生成系统编号
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-7-18
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class GenerateNextId {
	
	private static ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
	private static SqlDao sqlDao = (SqlDao)ContextUtil.getBean("sqlDao");
	/**
	 * 获得idcd
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static synchronized String getDiscId() {
		String idcd = "00000";
		String sql = "select max(DISC_CD) from TBL_INF_DISC_CD";
		List idList = commQueryDAO.findBySQLQuery(sql);
		if (idList.size() != 0 && idList.get(0) != null && idList.get(0).toString().trim().length() != 0){
			idcd = CommonFunction.fillString(idList.get(0).toString(),'0', 5, false);
		}			
		idcd = CommonFunction.fillString(String.valueOf(Integer.parseInt(idcd) + 1), '0', 5, false);
		return idcd;
		
	}
	
	
	/**
	 * 获得银联卡编号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static synchronized String getBankBinId() {
		String sql = "select min(IND + 1) from TBL_BANK_BIN_INF where " +
				"(IND + 1) not in (select IND from TBL_BANK_BIN_INF)";
		List<BigDecimal> resultSet = commQueryDAO.findBySQLQuery(sql);
		if(resultSet.size()>0 && resultSet.get(0) != null){
			return resultSet.get(0).toString();
		}else{
			return "1";
		}
		
	}
	
	/**
	 * 获得角色信息的编号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static synchronized String getNextRoleId() {
		String sql = "select min(role_id + 1) from tbl_role_inf where " +
				"(role_id + 1) not in (select role_id from tbl_role_inf)";
		List<BigDecimal> resultSet = commQueryDAO.findBySQLQuery(sql);
		return resultSet.get(0).toString();
	}
	
	/**
	 * 获得商户编号
	 * @param str
	 * @return 如果返回""，则表示该类商户已经达到数量上线
	 */
	@SuppressWarnings("unchecked")
	public static synchronized String getMchntId(String str) {
		str = SysParamUtil.getParam(SysParamConstants.BANK_BRH_CODE)+str;
		//判断是否存在序号为0001的ID
		String sql = "select count(1) from TBL_MCHT_BASE_INF_TMP where trim(mcht_no) = '" + str + "0001" + "'" ;
		BigDecimal c = (BigDecimal) commQueryDAO.findBySQLQuery(sql).get(0);
		if (c.intValue() == 0) {
			return str + "0001";
		}
		
		sql = "select min(substr(mcht_no,12,4) + 1) from TBL_MCHT_BASE_INF_TMP where (substr(mcht_no,12,4) + 1) not in " +
				"(select substr(mcht_no,12,4) + 0 from TBL_MCHT_BASE_INF_TMP where substr(mcht_no,1,11) = '" + str + "') " +
				"and substr(mcht_no,1,11) = '" + str + "'";
		List<BigDecimal> resultSet = commQueryDAO.findBySQLQuery(sql);
		if(resultSet.get(0) == null) {
			return str + "0001";
		}
		int id = resultSet.get(0).intValue();
		if(id == 10000) {
			return "";
		}
		return str + CommonFunction.fillString(String.valueOf(id), '0', 4, false);
	}
	
	/**
	 * 获得终端类型编号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static synchronized String getTermTpId() {
		String sql = "select min(term_tp + 1) from tbl_term_tp where " +
				"(term_tp + 1) not in (select term_tp from tbl_term_tp)";
		List<Double> resultSet = commQueryDAO.findBySQLQuery(sql);
		if(resultSet.get(0) == null) {
			return "01";
		}
		int id = resultSet.get(0).intValue();
		if(id == 100) {
			return "";
		}
		return CommonFunction.fillString(String.valueOf(id), '0', 2, false);
	}
	
	/**
	 * 查询操作日志流水号
	 * 
	 * @return 2010-12-9 上午10:21:38 Shuang.Pan
	 */
	public synchronized static String getTxnSeq() {
		String sql = "SELECT SEQ_TERM_NO.NEXTVAL FROM DUAL";
		sql = commQueryDAO.findBySQLQuery(sql).get(0).toString();
		sql = "1" + CommonFunction.fillString(sql, '0', 14, false);
		return sql;
	}
	
	/**
	 * 查询CA银联公钥编号
	 * 
	 * @return 2010-12-9 上午10:21:38 Shuang.Pan
	 */
	public synchronized static String getParaId() {
		String sql = "SELECT SEQ_EMV_PARA_NO.NEXTVAL FROM DUAL";
		sql = commQueryDAO.findBySQLQuery(sql).get(0).toString();
		sql = "1" + CommonFunction.fillString(sql, '0', 8, false);
		return sql;
	}
	
	/**
	 * 获得内部参数索引编号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static synchronized String getParaIdx(String usageKey) {
		String sql = "select min(para_idx + 1) from tbl_emv_para where " +
				"(para_idx + 1) not in (select para_idx from tbl_emv_para " +
			"where trim(usage_key) = '" + usageKey + "') and trim(usage_key) = '" + usageKey + "'";
		List resultSet = commQueryDAO.findBySQLQuery(sql);
		if(resultSet.get(0) == null) {
			return "01";
		}
		String id = resultSet.get(0).toString();

		if(id.equals("100")) {
			return "";
		}
		return CommonFunction.fillString(id, '0', 2, false);
	}
	
	/**
	 * 托收交易流水号
	 */
	@SuppressWarnings("unchecked")
	public static synchronized String getEntrustId() {

		String sql = "select max(trade_id)+1 from tbl_entrust_trade";
		List resultSet = commQueryDAO.findBySQLQuery(sql);
		
		if(resultSet.get(0) == null) {
			return "00000001";
		}

		String id = resultSet.get(0).toString();
		return CommonFunction.fillString(id, '0', 8, false);
	}
	
	/**
	 * 费用收取流水号
	 */
	public static synchronized String getCostId() {
		
		String sql = "select max(no)+1 from tbl_cost_inf";
		List resultSet = commQueryDAO.findBySQLQuery(sql);
		
		if(resultSet.get(0) == null) {
			return "00000001";
		}

		String id = resultSet.get(0).toString();
		return CommonFunction.fillString(id, '0', 8, false);
	}
	
	/**获取终端编号
	 * 数据库中终端表 终端号的字段长度12位、但考虑到有些银行可能是八位长度、所以现在也是用八位长度
	 * 终端号若中间有空缺需要填补
	 * 否者+1
	 * 
	 * 
	 * */
	public static synchronized String getTermId(String brhId) {
		if(StringUtils.isEmpty(brhId)){
			return Constants.FAILURE_CODE;
		}
			
		if(brhId.length()>2){
			brhId = brhId.substring(0, 2);//终端号在机构号前两位后面递增  中间有漏的补缺
		}
		String id1 = "99"+"000001";
		String check1sql = "select count(1) from TBL_TERM_INF_TMP where term_id='"+id1+"'";
		int count1 = sqlDao.queryForInt(check1sql);
		if(count1==0){//如果编号id1 不存在、返回id1
			return id1;
		}else{//如果编号id1存在、则可执行下面的sql语句找出所能插入的最小id编号
			String getMinIdSql = "select min(substr(term_id,3,6)+1)  from TBL_TERM_INF_TMP t where  substr(term_id,3,6)+1 not in (select substr(term_id,3,6) from TBL_TERM_INF_TMP where term_id like '99%')";
			int newId = sqlDao.queryForInt(getMinIdSql);
			if(newId>=1000000){
				return "当前机构下终端已经达到1000000台";
			}else{
				String idstr = CommonFunction.fillString(newId+"", '0', 6, false);
				return "99"+idstr;
			}
		}
			
	}
	
	public static synchronized String getInCardNum(TblInCardManagentPK tblInCardManagentPK) {
	    String inCardNum = tblInCardManagentPK.getInCardNum() ;
	    String mchtNo = tblInCardManagentPK.getMchntCd();
	    String inOutFlag = tblInCardManagentPK.getInOutFlag() ;
	    
	    if(StringUtils.isEmpty(inCardNum) 
	            || StringUtils.isEmpty(mchtNo) 
	            || StringUtils.isEmpty(inOutFlag)){
            return Constants.FAILURE_CODE;
        }
        String getInCardNum = "SELECT COUNT(1) FROM TBL_IN_CARD_MANAGENT WHERE MCHT_NO = '" + mchtNo + 
                "' AND IN_CARDNUM = '" + inCardNum + "' AND IN_OUT_FLAG ='" + inOutFlag + "'" ;
        System.out.println(getInCardNum);
        int count1 = sqlDao.queryForInt(getInCardNum);
        if(count1 == 0){
            return Constants.SUCCESS_CODE;
        }else{
            return "添加信息已经存在，请重新添加";
        }
       
	}
}
