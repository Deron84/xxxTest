package com.sdses.dao.iface.pos;

import com.sdses.po.TblVegeMappingInfo;
import com.sdses.po.TblVegeMappingInfoPK;

public interface TblVegeMappingInfoDao {
    
    public TblVegeMappingInfo get(TblVegeMappingInfoPK key);
    
    public String save(TblVegeMappingInfo tblVegeMappingInfo);
    
    public void delete(TblVegeMappingInfo vegCode);
    
    public void update(TblVegeMappingInfo vegeInfo);
}
