package com.rail.dao.iface.parameter;

import java.util.List;
import java.util.Map;

import com.rail.po.org.RailMfrsOrg;

/**
 * 供应商管理
 * @author qiufulong
 *
 */
public interface RailMfrsOrgDao {
	/**
	 * 根据参数查询施工单位数据
	 * 
	 */
	public List<RailMfrsOrg> getByParam(Map<String, String> params);
	
	
	/**
	 * 新增施工单位信息
	 * 
	 */
	public String add(RailMfrsOrg railMfrsOrg);
	
	
	/**
	 * 修改单位信息
	 * 
	 */
	public String update(RailMfrsOrg railMfrsOrg);
	
	
	/**
	 * 根据id获取施工单位
	 */
	public RailMfrsOrg getById(String id);

}

