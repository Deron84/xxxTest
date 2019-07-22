package com.rail.struts.tools.action;

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
public class T11000ExcelExportAction extends ActionSupport {
	private Log log = LogFactory.getLog(this.getClass());
	/**
	 * 
	 * @Description: 
	 * @param @throws UnsupportedEncodingException   
	 * @return void  
	 * @author syl
	 * @date 2019年4月25日
	 * 仓库名称、工具名称、工具标签、工具类型、材质、单位、存放位置（架层位）、保养周期、最后保养时间、有效期、入库时间、工具状态、在库状态
	 */
    public void exportExcelT1101() throws UnsupportedEncodingException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "工具基础信息";//文件名T1101
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	titleNames.add("仓库名称");
    	titleNames.add("工具名称");
    	titleNames.add("工具标签");
    	titleNames.add("工具类型");
    	titleNames.add("材质");
    	titleNames.add("单位");
    	titleNames.add("存放位置（架层位）");
    	titleNames.add("保养周期(天)");
    	titleNames.add("最后保养时间");
    	titleNames.add("有效期");
    	titleNames.add("入库时间");
    	titleNames.add("工具状态");
    	titleNames.add("在库状态");
    	titleNames.add("工具型号");
    	titleNames.add("采购部门");
    	titleNames.add("采购人");
    	titleNames.add("供应商");
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getToolInfofix(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName,1);
    	log.info("导出 "+fileName+".xls 成功");
	}
    /**
	 * @Description: 得到工具信息列表 110102/110103
	 * @param @param begin
	 * @param @param request  whseCode 仓库编码 dateStart入库开始时间 dateEnd入库结束时间   toolStatus工具状态
	 * @param @return 0工具编码、1工具标签、2工具分类、3工具名称、4工具型号、
	 * 5材质、6入库时间、7工具状态、8、有效期、9、出库状态、10、最后一次保养时间
	 * @return Object[]
	 * @throws
	 */
    public void exportExcelT1102() throws UnsupportedEncodingException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "工具出入库记录";//文件名T1102
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	titleNames.add("工具编码 ");
    	titleNames.add("工具标签");
    	titleNames.add("工具分类");
    	titleNames.add("工具名称");
    	titleNames.add("工具型号");
    	titleNames.add("材质");
    	titleNames.add("入库时间");
    	titleNames.add("工具状态");
    	titleNames.add("有效期");
    	titleNames.add("出库状态");
    	titleNames.add("最后一次保养时间");
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getToolInfo(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName,0);
    	log.info("导出 "+fileName+".xls 成功");
    }
    public void exportExcelT1103() throws UnsupportedEncodingException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "工具信息出入库记录";//文件名T1103
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	titleNames.add("工具编码 ");
    	titleNames.add("工具标签");
    	titleNames.add("工具分类");
    	titleNames.add("工具名称");
    	titleNames.add("工具型号");
    	titleNames.add("材质");
    	titleNames.add("入库时间");
    	titleNames.add("工具状态");
    	titleNames.add("有效期");
    	titleNames.add("出库状态");
    	titleNames.add("最后一次保养时间");
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getToolInfo(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName,0);
    	log.info("导出 "+fileName+".xls 成功");
    }
    /**
     * 
     * @Description: whseCode 仓库编码 dateStart维修申请开始时间 dateEnd维修申请结束时间   infoSign审核状态
	 * @param @return 仓库名称、工具名称、工具标签、存放位置（架层位）、维修信息、申请人、申请时间、审核状态、审核人、审核时间、审核信息
【同报废，审核通过之后变成维修】
	 * @return Object[]
     * @param @throws UnsupportedEncodingException   
     * @return void  
     * @author syl
     * @date 2019年4月24日
     */
    public void exportExcelT1104() throws UnsupportedEncodingException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "工具维修记录";//文件名T1104
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	titleNames.add("仓库名称");
    	titleNames.add("工具名称");
    	titleNames.add("工具标签");
    	titleNames.add("存放位置（架层位）");
    	titleNames.add("维修信息");
    	titleNames.add("申请人");
    	titleNames.add("申请时间");
    	titleNames.add("审核信息");
    	titleNames.add("审核状态");
    	titleNames.add("审核人");
    	titleNames.add("审核时间");
    	
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getToolRepair(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName,1);
    	log.info("导出 "+fileName+".xls 成功");
    }
    /**
	 * @Description: 得到工具调库列表 110501
	 * @param @param begin
	 * @param @param request  whseCode 仓库编码 dateStart开始时间 dateEnd结束时见   infoSign预警状态
	 * @param @return仓库名称、工具名称、工具标签、存放位置（架层位）、移库前仓库、移库事由、移库类型、移库人、移库时间
	 * @return Object[]
	 * @throws
	 */
    public void exportExcelT1105() throws UnsupportedEncodingException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "工具移库记录";//文件名T1105
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	titleNames.add("仓库名称");
    	titleNames.add("工具名称");
    	titleNames.add("工具标签");
    	titleNames.add("存放位置（架层位）");
    	titleNames.add("移库前仓库");
    	titleNames.add("移库事由");
    	titleNames.add("移库类型");
    	titleNames.add("移库人");
    	titleNames.add("移库时间");
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getToolTransfer(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName,1);
    	log.info("导出 "+fileName+".xls 成功");
    }
    /**
	 * @Description: 得到工具报废列表 110602/110603
	 * @param @param begin
	 * @param @param request  whseCode 仓库编码 dateStart报废申请开始时间 dateEnd报废申请结束时间   infoSign审核状态
	 * @param @return仓库名称、工具名称、工具标签、存放位置（架层位）、报废信息、申请人、申请时间、审核状态、审核人、审核时间、审核信息
	 * @return Object[]
	 * @throws
	 */
    public void exportExcelT1106() throws UnsupportedEncodingException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "工具报废记录";//文件名T1106
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	titleNames.add("仓库名称");
    	titleNames.add("工具名称");
    	titleNames.add("工具标签");
    	titleNames.add("存放位置（架层位）");
    	titleNames.add("报废信息");
    	titleNames.add("申请人");
    	titleNames.add("申请时间");
    	titleNames.add("审核信息");
    	titleNames.add("审核状态");
    	titleNames.add("审核人");
    	titleNames.add("审核时间");
    	
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getToolScrap(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName,1);
    	log.info("导出 "+fileName+".xls 成功");
    }
    /**
	 * @Description: 得到工具预警列表 110701
	 * @param @param begin
	 * @param @param request  whseCode 仓库编码 dateStart开始时间 dateEnd结束时见   infoSign预警状态
	 * @param @return 仓库名称、工具名称、工具标签、存放位置（架层位）、预警内容、是否确认、审核人、审核时间
	 * @return Object[]
	 * @throws
	 */
    public void exportExcelT1107() throws UnsupportedEncodingException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "工具预警记录";//文件名T1107
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	titleNames.add("仓库名称");
    	titleNames.add("工具名称");
    	titleNames.add("工具标签");
    	titleNames.add("存放位置（架层位）");
    	titleNames.add("预警信息");
    	titleNames.add("是否确认");
    	titleNames.add("操作备注");
    	titleNames.add("操作员");
    	titleNames.add("操作时间");
    	titleNames.add("预警类型");
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getToolMaintainWarnning(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName,1);
    	log.info("导出 "+fileName+".xls 成功");
    }
    /**
	 * @Description: 得到工具保养保养列表 110802
	 * @param @param begin
	 * @param @param request  whseCode 仓库编码 dateStart开始时间 dateEnd结束时间
	 * @param @return 仓库名称、工具名称、工具标签、存放位置（架层位）、入库时间、保养周期、保养人、保养时间 -->
	 * @return Object[]
	 * @throws
	 */
    public void exportExcelT1108() throws UnsupportedEncodingException{
    	HttpServletRequest request =  ServletActionContext.getRequest() ;
    	HttpServletResponse response = ServletActionContext.getResponse();
    	String fileName = "工具保养记录";//文件名T1108
    	List<String> titleNames = new ArrayList<String>();// 列名称
    	List<Object[]> dataList = new ArrayList<Object[]>(); //数据严格按照列的名称对应
    	int totalCount;
    	titleNames.add("仓库名称");
    	titleNames.add("工具名称");
    	titleNames.add("工具标签");
    	titleNames.add("存放位置（架层位）");
    	titleNames.add("入库时间");
    	titleNames.add("保养周期(天)");
    	titleNames.add("保养人");
    	titleNames.add("保养时间 ");
    	GridConfigMethod configMethod =  (GridConfigMethod) ContextUtil.getBean("GridConfigMethod");
    	Object[]  data  = configMethod.getToolMaintains(-1, request);
    	dataList = (List<Object[]>)data[0];
    	totalCount = Integer.valueOf((String)data[1]);
    	PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,fileName,1);
    	log.info("导出 "+fileName+".xls 成功");
    }
    
}



