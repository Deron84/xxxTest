package com.rail.dao.iface.access;

import java.util.List;
import java.util.Map;

import com.rail.po.access.RailAccessMaintainWarn;


public  interface RailAccessMaintainWarnDao {
	/**
	 * 根据参数条件查询门禁数据
	 * @Description: TODO
	 * @param @param paramMap
	 * @param @return   
	 * @return List<RailAccessWarn>  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public List<RailAccessMaintainWarn> getByParam(Map<String, String> paramMap);
	/**
	 * 新增门禁
	 * @Description: TODO
	 * @param @param railAccessWarn
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public String add(RailAccessMaintainWarn railAccessMaintainWarn);
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
	public String updateAccess(RailAccessMaintainWarn railAccessMaintainWarn);
	/**
	 * 根据门禁编码获得门禁信息
	 * @Description: TODO
	 * @param @param AccessCode
	 * @param @return   
	 * @return RailAccessWarn  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public RailAccessMaintainWarn getByCode(String accessWarnId);
}
