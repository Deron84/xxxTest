package com.rail.dao.iface.work;

import java.util.List;
import java.util.Map;

import com.rail.po.base.RailEmployee;
import com.rail.po.work.RailWorkEmployee;
import com.rail.po.work.RailWorkInfo;
import com.rail.po.work.RailWorkTool;

public interface RailWorkInfoDao {
	/**
	 * 根据参数条件查询工单数据
	 * @Description: TODO
	 * @param @param paramMap
	 * @param @return   
	 * @return List<RailWhseInfo>  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月16日
	 */
	public List<RailWorkInfo> getByParam(Map<String, String> paramMap);
	
	public List<RailWorkInfo> getByParam(String accessCode);
	/**
	 * 新增工单
	 * @Description: TODO
	 * @param @param railWorkInfo
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月16日
	 */
	public String add(RailWorkInfo railWorkInfo);
	/**
	 * 根据工单编码获得工单信息
	 * @Description: TODO
	 * @param @param workCode
	 * @param @return   
	 * @return RailWorkInfo  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月17日
	 */
	public List getByCode(String workCode);
	/**
	 * 根据teamId查询员工列表
	 * @Description: TODO
	 * @param @param teamId
	 * @param @return   
	 * @return List<RailEmployee>  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月24日
	 */
	public List<RailEmployee> getEmpByTeamId(long teamId);
	/**
	 * 为工单分配员工
	 * @Description: TODO
	 * @param @param rwes
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月25日
	 */
	public String addWorkEmployees(RailWorkEmployee rwe);
	/**
	 * 更新工单信息
	 * @Description: TODO
	 * @param @param railWorkInfo
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月27日
	 */
	public String updateWork(RailWorkInfo railWorkInfo);
	/**
	 * 删除该工单下的所有已关联员工
	 * @Description: TODO
	 * @param @param workCode
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月29日
	 */
	public void delWorkEmployeesByWorkCode(String workCode);
	/**
	 * 根据workcode和人员编码获得关联信息
	 * @Description: TODO
	 * @param @param paramMap
	 * @param @return   
	 * @return List<RailWorkEmployee>  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月30日
	 */
	public List<RailWorkEmployee> getWorkEmployeeByParam(Map<String, String> paramMap);
	/**
	 * 修改是否有开门权限
	 * @Description: TODO
	 * @param @param railWorkEmployee
	 * @param @return   
	 * @return Object  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月30日
	 */
	public String updateRailWorkEmployee(RailWorkEmployee railWorkEmployee);
	/**
	 * 删除该工单下的所有工具
	 * @Description: TODO
	 * @param @param workCode   
	 * @return void  
	 * @throws
	 * @author liujihui
	 * @date 2019年5月4日
	 */
	public void delWorkToolByWorkCode(String workCode);
	/**
	 * 添加工单工具
	 * @Description: TODO
	 * @param @param rwt
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author liujihui
	 * @date 2019年5月4日
	 */
	public String addWorkTool(RailWorkTool rwt);
	/**
	 * 获得工单预警
	 * @Description: TODO
	 * @param @param paramMap
	 * @param @return   
	 * @return List  
	 * @throws
	 * @author liujihui
	 * @date 2019年5月7日
	 */
	public List<Map<String,Object>> getWorkWarning(Map<String, Object> paramMap);
	/**
	 * 根据仓库编码和工具名称ID查询库存
	 * @Description: TODO
	 * @param @param whseCode
	 * @param @param nameID
	 * @param @return   
	 * @return int  
	 * @throws
	 * @author liujihui
	 * @date 2019年5月14日
	 */
	public int getWhseToolNum(String whseCode, String nameID);
	/**
	 * 根据工具编码查询工具
	 * @Description: TODO
	 * @param @param toolCode
	 * @param @return   
	 * @return List  
	 * @throws
	 * @author liujihui
	 * @date 2019年5月23日
	 */
	public List getToolInfoByToolCode(String toolCode);
	/**
	 * 统计查询未确认的所有预警
	 * @Description: TODO
	 * @param @return   
	 * @return List<Map<String,Object>>  
	 * @throws
	 * @author liujihui
	 * @date 2019年6月18日
	 */
	public List<Map<String, Object>> getAllWarnings();

}
