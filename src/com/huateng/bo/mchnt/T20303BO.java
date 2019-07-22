package com.huateng.bo.mchnt;

import java.util.List;

import com.huateng.po.mchnt.TblInfMchntTp;
import com.huateng.po.mchnt.TblInfMchntTpPK;

public interface T20303BO {

	public String add(TblInfMchntTp tblInfMchntTp);
	public TblInfMchntTp get(TblInfMchntTpPK mchntTp);
	public String delete(TblInfMchntTpPK mchntTp);
	public String delete(TblInfMchntTp tblInfMchntTp);
	public String update(TblInfMchntTp tblInfMchntTp);
	public String update(List<TblInfMchntTp> tblMchntTpList);
}
