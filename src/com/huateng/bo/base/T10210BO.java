package com.huateng.bo.base;

import java.util.List;

import com.huateng.po.CstSysParam;
import com.huateng.po.CstSysParamPK;

public interface T10210BO {

	public List<String> get(String sql);
	public String delete(String sql);
	public String add(String sql);
	public String update(String[] sql);
}
