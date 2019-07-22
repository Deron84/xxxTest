package com.rail.bo.pub;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.rail.po.base.RailEmployee;

/**  
* @author syl
* @version 创建时间：2019年5月4日 下午4:15:42  
* @desc
*/
public class PubExcelReadUtill {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	public static void read(InputStream inputStream) throws Exception {
		List<RailEmployee> employees = new ArrayList<RailEmployee>();
//		String filepath ="E:\\all_cach\\11.xls";
//		InputStream inputStream = new FileInputStream(filepath);
		HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
		HSSFSheet hssfSheet = workbook.getSheetAt(0);
		HSSFRow row = hssfSheet.getRow(0);//Row 每一行就是一个对象
		for(int i = 1;i<hssfSheet.getLastRowNum();i++){
			row = hssfSheet.getRow(i);
			RailEmployee employee =new RailEmployee();
			for(int j =0;j<row.getLastCellNum();j++){
		        HSSFCell cell = row.getCell((short)j);//Cell
		        String s = null;
		        if(cell!=null){
		        	s =  getCellContent(cell);//读取单元格String内容
		        	System.out.println("========index======"+j+":"+s);
		        }else{
		        	s = null;
		        }
		     // 0 人员编码	1 人员姓名	2 1、男；2、女	3 出生日期	4 身份证号	5 照片存放地址  6 	职务	 7 联系方式	8 0、未入网、1已入网、2、已出网  9	入职日期
		        if(j==0){
		        	employee.setEmployeeCode(s);
		        }else if(j==1){
		        	employee.setEmployeeName(s);
		        }else if(j==2){
		        	employee.setSex(s);
		        }else if(j==3){
		        	if(s!=null){
		        		Date d = sdf.parse(s);
		        		employee.setBirthday(d);
		        	}
		        }else if(j==4){
		        	employee.setIdNumber(s);
		        }else if(j==5){
		        	employee.setEmployeeImg(s);
		        }else if(j==6){
		        	employee.setJob(s);
		        }else if(j==7){
		        	employee.setEmployeeTel(s);
		        }else if(j==8){
		        	employee.setInfoSign(s);
		        }else if(j==9){
		        	if(s!=null){
		        		Date d = sdf.parse(s);
		        		employee.setEntryDate(d);
		        	}
		        }
			}
		}
		
	}
	public static String getCellContent(HSSFCell cell ) {
		String cellValue = null;
		if (null != cell) {
            switch (cell.getCellType()) {                     // 判断excel单元格内容的格式，并对其进行转换，以便插入数据库
            case 0:
                cellValue = String.valueOf((int) cell.getNumericCellValue());
                break;
            case 1:
                cellValue = cell.getStringCellValue();
                break;
            case 2:
                cellValue = cell.getNumericCellValue() + "";
                // cellValue = String.valueOf(cell.getDateCellValue());
                break;
            case 3:
                cellValue = "";
                break;
            case 4:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case 5:
                cellValue = String.valueOf(cell.getErrorCellValue());
                break;
            }
        } else {
            cellValue = null;
        }
		
		return cellValue;
				
	}

}

