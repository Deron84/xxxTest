package com.rail.dao.impl.parameter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.huateng.common.Constants;
import com.huateng.system.util.CommonFunction;
import com.rail.dao.iface.parameter.RailConstOrgDao;
import com.rail.po.access.RailAccessEquipInfo;
import com.rail.po.org.RailConstOrg;
import com.rail.po.warehouse.RailWhseInfo;
import com.sdses.dao._RootDAO;

public class RailConstOrgDaoTarget extends _RootDAO<RailConstOrg> implements RailConstOrgDao {

	@Override
	protected Class getReferenceClass() {
		// TODO 自动生成的方法存根
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RailConstOrg> getByParam(Map<String, String> params) {
		
		
		String querySql = "from RailConstOrg   where 1=1  and DEL_STATUS='0' ";
		for (String key : params.keySet()) {
			String value = params.get(key);
			querySql += " and " + key + "='" + value + "'";
		}
		System.out.println("根据参数查询组织单位querySql:" + querySql);
		return getHibernateTemplate().find(querySql + "");
		
		
		
		
//		String querySql ="select t.id,t.cost_org_name,t.add_user,t.add_date,t.upd_user,t.upd_date,t.enable_status,t.form_org,（select a.FORM_ORG_NAME from RAIL_FORM_ORG a where a.id=t.form_org) as FORM_ORG_NAME from RAIL_CONST_ORG t Where t.DEL_STATUS='0'  ";
//		for (String key : params.keySet()) {
//			String value = params.get(key);
//			querySql += " and " + key + "='" + value + "'";
//		}
//		List list =CommonFunction.getCommQueryDAO().findBySQLQuery(querySql);
//		List<RailConstOrg> resultList = new ArrayList<RailConstOrg>();
//		 for (int i = 0; i < list.size(); i++) {  
//			Object[] obj = (Object[])list.get(i);  
//			RailConstOrg railConstOrg = new RailConstOrg();
//			railConstOrg.setId(Long.parseLong(obj[0].toString()));
//			railConstOrg.setCostOrgName(obj[1].toString());
//			railConstOrg.setAddUser(obj[2].toString());
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//			try {
//				railConstOrg.setAddDate(sdf.parse(obj[3].toString()));
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			railConstOrg.setUpdUser(obj[4].toString());
//			try {
//				railConstOrg.setUpdDate(sdf.parse(obj[5].toString()));
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			railConstOrg.setEnableStatus(obj[6].toString());
//			railConstOrg.setFormOrg(Long.parseLong(obj[7].toString()));
//			railConstOrg.setFormOrgName(obj[8].toString());
//			resultList.add(railConstOrg);
//		}
//		return resultList;
		
		
		
		
		
	}

	@Override
	public String add(RailConstOrg railConstOrg) {
		try{
			super.save(railConstOrg);
			return Constants.SUCCESS_CODE;
		}catch(Exception e){
			e.printStackTrace();
			return Constants.FAILURE_CODE;
		}
	}

	@Override
	public String update(RailConstOrg railConstOrg) {
		try{
			super.update(railConstOrg);
			return Constants.SUCCESS_CODE;
		}catch(Exception e){
			e.printStackTrace();
			return Constants.FAILURE_CODE;
		}
	}

	@Override
	public RailConstOrg getById(String id) {
		String querySql = "from RailConstOrg where 1=1 AND id =" + id;
		return (RailConstOrg) getHibernateTemplate().find(querySql);
	}

}
