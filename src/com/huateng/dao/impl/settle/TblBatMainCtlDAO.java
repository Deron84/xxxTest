package com.huateng.dao.impl.settle;

import java.util.List;

import org.hibernate.criterion.Order;

import com.huateng.po.settle.TblBatMainCtl;
import com.sdses.dao._RootDAO;


public class TblBatMainCtlDAO extends _RootDAO implements com.huateng.dao.iface.TblBatMainCtlDAO {

	public TblBatMainCtlDAO () {}

	public Class getReferenceClass () {
		return com.huateng.po.settle.TblBatMainCtl.class;
	}

    public Order getDefaultOrder () {
		return null;
    }

	public com.huateng.po.settle.TblBatMainCtl cast (Object object) {
		return (com.huateng.po.settle.TblBatMainCtl) object;
	}

	public com.huateng.po.settle.TblBatMainCtl get(java.lang.String key)
	{
		return (com.huateng.po.settle.TblBatMainCtl) get(getReferenceClass(), key);
	}

	public com.huateng.po.settle.TblBatMainCtl load(java.lang.String key)
	{
		return (com.huateng.po.settle.TblBatMainCtl) load(getReferenceClass(), key);
	}

	public java.lang.String save(com.huateng.po.settle.TblBatMainCtl tblBatMainCtl)
	{
		return (java.lang.String) super.save(tblBatMainCtl);
	}

	public void saveOrUpdate(com.huateng.po.settle.TblBatMainCtl tblBatMainCtl)
	{
		saveOrUpdate((Object) tblBatMainCtl);
	}

	public void update(com.huateng.po.settle.TblBatMainCtl tblBatMainCtl) 
	{
		update((Object) tblBatMainCtl);
	}

	public void delete(java.lang.String id)
	{
		delete((Object) load(id));
	}

	public void delete(com.huateng.po.settle.TblBatMainCtl tblBatMainCtl)
	{
		delete((Object) tblBatMainCtl);
	}
	
	public List<TblBatMainCtl> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}