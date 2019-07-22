package com.sdses.bo.impl.mchnt;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.huateng.commquery.dao.ICommQueryDAO;
import com.sdses.bo.mchnt.T21504BO;
import com.sdses.dao.iface.mchnt.TblMchtMarkSubsidyBindDao;
import com.sdses.po.TblMchtMarkSubsidyBind;

public class T21504BOTarget implements T21504BO {

    private static Logger logger = Logger.getLogger(T21504BOTarget.class);

    private TblMchtMarkSubsidyBindDao tblMchtMarkSubsidyBindDao;

    private ICommQueryDAO commQueryDAO;

    @Override
    public List<Object[]> findByTermIdTimes(String terminalId, String endTime) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String format = sdf.format(date);
        // TODO Auto-generated method stub
        String sql = "select RECORD_ID,RULE_ID,MCHNT_NO,TERM_NO,OPEN_FLAG,BIND_TIME " + " from TBL_MCHT_MARKSUBSIDY_BIND " + " where TERM_NO  = '"
                + terminalId + "' and '" + format + "' <= '" + endTime + "'";
        return commQueryDAO.findBySQLQuery(sql);
    }

    @Override
    public List<Object[]> findTermListAllTwoTermStr(String termStr) {
        // TODO Auto-generated method stub
        String[] termStrArr = termStr.split(",");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < termStrArr.length; i++) {
            sb.append("'").append(termStrArr[i]).append("'").append(",");
            if (i == termStrArr.length - 1) {
                sb.append("'").append(termStrArr[i]).append("'");
            }
        }
        String sql = "SELECT t.MAPPING_MCHNTCDTWO,t.MAPPING_TERMIDTWO,t.MAPPING_MCHNTTYPETWO " + " FROM tbl_term_inf t" + " where 1=1 "
                + " and TERM_STA in (1,2)" + " and MAPPING_TERMIDTWO in (" + sb.toString() + ")";
        return commQueryDAO.findBySQLQuery(sql);
    }

    @Override
    public List<Object[]> findTermListAllTwoMchntStr(String mchntStr) {
        // TODO Auto-generated method stub
        String[] mchntcdArr = mchntStr.split(",");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mchntcdArr.length; i++) {
            sb.append("'").append(mchntcdArr[i]).append("'").append(",");
            if (i == mchntcdArr.length - 1) {
                sb.append("'").append(mchntcdArr[i]).append("'");
            }
        }
        String sql = "SELECT t.MAPPING_MCHNTCDTWO,t.MAPPING_TERMIDTWO,t.MAPPING_MCHNTTYPETWO " + " FROM tbl_term_inf t" + " where 1=1 "
                + " and TERM_STA in (1,2)" + " and MAPPING_MCHNTCDTWO in (" + sb.toString() + ")";
        return commQueryDAO.findBySQLQuery(sql);
    }

    @Override
    public List<Object[]> findBindListByDiscountId(String ruleID) {
        // TODO Auto-generated method stub
        // TODO Auto-generated method stub
        String sql = "select RULE_ID from TBL_MCHT_MARKSUBSIDY_BIND where RULE_ID ='" + ruleID + "'";
        return commQueryDAO.findBySQLQuery(sql);
    }

    @Override
    public List<Object[]> findTermListAllTwo() {
        // TODO Auto-generated method stub
        String sql = "SELECT t.MAPPING_MCHNTCDTWO,t.MAPPING_TERMIDTWO,t.MAPPING_MCHNTTYPETWO " + " FROM tbl_term_inf t" + " where 1=1 "
                + " and TERM_STA in (1,2)" + " and MAPPING_MCHNTCDTWO is not null and MAPPING_TERMIDTWO is not null";
        return commQueryDAO.findBySQLQuery(sql);
    }

    @Override
    public void update(TblMchtMarkSubsidyBind tblMchtMarkSudyBind) {
        // TODO Auto-generated method stub
        tblMchtMarkSubsidyBindDao.update(tblMchtMarkSudyBind);
    }

    @Override
    public TblMchtMarkSubsidyBind get(String recordID) {
        // TODO Auto-generated method stub
        return tblMchtMarkSubsidyBindDao.get(recordID);
    }

    @Override
    public String save(TblMchtMarkSubsidyBind tblMchtMarkSudyBind) {
        // TODO Auto-generated method stub
        return tblMchtMarkSubsidyBindDao.save(tblMchtMarkSudyBind);
    }

    @Override
    public void delete(TblMchtMarkSubsidyBind tblMchtMarkSudyBind) {
        // TODO Auto-generated method stub
        tblMchtMarkSubsidyBindDao.delete(tblMchtMarkSudyBind);
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        T21504BOTarget.logger = logger;
    }

    public TblMchtMarkSubsidyBindDao getTblMchtMarkSubsidyBindDao() {
        return tblMchtMarkSubsidyBindDao;
    }

    public void setTblMchtMarkSubsidyBindDao(TblMchtMarkSubsidyBindDao tblMchtMarkSubsidyBindDao) {
        this.tblMchtMarkSubsidyBindDao = tblMchtMarkSubsidyBindDao;
    }

    public ICommQueryDAO getCommQueryDAO() {
        return commQueryDAO;
    }

    public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
        this.commQueryDAO = commQueryDAO;
    }

}
