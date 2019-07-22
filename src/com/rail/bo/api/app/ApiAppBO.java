package com.rail.bo.api.app;

import java.util.List;
import java.util.Map;

import com.rail.bo.pub.ResultUtils;
import com.rail.po.warehouse.RailWhseEmployee;
import com.rail.po.warehouse.RailWhseTool;

public interface ApiAppBO {
	List getDeptList(List list_depts_all,String detp);
	String getWhere(List<String>  list_depts_all,String detp);
	/**
	 * @return
	 */
	List apiAppActionAllToolsImgList(String equipCode) ;
	List apiAppActionAllEmplayeeImgList(Map<String, String> params) ;
	List apiAppActionAllWorkInfoList(Map<String, String> params);
	/**
	 * 
	 * @Description: 只传递标识即可
	 * @author syl
	 * @date 2019年4月25日
	 */
	Map addRailWhseSingle(String workCode,String equipCode,String infoSign) ;
	Map addRailWhseOut(String tooCodes,String workCode,String equipCode,String infoSign) ;
	/**
	 * 
	 * @Description:  出网
	 * @author syl
	 * @date 2019年4月22日
	 */
	void addRailAccesssOutRecord(String infoSign ,String workCode,String equipCode) ;

	void addRailAccesssInRecord(String employees,String tooCodes,String infoSign ,String workCode,String equipCode) ;
	/**
	 * 
	 * @Description: 开门 
	 * @return void  
	 * @author syl
	 * @date 2019年4月22日
	 */
	Map openAccess(String employeeCode ,String equipCode,Integer openSign) ;
	
	Object getPresentBean(String id ,String type);
	
	

}
