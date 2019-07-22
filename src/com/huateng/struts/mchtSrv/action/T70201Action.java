package com.huateng.struts.mchtSrv.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.huateng.bo.impl.mchtSrv.MarketActSrv;
import com.huateng.po.mchtSrv.TblMarketActReview;
import com.huateng.struts.mchtSrv.MarketActConstants;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

public class T70201Action extends BaseSupport {

	private static final long serialVersionUID = -8912122700818040835L;

	private String bankNo;
	private String actNo;
	private String actName;
	private String state;
	private String startDate;
	private String endDate;
	private String remarks;
	private String organNo;
	private String organType;
	private String flag01;
	private String actContent;
	private MarketActSrv marketActSrv = (MarketActSrv) ContextUtil.getBean("marketActSrv");
	
	
	public String getOrganNo() {
		return organNo;
	}

	public void setOrganNo(String organNo) {
		this.organNo = organNo;
	}

	public String getOrganType() {
		return organType;
	}

	public void setOrganType(String organType) {
		this.organType = organType;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getActNo() {
		return actNo;
	}

	public void setActNo(String actNo) {
		this.actNo = actNo;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getFlag01() {
		return flag01;
	}

	public void setFlag01(String flag01) {
		this.flag01 = flag01;
	}

	public String getActContent() {
		return actContent;
	}

	public void setActContent(String actContent) {
		this.actContent = actContent;
	}

	public String update()
	{
		try {
			TblMarketActReview tblMarketActReview = marketActSrv.getMarketAct(actNo);
			
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
			tblMarketActReview.setOrganNo(organNo);
			tblMarketActReview.setActContent(actContent);
			tblMarketActReview.setOrganType(organType);
			tblMarketActReview.setState(MarketActConstants.STATE_UNCHECK);
			
			if(state.equals(MarketActConstants.STATE_CLOSE))
				tblMarketActReview.setFlag01(MarketActConstants.OPR_DEL);
			else
				if(flag01.equals(MarketActConstants.OPR_ADD))
					tblMarketActReview.setFlag01(MarketActConstants.OPR_ADD);
				else
					tblMarketActReview.setFlag01(MarketActConstants.OPR_MOD);
			
			tblMarketActReview.setUpdOpr(getOperator().getOprId());
			tblMarketActReview.setUpdDt(CommonFunction.getCurrentDate());
			
			rspCode = marketActSrv.update(tblMarketActReview);
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
