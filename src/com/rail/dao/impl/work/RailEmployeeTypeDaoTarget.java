package com.rail.dao.impl.work;

import java.util.List;
import java.util.Map;

import com.huateng.common.Constants;
import com.rail.dao.iface.work.RailEmployeeTypeDao;
import com.rail.po.base.RailEmployeeType;
import com.sdses.dao._RootDAO;

public class RailEmployeeTypeDaoTarget    extends _RootDAO<RailEmployeeType> implements RailEmployeeTypeDao {

	@Override
	protected Class getReferenceClass() {
		// TODO 自动生成的方法存根
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RailEmployeeType> getByParam(Map<String, String> params) {
		String querySql = "from RailEmployeeType where 1=1 ";
		for (String key : params.keySet()) {
			String value = params.get(key);
			querySql += " and " + key + "='" + value + "'";
		}
		System.out.println("根据参数查询人员类型querySql:" + querySql);
		return getHibernateTemplate().find(querySql + "");
	}

	@Override
	public String add(RailEmployeeType railEmployeeType) {
		try{
			super.save(railEmployeeType);
			return Constants.SUCCESS_CODE;
		}catch(Exception e){
			e.printStackTrace();
			return Constants.FAILURE_CODE;
		}
	}

	@Override
	public String update(RailEmployeeType railEmployeeType) {
		try{
			super.update(railEmployeeType);
			return Constants.SUCCESS_CODE;
		}catch(Exception e){
			e.printStackTrace();
			return Constants.FAILURE_CODE;
		}
	}

	@Override
	public RailEmployeeType getById(String id) {
		String querySql = "from RailEmployeeType where 1=1 AND id =" + id;
		return (RailEmployeeType) getHibernateTemplate().find(querySql);
	}

}
