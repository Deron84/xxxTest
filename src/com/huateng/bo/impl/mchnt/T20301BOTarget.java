package com.huateng.bo.impl.mchnt;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.huateng.bo.mchnt.T20301BO;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.dao.iface.mchnt.TblMerRoleFuncMapDAO;
import com.huateng.po.mchnt.TblMerRoleFuncMap;
import com.huateng.po.mchnt.TblMerRoleFuncMapId;

/**
 * Title:商户权限维护
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-10
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author liuxianxian
 * 
 * @version 1.0
 */
public class T20301BOTarget implements T20301BO {

private TblMerRoleFuncMapDAO tblMerRoleFuncMapDAO;
private ICommQueryDAO commQueryDAO;
	
	public void addRoleFuncMap(List<TblMerRoleFuncMap> tblRoleFuncMapList) {
		for(TblMerRoleFuncMap tblMerRoleFuncMap : tblRoleFuncMapList) {
			this.tblMerRoleFuncMapDAO.save(tblMerRoleFuncMap);
		}
	}

	public void updateRoleFuncMap(List<TblMerRoleFuncMap> tblMerRoleFuncMapList,
			List<TblMerRoleFuncMapId> tblMerRoleFuncMapIdList) {
		for(TblMerRoleFuncMapId tblMerRoleFuncMapId : tblMerRoleFuncMapIdList) {
			this.tblMerRoleFuncMapDAO.delete(tblMerRoleFuncMapId);
		}
		for(TblMerRoleFuncMap tblRoleFuncMap : tblMerRoleFuncMapList) {
			this.tblMerRoleFuncMapDAO.merge(tblRoleFuncMap);
//			this.tblMerRoleFuncMapDAO.saveOrUpdate(tblRoleFuncMap);
		}
		
	}

	public TblMerRoleFuncMapDAO getTblRoleFuncMapDAO() {
		return tblMerRoleFuncMapDAO;
	}

	public List<TblMerRoleFuncMap> getAllRoleMapId(String mchntTp){
		String hql = " from "+TblMerRoleFuncMap.class+" tblMerRoleFuncMap where tblMerRoleFuncMap.id.keyId = " + mchntTp;
		return commQueryDAO.findByHQLQuery(hql);
	}
	
	public List<Object> getRoleMap(String mchntTp){
		String sql = "select VALUE_ID from TBL_MER_ROLE_FUNC where KEY_ID = " + mchntTp;
		return commQueryDAO.findBySQLQuery(sql);
	}

	public void setTblMerRoleFuncMapDAO(TblMerRoleFuncMapDAO tblMerRoleFuncMapDAO) {
		this.tblMerRoleFuncMapDAO = tblMerRoleFuncMapDAO;
	}

	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}

	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}

	public TblMerRoleFuncMapDAO getTblMerRoleFuncMapDAO() {
		return tblMerRoleFuncMapDAO;
	}
	
	
}
