/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   Gavin      2011-11-4       first release
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
package com.huateng.struts.base.action;

import com.huateng.bo.base.T10206BO;
import com.huateng.common.Constants;
import com.huateng.po.base.TblEmvPara;
import com.huateng.po.base.TblEmvParaPK;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.GenerateNextId;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-11-4
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class T10208Action extends BaseAction{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private T10206BO t10206BO = (T10206BO) ContextUtil.getBean("T10206BO");

	
	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		if("add".equals(method)) {
			rspCode = add();
		} else if("delete".equals(method)) {
			rspCode = delete();
		} else if("update".equals(method)) {
			rspCode = update();
		} 
		
		return rspCode;
	}
	
	
	private String add(){
		
		try {
			
			StringBuffer sb = new StringBuffer();
			
			sb.append("9F06").append(CommonFunction.fillString(Integer.toString(TAG1.length()/2), '0', 2, false)).append(TAG1);
			sb.append("DF01").append("01").append(TAG2);
			sb.append("9F09").append("02").append(TAG3);
			sb.append("DF11").append("05").append(TAG4);
			sb.append("DF12").append("05").append(TAG5);
			sb.append("DF13").append("05").append(TAG6);
			sb.append("9F1B").append("04").append(TAG7);
			sb.append("DF15").append("04").append(TAG8);
			sb.append("DF16").append("01").append(TAG9);
			sb.append("DF17").append("01").append(TAG10);
			sb.append("DF14").append(CommonFunction.fillString(Integer.toString(TAG11.length()/2), '0', 2, false)).append(TAG11);
			sb.append("DF18").append("01").append(TAG12);
			sb.append("9F7B").append("06").append(TAG13);
			sb.append("DF19").append("06").append(TAG14);
			sb.append("DF20").append("06").append(TAG15);
			sb.append("DF21").append("06").append(TAG16);
			
			String usageKey = "0"; //IC卡其他参数
			
			String paraIdx = GenerateNextId.getParaIdx(usageKey);
			
			TblEmvParaPK pk = new TblEmvParaPK(usageKey,paraIdx);
			
			TblEmvPara tblEmvPara = new TblEmvPara(pk);
			tblEmvPara.setParaId(TAG1);
			tblEmvPara.setGenFlag("0");
			tblEmvPara.setParaLen(String.valueOf(sb.toString().length()));
			tblEmvPara.setParaSta("0");
			tblEmvPara.setParaOrg(paraOrg);
			tblEmvPara.setParaVal(sb.toString());
			tblEmvPara.setRecOprId("0");
			tblEmvPara.setRecUpdOpr(operator.getOprId());
			tblEmvPara.setRecCrtTs(CommonFunction.getCurrentDateTime());
			tblEmvPara.setRecUpdTs(CommonFunction.getCurrentDateTime());		
			
			t10206BO.createTblEmvPara(tblEmvPara);
			
			return Constants.SUCCESS_CODE;
			
		} catch (Exception e) {
			e.printStackTrace();
			return "操作失败";
		}
		
	}
	
	private String delete(){
		String usageKey = "0"; //IC卡其他参数
		TblEmvParaPK pk = new TblEmvParaPK(CommonFunction.fillString(usageKey, ' ', 8, true),CommonFunction.fillString(index, ' ', 4, true));
		t10206BO.delete(pk);
		return Constants.SUCCESS_CODE;
	}
	
	private String update(){
		
		try {
			
			StringBuffer sb = new StringBuffer();
			
			sb.append("9F06").append(CommonFunction.fillString(Integer.toString(TAG1.length()/2), '0', 2, false)).append(TAG1);
			sb.append("DF01").append("01").append(TAG2);
			sb.append("9F09").append("02").append(TAG3);
			sb.append("DF11").append("05").append(TAG4);
			sb.append("DF12").append("05").append(TAG5);
			sb.append("DF13").append("05").append(TAG6);
			sb.append("9F1B").append("04").append(TAG7);
			sb.append("DF15").append("04").append(TAG8);
			sb.append("DF16").append("01").append(TAG9);
			sb.append("DF17").append("01").append(TAG10);
			sb.append("DF14").append(CommonFunction.fillString(Integer.toString(TAG11.length()/2), '0', 2, false)).append(TAG11);
			sb.append("DF18").append("01").append(TAG12);
			sb.append("9F7B").append("06").append(TAG13);
			sb.append("DF19").append("06").append(TAG14);
			sb.append("DF20").append("06").append(TAG15);
			sb.append("DF21").append("06").append(TAG16);
			
			String usageKey = "0"; //IC卡其他参数
			
			TblEmvParaPK pk = new TblEmvParaPK(CommonFunction.fillString(usageKey, ' ', 8, true),CommonFunction.fillString(index, ' ', 4, true));

			TblEmvPara tblEmvPara = t10206BO.get(pk);
			if (null == tblEmvPara) {
				return "没有找到指定的参数信息，请刷新后重试！";
			}
			tblEmvPara.setParaId(TAG1);
			tblEmvPara.setGenFlag("0");
			tblEmvPara.setParaLen(String.valueOf(sb.toString().length()));
			tblEmvPara.setParaSta("0");
			tblEmvPara.setParaOrg(paraOrg);
			tblEmvPara.setParaVal(sb.toString());
			tblEmvPara.setRecOprId("0");
			tblEmvPara.setRecUpdOpr(operator.getOprId());
			tblEmvPara.setRecCrtTs(CommonFunction.getCurrentDateTime());
			tblEmvPara.setRecUpdTs(CommonFunction.getCurrentDateTime());		
			
			t10206BO.update(tblEmvPara);
			
			return Constants.SUCCESS_CODE;
			
		} catch (Exception e) {
			e.printStackTrace();
			return "ERROR:" + e.getMessage();
		}
		
	}
	
	private String index;
	
	private String paraOrg;
	
	private String TAG1;
	private String TAG2;
	private String TAG3;
	private String TAG4;
	private String TAG5;
	private String TAG6;
	private String TAG7;
	private String TAG8;
	private String TAG9;
	private String TAG10;
	private String TAG11;
	private String TAG12;
	private String TAG13;
	private String TAG14;
	private String TAG15;
	private String TAG16;
	/**
	 * @return the tAG1
	 */
	public String getTAG1() {
		return TAG1;
	}


	/**
	 * @param tAG1 the tAG1 to set
	 */
	public void setTAG1(String tAG1) {
		TAG1 = tAG1;
	}


	/**
	 * @return the tAG2
	 */
	public String getTAG2() {
		return TAG2;
	}


	/**
	 * @param tAG2 the tAG2 to set
	 */
	public void setTAG2(String tAG2) {
		TAG2 = tAG2;
	}


	/**
	 * @return the tAG3
	 */
	public String getTAG3() {
		return TAG3;
	}


	/**
	 * @param tAG3 the tAG3 to set
	 */
	public void setTAG3(String tAG3) {
		TAG3 = tAG3;
	}


	/**
	 * @return the tAG4
	 */
	public String getTAG4() {
		return TAG4;
	}


	/**
	 * @param tAG4 the tAG4 to set
	 */
	public void setTAG4(String tAG4) {
		TAG4 = tAG4;
	}


	/**
	 * @return the tAG5
	 */
	public String getTAG5() {
		return TAG5;
	}


	/**
	 * @param tAG5 the tAG5 to set
	 */
	public void setTAG5(String tAG5) {
		TAG5 = tAG5;
	}


	/**
	 * @return the tAG6
	 */
	public String getTAG6() {
		return TAG6;
	}


	/**
	 * @param tAG6 the tAG6 to set
	 */
	public void setTAG6(String tAG6) {
		TAG6 = tAG6;
	}


	/**
	 * @return the tAG7
	 */
	public String getTAG7() {
		return TAG7;
	}


	/**
	 * @param tAG7 the tAG7 to set
	 */
	public void setTAG7(String tAG7) {
		TAG7 = tAG7;
	}


	/**
	 * @return the tAG8
	 */
	public String getTAG8() {
		return TAG8;
	}


	/**
	 * @param tAG8 the tAG8 to set
	 */
	public void setTAG8(String tAG8) {
		TAG8 = tAG8;
	}


	/**
	 * @return the tAG9
	 */
	public String getTAG9() {
		return TAG9;
	}


	/**
	 * @param tAG9 the tAG9 to set
	 */
	public void setTAG9(String tAG9) {
		TAG9 = tAG9;
	}


	/**
	 * @return the tAG10
	 */
	public String getTAG10() {
		return TAG10;
	}


	/**
	 * @param tAG10 the tAG10 to set
	 */
	public void setTAG10(String tAG10) {
		TAG10 = tAG10;
	}


	/**
	 * @return the tAG11
	 */
	public String getTAG11() {
		return TAG11;
	}


	/**
	 * @param tAG11 the tAG11 to set
	 */
	public void setTAG11(String tAG11) {
		TAG11 = tAG11;
	}


	/**
	 * @return the tAG12
	 */
	public String getTAG12() {
		return TAG12;
	}


	/**
	 * @param tAG12 the tAG12 to set
	 */
	public void setTAG12(String tAG12) {
		TAG12 = tAG12;
	}


	/**
	 * @return the tAG13
	 */
	public String getTAG13() {
		return TAG13;
	}


	/**
	 * @param tAG13 the tAG13 to set
	 */
	public void setTAG13(String tAG13) {
		TAG13 = tAG13;
	}


	/**
	 * @return the tAG14
	 */
	public String getTAG14() {
		return TAG14;
	}


	/**
	 * @param tAG14 the tAG14 to set
	 */
	public void setTAG14(String tAG14) {
		TAG14 = tAG14;
	}


	/**
	 * @return the tAG15
	 */
	public String getTAG15() {
		return TAG15;
	}


	/**
	 * @param tAG15 the tAG15 to set
	 */
	public void setTAG15(String tAG15) {
		TAG15 = tAG15;
	}


	/**
	 * @return the tAG16
	 */
	public String getTAG16() {
		return TAG16;
	}


	/**
	 * @param tAG16 the tAG16 to set
	 */
	public void setTAG16(String tAG16) {
		TAG16 = tAG16;
	}


	/**
	 * @return the index
	 */
	public String getIndex() {
		return index;
	}


	/**
	 * @param index the index to set
	 */
	public void setIndex(String index) {
		this.index = index;
	}


	/**
	 * @return the paraOrg
	 */
	public String getParaOrg() {
		return paraOrg;
	}


	/**
	 * @param paraOrg the paraOrg to set
	 */
	public void setParaOrg(String paraOrg) {
		this.paraOrg = paraOrg;
	}
	
	

}
