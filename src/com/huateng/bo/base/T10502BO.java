package com.huateng.bo.base;

import java.util.List;

import com.huateng.po.base.TblComCardInf;

public interface T10502BO {

	public String add(TblComCardInf obj);
	public String update(TblComCardInf obj);
	public String delete(String id);
	public String update(String infoList);
	public String addBatch(List<TblComCardInf> list);
}
