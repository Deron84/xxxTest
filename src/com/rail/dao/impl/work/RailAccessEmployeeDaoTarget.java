package com.rail.dao.impl.work;

import java.util.List;
import java.util.Map;

import com.huateng.common.Constants;
import com.rail.dao.iface.work.RailAccessEmployeeDao;
import com.rail.po.access.RailAccessEmployee;
import com.sdses.dao._RootDAO;

public class RailAccessEmployeeDaoTarget  extends _RootDAO<RailAccessEmployee> implements RailAccessEmployeeDao{

	@Override
	public List<RailAccessEmployee> getByParam(Map<String, String> paramMap) {
		// TODO Auto-generated method stub
				String querySql = "from RailAccessEmployee where 1=1 ";
				for (String key : paramMap.keySet()) {
					String value = paramMap.get(key);
					querySql += " and " + key + "='" + value + "'";
				}
				System.out.println("根据参数查询门禁querySql:" + querySql);
				return getHibernateTemplate().find(querySql + "");
	}

	@Override
	public String add(RailAccessEmployee railAccessEmployee) {
		// TODO Auto-generated method stub
		try {
			super.save(railAccessEmployee);
			return Constants.SUCCESS_CODE;
		} catch (Exception e) {
			return Constants.FAILURE_CODE;
		}
	}

	@Override
	public String updateAccess(RailAccessEmployee railAccessEmployee) {
		// TODO Auto-generated method stub
		try {
			super.update(railAccessEmployee);
			return Constants.SUCCESS_CODE;
		} catch (Exception e) {
			return Constants.FAILURE_CODE;
		}
	}

	@Override
	public RailAccessEmployee getByCode(String railAccessEmployeeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Class getReferenceClass() {
		// TODO Auto-generated method stub
		return null;
	}

	
}

