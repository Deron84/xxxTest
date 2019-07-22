package com.huateng.bo.mchnt;

import java.util.List;

import com.huateng.po.mchnt.TblHisDiscAlgo;
import com.huateng.po.mchnt.TblInfDiscCd;

public interface T20701BO {

	public String createArith(TblInfDiscCd tblInfDiscCd, List<TblHisDiscAlgo> descList);
	public String updateArith(TblInfDiscCd tblInfDiscCd, List<TblHisDiscAlgo> descList);
	public String deleteArith(String discCd);
	public TblInfDiscCd getTblInfDiscCd(String discCd) throws Exception;
}
