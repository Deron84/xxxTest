package com.rail.dao.impl.parameter;

import java.util.List;
import java.util.Map;

import com.huateng.common.Constants;
import com.rail.dao.iface.parameter.RailFormOrgDao;
import com.rail.po.org.RailFormOrg;
import com.sdses.dao._RootDAO;

public class RailFormOrgDaoTarget extends _RootDAO<RailFormOrg> implements RailFormOrgDao {

	@Override
	protected Class<?> getReferenceClass() {
		// TODO 自动生成的方法存根
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RailFormOrg> getByParam(Map<String, String> params) {
		String querySql = "from RailFormOrg where 1=1  and DEL_STATUS='0' ";
		for (String key : params.keySet()) {
			String value = params.get(key);
			querySql += " and " + key + "='" + value + "'";
		}
		System.out.println("根据参数查询组织单位querySql:" + querySql);
		return getHibernateTemplate().find(querySql + "");
	}

	@Override
	public String add(RailFormOrg railFormOrg) {
		try{
			super.save(railFormOrg);
			return Constants.SUCCESS_CODE;
		}catch(Exception e){
			e.printStackTrace();
			return Constants.FAILURE_CODE;
		}
	}

	@Override
	public String update(RailFormOrg railFormOrg) {
		try{
			super.update(railFormOrg);
			return Constants.SUCCESS_CODE;
		}catch(Exception e){
			e.printStackTrace();
			return Constants.FAILURE_CODE;
		}
	}

	@Override
	public RailFormOrg getById(long id) {
		String querySql = "from RailConstOrg where 1=1 AND id =" + id;
		return (RailFormOrg) getHibernateTemplate().find(querySql);
	}

}
