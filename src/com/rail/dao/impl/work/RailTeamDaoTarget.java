package com.rail.dao.impl.work;

import java.util.List;
import java.util.Map;

import com.huateng.common.Constants;
import com.huateng.system.util.CommonFunction;
import com.rail.dao.iface.work.RailTeamDao;
import com.rail.po.base.RailPatrol;
import com.rail.po.base.RailTeam;
import com.rail.po.work.RailTeamEmployee;
import com.rail.po.work.RailWorkEmployee;
import com.sdses.dao._RootDAO;

public class RailTeamDaoTarget extends _RootDAO<RailTeam> implements RailTeamDao {

	@Override
	protected Class getReferenceClass() {
		// TODO 自动生成的方法存根
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RailTeam> getByParam(Map<String, String> params) {
		String querySql = "from RailTeam   where 1=1 ";
		for (String key : params.keySet()) {
			String value = params.get(key);
			querySql += " and " + key + "='" + value + "'";
		}
		System.out.println("根据参数查询组织单位querySql:" + querySql);
		return getHibernateTemplate().find(querySql + "");
	}
	/**
	 * 为班组分配员工
	 */
	@Override
	public String addTeamEmployees(RailTeamEmployee rwe) {
		try {
			super.save(rwe);
			return Constants.SUCCESS_CODE;
		} catch (Exception e) {
			return Constants.FAILURE_CODE;
		}
	}
	@Override
	public void delTeamEmployeesByTeamId(String teamId) {
		String sql = "delete from RAIL_TEAM_EMPLOYEE where WORK_TEAM = '"+teamId+"'";
		CommonFunction.getCommQueryDAO().excute(sql);
	}

	@Override
	public String add(RailTeam railTeam) {
		try{
			super.save(railTeam);
			return Constants.SUCCESS_CODE;
		}catch(Exception e){
			e.printStackTrace();
			return Constants.FAILURE_CODE;
		}
	}

	@Override
	public String update(RailTeam railTeam) {
		try{
			super.update(railTeam);
			return Constants.SUCCESS_CODE;
		}catch(Exception e){
			e.printStackTrace();
			return Constants.FAILURE_CODE;
		}
	}

	@Override
	public RailTeam getById(String id) {
		String querySql = "from RailTeam where 1=1 AND id =" + id;
		return (RailTeam) getHibernateTemplate().find(querySql);
	}

	@Override
	public String addPatrol(RailPatrol railPatrol) {
		try {
			super.save(railPatrol);
			return Constants.SUCCESS_CODE;
		} catch (Exception e) {
			return Constants.FAILURE_CODE;
		}
	}

	@Override
	public void delPatrolById(long id) {
		String sql = "delete from RAIL_PATROL where ID = "+id;
		CommonFunction.getCommQueryDAO().excute(sql);
	}

	@Override
	public List<RailPatrol> getRailPatrolByParam(Map<String, String> params) {
		String querySql = "from RailPatrol   where 1=1 ";
		for (String key : params.keySet()) {
			String value = params.get(key);
			querySql += " and " + key + "='" + value + "'";
		}
		System.out.println("根据参数查询巡护中队querySql:" + querySql);
		return getHibernateTemplate().find(querySql + "");
	}

	@Override
	public String updatePatrol(RailPatrol railPatrol) {
		try{
			super.update(railPatrol);
			return Constants.SUCCESS_CODE;
		}catch(Exception e){
			e.printStackTrace();
			return Constants.FAILURE_CODE;
		}
	}

}
