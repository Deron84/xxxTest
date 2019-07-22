package com.sdses.dao.iface.pos;

import com.huateng.po.TblTermTpPo;

public interface TblTermTpPoDAO {
    /**
     * 
     * //TODO 添加方法功能描述
     *
     * @param 终端型号
     * @return
     */
    public int queryForInt(String descrUpd);

    /**
     * 
     * //TODO 更新TBL_TERM_TP（终端类型字典表数）数据
     *
     * @param 终端类型编号
     * @param  终端型号
     * @return
     */
    public int updateData(String descrUpd, String descrUpd1,String termNumUpd,String termTypeUpd,String proDescrUpd);
    
    /**
     * 
     * //TODO 更新TBL_TERM_TP（终端类型字典表数）数据
     *
     * @param 终端类型编号
     * @return
     */
    public int queryForTermTp(String termTpUpd);
    /**
     * 
     * //TODO 更新TBL_TERM_TP（终端类型字典表数）数据
     *
     * @param 终端类型编号
     * @return
     */
    public String queryTermTp(String descrUpd1);

    /**
     * 
     * //TODO 删除TBL_TERM_TP（终端类型字典表数）数据
     *
     * @param 终端类型编号
     * @return
     */
    public int deleteData(String termTpUpd);

    /**
     * 
     * //TODO 查询TBL_TERM_TP的最小编号
     *
     * @return
     */
    public int findCountBySQLQuery();

    /**
     * 
     * //TODO 增加TBL_TERM_TP数据
     *
     * @param tblTermTpPo
     * @return
     */
    public int addDate(TblTermTpPo tblTermTpPo);
}
