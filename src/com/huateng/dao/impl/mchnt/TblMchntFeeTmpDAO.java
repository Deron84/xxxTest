package com.huateng.dao.impl.mchnt;

import java.util.List;

import com.huateng.dao.iface.mchnt.TblMchntFeeDAO;
import com.huateng.po.mchnt.TblMchntFee;
import com.sdses.dao._RootDAO;

public class TblMchntFeeTmpDAO extends _RootDAO<TblMchntFee> implements TblMchntFeeDAO{

	public TblMchntFeeTmpDAO(){}
	
	@Override
	public TblMchntFee get(String key) {
		return (TblMchntFee) get(getReferenceClass(), key);
	}

	@Override
	public TblMchntFee load(String key) {
		return (TblMchntFee) load(getReferenceClass(), key);
	}

	@Override
	public List<TblMchntFee> findAll() {
		return null;
	}

	@Override
	public String save(TblMchntFee tblDivMchntTmp) {
		return (String) super.save(tblDivMchntTmp);
	}

	@Override
	public void saveOrUpdate(TblMchntFee tblDivMchntTmp) {
		super.saveOrUpdate(tblDivMchntTmp);
	}

	@Override
	public void update(TblMchntFee tblDivMchntTmp) {
		super.update(tblDivMchntTmp);
	}

	@Override
	public void delete(String id) {
		super.delete((Object) load(id));
	}

	@Override
	public void delete(TblMchntFee tblDivMchntTmp) {
		super.delete((Object) tblDivMchntTmp);
	}

	@Override
	protected Class getReferenceClass() {
		return TblMchntFee.class;
	}

}
