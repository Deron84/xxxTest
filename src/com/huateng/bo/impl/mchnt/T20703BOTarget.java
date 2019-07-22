package com.huateng.bo.impl.mchnt;

import com.huateng.bo.mchnt.T20703BO;
import com.huateng.common.Constants;
import com.huateng.dao.common.HqlDao;
import com.huateng.po.TblDivMchnt;
import com.huateng.po.TblDivMchntPK;
import com.huateng.system.util.ContextUtil;


/**
 * project JSBConsole
 * date 2013-3-10
 * @author 樊东东
 */
public class T20703BOTarget implements T20703BO{
	private HqlDao hqlDao ;
	
	
	public HqlDao getHqlDao() {
		return hqlDao;
	}

	
	public void setHqlDao(HqlDao hqlDao) {
		this.hqlDao = hqlDao;
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.mchnt.T20703BO#delete(com.huateng.po.TblDivMchnt)
	 */
	public String delete(TblDivMchnt divMcht) {
		// TODO Auto-generated method stub
		hqlDao.delete(divMcht);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.mchnt.T20703BO#get(com.huateng.po.TblDivMchntPK)
	 */
	public TblDivMchnt get(TblDivMchntPK id) {
		// TODO Auto-generated method stub
		return (TblDivMchnt) hqlDao.get(TblDivMchnt.class, id);
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.mchnt.T20703BO#save(com.huateng.po.TblDivMchnt)
	 */
	public String save(TblDivMchnt divMcht) {
		// TODO Auto-generated method stub
		hqlDao.save(divMcht);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.mchnt.T20703BO#update(com.huateng.po.TblDivMchnt)
	 */
	public String update(TblDivMchnt divMcht) {
		// TODO Auto-generated method stub
		hqlDao.update(divMcht);
		return Constants.SUCCESS_CODE;
	}}
