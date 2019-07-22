package com.rail.bo.parameter;


import java.util.List;
import java.util.Map;

import com.rail.po.tool.RailToolName;


/**
 * 工具名称
 * @author wy
 *
 */
public  interface T140103BO {
	/**
	 * 根据条件查询施工单位信息
	 * @param params
	 * @return
	 */
	public List<RailToolName> getByParam(Map<String,String> params);
	
	
	/**
	 * 新增施工单位
	 * @param railToolName
	 * @return
	 */
	public String add(RailToolName railToolName);
	
	
	/**
	 * 新增施工单位
	 * @param railToolName
	 * @return
	 */
	public String update(RailToolName railToolName);
	
	
	/**
	 * 通过ID查询
	 * @param railToolName
	 * @return
	 */
	public RailToolName getById(String id);
	
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
	public RailToolName getByCode(String id);
}
