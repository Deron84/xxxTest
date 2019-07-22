package com.sdses.dao.iface.mchnt;

import com.sdses.po.TcMchntMap;
import com.sdses.po.TcMchntMapPK;

public interface TcMchntMapDao {
    
    public TcMchntMap get(TcMchntMapPK tcMchntMapPK);
    
    public String save(TcMchntMap tcMchntMap);
    
    public void delete(TcMchntMap tcMchntMap);
    
    public void update(TcMchntMap mapData);
}
