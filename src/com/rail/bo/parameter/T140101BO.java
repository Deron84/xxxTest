package com.rail.bo.parameter;


import java.util.List;
import java.util.Map;

import com.rail.po.org.RailMfrsOrg;

/**
 * 供应商管理
 * @author wy
 *
 */
public  interface  T140101BO {
	/**
	 * 根据条件查询施工单位信息
	 * @param params
	 * @return
	 */
	public List<RailMfrsOrg> getByParam(Map<String,String> params);
	
	
	/**
	 * 新增施工单位
	 * @param railMfrsOrg
	 * @return
	 */
	public String add(RailMfrsOrg railMfrsOrg);
	
	
	/**
	 * 新增施工单位
	 * @param railMfrsOrg
	 * @return
	 */
	public String update(RailMfrsOrg railMfrsOrg);
	
	
	/**
	 * 通过ID查询
	 * @param railMfrsOrg
	 * @return
	 */
	public RailMfrsOrg getById(String id);
	
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
	public RailMfrsOrg getByCode(String id);
}
