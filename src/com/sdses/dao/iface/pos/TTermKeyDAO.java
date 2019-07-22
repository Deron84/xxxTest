package com.sdses.dao.iface.pos;

import com.huateng.po.TTermKey;

public interface TTermKeyDAO {
    public int queryForInt(TTermKey tTermKey) throws Exception;
    public String queryForKey() throws Exception;
    public int addDate(TTermKey tTermKey) throws Exception;
}
