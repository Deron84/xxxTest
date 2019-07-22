package com.huateng.dao.iface.mchnt;

import java.util.List;
import java.util.Map;

import com.huateng.po.mchnt.TblHisDiscAlgo;
import com.huateng.po.mchnt.TblHisDiscAlgoPK;

public interface TblHisDiscAlgoDAO{
	
	public TblHisDiscAlgo get(TblHisDiscAlgoPK key);

	public TblHisDiscAlgo load(TblHisDiscAlgoPK key);
	
	public java.util.List<TblHisDiscAlgo> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param tblDivMchntTmp a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public TblHisDiscAlgoPK save(TblHisDiscAlgo tblDivMchntTmp);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param tblDivMchntTmp a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(TblHisDiscAlgo tblDivMchntTmp);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblDivMchntTmp a transient instance containing updated state
	 */
	public void update(TblHisDiscAlgo tblDivMchntTmp);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(TblHisDiscAlgoPK id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param tblDivMchntTmp the instance to be removed
	 */
	public void delete(TblHisDiscAlgo tblDivMchntTmp);

	/**
	 * @param string
	 * @param map
	 * @return
	 * 2011-6-21上午11:24:57
	 */
	public List findByNamedQuery(String string, Map<String, String> map);

}