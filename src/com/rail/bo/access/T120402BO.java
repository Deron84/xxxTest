package com.rail.bo.access;

import java.util.List;
import java.util.Map;

import com.rail.po.access.RailAccessMaintain;
import com.rail.po.warehouse.RailWhseInfo;


public  interface  T120402BO {
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
	public String updateAccess(RailAccessMaintain railAccessMaintain);
	
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
	public RailAccessMaintain getByCode(String accessWarnId);
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
	public List<RailAccessMaintain> getByParam(Map<String,String> paramMap);
	
	/**
	 * 新增门禁检修保养
	 * @Description: TODO
	 * @param @param railWhseInfo   
	 * @return void  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月16日
	 */
	public String add(RailAccessMaintain railAccessMaintain);
}
