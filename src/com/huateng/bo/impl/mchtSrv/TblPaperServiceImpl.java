/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   Gavin      2011-9-26       first release
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
package com.huateng.bo.impl.mchtSrv;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.aop.ThrowsAdvice;

import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.common.StringUtil;
import com.huateng.dao.iface.mchtSrv.TblPaperDefInfDAO;
import com.huateng.dao.iface.mchtSrv.TblPaperOptInfDAO;
import com.huateng.dao.iface.mchtSrv.TblPaperSelInfDAO;
import com.huateng.po.mchtSrv.TblPaperDefInf;
import com.huateng.po.mchtSrv.TblPaperDefInfPK;
import com.huateng.po.mchtSrv.TblPaperOptInf;
import com.huateng.po.mchtSrv.TblPaperSelInf;
import com.huateng.po.mchtSrv.TblPaperSelInfPK;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.InformationUtil;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-9-26
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class TblPaperServiceImpl implements TblPaperService{

	
	/* 
	 * 新增题目
	 * 
	 * @see com.huateng.bo.impl.mchtSrv.TblPaperService#save(com.huateng.po.mchtSrv.TblPaperDefInf, java.util.List)
	 */
	public String save(TblPaperDefInf inf, List<TblPaperOptInf> list) throws Exception {
		
		//保存题目
		tblPaperDefInfDAO.save(inf);
		
		//保存选项
		for (TblPaperOptInf opt : list) {
			tblPaperOptInfDAO.save(opt);
		}
		
		return Constants.SUCCESS_CODE;
	}
	
	/* 
	 * 修改题目
	 * 
	 * @see com.huateng.bo.impl.mchtSrv.TblPaperService#update(com.huateng.po.mchtSrv.TblPaperDefInf, java.util.List)
	 */
	public String update(TblPaperDefInf inf, List<TblPaperOptInf> list) throws Exception {
		
		//更新题目表
		tblPaperDefInfDAO.update(inf);
		
		//删除选项表数据
		String sql = "delete from TBL_PAPER_OPT_INF where trim(QUES_ID) = '" + inf.getId().getQuesId().trim() + "'";
		CommonFunction.getCommQueryDAO().excute(sql);
		
		//保存新的选项
		for (TblPaperOptInf opt : list) {
			tblPaperOptInfDAO.save(opt);
		}
		
		return Constants.SUCCESS_CODE;
	}

	
	/* 
	 * 删除题目
	 * 
	 * @see com.huateng.bo.impl.mchtSrv.TblPaperService#delte(com.huateng.po.mchtSrv.TblPaperDefInf)
	 */
	public String delete(TblPaperDefInf inf) throws Exception {
		
		//删除题目
		tblPaperDefInfDAO.delete(inf);
		
		//删除选项表数据
		String sql = "delete from TBL_PAPER_OPT_INF where trim(QUES_ID) = '" + inf.getId().getQuesId().trim() + "'";
		CommonFunction.getCommQueryDAO().excute(sql);
		
		return Constants.SUCCESS_CODE;
	}
	
	public TblPaperDefInf getPaperDef(TblPaperDefInfPK id) throws Exception {
		return tblPaperDefInfDAO.get(id);
	}
	
	
	/* 
	 * 激活修改
	 * 
	 * @see com.huateng.bo.impl.mchtSrv.TblPaperService#active()
	 */
	public String active(String oprId) throws Exception {
		
		String paperId = InformationUtil.getPaperId();
		
		//更新PAPER_ID
		String sql = "update TBL_PAPER_DEF_INF set PAPER_ID = '" + paperId + "' " +
				"where PAPER_ID != 'PAPER_STATUS' ";
		CommonFunction.getCommQueryDAO().excute(sql);
		
		//复制到历史表
		sql = "insert into TBL_PAPER_HIS_INF (select * from TBL_PAPER_DEF_INF where PAPER_ID != 'PAPER_STATUS')";
		CommonFunction.getCommQueryDAO().excute(sql);
		
		//更新选项表
		sql = "update TBL_PAPER_OPT_INF set PAPER_ID = '" + paperId + "' ";
		CommonFunction.getCommQueryDAO().excute(sql);
		
		//复制到历史表
		sql = "insert into TBL_PAPER_OPT_HIS (select * from TBL_PAPER_OPT_INF)";
		CommonFunction.getCommQueryDAO().excute(sql);
		
		//插入问卷记录表
		sql = "INSERT INTO TBL_PAPER_REC_INF (PAPER_ID,RESERVE1,RESERVE2,CRT_USER,CRT_TIME) VALUES " +
				"('" + paperId + "','','','" + oprId + "','" + CommonFunction.getCurrentDateTime() + "')";
		CommonFunction.getCommQueryDAO().excute(sql);
		
		//更新状态  RESERVE1 = '0'-正常，QUESTION=paperId-当前正在使用的问卷
		sql = "update TBL_PAPER_DEF_INF set RESERVE1 = '0',RESERVE2 = '',QUESTION='" + paperId + "' where PAPER_ID = 'PAPER_STATUS'";
		CommonFunction.getCommQueryDAO().excute(sql);
		
		
		return Constants.SUCCESS_CODE;
	}
	
	
	/* 
	 * 锁定并编辑
	 * 
	 * @see com.huateng.bo.impl.mchtSrv.TblPaperService#lock(java.lang.String)
	 */
	public String lock(String oprId) throws Exception {
		TblPaperDefInfPK id = new TblPaperDefInfPK();
		id.setQuesId("PAPER_STATUS");
		id.setPaperId("PAPER_STATUS");
		
		TblPaperDefInf inf = this.getPaperDef(id);
		if (null == inf) {
			return "无法获取当前状态信息，请刷新后重试。";
		}
		if (!StringUtil.isNull(inf.getReserve1()) && "0".equals(inf.getReserve1())) {
			inf.setReserve1("1");
			inf.setReserve2(oprId);
			tblPaperDefInfDAO.update(inf);
			return Constants.SUCCESS_CODE;
		} else {
			return "当前状态无法锁定，请刷新后重试。";
		}
	}
	
	
	public TblPaperDefInfDAO tblPaperDefInfDAO;
	
	public TblPaperOptInfDAO tblPaperOptInfDAO;
	
	public TblPaperSelInfDAO tblPaperSelInfDAO;

	public TblPaperDefInfDAO getTblPaperDefInfDAO() {
		return tblPaperDefInfDAO;
	}

	public void setTblPaperDefInfDAO(TblPaperDefInfDAO tblPaperDefInfDAO) {
		this.tblPaperDefInfDAO = tblPaperDefInfDAO;
	}

	public TblPaperOptInfDAO getTblPaperOptInfDAO() {
		return tblPaperOptInfDAO;
	}

	public void setTblPaperOptInfDAO(TblPaperOptInfDAO tblPaperOptInfDAO) {
		this.tblPaperOptInfDAO = tblPaperOptInfDAO;
	}

	public TblPaperSelInfDAO getTblPaperSelInfDAO() {
		return tblPaperSelInfDAO;
	}

	public void setTblPaperSelInfDAO(TblPaperSelInfDAO tblPaperSelInfDAO) {
		this.tblPaperSelInfDAO = tblPaperSelInfDAO;
	}

	/* 
	 * 提交问卷
	 * 
	 * @see com.huateng.bo.impl.mchtSrv.TblPaperService#submitPaper(java.lang.String, java.lang.String, java.lang.String)
	 */
	public String submitPaper(String result, String mchtId, String paperId, HttpServletRequest request)
			throws Exception {
		
		String selMchtId = InformationUtil.getSelMchtId();
		Operator opr = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		
		
		StringBuffer where = new StringBuffer();
		int point = 0;
		
		//解析选项
		String[] records = result.split("\\|");
		for (String tmp : records) {
			String[] record = tmp.split("_");
			
			TblPaperSelInf inf = new TblPaperSelInf();
			TblPaperSelInfPK id = new TblPaperSelInfPK();
			id.setPaperId(paperId);
			id.setQuesId(record[0]);
			id.setSelOptId(record[1]);
			id.setSelMchtId(selMchtId);
			inf.setId(id);
			
			tblPaperSelInfDAO.save(inf);
			
			point += Integer.parseInt(record[2]);
			
		}
		
		if (point > 100) {
			throw new SecurityException("分数之和不应大于100");
		}
		
		String level = InformationUtil.getMchtLevel(point);
		
		//分数插入评级表
		String sql = "INSERT INTO TBL_PAPER_LEL_INF(SEL_MCHT_ID,PAPER_ID,MCHT_ID,MCHT_POINT,MCHT_LEVEL,CRT_USER,CRT_TIME) VALUES (" +
				"'" + selMchtId + "','" + paperId + "','" + mchtId + "'" +
				"," + point + ",'" + level + "','" + opr.getOprId() + "','" + CommonFunction.getCurrentDateTime() + "')";
		CommonFunction.getCommQueryDAO().excute(sql);
		
		return Constants.SUCCESS_CODE_CUSTOMIZE + "商户评级成功，" +
				"该商户得分[" + String.valueOf(point) + "]，级别为[" + level + "]";
		
	}
}
