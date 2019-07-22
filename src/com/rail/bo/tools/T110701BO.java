package com.rail.bo.tools;

import com.rail.po.tool.RailToolMaintainWarn;


/**
 * Title: T110701BO
 * Description:  工具预警
 * @author Zhaozc 
 * @date 下午3:04:13 
 */
public interface T110701BO {
	
	/**
	 * @Description: 工具预警确认 
	 * @param @param railAccessMaintainWarn
	 * @param @return
	 * @param @throws Exception
	 * @return int
	 * @throws
	 */
	public void update(RailToolMaintainWarn railToolMaintainWarn) throws Exception;

}