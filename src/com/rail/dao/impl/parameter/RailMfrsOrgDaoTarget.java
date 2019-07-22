package com.rail.dao.impl.parameter;

import java.util.List;
import java.util.Map;

import com.huateng.common.Constants;
import com.rail.dao.iface.parameter.RailMfrsOrgDao;
import com.rail.po.org.RailMfrsOrg;
import com.sdses.dao._RootDAO;
/**
 * 供应商管理
 * @author qiufulong
 *
 */
public class RailMfrsOrgDaoTarget  extends _RootDAO<RailMfrsOrg> implements RailMfrsOrgDao {

	@Override
	protected Class getReferenceClass() {
		// TODO 自动生成的方法存根
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RailMfrsOrg> getByParam(Map<String, String> params) {
		String querySql = "from RailMfrsOrg where 1=1  and DEL_STATUS='0'  ";
		for (String key : params.keySet()) {
			String value = params.get(key);
			querySql += " and " + key + "='" + value + "'";
		}
		System.out.println("根据参数查询施工单位querySql:" + querySql);
		return getHibernateTemplate().find(querySql + "");
	}

	@Override
	public String add(RailMfrsOrg railMfrsOrg) {
		try{
			super.save(railMfrsOrg);
			return Constants.SUCCESS_CODE;
		}catch(Exception e){
			e.printStackTrace();
			return Constants.FAILURE_CODE;
		}
	}

	@Override
	public String update(RailMfrsOrg railMfrsOrg) {
		try{
			super.update(railMfrsOrg);
			return Constants.SUCCESS_CODE;
		}catch(Exception e){
			e.printStackTrace();
			return Constants.FAILURE_CODE;
		}
	}

	@Override
	public RailMfrsOrg getById(String id) {
		String querySql = "from RailMfrsOrg where 1=1 AND id =" + id;
		return (RailMfrsOrg) getHibernateTemplate().find(querySql);
	}

}
