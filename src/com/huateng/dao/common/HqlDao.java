package com.huateng.dao.common;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * project JSBConsole date 2013-3-10
 * 
 * @author 樊东东
 */
public class HqlDao extends HibernateTemplate {

	public void beginTransaction() {
		this.getSession().beginTransaction();
	}

	public void rollback() {
		this.getSession().getTransaction().rollback();
	}

	public void commit() {
		this.getSession().getTransaction().commit();
	}

	public void executeSql(final String sql) {
		this.execute(new HibernateCallback() {

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				// TODO Auto-generated method stub

				Statement st = session.connection().createStatement();
				st.execute(sql);
				return null;
			}
		});
	}

	public void executeSql(final String sql, final String[] ps) {
		this.execute(new HibernateCallback() {

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				PreparedStatement sta = session.connection().prepareStatement(
						sql);
				for (int i = 0, len = ps.length; i < len; i++) {
					if (StringUtils.isEmpty(ps[i])) {
						ps[i] = "";
					}
					sta.setString(i + 1, ps[i]);
				}
				return sta.execute();

			}
		});
	}
	public void executeSql(final String sql,final List<String> pl){
		String[] ps = new String[pl.size()];
		for(int i=0,len=ps.length;i<len;i++){
			ps[i] = pl.get(i);
		}
		this.executeSql(sql, ps);
	}
}
