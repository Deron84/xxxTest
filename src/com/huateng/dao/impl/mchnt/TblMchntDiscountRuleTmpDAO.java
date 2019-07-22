package com.huateng.dao.impl.mchnt;

import java.util.List;

import com.huateng.dao.iface.mchnt.TblMchntDiscountRuleDAO;
import com.huateng.po.mchnt.TblMchntDiscountRule;
import com.sdses.dao._RootDAO;

public class TblMchntDiscountRuleTmpDAO extends _RootDAO<TblMchntDiscountRule> implements TblMchntDiscountRuleDAO{

	public TblMchntDiscountRuleTmpDAO(){}
	
	@Override
	public TblMchntDiscountRule get(String key) {
		return (TblMchntDiscountRule) get(getReferenceClass(), key);
	}

	@Override
	public TblMchntDiscountRule load(String key) {
		return (TblMchntDiscountRule) load(getReferenceClass(), key);
	}

	@Override
	public List<TblMchntDiscountRule> findAll() {
		return null;
	}

	@Override
	public String save(TblMchntDiscountRule tblDivMchntTmp) {
		return (String) super.save(tblDivMchntTmp);
	}

	@Override
	public void saveOrUpdate(TblMchntDiscountRule tblDivMchntTmp) {
		super.saveOrUpdate(tblDivMchntTmp);
	}

	@Override
	public void update(TblMchntDiscountRule tblDivMchntTmp) {
		super.update(tblDivMchntTmp);
	}

	@Override
	public void delete(String id) {
		super.delete((Object) load(id));
	}

	@Override
	public void delete(TblMchntDiscountRule tblDivMchntTmp) {
		super.delete((Object) tblDivMchntTmp);
	}

	@Override
	protected Class getReferenceClass() {
		return TblMchntDiscountRule.class;
	}

}
