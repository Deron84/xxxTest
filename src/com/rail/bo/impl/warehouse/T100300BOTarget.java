package com.rail.bo.impl.warehouse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rail.bo.warehouse.T100300BO;
import com.rail.dao.iface.warehouse.RailWhseToolWarnDao;
import com.rail.po.warehouse.RailWhseToolWarn;


public class T100300BOTarget  implements T100300BO{
	private RailWhseToolWarnDao railWhseToolWarnDao;

	@Override
	public List<RailWhseToolWarn> getByParam(Map<String, String> params) {
		return railWhseToolWarnDao.getByParam(params);
	}
	@Override
	public String add(RailWhseToolWarn railWhseToolWarn) {
		return railWhseToolWarnDao.add(railWhseToolWarn);
	}
	
	@Override
	public String update(RailWhseToolWarn railWhseToolWarn) {
		return railWhseToolWarnDao.update(railWhseToolWarn);
	}
	@Override
	public RailWhseToolWarn getById(String id) {
		return railWhseToolWarnDao.getById(id);
	}
	
	@Override
	public RailWhseToolWarn getByCode(String accessWarnId) {
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("ID", accessWarnId);
		List<RailWhseToolWarn> raws = railWhseToolWarnDao.getByParam(paramMap);
		RailWhseToolWarn railWhseToolWarn = new RailWhseToolWarn();
		if(raws!=null&&!raws.isEmpty()){
			railWhseToolWarn = raws.get(0);
		}
		return railWhseToolWarn;
	}

	public RailWhseToolWarnDao getRailWhseToolWarnDao() {
		return railWhseToolWarnDao;
	}

	public void setRailWhseToolWarnDao(RailWhseToolWarnDao railWhseToolWarnDao) {
		this.railWhseToolWarnDao = railWhseToolWarnDao;
	}
	
}
