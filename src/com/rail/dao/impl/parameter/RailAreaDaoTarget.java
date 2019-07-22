package com.rail.dao.impl.parameter;

import java.util.List;
import java.util.Map;

import com.huateng.common.Constants;
import com.rail.dao.iface.parameter.RailAreaDao;
import com.rail.po.base.RailArea;
import com.sdses.dao._RootDAO;

/**
 * 仓库分区管理
 * @author qiufulong
 *
 */
public class RailAreaDaoTarget   extends _RootDAO<RailArea> implements RailAreaDao {

	@Override
	protected Class getReferenceClass() {
		// TODO 自动生成的方法存根
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RailArea> getByParam(Map<String, String> params) {
		String querySql = "from RailArea where 1=1   and DEL_STATUS='0' ";
		for (String key : params.keySet()) {
			String value = params.get(key);
			querySql += " and " + key + "='" + value + "'";
		}
		System.out.println("根据参数查询施工单位querySql:" + querySql);
		return getHibernateTemplate().find(querySql + "");
	}

	@Override
	public String add(RailArea railArea) {
		try{
			super.save(railArea);
			return Constants.SUCCESS_CODE;
		}catch(Exception e){
			e.printStackTrace();
			return Constants.FAILURE_CODE;
		}
	}

	@Override
	public String update(RailArea railArea) {
		try{
			super.update(railArea);
			return Constants.SUCCESS_CODE;
		}catch(Exception e){
			e.printStackTrace();
			return Constants.FAILURE_CODE;
		}
	}

	@Override
	public RailArea getById(String id) {
		String querySql = "from RailArea where 1=1 AND id =" + id;
		return (RailArea) getHibernateTemplate().find(querySql);
	}

}
