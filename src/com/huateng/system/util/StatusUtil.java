package com.huateng.system.util;

import java.util.ResourceBundle;

import com.huateng.common.TblMchntInfoConstants;

/**
 * Title: 系统交易信息
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-7-16
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class StatusUtil {

	private static String STATUS_FILE = "Status";

	private static ResourceBundle BUNDLE = ResourceBundle
			.getBundle(STATUS_FILE);

	/**
	 * 获得交易信息
	 * 
	 * @param key
	 * @return
	 */
	public static String getNextStatus(String key) {
		return BUNDLE.getString(key);
	}

	/** 从原状态变更后获取新的商户信息状态 */
	public static String getNewMchntStatus(String key) {
		String status = null;
		if (TblMchntInfoConstants.MCHNT_ST_NEW_UNCK.equals(key)
				|| TblMchntInfoConstants.MCHNT_ST_MODI_UNCK.equals(key)) {
			status = TblMchntInfoConstants.MCHNT_ST_OK;//添加待审核、修改待审核通过后置为正常
		}

		if (TblMchntInfoConstants.MCHNT_ST_STOP_UNCK.equals(key)) {
			status = TblMchntInfoConstants.MCHNT_ST_STOP;//冻结待审核通过后置为冻结
		}
		if (TblMchntInfoConstants.MCHNT_ST_RCV_UNCK.equals(key)) {
			status = TblMchntInfoConstants.MCHNT_ST_OK;//解冻通过置为正常
		}
		if (TblMchntInfoConstants.MCHNT_ST_CLOSE_UNCK.equals(key)) {
			status = TblMchntInfoConstants.MCHNT_ST_CLOSE;//注销通过置为注销
		}
		if (TblMchntInfoConstants.MCHNT_ST_REVIVE.equals(key)) {
			status = TblMchntInfoConstants.MCHNT_ST_OK;//注销恢复通过置为正常
		}
		return status;

	}
}
