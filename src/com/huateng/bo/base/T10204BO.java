package com.huateng.bo.base;

import java.util.List;

import com.huateng.po.CstSysParam;
import com.huateng.po.CstSysParamPK;

public interface T10204BO {

	public CstSysParam get(CstSysParamPK id);
	public String add(CstSysParam cstSysParam);
	public String update(List<CstSysParam> cstSysParamList);
	public String delete(CstSysParam cstSysParam);
	public String delete(CstSysParamPK id);
}
