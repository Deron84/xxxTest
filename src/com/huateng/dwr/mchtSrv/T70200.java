/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   Gavin      2011-9-28       first release
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
package com.huateng.dwr.mchtSrv;

import com.huateng.bo.impl.mchtSrv.MarketActSrv;
import com.huateng.common.Constants;
import com.huateng.struts.mchtSrv.MarketActConstants;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.SysCodeUtil;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-9-28
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class T70200 {
	

	/**
	 * 是否判断已经存在该商户
	 * @param actNo
	 * @param mchntNo
	 * @return
	 */
	public String isExist(String actNo ,String[] mchntNos){
		MarketActSrv marketActSrv = (MarketActSrv) ContextUtil.getBean("marketActSrv");
		StringBuffer mchntNo = new StringBuffer("'");
		if(mchntNos == null || mchntNos.length == 0 
				|| mchntNos[0].equals(""))
			return MarketActConstants.T70202_02;
		for(int i=0;i<mchntNos.length;i++)
		{
			mchntNo.append(mchntNos[i]).append("','");
		}
		String result = mchntNo.substring(0, mchntNo.length()-2).toString();
		String rspCode = marketActSrv.isExistMchntNo(actNo, result);
		if(rspCode == null)
			return Constants.SUCCESS_CODE;
		return rspCode;
	}
}
