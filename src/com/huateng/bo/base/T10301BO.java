package com.huateng.bo.base;

import java.util.List;

import com.huateng.po.TblRoleFuncMap;
import com.huateng.po.TblRoleInf;

public interface T10301BO {
	public TblRoleInf get(int roleId);
	public String add(TblRoleInf tblRoleInf,List<TblRoleFuncMap> tblRoleFuncMapList);
	public String delete(int roleId);
	public String update(List<TblRoleInf> tblRoleInfList);
	public String saveOrUpdate(TblRoleInf tblRoleInf);
	public List<TblRoleFuncMap> getMenuList(int roleId);
	public String updateRoleMenu(List<TblRoleFuncMap> tblRoleFuncMapList,
			List<TblRoleFuncMap> tblRoleFuncMapDeleteList);
	public String updateAuthorMenu(String menus) throws Exception;
}
