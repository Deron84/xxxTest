package com.huateng.struts.mchtSrv.action;

import com.huateng.bo.impl.mchtSrv.MarketActSrv;
import com.huateng.po.mchtSrv.TblMarketActReview;
import com.huateng.struts.mchtSrv.MarketActConstants;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.ContextUtil;

public class T70202Action extends BaseSupport {


	private static final long serialVersionUID = 4119508222109527952L;
	private String actNo;
	private String[] selectedOptions;
	private String[] actFees;
	private MarketActSrv marketActSrv = (MarketActSrv) ContextUtil.getBean("marketActSrv");

	public String addMchnt()
	{
		try {
			if(actNo == null || actNo.equals(""))
				return returnService(MarketActConstants.T70202_01);
			if(selectedOptions == null || selectedOptions[0].equals(""))
				return returnService(MarketActConstants.T70202_02);
			
			TblMarketActReview tblMarketActReview = marketActSrv.getMarketAct(actNo);
			
			rspCode = marketActSrv.addMchntNew(actNo,selectedOptions,getOperator().getOprId());
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
		
	}
	
	public String getActNo() {
		return actNo;
	}

	public void setActNo(String actNo) {
		this.actNo = actNo;
	}

	public String[] getSelectedOptions() {
		return selectedOptions;
	}

	public void setSelectedOptions(String[] selectedOptions) {
		this.selectedOptions = selectedOptions;
	}

	public String[] getActFees() {
		return actFees;
	}

	public void setActFees(String[] actFees) {
		this.actFees = actFees;
	}
	
	@Override
	public String getMsg() {
		return msg;
	}

	@Override
	public boolean isSuccess() {
		return success;
	}

}
