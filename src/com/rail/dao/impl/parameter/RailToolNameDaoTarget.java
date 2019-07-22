package com.rail.dao.impl.parameter;

import java.util.List;
import java.util.Map;

import com.huateng.common.Constants;
import com.rail.dao.iface.parameter.RailToolNameDao;
import com.rail.po.tool.RailToolName;
import com.sdses.dao._RootDAO;

/**
 * 工具名称管理
 * @author qiufulong
 *
 */
public class RailToolNameDaoTarget  extends _RootDAO<RailToolName> implements RailToolNameDao {

	@Override
	protected Class getReferenceClass() {
		// TODO 自动生成的方法存根
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RailToolName> getByParam(Map<String, String> params) {
		String querySql = "from RailToolName where 1=1   and DEL_STATUS='0' ";
		for (String key : params.keySet()) {
			String value = params.get(key);
			querySql += " and " + key + "='" + value + "'";
		}
		System.out.println("根据参数查询施工单位querySql:" + querySql);
		return getHibernateTemplate().find(querySql + "");
	}

	@Override
	public String add(RailToolName railToolName) {
		try{
			super.save(railToolName);
			return Constants.SUCCESS_CODE;
		}catch(Exception e){
			e.printStackTrace();
			return Constants.FAILURE_CODE;
		}
	}

	@Override
	public String update(RailToolName railToolName) {
		try{
			super.update(railToolName);
			return Constants.SUCCESS_CODE;
		}catch(Exception e){
			e.printStackTrace();
			return Constants.FAILURE_CODE;
		}
	}

	@Override
	public RailToolName getById(String id) {
		String querySql = "from RailToolName where 1=1 AND id =" + id;
		return (RailToolName) getHibernateTemplate().find(querySql);
	}

}

