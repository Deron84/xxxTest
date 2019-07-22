package com.rail.bo.impl.parameter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rail.bo.parameter.T140101BO;
import com.rail.dao.iface.parameter.RailMfrsOrgDao;
import com.rail.po.org.RailMfrsOrg;

public class T140101BOTarget  implements T140101BO{
	private RailMfrsOrgDao railMfrsOrgDao;

	@Override
	public List<RailMfrsOrg> getByParam(Map<String, String> params) {
		return railMfrsOrgDao.getByParam(params);
	}
	@Override
	public String add(RailMfrsOrg railMfrsOrg) {
		return railMfrsOrgDao.add(railMfrsOrg);
	}
	
	@Override
	public String update(RailMfrsOrg railMfrsOrg) {
		return railMfrsOrgDao.update(railMfrsOrg);
	}
	@Override
	public RailMfrsOrg getById(String id) {
		return railMfrsOrgDao.getById(id);
	}
	
	@Override
	public RailMfrsOrg getByCode(String accessWarnId) {
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("ID", accessWarnId);
		List<RailMfrsOrg> raws = railMfrsOrgDao.getByParam(paramMap);
		RailMfrsOrg railMfrsOrg = new RailMfrsOrg();
		if(raws!=null&&!raws.isEmpty()){
			railMfrsOrg = raws.get(0);
		}
		return railMfrsOrg;
	}

	public RailMfrsOrgDao getRailMfrsOrgDao() {
		return railMfrsOrgDao;
	}

	public void setRailMfrsOrgDao(RailMfrsOrgDao railMfrsOrgDao) {
		this.railMfrsOrgDao = railMfrsOrgDao;
	}
	
}
