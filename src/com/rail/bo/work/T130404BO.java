package com.rail.bo.work;

import java.util.List;
import java.util.Map;

import com.rail.po.base.RailEmployeeType;


public  interface  T130404BO {
	/**
	 * 根据条件查询人员类型信息
	 * @param params
	 * @return
	 */
	public List<RailEmployeeType> getByParam(Map<String,String> params);
	
	
	/**
	 * 新增人员类型
	 * @param railEmployeeType
	 * @return
	 */
	public String add(RailEmployeeType railEmployeeType);
	
	
	/**
	 * 新增人员类型
	 * @param railEmployeeType
	 * @return
	 */
	public String update(RailEmployeeType railEmployeeType);
	
	
	/**
	 * 通过ID查询
	 * @param railEmployeeType
	 * @return
	 */
	public RailEmployeeType getById(String id);
	
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
	public RailEmployeeType getByCode(String id);
}
