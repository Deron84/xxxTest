/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2011-8-12       first release
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
package com.huateng.struts.pos.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.struts.pos.TblTermTmkLogConstants;
import com.huateng.struts.system.action.ReportBaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.SysParamUtil;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-8-12
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class T3020303Action extends ReportBaseAction {

	private static final long serialVersionUID = 1777500196702175186L;

	private String[] mchtArr;
	private String[] termIdArr;
	private String[] termBranchArr;
	private ICommQueryDAO commQueryDAO;

	public String[] getMchtArr() {
		return mchtArr;
	}

	public void setMchtArr(String[] mchtArr) {
		this.mchtArr = mchtArr;
	}

	public String[] getTermIdArr() {
		return termIdArr;
	}

	public void setTermIdArr(String[] termIdArr) {
		this.termIdArr = termIdArr;
	}

	public String[] getTermBranchArr() {
		return termBranchArr;
	}

	public void setTermBranchArr(String[] termBranchArr) {
		this.termBranchArr = termBranchArr;
	}

	/**
	 * @param commQueryDAO the commQueryDAO to set
	 */
	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}

	@Override
	protected void reportAction() throws Exception {
		
		String fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "JSBANKKMS" +
				operator.getOprBrhId().trim().substring(0,2)+CommonFunction.getCurrentDate()+".txt";
		
		File file = new File(fileName);
		PrintWriter pw = new PrintWriter(new FileOutputStream(file)); 
		
		if(mchtArr.length == 0 || termIdArr.length == 0 || termBranchArr.length == 0){
			System.out.println("未选择数据！");
			return ;
		}
		
		for(int i = 0;i < mchtArr.length; i++){
			StringBuffer str = new StringBuffer();
			str.append(mchtArr[i].trim()).append(",")
			   .append(termIdArr[i].trim()).append(",")
			   .append(termBranchArr[i].substring(0, 4).toString().trim());

			if(i != mchtArr.length - 1){
				str.append("\r\n");
			}
			pw.write(str.toString().toCharArray()); 
			
			//只能打印一次，现当作打印状态。
			StringBuffer sql2 = new StringBuffer("UPDATE TBL_TERM_TMK_LOG SET PRT_OPR='").append(operator.getOprId())
										 .append("',REC_UPD_OPR = '").append(operator.getOprId())
										 .append("',REC_UPD_TS = '").append(CommonFunction.getCurrentDateTime())
										 .append("',PRT_DATE = '").append(CommonFunction.getCurrentDate())
										 .append("',PRT_COUNT = ").append("'1'")
										 .append(" WHERE MCHN_NO='").append(mchtArr[i].trim())
										 .append("' AND TERM_ID_ID='").append(termIdArr[i].trim()).append("'");
			
			commQueryDAO.excute(sql2.toString());
		}
		
		pw.flush();
		pw.close();
		writeUsefullMsg(fileName);
		return;
		
	}

	@Override
	protected String genSql() {
		if(mchtArr.length == 0 || termIdArr.length == 0){
			return "请先选择需要打印的数据！";
		}
		return null;
	}

	
}
