package com.huateng.bo.impl.base;

import java.util.List;

import com.huateng.bo.base.T10205BO;
import com.huateng.dao.iface.base.ITblBankBinInfoDAO;
import com.huateng.po.TblBankBinInf;

/**
 * Title:交易码BO实现
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-9
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */
public class T10205BOTarget implements T10205BO {
	
	private ITblBankBinInfoDAO tblBankBinInfDAO;

	public ITblBankBinInfoDAO gettblBankBinInfDAO() {
		return tblBankBinInfDAO;
	}

	public void settblBankBinInfDAO(ITblBankBinInfoDAO tblBankBinInfDAO) {
		this.tblBankBinInfDAO = tblBankBinInfDAO;
	}

	/**
	 * 获取POSP卡BIN表结构信息
	 * 
	 * @param alias
	 *            cstFeeBranchInf-POSP卡BIN参数主键ID号
	 * @return TblCardInfoRmb-POSP卡BIN表结构对象
	 * @throws Exception
	 */
	public TblBankBinInf get(Integer id){
			TblBankBinInf tblCardInfoRmb = tblBankBinInfDAO.get(id);
			return tblCardInfoRmb;
	}

	/**
	 * 实现POSP卡BIN参数数据库信息新增
	 * 
	 * @param alias
	 *            cstFeeBranchInf-POSP卡BIN参数表结构对象
	 * @return
	 * @throws Exception
	 */
	public void createTblBankBinInf(TblBankBinInf tblBankBinInf){
			tblBankBinInfDAO.save(tblBankBinInf);
	}

	/**
	 * 实现POSP卡BIN参数数据库信息修改
	 * 
	 * @param alias
	 *            cstFeeBranchInf-POSP卡BIN参数表结构对象修改
	 * @return
	 * @throws Exception
	 */
	public void update(TblBankBinInf tblBankBinInf){
			tblBankBinInfDAO.saveOrUpdate(tblBankBinInf);
	}

	/**
	 * 实现POSP卡BIN参数数据库信息删除
	 * 
	 * @param alias
	 *            cstFeeBranchInf-POSP卡BIN主键ID号
	 * 
	 * @return
	 * @throws Exception
	 */
	public void delete(Integer id){
			tblBankBinInfDAO.delete(id);
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.base.T10205BO#update(java.util.List)
	 */
	public void update(List<TblBankBinInf> tblBankBinInfList){
		for(TblBankBinInf tblBankBinInf:tblBankBinInfList){
			this.update(tblBankBinInf);
		}
	}
	
	

	public ITblBankBinInfoDAO getTblBankBinInfDAO() {
		return tblBankBinInfDAO;
	}

	public void setTblBankBinInfDAO(ITblBankBinInfoDAO tblBankBinInfDAO) {
		this.tblBankBinInfDAO = tblBankBinInfDAO;
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.base.T10205BO#upload(java.util.List)
	 */
	public String upload(List<TblBankBinInf> tblBankBinInfList) {
		
		return null;
	}
}
