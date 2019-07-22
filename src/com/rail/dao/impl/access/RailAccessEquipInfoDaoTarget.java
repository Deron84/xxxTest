package com.rail.dao.impl.access;

import java.util.List;
import java.util.Map;

import com.huateng.common.Constants;
import com.rail.dao.iface.access.RailAccessEquipInfoDao;
import com.rail.po.access.RailAccessEquipInfo;
import com.sdses.dao._RootDAO;

public class RailAccessEquipInfoDaoTarget extends _RootDAO<RailAccessEquipInfo> implements RailAccessEquipInfoDao {

	@Override
	protected Class getReferenceClass() {
		// TODO Auto-generated method stub
		return RailAccessEquipInfo.class;
	}

	@Override
	public List<RailAccessEquipInfo> getByParam(Map<String, String> paramMap) {
		// TODO Auto-generated method stub
		String querySql = "from RailAccessEquipInfo where 1=1 ";
		for (String key : paramMap.keySet()) {
			String value = paramMap.get(key);
			querySql += " and " + key + "='" + value + "'";
		}
		System.out.println("根据参数查询门禁querySql:" + querySql);
		return getHibernateTemplate().find(querySql + "");
	}

	@Override
	public String add(RailAccessEquipInfo railAccessEquipInfo) {
		// TODO Auto-generated method stub
		try {
			super.save(railAccessEquipInfo);
			return Constants.SUCCESS_CODE;
		} catch (Exception e) {
			return Constants.FAILURE_CODE;
		}
	}

	@Override
	public String updateAccess(RailAccessEquipInfo railAccessEquipInfo) {
		// TODO Auto-generated method stub
		try {
			super.update(railAccessEquipInfo);
			return Constants.SUCCESS_CODE;
		} catch (Exception e) {
			return Constants.FAILURE_CODE;
		}
	}
	
	@Override
	public String delete(RailAccessEquipInfo railAccessEquipInfo) {
		// TODO Auto-generated method stub
		try {
			super.delete(railAccessEquipInfo);
			return Constants.SUCCESS_CODE;
		} catch (Exception e) {
			return Constants.FAILURE_CODE;
		}
	}

	@Override
	public RailAccessEquipInfo getByCode(String equipCode) {
		String querySql = "from RailAccessEquipInfo where 1=1 AND EQUIP_CODE='" + equipCode + "'";
		return (RailAccessEquipInfo) getHibernateTemplate().find(querySql);
	}

}
