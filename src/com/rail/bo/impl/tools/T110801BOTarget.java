package com.rail.bo.impl.tools;

import java.util.Date;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.rail.bo.tools.T110401BO;
import com.rail.bo.tools.T110801BO;
import com.rail.po.tool.RailToolInfo;
import com.rail.po.tool.RailToolMaintain;

/**  
* @author zhaozc
* @version 创建时间：2019年4月15日 上午9:23:37  
* @desc 新增工具检修保养
*/
public class T110801BOTarget extends HibernateDaoSupport implements T110801BO {
	@Override
	public void save(RailToolMaintain railToolMaintain) throws Exception {
		this.getHibernateTemplate().save(railToolMaintain);
		
//				tmpObj[8] = "新增维修之后要修改对应的工具表里面的最后维修时间";
		T110401BO t110401bo = (T110401BO)ContextUtil.getBean("T110401BO"); 
		RailToolInfo railToolInfo = t110401bo.getRailToolInfoByToolCode(railToolMaintain.getToolCode());
		railToolInfo.setLastExam(new Date());
		railToolInfo.setUpdDate(new Date());
		this.getHibernateTemplate().update(railToolInfo);
		//StringBuffer sql=new StringBuffer();
		//sql.append("update RailToolInfo set lastExam='"+new Date()+"'");
		//CommonFunction.getCommQueryDAO().excute(sql.toString());
	}

}

