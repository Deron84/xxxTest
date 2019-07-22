package com.rail.dao.iface.warehouse;

import java.util.List;
import java.util.Map;

import com.rail.po.warehouse.RailWhseInfo;

public interface RailWhseInfoDao {
	/**
	 * 根据参数条件查询仓库数据
	 * @Description: TODO
	 * @param @param paramMap
	 * @param @return   
	 * @return List<RailWhseInfo>  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月16日
	 */
	public List<RailWhseInfo> getByParam(Map<String, String> paramMap);
	/**
	 * 新增仓库
	 * @Description: TODO
	 * @param @param railWhseInfo
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月16日
	 */
	public String add(RailWhseInfo railWhseInfo);
	/**
	 * 修改仓库信息
	 * @Description: TODO
	 * @param @param paramMap
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月17日
	 */
	public String updateWhse(RailWhseInfo railWhseInfo);
	/**
	 * 根据仓库编码获得仓库信息
	 * @Description: TODO
	 * @param @param whseCode
	 * @param @return   
	 * @return RailWhseInfo  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月17日
	 */
	public List<RailWhseInfo> getByCode(String whseCode);

}
