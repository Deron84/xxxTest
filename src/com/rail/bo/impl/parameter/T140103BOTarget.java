package com.rail.bo.impl.parameter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rail.bo.parameter.T140103BO;
import com.rail.dao.iface.parameter.RailToolNameDao;
import com.rail.po.tool.RailToolName;

public class T140103BOTarget  implements T140103BO{
	private RailToolNameDao railToolNameDao;

	@Override
	public List<RailToolName> getByParam(Map<String, String> params) {
		return railToolNameDao.getByParam(params);
	}
	@Override
	public String add(RailToolName railToolName) {
		return railToolNameDao.add(railToolName);
	}
	
	@Override
	public String update(RailToolName railToolName) {
		return railToolNameDao.update(railToolName);
	}
	@Override
	public RailToolName getById(String id) {
		return railToolNameDao.getById(id);
	}
	
	@Override
	public RailToolName getByCode(String accessWarnId) {
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("ID", accessWarnId);
		List<RailToolName> raws = railToolNameDao.getByParam(paramMap);
		RailToolName railToolName = new RailToolName();
		if(raws!=null&&!raws.isEmpty()){
			railToolName = raws.get(0);
		}
		return railToolName;
	}

	public RailToolNameDao getRailToolNameDao() {
		return railToolNameDao;
	}

	public void setRailToolNameDao(RailToolNameDao railToolNameDao) {
		this.railToolNameDao = railToolNameDao;
	}
	
}
