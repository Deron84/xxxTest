package com.huateng.dao.iface.mchnt;

import java.util.List;

import com.huateng.po.TblDivMchnt;


public interface TblDivMchntDAO {

/* (non-Javadoc)
 * @see com.huateng.dao.iface.TblDivMchntDAO#findAll()
 */
public List<TblDivMchnt> findAll();

public Class<com.huateng.po.TblDivMchnt> getReferenceClass ();


/**
 * Cast the object as a com.huateng.po.TblDivMchnt
 */
public com.huateng.po.TblDivMchnt cast (Object object);


public com.huateng.po.TblDivMchnt load(com.huateng.po.TblDivMchntPK key);

public com.huateng.po.TblDivMchnt get(com.huateng.po.TblDivMchntPK key);

@SuppressWarnings("unchecked")
public java.util.List<com.huateng.po.TblDivMchnt> loadAll();





/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblDivMchnt a transient instance of a persistent class
 * @return the class identifier
 */
public com.huateng.po.TblDivMchntPK save(com.huateng.po.TblDivMchnt tblDivMchnt);

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param tblDivMchnt a transient instance containing new or updated state
 */
public void saveOrUpdate(com.huateng.po.TblDivMchnt tblDivMchnt);


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param tblDivMchnt a transient instance containing updated state
 */
public void update(com.huateng.po.TblDivMchnt tblDivMchnt);

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param tblDivMchnt the instance to be removed
 */
public void delete(com.huateng.po.TblDivMchnt tblDivMchnt);

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param id the instance ID to be removed
 */
public void delete(com.huateng.po.TblDivMchntPK id);
}