package com.sdses.dao.impl.pos;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.po.TblTermTpPo;
import com.huateng.system.util.CommonFunction;
import com.sdses.dao.iface.pos.TblTermTpPoDAO;

public class TblTermTpPoDAOImpl implements TblTermTpPoDAO {

    private ICommQueryDAO commQueryDAO;
    @Override
    public int deleteData(String termTpUpd) {
        // TODO Auto-generated method stub
        String sql = "DELETE TBL_TERM_TP WHERE TRIM(TERM_TP)='" + termTpUpd + "'";
        //     boolean equals = sqlDao.equals(sql)
        Transaction tx = null;
        int executeUpdate = 0;
        try {
            Session currentSession = commQueryDAO.getCurrentSession();
            tx = currentSession.beginTransaction();
            SQLQuery createSQLQuery = currentSession.createSQLQuery(sql);
            executeUpdate = createSQLQuery.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
            return 0;
        }
        return executeUpdate;
    }

    @Override
    public int updateData(String descrUpd, String descrUpd1,String termNumUpd,String termTypeUpd,String proDescrUpd) {
        // TODO Auto-generated method stub
        StringBuffer sb = new StringBuffer("");
        sb.append("UPDATE TBL_TERM_TP SET DESCR='" + descrUpd1 + "',TERM_NUM='" + termNumUpd + "',TERM_TYPE='" + termTypeUpd + "',PRO_DESCR='" + proDescrUpd + "' ");
        sb.append("    WHERE TRIM(DESCR)='" + descrUpd + "' ");
        Transaction tx = null;
        int executeUpdate = 0;
        try {
            Session currentSession = commQueryDAO.getCurrentSession();
            tx = currentSession.beginTransaction();
            SQLQuery createSQLQuery = currentSession.createSQLQuery(sb.toString());
            executeUpdate = createSQLQuery.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
            return 0;
        }
        return executeUpdate;
    }
    @Override
    public int queryForTermTp(String termTpUpd) {
        // TODO Auto-generated method stub
        String findsql = "SELECT COUNT(1) FROM TBL_TERM_INF_TMP WHERE  TERM_TP='" + termTpUpd + "'";
        Session currentSession = commQueryDAO.getCurrentSession();
        SQLQuery createSQLQuery = currentSession.createSQLQuery(findsql);
        List list = createSQLQuery.list();
        Object object = list.get(0);
        int parseInt = Integer.parseInt(object.toString());
        return parseInt;
    }
    @Override
    public String queryTermTp(String descrUpd1) {
        // TODO Auto-generated method stub
        String findsql = "SELECT TERM_TP FROM TBL_TERM_TP WHERE  Descr='" + descrUpd1 + "'";
        Session currentSession = commQueryDAO.getCurrentSession();
        SQLQuery createSQLQuery = currentSession.createSQLQuery(findsql);
        List list = createSQLQuery.list();
        Object object = list.get(0);
        return object.toString();
    }
    @Override
    public int queryForInt(String descrUpd) {
        // TODO Auto-generated method stub
        String findsql = "SELECT COUNT(1) FROM TBL_TERM_TP WHERE  Descr='" + descrUpd + "'";
        Session currentSession = commQueryDAO.getCurrentSession();
        SQLQuery createSQLQuery = currentSession.createSQLQuery(findsql);
        List list = createSQLQuery.list();
        Object object = list.get(0);
        int parseInt = Integer.parseInt(object.toString());
        return parseInt;
    }

    @Override
    public int findCountBySQLQuery() {
        // TODO Auto-generated method stub
        StringBuffer stringBuffer = new StringBuffer("select nvl(max(TERM_TP),0) from TBL_TERM_TP");
        Session currentSession = commQueryDAO.getCurrentSession();
        SQLQuery createSQLQuery = currentSession.createSQLQuery(stringBuffer.toString());
        List list = createSQLQuery.list();
        Object object = list.get(0);
        int parseInt = Integer.parseInt(object.toString());
        return parseInt;
    }

    @Override
    public int addDate(TblTermTpPo tblTermTpPo) {
        // TODO Auto-generated method stub
    	if(tblTermTpPo.getProDescr()==null){
    		tblTermTpPo.setProDescr("");
    	}
        StringBuffer sb = new StringBuffer("");
        sb.append("INSERT INTO TBL_TERM_TP(TERM_TP,DESCR,TERM_NUM,TERM_TYPE,PRO_DESCR) ");
        sb.append(" VALUES(").append(CommonFunction.getStringWithDYHTwoSide(tblTermTpPo.getTermTp()));
        sb.append(CommonFunction.getStringWithDYHTwoSide(tblTermTpPo.getDescr()));
        sb.append(CommonFunction.getStringWithDYHTwoSide(tblTermTpPo.getTermNum()));
        sb.append(CommonFunction.getStringWithDYHTwoSide(tblTermTpPo.getTermType()));
        
        sb.append(CommonFunction.getStringWithDYHTwoSide(tblTermTpPo.getProDescr()));
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append(")");
        Transaction tx = null;
        int executeUpdate = 0;
        try {
            Session currentSession = commQueryDAO.getCurrentSession();
            tx = currentSession.beginTransaction();
            SQLQuery createSQLQuery = currentSession.createSQLQuery(sb.toString());
            executeUpdate = createSQLQuery.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
            return 0;
        }
        return executeUpdate;
    }

    public ICommQueryDAO getCommQueryDAO() {
        return commQueryDAO;
    }

    public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
        this.commQueryDAO = commQueryDAO;
    }
}
