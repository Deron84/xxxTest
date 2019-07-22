/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   Gavin      2011-6-24       first release
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


import com.huateng.bo.impl.mchnt.TblMchntService;
import com.huateng.common.StringUtil;
import com.huateng.common.TblMchntInfoConstants;
import com.huateng.po.mchnt.TblGroupMchtInf;
import com.huateng.po.mchnt.TblMchtAcctInf;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;

/**
 * Title:集团商户信息维护
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-24
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T20501Action extends BaseSupport{
	
	
	/**
	 * 添加商户信息
	 * @return
	 */
	public String add() {
		
		try {
			System.out.println("add invoked");
			TblGroupMchtInf inf = buildTblGroupMchtInf();
			TblMchtAcctInf infacct = buildTblMchtAcctInf();
			TblGroupMchtInf exist = service.getGroupInf(groupMchtCd);
			if (null != exist) {
				return returnService("您输入的集团商户号已经存在[" + groupMchtCd + "]");
			}
			rspCode = service.saveGroup(inf, infacct);
			
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode,e);
		}
	}
	

	/**
	 * 更新集团商户信息
	 * 
	 * @return
	 */
	public String update() {
		
		try {
			TblGroupMchtInf inf = service.getGroupInf(groupMchtCd);
			if (null == inf) {
				return returnService("没有找到指定的集团商户信息，请重试");
			}
			inf = updateTblGroupMchtInf(inf);
			rspCode = service.updateGroup(inf, new TblMchtAcctInf());
		
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
	}
	
	
	
	/**
	 * 构造集团商户信息
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * 2011-6-21下午01:52:21
	 */
	public TblGroupMchtInf buildTblGroupMchtInf() throws IllegalAccessException, InvocationTargetException {
		
		TblGroupMchtInf inf = new TblGroupMchtInf();
		
		BeanUtils.copyProperties(inf, this);
		
		//临时
		inf.setProvCd("00");
		
		// 记录修改时间
		inf.setRecUpdTs(CommonFunction.getCurrentDateTime());
		// 记录创建时间
		inf.setRecCrtTs(CommonFunction.getCurrentDateTime());
		// 记录修改人
		inf.setRecUpdOpr(getOperator().getOprId());
		
		return inf;
	}
	
	/**
	 * 修改集团商户信息
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * 2011-6-21下午01:52:21
	 */
	public TblGroupMchtInf updateTblGroupMchtInf(TblGroupMchtInf inf) throws IllegalAccessException, InvocationTargetException {
		
		BeanUtils.copyProperties(inf, this);
		// 记录修改时间
		inf.setRecUpdTs(CommonFunction.getCurrentDateTime());
		// 记录修改人
		inf.setRecUpdOpr(getOperator().getOprId());
		
		return inf;
	}
	
	
	/**
	 * 构造集团清算信息
	 * 
	 * @param request
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public TblMchtAcctInf buildTblMchtAcctInf() throws IllegalAccessException, InvocationTargetException {
		
		TblMchtAcctInf inf = new TblMchtAcctInf();
		
		BeanUtils.copyProperties(inf, this);		
		
		
		//是否支持无磁无密交易
		if (!StringUtil.isNull(stlmGroupFlg) 
				&& TblMchntInfoConstants.EXTJS_CHECKED.equalsIgnoreCase(stlmGroupFlg)) {
			inf.setStlmGroupFlg("1");
		} else {
			inf.setStlmGroupFlg("0");
		}
		//是否支持人工授权交易
		if (!StringUtil.isNull(mchtHoliFlg) 
				&& TblMchntInfoConstants.EXTJS_CHECKED.equalsIgnoreCase(mchtHoliFlg)) {
			inf.setMchtHoliFlg("1");
		} else {
			inf.setMchtHoliFlg("0");
		}
		//是否支持折扣消费
		if (!StringUtil.isNull(mchtStlmDtlFlg) 
				&& TblMchntInfoConstants.EXTJS_CHECKED.equalsIgnoreCase(mchtStlmDtlFlg)) {
			inf.setMchtStlmDtlFlg("1");
		} else {
			inf.setMchtStlmDtlFlg("0");
		}
		
		
		//临时
		inf.setAcqInsIdCd("0");
		
		
		
		// 记录修改时间
		inf.setRecUpdTs(CommonFunction.getCurrentDateTime());
		// 记录创建时间
		inf.setRecCrtTs(CommonFunction.getCurrentDateTime());
		// 记录修改人
		inf.setRecUpdOpr(getOperator().getOprId());
		
		return inf;
		
	}
	private java.lang.String groupMchtCd;
	private java.lang.String groupType;
	private java.lang.String groupName;
//	private java.lang.String provCd;
	private java.lang.String cityCd;
	private java.lang.String headAddr;
	private java.lang.String regFund;
	private java.lang.String busRange;
	private java.lang.String mchtPerson;
	private java.lang.String contactAddr;
	private java.lang.String zipCd;
	
	private java.lang.String mchtCd;
	private java.lang.String mchtType;
	private java.lang.String mchtStlmMd;
	private java.lang.String mchtStlmChnl;
	private java.lang.String mchtCpStlmCycle;
	private java.lang.String mchtCpStlmMd;
	private java.lang.String mchtFeStlmCycle;
	private java.lang.String mchtFeStlmMd;
	private java.lang.String mchtAloStlmCy;
	private java.lang.String mchtAloStlmMd;
	private java.lang.String mchtStlmInsId;
	private java.lang.String mchtStlmInsNm;
	private java.lang.String mchtOutInMd;
	private java.lang.String mchtStlmCNm;
	private java.lang.String mchtStlmCAcct;
	private java.lang.String mchtStlmDNm;
	private java.lang.String mchtStlmDAcct;
	private java.lang.String mchtCityTran;
	private java.lang.String mchtPaySysAcct;
	private java.lang.String mchtCurrcyCd;
	private java.lang.String mchtStlmWay;
	private java.lang.String stlmGroupFlg;
	private java.lang.String mchtHoliFlg;
	private java.lang.String mchtAccDt;
	private java.lang.String chkSndCycle;
	private java.lang.String chkSndMd;
	private java.lang.String mchtStlmDtlFlg;
	private java.lang.String mchtStlmStyle;
	private java.lang.String mchtShortCn;
	private java.lang.String acqInsIdCd;
	public TblMchntService getService() {
		return service;
	}

	public void setService(TblMchntService service) {
		this.service = service;
	}

	public java.lang.String getGroupMchtCd() {
		return groupMchtCd;
	}

	public void setGroupMchtCd(java.lang.String groupMchtCd) {
		this.groupMchtCd = groupMchtCd;
	}

	public java.lang.String getGroupType() {
		return groupType;
	}

	public void setGroupType(java.lang.String groupType) {
		this.groupType = groupType;
	}

	public java.lang.String getGroupName() {
		return groupName;
	}

	public void setGroupName(java.lang.String groupName) {
		this.groupName = groupName;
	}

	public java.lang.String getCityCd() {
		return cityCd;
	}

	public void setCityCd(java.lang.String cityCd) {
		this.cityCd = cityCd;
	}

	public java.lang.String getHeadAddr() {
		return headAddr;
	}

	public void setHeadAddr(java.lang.String headAddr) {
		this.headAddr = headAddr;
	}

	public java.lang.String getRegFund() {
		return regFund;
	}

	public void setRegFund(java.lang.String regFund) {
		this.regFund = regFund;
	}

	public java.lang.String getBusRange() {
		return busRange;
	}

	public void setBusRange(java.lang.String busRange) {
		this.busRange = busRange;
	}

	public java.lang.String getMchtPerson() {
		return mchtPerson;
	}

	public void setMchtPerson(java.lang.String mchtPerson) {
		this.mchtPerson = mchtPerson;
	}

	public java.lang.String getContactAddr() {
		return contactAddr;
	}

	public void setContactAddr(java.lang.String contactAddr) {
		this.contactAddr = contactAddr;
	}

	public java.lang.String getZipCd() {
		return zipCd;
	}

	public void setZipCd(java.lang.String zipCd) {
		this.zipCd = zipCd;
	}

	public java.lang.String getMchtCd() {
		return mchtCd;
	}

	public void setMchtCd(java.lang.String mchtCd) {
		this.mchtCd = mchtCd;
	}

	public java.lang.String getMchtType() {
		return mchtType;
	}

	public void setMchtType(java.lang.String mchtType) {
		this.mchtType = mchtType;
	}

	public java.lang.String getMchtStlmMd() {
		return mchtStlmMd;
	}

	public void setMchtStlmMd(java.lang.String mchtStlmMd) {
		this.mchtStlmMd = mchtStlmMd;
	}

	public java.lang.String getMchtStlmChnl() {
		return mchtStlmChnl;
	}

	public void setMchtStlmChnl(java.lang.String mchtStlmChnl) {
		this.mchtStlmChnl = mchtStlmChnl;
	}

	public java.lang.String getMchtCpStlmCycle() {
		return mchtCpStlmCycle;
	}

	public void setMchtCpStlmCycle(java.lang.String mchtCpStlmCycle) {
		this.mchtCpStlmCycle = mchtCpStlmCycle;
	}

	public java.lang.String getMchtCpStlmMd() {
		return mchtCpStlmMd;
	}

	public void setMchtCpStlmMd(java.lang.String mchtCpStlmMd) {
		this.mchtCpStlmMd = mchtCpStlmMd;
	}

	public java.lang.String getMchtFeStlmCycle() {
		return mchtFeStlmCycle;
	}

	public void setMchtFeStlmCycle(java.lang.String mchtFeStlmCycle) {
		this.mchtFeStlmCycle = mchtFeStlmCycle;
	}

	public java.lang.String getMchtFeStlmMd() {
		return mchtFeStlmMd;
	}

	public void setMchtFeStlmMd(java.lang.String mchtFeStlmMd) {
		this.mchtFeStlmMd = mchtFeStlmMd;
	}

	public java.lang.String getMchtAloStlmCy() {
		return mchtAloStlmCy;
	}

	public void setMchtAloStlmCy(java.lang.String mchtAloStlmCy) {
		this.mchtAloStlmCy = mchtAloStlmCy;
	}

	public java.lang.String getMchtAloStlmMd() {
		return mchtAloStlmMd;
	}

	public void setMchtAloStlmMd(java.lang.String mchtAloStlmMd) {
		this.mchtAloStlmMd = mchtAloStlmMd;
	}

	public java.lang.String getMchtStlmInsId() {
		return mchtStlmInsId;
	}

	public void setMchtStlmInsId(java.lang.String mchtStlmInsId) {
		this.mchtStlmInsId = mchtStlmInsId;
	}

	public java.lang.String getMchtStlmInsNm() {
		return mchtStlmInsNm;
	}

	public void setMchtStlmInsNm(java.lang.String mchtStlmInsNm) {
		this.mchtStlmInsNm = mchtStlmInsNm;
	}

	public java.lang.String getMchtOutInMd() {
		return mchtOutInMd;
	}

	public void setMchtOutInMd(java.lang.String mchtOutInMd) {
		this.mchtOutInMd = mchtOutInMd;
	}

	public java.lang.String getMchtStlmCNm() {
		return mchtStlmCNm;
	}

	public void setMchtStlmCNm(java.lang.String mchtStlmCNm) {
		this.mchtStlmCNm = mchtStlmCNm;
	}

	public java.lang.String getMchtStlmCAcct() {
		return mchtStlmCAcct;
	}

	public void setMchtStlmCAcct(java.lang.String mchtStlmCAcct) {
		this.mchtStlmCAcct = mchtStlmCAcct;
	}

	public java.lang.String getMchtStlmDNm() {
		return mchtStlmDNm;
	}

	public void setMchtStlmDNm(java.lang.String mchtStlmDNm) {
		this.mchtStlmDNm = mchtStlmDNm;
	}

	public java.lang.String getMchtStlmDAcct() {
		return mchtStlmDAcct;
	}

	public void setMchtStlmDAcct(java.lang.String mchtStlmDAcct) {
		this.mchtStlmDAcct = mchtStlmDAcct;
	}

	public java.lang.String getMchtCityTran() {
		return mchtCityTran;
	}

	public void setMchtCityTran(java.lang.String mchtCityTran) {
		this.mchtCityTran = mchtCityTran;
	}

	public java.lang.String getMchtPaySysAcct() {
		return mchtPaySysAcct;
	}

	public void setMchtPaySysAcct(java.lang.String mchtPaySysAcct) {
		this.mchtPaySysAcct = mchtPaySysAcct;
	}

	public java.lang.String getMchtCurrcyCd() {
		return mchtCurrcyCd;
	}

	public void setMchtCurrcyCd(java.lang.String mchtCurrcyCd) {
		this.mchtCurrcyCd = mchtCurrcyCd;
	}

	public java.lang.String getMchtStlmWay() {
		return mchtStlmWay;
	}

	public void setMchtStlmWay(java.lang.String mchtStlmWay) {
		this.mchtStlmWay = mchtStlmWay;
	}

	public java.lang.String getStlmGroupFlg() {
		return stlmGroupFlg;
	}

	public void setStlmGroupFlg(java.lang.String stlmGroupFlg) {
		this.stlmGroupFlg = stlmGroupFlg;
	}

	public java.lang.String getMchtHoliFlg() {
		return mchtHoliFlg;
	}

	public void setMchtHoliFlg(java.lang.String mchtHoliFlg) {
		this.mchtHoliFlg = mchtHoliFlg;
	}

	public java.lang.String getMchtAccDt() {
		return mchtAccDt;
	}

	public void setMchtAccDt(java.lang.String mchtAccDt) {
		this.mchtAccDt = mchtAccDt;
	}

	public java.lang.String getChkSndCycle() {
		return chkSndCycle;
	}

	public void setChkSndCycle(java.lang.String chkSndCycle) {
		this.chkSndCycle = chkSndCycle;
	}

	public java.lang.String getChkSndMd() {
		return chkSndMd;
	}

	public void setChkSndMd(java.lang.String chkSndMd) {
		this.chkSndMd = chkSndMd;
	}

	public java.lang.String getMchtStlmDtlFlg() {
		return mchtStlmDtlFlg;
	}

	public void setMchtStlmDtlFlg(java.lang.String mchtStlmDtlFlg) {
		this.mchtStlmDtlFlg = mchtStlmDtlFlg;
	}

	public java.lang.String getMchtStlmStyle() {
		return mchtStlmStyle;
	}

	public void setMchtStlmStyle(java.lang.String mchtStlmStyle) {
		this.mchtStlmStyle = mchtStlmStyle;
	}

	public java.lang.String getMchtShortCn() {
		return mchtShortCn;
	}

	public void setMchtShortCn(java.lang.String mchtShortCn) {
		this.mchtShortCn = mchtShortCn;
	}

	public java.lang.String getAcqInsIdCd() {
		return acqInsIdCd;
	}

	public void setAcqInsIdCd(java.lang.String acqInsIdCd) {
		this.acqInsIdCd = acqInsIdCd;
	}
	
	
	public String getMsg() {
		return msg;
	}

	public boolean isSuccess() {
		return success;
	}
	
}
