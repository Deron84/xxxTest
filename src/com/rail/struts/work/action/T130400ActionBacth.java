package com.rail.struts.work.action;

import com.alibaba.fastjson.JSONObject;
import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.FileUploadPathUtil;
import com.huateng.system.util.TaskInfoUtil;
import com.rail.bo.enums.InfoSignInOutEnums;
import com.rail.bo.pub.*;
import com.rail.bo.tools.T110100BO;
import com.rail.bo.work.T130400BO;
import com.rail.po.base.RailEmployee;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 人员维护
 * @author qiufulon
 *
 */
public class T130400ActionBacth extends BaseAction {
	
	private Log log = LogFactory.getLog(this.getClass());
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private static final String imgPath = FileUploadPathUtil.getTxnInfo("eployeeImgs");//"/upload/img/employeeHeadImg/";
	
	private static final long serialVersionUID = 1L;
	private long id;
	private String employeeCode;
	private String employeeName;
	private String sex;
	private Date birthday;
	private String idNumber;
	private String employeeImg;
	private long constOrg;
	private String job;
	private long employeeType;
	private String employeeTel;
	private String infoSign;
	private Date addDate;
	private String addUser;
	private Date updDate;
	private String updUser;
	private String note1;
	private String note2;
	private String note3;
	private String password;
	private Date entryDate;
	private String dept;
	
	private String detpName;
	private String workStatus;
	
	private List<File> files; 
	private String Filename; 
	/*private Object Upload; */
	
	//syl  excel导入上传
	private Map result;
	
	private List<File> upload; 
	private List<File> upload22; 
    // 上传文件名  
    private String[] uploadFileName; 
    // 上传文件的MIME类型  
    private String[] uploadContentType;  
	
	//人员信息维护
	private T130400BO t130400BO = (T130400BO) ContextUtil.getBean("T130400BO");
	private T110100BO t110100BO = (T110100BO) ContextUtil.getBean("T110100BO");
	public T130400BO getT130400BO() {
		return t130400BO;
	}
	public void setT130400BO(T130400BO t130400bo) {
		t130400BO = t130400bo;
	}
		
	@Override
	protected String subExecute() throws Exception {
		System.out.println(getMethod()+"  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		try {
			 if ("open".equals(getMethod())) {
	                rspCode = open();
	            }else if("close".equals(getMethod())){
	            	rspCode = close();
	            }else if("edit".equals(getMethod())){
	            	rspCode = edit();
	            }else if("add".equals(getMethod())){
	            	rspCode = add();
	            }else if("delete".equals(getMethod())){
	            	rspCode = delete();
	            }
        } catch (Exception e) {
        	//e.printStackTrace();
            log("操作员：" + operator.getOprId() + "，对人员进行：" + getMethod() + "，失败，失败原因为：" + e);
        }
        return rspCode;
	}
	
	/**
	 * 获得单个人员信息
	 * @Description: TODO
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public String getData() {
		System.out.println("----------------获得单个人员信息-----------------");
        try {
        	RailEmployee railEmployee = t130400BO.getByCode(getEmployeeCode());
            Map<String, Object> midMap = new HashMap<String, Object>();
            midMap.put("success", true);
            midMap.put("msg", railEmployee);
            String str = JSONObject.toJSONString(midMap);//.fromObject(midMap).toString();
            PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
            printWriter.write(str);
            printWriter.flush();
            printWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }
	
	/**
	 * 新增人员
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception{
		System.out.println("----------------新增人员-----------------");
		Map<String, String> params = new HashMap<String, String>();
		params.put("EMPLOYEE_CODE", getEmployeeCode());
		List<RailEmployee> rcoInfos = t130400BO.getByParam(params);
		if(rcoInfos != null && rcoInfos.size() > 0){
			Map<String, Object> errorMap = new HashMap<String, Object>();
			errorMap.put("success", false);
			errorMap.put("msg", "人员编码已存在！");
            String str = JSONObject.toJSONString(errorMap);
            PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
            printWriter.write(str);
            printWriter.flush();
            printWriter.close();
            return null;
		}
		
		
		operator = (Operator) getSessionAttribute(Constants.OPERATOR_INFO);
		log.info("上传图片开始");
		String imgPath = "";
		String format = sdf.format(new Date());
    	String suffix = null;
        String fileName = null;
        String fileName2 = null;
		if(null!=upload22){
			for (int i = 0; i < upload22.size(); i++) {
				File myfile=upload22.get(i);
//				String myfileName=uploadFileName[i];
//				String myfileType=uploadContentType[i];
				if(myfile.exists()){
					log.info("--> File Length: " + myfile.length());
//	                log.info("--> File Tyep: " + myfileType);
	                log.info("--> File Name: " + myfile.getName());
//	                log.info("--> File Original: " + myfileName);
	                log.info("========================================");
//					String realPath = ServletActionContext.getServletContext().getRealPath("/upload/" + format);
					
	                suffix = TaskInfoUtil.getTxnInfo("suffix");
	                
					String uploadPath = FileUploadPathUtil.getTxnInfo("eployeeImgs");//=/uploads/img/employeeHead...
					String projectName = ServletActionContext.getServletContext().getContextPath();
	                log.info("projectName --> " + projectName);
					String realPath = ServletActionContext.getServletContext().getRealPath(uploadPath);
					realPath = realPath.substring(0, realPath.length() - projectName.length() -uploadPath.length());
	                realPath = realPath +uploadPath;
	                log.info("realPath --> " + realPath);
					
					
	                log.info("--> " + realPath);
	                File dir = new File(realPath);
	                if (dir.exists()) {
	                	System.out.println("file exists");
	                } else {
	                	System.out.println("file not exists, create it ...");
	                	try {
	                        dir.mkdirs();
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                    }    
	                } 
	                FileInputStream inputStream=new FileInputStream(myfile);
//	                int lastIndexOf = myfileName.lastIndexOf(".");
//	                suffix = myfileName.substring(lastIndexOf + 1, myfileName.length());
	                fileName2 = UUID.randomUUID().toString().replaceAll("-", "");
	                
	                fileName = fileName2 + "." + suffix;
//	                log.info("--> File Suffix Name: " + fileName + "---- old Name: " + myfileName);
	                File file = new File(realPath, fileName);
	                FileOutputStream fos = new FileOutputStream(file);
	                int buffer = 1024; //定义缓冲区的大小
	                int length = 0;
	                byte[] b = new byte[buffer];
	                while ((length = inputStream.read(b)) != -1) {
	                    fos.write(b, 0, length); //向文件输出流写读取的数
	                }
	                fos.close();
	                
	                imgPath = uploadPath+"/"+fileName;
	                b = null;
	                file = null;
	                inputStream = null;
	                fos = null;
				}
			}
			log.info("上传图片成功");
		}else{
			log.info("上传图片未成功");
		}
		
		
		
		RailEmployee railEmployee = new RailEmployee();
		railEmployee.setEmployeeCode(getEmployeeCode());
		railEmployee.setEmployeeName(getEmployeeName());
		railEmployee.setSex(getSex());
		railEmployee.setBirthday(getBirthday());
		railEmployee.setIdNumber(getIdNumber());
		railEmployee.setEmployeeImg(imgPath);
		railEmployee.setConstOrg(getConstOrg());
		railEmployee.setJob(getJob());
		railEmployee.setEmployeeType(getEmployeeType());
		railEmployee.setEmployeeTel(getEmployeeTel());
		railEmployee.setInfoSign("0");
		railEmployee.setDept(getDept());
		railEmployee.setAddUser(operator.getOprId());
		railEmployee.setAddDate(new Date());
		railEmployee.setUpdUser(operator.getOprId());
		railEmployee.setUpdDate(new Date());
		
		String rCode = t130400BO.add(railEmployee);
		String msg = "添加人员成功";
		boolean success = true;
		if(!rCode.equals(Constants.SUCCESS_CODE)){
			msg = "添加人员失败";
			success = false;
		}
		Map<String, Object> midMap = new HashMap<String, Object>();
        midMap.put("success", success);
        midMap.put("msg", msg);
        String str = JSONObject.toJSONString(midMap);
        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
        printWriter.write(str);
        printWriter.flush();
        printWriter.close();
		log("添加人员成功。操作员编号：" + operator.getOprId());
        return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 修改人员
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception{
		System.out.println("----------------修改人员-----------------");
		RailEmployee railEmployee = t130400BO.getByCode(getEmployeeCode());
		if(railEmployee == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该人员");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}
		railEmployee.setEmployeeCode(getEmployeeCode());
		railEmployee.setEmployeeName(getEmployeeName());
		railEmployee.setSex(getSex());
		railEmployee.setBirthday(getBirthday());
		railEmployee.setIdNumber(getIdNumber());
//		railEmployee.setEmployeeImg(getEmployeeImg());
		railEmployee.setConstOrg(getConstOrg());
		railEmployee.setJob(getJob());
		railEmployee.setEmployeeType(getEmployeeType());
		railEmployee.setEmployeeTel(getEmployeeTel());
		railEmployee.setInfoSign(getInfoSign());
		railEmployee.setDept(getDept());
		railEmployee.setUpdUser(operator.getOprId());
		railEmployee.setUpdDate(new Date());
        String rcode =t130400BO.update(railEmployee);
		
        String msg = "操作失败";
		boolean success = false;
		if(rcode.equals(Constants.SUCCESS_CODE)){
			success=true;
			msg = "操作成功";
		}else{
			msg = "操作失败";
			success = false;
		}
		Map<String, Object> midMap = new HashMap<String, Object>();
        midMap.put("success", success);
        midMap.put("msg", msg);
        String str = JSONObject.toJSONString(midMap);
        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
        printWriter.write(str);
        printWriter.flush();
        printWriter.close();
        
		log("更新人员信息成功。操作员编号：" + operator.getOprId());
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 删除人员
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception{
		System.out.println("----------------删除人员-----------------");
		RailEmployee railEmployee =  t130400BO.getByCode(getEmployeeCode());
		if(railEmployee == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该人员");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}
       // railEmployee.setDelStatus("1");
		railEmployee.setUpdUser(operator.getOprId());
		railEmployee.setUpdDate(new Date());
		  // String rcode =t130400BO.update(railEmployee);
		   String rcode =t130400BO.delete(railEmployee);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(rcode);
	        printWriter.flush();
	        printWriter.close();
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 启用人员
	 * @return
	 */
	public String open() throws Exception {
		System.out.println("----------------启用人员-----------------");
		RailEmployee railEmployee =  t130400BO.getByCode(String.valueOf(getId()));
		if(railEmployee == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该人员");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}
//		railEmployee.setEnableStatus("0");
		railEmployee.setUpdUser(operator.getOprId());
		railEmployee.setUpdDate(new Date());
		   String rcode =t130400BO.update(railEmployee);
			
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(rcode);
	        printWriter.flush();
	        printWriter.close();
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 停用人员
	 * @return
	 */
	public String close() throws Exception {
		System.out.println("----------------停用人员-----------------");
		RailEmployee railEmployee =  t130400BO.getByCode(String.valueOf(getId()));
		if(railEmployee == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该人员");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}
//		railEmployee.setEnableStatus("1");
		railEmployee.setUpdUser(operator.getOprId());
		railEmployee.setUpdDate(new Date());
		   String rcode =t130400BO.update(railEmployee);
			
		   PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(rcode);
	        printWriter.flush();
	        printWriter.close();
		return Constants.SUCCESS_CODE;
	}
	/**
	 * 
	 * @Description: 人员信息批量倒入
	 * @param @return
	 * @param @throws Exception   
	 * @return String  
	 * @author syl
	 * @date 2019年5月4日
	 */
//	public String excelImport() {
//		try {
//			operator = (Operator) getSessionAttribute(Constants.OPERATOR_INFO);
//			List<RailEmployee> employees = new ArrayList<RailEmployee>();
//
//			log.info("上传excel");
//			//String imgPath = "";
//			String format = sdf.format(new Date());
//	    	String suffix = null;
//	        String fileName = null;
//	        String fileName2 = null;
//			if(null!=upload){
//				for (int i = 0; i < upload.size(); i++) {
//					File myfile=upload.get(i);
//					String myfileName=uploadFileName[i];
//					String myfileType=uploadContentType[i];
//					if(myfile.exists()){
//						log.info("--> File Length: " + myfile.length());
//		                log.info("--> File Tyep: " + myfileType);
//		                log.info("--> File Name: " + myfile.getName());
//		                log.info("--> File Original: " + myfileName);
//		                log.info("========================================");
//		                FileInputStream inputStream =  new FileInputStream(myfile);
//
//		        		HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
//		        		HSSFSheet hssfSheet = workbook.getSheetAt(0);
//		        		HSSFRow row = hssfSheet.getRow(0);//Row 每一行就是一个对象
//		        		for(int ii = 1;ii<hssfSheet.getLastRowNum()+1;ii++){
//		        			log.info("一共"+hssfSheet.getLastRowNum()+"行");
//		        			row = hssfSheet.getRow(ii);
//		        			if(row == null ){
//		        				continue;
//		        			}
//		        			log.info(row);
//		        			RailEmployee employee = new RailEmployee();
//		        			for(int j =0;j<row.getLastCellNum();j++){
//		        		        HSSFCell cell = row.getCell((short)j);//Cell
//		        		        String s = null;
//		        		        if(cell!=null){
//		        		        	s =  PubExcelReadUtill.getCellContent(cell);//读取单元格String内容
//		        		        	System.out.println("========index======"+j+":"+s);
//		        		        }else{
//		        		        	s = null;
//		        		        }
//		        		     // 0 人员编码	1 人员姓名	2 1、男；2、女	3 出生日期	4 身份证号	5 照片存放地址  6 	职务	 7 联系方式	 9	入职日期
//		        		        if(j==0){
//		        		        	employee.setEmployeeCode(s);
//		        		        }else if(j==1){
//		        		        	employee.setEmployeeName(s);
//		        		        }else if(j==2){
//		        		        	employee.setSex(s);
//		        		        }else if(j==3){
//		        		        	if(s!=null){
//		        		        		Date d = sdf.parse(s);
//		        		        		employee.setBirthday(d);
//		        		        	}
//		        		        }else if(j==4){
//		        		        	employee.setIdNumber(s);
//		        		        }else if(j==5){
////		        		        	 suffix = TaskInfoUtil.getTxnInfo("suffix");
//		        		        	if (isNotEmpty(s)) {
//		        		        		employee.setEmployeeImg(imgPath+"/"+s);
//									}
//		        		        }else if(j==6){
//		        		        	employee.setJob(s);
//		        		        }else if(j==7){
//		        		        	employee.setEmployeeTel(s);
////		        		        }else if(j==8){
////		        		        	if(s!=null){
////		        		        		Date d = sdf.parse(s);
////		        		        		employee.setEntryDate(d);
////		        		        	}
//		        		        }
//		        			}
//		        			employee.setDept(operator.getOprBrhId());
//		        			employees.add(employee);
//
//		        		}
//
//					}
//				}
//
//				log.info("文件接收成功");
//				log("批量添加员工成功。操作员编号：" + operator.getOprId());
//			}else{
//				log.error("文件未上传");
//			}
//			Date date = new Date();
//			result = t130400BO.addListFromExcel(employees);
//	        //String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//
//		} catch (Exception e) {
//			result = ResultUtils.fail(e.toString());
//			log.error("批量添加员工出错=====:"+e);
//			log.error(e.getMessage());
//		}
//		return SUCCESS;
//	}
	
	public String excelImport() {
		HttpServletRequest request =  ServletActionContext.getRequest() ;
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			operator = (Operator) getSessionAttribute(Constants.OPERATOR_INFO);
			List<RailEmployee> employees = new ArrayList<RailEmployee>();

			log.info("上传excel");
			//String imgPath = "";
			String format = sdf.format(new Date());
	    	String suffix = null;
	        String fileName = null;
	        String fileName2 = null;

			List<Object[]> dataList = new ArrayList<>();//导出

			if(null!=upload){

				HashMap<String, String> map = new HashMap<String, String>();
				//map.put("", value);
				List<RailEmployee> finds = t130400BO.getByParam(map);

				for (int i = 0; i < upload.size(); i++) {
					File myfile=upload.get(i);
					String myfileName=uploadFileName[i];
					String myfileType=uploadContentType[i];
					if(myfile.exists()){
						log.info("--> File Length: " + myfile.length());
		                log.info("--> File Tyep: " + myfileType);
		                log.info("--> File Name: " + myfile.getName());
		                log.info("--> File Original: " + myfileName);
		                log.info("========================================");
		                FileInputStream inputStream =  new FileInputStream(myfile);

		        		HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
		        		HSSFSheet hssfSheet = workbook.getSheetAt(0);
		        		HSSFRow row = hssfSheet.getRow(0);//Row 每一行就是一个对象



		        		for(int ii = 1;ii<hssfSheet.getLastRowNum()+1;ii++){
		        			log.info("一共"+hssfSheet.getLastRowNum()+"行");
							row = hssfSheet.getRow(ii);
							if(row == null ){
								continue;
							}
							log.info(row);
							List<Object> listObject = new ArrayList<>();//单行对象的数据
							RailEmployee employee = new RailEmployee();
							boolean pass = true;
							String passMsg = "";
							for(int j =0;j<row.getLastCellNum();j++){
		        		        HSSFCell cell = row.getCell((short)j);//Cell
								String s = null;
								if(cell!=null){
									s =  PubExcelReadUtill.getCellContent(cell);//读取单元格String内容
									System.out.println("========index======"+j+":"+s);
								}else{
									s = null;
								}
								listObject.add(s);// 单个对象属性
								// 0 人员编码	1 人员姓名	2 1、男；2、女	3 出生日期	4 身份证号	5 照片存放地址  6 	职务	 7 联系方式	 9	入职日期
		        		        if(j==0){
									if(s == null){
										passMsg += "人员编码不能为空;";
										pass = false;
										continue;
									}
									if(s.length() != 8){
										passMsg += "人员编码必须为八位;";
										pass = false;
										continue;
									}
		        		        	if(t130400BO.checkExistOnly(s,null,finds)){
										passMsg += "人员编码"+s+"已存在;";
										pass = false;
										continue;
									}
		        		        	employee.setEmployeeCode(s);
		        		        }else if(j==1){
									if(s == null){
										passMsg += "人员姓名不能为空;";
										pass = false;
										continue;
									}

		        		        	employee.setEmployeeName(s);
		        		        }else if(j==2){
									if(s == null){
										passMsg += "性别不能为空;";
										pass = false;
										continue;
									}
									if(!s.equals("1") & !s.equals("2") & !s.equals(1) & !s.equals(2)){
										passMsg += "性别格式为1或者2;";
										pass = false;
										continue;
									}
		        		        	employee.setSex(s);
		        		        }else if(j==3){
									if(s == null){
										passMsg += "出生日期不能为空;";
										pass = false;
										continue;
									}
		        		        	if(s!=null){
		        		        		try {
											Date d = sdf.parse(s);
											employee.setBirthday(d);
										}catch (Exception e){
											passMsg += "出生日期格式不正确，应该为(20190101);";
											pass = false;
											continue;
										}
		        		        	}
		        		        }else if(j==4){
									if(s == null){
										passMsg += "身份证号不能为空;";
										pass = false;
										continue;
									}
									if(!StringCheckUtils.cardCodeVerifySimple(s)){
										passMsg += "身份证号格式不正确;";
										pass = false;
										continue;
									}
		        		        	employee.setIdNumber(s);
		        		        }else if(j==5){
//		        		        	 suffix = TaskInfoUtil.getTxnInfo("suffix");
									if(s == null || s.trim()==""){
										continue;
									}
									String[] headAr = s.split("\\.");
									if(headAr.length<2 || !FileTypeUtil.isImg(s.substring(s.lastIndexOf(".")+1,s.length()))){
										passMsg += "头像后缀不是图片格式;";
										pass = false;
										continue;
									}

		        		        	if (isNotEmpty(s)) {
		        		        		employee.setEmployeeImg(imgPath+"/"+s);
									}
		        		        }else if(j==6){
		        		        	employee.setJob(s);
		        		        }else if(j==7){
									if(s == null){
										passMsg += "手机号不能为空;";
										pass = false;
										continue;
									}
									if(!StringCheckUtils.checkMobile(s)){
										passMsg +=   "手机号格式不正确;";
										pass = false;
										continue;
									}
		        		        	employee.setEmployeeTel(s);
		        		        }
		        			}
		        			if(pass){
								employee.setDept(operator.getOprBrhId());
								employee.setAddDate(new Date());
								employee.setNote1("0");
								employee.setInfoSign("0");
								employee.setAddUser(operator.getOprId());
								employees.add(employee);
								listObject.add("导入成功");
							}else{
								listObject.add("导入不成功："+passMsg);
							}
							dataList.add(listObject.toArray());
		        		}

					}
				}

				log.info("文件接收成功");
				log("批量添加员工成功。操作员编号：" + operator.getOprId());
			}else{
				log.error("文件未上传");
				dataList.add(new String[]{"文件未上传"});
				List<String> titleNames = new ArrayList<String>();// 列名称
				titleNames.add("导入失败");
				PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,"失败");
			}
			Date date = new Date();
			result = t110100BO.addListFromExcel(employees);

	        //String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			// 	0 人员编码	1 人员姓名	2 1、男；2、女	3 出生日期	4 身份证号	5 照片存放地址  6 	职务	 7 联系方式	 9	入职日期
			List<String> titleNames = new ArrayList<String>();// 列名称
			titleNames.add("人员编码");
			titleNames.add("人员姓名");
			titleNames.add(" 1、男；2、女");
			titleNames.add("出生日期（格式为20190101）");
			titleNames.add("身份证号");
			titleNames.add("照片名称");
			titleNames.add("职务");
			titleNames.add("联系方式");
			titleNames.add("导入结果");
			//导出报告
			if(dataList!=null && dataList.size()>0){
				PubExportExcelUtil.doexportExcel(request, response, titleNames,dataList,"导入结果报告");
			}

		} catch (Exception e) {
			result = ResultUtils.fail(e.toString());
			log.error("批量添加员工出错=====:"+e);
			log.error(e.getMessage());
		}
		return SUCCESS;
	}

	public void upload() throws Exception {
		
		operator = (Operator) getSessionAttribute(Constants.OPERATOR_INFO);
		
		log.info("批量上传图片开始");
		String imgPath = "";
		String format = sdf.format(new Date());
    	String suffix = null;
        String fileName = null;
        String fileName2 = null;
		if(null!=files){
			for (int i = 0; i < files.size(); i++) {
				File myfile=files.get(i);
				String myfileName=uploadFileName[i];
				String myfileType=uploadContentType[i];
				if(myfile.exists()){
					log.info("--> File Length: " + myfile.length());
	                log.info("--> File Tyep: " + myfileType);
	                log.info("--> File Name: " + myfile.getName());
	                log.info("--> File Original: " + myfileName);
	                log.info("========================================");
	                
//					String realPath = ServletActionContext.getServletContext().getRealPath(imgPath);
					
					String uploadPath  = FileUploadPathUtil.getTxnInfo("eployeeImgs");///=/uploads/img/employeeHead...
	                String projectName = ServletActionContext.getServletContext().getContextPath();
	                log.info("projectName --> " + projectName);
					String realPath = ServletActionContext.getServletContext().getRealPath(uploadPath);
					realPath = realPath.substring(0, realPath.length() - projectName.length() -uploadPath.length());
	                realPath = realPath +uploadPath;
	                log.info("realPath --> " + realPath);
					
	                log.info("--> " + realPath);
	                File dir = new File(realPath);
	                if (dir.exists()) {
	                	System.out.println("file exists");
	                } else {
	                	System.out.println("file not exists, create it ...");
	                	try {
	                        dir.mkdirs();
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                    }    
	                } 
	                FileInputStream inputStream=new FileInputStream(myfile);
	                int lastIndexOf = myfileName.lastIndexOf(".");
	                suffix = myfileName.substring(lastIndexOf + 1, myfileName.length());
	                fileName2 = UUID.randomUUID().toString().replaceAll("-", "");
	                fileName = myfile.getName() + "." + suffix;
	                log.info("--> File Suffix Name: " + fileName + "---- old Name: " + myfileName);
	                File file = new File(realPath, myfileName);//用原来的 
	                FileOutputStream fos = new FileOutputStream(file);
	                int buffer = 1024; //定义缓冲区的大小
	                int length = 0;
	                byte[] b = new byte[buffer];
	                while ((length = inputStream.read(b)) != -1) {
	                    fos.write(b, 0, length); //向文件输出流写读取的数
	                }
	                fos.close();
	               // imgPath = "/upload/"+format +"/"+ fileName;
	                b = null;
	                file = null;
	                inputStream = null;
	                fos = null;
				}
			}
			
			log.info("批量上传图片成功");
		}
		Date date = new Date();
		log.info("------------------批量新增员工图片---------------");
		log("批量导入员工图片成功。操作员编号：" + operator.getOprId());
		result =  ResultUtils.success(1);
		
		   PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write("{'success':true,'message':'上传成功'}");
	        printWriter.flush();
	        printWriter.close();
	}
	
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getEmployeeImg() {
		return employeeImg;
	}
	public void setEmployeeImg(String employeeImg) {
		this.employeeImg = employeeImg;
	}
	public long getConstOrg() {
		return constOrg;
	}
	public void setConstOrg(long constOrg) {
		this.constOrg = constOrg;
	}
	
	public Map getResult() {
		return result;
	}
	public void setResult(Map result) {
		this.result = result;
	}
	public List<File> getUpload() {
		return upload;
	}
	public void setUpload(List<File> upload) {
		this.upload = upload;
	}
	public String[] getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String[] uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public String[] getUploadContentType() {
		return uploadContentType;
	}
	public void setUploadContentType(String[] uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public long getEmployeeType() {
		return employeeType;
	}
	public void setEmployeeType(long employeeType) {
		this.employeeType = employeeType;
	}
	public String getEmployeeTel() {
		return employeeTel;
	}
	public void setEmployeeTel(String employeeTel) {
		this.employeeTel = employeeTel;
	}
	public String getInfoSign() {
		return infoSign;
	}
	public void setInfoSign(String infoSign) {
		this.infoSign = infoSign;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	public String getAddUser() {
		return addUser;
	}
	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}
	public Date getUpdDate() {
		return updDate;
	}
	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}
	public String getUpdUser() {
		return updUser;
	}
	public void setUpdUser(String updUser) {
		this.updUser = updUser;
	}
	public String getNote1() {
		return note1;
	}
	public void setNote1(String note1) {
		this.note1 = note1;
	}
	public String getNote2() {
		return note2;
	}
	public void setNote2(String note2) {
		this.note2 = note2;
	}
	public String getNote3() {
		return note3;
	}
	public void setNote3(String note3) {
		this.note3 = note3;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getDetpName() {
		return detpName;
	}
	public void setDetpName(String detpName) {
		this.detpName = detpName;
	}
	public String getWorkStatus() {
		return workStatus;
	}
	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}
	public static String getImgpath() {
		return imgPath;
	}
	public Log getLog() {
		return log;
	}
	public void setLog(Log log) {
		this.log = log;
	}
	public List<File> getFiles() {
		return files;
	}
	public void setFiles(List<File> files) {
		this.files = files;
	}
	public String getFilename() {
		return Filename;
	}
	public void setFilename(String filename) {
		Filename = filename;
	}
	public static SimpleDateFormat getSdf() {
		return sdf;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
//	public void setUpload(Object upload) {
//		Upload = upload;
//	}
	public List<File> getUpload22() {
		return upload22;
	}
	public void setUpload22(List<File> upload22) {
		this.upload22 = upload22;
	}


	

}
