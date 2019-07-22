package com.rail.bo.parameter;


import java.util.List;
import java.util.Map;

import com.rail.po.tool.RailToolUnit;

/**
 * 工具单位管理
 * @author wy
 *
 */
public  interface  T140105BO {

	/**
	 * 根据条件查询施工单位信息
	 * @param params
	 * @return
	 */
	public List<RailToolUnit> getByParam(Map<String,String> params);
	
	
	/**
	 * 新增施工单位
	 * @param railToolUnit
	 * @return
	 */
	public String add(RailToolUnit railToolUnit);
	
	
	/**
	 * 新增施工单位
	 * @param railToolUnit
	 * @return
	 */
	public String update(RailToolUnit railToolUnit);
	
	
	/**
	 * 通过ID查询
	 * @param railToolUnit
	 * @return
	 */
	public RailToolUnit getById(String id);
	
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
	public RailToolUnit getByCode(String id);
}
