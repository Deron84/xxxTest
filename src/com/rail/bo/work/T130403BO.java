package com.rail.bo.work;

import java.util.List;
import java.util.Map;

import com.rail.po.base.RailPatrol;
import com.rail.po.base.RailTeam;
import com.rail.po.work.RailTeamEmployee;
import com.rail.po.work.RailWorkEmployee;

/**
 * Title: T110801BO
 * Description: 班组管理
 * @author Zhaozc 
 * @date 下午8:14:39 
 */
public interface T130403BO {
	
	public String addTeamEmployees(List<RailTeamEmployee> rwes);
	
	public void delTeamEmployeesByTeamId(String teamId);
	/**
	 * 根据条件查询班组信息
	 * @param params
	 * @return
	 */
	public List<RailTeam> getByParam(Map<String,String> params);
	
	
	/**
	 * 新增班组
	 * @param railTeam
	 * @return
	 */
	public String add(RailTeam railTeam);
	
	
	/**
	 * 新增班组
	 * @param railTeam
	 * @return
	 */
	public String update(RailTeam railTeam);
	
	
	/**
	 * 通过ID查询
	 * @param railTeam
	 * @return
	 */
	public RailTeam getById(String id);
	
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
	public RailTeam getByCode(String id);
	/**
	 * 添加巡护中队
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
	 * 根据ID查询巡护中队
	 * @Description: TODO
	 * @param @param id
	 * @param @return   
	 * @return RailPatrol  
	 * @throws
	 * @author liujihui
	 * @date 2019年5月10日
	 */
	public RailPatrol getPatrolById(long id);
	/**
	 * 根据参数获得巡护中队
	 * @Description: TODO
	 * @param @param params
	 * @param @return   
	 * @return List<RailPatrol>  
	 * @throws
	 * @author liujihui
	 * @date 2019年5月10日
	 */
	public List<RailPatrol> getPatrolByParam(Map<String, String> params);
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