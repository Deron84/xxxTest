package com.rail.bo.impl.access;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.huateng.common.StringUtil;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.DateUtil;
import com.rail.bo.access.T120101BO;
import com.rail.dao.iface.access.RailAccessInfoDao;
import com.rail.po.access.RailAccessEquipInfo;
import com.rail.po.access.RailAccessInfo;
import com.rail.po.access.RailAccessOptlog;

public class T120101BOTarget  implements T120101BO{
	private RailAccessInfoDao railAccessInfoDao;


	public RailAccessInfoDao getRailAccessInfoDao() {
		return railAccessInfoDao;
	}

	public void setRailAccessInfoDao(RailAccessInfoDao railAccessInfoDao) {
		this.railAccessInfoDao = railAccessInfoDao;
	}
	
	@Override
	public void saveRailAccessOptlog(RailAccessOptlog railAccessOptlog){
		this.railAccessInfoDao.saveRailAccessOptlog(railAccessOptlog);
	}
	/**
	 * 修改仓库信息
	 */
	@Override
	public String updateAccess(RailAccessInfo railAccessInfo) {
		return railAccessInfoDao.updateAccess(railAccessInfo);
		
	}
	/**
	 * 根据仓库Code获得仓库信息
	 */
	@Override
	public RailAccessInfo getByCode(String accessCode) {
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("ACCESS_CODE", accessCode);
		List<RailAccessInfo> rwis = railAccessInfoDao.getByParam(paramMap);
		RailAccessInfo railAccessInfo = new RailAccessInfo();
		if(rwis!=null&&!rwis.isEmpty()){
			railAccessInfo = rwis.get(0);
		}
		return railAccessInfo;
	}

	@Override
	public List<RailAccessEquipInfo> getEqInfoByCodeType(String eqCode, int type) {
		List<RailAccessEquipInfo> raeis = railAccessInfoDao.getEqInfoByCodeType(eqCode,type);
//		RailAccessEquipInfo railAccessEquipInfo = new RailAccessEquipInfo();
//		if(raeis.size()>0){
//			railAccessEquipInfo = raeis.get(0);
//		}
		return raeis;
	}
}
