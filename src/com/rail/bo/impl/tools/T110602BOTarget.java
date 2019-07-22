package com.rail.bo.impl.tools;

import java.util.Date;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.huateng.system.util.CommonFunction;
import com.rail.bo.tools.T110602BO;
import com.rail.po.tool.RailToolScrap;

/**
 * Title: T110701BOTarget
 * Description:  报废申请审核
 * @author Zhaozc 
 * @date 下午3:05:11 
 */
public class T110602BOTarget extends HibernateDaoSupport implements T110602BO {

	@Override
	public void update(RailToolScrap railToolScrap) throws Exception {
		StringBuffer sql=new StringBuffer();
		//根据确认的信息进行更新railToolScrap表
		sql.append("update RailToolScrap set verifyUser='"+railToolScrap.getVerifyUser()+"'");
		sql.append(",verifyDate="+new Date());
		sql.append(",verifyMsg='"+railToolScrap.getVerifyMsg()+"'");
		sql.append(",infoSign='"+railToolScrap.getInfoSign()+"'");
		sql.append(" where id="+railToolScrap.getId());
		
		CommonFunction.getCommQueryDAO().excute(sql.toString());
		sql=new StringBuffer();
		if (railToolScrap.getInfoSign() != null && !railToolScrap.getInfoSign().equals("")) {
			if (railToolScrap.getInfoSign().equals("1")) {
//				tmpObj[8] = "审核通过";
				sql.append("update RailToolInfo set toolStatus='3'");
				CommonFunction.getCommQueryDAO().excute(sql.toString());
			} 
		}
	}
}

