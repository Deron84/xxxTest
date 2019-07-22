package com.rail.bo.impl.tools;

import java.util.Date;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.rail.bo.enums.InfoSignEnums;
import com.rail.bo.enums.ToolsStatusEnums;
import com.rail.bo.tools.T110401BO;
import com.rail.bo.tools.T110601BO;
import com.rail.po.tool.RailToolInfo;
import com.rail.po.tool.RailToolScrap;

/**  
* @author zhaozc
* @version 创建时间：2019年4月15日 上午9:23:37  
* @desc 新增工具报废申请
*/
public class T110601BOTarget extends HibernateDaoSupport implements T110601BO {

	@Override
	public void save(RailToolScrap railToolScrap) throws Exception {
		
		this.getHibernateTemplate().save(railToolScrap);
	}

	@Override
	public void update(RailToolScrap railToolScrap) throws Exception {
		RailToolScrap railToolScrap2 = this.getHibernateTemplate().get(RailToolScrap.class, railToolScrap.getId());
		if(railToolScrap2==null){
			return;
		}
		railToolScrap2.setVerifyUser(railToolScrap.getVerifyUser());
		railToolScrap2.setVerifyMsg(railToolScrap.getVerifyMsg());
		railToolScrap2.setInfoSign(railToolScrap.getInfoSign());
		railToolScrap2.setVerifyDate(new Date());
		T110401BO t110401bo = (T110401BO)ContextUtil.getBean("T110401BO"); 
		if(Integer.valueOf(railToolScrap2.getInfoSign())==InfoSignEnums.YSE.getCode()){
			RailToolInfo info = t110401bo.getRailToolInfoByToolCode(railToolScrap2.getToolCode());
			info.setToolStatus(String.valueOf(ToolsStatusEnums.DEL.getCode()));
			this.getHibernateTemplate().update(info);
		}
		
		this.getHibernateTemplate().update(railToolScrap2);
	}

	@Override
	public RailToolScrap get(RailToolScrap railToolScrap) throws Exception {
		return  this.getHibernateTemplate().get(RailToolScrap.class,railToolScrap.getId());
		
	}


}

