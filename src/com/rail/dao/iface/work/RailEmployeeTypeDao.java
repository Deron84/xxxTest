package com.rail.dao.iface.work;

import java.util.List;
import java.util.Map;

import com.rail.po.base.RailEmployeeType;


public interface  RailEmployeeTypeDao {
	/**
	 * 根据参数查询人员类型数据
	 * 
	 */
	public List<RailEmployeeType> getByParam(Map<String, String> params);
	
	
	/**
	 * 新增人员类型信息
	 * 
	 */
	public String add(RailEmployeeType railEmployeeType);
	
	
	/**
	 * 修改单位信息
	 * 
	 */
	public String update(RailEmployeeType railEmployeeType);
	
	
	/**
	 * 根据id获取人员类型
	 */
	public RailEmployeeType getById(String id);

}



