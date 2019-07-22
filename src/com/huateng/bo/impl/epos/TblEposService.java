package com.huateng.bo.impl.epos;

import java.util.List;

import com.huateng.po.epos.TblCupBcMap;
import com.huateng.po.epos.TblFirstPage;
import com.huateng.po.epos.TblMenuMsg;
import com.huateng.po.epos.TblMenuMsgPK;
import com.huateng.po.epos.TblPptMsg;
import com.huateng.po.epos.TblPptMsgPK;
import com.huateng.po.epos.TblPrtMsg;
import com.huateng.po.epos.TblPrtMsgPK;
import com.huateng.po.epos.TblRspMsg;
import com.huateng.po.epos.TblRspMsgPK;
import com.huateng.po.epos.TblTermTxn;
import com.huateng.po.epos.TblVerMng;
import com.huateng.po.epos.TblVerMngPK;

public interface TblEposService {

	/*
	 * 首页提示信息
	 */
	/**
	 * 保存首页提示信息
	 * @param TblFirstPage
	 * @return String
	 * @throws Exception
	 * @author Gavin
	 */
	public String save(TblFirstPage inf) throws Exception;
	
	public String updateFirstPage(List<TblFirstPage> list) throws Exception;
	
	public String delete(String brhId) throws Exception;
	
	public TblFirstPage get(String brhId) throws Exception;
	
	
	/*
	 * 终端打印模板
	 */
	public String save(TblPrtMsg inf) throws Exception;
	
	public String updatePrtMsg(List<TblPrtMsg> list) throws Exception;
	
	public String delete(TblPrtMsgPK id) throws Exception;
	
	public TblPrtMsg get(TblPrtMsgPK id) throws Exception;
	
	
	/**
	 * 获取主键
	 * @param brhId
	 * @param menuLevel
	 * @param verId
	 * @return
	 */
	public String getMenuId(String brhId,String menuLevel,String verId,String menuPreId1,String menuPreId2);
	/**
	 * 判断是否有其上级菜单
	 * @param brhId
	 * @param menuLevel
	 * @param verId
	 * @param menuId
	 * @return
	 */
	public boolean isPreMenuIdExist(String brhId, String menuLevel,String verId,String menuId);
	/**
	 * 获取菜单信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public TblMenuMsg get(TblMenuMsgPK id) throws Exception;
	/**
	 * 新增终端菜单信息
	 * @param tblMenuMsg
	 * @return
	 * @throws Exception
	 */
	public String save(TblMenuMsg tblMenuMsg) throws Exception;
	/**
	 * 修改终端菜单信息
	 * @param tblMenuMsg
	 * @return
	 * @throws Exception
	 */
	public String update(TblMenuMsg tblMenuMsg) throws Exception;
	
	/**
	 * 新增交易提示信息
	 * @param tblPptMsg
	 * @return
	 * @throws Exception
	 */
	public String save(TblPptMsg tblPptMsg) throws Exception;

	/**
	 * 修改交易提示信息
	 * @param tblPptMsg
	 * @return
	 * @throws Exception
	 */
	public String update(TblPptMsg tblPptMsg) throws Exception;
	
	/**
	 * 删除交易提示信息
	 * @param tblPptMsgPK
	 * @return
	 * @throws Exception
	 */
	public String delete(TblPptMsgPK id) throws Exception;
	
	/*
	 * 返回码说明
	 */
	public String save(TblRspMsg inf) throws Exception;
	
	public String updateRspMsg(List<TblRspMsg> list) throws Exception;
	
	public String delete(TblRspMsgPK id) throws Exception;
	
	public TblRspMsg get(TblRspMsgPK id) throws Exception;
	/**
	 * 新增终端交易映射信息
	 * @param tblTermTxn
	 * @return
	 * @throws Exception
	 */
	public String save(TblTermTxn tblTermTxn) throws Exception;
	/**
	 * 修改终端交易映射信息
	 * @param tblTermTxn
	 * @return
	 * @throws Exception
	 */
	public String update(TblTermTxn tblTermTxn) throws Exception;
	/**
	 * 删除终端交易映射信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String delTermTxn(String id) throws Exception;
	/**
	 * 获取终端交易映射交易
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public TblTermTxn getTermTxn(String id) throws Exception;
	/**
	 * 终端版本管理
	 * @param tblVerMng
	 * @return
	 * @throws Exception
	 */
	public String save(TblVerMng tblVerMng) throws Exception;
	/**
	 * 删除终端版本管理
	 * @param brhId
	 * @param verId
	 * @return
	 * @throws Exception
	 */
	public String delVerInfo(String brhId,String verId) throws Exception;
	/**
	 * 获取终端版本号
	 * @param pk
	 * @return
	 * @throws Exception
	 */
	public TblVerMng getVerInfo(TblVerMngPK pk) throws Exception;
	/**
	 * 获取终端版本号
	 * @param pk
	 * @return
	 * @throws Exception
	 */
	public int getVerInfoNew(TblVerMngPK pk) throws Exception;
	/**
	 * 获取版本号
	 * @param pptId
	 * @param msgType
	 * @param usageKey
	 * @param verId
	 * @return
	 * @throws Exception
	 */
	public int getTmpId(int pptId,String msgType,String usageKey,String verId) throws Exception;
	/**
	 * 判断是否已存在该提示信息
	 * @param pk
	 * @return
	 * @throws Exception
	 */
	public boolean isExist(TblPptMsgPK pk) throws Exception;
	/**
	 * 银联人行映射信息
	 * @param tblCupBcMap
	 * @return
	 * @throws Exception
	 */
	public String save(TblCupBcMap tblCupBcMap) throws Exception;
	/**
	 * 删除银联人行映射信息
	 * @param cupId
	 * @return
	 * @throws Exception
	 */
	public String delCupBcMap(String cupId) throws Exception;
	/**
	 * 获取银联人行映射信息
	 * @param cupId
	 * @return
	 * @throws Exception
	 */
	public TblCupBcMap getCupBcInfo(String cupId) throws Exception;
	/**
	 * 更新银联人行映射信息
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public String updateCupBcMap(List<TblCupBcMap> list) throws Exception;
}
