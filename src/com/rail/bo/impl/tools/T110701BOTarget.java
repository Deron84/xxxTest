package com.rail.bo.impl.tools;

import java.util.Date;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.huateng.system.util.CommonFunction;
import com.rail.bo.enums.InfoSignEnums;
import com.rail.bo.enums.InfoSignEnums2;
import com.rail.bo.tools.T110701BO;
import com.rail.po.tool.RailToolMaintainWarn;

/**
 * Title: T110701BOTarget
 * Description:  
 * @author Zhaozc 
 * @date 下午3:05:11 
 */
public class T110701BOTarget extends HibernateDaoSupport implements T110701BO {

	/* (non-Javadoc)
	 * @ 更新审核特定的工具预警信息
	 */
	@Override
	public void update(RailToolMaintainWarn railToolMaintainWarn) throws Exception {
		RailToolMaintainWarn find  = this.getHibernateTemplate().get(RailToolMaintainWarn.class, railToolMaintainWarn.getId());
		find.setVerifyDate(new Date());
		find.setVerifyMsg(railToolMaintainWarn.getVerifyMsg());
		find.setVerifyUser(railToolMaintainWarn.getVerifyUser());
		find.setInfoSign(String.valueOf(InfoSignEnums2.YSE.getCode()));
		this.getHibernateTemplate().update(find);
		
//		StringBuffer sql=new StringBuffer();
//		//根据确认的信息进行更新RailAccessMaintainWarn表
//		sql.append("update RailToolMaintainWarn set verifyUser='"+railToolMaintainWarn.getVerifyUser()+"'");
//		sql.append(",verifyDate="+new Date());
//		sql.append(",verifyMsg='"+railToolMaintainWarn.getVerifyMsg()+"'");
//		sql.append(",infoSign='1'");
//		sql.append(" where id="+railToolMaintainWarn.getId());
//		
//		CommonFunction.getCommQueryDAO().excute(sql.toString());
	}


}

