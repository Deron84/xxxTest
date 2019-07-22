/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-6-8       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2008 Huateng Software, Inc. All rights reserved.
 *
 *       This software is the confidential and proprietary information of
 *       Shanghai HUATENG Software Co., Ltd. ("Confidential Information").
 *       You shall not disclose such Confidential Information and shall use it
 *       only in accordance with the terms of the license agreement you entered
 *       into with Huateng.
 *
 * Warning:
 * =============================================================================
 *
 */
package com.sdses.dao.impl;

import java.util.List;

import com.sdses.dao.TblTxnInfoDAO;
import com.sdses.dao.iface.TblTxnInfoDAO2;
import com.sdses.po.TblTxnInfo;
import com.sdses.po.TblTxnInfoPK;

/**
 * Title:交易流水
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-6-8
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class TblTxnInfoDAOTarget implements TblTxnInfoDAO2 {
	private TblTxnInfoDAO txnInfoDAO;

	/* (non-Javadoc)
	 * @see com.huateng.dao.iface.TblTxnInfoDAO#delete(com.huateng.po.TblTxnInfoPK)
	 */
	public void delete(TblTxnInfoPK id) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.huateng.dao.iface.TblTxnInfoDAO#delete(com.huateng.po.TblTxnInfo)
	 */
	public void delete(TblTxnInfo tblTxnInfo) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.huateng.dao.iface.TblTxnInfoDAO#findAll()
	 */
	public List<TblTxnInfo> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.huateng.dao.iface.TblTxnInfoDAO#get(com.huateng.po.TblTxnInfoPK)
	 */
	public TblTxnInfo get(TblTxnInfoPK key) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.huateng.dao.iface.TblTxnInfoDAO#load(com.huateng.po.TblTxnInfoPK)
	 */
	public TblTxnInfo load(TblTxnInfoPK key) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.huateng.dao.iface.TblTxnInfoDAO#save(com.huateng.po.TblTxnInfo)
	 */
	public TblTxnInfoPK save(TblTxnInfo tblTxnInfo) {
		return this.txnInfoDAO.save(tblTxnInfo);
	}

	/* (non-Javadoc)
	 * @see com.huateng.dao.iface.TblTxnInfoDAO#saveOrUpdate(com.huateng.po.TblTxnInfo)
	 */
	public void saveOrUpdate(TblTxnInfo tblTxnInfo) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.huateng.dao.iface.TblTxnInfoDAO#update(com.huateng.po.TblTxnInfo)
	 */
	public void update(TblTxnInfo tblTxnInfo) {
		// TODO Auto-generated method stub

	}

    public TblTxnInfoDAO getTxnInfoDAO() {
        return txnInfoDAO;
    }

    public void setTxnInfoDAO(TblTxnInfoDAO txnInfoDAO) {
        this.txnInfoDAO = txnInfoDAO;
    }

}
