package com.sdses.struts.mchnt.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONObject;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.BaseAction;
import com.sdses.struts.util.APIHttpClient;
import com.sdses.struts.util.MD5Util;
import com.sdses.struts.util.StaticUtils;

public class dataUploadAction extends BaseAction{
	private Log log = LogFactory.getLog(this.getClass());
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
	private static final long serialVersionUID = 5283736418947840300L;
	private String operId;
	private String outMchntId;
	private String cmbcMchntId;
	private String edType;
	private String upFileCount;	
	private List<File> upload; 
    // 上传文件名  
    private String[] uploadFileName; 
    // 上传文件的MIME类型  
    private String[] uploadContentType;  
	
	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
		String postStr = null;
		try {
            if ("upload".equals(getMethod())) {
            	postStr = dataUpload();
            }
        } catch (Exception e) {
            log("操作员编号：" + operator.getOprId() + "，对机构的维护操作" + getMethod() + "失败，失败原因为：" + e.getMessage());
        }
		return postStr;
	}
	/**
     * 电子资料上传
     * @throws Exception
     */
 //   @SuppressWarnings("unchecked")
	private String dataUpload() throws Exception {
        Map<String, Object> returnmap = new HashMap<String, Object>();
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        List<String> arrayList = new ArrayList<String>();
        List<Part> partList = new ArrayList<Part>();
        ResourceBundle bundle = ResourceBundle.getBundle(SysParamConstants.CKEY);
    	String addrInterface = bundle.getString("addrInterface");
    	String uploadEncry = bundle.getString("uploadEncry");
    	String uploadDecry = bundle.getString("uploadDecry");
    	String uploadData = bundle.getString("uploadData");
  
    	String format = sdf.format(new Date());
    	String rtnVal = null;
    	String suffix = null;
        String fileName = null;
        String fileName2 = null;
    	String postStr = null;
    	String statStr = null;
    	String msStr = null;
    	
    	int count=0;
    	
    	try {
    		if (upload == null) {
                log.info("--> No Upload File -- ");
                rtnMap.put("result_code", "9001" + "");
                rtnMap.put("result_msg", "数据验证不通过");
                rtnMap.put("error_msg", "未接收到文件");
                rtnMap.put("cmbcMchntId", "");
                rtnMap.put("outMchntId", "");
                postStr = StaticUtils.mapToResponse(rtnMap);
                return postStr;
            }
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
    				String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload/" + format);
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
                    arrayList.add("\"" + fileName2 + "\":\"" + MD5Util.getMD5(file) + "\"");
                    FilePart fp = new FilePart("file" + count, file);
                    partList.add(fp);
                    count++;
                    b = null;
                    fp = null;
                    file = null;
                    inputStream = null;
                    fos = null;
                    Thread.sleep(1000);
    			}
    		}
    		String md5s = "{";
            for (int i = 0; i < arrayList.size(); i++) {
                if (i == 0) {
                    md5s = md5s + arrayList.get(i);
                } else {
                    md5s = md5s + "," + arrayList.get(i);
                }
            }
            md5s = md5s + "}";
            log.info("--> md5s:" + md5s);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // 建立一个NameValuePair数组，用于存储欲传送的参数
            params.add(new BasicNameValuePair("operId", operId)); // 拓展人员编号
            params.add(new BasicNameValuePair("outMchntId", outMchntId)); // 外部商户号
            params.add(new BasicNameValuePair("cmbcMchntId", cmbcMchntId)); // 民生商户号
            if ("营业执照".equals(edType)) {
            	edType="01";
			} else if ("业务申请表".equals(edType)) {
				edType="02";
			} else if ("其他".equals(edType)) {
				edType="03";
			} else if ("银行卡".equals(edType)) {
				edType="04";
			} else if ("商户协议".equals(edType)) {
				edType="05";
			} else if ("店铺照片".equals(edType)) {
				edType="06";
			} else if ("法人/负责人身份证".equals(edType)) {
				edType="07";
			}
            params.add(new BasicNameValuePair("edType", edType)); // 资料分类
            params.add(new BasicNameValuePair("upFileCount", upFileCount)); // 文件数量
            params.add(new BasicNameValuePair("md5s", md5s)); //MD5校验值
            log.info("--> Parameters Value http Encry: " + params.toString());
            uploadEncry = addrInterface + uploadEncry;
            log.info("-->Resuest address http Encry- " + uploadEncry);
            postStr = new APIHttpClient(uploadEncry).post(params);
            JSONObject jsStr = JSONObject.parseObject(postStr);
            postStr = null;
            String str = (String) jsStr.get("result_code");
            log.info("-->" + str);
            if (str != null && str.equals("4000")) {
            	String objStr = (String) jsStr.get("error_msg");
                log.info("--> Parameters Number Upload Decry:" + (partList.size() + 1));
                Part[] parts = new Part[partList.size() + 1];
                parts[0] = new StringPart("uploadContext", objStr, "UTF-8");
                for (int i = 1; i <= partList.size(); i++) {
                    parts[i] = partList.get(i - 1);
                }
                HttpClient client = new HttpClient();
                PostMethod post = new PostMethod(uploadData);
                MultipartRequestEntity entity = new MultipartRequestEntity(parts, new HttpMethodParams());
                post.setRequestEntity(entity);
                log.info(entity.getContentType());
                client.executeMethod(post);
                //释放连接，以免超过服务器负荷  
                log.info("============================");
                rtnVal = post.getResponseBodyAsString();
                log.info(rtnVal);
                log.info("============================");
                //释放连接，以免超过服务器负荷  
                post.releaseConnection();
            } else {
                rtnMap.put("result_code", "4001" + "");
                rtnMap.put("result_msg", "加密失败");
                rtnMap.put("error_msg", "数据加密失败，请稍后重试");
                rtnMap.put("cmbcMchntId", "");
                rtnMap.put("outMchntId", "");
                postStr = StaticUtils.mapToResponse(rtnMap);
                return postStr;
            }
            
            JSONObject midObj = JSONObject.parseObject(rtnVal);   
            if (midObj != null) {
            	statStr = midObj.get("gateReturnType").toString();
                if (statStr.equals("S")) {
                    msStr = midObj.get("businessContext").toString();
                } else {
                    rtnMap.put("result_code", "5002");
                    rtnMap.put("result_msg", "解密异常");
                    rtnMap.put("error_msg", midObj.get("gateReturnMessage"));
                    rtnMap.put("cmbcMchntId", "");
                    rtnMap.put("outMchntId", "");
                    postStr = StaticUtils.mapToResponse(rtnMap);
                    return postStr;
                }
            } else {
                rtnMap.put("result_code", "5002");
                rtnMap.put("result_msg", "解密异常");
                rtnMap.put("error_msg", "民生返回数据异常");
                rtnMap.put("cmbcMchntId", "");
                rtnMap.put("outMchntId", "");
                postStr = StaticUtils.mapToResponse(rtnMap);
                return postStr;
            }
            params = new ArrayList<NameValuePair>();
            // 建立一个NameValuePair数组，用于存储欲传送的参数
            params.add(new BasicNameValuePair("encryData", msStr)); // 加密数据
            log.info("--> Parameters Value http Decry: " + params.toString());
            uploadDecry = addrInterface  + uploadDecry;
            log.info("-->Resuest address http Decry - " + uploadDecry);
            postStr = new APIHttpClient(uploadDecry).post(params); 
		} catch (Exception e) {
			e.printStackTrace();
            rtnMap.put("result_code", "4003");
            rtnMap.put("result_msg", "文件上传失败");
            rtnMap.put("error_msg", "文件上传失败，请稍后重试");
            rtnMap.put("cmbcMchntId", "");
            rtnMap.put("outMchntId", "");
            postStr = StaticUtils.mapToResponse(rtnMap);
            return postStr;
		}
    	log.info("--> Retrun Message: " + postStr.toString());    	
    	returnmap=StaticUtils.jsonToMap(postStr);
        postStr = StaticUtils.mapToResponse(returnmap);
        return postStr;   	
    }
	public String getOperId() {
		return operId;
	}
	public void setOperId(String operId) {
		this.operId = operId;
	}
	public String getOutMchntId() {
		return outMchntId;
	}
	public void setOutMchntId(String outMchntId) {
		this.outMchntId = outMchntId;
	}
	public String getCmbcMchntId() {
		return cmbcMchntId;
	}
	public void setCmbcMchntId(String cmbcMchntId) {
		this.cmbcMchntId = cmbcMchntId;
	}
	public String getEdType() {
		return edType;
	}
	public void setEdType(String edType) {
		this.edType = edType;
	}
	public String getUpFileCount() {
		return upFileCount;
	}
	public void setUpFileCount(String upFileCount) {
		this.upFileCount = upFileCount;
	}
	
	public List<File> getUpload() {
		return upload;
	}
	public void setUpload(List<File> upload) {
		this.upload = upload;
	}
	public String[] getUploadContentType() {
		return uploadContentType;
	}
	public void setUploadContentType(String[] uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	public String[] getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String[] uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
}
