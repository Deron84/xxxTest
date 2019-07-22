/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2011-6-14       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2011 Huateng Software, Inc. All rights reserved.
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
package com.huateng.dao.impl.mchnt;
import java.io.Serializable;
import java.util.List;

import com.huateng.po.mchnt.TblMchtBaseInf;
import com.huateng.po.mchnt.TblMerRoleFuncMap;
import com.huateng.po.mchnt.TblMerRoleFuncMapId;
import com.sdses.dao._RootDAO;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-14
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */
public class TblMerRoleFuncMapDAO extends _RootDAO<TblMchtBaseInf> implements com.huateng.dao.iface.mchnt.TblMerRoleFuncMapDAO{

	/* (non-Javadoc)
	 * @see com.huateng.dao._RootDAO#getReferenceClass()
	 */
	@Override
	protected Class getReferenceClass() {
		return TblMerRoleFuncMap.class;
	}

	/* (non-Javadoc)
	 * @see com.huateng.dao.iface.TblMerRoleFuncMapDAO#delete(java.lang.String)
	 */
	public void delete(TblMerRoleFuncMapId id) {
		super.delete((Object) load(id));
	}

	/* (non-Javadoc)
	 * @see com.huateng.dao.iface.TblMerRoleFuncMapDAO#delete(as.huateng.po.base.management.mer.TblMerRoleFuncMap)
	 */
	public void delete(TblMerRoleFuncMap tblMchntInfo) {
		super.delete((Object) tblMchntInfo);
	}

	/* (non-Javadoc)
	 * @see com.huateng.dao.iface.TblMerRoleFuncMapDAO#findAll()
	 */
	public List<TblMerRoleFuncMap> findAll() {
		return super.loadAll(null);
	}

	/* (non-Javadoc)
	 * @see com.huateng.dao.iface.TblMerRoleFuncMapDAO#get(java.lang.String)
	 */
	public TblMerRoleFuncMap get(TblMerRoleFuncMapId key) {
		return  (TblMerRoleFuncMap) super.get(getReferenceClass(), (Serializable) key);
	}

	/* (non-Javadoc)
	 * @see com.huateng.dao.iface.TblMerRoleFuncMapDAO#load(java.lang.String)
	 */
	public TblMerRoleFuncMap load(TblMerRoleFuncMapId key) {
		return (TblMerRoleFuncMap) load(getReferenceClass(), (Serializable) key);
	}

	/* (non-Javadoc)
	 * @see com.huateng.dao.iface.TblMerRoleFuncMapDAO#save(as.huateng.po.base.management.mer.TblMerRoleFuncMap)
	 */
	public TblMerRoleFuncMapId save(TblMerRoleFuncMap tblMchntInfo) {
		return (TblMerRoleFuncMapId) super.save(tblMchntInfo);
	}

	/* (non-Javadoc)
	 * @see com.huateng.dao.iface.TblMerRoleFuncMapDAO#saveOrUpdate(as.huateng.po.base.management.mer.TblMerRoleFuncMap)
	 */
	public void saveOrUpdate(TblMerRoleFuncMap tblMchntInfo) {
		super.saveOrUpdate(tblMchntInfo);
	}

	/* (non-Javadoc)
	 * @see com.huateng.dao.iface.TblMerRoleFuncMapDAO#update(as.huateng.po.base.management.mer.TblMerRoleFuncMap)
	 */
	public void update(TblMerRoleFuncMap tblMchntInfo) {
		super.update(tblMchntInfo);
		
	}

	/* (non-Javadoc)
	 * @see com.huateng.dao.iface.mchnt.TblMerRoleFuncMapDAO#merge(com.huateng.po.mchnt.TblMerRoleFuncMap)
	 */
	public void merge(TblMerRoleFuncMap tblRoleFuncMap) {
		// TODO Auto-generated method stub
		super.getSession().merge(tblRoleFuncMap);
	}

}
