package com.huateng.bo.base;

import java.util.List;

import com.huateng.po.base.TblPosCardInf;

public interface T10501BO {

	public String add(TblPosCardInf obj);
	public String update(TblPosCardInf obj);
	public String delete(String id);
	public String update(String infoList);
	public String addBatch(List<TblPosCardInf> list);
}
