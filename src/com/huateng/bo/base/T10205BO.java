package com.huateng.bo.base;

import java.util.List;

import com.huateng.po.TblBankBinInf;

public interface T10205BO {
	
	public TblBankBinInf get(Integer id); 
	public void createTblBankBinInf(TblBankBinInf tblBankBinInf);
	public void update(TblBankBinInf tblBankBinInf);
	public void delete(Integer id);
	public void update(List<TblBankBinInf> tblBankBinInfList);
	public String upload(List<TblBankBinInf> tblBankBinInfList);
}
