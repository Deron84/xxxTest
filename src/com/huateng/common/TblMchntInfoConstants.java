/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-7-30       first release
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
package com.huateng.common;

/**
 * Title:商户信息宏定义
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-7-30
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class TblMchntInfoConstants {
	
	/**
	 * 0:正常 1:添加待审核  2:添加审核退回 3:修改待审核 4:修改审核退回(保留) 
	 * 5:冻结待审核  6:冻结 7:解冻待审核  8:注销待审核  9:注销 R：注销恢复待审核
	 * */
	/**商户状态-正常*/
	public static final String MCHNT_ST_OK = "0";
	/**商户状态-添加待审核*/
	public static final String MCHNT_ST_NEW_UNCK = "1";
	/**商户状态-添加审核退回*/
	public static final String MCHNT_ST_NEW_UNCK_BACK = "2";
	/**商户状态-修改待审核*/
	public static final String MCHNT_ST_MODI_UNCK = "3";
//	/**商户状态-修改审核退回*/
//	public static final String MCHNT_ST_MODI_UNCK_BACK = "4";
	//把停用改为冻结
	/**商户状态-冻结待审核*/ 
	public static final String MCHNT_ST_STOP_UNCK = "5";
	/**商户状态-冻结   */  
	public static final String MCHNT_ST_STOP = "6";
	/**商户状态-解冻待审核*/
	public static final String MCHNT_ST_RCV_UNCK = "7";
	
	/**商户状态-注销待审核*/
	public static final String MCHNT_ST_CLOSE_UNCK = "8";
	
	/**商户状态-注销*/
	public static final String MCHNT_ST_CLOSE = "9";
	
	/**商户状态-注销恢复待审核*/
	public static final String MCHNT_ST_REVIVE = "R";
	
	

	
	/**商户结算方式-日结*/
	public static final String MCHNT_SETTLE_TYPE_DAY = "1";
	/**商户结算方式-月结*/
	public static final String MCHNT_SETTLE_TYPE_MON = "2";
	/**商户手续费结算类型-按商户*/
	public static final String MCHNT_RATE_FLAG_MCHT = "0";
	/**商户手续费结算类型-按卡种*/
	public static final String MCHNT_RATE_FLAG_CARD = "1";
	/**商户结算渠道-本行对公活期账户*/
	public static final String MCHNT_SETTLE_CHN_SELF = "1";
	/**他行帐户*/
	public static final String MCHNT_SETTLE_CHN_OUT = "2";
	/**按分段金额，针对手续费月结商户*/
	public static final String MCHNT_FEE_TYPE_DIV = "1";
	/**按每笔固定金额*/
	public static final String MCHNT_FEE_TYPE_FIX = "2";
	/**按每笔百分比，且有最高上限*/
	public static final String MCHNT_FEE_TYPE_PER = "3";
	
	/**商户临时基本信息*/
	public static final String MCHNT_BASE_INFO_TMP = "MCHT_BASE_INFO_TMP";
	/**商户临时清算信息*/
	public static final String MCHNT_SETTLE_INFO_TMP = "MCHT_SETTLE_INFO_TMP";
	/**商户临时补充信息*/
	public static final String MCHNT_SUPP_INFO_TMP = "MCHT_SUPP_INFO_TMP";
	/**商户补充信息集合*/
	public static final String MCHNT_SUPP_INFO_TMPS = "MCHT_SUPP_INFO_TMPS";
	/**商户基本信息*/
	public static final String MCHNT_BASE_INFO = "MCHT_BASE_INFO";
	/**商户清算信息*/
	public static final String MCHNT_SETTLE_INFO = "MCHT_SETTLE_INFO";
	/**商户补充信息*/
	public static final String MCHNT_SUPP_INFO = "MCHT_SUPP_INFO";
	/**商户分期参数信息*/
	public static final String MCHNT_DIV_NO = "DIV_NO_ARRAY";
	/**商户分期商品代码信息*/
	public static final String MCHNT_PRODUCT = "PRODUCT_ARRAY";
	/**商户分期扣率*/
	public static final String MCHNT_DIV_FEE_TYPE_RATE = "0";
	/**商户分期固定金额*/
	public static final String MCHNT_DIV_FEE_TYPE_FIX = "1";
	/**商户补充信息国内他行卡*/
	public static final String MCHNT_CARD_TYPE_CUPS = "00";
	/**商户补充信息本行借记卡*/
	public static final String MCHNT_CARD_TYPE_DEBITS = "01";
	/**商户补充信息本行一帐通*/
	public static final String MCHNT_CARD_TYPE_CDC = "03";
	/**商户补充信息本行信用卡*/
	public static final String MCHNT_CARD_TYPE_CREDIT = "04";
	/**商户按卡种手续费类型-固定手续费*/
	public static final String MCHNT_CARD_TYPE_FEE_FIX = "2";
	/**商户按卡种手续费类型-按比例*/
	public static final String MCHNT_CARD_TYPE_FEE_RATE = "3";
	
	
	/**Extjs checkbox checked*/
	public static final String EXTJS_CHECKED = "on";
	
}
