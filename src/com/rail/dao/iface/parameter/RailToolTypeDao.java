package com.rail.dao.iface.parameter;

import java.util.List;
import java.util.Map;

import com.rail.po.tool.RailToolType;


/**
 * 工具分类管理
 * @author qiufulong
 *
 */
public  interface  RailToolTypeDao {
	/**
	 * 根据参数查询施工单位数据
	 * 
	 */
	public List<RailToolType> getByParam(Map<String, String> params);
	
	
	/**
	 * 新增施工单位信息
	 * 
	 */
	public String add(RailToolType railToolType);
	
	
	/**
	 * 修改单位信息
	 * 
	 */
	public String update(RailToolType railToolType);
	
	
	/**
	 * 根据id获取施工单位
	 */
	public RailToolType getById(String id);

}

