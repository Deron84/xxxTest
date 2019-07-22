package com.huateng.bo.mchnt;

import java.util.List;

import com.huateng.po.mchnt.TblMchntMap;
import com.huateng.po.mchnt.TblMchntMapPK;

public interface T21401BO {
	public int countNum(TblMchntMapPK id);
    /**
     * 查询商户签约账户信息
     * @param id    签约编号
     * @return
     */
    public TblMchntMap get(TblMchntMapPK id);
    /**
     * 添加商户签约账户信息
     * @param TblMchtSignAccInf    签约信息
     * @return
     */
    public String add(TblMchntMap cstSysParam);
 
    public String delete(String mappingId);
    
    public String update(TblMchntMap cstSysParam);
    //商户号、终端号、两个作为逻辑主键查询
    public List<Object[]> findMappingListByTwoId(String mchntId,String equipmentId);
    //商户号、终端号、收单方三个作为逻辑主键查询
    public List<Object[]> findMappingListByThreeId(String mchntId,String equipmentId,String acquiresId);
}
