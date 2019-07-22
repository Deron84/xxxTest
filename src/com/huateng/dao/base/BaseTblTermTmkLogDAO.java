package com.huateng.dao.base;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.huateng.dao.iface.term.TblTermTmkLogDAO;
import com.huateng.po.TblTermTmkLog;
import com.huateng.po.TblTermTmkLogPK;

/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseTblTermTmkLogDAO extends com.sdses.dao._RootDAO {

	public BaseTblTermTmkLogDAO () {}
	

	// query name references


	public static TblTermTmkLogDAO instance;

	/**
	 * Return a singleton of the DAO
	 */
	public static TblTermTmkLogDAO getInstance () {
		if (null == instance) instance = new com.huateng.dao.impl.term.TblTermTmkLogDAO();
		return instance;
	}

	public Class getReferenceClass () {
		return com.huateng.po.TblTermTmkLog.class;
	}

    public Order getDefaultOrder () {
		return null;
    }

	/**
	 * Cast the object as a com.huateng.po.TblTermTmkLog
	 */
	public com.huateng.po.TblTermTmkLog cast (Object object) {
		return (com.huateng.po.TblTermTmkLog) object;
	}

	public com.huateng.po.TblTermTmkLog get(TblTermTmkLogPK key)
	{
		return (com.huateng.po.TblTermTmkLog) get(getReferenceClass(), key);
	}


	public com.huateng.po.TblTermTmkLog load(TblTermTmkLogPK key)
	{
		return (com.huateng.po.TblTermTmkLog) load(getReferenceClass(), key);
	}


/* Generic methods */

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param tblTermTmkLog a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public TblTermTmkLogPK save(com.huateng.po.TblTermTmkLog tblTermTmkLog)
	{
		return (TblTermTmkLogPK) super.save(tblTermTmkLog);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param tblTermTmkLog a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(com.huateng.po.TblTermTmkLog tblTermTmkLog)
	{
		saveOrUpdate((Object) tblTermTmkLog);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblTermTmkLog a transient instance containing updated state
	 */
	public void update(com.huateng.po.TblTermTmkLog tblTermTmkLog) 
	{
		update((Object) tblTermTmkLog);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(TblTermTmkLogPK id)
	{
		delete((Object) load(id));
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param tblTermTmkLog the instance to be removed
	 */
	public void delete(com.huateng.po.TblTermTmkLog tblTermTmkLog)
	{
		delete((Object) tblTermTmkLog);
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
	public void batchSave(final List<TblTermTmkLog> list) {  
        this.getHibernateTemplate().execute(new HibernateCallback() {  
            public Object doInHibernate(Session session) throws HibernateException, SQLException {                  
	        for (int i = 0; i < list.size(); i++) {  
	                    session.save(list.get(i));         
	                    if (i % 50 == 0) {  
	                        session.flush();  
	                        session.clear();  
	                    }  
	                }                  
	                return null;  
	            }  
	        });  
}  
}