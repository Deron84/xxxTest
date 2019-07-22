package com.rail.bo.impl.tools;

import java.util.Date;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.huateng.system.util.CommonFunction;
import com.rail.bo.tools.T110501BO;
import com.rail.bo.tools.T110601BO;
import com.rail.po.tool.RailToolScrap;
import com.rail.po.tool.RailToolTransfer;

/**  
* @author zhaozc
* @version 创建时间：2019年4月15日 上午9:23:37  
* @desc 工具调库
*/
public class T110501BOTarget extends HibernateDaoSupport implements T110501BO {

	@Override
	public void save(RailToolTransfer railToolTransfer) throws Exception {
		this.getHibernateTemplate().save(railToolTransfer);
		StringBuffer sql=new StringBuffer();
		//修改工具对应的仓库标识
		sql.append("update RailToolInfo set whseCode='" + railToolTransfer.getWhseAfter()+"'");
		CommonFunction.getCommQueryDAO().excute(sql.toString());
		
	}


}

