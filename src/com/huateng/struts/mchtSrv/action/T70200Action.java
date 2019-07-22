package com.huateng.struts.mchtSrv.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.huateng.bo.impl.mchtSrv.MarketActSrv;
import com.huateng.po.mchtSrv.TblMarketActReview;
import com.huateng.struts.mchtSrv.MarketActConstants;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

public class T70200Action extends BaseSupport {

	private static final long serialVersionUID = -3515561114470279923L;

	private String startDate;
	private String endDate;
	private String settleCode;
	private String settleType;
	private String[] actFees;
	private String actName;
	private String bankNo;
	private String[] selectedOptions;
	private String remarks;
	private String actContent;
	private MarketActSrv marketActSrv = (MarketActSrv) ContextUtil.getBean("marketActSrv");
	
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String[] getActFees() {
		return actFees;
	}
	public void setActFees(String[] actFees) {
		this.actFees = actFees;
	}
	public String[] getSelectedOptions() {
		return selectedOptions;
	}
	public void setSelectedOptions(String[] selectedOptions) {
		this.selectedOptions = selectedOptions;
	}
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public String getActName() {
		return actName;
	}
	public void setActName(String actName) {
		this.actName = actName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getSettleCode() {
		return settleCode;
	}
	public void setSettleCode(String settleCode) {
		this.settleCode = settleCode;
	}
	public String getSettleType() {
		return settleType;
	}
	public void setSettleType(String settleType) {
		this.settleType = settleType;
	}
	public String getActContent() {
		return actContent;
	}
	public void setActContent(String actContent) {
		this.actContent = actContent;
	}

	public String add()
	{
		try {
			TblMarketActReview tblMarketActReview = new TblMarketActReview();
			
			tblMarketActReview.setActNo(marketActSrv.getActNo(getOperator().getOprBrhId()));
			tblMarketActReview.setActName(actName);
			
			SimpleDateFormat sysDateFormat = new SimpleDateFormat("yyyyMM");
			String start = sysDateFormat.format(new Date())+"01";
			sysDateFormat = new SimpleDateFormat("yyyyMMdd");
			Date date = sysDateFormat.parse(start);
			if(date.after(sysDateFormat.parse(startDate)))
				return  returnService(MarketActConstants.T70200_01);
			tblMarketActReview.setStartDate(startDate);
			
			tblMarketActReview.setEndDate(endDate);
			tblMarketActReview.setBankNo(bankNo);
			tblMarketActReview.setRemarks(remarks);
			tblMarketActReview.setOrganNo(settleCode);
			tblMarketActReview.setOrganType(settleType);
			tblMarketActReview.setActContent(actContent);
			tblMarketActReview.setFlag01(MarketActConstants.OPR_ADD);
			tblMarketActReview.setState(MarketActConstants.STATE_UNCHECK);
			tblMarketActReview.setCrtOpr(getOperator().getOprId());
			tblMarketActReview.setCrtTs(CommonFunction.getCurrentDate());
			tblMarketActReview.setUpdOpr(getOperator().getOprId());
			tblMarketActReview.setUpdDt(CommonFunction.getCurrentDate());
			
			
//			if(selectedOptions == null || selectedOptions[0].equals(""))
//				return returnService(MarketActConstants.T70202_02);

			rspCode = marketActSrv.saveNew(tblMarketActReview,selectedOptions);
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
		
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
