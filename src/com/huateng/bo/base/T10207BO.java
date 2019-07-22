package com.huateng.bo.base;

import java.util.List;

import com.huateng.po.TblBrhTlrInfo;
import com.huateng.po.TblBrhTlrInfoPK;

public interface T10207BO {
	
	public TblBrhTlrInfo get(TblBrhTlrInfoPK id) ;
	public void createTblBrhTlrInfo(TblBrhTlrInfo TblBrhTlrInfo) ;
	public void update(TblBrhTlrInfo TblBrhTlrInfo) ;
	public void delete(TblBrhTlrInfoPK id) ;
	public void update(List<TblBrhTlrInfo> TblBrhTlrInfoList);	
}
