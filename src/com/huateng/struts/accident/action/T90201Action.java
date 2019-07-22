package com.huateng.struts.accident.action;

import com.huateng.bo.accident.T90201BO;
import com.huateng.common.accident.TblEntrustTradeConstants;
import com.huateng.po.accident.TblEntrustTrade;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

public class T90201Action extends BaseAction {

	private static final long serialVersionUID = 1L;

	private T90201BO t90201BO = (T90201BO) ContextUtil.getBean("T90201BO");

	protected String subExecute(){
		
		try {
			if("add".equals(getMethod())) {			
					rspCode = add();
			} else if("del".equals(getMethod())) {			
					rspCode = del();
			} else if("edit".equals(getMethod())) {			
					rspCode = edit();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，对托收交易的操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}

	private String add() throws Exception {
		
		TblEntrustTrade tblEntrustTrade = new TblEntrustTrade();

		if("1".equals(kind)) {												//延迟日期，如果是例外协商为0，当天清算
			tblEntrustTrade.setDeferDay("0");
		} else {															//如果是请款默认为10天
			tblEntrustTrade.setDeferDay("10");
		}
		
		tblEntrustTrade.setAmtTrans(transName);								//金额
		tblEntrustTrade.setCardAccpId(mchtNo);								//商户号，受卡方标识码
		tblEntrustTrade.setPan(panName);									//卡号，主账户号
		tblEntrustTrade.setStatus(TblEntrustTradeConstants.WAIT);			//状态,新增的信息状态为待处理
		tblEntrustTrade.setCountDay(TblEntrustTradeConstants.COUNT_INIT);	//累计的天数，由后台累加，这里插入0
		tblEntrustTrade.setCrtOprId(operator.getOprId());					//创建操作员
		tblEntrustTrade.setCrtTs(CommonFunction.getCurrentDateTime());		//创建时间
		tblEntrustTrade.setId(TblEntrustTradeConstants.NO_VALUE);			//托收交易号，在BO中获取，这里插默认值
		tblEntrustTrade.setAuditOprId(TblEntrustTradeConstants.NO_VALUE);	//审核操作员
		tblEntrustTrade.setAuditTs(TblEntrustTradeConstants.NO_VALUE);		//审核时间
		tblEntrustTrade.setReserved(TblEntrustTradeConstants.NO_VALUE);		//保留域
		
		return t90201BO.add(tblEntrustTrade);
	}
	
	private String del() throws Exception {
		
		return t90201BO.del(tradeId);
	}
	
	private String edit() throws Exception {
		
		TblEntrustTrade tblEntrustTrade = new TblEntrustTrade();
		
		tblEntrustTrade.setId(entrustName);
		tblEntrustTrade.setCardAccpId(mchtNoUpd);
		tblEntrustTrade.setPan(panNameUpd);
		tblEntrustTrade.setAmtTrans(transNameUpd);
		
		return t90201BO.update(tblEntrustTrade);
	}
	
	private String mchtNo;
	private String panName;
	private String transName;
	private String kind;
	
	private String tradeId;
	
	private String entrustName;
	private String mchtNoUpd;
	private String panNameUpd;
	private String transNameUpd;
	
	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public void setPanName(String panName) {
		this.panName = panName;
	}

	public void setTransName(String transName) {
		this.transName = transName;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
	
	public void setTransNameUpd(String transNameUpd) {
		this.transNameUpd = transNameUpd;
	}

	public void setPanNameUpd(String panNameUpd) {
		this.panNameUpd = panNameUpd;
	}

	public void setMchtNoUpd(String mchtNoUpd) {
		this.mchtNoUpd = mchtNoUpd;
	}

	public void setEntrustName(String entrustName) {
		this.entrustName = entrustName;
	}
}
