package com.huateng.bo.mchnt;

import java.util.List;

import com.huateng.po.TblMchtCupInfo;

public interface T20402BO {

	public TblMchtCupInfo get(String id);

	public String add(TblMchtCupInfo tblMchtCupInfo);
	
	public String saveOrUpdate(TblMchtCupInfo tblMchtCupInfo);

	public String update(List<TblMchtCupInfo> tblMchtCupInfoList);
	
	public String cancel(TblMchtCupInfo tblMchtCupInfo);

	public String delete(TblMchtCupInfo tblMchtCupInfo);

	public String delete(String id);
}
