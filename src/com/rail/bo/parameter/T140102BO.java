package com.rail.bo.parameter;


import java.util.List;
import java.util.Map;

import com.rail.po.base.RailArea;


/**
 * 仓库区管理
 * @author wy
 *
 */
public  interface T140102BO {

	/**
	 * 根据条件查询施工单位信息
	 * @param params
	 * @return
	 */
	public List<RailArea> getByParam(Map<String,String> params);
	
	
	/**
	 * 新增施工单位
	 * @param railArea
	 * @return
	 */
	public String add(RailArea railArea);
	
	
	/**
	 * 新增施工单位
	 * @param railArea
	 * @return
	 */
	public String update(RailArea railArea);
	
	
	/**
	 * 通过ID查询
	 * @param railArea
	 * @return
	 */
	public RailArea getById(String id);
	
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
	public RailArea getByCode(String id);
}

