package com.huateng.bo.impl.mchnt;

import com.huateng.bo.mchnt.T20201BO;
import com.huateng.dao.iface.mchnt.TblMchntInfoDAO;
import com.huateng.po.mchnt.TblMchtBaseInf;

public class T20201BOTarget implements T20201BO {
	
	private TblMchntInfoDAO tblMchntInfoDAO;

	public void setTblMchntInfoDAO(TblMchntInfoDAO tblMchntInfoDAO) {
		this.tblMchntInfoDAO = tblMchntInfoDAO;
	}
	
	public TblMchtBaseInf get(String key) {
		return tblMchntInfoDAO.get(key);
	}
}
