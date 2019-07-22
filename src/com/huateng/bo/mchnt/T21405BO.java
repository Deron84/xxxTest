package com.huateng.bo.mchnt;

import com.huateng.po.mchnt.TblMchntFee;

public interface T21405BO {

	public int countNum(String id);
    /**
     * 查询商户签约账户信息
     * @param id    签约编号
     * @return
     */
    public TblMchntFee get(String id);
    /**
     * 添加商户签约账户信息
     * @param TblMchtSignAccInf    签约信息
     * @return
     */
    public String add(TblMchntFee cstSysParam);
 
    public String delete(String id);
    
    public String update(TblMchntFee cstSysParam);
    public String saveOrupdate(TblMchntFee cstSysParam);
}
