package com.huateng.bo.mchnt;

import com.huateng.common.Operator;
import com.huateng.po.mchnt.TblMchtSignAccInf;
import com.huateng.po.mchnt.TblMchtSignAccInfPK;
import com.huateng.system.util.JSONBean;

public interface T21200BO {
	public int countNum(TblMchtSignAccInfPK id);
    /**
     * 查询商户签约账户信息
     * @param id    签约编号
     * @return
     */
    public TblMchtSignAccInf get(TblMchtSignAccInfPK id);
    /**
     * 添加商户签约账户信息
     * @param TblMchtSignAccInf    签约信息
     * @return
     */
    public String add(TblMchtSignAccInf cstSysParam);
    /**
     * 批量更新商户签约账户信息
     * @param TblMchtSignAccInfList    签约信息集合
     * @return
     */
    public String update(JSONBean jsonBean,Operator operator);
   /**
    * 
    * 功能描述:删除商户签约账户信息 <br>
    * 〈功能详细描述〉
    *
    * @param mchntId 商户号
    * @param signAcct 签约账户
    * @return
    * @see [相关类/方法](可选)
    * @since [产品/模块版本](可选)
    */
    public String delete(String mchntId,String signAcct);
//    /**
//     * 删除商户签约账户信息
//     * @param id    签约编号
//     * @return
//     */
//    public String delete(TblMchtSignAccInfPK id);
}
