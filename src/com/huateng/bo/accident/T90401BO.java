package com.huateng.bo.accident;

import com.huateng.po.accident.TblCostInf;

public interface T90401BO {

	public String add(TblCostInf obj);
	public String upd(String infoList);
	public String del(String id);
}
