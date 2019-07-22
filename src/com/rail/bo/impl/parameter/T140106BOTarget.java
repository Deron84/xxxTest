package com.rail.bo.impl.parameter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rail.bo.parameter.T140106BO;
import com.rail.dao.iface.parameter.RailToolModelDao;
import com.rail.po.tool.RailToolModel;

public class T140106BOTarget  implements T140106BO{
	private RailToolModelDao railToolModelDao;

	@Override
	public List<RailToolModel> getByParam(Map<String, String> params) {
		return railToolModelDao.getByParam(params);
	}
	@Override
	public String add(RailToolModel railToolModel) {
		return railToolModelDao.add(railToolModel);
	}
	
	@Override
	public String update(RailToolModel railToolModel) {
		return railToolModelDao.update(railToolModel);
	}
	@Override
	public RailToolModel getById(String id) {
		return railToolModelDao.getById(id);
	}
	
	@Override
	public RailToolModel getByCode(String accessWarnId) {
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("ID", accessWarnId);
		List<RailToolModel> raws = railToolModelDao.getByParam(paramMap);
		RailToolModel railToolModel = new RailToolModel();
		if(raws!=null&&!raws.isEmpty()){
			railToolModel = raws.get(0);
		}
		return railToolModel;
	}

	public RailToolModelDao getRailToolModelDao() {
		return railToolModelDao;
	}

	public void setRailToolModelDao(RailToolModelDao railToolModelDao) {
		this.railToolModelDao = railToolModelDao;
	}
	
}
