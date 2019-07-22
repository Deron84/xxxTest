package com.rail.dao.impl.parameter;

import java.util.List;
import java.util.Map;

import com.huateng.common.Constants;
import com.rail.dao.iface.parameter.RailToolNameDao;
import com.rail.dao.iface.parameter.RailToolTypeDao;
import com.rail.po.tool.RailToolType;
import com.sdses.dao._RootDAO;

/**
 * 工具分类管理
 * @author qiufulong
 *
 */
public class RailToolTypeDaoTarget   extends _RootDAO<RailToolType> implements RailToolTypeDao{

	@Override
	protected Class getReferenceClass() {
		// TODO 自动生成的方法存根
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RailToolType> getByParam(Map<String, String> params) {
		String querySql = "from RailToolType where 1=1  and DEL_STATUS='0'  ";
		for (String key : params.keySet()) {
			String value = params.get(key);
			querySql += " and " + key + "='" + value + "'";
		}
		System.out.println("根据参数查询施工单位querySql:" + querySql);
		return getHibernateTemplate().find(querySql + "");
	}

	@Override
	public String add(RailToolType railToolType) {
		try{
			super.save(railToolType);
			return Constants.SUCCESS_CODE;
		}catch(Exception e){
			e.printStackTrace();
			return Constants.FAILURE_CODE;
		}
	}

	@Override
	public String update(RailToolType railToolType) {
		try{
			super.update(railToolType);
			return Constants.SUCCESS_CODE;
		}catch(Exception e){
			e.printStackTrace();
			return Constants.FAILURE_CODE;
		}
	}

	@Override
	public RailToolType getById(String id) {
		String querySql = "from RailToolType where 1=1 AND id =" + id;
		return (RailToolType) getHibernateTemplate().find(querySql);
	}

}


