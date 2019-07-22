package com.huateng.bo.risk;

import com.huateng.common.Operator;
import com.huateng.po.TblRiskInf;

public interface T40101BO {

	public String update(TblRiskInf tblRiskInfNew, TblRiskInf tblRiskInfOld, Operator operator) throws Exception;
	public TblRiskInf get(String key) throws Exception;
}
