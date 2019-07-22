package com.rail.bo.tools;

import com.rail.po.tool.RailToolScrap;

/**
 * Title: T110801BO
 * Description: 新增工具报废申请
 * @author Zhaozc 
 * @date 下午8:14:39 
 */
public interface T110601BO {
	
	void save(RailToolScrap railToolScrap)throws Exception;
	void update(RailToolScrap railToolScrap)throws Exception;
	RailToolScrap get(RailToolScrap railToolScrap)throws Exception;

}