package com.rail.dao.iface.parameter;

import java.util.List;
import java.util.Map;

import com.rail.po.org.RailFormOrg;

public interface RailFormOrgDao {
	
	/**
	 * 根据参数查询施工单位数据
	 * 
	 */
	public List<RailFormOrg> getByParam(Map<String, String> params);
	
	
	/**
	 * 新增施工单位信息
	 * 
	 */
	public String add(RailFormOrg railFormOrg);
	
	
	/**
	 * 修改单位信息
	 * 
	 */
	public String update(RailFormOrg railFormOrg);
	
	
	/**
	 * 根据id获取施工单位
	 */
	public RailFormOrg getById(long id);

}
