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
public class T13000ExcelExportAction extends ActionSupport {
	private Log log = LogFactory.getLog(this.getClass());
	/**
	 * 
	 * @Description: 
	 * @param @throws UnsupportedEncodingException   
	 * @return void  
	 * @author syl
	 * @date 2019年4月25日
	 */
    public void exportExcelT130101() throws UnsupportedEncodingException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "作业单元管理";//文件名T1301
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	titleNames.add("工单编码");
    	titleNames.add("调度号");
    	titleNames.add("工单名称");
    	titleNames.add("工单状态");
    	titleNames.add("班组");
    	titleNames.add("人员编码");
    	titleNames.add("人员名称");
    	titleNames.add("开门权限");
    	titleNames.add("人员状态");
    	titleNames.add("添加时间");
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getWorkEmployees(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName);
    	log.info("导出 "+fileName+".xls 成功");
	}
    public void exportExcelT130102() throws UnsupportedEncodingException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "作业工具管理";//文件名T1303
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	titleNames.add("工单编码");
    	titleNames.add("调度号");
    	titleNames.add("工单名称");
    	titleNames.add("工单状态");
    	titleNames.add("工具类型");
    	titleNames.add("工具名称");
    	titleNames.add("工具型号");
    	titleNames.add("工具状态");
    	titleNames.add("是否在库");
    	titleNames.add("当前状态");
    	titleNames.add("添加时间");
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getWorkTools(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName);
    	log.info("导出 "+fileName+".xls 成功");
    }
    public void exportExcelT130103() throws UnsupportedEncodingException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "工单审核";//文件名T1303
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	titleNames.add("工单编码");
    	titleNames.add("调度号");
    	titleNames.add("工单名称");
    	titleNames.add("维修类型");
    	titleNames.add("站/区段");
    	titleNames.add("负责人");
    	titleNames.add("施工作业天窗开始");
    	titleNames.add("施工作业天窗结束");
    	titleNames.add("工单状态");
    	titleNames.add("作业开始时间");
    	titleNames.add("作业结束时间");
    	titleNames.add("审核状态");
    	titleNames.add("审核意见");
    	titleNames.add("审核时间");
    	titleNames.add("审核人");
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getWorkInfo(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName);
    	log.info("导出 "+fileName+".xls 成功");
    }
    public void exportExcelT130104() throws UnsupportedEncodingException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "工单信息维护";//文件名T1303
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	titleNames.add("工单编码");
    	titleNames.add("调度号");
    	titleNames.add("工单名称");
    	titleNames.add("维修类型");
    	titleNames.add("站/区段");
    	titleNames.add("负责人");
    	titleNames.add("施工作业天窗开始");
    	titleNames.add("施工作业天窗结束");
    	titleNames.add("工单状态");
    	titleNames.add("作业开始时间");
    	titleNames.add("作业结束时间");
    	titleNames.add("审核状态");
    	titleNames.add("审核意见");
    	titleNames.add("审核时间");
    	titleNames.add("审核人");
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getWorkInfo(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName);
    	log.info("导出 "+fileName+".xls 成功");
    }
    public void exportExcelT130105() throws UnsupportedEncodingException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "工单信息查询";//文件名T1303
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	titleNames.add("工单编码");
    	titleNames.add("调度号");
    	titleNames.add("工单名称");
    	titleNames.add("维修类型");
    	titleNames.add("站/区段");
    	titleNames.add("负责人");
    	titleNames.add("施工作业天窗开始");
    	titleNames.add("施工作业天窗结束");
    	titleNames.add("工单状态");
    	titleNames.add("作业开始时间");
    	titleNames.add("作业结束时间");
    	titleNames.add("审核状态");
    	titleNames.add("审核意见");
    	titleNames.add("审核时间");
    	titleNames.add("审核人");
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getWorkInfo(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName);
    	log.info("导出 "+fileName+".xls 成功");
    }
    public void exportExcelT130200() throws UnsupportedEncodingException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "作业现场图片";//文件名T1304
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
//    	titleNames.add("标识");
    	titleNames.add("工单编码");
    	titleNames.add("工单名称");
    	titleNames.add("类型");
    	titleNames.add("图片描述");
    	titleNames.add("创建人");
    	titleNames.add("创建时间");
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getWorkImgs(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName);
    	log.info("导出 "+fileName+".xls 成功");
    }
    public void exportExcelT130201() throws UnsupportedEncodingException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "作业现场视频";//文件名T1304
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
//    	titleNames.add("标识");
    	titleNames.add("工单编码");
    	titleNames.add("工单名称");
    	titleNames.add("类型");
    	titleNames.add("视频描述");
    	titleNames.add("创建人");
    	titleNames.add("创建时间");
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getWorkImgs(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName);
    	log.info("导出 "+fileName+".xls 成功");
    }
    public void exportExcelT130300() throws UnsupportedEncodingException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "进门信息查询";//文件名T1304
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	
//    	titleNames.add("标识");
    	titleNames.add("作业门编码");
    	titleNames.add("作业门名称");
    	titleNames.add("进门时间");
    	titleNames.add("计划号");
    	titleNames.add("调度号");
    	titleNames.add("工单名称");
    	
    	titleNames.add("负责人");
    	titleNames.add("负责人电话");
    	titleNames.add("当前状态");
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getAccessEmployees(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName);
    	log.info("导出 "+fileName+".xls 成功");
    }
    public void exportExcelT130301() throws UnsupportedEncodingException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "出门信息查询";//文件名T1304
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	
//    	titleNames.add("标识");
    	titleNames.add("作业门编码");
    	titleNames.add("作业门名称");
    	titleNames.add("出门时间");
    	titleNames.add("计划号");
    	titleNames.add("调度号");
    	titleNames.add("工单名称");
    	
    	titleNames.add("负责人");
    	titleNames.add("负责人电话");
    	titleNames.add("当前状态");
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getAccessEmployees(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName);
    	log.info("导出 "+fileName+".xls 成功");
    }
    public void exportExcelT130401() throws UnsupportedEncodingException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "人员信息查询";//文件名T1304
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	titleNames.add("人员编码");
    	titleNames.add("人员名称");
//    	titleNames.add("门禁名称");
    	titleNames.add("性别");
    	titleNames.add("年龄");
    	titleNames.add("身份证号");
//    	titleNames.add("预警内容");
    	
    	titleNames.add("照片存放地址");
    	titleNames.add("作业单位");
    	titleNames.add("职务");
    	titleNames.add("人员类型");
    	titleNames.add("联系方式");
    	titleNames.add("所属机构");
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getEmployees(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName);
    	log.info("导出 "+fileName+".xls 成功");
    }
    /**
     * 
	 * @return Object[]
     * @param @throws UnsupportedEncodingException   
     * @return void  
     * @author syl
     * @date 2019年4月24日
     */
    public void exportExcelT130402() throws UnsupportedEncodingException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "人员信息维护";//文件名T1304
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	titleNames.add("人员编码");
    	titleNames.add("人员名称");
//    	titleNames.add("门禁名称");
    	titleNames.add("性别");
    	titleNames.add("年龄");
    	titleNames.add("身份证号");
//    	titleNames.add("预警内容");
    	
    	titleNames.add("照片存放地址");
    	titleNames.add("作业单位");
    	titleNames.add("职务");
    	titleNames.add("人员类型");
    	titleNames.add("联系方式");
    	titleNames.add("所属机构");
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getEmployees(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName);
    	log.info("导出 "+fileName+".xls 成功");
    }
    public void exportExcelT130403() throws UnsupportedEncodingException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "班组管理";//文件名T1304
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	
    	titleNames.add("班组名称");
    	titleNames.add("所属机构");
    	titleNames.add("班组人数");
    	titleNames.add("班组状态");
    	
    	titleNames.add("创建人");
    	titleNames.add("创建时间");
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getTeams(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName);
    	log.info("导出 "+fileName+".xls 成功");
    }
    public void exportExcelT130404() throws UnsupportedEncodingException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "班组管理";//文件名T1304
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	
    	titleNames.add("人员类型名称");
    	titleNames.add("人员类型状态");
    	titleNames.add("创建人");
    	titleNames.add("创建时间");
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getEmployeeType(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName);
    	log.info("导出 "+fileName+".xls 成功");
    }
    public void exportExcelT130405() throws UnsupportedEncodingException, ParseException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "巡护中队管理";//文件名T1304
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	
    	titleNames.add("巡护中队编码");
    	titleNames.add("巡护中队名称");
    	titleNames.add("添加时间");
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getPatrols(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName);
    	log.info("导出 "+fileName+".xls 成功");
    }
    public void exportExcelT130500() throws UnsupportedEncodingException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "工单预警";//文件名T1304
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
//    	titleNames.add("标识");
    	titleNames.add("工单编码");
    	titleNames.add("工单名称");
    	titleNames.add("预警类型");
    	titleNames.add("预警内容");
    	titleNames.add("是否确认");
    	titleNames.add("预警时间");
    	titleNames.add("操作备注");
    	titleNames.add("操作员");
    	titleNames.add("操作时间");
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getRailWorkWarn(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName);
    	log.info("导出 "+fileName+".xls 成功");
    }
    
  
}



