package com.sdses.bo.mchnt;

import java.util.List;

import com.sdses.po.TblMchtMarkSubsidy;

public interface T21503BO {

    /**
     * 
     * //TODO 查询有无重复主键
     *
     * @param ruleID
     * @return
     */
    public TblMchtMarkSubsidy get(String ruleID);

    /**
     * 
     * //TODO 保存商户营销补贴方案
     *
     * @param tblMchtMarkSudy
     */
    public String save(TblMchtMarkSubsidy tblMchtMarkSudy);

    /**
     * 
     * //TODO 删除相关信息
     *
     * @param tblMchtMarkSudy
     */
    public void delete(TblMchtMarkSubsidy tblMchtMarkSudy);

    /**
     * 
     * //TODO 更新商户营销补贴方案
     *
     * @param tblMchtMarkSudy
     */
    public void update(TblMchtMarkSubsidy tblMchtMarkSudy);

    public List<Object[]> findRuleListAll(String ruleID);

}
