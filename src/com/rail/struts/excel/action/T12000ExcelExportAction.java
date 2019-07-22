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
public class T12000ExcelExportAction extends ActionSupport {
	private Log log = LogFactory.getLog(this.getClass());
	/**
	 * 
	 * @Description: 
	 * @param @throws UnsupportedEncodingException   
	 * @return void  
	 * @author syl
	 * @date 2019年4月25日
	 */
    public void exportExcelT120101() throws UnsupportedEncodingException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "门禁信息";//文件名T1201
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	titleNames.add("门禁编码");
    	titleNames.add("门禁名称");
    	titleNames.add("门禁类型");
    	titleNames.add("配套设备编码");
    	titleNames.add("线路名称");
    	titleNames.add("门禁位置");
    	titleNames.add("所属机构");
    	titleNames.add("负责人");
    	titleNames.add("联系电话");
    	titleNames.add("所属派出所");
    	
    	titleNames.add("检修周期（天）");
    	titleNames.add("最后一次检修时间");
    	titleNames.add("使用状态");
    	titleNames.add("设备状态");
    	titleNames.add("当前状态");
//    	titleNames.add("平台 预警");
//    	titleNames.add("微信预警 ");
    	titleNames.add("通道门里程 ");
    	titleNames.add("与上一通道门距离");
    	titleNames.add("与下一通道门距离 ");
    	titleNames.add("安装时间 ");
    	titleNames.add("地理经度");
    	titleNames.add("地理纬度");
    	titleNames.add("仓库编码");
    	titleNames.add("创建人");
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getRailAccessInfo(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName,1);
    	log.info("导出 "+fileName+".xls 成功");
	}
    public void exportExcelT120103() throws UnsupportedEncodingException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "终端设备信息";//文件名T1203
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	titleNames.add("门禁编码");
    	titleNames.add("终端设备编码");
    	titleNames.add("终端设备名称");
    	titleNames.add("设备类型");
    	titleNames.add("设备IP");
    	titleNames.add("用户名");
    	titleNames.add("密码");
//    	titleNames.add("门禁位置");
//    	titleNames.add("网络地址");
//    	titleNames.add("供应商");
//    	titleNames.add("供应商电话");
//    	titleNames.add("设备有效期");
    	
    	titleNames.add("安装时间");
//    	titleNames.add("检修周期（天）");
//    	titleNames.add("最后一次检修时间");
    	titleNames.add("设备状态");
    	titleNames.add("创建人");
    	titleNames.add("创建时间");
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getRailAccessEquipInfo(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName,2);
    	log.info("导出 "+fileName+".xls 成功");
    }
    public void exportExcelT1203() throws UnsupportedEncodingException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "门禁预警记录";//文件名T1204
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	titleNames.add("门禁编码");
    	titleNames.add("门禁名称");
    	titleNames.add("预警类型");
    	titleNames.add("预警内容");
    	titleNames.add("是否确认");
    	titleNames.add("预警时间");
    	
    	titleNames.add("操作备注");
    	titleNames.add("操作员");
    	titleNames.add("操作时间");
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getRailAccessWarn(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName,1);
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
    public void exportExcelT120401() throws UnsupportedEncodingException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "门禁检修保养预警记录";//文件名T1204
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	titleNames.add("门禁编码");
    	titleNames.add("门禁名称");
//    	titleNames.add("设备编码");
//    	titleNames.add("设备名称");
    	titleNames.add("预警内容");
    	titleNames.add("是否确认");
    	
    	titleNames.add("预警时间");
    	titleNames.add("操作备注");
    	titleNames.add("操作员");
    	titleNames.add("操作时间");
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getRailAccessMaintainWarn(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName,1);
    	log.info("导出 "+fileName+".xls 成功");
    }
    public void exportExcelT120402() throws UnsupportedEncodingException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "门禁检修保养记录";//文件名T1204
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	titleNames.add("门禁编码");
    	titleNames.add("门禁名称");
    	titleNames.add("线路名称");
    	titleNames.add("门禁位置");
    	titleNames.add("门禁负责人");
    	titleNames.add("联系电话");
    	titleNames.add("保养时间");
    	titleNames.add("保养人");
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getRailAccessMaintain(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName,1);
    	log.info("导出 "+fileName+".xls 成功");
    }
  
    /**
	 * @Description: 得到工具调库列表 120501
	 * @param @param begin
	 * @param @param request  whseCode 仓库编码 dateStart开始时间 dateEnd结束时见   infoSign预警状态
	 * @param @return仓库名称、工具名称、标签号、存放位置（架层位）、移库前仓库、移库事由、移库类型、移库人、移库时间
	 * @return Object[]
	 * @throws
	 */
    public void exportExcelT120501() throws UnsupportedEncodingException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "门禁开记录";//文件名T1205
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	titleNames.add("门禁编码");
    	titleNames.add("门禁名称");
    	
    	titleNames.add("员工编码");
    	titleNames.add("员工名称");
    	titleNames.add("工单编码");
    	titleNames.add("工单名称");
    	titleNames.add("出入类型");
    	titleNames.add("开启形式");
    	titleNames.add("创建时间");
    	titleNames.add("操作员");
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getRailAccessOptlog(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName,1);
    	log.info("导出 "+fileName+".xls 成功");
    }
    public void exportExcelT120502() throws UnsupportedEncodingException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "门禁关记录";//文件名T1205
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	titleNames.add("门禁编码");
    	titleNames.add("门禁名称");
    	titleNames.add("设备编码");
    	titleNames.add("设备名称");
    	
    	titleNames.add("员工标识");
    	titleNames.add("员工名称");
    	titleNames.add("工单标识");
    	titleNames.add("工单名称");
    	titleNames.add("出入类型");
    	titleNames.add("开启状态");
    	titleNames.add("创建时间");
    	titleNames.add("创建人");
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getRailAccessOptlog(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName,1);
    	log.info("导出 "+fileName+".xls 成功");
    }
}



