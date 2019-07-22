/*
 *  神思电子源代码，版权归神思电子股份有限公司所有。
 * 
 * 项目名称 : JSBConsole
 * 创建日期 : 2017年3月15日
 * 修改历史 : 
 *     1. [2017年3月15日]创建文件 by Wrangler
 */
package com.sdses.bo.impl.mchnt;

import java.util.List;

import com.huateng.common.Constants;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.sdses.bo.mchnt.T20107BO;
import com.sdses.dao.iface.mchnt.TcMchntMapDao;
import com.sdses.po.TcMchntMap;
import com.sdses.po.TcMchntMapPK;

/**
 * //TODO T30107BOTarget
 * //TODO T30107BO/完成菜单编码表的增删改查操作
 * 
 * @author hanyongqing
 * @version 
 */
public class T20107BOTarget implements T20107BO {

    private TcMchntMapDao tcMchntMapDao;

    private ICommQueryDAO commQueryDAO;
    @Override
    public String update(List<TcMchntMap> mchntInfoList) {
        // TODO Auto-generated method stub
        for (TcMchntMap mapData : mchntInfoList) {
            tcMchntMapDao.update(mapData);
        }
        return Constants.SUCCESS_CODE;
    }

    /**
     * 
     * //TODO 查询是否有此主键
     * @see com.huateng.bo.base.T20107BO#get(com.huateng.po.TcMchntMapPK)
     *
     */
    @Override
    public TcMchntMap get(TcMchntMapPK tcMchntMapPK) {
        // TODO Auto-generated method stub
        return this.tcMchntMapDao.get(tcMchntMapPK);
    }

    @Override
    public List<Object[]> findListByName(String name) {
        String sql = "select VEGE_CODE,VEGE_NAME from vege_code_base where trim(VEGE_NAME) = trim('" + name + "')";
        return commQueryDAO.findBySQLQuery(sql);
    }

    @Override
    public String add(TcMchntMap tcMchntMap) {
        // TODO Auto-generated method stub
        tcMchntMapDao.save(tcMchntMap);
        return Constants.SUCCESS_CODE;
    }

    @Override
    public String delete(TcMchntMap tcMchntMap) {
        // TODO Auto-generated method stub
        tcMchntMapDao.delete(tcMchntMap);
        return Constants.SUCCESS_CODE;
    }

    public TcMchntMapDao getTcMchntMapDao() {
        return tcMchntMapDao;
    }

    public void setTcMchntMapDao(TcMchntMapDao tcMchntMapDao) {
        this.tcMchntMapDao = tcMchntMapDao;
    }

    public ICommQueryDAO getCommQueryDAO() {
        return commQueryDAO;
    }

    public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
        this.commQueryDAO = commQueryDAO;
    }

	@Override
	public List<Object[]> findList3(String ylMchntNo, String ylTermNo) {
		String sql = "select YL_MCHNT_NO,YL_TERM_NO,SCANCODEFLAG,C_MCHNT_BRH from t_cmchnt_map where trim(YL_MCHNT_NO) = '" + ylMchntNo + "' and trim(YL_TERM_NO) = '" + ylTermNo + "' order by SCANCODEFLAG";
        return commQueryDAO.findBySQLQuery(sql);
	}

	@Override
	public List<Object[]> findList2(String ylMchntNo, String ylTermNo, String id) {
		String sql = "select YL_MCHNT_NO,YL_TERM_NO,SCANCODEFLAG,C_MCHNT_BRH from t_cmchnt_map where trim(YL_MCHNT_NO) = '" + ylMchntNo + "' and trim(YL_TERM_NO) = '" + ylTermNo + "' and trim(ID)='"+id+"' order by SCANCODEFLAG";
        return commQueryDAO.findBySQLQuery(sql);
	}
}
