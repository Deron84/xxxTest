package com.rail.bo.work;

import com.rail.po.work.RailWorkInfo;

/**
 * Title: T110801BO
 * Description: 工单信息审核
 * @author Zhaozc 
 * @date 下午8:14:39 
 */
public interface T130103BO {

	RailWorkInfo getWorkInfoByCode(String workCode);
	
//	void update(RailWorkInfo railWorkInfo)throws Exception;

}