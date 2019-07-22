/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-7-5       first release
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

import com.huateng.bo.base.T10204BO;
import com.huateng.common.Constants;
import com.huateng.po.CstSysParam;
import com.huateng.po.CstSysParamPK;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.ContextUtil;

/**
 * Title:系统参数维护
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-7-5
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T10204Action extends BaseAction {
	
	T10204BO t10204BO = (T10204BO) ContextUtil.getBean("T10204BO");
	
	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		
		// add method
		if("add".equals(method)) {
			rspCode = add();
		} else if("delete".equals(method)) {
			rspCode = delete();
		} else if("update".equals(method)) {
			rspCode = update();
		}
		return rspCode;
	}
	
	/**
	 * add system parameter information
	 * @return
	 */
	private String add() {
		
		//initialize system parameter object
		CstSysParam cstSysParam = new CstSysParam();
		//initialize id of system parameter object
		CstSysParamPK cstSysParamPK = new CstSysParamPK();
		//set property
		cstSysParamPK.setOwner(owner);

		cstSysParamPK.setKey(key);
		
		// if there is a same id in the system
		if(t10204BO.get(cstSysParamPK) != null) {
			return "该系统参数的属主和键值均已存在。";
		}
		
		cstSysParam.setId(cstSysParamPK);

		cstSysParam.setType(type);

		cstSysParam.setValue(value);
		
		cstSysParam.setDescr(descr);
		
		cstSysParam.setReserve(reserve);
		
		//add to database
		t10204BO.add(cstSysParam);
		
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * delete system parameter information
	 * @return
	 */
	private String delete() {
		
		CstSysParamPK cstSysParamPK = new CstSysParamPK(owner,key);
		
		if(t10204BO.get(cstSysParamPK) == null) {
			return "您所要删除的参数信息已经不存在";
		}
		
		t10204BO.delete(cstSysParamPK);
		
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * update the parameter information list
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private String update() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		jsonBean.parseJSONArrayData(getParameterList());
		
		int len = jsonBean.getArray().size();
		
		
		CstSysParam cstSysParam = null;
		
		CstSysParamPK cstSysParamPK = null;
		
		List<CstSysParam> cstSysParamList = new ArrayList<CstSysParam>(len);
		
		for(int i = 0; i < len; i++) {
			
			cstSysParam = new CstSysParam();
			
			cstSysParamPK = new CstSysParamPK();
			
			jsonBean.setObject(jsonBean.getJSONDataAt(i));
			
			BeanUtils.setObjectWithPropertiesValue(cstSysParamPK, jsonBean,false);
			
			cstSysParam.setId(cstSysParamPK);
			
			BeanUtils.setObjectWithPropertiesValue(cstSysParam, jsonBean,false);
			
			BeanUtils.setNullValueWithValue(cstSysParam, "-", 0);
			
			cstSysParamList.add(cstSysParam);
		}
		
		t10204BO.update(cstSysParamList);
		
		return Constants.SUCCESS_CODE;
	}
	/**the parameter owner*/
	private String owner;
	/**the parameter key*/
	private String key;
	/**the parameter type*/
	private String type;
	/**the parameter value*/
	private String value;
	/**the paramter descr*/
	private String descr;
	/**the paramter reserve*/
	private String reserve;
	/**the list of parameter to update*/
	private String parameterList;
	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the descr
	 */
	public String getDescr() {
		return descr;
	}

	/**
	 * @param descr the descr to set
	 */
	public void setDescr(String descr) {
		this.descr = descr;
	}

	/**
	 * @return the reserve
	 */
	public String getReserve() {
		return reserve;
	}

	/**
	 * @param reserve the reserve to set
	 */
	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

	/**
	 * @return the parameterList
	 */
	public String getParameterList() {
		return parameterList;
	}

	/**
	 * @param parameterList the parameterList to set
	 */
	public void setParameterList(String parameterList) {
		this.parameterList = parameterList;
	}
}
