package com.rail.bo.parameter;


import java.util.List;
import java.util.Map;

import com.rail.po.tool.RailToolModel;


public  interface  T140106BO {

	/**
	 * 根据条件查询施工单位信息
	 * @param params
	 * @return
	 */
	public List<RailToolModel> getByParam(Map<String,String> params);
	
	
	/**
	 * 新增施工单位
	 * @param railToolModel
	 * @return
	 */
	public String add(RailToolModel railToolModel);
	
	
	/**
	 * 新增施工单位
	 * @param railToolModel
	 * @return
	 */
	public String update(RailToolModel railToolModel);
	
	
	/**
	 * 通过ID查询
	 * @param railToolModel
	 * @return
	 */
	public RailToolModel getById(String id);
	
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
	public RailToolModel getByCode(String id);
}
