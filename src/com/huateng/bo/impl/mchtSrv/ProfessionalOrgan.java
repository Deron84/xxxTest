package com.huateng.bo.impl.mchtSrv;

import com.huateng.po.mchtSrv.TblProfessionalOrgan;

public interface ProfessionalOrgan {

	public String save(TblProfessionalOrgan tblProfessionalOrgan);
	
	public String modify(TblProfessionalOrgan tblProfessionalOrgan);
	
	public TblProfessionalOrgan get(String orgId);

	/**
	 * @param orgId
	 * @return
	 */
	public String delete(String orgId);
}
