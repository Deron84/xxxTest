/**
 * 
 */
package com.huateng.system.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * @author shuke.hu
 * @date 2015年11月24日
 * 
 */
public class HttpHandler {
	

	public static String getRemoteHost(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
	}

	/**
	 * httpPost
	 * 
	 * @param url
	 *            路径
	 * @param jsonParam
	 *            参数
	 * @return
	 */
	public static JSONObject httpPost(String url, JSONObject jsonParam) {
		return httpPost(url, jsonParam, null, false);
	}
	
	/**
	 * httpPost
	 * 
	 * @param url
	 *            路径
	 * @param jsonParam
	 *            参数
	 * @return
	 */
	public static JSONObject httpPost(String url, Map<String, String> paramMap) {
		return httpPost(url, null, paramMap, false);
	}
	
	public static JSONObject httpPost(String url, Map<String, String> paramMap, boolean noNeedResponse) {
		return httpPost(url, null, paramMap, noNeedResponse);
	}

	/**
	 * post请求
	 * 
	 * @param url
	 *            url地址
	 * @param jsonParam
	 *            参数
	 * @param noNeedResponse
	 *            不需要返回结果
	 * @return
	 */
	public static JSONObject httpPost(String url, JSONObject jsonParam, Map<String, String> paramMap,
			boolean noNeedResponse) {
		JSONObject jsonResult = null;
		
		CloseableHttpResponse result = null;
		try {
			// post请求返回结果
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			//logger.info("发起post请求，地址为{}", url);
			HttpPost method = new HttpPost(url);
			if (null != jsonParam) {
				// 解决中文乱码问题
				StringEntity entity = new StringEntity(
						jsonParam.toJSONString(), "utf-8");
				entity.setContentEncoding("UTF-8");
				entity.setContentType("application/json");
				method.setEntity(entity);
			}else if(paramMap != null){
				List<NameValuePair> formparams = new ArrayList<NameValuePair>();
				for (String key : paramMap.keySet()) {
					formparams.add(new BasicNameValuePair(key, paramMap.get(key)));
				}
				HttpEntity entity = new UrlEncodedFormEntity(formparams, "utf-8");
				method.setEntity(entity);
			}
			
			url = URLDecoder.decode(url, "UTF-8");
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(3000).setConnectTimeout(2000).build();// 设置请求和传输超时时间
			method.setConfig(requestConfig);
			result = httpClient.execute(method);
			/** 请求发送成功，并得到响应 **/
			//logger.info("请求结果状态码为{}", result.getStatusLine().getStatusCode());
			if (result.getStatusLine().getStatusCode() == 200) {
				/** 读取服务器返回过来的json字符串数据 **/
				String str = EntityUtils.toString(result.getEntity());
				if (noNeedResponse) {
					return null;
				}
				/** 把json字符串转换成json对象 **/
				jsonResult = JSONObject.parseObject(str);
				//logger.info("post请求成功返回,{}", jsonResult.toJSONString());
			}
		} catch (IOException e) {
			//logger.error("post请求提交失败:{}", url, e);
		}finally{
			if(result != null){
				try {
					result.close();
				} catch (IOException e) {
				}
			}
		}
		return jsonResult;
	}
	
	/**
	 * 发送get请求
	 * 
	 * @param url
	 *            路径
	 * @return
	 */
	public static JSONObject httpGet(String url) {
		// get请求返回结果
		JSONObject jsonResult = null;
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			// 发送get请求
			HttpGet request = new HttpGet(url);
			HttpResponse response = httpClient.execute(request);

			/** 请求发送成功，并得到响应 **/
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				/** 读取服务器返回过来的json字符串数据 **/
				String strResult = EntityUtils.toString(response.getEntity());
				/** 把json字符串转换成json对象 **/
				jsonResult = JSONObject.parseObject(strResult);
				url = URLDecoder.decode(url, "UTF-8");
			} else {
				//logger.error("get请求提交失败:{}", url);
			}
		} catch (IOException e) {
			//logger.error("get请求提交失败:{}", url, e);
		}
		return jsonResult;
	}

	/**
     * post方式请求服务器(https协议)
     * 
     * @param url
     *            请求地址
     * @param content
     *            参数
     * @param charset
     *            编码
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws IOException
     */
    public static byte[] httpsPost(String url, String content, String charset) {
        try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());
			URL console = new URL(url);
			HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
			conn.setSSLSocketFactory(sc.getSocketFactory());
			conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
			conn.setDoOutput(true);
			conn.connect();
			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			out.write(content.getBytes(charset));
			// 刷新、关闭
			out.flush();
			out.close();
			InputStream is = conn.getInputStream();
			if (is != null) {
			    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			    byte[] buffer = new byte[1024];
			    int len = 0;
			    while ((len = is.read(buffer)) != -1) {
			        outStream.write(buffer, 0, len);
			    }
			    is.close();
			    return outStream.toByteArray();
			}
		} catch (Exception e) {
			//logger.error("发起SSL请求异常", e);
		}
        return null;
    }
    
    private static class TrustAnyTrustManager implements X509TrustManager {
    	 
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }
 
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }
 
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[] {};
        }
    }
 
    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}
