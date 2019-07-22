/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-8-20       first release
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
package com.huateng.bo.impl.risk;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.huateng.bo.risk.T40101BO;
import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.dao.iface.risk.TblRiskInfDAO;
import com.huateng.dao.iface.risk.TblRiskInfUpdLogDAO;
import com.huateng.po.TblRiskInf;
import com.huateng.po.TblRiskInfUpdLog;
import com.huateng.system.util.CommonFunction;

/**
 * Title:设置风险模型
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-20
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class T40101BOTarget implements T40101BO {
	
	private TblRiskInfDAO tblRiskInfDAO;
	private TblRiskInfUpdLogDAO tblRiskInfUpdLogDAO;
	
	/* (non-Javadoc)
	 * @see com.huateng.bo.T40101BO#update(com.huateng.po.TblRiskInf)
	 */
	public String update(TblRiskInf tblRiskInfNew, TblRiskInf tblRiskInfOld, Operator operator) throws Exception {
		
		// 修改时间不记录日志
		tblRiskInfOld.setModiOprId(tblRiskInfNew.getModiOprId());
		tblRiskInfOld.setModiZoneNo(tblRiskInfNew.getModiZoneNo());
		tblRiskInfOld.setModiTime(tblRiskInfNew.getModiTime());
		
		Class classOld = tblRiskInfOld.getClass();
		Class classNew = tblRiskInfNew.getClass();
		
		Field[] fieldOld = classOld.getDeclaredFields();
		Field[] fieldNew = classNew.getDeclaredFields();
		for (int i=0; i<fieldOld.length; i++) { 
			Field oldField = fieldOld[i];
			Field newField = fieldNew[i];
			String oldName = oldField.getName();
			String newName = newField.getName();
			
			if ("java.lang.String".equals(oldField.getType().getName())) {
				if (oldName.equals(newName)) {
					
					Method oldMethod = classOld.getMethod("get"+oldName.substring(0,1).toUpperCase()+oldName.substring(1));
					String oldValue = oldMethod.invoke(tblRiskInfOld).toString();
					
					Method newMethod = classNew.getMethod("get"+newName.substring(0,1).toUpperCase()+newName.substring(1));
					String newValue = newMethod.invoke(tblRiskInfNew).toString();
					if (!oldValue.equals(newValue)) {
	
						TblRiskInfUpdLog tblRiskInfUpdLog = new TblRiskInfUpdLog();
						tblRiskInfUpdLog.setSaModelKind(tblRiskInfNew.getId());
						tblRiskInfUpdLog.setSaFieldName(oldName);
						tblRiskInfUpdLog.setSaFieldValueBF(oldValue);
						tblRiskInfUpdLog.setSaFieldValue(newValue);
						tblRiskInfUpdLog.setModiZoneNo(operator.getOprBrhId());
						tblRiskInfUpdLog.setModiOprId(operator.getOprId());
						tblRiskInfUpdLog.setModiTime(CommonFunction.getCurrentDateTime());
						tblRiskInfUpdLogDAO.save(tblRiskInfUpdLog);
					}
				}
			}
		}
		tblRiskInfDAO.update(tblRiskInfNew);
		return Constants.SUCCESS_CODE;
	}

	public TblRiskInf get(String key) {
		return tblRiskInfDAO.get(key);
	}
	
	/**
	 * @param tblRiskInfDAO the tblRiskInfDAO to set
	 */
	public void setTblRiskInfDAO(TblRiskInfDAO tblRiskInfDAO) {
		this.tblRiskInfDAO = tblRiskInfDAO;
	}

	/**
	 * @param tblRiskInfUpdLogDAO the tblRiskInfUpdLogDAO to set
	 */
	public void setTblRiskInfUpdLogDAO(TblRiskInfUpdLogDAO tblRiskInfUpdLogDAO) {
		this.tblRiskInfUpdLogDAO = tblRiskInfUpdLogDAO;
	}
	
}
