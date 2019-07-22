package com.huateng.dao.impl.term;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.huateng.po.TblTermInfTmp;
import com.huateng.po.TblTermInfTmpPK;
import com.sdses.dao._RootDAO;


public class TblTermInfTmpDAO extends _RootDAO<com.huateng.po.TblTermInfTmp> implements com.huateng.dao.iface.term.TblTermInfTmpDAO{

		public TblTermInfTmpDAO () {}
		
		public List<TblTermInfTmp> findAll() {
			// TODO Auto-generated method stub
			return null;
		}
		
		public Class<com.huateng.po.TblTermInfTmp> getReferenceClass () {
			return com.huateng.po.TblTermInfTmp.class;
		}
		
		
		/**
		 * Cast the object as a com.huateng.po.TblTermInfTmp
		 */
		public com.huateng.po.TblTermInfTmp cast (Object object) {
			return (com.huateng.po.TblTermInfTmp) object;
		}
		
		
		@SuppressWarnings("unchecked")
		public java.util.List<com.huateng.po.TblTermInfTmp> loadAll()
		{
			return loadAll(getReferenceClass());
		}
		
		
		
		
		
		/**
		 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
		 * of the identifier property if the assigned generator is used.)
		 * @param tblTermInfTmp a transient instance of a persistent class
		 * @return the class identifier
		 */
		public TblTermInfTmpPK save(com.huateng.po.TblTermInfTmp tblTermInfTmp)
		{
			return (TblTermInfTmpPK) super.save(tblTermInfTmp);
		}
		
		/**
		 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
		 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
		 * identifier property mapping.
		 * @param tblTermInfTmp a transient instance containing new or updated state
		 */
		public void saveOrUpdate(com.huateng.po.TblTermInfTmp tblTermInfTmp)
		{
			super.saveOrUpdate(tblTermInfTmp);
		}
		
		
		/**
		 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
		 * instance with the same identifier in the current session.
		 * @param tblTermInfTmp a transient instance containing updated state
		 */
		public void update(com.huateng.po.TblTermInfTmp tblTermInfTmp)
		{
			super.update(tblTermInfTmp);
		}
		
		/**
		 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
		 * Session or a transient instance with an identifier associated with existing persistent state.
		 * @param tblTermInfTmp the instance to be removed
		 */
		public void delete(com.huateng.po.TblTermInfTmp tblTermInfTmp)
		{
			super.delete((Object) tblTermInfTmp);
		}
		
		/**
		 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
		 * Session or a transient instance with an identifier associated with existing persistent state.
		 * @param id the instance ID to be removed
		 */
		public void delete(TblTermInfTmpPK pk)
		{
			super.delete((Object) load(pk));
		}
		
		public TblTermInfTmp get(TblTermInfTmpPK key) {
			return (TblTermInfTmp) super.get(getReferenceClass(), key);
		}
		
		
		public TblTermInfTmp load(TblTermInfTmpPK key) {
			return (com.huateng.po.TblTermInfTmp) load(getReferenceClass(), key);
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
}