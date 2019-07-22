package com.sdses.dao.iface.pos;

import com.sdses.po.TblVegeCodeInfo;

public interface TblVegeCodeInfoDao {
    
    public TblVegeCodeInfo get(java.lang.String key);
    
    public String save(TblVegeCodeInfo vegeCodeInfo);
    
    public void delete(TblVegeCodeInfo vegCode);
    
    public void update(TblVegeCodeInfo vegeInfo);
}
