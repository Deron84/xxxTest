package com.rail.bo.warehouse;

import java.util.List;
import java.util.Map;

import com.rail.po.warehouse.RailWhseInfo;

public interface T100100BO {
	/**
	 * 根据参数条件查询仓库数据
	 * @Description: TODO
	 * @param @param paramMap
	 * @param @return   
	 * @return List<RailWhseInfo>  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月16日
	 */
	public List<RailWhseInfo> getByParam(Map<String,String> paramMap);
	/**
	 * 新增仓库
	 * @Description: TODO
	 * @param @param railWhseInfo   
	 * @return void  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月16日
	 */
	public String add(RailWhseInfo railWhseInfo);

}
