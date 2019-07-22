package com.rail.dao.impl.parameter;

import java.util.List;
import java.util.Map;

import com.huateng.common.Constants;
import com.rail.dao.iface.parameter.RailToolUnitDao;
import com.rail.po.tool.RailToolUnit;
import com.sdses.dao._RootDAO;

/**
 * 工具单位管理
 * @author qiufulong
 *
 */
public class RailToolUnitDaoTarget  extends _RootDAO<RailToolUnit> implements RailToolUnitDao{

	@Override
	protected Class getReferenceClass() {
		// TODO 自动生成的方法存根
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RailToolUnit> getByParam(Map<String, String> params) {
		String querySql = "from RailToolUnit where 1=1 and DEL_STATUS='0' ";
		for (String key : params.keySet()) {
			String value = params.get(key);
			querySql += " and " + key + "='" + value + "'";
		}
		System.out.println("根据参数查询施工单位querySql:" + querySql);
		return getHibernateTemplate().find(querySql + "");
	}

	@Override
	public String add(RailToolUnit railToolUnit) {
		try{
			super.save(railToolUnit);
			return Constants.SUCCESS_CODE;
		}catch(Exception e){
			e.printStackTrace();
			return Constants.FAILURE_CODE;
		}
	}

	@Override
	public String update(RailToolUnit railToolUnit) {
		try{
			super.update(railToolUnit);
			return Constants.SUCCESS_CODE;
		}catch(Exception e){
			e.printStackTrace();
			return Constants.FAILURE_CODE;
		}
	}

	@Override
	public RailToolUnit getById(String id) {
		String querySql = "from RailToolUnit where 1=1 AND id =" + id;
		return (RailToolUnit) getHibernateTemplate().find(querySql);
	}

}


