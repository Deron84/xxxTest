package com.rail.bo.impl.warehouse;

import java.util.List;
import java.util.Map;

import com.rail.bo.warehouse.T100100BO;
import com.rail.dao.iface.warehouse.RailWhseInfoDao;
import com.rail.po.warehouse.RailWhseInfo;

public class T100100BOTarget implements T100100BO {
	private RailWhseInfoDao railWhseInfoDao;
	/**
	 * 根据参数查询仓库
	 */
	@Override
	public List<RailWhseInfo> getByParam(Map<String,String> paramMap) {
		return this.railWhseInfoDao.getByParam(paramMap);
	}
	/**
	 * 新增仓库信息
	 */
	@Override
	public String add(RailWhseInfo railWhseInfo) {
		return railWhseInfoDao.add(railWhseInfo);
	}
	
	public RailWhseInfoDao getRailWhseInfoDao() {
		return railWhseInfoDao;
	}
	public void setRailWhseInfoDao(RailWhseInfoDao railWhseInfoDao) {
		this.railWhseInfoDao = railWhseInfoDao;
	}

}
