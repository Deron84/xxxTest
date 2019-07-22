/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   Gavin      2011-6-17       first release
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
package com.huateng.bo.impl.mchnt;

import java.lang.reflect.InvocationTargetException;

import com.huateng.po.TblMchtCupInfo;
import com.huateng.po.TblMchtCupInfoTmp;
import com.huateng.po.mchnt.TblGroupMchtInf;
import com.huateng.po.mchnt.TblMchntUser;
import com.huateng.po.mchnt.TblMchtAcctInf;
import com.huateng.po.mchnt.TblMchtBaseInf;
import com.huateng.po.mchnt.TblMchtBaseInfTmp;
import com.huateng.po.mchnt.TblMchtLicenceInf;
import com.huateng.po.mchnt.TblMchtLicenceInfTmp;
import com.huateng.po.mchnt.TblMchtSettleInf;
import com.huateng.po.mchnt.TblMchtSettleInfTmp;



/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-17
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public interface TblMchntService {
	
	
	/**
	 * 保存商户临时信息
	 * @param tblMchtBaseInfTmp
	 * @param tblMchtSettleInfTmp
	 * @return
	 */
	public String saveTmp(TblMchtBaseInfTmp tblMchtBaseInfTmp, TblMchtSettleInfTmp tblMchtSettleInfTmp, TblMchtCupInfoTmp tblMchtCupInfoTmp);
	
	/**
	 * 保存证照号临时信息
	 * @param tblMchtLicenceInfTmp
	 * @return
	 */
	public String saveLicenceTmp(TblMchtLicenceInfTmp tblMchtLicenceInfTmp);
	
	/**
	 * 更新商户临时信息
	 * @param tblMchtLicenceInfTmp
	 * @return
	 */
	public String updateLicenceTmp(TblMchtLicenceInfTmp tblMchtLicenceInfTmp);
	
	
	/**
	 * 更新商户临时信息
	 * @param tblMchtBaseInfTmp
	 * @param tblMchtSettleInfTmp
	 * @return
	 */
	public String updateTmp(TblMchtBaseInfTmp tblMchtBaseInfTmp, TblMchtSettleInfTmp tblMchtSettleInfTmp, TblMchtCupInfoTmp tblMchtCupInfoTmp);
	
	/**
	 * 商户信息审核（通过）
	 * @param mchntId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String accept(String mchntId) throws IllegalAccessException, InvocationTargetException;
	
	/**
	 * 商户信息审核（退回）
	 * @param mchntId
	 * @param refuseInfo
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String back(String mchntId, String refuseInfo) throws IllegalAccessException, InvocationTargetException;
	
	/**
	 * 商户信息审核（拒绝）
	 * @param mchntId
	 * @param refuseInfo
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String refuse(String mchntId, String refuseInfo) throws IllegalAccessException, InvocationTargetException;
	
	/**
	 * 保存集团商户信息
	 * @param inf
	 * @param acctinf
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String saveGroup(TblGroupMchtInf inf, TblMchtAcctInf acctinf) throws IllegalAccessException, InvocationTargetException;
	
	/**
	 * 更新集团商户信息
	 * @param inf
	 * @param acctinf
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String updateGroup(TblGroupMchtInf inf, TblMchtAcctInf acctinf) throws IllegalAccessException, InvocationTargetException;
	
	/**
	 * 获取商户信息
	 * @param mchntId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * 2011-6-24下午02:34:05
	 */
	public TblMchtBaseInf getMccByMchntId(String mchntId) throws IllegalAccessException, InvocationTargetException;
	
	/**
	 * @param mchntId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public TblGroupMchtInf getGroupInf(String mchntId) throws IllegalAccessException, InvocationTargetException;
	
	/**
	 * 读取商户临时表基本信息
	 * @param mchntId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public TblMchtBaseInfTmp getBaseInfTmp(String mchntId) throws IllegalAccessException, InvocationTargetException;
	
	public TblMchtBaseInf getBaseInf(String mchntId) throws IllegalAccessException, InvocationTargetException;

	public TblMchtCupInfoTmp getCupInfoTmp(String mchntId) throws IllegalAccessException, InvocationTargetException;
	
	public TblMchtCupInfo getCupInfo(String mchntId) throws IllegalAccessException, InvocationTargetException;
	
	/**
	 * 读取证照号临时表基本信息
	 * @param mchntId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public TblMchtLicenceInfTmp getLicenceInfTmp(String licenceNo) throws IllegalAccessException, InvocationTargetException;
	
	public TblMchtLicenceInf getLicenceInf(String licenceNo) throws IllegalAccessException, InvocationTargetException;

	
	/**
	 * 更新商户临时表基本信息
	 * @param mchntId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String updateBaseInfTmp(TblMchtBaseInfTmp inf) throws IllegalAccessException, InvocationTargetException;
	
	/**
	 * 读取商户临时表清算信息
	 * @param mchntId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public TblMchtSettleInfTmp getSettleInfTmp(String mchntId) throws IllegalAccessException, InvocationTargetException;
	public TblMchtSettleInf getSettleInf(String mchntId) throws IllegalAccessException, InvocationTargetException;

	/**
	 * 证照号信息审核（通过）
	 * @param mchntId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String acceptLicence(String licenceNo) throws IllegalAccessException, InvocationTargetException;
	
	/**
	 * 证照号信息审核（退回）
	 * @param mchntId
	 * @param refuseInfo
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String backLicence(String licenceNo, String refuseInfo) throws IllegalAccessException, InvocationTargetException;
	
	/**
	 * 商户信息审核（拒绝）
	 * @param mchntId
	 * @param refuseInfo
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public String refuseLicence(String licenceNo, String refuseInfo) throws IllegalAccessException, InvocationTargetException;

	/**
	 * @param mchtNo
	 * @return
	 */
	public String deleteMchntAll(String mchtNo);
	
	public void saveMchtUser(TblMchntUser tblMchtUser);
}
