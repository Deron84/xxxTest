package com.rail.bo.impl.work;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rail.bo.work.T130301BO;
import com.rail.dao.iface.work.RailAccessEmployeeDao;
import com.rail.po.access.RailAccessEmployee;

public class T130301BOTarget  implements T130301BO{

private RailAccessEmployeeDao railAccessEmployeeDao;
	

	public RailAccessEmployeeDao getRailAccessEmployeeDao() {
	return railAccessEmployeeDao;
}

public void setRailAccessEmployeeDao(RailAccessEmployeeDao railAccessEmployeeDao) {
	this.railAccessEmployeeDao = railAccessEmployeeDao;
}

	@Override
	public String updateAccess(RailAccessEmployee railAccessEmployee) {
		// TODO Auto-generated method stub
		return railAccessEmployeeDao.updateAccess(railAccessEmployee);
	}

	@Override
	public RailAccessEmployee getByCode(String railAccessEmployeeId) {
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("ID", railAccessEmployeeId);
		List<RailAccessEmployee> raws = railAccessEmployeeDao.getByParam(paramMap);
		RailAccessEmployee railAccessEmployee = new RailAccessEmployee();
		if(raws!=null&&!raws.isEmpty()){
			railAccessEmployee = raws.get(0);
		}
		System.out.println("qiufulong======="+railAccessEmployee);
		return railAccessEmployee;
	}

	@Override
	public List<RailAccessEmployee> getByParam(Map<String, String> paramMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String add(RailAccessEmployee railAccessEmployee) {
		// TODO Auto-generated method stub
		return railAccessEmployeeDao.add(railAccessEmployee);
	}

}

