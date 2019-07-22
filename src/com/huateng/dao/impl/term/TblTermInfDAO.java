package com.huateng.dao.impl.term;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.huateng.po.TblTermInf;
import com.huateng.po.TblTermInfTmpPK;
import com.sdses.dao._RootDAO;


public class TblTermInfDAO extends _RootDAO<com.huateng.po.TblTermInf> implements com.huateng.dao.iface.term.TblTermInfDAO{

	public TblTermInfDAO () {}
	
	/* (non-Javadoc)
	 * @see com.huateng.dao.iface.TblTermInfDAO#findAll()
	 */
	public List<TblTermInf> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Class<com.huateng.po.TblTermInf> getReferenceClass () {
		return com.huateng.po.TblTermInf.class;
	}
	
	
	/**
	 * Cast the object as a com.huateng.po.TblTermInf
	 */
	public com.huateng.po.TblTermInf cast (Object object) {
		return (com.huateng.po.TblTermInf) object;
	}
	
	
	public com.huateng.po.TblTermInf load(java.lang.String key)
	{
		return (com.huateng.po.TblTermInf) load(getReferenceClass(), key);
	}
	
	public com.huateng.po.TblTermInf get(java.lang.String key)
	{
		return (com.huateng.po.TblTermInf) get(getReferenceClass(), key);
	}
	
	@SuppressWarnings("unchecked")
	public java.util.List<com.huateng.po.TblTermInf> loadAll()
	{
		return loadAll(getReferenceClass());
	}
	
	
	
	
	
	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblTermInf a transient instance of a persistent class
	 * @return the class identifier
	 */
	public java.lang.String save(com.huateng.po.TblTermInf tblTermInf)
	{
		return (java.lang.String) super.save(tblTermInf);
	}
	
	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblTermInf a transient instance containing new or updated state
	 */
	public void saveOrUpdate(com.huateng.po.TblTermInf tblTermInf)
	{
		super.saveOrUpdate(tblTermInf);
	}
	
	
	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblTermInf a transient instance containing updated state
	 */
	public void update(com.huateng.po.TblTermInf tblTermInf)
	{
		super.update(tblTermInf);
	}
	
	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblTermInf the instance to be removed
	 */
	public void delete(com.huateng.po.TblTermInf tblTermInf)
	{
		super.delete((Object) tblTermInf);
	}
	
	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id)
	{
		super.delete((Object) load(id));
	}

	public List findByHQLQuery(final String hql) {
		List data = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				return query.list();
			}
		});
		return data;
	}

	public void delete(TblTermInfTmpPK id) {
		super.delete((Object) load(id));
	}
	public TblTermInf load(TblTermInfTmpPK key) {
		return (com.huateng.po.TblTermInf) load(getReferenceClass(), key);
	}

	public TblTermInf get(TblTermInfTmpPK key) {
		return (TblTermInf) super.get(getReferenceClass(), key);
	}
}