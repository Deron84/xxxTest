package com.rail.bo.impl.parameter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rail.bo.parameter.T140107BO;
import com.rail.dao.iface.parameter.RailAccessEquipTypeDao;
import com.rail.po.access.RailAccessEquipType;

public class T140107BOTarget  implements T140107BO{
	private RailAccessEquipTypeDao railAccessEquipTypeDao;

	@Override
	public List<RailAccessEquipType> getByParam(Map<String, String> params) {
		return railAccessEquipTypeDao.getByParam(params);
	}
	@Override
	public String add(RailAccessEquipType railAccessEquipType) {
		return railAccessEquipTypeDao.add(railAccessEquipType);
	}
	
	@Override
	public String update(RailAccessEquipType railAccessEquipType) {
		return railAccessEquipTypeDao.update(railAccessEquipType);
	}
	@Override
	public RailAccessEquipType getById(String id) {
		return railAccessEquipTypeDao.getById(id);
	}
	
	@Override
	public RailAccessEquipType getByCode(String accessWarnId) {
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("ID", accessWarnId);
		List<RailAccessEquipType> raws = railAccessEquipTypeDao.getByParam(paramMap);
		RailAccessEquipType railAccessEquipType = new RailAccessEquipType();
		if(raws!=null&&!raws.isEmpty()){
			railAccessEquipType = raws.get(0);
		}
		return railAccessEquipType;
	}

	public RailAccessEquipTypeDao getRailAccessEquipTypeDao() {
		return railAccessEquipTypeDao;
	}

	public void setRailAccessEquipTypeDao(RailAccessEquipTypeDao railAccessEquipTypeDao) {
		this.railAccessEquipTypeDao = railAccessEquipTypeDao;
	}
	
}
