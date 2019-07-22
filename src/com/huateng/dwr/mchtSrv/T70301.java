package com.huateng.dwr.mchtSrv;

import com.huateng.bo.impl.mchtSrv.ProfessionalOrgan;
import com.huateng.dao.common.SqlDao;
import com.huateng.po.mchtSrv.TblProfessionalOrgan;
import com.huateng.system.util.ContextUtil;


/**
 * project JSBConsole
 * date 2013-3-7
 * @author 樊东东
 */
public class T70301 {
	
	public TblProfessionalOrgan getThirdOrgInfo(String orgId){
		TblProfessionalOrgan org = null;
		try{
			ProfessionalOrgan service = (ProfessionalOrgan) ContextUtil.getBean("professionalOrganSrv");
			org = service.get(orgId);
		}catch(Exception e){
			e.printStackTrace();
		}
		return org;
	}
	
	public String del(String orgId){

		try{
			SqlDao dao = (SqlDao)ContextUtil.getBean("sqlDao");
			String sql = "delete TBL_PROFESSIONAL_ORGAN where org_id='"+orgId+"'";
			dao.execute(sql);
			return "success";
		}catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
		
	
	}
}
