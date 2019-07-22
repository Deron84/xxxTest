package com.rail.bo.impl.work;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.huateng.system.util.CommonFunction;
import com.rail.bo.work.T130102BO;
import com.rail.po.work.RailWorkEmployee;
import com.rail.po.work.RailWorkTool;

/**  
* @author zhaozc
* @version 创建时间：2019年4月15日 上午9:23:37  
* @desc 作业工具管理
*/
public class T130102BOTarget extends HibernateDaoSupport implements T130102BO {

	/* (non-Javadoc)
	 * 保存作业工具
	 */
	@Override
	public void save(List<RailWorkTool> railWorkTools) throws Exception {
		if (railWorkTools!=null &&railWorkTools.size()>0) {
			for (RailWorkTool railWorkTool:railWorkTools) {
				this.getHibernateTemplate().save(railWorkTool);
			}
		}
	}

	/* (non-Javadoc)
	 * 更新工单跟工具关联 操作先删除之前的关联，再插入新的关联
	 */
	@Override
	public void update(List<RailWorkTool> railWorkTools) throws Exception {
		if (railWorkTools!=null &&railWorkTools.size()>0) {
			String workCode=railWorkTools.get(0).getWorkCode();
			String sql="delete from RailWorkTool where workCode='"+workCode+"'";
			CommonFunction.getCommQueryDAO().excute(sql);
			//然后执行插入操作
			save(railWorkTools);
		}
		
	}

}

