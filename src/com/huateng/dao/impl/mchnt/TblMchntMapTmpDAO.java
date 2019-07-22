package com.huateng.dao.impl.mchnt;

import java.util.List;

import com.huateng.dao.iface.mchnt.TblMchntMapDAO;
import com.huateng.po.mchnt.TblMchntMap;
import com.huateng.po.mchnt.TblMchntMapPK;
import com.sdses.dao._RootDAO;

public class TblMchntMapTmpDAO extends _RootDAO<TblMchntMap> implements TblMchntMapDAO{

	public TblMchntMapTmpDAO() {}
	
	@Override
	public TblMchntMap get(TblMchntMapPK key) {
		return (TblMchntMap) get(getReferenceClass(), key);
	}

	@Override
	public TblMchntMap load(TblMchntMapPK key) {
		return (TblMchntMap) load(getReferenceClass(), key);
	}

	@Override
	public List<TblMchntMap> findAll() {
		return null;
	}

	@Override
	public TblMchntMapPK save(TblMchntMap tblDivMchntTmp) {
		return (TblMchntMapPK) super.save(tblDivMchntTmp);
	}

	@Override
	public void saveOrUpdate(TblMchntMap tblDivMchntTmp) {
		super.saveOrUpdate(tblDivMchntTmp);
	}

	@Override
	public void update(TblMchntMap tblDivMchntTmp) {
		super.update(tblDivMchntTmp);
	}

	@Override
	public void delete(TblMchntMapPK id) {
		 super.delete((Object) load(id));
	}

	@Override
	public void delete(TblMchntMap tblDivMchntTmp) {
		super.delete((Object) tblDivMchntTmp);
	}

	@Override
	protected Class getReferenceClass() {
		return TblMchntMap.class;
	}

}
