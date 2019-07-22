package com.rail.bo.parameter;

import java.util.List;
import java.util.Map;

import com.rail.po.access.RailAccessWarn;
import com.rail.po.org.RailConstOrg;

public interface T140100BO {
	
	/**
	 * 根据条件查询施工单位信息
	 * @param params
	 * @return
	 */
	public List<RailConstOrg> getByParam(Map<String,String> params);
	
	
	/**
	 * 新增施工单位
	 * @param railConstOrg
	 * @return
	 */
	public String add(RailConstOrg railConstOrg);
	
	
	/**
	 * 新增施工单位
	 * @param railConstOrg
	 * @return
	 */
	public String update(RailConstOrg railConstOrg);
	
	
	/**
	 * 通过ID查询
	 * @param railConstOrg
	 * @return
	 */
	public RailConstOrg getById(String id);
	
	/**
	 * 根据仓库code获得仓库信息
	 * @Description: TODO
	 * @param @param accessCode
	 * @param @return   
	 * @return RailAccessInfo  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public RailConstOrg getByCode(String id);

}
