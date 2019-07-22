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
import com.sdses.bo.pos.T30501BO;
import com.sdses.dao.iface.pos.TblVegeMappingInfoDao;
import com.sdses.po.TblVegeMappingInfo;
import com.sdses.po.TblVegeMappingInfoPK;

/**
 * //TODO T30107BOTarget
 * //TODO T30107BO/完成菜单编码表的增删改查操作
 * 
 * @author hanyongqing
 * @version 
 */
public class T30501BOTarget implements T30501BO {

    private TblVegeMappingInfoDao vegeMappingInfoDao;
    
    private ICommQueryDAO commQueryDAO;
        
    /**
     * //TODO 查询表中是否有此主键
     * @see com.huateng.bo.base.T30108BO#get(java.lang.String)
     **/
    @Override
    public TblVegeMappingInfo get(TblVegeMappingInfoPK tblVegeMappingInfoPK) {
        // TODO Auto-generated method stub
        return this.vegeMappingInfoDao.get(tblVegeMappingInfoPK);
    }
    
        @Override
        public String update(List<TblVegeMappingInfo> vegeInfoList) {
            // TODO Auto-generated method stub
            for(TblVegeMappingInfo vegeInfo : vegeInfoList) {
                vegeMappingInfoDao.update(vegeInfo);
            }
            return Constants.SUCCESS_CODE;
        }
//    
//    @Override
//    public List<Object[]> findListByName(String name) {
//        String sql = "select VEGE_CODE,VEGE_NAME from vege_code_base where trim(VEGE_NAME) = trim('"+ name +"')"; 
//        return commQueryDAO.findBySQLQuery(sql);
//    }
//    
    @Override
    public String add(TblVegeMappingInfo tblVegeMappingInfo) {
        // TODO Auto-generated method stub
        vegeMappingInfoDao.save(tblVegeMappingInfo);
        return Constants.SUCCESS_CODE;
    }
    
    @Override
    public String delete(TblVegeMappingInfo tblVegeMappingInfo) {
        // TODO Auto-generated method stub
        vegeMappingInfoDao.delete(tblVegeMappingInfo);
        return Constants.SUCCESS_CODE;
    }
    
    public ICommQueryDAO getCommQueryDAO() {
        return commQueryDAO;
    }

    public TblVegeMappingInfoDao getVegeMappingInfoDao() {
        return vegeMappingInfoDao;
    }

    public void setVegeMappingInfoDao(TblVegeMappingInfoDao vegeMappingInfoDao) {
        this.vegeMappingInfoDao = vegeMappingInfoDao;
    }

    public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
        this.commQueryDAO = commQueryDAO;
    }
}
