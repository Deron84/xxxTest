package com.rail.bo.impl.access;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rail.bo.access.T120300BO;
import com.rail.dao.iface.access.RailAccessWarnDao;
import com.rail.po.access.RailAccessWarn;

public class T120300BOTarget  implements T120300BO{
	private RailAccessWarnDao railAccessWarnDao;
	
	public RailAccessWarnDao getRailAccessWarnDao() {
		return railAccessWarnDao;
	}

	public void setRailAccessWarnDao(RailAccessWarnDao railAccessWarnDao) {
		this.railAccessWarnDao = railAccessWarnDao;
	}

	@Override
	public String updateAccess(RailAccessWarn railAccessWarn) {
		// TODO Auto-generated method stub
		return railAccessWarnDao.updateAccess(railAccessWarn);
	}

	@Override
	public RailAccessWarn getByCode(String accessWarnId) {
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("ID", accessWarnId);
		List<RailAccessWarn> raws = railAccessWarnDao.getByParam(paramMap);
		RailAccessWarn railAccessWarn = new RailAccessWarn();
		if(raws!=null&&!raws.isEmpty()){
			railAccessWarn = raws.get(0);
		}
		System.out.println("qiufulong======="+railAccessWarn);
		return railAccessWarn;
	}

	@Override
	public List<RailAccessWarn> getByParam(Map<String, String> paramMap) {
		// TODO Auto-generated method stub
		return null;
	}

}
