package com.rail.dao.iface.work;

import java.util.List;
import java.util.Map;

import com.rail.po.base.RailEmployee;


public interface RailEmployeeDao {
	
	/**
	 * 根据参数查询班组数据
	 * 
	 */
	public List<RailEmployee> getByParam(Map<String, String> params);
	
	
	/**
	 * 新增班组信息
	 * 
	 */
	public String add(RailEmployee railEmployee);
	
	
	/**
	 * 修改单位信息
	 * 
	 */
	public String update(RailEmployee railEmployee);
	
	/**
	 * 修改单位信息
	 * 
	 */
	public String delete(RailEmployee railEmployee);
	
	/**
	 * 根据id获取班组
	 */
	public RailEmployee getById(String id);


	public int getInWorkByCode(String employeeCode);

}
