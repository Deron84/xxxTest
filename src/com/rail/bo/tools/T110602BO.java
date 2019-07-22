package com.rail.bo.tools;

import com.rail.po.tool.RailToolMaintainWarn;
import com.rail.po.tool.RailToolScrap;


/**
 * Title: T110701BO
 * Description:  工具报废审核
 * @author Zhaozc 
 * @date 下午3:04:13 
 */
public interface T110602BO {
	
	/**
	 * @Description: 工具报废审核
	 * @param @param railAccessMaintainWarn
	 * @param @return
	 * @param @throws Exception
	 * @return int
	 * @throws
	 */
	public void update(RailToolScrap railToolScrap) throws Exception;

}