package com.sdses.dao.impl.mchnt;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.system.util.CommonFunction;
import com.sdses.dao.iface.mchnt.TAirpayMapDAO;
import com.sdses.po.TAirpayMap;
import com.sdses.po.TAirpayMapPK;

public class TAirpayMapDAOImpl implements TAirpayMapDAO{

	private ICommQueryDAO commQueryDAO;
	
	@Override
	public int addDate(TAirpayMap tAirpayMap) {
		if(tAirpayMap.getcKey2()==null){
			tAirpayMap.setcKey2("");
		}
		if(tAirpayMap.getSignType()==null){
			tAirpayMap.setSignType("");
		}
		StringBuffer sb = new StringBuffer("");
        sb.append("INSERT INTO T_AIRPAY_MAP(YL_MCHNT_NO,YL_TERM_NO,APP_ID,MCH_ID,C_KEY1,C_KEY2,SIGN_TYPE,PAY_FLAG,FLAG,AUTHORITY_FLAG) ");
        sb.append(" VALUES(").append(CommonFunction.getStringWithDYHTwoSide(tAirpayMap.gettAirpayMapPK().getYlMchntNo()));
        sb.append(CommonFunction.getStringWithDYHTwoSide(tAirpayMap.gettAirpayMapPK().getYlTermNo()));
        sb.append(CommonFunction.getStringWithDYHTwoSide(tAirpayMap.getAppId()));
        sb.append(CommonFunction.getStringWithDYHTwoSide(tAirpayMap.getMchId()));
        sb.append(CommonFunction.getStringWithDYHTwoSide(tAirpayMap.getcKey1()));
        sb.append(CommonFunction.getStringWithDYHTwoSide(tAirpayMap.getcKey2()));
        sb.append(CommonFunction.getStringWithDYHTwoSide(tAirpayMap.getSignType()));
        sb.append(CommonFunction.getStringWithDYHTwoSide(tAirpayMap.gettAirpayMapPK().getPayFlag()));
        sb.append(CommonFunction.getStringWithDYHTwoSide(tAirpayMap.getFlag()));
        sb.append(CommonFunction.getStringWithDYHTwoSide(tAirpayMap.getAuthorityFlag()));
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
	
	@Override
	public int deleteDate(TAirpayMapPK tAirpayMapPK) {
		String sql = "DELETE from T_AIRPAY_MAP WHERE TRIM(YL_MCHNT_NO)='" + tAirpayMapPK.getYlMchntNo() + "' "
				+ "and trim(YL_TERM_NO)='"+tAirpayMapPK.getYlTermNo()+"' and trim(PAY_FLAG)='"+tAirpayMapPK.getPayFlag()+"'";
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
	public int isExitRecord(TAirpayMapPK tAirpayMapPK) throws Exception {
		String findsql="select COUNT(1) from T_AIRPAY_MAP where TRIM(YL_MCHNT_NO)='" + tAirpayMapPK.getYlMchntNo() + "' "
				+ "and TRIM(YL_TERM_NO)='" + tAirpayMapPK.getYlTermNo()+ "' and TRIM(PAY_FLAG)='"+tAirpayMapPK.getPayFlag()+"'";
		Session currentSession = commQueryDAO.getCurrentSession();
	    SQLQuery createSQLQuery = currentSession.createSQLQuery(findsql);
	    List list = createSQLQuery.list();
	    Object object = list.get(0);
	    int parseInt = Integer.parseInt(object.toString());
	    return parseInt;
	}
	
	@Override
	public int updateData(TAirpayMap tAirpayMap) throws Exception {
		if(tAirpayMap.getcKey2()==null){
			tAirpayMap.setcKey2("");
		}
		if(tAirpayMap.getSignType()==null){
			tAirpayMap.setSignType("");
		}
		StringBuffer sb = new StringBuffer("");
		sb.append("UPDATE T_AIRPAY_MAP SET APP_ID='" + tAirpayMap.getAppId() + "',MCH_ID='"+tAirpayMap.getMchId()+"',C_KEY1='"+tAirpayMap.getcKey1()+"', ");
		sb.append("C_KEY2='"+tAirpayMap.getcKey2()+"',SIGN_TYPE='"+tAirpayMap.getSignType()+"',FLAG='"+tAirpayMap.getFlag()+"',AUTHORITY_FLAG='"+tAirpayMap.getAuthorityFlag()+"'  WHERE TRIM(YL_MCHNT_NO)='" + tAirpayMap.gettAirpayMapPK().getYlMchntNo() + "'  ");
	    sb.append("    and TRIM(YL_TERM_NO)='" + tAirpayMap.gettAirpayMapPK().getYlTermNo() + "' and TRIM(PAY_FLAG)='"+tAirpayMap.gettAirpayMapPK().getPayFlag()+"'");
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
	@Override
	public List<Object[]> isExitAliDate(String appId) throws Exception {
		String findsql="select YL_MCHNT_NO,YL_TERM_NO,APP_ID,MCH_ID,PAY_FLAG from T_AIRPAY_MAP where TRIM(APP_ID)='" + appId + "' ";
		Session currentSession = commQueryDAO.getCurrentSession();
	    SQLQuery createSQLQuery = currentSession.createSQLQuery(findsql);
	    return createSQLQuery.list();
	}

	@Override
	public List<Object[]> isExitWeDate(String appId, String mchId) throws Exception {
		String findsql="select YL_MCHNT_NO,YL_TERM_NO,APP_ID,MCH_ID,PAY_FLAG from T_AIRPAY_MAP where TRIM(APP_ID)='" + appId + "'"
				+ " and TRIM(MCH_ID)='"+mchId+"' ";
		Session currentSession = commQueryDAO.getCurrentSession();
	    SQLQuery createSQLQuery = currentSession.createSQLQuery(findsql);
	    return createSQLQuery.list();
	}
}
