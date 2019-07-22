package com.rail.dao.iface.work;

import java.util.List;
import java.util.Map;

import com.huateng.common.Constants;
import com.huateng.system.util.CommonFunction;
import com.rail.po.base.RailPatrol;
import com.rail.po.base.RailTeam;
import com.rail.po.work.RailTeamEmployee;


public interface RailTeamDao {
	
	/**
	 * 根据参数查询班组数据
	 * 
	 */
	public List<RailTeam> getByParam(Map<String, String> params);
	
	
	public String addTeamEmployees(RailTeamEmployee rwe);
	public void delTeamEmployeesByTeamId(String teamId);
	
	
	/**
	 * 新增班组信息
	 * 
	 */
	public String add(RailTeam railTeam);
	
	
	/**
	 * 修改单位信息
	 * 
	 */
	public String update(RailTeam railTeam);
	
	
	/**
	 * 根据id获取班组
	 */
	public RailTeam getById(String id);

	/**
	 * 新增巡护中队
	 * @Description: TODO
	 * @param @param railPatrol
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author liujihui
	 * @date 2019年5月10日
	 */
	public String addPatrol(RailPatrol railPatrol);

	/**
	 * 删除巡护中队
	 * @Description: TODO
	 * @param @param id   
	 * @return void  
	 * @throws
	 * @author liujihui
	 * @date 2019年5月10日
	 */
	public void delPatrolById(long id);

	/**
	 * 根据id获得巡护中队信息
	 * @Description: TODO
	 * @param @param paramMap
	 * @param @return   
	 * @return List<RailPatrol>  
	 * @throws
	 * @author liujihui
	 * @date 2019年5月10日
	 */
	public List<RailPatrol> getRailPatrolByParam(Map<String, String> paramMap);

	/**
	 * 修改巡护中队信息
	 * @Description: TODO
	 * @param @param railPatrol
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author liujihui
	 * @date 2019年5月10日
	 */
	public String updatePatrol(RailPatrol railPatrol);

}
