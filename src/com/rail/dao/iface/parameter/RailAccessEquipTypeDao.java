package com.rail.dao.iface.parameter;

import java.util.List;
import java.util.Map;

import com.rail.po.access.RailAccessEquipType;



/**
 * 门禁终端类型管理
 * @author qiufulong
 *
 */
public interface  RailAccessEquipTypeDao {
	/**
	 * 根据参数查询施工单位数据
	 * 
	 */
	public List<RailAccessEquipType> getByParam(Map<String, String> params);
	
	
	/**
	 * 新增施工单位信息
	 * 
	 */
	public String add(RailAccessEquipType railAccessEquipType);
	
	
	/**
	 * 修改单位信息
	 * 
	 */
	public String update(RailAccessEquipType railAccessEquipType);
	
	
	/**
	 * 根据id获取施工单位
	 */
	public RailAccessEquipType getById(String id);

}



