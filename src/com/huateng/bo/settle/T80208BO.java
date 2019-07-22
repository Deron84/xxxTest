package com.huateng.bo.settle;

import java.util.List;

import com.huateng.po.TblBrhAcct;

public interface T80208BO {

	public String add(TblBrhAcct tblBrhAcct);
	public String delete(TblBrhAcct tblBrhAcct);
	public String delete(String key);	
	public String update(List<TblBrhAcct> list);
}
