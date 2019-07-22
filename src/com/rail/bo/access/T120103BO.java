package com.rail.bo.access;

import java.util.List;
import java.util.Map;

import com.rail.po.access.RailAccessEquipInfo;


public  interface  T120103BO {
	/**
	 * 根据参数条件查询门禁数据
	 * @Description: TODO
	 * @param @param paramMap
	 * @param @return   
	 * @return List<RailAccessInfo>  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public List<RailAccessEquipInfo> getByParam(Map<String,String> paramMap);
	/**
	 * 新增门禁
	 * @Description: TODO
	 * @param @param railAccessInfo  
	 * @return void  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public String add(RailAccessEquipInfo railAccessEquipInfo);
	/**
	 * 更新仓库信息
	 * @Description: TODO
	 * @param @param railAccessInfo
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月17日
	 */
	public String updateAccess(RailAccessEquipInfo railAccessEquipInfo);
	/**
	 * 删除仓库信息
	 * @Description: TODO
	 * @param @param railAccessInfo
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月17日
	 */
	public String delete(RailAccessEquipInfo railAccessEquipInfo);
	
	/**
	 * 根据仓库code获得仓库信息
	 * @Description: TODO
	 * @param @param accessCode
	 * @param @return   
	 * @return RailAccessInfo  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public RailAccessEquipInfo getByCode(String equipCode);
}
