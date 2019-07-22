package com.rail.bo.work;

import java.util.List;
import java.util.Map;

import com.rail.po.access.RailAccessEmployee;

public interface  T130300BO {
	/**
	 * 更新作业门视频信息
	 * @Description: TODO
	 * @param @param railAccessInfo
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月25日
	 */
	public String updateAccess(RailAccessEmployee  railAccessEmployee);
	
	/**
	 * 根据作业门视频code获得作业门视频信息
	 * @Description: TODO
	 * @param @param accessCode
	 * @param @return   
	 * @return RailAccessInfo  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月25日
	 */
	public RailAccessEmployee getByCode(String railAccessEmployeeId);
	/**
	 * 根据参数条件查询作业门视频数据
	 * @Description: TODO
	 * @param @param paramMap
	 * @param @return   
	 * @return List<RailAccessInfo>  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月25日
	 */
	public List<RailAccessEmployee> getByParam(Map<String,String> paramMap);
	
	/**
	 * 新增作业门视频
	 * @Description: TODO
	 * @param @param railWhseInfo   
	 * @return void  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月25日
	 */
	public String add(RailAccessEmployee railAccessEmployee);
}
