package com.huateng.bo.impl.base;

import java.util.List;

import com.huateng.bo.base.T10204BO;
import com.huateng.common.Constants;
import com.huateng.dao.iface.base.CstSysParamDAO;
import com.huateng.po.CstSysParam;
import com.huateng.po.CstSysParamPK;

/**
 * Title:系统参数BO
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-9
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */
public class T10204BOTarget implements T10204BO {
	
	private CstSysParamDAO cstSysParamDAO;

	public String add(CstSysParam cstSysParam) {
		cstSysParamDAO.save(cstSysParam);
		return Constants.SUCCESS_CODE;
	}

	public String delete(CstSysParam cstSysParam) {
		cstSysParamDAO.delete(cstSysParam);
		return Constants.SUCCESS_CODE;
	}

	public String delete(CstSysParamPK id) {
		cstSysParamDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

	public CstSysParam get(CstSysParamPK id) {
		return cstSysParamDAO.get(id);
	}

	public String update(List<CstSysParam> cstSysParamList) {
		for(CstSysParam cstSysParam : cstSysParamList) {
			cstSysParamDAO.update(cstSysParam);
		}
		return Constants.SUCCESS_CODE;
	}

	public CstSysParamDAO getCstSysParamDAO() {
		return cstSysParamDAO;
	}

	public void setCstSysParamDAO(CstSysParamDAO cstSysParamDAO) {
		this.cstSysParamDAO = cstSysParamDAO;
	}
}
