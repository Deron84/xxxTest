package com.huateng.bo.mchnt;
import java.util.List;

import com.huateng.po.mchnt.TblMchtBranInf;
import com.huateng.po.mchnt.TblMchtBranInfPK;

public interface T20601BO {
	
	public String addMchtBranchInfo(TblMchtBranInf tblMchtBranInf);
	public void delete(TblMchtBranInfPK pk);	
	public String updateMchtBranchInfo(TblMchtBranInf tblMchtBranInf);	
	public TblMchtBranInf getMchtBranchInfo(TblMchtBranInfPK key);
	public String update(List<TblMchtBranInf> tblMchtBranInfList);	
}
