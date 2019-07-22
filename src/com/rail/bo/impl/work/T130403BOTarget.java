package com.rail.bo.impl.work;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rail.bo.work.T130403BO;
import com.rail.dao.iface.work.RailTeamDao;
import com.rail.po.base.RailPatrol;
import com.rail.po.base.RailTeam;
import com.rail.po.work.RailTeamEmployee;
import com.rail.po.work.RailWorkEmployee;

/**  
* @author zhaozc
* @version 创建时间：2019年4月15日 上午9:23:37  
* @desc 班组管理
*/
public class T130403BOTarget implements T130403BO {

	private RailTeamDao railTeamDao;
	
	@Override
	public String addTeamEmployees(List<RailTeamEmployee> rwes) {
		String result="";
		for(RailTeamEmployee rwe:rwes){
			result=railTeamDao.addTeamEmployees(rwe);
		}
		return result;
	}
	
	@Override
	public void delTeamEmployeesByTeamId(String teamId) {
		railTeamDao.delTeamEmployeesByTeamId(teamId);
	}

	@Override
	public List<RailTeam> getByParam(Map<String, String> params) {
		return railTeamDao.getByParam(params);
	}
	@Override
	public String add(RailTeam railTeam) {
		return railTeamDao.add(railTeam);
	}
	
	@Override
	public String update(RailTeam railTeam) {
		return railTeamDao.update(railTeam);
	}
	@Override
	public RailTeam getById(String id) {
		return railTeamDao.getById(id);
	}
	
	@Override
	public RailTeam getByCode(String accessWarnId) {
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("ID", accessWarnId);
		List<RailTeam> raws = railTeamDao.getByParam(paramMap);
		RailTeam railTeam = new RailTeam();
		if(raws!=null&&!raws.isEmpty()){
			railTeam = raws.get(0);
		}
		return railTeam;
	}

	public RailTeamDao getRailTeamDao() {
		return railTeamDao;
	}

	public void setRailTeamDao(RailTeamDao railTeamDao) {
		this.railTeamDao = railTeamDao;
	}

	@Override
	public String addPatrol(RailPatrol railPatrol) {
		return railTeamDao.addPatrol(railPatrol);
	}

	@Override
	public void delPatrolById(long id) {
		railTeamDao.delPatrolById(id);
	}

	@Override
	public RailPatrol getPatrolById(long id) {
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("ID", id+"");
		List<RailPatrol> rps = railTeamDao.getRailPatrolByParam(paramMap);
		RailPatrol railPatrol = new RailPatrol();
		if(rps!=null&&!rps.isEmpty()){
			railPatrol = rps.get(0);
		}
		return railPatrol;
	}

	@Override
	public List<RailPatrol> getPatrolByParam(Map<String, String> params) {
		return railTeamDao.getRailPatrolByParam(params);
	}

	@Override
	public String updatePatrol(RailPatrol railPatrol) {
		return railTeamDao.updatePatrol(railPatrol);
	}


	
}
