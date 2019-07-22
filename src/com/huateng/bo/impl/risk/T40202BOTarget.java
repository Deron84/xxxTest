/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-8-26       first release
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

import com.huateng.bo.risk.T40202BO;
import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.dao.iface.risk.TblCtlMchtInfDAO;
import com.huateng.po.TblCtlMchtInf;
import com.huateng.system.util.CommonFunction;

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
public class T40202BOTarget implements T40202BO {

	private TblCtlMchtInfDAO tblCtlMchtInfDAO;
	/* (non-Javadoc)
	 * @see com.huateng.bo.T40202BO#add(com.huateng.po.TblCtlMchtInf)
	 */
	public String add(TblCtlMchtInf tblCtlMchtInf) throws Exception {
	//	tblCtlMchtInf.setSaLimitAmt(CommonFunction.transYuanToFen(tblCtlMchtInf.getSaLimitAmt()));
		tblCtlMchtInfDAO.save(tblCtlMchtInf);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.T40202BO#get(java.lang.String)
	 */
	public TblCtlMchtInf get(String key) {
		return tblCtlMchtInfDAO.get(key);
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.T40202BO#update(com.huateng.po.TblCtlMchtInf)
	 */
	public String update(TblCtlMchtInf tblCtlMchtInf) throws Exception {
	//	tblCtlMchtInf.setSaLimitAmt(CommonFunction.transYuanToFen(tblCtlMchtInf.getSaLimitAmt()));
		tblCtlMchtInfDAO.update(tblCtlMchtInf);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.huateng.bo.T40202BO#importFile(java.util.List, java.util.List, com.huateng.common.Operator)
	 */
	@SuppressWarnings("unchecked")
	public String importFile(List<File> fileList, List<String> fileNameList,
			Operator operator) throws Exception {
		HSSFWorkbook workbook = null;
		HSSFSheet sheet = null;
		HSSFRow row = null;
		// 返回信息
		String returnMsg = "";
		// 文件名称索引
		int fileNameIndex = 0;
		// 文件名称
		String fileName = null;
		String sql = null;
		// 存放查询结果集
		List<Object[]> dataList = null;
		
		// 受控商户号
		String saMerNo = null;
		// 商户中文名称
		String saMerChName = null;
		// 商户英文名称
		String saMerEnName = null;
		// 分行号
		String saZoneNo = null;
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
		
		TblCtlMchtInf tblCtlMchtInf = null;
		
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

				
				saMerNo = row.getCell(0).getStringCellValue();
				// 校验商户编号
				if(saMerNo.getBytes().length > 15)
					return "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，受控商户编号长度超出限制<br>";
				sql = "select  mcht_nm,eng_name,bank_no from TBL_MCHT_BASE_INF where MCHT_NO = '" + saMerNo + "'";
				
				dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
				if(dataList.size() == 0)
					return "文件[ " + fileName + " ]，第" + (row.getRowNum() + 1) + "行，商户编号不存在<br>";
				// 商户中文名称
				saMerChName = dataList.get(0)[0].toString();
				// 商户英文名称
				saMerEnName = dataList.get(0)[1].toString();
				saZoneNo = dataList.get(0)[2].toString();
				
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
				
				
				// 验证通过，对商户黑名单信息进行处理
				tblCtlMchtInf = new TblCtlMchtInf();
				tblCtlMchtInf.setId(saMerNo);
				tblCtlMchtInf.setSaMerChName(saMerChName);
				tblCtlMchtInf.setSaMerEnName(saMerEnName);
				tblCtlMchtInf.setSaZoneNo(saZoneNo);
				tblCtlMchtInf.setSaLimitAmt(saLimitAmt);
				tblCtlMchtInf.setSaAction(saAction);
				tblCtlMchtInf.setSaInitZoneNo(saBrhId);
				tblCtlMchtInf.setSaInitOprId(saOprId);
				tblCtlMchtInf.setSaInitTime(saInitTime);
				tblCtlMchtInfDAO.saveOrUpdate(tblCtlMchtInf);
			}
			fileInputStream.close();
			fileNameIndex++;
		}
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * @return the tblCtlMchtInfDAO
	 */
	public TblCtlMchtInfDAO getTblCtlMchtInfDAO() {
		return tblCtlMchtInfDAO;
	}
	/**
	 * @param tblCtlMchtInfDAO the tblCtlMchtInfDAO to set
	 */
	public void setTblCtlMchtInfDAO(TblCtlMchtInfDAO tblCtlMchtInfDAO) {
		this.tblCtlMchtInfDAO = tblCtlMchtInfDAO;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.huateng.bo.T40202BO#delete(java.lang.String)
	 */
	public void delete(String key) {
		tblCtlMchtInfDAO.delete(key);
	}
}
