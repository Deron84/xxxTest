package com.huateng.bo.base;

import java.util.List;

import com.huateng.po.TblBrhParam;

public interface T10211BO {

	public TblBrhParam get(String brhId);

	public String add(TblBrhParam tblBrhParam);

	public String update(List<TblBrhParam> tblBrhParamList);

	public String delete(String brhId);
}
