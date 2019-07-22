package com.rail.bo.impl.warehouse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rail.bo.warehouse.T100101BO;
import com.rail.dao.iface.warehouse.RailWhseInfoDao;
import com.rail.po.warehouse.RailWhseInfo;

public class T100101BOTarget implements T100101BO {
	
	private RailWhseInfoDao railWhseInfoDao;
	
	public RailWhseInfoDao getRailWhseInfoDao() {
		return railWhseInfoDao;
	}
	public void setRailWhseInfoDao(RailWhseInfoDao railWhseInfoDao) {
		this.railWhseInfoDao = railWhseInfoDao;
	}
	/**
	 * 修改仓库信息
	 */
	@Override
	public String updateWhse(RailWhseInfo railWhseInfo) {
		return railWhseInfoDao.updateWhse(railWhseInfo);
	}
	/**
	 * 根据仓库Code获得仓库信息
	 */
	@Override
	public RailWhseInfo getByCode(String whseCode) {
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("WHSE_CODE", whseCode);
		List<RailWhseInfo> rwis = railWhseInfoDao.getByParam(paramMap);
		RailWhseInfo railWhseInfo = new RailWhseInfo();
		if(rwis!=null&&!rwis.isEmpty()){
			railWhseInfo = rwis.get(0);
		}
		return railWhseInfo;
	}
	/**
	 * 根据仓库code获得仓库表基本信息
	 * @Description: TODO
	 * @param @param whseCode
	 * @param @return   
	 * @return RailWhseInfo  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月18日
	 */
	@Override
	public RailWhseInfo getWhseInfoByCode(String whseCode) {
		List<RailWhseInfo> rwis = railWhseInfoDao.getByCode(whseCode);
		RailWhseInfo railWhseInfo = new RailWhseInfo();
		if(rwis!=null&&!rwis.isEmpty()){
			railWhseInfo = rwis.get(0);
		}
		return railWhseInfo;
	}
}
