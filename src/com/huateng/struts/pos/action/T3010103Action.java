package com.huateng.struts.pos.action;

import com.huateng.bo.term.T3010BO;
import com.huateng.common.Constants;
import com.huateng.po.TblTermInfTmp;
import com.huateng.po.TblTermInfTmpPK;
import com.huateng.struts.pos.TblTermInfConstants;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;

public class T3010103Action extends BaseAction {
/**没用到*/
	private static final long serialVersionUID = 8817290734735660318L;
	private T3010BO t3010BO;
	private String termId;
	private String mchtCd;
	

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
	 * @param t3010bo the t3010BO to set
	 */
	public void setT3010BO(T3010BO t3010bo) {
		t3010BO = t3010bo;
	}

	@Override
	protected String subExecute() throws Exception {
		TblTermInfTmp tblTermInf = t3010BO.get(CommonFunction.fillString(termId, ' ', 12, true), mchtCd);
		TblTermInfTmpPK pk = new TblTermInfTmpPK();
		String id = t3010BO.getTermId(tblTermInf.getTermBranch());
		pk.setMchtCd(mchtCd);
		pk.setTermId(id);
		tblTermInf.setId(pk);
		tblTermInf.setKeyDownSign(TblTermInfConstants.DEFUALT_CHECKBOX);
		tblTermInf.setParam1DownSign(TblTermInfConstants.DEFUALT_CHECKBOX);
		tblTermInf.setParamDownSign(TblTermInfConstants.UN_CHECKED);
		tblTermInf.setSupportIc(TblTermInfConstants.DEFUALT_CHECKBOX);
		tblTermInf.setTermSta(TblTermInfConstants.TERM_ST_INIT);
		tblTermInf.setTermFactory(null);
		tblTermInf.setTermMachTp(null);
		String termId = t3010BO.add(tblTermInf);
		if(termId != null)
		{
			return Constants.SUCCESS_CODE_CUSTOMIZE+"新增终端号："+termId;
		}
		return Constants.FAILURE_CODE;
	}

}
