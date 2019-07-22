package com.huateng.struts.mchnt.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.huateng.bo.mchnt.T20301BO;
import com.huateng.common.Constants;
import com.huateng.exception.AppException;
import com.huateng.po.mchnt.TblInfMchntTp;
import com.huateng.po.mchnt.TblInfMchntTpPK;
import com.huateng.po.mchnt.TblMerRoleFuncMap;
import com.huateng.po.mchnt.TblMerRoleFuncMapId;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

/**
 * Title:商户角色信息维护
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-7-18
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T20301Action extends BaseAction {

	private T20301BO t20301BO = (T20301BO) ContextUtil.getBean("T20301BO");

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		if ("add".equals(method)) { // 新增角色信息
			rspCode = add();
		} else if ("delete".equals(method)) { // 删除角色信息
			rspCode = delete();
		} else if ("update".equals(method)) { // 同步角色信息
			rspCode = update();
		} else if ("edit".equals(method)) { // 更新权限信息
			rspCode = edit();
		}

		return rspCode;
	}

	/**
	 * 添加角色信息
	 * 
	 * @return
	 */
	private String add() {
		return Constants.SUCCESS_CODE;
	}

	/**
	 * 删除权限信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String delete() {
		return Constants.SUCCESS_CODE;
	}

	/**
	 * 更新角色信息
	 * 
	 * @return
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	private String update() throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		return Constants.SUCCESS_CODE;
	}

	/**
	 * 更新角色的权限信息
	 * 
	 * @return
	 */
	public String edit() {

		jsonBean.parseJSONArrayData(getRoleFuncList());

		int len = jsonBean.getArray().size();

		List<TblMerRoleFuncMap> updateRoleMapList = new ArrayList<TblMerRoleFuncMap>();
		List<TblMerRoleFuncMapId> delRoleMapList = new ArrayList<TblMerRoleFuncMapId>();
		// 添加选择的权限信息
		for (int i = 0, n = len; i < n; i++) {
			String valueId = jsonBean.getJSONDataAt(i).getString(
					"valueId");
			TblMerRoleFuncMap tblMerRoleFuncMap = new TblMerRoleFuncMap();
			tblMerRoleFuncMap.setId(new TblMerRoleFuncMapId(mchntTp, valueId));
			tblMerRoleFuncMap.setRecUpdTs(CommonFunction.getCurrentDateTime());
			tblMerRoleFuncMap.setRecUpdUsr(operator.getOprId());
			updateRoleMapList.add(tblMerRoleFuncMap);
		}
		// 添加要删除的权限信息
		List<Object> roleIdMapList;
		try {
			// roleIdMapList =
			// t20301BO.getAllRoleMapId(Long.valueOf(mchntTp.trim()));
			roleIdMapList = t20301BO.getRoleMap(mchntTp);
			for (Object roleId : roleIdMapList) {
				// Long valueId = merRoleFuncMap.getId().getValueId();
				String valueId = roleId.toString();
				boolean flag = false;
				for (TblMerRoleFuncMap newMerRoleFuncMap : updateRoleMapList) {
					String newValueId = newMerRoleFuncMap.getId().getValueId();
					if (valueId.equals(newValueId)) {
						flag = true;
						break;
					}
				}
				if (!flag) {
					delRoleMapList.add(new TblMerRoleFuncMapId(mchntTp, valueId));
				}
			}
			t20301BO.updateRoleFuncMap(updateRoleMapList, delRoleMapList);
			log("更新商户权限成功");
		} catch (NumberFormatException e) {
			log("更新商户权限失败，失败信息为：" + e.getMessage());
		}

		return Constants.SUCCESS_CODE;
	}

	private String frnIn;

	private String mchntTp;

	private String roleFuncList;

	public String getFrnIn() {
		return frnIn;
	}

	public void setFrnIn(String frnIn) {
		this.frnIn = frnIn;
	}

	public String getMchntTp() {
		return mchntTp;
	}

	public void setMchntTp(String mchntTp) {
		this.mchntTp = mchntTp;
	}

	public String getRoleFuncList() {
		return roleFuncList;
	}

	public void setRoleFuncList(String roleFuncList) {
		this.roleFuncList = roleFuncList;
	}

}
