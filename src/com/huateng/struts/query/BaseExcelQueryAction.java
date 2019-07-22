package com.huateng.struts.query;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.struts2.ServletActionContext;

import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.common.StringUtil;
import com.huateng.struts.system.action.BaseSupport;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ExcelUtil;

import net.sf.json.JSONObject;

/**
 * project JSBConsole date 2013-4-15
 * 
 * @author 樊东东
 */
public abstract class BaseExcelQueryAction extends BaseSupport {

    protected HSSFWorkbook workbook;

    protected Sheet sheet;

    protected Row r;

    protected Cell c;

    protected InputStream is;

    protected Operator operator;

    /**
     * 
     * //TODO 组装Excel文件
     *
     * @param countList 数据列表(List<Object[]>)
     * @param count2List 统计数据(List<Object[]>)
     * @param arrStr  列头数组(String[])
     * @param nameStr  sheet页名字(lava.lang.String)
     * @param titleStr Excel表格的标题 (lava.lang.String)
     * @param length  列数
     * @return 行数
     */
    public int fillData2(List<Object[]> countList, List<Object[]> count2List, String[] arrStr, String nameStr, String titleStr, int length,
            Object[] arr) {
        int index = 1;
        if (arrStr == null || arrStr.length <= 0 || countList == null || countList.size() <= 0) {
            return 0;
        }
        workbook = new HSSFWorkbook(); // 定义一个新的工作簿
        Sheet sheet = workbook.createSheet(nameStr); // 创建第一个Sheet页

        Font font1 = workbook.createFont();
        font1.setFontName("宋体");
        font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示    
        font1.setFontHeightInPoints((short) 20);

        CellStyle style1 = workbook.createCellStyle();
        style1.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
        style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
        style1.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
        style1.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框 
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 内容居中
        style1.setFont(font1);

        Row row1 = sheet.createRow(index); // 创建第一行
        for (int i = 0; i <= length; i++) {
            Cell cell1 = row1.createCell(i);
            cell1.setCellValue("");
            cell1.setCellStyle(style1);
        }
        sheet.addMergedRegion(new CellRangeAddress(1, // 起始行
                1, // 结束行
                0, // 其实列
                length // 结束列(添加了序号列)
        ));
        Cell cell1 = row1.getCell(0);
        cell1.setCellValue(titleStr);

        Font font2 = workbook.createFont();
        font2.setFontName("宋体");
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示    
        font2.setFontHeightInPoints((short) 12);

        CellStyle style2 = workbook.createCellStyle();
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框 
        style2.setAlignment(HSSFCellStyle.ALIGN_RIGHT); // 内容居右
        style2.setFont(font2);

        Row row2 = sheet.createRow(++index); // 创建第二个行
        for (int i = 0; i <= length; i++) {
            Cell cell2 = row2.createCell(i);
            cell2.setCellValue("");
            cell2.setCellStyle(style2);
        }
        sheet.addMergedRegion(new CellRangeAddress(2, // 起始行
                2, // 结束行
                0, // 其实列
                length // 结束列(添加了序号列)
        ));
        String date = CommonFunction.getCurrentDateTimeForShow();
        Cell cell2 = row2.getCell(0);
        cell2.setCellValue("日期时间：" + date);

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

        Row row3 = sheet.createRow(++index); // 创建第二个行
        for (int i = 0; i <= length; i++) {
            Cell cell3 = row3.createCell(i);
            if (i == 0) {
                cell3.setCellValue("");
            } else {
                cell3.setCellValue("  "+arrStr[i - 1]+"  ");
            }
            cell3.setCellStyle(style3);
        }

        Font font = workbook.createFont();
        font.setFontName("宋体");
        //       font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示    
        font.setFontHeightInPoints((short) 12);

        CellStyle style = workbook.createCellStyle();
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框 
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 内容居中
        style.setFont(font);

        int rowNum = 1;
        int midLength = length;
        if (arr != null) {
            midLength = countList.get(0).length;
        }
        if (countList != null) {
            for (Object[] obj : countList) {
                Row row = sheet.createRow(++index); // 创建第二个行
                int midNum = 0;
                int countNum = -1;

                for (int i = 0; i <= midLength; i++) {
                    if (arr != null) {
                        for (Object obj2 : arr) {
                            if (Integer.parseInt(String.valueOf(obj2)) == i) {
                                midNum++;
                                break;
                            }
                        }
                    }
                    if (midNum == 0) {
                        countNum++;
                    } else {
                        midNum = 0;
                        continue;
                    }
                    Cell cell = row.createCell(countNum);
                    if (i == 0) {
                        cell.setCellValue(rowNum++);
                    } else {
                        if (obj[i - 1] == null || obj[i - 1] == "") {
                            cell.setCellValue(" ");
                        } else {
                            cell.setCellValue("  "+obj[i - 1].toString()+"  ");
                        }

                    }
                    cell.setCellStyle(style);
                }
            }
        }
        if (count2List != null) {
            for (Object[] obj : count2List) {
                Row rowTotal = sheet.createRow(++index); // 创建第二个行
                for (int i = 0; i <= length; i++) {
                    Cell cellTotal = rowTotal.createCell(i);
                    if (i == 0) {
                        cellTotal.setCellValue("合计");
                    } else {
                        if (obj[i - 1] == null || obj[i - 1] == "") {
                            cellTotal.setCellValue("");
                        } else {
                            cellTotal.setCellValue(obj[i - 1].toString());
                        }
                    }
                    cellTotal.setCellStyle(style);
                }
            }
        }
        
        for(int i = 0;i <= length; i ++){
        	sheet.autoSizeColumn(i,true);
        }
        
        return countList.size() + 1;
    }

    /**
     * 
     * //TODO 组装Excel文件
     *
     * @param countList 数据列表(List<Object[]>)
     * @param count2List 统计数据(List<Object[]>)
     * @param arrStr  列头数组(String[])
     * @param nameStr  sheet页名字(lava.lang.String)
     * @param titleStr Excel表格的标题 (lava.lang.String)
     * @param length  列数
     * @return 行数
     */
    public int fillData3(List<Object[]> countList, String[] arrStr,String nameStr, String titleStr, int length,Object[] arr) {
        int index = 1;
        if (arrStr == null || arrStr.length <= 0 || countList == null || countList.size() <= 0) {
            return 0;
        }
        workbook = new HSSFWorkbook(); // 定义一个新的工作簿
        Sheet sheet = workbook.createSheet(nameStr); // 创建第一个Sheet页
        sheet.setDefaultColumnWidth(6);
        sheet.setColumnWidth(0, 1000);
        sheet.setColumnWidth(2, 3000);
        sheet.setColumnWidth(3, 4000);
        sheet.setColumnWidth(4, 4000);
        if(length==16){
        	sheet.setColumnWidth(5, 2500);
        }
        sheet.setColumnWidth(length-5, 2000);
        sheet.setColumnWidth(length-4, 2000);

        Font font1 = workbook.createFont();
        font1.setFontName("宋体");
        font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示    
        font1.setFontHeightInPoints((short) 20);

        CellStyle style1 = workbook.createCellStyle();
        style1.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
        style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
        style1.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
        style1.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框 
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 内容居中
        style1.setFont(font1);

        Row row1 = sheet.createRow(index); // 创建第一行
        for (int i = 0; i <= length; i++) {
            Cell cell1 = row1.createCell(i);
            cell1.setCellValue("");
            cell1.setCellStyle(style1);
        }
        sheet.addMergedRegion(new CellRangeAddress(1, // 起始行
                1, // 结束行
                0, // 起始列
                length // 结束列(添加了序号列)
        ));
        Cell cell1 = row1.getCell(0);
        cell1.setCellValue(titleStr);

        Font font2 = workbook.createFont();
        font2.setFontName("宋体");
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示    
        font2.setFontHeightInPoints((short) 12);

        CellStyle style2 = workbook.createCellStyle();
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框 
        style2.setAlignment(HSSFCellStyle.ALIGN_RIGHT); // 内容居右
        style2.setFont(font2);

        Row row2 = sheet.createRow(++index); // 创建第二个行
        for (int i = 0; i <= length; i++) {
            Cell cell2 = row2.createCell(i);
            cell2.setCellValue("");
            cell2.setCellStyle(style2);
        }
        sheet.addMergedRegion(new CellRangeAddress(2, // 起始行
                2, // 结束行
                0, // 其实列
                length // 结束列(添加了序号列)
        ));
        String date = CommonFunction.getCurrentDateTimeForShow();
        Cell cell2 = row2.getCell(0);
        cell2.setCellValue("A:总金额,C:总笔数"+"          日期时间：" + date);

        Font font3 = workbook.createFont();
        font3.setFontName("宋体");
        //       font3.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示    
        font3.setFontHeightInPoints((short) 10);
        CellStyle style3 = workbook.createCellStyle();
        style3.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
        style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
        style3.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
        style3.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框 
        style3.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 内容居中
        style3.setFont(font3);
        
        Row row3 = sheet.createRow(++index); // 创建第三个行
        for (int i = 0; i <= length; i++) {
            Cell cell3 = row3.createCell(i);
            if (i == 0) {
                cell3.setCellValue("");
            } else {
                cell3.setCellValue(arrStr[i - 1]);
            }
            cell3.setCellStyle(style3);
        }
        
        Font font = workbook.createFont();
        font.setFontName("宋体");
        //       font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示    
        font.setFontHeightInPoints((short) 10);
        CellStyle style = workbook.createCellStyle();
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框 
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 内容居中
        style.setFont(font);

        int rowNum = 1;
        int midLength = length;
        if (arr != null) {
            midLength = countList.get(0).length;
        }
        if (countList != null) {
            for (Object[] obj : countList) {
                Row row = sheet.createRow(++index); // 创建第二个行
                int midNum = 0;
                int countNum = -1;

                for (int i = 0; i <= midLength; i++) {
                    if (arr != null) {
                        for (Object obj2 : arr) {
                            if (Integer.parseInt(String.valueOf(obj2)) == i) {
                                midNum++;
                                break;
                            }
                        }
                    }
                    if (midNum == 0) {
                        countNum++;
                    } else {
                        midNum = 0;
                        continue;
                    }
                    Cell cell = row.createCell(countNum);
                    if (i == 0) {
                        cell.setCellValue(rowNum++);
                    } else {
                        if (obj[i - 1] == null || obj[i - 1] == "") {
                            cell.setCellValue(" ");
                        } else {
                            cell.setCellValue(obj[i - 1].toString());
                        }

                    }
                    cell.setCellStyle(style);
                }
            }
        }

        return countList.size() + 1;
    }
    
    /**
     * 
     * //TODO 不读取Excel文件，直接生成报表
     *
     * @return
     * @author hanyongqing
     */
    public String download2() {

        operator = (Operator) ServletActionContext.getRequest().getSession().getAttribute(Constants.OPERATOR_INFO);
        try {
            deal();// 业务处理
            String path = ExcelUtil.writeFiles(workbook);
            String midStr = returnService(Constants.SUCCESS_CODE_CUSTOMIZE + path);
            Map<String, Object> midMap = new HashMap<String, Object>();
            midMap.put("success", success);
            midMap.put("msg", msg);
            String str = JSONObject.fromObject(midMap).toString();
            PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
            printWriter.write(str);
            printWriter.flush();
            printWriter.close();
            return midStr;
        } catch (Exception e) {
            e.printStackTrace();
            return returnService("对不起,报表生成失败", e);
        }
    }

    public String download() {
        operator = (Operator) ServletActionContext.getRequest().getSession().getAttribute(Constants.OPERATOR_INFO);

        try {

            String report = ExcelName.getFileName(getFileKey());
            is = ServletActionContext.getServletContext().getResourceAsStream(report);
            byte[] bts = IOUtils.toByteArray(is);

            workbook = new HSSFWorkbook(new ByteArrayInputStream(bts));
            sheet = workbook.getSheetAt(0);

            deal();// 业务处理
            String path = ExcelUtil.writeFiles(workbook);

            return returnService(Constants.SUCCESS_CODE_CUSTOMIZE + path);

        } catch (Exception e) {
            e.printStackTrace();
            return returnService("对不起,报表生成失败", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    return returnService("对不起,报表生成失败", e);
                }
            }
        }

    }

    /**
     * @return excel文件在配置文件中的key
     */
    protected abstract String getFileKey();

    /**
     * 
     * //TODO 读取Excel模板文件的形式
     *
     */
    protected abstract void deal();

    /**
     * 填充数据 sql查询出的list有多少条记录插多少行
     * 
     * @param sql
     * @param rowNum
     *            起始行
     * @param cellNum
     *            起始列
     * @param columnNum
     *            填充多少列
     * @param sheet
     */
    public int fillData(String sql, int rowNum, int cellNum, int columnNum) {

        List countList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
        if (countList != null && countList.size() > 0) {
            for (int index = 0, size = countList.size(); index < size; index++) {
                Object[] record = (Object[]) countList.get(index);

                r = sheet.getRow(rowNum + index);
                if (r == null) {
                    r = sheet.createRow(rowNum + index);
                }
                for (int j = 0; j < columnNum; j++) {
                    String v = String.valueOf(record[j]);
                    if (StringUtils.isEmpty(v) || "null".equals(v)) {// 这个是必须的，不然没数据会报错，不知道为什么没数据查出来的是"null"
                        v = "0";// 表示没数据
                    }
                    c = r.getCell(cellNum + j);
                    if (c == null) {
                        c = r.createCell(cellNum + j);
                    }
                    HSSFCellStyle styleThin = ExcelUtil.createStyleCenter(workbook);
                    c.setCellStyle(styleThin);

                    if (v.matches("^-?[0-9]+(\\.[0-9]+)?$")) {
                        if (v.length() < 12) {
                            if (v.indexOf(".") != -1) {
                                if (String.valueOf(Double.valueOf(v)).length() == v.length()) {
                                    c.setCellValue(Double.parseDouble(v));
                                } else {
                                    c.setCellValue(StringUtils.trim(v));
                                }
                            } else {
                                if (String.valueOf(Long.valueOf(v)).length() == v.length()) {
                                    c.setCellValue(Double.parseDouble(v));
                                } else {
                                    c.setCellValue(StringUtils.trim(v));
                                }
                            }
                        } else {
                            c.setCellValue(StringUtils.trim(v));
                        }
                    } else {
                        c.setCellValue(StringUtils.trim(v));
                    }
                }
            }

        }
        if (countList != null) {
            return countList.size();
        } else {
            return 0;
        }
    }

    public int fillData(List countList, int rowNum, int cellNum, int columnNum) {

        //		List countList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
        if (countList != null && countList.size() > 0) {

            for (int index = 0, size = countList.size(); index < size; index++) {
                Object[] record = (Object[]) countList.get(index);

                r = sheet.getRow(rowNum + index);
                if (r == null) {
                    r = sheet.createRow(rowNum + index);
                }
                HSSFCellStyle styleThin = ExcelUtil.createStyleCenter(workbook);
                for (int j = 0; j < columnNum; j++) {
                    String v = String.valueOf(record[j]).trim();
                    if (StringUtils.isEmpty(v) || "null".equals(v)) {// 这个是必须的，不然没数据会报错，不知道为什么没数据查出来的是"null"
                        v = "";// 表示没数据
                    }

                    c = r.getCell(cellNum + j);
                    if (c == null) {
                        c = r.createCell(cellNum + j);
                    }

                    //c.setCellStyle(styleThin);

                    if (v.matches("^-?[0-9]+(\\.[0-9]+)?$")) {
                        if (v.length() < 12) {
                            if (v.indexOf(".") != -1) {
                                if (String.valueOf(Double.valueOf(v)).length() == v.length()) {
                                    c.setCellValue(Double.parseDouble(v));
                                } else {
                                    c.setCellValue(StringUtils.trim(v));
                                }
                            } else {
                                if (String.valueOf(Long.valueOf(v)).length() == v.length()) {
                                    c.setCellValue(Double.parseDouble(v));
                                } else {
                                    c.setCellValue(StringUtils.trim(v));
                                }
                            }
                        } else {
                            c.setCellValue(StringUtils.trim(v));
                        }
                    } else {
                        c.setCellValue(StringUtils.trim(v));
                    }
                }
            }

        }
        if (countList != null) {
            return countList.size();
        } else {
            return 0;
        }
    }

    //获得月末
    public String getMonEnd(int m, int y, String YYYYMM) {
        String monEnd = null;
        if (m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12) {
            monEnd = YYYYMM + "31";
        } else if (m == 4 || m == 6 || m == 9 || m == 11) {
            monEnd = YYYYMM + "30";
        } else if (m == 2) {
            if (y % 4 == 0 || y % 400 == 0) {
                monEnd = YYYYMM + "29";
            } else {
                monEnd = YYYYMM + "28";
            }
        }

        return monEnd;
    }

    /**
     * 合并单元格， 都哪些列需要合并单元格(不跨列)
     * 
     * @param cellNums
     *            需要处理的列
     */
    public void consolidate(int[] cellNums) {
        for (int i = 0, len = cellNums.length; i < len; i++) {// 遍历需要处理的列
            int cellNum = cellNums[i];

            for (int j = sheet.getFirstRowNum(), lastRowNum = sheet.getLastRowNum(); j < lastRowNum; j++) {// 遍历行
                r = sheet.getRow(j);
                if (r == null) {
                    continue;
                }
                Cell cBegin = r.getCell(cellNum);
                if (cBegin == null) {
                    continue;
                }
                String beginValue = cBegin.getStringCellValue();
                if (StringUtil.isEmpty(beginValue)) {
                    continue;
                }
                int beginR = j;
                int endR = j;
                for (int k = j + 1; k <= lastRowNum; k++) {// 找出相同单元格的最大rowNum
                    r = sheet.getRow(k);
                    if (r == null) {
                        continue;
                    }
                    Cell cEnd = r.getCell(cellNum);
                    if (cEnd == null) {
                        continue;
                    }
                    String endValue = cEnd.getStringCellValue();
                    if (StringUtils.trim(beginValue).equals(StringUtils.trim(endValue))) {
                        endR = k;// 如果与beginR的单元格值相同，更新endR
                    } else {
                        break;
                    }
                }
                if (beginR < endR) {// 表示有多个单元格值一样需要合并单元格
                    sheet.addMergedRegion(new CellRangeAddress(beginR, endR, cellNum, cellNum));// 合并单元格
                    j = endR;// 更新j跳过合并后的单元格 下次循环j++ 所以不是endR+1
                }

            }
        }

    }

}
