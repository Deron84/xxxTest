package com.huateng.bo.impl.mchnt;


import java.util.List;

import com.huateng.bo.mchnt.T20601BO;
import com.huateng.dao.iface.mchnt.TblMchtBranInfDAO;
import com.huateng.po.mchnt.TblMchtBranInf;
import com.huateng.po.mchnt.TblMchtBranInfPK;
import com.huateng.system.util.CommonFunction;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-15
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author liuxianxian
 * 
 * @version 1.0
 */
public class T20601BOTarget implements T20601BO {
	public String addMchtBranchInfo(TblMchtBranInf tblMchtBranInf){
		tblMchtBranInfDAO.save(tblMchtBranInf);
		return "00";
		}
		
		/* (non-Javadoc)
		 * @see com.huateng.bo.mchnt.T20601BO#delete(as.huateng.po.management.mer.TblMchtBranInfPK)
		 */
		public void delete(TblMchtBranInfPK key) {
			key.setBranchCd(CommonFunction.fillString(key.getBranchCd(), ' ', 11, true));
			key.setMchtCd(CommonFunction.fillString(key.getMchtCd(), ' ', 15, true));
			tblMchtBranInfDAO.delete(key);
		}
	
		public TblMchtBranInf getMchtBranchInfo(TblMchtBranInfPK key){		
		key.setBranchCd(CommonFunction.fillString(key.getBranchCd(), ' ', 11, true));
		key.setMchtCd(CommonFunction.fillString(key.getMchtCd(), ' ', 15, true));
		return tblMchtBranInfDAO.get(key);
		}
		
		public String updateMchtBranchInfo(TblMchtBranInf tblMchtBranInf){
		tblMchtBranInfDAO.update(tblMchtBranInf);
		return "00";
		}
		
		/* (non-Javadoc)
		 * @see com.huateng.bo.mchnt.T20601BO#update(java.util.List)
		 */
		public String update(List<TblMchtBranInf> tblMchtBranInfList) {
			for(TblMchtBranInf tblMchtBranInf:tblMchtBranInfList ){
				tblMchtBranInfDAO.update(tblMchtBranInf);
			}
			return "00";
		}
		
		private TblMchtBranInfDAO tblMchtBranInfDAO;
		
		
		/**
		* @return the tblMchtBranInfDAO
		*/
		public TblMchtBranInfDAO getTblMchtBranInfDAO() {
		return tblMchtBranInfDAO;
		}
		
		/**
		* @param tblMchtBranInfDAO the tblMchtBranInfDAO to set
		*/
		public void setTblMchtBranInfDAO(TblMchtBranInfDAO tblMchtBranInfDAO) {
		this.tblMchtBranInfDAO = tblMchtBranInfDAO;
		}

		



		
	

}
