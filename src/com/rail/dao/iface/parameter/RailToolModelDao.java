package com.rail.dao.iface.parameter;

import java.util.List;
import java.util.Map;

import com.rail.po.tool.RailToolModel;


/**
 * 工具型号管理
 * @author qiufulong
 *
 */
public interface RailToolModelDao {
	/**
	 * 根据参数查询施工单位数据
	 * 
	 */
	public List<RailToolModel> getByParam(Map<String, String> params);
	
	
	/**
	 * 新增施工单位信息
	 * 
	 */
	public String add(RailToolModel railToolModel);
	
	
	/**
	 * 修改单位信息
	 * 
	 */
	public String update(RailToolModel railToolModel);
	
	
	/**
	 * 根据id获取施工单位
	 */
	public RailToolModel getById(String id);

}

