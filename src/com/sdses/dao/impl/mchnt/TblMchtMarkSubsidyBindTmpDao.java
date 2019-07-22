package com.sdses.dao.impl.mchnt;

import com.huateng.common.Constants;
import com.sdses.dao._RootDAO;
import com.sdses.dao.iface.mchnt.TblMchtMarkSubsidyBindDao;
import com.sdses.po.TblMchtMarkSubsidyBind;

public class TblMchtMarkSubsidyBindTmpDao extends _RootDAO<TblMchtMarkSubsidyBind> implements TblMchtMarkSubsidyBindDao {

    @Override
    public void update(TblMchtMarkSubsidyBind tblMchtMarkSudyBind) {
        // TODO Auto-generated method stub
        super.saveOrUpdate(tblMchtMarkSudyBind);
    }

    @Override
    public String save(TblMchtMarkSubsidyBind tblMchtMarkSudyBind) {
        // TODO Auto-generated method stub
        super.save(tblMchtMarkSudyBind);
        return Constants.SUCCESS_CODE;
    }

    @Override
    public TblMchtMarkSubsidyBind get(String recordID) {
        // TODO Auto-generated method stub
        return (TblMchtMarkSubsidyBind) get(getReferenceClass(), recordID);
    }

    @Override
    public void delete(TblMchtMarkSubsidyBind tblMchtMarkSudyBind) {
        // TODO Auto-generated method stub
        super.delete(tblMchtMarkSudyBind);
    }

    @Override
    protected Class getReferenceClass() {
        return TblMchtMarkSubsidyBind.class;
    }

}
