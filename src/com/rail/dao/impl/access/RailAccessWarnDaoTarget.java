package com.rail.dao.impl.access;

import java.util.List;
import java.util.Map;

import com.huateng.common.Constants;
import com.rail.dao.iface.access.RailAccessWarnDao;
import com.rail.po.access.RailAccessWarn;
import com.sdses.dao._RootDAO;

public class RailAccessWarnDaoTarget  extends _RootDAO<RailAccessWarn> implements RailAccessWarnDao{
	

	@Override
	public List<RailAccessWarn> getByParam(Map<String, String> paramMap) {
		// TODO Auto-generated method stub
		String querySql = "from RailAccessWarn where 1=1 ";
		for (String key : paramMap.keySet()) {
			String value = paramMap.get(key);
			querySql += " and " + key + "='" + value + "'";
		}
		System.out.println("根据参数查询门禁querySql:" + querySql);
		return getHibernateTemplate().find(querySql + "");
	}

	@Override
	public String add(RailAccessWarn railAccessWarn) {
		// TODO Auto-generated method stub
		try {
			super.saveOrUpdate(railAccessWarn);
			return Constants.SUCCESS_CODE;
		} catch (Exception e) {
			return Constants.FAILURE_CODE;
		}
	}

	@Override
	public String updateAccess(RailAccessWarn railAccessWarn) {
		// TODO Auto-generated method stub
				try {
					super.update(railAccessWarn);
					return Constants.SUCCESS_CODE;
				} catch (Exception e) {
					return Constants.FAILURE_CODE;
				}
	}

	@Override
	public RailAccessWarn getByCode(String accessWarnId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Class getReferenceClass() {
		// TODO Auto-generated method stub
		return null;
	}

}
