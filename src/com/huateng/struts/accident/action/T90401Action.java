package com.huateng.struts.accident.action;

import com.huateng.bo.accident.T90401BO;
import com.huateng.po.accident.TblCostInf;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

public class T90401Action extends BaseAction {

	private static final long serialVersionUID = 1L;

	private T90401BO t90401BO = (T90401BO) ContextUtil.getBean("T90401BO");

	protected String subExecute(){
		
		try {
			if("add".equals(getMethod())) {			
					rspCode = add();
			} else if("upd".equals(getMethod())) {			
					rspCode = upd();
			} else if("del".equals(getMethod())) {			
					rspCode = del();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，对费用收取的操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}

	private String add() throws Exception {
		
		TblCostInf tblCostInf = new TblCostInf();

		tblCostInf.setMchtNo(mchtNo);
		tblCostInf.setAmount(amount);
		tblCostInf.setCardId(cardId);
		tblCostInf.setRemarkInf(remarkInf);
		tblCostInf.setState("0");
		tblCostInf.setCrtOprId(operator.getOprId());
		tblCostInf.setRecCrtTs(CommonFunction.getCurrentDateTime());
		
		return t90401BO.add(tblCostInf);
	}
	
	private String upd() throws Exception {
		
		return t90401BO.upd(infoList);
	}
	
	private String del() throws Exception {
		
		return t90401BO.del(no);
	}
	
	private String no;
	private String mchtNo;
	private String amount;
	private String cardId;
	private String remarkInf;

	private String infoList;
	
	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public void setRemarkInf(String remarkInf) {
		this.remarkInf = remarkInf;
	}
	
	public void setNo(String no) {
		this.no = no;
	}
	
	public void setInfoList(String infoList) {
		this.infoList = infoList;
	}
}