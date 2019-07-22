package com.huateng.bo.impl.settle;

import java.util.List;

import com.huateng.bo.settle.T80208BO;
import com.huateng.common.Constants;
import com.huateng.dao.iface.settle.TblBrhAcctDAO;
import com.huateng.po.TblBrhAcct;

public class T80208BOTarget implements T80208BO {
	
	private TblBrhAcctDAO tblBrhAcctDAO;

	public TblBrhAcctDAO getTblBrhAcctDAO() {
		return tblBrhAcctDAO;
	}

	public void setTblBrhAcctDAO(TblBrhAcctDAO tblBrhAcctDAO) {
		this.tblBrhAcctDAO = tblBrhAcctDAO;
	}

	public String add(TblBrhAcct tblBrhAcct) {
		
		if(tblBrhAcctDAO.get(tblBrhAcct.getId()) != null) {
			return "该编号已经存在";
		}
		
		tblBrhAcctDAO.save(tblBrhAcct);
		return Constants.SUCCESS_CODE;
	}

	public String delete(TblBrhAcct tblBrhAcct) {
		
		if(tblBrhAcctDAO.get(tblBrhAcct.getId()) == null) {
			return "您所要删除的信息已经不存在";
		}
		
		tblBrhAcctDAO.delete(tblBrhAcct);
		return Constants.SUCCESS_CODE;
	}
	
	public String delete(String key) {
		
		if(tblBrhAcctDAO.get(key) == null) {
			return "您所要删除的信息已经不存在";
		}
		
		tblBrhAcctDAO.delete(key);
		return Constants.SUCCESS_CODE;
	}
	
	public String update(List<TblBrhAcct> list) {
		for(TblBrhAcct tblBrhAcct : list) {
			tblBrhAcctDAO.update(tblBrhAcct);
		}
		return Constants.SUCCESS_CODE;
	}
}
