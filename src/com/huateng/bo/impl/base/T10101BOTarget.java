package com.huateng.bo.impl.base;

import java.util.List;

import com.huateng.bo.base.T10101BO;
import com.huateng.common.Constants;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.dao.iface.base.TblBrhInfoDAO;
import com.huateng.po.TblBrhInfo;

/**
 * Title:机构信息BO
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-9
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */
public class T10101BOTarget implements T10101BO {

    private TblBrhInfoDAO tblBrhInfoDAO;

    private ICommQueryDAO commQueryDAO;

    public ICommQueryDAO getCommQueryDAO() {
        return commQueryDAO;
    }

    public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
        this.commQueryDAO = commQueryDAO;
    }

    /* (non-Javadoc)
     * @see com.huateng.bo.T10101BO#add(com.huateng.po.TblBrhInfo)
     */
    @Override
    public String add(TblBrhInfo tblBrhInfo) {
        tblBrhInfoDAO.save(tblBrhInfo);
        return Constants.SUCCESS_CODE;
    }

    /* (non-Javadoc)
     * @see com.huateng.bo.T10101BO#delete(com.huateng.po.TblBrhInfo)
     */
    @Override
    public String delete(TblBrhInfo tblBrhInfo) {
        tblBrhInfoDAO.delete(tblBrhInfo);
        return Constants.SUCCESS_CODE;
    }

    /* (non-Javadoc)
     * @see com.huateng.bo.T10101BO#update(java.util.List)
     */
    @Override
    public String update(List<TblBrhInfo> tblBrhInfoList) {
        for (TblBrhInfo tblBrhInfo : tblBrhInfoList) {
            tblBrhInfoDAO.update(tblBrhInfo);
        }
        return Constants.SUCCESS_CODE;
    }

    /**
     * @return the tblBrhInfoDAO
     */
    public TblBrhInfoDAO getTblBrhInfoDAO() {
        return tblBrhInfoDAO;
    }

    /**
     * @param tblBrhInfoDAO the tblBrhInfoDAO to set
     */
    public void setTblBrhInfoDAO(TblBrhInfoDAO tblBrhInfoDAO) {
        this.tblBrhInfoDAO = tblBrhInfoDAO;
    }

    /* (non-Javadoc)
     * @see com.huateng.bo.T10101BO#get(java.lang.String)
     */
    @Override
    public TblBrhInfo get(String brhId) {
        return this.tblBrhInfoDAO.get(brhId);
    }

    /* (non-Javadoc)
     * @see com.huateng.bo.T10101BO#delete(java.lang.String)
     */
    @Override
    public String delete(String brhId) {
        this.tblBrhInfoDAO.delete(brhId);
        return Constants.SUCCESS_CODE;
    }

    @Override
    public List<Object[]> findListByName(String name) {
        String sql = "select BRH_ID,BRH_NAME,UP_BRH_ID  from TBL_BRH_INFO where BRH_NAME = '" + name + "'";
        return commQueryDAO.findBySQLQuery(sql);
    }

    @Override
    public List<Object[]> findListByUpBrhId(String upBrhId) {
        String sql = "select BRH_ID,BRH_NAME,UP_BRH_ID from TBL_BRH_INFO where UP_BRH_ID = '" + upBrhId + "'";
        return commQueryDAO.findBySQLQuery(sql);
    }

    @Override
    public List<Object> getBelowBrh(String upBrhID) {
        // TODO Auto-generated method stub
        String sql = " select BRH_ID from TBL_BRH_INFO start with BRH_ID = '" + upBrhID + "' connect by  BRH_ID = prior UP_BRH_ID ";
        return commQueryDAO.findBySQLQuery(sql);
    }
}
