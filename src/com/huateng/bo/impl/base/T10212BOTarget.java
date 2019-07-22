package com.huateng.bo.impl.base;

import com.huateng.bo.base.T10212BO;
import com.huateng.po.TblTermTpPo;
import com.sdses.dao.iface.pos.TblTermTpPoDAO;

public class T10212BOTarget implements T10212BO {

    private TblTermTpPoDAO tblTermTpPoDAO;

    @Override
    public int deleteData(String termTpUpd) throws Exception {
        // TODO Auto-generated method stub

        int deleteData = tblTermTpPoDAO.deleteData(termTpUpd);

        return deleteData;
    }

    @Override
    public int updateData(String descrUpd, String descrUpd1,String termNumUpd,String termTypeUpd,String proDescrUpd) throws Exception {
        // TODO Auto-generated method stub

        int updateData = tblTermTpPoDAO.updateData(descrUpd, descrUpd1,termNumUpd,termTypeUpd,proDescrUpd);

        return updateData;
    }
    @Override
    public int queryForTermTp(String termTpUpd) throws Exception {
        // TODO Auto-generated method stub
        int queryForTermTp = tblTermTpPoDAO.queryForTermTp(termTpUpd);
        return queryForTermTp;
    }
    @Override
    public String queryTermTp(String descrUpd1) throws Exception {
        // TODO Auto-generated method stub
    	String descrUpd = tblTermTpPoDAO.queryTermTp(descrUpd1);
        return descrUpd;
    }

    @Override
    public int queryForInt(String descrUpd) throws Exception {
        // TODO Auto-generated method stub
        int queryForInt = tblTermTpPoDAO.queryForInt(descrUpd);
        return queryForInt;
    }

    @Override
    public int findCountBySQLQuery() throws Exception {
        // TODO Auto-generated method stub
        int findCountBySQLQuery = tblTermTpPoDAO.findCountBySQLQuery();
        return findCountBySQLQuery;
    }

    @Override
    public int addDate(TblTermTpPo tblTermTpPo) throws Exception {
        // TODO Auto-generated method stub
        int addDate = tblTermTpPoDAO.addDate(tblTermTpPo);
        return addDate;
    }

    public TblTermTpPoDAO getTblTermTpPoDAO() {
        return tblTermTpPoDAO;
    }

    public void setTblTermTpPoDAO(TblTermTpPoDAO tblTermTpPoDAO) {
        this.tblTermTpPoDAO = tblTermTpPoDAO;
    }
}
