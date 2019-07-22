package com.huateng.struts.pos.action;

import com.huateng.bo.term.T3010BO;
import com.huateng.common.Constants;
import com.huateng.struts.system.action.BaseAction;

public class T30203Action extends BaseAction {

	private static final long serialVersionUID = -3156898368459245662L;
	private T3010BO t3010BO;
	private String[] termIdId;
	private String[] batchNo;
	
	/**
	 * @return the termIdId
	 */
	public String[] getTermIdId() {
		return termIdId;
	}

	/**
	 * @param termIdId the termIdId to set
	 */
	public void setTermIdId(String[] termIdId) {
		this.termIdId = termIdId;
	}

	/**
	 * @return the batchNo
	 */
	public String[] getBatchNo() {
		return batchNo;
	}

	/**
	 * @param batchNo the batchNo to set
	 */
	public void setBatchNo(String[] batchNo) {
		this.batchNo = batchNo;
	}

	/**
	 * @param t3010bo the t3010BO to set
	 */
	public void setT3010BO(T3010BO t3010bo) {
		t3010BO = t3010bo;
	}

	@Override
	protected String subExecute() throws Exception {
		String subTxnId = getSubTxnId();
//		if(subTxnId.equals("01"))
//		{
//			return t3010BO.chkTmkLog(termIdId, batchNo, operator.getOprId());
//		}
//		if(subTxnId.equals("02"))
//		{
//			return t3010BO.chkTmkLog(batchNo, operator.getOprId());
//		}
//		if(subTxnId.equals("03"))
//		{
//			return t3010BO.delTmkLog(termIdId,batchNo, operator.getOprId());
//		}
		if(subTxnId.equals("01"))
			{
				return t3010BO.chkTmkLogNew(termIdId, batchNo, operator.getOprId());
			}
			if(subTxnId.equals("03"))
			{
				return t3010BO.delTmkLogNew(termIdId,batchNo, operator.getOprId());
			}
		return Constants.FAILURE_CODE;		
	}

}
