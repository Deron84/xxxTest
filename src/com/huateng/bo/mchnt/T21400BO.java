package com.huateng.bo.mchnt;

import com.huateng.po.mchnt.TblMchntApply;
import com.huateng.po.mchnt.TblMchntUser;

public interface T21400BO {
	public int countNum(String id);
    /**
     * 查询商户签约账户信息
     * @param id    签约编号
     * @return
     */
    public TblMchntApply get(String id);
	public String update(TblMchntApply cstSysParam);
	public TblMchntUser getMchntUser(String id);
	public TblMchntUser getMchntUserByMchntId(String mchntId);
	public String updateMchntUser(TblMchntUser cstSysParam);
}
