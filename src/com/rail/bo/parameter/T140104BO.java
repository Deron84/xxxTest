package com.rail.bo.parameter;


import java.util.List;
import java.util.Map;

import com.rail.po.tool.RailToolType;

/**
 * 工具分类
 * @author wy
 *
 */
public  interface T140104BO {

	/**
	 * 根据条件查询施工单位信息
	 * @param params
	 * @return
	 */
	public List<RailToolType> getByParam(Map<String,String> params);
	
	
	/**
	 * 新增施工单位
	 * @param railToolType
	 * @return
	 */
	public String add(RailToolType railToolType);
	
	
	/**
	 * 新增施工单位
	 * @param railToolType
	 * @return
	 */
	public String update(RailToolType railToolType);
	
	
	/**
	 * 通过ID查询
	 * @param railToolType
	 * @return
	 */
	public RailToolType getById(String id);
	
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
	public RailToolType getByCode(String id);
}
