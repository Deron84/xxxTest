package com.huateng.struts.mchtSrv.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.huateng.bo.impl.mchtSrv.MarketActSrv;
import com.huateng.po.mchtSrv.TblMarketActReview;
import com.huateng.po.mchtSrv.TblMchntParticipatReview;
import com.huateng.struts.mchtSrv.MarketActConstants;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.ContextUtil;

public class T70204Action extends BaseSupport {

	private static final long serialVersionUID = 5740040654680303542L;
	private String actNo;
	private String startDateChk;
	private String endDateChk;
	private String organNoChk;
	private String organTypeChk;
	private String[] mchntNos;
	private String[] actFees;
	
	private MarketActSrv marketActSrv = (MarketActSrv) ContextUtil.getBean("marketActSrv");

	public String check(){
		try {
			if(actNo == null||actNo.equals(""))
				return returnService(MarketActConstants.T70204_01);
			
			TblMarketActReview tblMarketActReview = marketActSrv.getMarketAct(actNo);

			
			List<TblMchntParticipatReview> list = new ArrayList<TblMchntParticipatReview>();
			if(mchntNos !=null && !mchntNos[0].equals("")){
				for(int i=0;i<mchntNos.length;i++){
					TblMchntParticipatReview tblMchntParticipatReview = marketActSrv.getMchnt(actNo, mchntNos[i]);
					list.add(tblMchntParticipatReview);
				}
			}
			
			
			rspCode = marketActSrv.check(actNo,getOperator().getOprId(),list);
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
	}
	
	public String modifyCheck(){
		try {
			
			TblMarketActReview tblMarketActReview = marketActSrv.getMarketAct(actNo);
			List<TblMchntParticipatReview> list = new ArrayList<TblMchntParticipatReview>();
			if(mchntNos !=null && !mchntNos[0].equals("")){
				for(int i=0;i<mchntNos.length;i++){
					TblMchntParticipatReview tblMchntParticipatReview = marketActSrv.getMchnt(actNo, mchntNos[i]);
					list.add(tblMchntParticipatReview);
				}
			}
			
			rspCode = marketActSrv.check(actNo,getOperator().getOprId(),list);
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
	}
	
	
	public String refuse(){
		try {
			if(actNo == null||actNo.equals(""))
				return returnService(MarketActConstants.T70204_01);
			
			rspCode = marketActSrv.refuse(actNo,getOperator().getOprId());
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

	public String getStartDateChk() {
		return startDateChk;
	}

	public void setStartDateChk(String startDateChk) {
		this.startDateChk = startDateChk;
	}

	public String getEndDateChk() {
		return endDateChk;
	}

	public void setEndDateChk(String endDateChk) {
		this.endDateChk = endDateChk;
	}

	public String getOrganNoChk() {
		return organNoChk;
	}

	public void setOrganNoChk(String organNoChk) {
		this.organNoChk = organNoChk;
	}

	public String getOrganTypeChk() {
		return organTypeChk;
	}

	public void setOrganTypeChk(String organTypeChk) {
		this.organTypeChk = organTypeChk;
	}

	public String[] getMchntNos() {
		return mchntNos;
	}

	public void setMchntNos(String[] mchntNos) {
		this.mchntNos = mchntNos;
	}

	public String[] getActFees() {
		return actFees;
	}

	public void setActFees(String[] actFees) {
		this.actFees = actFees;
	}

	public MarketActSrv getMarketActSrv() {
		return marketActSrv;
	}

	public void setMarketActSrv(MarketActSrv marketActSrv) {
		this.marketActSrv = marketActSrv;
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
