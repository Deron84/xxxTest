package com.rail.bo.impl.tools;

import java.util.Date;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.rail.bo.enums.ApplyTypeEnums;
import com.rail.bo.enums.InfoSignEnums;
import com.rail.bo.enums.ToolsStatusEnums;
import com.rail.bo.tools.T110401BO;
import com.rail.po.tool.RailToolInfo;
import com.rail.po.tool.RailToolRepair;
import com.rail.po.tool.RailToolScrap;

/**  
* @author zhaozc
* @version 创建时间：2019年4月15日 上午9:23:37  
* @desc 新增工具维修申请
*/
public class T110401BOTarget extends HibernateDaoSupport implements T110401BO {


	@Override
	public void save(RailToolRepair railToolRepair) throws Exception {
		this.getHibernateTemplate().save(railToolRepair);
	}

	@Override
	public void update(RailToolRepair railToolRepair) throws Exception {
		RailToolRepair railToolRepair2 = this.getHibernateTemplate().get(RailToolRepair.class, railToolRepair.getId());
		railToolRepair2.setVerifyUser(railToolRepair.getVerifyUser());
		railToolRepair2.setVerifyMsg(railToolRepair.getVerifyMsg());
		railToolRepair2.setInfoSign(railToolRepair.getInfoSign());
		railToolRepair2.setVerifyDate(new Date());
		railToolRepair2.setNote1(String.valueOf(ApplyTypeEnums.STRAT.getCode()));
		//维记录句的类型为  申请维修 在 工具改为 维修状态   完成状态 则为 正常
		if(Integer.valueOf(railToolRepair.getInfoSign())==InfoSignEnums.YSE.getCode()){
			RailToolInfo info = getRailToolInfoByToolCode(railToolRepair2.getToolCode());
			if(Integer.valueOf(railToolRepair2.getNote1())==ApplyTypeEnums.STRAT.getCode()){
				info.setToolStatus(String.valueOf(ToolsStatusEnums.REPAIE.getCode()));
			}else{
				info.setToolStatus(String.valueOf(ToolsStatusEnums.NORMAL.getCode()));
			}
			this.getHibernateTemplate().update(info);
		}
		
		this.getHibernateTemplate().update(railToolRepair2);
		
	}

	@Override
	public RailToolRepair get(RailToolRepair railToolRepair) throws Exception {
		return this.getHibernateTemplate().get(RailToolRepair.class,railToolRepair.getId());
	}

	@Override
	public RailToolInfo getRailToolInfoByToolCode(String toolCode) throws Exception {
		String queryString = "from RailToolInfo t where t.toolCode = '"+toolCode+"'";
		List<RailToolInfo> railToolInfos = this.getHibernateTemplate().find(queryString);
		if(railToolInfos!=null && railToolInfos.size()>0){
			return railToolInfos.get(0);
		}
		return null;
	}

	@Override
	public void updateComplete(RailToolRepair railToolRepair) throws Exception {
		RailToolRepair railToolRepair2 = this.getHibernateTemplate().get(RailToolRepair.class, railToolRepair.getId());
//		railToolRepair2.setVerifyMsg(railToolRepair.getApplyMsg());
		railToolRepair2.setInfoSign(String.valueOf(InfoSignEnums.COMPLETE.getCode()));
		railToolRepair2.setApplyDate(new Date());
		this.getHibernateTemplate().update(railToolRepair2);
		RailToolInfo railToolInfo = getRailToolInfoByToolCode(railToolRepair2.getToolCode());
		railToolInfo.setToolStatus(String.valueOf(ToolsStatusEnums.NORMAL.getCode()));
		railToolInfo.setUpdDate(new Date());
		this.getHibernateTemplate().update(railToolInfo);
		
	}


}

