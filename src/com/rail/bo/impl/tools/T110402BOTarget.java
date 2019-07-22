package com.rail.bo.impl.tools;

import java.util.Date;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.huateng.system.util.CommonFunction;
import com.rail.bo.tools.T110402BO;
import com.rail.po.tool.RailToolRepair;
import com.rail.po.tool.RailToolScrap;

/**
 * Title: T110701BOTarget
 * Description:  维修申请审核
 * @author Zhaozc 
 * @date 下午3:05:11 
 */
public class T110402BOTarget extends HibernateDaoSupport implements T110402BO {

	@Override
	public void update(RailToolRepair railToolRepair) throws Exception {
		StringBuffer sql=new StringBuffer();
		//根据确认的信息进行更新railToolScrap表
		sql.append("update RailToolRepair set verifyUser='"+railToolRepair.getVerifyUser()+"'");
		sql.append(",verifyDate="+new Date());
		sql.append(",verifyMsg='"+railToolRepair.getVerifyMsg()+"'");
		sql.append(",infoSign='"+railToolRepair.getInfoSign()+"'");
		sql.append(" where id="+railToolRepair.getId());
		
		CommonFunction.getCommQueryDAO().excute(sql.toString());
		sql=new StringBuffer();
		if (railToolRepair.getInfoSign() != null && !railToolRepair.getInfoSign().equals("")) {
			if (railToolRepair.getInfoSign().equals("1")) {
//				tmpObj[8] = "审核通过";状态变成维修状态
				sql.append("update RailToolInfo set toolStatus='1'");
				CommonFunction.getCommQueryDAO().excute(sql.toString());
			} 
		}
	}
}

