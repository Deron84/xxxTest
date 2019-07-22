package com.sdses.struts.util;

import java.util.Iterator;
import java.util.Map;
import com.alibaba.fastjson.JSONObject;

public class StaticUtils {
	 /**
	  * //TODO map转string报文
	  *
	  * @param request
	  * @return
	  */     
	 public static String mapToStr(Map<String, Object> hashMap){
		 Iterator<Map.Entry<String, Object>> it = hashMap.entrySet().iterator();
		 StringBuffer paramStr = new StringBuffer("");
		 while (it.hasNext()) {
			 @SuppressWarnings("rawtypes")
	         Map.Entry element = it.next();
	         paramStr.append(element.getKey());
	         paramStr.append("=");
	         paramStr.append(element.getValue());
	         paramStr.append("&");
	      }
		 return paramStr.toString();
     }
	 public static JSONObject jsonToMap(String json) {
		 if (json == null)
			 return null;
		 JSONObject midObj = JSONObject.parseObject(json);
		 return midObj;
    }
     public static String ListToSt(ResponseBean bean){
 		StringBuffer sb = new StringBuffer();
    	sb.append("返回码:"+bean.getResult_code()+"\n");
    	sb.append("民生商户号:"+bean.getCmbcMchntId()+"\n");
    	sb.append("返回结果:"+bean.getResult_msg()+"\n");
    	sb.append("外部商户号:"+bean.getOutMchntId()+"\n");
    	sb.append("返回信息:"+bean.getError_msg()+"\n");
		return sb.toString();
		 
	 }
     public static String ListToSt2(ResponseBean bean){
  		StringBuffer sb = new StringBuffer();
     	sb.append("返回码:"+bean.getResult_code()+"\n");
     	sb.append("民生商户号:"+bean.getCmbcMchntId()+"\n");
     	sb.append("返回结果:"+bean.getResult_msg()+"\n");
     	sb.append("外部商户号:"+bean.getOutMchntId()+"\n");
     	sb.append("民生签约编号:"+bean.getCmbcSignId()+"\n");
     	sb.append("返回信息:"+bean.getError_msg()+"\n");
 		return sb.toString();
 		 
 	 }
     public static String mapToResponse(Map<String, Object> map){
    	 StringBuffer sb = new StringBuffer();
      	sb.append("返回码:"+map.get("result_code")+"\n");
      	sb.append("返回结果:"+map.get("result_msg")+"\n");
      	sb.append("返回信息:"+map.get("error_msg")+"\n");
      	sb.append("民生商户号:"+map.get("cmbcMchntId")+"\n");
      	sb.append("外部商户号:"+map.get("outMchntId")+"\n");
  		return sb.toString(); 
     }
}
