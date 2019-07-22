/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-8-27       first release
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

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Title: 交易名称
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-27
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class TxnInfo {
	/**
	 * 交易代码和名称集合
	 */
	public static Map<String, String> txnNameMap = new LinkedHashMap<String, String>();
	/**
	 * 交易渠道集合
	 */
	public static Map<String, String> txnChannelMap = new LinkedHashMap<String, String>();
	
	static {
		//交易类型
		txnNameMap.put("1161", "查询");
		txnNameMap.put("5151", "退货");
		txnNameMap.put("1101", "消费");
		txnNameMap.put("2101", "消费冲正");
		txnNameMap.put("3101", "消费撤消");
		txnNameMap.put("4101", "撤消冲正");
		txnNameMap.put("1011", "预授权");
		txnNameMap.put("2011", "预授权冲正");
		txnNameMap.put("3011", "预授权撤消");
		txnNameMap.put("4011", "预授权撤消冲正");
		txnNameMap.put("1091", "联机预授权完成");
		txnNameMap.put("2091", "联机预授权完成冲正");
		txnNameMap.put("3091", "联机预授权完成撤消");
		txnNameMap.put("4091", "联机预授权完成撤消冲正");
		txnNameMap.put("1111", "分期付款");
		txnNameMap.put("2111", "分期付款冲正");
		txnNameMap.put("1171", "积分查询");
		txnNameMap.put("1121", "积分消费");
		txnNameMap.put("2121", "积分消费冲正");
		txnNameMap.put("3121", "积分撤消");
		txnNameMap.put("4121", "积分撤消冲正");
		txnNameMap.put("5161", "离线预授权完成");
		txnNameMap.put("1131", "财务转账");
		txnNameMap.put("3131", "转账撤销");
		txnNameMap.put("1501", "T+0消费");
		
		//交易渠道
		txnChannelMap.put("1601", "借记卡");
		txnChannelMap.put("1602", "贷记卡");
		txnChannelMap.put("1603", "他行卡");
	}
	
}
