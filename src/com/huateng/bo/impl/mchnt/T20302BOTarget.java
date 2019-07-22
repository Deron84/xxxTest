package com.huateng.bo.impl.mchnt;

import java.util.List;

import com.huateng.po.mchnt.TblInfMchntTpGrp;
import com.huateng.po.mchnt.TblInfMchntTpGrpPK;

import com.huateng.bo.mchnt.T20302BO;
import com.huateng.common.Constants;
import com.huateng.dao.iface.mchnt.TblMchntTpGrpDAO;


/**
 * Title:商户组别维护
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-10
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */
public class T20302BOTarget implements T20302BO {
	
	private TblMchntTpGrpDAO tblMchntTpGrpDAO;
	
	/* (non-Javadoc)
	 * @see com.huateng.bo.T20302BO#add(com.huateng.po.TblMchntTpGrp)
	 */
	public String add(TblInfMchntTpGrp TblInfMchntTpGrp) {
		tblMchntTpGrpDAO.save(TblInfMchntTpGrp);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.T20302BO#delete(java.lang.String)
	 */
	public String delete(TblInfMchntTpGrpPK id) {
		tblMchntTpGrpDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.T20302BO#delete(com.huateng.po.TblInfMchntTpGrp)
	 */
	public String delete(TblInfMchntTpGrp TblInfMchntTpGrp) {
		tblMchntTpGrpDAO.delete(TblInfMchntTpGrp);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.T20302BO#get(java.lang.String)
	 */
	public TblInfMchntTpGrp get(TblInfMchntTpGrpPK id) {
		return tblMchntTpGrpDAO.get(id);
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.T20302BO#update(com.huateng.po.TblInfMchntTpGrp)
	 */
	public String update(TblInfMchntTpGrp TblInfMchntTpGrp) {
		tblMchntTpGrpDAO.update(TblInfMchntTpGrp);
		return Constants.SUCCESS_CODE;
	}
	
	/* (non-Javadoc)
	 * @see com.huateng.bo.T20302BO#update(java.util.List)
	 */
	public String update(List<TblInfMchntTpGrp> tblMchntTpGrpList) {
		for(TblInfMchntTpGrp TblInfMchntTpGrp : tblMchntTpGrpList) {
			update(TblInfMchntTpGrp);
		}
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * @return the tblMchntTpGrpDAO
	 */
	public TblMchntTpGrpDAO getTblMchntTpGrpDAO() {
		return tblMchntTpGrpDAO;
	}

	/**
	 * @param tblMchntTpGrpDAO the tblMchntTpGrpDAO to set
	 */
	public void setTblMchntTpGrpDAO(TblMchntTpGrpDAO tblMchntTpGrpDAO) {
		this.tblMchntTpGrpDAO = tblMchntTpGrpDAO;
	}
}
