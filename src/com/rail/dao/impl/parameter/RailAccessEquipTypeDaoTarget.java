package com.rail.dao.impl.parameter;

import java.util.List;
import java.util.Map;

import com.huateng.common.Constants;
import com.rail.dao.iface.parameter.RailAccessEquipTypeDao;
import com.rail.dao.iface.parameter.RailAreaDao;
import com.rail.po.access.RailAccessEquipType;
import com.sdses.dao._RootDAO;

/**
 * 门禁终端类型管理
 * @author qiufulong
 *
 */
public class RailAccessEquipTypeDaoTarget    extends _RootDAO<RailAccessEquipType> implements RailAccessEquipTypeDao {

	@Override
	protected Class getReferenceClass() {
		// TODO 自动生成的方法存根
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RailAccessEquipType> getByParam(Map<String, String> params) {
		String querySql = "from RailAccessEquipType where 1=1  and DEL_STATUS='0' ";
		for (String key : params.keySet()) {
			String value = params.get(key);
			querySql += " and " + key + "='" + value + "'";
		}
		System.out.println("根据参数查询施工单位querySql:" + querySql);
		return getHibernateTemplate().find(querySql + "");
	}

	@Override
	public String add(RailAccessEquipType railAccessEquipType) {
		try{
			super.save(railAccessEquipType);
			return Constants.SUCCESS_CODE;
		}catch(Exception e){
			e.printStackTrace();
			return Constants.FAILURE_CODE;
		}
	}

	@Override
	public String update(RailAccessEquipType railAccessEquipType) {
		try{
			super.update(railAccessEquipType);
			return Constants.SUCCESS_CODE;
		}catch(Exception e){
			e.printStackTrace();
			return Constants.FAILURE_CODE;
		}
	}

	@Override
	public RailAccessEquipType getById(String id) {
		String querySql = "from RailAccessEquipType where 1=1 AND id =" + id;
		return (RailAccessEquipType) getHibernateTemplate().find(querySql);
	}

}
