package com.rail.bo.impl.parameter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rail.bo.parameter.T140102BO;
import com.rail.dao.iface.parameter.RailAreaDao;
import com.rail.po.base.RailArea;

public class T140102BOTarget  implements T140102BO{
	private RailAreaDao railAreaDao;

	@Override
	public List<RailArea> getByParam(Map<String, String> params) {
		return railAreaDao.getByParam(params);
	}
	@Override
	public String add(RailArea railArea) {
		return railAreaDao.add(railArea);
	}
	
	@Override
	public String update(RailArea railArea) {
		return railAreaDao.update(railArea);
	}
	@Override
	public RailArea getById(String id) {
		return railAreaDao.getById(id);
	}
	
	@Override
	public RailArea getByCode(String accessWarnId) {
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("ID", accessWarnId);
		List<RailArea> raws = railAreaDao.getByParam(paramMap);
		RailArea railArea = new RailArea();
		if(raws!=null&&!raws.isEmpty()){
			railArea = raws.get(0);
		}
		return railArea;
	}

	public RailAreaDao getRailAreaDao() {
		return railAreaDao;
	}

	public void setRailAreaDao(RailAreaDao railAreaDao) {
		this.railAreaDao = railAreaDao;
	}
	
}
