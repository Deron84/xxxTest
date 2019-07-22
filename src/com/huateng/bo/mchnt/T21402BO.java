package com.huateng.bo.mchnt;

import com.huateng.po.mchnt.TblMchntDiscountRule;

public interface T21402BO {

	public int countNum(String id);
    /**
     * 查询商户签约账户信息
     * @param id    签约编号
     * @return
     */
    public TblMchntDiscountRule get(String id);
    /**
     * 添加商户签约账户信息
     * @param TblMchtSignAccInf    签约信息
     * @return
     */
    public String add(TblMchntDiscountRule cstSysParam);
 
    public String delete(String id);
    
    public String update(TblMchntDiscountRule cstSysParam);
	
}
