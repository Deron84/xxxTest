/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2011-10-11       first release
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
package com.huateng.struts.mchtSrv.action;

import java.math.BigDecimal;

import org.springframework.beans.BeanUtils;

import com.huateng.bo.impl.mchtSrv.ProfessionalOrgan;
import com.huateng.common.Constants;
import com.huateng.dao.common.SqlDao;
import com.huateng.po.mchtSrv.TblProfessionalOrgan;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-10-11
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Lysander
 * 
 * @version 1.0
 */
public class T70301Action extends BaseSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6168197261385499519L;

	//ADD
	private String orgId;

	private String branch;

	private String orgName;

	private String areaCode;
	
	private String principal;
	
	private String linkman;
	
	private String linkTel;

	// private String rateType;
	private String remarks;

	//UPDATE
	private String orgIdU;

	private String branchU;

	private String orgNameU;

	private String areaCodeU;

	private String remarksU;
	
	private String principalU;
	
	private String linkmanU;
	
	private String linkTelU;

	private ProfessionalOrgan service = (ProfessionalOrgan) ContextUtil
			.getBean("professionalOrganSrv");

	public String add() {
		try {
			TblProfessionalOrgan old = service.get(orgId);
			if(old!=null){
				success = false;
				msg = "已存在该代码的服务机构:"+orgId;
				return this.SUCCESS;
			}
			TblProfessionalOrgan tblProfessionalOrgan = new TblProfessionalOrgan();
			tblProfessionalOrgan.setRate(BigDecimal.valueOf(1));// 利率现在不用
			BeanUtils.copyProperties(this, tblProfessionalOrgan);
			tblProfessionalOrgan.setCrtOpr(getOperator().getOprId());
			tblProfessionalOrgan.setCrtTs(CommonFunction.getCurrentDateTime());
			tblProfessionalOrgan.setLastUpdOpr(getOperator().getOprId());
			tblProfessionalOrgan.setLastUpdTs(CommonFunction
					.getCurrentDateTime());

			rspCode = service.save(tblProfessionalOrgan);
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}

	}

	public String update() {
		try {
			TblProfessionalOrgan tblProfessionalOrgan = service.get(orgIdU);
			tblProfessionalOrgan.setBranch(branchU);
			tblProfessionalOrgan.setAreaCode(areaCodeU);
			tblProfessionalOrgan.setOrgName(orgNameU);
			tblProfessionalOrgan.setRemarks(remarksU);
			tblProfessionalOrgan.setPrincipal(principalU);
			tblProfessionalOrgan.setLinkman(linkmanU);
			tblProfessionalOrgan.setLinkTel(linkTelU);
			tblProfessionalOrgan.setRate(BigDecimal.valueOf(1));// 利率现在不用
			tblProfessionalOrgan.setLastUpdOpr(getOperator().getOprId());
			tblProfessionalOrgan.setLastUpdTs(CommonFunction
					.getCurrentDateTime());

			rspCode = service.modify(tblProfessionalOrgan);
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
	}

	public String del() {
		try{
			SqlDao dao = (SqlDao)ContextUtil.getBean("sqlDao");
			String sql = "delete TBL_PROFESSIONAL_ORGAN where org_id='"+orgId+"'";
			dao.execute(sql);
			success = true;
			msg = "删除外包商信息成功";
		}catch (Exception e) {
			e.printStackTrace();
			success = false;
			msg = "删除外包商信息失败";
		}
		return this.SUCCESS;
		
	}

	@Override
	public String getMsg() {
		return msg;
	}

	@Override
	public boolean isSuccess() {
		return success;
	}
	
	
	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getLinkTel() {
		return linkTel;
	}

	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}

	public ProfessionalOrgan getService() {
		return service;
	}

	public void setService(ProfessionalOrgan service) {
		this.service = service;
	}
	
	public String getOrgIdU() {
		return orgIdU;
	}

	public void setOrgIdU(String orgIdU) {
		this.orgIdU = orgIdU;
	}

	public String getBranchU() {
		return branchU;
	}

	public void setBranchU(String branchU) {
		this.branchU = branchU;
	}

	public String getOrgNameU() {
		return orgNameU;
	}

	public void setOrgNameU(String orgNameU) {
		this.orgNameU = orgNameU;
	}

	public String getAreaCodeU() {
		return areaCodeU;
	}

	public void setAreaCodeU(String areaCodeU) {
		this.areaCodeU = areaCodeU;
	}

	public String getRemarksU() {
		return remarksU;
	}

	public void setRemarksU(String remarksU) {
		this.remarksU = remarksU;
	}

	public String getPrincipalU() {
		return principalU;
	}

	public void setPrincipalU(String principalU) {
		this.principalU = principalU;
	}

	public String getLinkmanU() {
		return linkmanU;
	}

	public void setLinkmanU(String linkmanU) {
		this.linkmanU = linkmanU;
	}

	public String getLinkTelU() {
		return linkTelU;
	}

	public void setLinkTelU(String linkTelU) {
		this.linkTelU = linkTelU;
	}
}
