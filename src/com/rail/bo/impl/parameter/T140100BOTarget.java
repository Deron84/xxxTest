package com.rail.bo.impl.parameter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rail.bo.parameter.T140100BO;
import com.rail.dao.iface.parameter.RailConstOrgDao;
import com.rail.po.access.RailAccessWarn;
import com.rail.po.org.RailConstOrg;

public class T140100BOTarget implements T140100BO {

	private RailConstOrgDao railConstOrgDao;

	@Override
	public List<RailConstOrg> getByParam(Map<String, String> params) {
		return railConstOrgDao.getByParam(params);
	}
	@Override
	public String add(RailConstOrg railConstOrg) {
		return railConstOrgDao.add(railConstOrg);
	}
	
	@Override
	public String update(RailConstOrg railConstOrg) {
		return railConstOrgDao.update(railConstOrg);
	}
	@Override
	public RailConstOrg getById(String id) {
		return railConstOrgDao.getById(id);
	}
	
	@Override
	public RailConstOrg getByCode(String accessWarnId) {
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("ID", accessWarnId);
		List<RailConstOrg> raws = railConstOrgDao.getByParam(paramMap);
		RailConstOrg railConstOrg = new RailConstOrg();
		if(raws!=null&&!raws.isEmpty()){
			railConstOrg = raws.get(0);
		}
		return railConstOrg;
	}

	public RailConstOrgDao getRailConstOrgDao() {
		return railConstOrgDao;
	}

	public void setRailConstOrgDao(RailConstOrgDao railConstOrgDao) {
		this.railConstOrgDao = railConstOrgDao;
	}
	
}
