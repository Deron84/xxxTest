package com.sdses.bo.impl.mchnt;

import java.util.List;

import org.apache.log4j.Logger;

import com.huateng.commquery.dao.ICommQueryDAO;
import com.sdses.bo.mchnt.T21503BO;
import com.sdses.dao.iface.mchnt.TblMchtMarkSubsidyDao;
import com.sdses.po.TblMchtMarkSubsidy;

public class T21503BOTarget implements T21503BO {

    private static Logger logger = Logger.getLogger(T21503BOTarget.class);

    private TblMchtMarkSubsidyDao tblMchtMarkSubsidyDao;

    private ICommQueryDAO commQueryDAO;

    @Override
    public List<Object[]> findRuleListAll(String ruleID) {
        // TODO Auto-generated method stub
        String sql = "select * from tbl_mcht_marksubsidy_bind where rule_id = '" + ruleID + "'";
        return commQueryDAO.findBySQLQuery(sql);
    }

    @Override
    public void update(TblMchtMarkSubsidy tblMchtMarkSudy) {
        // TODO Auto-generated method stub
        tblMchtMarkSubsidyDao.update(tblMchtMarkSudy);
    }

    @Override
    public TblMchtMarkSubsidy get(String ruleID) {
        // TODO Auto-generated method stub
        return tblMchtMarkSubsidyDao.get(ruleID);
    }

    @Override
    public String save(TblMchtMarkSubsidy tblMchtMarkSudy) {
        // TODO Auto-generated method stub
        return tblMchtMarkSubsidyDao.save(tblMchtMarkSudy);
    }

    @Override
    public void delete(TblMchtMarkSubsidy tblMchtMarkSudy) {
        // TODO Auto-generated method stub
        tblMchtMarkSubsidyDao.delete(tblMchtMarkSudy);
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        T21503BOTarget.logger = logger;
    }

    public TblMchtMarkSubsidyDao getTblMchtMarkSubsidyDao() {
        return tblMchtMarkSubsidyDao;
    }

    public void setTblMchtMarkSubsidyDao(TblMchtMarkSubsidyDao tblMchtMarkSubsidyDao) {
        this.tblMchtMarkSubsidyDao = tblMchtMarkSubsidyDao;
    }

    public ICommQueryDAO getCommQueryDAO() {
        return commQueryDAO;
    }

    public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
        this.commQueryDAO = commQueryDAO;
    }

}
