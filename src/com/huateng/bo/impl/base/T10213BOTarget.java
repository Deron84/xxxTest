package com.huateng.bo.impl.base;

import com.huateng.bo.base.T10213BO;
import com.huateng.po.TTermKey;
import com.sdses.dao.iface.pos.TTermKeyDAO;

public class T10213BOTarget implements T10213BO{
	
	private TTermKeyDAO tTermKeyDAO;

	@Override
	public int queryForInt(TTermKey tTermKey) throws Exception {
		// TODO Auto-generated method stub
		return tTermKeyDAO.queryForInt(tTermKey);
	}

	@Override
	public int addDate(TTermKey tTermKey) throws Exception {
		// TODO Auto-generated method stub
		return tTermKeyDAO.addDate(tTermKey);
	}

	@Override
	public String queryForKey() throws Exception {
		// TODO Auto-generated method stub
		return tTermKeyDAO.queryForKey();
	}

	public TTermKeyDAO gettTermKeyDAO() {
		return tTermKeyDAO;
	}

	public void settTermKeyDAO(TTermKeyDAO tTermKeyDAO) {
		this.tTermKeyDAO = tTermKeyDAO;
	}
}
