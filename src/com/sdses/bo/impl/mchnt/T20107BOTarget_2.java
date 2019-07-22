package com.sdses.bo.impl.mchnt;

import java.util.List;

import com.huateng.common.Constants;
import com.sdses.bo.mchnt.T20107BO_2;
import com.sdses.dao.iface.mchnt.TcMchntMapPoDAO;
import com.sdses.po.TcMchntMap;
import com.sdses.po.TcMchntMapPK;

public class T20107BOTarget_2 implements T20107BO_2{
	private TcMchntMapPoDAO tcMchntMapPoDAO;
	@Override
    public int update(TcMchntMap tcMchntMap) throws Exception {
        // TODO Auto-generated method stub
		 int updateData = tcMchntMapPoDAO.update(tcMchntMap);

	     return updateData;
    }
	@Override
    public int updateData(TcMchntMap tcMchntMap) throws Exception {
        // TODO Auto-generated method stub

        int updateData = tcMchntMapPoDAO.updateData(tcMchntMap);

        return updateData;
    }
	@Override
    public int updateCertData(String ylMchntNo, String uploadFileName,String brhFlag,String scanFlag) throws Exception {
        // TODO Auto-generated method stub

        int updateData = tcMchntMapPoDAO.updateCertData(ylMchntNo, uploadFileName,brhFlag,scanFlag);

        return updateData;
    }
	@Override
	public int isExitMchnt(String id) throws Exception {
		// TODO Auto-generated method stub

	    int updateData = tcMchntMapPoDAO.isExitMchnt(id);

	    return updateData;
	}
	@Override
	public int delete(TcMchntMapPK tcMchntMapPK,String id) throws Exception {
        // TODO Auto-generated method stub

        int deleteData = tcMchntMapPoDAO.delete(tcMchntMapPK,id);

        return deleteData;
    }
	@Override
	public int deleteData(TcMchntMapPK tcMchntMapPK,String mergeFlag) throws Exception {
        // TODO Auto-generated method stub

        int deleteData = tcMchntMapPoDAO.deleteData(tcMchntMapPK,mergeFlag);

        return deleteData;
    }
	public TcMchntMapPoDAO getTcMchntMapPoDAO() {
		return tcMchntMapPoDAO;
	}
	public void setTcMchntMapPoDAO(TcMchntMapPoDAO tcMchntMapPoDAO) {
		this.tcMchntMapPoDAO = tcMchntMapPoDAO;
	}
	@Override
	public int delete2(TcMchntMapPK tcMchntMapPK,String id) throws Exception {
		// TODO Auto-generated method stub
		int deleteData = tcMchntMapPoDAO.delete2(tcMchntMapPK,id);

        return deleteData;
	}
	
}
