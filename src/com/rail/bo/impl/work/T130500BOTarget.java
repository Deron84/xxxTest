package com.rail.bo.impl.work;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rail.bo.work.T130500BO;
import com.rail.dao.iface.work.RailWorkWarnDao;
import com.rail.po.work.RailWorkWarn;

public class T130500BOTarget  implements T130500BO{
	private RailWorkWarnDao railWorkWarnDao;
	
	public RailWorkWarnDao getRailWorkWarnDao() {
		return railWorkWarnDao;
	}

	public void setRailWorkWarnDao(RailWorkWarnDao railWorkWarnDao) {
		this.railWorkWarnDao = railWorkWarnDao;
	}

	@Override
	public String updateAccess(RailWorkWarn railWorkWarn) {
		// TODO Auto-generated method stub
		return railWorkWarnDao.updateAccess(railWorkWarn);
	}

	@Override
	public RailWorkWarn getByCode(String workWarnId) {
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("ID", workWarnId);
		List<RailWorkWarn> raws = railWorkWarnDao.getByParam(paramMap);
		RailWorkWarn railWorkWarn = new RailWorkWarn();
		if(raws!=null&&!raws.isEmpty()){
			railWorkWarn = raws.get(0);
		}
		System.out.println("qiufulong======="+railWorkWarn);
		return railWorkWarn;
	}

	@Override
	public List<RailWorkWarn> getByParam(Map<String, String> paramMap) {
		// TODO Auto-generated method stub
		return null;
	}

}
