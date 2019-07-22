package com.rail.bo.access;

import java.util.List;
import java.util.Map;

import com.rail.po.access.RailAccessInfo;
import com.rail.po.warehouse.RailWhseInfo;

public interface T120100BO {
	
	/**
	 * 根据参数条件查询门禁数据
	 * @Description: TODO
	 * @param @param paramMap
	 * @param @return   
	 * @return List<RailAccessInfo>  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public List<RailAccessInfo> getByParam(Map<String,String> paramMap);
	/**
	 * 新增门禁
	 * @Description: TODO
	 * @param @param railAccessInfo  
	 * @return void  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public String add(RailAccessInfo railAccessInfo);

}
