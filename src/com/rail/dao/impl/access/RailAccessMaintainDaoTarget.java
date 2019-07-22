package com.rail.dao.impl.access;

import java.util.List;
import java.util.Map;

import com.huateng.common.Constants;
import com.rail.dao.iface.access.RailAccessMaintainDao;
import com.rail.dao.iface.access.RailAccessWarnDao;
import com.rail.po.access.RailAccessMaintain;
import com.sdses.dao._RootDAO;

public class RailAccessMaintainDaoTarget  extends _RootDAO<RailAccessMaintain> implements RailAccessMaintainDao{

	@Override
	public List<RailAccessMaintain> getByParam(Map<String, String> paramMap) {
		// TODO Auto-generated method stub
				String querySql = "from RailAccessMaintain where 1=1 ";
				for (String key : paramMap.keySet()) {
					String value = paramMap.get(key);
					querySql += " and " + key + "='" + value + "'";
				}
				System.out.println("根据参数查询门禁querySql:" + querySql);
				return getHibernateTemplate().find(querySql + "");
	}

	@Override
	public String add(RailAccessMaintain railAccessMaintain) {
		// TODO Auto-generated method stub
		try {
			super.save(railAccessMaintain);
			return Constants.SUCCESS_CODE;
		} catch (Exception e) {
			return Constants.FAILURE_CODE;
		}
	}

	@Override
	public String updateAccess(RailAccessMaintain railAccessMaintain) {
		// TODO Auto-generated method stub
		try {
			super.update(railAccessMaintain);
			return Constants.SUCCESS_CODE;
		} catch (Exception e) {
			return Constants.FAILURE_CODE;
		}
	}

	@Override
	public RailAccessMaintain getByCode(String accessWarnId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Class getReferenceClass() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
