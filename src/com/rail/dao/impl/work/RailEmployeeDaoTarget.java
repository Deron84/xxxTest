package com.rail.dao.impl.work;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.huateng.common.Constants;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.informix.util.stringUtil;
import com.rail.bo.enums.ApiSendMsgFlagEnums;
import com.rail.bo.tools.TSendMsgSocketBO;
import com.rail.dao.iface.work.RailEmployeeDao;
import com.rail.po.base.RailEmployee;
import com.sdses.dao._RootDAO;

public class RailEmployeeDaoTarget extends _RootDAO<RailEmployee> implements RailEmployeeDao {

	@Override
	protected Class getReferenceClass() {
		// TODO 自动生成的方法存根
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RailEmployee> getByParam(Map<String, String> params) {
		String querySql = "from RailEmployee   where 1=1 ";
		for (String key : params.keySet()) {
			String value = params.get(key);
			querySql += " and " + key + "='" + value + "'";
		}
		System.out.println("根据参数查询组织单位querySql:" + querySql);
		return getHibernateTemplate().find(querySql + "");
	}

	@Override
	public String add(RailEmployee railEmployee) {
		
		
		try{
			super.save(railEmployee);
			
			TSendMsgSocketBO tSendMsgSocketBO = (TSendMsgSocketBO) ContextUtil.getBean("TSendMsgSocketBO");
			int flag  = ApiSendMsgFlagEnums.TOOL.getCode();
			//发送信息 更新app人员列表
			tSendMsgSocketBO.sendSocketMsg(railEmployee.getDept(), railEmployee.getId(), ApiSendMsgFlagEnums.EMPLOYEE.getCode());
			
			return Constants.SUCCESS_CODE;
		}catch(Exception e){
			e.printStackTrace();
			return Constants.FAILURE_CODE;
		}
	}

	@Override
	public String update(RailEmployee railEmployee) {
		try{
			super.update(railEmployee);
			return Constants.SUCCESS_CODE;
		}catch(Exception e){
			e.printStackTrace();
			return Constants.FAILURE_CODE;
		}
	}
	
	@Override
	public String delete(RailEmployee railEmployee) {
		try{
			super.delete(railEmployee);
			return Constants.SUCCESS_CODE;
		}catch(Exception e){
			e.printStackTrace();
			return Constants.FAILURE_CODE;
		}
	}

	@Override
	public RailEmployee getById(String id) {
		String querySql = "from RailEmployee where 1=1 AND id =" + id;
		return (RailEmployee) getHibernateTemplate().find(querySql);
	}

	@Override
	public int getInWorkByCode(String employeeCode) {
		String sql = "SELECT * FROM RAIL_WORK_EMPLOYEE t,RAIL_WORK_INFO t1 WHERE 1=1 AND t.WORK_CODE = t1.WORK_CODE AND (t1.WORK_STATUS = 0 OR t1.WORK_STATUS = 1) AND t.EMPLPOYEE_CODE='"+employeeCode+"'";
		List list = new ArrayList();
		list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		return list.size();
	}

}
