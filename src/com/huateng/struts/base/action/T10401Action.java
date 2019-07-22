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
package com.huateng.struts.base.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.huateng.bo.base.T10401BO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.po.TblOprInfo;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.Encryption;
import com.huateng.system.util.SysParamUtil;

/**
 * Title:操作员信息维护
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
public class T10401Action extends BaseAction {
	
	private T10401BO t10401BO = (T10401BO) ContextUtil.getBean("T10401BO");
	
	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		try {
			if("add".equals(method)) {
				rspCode = add();
			} else if("delete".equals(method)) {
				rspCode = delete();
			} else if("update".equals(method)) {
				rspCode = update();
			} else if("edit".equals(method)) {
				rspCode = edit();
			} else if("reset".equals(method)) {
				rspCode = reset();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，操作员信息维护操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	
	/**
	 * 添加操作员
	 * @return
	 * @throws Exception 
	 */
	private String add() throws Exception {
		
		// 检查操作员编号是否已经存在
		if(t10401BO.get(oprId) != null) {
			return "操作员编号已经被使用";
		}
		
		TblOprInfo tblOprInfo = new TblOprInfo();
		
		tblOprInfo.setId(oprId);
		
		tblOprInfo.setBrhId(brhId);
		
		tblOprInfo.setOprName(oprName);
		
		tblOprInfo.setOprGender(oprGender);
		
		tblOprInfo.setOprDegree(oprDegree);
		
		tblOprInfo.setOprTel(oprTel);
		
		tblOprInfo.setOprMobile(oprMobile);
		
		tblOprInfo.setOprPwd(Encryption.encrypt(oprPwd));
		
		tblOprInfo.setRegisterDt(CommonFunction.getCurrentDate());
		
		tblOprInfo.setPwdWrTm("0");
		
		tblOprInfo.setPwdWrTmTotal("0");
		
		tblOprInfo.setPwdOutDate(CommonFunction.getOffSizeDate(
							CommonFunction.getCurrentDate(), SysParamUtil.getParam(SysParamConstants.OPR_PWD_OUT_DAY)));
		
		tblOprInfo.setOprSta("0");
		
		tblOprInfo.setLastUpdOprId(operator.getOprId());
		
		tblOprInfo.setLastUpdTs(CommonFunction.getCurrentDate());
		
		tblOprInfo.setLastUpdTxnId(getTxnId() + getSubTxnId());
		tblOprInfo.setPwdWrLastDt(CommonFunction.getCurrentDate());
		
		tblOprInfo.setOprEmail(oprEmail);
		
		if(StringUtil.isNull(oprDegreeRsc)){
			tblOprInfo.setOprDegreeRsc(" ");
		}else{
			tblOprInfo.setOprDegreeRsc(oprDegreeRsc);
		}
		tblOprInfo.setOprLogSta("0");
		tblOprInfo.setSetOprId(operator.getOprId());
		
		t10401BO.add(tblOprInfo);
		log("添加操作员信息成功。操作员编号：" + operator.getOprId()+ "，被添加操作员号：" + oprId);
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 删除操作员
	 * @return
	 */
	private String delete() {
		t10401BO.delete(oprId);
		log("删除操作员信息成功。操作员编号：" + operator.getOprId()+ "，被删除操作员号：" + oprId);
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 同步操作员信息
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private String update() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		jsonBean.parseJSONArrayData(getOprInfoList());
		
		int len = jsonBean.getArray().size();
		
		TblOprInfo tblOprInfo = null;
		
		List<TblOprInfo> tblOprInfoList = new ArrayList<TblOprInfo>(len);
		for(int i = 0; i < len; i++) {
			oprId = jsonBean.getJSONDataAt(i).getString("oprId");
			tblOprInfo = t10401BO.get(oprId);
			jsonBean.setObject(jsonBean.getJSONDataAt(i));
			BeanUtils.setObjectWithPropertiesValue(tblOprInfo, jsonBean, true);
			tblOprInfoList.add(tblOprInfo);
		}
		t10401BO.update(tblOprInfoList);
		log("同步操作员信息成功。操作员编号：" + operator.getOprId()+ "，被同步操作员信息号：" + oprId);
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 编辑操作员机构/角色信息
	 * @return
	 */
	private String edit() {
		
		TblOprInfo tblOprInfo = t10401BO.get(oprId);
		
		if(tblOprInfo == null) {
			return "您所选择的操作员信息已不存在";
		}
		
		tblOprInfo.setBrhId(brhId);
		
		tblOprInfo.setOprDegree(oprDegree);
		
		t10401BO.update(tblOprInfo);
		log("编辑操作员机构/角色信息成功。操作员编号：" + operator.getOprId()+ "，被编辑操作员机构/角色信息号：" + oprId);
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 重置/解锁操作员信息
	 * 需要将操作员密码重置并将操作员解锁定
	 * @return
	 * @throws Exception 
	 */
	private String reset() throws Exception {
		
		TblOprInfo tblOprInfo = t10401BO.get(oprId);
		
		if(tblOprInfo == null) {
			return "您所选择的操作员信息已不存在";
		}
		
		tblOprInfo.setOprPwd(Encryption.encrypt("111111"));
		
		tblOprInfo.setOprSta("0");
		
		tblOprInfo.setPwdWrTm("0");
		
		tblOprInfo.setPwdWrTmTotal("0");
		
		tblOprInfo.setPwdOutDate(CommonFunction.getOffSizeDate(
						CommonFunction.getCurrentDate(), SysParamUtil.getParam(SysParamConstants.OPR_PWD_OUT_DAY)));
		
		t10401BO.update(tblOprInfo);
		
		log("重置/解锁操作员信息成功。操作员编号：" + operator.getOprId()+ "，被重置/解锁操作员信息号：" + oprId);
		
		return Constants.SUCCESS_CODE;
	}
	

	// 操作员编号
	private String oprId;
	// 机构编号
	private String brhId;
	// 操作员级别
	private String oprDegree;
	// 操作员名称
	private String oprName;
	// 操作员性别
	private String oprGender;
	// 操作员密码
	private String oprPwd;
	// 操作员电话
	private String oprTel;
	// 操作员移动电话
	private String oprMobile;
	// 操作员信息集合
	private String oprInfoList;
	
	// 操作员信息集合
	private String oprEmail;
	
	// 操作员信息集合
	private String oprDegreeRsc;
	
	/**
	 * @return the oprId
	 */
	public String getOprId() {
		return oprId;
	}
	/**
	 * @param oprId the oprId to set
	 */
	public void setOprId(String oprId) {
		this.oprId = oprId;
	}
	/**
	 * @return the brhId
	 */
	public String getBrhId() {
		return brhId;
	}
	/**
	 * @param brhId the brhId to set
	 */
	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}
	/**
	 * @return the oprDegree
	 */
	public String getOprDegree() {
		return oprDegree;
	}
	/**
	 * @param oprDegree the oprDegree to set
	 */
	public void setOprDegree(String oprDegree) {
		this.oprDegree = oprDegree;
	}
	/**
	 * @return the oprName
	 */
	public String getOprName() {
		return oprName;
	}
	/**
	 * @param oprName the oprName to set
	 */
	public void setOprName(String oprName) {
		this.oprName = oprName;
	}
	/**
	 * @return the oprGender
	 */
	public String getOprGender() {
		return oprGender;
	}
	/**
	 * @param oprGender the oprGender to set
	 */
	public void setOprGender(String oprGender) {
		this.oprGender = oprGender;
	}
	/**
	 * @return the oprPwd
	 */
	public String getOprPwd() {
		return oprPwd;
	}
	/**
	 * @param oprPwd the oprPwd to set
	 */
	public void setOprPwd(String oprPwd) {
		this.oprPwd = oprPwd;
	}
	/**
	 * @return the oprTel
	 */
	public String getOprTel() {
		return oprTel;
	}
	/**
	 * @param oprTel the oprTel to set
	 */
	public void setOprTel(String oprTel) {
		this.oprTel = oprTel;
	}
	/**
	 * @return the oprMobile
	 */
	public String getOprMobile() {
		return oprMobile;
	}
	/**
	 * @param oprMobile the oprMobile to set
	 */
	public void setOprMobile(String oprMobile) {
		this.oprMobile = oprMobile;
	}

	/**
	 * @return the oprInfoList
	 */
	public String getOprInfoList() {
		return oprInfoList;
	}

	/**
	 * @param oprInfoList the oprInfoList to set
	 */
	public void setOprInfoList(String oprInfoList) {
		this.oprInfoList = oprInfoList;
	}

	public String getOprEmail() {
		return oprEmail;
	}

	public void setOprEmail(String oprEmail) {
		this.oprEmail = oprEmail;
	}

	public String getOprDegreeRsc() {
		return oprDegreeRsc;
	}

	public void setOprDegreeRsc(String oprDegreeRsc) {
		this.oprDegreeRsc = oprDegreeRsc;
	}
	
	
	
}
