package com.huateng.dao.impl.mchnt;

import java.util.List;

import com.huateng.dao.iface.mchnt.TblMchntUserDAO;
import com.huateng.po.mchnt.TblMchntUser;
import com.sdses.dao._RootDAO;

public class TblMchntUserTmpDAO extends _RootDAO<TblMchntUser> implements TblMchntUserDAO{

	public TblMchntUserTmpDAO(){}
	
	@Override
	public TblMchntUser get(String key) {
		
		return (TblMchntUser) get(getReferenceClass(), key);
	}

	@Override
	public TblMchntUser load(String key) {
		return (TblMchntUser) load(getReferenceClass(), key);
	}

	@Override
	public List<TblMchntUser> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String save(TblMchntUser tblDivMchntTmp) {
		return (String) super.save(tblDivMchntTmp);
	}

	@Override
	public void saveOrUpdate(TblMchntUser tblDivMchntTmp) {
		super.saveOrUpdate(tblDivMchntTmp);
		
	}

	@Override
	public void update(TblMchntUser tblDivMchntTmp) {
		super.update(tblDivMchntTmp);
		
	}

	@Override
	public void delete(String id) {
		super.delete((Object) load(id));
		
	}

	@Override
	public void delete(TblMchntUser tblDivMchntTmp) {
		super.delete((Object) tblDivMchntTmp);
		
	}

	@Override
	protected Class getReferenceClass() {
		return TblMchntUser.class;
	}

}
