/*
 *  神思电子源代码，版权归神思电子股份有限公司所有。
 * 
 * 项目名称 : JSBConsole
 * 创建日期 : 2017年3月15日
 * 修改历史 : 
 *     1. [2017年3月15日]创建文件 by Wrangler
 */
package com.sdses.bo.impl.pos;

import java.util.List;

import com.huateng.common.Constants;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.sdses.bo.pos.T30500BO;
import com.sdses.dao.iface.pos.TblVegeCodeInfoDao;
import com.sdses.po.TblVegeCodeInfo;

/**
 * //TODO T30107BOTarget
 * //TODO T30107BO/完成菜单编码表的增删改查操作
 * 
 * @author hanyongqing
 * @version 
 */
public class T30500BOTarget implements T30500BO {

    @Override
    public String update(List<TblVegeCodeInfo> vegeInfoList) {
        // TODO Auto-generated method stub
        for(TblVegeCodeInfo vegeInfo : vegeInfoList) {
            vegeCodeInfoDao.update(vegeInfo);
        }
        return Constants.SUCCESS_CODE;
    }

    private TblVegeCodeInfoDao vegeCodeInfoDao;
    
    private ICommQueryDAO commQueryDAO;
    /**
     * //TODO 查询表中是否有此主键
     * @see com.huateng.bo.base.T30107BO#get(java.lang.String)
     **/
    @Override
    public TblVegeCodeInfo get(String vegeCode) {
        // TODO Auto-generated method stub
        return this.vegeCodeInfoDao.get(vegeCode);
    }
    
    @Override
    public List<Object[]> findListByName(String name) {
        String sql = "select VEGE_CODE,VEGE_NAME from vege_code_base where trim(VEGE_NAME) = trim('"+ name +"')"; 
        return commQueryDAO.findBySQLQuery(sql);
    }
    
    @Override
    public String add(TblVegeCodeInfo vegeCodeInfo) {
        // TODO Auto-generated method stub
        vegeCodeInfoDao.save(vegeCodeInfo);
        return Constants.SUCCESS_CODE;
    }
    
    @Override
    public String delete(TblVegeCodeInfo vegCode) {
        // TODO Auto-generated method stub
        vegeCodeInfoDao.delete(vegCode);
        return Constants.SUCCESS_CODE;
    }
    
    public TblVegeCodeInfoDao getVegeCodeInfoDao() {
        return vegeCodeInfoDao;
    }
    public void setVegeCodeInfoDao(TblVegeCodeInfoDao vegeCodeInfoDao) {
        this.vegeCodeInfoDao = vegeCodeInfoDao;
    }

    public ICommQueryDAO getCommQueryDAO() {
        return commQueryDAO;
    }

    public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
        this.commQueryDAO = commQueryDAO;
    }
}
