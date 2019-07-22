package com.rail.bo.impl.work;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.rail.bo.work.T130402BO;
import com.rail.po.base.RailEmployee;

/**
 * Title: T110701BOTarget
 * Description:  人员信息维护
 * @author Zhaozc 
 * @date 下午3:05:11 
 */
public class T130402BOTarget extends HibernateDaoSupport implements T130402BO {

	/* (non-Javadoc)
	 * @ 人员信息维护
	 */
	@Override
	public void update(RailEmployee railEmployee) throws Exception {
		this.getHibernateTemplate().saveOrUpdate(railEmployee);
	}



}

