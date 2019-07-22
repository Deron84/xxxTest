package com.rail.struts.work.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSONObject;
import com.huateng.common.Constants;
import com.huateng.common.ErrorCode;
import com.huateng.common.Operator;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.FileUploadPathUtil;
import com.rail.bo.enums.DelStatusEnums;
import com.rail.bo.enums.ImgTypeEnums;
import com.rail.bo.enums.InWhseEnums;
import com.rail.bo.enums.ToolsStatusEnums;
import com.rail.bo.pub.FileTypeUtil;
import com.rail.bo.pub.ResultUtils;
import com.rail.bo.work.T130200BO;
import com.rail.po.tool.RailToolInfo;
import com.rail.po.work.RailWorkImg;

public class T130200Action extends BaseAction {
	private Log log = LogFactory.getLog(this.getClass());
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String workCode;
	private String  imgType;
	private String  imgPath;
	private String delStatus;
	private String  addUser;
	private Date addDate;
	private String  updUser;
	private Date updDate;
	private String  note1 ;
	private String  note2 ;
	private String  note3 ;
	
	//syl工单图片上传
	private Map result;
	
	private List<File> upload; 
    // 上传文件名  
    private String[] uploadFileName; 
    // 上传文件的MIME类型  
    private String[] uploadContentType;  
	
    
    private String toolExpiration1;
    
    private String transferMsg;
    	
	
	
	
	private T130200BO t130200BO = (T130200BO) ContextUtil.getBean("T130200BO");
	public T130200BO getT130200BO() {
		return t130200BO;
	}

	public void setT130200BO(T130200BO t130200BO) {
		t130200BO = t130200BO;
	}
		
	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
				System.out.println(getMethod()+"  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				try {
		            if ("edit".equals(getMethod())) {
		            	rspCode = editAccess();
		            }else if ("add".equals(getMethod())) {
		                rspCode = add();
		            }
		        } catch (Exception e) {
		        	//e.printStackTrace();
		            log("操作员：" + operator.getOprId() + "，对门禁进行：" + getMethod() + "，失败，失败原因为：" + e);
		        }
		        return rspCode;
	}
	
	/**
	 * 新增门禁检修保养
	 * @Description: TODO
	 * @param @return
	 * @param @throws Exception   
	 * @return String  
	 * @throws IOException 
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月16日
	 */
	private String add() throws IOException {
		System.out.println("------------------新增门禁检修保养---------------");
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("WORK_CODE", getWorkCode());
		List<RailWorkImg> rwInfos = t130200BO.getByParam(paramMap);
		RailWorkImg railWorkImg = new RailWorkImg();
//		railAccessMaintain.setAccessCode(getAccessCode());
//		railAccessMaintain.setEquipCode(getEquipCode());
//		railAccessMaintain.setAddDate(new Date());
//		railAccessMaintain.setMaintainUser(operator.getOprId());
		String rcode=t130200BO.add(railWorkImg);
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
		log("添加仓库信息成功。操作员编号：" + operator.getOprId());
        return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 更新门禁检修保养信息
	 * @Description: TODO
	 * @param @return
	 * @param @throws Exception   
	 * @return String  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public String editAccess()throws Exception{
		System.out.println("------------------更新门禁检修保养信息---------------");
		RailWorkImg railWorkImg  = t130200BO.getByCode(String.valueOf(getId()));
        if (railWorkImg==null) {
            return ErrorCode.T100100_01;
        }
		String rcode = t130200BO.updateAccess(railWorkImg);
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
        
		log("更新门禁信息成功。操作员编号：" + operator.getOprId());
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 获得单个门禁检修保养信息
	 * @Description: TODO
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author qiufulong
	 * @date 2019年4月17日
	 */
	public String getData() {
		System.out.println("------------------获得单个门禁检修保养信息---------------");
        try {
        	RailWorkImg railWorkImg = t130200BO.getByCode(getWorkCode());
            Map<String, Object> midMap = new HashMap<String, Object>();
            midMap.put("success", true);
            midMap.put("msg", railWorkImg);
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
	public static Map<String, Object> nullToEmpty(Map<String, Object> map) {
		Set<String> set = map.keySet();
		if(set != null && !set.isEmpty()) {
			for(String key : set) {
				if(map.get(key) == null) { 
					map.put(key, ""); 
				}
			}
		}
		return map;
	}
public String upload() throws Exception {
		
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
				//String myfileType=uploadContentType[i];
				String myfileType=myfileName.substring(myfileName.lastIndexOf(".")+1,myfileName.length());
				if(!FileTypeUtil.isImg(myfileType)){
					String msg = "文件"+myfileName+"类型必须为图片格式";
					result =  ResultUtils.fail(msg);
					
//				    PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
//			        printWriter.write("{'success':false,'msg':'"+msg+"'}");
//			        printWriter.flush();
//			        printWriter.close();	
			        return SUCCESS;
				}
				
				
				if(myfile.exists()){
					log.info("--> File Length: " + myfile.length());
	                log.info("--> File Tyep: " + myfileType);
	                log.info("--> File Name: " + myfile.getName());
	                log.info("--> File Original: " + myfileName);
	                log.info("========================================");
	                
	                String uploadPath = FileUploadPathUtil.getTxnInfo("workImgs");
	                imgPath = uploadPath;
	                
	                String projectName = ServletActionContext.getServletContext().getContextPath();
	                log.info("projectName --> " + projectName);
					String realPath = ServletActionContext.getServletContext().getRealPath(imgPath);
					realPath = realPath.substring(0, realPath.length() - projectName.length() -imgPath.length());
	                realPath = realPath +imgPath;
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
	                
	                imgPath = uploadPath+"/"+fileName;
	                b = null;
	                file = null;
	                inputStream = null;
	                fos = null;
				}
			}
			
			log.info("上传图片成功");
		}
		Date date = new Date();
		log.info("------------------新增工图片---------------");
		RailWorkImg railWorkImg = new RailWorkImg();
		railWorkImg.setImgPath(imgPath);
		railWorkImg.setNote1(note1);
		railWorkImg.setWorkCode(workCode);
		railWorkImg.setImgType(String.valueOf(ImgTypeEnums.PIG.getCode()));
		railWorkImg.setAddDate(new Date());
		railWorkImg.setAddUser(operator.getOprId());
		railWorkImg.setDelStatus(String.valueOf(DelStatusEnums.NO.getCode()));
		
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        if(imgPath!=""){
        	//railToolInfo.setToolImg(imgPath);
        }else{
        	log.info("图片未上传");
        }
		log("添加工单图片成功。操作员编号：" + operator.getOprId());
		t130200BO.add(railWorkImg);
		result =  ResultUtils.success(1);
		return SUCCESS;
	}
public String uploadVedio() throws Exception {
	
	operator = (Operator) getSessionAttribute(Constants.OPERATOR_INFO);
	
	log.info("视频开始");
	String imgPath = "";
	String format = sdf.format(new Date());
	String suffix = null;
	String fileName = null;
	String fileName2 = null;
	if(null!=upload){
		for (int i = 0; i < upload.size(); i++) {
			File myfile=upload.get(i);
			String myfileName=uploadFileName[i];
			//String myfileType=uploadContentType[i];
			String myfileType=myfileName.substring(myfileName.lastIndexOf(".")+1,myfileName.length());
			if(!FileTypeUtil.isVideo(myfileType)){
				String msg = "文件"+myfileName+"类型必须为视频格式";
				result =  ResultUtils.fail(msg);
				
//			    PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
//		        printWriter.write("{'success':false,'msg':'"+msg+"'}");
//		        printWriter.flush();
//		        printWriter.close();	
		        return SUCCESS;
			}
			
			
			if(myfile.exists()){
				log.info("--> File Length: " + myfile.length());
				log.info("--> File Tyep: " + myfileType);
				log.info("--> File Name: " + myfile.getName());
				log.info("--> File Original: " + myfileName);
				log.info("========================================");
//				String realPath = ServletActionContext.getServletContext().getRealPath("/upload/mp4/" + format);
//				log.info("--> " + realPath);
				String uploadPath = FileUploadPathUtil.getTxnInfo("workVideos");//uploads/img/workVideos
                imgPath = uploadPath;
                
                String projectName = ServletActionContext.getServletContext().getContextPath();
                log.info("projectName --> " + projectName);
				String realPath = ServletActionContext.getServletContext().getRealPath(imgPath);
				realPath = realPath.substring(0, realPath.length() - projectName.length() -imgPath.length());
                realPath = realPath +imgPath;
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
				
				imgPath = uploadPath+"/"+fileName;
				b = null;
				file = null;
				inputStream = null;
				fos = null;
			}
		}
		
		log.info("上传视频成功");
	}
	Date date = new Date();
	log.info("------------------新增工单视频---------------");
	RailWorkImg railWorkImg = new RailWorkImg();
	railWorkImg.setImgPath(imgPath);
	railWorkImg.setNote1(note1);
	railWorkImg.setWorkCode(workCode);
	railWorkImg.setImgType(String.valueOf(ImgTypeEnums.VIEDIO.getCode()));
	railWorkImg.setAddDate(new Date());
	railWorkImg.setAddUser(operator.getOprId());
	railWorkImg.setDelStatus(String.valueOf(DelStatusEnums.NO.getCode()));
	
	String uuid = UUID.randomUUID().toString().replaceAll("-", "");
	if(imgPath!=""){
		//railToolInfo.setToolImg(imgPath);
	}else{
		log.info("视频未上传");
	}
	log("添加工单视频成功。操作员编号：" + operator.getOprId());
	t130200BO.add(railWorkImg);
	result =  ResultUtils.success(1);
	return SUCCESS;
}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	public String getWorkCode() {
		return workCode;
	}

	public void setWorkCode(String workCode) {
		this.workCode = workCode;
	}

	public String getImgType() {
		return imgType;
	}

	public void setImgType(String imgType) {
		this.imgType = imgType;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getDelStatus() {
		return delStatus;
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

	public String getToolExpiration1() {
		return toolExpiration1;
	}

	public void setToolExpiration1(String toolExpiration1) {
		this.toolExpiration1 = toolExpiration1;
	}

	public String getTransferMsg() {
		return transferMsg;
	}

	public void setTransferMsg(String transferMsg) {
		this.transferMsg = transferMsg;
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

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
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
}


