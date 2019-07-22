package com.rail.bo.work;

import java.util.List;
import java.util.Map;

import com.rail.po.work.RailWorkWarn;


public interface  T130500BO {
	/**
	 * 更新仓库信息
	 * @Description: TODO
	 * @param @param railAccessInfo
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月17日
	 */
	public String updateAccess(RailWorkWarn railWorkWarn);
	
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
	public RailWorkWarn getByCode(String workWarnId);
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
	public List<RailWorkWarn> getByParam(Map<String,String> paramMap);
}

