package com.huateng.dao.impl.mchnt;

import java.util.List;

import com.huateng.dao.iface.mchnt.TblMchntApplyDAO;
import com.huateng.po.mchnt.TblMchntApply;
import com.sdses.dao._RootDAO;

public class TblMchntApplyTmpDAO extends _RootDAO<TblMchntApply> implements TblMchntApplyDAO{

	public TblMchntApplyTmpDAO(){}
	
	@Override
	public TblMchntApply get(String key) {
		
		return (TblMchntApply) get(getReferenceClass(), key);
	}

	@Override
	public TblMchntApply load(String key) {
		return (TblMchntApply) load(getReferenceClass(), key);
	}

	@Override
	public List<TblMchntApply> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String save(TblMchntApply tblDivMchntTmp) {
		return (String) super.save(tblDivMchntTmp);
	}

	@Override
	public void saveOrUpdate(TblMchntApply tblDivMchntTmp) {
		super.saveOrUpdate(tblDivMchntTmp);
		
	}

	@Override
	public void update(TblMchntApply tblDivMchntTmp) {
		super.update(tblDivMchntTmp);
		
	}

	@Override
	public void delete(String id) {
		super.delete((Object) load(id));
		
	}

	@Override
	public void delete(TblMchntApply tblDivMchntTmp) {
		super.delete((Object) tblDivMchntTmp);
		
	}

	@Override
	protected Class getReferenceClass() {
		return TblMchntApply.class;
	}

}
