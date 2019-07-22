package com.rail.dao.iface.parameter;

import java.util.List;
import java.util.Map;

import com.rail.po.tool.RailToolName;


/**
 * 工具名称管理
 * @author qiufulong
 *
 */
public  interface RailToolNameDao {
	/**
	 * 根据参数查询施工单位数据
	 * 
	 */
	public List<RailToolName> getByParam(Map<String, String> params);
	
	
	/**
	 * 新增施工单位信息
	 * 
	 */
	public String add(RailToolName railToolName);
	
	
	/**
	 * 修改单位信息
	 * 
	 */
	public String update(RailToolName railToolName);
	
	
	/**
	 * 根据id获取施工单位
	 */
	public RailToolName getById(String id);

}

