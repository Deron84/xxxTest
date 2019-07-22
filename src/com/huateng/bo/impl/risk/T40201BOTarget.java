/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-8-24       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2010 Huateng Software, Inc. All rights reserved.
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
package com.huateng.bo.impl.risk;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.huateng.bo.risk.T40201BO;
import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.dao.iface.risk.TblCtlCardInfDAO;
import com.huateng.po.TblCtlCardInf;
import com.huateng.system.util.CommonFunction;

/**
 * Title:卡黑名单管理
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-24
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class T40201BOTarget implements T40201BO {
	
	private TblCtlCardInfDAO tblCtlCardInfDAO;
	
	/* (non-Javadoc)
	 * @see com.huateng.bo.T40201BO#add(com.huateng.po.TblCtlCardInf)
	 */
	public String add(TblCtlCardInf tblCtlCardInf) throws Exception {
		if(tblCtlCardInfDAO.get(tblCtlCardInf.getId()) != null) {
			return "该卡编号已经存在";
		}
		//tblCtlCardInf.setSaLimitAmt(CommonFunction.transYuanToFen(tblCtlCardInf.getSaLimitAmt().trim()));
		tblCtlCardInfDAO.save(tblCtlCardInf);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.T40201BO#update(com.huateng.po.TblCtlCardInf)
	 */
	public String update(TblCtlCardInf tblCtlCardInf) throws Exception {
	//	tblCtlCardInf.setSaLimitAmt(CommonFunction.transYuanToFen(tblCtlCardInf.getSaLimitAmt().trim()));
		
		tblCtlCardInfDAO.update(tblCtlCardInf);
		return Constants.SUCCESS_CODE;
	}
	
	
	/* (non-Javadoc)
	 * @see com.huateng.bo.T40201BO#importFile(java.util.List)
	 */
	public String importFile(List<File> fileList,List<String> fileNameList,Operator operator) throws Exception {
		HSSFWorkbook workbook = null;
		HSSFSheet sheet = null;
		HSSFRow row = null;
		// 返回信息
		String returnMsg = "";
		// 文件名称索引
		int fileNameIndex = 0;
		// 文件名称
		String fileName = null;
		
		// 交易卡号
		String saCardNo = null;
		// 受控金额
		String saLimitAmt = null;
		// 受控动作
		String saAction = null;
		// 机构
		String saBrhId = operator.getOprBrhId();
		// 操作员
		String saOprId = operator.getOprId();
		// 时间
		String saInitTime = CommonFunction.getCurrentDateTime();
		
		TblCtlCardInf tblCtlCardInf = null;
		
		FileInputStream fileInputStream = null;
		
		for(File file : fileList) {
			
			fileInputStream = new FileInputStream(file);
			
			workbook = new HSSFWorkbook(fileInputStream);
			
			sheet = workbook.getSheetAt(0);
			
			fileName = fileNameList.get(fileNameIndex);
			
			for(int rowIndex = sheet.getFirstRowNum(); rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				
				row = sheet.getRow(rowIndex);
				
				for(int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++)
					if(row.getCell(i).getCellType() != HSSFCell.CELL_TYPE_STRING)
						returnMsg += "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，" + 
									"第" + (i + 1) + "列单元格格式不正确<br>";

				if(!"".equals(returnMsg))
					return returnMsg;

				
				saCardNo = row.getCell(0).getStringCellValue();
				// 校验交易卡号
				if(saCardNo.getBytes().length > 19)
					return "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，交易卡号长度超出限制<br>";

				
				saLimitAmt = row.getCell(1).getStringCellValue();
//				saLimitAmt = CommonFunction.transYuanToFen(saLimitAmt);
				
				// 校验受控金额
				if(!CommonFunction.isAllDigit(saLimitAmt))
					return "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，受控金额包含非数字内容<br>";
				
				if(saLimitAmt.getBytes().length > 12) 
					return "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，受控金额长度超出限制<br>";

				
				saAction = row.getCell(2).getStringCellValue();
				// 校验受控动作
				if(!("1".equals(saAction) || "2".equals(saAction) || "3".equals(saAction) || "4".equals(saAction)))
					return "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，受控动作不在可选范围内<br>";
				
				
				// 验证通过，对卡黑名单信息进行处理
				tblCtlCardInf = new TblCtlCardInf();
				tblCtlCardInf.setId(saCardNo);
				tblCtlCardInf.setSaLimitAmt(saLimitAmt);
				tblCtlCardInf.setSaAction(saAction);
				tblCtlCardInf.setSaInitZoneNo(saBrhId);
				tblCtlCardInf.setSaInitOprId(saOprId);
				tblCtlCardInf.setSaInitTime(saInitTime);
				tblCtlCardInfDAO.saveOrUpdate(tblCtlCardInf);
			}
			fileInputStream.close();
			fileNameIndex++;
		}
		return Constants.SUCCESS_CODE;
	}
	/**
	 * @return the tblCtlCardInfDAO
	 */
	public TblCtlCardInfDAO getTblCtlCardInfDAO() {
		return tblCtlCardInfDAO;
	}

	/**
	 * @param tblCtlCardInfDAO the tblCtlCardInfDAO to set
	 */
	public void setTblCtlCardInfDAO(TblCtlCardInfDAO tblCtlCardInfDAO) {
		this.tblCtlCardInfDAO = tblCtlCardInfDAO;
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.T40201BO#get(java.lang.String)
	 */
	public TblCtlCardInf get(String key) {
		return tblCtlCardInfDAO.get(key);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.huateng.bo.T40201BO#delete(com.huateng.po.TblCtlCardInf)
	 */
	public void delete(String key) {
		tblCtlCardInfDAO.delete(key);
	}
}
