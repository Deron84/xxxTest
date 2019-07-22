package com.huateng.dao.impl.mchtSrv;

import com.sdses.dao._RootDAO;

/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseTblMarketActReviewDAO extends _RootDAO<com.huateng.po.mchtSrv.TblMarketActReview> {



	// query name references




	public Class<com.huateng.po.mchtSrv.TblMarketActReview> getReferenceClass () {
		return com.huateng.po.mchtSrv.TblMarketActReview.class;
	}


	/**
	 * Cast the object as a com.huateng.po.mchtSrv.TblMarketActReview
	 */
	public com.huateng.po.mchtSrv.TblMarketActReview cast (Object object) {
		return (com.huateng.po.mchtSrv.TblMarketActReview) object;
	}


	public com.huateng.po.mchtSrv.TblMarketActReview load(java.lang.String key)
	{
		return (com.huateng.po.mchtSrv.TblMarketActReview) load(getReferenceClass(), key);
	}

	public com.huateng.po.mchtSrv.TblMarketActReview get(java.lang.String key)
	{
		return (com.huateng.po.mchtSrv.TblMarketActReview) get(getReferenceClass(), key);
	}

	public java.util.List<com.huateng.po.mchtSrv.TblMarketActReview> loadAll()
	{
		return loadAll(getReferenceClass());
	}





	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblMarketActReview a transient instance of a persistent class
	 * @return the class identifier
	 */
	public java.lang.String save(com.huateng.po.mchtSrv.TblMarketActReview tblMarketActReview)
	{
		return (java.lang.String) super.save(tblMarketActReview);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblMarketActReview a transient instance containing new or updated state
	 */
	public void saveOrUpdate(com.huateng.po.mchtSrv.TblMarketActReview tblMarketActReview)
	{
		super.saveOrUpdate(tblMarketActReview);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblMarketActReview a transient instance containing updated state
	 */
	public void update(com.huateng.po.mchtSrv.TblMarketActReview tblMarketActReview)
	{
		super.update(tblMarketActReview);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblMarketActReview the instance to be removed
	 */
	public void delete(com.huateng.po.mchtSrv.TblMarketActReview tblMarketActReview)
	{
		super.delete((Object) tblMarketActReview);
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






}