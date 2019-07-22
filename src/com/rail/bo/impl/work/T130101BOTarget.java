package com.rail.bo.impl.work;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.huateng.system.util.CommonFunction;
import com.rail.bo.work.T130101BO;
import com.rail.po.work.RailWorkEmployee;

/**  
* @author zhaozc
* @version 创建时间：2019年4月15日 上午9:23:37  
* @desc 作业单元管理
*/
public class T130101BOTarget extends HibernateDaoSupport implements T130101BO {

	/* (non-Javadoc)
	 * @see com.rail.bo.work.T130101BO#save(java.util.List)
	 * 保存业务单元
	 */
	@Override
	public void save(List<RailWorkEmployee> railWorkEmployees) throws Exception {
		if (railWorkEmployees!=null &&railWorkEmployees.size()>0) {
			for (RailWorkEmployee railWorkEmployee:railWorkEmployees) {
				this.getHibernateTemplate().save(railWorkEmployee);
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.rail.bo.work.T130101BO#update(java.util.List)
	 * 更新工单跟人员关联 操作先删除之前的关联，再插入新的关联
	 */
	@Override
	public void update(List<RailWorkEmployee> railWorkEmployees) throws Exception {
		if (railWorkEmployees!=null &&railWorkEmployees.size()>0) {
			String workCode=railWorkEmployees.get(0).getWorkCode();
			String sql="delete from RailWorkEmployee where workCode='"+workCode+"'";
			CommonFunction.getCommQueryDAO().excute(sql);
			//然后执行插入操作
			save(railWorkEmployees);
		}
		
	}

}

