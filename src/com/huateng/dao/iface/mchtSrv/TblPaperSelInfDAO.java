package com.huateng.dao.iface.mchtSrv;

import java.io.Serializable;

public interface TblPaperSelInfDAO {

	
	public com.huateng.po.mchtSrv.TblPaperSelInf get(com.huateng.po.mchtSrv.TblPaperSelInfPK key);

	public com.huateng.po.mchtSrv.TblPaperSelInf load(com.huateng.po.mchtSrv.TblPaperSelInfPK key);


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param tblPaperSelInf a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public com.huateng.po.mchtSrv.TblPaperSelInfPK save(com.huateng.po.mchtSrv.TblPaperSelInf tblPaperSelInf);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param tblPaperSelInf a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(com.huateng.po.mchtSrv.TblPaperSelInf tblPaperSelInf);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblPaperSelInf a transient instance containing updated state
	 */
	public void update(com.huateng.po.mchtSrv.TblPaperSelInf tblPaperSelInf);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param tblPaperSelInf the instance to be removed
	 */
	public void delete(com.huateng.po.mchtSrv.TblPaperSelInf tblPaperSelInf);


}