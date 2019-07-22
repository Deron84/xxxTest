
package com.huateng.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import com.huateng.common.Constants;
import com.huateng.system.util.SysParamUtil;

import sun.net.TelnetInputStream;
import sun.net.ftp.FtpClient;


public class Download {
	
	private static String ftpIP = SysParamUtil.getParam("ftpIP");
	private static String userName = SysParamUtil.getParam("ftpName");
	private static String passWord = SysParamUtil.getParam("ftpPwd");
	private static Logger log = Logger.getLogger(Download.class);
	
	public static void fileDown( String downUrl, String fileName,String fileUrl) throws Exception {
		InputStream fget = null;
		FtpClient fc = null;

		try {
			if (!new File(downUrl).isDirectory()) {// 判断本地存放文件的文件夹是否存在
				new File(downUrl).mkdirs();
			}
			
			/*fc = new FtpClient();// ftp客户端对象
			fc.openServer(ftpIP);// 连接ftp服务器
			fc.login(userName, passWord);// 登录ftp服务器
			fc.binary();// 使用二进制的方式下载
*/
			fc = FtpClient.create(ftpIP);
			fc.login(userName, null, passWord); 
			fc.setBinaryType();
			
			fget = fc.getFileStream(fileUrl+fileName);// 读取ftp远程文件
			
			 byte[] buf = new byte[204800];
	         int bufsize = 0;
	         FileOutputStream  ftpOut = new FileOutputStream(downUrl+fileName);//存放在本地硬盘的物理位置
	         while ((bufsize = fget.read(buf, 0, buf.length)) != -1) {
	        	 ftpOut.write(buf, 0, bufsize);
	         }
		} catch (Exception e) {
			e.printStackTrace(System.out);
			throw new Exception(e.getMessage());
		} finally {
				fget.close();
				fc.close();
		}
	}
	
	public static boolean check(String fileUrl,String fileName) {
		FTPClient ftp=new FTPClient();
		try {
			ftp.connect(ftpIP);
			ftp.login(userName, passWord);
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			if(!ftp.changeWorkingDirectory(fileUrl)){
				ftp.logout();
				ftp.disconnect();
				return false;
			}
			FTPFile[] fs=ftp.listFiles();
			for (FTPFile file : fs) {
				if(fileName.equals(file.getName())){
					ftp.logout();
					ftp.disconnect();
					return true;
				}
			}
			ftp.logout();
			ftp.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static String FTPDownLoad(String fileUrl,String fileName,String downUrl) {
		String encoding = System.getProperty("file.encoding"); 
		log("fileEncode:"+encoding);
		FTPClient ftp=new FTPClient();
		
		try {
			ftp.connect(ftpIP);
			ftp.login(userName, passWord);
			
			ftp.setBufferSize(1024);
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftp.setControlEncoding(encoding);
			FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);   
	        conf.setServerLanguageCode("zh"); 

	        // 连接后检测返回码来校验连接是否成功
			if(!FTPReply.isPositiveCompletion(ftp.getReplyCode())){
				ftp.disconnect();
			}
			
			//找到文件所在目录
			if(!ftp.changeWorkingDirectory(fileUrl)){
				ftp.logout();
				ftp.disconnect();
				return "该日未生成文件！";
			}
			
			
			boolean boo=false;
			FTPFile[] fs=ftp.listFiles();
			for (FTPFile file : fs) {
				if(fileName.equals(file.getName())){
					boo=true;
					break;
				}
			}
			if(!boo){
				return "不存在该日期的文件！";
			}
			
			// 判断本地存放文件的文件夹是否存在
			if (!new File(downUrl).isDirectory()) {
				new File(downUrl).mkdirs();
			}
			
			FileOutputStream fos = new FileOutputStream(downUrl + fileName);
			
			ftp.retrieveFile(new String((fileUrl + fileName).getBytes(encoding),"ISO-8859-1"),fos);
			
			fos.flush();
			fos.close();
			
			ftp.logout();
			if(ftp.isConnected()){
				ftp.disconnect();
			}
			
			return Constants.SUCCESS_CODE_CUSTOMIZE + downUrl + fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "对不起，FTP获取文件失败！";
	}
	
	/**
	 * 记录系统日志
	 * @param info
	 */
	protected static void log(String info) {
		log.info(info);
	}
}
