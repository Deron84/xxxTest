/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-8-27       first release
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
package com.huateng.struts.query.action;

import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ExcelUtil;
import com.huateng.system.util.InformationUtil;

/**
 * Title: 商户终端信息统计表
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-27
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T50202Action extends BaseSupport {
	
	public String download() {
		
		String brhBelow = InformationUtil.getBrhGroupString(brhId);
		System.out.println(brhBelow);
		
		String thisMonStart = date + "01";
		String thisMonEnd = date + "31";
		String lastYear = String.valueOf(Integer.parseInt(date.substring(0, 4)) - 1) + "1231";
		String lastMon = thisMonStart;//直接用本月的start代替上月末，比较时不用等于。
		String thisYearStart = date.substring(0, 4) + "0101";
		String thisYearEnd = date.substring(0, 4) + "1231";
		
		try {
			//组装报表主数据
			String[][] data = new String[40][9];
			//初始化data
			for (int i = 0; i < data.length; i++) {
				for (int j = 0; j < data[i].length; j++) {
					if (j < 4) {
						data[i][j] = "";
					} else {
						data[i][j] = "0";
					}
				}
			}
			Iterator it = null;
			//商户直联间联信息
			//初始化data
			data[0][0] = "1";
			data[0][1] = "联网商户";
			data[0][3] = "户";
			data[1][1] = "其中";
			data[1][2] = "间联";
			data[1][3] = "户";
			data[2][2] = "直联";
			data[2][3] = "户";
			String sql = "select CONN_TYPE, "
					+ "sum(case when apply_date <= '" + lastYear + "' then 1 else 0 end) as c1,"
					+ "sum(case when apply_date < '" + lastMon + "' then 1 else 0 end) as c2,"
					+ "sum(case when (apply_date >= '" + thisMonStart + "' and apply_date <= '" + thisMonEnd + "') then 1 else 0 end) as c3,"
					+ "sum(case when (apply_date >= '" + thisYearStart + "' and apply_date <= '" + thisYearEnd + "') then 1 else 0 end) as c4 "
					+ "from TBL_MCHT_BASE_INF where SIGN_INST_ID in " + brhBelow + " group by CONN_TYPE";
			List list = commQueryDAO.findBySQLQuery(sql);
			if (null != list && !list.isEmpty()) {
				it = list.iterator();
				while (it.hasNext()) {
					Object[] obj = (Object[]) it.next();
					int index = 0;
					if ("J".equals(obj[0])) {
						index = 1;
					} else {
						index = 2;
					}
					data[index][4] = obj[1].toString();
					data[index][5] = obj[2].toString();
					data[index][6] = obj[3].toString();
					data[index][7] = obj[4].toString();
					data[index][8] = String.valueOf(Integer.valueOf(obj[1]
							.toString()) + Integer.valueOf(obj[4].toString()));
				}
				data[0][4] = String.valueOf(Integer.valueOf(data[1][4])
						+ Integer.valueOf(data[2][4]));
				data[0][5] = String.valueOf(Integer.valueOf(data[1][5])
						+ Integer.valueOf(data[2][5]));
				data[0][6] = String.valueOf(Integer.valueOf(data[1][6])
						+ Integer.valueOf(data[2][6]));
				data[0][7] = String.valueOf(Integer.valueOf(data[1][7])
						+ Integer.valueOf(data[2][7]));
				data[0][8] = String.valueOf(Integer.valueOf(data[1][8])
						+ Integer.valueOf(data[2][8]));
			}
			//商户直联间联信息
			//初始化data
			data[3][0] = "2";
			data[3][1] = "联网POS终端";
			data[3][3] = "台";
			data[4][1] = "其中";
			data[4][2] = "间联";
			data[4][3] = "台";
			data[5][2] = "直联";
			data[5][3] = "台";
			sql = "select CONN_TYPE ,"
					+ "sum(case when t.REC_CRT_TS <= '" + lastYear + "' then 1 else 0 end) as c1,"
					+ "sum(case when t.REC_CRT_TS < '" + lastMon + "' then 1 else 0 end) as c2,"
					+ "sum(case when (t.REC_CRT_TS >= '" + thisMonStart + "' and t.REC_CRT_TS <= '" + thisMonEnd + "') then 1 else 0 end) as c3,"
					+ "sum(case when (t.REC_CRT_TS >= '" + thisYearStart + "' and t.REC_CRT_TS <= '" + thisYearEnd + "') then 1 else 0 end) as c4 "
					+ "from TBL_TERM_INF t,TBL_MCHT_BASE_INF m where t.MCHT_CD = m.MCHT_NO "
					+ "and m.ACQ_INST_ID in " + brhBelow + " group by CONN_TYPE";
			list = commQueryDAO.findBySQLQuery(sql);
			if (null != list && !list.isEmpty()) {
				it = list.iterator();
				while (it.hasNext()) {
					Object[] obj = (Object[]) it.next();
					int index = 0;
					if ("J".equals(obj[0])) {
						index = 4;
					} else {
						index = 5;
					}
					data[index][4] = obj[1].toString();
					data[index][5] = obj[2].toString();
					data[index][6] = obj[3].toString();
					data[index][7] = obj[4].toString();
					data[index][8] = String.valueOf(Integer.valueOf(obj[1]
							.toString()) + Integer.valueOf(obj[4].toString()));
				}
				data[3][4] = String.valueOf(Integer.valueOf(data[4][4])
						+ Integer.valueOf(data[5][4]));
				data[3][5] = String.valueOf(Integer.valueOf(data[4][5])
						+ Integer.valueOf(data[5][5]));
				data[3][6] = String.valueOf(Integer.valueOf(data[4][6])
						+ Integer.valueOf(data[5][6]));
				data[3][7] = String.valueOf(Integer.valueOf(data[4][7])
						+ Integer.valueOf(data[5][7]));
				data[3][8] = String.valueOf(Integer.valueOf(data[4][8])
						+ Integer.valueOf(data[5][8]));
			}
			//商户直联间联信息
			//初始化data
			data[6][0] = "3";
			data[6][1] = "联网商户";
			sql = "SELECT DESCR,nvl(mchnt1.c,0),nvl(mchnt2.c,0),nvl(mchnt3.c,0),nvl(mchnt4.c,0) FROM TBL_INF_MCHNT_TP_GRP grp "
					+ "left outer join (select MCHT_GRP,COUNT(*) as c from TBL_MCHT_BASE_INF where ACQ_INST_ID in " + brhBelow + " and apply_date <= '" + lastYear + "' group by MCHT_GRP) mchnt1 on (grp.MCHNT_TP_GRP = mchnt1.MCHT_GRP) "
					+ "left outer join (select MCHT_GRP,COUNT(*) as c from TBL_MCHT_BASE_INF where ACQ_INST_ID in " + brhBelow + " and apply_date < '" + lastMon + "' group by MCHT_GRP) mchnt2 on (grp.MCHNT_TP_GRP = mchnt2.MCHT_GRP)"
					+ "left outer join (select MCHT_GRP,COUNT(*) as c from TBL_MCHT_BASE_INF where ACQ_INST_ID in " + brhBelow + " and apply_date >= '" + thisMonStart + "' and apply_date <= '" + thisMonEnd + "' group by MCHT_GRP) mchnt3 on (grp.MCHNT_TP_GRP = mchnt3.MCHT_GRP)"
					+ "left outer join (select MCHT_GRP,COUNT(*) as c from TBL_MCHT_BASE_INF where ACQ_INST_ID in " + brhBelow + " and apply_date >= '" + thisYearStart + "' and apply_date <= '" + thisYearEnd + "' group by MCHT_GRP) mchnt4 on (grp.MCHNT_TP_GRP = mchnt4.MCHT_GRP) "
					+ "order by MCHNT_TP_GRP ";
			list = commQueryDAO.findBySQLQuery(sql);
			int index = 6;
			int len = 0;
			if (null != list && !list.isEmpty()) {
				it = list.iterator();
				while (it.hasNext()) {
					Object[] obj = (Object[]) it.next();
					if (!StringUtil.isNull(obj[0]) && (obj[0].toString().indexOf("(") != -1 || obj[0].toString().indexOf("（") != -1)) {
						int a = obj[0].toString().indexOf("(");
						int b = obj[0].toString().indexOf("（");
						if (a > 0 && b > 0) {
							data[index][2] = obj[0].toString().substring(0, a < b ? a : b);
						} else {
							data[index][2] = obj[0].toString().substring(0, a + b + 1);
						}
					} else {
						data[index][2] = obj[0].toString();
					}
					data[index][3] = "户";
					data[index][4] = obj[1].toString();
					data[index][5] = obj[2].toString();
					data[index][6] = obj[3].toString();
					data[index][7] = obj[4].toString();
					data[index][8] = String.valueOf(Integer.valueOf(obj[1]
							.toString()) + Integer.valueOf(obj[4].toString()));
					index++;
					len++;
				}
			}
			data[index][0] = "4";
			data[index][1] = "联网POS终端";
			sql = "SELECT DESCR,nvl(term1.c,0),nvl(term2.c,0),nvl(term3.c,0),nvl(term4.c,0) FROM TBL_INF_MCHNT_TP_GRP grp "
					+ "left outer join (select MCHT_GRP,COUNT(*) as c FROM TBL_TERM_INF t,TBL_MCHT_BASE_INF m where m.ACQ_INST_ID in " + brhBelow + " and t.MCHT_CD = m.MCHT_NO AND t.REC_CRT_TS <= '" + lastYear + "' group by MCHT_GRP) term1 on (grp.MCHNT_TP_GRP = term1.MCHT_GRP) "
					+ "left outer join (select MCHT_GRP,COUNT(*) as c FROM TBL_TERM_INF t,TBL_MCHT_BASE_INF m where m.ACQ_INST_ID in " + brhBelow + " and t.MCHT_CD = m.MCHT_NO AND t.REC_CRT_TS < '" + lastMon + "' group by MCHT_GRP) term2 on (grp.MCHNT_TP_GRP = term2.MCHT_GRP)"
					+ "left outer join (select MCHT_GRP,COUNT(*) as c FROM TBL_TERM_INF t,TBL_MCHT_BASE_INF m where m.ACQ_INST_ID in " + brhBelow + " and t.MCHT_CD = m.MCHT_NO AND t.REC_CRT_TS >= '" + thisMonStart + "' and t.REC_CRT_TS <= '" + thisMonEnd + "' group by MCHT_GRP) term3 on (grp.MCHNT_TP_GRP = term3.MCHT_GRP)"
					+ "left outer join (select MCHT_GRP,COUNT(*) as c FROM TBL_TERM_INF t,TBL_MCHT_BASE_INF m where m.ACQ_INST_ID in " + brhBelow + " and t.MCHT_CD = m.MCHT_NO AND t.REC_CRT_TS >= '" + thisYearStart + "' and t.REC_CRT_TS <= '" + thisYearEnd + "' group by MCHT_GRP) term4 on (grp.MCHNT_TP_GRP = term4.MCHT_GRP) "
					+ "order by MCHNT_TP_GRP ";
			list = commQueryDAO.findBySQLQuery(sql);
			if (null != list && !list.isEmpty()) {
				it = list.iterator();
				while (it.hasNext()) {
					Object[] obj = (Object[]) it.next();
					if (!StringUtil.isNull(obj[0]) && (obj[0].toString().indexOf("(") != -1 || obj[0].toString().indexOf("（") != -1)) {
						int a = obj[0].toString().indexOf("(");
						int b = obj[0].toString().indexOf("（");
						if (a > 0 && b > 0) {
							data[index][2] = obj[0].toString().substring(0, a < b ? a : b);
						} else {
							data[index][2] = obj[0].toString().substring(0, a + b + 1);
						}
					} else {
						data[index][2] = obj[0].toString();
					}
					data[index][3] = "台";
					data[index][4] = obj[1].toString();
					data[index][5] = obj[2].toString();
					data[index][6] = obj[3].toString();
					data[index][7] = obj[4].toString();
					data[index][8] = String.valueOf(Integer.valueOf(obj[1]
							.toString()) + Integer.valueOf(obj[4].toString()));
					index++;
				}
			}
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();
			HSSFRow row = null;
			HSSFCell cell = null;
			sheet.setDefaultColumnWidth(10);
			sheet.setColumnWidth(1, 3000);
			sheet.setColumnWidth(2, 6000);
			//样式
			HSSFCellStyle styleTitle = ExcelUtil.createStyleTitle(workbook);
			HSSFCellStyle styleBold = ExcelUtil.createStyleBold(workbook);
			HSSFCellStyle styleCenter = ExcelUtil.createStyleCenter(workbook);
			HSSFCellStyle styleRight = ExcelUtil.createStyleRight(workbook);
			HSSFCellStyle styleThin = ExcelUtil.createStyleThinCenter(workbook);
			
			short rowIndex = 0;// 行的基准数
			// 定义主标题
			row = sheet.createRow(rowIndex++);
			row.setHeight((short) 800);
			cell = row.createCell(0);
			cell.setCellStyle(styleTitle);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("商户终端信息统计");
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));// 合并单元格
			rowIndex++;
			row = sheet.createRow(rowIndex++);
			cell = row.createCell(0);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(styleThin);
			cell.setCellValue("统计时间：");
			cell = row.createCell(1);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(styleThin);
			cell.setCellValue(date.substring(0,4) + "年" + date.substring(4) + "月");
			cell = row.createCell(3);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(styleThin);
			cell.setCellValue("统计机构：");
			cell = row.createCell(4);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(styleThin);
			cell.setCellValue(InformationUtil.getBrhName(brhId));
			
			
			String[] titles = { "序号", "项        目", "", "单位", "上年末", "上月末",
					"本月新增", "本年新增", "累计" };
			row = sheet.createRow(rowIndex++);
			for (int i = 0; i < titles.length; i++) {
				cell = row.createCell(i);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(titles[i]);
				cell.setCellStyle(styleBold);
			}
			for (int i = 0; i < index; i++) {
				row = sheet.createRow(rowIndex++);
				for (int j = 0; j < data[i].length; j++) {
					cell = row.createCell(j);
					if (j < 4) {
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellStyle(styleCenter);
						cell.setCellValue(data[i][j]);
					} else {
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellStyle(styleRight);
						cell.setCellValue(Double.valueOf(data[i][j]));
					}
				}
			}
			
			int start = 1;
			sheet.addMergedRegion(new CellRangeAddress(start + 1, start + 1, 4, 8));
			
			sheet.addMergedRegion(new CellRangeAddress(start + 2, start + 2, 1, 2));
			sheet.addMergedRegion(new CellRangeAddress(start + 3, start + 3, 1, 2));
			sheet.addMergedRegion(new CellRangeAddress(start + 3, start + 5, 0, 0));
			sheet.addMergedRegion(new CellRangeAddress(start + 4, start + 5, 1, 1));
			sheet.addMergedRegion(new CellRangeAddress(start + 6, start + 6, 1, 2));
			sheet.addMergedRegion(new CellRangeAddress(start + 6, start + 8, 0, 0));
			sheet.addMergedRegion(new CellRangeAddress(start + 7, start + 8, 1, 1));
			
			sheet.addMergedRegion(new CellRangeAddress(start + 9, start + 9 + len - 1, 0, 0));
			sheet.addMergedRegion(new CellRangeAddress(start + 9, start + 9 + len - 1, 1, 1));
			sheet.addMergedRegion(new CellRangeAddress(start + 9 + len, start + 9 + len + len - 1, 0, 0));
			sheet.addMergedRegion(new CellRangeAddress(start + 9 + len, start + 9 + len + len - 1, 1, 1));
			
			String path = ExcelUtil.writeFiles(workbook, "RN50202RN_" + 
					brhId + "_" + CommonFunction.getCurrentDateTime() + ".xls");
			
			return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + path);
			
		} catch (Exception e) {
			e.printStackTrace();
			return returnService("对不起,报表生成失败", e);
		}
	}
	
	@Override
	public String getMsg() {
		return msg;
	}

	@Override
	public boolean isSuccess() {
		return success;
	}
	
	private String date;
	private String brhId;
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getBrhId() {
		return brhId;
	}

	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}
	
}
