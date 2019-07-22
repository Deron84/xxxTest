package com.rail.struts.excel.action;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
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
@SuppressWarnings("serial")
public class T10000ExcelExportAction extends ActionSupport {
	private Log log = LogFactory.getLog(this.getClass());
	/**
	 * 
	 * @Description: 
	 * @param @throws UnsupportedEncodingException   
	 * @return void  
	 * @author syl
	 * @date 2019年4月25日
	 */
    public void exportExcelT100101() throws UnsupportedEncodingException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "仓库信息维护";//文件名T1001
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	titleNames.add("仓库编码");
    	titleNames.add("仓库名称");
    	titleNames.add("仓库地址");
    	titleNames.add("上级仓库");
    	titleNames.add("仓库容量");
    	titleNames.add("负责人");
    	titleNames.add("联系方式");
    	titleNames.add("所属机构");
    	titleNames.add("仓库状态");
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getRailWhseInfo(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName,2);
    	log.info("导出 "+fileName+".xls 成功");
	}
    public void exportExcelT100102() throws UnsupportedEncodingException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "仓库信息查询";//文件名T1001
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	titleNames.add("仓库编码");
    	titleNames.add("仓库名称");
    	titleNames.add("仓库地址");
    	titleNames.add("上级仓库");
    	titleNames.add("仓库容量");
    	titleNames.add("负责人");
    	titleNames.add("联系方式");
    	titleNames.add("所属机构");
    	titleNames.add("仓库状态");
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getRailWhseInfo(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName,titleNames.size());
    	log.info("导出 "+fileName+".xls 成功");
    }
    public void exportExcelT100103() throws UnsupportedEncodingException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "仓库盘点";//文件名T1004
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	titleNames.add("仓库编码");
    	titleNames.add("仓库名称");
    	titleNames.add("工具名称");
    	titleNames.add("正常数量");
    	titleNames.add("维护数量	");
    	titleNames.add("报废数量");
    	titleNames.add("停用数量");
    	titleNames.add("使用次数");
    	
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getWhseTools(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName,0);
    	log.info("导出 "+fileName+".xls 成功");
    }
    public void exportExcelT100104() throws UnsupportedEncodingException, ParseException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "移库查询";//文件名T1004
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	titleNames.add("工具编码");
    	titleNames.add("工具名称");
    	titleNames.add("移库前仓库编码");
    	titleNames.add("移库前仓库名称");
    	titleNames.add("移库后仓库编码");
    	titleNames.add("移库后仓库名称");
    	titleNames.add("移库事由");
    	titleNames.add("移库类型");
    	titleNames.add("移库操作人");
    	titleNames.add("移库时间");
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getRailToolTransfer(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName,4);
    	log.info("导出 "+fileName+".xls 成功");
    }
    public void exportExcelT100105() throws UnsupportedEncodingException, ParseException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "仓库统计";//文件名T1004
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	titleNames.add("仓库编码");
    	titleNames.add("仓库名称");
    	titleNames.add("工具名称");
    	titleNames.add("存放位置（架-层-位）");
    	titleNames.add("在库数量");
    	titleNames.add("出库数量");
    	titleNames.add("总数量");
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getWhseToolsStatic(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName,0);
    	log.info("导出 "+fileName+".xls 成功");
    }
    public void exportExcelT100200() throws UnsupportedEncodingException, ParseException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "人员出入库查询";//文件名T1004
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	titleNames.add("人员编码");
    	titleNames.add("人员名称");
    	titleNames.add("性别");
    	titleNames.add("联系方式");
    	titleNames.add("所属单位");
    	titleNames.add("工单编码");
    	titleNames.add("工单名称");
    	titleNames.add("仓库编码");
    	titleNames.add("仓库名称");
    	titleNames.add("出库/入库");
    	titleNames.add("出入时间");
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getRailWhseEmployee(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName,0);
    	log.info("导出 "+fileName+".xls 成功");
    }
    public void exportExcelT100201() throws UnsupportedEncodingException, ParseException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "工具出入库查询";//文件名T1004
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	titleNames.add("工具编码");
    	titleNames.add("工具名称");
    	titleNames.add("工具有效期");
    	titleNames.add("仓库编码");
    	titleNames.add("仓库名称");
    	titleNames.add("工单编码");
    	titleNames.add("工单名称");
//    	titleNames.add("工具状态");
    	titleNames.add("出库/入库");
    	titleNames.add("出入库时间");
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getRailWhseTool(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName,3);
    	log.info("导出 "+fileName+".xls 成功");
    }

    public void exportExcelT100301() throws UnsupportedEncodingException, ParseException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "工具下限预警";//文件名T1003
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	titleNames.add("仓库编码");
    	titleNames.add("仓库名称");
    	titleNames.add("工具名称");
    	titleNames.add("可用工具数量");
    	titleNames.add("下限预警值");
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getRailWhseToolWarntongji(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName,3);
    	log.info("导出 "+fileName+".xls 成功");
    }

    
    public void exportExcelT100300() throws UnsupportedEncodingException, ParseException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "工具下限预警阈值";//文件名T1004
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	titleNames.add("仓库编码");
    	titleNames.add("仓库名称");
    	titleNames.add("工具名称编码");
    	titleNames.add("工具名称");
    	titleNames.add("下限预警阈值");
    	titleNames.add("创建人");
    	titleNames.add("创建时间");
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getRailWhseToolWarn(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName,3);
    	log.info("导出 "+fileName+".xls 成功");
    }

}



