package com.huateng.struts.risk.action;

import java.io.File;
import java.util.List;

import com.huateng.bo.risk.T40202BO;
import com.huateng.common.Constants;
import com.huateng.common.ErrorCode;
import com.huateng.common.StringUtil;
import com.huateng.po.TblCtlMchtInf;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;

/**
 * Title: 商户黑名单管理
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-26
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T40202Action extends BaseAction {
	
	T40202BO t40202BO = (T40202BO) ContextUtil.getBean("T40202BO");
	
	/* (non-Javadoc)
	 * @see com.huateng.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		if("add".equals(method)) {
			log("添加商户黑名单信息");
			rspCode = add();
		} else if("update".equals(method)) {
			log("更新商户黑名单信息");
			rspCode = update();
		} else if("delete".equals(method)) {
			log("删除商户黑名单信息");
			rspCode = delete();
		} else if("uploadFile".equals(method)) {
			log("批量上传商户黑名单信息");
			rspCode = uploadFile();
		}
		return rspCode;
	}
	
	/**
	 * 添加商户黑名单信息
	 * @return
	 * 2010-8-26下午11:19:52
	 * @throws Exception 
	 */
	private String add() throws Exception {
		
		if(t40202BO.get(saMerNo) != null) {
			return ErrorCode.T40202_01;
		}
		TblCtlMchtInf tblCtlMchtInf = new TblCtlMchtInf();
		BeanUtils.copyProperties(tblCtlMchtInf, this);
		tblCtlMchtInf.setId(saMerNo);
		
		if(StringUtil.isNull(saMerChName)){
			tblCtlMchtInf.setSaMerChName(" ");
		}else{
			tblCtlMchtInf.setSaMerChName(saMerChName.trim());
		}
		if(StringUtil.isNull(saMerEnName)){
			tblCtlMchtInf.setSaMerEnName(" ");
		}else{
			tblCtlMchtInf.setSaMerEnName(saMerEnName.trim());
		}
		tblCtlMchtInf.setSaAction(saAction);
		tblCtlMchtInf.setSaLimitAmt(saLimitAmt);
		if(StringUtil.isNull(saZoneNo)){
			tblCtlMchtInf.setSaZoneNo(" ");
		}else{
			tblCtlMchtInf.setSaZoneNo(saZoneNo.trim());
		}
		tblCtlMchtInf.setSaAdmiBranNo(Constants.DEFAULT);
		if(StringUtil.isNull(saConnOr)){
			tblCtlMchtInf.setSaConnOr(" ");
		}else{
			tblCtlMchtInf.setSaConnOr(saConnOr.trim());
		}
		if(StringUtil.isNull(saConnTel)){
			tblCtlMchtInf.setSaConnTel(" ");
		}else{
			tblCtlMchtInf.setSaConnTel(saConnTel.trim());
		}
		
		tblCtlMchtInf.setSaInitZoneNo(operator.getOprBrhId());
		tblCtlMchtInf.setSaInitOprId(operator.getOprId());
		tblCtlMchtInf.setSaInitTime(CommonFunction.getCurrentDateTime());
		rspCode = t40202BO.add(tblCtlMchtInf);
		return rspCode;
	}
	
	/**
	 * 更新商户黑名单信息
	 * @return
	 * 2010-8-26下午11:56:26
	 * @throws Exception 
	 */
	private String update() throws Exception {
		jsonBean.parseJSONArrayData(getMchtInfList());
		int len = jsonBean.getArray().size();
		for(int i = 0; i < len; i++) {
			jsonBean.setObject(jsonBean.getJSONDataAt(i));
			TblCtlMchtInf tblCtlMchtInf = new TblCtlMchtInf();
			BeanUtils.setObjectWithPropertiesValue(tblCtlMchtInf,jsonBean,true);
			tblCtlMchtInf.setSaModiZoneNo(operator.getOprBrhId());
			tblCtlMchtInf.setSaModiOprId(operator.getOprId());
			tblCtlMchtInf.setSaModiTime(CommonFunction.getCurrentDateTime());
			
			if(StringUtil.isNull(tblCtlMchtInf.getSaMerEnName())) tblCtlMchtInf.setSaMerEnName(" ");
			
			t40202BO.update(tblCtlMchtInf);
		}
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 删除商户黑名单信息
	 * @return
	 * 2010-8-26下午11:56:26
	 * @throws Exception 
	 */
	private String delete() throws Exception {
		t40202BO.delete(saMerNo);
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 批量新增商户黑名单信息
	 * @return
	 * 2010-8-26下午11:59:38
	 * @throws Exception 
	 */
	private String uploadFile() throws Exception {
		rspCode = t40202BO.importFile(xlsFile, xlsFileFileName, operator);
		return rspCode;
	}
	// 受控商户号
	private String saMerNo;
	// 商户中文名称
	private String saMerChName;
	// 商户英文名称
	private String saMerEnName;
	// 商户受控金额
	private String saLimitAmt;
	// 商户受控动作
	private String saAction;
	
	// 联系人
	private String saConnOr;
	// 联系电话
	private String saConnTel;
	// 分行号
	private String saZoneNo;
	// 主管分行号
	private String saAdmiBranNo;
	
	// 文件集合
	private List<File> xlsFile;
	// 文件名称集合
	private List<String> xlsFileFileName;
	// 黑名单商户修改集
	private String mchtInfList;
	
	/**
	 * @return the saMerNo
	 */
	public String getSaMerNo() {
		return saMerNo;
	}

	/**
	 * @param saMerNo the saMerNo to set
	 */
	public void setSaMerNo(String saMerNo) {
		this.saMerNo = saMerNo;
	}

	/**
	 * @return the saMerChName
	 */
	public String getSaMerChName() {
		return saMerChName;
	}

	/**
	 * @param saMerChName the saMerChName to set
	 */
	public void setSaMerChName(String saMerChName) {
		this.saMerChName = saMerChName;
	}

	/**
	 * @return the saMerEnName
	 */
	public String getSaMerEnName() {
		return saMerEnName;
	}

	/**
	 * @param saMerEnName the saMerEnName to set
	 */
	public void setSaMerEnName(String saMerEnName) {
		this.saMerEnName = saMerEnName;
	}

	/**
	 * @return the saLimitAmt
	 */
	public String getSaLimitAmt() {
		return saLimitAmt;
	}

	/**
	 * @param saLimitAmt the saLimitAmt to set
	 */
	public void setSaLimitAmt(String saLimitAmt) {
		this.saLimitAmt = saLimitAmt;
	}

	/**
	 * @return the saAction
	 */
	public String getSaAction() {
		return saAction;
	}

	/**
	 * @param saAction the saAction to set
	 */
	public void setSaAction(String saAction) {
		this.saAction = saAction;
	}

	/**
	 * @return the xlsFile
	 */
	public List<File> getXlsFile() {
		return xlsFile;
	}

	/**
	 * @param xlsFile the xlsFile to set
	 */
	public void setXlsFile(List<File> xlsFile) {
		this.xlsFile = xlsFile;
	}

	/**
	 * @return the xlsFileFileName
	 */
	public List<String> getXlsFileFileName() {
		return xlsFileFileName;
	}

	/**
	 * @param xlsFileFileName the xlsFileFileName to set
	 */
	public void setXlsFileFileName(List<String> xlsFileFileName) {
		this.xlsFileFileName = xlsFileFileName;
	}

	/**
	 * @return the mchtInfList
	 */
	public String getMchtInfList() {
		return mchtInfList;
	}

	/**
	 * @param mchtInfList the mchtInfList to set
	 */
	public void setMchtInfList(String mchtInfList) {
		this.mchtInfList = mchtInfList;
	}

	/**
	 * @return the saConnOr
	 */
	public String getSaConnOr() {
		return saConnOr;
	}

	/**
	 * @param saConnOr the saConnOr to set
	 */
	public void setSaConnOr(String saConnOr) {
		this.saConnOr = saConnOr;
	}

	/**
	 * @return the saConnTel
	 */
	public String getSaConnTel() {
		return saConnTel;
	}

	/**
	 * @param saConnTel the saConnTel to set
	 */
	public void setSaConnTel(String saConnTel) {
		this.saConnTel = saConnTel;
	}

	/**
	 * @return the saZoneNo
	 */
	public String getSaZoneNo() {
		return saZoneNo;
	}

	/**
	 * @param saZoneNo the saZoneNo to set
	 */
	public void setSaZoneNo(String saZoneNo) {
		this.saZoneNo = saZoneNo;
	}

	/**
	 * @return the saAdmiBranNo
	 */
	public String getSaAdmiBranNo() {
		return saAdmiBranNo;
	}

	/**
	 * @param saAdmiBranNo the saAdmiBranNo to set
	 */
	public void setSaAdmiBranNo(String saAdmiBranNo) {
		this.saAdmiBranNo = saAdmiBranNo;
	}
	
}
