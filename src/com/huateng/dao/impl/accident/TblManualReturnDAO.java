package com.huateng.dao.impl.accident;

import com.huateng.po.accident.TblManualReturn;
import com.sdses.dao._RootDAO;

public class TblManualReturnDAO extends _RootDAO<com.huateng.po.accident.TblManualReturn> implements com.huateng.dao.iface.accident.TblManualReturnDAO {

	public void save(TblManualReturn tblManualReturn) {

		super.save(tblManualReturn);
	}

	public void update(TblManualReturn tblManualReturn) {
		
		super.update(tblManualReturn);
	}

	public void delete(TblManualReturn tblManualReturn) {
		super.delete(tblManualReturn);
	}

	protected Class getReferenceClass() {
		
		return com.huateng.po.accident.TblManualReturn.class;
	}

	public com.huateng.po.accident.TblManualReturn get(java.lang.String key) {
		
		return (com.huateng.po.accident.TblManualReturn) get(getReferenceClass(), key);
	}
}