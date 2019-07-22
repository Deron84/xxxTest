package com.sdses.struts.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;



public class APIHttpClient {
	
	private static final Log logger = LogFactory.getLog(APIHttpClient.class);	
	
	private String apiURL = "";
	
	private HttpClient httpClient = null;

	private HttpPost method = null;

    private long startTime = 0L;

    private long endTime = 0L;
    
    private int status = 0;
	
    /**
     * 接口地址
     * 
     * @param url
     */
    public APIHttpClient(String url) {

        if (url != null) {
            this.apiURL = url;
        }
        if (apiURL != null) {
            httpClient = HttpClients.createDefault();
            method = new HttpPost(apiURL);
        }
    }
	public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) throws Exception {
		 HttpsURLConnection.setDefaultHostnameVerifier(new NullHostNameVerifier());
	     SSLContext sc = SSLContext.getInstance("TLSv1.2");
	     TrustManager[] tm = { new MyX509TrustManager() };
	     sc.init(null, tm, new SecureRandom());
	     HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	     URL url = new URL(requestUrl);
	     // 打开restful链接
	     HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	     conn.setRequestMethod(requestMethod);// POST GET PUT DELETE
	     conn.setRequestProperty("Content-type", "application/x-java-serialized-object");
	     // 设置访问提交模式，表单提交
	     conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
	     conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	     //        conn.setConnectTimeout(5000);// 连接超时 单位毫秒
	     //        conn.setReadTimeout(5000);// 读取超时 单位毫秒
	     conn.setDoOutput(true);
	     // 当outputStr不为null时向输出流写数据
	     if (null != outputStr) {
	    	 OutputStream outputStream = conn.getOutputStream();
	    	// 注意编码格式
	        //   StringEntity stringEntity = new StringEntity(outputStr);

	        outputStream.write(outputStr.getBytes("UTF-8"));
	        outputStream.close();
	     }
	     InputStream inputStream = conn.getInputStream();
	     InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
	     BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	     String str = null;
	     StringBuffer buffer = new StringBuffer();
	     while ((str = bufferedReader.readLine()) != null) {
	            buffer.append(str);
	     }
	     // 释放资源
	     bufferedReader.close();
	     inputStreamReader.close();
	     inputStream.close();
	     inputStream = null;
	     conn.disconnect();
	     return buffer.toString();
	 }
	 /**
	     * 调用 API
	     * 
	     * @param parameters
	     * @return
	     */
	    public String post(List<NameValuePair> parameters) {
	        String body = null;
	        logger.info("parameters:" + parameters);

	        if (method != null & parameters != null && (!(parameters.size() < 0))) {
	            try {

	                method.setEntity(new UrlEncodedFormEntity(parameters, StandardCharsets.UTF_8));

	                startTime = System.currentTimeMillis();
	                RequestConfig requestConfig = RequestConfig.custom()
	                        //      .setConnectTimeout(5000).setConnectionRequestTimeout(5000).setSocketTimeout(5000)
	                        .build();
	                method.setConfig(requestConfig);
	                // 设置编码
	                HttpResponse response = httpClient.execute(method);
	                endTime = System.currentTimeMillis();
	                int statusCode = response.getStatusLine().getStatusCode();
	                System.out.println("statusCode:" + statusCode);
	                System.out.println("调用API 花费时间(单位：毫秒)：" + (endTime - startTime));
	                if (statusCode != HttpStatus.SC_OK) {
	                    logger.error("Method failed:" + response.getStatusLine());
	                    status = 1;
	                }
	                // Read the response body
	                body = EntityUtils.toString(response.getEntity());
	            } catch (IOException e) {
	                e.printStackTrace();
	                // 发生网络异常
	                // logger.error("exception occurred!\n"+ExceptionUtils.getFullStackTrace(e));
	                // 网络错误
	                status = 3;
	            } finally {
	                System.out.println("调用接口状态:" + status);
	            }

	        }
	        System.out.println(body);
	        return body;
	    }
}
