package com.rail.dao.iface.work;

import java.util.List;
import java.util.Map;

import com.rail.po.access.RailAccessEmployee;


public interface RailAccessEmployeeDao {
	/**
	 * 根据参数条件查询人员出入网数据
	 * @Description: TODO
	 * @param @param paramMap
	 * @param @return   
	 * @return List<RailAccessEmployee>  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public List<RailAccessEmployee> getByParam(Map<String, String> paramMap);
	/**
	 * 新增人员出入网
	 * @Description: TODO
	 * @param @param railAccessEmployee
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public String add(RailAccessEmployee railAccessEmployee);
	/**
	 * 修改人员出入网信息
	 * @Description: TODO
	 * @param @param paramMap
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public String updateAccess(RailAccessEmployee railAccessEmployee);
	/**
	 * 根据人员出入网编码获得人员出入网信息
	 * @Description: TODO
	 * @param @param AccessCode
	 * @param @return   
	 * @return RailAccessWarn  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public RailAccessEmployee getByCode(String railAccessEmployeeId);
}
