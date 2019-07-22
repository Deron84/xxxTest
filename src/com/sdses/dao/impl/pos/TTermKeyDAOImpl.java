package com.sdses.dao.impl.pos;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.po.TTermKey;
import com.huateng.system.util.CommonFunction;
import com.sdses.dao.iface.pos.TTermKeyDAO;

public class TTermKeyDAOImpl implements TTermKeyDAO{

	private ICommQueryDAO commQueryDAO;
	
	@Override
	public int queryForInt(TTermKey tTermKey) throws Exception {
		 String findsql = "SELECT COUNT(1) FROM T_TERM_KEY WHERE YL_MCHNT_NO='" + tTermKey.getYlMchntNo() + "' "
		 		             + "AND YL_TERM_NO='"+tTermKey.getYlTermNo()+"'";
	     Session currentSession = commQueryDAO.getCurrentSession();
	     SQLQuery createSQLQuery = currentSession.createSQLQuery(findsql);
	     List list = createSQLQuery.list();
	     Object object = list.get(0);
	     int parseInt = Integer.parseInt(object.toString());
	     return parseInt;
	}

	@Override
	public String queryForKey() throws Exception {
		 String findsql = "SELECT PARAM FROM T_KEY_PARAM WHERE ID='1'";
	     Session currentSession = commQueryDAO.getCurrentSession();
	     SQLQuery createSQLQuery = currentSession.createSQLQuery(findsql);
	     List list = createSQLQuery.list();
	     Object object = list.get(0);
	     return object.toString();
	}

	@Override
	public int addDate(TTermKey tTermKey) throws Exception {
        StringBuffer sb = new StringBuffer("");
        sb.append("INSERT INTO T_TERM_KEY(YL_MCHNT_NO,YL_TERM_NO,C_KEY) ");
        sb.append(" VALUES(").append(CommonFunction.getStringWithDYHTwoSide(tTermKey.getYlMchntNo()));
        sb.append(CommonFunction.getStringWithDYHTwoSide(tTermKey.getYlTermNo()));
        sb.append(CommonFunction.getStringWithDYHTwoSide(tTermKey.getcKey()));
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
