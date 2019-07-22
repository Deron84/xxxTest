package com.huateng.bo.term;

import java.util.HashMap;
import java.util.List;

import com.huateng.common.Operator;
import com.huateng.po.TblTermInf;
import com.huateng.po.TblTermInfTmp;
import com.huateng.po.TblTermManagement;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-10
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @version 1.0
 */
public interface TermManagementBO {
	/**
	 * 入库
	 * @param map
	 * @param number
	 * @return
	 * 2011-6-10下午01:24:18
	 */
	public String storeTerminal(HashMap map);
	/**
	 * 批量入库
	 * @param map
	 * @return
	 * 2011-6-13下午05:06:57
	 */
	public String storeTerminals(HashMap map);
	/**
	 * 获取终端库存表的nextId
	 * @return
	 * 2011-6-10下午03:50:14
	 */
	public String getNextId(String brhId);
	/**
	 * 终端库存作废
	 * @param map
	 * @return
	 * 2011-6-14下午01:08:51
	 */
	public String invalidTerminal(HashMap map);
	/**
	 * 终端库存下发
	 * @param manufacturer
	 * @param terminalType
	 * @param number
	 * @param termType
	 * @param brhId
	 * @return
	 * 2011-6-15上午11:30:54
	 */
	public List<TblTermManagement> queryTerminal(String manufacturer,String terminalType,int number,String termType);
	/**
	 * 获取批次号
	 * @return
	 * 2011-6-15下午12:30:52
	 */
	public String getNextBatchNo();
	/**
	 * 更新终端库存状态
	 * @param terminal
	 * @param state
	 * @param oprId
	 * @param mech
	 * @return
	 * 2011-6-15下午01:01:50
	 */
	public boolean updTerminal(TblTermManagement terminal, String state,String oprId,String mech);
	/**
	 * 批次拒绝
	 * @param batchNo
	 * @param oprId
	 * @return
	 * 2011-6-15下午02:50:28
	 */
	public String refuseTerminals(String batchNo,String termId,String mechNo,String oprId);
	/**
	 * 审核拒绝
	 * @param termNo
	 * @param oprId
	 * @return
	 * 2011-6-15下午02:50:51
	 */
	public String refuseTerminal(String batchNo,String termId,String mechNo,String oprId);
	/**
	 * 通过审核
	 * @param termNo
	 * @param oprId
	 * @return
	 * 2011-6-15下午02:51:50
	 */
	public String reciTerminal(String batchNo,String termId,String mechNo,String oprId);
	/**
	 * 批次号通过
	 * @param batchNo
	 * @param oprId
	 * @return
	 * 2011-6-15下午02:52:10
	 */
	public String reciTerminals(String batchNo,String termId,String mechNo,String oprId);
	/**
	 * 批次签收
	 * @param batchNo
	 * @param termId
	 * @param oprId
	 * @return
	 * 2011-6-16下午05:26:26
	 */
	public String signTerminals(String batchNo,String termId,String oprId);
	/**
	 * 单笔签收
	 * @param batchNo
	 * @param termId
	 * @param oprId
	 * @return
	 * 2011-6-16下午05:25:52
	 */
	public String signTerminal(String batchNo,String termId,String oprId);
	/**
	 * 回退操作
	 * @param action
	 * @param termId
	 * @param operators
	 * @return
	 * 2011-6-17上午10:57:44
	 */
	public String bankTermianl(int action,String termId,Operator operator);
	/**
	 * 获取终端库存信息
	 * @param termNo
	 * @return
	 * 2011-6-22上午10:42:24
	 */
	public TblTermManagement getTerminal(String termId);
	/**
	 * 新增终端审核拒绝解绑终端库存
	 * @param termId
	 * 2011-6-22下午03:53:58
	 */
	public boolean refuseTerm(String termId,String oprId);
	/**
	 * 终端绑定
	 * @param tblTermInf
	 * @return
	 * 2011-8-3下午05:38:47
	 */
	public boolean bindTermInfo(TblTermManagement tblTermManagement,String oprId,TblTermInf tblTermInf,TblTermInfTmp tblTermInfTmp);
}
