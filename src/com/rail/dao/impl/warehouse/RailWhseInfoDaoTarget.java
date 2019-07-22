package com.rail.dao.impl.warehouse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import com.huateng.common.Constants;
import com.huateng.system.util.CommonFunction;
import com.rail.dao.iface.warehouse.RailWhseInfoDao;
import com.rail.po.warehouse.RailWhseInfo;
import com.sdses.dao._RootDAO;

public class RailWhseInfoDaoTarget extends _RootDAO<RailWhseInfo> implements RailWhseInfoDao{
	
	@Override
	protected Class<RailWhseInfo> getReferenceClass() {
		return RailWhseInfo.class;
	}
	/**
	 * 根据仓库编码查询仓库信息
	 */
	@Override
	public List<RailWhseInfo> getByCode(String whseCode) {
		String querySql ="from RailWhseInfo where 1=1 AND WHSE_CODE='"+whseCode+"'";
		
		return getHibernateTemplate().find(querySql);
	}
	
	/**
	 * 根据不同参数条件查询仓库
	 */
	@Override
	public List<RailWhseInfo> getByParam(Map<String, String> paramMap) {
		String querySql ="SELECT RWI.WHSE_CODE,RWI.WHSE_NAME,RWI.WHSE_ADDRESS,RWI.WHSE_CAPA,RWI.WHSE_PIC,RWI.WHSE_TEL,"
				+ "TBI.BRH_NAME,RWI.ENABLE_STATUS,RWI.WHSE_DEPT,RWI.PARENT_WHSE_CODE,RWI2.WHSE_NAME AS parentWhse "
				+ "FROM RAIL_WHSE_INFO RWI LEFT JOIN TBL_BRH_INFO TBI ON TBI.BRH_ID = RWI.WHSE_DEPT "
				+ "LEFT JOIN RAIL_WHSE_INFO RWI2 ON RWI2.WHSE_CODE = rwi.PARENT_WHSE_CODE WHERE 1=1 ";
		for(String key : paramMap.keySet()){
			String value = paramMap.get(key);
			querySql+=" and rwi."+key+"='"+value+"'";
		}
		List list =CommonFunction.getCommQueryDAO().findBySQLQuery(querySql);
		List<RailWhseInfo> resultList = new ArrayList<RailWhseInfo>();
		//0,仓库代码，1，仓库名称，2仓库地址，3仓库容量，4仓库负责人，5联系电话，6机构名称，7仓库状态，8仓库机构标识，9父级仓库标识，10父级仓库名称
		 for (int i = 0; i < list.size(); i++) {  
			Object[] obj = (Object[])list.get(i);  
			RailWhseInfo railWhseInfo = new RailWhseInfo();
			railWhseInfo.setWhseCode(obj[0].toString());
			railWhseInfo.setWhseName(obj[1].toString());
			railWhseInfo.setWhseAddress(obj[2].toString());
//			railWhseInfo.setWhseRank(obj[3].toString());
			railWhseInfo.setWhseCapa(obj[3].toString());
			railWhseInfo.setWhsePic(obj[4].toString());
			railWhseInfo.setWhseTel(obj[5].toString());
			railWhseInfo.setWhseBrhName(obj[6].toString());
			railWhseInfo.setEnableStatus(obj[7].toString());
			railWhseInfo.setWhseDept(obj[8].toString());
			if(obj[9]!=null&&!obj[9].equals("")){
				railWhseInfo.setParentWhseCode(obj[9].toString());
			}else{
				railWhseInfo.setParentWhseCode("");
			}
			if(obj[10]!=null&&!obj[10].equals("")){
				railWhseInfo.setParentWhse(obj[10].toString());
			}else{
				railWhseInfo.setParentWhse("无");
			}
			resultList.add(railWhseInfo);
		}
		return resultList;
	}
	/**
	 * 新增仓库信息
	 */
	@Override
	public String add(RailWhseInfo railWhseInfo) {
		try {
			super.save(railWhseInfo);
			return Constants.SUCCESS_CODE;
		} catch (Exception e) {
			return Constants.FAILURE_CODE;
		}
	}
	/**
	 * 修改仓库信息
	 */
	@Override
	public String updateWhse(RailWhseInfo railWhseInfo) {
		try {
			super.update(railWhseInfo);
			return Constants.SUCCESS_CODE;
		} catch (Exception e) {
			return Constants.FAILURE_CODE;
		}
	}
}
