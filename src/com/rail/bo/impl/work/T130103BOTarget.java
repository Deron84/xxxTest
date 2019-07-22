package com.rail.bo.impl.work;

import java.util.Date;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.huateng.system.util.CommonFunction;
import com.rail.bo.work.T130103BO;
import com.rail.dao.iface.warehouse.RailWhseInfoDao;
import com.rail.dao.iface.work.RailWorkEmployeeDao;
import com.rail.dao.iface.work.RailWorkInfoDao;
import com.rail.po.work.RailWorkEmployee;
import com.rail.po.work.RailWorkInfo;

/**
 * Title: T110701BOTarget
 * Description:  工单信息审核
 * @author Zhaozc 
 * @date 下午3:05:11 
 */
public class T130103BOTarget implements T130103BO {
	
	private RailWorkEmployeeDao railWorkEmployeeDao ;

	public RailWorkEmployeeDao getRailWorkEmployeeDao() {
		return railWorkEmployeeDao;
	}

	public void setRailWorkEmployeeDao(RailWorkEmployeeDao railWorkEmployeeDao) {
		this.railWorkEmployeeDao = railWorkEmployeeDao;
	}
	/**
	 * 
	 */
	@Override
	public RailWorkInfo getWorkInfoByCode(String workCode) {
		
		return null;
	}

	

	/* (non-Javadoc)
	 * @ 工单信息审核
	 */
//	@Override
//	public void update(RailWorkInfo railWorkInfo) throws Exception {
//		StringBuffer sql=new StringBuffer();
//		//根据确认的信息进行更新RailWorkInfo表
//		sql.append("update RailWorkInfo set auditUser='"+railWorkInfo.getAuditUser()+"'");
//		sql.append(",auditDate="+new Date());
//		sql.append(",auditMsg='"+railWorkInfo.getAuditMsg()+"'");
//		sql.append(" where workCode="+railWorkInfo.getWorkCode());
//		
//		CommonFunction.getCommQueryDAO().excute(sql.toString());
//	}



}

