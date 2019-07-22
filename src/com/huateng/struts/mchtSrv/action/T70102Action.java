/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   Gavin      2011-9-29       first release
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
package com.huateng.struts.mchtSrv.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.huateng.bo.impl.mchtSrv.TblPaperService;
import com.huateng.common.Constants;
import com.huateng.po.mchtSrv.TblPaperDefInf;
import com.huateng.po.mchtSrv.TblPaperDefInfPK;
import com.huateng.po.mchtSrv.TblPaperOptInf;
import com.huateng.po.mchtSrv.TblPaperOptInfPK;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.InformationUtil;
import com.huateng.system.util.JSONBean;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-9-29
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class T70102Action extends BaseSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TblPaperService service = (TblPaperService) ContextUtil.getBean("TblPaperService");
	
	public String add(){
		
		try {
		
			
			//判断当前状态是否为可编辑状态
			if (!InformationUtil.checkStates(getOperator().getOprId())) {
				return returnService("当前状态为不可编辑状态，请刷新后重试。");
			}
			
			
			TblPaperDefInf inf = new TblPaperDefInf();
			
			TblPaperDefInfPK id = new TblPaperDefInfPK();
			id.setPaperId(Constants.INIT_PAPER_ID);
			id.setQuesId(InformationUtil.getQuestionId());
			
			inf.setId(id);
			inf.setQuesIndex(InformationUtil.getMaxQuestionIndex());
			inf.setQuestion(question);
			
			List<TblPaperOptInf> list = new ArrayList<TblPaperOptInf>();
			inf.setOptions(buildOptions(list, inf.getId().getQuesId()));
			
			inf.setCrtUser(getOperator().getOprId());
			inf.setCrtTime(CommonFunction.getCurrentDateTime());
			rspCode = service.save(inf, list);
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
		
	}
	
	
	public String update(){
		
		try {
			//判断当前状态是否为可编辑状态
			if (!InformationUtil.checkStates(getOperator().getOprId())) {
				return returnService("当前状态为不可编辑状态，请刷新后重试。");
			}
			
			TblPaperDefInfPK id = new TblPaperDefInfPK();
			id.setPaperId(paperId);
			id.setQuesId(quesId);
			
			TblPaperDefInf inf = service.getPaperDef(id);
			if (null == inf) {
				return returnService("没有找到指定的信息，请刷新后重试！");
			}
			
			inf.setQuestion(question);
			
			List<TblPaperOptInf> list = new ArrayList<TblPaperOptInf>();
			inf.setOptions(buildOptions(list, inf.getId().getQuesId()));
			
			inf.setUpdUser(getOperator().getOprId());
			inf.setUpdTime(CommonFunction.getCurrentDateTime());
			rspCode = service.update(inf, list);
			
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
	}
	
	public String delete(){
		
		try {
			//判断当前状态是否为可编辑状态
			if (!InformationUtil.checkStates(getOperator().getOprId())) {
				return returnService("当前状态为不可编辑状态，请刷新后重试。");
			}
			TblPaperDefInfPK id = new TblPaperDefInfPK();
			id.setPaperId(paperId);
			id.setQuesId(quesId);
			
			TblPaperDefInf inf = service.getPaperDef(id);
			if (null == inf) {
				return returnService("没有找到指定的信息，请刷新后重试！");
			}
			
			rspCode = service.delete(inf);
			
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
		
	}
	
	public String active(){
		
		try {
			//计算当前总分
			String sql = "select nvl(sum(max(point)),0) from TBL_PAPER_OPT_INF group by QUES_ID";
			BigDecimal count = (BigDecimal) commQueryDAO.findBySQLQuery(sql).get(0);
			if (count.intValue() != 100 ) {
				return returnService("当前问卷的权重之和不为100%，暂不能激活该问卷。");
			}
			
			rspCode = service.active(getOperator().getOprId());
			
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
	}
	
	public String lock(){
		
		try {
			rspCode = service.lock(getOperator().getOprId());
			
			return returnService(rspCode);
		} catch (Exception e) {
			e.printStackTrace();
			return returnService(rspCode, e);
		}
	}
	
	
	public String buildOptions(List<TblPaperOptInf> list, String questId){
		
		JSONBean jsonBean = new JSONBean();
		jsonBean.parseJSONArrayData(data);
		int len = jsonBean.getArray().size();
		StringBuffer options = new StringBuffer();
		for(int i = 0; i < len; i++) {
			jsonBean.setObject(jsonBean.getJSONDataAt(i));
			
			//选项
			String option = jsonBean.getJSONDataAt(i).getString("option");
			//分值
			String point = jsonBean.getJSONDataAt(i).getString("point");
			
			TblPaperOptInf inf = new TblPaperOptInf();
			
			TblPaperOptInfPK id = new TblPaperOptInfPK();
			id.setPaperId(Constants.INIT_PAPER_ID);
			id.setQuesId(questId);
			id.setOptId(InformationUtil.getOptionId(i));
			
			inf.setId(id);
			inf.setOpt(option);
			inf.setPoint(Integer.parseInt(point));
			
			list.add(inf);
			
			options.append(option).append("(").append(point).append("分)， ");
		}
		
		return options.substring(0, options.lastIndexOf(")") + 1);
	}
	
	private String paperId;
	
	private String quesId;
	
	private String question;
	
	private String data;
	
	
	
	/**
	 * @return the paperId
	 */
	public String getPaperId() {
		return paperId;
	}


	/**
	 * @param paperId the paperId to set
	 */
	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}


	/**
	 * @return the quesId
	 */
	public String getQuesId() {
		return quesId;
	}

	/**
	 * @param quesId the quesId to set
	 */
	public void setQuesId(String quesId) {
		this.quesId = quesId;
	}

	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * @param question the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}


	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}


	@Override
	public String getMsg() {
		return msg;
	}

	@Override
	public boolean isSuccess() {
		return success;
	}

}
