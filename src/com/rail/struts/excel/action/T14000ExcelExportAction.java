package com.rail.struts.excel.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.huateng.common.grid.GridConfigMethod;
import com.huateng.system.util.ContextUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.rail.bo.pub.PubExportExcelUtil;

/**
 * 
 * ClassName: ToolsAction 
 * @Description: TODO
 * @author syl
 * @date 2019年4月15日
 */
public class T14000ExcelExportAction extends ActionSupport {
	private Log log = LogFactory.getLog(this.getClass());
	/**
	 * 
	 * @Description: 
	 * @param @throws UnsupportedEncodingException   
	 * @return void  
	 * @author syl
	 * @date 2019年4月25日
	 */
    public void exportExcelT140100() throws UnsupportedEncodingException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "作业单位管理";//文件名T1401
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	titleNames.add("作业单位名称");
    	titleNames.add("作业单位状态");
//    	titleNames.add("关联组织单位");
    	titleNames.add("创建人");
    	titleNames.add("创建时间");
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getConstOrg(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName,0);
    	log.info("导出 "+fileName+".xls 成功");
	}
    public void exportExcelT140101() throws UnsupportedEncodingException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "供应商管理";//文件名T1401
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	titleNames.add("供应商");
    	titleNames.add("供应商状态");
    	titleNames.add("地址");
    	titleNames.add("联系人");
    	titleNames.add("联系电话");
    	titleNames.add("传真");
    	titleNames.add("创建人");
    	titleNames.add("创建时间");
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getMfrsOrg(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName,0);
    	log.info("导出 "+fileName+".xls 成功");
    }
    public void exportExcelT140103() throws UnsupportedEncodingException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "工具名称管理";//文件名T1404
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	titleNames.add("工具名称	");
    	titleNames.add("工具名称状态");
    	titleNames.add("工具分类");
    	titleNames.add("工具图片");
    	titleNames.add("创建人");
    	titleNames.add("创建时间");
    	
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getToolName(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName);
    	log.info("导出 "+fileName+".xls 成功");
    }
    public void exportExcelT140104() throws UnsupportedEncodingException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "工具分类管理";//文件名T1404
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	titleNames.add("工具分类名称");
    	titleNames.add("工具分类状态");
    	titleNames.add("创建人");
    	titleNames.add("创建时间");
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getToolType(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName);
    	log.info("导出 "+fileName+".xls 成功");
    }
    public void exportExcelT140105() throws UnsupportedEncodingException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "工具单位管理";//文件名T1404
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	titleNames.add("工具单位名称");
    	titleNames.add("工具单位状态");
    	titleNames.add("创建人");
    	titleNames.add("创建时间");
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getToolUnit(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName);
    	log.info("导出 "+fileName+".xls 成功");
    }
    public void exportExcelT140107() throws UnsupportedEncodingException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "门禁终端类型记录";//文件名T1404
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	titleNames.add("终端类型名称");
    	titleNames.add("终端类型状态");
    	titleNames.add("创建人");
    	titleNames.add("创建时间");
    	
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getAccessEquipType(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName);
    	log.info("导出 "+fileName+".xls 成功");
    }
}



