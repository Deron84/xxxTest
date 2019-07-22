package com.sdses.dao.impl.mchnt;


import com.huateng.common.Constants;
import com.sdses.dao._RootDAO;
import com.sdses.po.TcMchntMap;
import com.sdses.po.TcMchntMapPK;

public class TcMchntMapDao extends _RootDAO<com.sdses.po.TcMchntMap> implements com.sdses.dao.iface.mchnt.TcMchntMapDao {
    @Override
    public void update(TcMchntMap mapData) {
        // TODO Auto-generated method stub
        super.update(mapData);
    }

    @Override
    public void delete(TcMchntMap tcMchntMap) {
        // TODO Auto-generated method stub
        super.delete(tcMchntMap);
    }

    @Override
    public String save(TcMchntMap tcMchntMap) {
        // TODO Auto-generated method stub
        super.save(tcMchntMap);
        return Constants.SUCCESS_CODE;
    }

    @Override
    protected Class getReferenceClass() {
        // TODO Auto-generated method stub
        return TcMchntMap.class;
    }

    @Override
    public TcMchntMap get(TcMchntMapPK tcMchntMapPK) {
        // TODO Auto-generated method stub
        return (TcMchntMap) get(getReferenceClass(), tcMchntMapPK);
    }
 
}
