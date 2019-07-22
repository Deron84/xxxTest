package com.sdses.dao.iface.mchnt;

import java.util.List;

import com.sdses.po.TcMchntMap;
import com.sdses.po.TcMchntMapPK;

public interface TcMchntMapPoDAO {
	/**
     * 
     * //TODO 更新扫码支付商户
     *
     * @param TcMchntMap
     * @return
     */
    public int update(TcMchntMap tcMchntMap) throws Exception;
	/**
     * 
     * //TODO 更新扫码支付商户
     *
     * @param TcMchntMap
     * @return
     */
    public int updateData(TcMchntMap tcMchntMap) throws Exception;
	/**
     * 
     * //TODO 更新银联证书
     *
     * @param 银联商户号
     * @param  证书名称
     * @return
     */
    public int updateCertData(String ylMchntNo, String uploadFileName,String brhFlag,String scanFlag) throws Exception;
    /**
     * 
     * //TODO 查询该商户是否存在
     *
     * @param 银联商户号
     * @param 银联终端号
     * @param 扫码支付商户号
     * @return
     */
    public int isExitMchnt(String id) throws Exception;
    /**
     * 
     * //TODO 删除指定商户
     *
     * @param 银联商户号
     * @param 银联终端号
     * @param 扫码支付商户号
     * @return
     */
    public int delete(TcMchntMapPK tcMchntMapPK,String id) throws Exception;
    /**
     * 
     * //TODO 删除指定商户
     *
     * @param 银联商户号
     * @param 银联终端号
     * @param 扫码支付商户号
     * @return
     */
    public int delete2(TcMchntMapPK tcMchntMapPK,String id) throws Exception;
    /**
     * 
     * //TODO 删除某个聚合的指定商户
     *
     * @param 银联商户号
     * @param 银联终端号
     * @param 扫码支付商户号
     * @param 聚合标识
     * @return
     */
    public int deleteData(TcMchntMapPK tcMchntMapPK,String mergeFlag) throws Exception;
}
