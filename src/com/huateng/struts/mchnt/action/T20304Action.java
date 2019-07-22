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
package com.huateng.struts.mchnt.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.huateng.bo.mchnt.T20304BO;
import com.huateng.common.Constants;
import com.huateng.po.mchnt.CstMchtFeeInf;
import com.huateng.po.mchnt.CstMchtFeeInfPK;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.ContextUtil;

/**
 * Title:商户限额维护
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-7-30
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T20304Action extends BaseAction {
	
	private T20304BO t20304BO = (T20304BO) ContextUtil.getBean("T20304BO");
	
	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute(){
		try {
			if("add".equals(method)) {
				rspCode = add();
			} else if("delete".equals(method)) {
				rspCode = delete();
			} else if("update".equals(method)) {
				rspCode = update();
			} 
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，商户限额维护操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	
	/**
	 * 添加商户限额维护
	 * @return
	 * @throws Exception 
	 */
	private String add() throws Exception {
		CstMchtFeeInf cstMchtFeeInf = new CstMchtFeeInf();
		
		CstMchtFeeInfPK cstMchtFeeInfPK = new CstMchtFeeInfPK();
		
		cstMchtFeeInfPK.setCardType(cardType);
		
		cstMchtFeeInfPK.setMchtCd(mchtCd);
		
		cstMchtFeeInfPK.setTxnNum(txnNum);
		
		cstMchtFeeInf.setId(cstMchtFeeInfPK);
		//不考虑渠道 默认0
		cstMchtFeeInf.setChannel("0");
		
		cstMchtFeeInf.setDayNum(dayNum);
		
		cstMchtFeeInf.setDayAmt(Float.valueOf(dayAmt));
		
		cstMchtFeeInf.setDaySingle(Float.valueOf(daySingle));
		
		cstMchtFeeInf.setMonAmt(Float.valueOf(monAmt));
		
		cstMchtFeeInf.setMonNum(monNum);
		
		if(t20304BO.getMchtLimit(cstMchtFeeInfPK) != null) {
			return "该商户已经拥有该卡和该交易类型的限额信息！！";
		}
		t20304BO.addMchtLimit(cstMchtFeeInf);
		log("添加商户限额维护成功。操作员编号：" + operator.getOprId());		
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 删除商户限额
	 * @return
	 */
	private String delete() {
		t20304BO.delete(new CstMchtFeeInfPK(mchtCd,txnNum,cardType));
		log("删除商户限额维护成功。操作员编号：" + operator.getOprId());
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 更新商户限额信息
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @thr同步ows IllegalAccessException 
	 */
	private String update() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		jsonBean.parseJSONArrayData(getCstMchtFeeList());
		
		int len = jsonBean.getArray().size();
//		
		CstMchtFeeInf cstMchtFeeInf = null;
//		
		List<CstMchtFeeInf> cstMchtFeeInfList = new ArrayList<CstMchtFeeInf>(len);
		for(int i = 0; i < len; i++) {				
			mchtCd = jsonBean.getJSONDataAt(i).getString("mchtCd");
			txnNum = jsonBean.getJSONDataAt(i).getString("txnNum");
			cardType = jsonBean.getJSONDataAt(i).getString("cardType");
			cstMchtFeeInf = t20304BO.getMchtLimit(new CstMchtFeeInfPK(mchtCd,txnNum,cardType));		
			BeanUtils.setObjectWithPropertiesValue(cstMchtFeeInf, jsonBean, false);
			//不考虑渠道 默认0
			cstMchtFeeInf.setChannel("0");
			cstMchtFeeInfList.add(cstMchtFeeInf);
		}
		t20304BO.update(cstMchtFeeInfList);
		log("更新商户限额维护成功。操作员编号：" + operator.getOprId());
		return Constants.SUCCESS_CODE;
	}
	

	

	// 商户编号
	private String mchtCd;
	// 交易类型
	private String txnNum;
	// 卡类型
	private String cardType;
	// 交易渠道
	private String channel;
	// 单日累计交易笔数
	private String dayNum;
	// 单日累计交易金额
	private String dayAmt;
	// 单日单笔交易金额
	private String daySingle;
	// 单月累计交易笔数
	private String monNum;
	// 单月累计交易金额
	private String monAmt;

	private String cstMchtFeeList;

	public T20304BO getT20304BO() {
		return t20304BO;
	}

	public void setT20304BO(T20304BO t20304bo) {
		t20304BO = t20304bo;
	}

	public String getMchtCd() {
		return mchtCd;
	}

	public void setMchtCd(String mchtCd) {
		this.mchtCd = mchtCd;
	}

	public String getTxnNum() {
		return txnNum;
	}

	public void setTxnNum(String txnNum) {
		this.txnNum = txnNum;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getDayNum() {
		return dayNum;
	}

	public void setDayNum(String dayNum) {
		this.dayNum = dayNum;
	}

	public String getDayAmt() {
		return dayAmt;
	}

	public void setDayAmt(String dayAmt) {
		this.dayAmt = dayAmt;
	}

	public String getDaySingle() {
		return daySingle;
	}

	public void setDaySingle(String daySingle) {
		this.daySingle = daySingle;
	}

	public String getMonNum() {
		return monNum;
	}

	public void setMonNum(String monNum) {
		this.monNum = monNum;
	}

	public String getMonAmt() {
		return monAmt;
	}

	public void setMonAmt(String monAmt) {
		this.monAmt = monAmt;
	}

	public String getCstMchtFeeList() {
		return cstMchtFeeList;
	}

	public void setCstMchtFeeList(String cstMchtFeeList) {
		this.cstMchtFeeList = cstMchtFeeList;
	}
	
}
