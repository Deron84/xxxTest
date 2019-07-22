package com.rail.bo.impl.parameter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rail.bo.parameter.T140105BO;
import com.rail.dao.iface.parameter.RailToolUnitDao;
import com.rail.po.tool.RailToolUnit;

public class T140105BOTarget  implements T140105BO{
	private RailToolUnitDao railToolUnitDao;

	@Override
	public List<RailToolUnit> getByParam(Map<String, String> params) {
		return railToolUnitDao.getByParam(params);
	}
	@Override
	public String add(RailToolUnit railToolUnit) {
		return railToolUnitDao.add(railToolUnit);
	}
	
	@Override
	public String update(RailToolUnit railToolUnit) {
		return railToolUnitDao.update(railToolUnit);
	}
	@Override
	public RailToolUnit getById(String id) {
		return railToolUnitDao.getById(id);
	}
	
	@Override
	public RailToolUnit getByCode(String accessWarnId) {
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("ID", accessWarnId);
		List<RailToolUnit> raws = railToolUnitDao.getByParam(paramMap);
		RailToolUnit railToolUnit = new RailToolUnit();
		if(raws!=null&&!raws.isEmpty()){
			railToolUnit = raws.get(0);
		}
		return railToolUnit;
	}

	public RailToolUnitDao getRailToolUnitDao() {
		return railToolUnitDao;
	}

	public void setRailToolUnitDao(RailToolUnitDao railToolUnitDao) {
		this.railToolUnitDao = railToolUnitDao;
	}
	
}
