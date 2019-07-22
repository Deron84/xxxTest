package com.huateng.bo.impl.base;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.huateng.bo.base.T10301BO;
import com.huateng.common.Constants;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.dao.iface.TblRoleFuncMapDAO;
import com.huateng.dao.iface.TblRoleInfDAO;
import com.huateng.po.TblRoleFuncMap;
import com.huateng.po.TblRoleInf;

/**
 * Title:角色菜单BO实现
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
public class T10301BOTarget implements T10301BO {
	
	private TblRoleInfDAO tblRoleInfDAO;
	
	private TblRoleFuncMapDAO tblRoleFuncMapDAO;
	
	private ICommQueryDAO commQueryDAO;
	

	/* (non-Javadoc)
	 * @see com.huateng.bo.T10301BO#add(com.huateng.po.TblRoleInf, java.util.List)
	 */
	public String add(TblRoleInf tblRoleInf,
			List<TblRoleFuncMap> tblRoleFuncMapList) {
		for(TblRoleFuncMap tblRoleFuncMap : tblRoleFuncMapList) {
			System.out.println(JSON.toJSONString(tblRoleFuncMap));
			tblRoleFuncMapDAO.save(tblRoleFuncMap);
		}
		tblRoleInfDAO.save(tblRoleInf);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.T10301BO#delete(int)
	 */
	public String delete(int roleId) {
		for(TblRoleFuncMap tblRoleFuncMap : getMenuList(roleId)) {
			tblRoleFuncMapDAO.delete(tblRoleFuncMap);
		}
		tblRoleInfDAO.delete(roleId);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.T10301BO#getMenuList(int)
	 */
	@SuppressWarnings("unchecked")
	public List<TblRoleFuncMap> getMenuList(int roleId) {
		
		String hql = "from com.huateng.po.TblRoleFuncMap t where t.Id.KeyId = " + roleId;
		
		return commQueryDAO.findByHQLQuery(hql);
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.T10301BO#update(java.util.List)
	 */
	public String update(List<TblRoleInf> tblRoleInfList) {
		for(TblRoleInf tblRoleInf : tblRoleInfList) {
			tblRoleInfDAO.update(tblRoleInf);
		}
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.T10301BO#updateRoleMenu(java.util.List, java.util.List)
	 */
	public String updateRoleMenu(List<TblRoleFuncMap> tblRoleFuncMapList,
			List<TblRoleFuncMap> tblRoleFuncMapDeleteList) {
		for(TblRoleFuncMap tblRoleFuncMap : tblRoleFuncMapDeleteList) {
			tblRoleFuncMapDAO.delete(tblRoleFuncMap);
		}
		
		for(TblRoleFuncMap tblRoleFuncMap : tblRoleFuncMapList) {
			tblRoleFuncMapDAO.save(tblRoleFuncMap);
		}
		return Constants.SUCCESS_CODE;
	}

	/**
	 * @return the tblRoleInfDAO
	 */
	public TblRoleInfDAO getTblRoleInfDAO() {
		return tblRoleInfDAO;
	}

	/**
	 * @param tblRoleInfDAO the tblRoleInfDAO to set
	 */
	public void setTblRoleInfDAO(TblRoleInfDAO tblRoleInfDAO) {
		this.tblRoleInfDAO = tblRoleInfDAO;
	}

	/**
	 * @return the tblRoleFuncMapDAO
	 */
	public TblRoleFuncMapDAO getTblRoleFuncMapDAO() {
		return tblRoleFuncMapDAO;
	}

	/**
	 * @param tblRoleFuncMapDAO the tblRoleFuncMapDAO to set
	 */
	public void setTblRoleFuncMapDAO(TblRoleFuncMapDAO tblRoleFuncMapDAO) {
		this.tblRoleFuncMapDAO = tblRoleFuncMapDAO;
	}

	/**
	 * @return the commQueryDAO
	 */
	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}

	/**
	 * @param commQueryDAO the commQueryDAO to set
	 */
	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}

	/* 
	 * 更新授权信息
	 * 
	 * @see com.huateng.bo.base.T10301BO#updateAuthorMenu(java.lang.String)
	 */
	public String updateAuthorMenu(String menus) throws Exception{

		//全部重置
		String sql = "update TBL_FUNC_INF set misc1 = '2' where trim(misc1) in ('1','2')";
		commQueryDAO.excute(sql);
		if (menus.length() >= 5) {//该判断保证in条件中的数据是有效的
			//需授权的交易
			sql = "update TBL_FUNC_INF set misc1 = '1' where func_id in " + menus;
			commQueryDAO.excute(sql);
		}
		
		return Constants.SUCCESS_CODE;
	}

	public TblRoleInf get(int roleId) {
		return tblRoleInfDAO.get(roleId);
	}

	public String saveOrUpdate(TblRoleInf tblRoleInf) {
		tblRoleInfDAO.saveOrUpdate(tblRoleInf);
		return Constants.SUCCESS_CODE;
	}
	
	
}
