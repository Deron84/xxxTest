package com.huateng.po.mchnt;

import com.huateng.po.mchnt.base.BaseTblHisDiscAlgoPK;

public class TblMchntDiscountRuleBindPK extends BaseTblHisDiscAlgoPK{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9160010451179137168L;

	private String bindId;
	
		
	

	public TblMchntDiscountRuleBindPK(){}
	
	public TblMchntDiscountRuleBindPK(String bindId){
		this.bindId = bindId;
			
	}

	public String getBindId() {
		return bindId;
	}

	public void setBindId(String bindId) {
		this.bindId = bindId;
	}

	
	
}
