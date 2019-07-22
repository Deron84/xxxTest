package com.huateng.bo.mchnt;

import java.util.List;

import com.huateng.po.mchnt.TblMchntDiscountRuleBind;
import com.huateng.po.mchnt.TblMchntDiscountRuleBindPK;

public interface T21403BO {

	public int countNum(TblMchntDiscountRuleBindPK id);
    /**
     * 查询商户签约账户信息
     * @param id    签约编号
     * @return
     */
    public TblMchntDiscountRuleBind get(TblMchntDiscountRuleBindPK id);
    /**
     * 添加商户签约账户信息
     * @param TblMchtSignAccInf    签约信息
     * @return
     */
    public String add(TblMchntDiscountRuleBind cstSysParam);
 
    public String delete(TblMchntDiscountRuleBindPK id);
    
    public String update(TblMchntDiscountRuleBind cstSysParam);
    //根据优惠规则查询
    public List<Object[]> findBindListByDiscountId(String discountId);
    //根据优惠规则查询
    public List<Object[]> findBindListByDiscountCode(String discountCode);
    //根据终端号查询
    public List<Object[]> findBindListByEquipmentId(String equipmentId);
    
    //查询所有的（终端表）
    public List<Object[]> findTermListAllOne();
    public List<Object[]> findTermListAllTwo();
    public List<Object[]> findTermListAllOneMchntStr(String mchntStr);
    public List<Object[]> findTermListAllTwoMchntStr(String mchntStr);
    public List<Object[]> findTermListAllOneTermStr(String TermStr);
    public List<Object[]> findTermListAllTwoTermStr(String TermStr);
    public List<Object[]> findByTermIdTimes(String equipmentId,String stratTime,String endTime);
}
