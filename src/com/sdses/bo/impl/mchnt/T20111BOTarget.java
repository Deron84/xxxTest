package com.sdses.bo.impl.mchnt;

import java.util.List;

import com.sdses.bo.mchnt.T20111BO;
import com.sdses.dao.iface.mchnt.TAirpayMapDAO;
import com.sdses.po.TAirpayMap;
import com.sdses.po.TAirpayMapPK;

public class T20111BOTarget implements T20111BO{

	private TAirpayMapDAO tAirpayMapDAO;
	@Override
	public int addDate(TAirpayMap tAirpayMap) throws Exception {
		// TODO Auto-generated method stub
		return tAirpayMapDAO.addDate(tAirpayMap);
	}
	@Override
	public int deleteDate(TAirpayMapPK tAirpayMapPK) throws Exception {
		// TODO Auto-generated method stub
		return tAirpayMapDAO.deleteDate(tAirpayMapPK);
	}
	
	@Override
	public int isExitRecord(TAirpayMapPK tAirpayMapPK) throws Exception {
		// TODO Auto-generated method stub
		return tAirpayMapDAO.isExitRecord(tAirpayMapPK);
	}
	
	@Override
	public int updateData(TAirpayMap tAirpayMap) throws Exception {
		// TODO Auto-generated method stub
		return tAirpayMapDAO.updateData(tAirpayMap);
	}
	
	public TAirpayMapDAO gettAirpayMapDAO() {
		return tAirpayMapDAO;
	}
	public void settAirpayMapDAO(TAirpayMapDAO tAirpayMapDAO) {
		this.tAirpayMapDAO = tAirpayMapDAO;
	}
	public List<Object[]> isExitAliDate(String appId) throws Exception {
		// TODO Auto-generated method stub
		return tAirpayMapDAO.isExitAliDate(appId);
	}
	@Override
	public List<Object[]> isExitWeDate(String appId,String mchId) throws Exception {
		// TODO Auto-generated method stub
		return tAirpayMapDAO.isExitWeDate(appId, mchId);
	}

}
