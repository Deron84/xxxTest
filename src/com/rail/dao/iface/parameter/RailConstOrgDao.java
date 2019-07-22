package com.rail.dao.iface.parameter;

import java.util.List;
import java.util.Map;

import com.rail.po.org.RailConstOrg;
/**
 * 施工单位管理
 * @author qiufulong
 *
 */
public interface RailConstOrgDao {
	
	/**
	 * 根据参数查询施工单位数据
	 * 
	 */
	public List<RailConstOrg> getByParam(Map<String, String> params);
	
	
	/**
	 * 新增施工单位信息
	 * 
	 */
	public String add(RailConstOrg railConstOrg);
	
	
	/**
	 * 修改单位信息
	 * 
	 */
	public String update(RailConstOrg railConstOrg);
	
	
	/**
	 * 根据id获取施工单位
	 */
	public RailConstOrg getById(String id);

}
