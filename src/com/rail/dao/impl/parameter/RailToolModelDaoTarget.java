package com.rail.dao.impl.parameter;

import java.util.List;
import java.util.Map;

import com.huateng.common.Constants;
import com.rail.dao.iface.parameter.RailToolModelDao;
import com.rail.po.tool.RailToolModel;
import com.sdses.dao._RootDAO;

/**
 * 工具型号管理
 * @author qiufulong
 *
 */
public class RailToolModelDaoTarget   extends _RootDAO<RailToolModel> implements RailToolModelDao{

	@Override
	protected Class getReferenceClass() {
		// TODO 自动生成的方法存根
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RailToolModel> getByParam(Map<String, String> params) {
		String querySql = "from RailToolModel where 1=1  and DEL_STATUS='0'  ";
		for (String key : params.keySet()) {
			String value = params.get(key);
			querySql += " and " + key + "='" + value + "'";
		}
		System.out.println("根据参数查询施工单位querySql:" + querySql);
		return getHibernateTemplate().find(querySql + "");
	}

	@Override
	public String add(RailToolModel railToolModel) {
		try{
			super.save(railToolModel);
			return Constants.SUCCESS_CODE;
		}catch(Exception e){
			e.printStackTrace();
			return Constants.FAILURE_CODE;
		}
	}

	@Override
	public String update(RailToolModel railToolModel) {
		try{
			super.update(railToolModel);
			return Constants.SUCCESS_CODE;
		}catch(Exception e){
			e.printStackTrace();
			return Constants.FAILURE_CODE;
		}
	}

	@Override
	public RailToolModel getById(String id) {
		String querySql = "from RailToolModel where 1=1 AND id =" + id;
		return (RailToolModel) getHibernateTemplate().find(querySql);
	}

}


