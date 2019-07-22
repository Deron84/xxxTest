/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   Gavin      2011-6-21       first release
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
package com.huateng.common.select;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import com.huateng.bo.base.T10206BO;
import com.huateng.bo.impl.mchnt.TblMchntService;
import com.huateng.bo.impl.mchtSrv.MarketActSrv;
import com.huateng.bo.mchnt.T20701BO;
import com.huateng.bo.term.T3010BO;
import com.huateng.dao.common.HqlDao;
import com.huateng.po.TblMchtCupInfo;
import com.huateng.po.TblMchtCupInfoTmp;
import com.huateng.po.TblTermInfTmp;
import com.huateng.po.TblTermInfTmpPK;
import com.huateng.po.base.TblEmvPara;
import com.huateng.po.base.TblEmvParaPK;
import com.huateng.po.base.TblEmvParaValue;
import com.huateng.po.mchnt.TblGroupMchtInf;
import com.huateng.po.mchnt.TblInfDiscCd;
import com.huateng.po.mchnt.TblMchtBaseInf;
import com.huateng.po.mchnt.TblMchtBaseInfTmp;
import com.huateng.po.mchnt.TblMchtSettleInf;
import com.huateng.po.mchnt.TblMchtSettleInfTmp;
import com.huateng.po.mchtSrv.TblMarketAct;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-21
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class LoadRecordMethod {

	public static TblMchntService service = (TblMchntService) ContextUtil
			.getBean("TblMchntService");

	public static T3010BO t3010BO = (T3010BO) ContextUtil.getBean("t3010BO");
	
	private T20701BO t20701BO = (T20701BO) ContextUtil.getBean("T20701BO");
	
	private static MarketActSrv marketActSrv = (MarketActSrv) ContextUtil.getBean("marketActSrv");

	/**
	 * 获得集团商户信息
	 * 
	 * @param request
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 *             2011-6-22下午01:43:43
	 */
	public String getGroupMchnt(HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {

		try {
			TblGroupMchtInf inf = service.getGroupInf(request
					.getParameter("groupMchtCd"));
			if (null == inf) {
				return null;
			}
			inf = (TblGroupMchtInf) CommonFunction.trimObject(inf);
			return getMessage(JSONArray.fromObject(inf).toString());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获得商户信息
	 * 
	 * @param request
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String getMchntInf(HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {
		try {
		    String parameter = request.getParameter("mchntId");
			TblMchtBaseInfTmp inf = service.getBaseInfTmp(request
					.getParameter("mchntId"));
			TblMchtSettleInfTmp settle = service.getSettleInfTmp(request
					.getParameter("mchntId"));
			TblMchtCupInfoTmp cupInfo = service.getCupInfoTmp(request
					.getParameter("mchntId"));
			
			if (null == inf || null == settle) {
				return null;
			}
			
			inf = (TblMchtBaseInfTmp) CommonFunction.trimObject(inf);
			settle = (TblMchtSettleInfTmp) CommonFunction.trimObject(settle);
				
			String infArr = JSONArray.fromObject(inf).toString();
			String settleArr = JSONArray.fromObject(settle).toString();
			String cupInfoArr = "[{" +
								'"' +
								"mcht_no" +
								'"' +
								":" +
								'"' +
								request.getParameter("mchntId") +
								'"' +
								"}]";
			
			if("Z".equals(inf.getConnType())){
				if(null == cupInfo){
					return null;
				}else{
					cupInfo = (TblMchtCupInfoTmp) CommonFunction.trimObject(cupInfo);
					cupInfoArr = JSONArray.fromObject(cupInfo).toString();
					System.out.println(cupInfoArr);
				}
			}

			String[] srcs = {infArr,settleArr,cupInfoArr};
			
			//重名替换
			String sub1 = srcs[0].substring(0,srcs[0].indexOf("manager"));
			String sub2 = srcs[0].substring(srcs[0].indexOf("manager")+"manager".length());
			srcs[0] = sub1 + "manager1" + sub2;
			
			return getMessage(srcs);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取正式表商户信息
	 */
	public String getMchntBaseInf(HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {
		try {
		    String parameter = request.getParameter("mchntId");
			TblMchtBaseInf inf = service.getBaseInf(request
					.getParameter("mchntId"));
			TblMchtSettleInf settle = service.getSettleInf(request
					.getParameter("mchntId"));
			TblMchtCupInfo cupInfo = service.getCupInfo(request
					.getParameter("mchntId"));

			if (null == inf || null == settle) {
				return null;
			}
			inf = (TblMchtBaseInf) CommonFunction.trimObject(inf);
			settle = (TblMchtSettleInf) CommonFunction.trimObject(settle);
			
			String infArr = JSONArray.fromObject(inf).toString();
			String settleArr = JSONArray.fromObject(settle).toString();
			String cupInfoArr = "[{" +
								'"' +
								"mcht_no" +
								'"' +
								":" +
								'"' +
								request.getParameter("mchntId") +
								'"' +
								"}]";
			
			if("Z".equals(inf.getConnType())){
				if(null == cupInfo){
					return null;
				}else{
					cupInfo = (TblMchtCupInfo) CommonFunction.trimObject(cupInfo);
					cupInfoArr = JSONArray.fromObject(cupInfo).toString();
				}
			}
			
			String[] srcs = {infArr,settleArr,cupInfoArr};
			
			//重名替换
			String sub1 = srcs[0].substring(0,srcs[0].indexOf("manager"));
			String sub2 = srcs[0].substring(srcs[0].indexOf("manager")+"manager".length());
			srcs[0] = sub1 + "manager1" + sub2;
			
			return getMessage(srcs);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 计费算法信息
	 * 
	 * @param request
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String getFeeInf(HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {

		try {
			TblInfDiscCd inf = t20701BO.getTblInfDiscCd(request.getParameter("discCd"));
			
			if (null == inf) {
				return null;
			}
			inf = (TblInfDiscCd) CommonFunction.trimObject(inf);
			return getMessage(JSONArray.fromObject(inf).toString());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取终端信息
	 * 
	 * @param request
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 *             2011-6-27下午04:17:15
	 */
	public String getTermInfo(HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {
		String termId = request.getParameter("termId");
		String mchtCd = request.getParameter("mchtCd");
		HqlDao dao = (HqlDao) ContextUtil.getBean("hqlDao");
		TblTermInfTmpPK id = new TblTermInfTmpPK(CommonFunction.fillString(termId, ' ', 12, true),CommonFunction.fillString(mchtCd, ' ', 15, true));
//		System.out.println(termId+"-"+mchtCd);
		TblTermInfTmp tblTermInfTmp = (TblTermInfTmp) dao.get(TblTermInfTmp.class, id);
		if (tblTermInfTmp != null)
			return getMessage(JSONArray.fromObject(tblTermInfTmp).toString());
		return null;
	}
	
	public String getActInfo(HttpServletRequest request)
	throws IllegalAccessException, InvocationTargetException {
		String actNo = request.getParameter("actNo");
		TblMarketAct tblMarketAct = marketActSrv.getActInfo(actNo);
		if (tblMarketAct != null)
			return getMessage(JSONArray.fromObject(tblMarketAct).toString());
		return null;
	}
	
	public String getIcParamInf(HttpServletRequest request){
		
		T10206BO t10206BO = (T10206BO) ContextUtil.getBean("T10206BO");

		TblEmvPara para = t10206BO.get(new TblEmvParaPK(CommonFunction.fillString("0", ' ', 8, true),CommonFunction.fillString(request.getParameter("index"), ' ', 4, true)));
		
		TblEmvParaValue value = new TblEmvParaValue(para.getParaVal(), para.getParaOrg());
		
		return getMessage(JSONArray.fromObject(value).toString());
	}

	/**
	 * 格式化输出
	 * 
	 * @param src
	 * @return 2011-6-22下午01:43:30
	 */
	public String getMessage(String src) {
		return "{\"data\":" + src + "}";
	}

	

	/**
	 * 多对象格式化输出
	 * 
	 * @param src
	 * @return 2011-6-22下午01:43:30
	 */
	public String getMessage(String[] srcs) {

		StringBuffer sb = new StringBuffer("{\"data\":[{");
		for (String src : srcs) {
			src = src.substring(2).trim();
			sb.append(src);
			sb.delete(sb.length() - 2, sb.length());
			sb.append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("}]}");
		return sb.toString();
	}
}
