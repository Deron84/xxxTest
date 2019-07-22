package com.huateng.bo.base;

import java.util.List;

import com.huateng.po.TblOprInfo;

public interface T10401BO {

	public TblOprInfo get(String oprId);
	public String add(TblOprInfo tblOprInfo);
	public String update(List<TblOprInfo> tblOprInfoList);
	public String update(TblOprInfo tblOprInfo);
	public String delete(TblOprInfo tblOprInfo);
	public String delete(String oprId);
}
