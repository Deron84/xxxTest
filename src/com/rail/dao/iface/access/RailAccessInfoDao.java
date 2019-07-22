package com.rail.dao.iface.access;

import java.util.List;
import java.util.Map;

import com.rail.po.access.RailAccessEquipInfo;
import com.rail.po.access.RailAccessInfo;
import com.rail.po.access.RailAccessOptlog;

public interface RailAccessInfoDao {
	public void saveRailAccessOptlog(RailAccessOptlog railAccessOptlog);
	/**
	 * 根据参数条件查询门禁数据
	 * @Description: TODO
	 * @param @param paramMap
	 * @param @return   
	 * @return List<RailAccessInfo>  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public List<RailAccessInfo> getByParam(Map<String, String> paramMap);
	/**
	 * 新增门禁
	 * @Description: TODO
	 * @param @param railAccessInfo
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public String add(RailAccessInfo railAccessInfo);
	/**
	 * 修改门禁信息
	 * @Description: TODO
	 * @param @param paramMap
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public String updateAccess(RailAccessInfo railAccessInfo);
	/**
	 * 根据门禁编码获得门禁信息
	 * @Description: TODO
	 * @param @param AccessCode
	 * @param @return   
	 * @return RailAccessInfo  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public RailAccessInfo getByCode(String AccessCode);
	/**
	 * 根据配套设备编码查新设备信息
	 * @Description: TODO
	 * @param @param eqCode
	 * @param @param type
	 * @param @return   
	 * @return List<RailAccessEquipInfo>  
	 * @throws
	 * @author liujihui
	 * @date 2019年5月9日
	 */
	public List<RailAccessEquipInfo> getEqInfoByCodeType(String eqCode, int type);

}
