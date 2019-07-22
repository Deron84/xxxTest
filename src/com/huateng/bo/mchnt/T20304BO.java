package com.huateng.bo.mchnt;

import java.util.List;

import com.huateng.po.mchnt.CstMchtFeeInf;
import com.huateng.po.mchnt.CstMchtFeeInfPK;

public interface T20304BO {
	
	public String addMchtLimit(CstMchtFeeInf cstMchtFeeInf);	
	public String updateMchtLimit(CstMchtFeeInf cstMchtFeeInf);	
	public CstMchtFeeInf getMchtLimit(CstMchtFeeInfPK cstMchtFeeInfPK);
	public void delete(CstMchtFeeInfPK id);
	public String update(List<CstMchtFeeInf> cstMchtFeeInfList) ;

}
