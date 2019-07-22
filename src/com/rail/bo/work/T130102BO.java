package com.rail.bo.work;

import java.util.List;

import com.rail.po.work.RailWorkTool;

/**
 * Title: T110801BO
 * Description: 作业工具管理
 * @author Zhaozc 
 * @date 下午8:14:39 
 */
public interface T130102BO {
	
	/**
	 * @Description: 新增作业工具
	 * @param @param railWorkInfo
	 * @param @throws Exception
	 * @return void 
	 * @throws
	 */
	void save(List<RailWorkTool> railWorkTools)throws Exception;
	
	/**
	 * @Description: 编辑作业工具 
	 * @param @param railWorkInfo
	 * @param @throws Exception
	 * @return void 
	 * @throws
	 */
	void update(List<RailWorkTool> railWorkToolrs)throws Exception;

}