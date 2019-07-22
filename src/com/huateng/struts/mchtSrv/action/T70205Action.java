package com.huateng.struts.mchtSrv.action;

import java.math.BigDecimal;

import com.huateng.bo.impl.mchtSrv.MarketActSrv;
import com.huateng.po.mchtSrv.TblMarketActReview;
import com.huateng.po.mchtSrv.TblMchntParticipatReview;
import com.huateng.struts.mchtSrv.MarketActConstants;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.ContextUtil;

public class T70205Action extends BaseSupport {

	private static final long serialVersionUID = -8740953895632645693L;
	private String actNo;
	private String mchntNo;
	private String actFee;
	private String flag;
	
	private String[] actNos;
	private String[] mchntNos;
	private String[] actFees;
	private String[] flags;
	private MarketActSrv marketActSrv = (MarketActSrv) ContextUtil.getBean("marketActSrv");
	
	public String refuse()
	{
		try {
			if(actNo == null||actNo.equals(""))
				return returnService(MarketActConstants.T70205_01);
			if(mchntNo == null||mchntNo.equals(""))
				return returnService(MarketActConstants.T70205_02);
			
			rspCode = marketActSrv.refuseMchnt(actNo,mchntNo,getOperator().getOprId());
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
	}
	
	public String batch(){
		try {
			for(int i=0;i<actNos.length && i<mchntNos.length;i++)
				{
					TblMchntParticipatReview tblMchntParticipatReview = marketActSrv.getMchnt(actNos[i], mchntNos[i]);
					
					TblMarketActReview tblMarketActReview = marketActSrv.getMarketAct(actNos[i]);
					if(tblMarketActReview!=null
							&&!tblMarketActReview.getState().equals(MarketActConstants.STATE_OK))
						return returnService(MarketActConstants.T70205_08);
				}
			rspCode = marketActSrv.checkMchnt(actNos,mchntNos,getOperator().getOprId());
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
	}
	
	
	
	public String[] getActNos() {
		return actNos;
	}

	public void setActNos(String[] actNos) {
		this.actNos = actNos;
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

	public String[] getFlags() {
		return flags;
	}

	public void setFlags(String[] flags) {
		this.flags = flags;
	}

	public String getActNo() {
		return actNo;
	}

	public void setActNo(String actNo) {
		this.actNo = actNo;
	}

	public String getMchntNo() {
		return mchntNo;
	}

	public void setMchntNo(String mchntNo) {
		this.mchntNo = mchntNo;
	}
	
	public String getActFee() {
		return actFee;
	}

	public void setActFee(String actFee) {
		this.actFee = actFee;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
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
