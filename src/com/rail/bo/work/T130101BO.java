package com.rail.bo.work;

import java.util.List;

import com.rail.po.work.RailWorkEmployee;

/**
 * Title: T110801BO
 * Description: 作业单元管理
 * @author Zhaozc 
 * @date 下午8:14:39 
 */
public interface T130101BO {
	
	/**
	 * @Description: 新增作业单元 
	 * @param @param railWorkInfo
	 * @param @throws Exception
	 * @return void 
	 * @throws
	 */
	void save(List<RailWorkEmployee> railWorkEmployees)throws Exception;
	
	/**
	 * @Description: 编辑作业单元 
	 * @param @param railWorkInfo
	 * @param @throws Exception
	 * @return void 
	 * @throws
	 */
	void update(List<RailWorkEmployee> railWorkEmployees)throws Exception;

}