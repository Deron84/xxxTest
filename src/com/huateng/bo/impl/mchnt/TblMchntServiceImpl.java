/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   Gavin      2011-6-17       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2011 Huateng Software, Inc. All rights reserved.
 *
 *       This software is the confidential and proprietary information of
 *       Shanghai HUATENG Software Co., Ltd. ("Confidential Information").
 *       You shall not disclose such Confidential Information and shall use it
 *       only in accordance with the terms of the license agreement you entered
 *       into with Huateng.
 *
 * Warning:
 * =============================================================================
 *
 */
package com.huateng.bo.impl.mchnt;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeanUtils;

import com.huateng.bo.mchnt.T20101BO;
import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.common.StringUtil;
import com.huateng.common.TblMchntInfoConstants;
import com.huateng.dao.iface.mchnt.ITblGroupMchtInfDAO;
import com.huateng.dao.iface.mchnt.ITblMchtAcctInfDAO;
import com.huateng.dao.iface.mchnt.ITblMchtBaseInfDAO;
import com.huateng.dao.iface.mchnt.ITblMchtBaseInfTmpDAO;
import com.huateng.dao.iface.mchnt.ITblMchtLicenceInfDAO;
import com.huateng.dao.iface.mchnt.ITblMchtLicenceInfTmpDAO;
import com.huateng.dao.iface.mchnt.ITblMchtSettleInfDAO;
import com.huateng.dao.iface.mchnt.ITblMchtSettleInfTmpDAO;
import com.huateng.dao.iface.mchnt.TblMchntRefuseDAO;
import com.huateng.dao.iface.mchnt.TblMchntUserDAO;
import com.huateng.dao.iface.mchtSrv.TblMchtCupInfoDAO;
import com.huateng.dao.iface.mchtSrv.TblMchtCupInfoTmpDAO;
import com.huateng.dao.iface.term.TblTermInfDAO;
import com.huateng.dao.iface.term.TblTermInfTmpDAO;
import com.huateng.dao.iface.term.TblTermRefuseDAO;
import com.huateng.po.TblMchntRefuse;
import com.huateng.po.TblMchntRefusePK;
import com.huateng.po.TblMchtCupInfo;
import com.huateng.po.TblMchtCupInfoTmp;
import com.huateng.po.TblTermInfTmpPK;
import com.huateng.po.TblTermRefuse;
import com.huateng.po.TblTermRefusePK;
import com.huateng.po.mchnt.TblGroupMchtInf;
import com.huateng.po.mchnt.TblMchntUser;
import com.huateng.po.mchnt.TblMchtAcctInf;
import com.huateng.po.mchnt.TblMchtBaseInf;
import com.huateng.po.mchnt.TblMchtBaseInfTmp;
import com.huateng.po.mchnt.TblMchtLicenceInf;
import com.huateng.po.mchnt.TblMchtLicenceInfTmp;
import com.huateng.po.mchnt.TblMchtSettleInf;
import com.huateng.po.mchnt.TblMchtSettleInfTmp;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.StatusUtil;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-17
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class TblMchntServiceImpl implements TblMchntService {

	public ITblMchtBaseInfTmpDAO tblMchtBaseInfTmpDAO;

	public ITblMchtBaseInfDAO tblMchtBaseInfDAO;

	public ITblMchtSettleInfTmpDAO tblMchtSettleInfTmpDAO;

	public ITblMchtSettleInfDAO tblMchtSettleInfDAO;
	
	public ITblMchtLicenceInfTmpDAO tblMchtLicenceInfTmpDAO;
	
	public ITblMchtLicenceInfDAO tblMchtLicenceInfDAO;

	public TblMchntRefuseDAO tblMchntRefuseDAO;

	public ITblGroupMchtInfDAO tblGroupMchtInfDAO;

	public ITblMchtAcctInfDAO tblMchtAcctInfDAO;
	
	public TblMchtCupInfoDAO tblMchtCupInfoDAO;
	
	public TblMchtCupInfoTmpDAO tblMchtCupInfoTmpDAO;
	
	public TblTermInfTmpDAO tblTermInfTmpDAO;
	
	public TblTermInfDAO tblTermInfDAO;
	
	public TblTermRefuseDAO tblTermRefuseDAO;
	private TblMchntUserDAO tblMchtUserDAO;
	/*
	 * 保存商户信息至临时表
	 * 
	 * @see
	 * com.huateng.bo.impl.mchnt.TblMchntService#saveTmp(com.huateng.po.mchnt
	 * .TblMchtBaseInfTmp, as.huateng.po.management.mer.TblMchtSettleInfTmp)
	 */
	public String saveTmp(TblMchtBaseInfTmp tblMchtBaseInfTmp,
			TblMchtSettleInfTmp tblMchtSettleInfTmp, TblMchtCupInfoTmp tblMchtCupInfoTmp) {
		T20101BO t20101BO = (T20101BO) ContextUtil.getBean("T20101BO");
		if(tblMchtBaseInfTmpDAO.get(tblMchtBaseInfTmp.getMchtNo()) != null) {
			return "商户ID已经存在";
		}
		//添加判断：1、银联商户号不能重复2、一卡通商户号不能重复
		if(StringUtils.isNotBlank(tblMchtBaseInfTmp.getMappingMchntcdOne())){
			List<Object[]> lists=t20101BO.findListByMappingMchntcdOne(tblMchtBaseInfTmp.getMappingMchntcdOne());
			if(lists!=null&&lists.size()>0){
				return "商户号"+tblMchtBaseInfTmp.getMappingMchntcdOne()+"已经存在";
			}
		}
        if(StringUtils.isNotBlank(tblMchtBaseInfTmp.getMappingMchntcdTwo())){
        	List<Object[]> lists=t20101BO.findListByMappingMchntcdTwo(tblMchtBaseInfTmp.getMappingMchntcdTwo());
			if(lists!=null&&lists.size()>0){
				return "商户"+tblMchtBaseInfTmp.getMappingMchntcdTwo()+"已经存在";
			}
		}
		tblMchtBaseInfTmpDAO.save(tblMchtBaseInfTmp);
		tblMchtSettleInfTmpDAO.save(tblMchtSettleInfTmp);
		if("Z".equals(tblMchtBaseInfTmp.getConnType())){
			tblMchtCupInfoTmpDAO.save(tblMchtCupInfoTmp);
		}
		
		return Constants.SUCCESS_CODE;
	}
	
	/*
	 * 保存证照号信息至临时表
	 * 
	 */
	public String saveLicenceTmp(TblMchtLicenceInfTmp tblMchtLicenceInfTmp) {

		if(tblMchtLicenceInfTmpDAO.get(tblMchtLicenceInfTmp.getLicenceNo()) != null) {
			return "此证照号已经存在";
		}
		tblMchtLicenceInfTmpDAO.save(tblMchtLicenceInfTmp);

		return Constants.SUCCESS_CODE;
	}
	
	/*
	 * 更新证照号信息至临时表
	 * 
	 */
	public String updateLicenceTmp(TblMchtLicenceInfTmp tblMchtLicenceInfTmp) {

		tblMchtLicenceInfTmpDAO.update(tblMchtLicenceInfTmp);

		return Constants.SUCCESS_CODE;
	}

	/*
	 * 更新商户信息至临时表
	 * 
	 * @see
	 * com.huateng.bo.impl.mchnt.TblMchntService#updateTmp(com.huateng.po.mchnt
	 * .TblMchtBaseInfTmp, com.huateng.po.mchnt.TblMchtSettleInfTmp)
	 */
	public String updateTmp(TblMchtBaseInfTmp tblMchtBaseInfTmp,
			TblMchtSettleInfTmp tblMchtSettleInfTmp, TblMchtCupInfoTmp tblMchtCupInfoTmp) {

		tblMchtBaseInfTmpDAO.update(tblMchtBaseInfTmp);
		tblMchtSettleInfTmpDAO.update(tblMchtSettleInfTmp);
		if("Z".equals(tblMchtBaseInfTmp.getConnType())){
			tblMchtCupInfoTmpDAO.update(tblMchtCupInfoTmp);
		}
		
		return Constants.SUCCESS_CODE;
	}

	/*
	 * 商户审核通过
	 * 
	 * @see com.huateng.bo.impl.mchnt.TblMchntService#accept(java.lang.String)
	 */
	public String accept(String mchntId) throws IllegalAccessException,
			InvocationTargetException {

		TblMchtBaseInfTmp tmp = tblMchtBaseInfTmpDAO.get(mchntId);
		TblMchtSettleInfTmp tmpSettle = tblMchtSettleInfTmpDAO.get(mchntId);
		TblMchtCupInfoTmp tmpCup = tblMchtCupInfoTmpDAO.get(mchntId);
		if (null == tmp || null == tmpSettle) {
			return "没有找到商户的临时间联信息，请重试";
		}

		// 取得原始信息
		TblMchtBaseInf inf = tblMchtBaseInfDAO.get(tmp.getMchtNo());
		TblMchtSettleInf infSettle = tblMchtSettleInfDAO.get(tmpSettle
				.getMchtNo());
		
		if (null == inf) {
			inf = new TblMchtBaseInf();
		}
		if (null == infSettle) {
			infSettle = new TblMchtSettleInf();
		}
		
		String feeBackFlg = infSettle.getFeeBackFlg();
		String statu = tmp.getMchtStatus();
		String connType = tmp.getConnType();
		
		// 更新时间和柜员
		tmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		Operator opr = (Operator) ServletActionContext.getRequest()
				.getSession().getAttribute(Constants.OPERATOR_INFO);
		tmp.setUpdOprId(opr.getOprId());
		tmpSettle.setRecUpdTs(CommonFunction.getCurrentDateTime());
		
		//当前状态  后台要求fee_back_flg在商户新增审核通过时候置为0
		if(TblMchntInfoConstants.MCHNT_ST_NEW_UNCK.equals(statu)){
			tmpSettle.setRecCrtTs(CommonFunction.getCurrentDateTime());
			tmp.setRecCrtTs(CommonFunction.getCurrentDateTime());//记录商户入网日期，之后不再更改
		}else {
			tmpSettle.setFeeBackFlg(feeBackFlg);
		}
		// 获得下一状态
		tmp.setMchtStatus(StatusUtil.getNewMchntStatus(statu));
		
		//终端新增时MCC，所属机构，连接种类是选中商户后自动填充的，在修改商户通过时必须同步过去
		if("3".equals(statu)){
			String sql1 ="update TBL_TERM_INF set TERM_MCC ='"+ tmp.getMcc() +"'," +
						"TERM_BRANCH ='"+ tmp.getSignInstId() +"'," +
						"CONNECT_MODE = '"+ tmp.getConnType() +"' " +
						"where MCHT_CD = '"+ tmp.getMchtNo() +"'";
			String sql2 ="update TBL_TERM_INF_TMP set TERM_MCC ='"+ tmp.getMcc() +"'," +
						 "TERM_BRANCH ='"+ tmp.getSignInstId() +"'," +
						 "CONNECT_MODE = '"+ tmp.getConnType() +"' " +
						 "where MCHT_CD = '"+ tmp.getMchtNo() +"'";
			CommonFunction.getCommQueryDAO().excute(sql1);
			CommonFunction.getCommQueryDAO().excute(sql2);
		}
		
		/**
		 * 文档需求： 如果直联商户转成间联商户，按新增间联商户号的形式进行，原直联商户仅是状态变更为销户状态，原直联商户代码不做删除处理。经办复核。
		 * 直联商户如果要转成间联商户，需要新增间联商户信息，然后在直联商户管理中对原直联商户进行删除。
		 * 
		 * 现修改审核通过后，若发现时直联改间联则将原商户信息和清算信息注销 商户号按规则增加并直接置商户信息和清算信息为正常状态
		 * */
		
		
		// 复制新的信息
		BeanUtils.copyProperties(tmp, inf);
		BeanUtils.copyProperties(tmpSettle, infSettle);
		//将正式表信息和清算信息保存到数据库
		tblMchtBaseInfDAO.saveOrUpdate(inf);
		tblMchtSettleInfDAO.saveOrUpdate(infSettle);
		
		tblMchtBaseInfTmpDAO.update(tmp);//更新到数据库
		tblMchtSettleInfTmpDAO.update(tmpSettle);
		
		/*******************直联商户数据部分**************/
		TblMchtCupInfo infCup = new TblMchtCupInfo();
		if("Z".equals(connType)){
			
			if(null == tmpCup){
				return "没有找到商户的临时直联信息，请重试";
			}
			
			if("0".equals(StatusUtil.getNewMchntStatus(statu))){
				tmpCup.setMcht_status("1");//正常
			}else if("6".equals(StatusUtil.getNewMchntStatus(statu))){
				tmpCup.setMcht_status("2");//冻结
			}else if("9".equals(StatusUtil.getNewMchntStatus(statu))){
				tmpCup.setMcht_status("0");//注销
			}
			
			tmpCup.setUpd_opr_id(opr.getOprId());
			tmpCup.setUpd_ts(CommonFunction.getCurrentDateTime());
			if(TblMchntInfoConstants.MCHNT_ST_NEW_UNCK.equals(statu)){
				tmpCup.setCrt_ts(tmpCup.getUpd_ts());//记录商户入网日期，之后不再更改,因为出直联商户报表时变更类型比较创建日期和更新日期精确到秒，避免误差，和更新日期保持一致
			}	
			
			BeanUtils.copyProperties(tmpCup, infCup);
			tblMchtCupInfoTmpDAO.update(tmpCup);
			tblMchtCupInfoDAO.saveOrUpdate(infCup);
		}
		
		return Constants.SUCCESS_CODE;
	}

	/*
	 * 商户审核退回
	 * 
	 * @see com.huateng.bo.impl.mchnt.TblMchntService#back(java.lang.String,
	 * java.lang.String)
	 */
	public String back(String mchntId, String refuseInfo)
			throws IllegalAccessException, InvocationTargetException {

		TblMchtBaseInfTmp tmp = tblMchtBaseInfTmpDAO.get(mchntId);
		TblMchtSettleInfTmp tmpSettle = tblMchtSettleInfTmpDAO.get(mchntId);
		if (null == tmp || null == tmpSettle) {
			return "没有找到商户的临时信息，请重试";
		}
		
		String statu = tmp.getMchtStatus();
		String connType = tmp.getConnType();
		Operator opr = (Operator) ServletActionContext.getRequest()
				.getSession().getAttribute(Constants.OPERATOR_INFO);

		String refuseNm = mchntId+"-"+tmp.getMchtNm();
		// 记录退回信息
		TblMchntRefuse refuse = new TblMchntRefuse();
		TblMchntRefusePK tblMchntRefusePK = new TblMchntRefusePK(refuseNm,
				CommonFunction.getCurrentDateTime());
		refuse.setId(tblMchntRefusePK);
		refuse.setRefuseInfo(refuseInfo);
		refuse.setBrhId(tmp.getAgrBr());
		refuse.setOprId(opr.getOprId());

		// 获得退回信息
		refuse.setRefuseType(StatusUtil.getNextStatus("BM."
				+ tmp.getMchtStatus()));
		// 获得下一状态
		tmp.setMchtStatus(StatusUtil.getNextStatus("B."
				+ tmp.getMchtStatus()));

		// 更新时间和柜员
		tmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		tmp.setUpdOprId(opr.getOprId());
		tmpSettle.setRecUpdTs(CommonFunction.getCurrentDateTime());

		// 更新到数据库
		tblMchtBaseInfTmpDAO.update(tmp);
		tblMchtSettleInfTmpDAO.update(tmpSettle);
		tblMchntRefuseDAO.save(refuse);

		/*******************直联商户数据部分**************/
		TblMchtCupInfoTmp tmpCup = new TblMchtCupInfoTmp();
		if("Z".equals(connType)){
			tmpCup = tblMchtCupInfoTmpDAO.get(mchntId);
			
			if(null == tmpCup){
				return "没有找到商户的临时直联信息，请重试";
			}
			
			if("0".equals(StatusUtil.getNextStatus("B." + statu))){
				tmpCup.setMcht_status("1");//注销和冻结退回后正常
			}else if("2".equals(StatusUtil.getNextStatus("B." + statu))){
				tmpCup.setMcht_status("6");//新增待审核退回
			}else if("4".equals(StatusUtil.getNextStatus("B." + statu))){
				tmpCup.setMcht_status("4");//修改待审核退回
			}else if("6".equals(StatusUtil.getNextStatus("B." + statu))){
				tmpCup.setMcht_status("2");//恢复待审核退回
			}
			
			tmpCup.setUpd_opr_id(opr.getOprId());
			tmpCup.setUpd_ts(CommonFunction.getCurrentDateTime());
			
			tblMchtCupInfoTmpDAO.update(tmpCup);
		}
		
		return Constants.SUCCESS_CODE;
	}

	/*
	 * 商户审核拒绝
	 * 
	 * @see com.huateng.bo.impl.mchnt.TblMchntService#refuse(java.lang.String,
	 * java.lang.String)
	 */
	public String refuse(String mchntId, String refuseInfo)
			throws IllegalAccessException, InvocationTargetException {

		TblMchtBaseInfTmp tmp = tblMchtBaseInfTmpDAO.get(mchntId);
		TblMchtSettleInfTmp tmpSettle = tblMchtSettleInfTmpDAO.get(mchntId);
		TblMchtCupInfoTmp tmpCup = tblMchtCupInfoTmpDAO.get(mchntId);
		
		if (null == tmp || null == tmpSettle) {
			return "没有找到商户的临时信息，请重试";
		}
		Operator opr = (Operator) ServletActionContext.getRequest()
				.getSession().getAttribute(Constants.OPERATOR_INFO);

		String refuseNo = tmp.getMchtNo()+"-"+tmp.getMchtNm();
		// 记录拒绝信息
		TblMchntRefuse refuse = new TblMchntRefuse();
		TblMchntRefusePK tblMchntRefusePK = new TblMchntRefusePK(refuseNo,
				CommonFunction.getCurrentDateTime());
		refuse.setId(tblMchntRefusePK);
		refuse.setRefuseInfo(refuseInfo);
		refuse.setBrhId(tmp.getAgrBr());
		refuse.setOprId(opr.getOprId());

		// 获得拒绝信息
		refuse.setRefuseType(StatusUtil.getNextStatus("RM."
				+ tmp.getMchtStatus()));

		TblMchtCupInfo infCup =null;
		
		// 分别处理新增拒绝和其他拒绝
		if (TblMchntInfoConstants.MCHNT_ST_NEW_UNCK.equals(tmp.getMchtStatus())) {

			//新增拒绝时同时删除其下挂终端
			TblTermRefuse refuseTerm =null;
			TblTermRefusePK refuseTermPK =null;
			TblTermInfTmpPK tmpPK = null;
			String sql ="select trim(TERM_ID) from TBL_TERM_INF_TMP where trim(MCHT_CD) = '" + mchntId.trim() + "'";
			List<String> termCount = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
			
			if(termCount != null){
				refuseTerm = new TblTermRefuse();
				refuseTermPK = new TblTermRefusePK();
				tmpPK = new TblTermInfTmpPK();
				String sql2 = "";
				for(int i=0;i<termCount.size();i++){
					refuseTermPK.setMchtCd(refuseNo);
					refuseTermPK.setTermId(termCount.get(i));
					refuseTermPK.setTxnTime(CommonFunction.getCurrentDateTime());
					refuseTerm.setId(refuseTermPK);
					refuseTerm.setOprId(opr.getOprId());
					refuseTerm.setBrhId(tmp.getAgrBr());
					refuseTerm.setRefuseInfo(refuseInfo);
					refuseTerm.setRefuseType("mchnt"+StatusUtil.getNextStatus("RM."
							+ tmp.getMchtStatus()));
					
					tmpPK.setMchtCd(mchntId);
					tmpPK.setTermId(termCount.get(i));
					
					if(tblTermInfTmpDAO.get(tmpPK) == null){
						return "找不到终端['"+termCount.get(i)+"']的临时信息！";
					}
					//tblTermInfTmpDAO.delete(tmpPK);
					sql2 = "delete from TBL_TERM_INF_TMP where MCHT_CD= '"+mchntId+"' and TERM_ID='"+termCount.get(i)+"'";
					CommonFunction.getCommQueryDAO().excute(sql2);
					tblTermRefuseDAO.save(refuseTerm);
					if(tblTermInfDAO.get(tmpPK) != null){
						//tblTermInfDAO.delete(tmpPK);
						CommonFunction.getCommQueryDAO().excute("delete from TBL_TERM_INF where MCHT_CD= '"+mchntId+"' and TERM_ID='"+termCount.get(i)+"'");
					}
				}
			}
			
			if("Z".equals(tmp.getConnType())){
				if(null == tmpCup){
					return "没有找到商户的临时直联信息，请重试";
				}
				tblMchtCupInfoTmpDAO.delete(tmpCup);
			}
			
			tblMchtBaseInfTmpDAO.delete(tmp);
			tblMchtSettleInfTmpDAO.delete(tmpSettle);
			tblMchntRefuseDAO.save(refuse);
		} else {
			// 取得原始信息
			TblMchtBaseInf inf = tblMchtBaseInfDAO.get(tmp.getMchtNo());
			TblMchtSettleInf infSettle = tblMchtSettleInfDAO.get(tmpSettle.getMchtNo());
			
			if (null == inf || null == infSettle) {
				return "没有找到商户的正式信息，请重试";
			} 
			
			// 复制新的信息
			BeanUtils.copyProperties(inf, tmp);
			BeanUtils.copyProperties(infSettle, tmpSettle);
			
			// 更新时间和柜员
			tmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
			tmp.setUpdOprId(opr.getOprId());
			tmpSettle.setRecUpdTs(CommonFunction.getCurrentDateTime());
			
			// 更新到数据库 填写集团商户基本信息
			tblMchtBaseInfTmpDAO.update(tmp);
			tblMchtSettleInfTmpDAO.update(tmpSettle);
			tblMchntRefuseDAO.save(refuse);
			
			
			if("Z".equals(tmp.getConnType())){
				infCup = new TblMchtCupInfo();
				infCup = tblMchtCupInfoDAO.get(tmpCup.getMcht_no());
				
				if(null == tmpCup){
					return "没有找到商户的临时直联信息，请重试";
				}
				if(null == infCup){
					return "没有找到商户的正式直联信息，请重试";
				}
				// 复制新的信息
				BeanUtils.copyProperties(infCup, tmpCup);
				
				tmpCup.setUpd_opr_id(opr.getOprId());
				tmpCup.setUpd_ts(CommonFunction.getCurrentDateTime());
				
				tblMchtCupInfoTmpDAO.update(tmpCup);
			}
		}

		return Constants.SUCCESS_CODE;
	}

	/*
	 * 保存集团商户信息
	 * 
	 * @see com.huateng.bo.impl.mchnt.TblMchntService#saveGroup()
	 */
	public String saveGroup(TblGroupMchtInf inf, TblMchtAcctInf acctinf)
			throws IllegalAccessException, InvocationTargetException {

		tblGroupMchtInfDAO.save(inf);

		// 缺表暂时屏蔽
		// tblMchtAcctInfDAO.save(acctinf);

		return Constants.SUCCESS_CODE;
	}

	public TblMchtBaseInf getMccByMchntId(String mchntId)
			throws IllegalAccessException, InvocationTargetException {
		if(StringUtil.isNull(mchntId)){
			return null;
		}
		TblMchtBaseInf inf = tblMchtBaseInfDAO.get(mchntId);
		if (null == inf) {
			return null;
		} else {
			return inf;
		}
	}

	/*
	 * 获取集团商户基本信息
	 * 
	 * @see
	 * com.huateng.bo.impl.mchnt.TblMchntService#getGroupInf(java.lang.String)
	 */
	public TblGroupMchtInf getGroupInf(String mchntId)
			throws IllegalAccessException, InvocationTargetException {
		TblGroupMchtInf inf = tblGroupMchtInfDAO.get(StringUtil.fillValue(
				mchntId, 8, ' '));

		return inf;
	}

	/*
	 * 更新集团商户
	 * 
	 * @seecom.huateng.bo.impl.mchnt.TblMchntService#updateGroup(as.huateng.po.
	 * management.mer.TblGroupMchtInf,
	 * as.huateng.po.management.mer.TblMchtAcctInf)
	 */
	public String updateGroup(TblGroupMchtInf inf, TblMchtAcctInf acctinf)
			throws IllegalAccessException, InvocationTargetException {

		try {

			inf.setGroupMchtCd(CommonFunction.fillString(inf.getGroupMchtCd(),
					' ', 8, true));

			tblGroupMchtInfDAO.update(inf);
			return Constants.SUCCESS_CODE;
		} catch (Exception e) {
			e.printStackTrace();
			return Constants.DATA_OPR_FAIL;
		}
	}

	/*
	 * GET商户临时基本信息
	 * 
	 * @see
	 * com.huateng.bo.impl.mchnt.TblMchntService#getBaseInfTmp(java.lang.String)
	 */
	public TblMchtBaseInfTmp getBaseInfTmp(String mchntId)
			throws IllegalAccessException, InvocationTargetException {

		return tblMchtBaseInfTmpDAO.get(mchntId);
	}

	public TblMchtSettleInfTmp getSettleInfTmp(String mchntId)
			throws IllegalAccessException, InvocationTargetException {

		return tblMchtSettleInfTmpDAO.get(mchntId);
	}
	
	public TblMchtBaseInf getBaseInf(String mchntId)
			throws IllegalAccessException, InvocationTargetException {

		return tblMchtBaseInfDAO.get(mchntId);
	}

	public TblMchtSettleInf getSettleInf(String mchntId)
			throws IllegalAccessException, InvocationTargetException {

		return tblMchtSettleInfDAO.get(mchntId);
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.impl.mchnt.TblMchntService#updateBaseInfTmp(java.lang.String)
	 */
	public String updateBaseInfTmp(TblMchtBaseInfTmp inf)
			throws IllegalAccessException, InvocationTargetException {
		
		tblMchtBaseInfTmpDAO.update(inf);
		
		return Constants.SUCCESS_CODE;
	}

	public TblMchtLicenceInfTmp getLicenceInfTmp(String licenceNo)
			throws IllegalAccessException, InvocationTargetException {
		return tblMchtLicenceInfTmpDAO.get(licenceNo);
	}

	public TblMchtLicenceInf getLicenceInf(String licenceNo)
			throws IllegalAccessException, InvocationTargetException {
		return tblMchtLicenceInfDAO.get(licenceNo);
	}

	public String acceptLicence(String licenceNo)
			throws IllegalAccessException, InvocationTargetException {
		TblMchtLicenceInfTmp tmp = tblMchtLicenceInfTmpDAO.get(licenceNo);
		if (null == tmp ) {
			return "没有找到证照号的临时信息，请重试";
		}

		// 取得原始信息
		TblMchtLicenceInf inf = tblMchtLicenceInfDAO.get(tmp.getLicenceNo());
		if (null == inf) {
			inf = new TblMchtLicenceInf();
		}

		// 更新时间和柜员
		tmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		Operator opr = (Operator) ServletActionContext.getRequest()
				.getSession().getAttribute(Constants.OPERATOR_INFO);
		tmp.setUpdOprId(opr.getOprId());

		// 获得下一状态
		tmp.setEtpsState(StatusUtil.getNextStatus("A."
				+ tmp.getEtpsState()));
		
		// 复制新的信息
		BeanUtils.copyProperties(tmp, inf);
		
		// 更新到数据库
		tblMchtLicenceInfTmpDAO.update(tmp);
		tblMchtLicenceInfDAO.saveOrUpdate(inf);
		
		return Constants.SUCCESS_CODE;
	}

	public String backLicence(String licenceNo, String refuseInfo)
			throws IllegalAccessException, InvocationTargetException {
		TblMchtLicenceInfTmp tmp = tblMchtLicenceInfTmpDAO.get(licenceNo);
		if (null == tmp ) {
			return "没有找到证照号的临时信息，请重试";
		}
		Operator opr = (Operator) ServletActionContext.getRequest()
				.getSession().getAttribute(Constants.OPERATOR_INFO);

		// 获得下一状态
		tmp.setEtpsState(StatusUtil.getNextStatus("B."
				+ tmp.getEtpsState()));
		tmp.setRefuseMsg2(refuseInfo);

		// 更新时间和柜员
		tmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		tmp.setUpdOprId(opr.getOprId());

		// 更新到数据库
		tblMchtLicenceInfTmpDAO.update(tmp);
		//tblMchntRefuseDAO.save(refuse);

		return Constants.SUCCESS_CODE;
	}

	public String refuseLicence(String licenceNo, String refuseInfo)
			throws IllegalAccessException, InvocationTargetException {
		TblMchtLicenceInfTmp tmp = tblMchtLicenceInfTmpDAO.get(licenceNo);
		if (null == tmp ) {
			return "没有找到证照号的临时信息，请重试";
		}
		Operator opr = (Operator) ServletActionContext.getRequest()
				.getSession().getAttribute(Constants.OPERATOR_INFO);

		tmp.setReserved("refuse");
		tmp.setRefuseMsg2(refuseInfo);
		// 更新时间和柜员
		tmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		tmp.setUpdOprId(opr.getOprId());
		tblMchtLicenceInfTmpDAO.update(tmp);

		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.impl.mchnt.TblMchntService#deleteMchntAll(java.lang.String)
	 */
	public String deleteMchntAll(String mchtNo) {
		if(tblMchtBaseInfTmpDAO.get(mchtNo) == null 
				|| tblMchtSettleInfTmpDAO.get(mchtNo) == null){
			return "找不到该商户临时信息！";
		}
		
		tblMchtBaseInfTmpDAO.delete(mchtNo);
		tblMchtSettleInfTmpDAO.delete(mchtNo);
		if(tblMchtCupInfoTmpDAO.get(mchtNo) != null){
			tblMchtCupInfoTmpDAO.delete(mchtNo);
		}
		
		String sqlTmp ="delete TBL_TERM_INF_TMP where trim(MCHT_CD) = '" + mchtNo.trim() + "'";
		String sqlInf ="delete TBL_TERM_INF where trim(MCHT_CD) = '" + mchtNo.trim() + "'";
		CommonFunction.getCommQueryDAO().excute(sqlTmp);
		CommonFunction.getCommQueryDAO().excute(sqlInf);
		System.out.println("删除商户['"+mchtNo +"'],同时删除其下挂终端。");
		
		//同时还要删除清算表信息，分期表信息，
		return Constants.SUCCESS_CODE;
	}

	public TblMchtCupInfo getCupInfo(String mchntId)
			throws IllegalAccessException, InvocationTargetException {
		return tblMchtCupInfoDAO.get(mchntId);
	}

	public TblMchtCupInfoTmp getCupInfoTmp(String mchntId)
			throws IllegalAccessException, InvocationTargetException {
		return tblMchtCupInfoTmpDAO.get(mchntId);
	}

	public ITblMchtBaseInfTmpDAO getTblMchtBaseInfTmpDAO() {
		return tblMchtBaseInfTmpDAO;
	}

	public void setTblMchtBaseInfTmpDAO(
			ITblMchtBaseInfTmpDAO tblMchtBaseInfTmpDAO) {
		this.tblMchtBaseInfTmpDAO = tblMchtBaseInfTmpDAO;
	}

	public ITblMchtBaseInfDAO getTblMchtBaseInfDAO() {
		return tblMchtBaseInfDAO;
	}

	public void setTblMchtBaseInfDAO(ITblMchtBaseInfDAO tblMchtBaseInfDAO) {
		this.tblMchtBaseInfDAO = tblMchtBaseInfDAO;
	}

	public ITblMchtSettleInfTmpDAO getTblMchtSettleInfTmpDAO() {
		return tblMchtSettleInfTmpDAO;
	}

	public void setTblMchtSettleInfTmpDAO(
			ITblMchtSettleInfTmpDAO tblMchtSettleInfTmpDAO) {
		this.tblMchtSettleInfTmpDAO = tblMchtSettleInfTmpDAO;
	}

	public ITblMchtSettleInfDAO getTblMchtSettleInfDAO() {
		return tblMchtSettleInfDAO;
	}

	public void setTblMchtSettleInfDAO(ITblMchtSettleInfDAO tblMchtSettleInfDAO) {
		this.tblMchtSettleInfDAO = tblMchtSettleInfDAO;
	}

	public TblMchntRefuseDAO getTblMchntRefuseDAO() {
		return tblMchntRefuseDAO;
	}

	public void setTblMchntRefuseDAO(TblMchntRefuseDAO tblMchntRefuseDAO) {
		this.tblMchntRefuseDAO = tblMchntRefuseDAO;
	}

	public ITblGroupMchtInfDAO getTblGroupMchtInfDAO() {
		return tblGroupMchtInfDAO;
	}

	public void setTblGroupMchtInfDAO(ITblGroupMchtInfDAO tblGroupMchtInfDAO) {
		this.tblGroupMchtInfDAO = tblGroupMchtInfDAO;
	}

	public ITblMchtAcctInfDAO getTblMchtAcctInfDAO() {
		return tblMchtAcctInfDAO;
	}

	public void setTblMchtAcctInfDAO(ITblMchtAcctInfDAO tblMchtAcctInfDAO) {
		this.tblMchtAcctInfDAO = tblMchtAcctInfDAO;
	}

	/**
	 * @return the tblMchtLicenceInfTmpDAO
	 */
	public ITblMchtLicenceInfTmpDAO getTblMchtLicenceInfTmpDAO() {
		return tblMchtLicenceInfTmpDAO;
	}

	/**
	 * @param tblMchtLicenceInfTmpDAO the tblMchtLicenceInfTmpDAO to set
	 */
	public void setTblMchtLicenceInfTmpDAO(
			ITblMchtLicenceInfTmpDAO tblMchtLicenceInfTmpDAO) {
		this.tblMchtLicenceInfTmpDAO = tblMchtLicenceInfTmpDAO;
	}

	/**
	 * @return the tblMchtLicenceInfDAO
	 */
	public ITblMchtLicenceInfDAO getTblMchtLicenceInfDAO() {
		return tblMchtLicenceInfDAO;
	}

	/**
	 * @param tblMchtLicenceInfDAO the tblMchtLicenceInfDAO to set
	 */
	public void setTblMchtLicenceInfDAO(ITblMchtLicenceInfDAO tblMchtLicenceInfDAO) {
		this.tblMchtLicenceInfDAO = tblMchtLicenceInfDAO;
	}

	public TblMchtCupInfoDAO getTblMchtCupInfoDAO() {
		return tblMchtCupInfoDAO;
	}

	public void setTblMchtCupInfoDAO(TblMchtCupInfoDAO tblMchtCupInfoDAO) {
		this.tblMchtCupInfoDAO = tblMchtCupInfoDAO;
	}

	public TblMchtCupInfoTmpDAO getTblMchtCupInfoTmpDAO() {
		return tblMchtCupInfoTmpDAO;
	}

	public void setTblMchtCupInfoTmpDAO(TblMchtCupInfoTmpDAO tblMchtCupInfoTmpDAO) {
		this.tblMchtCupInfoTmpDAO = tblMchtCupInfoTmpDAO;
	}

	public TblTermInfTmpDAO getTblTermInfTmpDAO() {
		return tblTermInfTmpDAO;
	}

	public void setTblTermInfTmpDAO(TblTermInfTmpDAO tblTermInfTmpDAO) {
		this.tblTermInfTmpDAO = tblTermInfTmpDAO;
	}
	
	public TblTermInfDAO getTblTermInfDAO() {
		return tblTermInfDAO;
	}

	public void setTblTermInfDAO(TblTermInfDAO tblTermInfDAO) {
		this.tblTermInfDAO = tblTermInfDAO;
	}

	public TblTermRefuseDAO getTblTermRefuseDAO() {
		return tblTermRefuseDAO;
	}

	public void setTblTermRefuseDAO(TblTermRefuseDAO tblTermRefuseDAO) {
		this.tblTermRefuseDAO = tblTermRefuseDAO;
	}

	public TblMchntUserDAO getTblMchtUserDAO() {
		return tblMchtUserDAO;
	}

	public void setTblMchtUserDAO(TblMchntUserDAO tblMchtUserDAO) {
		this.tblMchtUserDAO = tblMchtUserDAO;
	}
	
	
	public void saveMchtUser(TblMchntUser tblMchtUser) {
		tblMchtUserDAO.save(tblMchtUser);
	}
}
