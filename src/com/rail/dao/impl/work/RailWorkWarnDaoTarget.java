package com.rail.dao.impl.work;

import java.util.List;
import java.util.Map;

import com.huateng.common.Constants;
import com.rail.dao.iface.access.RailAccessWarnDao;
import com.rail.dao.iface.work.RailWorkWarnDao;
import com.rail.po.access.RailAccessWarn;
import com.rail.po.work.RailWorkWarn;
import com.sdses.dao._RootDAO;

public class RailWorkWarnDaoTarget  extends _RootDAO<RailWorkWarn> implements RailWorkWarnDao{
	

	@Override
	public List<RailWorkWarn> getByParam(Map<String, String> paramMap) {
		String querySql = "from RailWorkWarn where 1=1 ";
		for (String key : paramMap.keySet()) {
			String value = paramMap.get(key);
			querySql += " and " + key + "='" + value + "'";
		}
		System.out.println("根据参数查询门禁querySql:" + querySql);
		return getHibernateTemplate().find(querySql + "");
	}

	@Override
	public String add(RailWorkWarn railWorkWarn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateAccess(RailWorkWarn railWorkWarn) {
		// TODO Auto-generated method stub
				try {
					super.update(railWorkWarn);
					return Constants.SUCCESS_CODE;
				} catch (Exception e) {
					return Constants.FAILURE_CODE;
				}
	}

	@Override
	public RailWorkWarn getByCode(String workWarnId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Class getReferenceClass() {
		// TODO Auto-generated method stub
		return null;
	}

}
