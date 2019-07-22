package com.huateng.bo.mchnt;

import com.huateng.po.mchnt.TblMchtCheckInf;
import com.huateng.po.mchnt.TblMchtCheckInfPK;

public interface T20801BO {

//	public String add(TblMchtCheckInf obj) ;
//	public String update(String obj) ;
//	public String del(TblMchtCheckInfPK pk) ;
	public String checkMcht(String mchtNo);
	public String checkMchts(String[] mchtNos);
}
