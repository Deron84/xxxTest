package com.huateng.bo.impl.base;

import java.util.List;

import com.huateng.bo.base.T10211BO;
import com.huateng.common.Constants;
import com.huateng.dao.iface.base.TblBrhParamDAO;
import com.huateng.po.TblBrhParam;

/**
 * 机构参数信息管理实现类
 * 
 * @author zhangkai
 *
 */
public class T10211BOTarget implements T10211BO {

	private TblBrhParamDAO tblBrhParamDAO;

	public String add(TblBrhParam tblBrhParam) {
		tblBrhParamDAO.save(tblBrhParam);
		return Constants.SUCCESS_CODE;
	}

	public String delete(TblBrhParam tblBrhParam) {
		tblBrhParamDAO.delete(tblBrhParam);
		return Constants.SUCCESS_CODE;
	}

	public String delete(String id) {
		tblBrhParamDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

	public TblBrhParam get(String id) {
		return tblBrhParamDAO.get(id);
	}

	public String update(List<TblBrhParam> tblBrhParamList) {
		for (TblBrhParam tblBrhParam : tblBrhParamList) {
			tblBrhParamDAO.update(tblBrhParam);
		}
		return Constants.SUCCESS_CODE;
	}

	public TblBrhParamDAO getTblBrhParamDAO() {
		return tblBrhParamDAO;
	}

	public void setTblBrhParamDAO(TblBrhParamDAO tblBrhParamDAO) {
		this.tblBrhParamDAO = tblBrhParamDAO;
	}
}
