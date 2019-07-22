package com.huateng.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.log4j.Logger;

import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.system.util.ContextUtil;
/**
 * 
 * Title: 报表生成类
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
public class ReportCreator {
	/**报表对象*/
	private JasperReport reportObject = null;
	/**报表打印对象*/
	private JasperPrint jasperPrint = null;
	/**报表类型*/
	private String reportType = "";
	/**数据库连接*/
	private static Connection connection = null;
	/**数据库执行句柄*/
	private PreparedStatement preparedStatement = null;
	/**结果集*/
	private ResultSet resultSet = null;
	/**日志*/
	private static Logger log = Logger.getLogger(ReportCreator.class);
	
	public ReportCreator() {
		if(connection == null)
			try {
				connection = ((DataSource)ContextUtil.getBean("dataSourceQuery")).getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
				log.error("报表生成类初始化错误：" + e.getMessage());
			}
	}
	
	/**
	 * 初始化报表模版
	 * @param reportStream
	 * @param parameters
	 * @param dataSource
	 * @param reportType
	 * @throws JRException
	 * 2010-8-27下午02:42:12
	 */
	public void initReportData(InputStream reportStream, Map<String, String> parameters,
	        JRDataSource dataSource, String reportType) throws JRException {
		reportObject = (JasperReport) JRLoader.loadObject(reportStream);
		jasperPrint = JasperFillManager.fillReport(reportObject, parameters,
		        dataSource);
		this.reportType = reportType;
	}
	/**
	 * 初始化报表模版
	 * @param reportStream
	 * @param parameters
	 * @param reportType
	 * @throws JRException
	 * 2010-8-27下午02:44:03
	 */
	public void initReportNoData(InputStream reportStream, Map<String, String> parameters,
	        String reportType) throws JRException {
		reportObject = (JasperReport) JRLoader.loadObject(reportStream);
		jasperPrint = JasperFillManager.fillReport(reportObject, parameters);
		this.reportType = reportType;
	}
	
	/**
	 * 导出报表
	 * @param outputStream
	 * @param name
	 * @throws JRException
	 * @throws IOException
	 * 2010-8-27下午02:44:12
	 */
	public void exportReport(OutputStream outputStream,String name) throws JRException, IOException{
		if (reportType.equals(Constants.REPORT_PDFMODE)) {
			pdfReportExporter(outputStream);
		} else if (reportType.equals(Constants.REPORT_RTFMODE)) {
			rtfReportExporter(outputStream);
		} else if (reportType.equals(Constants.REPORT_HTMLMODE)) {
	        htmlReportExporter(outputStream);
	    } else if (reportType.equals(Constants.REPORT_TXTMODE)){
	        txtReportExporter(outputStream);
	    } else if(reportType.equals(Constants.REPORT_EXCELMODE)) {
	    	xlsReportExporter(outputStream,name);
	    }
	}
	
	/**
	 * 导出PDF报表
	 * @param outputStream
	 * @throws JRException
	 * @throws IOException
	 * 2010-8-27下午02:44:21
	 */
	private void pdfReportExporter(OutputStream outputStream)
			throws JRException, IOException {
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT,
				this.jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		exporter.exportReport();
		outputStream.close();
		outputStream = null;
		exporter = null;
	}
	
	/**
	 * 导出RTF报表
	 * @param outputStream
	 * @throws JRException
	 * @throws IOException
	 * 2010-8-27下午02:44:31
	 */
	private void rtfReportExporter(OutputStream outputStream)
			throws JRException, IOException {
		JRRtfExporter exporter = new JRRtfExporter();
		exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "gb2312");
		exporter.setParameter(JRExporterParameter.JASPER_PRINT,
				this.jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		exporter.exportReport();
		outputStream.close();
		outputStream = null;
		exporter = null;
	}
	
	/**
	 * 导出HTML报表
	 * @param outputStream
	 * @throws JRException
	 * @throws IOException
	 * 2010-8-27下午02:44:42
	 */
    private void htmlReportExporter(OutputStream outputStream)
            throws JRException, IOException {
        JRHtmlExporter exporter = new JRHtmlExporter();
        exporter.setParameter(JRHtmlExporterParameter.CHARACTER_ENCODING, "gb2312");
        exporter.setParameter(JRHtmlExporterParameter.JASPER_PRINT,
                this.jasperPrint);
        exporter.setParameter(JRHtmlExporterParameter.OUTPUT_STREAM, outputStream);
        exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
        exporter.exportReport();
        outputStream.close();
        outputStream = null;
		exporter = null;
    }
    
    /**
     * 导出TXT报表
     * @param outputStream
     * @throws JRException
     * @throws IOException
     * 2010-8-27下午02:44:52
     */
    private void txtReportExporter(OutputStream outputStream)
            throws JRException, IOException {
        JRTextExporter exporter = new JRTextExporter();
        exporter.setParameter(JRTextExporterParameter.CHARACTER_ENCODING, "GBK");
        exporter.setParameter(JRTextExporterParameter.JASPER_PRINT,
                this.jasperPrint);
        exporter.setParameter(JRTextExporterParameter.OUTPUT_STREAM, outputStream);
        exporter.setParameter(JRTextExporterParameter.PAGE_WIDTH, new Integer(200));
        exporter.setParameter(JRTextExporterParameter.PAGE_HEIGHT, new Integer(200));
        exporter.setParameter(JRTextExporterParameter.BETWEEN_PAGES_TEXT,
                System.getProperty("line.separator") + System.getProperty("line.separator"));
        exporter.setParameter(JRTextExporterParameter.LINE_SEPARATOR, System.getProperty("line.separator"));
        exporter.exportReport();
        outputStream.close();
        outputStream = null;
		exporter = null;
    }
    
    /**
     * 导出XLS报表
     * @param outputStream
     * @param name
     * @throws JRException
     * @throws IOException
     * 2010-8-27下午02:45:01
     */
    private void xlsReportExporter(OutputStream outputStream,String name)
            throws JRException, IOException {
    	JRXlsExporter exporter = new JRXlsExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT,
				this.jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
                Boolean.FALSE);
        exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
                Boolean.FALSE);
        exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
                Boolean.TRUE);
        exporter.setParameter(JRXlsExporterParameter.SHEET_NAMES,
                new String[]{name});
        exporter.exportReport();
		outputStream.close();
		outputStream = null;
		exporter = null;
    }
    
    
    /**
     * 导出多表单XLS报表
     * @param jasperPrintList
     * @param outputStream
     * @param names
     * @throws JRException
     * @throws IOException
     * 2010-8-27下午02:45:14
     */
    public void multXlsReportExporter(List<JasperPrint> jasperPrintList,OutputStream outputStream,String[] names)
            throws JRException, IOException {
    	JRXlsExporter exporter = new JRXlsExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST,
				jasperPrintList);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
                Boolean.FALSE);
        exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
                Boolean.FALSE);
        exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
                Boolean.TRUE);
        exporter.setParameter(JRXlsExporterParameter.CHARACTER_ENCODING,
                "UTF-8");
        exporter.setParameter(JRXlsExporterParameter.SHEET_NAMES,
        		names);
        exporter.exportReport();
		outputStream.close();
		outputStream = null;
		exporter = null;
    }
    
    /**
     * 导出多页面RTF报表
     * @param jasperPrintList
     * @param outputStream
     * @throws JRException
     * @throws IOException
     * 2010-8-27下午02:45:27
     */
    public void multRtfReportExporter(List<JasperPrint> jasperPrintList,OutputStream outputStream) 
    				throws JRException, IOException {
    	JRRtfExporter exporter = new JRRtfExporter();
    	exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST,
				jasperPrintList);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
        exporter.exportReport();
		outputStream.close();
		outputStream = null;
		exporter = null;
    }
    
    /**
     * 生成数据
     * @param sql
     * @param title
     * @return
     * @throws SQLException
     * 2010-8-27下午02:46:11
     */
    public Object[][] process(String sql, String[] title) throws SQLException {
		try {
			if(title == null) {
				throw new IllegalArgumentException("报表模型的[ title ]属性不能为null");
			}
			if(sql == null) {
				throw new IllegalArgumentException("报表模型的[ sql ]属性不能为null");
			}
			ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
			
			List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
			Object[][] data = new Object[dataList.size()][title.length];
			for(int i = 0; i < dataList.size(); i++) {
				data[i] =  dataList.get(i);
			}
			return data;
		}catch(Exception e ) {
			System.out.println("报表通用接口：" + e);
		}
		return null;
	}
	
}
