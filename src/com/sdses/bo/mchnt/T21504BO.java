package com.sdses.bo.mchnt;

import java.util.List;

import com.sdses.po.TblMchtMarkSubsidyBind;

public interface T21504BO {

    /**
     * 
     * //TODO 查询有无重复主键
     *
     * @param ruleID
     * @return
     */
    public TblMchtMarkSubsidyBind get(String recordID);

    /**
     * 
     * //TODO 保存商户营销补贴方案
     *
     * @param tblMchtMarkSudy
     */
    public String save(TblMchtMarkSubsidyBind tblMchtMarkSudyBind);

    /**
     * 
     * //TODO 删除相关信息
     *
     * @param tblMchtMarkSudy
     */
    public void delete(TblMchtMarkSubsidyBind tblMchtMarkSudyBind);

    /**
     * 
     * //TODO 更新商户营销补贴方案
     *
     * @param tblMchtMarkSudy
     */
    public void update(TblMchtMarkSubsidyBind tblMchtMarkSudyBind);

    // 查询营销规则是否在使用
    public List<Object[]> findBindListByDiscountId(String ruleID);

    // 查询所有终端表
    public List<Object[]> findTermListAllTwo();

    public List<Object[]> findTermListAllTwoMchntStr(String mchntStr);

    public List<Object[]> findTermListAllTwoTermStr(String termStr);

    public List<Object[]> findByTermIdTimes(String terminalId, String endTime);

}
