package com.rail.bo.tools;

import com.rail.po.tool.RailToolRepair;


/**
 * Title: T110701BO
 * Description:  工具维修审核
 * @author Zhaozc 
 * @date 下午3:04:13 
 */
public interface T110402BO {
	
	/**
	 * @Description: 工具维修审核
	 * @param @param railAccessMaintainWarn
	 * @param @return
	 * @param @throws Exception
	 * @return int
	 * @throws
	 */
	public void update(RailToolRepair railToolRepair) throws Exception;

}