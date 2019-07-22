package com.rail.bo.impl.access;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rail.bo.access.T120402BO;
import com.rail.dao.iface.access.RailAccessMaintainDao;
import com.rail.po.access.RailAccessInfo;
import com.rail.po.access.RailAccessMaintain;

public class T120402BOTarget  implements T120402BO{

private RailAccessMaintainDao railAccessMaintainDao;
	

	public RailAccessMaintainDao getRailAccessMaintainDao() {
	return railAccessMaintainDao;
}

public void setRailAccessMaintainDao(RailAccessMaintainDao railAccessMaintainDao) {
	this.railAccessMaintainDao = railAccessMaintainDao;
}

	@Override
	public String updateAccess(RailAccessMaintain railAccessMaintain) {
		// TODO Auto-generated method stub
		return railAccessMaintainDao.updateAccess(railAccessMaintain);
	}

	@Override
	public RailAccessMaintain getByCode(String accessWarnId) {
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("ID", accessWarnId);
		List<RailAccessMaintain> raws = railAccessMaintainDao.getByParam(paramMap);
		RailAccessMaintain railAccessMaintain = new RailAccessMaintain();
		if(raws!=null&&!raws.isEmpty()){
			railAccessMaintain = raws.get(0);
		}
		System.out.println("qiufulong======="+railAccessMaintain);
		return railAccessMaintain;
	}

	@Override
	public List<RailAccessMaintain> getByParam(Map<String, String> paramMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String add(RailAccessMaintain railAccessMaintain) {
		// TODO Auto-generated method stub
		return railAccessMaintainDao.add(railAccessMaintain);
	}

}
