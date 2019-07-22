package com.rail.dao.iface.parameter;

import java.util.List;
import java.util.Map;

import com.rail.po.tool.RailToolUnit;


/**
 * 工具单位管理
 * @author qiufulong
 *
 */
public interface  RailToolUnitDao {
	/**
	 * 根据参数查询施工单位数据
	 * 
	 */
	public List<RailToolUnit> getByParam(Map<String, String> params);
	
	
	/**
	 * 新增施工单位信息
	 * 
	 */
	public String add(RailToolUnit railToolUnit);
	
	
	/**
	 * 修改单位信息
	 * 
	 */
	public String update(RailToolUnit railToolUnit);
	
	
	/**
	 * 根据id获取施工单位
	 */
	public RailToolUnit getById(String id);

}


