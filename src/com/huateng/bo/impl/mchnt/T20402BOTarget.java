package com.huateng.bo.impl.mchnt;

import java.util.List;

import com.huateng.bo.mchnt.T20402BO;
import com.huateng.common.Constants;
import com.huateng.dao.iface.mchtSrv.TblMchtCupInfoDAO;
import com.huateng.po.TblMchtCupInfo;

public class T20402BOTarget implements T20402BO {
	
	private TblMchtCupInfoDAO tblMchtCupInfoDAO;
	
	
	/* (non-Javadoc)
	 * @see com.huateng.bo.T10202BO#add(com.huateng.po.TblMchtCupInfo)
	 */
	public String add(TblMchtCupInfo tblMchtCupInfo) {
		tblMchtCupInfoDAO.save(tblMchtCupInfo);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.T10202BO#delete(com.huateng.po.TblMchtCupInfo)
	 */
	public String delete(TblMchtCupInfo tblMchtCupInfo) {
		tblMchtCupInfoDAO.delete(tblMchtCupInfo);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.T10202BO#delete(com.huateng.po.TblMchtCupInfo)
	 */
	public String delete(String id) {
		tblMchtCupInfoDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.T10202BO#get(com.huateng.po.TblMchtCupInfoPK)
	 */
	public TblMchtCupInfo get(String id) {
		return tblMchtCupInfoDAO.get(id);
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.T10202BO#update(java.util.List)
	 */
	public String update(List<TblMchtCupInfo> tblMchtCupInfoList) {
		for(TblMchtCupInfo tblMchtCupInfo : tblMchtCupInfoList) {
			tblMchtCupInfoDAO.update(tblMchtCupInfo);
		}
		return Constants.SUCCESS_CODE;
	}

	/**
	 * @return the tblMchtCupInfoDAO
	 */
	public TblMchtCupInfoDAO getTblMchtCupInfoDAO() {
		return tblMchtCupInfoDAO;
	}

	/**
	 * @param tblMchtCupInfoDAO the tblMchtCupInfoDAO to set
	 */
	public void setTblMchtCupInfoDAO(TblMchtCupInfoDAO tblMchtCupInfoDAO) {
		this.tblMchtCupInfoDAO = tblMchtCupInfoDAO;
	}

	public String cancel(TblMchtCupInfo tblMchtCupInfo) {
		tblMchtCupInfoDAO.update(tblMchtCupInfo);
		return Constants.SUCCESS_CODE;
	}

	public String saveOrUpdate(TblMchtCupInfo tblMchtCupInfo) {
		tblMchtCupInfoDAO.saveOrUpdate(tblMchtCupInfo);
		return Constants.SUCCESS_CODE;
	}

	
}