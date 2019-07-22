package com.huateng.bo.impl.mchnt;

import java.util.List;

import org.apache.log4j.Logger;

import com.huateng.bo.mchnt.T21400BO;
import com.huateng.common.Constants;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.dao.iface.mchnt.TblMchntApplyDAO;
import com.huateng.dao.iface.mchnt.TblMchntUserDAO;
import com.huateng.po.mchnt.TblMchntApply;
import com.huateng.po.mchnt.TblMchntUser;
import com.huateng.system.util.CommonFunction;

public class T21400BOTarget implements T21400BO{
	private static Logger logger = Logger.getLogger(T21400BOTarget.class);
	
	private TblMchntApplyDAO tblMchntApplyTmpDAO;
	private TblMchntUserDAO tblMchntUserTmpDAO;
	private ICommQueryDAO commQueryDAO;
	
	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		T21400BOTarget.logger = logger;
	}

	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}

	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}

	public TblMchntApplyDAO getTblMchntApplyTmpDAO() {
		return tblMchntApplyTmpDAO;
	}

	public void setTblMchntApplyTmpDAO(TblMchntApplyDAO tblMchntApplyTmpDAO) {
		this.tblMchntApplyTmpDAO = tblMchntApplyTmpDAO;
	}

	

	public TblMchntUserDAO getTblMchntUserTmpDAO() {
		return tblMchntUserTmpDAO;
	}

	public void setTblMchntUserTmpDAO(TblMchntUserDAO tblMchntUserTmpDAO) {
		this.tblMchntUserTmpDAO = tblMchntUserTmpDAO;
	}

	@Override
	public int countNum(String id) {
		String countSql="select count(*) from TBL_MCHNT_APPLY where APPLY_ID='" + id.trim()+"'";
		int countNum=Integer.parseInt(CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql));
		return countNum;
	}

	@Override
	public TblMchntApply get(String id) {
		return tblMchntApplyTmpDAO.get(id);
	}

	@Override
	public String update(TblMchntApply cstSysParam) {
		tblMchntApplyTmpDAO.update(cstSysParam);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public TblMchntUser getMchntUser(String id) {		
		
		return tblMchntUserTmpDAO.get(id);
		
	}

	@Override
	public String updateMchntUser(TblMchntUser cstSysParam) {
		tblMchntUserTmpDAO.update(cstSysParam);
		return Constants.SUCCESS_CODE;
	}
	@Override
	public TblMchntUser getMchntUserByMchntId(String mchntId) {		
		String sql="select ID from TBL_MCHNT_USER where MCHNT_ID='" + mchntId.trim()+"'"+" and PARENT_ID is not null";
		List<Object> objs=commQueryDAO.findBySQLQuery(sql);
		String id="";
		if(objs!=null&&objs.size()>0){
			id=objs.get(0).toString();
			return getMchntUser(id);
		}else{
			return null;
		}
		
	}
}
