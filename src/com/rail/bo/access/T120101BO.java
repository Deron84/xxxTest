package com.rail.bo.access;

import java.util.List;
import java.util.Map;

import com.rail.po.access.RailAccessEquipInfo;
import com.rail.po.access.RailAccessInfo;
import com.rail.po.access.RailAccessOptlog;

public  interface  T120101BO {
	
	public void saveRailAccessOptlog(RailAccessOptlog railAccessOptlog);
	/**
	 * 更新仓库信息
	 * @Description: TODO
	 * @param @param railAccessInfo
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月17日
	 */
	public String updateAccess(RailAccessInfo railAccessInfo);
	
	/**
	 * 根据仓库code获得仓库信息
	 * @Description: TODO
	 * @param @param accessCode
	 * @param @return   
	 * @return RailAccessInfo  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public RailAccessInfo getByCode(String accessCode);
	/**
	 * 根据类型或配套设备编码查询门禁终端信息
	 * @param type 
	 * @param eqCode 
	 * @Description: TODO
	 * @param @return   
	 * @return List<RailAccessEquipInfo>  
	 * @throws
	 * @author liujihui
	 * @date 2019年5月9日
	 */
	public List<RailAccessEquipInfo> getEqInfoByCodeType(String eqCode, int type);
}
