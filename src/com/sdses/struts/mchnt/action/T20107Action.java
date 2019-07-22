package com.sdses.struts.mchnt.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.huateng.common.Constants;
import com.huateng.common.ErrorCode;
import com.huateng.common.SysParamConstants;
import com.huateng.struts.system.action.BaseAction;
import com.huateng.system.util.BeanUtils;
import com.huateng.system.util.CommonFunction;
import com.huateng.system.util.ContextUtil;
import com.sdses.bo.mchnt.T20107BO;
import com.sdses.bo.mchnt.T20107BO_2;
import com.sdses.po.TcMchntMap;
import com.sdses.po.TcMchntMapPK;
import com.sdses.struts.util.APIHttpClient;
import com.sdses.struts.util.StaticUtils;
import com.sun.xml.internal.bind.v2.model.core.ID;
/**
 * Title:机构维护
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2017-04-08
 * 
 * Company:  Synthesis Electronic Technology Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */
public class T20107Action extends BaseAction {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private static Log log=LogFactory.getLog(T20107Action.class);

    private static final long serialVersionUID = 1L;

    //	
    //	
    //菜品编码表BO
    private T20107BO t20107BO = (T20107BO) ContextUtil.getBean("T20107BO");
    private T20107BO_2 t20107BO_2 = (T20107BO_2) ContextUtil.getBean("T20107BO_2");
    //扫码支付商户号
    private String cmchntNo;

    //终端硬件编号
    private String termFixNo;

    // 密钥
    private String ckey;
    

    // 业务代码
    private String bussId;
    
    // 密码
    private String pwdId;
    
    // 银联密码
    private String pwdIdYL;

    private String resv1;

	// 商户号
    private String ylMchntNo;

    // 终端号
    private String ylTermNo;

    // 启用停用标识
    private String enableFlag1;

    // 开始日期
    private String startDate;

    // 结束日期
    private String endDate;

    // 保存数据字符串
    private String mchntDataStr;

    // 第三方机构
    private String brhFlag;
    
    private List<File> upload; 
    // 上传文件名  
    private String[] uploadFileName; 
    // 上传文件的MIME类型  
    private String[] uploadContentType; 
    
    private String uploadCert;
    
    // 分行代码
    private String branchCode;
    // 聚合标识
 //   private String mergeFlag;
    // 柜台代码
    private String bussId2;
    
    private String payMchntNo;
    
    //扫码方式
    private String scanFlag;
    
    private String id;

	@Override
    protected String subExecute() {
    	  try {
              if ("add".equals(getMethod())) {
                  rspCode = add();
              } else if ("delete".equals(getMethod())) {
                  rspCode = delete();
              } else if ("update".equals(getMethod())) {
                  rspCode = update();
              } else if ("isExistYLCert".equals(getMethod())) {
            	  rspCode = isExistYLCert();
              } else if ("updateCert".equals(getMethod())) {
            	  rspCode = updateCert();
              }
          } catch (Exception e) {
              log("操作员编号：" + operator.getOprId() + "，对机构的维护操作" + getMethod() + "失败，失败原因为：" + e.getMessage());
          }
          return rspCode;
    }

    /**
     * 添加机构信息
     * @throws Exception
     */
    private String add() throws Exception {
    	String format = sdf.format(new Date());
    	
    	if(upload != null){
    		JSONObject resultMap=null;
    		ResourceBundle bundle = ResourceBundle.getBundle(SysParamConstants.CKEY);
        	String addrInterface = bundle.getString("addrInterface");
        	String certDate = bundle.getString("certDate");
        	File myfile=upload.get(0);
        	String myfileName=uploadFileName[0];
        	if(myfile.exists()){
				String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload/" + format);
                log.info("--> " + realPath);
                File dir = new File(realPath);
                if (dir.exists()) {
                	log.info("file exists");
                } else {
                	log.info("file not exists, create it ...");
                	try {
                        dir.mkdirs();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }    
                } 
                FileInputStream inputStream=new FileInputStream(myfile);
                File file = new File(realPath, myfileName);
                FileOutputStream fos = new FileOutputStream(file);
                int buffer = 1024; //定义缓冲区的大小
                int length = 0;
                byte[] b = new byte[buffer];
                while ((length = inputStream.read(b)) != -1) {
                    fos.write(b, 0, length); //向文件输出流写读取的数
                }
                fos.close();
                FilePart fp = new FilePart("file", file);
        	    Part[] parts = new Part[2];
        	    parts[0] = new StringPart("mchnt_id", ylMchntNo.trim(), "UTF-8");
        	    parts[1] = fp;
        	    HttpClient client = new HttpClient();
        	    PostMethod post = new PostMethod(addrInterface+certDate);
        	    MultipartRequestEntity entity = new MultipartRequestEntity(parts, new HttpMethodParams());
        	    post.setRequestEntity(entity);
        	    log.info(entity.getContentType() + "");
        	    client.executeMethod(post);
        	    //释放连接，以免超过服务器负荷  
        	    log.info("============================");
        	    String rtnVal = post.getResponseBodyAsString();
        	    log.info("--> rtnVal" + rtnVal);
        	    log.info("============================");
        	    //释放连接，以免超过服务器负荷  
        	    post.releaseConnection();
        	    resultMap=StaticUtils.jsonToMap(rtnVal.toString());
                if(!"4000".equals(resultMap.get("result_code"))){
                	return (String) resultMap.get("error_msg");
                }
    		}else {
				return "上传证书大小不能为0字节";
			}
    	}
    	 TcMchntMapPK tcMchntMapPK = new TcMchntMapPK();
         tcMchntMapPK.setYlMchntNo(ylMchntNo.trim());
         tcMchntMapPK.setTermFixNo(termFixNo.trim());
         tcMchntMapPK.setYlTermNo(ylTermNo.trim());
         tcMchntMapPK.setBrhFlag(brhFlag.trim());
         tcMchntMapPK.setScanFlag(scanFlag.trim());
         TcMchntMap tcMchntMap = new TcMchntMap();
         tcMchntMap.setcMchntNo(cmchntNo.trim());  
         tcMchntMap.setStartDate(startDate.trim());
         tcMchntMap.setEndDate(endDate.trim());
         tcMchntMap.setEnableFlag(enableFlag1.trim());
         tcMchntMap.setMergeFlag("0");
         if(!"".equals(resv1.trim())){
         	tcMchntMap.setResv1(resv1.trim());
         }
         if("2".equals(brhFlag.trim())){
         	tcMchntMap.setcKey(ckey.trim());
         	tcMchntMap.setBussId("000000");
         } else if ("3".equals(brhFlag.trim())) {
         	tcMchntMap.setcKey(ckey.trim());
         	tcMchntMap.setBussId(bussId.trim());
 		}else if ("16".equals(brhFlag.trim())) {
         	tcMchntMap.setcKey(ckey.trim());
         	tcMchntMap.setCipher(pwdIdYL.trim());
         	tcMchntMap.setBussId("000000");
 		} else if ("4".equals(brhFlag.trim())) {
         	tcMchntMap.setcKey(pwdId.trim());
         	tcMchntMap.setBussId("000000");
 		} else if ("5".equals(brhFlag.trim())) {
 			ResourceBundle bundle = ResourceBundle.getBundle(SysParamConstants.CKEY);
            String midStr = bundle.getString("ckey"); //CKEY
            tcMchntMap.setcKey(midStr.trim());
 			tcMchntMap.setCipher(pwdIdYL.trim());
 			if(uploadFileName != null){
             	tcMchntMap.setCertpath(uploadFileName[0].trim());
            } else if (!"".equals(uploadCert.trim())) {
				tcMchntMap.setCertpath(uploadCert.trim());
			}
 			tcMchntMap.setBussId("000000");
 		} else if("6".equals(brhFlag.trim())){
 			tcMchntMap.setcKey(ckey.trim());
         	tcMchntMap.setBussId(bussId.trim());
 		} else if ("7".equals(brhFlag.trim())) {
 			tcMchntMap.setcKey(ckey.trim());
         	tcMchntMap.setBussId(bussId2.trim());
         	tcMchntMap.setBranchCode(branchCode.trim());
 		} else if ("9".equals(brhFlag.trim())) {
 			tcMchntMap.setcMchntNo(tcMchntMapPK.getYlMchntNo());
 			ResourceBundle bundle = ResourceBundle.getBundle(SysParamConstants.CKEY);
            String midStr = bundle.getString("ckey"); //CKEY
            tcMchntMap.setcKey(midStr.trim());
            tcMchntMap.setBussId("000000");
 		} else {
         	 ResourceBundle bundle = ResourceBundle.getBundle(SysParamConstants.CKEY);
             String midStr = bundle.getString("ckey"); //CKEY
             tcMchntMap.setcKey(midStr.trim());
             tcMchntMap.setBussId("000000");
 		}
        /* if(mergeFlag !=null && "1".equals(mergeFlag.trim())){
       		tcMchntMap.setMergeFlag(mergeFlag.trim());
       		tcMchntMap.setCipher(pwdIdYL.trim());
       		if(cmchntNo.trim() == null){
       			tcMchntMap.setcMchntNo(ylMchntNo.trim());
       		}
       		if(uploadFileName != null){
             	tcMchntMap.setCertpath(uploadFileName[0].trim());
            } else if (!"".equals(uploadCert.trim())) {
 				tcMchntMap.setCertpath(uploadCert.trim());
 			}
       	 }*/
         tcMchntMap.setTcMchntMapPK(tcMchntMapPK);
         // 判断有无此条记录
         if (t20107BO.get(tcMchntMapPK) != null) {
             return ErrorCode.T20107_01;
         }
         List<Object[]> findList3 = t20107BO.findList3(ylMchntNo.trim(),ylTermNo.trim());
         if ("0".equals(scanFlag.trim())) {       	 
             if (findList3.size() > 0) {
            	 return "此商户下该银联终端号已存在!";
             }
		 } else if ("1".equals(scanFlag.trim())) {
             if (findList3.size() > 0) {
            	 for (Object[] obj : findList3) {	
            		 if ("1".equals(obj[2]) || "0".equals(obj[2])) {
            			 return "此商户下该银联终端号已存在!";
					}
            	 }
             }
         } else if ("2".equals(scanFlag.trim())) {        	
             if (findList3.size()==3 || (findList3.size()==2 && "2".equals(findList3.get(0)[2]) && "2".equals(findList3.get(1)[2]))) {
                 return "该终端号下被扫方式已绑定两条!";
             } else if (findList3.size()==2 && "5".equals(findList3.get(1)[3]) && "5".equals(brhFlag.trim())) {
            	 return "被扫方式下已存在银联支付!";
			 } else if (findList3.size()==2 && !"5".equals(findList3.get(1)[3]) && !"5".equals(brhFlag.trim())) {
				 return "被扫方式绑定的两条渠道,必须一条为银联!";
			 } else if (findList3.size()==1 && "2".equals(findList3.get(0)[2]) && "5".equals(findList3.get(0)[3]) && "5".equals(brhFlag.trim())) {
            	 return "被扫方式下已存在银联支付!";
			 } else if (findList3.size()==1 && "2".equals(findList3.get(0)[2]) && !"5".equals(findList3.get(0)[3]) && !"5".equals(brhFlag.trim())) {
            	 return "被扫方式绑定的两条渠道,必须一条为银联!";
			 } else if (findList3.size()==1 && "0".equals(findList3.get(0)[2])) {
				 return "此商户下该银联终端号已存在!";
             }
         }       
         tcMchntMap.setId(CommonFunction.generateString(18));
         t20107BO.add(tcMchntMap);
         return Constants.SUCCESS_CODE;
    }
    /**
     * 
     * //TODO 更新银联证书
     *
     * @return
     * @throws Exception
     * @author lixiaomin
     */
    private String  updateCert() throws Exception{
    	String format = sdf.format(new Date());	
    	if(upload != null){
    		JSONObject resultMap=null;
    		ResourceBundle bundle = ResourceBundle.getBundle(SysParamConstants.CKEY);
        	String addrInterface = bundle.getString("addrInterface");
        	String certDate = bundle.getString("certDate");
        	File myfile=upload.get(0);
        	String name = myfile.getName();
        	log.info("-->"+name);
        	String myfileName=uploadFileName[0];
        	if(myfile.exists()){
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
                File file = new File(realPath, myfileName);
                FileOutputStream fos = new FileOutputStream(file);
                int buffer = 1024; //定义缓冲区的大小
                int length = 0;
                byte[] b = new byte[buffer];
                while ((length = inputStream.read(b)) != -1) {
                    fos.write(b, 0, length); //向文件输出流写读取的数
                }
                fos.close();
                FilePart fp = new FilePart("file", file);
        	    Part[] parts = new Part[2];
        	    parts[0] = new StringPart("mchnt_id", ylMchntNo.trim(), "UTF-8");
        	    parts[1] = fp;
        	    HttpClient client = new HttpClient();
        	    PostMethod post = new PostMethod(addrInterface+certDate);
        	    MultipartRequestEntity entity = new MultipartRequestEntity(parts, new HttpMethodParams());
        	    post.setRequestEntity(entity);
        	    log.info(entity.getContentType() + "");
        	    client.executeMethod(post);
        	    //释放连接，以免超过服务器负荷  
        	    log.info("============================");
        	    String rtnVal = post.getResponseBodyAsString();
        	    log.info("--> rtnVal" + rtnVal);
        	    log.info("============================");
        	    //释放连接，以免超过服务器负荷  
        	    post.releaseConnection();
        	    resultMap=StaticUtils.jsonToMap(rtnVal.toString());
                if(!"4000".equals(resultMap.get("result_code"))){
                	return (String) resultMap.get("error_msg");
                }
    		
        	}else {
				return "上传证书大小不能为0字节";
			}
    	}
    	int updateData = t20107BO_2.updateCertData(ylMchntNo.trim(), uploadFileName[0].trim(),brhFlag.trim(),scanFlag.trim());
        if (updateData > 0) {
            return Constants.SUCCESS_CODE;
        } else {
            return ErrorCode.T20107_02;
        }
    }
    /**
     * 
     * //TODO 删除所选的条目信息
     *
     * @return
     * @throws Exception
     * @author hanyongqing
     */
    @SuppressWarnings("unchecked")
    private String delete() throws Exception {

        TcMchntMapPK tcMchntMapPK = new TcMchntMapPK();
        tcMchntMapPK.setYlMchntNo(ylMchntNo.trim());
        tcMchntMapPK.setYlTermNo(ylTermNo.trim());
        
        int tcMchntMapData = t20107BO_2.isExitMchnt(id.trim());
        if (tcMchntMapData == 0) {
            return ErrorCode.T20107_02;
        }
        int deleteData = t20107BO_2.delete(tcMchntMapPK,id.trim());

        if (deleteData > 0) {
            return Constants.SUCCESS_CODE;
        } else {
            return ErrorCode.T20107_03;
        }
    }

    /**
     * 
     * //TODO 更新菜品信息
     *
     * @return
     * @throws Exception
     */
    private String update() throws Exception {
    	 jsonBean.parseJSONArrayData(getMchntDataStr());
         int len = jsonBean.getArray().size();
         List<TcMchntMap> mchntInfoList = new ArrayList<TcMchntMap>();
         for (int i = 0; i < len; i++) {
             jsonBean.setObject(jsonBean.getJSONDataAt(i));
             TcMchntMap tcMchntMap = new TcMchntMap();
             TcMchntMapPK tcMchntMapPK = new TcMchntMapPK();
             BeanUtils.setObjectWithPropertiesValue(tcMchntMap, jsonBean, false);
             BeanUtils.setObjectWithPropertiesValue(tcMchntMapPK, jsonBean, false);
           /*  if("不聚合".equals(tcMchntMap.getMergeFlag())){
            	 tcMchntMap.setMergeFlag("0");
             }*/

             int tcMchntMapData = t20107BO_2.isExitMchnt(tcMchntMap.getId());
             if (tcMchntMapData == 0) {
                 return ErrorCode.T20107_02;
             }
             if("0".equals(tcMchntMapPK.getBrhFlag().trim())|| "1".equals(tcMchntMapPK.getBrhFlag().trim())){
            	 ResourceBundle bundle = ResourceBundle.getBundle(SysParamConstants.CKEY);
                 String midStr = bundle.getString("ckey"); //CKEY
                 tcMchntMap.setcKey(midStr.trim());
              	 tcMchntMap.setBussId("000000");
             } else if("2".equals(tcMchntMapPK.getBrhFlag().trim())){
            	 tcMchntMap.setBussId("000000");
             } else if ("4".equals(tcMchntMapPK.getBrhFlag().trim())) {
 			   	 tcMchntMap.setcKey(tcMchntMap.getPwdId().trim());
             	 tcMchntMap.setBussId("000000");
 		   	 } else if ("5".equals(tcMchntMapPK.getBrhFlag().trim()) || "9".equals(tcMchntMapPK.getBrhFlag().trim())) {
 				 ResourceBundle bundle = ResourceBundle.getBundle(SysParamConstants.CKEY);
 	             String midStr = bundle.getString("ckey"); //CKEY
 	             tcMchntMap.setcKey(midStr.trim());
             	 tcMchntMap.setBussId("000000");
                 tcMchntMap.setTcMchntMapPK(tcMchntMapPK);
 			 } else if ("7".equals(tcMchntMapPK.getBrhFlag().trim())) {
 	         	 tcMchntMap.setBussId(tcMchntMap.getBussId2());
 	 		 }
             tcMchntMap.setTcMchntMapPK(tcMchntMapPK);   
             tcMchntMap.setMergeFlag("0");
             mchntInfoList.add(tcMchntMap);
             
             List<Object[]> findList2 = t20107BO.findList2(tcMchntMapPK.getYlMchntNo(),tcMchntMapPK.getYlTermNo(),tcMchntMap.getId());
             List<Object[]> findList3 = t20107BO.findList3(tcMchntMapPK.getYlMchntNo(),tcMchntMapPK.getYlTermNo());
             if("0".equals(tcMchntMapPK.getScanFlag())){
            	 if (findList3.size() > 1) {
            		 int deleteData = t20107BO_2.delete2(tcMchntMapPK,tcMchntMap.getId());
            		 if (deleteData <= 0) {
            			 return ErrorCode.T20107_03;
            	     }
				 }
             } else if ("1".equals(tcMchntMapPK.getScanFlag())) {
				if (findList3.size() ==3 && (!"1".equals(findList2.get(0)[2]))) {
					return "该银联终端号存在三条记录，必须一条为主扫，两条为被扫!";
				} else if (findList3.size() ==2 && "1".equals(findList3.get(0)[2]) && (!"1".equals(findList2.get(0)[2]))) {
					return "此商户下该银联终端号的主扫方式已经存在!";
				}
			} else if ("2".equals(tcMchntMapPK.getScanFlag())) {
				if (findList3.size() == 3) {
					if(!"2".equals(findList2.get(0)[2])){
						return "该银联终端号存在三条记录，必须一条为主扫，两条为被扫!";
					} else if (("5".equals(tcMchntMapPK.getBrhFlag()) && !"5".equals(findList2.get(0)[3])) || (!"5".equals(tcMchntMapPK.getBrhFlag()) && "5".equals(findList2.get(0)[3]))) {
						return "被扫方式绑定的两条渠道,其中一条为银联!";
					}
				} else if (findList3.size() == 2) {
					if("1".equals(findList3.get(0)[2]) && "1".equals(findList2.get(0)[2])){
						if ("5".equals(tcMchntMapPK.getBrhFlag()) && "5".equals(findList3.get(1)[3])) {
							return "被扫方式下已存在银联支付!";
						} else if (!"5".equals(tcMchntMapPK.getBrhFlag()) && !"5".equals(findList3.get(1)[3])) {
							return "被扫方式绑定的两条渠道,必须一条为银联!";
						}
					} else if ("2".equals(findList3.get(0)[2])) {
						if (("5".equals(tcMchntMapPK.getBrhFlag()) && !"5".equals(findList2.get(0)[3])) || (!"5".equals(tcMchntMapPK.getBrhFlag()) && "5".equals(findList2.get(0)[3]))) {
							return "被扫方式绑定的两条渠道,其中一条为银联!";
						}
					}
				}
			 }
        /*     if("1".equals(tcMchntMap.getMergeFlag()) && tcMchntMapData == 1){
            	 if(tcMchntMap.getcMchntNo() == null){
            		 tcMchntMap.setcMchntNo(tcMchntMapPK.getYlMchntNo());
            	 }
            	 tcMchntMap.setTcMchntMapPK(tcMchntMapPK);
                 int updateDate=t20107BO_2.update(tcMchntMap);
                 if (updateDate > 0) {
                	 return Constants.SUCCESS_CODE;
                 } else {
                	 return ErrorCode.T20107_03;
				}
             }*/            
             
             t20107BO_2.update(tcMchntMap);
         }
         return Constants.SUCCESS_CODE;
    }
    /**
     * 检查是否存在证书
     * @throws Exception
     */
    private String isExistYLCert() throws Exception {
    	HashMap<String, Object> hashMap = new HashMap<String, Object>();
    	JSONObject resultMap=null;
        String postStr = null;
        hashMap.put("mchnt_id",ylMchntNo.trim());
        String paramStr = StaticUtils.mapToStr(hashMap);
        System.out.println(paramStr.toString());
        ResourceBundle bundle = ResourceBundle.getBundle(SysParamConstants.CKEY);
     	String addrInterface = bundle.getString("addrInterface");
     	String isExistCert = bundle.getString("isExistCert");
        postStr = APIHttpClient.httpsRequest(addrInterface+isExistCert, "POST", paramStr.toString());
        System.out.println(postStr.toString());
        resultMap=StaticUtils.jsonToMap(postStr.toString());
        if("4000".equals(resultMap.get("result_code"))){
        	return (String) resultMap.get("cerName");
        } else {
			return Constants.SUCCESS_CODE;
		}
    }
   
    public T20107BO getT20107BO() {
        return t20107BO;
    }

    public void setT20107BO(T20107BO t20107bo) {
        t20107BO = t20107bo;
    }

    public String getTermFixNo() {
        return termFixNo;
    }

    public void setTermFixNo(String termFixNo) {
        this.termFixNo = termFixNo;
    }

    public String getCmchntNo() {
        return cmchntNo;
    }

    public void setCmchntNo(String cmchntNo) {
        this.cmchntNo = cmchntNo;
    }

	public String getCkey() {
		return ckey;
	}

	public void setCkey(String ckey) {
		this.ckey = ckey;
	}

	public String getYlMchntNo() {
        return ylMchntNo;
    }

    public void setYlMchntNo(String ylMchntNo) {
        this.ylMchntNo = ylMchntNo;
    }

    public String getYlTermNo() {
        return ylTermNo;
    }

    public void setYlTermNo(String ylTermNo) {
        this.ylTermNo = ylTermNo;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getMchntDataStr() {
        return mchntDataStr;
    }

    public void setMchntDataStr(String mchntDataStr) {
        this.mchntDataStr = mchntDataStr;
    }


    public String getEnableFlag1() {
		return enableFlag1;
	}

	public void setEnableFlag1(String enableFlag1) {
		this.enableFlag1 = enableFlag1;
	}

	public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getBrhFlag() {
        return brhFlag;
    }

    public void setBrhFlag(String brhFlag) {
        this.brhFlag = brhFlag;
    }

	public String getPwdId() {
		return pwdId;
	}

	public void setPwdId(String pwdId) {
		this.pwdId = pwdId;
	}

	public String getResv1() {
		return resv1;
	}

	public void setResv1(String resv1) {
		this.resv1 = resv1;
	}
	public String getBussId() {
		return bussId;
	}

	public void setBussId(String bussId) {
		this.bussId = bussId;
	}

	public String getPwdIdYL() {
		return pwdIdYL;
	}

	public void setPwdIdYL(String pwdIdYL) {
		this.pwdIdYL = pwdIdYL;
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

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

/*	public String getMergeFlag() {
		return mergeFlag;
	}

	public void setMergeFlag(String mergeFlag) {
		this.mergeFlag = mergeFlag;
	}*/

	public String getBussId2() {
		return bussId2;
	}

	public void setBussId2(String bussId2) {
		this.bussId2 = bussId2;
	}

	public String getPayMchntNo() {
		return payMchntNo;
	}

	public void setPayMchntNo(String payMchntNo) {
		this.payMchntNo = payMchntNo;
	}

	public String getUploadCert() {
		return uploadCert;
	}

	public void setUploadCert(String uploadCert) {
		this.uploadCert = uploadCert;
	}
	
	public String getScanFlag() {
		return scanFlag;
	}

	public void setScanFlag(String scanFlag) {
		this.scanFlag = scanFlag;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
