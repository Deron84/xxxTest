package com.huateng.system.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import com.huateng.common.StringUtil;
import com.huateng.common.SysParamConstants;


public class ExcelUtil {
	
	/**
	 * @param titles
	 * @param dataList
	 * @param keyList
	 * @param Moneys
	 * @return
	 * @throws Exception
	 */
	public static HSSFWorkbook makeExcel(String[] titles, List<Map<String, String>> dataList, List<String> keyList, int defaultSize) throws Exception{
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet s = wb.createSheet();
		s.setDefaultColumnWidth(defaultSize);
		short rowIndex = 0;
		HSSFRow rowTitle = s.createRow(rowIndex++);
		for(int i = 0;i<titles.length;i++){
			HSSFCell title = rowTitle.createCell(i);
			title.setCellType(HSSFCell.CELL_TYPE_STRING);
			title.setCellValue(titles[i]);
		}
		for(int i = 0; i<dataList.size();i++){
			Map<String, String> dataMap = dataList.get(i);
			HSSFRow row = s.createRow(rowIndex++);
			for(int j = 0; j < keyList.size(); j++){
				String key = keyList.get(j);
				String value = dataMap.get(key);
				HSSFCell cell = row.createCell(j);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				if(!StringUtil.isEmpty(value)){
					cell.setCellValue(dataMap.get(keyList.get(j)).trim());
				}
			}
		}
		return wb;
	}
	
	/**
	 * @param is
	 * @param keyList
	 * @param Moneys
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, String>> getExcel(InputStream is, List<String> keyList) throws Exception{
		
		List<Map<String, String>> dataList = new ArrayList<Map<String,String>>();
		HSSFWorkbook wb = new HSSFWorkbook(is);
		Sheet sheet = wb.getSheetAt(0);
		for (Row row : sheet) {
			if(row.getRowNum() == 0) continue;
			Map<String, String> tmpMap = new HashMap<String, String>();
			for (int i = 0; i < keyList.size(); i++) {
				Cell cell = row.getCell(i);
				String data = "";
				if(cell == null){
					tmpMap.put(keyList.get(i), "");
					continue;
				}
				switch (cell.getCellType()) {
					case Cell.CELL_TYPE_BLANK: {
						data = "";
						break;
					}
					case Cell.CELL_TYPE_BOOLEAN: {
						data = "ERROR";
						break;
					}
					case Cell.CELL_TYPE_ERROR: {
						data = "ERROR";
						break;
					}
					case Cell.CELL_TYPE_FORMULA: {
						data = cell.getCellFormula();
						break;
					}
					case Cell.CELL_TYPE_NUMERIC: {
						data = String.valueOf(cell.getNumericCellValue());
						break;
					}
					case Cell.CELL_TYPE_STRING: {
						data = cell.getStringCellValue();
						break;
					}
				}
				if(!StringUtil.isNull(data)&&!data.equals("ERROR")){
					if(data.indexOf("E") != -1){
						DecimalFormat df = new DecimalFormat("0");
						data =  df.format(Double.parseDouble(data));
					} else if(data.indexOf(".") != -1){
						if(Double.valueOf(data) == 0){
							data = "0";
						} else {
							while(data.lastIndexOf("0") == data.length() - 1){
								data = data.substring(0, data.length() - 1);
							}
							if(data.lastIndexOf(".") == data.length() - 1){
								data = data.substring(0, data.length() - 1);
							}
						}
					}
					tmpMap.put(keyList.get(i), data.trim());
				}else{
					tmpMap.put(keyList.get(i), "");
				}
			}
			dataList.add(tmpMap);
		}
		is.close();
		return dataList;
	}
	
	public static HSSFWorkbook makeSpecialExcel2(String title, String[] detail,List<String> keyList,
			List<Map<String, String>> dataList, int defaultWidth, int titleLength) {

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		HSSFRow row = null;
		HSSFCell cell = null;

		HSSFFont font1 = workbook.createFont();
		font1.setFontHeight((short) 400);
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		sheet.setDefaultColumnWidth(defaultWidth);

		HSSFCellStyle style1 = workbook.createCellStyle();
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, keyList.size() - 1));// 
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 
		style1.setFont(font1);
		

		// DETAIL
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);//

		// TITLES
		HSSFCellStyle style3 = workbook.createCellStyle();
		style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 
		style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 
		style3.setFillBackgroundColor(IndexedColors.BLUE.getIndex());
		style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style3.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style3.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style3.setFont(font2);
		// DATE
		HSSFCellStyle style4 = workbook.createCellStyle();
		style4.setAlignment(HSSFCellStyle.ALIGN_RIGHT);// 
		style4.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style4.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style4.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style4.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		
		
		
		short rowIndex = 0;// 

		// 
		row = sheet.createRow(rowIndex++);
		row.setHeight((short) 800);
		cell = row.createCell(0);
		cell.setCellStyle(style1);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(title);
		
		//
		rowIndex++;
		// DETAIL
		if(detail.length != 0){
			row = sheet.createRow(rowIndex++);
			for (int i = 0; i < detail.length; i++) {
				cell = row.createCell(i);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(detail[i]);
				cell.setCellStyle(style2);
			}
		}


		// 
		for (int i = 0; i < dataList.size(); i++) {
			Map<String, String> dataMap = dataList.get(i);
			row = sheet.createRow(rowIndex++);
			
			for (int j = 0; j < keyList.size(); j++) {
				cell = row.createCell(j);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				if(i < titleLength){
					cell.setCellStyle(style3);
				} else {
					cell.setCellStyle(style4);
				}
				if (StringUtil.isNull(dataMap.get(keyList.get(j)))) {
					cell.setCellValue("");
				} else {
					cell.setCellValue(dataMap.get(keyList.get(j)).trim());
				}
			}
		}
		return workbook;
	}
	
	public static HSSFWorkbook makeSpecialExcel(String title, String[] detail,
			String[] titles, List<String> keyList,
			List<Map<String, String>> dataList, String[] totalDetial, String[] subDetial, int defaultWidth) {
		// 
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		HSSFRow row = null;
		HSSFCell cell = null;
		// 
		HSSFFont font1 = workbook.createFont();
		font1.setFontHeight((short) 600);
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 
		sheet.setDefaultColumnWidth(defaultWidth);
		// 
		HSSFCellStyle style1 = workbook.createCellStyle();
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, keyList.size() - 1));// 
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 
		style1.setFont(font1);
		

		// DETAIL
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 

		// TITLES
		HSSFCellStyle style3 = workbook.createCellStyle();
		style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 
		style3.setFillBackgroundColor(IndexedColors.BLUE.getIndex());
		style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style3.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style3.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style3.setFont(font2);
		// DATE
		HSSFCellStyle style4 = workbook.createCellStyle();
		style4.setAlignment(HSSFCellStyle.ALIGN_RIGHT);// 
		style4.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style4.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style4.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style4.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		
		
		
		short rowIndex = 0;// 

		// 
		row = sheet.createRow(rowIndex++);
		row.setHeight((short) 800);
		cell = row.createCell(0);
		cell.setCellStyle(style1);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(title);
		
		//
		rowIndex++;
		// DETAIL
		if(detail.length != 0){
			row = sheet.createRow(rowIndex++);
			for (int i = 0; i < detail.length; i++) {
				cell = row.createCell(i);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(detail[i]);
				cell.setCellStyle(style2);
			}
		}

		// Titles
		row = sheet.createRow(rowIndex++);
		for (int i = 0; i < titles.length; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(style3);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(titles[i]);
		}

		// 
		for (int i = 0; i < dataList.size(); i++) {
			Map<String, String> dataMap = dataList.get(i);
			row = sheet.createRow(rowIndex++);
			for (int j = 0; j < keyList.size(); j++) {
				String key = keyList.get(j);
				String value = dataMap.get(key);
				cell = row.createCell(j);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellStyle(style4);
				if (StringUtil.isNull(dataMap.get(keyList.get(j)))) {
					cell.setCellValue("");
				} else {
					cell.setCellValue(dataMap.get(keyList.get(j)).trim());
				}
			}
		}
		rowIndex += 2;
		// TOTALDETAIL
		if(totalDetial.length != 0){
			row = sheet.createRow(rowIndex++);
			for (int i = 0; i < totalDetial.length; i++) {
				cell = row.createCell(i);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(totalDetial[i]);
				cell.setCellStyle(style2);
			}
		}
		// SUBDETAIL
		row = sheet.createRow(rowIndex++);
		for (int i = 0; i < subDetial.length; i++) {
			cell = row.createCell(i);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(subDetial[i]);
			cell.setCellStyle(style2);
		}
		return workbook;
	}
	
	/**
	 * @param Moneys
	 * @param key
	 * @param value
	 * @return
	 */
	public static String changeMoneyDivide(String[] Moneys, String key, String value){
		try{
			for(String moneyKey : Moneys){
				if(moneyKey.equals(key)){
					BigDecimal bd = new BigDecimal(value).divide(new BigDecimal(100)).setScale(2);
					return String.valueOf(bd);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return value;
	}
	
	/**
	 * @param Moneys
	 * @param key
	 * @return
	 */
	public static boolean isMoney(String[] Moneys, String key){
		if(Moneys.length == 0) return false;
		for(String moneyKey : Moneys){
			if(moneyKey.equals(key)){
				return true;
			}
		}
		return false;
	}
	
	public static HSSFFont createFontBig(HSSFWorkbook workbook){
		HSSFFont font = workbook.createFont();
		font.setFontHeight((short) 400);
		return font;
	}
	
	public static HSSFFont createFontBold(HSSFWorkbook workbook){
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		return font;
	}
	
	public static HSSFCellStyle createStyleTitle(HSSFWorkbook workbook){
		HSSFCellStyle style = workbook.createCellStyle();
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFont(createFontBig(workbook));
		return style;
	}
	
	public static HSSFCellStyle createStyleBold(HSSFWorkbook workbook){
		HSSFCellStyle style = workbook.createCellStyle();
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setFont(createFontBold(workbook));
		return style;
	}
	
	public static HSSFCellStyle createStyleLeft(HSSFWorkbook workbook){
		HSSFCellStyle style = workbook.createCellStyle();
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		return style;
	}
	
	public static HSSFCellStyle createStyleCenter(HSSFWorkbook workbook){
		HSSFCellStyle style = workbook.createCellStyle();
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		return style;
	}
	
	public static HSSFCellStyle createStyleRight(HSSFWorkbook workbook){
		HSSFCellStyle style = workbook.createCellStyle();
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		return style;
	}
	
	public static HSSFCellStyle createStyleThinCenter(HSSFWorkbook workbook){
		HSSFCellStyle style = workbook.createCellStyle();
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		return style;
	}
	
	
	public static String writeFiles(HSSFWorkbook workbook, String fileName){
		DataOutputStream out = null;
		try {
			String basePath = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK);
			basePath = basePath.replace("\\", "/");
			File writeFile = new File(basePath + fileName);
			if (!writeFile.getParentFile().exists()) {
				writeFile.getParentFile().mkdirs();
			}
			if (writeFile.exists()) {
				writeFile.delete();
			}
			out = new DataOutputStream(new FileOutputStream(writeFile));
			workbook.write(out);
			out.flush();
			return basePath + fileName;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != out) {
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "F";
	}

	public static String writeFiles(HSSFWorkbook workbook){
		DataOutputStream out = null;
		String fileName = UUID.randomUUID().toString();//防止文件重名
		try {
			String basePath = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK);
			basePath = basePath.replace("\\", "/");
			File writeFile = new File(basePath + fileName);
			if (!writeFile.getParentFile().exists()) {
				writeFile.getParentFile().mkdirs();
			}
			if (writeFile.exists()) {
				writeFile.delete();
			}
			out = new DataOutputStream(new FileOutputStream(writeFile));
			workbook.write(out);
			out.flush();
			return basePath + fileName;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != out) {
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "F";
	}
}
