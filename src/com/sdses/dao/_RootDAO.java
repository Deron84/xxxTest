package com.sdses.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public abstract class _RootDAO<T> extends HibernateDaoSupport {
	
	@SuppressWarnings("unchecked")
	protected abstract Class getReferenceClass();
	
	@SuppressWarnings("unchecked")
	protected Object load(Class refClass, Serializable key) {
        return getHibernateTemplate().load(refClass, key);
    }
	
	@SuppressWarnings("unchecked")
	protected List loadAll(Class refClass) {
		return getHibernateTemplate().loadAll(refClass);
	}
	
	@SuppressWarnings("unchecked")
	protected Object get(Class refClass, Serializable key) {
        return getHibernateTemplate().get(refClass, key);
    }
	
	protected Serializable save(Object obj) {
		return getHibernateTemplate().save(obj);
	}
	
	protected void saveOrUpdate(Object obj) {
		getHibernateTemplate().saveOrUpdate(obj);
    }
	
	protected void update(Object obj) {
		getHibernateTemplate().update(obj);
    }
	
	protected void delete(Object obj) {
		getHibernateTemplate().delete(obj);
    }
	
	protected void refresh(Object obj) {
		getHibernateTemplate().refresh(obj);
    }

    public void flush() {
		getHibernateTemplate().flush();
    }

    @SuppressWarnings("unchecked")
	public List findByNamedQuery(String name) {
        return getHibernateTemplate().findByNamedQuery(name);
    }
 
    @SuppressWarnings("unchecked")
	public List findByNamedQuery(String name, Map params) {
        return findByNamedQuery(name, params, -1, -1);
    }

    @SuppressWarnings("unchecked")
	public List findByNamedQuery(final String name, final Map params, final int begin, final int count) {
        return getHibernateTemplate().executeFind(
            new HibernateCallback() { 
                public Object doInHibernate(Session session) 
                    throws HibernateException, SQLException { 
                    Query query = session.getNamedQuery(name); 
                    if (null != params) {
                        for (Iterator i=params.entrySet().iterator(); i.hasNext(); ) {
                            Map.Entry entry = (Map.Entry) i.next();
                            query.setParameter((String) entry.getKey(), entry.getValue());
                        }
                    }
                    if( begin >= 0 ) {
                        query.setFirstResult(begin); 
                        query.setMaxResults(count);
                    } 
                    return query.list(); 
                } 
            }
        ); 
    }
    
    @SuppressWarnings("unchecked")
	public List findByNamedQuery(String name, Serializable[] params) {
        return findByNamedQuery(name, params, -1, -1);
    }

    @SuppressWarnings("unchecked")
	public List findByNamedQuery(final String name, final Serializable[] params, final int begin, final int count) {
        return getHibernateTemplate().executeFind(
            new HibernateCallback() { 
                public Object doInHibernate(Session session) 
                    throws HibernateException, SQLException { 
                    Query query = session.getNamedQuery(name); 
                    if (null != params) {
                        for (int i = 0; i < params.length; i++) {
                            query.setParameter(i, params[i]);
                        }
                    }
                    if( begin >= 0 ) {
                        query.setFirstResult(begin); 
                        query.setMaxResults(count); 
                    }
                    return query.list(); 
                } 
            }
        ); 
    }
}
