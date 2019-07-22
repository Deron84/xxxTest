package com.rail.dao.impl.access;

import java.util.List;
import java.util.Map;

import com.huateng.common.Constants;
import com.rail.dao.iface.access.RailAccessMaintainWarnDao;
import com.rail.dao.iface.access.RailAccessWarnDao;
import com.rail.po.access.RailAccessMaintainWarn;
import com.sdses.dao._RootDAO;

public class RailAccessMaintainWarnDaoTarget  extends _RootDAO<RailAccessMaintainWarn> implements RailAccessMaintainWarnDao{

	@Override
	public List<RailAccessMaintainWarn> getByParam(Map<String, String> paramMap) {
		// TODO Auto-generated method stub
				String querySql = "from RailAccessMaintainWarn where 1=1 ";
				for (String key : paramMap.keySet()) {
					String value = paramMap.get(key);
					querySql += " and " + key + "='" + value + "'";
				}
				System.out.println("根据参数查询门禁querySql:" + querySql);
				return getHibernateTemplate().find(querySql + "");
	}

	@Override
	public String add(RailAccessMaintainWarn railAccessMaintainWarn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateAccess(RailAccessMaintainWarn railAccessMaintainWarn) {
		// TODO Auto-generated method stub
		try {
			super.update(railAccessMaintainWarn);
			return Constants.SUCCESS_CODE;
		} catch (Exception e) {
			return Constants.FAILURE_CODE;
		}
	}

	@Override
	public RailAccessMaintainWarn getByCode(String accessWarnId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Class getReferenceClass() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
