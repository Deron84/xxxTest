package com.huateng.bo.impl.base;

import java.util.List;

import com.huateng.bo.base.T10204BO;
import com.huateng.bo.base.T10210BO;
import com.huateng.common.Constants;
import com.huateng.dao.common.SqlDao;
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
public class T10210BOTarget implements T10210BO {
	
	private SqlDao sqlDao;
	

	public SqlDao getSqlDao() {
		return sqlDao;
	}

	public void setSqlDao(SqlDao sqlDao) {
		this.sqlDao = sqlDao;
	}

	@Override
	public List get(String sql) {
		// TODO Auto-generated method stub
		List<String> list = null;
		list = sqlDao.queryForList(sql);
		return list;
	}

	@Override
	public String delete(String sql) {
		// TODO Auto-generated method stub
		sqlDao.execute(sql);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String add(String sql) {
		// TODO Auto-generated method stub
		sqlDao.execute(sql);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String update(String[] sql) {
		sqlDao.batchUpdate(sql);
		return Constants.SUCCESS_CODE;
	}


}
