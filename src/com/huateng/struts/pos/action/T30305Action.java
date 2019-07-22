package com.huateng.struts.pos.action;

import org.apache.commons.lang.xwork.StringUtils;

import com.huateng.bo.term.T3010BO;
import com.huateng.bo.term.TermManagementBO;
import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.dao.common.HqlDao;
import com.huateng.po.TblTermInf;
import com.huateng.po.TblTermInfTmp;
import com.huateng.po.TblTermInfTmpPK;
import com.huateng.po.TblTermManagement;
import com.huateng.struts.pos.TblTermManagementConstants;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.ContextUtil;

public class T30305Action extends BaseAction {
	
	private static final long serialVersionUID = -7458083560605416590L;
	private TermManagementBO t3030BO;
	private T3010BO t3010BO;
	private String termId;
	private String termIdId;
	private String pinPad;
	private String mchtCd;
	
	
	public String getMchtCd() {
		return mchtCd;
	}

	
	public void setMchtCd(String mchtCd) {
		this.mchtCd = mchtCd;
	}

	/**
	 * @param t3030bo the t3030BO to set
	 */
	public void setT3030BO(TermManagementBO t3030bo) {
		t3030BO = t3030bo;
	}

	/**
	 * @param t3010bo the t3010BO to set
	 */
	public void setT3010BO(T3010BO t3010bo) {
		t3010BO = t3010bo;
	}

	/**
	 * @return the termId
	 */
	public String getTermId() {
		return termId;
	}

	/**
	 * @param termId the termId to set
	 */
	public void setTermId(String termId) {
		this.termId = termId;
	}

	/**
	 * @return the termIdId
	 */
	public String getTermIdId() {
		return termIdId;
	}

	/**
	 * @param termIdId the termIdId to set
	 */
	public void setTermIdId(String termIdId) {
		this.termIdId = termIdId;
	}
	/**
	 * @return the pinPad
	 */
	public String getPinPad() {
		return pinPad;
	}

	/**
	 * @param pinPad the pinPad to set
	 */
	public void setPinPad(String pinPad) {
		this.pinPad = pinPad;
	}


	@Override
	protected String subExecute() throws Exception {
		TblTermInfTmpPK pk = new  TblTermInfTmpPK(termId,mchtCd);
		HqlDao hqlDao = (HqlDao) ContextUtil.getBean("hqlDao");
		TblTermInf tblTermInf = (TblTermInf) hqlDao.get(TblTermInf.class, pk);
//		TblTermInf tblTermInf = t3010BO.getTermInfo(CommonFunction.fillString(termId, ' ', 12, true),mchtCd);
		TblTermInfTmp tblTermInfTmp = t3010BO.get(tblTermInf.getId().getTermId(),tblTermInf.getId().getMchtCd());
		TblTermManagement tblTermManagement = t3030BO.getTerminal(termIdId);
		if(tblTermInf == null)
			return TblTermManagementConstants.T30305_01;
		if(tblTermManagement == null)
			return TblTermManagementConstants.T30305_02;
		if(StringUtils.isNotEmpty(tblTermManagement.getMechNo())){
			return TblTermManagementConstants.T30305_06;
		}
		if(!StringUtils.trim(tblTermInf.getTermIdId()).equals(Constants.DEFAULT))//新增终端时候该字段赋值为-
			return TblTermManagementConstants.T30305_04;
		if(tblTermManagement.getState().equals(TblTermManagementConstants.TERM_STATE_INVALID))
			return TblTermManagementConstants.T30305_05;
		tblTermManagement.setPinPad(pinPad);
		if(!tblTermManagement.getState().equals(TblTermManagementConstants.TERM_STATE_END))
			t3030BO.bindTermInfo(tblTermManagement,operator.getOprId(),tblTermInf,tblTermInfTmp);
		else
			return TblTermManagementConstants.T30305_03;
		return Constants.SUCCESS_CODE;
	}

}
