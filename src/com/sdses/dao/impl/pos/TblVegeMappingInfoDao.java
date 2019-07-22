package com.sdses.dao.impl.pos;

import com.huateng.common.Constants;
import com.sdses.dao._RootDAO;
import com.sdses.po.TblVegeMappingInfo;
import com.sdses.po.TblVegeMappingInfoPK;

public class TblVegeMappingInfoDao extends _RootDAO<com.sdses.po.TblVegeMappingInfo> implements com.sdses.dao.iface.pos.TblVegeMappingInfoDao {

    @Override
    public void update(TblVegeMappingInfo vegeInfo) {
        // TODO Auto-generated method stub
        super.update(vegeInfo);
    }

    @Override
    public void delete(TblVegeMappingInfo tblVegeMappingInfo) {
        // TODO Auto-generated method stub
        super.delete(tblVegeMappingInfo);
    }

    @Override
    public String save(TblVegeMappingInfo tblVegeMappingInfo) {
        // TODO Auto-generated method stub
        super.save(tblVegeMappingInfo);
        return Constants.SUCCESS_CODE;
    }

    @Override
    protected Class getReferenceClass() {
        // TODO Auto-generated method stub
        return TblVegeMappingInfo.class;
    }

    @Override
    public TblVegeMappingInfo get(TblVegeMappingInfoPK key) {
        // TODO Auto-generated method stub
        return (TblVegeMappingInfo) get(getReferenceClass(), key);
    }

    
}
