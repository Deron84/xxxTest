package com.rail.bo.parameter;


import java.util.List;
import java.util.Map;

import com.rail.po.access.RailAccessEquipType;

/**
 * 门禁终端类型管理
 * @author wy
 *
 */
public  interface  T140107BO {
	/**
	 * 根据条件查询施工单位信息
	 * @param params
	 * @return
	 */
	public List<RailAccessEquipType> getByParam(Map<String,String> params);
	
	
	/**
	 * 新增施工单位
	 * @param railAccessEquipType
	 * @return
	 */
	public String add(RailAccessEquipType railAccessEquipType);
	
	
	/**
	 * 新增施工单位
	 * @param railAccessEquipType
	 * @return
	 */
	public String update(RailAccessEquipType railAccessEquipType);
	
	
	/**
	 * 通过ID查询
	 * @param railAccessEquipType
	 * @return
	 */
	public RailAccessEquipType getById(String id);
	
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
	public RailAccessEquipType getByCode(String id);
}
