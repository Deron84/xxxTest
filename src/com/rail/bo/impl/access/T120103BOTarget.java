package com.rail.bo.impl.access;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rail.bo.access.T120103BO;
import com.rail.dao.iface.access.RailAccessEquipInfoDao;
import com.rail.po.access.RailAccessEquipInfo;
import com.rail.po.access.RailAccessInfo;

public class T120103BOTarget implements T120103BO{
	private RailAccessEquipInfoDao railAccessEquipInfoDao;


	public RailAccessEquipInfoDao getRailAccessEquipInfoDao() {
		return railAccessEquipInfoDao;
	}

	public void setRailAccessEquipInfoDao(RailAccessEquipInfoDao railAccessEquipInfoDao) {
		this.railAccessEquipInfoDao = railAccessEquipInfoDao;
	}
	
	@Override
	public List<RailAccessEquipInfo> getByParam(Map<String, String> paramMap) {
		// TODO Auto-generated method stub
		return this.railAccessEquipInfoDao.getByParam(paramMap);
	}

	@Override
	public String add(RailAccessEquipInfo railAccessEquipInfo) {
		// TODO Auto-generated method stub
		return railAccessEquipInfoDao.add(railAccessEquipInfo);
	}
	/**
	 * 修改仓库信息
	 */
	@Override
	public String updateAccess(RailAccessEquipInfo railAccessEquipInfo) {
		return railAccessEquipInfoDao.updateAccess(railAccessEquipInfo);
	}
	
	/**
	 * 删除仓库信息
	 */
	@Override
	public String delete(RailAccessEquipInfo railAccessEquipInfo) {
		return railAccessEquipInfoDao.delete(railAccessEquipInfo);
	}
	/**
	 * 根据仓库Code获得仓库信息
	 */
	@Override
	public RailAccessEquipInfo getByCode(String equipId) {
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("id", equipId);
		List<RailAccessEquipInfo> rwis = railAccessEquipInfoDao.getByParam(paramMap);
		RailAccessEquipInfo railAccessEquipInfo = new RailAccessEquipInfo();
		if(rwis!=null&&!rwis.isEmpty()){
			railAccessEquipInfo = rwis.get(0);
		}
		return railAccessEquipInfo;
	}
}
