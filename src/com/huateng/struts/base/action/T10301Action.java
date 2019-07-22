/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-7-18       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2010 Huateng Software, Inc. All rights reserved.
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
package com.huateng.struts.base.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.huateng.bo.base.T10301BO;
import com.huateng.common.Constants;
import com.huateng.dao.common.SqlDao;
import com.huateng.po.TblRoleFuncMap;
import com.huateng.po.TblRoleFuncMapPK;
import com.huateng.po.TblRoleInf;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.GenerateNextId;

/**
 * Title:角色信息维护
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-7-18
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T10301Action extends BaseAction {
	
	private SqlDao dao = (SqlDao) ContextUtil.getBean("sqlDao");
	private T10301BO t10301BO = (T10301BO) ContextUtil.getBean("T10301BO");
	
	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute(){
		try {
			if("add".equals(method)) { // 新增角色信息
				rspCode = add();
			} else if("delete".equals(method)) { //删除角色信息
				rspCode = delete();
			} else if("update".equals(method)) { //同步角色信息
				rspCode = update();
			} else if("edit".equals(method)) {  // 更新权限信息
				rspCode = edit();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，角色信息维护" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	
	/**
	 * 添加角色信息
	 * @return
	 */
	private String add() {
		
		// 角色基本信息
		String roleId = GenerateNextId.getNextRoleId();
		
		TblRoleInf tblRoleInf = new TblRoleInf();
		
		tblRoleInf.setRoleId(Integer.parseInt(roleId));
		
		tblRoleInf.setRoleName(roleName.trim());
		
		tblRoleInf.setRoleType("0");
		
		if(isNotEmpty(description)){
			tblRoleInf.setDescription(description.trim());
		}else{
			tblRoleInf.setDescription("");
		}
		
		
		
		tblRoleInf.setRecCrtTs(CommonFunction.getCurrentDateTime());
		
		tblRoleInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
		
		tblRoleInf.setRecUpdOpr(operator.getOprId());
		
		String findsql = "SELECT COUNT(1) FROM tbl_role_inf WHERE trim(role_name)='"+ roleName.trim() + "'";
		int count = dao.queryForInt(findsql);
		if (count != 0) {
			return "角色:" + roleName + "已存在，请重新输入";
		}
		
		
		// 角色权限信息
		
		jsonBean.parseJSONArrayData(getMenuList());
		
		int len = jsonBean.getArray().size();
		
		List<TblRoleFuncMap> roleFuncMapList = new ArrayList<TblRoleFuncMap>(10);
		for(int i = 0; i < len; i++) {
			TblRoleFuncMap tblRoleFuncMap = new TblRoleFuncMap();
			TblRoleFuncMapPK tblRoleFuncMapPK = new TblRoleFuncMapPK();
			tblRoleFuncMapPK.setKeyId(tblRoleInf.getRoleId());
			tblRoleFuncMapPK.setValueId(Integer.parseInt(jsonBean.getJSONDataAt(i).get("valueId").toString()));
			tblRoleFuncMap.setId(tblRoleFuncMapPK);
			tblRoleFuncMap.setRecCrtTs(tblRoleInf.getRecCrtTs());
			tblRoleFuncMap.setRecUpdOpr(tblRoleInf.getRecUpdOpr());
			tblRoleFuncMap.setRecUpdTs(tblRoleInf.getRecUpdTs());
			roleFuncMapList.add(tblRoleFuncMap);
		}
		
		t10301BO.add(tblRoleInf, roleFuncMapList);
		log("添加角色信息维护成功。操作员编号：" + operator.getOprId()+ "，被添加角色编号：" + roleId);		
		return Constants.SUCCESS_CODE;
	}
	
	
	/**
	 * 删除权限信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String delete() {
		
		String sql = "select * from tbl_opr_info where opr_degree = " + roleId;
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		if(dataList.size() > 0) {
			return "该角色下已经存在操作员信息，无法删除";
		}
		t10301BO.delete(Integer.parseInt(roleId));
		log("删除角色信息维护成功。操作员编号：" + operator.getOprId()+ "，被删除操角色编号：" + roleId);
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 更新角色信息
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private String update() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		jsonBean.parseJSONArrayData(getRoleInfoList());
		
		int len = jsonBean.getArray().size();
		
		TblRoleInf tblRoleInf = null;
		
		List<TblRoleInf> tblRoleInfList = new ArrayList<TblRoleInf>(len);
		
		for(int i = 0; i < len; i++) {
			jsonBean.setObject(jsonBean.getJSONDataAt(i));
			
			tblRoleInf = new TblRoleInf();
			
			tblRoleInf.setRecUpdOpr(operator.getOprId());
			
			tblRoleInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
			
			BeanUtils.setObjectWithPropertiesValue(tblRoleInf, jsonBean, true);
			
			tblRoleInfList.add(tblRoleInf);
		}
		String findsql = "SELECT COUNT(1) FROM tbl_role_inf WHERE trim(role_name)='"+ tblRoleInf.getRoleName().trim() + "'  and role_Id!='"+ tblRoleInf.getRoleId() + "'    ";
		int count = dao.queryForInt(findsql);
		if (count != 0) {
			return "角色:" + tblRoleInf.getRoleName() + "已存在，请重新输入";
		}
		t10301BO.update(tblRoleInfList);
		log("更新角色信息维护成功。操作员编号：" + operator.getOprId()+ "，被添加角色编号：" + roleId);	
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 更新角色的权限信息
	 * @return
	 */
	public String edit() {
		
		jsonBean.parseJSONArrayData(getMenuList());
		
		int len = jsonBean.getArray().size();
		
		List<TblRoleFuncMap> roleFuncMapList = new ArrayList<TblRoleFuncMap>(len);
		
		TblRoleFuncMapPK tblRoleFuncMapPK = null;
		
		TblRoleFuncMap tblRoleFuncMap = null;
		
		for(int i = 0; i < len; i++) {
			
			tblRoleFuncMap = new TblRoleFuncMap();
			
			tblRoleFuncMapPK = new TblRoleFuncMapPK();
			
			tblRoleFuncMapPK.setKeyId(Integer.parseInt(roleId));
			
			tblRoleFuncMapPK.setValueId(Integer.parseInt(jsonBean.getJSONDataAt(i).getString("valueId")));
			
			tblRoleFuncMap.setId(tblRoleFuncMapPK);
			
			tblRoleFuncMap.setRecCrtTs(CommonFunction.getCurrentDateTime());
			
			tblRoleFuncMap.setRecUpdOpr(operator.getOprId());
			
			tblRoleFuncMap.setRecUpdTs(CommonFunction.getCurrentDateTime());
			
			roleFuncMapList.add(tblRoleFuncMap);
		}
		
		TblRoleInf role = new TblRoleInf();
		role = t10301BO.get(Integer.valueOf(roleId));
		role.setRecUpdOpr(operator.getOprId());
		role.setRecUpdTs(CommonFunction.getCurrentDateTime());
		if(role.getDescription() != null){
			role.setDescription(role.getDescription().trim());
		}
		t10301BO.saveOrUpdate(role);
		t10301BO.updateRoleMenu(roleFuncMapList,t10301BO.getMenuList(Integer.parseInt(roleId)));	
		log("更新角色的权限信息成功添加角色信息维护成功。操作员编号：" + operator.getOprId()+ "，被添加角色编号：" + roleId);			
		return Constants.SUCCESS_CODE;
	}
	private String roleId;
	
	private String roleName;
	
	private String roleType;
	
	private String description;
	
	private String menuList;
	
	private String roleInfoList;


	/**
	 * @return the roleId
	 */
	public String getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return the roleType
	 */
	public String getRoleType() {
		return roleType;
	}

	/**
	 * @param roleType the roleType to set
	 */
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the menuList
	 */
	public String getMenuList() {
		return menuList;
	}


	/**
	 * @param menuList the menuList to set
	 */
	public void setMenuList(String menuList) {
		this.menuList = menuList;
	}

	/**
	 * @return the roleInfoList
	 */
	public String getRoleInfoList() {
		return roleInfoList;
	}

	/**
	 * @param roleInfoList the roleInfoList to set
	 */
	public void setRoleInfoList(String roleInfoList) {
		this.roleInfoList = roleInfoList;
	}
	
	
}
