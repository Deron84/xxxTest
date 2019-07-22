package com.rail.bo.warehouse;


import java.util.List;
import java.util.Map;

import com.rail.po.warehouse.RailWhseToolWarn;


/**
 * 门禁终端类型管理
 * @author wy
 *
 */
public  interface  T100300BO {
	/**
	 * 根据条件查询施工单位信息
	 * @param params
	 * @return
	 */
	public List<RailWhseToolWarn> getByParam(Map<String,String> params);
	
	
	/**
	 * 新增施工单位
	 * @param railWhseToolWarn
	 * @return
	 */
	public String add(RailWhseToolWarn railWhseToolWarn);
	
	
	/**
	 * 新增施工单位
	 * @param railWhseToolWarn
	 * @return
	 */
	public String update(RailWhseToolWarn railWhseToolWarn);
	
	
	/**
	 * 通过ID查询
	 * @param railWhseToolWarn
	 * @return
	 */
	public RailWhseToolWarn getById(String id);
	
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
	public RailWhseToolWarn getByCode(String id);
}
