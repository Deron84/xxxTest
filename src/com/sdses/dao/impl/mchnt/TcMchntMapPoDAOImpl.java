package com.sdses.dao.impl.mchnt;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.huateng.commquery.dao.ICommQueryDAO;
import com.sdses.dao.iface.mchnt.TcMchntMapPoDAO;
import com.sdses.po.TcMchntMap;
import com.sdses.po.TcMchntMapPK;

public class TcMchntMapPoDAOImpl implements TcMchntMapPoDAO{
	private ICommQueryDAO commQueryDAO;
	@Override
	public int update(TcMchntMap tcMchntMap) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer("");
		sb.append(
				"UPDATE T_CMCHNT_MAP SET C_MCHNT_NO='"+tcMchntMap.getcMchntNo()+"',C_KEY='"+tcMchntMap.getcKey()+"', "
				+ "FLAG='"+tcMchntMap.getEnableFlag()+"',VALID_START_DATE='"+tcMchntMap.getStartDate()+"',SCANCODEFLAG='"+tcMchntMap.getTcMchntMapPK().getScanFlag()+"', "
				+ "VALID_END_DATE='"+tcMchntMap.getEndDate()+"',RESV1='"+tcMchntMap.getResv1()+"',C_MCHNT_BRH='"+tcMchntMap.getTcMchntMapPK().getBrhFlag()+"', "
				+ "BUSS_ID='"+tcMchntMap.getBussId()+"', BRANCHCODE='"+tcMchntMap.getBranchCode()+"',CIPHER='"+tcMchntMap.getCipher()+"',TERM_FIX_NO='"+tcMchntMap.getTcMchntMapPK().getTermFixNo()+"' "
				+ " where YL_MCHNT_NO='"+tcMchntMap.getTcMchntMapPK().getYlMchntNo()+"' and YL_TERM_NO='"+tcMchntMap.getTcMchntMapPK().getYlTermNo()+"' and ID='"+tcMchntMap.getId()+"'");
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
	public int updateData(TcMchntMap tcMchntMap) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer("");
		sb.append(
				"UPDATE T_CMCHNT_MAP SET C_MCHNT_NO='"+tcMchntMap.getcMchntNo()+"',C_KEY='"+tcMchntMap.getcKey()+"', "
				+ "FLAG='"+tcMchntMap.getEnableFlag()+"',VALID_START_DATE='"+tcMchntMap.getStartDate()+"', "
				+ "VALID_END_DATE='"+tcMchntMap.getEndDate()+"',RESV1='"+tcMchntMap.getResv1()+"',C_MCHNT_BRH='"+tcMchntMap.getTcMchntMapPK().getBrhFlag()+"', "
				+ "BUSS_ID='"+tcMchntMap.getBussId()+"', BRANCHCODE='"+tcMchntMap.getBranchCode()+"',CIPHER='"+tcMchntMap.getCipher()+"' "
				+ "where YL_MCHNT_NO='"+tcMchntMap.getTcMchntMapPK().getYlMchntNo()+"' and YL_TERM_NO='"+tcMchntMap.getTcMchntMapPK().getYlTermNo()+"' "
				+ "and TERM_FIX_NO='"+tcMchntMap.getTcMchntMapPK().getTermFixNo()+"' and MERGEFLAG='"+tcMchntMap.getMergeFlag()+"'");
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
	public int updateCertData(String ylMchntNo, String uploadFileName,String brhFlag,String scanFlag) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer("");
		sb.append("UPDATE T_CMCHNT_MAP SET CERTPATH='" + uploadFileName + "' ");
	    sb.append("    WHERE TRIM(YL_MCHNT_NO)='" + ylMchntNo + "'  and TRIM(C_MCHNT_BRH)='" + brhFlag + "' and TRIM(SCANCODEFLAG)='"+scanFlag+"'");
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
	public int isExitMchnt(String id) throws Exception {
		// TODO Auto-generated method stub
		String findsql="select COUNT(1) from T_CMCHNT_MAP where id='" + id + "' ";
		Session currentSession = commQueryDAO.getCurrentSession();
	    SQLQuery createSQLQuery = currentSession.createSQLQuery(findsql);
	    List list = createSQLQuery.list();
	    Object object = list.get(0);
	    int parseInt = Integer.parseInt(object.toString());
	    return parseInt;
	}
	@Override
	public int delete(TcMchntMapPK tcMchntMapPK,String id) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer("");
		sb.append("delete from T_CMCHNT_MAP where TRIM(YL_MCHNT_NO)='" + tcMchntMapPK.getYlMchntNo() + "'");
		sb.append(" and TRIM(YL_TERM_NO)='" + tcMchntMapPK.getYlTermNo() + "' and TRIM(ID)='"+id+"' ");
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
	public int delete2(TcMchntMapPK tcMchntMapPK,String id) throws Exception {
		StringBuffer sb = new StringBuffer("");
		sb.append("delete from T_CMCHNT_MAP where TRIM(YL_MCHNT_NO)='" + tcMchntMapPK.getYlMchntNo() + "'");
		sb.append(" and TRIM(YL_TERM_NO)='" + tcMchntMapPK.getYlTermNo() + "' and TRIM(ID)!='"+id+"'");
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
	public int deleteData(TcMchntMapPK tcMchntMapPK, String mergeFlag) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer("");
		sb.append("delete from T_CMCHNT_MAP where TRIM(YL_MCHNT_NO)='" + tcMchntMapPK.getYlMchntNo() + "' and TRIM(YL_TERM_NO)='" + tcMchntMapPK.getYlTermNo() + "'");
		sb.append(" and TRIM(TERM_FIX_NO)='"+tcMchntMapPK.getTermFixNo()+"' and TRIM(MERGEFLAG)='"+mergeFlag+"'");
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
	/*@Override
	public String getBrhId(String brhName) throws Exception {
		String findsql = "SELECT C_BRH_ID FROM T_BRH_DATADIC WHERE  C_BRH_NAME='" + brhName + "'";
        Session currentSession = commQueryDAO.getCurrentSession();
        SQLQuery createSQLQuery = currentSession.createSQLQuery(findsql);
        List list = createSQLQuery.list();
        Object object = list.get(0);
        return object.toString();
	}*/

	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}
	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}
	
}
