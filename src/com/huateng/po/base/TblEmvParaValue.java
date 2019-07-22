/* @(#)
 *
 * Project:JSBConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2011-11-8       first release
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
package com.huateng.po.base;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-11-8
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class TblEmvParaValue {
	
	/**
	 * 
	 */
	private static String val;
	
	public TblEmvParaValue(String value, String name) {
		
		val = value;
		
		TAG1 = getNextValue();
		TAG2 = getNextValue();
		TAG3 = getNextValue();
		TAG4 = getNextValue();
		TAG5 = getNextValue();
		TAG6 = getNextValue();
		TAG7 = getNextValue();
		TAG8 = getNextValue();
		TAG9 = getNextValue();
		TAG10 = getNextValue();
		TAG11 = getNextValue();
		TAG12 = getNextValue();
		TAG13 = getNextValue();
		TAG14 = getNextValue();
		TAG15 = getNextValue();
		TAG16 = getNextValue();
		name = paraOrg;
	}
	
	private String getNextValue(){
		int i;
		String tmp = val.substring(6, i = 6 + Integer.parseInt(val.substring(4,6)) * 2);
		val = val.substring(i);
		return tmp;
	}
	
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
	
	private String paraOrg;
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
