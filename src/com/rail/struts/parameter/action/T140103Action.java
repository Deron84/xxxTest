package com.rail.struts.parameter.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSONObject;
import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.FileUploadPathUtil;
import com.huateng.system.util.TaskInfoUtil;
import com.rail.bo.enums.DelStatusEnums;
import com.rail.bo.enums.ImgTypeEnums;
import com.rail.bo.parameter.T140103BO;
import com.rail.bo.pub.ResultUtils;
import com.rail.po.access.RailAccessEquipType;
import com.rail.po.tool.RailToolName;
import com.rail.po.work.RailWorkImg;
/**
 *工具名称管理
 * @author qiufulong
 *
 */
public class T140103Action extends BaseAction {
	private Log log = LogFactory.getLog(this.getClass());
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private static final long serialVersionUID = 1L;
	private long id;
	private String toolName;
	private String delStatus;
	private String addUser;
	private Date addDate;
	private String updUser;
	private Date updDate;
	private String enableStatus;
	private long formOrg;
	private String toolType;
	
	//syl工单图片上传
	private Map result;
	
	private List<File> upload; 
	
    // 上传文件名  
    private String[] uploadFileName; 
    // 上传文件的MIME类型  
    private String[] uploadContentType;  
    
    private List<File> upload1; 
	  // 上传文件名  
  private String[] uploadFileName1; 
  // 上传文件的MIME类型  
  private String[] uploadContentType1;  
	
	//工具名称信息维护
	private T140103BO t140103BO = (T140103BO) ContextUtil.getBean("T140103BO");
	public T140103BO getT140103BO() {
		return t140103BO;
	}
	public void setT140103BO(T140103BO t140103bo) {
		t140103BO = t140103bo;
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
	            }else if("change".equals(getMethod())){
	            	rspCode = change();
	            }
        } catch (Exception e) {
        	//e.printStackTrace();
            log("操作员：" + operator.getOprId() + "，对工具名称进行：" + getMethod() + "，失败，失败原因为：" + e);
        }
        return rspCode;
	}
	
	/**
	 * 获得单个工具名称信息
	 * @Description: TODO
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public String getData() {
		System.out.println("----------------获得单个工具名称-----------------");
        try {
        	RailToolName railToolName = t140103BO.getByCode(String.valueOf(getId()));
            Map<String, Object> midMap = new HashMap<String, Object>();
            midMap.put("success", true);
            midMap.put("msg", railToolName);
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
	 * 新增工具名称
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception{
		System.out.println("----------------新增工具名称-----------------");
		Map<String, String> params = new HashMap<String, String>();
		params.put("TOOL_NAME", getToolName());
		List<RailToolName> rcoInfos = t140103BO.getByParam(params);
		if(rcoInfos != null && rcoInfos.size() > 0){
			Map<String, Object> errorMap = new HashMap<String, Object>();
			errorMap.put("success", false);
			errorMap.put("msg", "工具名称已存在！");
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
		if(null!=upload){
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
//					String realPath = ServletActionContext.getServletContext().getRealPath("/upload/" + format);
//	                log.info("--> " + realPath);
	                
	                String uploadPath  = FileUploadPathUtil.getTxnInfo("toolNameImgs");///uploads/img/toolNameImgs
	                String projectName = ServletActionContext.getServletContext().getContextPath();
	                log.info("projectName --> " + projectName);
					String realPath = ServletActionContext.getServletContext().getRealPath(uploadPath);
					realPath = realPath.substring(0, realPath.length() - projectName.length() -uploadPath.length());
	                realPath = realPath +uploadPath;
	                log.info("realPath --> " + realPath);
	                
	                
	                
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
	                fileName = fileName2 + "." + suffix;
	                log.info("--> File Suffix Name: " + fileName + "---- old Name: " + myfileName);
	                File file = new File(realPath, fileName);
	                FileOutputStream fos = new FileOutputStream(file);
	                int buffer = 1024; //定义缓冲区的大小
	                int length = 0;
	                byte[] b = new byte[buffer];
	                while ((length = inputStream.read(b)) != -1) {
	                    fos.write(b, 0, length); //向文件输出流写读取的数
	                }
	                fos.close();
	                
	                imgPath = uploadPath+"/"+ fileName;
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
		
		RailToolName railToolName = new RailToolName();
		railToolName.setToolName(getToolName());
		railToolName.setToolType(Long.parseLong(getToolType()));
		railToolName.setToolImg(imgPath);
		railToolName.setDelStatus("0");
		railToolName.setEnableStatus("0");
		railToolName.setAddUser(operator.getOprId());
		railToolName.setAddDate(new Date());
		railToolName.setUpdUser(operator.getOprId());
		railToolName.setUpdDate(new Date());
		   if(imgPath!=""){
	        	//railToolInfo.setToolImg(imgPath);
	        }else{
	        	log.info("图片未上传");
	        }
		String rCode = t140103BO.add(railToolName);
		String msg = "添加工具名称成功";
		boolean success = true;
		if(!rCode.equals(Constants.SUCCESS_CODE)){
			msg = "添加工具名称失败";
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
		log("添加工具名称成功。操作员编号：" + operator.getOprId());
        return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 修改工具名称
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception{
		System.out.println("----------------修改工具名称-----------------");
		RailToolName railToolName = t140103BO.getByCode(String.valueOf(getId()));
		if(railToolName == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该工具名称");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}else{
			if(!getToolName().equals(railToolName.getToolName())){
				Map<String, String> params = new HashMap<String, String>();
				params.put("TOOL_NAME", getToolName());
				List<RailToolName> rcoInfos = t140103BO.getByParam(params);
				if(rcoInfos != null && rcoInfos.size() > 0){
					Map<String, Object> errorMap = new HashMap<String, Object>();
					errorMap.put("success", false);
					errorMap.put("msg", "工具名称已存在！");
		            String str = JSONObject.toJSONString(errorMap);
		            PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
		            printWriter.write(str);
		            printWriter.flush();
		            printWriter.close();
		            return Constants.FAILURE_CODE;
				}
		  }
		}
		railToolName.setToolName(getToolName());
		railToolName.setToolType(Long.parseLong(getToolType()));
		railToolName.setUpdUser(operator.getOprId());
		railToolName.setEnableStatus(getEnableStatus());
		railToolName.setUpdDate(new Date());
        String rcode =t140103BO.update(railToolName);
		
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
        
		log("更新工具名称信息成功。操作员编号：" + operator.getOprId());
		return Constants.SUCCESS_CODE;
	}
	
	
	
	/**
	 * 修改工具名称
	 * @return
	 * @throws Exception
	 */
	public String change() throws Exception{
		System.out.println("---------------更换图片-----------------");
		RailToolName railToolName = t140103BO.getByCode(String.valueOf(getId()));
		if(railToolName == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该工具名称");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}
		
		
		operator = (Operator) getSessionAttribute(Constants.OPERATOR_INFO);
		log.info("上传图片开始");
		String imgPath = "";
		String format = sdf.format(new Date());
    	String suffix = null;
        String fileName = null;
        String fileName2 = null;
		if(null!=upload1){
			for (int i = 0; i < upload1.size(); i++) {
				File myfile=upload1.get(i);
//				String myfileName=uploadFileName[i];
//				String myfileType=uploadContentType[i];	
				if(myfile.exists()){
					log.info("--> File Length: " + myfile.length());
//	                log.info("--> File Tyep: " + myfileType);
	                log.info("--> File Name: " + myfile.getName());
//	                log.info("--> File Original: " + myfileName);
	                log.info("========================================");
//					String realPath = ServletActionContext.getServletContext().getRealPath("/upload/" + format);
//	                log.info("--> " + realPath);
	                
	                
	                String uploadPath  = FileUploadPathUtil.getTxnInfo("toolNameImgs");///uploads/img/toolNameImgs
	                String projectName = ServletActionContext.getServletContext().getContextPath();
	                log.info("projectName --> " + projectName);
					String realPath = ServletActionContext.getServletContext().getRealPath(uploadPath);
					realPath = realPath.substring(0, realPath.length() - projectName.length() -uploadPath.length());
	                realPath = realPath +uploadPath;
	                log.info("realPath --> " + realPath);
	                
	                
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
	                suffix =  TaskInfoUtil.getTxnInfo("suffix");
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
	                imgPath = uploadPath+"/"+ fileName;
//	                imgPath = "/upload/"+format +"/"+ fileName;
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
		log.info(imgPath);
		System.out.println("000000000000000000000000000000000");
		System.out.println(imgPath);
		System.out.println(railToolName.getToolName());
		railToolName.setToolImg(imgPath);
		railToolName.setUpdUser(operator.getOprId());
		railToolName.setUpdDate(new Date());
        String rcode =t140103BO.update(railToolName);
		
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
        
		log("更新工具名称信息成功。操作员编号：" + operator.getOprId());
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 删除工具名称
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception{
		System.out.println("----------------删除工具名称-----------------");
		RailToolName railToolName =  t140103BO.getByCode(String.valueOf(getId()));
		if(railToolName == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该工具名称");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}
		railToolName.setDelStatus("1");
		railToolName.setUpdUser(operator.getOprId());
		railToolName.setUpdDate(new Date());
		   String rcode =t140103BO.update(railToolName);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(rcode);
	        printWriter.flush();
	        printWriter.close();
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 启用工具名称
	 * @return
	 */
	public String open() throws Exception {
		System.out.println("----------------启用工具名称-----------------");
		RailToolName railToolName =  t140103BO.getByCode(String.valueOf(getId()));
		if(railToolName == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该工具名称");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}
		railToolName.setEnableStatus("0");
		railToolName.setUpdUser(operator.getOprId());
		railToolName.setUpdDate(new Date());
		   String rcode =t140103BO.update(railToolName);
			
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(rcode);
	        printWriter.flush();
	        printWriter.close();
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 停用工具名称
	 * @return
	 */
	public String close() throws Exception {
		System.out.println("----------------停用工具名称-----------------");
		RailToolName railToolName =  t140103BO.getByCode(String.valueOf(getId()));
		if(railToolName == null){
			Map<String, Object> midMap = new HashMap<String, Object>();
	        midMap.put("success", false);
	        midMap.put("msg", "未查询到该工具名称");
	        String str = JSONObject.toJSONString(midMap);
	        PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(str);
	        printWriter.flush();
	        printWriter.close();
			return Constants.FAILURE_CODE;
		}
		railToolName.setEnableStatus("1");
		railToolName.setUpdUser(operator.getOprId());
		railToolName.setUpdDate(new Date());
		   String rcode =t140103BO.update(railToolName);
			
		   PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
	        printWriter.write(rcode);
	        printWriter.flush();
	        printWriter.close();
		return Constants.SUCCESS_CODE;
	}

	public String getToolName() {
		return toolName;
	}

	public void setToolName(String toolName) {
		this.toolName = toolName;
	}

	public String getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(String delStatus) {
		this.delStatus = delStatus;
	}

	public String getAddUser() {
		return addUser;
	}

	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public String getUpdUser() {
		return updUser;
	}

	public void setUpdUser(String updUser) {
		this.updUser = updUser;
	}

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	public String getEnableStatus() {
		return enableStatus;
	}

	public void setEnableStatus(String enableStatus) {
		this.enableStatus = enableStatus;
	}

	public long getFormOrg() {
		return formOrg;
	}

	public void setFormOrg(long formOrg) {
		this.formOrg = formOrg;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public String getToolType() {
		return toolType;
	}
	public void setToolType(String toolType) {
		this.toolType = toolType;
	}
	public List<File> getUpload1() {
		return upload1;
	}
	public void setUpload1(List<File> upload1) {
		this.upload1 = upload1;
	}
	public String[] getUploadFileName1() {
		return uploadFileName1;
	}
	public void setUploadFileName1(String[] uploadFileName1) {
		this.uploadFileName1 = uploadFileName1;
	}
	public String[] getUploadContentType1() {
		return uploadContentType1;
	}
	public void setUploadContentType1(String[] uploadContentType1) {
		this.uploadContentType1 = uploadContentType1;
	}
	
}
