package com.rail.dao.iface.parameter;

import java.util.List;
import java.util.Map;

import com.rail.po.base.RailArea;


/**
 * 仓库分区管理
 * @author qiufulong
 *
 */
public interface RailAreaDao {
	/**
	 * 根据参数查询施工单位数据
	 * 
	 */
	public List<RailArea> getByParam(Map<String, String> params);
	
	
	/**
	 * 新增施工单位信息
	 * 
	 */
	public String add(RailArea railArea);
	
	
	/**
	 * 修改单位信息
	 * 
	 */
	public String update(RailArea railArea);
	
	
	/**
	 * 根据id获取施工单位
	 */
	public RailArea getById(String id);

}


