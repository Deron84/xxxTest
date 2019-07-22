package com.huateng.bo.base;

import java.util.List;

import com.huateng.po.TblBankBinInf;
import com.huateng.po.base.TblEmvPara;
import com.huateng.po.base.TblEmvParaPK;

public interface T10206BO {
	
	public TblEmvPara get(TblEmvParaPK id);
	public void createTblEmvPara(TblEmvPara tblEmvPara);
	public void update(TblEmvPara tblEmvPara);
	public void delete(TblEmvParaPK id);	
	public void update(List<TblEmvPara> tblEmvParaList);
}
