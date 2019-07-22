package com.huateng.struts.mchnt.action;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.huateng.bo.mchnt.T21405BO;
import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.po.mchnt.TblMchntFee;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

public class T21405Action extends BaseAction{
	 private static Logger logger = Logger.getLogger(T21405Action.class);
	T21405BO t21405BO = (T21405BO) ContextUtil.getBean("T21405BO");
	private String mchntId;
    private String feeType;	
	private String feeRate;	
	private String feeMin;
	private String feeMax;	
	
	@Override
	protected String subExecute() throws Exception {
        if ("saveOrupdate".equals(method)) {
            rspCode = saveOrupdate();
        }
        return rspCode;
	}

	private String update() {		
			TblMchntFee mchntFee=t21405BO.get(mchntId);
			mchntFee.setFeeType(feeType);			
			mchntFee.setFeeMin(feeMin);
			mchntFee.setFeeMax(feeMax);
			mchntFee.setFeeRate(feeRate);
			//操作人和时间
			Operator opr = (Operator) ServletActionContext.getRequest()
    				.getSession().getAttribute(Constants.OPERATOR_INFO);
			mchntFee.setOperator(opr.getOprId());				
			mchntFee.setUpdateTime(CommonFunction.getCurrentDateTime());
			return t21405BO.update(mchntFee);		
	}
	private String add() {		
			TblMchntFee mchntFee = new TblMchntFee();
			mchntFee.setMchntId(mchntId);
			mchntFee.setFeeType(feeType);			
			mchntFee.setFeeMin(feeMin);
			mchntFee.setFeeMax(feeMax);	
			mchntFee.setFeeRate(feeRate);
			//操作人和时间
			Operator opr = (Operator) ServletActionContext.getRequest()
    				.getSession().getAttribute(Constants.OPERATOR_INFO);
			mchntFee.setOperator(opr.getOprId());			
			mchntFee.setCreateTime(CommonFunction.getCurrentDateTime());			
			t21405BO.add(mchntFee);
			return Constants.SUCCESS_CODE;
		
	}
	
	private String saveOrupdate() {
		if (mchntId!=null) {
			TblMchntFee mchntFee=t21405BO.get(mchntId);
			if(mchntFee==null){
				add();
			}else{
				update();
			}		
		}else {
			return "传入后台系统参数丢失，请联系管理员!";
		}
		return Constants.SUCCESS_CODE;
	}


	public String getMchntId() {
		return mchntId;
	}

	public void setMchntId(String mchntId) {
		this.mchntId = mchntId;
	}

	public T21405BO getT21405BO() {
		return t21405BO;
	}

	public void setT21405BO(T21405BO t21405bo) {
		t21405BO = t21405bo;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getFeeRate() {
		return feeRate;
	}

	public void setFeeRate(String feeRate) {
		this.feeRate = feeRate;
	}

	public String getFeeMin() {
		return feeMin;
	}

	public void setFeeMin(String feeMin) {
		this.feeMin = feeMin;
	}

	public String getFeeMax() {
		return feeMax;
	}

	public void setFeeMax(String feeMax) {
		this.feeMax = feeMax;
	}

}
