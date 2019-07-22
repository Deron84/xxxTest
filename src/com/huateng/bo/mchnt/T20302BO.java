package com.huateng.bo.mchnt;

import java.util.List;

import com.huateng.po.mchnt.TblInfMchntTpGrp;
import com.huateng.po.mchnt.TblInfMchntTpGrpPK;

public interface T20302BO {

	public String add(TblInfMchntTpGrp tblMchntTpGrp);
	public TblInfMchntTpGrp get(TblInfMchntTpGrpPK id);
	public String delete(TblInfMchntTpGrpPK id);
	public String delete(TblInfMchntTpGrp tblMchntTpGrp);
	public String update(TblInfMchntTpGrp TblInfMchntTpGrp);
	public String update(List<TblInfMchntTpGrp> tblMchntTpGrpList);
}
