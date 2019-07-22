package com.rail.bo.task;

import java.util.List;

import com.huateng.po.CstSysParam;

/**  
* @author syl
* @version 创建时间：2019年4月15日 上午9:23:37  
* @desc
*/
public interface CommonBO  {
	//根据
	List<CstSysParam> getSysParam(String owner);
}

