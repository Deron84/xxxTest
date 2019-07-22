package com.rail.bo.impl.access;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.huateng.common.Constants;
import com.rail.bo.access.T120100BO;
import com.rail.dao.iface.access.RailAccessEquipInfoDao;
import com.rail.dao.iface.access.RailAccessInfoDao;
import com.rail.po.access.RailAccessEquipInfo;
import com.rail.po.access.RailAccessInfo;

public class T120100BOTarget extends HibernateDaoSupport implements T120100BO{
	private RailAccessInfoDao railAccessInfoDao;
	private RailAccessEquipInfoDao railAccessEquipInfoDao;
	@Override
	public List<RailAccessInfo> getByParam(Map<String, String> paramMap) {
		// TODO Auto-generated method stub
		return this.railAccessInfoDao.getByParam(paramMap);
	}

	/* (non-Javadoc)
	 * @see com.rail.bo.access.T120100BO#add(com.rail.po.access.RailAccessInfo)
	 * 这里在添加门禁的时候就默认把终端设备添加进去了
	 */
	@Override
	public String add(RailAccessInfo railAccessInfo) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().save(railAccessInfo);
		return Constants.SUCCESS_CODE;
	}
	
	public RailAccessInfoDao getRailAccessInfoDao() {
		return railAccessInfoDao;
	}

	public void setRailAccessInfoDao(RailAccessInfoDao railAccessInfoDao) {
		this.railAccessInfoDao = railAccessInfoDao;
	}

	public RailAccessEquipInfoDao getRailAccessEquipInfoDao() {
		return railAccessEquipInfoDao;
	}

	public void setRailAccessEquipInfoDao(RailAccessEquipInfoDao railAccessEquipInfoDao) {
		this.railAccessEquipInfoDao = railAccessEquipInfoDao;
	}

}
