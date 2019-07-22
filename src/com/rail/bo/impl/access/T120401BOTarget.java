package com.rail.bo.impl.access;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rail.bo.access.T120401BO;
import com.rail.dao.iface.access.RailAccessMaintainWarnDao;
import com.rail.po.access.RailAccessMaintainWarn;



public class T120401BOTarget  implements T120401BO{

private RailAccessMaintainWarnDao railAccessMaintainWarnDao;
	

	public RailAccessMaintainWarnDao getRailAccessMaintainWarnDao() {
	return railAccessMaintainWarnDao;
}

public void setRailAccessMaintainWarnDao(RailAccessMaintainWarnDao railAccessMaintainWarnDao) {
	this.railAccessMaintainWarnDao = railAccessMaintainWarnDao;
}

	@Override
	public String updateAccess(RailAccessMaintainWarn railAccessMaintainWarn) {
		// TODO Auto-generated method stub
		return railAccessMaintainWarnDao.updateAccess(railAccessMaintainWarn);
	}

	@Override
	public RailAccessMaintainWarn getByCode(String accessWarnId) {
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("ID", accessWarnId);
		List<RailAccessMaintainWarn> raws = railAccessMaintainWarnDao.getByParam(paramMap);
		RailAccessMaintainWarn railAccessMaintainWarn = new RailAccessMaintainWarn();
		if(raws!=null&&!raws.isEmpty()){
			railAccessMaintainWarn = raws.get(0);
		}
		System.out.println("qiufulong======="+railAccessMaintainWarn);
		return railAccessMaintainWarn;
	}

	@Override
	public List<RailAccessMaintainWarn> getByParam(Map<String, String> paramMap) {
		// TODO Auto-generated method stub
		return null;
	}

}
