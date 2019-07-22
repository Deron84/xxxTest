package com.rail.dao.impl.access;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.huateng.common.Constants;
import com.huateng.system.util.CommonFunction;
import com.rail.dao.iface.access.RailAccessInfoDao;
import com.rail.po.access.RailAccessEquipInfo;
import com.rail.po.access.RailAccessInfo;
import com.rail.po.access.RailAccessOptlog;
import com.sdses.dao._RootDAO;

public class RailAccessInfoDaoTarget extends _RootDAO<RailAccessInfo> implements RailAccessInfoDao {

	@Override
	protected Class getReferenceClass() {
		// TODO Auto-generated method stub
		return RailAccessInfo.class;
	}

	@Override
	public List<RailAccessInfo> getByParam(Map<String, String> paramMap) {
		// TODO Auto-generated method stub
		String querySql = "from RailAccessInfo where 1=1 ";
		for (String key : paramMap.keySet()) {
			String value = paramMap.get(key);
			querySql += " and " + key + "='" + value + "'";
		}
		System.out.println("根据参数查询门禁querySql:" + querySql);
		return getHibernateTemplate().find(querySql + "");
	}

	@Override
	public String add(RailAccessInfo railAccessInfo) {
		// TODO Auto-generated method stub
		try {
			super.save(railAccessInfo);
			return Constants.SUCCESS_CODE;
		} catch (Exception e) {
			return Constants.FAILURE_CODE;
		}
	}
	
	@Override
	public void saveRailAccessOptlog(RailAccessOptlog railAccessOptlog){
		this.getHibernateTemplate().saveOrUpdate(railAccessOptlog);
	}

	@Override
	public String updateAccess(RailAccessInfo railAccessInfo) {
		// TODO Auto-generated method stub
		try {
			super.update(railAccessInfo);
			return Constants.SUCCESS_CODE;
		} catch (Exception e) {
			return Constants.FAILURE_CODE;
		}
	}

	@Override
	public RailAccessInfo getByCode(String accessCode) {
		String querySql = "from RailAccessInfo where 1=1 AND ACCESS_CODE='" + accessCode + "'";
		return (RailAccessInfo) getHibernateTemplate().find(querySql);
	}

	@Override
	public List<RailAccessEquipInfo> getEqInfoByCodeType(String eqCode, int type) {
		String sql = "from RailAccessEquipInfo where 1=1 AND ACCESS_CODE='"+eqCode+"' AND EQUIP_TYPE = "+type;
		List<RailAccessEquipInfo> raeis = getHibernateTemplate().find(sql);
		return raeis;
	}

}
