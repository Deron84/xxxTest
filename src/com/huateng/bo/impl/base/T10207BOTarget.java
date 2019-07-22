package com.huateng.bo.impl.base;

import java.util.List;
import com.huateng.bo.base.T10207BO;
import com.huateng.dao.iface.base.TblBrhTlrInfoDao;
import com.huateng.po.TblBrhTlrInfo;
import com.huateng.po.TblBrhTlrInfoPK;

/**
 * Title:交易码BO实现
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-9
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author liuxianxian
 * 
 * @version 1.0
 */
public class T10207BOTarget implements T10207BO {
	
	private TblBrhTlrInfoDao tblBrhTlrInfoDao;


	public TblBrhTlrInfoDao getTblBrhTlrInfo() {
		return tblBrhTlrInfoDao;
	}

	public void setTblBrhTlrInfo(TblBrhTlrInfoDao tblBrhTlrInfo) {
		this.tblBrhTlrInfoDao = tblBrhTlrInfo;
	}

	/**
	 * 获取虚拟柜员信息
	 * 
	 * @param alias
	 *            cstFeeBranchInf虚拟柜员参数主键ID号
	 * @return TblCardInfoRmb虚拟柜员表结构对象
	 * @throws Exception
	 */
	public TblBrhTlrInfo get(TblBrhTlrInfoPK id) {
			TblBrhTlrInfo tblCardInfoRmb = tblBrhTlrInfoDao.get(id);
			return tblCardInfoRmb;
	}

	/**
	 * 实现虚拟柜员参数数据库信息新增
	 * 
	 * @param alias
	 *            cstFeeBranchInf虚拟柜员参数表结构对象
	 * @return
	 * @throws Exception
	 */
	public void createTblBrhTlrInfo(TblBrhTlrInfo tblBrhTlrInfo){		
			tblBrhTlrInfoDao.save(tblBrhTlrInfo);
	}

	/**
	 * 实现虚拟柜员参数数据库信息修改
	 * 
	 * @param alias
	 *            cstFeeBranchInf虚拟柜员参数表结构对象修改
	 * @return
	 * @throws Exception
	 */
	public void update(TblBrhTlrInfo tblBrhTlrInfo){
			tblBrhTlrInfoDao.saveOrUpdate(tblBrhTlrInfo);
	}

	/**
	 * 实现虚拟柜员参数数据库信息删除
	 * 
	 * @param alias
	 *            cstFeeBranchInf虚拟柜员主键ID号
	 * 
	 * @return
	 * @throws Exception
	 */
	public void delete(TblBrhTlrInfoPK id)  {
			tblBrhTlrInfoDao.delete(id);
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.base.T10205BO#update(java.util.List)
	 */
	public void update(List<TblBrhTlrInfo> TblBrhTlrInfoList)  {
		for(TblBrhTlrInfo tblBrhTlrInfo:TblBrhTlrInfoList){
			this.update(tblBrhTlrInfo);
		}
	}

	public TblBrhTlrInfoDao getTblBrhTlrInfoDao() {
		return tblBrhTlrInfoDao;
	}

	public void setTblBrhTlrInfoDao(TblBrhTlrInfoDao tblBrhTlrInfoDao) {
		this.tblBrhTlrInfoDao = tblBrhTlrInfoDao;
	}
	
	

	
}
