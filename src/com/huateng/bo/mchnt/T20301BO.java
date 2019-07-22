package com.huateng.bo.mchnt;

import java.util.List;

import com.huateng.exception.AppException;
import com.huateng.po.mchnt.TblMerRoleFuncMap;
import com.huateng.po.mchnt.TblMerRoleFuncMapId;

public interface T20301BO {

	public void addRoleFuncMap(List<TblMerRoleFuncMap> tblRoleFuncMapList) ;
	public void updateRoleFuncMap(List<TblMerRoleFuncMap> tblMerRoleFuncMapList,List<TblMerRoleFuncMapId> tblMerRoleFuncMapIdList) ;
	public List<TblMerRoleFuncMap> getAllRoleMapId(String mchntTp);
	public List<Object> getRoleMap(String mchntTp);
}
