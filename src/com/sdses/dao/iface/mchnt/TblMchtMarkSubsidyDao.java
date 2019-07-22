package com.sdses.dao.iface.mchnt;

import com.sdses.po.TblMchtMarkSubsidy;

public interface TblMchtMarkSubsidyDao {
    /**
     * 
     * //TODO 根据主键查询商户营销中的对应数据
     *
     * @param ruleID 规则ID
     * @return
     */
    public TblMchtMarkSubsidy get(String ruleID);

    /**
     * 
     * //TODO 保存营销补贴商户数据
     *
     * @param tblMchtMarkSudy
     * @return
     */
    public String save(TblMchtMarkSubsidy tblMchtMarkSudy);

    /**
     * 
     * //TODO 删除对应信息
     *
     * @param tblMchtMarkSudy
     * @return
     */
    public void delete(TblMchtMarkSubsidy tblMchtMarkSudy);

    /**
     * 
     * //TODO 更新相关信息
     *
     * @param tblMchtMarkSudy
     * @return
     */
    public void update(TblMchtMarkSubsidy tblMchtMarkSudy);
}
