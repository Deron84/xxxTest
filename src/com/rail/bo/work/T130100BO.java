package com.rail.bo.work;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.rail.po.base.RailEmployee;
import com.rail.po.tool.RailToolInfo;
import com.rail.po.work.RailWorkEmployee;
import com.rail.po.work.RailWorkInfo;
import com.rail.po.work.RailWorkTool;

/**
 * Title: T110801BO
 * Description: 新增工单
 * @author Zhaozc 
 * @date 下午8:14:39 
 */
public interface T130100BO {
	
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
	public List<RailWorkInfo> getByParam(Map<String,String> paramMap);
	/**
	 * 新增工单
	 * @Description: TODO
	 * @param @param railWhseInfo   
	 * @return void  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月16日
	 */
	public String add(RailWorkInfo railWhseInfo);
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
	public String addWorkEmployees(List<RailWorkEmployee> rwes);
	/**
	 * 根据workCode获得工单信息
	 * @Description: TODO
	 * @param @param workCode
	 * @param @return   
	 * @return RailWorkInfo  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月26日
	 */
	public RailWorkInfo getWorkInfoByCode(String workCode);
	/**
	 * 更新订单信息
	 * @Description: TODO
	 * @param @param railWorkInfo
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月27日
	 */
	public String updateRailWorkInfo(RailWorkInfo railWorkInfo);
	/**
	 * 删除该工单下的所有人员
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
	 * 根据工单编码和人员编码获得工单人员关联
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
	 * 修改关联表是否有权限开门
	 * @Description: TODO
	 * @param @param railWorkEmployee   
	 * @return void  
	 * @throws
	 * @author liujihui
	 * @date 2019年4月30日
	 */
	public String updateRailWorkEmployee(RailWorkEmployee railWorkEmployee);
	/**
	 * 删除该工单下所有工具
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
	 * @param @param rwts
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author liujihui
	 * @date 2019年5月4日
	 */
	public String addWorkTool(List<RailWorkTool> rwts);
	/**
	 * 查询预警信息
	 * @Description: TODO
	 * @param @param begin
	 * @param @param end
	 * @param @return   
	 * @return List  
	 * @throws
	 * @author liujihui
	 * @date 2019年5月7日
	 */
	public List<Map<String,Object>> getWarnings(Date begin, Date end);
	/**
	 * 根据仓库标识和工具名称id查询库存
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
	 * 根据工具编码查询工具详情
	 * @Description: TODO
	 * @param @param toolCode
	 * @param @return   
	 * @return RailToolInfo  
	 * @throws
	 * @author liujihui
	 * @date 2019年5月23日
	 */
	public RailToolInfo getToolInfoByToolCode(String toolCode);
	/**
	 * 获得未确认的所有预警
	 * @Description: TODO
	 * @param @return   
	 * @return List<Map<String,Object>>  
	 * @throws
	 * @author liujihui
	 * @date 2019年6月18日
	 */
	public List<Map<String, Object>> getAllWarnings();

}