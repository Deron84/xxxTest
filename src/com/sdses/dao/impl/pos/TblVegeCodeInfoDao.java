package com.sdses.dao.impl.pos;

import com.huateng.common.Constants;
import com.sdses.dao._RootDAO;
import com.sdses.po.TblVegeCodeInfo;

public class TblVegeCodeInfoDao extends _RootDAO<com.sdses.po.TblVegeCodeInfo> implements com.sdses.dao.iface.pos.TblVegeCodeInfoDao {


    @Override
    public void update(TblVegeCodeInfo vegeInfo) {
        // TODO Auto-generated method stub
        super.update(vegeInfo);
    }

    @Override
    public void delete(TblVegeCodeInfo vegCode) {
        // TODO Auto-generated method stub
        super.delete(vegCode);
    }

    @Override
    public String save(TblVegeCodeInfo vegeCodeInfo) {
        // TODO Auto-generated method stub
        super.save(vegeCodeInfo);
        return Constants.SUCCESS_CODE;
    }

    @Override
    protected Class getReferenceClass() {
        // TODO Auto-generated method stub
        return TblVegeCodeInfo.class;
    }

    @Override
    public TblVegeCodeInfo get(String key) {
        // TODO Auto-generated method stub
        return (TblVegeCodeInfo) get(getReferenceClass(), key);
    }

    
}
