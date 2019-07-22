/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-7-31       first release
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

import java.util.ArrayList;
import java.util.List;

import com.huateng.bo.mchnt.T20302BO;
import com.huateng.common.Constants;
import com.huateng.po.mchnt.TblInfMchntTpGrp;
import com.huateng.po.mchnt.TblInfMchntTpGrpPK;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

/**
 * Title:商户组别维护
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-7-31
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T20302Action extends BaseAction {
	
	// 商户组别编号
	private String mchntTpGrp;
	// 商户组别维护
	private String grpDescr;
	// 商户组别信息集合
	private String mchntTpGrpList;

	
	private T20302BO t20302BO = (T20302BO) ContextUtil.getBean("T20302BO");
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
			log("操作员编号：" + operator.getOprId()+ "，商户组别维护操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	
	
	/**
	 * 添加商户组别信息
	 * @return
	 */
	private String add() {
		
		TblInfMchntTpGrp tblInfMchntTpGrp = new TblInfMchntTpGrp();
		TblInfMchntTpGrpPK id = new TblInfMchntTpGrpPK(mchntTpGrp,"0");
		if(t20302BO.get(id) != null) {
			return "您输入的商户类型组别已经存在，请重新输入!";
		}
		
		tblInfMchntTpGrp.setId(id);
		tblInfMchntTpGrp.setDescr(grpDescr);
		tblInfMchntTpGrp.setRecUpdUsrId(operator.getOprId());	
		tblInfMchntTpGrp.setLastOperIn(Constants.ADD);
		tblInfMchntTpGrp.setRecCrtTs(CommonFunction.getCurrentDateTime());
		tblInfMchntTpGrp.setRecSt(Constants.VALID);
		tblInfMchntTpGrp.setRecUpdTs(CommonFunction.getCurrentDateTime());

		log("添加商户组别信息成功。操作员编号：" + operator.getOprId());
		t20302BO.add(tblInfMchntTpGrp);		
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 删除商户组别信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String delete() {
		
		String sql = "select * from tbl_inf_mchnt_tp where mchnt_tp_grp = '" + mchntTpGrp + "'";
		
		List<Object[]> result = commQueryDAO.findBySQLQuery(sql);
		
		if(result.size() > 0) {
			return "请先清空该组别下的商户MCC,再删除该组别";
		}
		
		TblInfMchntTpGrpPK id = new TblInfMchntTpGrpPK(mchntTpGrp,"0");
		
		t20302BO.delete(id);
		log("删除商户组别信息成功。操作员编号：" + operator.getOprId());
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 同步商户组别信息
	 * @return
	 */
	private String update() {
		
		jsonBean.parseJSONArrayData(mchntTpGrpList);
		
		int len = jsonBean.getArray().size();
		
		TblInfMchntTpGrp tblMchntTpGrp = null;
		
		List<TblInfMchntTpGrp> tblMchntTpGrpList = new ArrayList<TblInfMchntTpGrp>(len);
		for(int i = 0; i < len; i++) {
			TblInfMchntTpGrpPK id =  new TblInfMchntTpGrpPK(jsonBean.getJSONDataAt(i).getString("mchntTpGrp"),"0");
			tblMchntTpGrp = t20302BO.get(id);
			tblMchntTpGrp.setRecUpdUsrId(operator.getOprId());
			tblMchntTpGrp.setRecUpdTs(CommonFunction.getCurrentDate());
			tblMchntTpGrp.setDescr(jsonBean.getJSONDataAt(i).getString("descr"));
			tblMchntTpGrpList.add(tblMchntTpGrp);
		}
		t20302BO.update(tblMchntTpGrpList);
		log("同步商户组别信息成功。操作员编号：" + operator.getOprId());
		return Constants.SUCCESS_CODE;
	}
	
	
	/**
	 * @return the mchntTpGrp
	 */
	public String getMchntTpGrp() {
		return mchntTpGrp;
	}

	/**
	 * @param mchntTpGrp the mchntTpGrp to set
	 */
	public void setMchntTpGrp(String mchntTpGrp) {
		this.mchntTpGrp = mchntTpGrp;
	}

	/**
	 * @return the grpDescr
	 */
	public String getGrpDescr() {
		return grpDescr;
	}

	/**
	 * @param grpDescr the grpDescr to set
	 */
	public void setGrpDescr(String grpDescr) {
		this.grpDescr = grpDescr;
	}


	/**
	 * @return the mchntTpGrpList
	 */
	public String getMchntTpGrpList() {
		return mchntTpGrpList;
	}


	/**
	 * @param mchntTpGrpList the mchntTpGrpList to set
	 */
	public void setMchntTpGrpList(String mchntTpGrpList) {
		this.mchntTpGrpList = mchntTpGrpList;
	}
	

	
	
}
