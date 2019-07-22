package com.huateng.dao.impl.term;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.huateng.dao.base.BaseTblTermManagementDAO;
import com.huateng.po.TblTermManagement;


public class TblTermManagementDAO extends BaseTblTermManagementDAO implements com.huateng.dao.iface.term.TblTermManagementDAO {

	public TblTermManagementDAO () {}

	/* (non-Javadoc)
	 * @see com.huateng.dao.iface.TblTermManagementDAO#findAll()
	 */
	public List<TblTermManagement> findAll() {
		return null;
	}
	
	public void saveBatch(List<TblTermManagement> list)
	{
		Iterator<TblTermManagement> it = list.iterator();
		while(it.hasNext())
		{
			this.getHibernateTemplate().save(it.next());
		}
	}
	
	public List<TblTermManagement> findByHQLQuery(final String hql,final int count) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setFirstResult(0);
				query.setMaxResults(count);
				return query.list();
			}
		});
	}
}