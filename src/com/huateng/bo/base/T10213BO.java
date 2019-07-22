package com.huateng.bo.base;

import com.huateng.po.TTermKey;

public interface T10213BO {
	/**
     * 
     * //TODO 查询该商户号+终端号是否存在
     *
     * @param tTermKey
     * @return
     */
    public int queryForInt(TTermKey tTermKey) throws Exception;
    /**
     * 
     * //TODO 查询密钥参数
     *
     * @return
     */
    public String queryForKey() throws Exception;
    /**
     * 
     * //TODO 添加国密终端密钥绑定
     *
     * @param tTermKey
     * @return
     */
    public int addDate(TTermKey tTermKey) throws Exception;
}
