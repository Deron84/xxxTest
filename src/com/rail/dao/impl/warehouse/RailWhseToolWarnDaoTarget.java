package com.rail.dao.impl.warehouse;

import java.util.List;
import java.util.Map;

import com.huateng.common.Constants;
import com.rail.dao.iface.warehouse.RailWhseToolWarnDao;
import com.rail.po.warehouse.RailWhseToolWarn;
import com.sdses.dao._RootDAO;

/**
 * 门禁工具预警阈值管理
 * @author qiufulong
 *
 */
public class RailWhseToolWarnDaoTarget   extends _RootDAO<RailWhseToolWarn> implements RailWhseToolWarnDao {

	@Override
	protected Class<RailWhseToolWarn> getReferenceClass() {
		return RailWhseToolWarn.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RailWhseToolWarn> getByParam(Map<String, String> params) {
		String querySql = "from RailWhseToolWarn where 1=1  and DEL_STATUS='0' ";
		for (String key : params.keySet()) {
			String value = params.get(key);
			querySql += " and " + key + "='" + value + "'";
		}
		System.out.println("根据参数查询工具预警阈值querySql:" + querySql);
		return getHibernateTemplate().find(querySql + "");
	}

	@Override
	public String add(RailWhseToolWarn railWhseToolWarn) {
		try{
			super.save(railWhseToolWarn);
			return Constants.SUCCESS_CODE;
		}catch(Exception e){
			e.printStackTrace();
			return Constants.FAILURE_CODE;
		}
	}

	@Override
	public String update(RailWhseToolWarn railWhseToolWarn) {
		try{
			super.update(railWhseToolWarn);
			return Constants.SUCCESS_CODE;
		}catch(Exception e){
			e.printStackTrace();
			return Constants.FAILURE_CODE;
		}
	}

	@Override
	public RailWhseToolWarn getById(String id) {
		String querySql = "from RailWhseToolWarn where 1=1 AND id =" + id;
		return (RailWhseToolWarn) getHibernateTemplate().find(querySql);
	}

}
