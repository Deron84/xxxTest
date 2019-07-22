package com.rail.bo.impl.parameter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rail.bo.parameter.T140104BO;
import com.rail.dao.iface.parameter.RailToolTypeDao;
import com.rail.po.tool.RailToolType;

public class T140104BOTarget  implements T140104BO{
	private RailToolTypeDao railToolTypeDao;

	@Override
	public List<RailToolType> getByParam(Map<String, String> params) {
		return railToolTypeDao.getByParam(params);
	}
	@Override
	public String add(RailToolType railToolType) {
		return railToolTypeDao.add(railToolType);
	}
	
	@Override
	public String update(RailToolType railToolType) {
		return railToolTypeDao.update(railToolType);
	}
	@Override
	public RailToolType getById(String id) {
		return railToolTypeDao.getById(id);
	}
	
	@Override
	public RailToolType getByCode(String accessWarnId) {
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("ID", accessWarnId);
		List<RailToolType> raws = railToolTypeDao.getByParam(paramMap);
		RailToolType railToolType = new RailToolType();
		if(raws!=null&&!raws.isEmpty()){
			railToolType = raws.get(0);
		}
		return railToolType;
	}

	public RailToolTypeDao getRailToolTypeDao() {
		return railToolTypeDao;
	}

	public void setRailToolTypeDao(RailToolTypeDao railToolTypeDao) {
		this.railToolTypeDao = railToolTypeDao;
	}
	
}
