package com.huateng.bo.impl.mchnt;

import com.huateng.bo.mchnt.T21001BO;
import com.huateng.common.Constants;
import com.huateng.dao.common.HqlDao;
import com.huateng.po.mchnt.TblUnitPersonBind;
import com.huateng.po.mchnt.TblUnitPersonBindPK;


/**
 * project JSBConsole
 * date 2013-3-11
 * @author 樊东东
 */
public class T21001BOTarget implements T21001BO{
	private HqlDao hqlDao ;
	
	public HqlDao getHqlDao() {
		return hqlDao;
	}

	
	public void setHqlDao(HqlDao hqlDao) {
		this.hqlDao = hqlDao;
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.mchnt.T21001BO#delete(com.huateng.po.mchnt.TblUnitPersonBind)
	 */
	public String delete(TblUnitPersonBind existObj) {
		// TODO Auto-generated method stub
		hqlDao.delete(existObj);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.mchnt.T21001BO#get(com.huateng.po.mchnt.TblUnitPersonBindPK)
	 */
	public TblUnitPersonBind get(TblUnitPersonBindPK id) {
		// TODO Auto-generated method stub
		
		return (TblUnitPersonBind) hqlDao.get(TblUnitPersonBind.class, id);
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.mchnt.T21001BO#save(com.huateng.po.mchnt.TblUnitPersonBind)
	 */
	public String save(TblUnitPersonBind obj) {
		// TODO Auto-generated method stub
		hqlDao.save(obj);
		return Constants.SUCCESS_CODE;
	}

}
