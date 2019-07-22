package com.huateng.dao.impl.mchnt;

import java.util.List;

import com.huateng.dao.iface.mchnt.TblMchntDiscountRuleBindDAO;
import com.huateng.po.mchnt.TblMchntDiscountRuleBind;
import com.huateng.po.mchnt.TblMchntDiscountRuleBindPK;
import com.sdses.dao._RootDAO;

public class TblMchntDiscountRuleBindTmpDAO extends _RootDAO<TblMchntDiscountRuleBind> implements TblMchntDiscountRuleBindDAO{

	public TblMchntDiscountRuleBindTmpDAO(){}
	
	@Override
	public TblMchntDiscountRuleBind get(TblMchntDiscountRuleBindPK key) {
		return (TblMchntDiscountRuleBind) get(getReferenceClass(), key);
	}

	@Override
	public TblMchntDiscountRuleBind load(String key) {
		return (TblMchntDiscountRuleBind) load(getReferenceClass(), key);
	}

	@Override
	public List<TblMchntDiscountRuleBind> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TblMchntDiscountRuleBindPK save(TblMchntDiscountRuleBind tblDivMchntTmp) {
		return (TblMchntDiscountRuleBindPK) super.save(tblDivMchntTmp);
	}

	@Override
	public void saveOrUpdate(TblMchntDiscountRuleBind tblDivMchntTmp) {
		super.saveOrUpdate(tblDivMchntTmp);
	}

	@Override
	public void update(TblMchntDiscountRuleBind tblDivMchntTmp) {
		super.update(tblDivMchntTmp);
	}

	@Override
	public void delete(String id) {
		super.delete((Object) load(id));
	}

	@Override
	public void delete(TblMchntDiscountRuleBind tblDivMchntTmp) {
		super.delete((Object) tblDivMchntTmp);
	}

	@Override
	protected Class getReferenceClass() {
		return TblMchntDiscountRuleBind.class;
	}

}
