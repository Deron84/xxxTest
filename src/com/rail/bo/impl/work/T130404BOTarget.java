package com.rail.bo.impl.work;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rail.bo.work.T130404BO;
import com.rail.dao.iface.work.RailEmployeeTypeDao;
import com.rail.po.base.RailEmployeeType;

public class  T130404BOTarget  implements T130404BO{
	private RailEmployeeTypeDao railEmployeeTypeDao;

	@Override
	public List<RailEmployeeType> getByParam(Map<String, String> params) {
		return railEmployeeTypeDao.getByParam(params);
	}
	@Override
	public String add(RailEmployeeType railEmployeeType) {
		return railEmployeeTypeDao.add(railEmployeeType);
	}
	
	@Override
	public String update(RailEmployeeType railEmployeeType) {
		return railEmployeeTypeDao.update(railEmployeeType);
	}
	@Override
	public RailEmployeeType getById(String id) {
		return railEmployeeTypeDao.getById(id);
	}
	
	@Override
	public RailEmployeeType getByCode(String accessWarnId) {
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("ID", accessWarnId);
		List<RailEmployeeType> raws = railEmployeeTypeDao.getByParam(paramMap);
		RailEmployeeType railEmployeeType = new RailEmployeeType();
		if(raws!=null&&!raws.isEmpty()){
			railEmployeeType = raws.get(0);
		}
		return railEmployeeType;
	}

	public RailEmployeeTypeDao getRailEmployeeTypeDao() {
		return railEmployeeTypeDao;
	}

	public void setRailEmployeeTypeDao(RailEmployeeTypeDao railEmployeeTypeDao) {
		this.railEmployeeTypeDao = railEmployeeTypeDao;
	}
	
}
