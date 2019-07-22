package com.sdses.dao.impl.mchnt;

import com.huateng.common.Constants;
import com.sdses.dao._RootDAO;
import com.sdses.dao.iface.mchnt.TblMchtMarkSubsidyDao;
import com.sdses.po.TblMchtMarkSubsidy;

public class TblMchtMarkSubsidyTmpDao extends _RootDAO<TblMchtMarkSubsidy> implements TblMchtMarkSubsidyDao {

    @Override
    public void update(TblMchtMarkSubsidy tblMchtMarkSudy) {
        // TODO Auto-generated method stub
        super.saveOrUpdate(tblMchtMarkSudy);
    }

    @Override
    public String save(TblMchtMarkSubsidy tblMchtMarkSudy) {
        // TODO Auto-generated method stub
        super.save(tblMchtMarkSudy);
        return Constants.SUCCESS_CODE;
    }

    @Override
    public TblMchtMarkSubsidy get(String ruleID) {
        // TODO Auto-generated method stub
        return (TblMchtMarkSubsidy) get(getReferenceClass(), ruleID);
    }

    @Override
    public void delete(TblMchtMarkSubsidy tblMchtMarkSudy) {
        // TODO Auto-generated method stub
        super.delete(tblMchtMarkSudy);
    }

    @Override
    protected Class getReferenceClass() {
        return TblMchtMarkSubsidy.class;
    }

}
