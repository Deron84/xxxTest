package com.sdses.bo.mchnt;

import java.util.List;

import com.sdses.po.TAirpayMap;
import com.sdses.po.TAirpayMapPK;

public interface T20111BO {
	/**
     * 
     * //TODO 新增微信、支付宝渠道数据
     *
     * @param tAirpayMap
     * @return
     */
	public int addDate(TAirpayMap tAirpayMap) throws Exception;
	/**
     * 
     * //TODO 删除微信、支付宝渠道数据
     *
     * @param tAirpayMapPK
     * @return
     */
	public int deleteDate(TAirpayMapPK tAirpayMapPK) throws Exception;
	/**
     * 
     * //TODO 查询该记录是否存在
     *
     * @param 银联商户号
     * @param 银联终端号
     * @param 支付方式
     * @return
     */
    public int isExitRecord(TAirpayMapPK tAirpayMapPK) throws Exception;
    /**
     * 
     * //TODO 修改微信、支付宝渠道数据
     *
     * @param tAirpayMap
     * @return
     */
    public int updateData(TAirpayMap tAirpayMap) throws Exception;
    /**
     * 
     * //TODO 查询该支付宝应用ID是否存在
     *
     * @param 支付宝应用ID
     * @return
     */
    public List<Object[]> isExitAliDate(String appId) throws Exception;
    /**
     * 
     * //TODO 查询该微信应用ID下的该商户号是否存在
     *
     * @param 微信应用ID
     * @param 商户号
     * @return
     */
    public List<Object[]> isExitWeDate(String appId,String mchId) throws Exception;
}
