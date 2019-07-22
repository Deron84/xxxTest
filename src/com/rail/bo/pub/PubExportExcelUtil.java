package com.rail.bo.pub;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

/**  
* @author syl
* @version 创建时间：2019年4月23日 下午7:07:43  
* @desc
*/
public class PubExportExcelUtil {
	/**
	 * 
	 * @Description: 
	 * @param @param request
	 * @param @param response 
	 * @param @param titleNames  表格的列名 ['名称','编码'] 
	 * @param @param dataList  表格的数据   [['钳子','GT_0001'],['钳子','GT_0001']]
	 * @param @param fileName  导出的文件名 工具信息 
					diff
	 * @param @throws UnsupportedEncodingException   
	 * @return void  
	 * @author syl
	 * @date 2019年4月24日
	 */
	public static void doexportExcel(HttpServletRequest request,HttpServletResponse response,List<String> titleNames,
			List<Object[]> dataList,String fileName)throws UnsupportedEncodingException{
		doexportExcel(request, response, titleNames, dataList, fileName, 0);
	}
	public static void doexportExcel(HttpServletRequest request,HttpServletResponse response,List<String> titleNames,List<Object[]> dataList,String fileName,int diff)
    		throws UnsupportedEncodingException{
		int length = titleNames.size();
		String title = fileName;
    	//一、从后台拿数据
		if (null == request || null == response)
		{
			return;
		}
		List<Map> list = new ArrayList<Map>(7);
		//二、 数据转成excel
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/x-download");
		SimpleDateFormat f1 = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = f1.format(new Date());
		
		
		//中文乱码 

		 fileName =time+ fileName+".xls";  
	    String agent = request.getHeader("USER-AGENT").toLowerCase();
	    if (agent.contains("firefox")) {
	     response.reset(); 
	      response.setContentType("application/vnd.ms-excel;charset=UTF-8"); 
	        response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), "ISO8859-1") );
	    }else{
	      fileName = URLEncoder.encode(fileName, "UTF-8"); 
	         response.reset(); 
	       // ContentType 可以不设置 
	        response.setContentType("application/vnd.ms-excel;charset=UTF-8"); 
	        response.addHeader("Content-Disposition", "attachment;filename=" + fileName); 
	    } 
		
		
//		fileName = time+"-"+fileName+".xls";
//		fileName = URLEncoder.encode(fileName, "UTF-8");
//		response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        // 第一步：定义一个新的工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		//XSSFWorkbook wb = new XSSFWorkbook();
		// 第二步：创建一个Sheet页
        Sheet sheet = workbook.createSheet("Sheet 1");
        
       
        
        
        Font font1 = workbook.createFont();
        font1.setFontName("宋体");
        font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示    
        font1.setFontHeightInPoints((short) 28);
        CellStyle style1 = workbook.createCellStyle();
        style1.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
        style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
        style1.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
        style1.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框 
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 内容居中
        style1.setFont(font1);
        
        Font font2 = workbook.createFont();
        font2.setFontName("宋体");
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示    
        font2.setFontHeightInPoints((short) 12);

        CellStyle style2 = workbook.createCellStyle();
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框 
//        style2.setAlignment(HSSFCellStyle.ALIGN_RIGHT); // 内容居右
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 内容居右
        style2.setFont(font2);
        
        Font font3 = workbook.createFont();
        font3.setFontName("宋体");
        //       font3.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示    
        font3.setFontHeightInPoints((short) 12);

        CellStyle style3 = workbook.createCellStyle();
        style3.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
        style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
        style3.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
        style3.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框 
        style3.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 内容居中
        style3.setFont(font3);
        
//        Row row1 = sheet.createRow(1); // 创建第一行
//        for (int i = 0; i <= 14; i++) {
//            Cell cell1 = row1.createCell(i);
//            cell1.setCellValue("111");
//        }
        Row row1 = sheet.createRow(0); // 创建第一行
        for (int i = 0; i <= length-1; i++) {
            Cell cell1 = row1.createCell(i);
            cell1.setCellValue("");
            cell1.setCellStyle(style1);
        }
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, titleNames.size() - 1));// 
//        sheet.addMergedRegion(new CellRangeAddress(0, // 起始行
//                1, // 结束行
//                0, // 其实列
//                length // 结束列(添加了序号列)
//        ));
        Cell cell1 = row1.getCell(0);
        cell1.setCellValue(title);
        
        
		sheet.setDefaultRowHeight((short) (4 * 256));//设置行高
		sheet.setColumnWidth(0, 4000);//设置列宽
		sheet.setColumnWidth(1,5500);
		sheet.setColumnWidth(2,5500);
		sheet.setColumnWidth(3,5500);
		sheet.setColumnWidth(4,5500);
		sheet.setColumnWidth(5,5500);
		sheet.setColumnWidth(6,5500);
		sheet.setColumnWidth(7,5500);
		sheet.setColumnWidth(8,5500);
		sheet.setColumnWidth(9,5500);
		sheet.setColumnWidth(10,5500);
		sheet.setColumnWidth(11,3000);
		sheet.setColumnWidth(12,3000);
		sheet.setColumnWidth(13,3000);
		sheet.setColumnWidth(14,3000);
		sheet.setColumnWidth(15,3000);
		sheet.setColumnWidth(16,3000);
		sheet.setColumnWidth(17,3000);
		sheet.setColumnWidth(18,3000);
		sheet.setColumnWidth(19,3000);
		sheet.setColumnWidth(20,3000);
		sheet.setColumnWidth(21,3000);
		sheet.setColumnWidth(22,3000);
		sheet.setColumnWidth(23,3000);
		sheet.setColumnWidth(24,3000);
		sheet.setColumnWidth(25,3000);
		sheet.setColumnWidth(26,3000);
		Font font = workbook.createFont();
		//font.setFontName("宋体");
		font.setFontHeightInPoints((short) 24);
		
		Row row = sheet.createRow(1);
		for(int i = 0;i<titleNames.size();i++){
			Cell cell = row.createCell(i);
			cell.setCellStyle(style2);
    		cell.setCellValue(titleNames.get(i));
		}
//		Cell cell = row.createCell(0);
//		cell.setCellValue("流水号 ");
//		cell = row.createCell(1);
//		cell.setCellValue("微信名 ");
//		cell = row.createCell(2);
//		cell.setCellValue("微信订单号");
//		cell = row.createCell(13);
//		cell.setCellValue("钱包剩余金额");
//		
		
        
		Row rows;
		Cell cells;
		//for (int i = 0; i < list.size(); i++) {
			for (int i = 0; i < dataList.size(); i++) {
			// 第三步：在这个sheet页里创建一行
			rows = sheet.createRow(i+2);
			Object[]  item    = (Object[]) dataList.get(i);
			//int j = 0;
			for(int j = 0;j<titleNames.size();j++){
//				for(int j = 0;j<item.length - diff;j++){
				cells = rows.createCell(j);
				cells.setCellStyle(style3);
				if(item[j]==null){
					continue;
				}
				cells.setCellValue(String.valueOf(item[j]));
			}
//			for(Object o:item){
//				// 第四步：在该行创建一个单元格
//			
//				// 第五步：在该单元格里设置值
////				cells = rows.createCell(1+i);
////				cells.setCellValue("1111");
////				cells = rows.createCell(5);
////				cells.setCellValue("1111");
////				cells = rows.createCell(9);
////				cells.setCellValue("1111");
////				cells = rows.createCell(11);
//			}
			
		}
			
			try {
				OutputStream out = response.getOutputStream();
    			workbook.write(out);
    			out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	
    	
	}
}

