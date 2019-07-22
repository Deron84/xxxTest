package com.huateng.bo.impl.mchtSrv;

import com.huateng.common.Constants;
import com.huateng.dao.iface.mchtSrv.TblProfessionalOrganDAO;
import com.huateng.po.mchtSrv.TblProfessionalOrgan;
import com.huateng.struts.mchtSrv.ProfessionalOrganConstants;

public class ProfessionalOrganImpl implements ProfessionalOrgan {

	TblProfessionalOrganDAO tblProfessionalOrganDAO;

	public void setTblProfessionalOrganDAO(
			TblProfessionalOrganDAO tblProfessionalOrganDAO) {
		this.tblProfessionalOrganDAO = tblProfessionalOrganDAO;
	}

	public String save(TblProfessionalOrgan tblProfessionalOrgan) {
		if(tblProfessionalOrgan == null)
			return ProfessionalOrganConstants.T70301_02;
		if(tblProfessionalOrganDAO.get(tblProfessionalOrgan.getOrgId()) != null)
			return ProfessionalOrganConstants.T70301_01;
		tblProfessionalOrganDAO.save(tblProfessionalOrgan);
		return Constants.SUCCESS_CODE;
	}

	public String modify(TblProfessionalOrgan tblProfessionalOrgan) {
		tblProfessionalOrganDAO.update(tblProfessionalOrgan);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.impl.mchtSrv.ProfessionalOrgan#query(java.lang.String)
	 */
	public TblProfessionalOrgan get(String orgId) {
		// TODO Auto-generated method stub
		return tblProfessionalOrganDAO.get(orgId);
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.impl.mchtSrv.ProfessionalOrgan#delete(java.lang.String)
	 */
	public String delete(String orgId) {
		// TODO Auto-generated method stub
		tblProfessionalOrganDAO.delete(orgId);
		return Constants.SUCCESS_CODE;
	}
}
