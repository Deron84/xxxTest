package com.sdses.struts.upGrade.action;

import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class UpGradeAction {
    private static final long serialVersionUID = 1L;

    private SessionFactory sessionFactory;

    public class TCmchntMap {
        private String ylMchntNo;

        private String ylTermNo;

        private String cMchntBrh;

        private String sCanCodeFlag;

        public String getYlMchntNo() {
            return ylMchntNo;
        }

        public void setYlMchntNo(String ylMchntNo) {
            this.ylMchntNo = ylMchntNo;
        }

        public String getYlTermNo() {
            return ylTermNo;
        }

        public void setYlTermNo(String ylTermNo) {
            this.ylTermNo = ylTermNo;
        }

        public String getcMchntBrh() {
            return cMchntBrh;
        }

        public void setcMchntBrh(String cMchntBrh) {
            this.cMchntBrh = cMchntBrh;
        }

        public String getsCanCodeFlag() {
            return sCanCodeFlag;
        }

        public void setsCanCodeFlag(String sCanCodeFlag) {
            this.sCanCodeFlag = sCanCodeFlag;
        }
    }

    public void execute() throws Exception {
        PrintWriter printWriter = null;
        Transaction tx = null;
        SQLQuery sqlQuery = null;
        String uuid = null;
        String sqlStr = null;
        String sql = null;
        Session session = sessionFactory.openSession();
        tx = session.beginTransaction();
        sql = " select YL_MCHNT_NO,YL_TERM_NO,C_MCHNT_BRH,SCANCODEFLAG from t_cmchnt_map ";
        Query query = session.createSQLQuery(sql);
        List<Object> list = query.list();
        for (int i = 0; i < list.size(); i++) {
            Object[] obj = (Object[]) list.get(i);
            uuid = UUID.randomUUID().toString().replace("-", "");
            sqlStr = "update t_cmchnt_map set ID= '" + uuid + "' where YL_MCHNT_NO='" + obj[0] + "' and YL_TERM_NO='" + obj[1] + "' and C_MCHNT_BRH='"
                    + obj[2] + "' and SCANCODEFLAG='" + obj[3] + "'";
            sqlQuery = session.createSQLQuery(sqlStr);
            int executeUpdate = sqlQuery.executeUpdate();
            if (executeUpdate <= 0) {
                tx.rollback();
                session.close();
                printWriter = ServletActionContext.getResponse().getWriter();
                printWriter.write("FAIL");
                printWriter.flush();
                printWriter.close();
                return;
            }
            sqlStr = null;
            uuid = null;
        }
        tx.commit();
        session.close();
        printWriter = ServletActionContext.getResponse().getWriter();
        printWriter.write("SUCCESS");
        printWriter.flush();
        printWriter.close();
        return;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
