package com.rail.dao.iface.warehouse;

import java.util.List;
import java.util.Map;

import com.rail.po.warehouse.RailWhseToolWarn;




/**
 * 门禁终端类型管理
 * @author qiufulong
 *
 */
public interface  RailWhseToolWarnDao {
	/**
	 * 根据参数查询施工单位数据
	 * 
	 */
	public List<RailWhseToolWarn> getByParam(Map<String, String> params);
	
	
	/**
	 * 新增施工单位信息
	 * 
	 */
	public String add(RailWhseToolWarn railWhseToolWarn);
	
	
	/**
	 * 修改单位信息
	 * 
	 */
	public String update(RailWhseToolWarn railWhseToolWarn);
	
	
	/**
	 * 根据id获取施工单位
	 */
	public RailWhseToolWarn getById(String id);

}



