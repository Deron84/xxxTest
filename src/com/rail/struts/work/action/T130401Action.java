package com.rail.struts.work.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.huateng.common.Constants;
import com.huateng.common.Operator;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.ContextUtil;
import com.huateng.system.util.FileUploadPathUtil;
import com.huateng.system.util.TaskInfoUtil;
import com.rail.bo.pub.FileTypeUtil;
import com.rail.bo.pub.PubPath;
import com.rail.bo.pub.ResultUtils;
import com.rail.bo.work.T130400BO;

/**
 * 
 * ClassName: T130401Action 
 * @Description: TODO
 * @author syl
 * @date 2019年5月5日
 */
public class T130401Action extends BaseAction {
	
	private Log log = LogFactory.getLog(this.getClass());
	private static final String imgPath = FileUploadPathUtil.getTxnInfo("eployeeImgs");// "/upload/img/employeeHeadImgs/";
	private static final Double fileSize =Double.valueOf(FileUploadPathUtil.getTxnInfo("fileSize(KB)")) ;// "/upload/img/employeeHeadImgs/";
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
	private List<File> files; 
	private String Filename; 
	private Object Upload; 
//    // 上传文件名  
//    private String[] uploadFileName; 
//    // 上传文件的MIME类型  
//    private String[] uploadContentType;  
	
	
	//syl  excel导入上传
	private Map result;
	
	
	//人员信息维护
	private T130400BO t130400BO = (T130400BO) ContextUtil.getBean("T130400BO");
	public T130400BO getT130400BO() {
		return t130400BO;
	}
	public void setT130400BO(T130400BO t130400bo) {
		t130400BO = t130400bo;
	}
		
	@Override
	protected String subExecute() throws Exception {
		System.out.println(getMethod()+"  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        return rspCode;
	}
	
	public void upload() throws Exception {
		operator = (Operator) getSessionAttribute(Constants.OPERATOR_INFO);
		log.info("批量上传图片开始");
		//String imgPath = "";
		String format = sdf.format(new Date());
    	String suffix = null;
        String fileName = null;
        String fileName2 = null;
		if(null!=files){
			for (int i = 0; i < files.size(); i++) {
				File myfile=files.get(i);
				String myfileName=Filename;
//				String myfileType=uploadContentType[i];
				String myfileType=myfileName.substring(myfileName.lastIndexOf(".")+1,myfileName.length());
				if(!FileTypeUtil.isImg(myfileType)){
					String msg = "文件"+myfileName+"类型必须为图片格式";
					result =  ResultUtils.fail(msg);
					
				    PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
			        printWriter.write("{'success':false,'msg':'"+msg+"'}");
			        printWriter.flush();
			        printWriter.close();	
			        return ;
				}
				if(myfile.exists()){
					log.info("--> File Length: " + myfile.length());
	                log.info("--> File Tyep: " + myfileType);
	                log.info("--> File Name: " + myfile.getName());
	                log.info("--> File Original: " + myfileName);
	                log.info("========================================");
	                
	                
					if(myfile.length()>fileSize*1024){
						String msg = "文件"+myfileName+"上传超过"+fileSize+"K";
						result =  ResultUtils.fail(msg);
						
					    PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
				        printWriter.write("{'success':false,'msg':'"+msg+"'}");
				        printWriter.flush();
				        printWriter.close();	
				        return ;
					}
	                
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
	                suffix = TaskInfoUtil.getTxnInfo("suffix");
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
	        printWriter.write("{'success':true,'msg':'上传成功'}");
	        printWriter.flush();
	        printWriter.close();
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
	public Object getUpload() {
		return Upload;
	}
	public void setUpload(Object upload) {
		Upload = upload;
	}
	public Map getResult() {
		return result;
	}
	public void setResult(Map result) {
		this.result = result;
	}

	public static SimpleDateFormat getSdf() {
		return sdf;
	}

	


	

}
