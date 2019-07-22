package com.huateng.dao.iface.mchtSrv;

import java.io.Serializable;

public interface TblPaperDefInfDAO {
	public com.huateng.po.mchtSrv.TblPaperDefInf get(com.huateng.po.mchtSrv.TblPaperDefInfPK key);

	public com.huateng.po.mchtSrv.TblPaperDefInf load(com.huateng.po.mchtSrv.TblPaperDefInfPK key);

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param tblPaperDefInf a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public com.huateng.po.mchtSrv.TblPaperDefInfPK save(com.huateng.po.mchtSrv.TblPaperDefInf tblPaperDefInf);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param tblPaperDefInf a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(com.huateng.po.mchtSrv.TblPaperDefInf tblPaperDefInf);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblPaperDefInf a transient instance containing updated state
	 */
	public void update(com.huateng.po.mchtSrv.TblPaperDefInf tblPaperDefInf);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(com.huateng.po.mchtSrv.TblPaperDefInfPK id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param tblPaperDefInf the instance to be removed
	 */
	public void delete(com.huateng.po.mchtSrv.TblPaperDefInf tblPaperDefInf);


}