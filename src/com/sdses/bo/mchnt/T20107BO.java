package com.sdses.bo.mchnt;

import java.util.List;

import com.sdses.po.TcMchntMap;
import com.sdses.po.TcMchntMapPK;

/**
 * 
 * //TODO 扫码支付商户维护
 * //TODO T20107BO/扫码支付商户维护
 * 
 * @author hanyongqing
 * @version
 */
public interface T20107BO {
    /**
     * 
     * //TODO 获取对应记录的扫码支付商户信息
     *
     * @param tcMchntMapPK
     * @return
     */
    public TcMchntMap get(TcMchntMapPK tcMchntMapPK);

    /**
     * 
     * //TODO 获取菜单编码表的相关信息，根据菜名（非主键）
     *
     * @param name
     * @return
     * @author hanyongqing
     */
    public List<Object[]> findListByName(String name);
    /**
     * 
     * //TODO 根据银联商户号、银联终端号、终端硬件编号获取扫码支付商户信息
     *
     * @param ylMchntNo
     * @param ylTermNo
     * @param termFixNo
     * @return
     * @author lixiaomin
     */
    public List<Object[]> findList2(String ylMchntNo,String ylTermNo,String id);
    /**
     * 
     * //TODO 根据银联商户号、银联终端号、扫码方式获取扫码支付商户信息
     *
     * @param ylMchntNo
     * @param ylTermNo
     * @param 
     * @return
     * @author lixiaomin
     */
    public List<Object[]> findList3(String ylMchntNo,String ylTermNo);

    /**
     * 
     * //TODO 菜品编码表增加信息
     *
     * @param tblBrhInfo
     * @return
     * @author hanyongqing
     */
    public String add(TcMchntMap tcMchntMap);

    /**
     * 
     * //TODO 删除相关的记录信息
     *
     * @param vegCode
     * @return
     * @author hanyongqing
     */
    public String delete(TcMchntMap tcMchntMap);

    /**
     * 
     * //TODO 更新修改的记录
     *
     * @param tblBrhInfoList
     * @return
     * @author hanyongqing
     */
    public String update(List<TcMchntMap> mchntInfoList);

}
