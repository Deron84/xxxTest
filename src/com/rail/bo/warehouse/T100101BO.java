package com.rail.bo.warehouse;

import java.util.List;
import java.util.Map;

import com.rail.po.warehouse.RailWhseInfo;

public interface T100101BO {
	/**
	 * 更新仓库信息
	 * @Description: TODO
	 * @param @param railWhseInfo
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月17日
	 */
	public String updateWhse(RailWhseInfo railWhseInfo);
	/**
	 * 根据仓库code获得仓库信息
	 * @Description: TODO
	 * @param @param whseCode
	 * @param @return   
	 * @return RailWhseInfo  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月17日
	 */
	public RailWhseInfo getByCode(String whseCode);
	/**
	 * 根据仓库code获得仓库表基本信息
	 * @Description: TODO
	 * @param @param whseCode
	 * @param @return   
	 * @return RailWhseInfo  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月18日
	 */
	public RailWhseInfo getWhseInfoByCode(String whseCode);

}
