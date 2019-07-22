package com.rail.bo.tools;

import com.rail.po.tool.RailToolInfo;
import com.rail.po.tool.RailToolRepair;

/**
 * Title: T110801BO
 * Description: 新增工具维修申请
 * @author Zhaozc 
 * @date 下午8:14:39 
 */
public interface T110401BO {
	
	void save(RailToolRepair railToolRepair)throws Exception;
	void update(RailToolRepair railToolRepair)throws Exception;
	void updateComplete(RailToolRepair railToolRepair)throws Exception;
	RailToolRepair get(RailToolRepair railToolRepair)throws Exception;
	RailToolInfo getRailToolInfoByToolCode(String toolCode)throws Exception;

}