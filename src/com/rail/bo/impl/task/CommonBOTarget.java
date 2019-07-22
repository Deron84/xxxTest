package com.rail.bo.impl.task;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.huateng.po.CstSysParam;
import com.rail.bo.task.CommonBO;

/**  
* @author syl
* @version 创建时间：2019年4月15日 上午9:23:37  
* @desc
*/
public class CommonBOTarget extends HibernateDaoSupport implements CommonBO {
	private static Logger logger = Logger.getLogger(CommonBOTarget.class);

	@Override
	public List<CstSysParam> getSysParam(String owner) {
		String hsql  = "from  CstSysParam t where t.id='"+owner+"'";
		List<CstSysParam> cstSysParams = this.getHibernateTemplate().find(hsql);
		logger.info("获取参数属主"+owner);
		return cstSysParams;
	}
	
		

}

