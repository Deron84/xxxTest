package com.huateng.bo.impl.accident;

import com.huateng.bo.accident.T90201BO;
import com.huateng.common.Constants;
import com.huateng.common.accident.TblEntrustTradeConstants;
import com.huateng.po.accident.TblEntrustTrade;
import com.huateng.system.util.GenerateNextId;
import com.huateng.dao.iface.accident.TblEntrustTradeDAO;;

public class T90201BOTarget implements T90201BO {

	private TblEntrustTradeDAO tblEntrustTradeDAO;
	
	public String add(TblEntrustTrade t) {

		t.setId(GenerateNextId.getEntrustId());
		tblEntrustTradeDAO.save(t);
		return Constants.SUCCESS_CODE;
	}

	public String update(TblEntrustTrade t) {

		String id = t.getId();
		TblEntrustTrade tblEntrustTrade = get(id);
		
		tblEntrustTrade.setAmtTrans(t.getAmtTrans());
		tblEntrustTrade.setCardAccpId(t.getCardAccpId());
		tblEntrustTrade.setPan(t.getPan());

		tblEntrustTradeDAO.update(tblEntrustTrade);
		
		return Constants.SUCCESS_CODE;
	}

	public String del(String id) {
		
		TblEntrustTrade tblEntrustTrade = get(id);
		tblEntrustTrade.setStatus(TblEntrustTradeConstants.DEL);
		tblEntrustTradeDAO.update(tblEntrustTrade);
		return Constants.SUCCESS_CODE;
	}

	public TblEntrustTrade get(String id) {
		return tblEntrustTradeDAO.get(id);
	}

	public TblEntrustTradeDAO getTblEntrustTradeDAO() {
		return tblEntrustTradeDAO;
	}

	public void setTblEntrustTradeDAO(TblEntrustTradeDAO tblEntrustTradeDAO) {
		this.tblEntrustTradeDAO = tblEntrustTradeDAO;
	}
}
