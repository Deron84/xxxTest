package com.huateng.ftp;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.util.StringTokenizer;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;

import sun.net.TelnetInputStream;
import com.huateng.common.StringUtil;
import com.huateng.struts.system.action.BaseAction;

/**
 * @author zoujunqing
 * 
 */
public class FtpUtil {
	private String ip = "";
	private String username = "";
	private String password = "";
	private int port = -1;
	private String path = "";
	public FTPClient FTPClient = null;
	OutputStream os = null;
	FileInputStream is = null;
	private static Logger log = Logger.getLogger(FtpUtil.class);

	public FtpUtil(String serverIP, String username, String password) {
		this.ip = serverIP;
		this.username = username;
		this.password = password;
	}

	public FtpUtil(String serverIP, String port, String username, String password) {
		this.ip = serverIP;
		this.username = username;
		this.password = password;
		if(!StringUtil.isEmpty(port))
		this.port = Integer.parseInt(port);
	}

	/**
	 * 连接ftp服务器
	 * @throws Exception 
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("static-access")
	public boolean connectServer() throws Exception {
		FTPClient = new FTPClient();
		try {
			FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_UNIX);
			FTPClient.configure(conf);
			if (this.port != -1) {
				FTPClient.connect(this.ip, this.port);
			} else {
				FTPClient.connect(this.ip);
			}
			FTPClient.enterLocalPassiveMode();	//被动模式
//			FTPClient.enterLocalActiveMode();	//主动模式
			
//			System.out.println("PassivePort："+FTPClient.getPassivePort());
//			System.out.println("RemotePort："+FTPClient.getRemotePort());
//			System.out.println("DefaultPort："+FTPClient.getDefaultPort());
//			System.out.println("LocaltPort："+FTPClient.getLocalPort());

			FTPClient.login(this.username, this.password);
			
			if (this.path.length() != 0) {
				FTPClient.cwd(this.path);
			}
			FTPClient.setFileType(FTPClient.BINARY_FILE_TYPE);
//			Log.log("已登录到\"" + FTPClient.printWorkingDirectory() + "\"目录");
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("FTP连接错误！");
			//return false;
		}
	}

	/**
	 * 断开与ftp服务器连接
	 * 
	 * @throws IOException
	 */
	public boolean closeServer() {
		try {
			if (is != null) {
				is.close();
			}
			if (os != null) {
				os.close();
			}
			if (FTPClient != null) {
				FTPClient.logout();
			}
//			Log.log("已从服务器断开");
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 检查文件夹在当前目录下是否存在
	 * 
	 * @param dir
	 *@return
	 */
	private boolean isDirExist(String dir) {
//		String pwd = "";
		try {
			if(FTPClient.changeWorkingDirectory(dir)){
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	/**
	 * 在当前目录下创建文件夹
	 * 
	 * @param dir
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	private boolean createDir(String dir) {
		try {
			
			log("dir:"+dir);
			FTPClient.setFileType(FTPClient.ASCII_FILE_TYPE);
			StringTokenizer s = new StringTokenizer(dir, "/"); // sign
			s.countTokens();
			String pathName = "";//FTPClient.pwd();
			while (s.hasMoreElements()) {
				pathName = pathName + "/" + (String) s.nextElement();
				try {
					FTPClient.mkd(pathName+ "\r\n");
					log(pathName);
				} catch (Exception e) {
					e.printStackTrace();
					e = null;
					return false;
				}
			}
			FTPClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			return true;
		} catch (IOException e1) {
			e1.printStackTrace();
			return false;
		}
	}

	/**
	 * ftp上传 如果服务器段已存在名为filename的文件夹，该文件夹中与要上传的文件夹中同名的文件将被替换
	 * 
	 * @param filename
	 *            要上传的文件（或文件夹）名
	 * @return
	 * @throws Exception
	 */
	/*public boolean upload(String filename) {
		String newname = "";
		if (filename.indexOf("/") > -1) {
			newname = filename.substring(filename.lastIndexOf("/") + 1);
		} else {
			newname = filename;
		}
		return upload(filename, newname);
	}*/

	/**
	 * ftp上传 如果服务器段已存在名为newName的文件夹，该文件夹中与要上传的文件夹中同名的文件将被替换
	 * 
	 * @param fileName
	 *            要上传的文件（或文件夹）名
	 * @param newName
	 *            服务器段要生成的文件（或文件夹）名
	 * @return
	 */
	/*public boolean upload(String fileName, String newName) {
		try {
			String savefilename = new String(fileName);
			File file_in = new File(savefilename);// 打开本地待长传的文件
			if (!file_in.exists()) {
				throw new Exception("此文件或文件夹[" + file_in.getName() + "]有误或不存在!");
			}
			if (file_in.isDirectory()) {
				upload(file_in.getPath(), newName, FTPClient.pwd());
			} else {
				uploadFile(file_in.getPath(), newName);
			}
			if (is != null) {
				is.close();
			}
			if (os != null) {
				os.close();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Exception e in Ftp upload(): " + e.toString());
			return false;
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}*/

	/**
	 * 真正用于上传的方法
	 * 
	 * @param fileName
	 * @param newName
	 * @param path
	 * @throws Exception
	 */
	/*private void upload(String fileName, String newName, String path)
			throws Exception {
		String savefilename = new String(fileName);
		File file_in = new File(savefilename);// 打开本地待长传的文件
		Log.log("file_in:"+file_in);
		if (!file_in.exists()) {
			throw new Exception("此文件或文件夹[" + file_in.getName() + "]有误或不存在!");
		}
		Log.log("path:"+path);
		Log.log("file_in.isDirectory:"+file_in.isDirectory());
		if (file_in.isDirectory()) {
			if (!isDirExist(newName)) {
				createDir(newName);
			}
			FTPClient.cd(newName);
			File sourceFile[] = file_in.listFiles();
			for (int i = 0; i < sourceFile.length; i++) {
				if (!sourceFile[i].exists()) {
					continue;
				}
				if (sourceFile[i].isDirectory()) {
					this.upload(sourceFile[i].getPath(), sourceFile[i]
							.getName(), path + "/" + newName);
				} else {
					this.uploadFile(sourceFile[i].getPath(), sourceFile[i]
							.getName());
				}
			}
		} else {
			uploadFile(file_in.getPath(), newName);
		}
		FTPClient.cd(path);
	}*/

	/**
	 * upload 上传文件
	 * 
	 * @param filename
	 *            要上传的文件名
	 * @param newname
	 *            上传后的新文件名
	 * @return -1 文件不存在 >=0 成功上传，返回文件的大小
	 * @throws Exception
	 */
	public long upload(String filename, String newname) throws Exception {
		long result = 0;
		FileInputStream is = null;
		try {
			log("filename:"+filename);
			java.io.File file_in = new java.io.File(filename);
			log("newname:"+newname);
			
			System.out.println("**********要写入FTP的文件名是否存在：" + file_in.exists());//20121105
			if (!file_in.exists())
				return -1;
			
			System.out.println("**********要写入FTP的目录是否存在：" + isDirExist(newname.substring(0,newname.lastIndexOf("/")+1)));//20121105
			if (!isDirExist(newname.substring(0,newname.lastIndexOf("/")+1))) {//?
				createDir(newname.substring(0,newname.lastIndexOf("/")+1));
			}
			log(newname);
			//进入到要存放文件的目录
			boolean result1 = FTPClient.changeWorkingDirectory(newname.substring(0,newname.lastIndexOf("/")+1));
			System.out.println("**********是否进入要存放文件的目录：" + result1);//20121105
			
			is = new FileInputStream(file_in);
			result = file_in.length();
			boolean result2 = FTPClient.storeFile(newname.substring(newname.lastIndexOf("/") + 1), is);
			System.out.println("**********是否写入文件成功：" + result2);//20121105
		} finally {
			if (is != null) {
				is.close();
			}
		}
		return result;
	}
	public boolean ftpFileIsExist(String filename) throws Exception{
		boolean isExist=false;
		log("file:"+filename);
		FTPClient.changeWorkingDirectory(filename.substring(0,filename.lastIndexOf("/")+1));
		FTPFile[] ftpFiles=FTPClient.listFiles(filename.substring(filename.lastIndexOf("/") + 1));
		if(ftpFiles.length>0)//判断返回列表大小，为0就表示没有。列表返回的是匹配文件名的文件，其大小就是文件数目，为0就表示没有。这里的0并不是文件大小。
			isExist=true;
		return isExist;
	}
	/**
	 * 从ftp下载文件到本地
	 * 
	 * @param filename
	 *            服务器上的文件名
	 * @param newfilename
	 *            本地生成的文件名
	 * @return
	 * @throws Exception
	 */
	public long downloadFile(String filename, String newfilename) {
		long result = 0;
//		TelnetInputStream is = null;
		InputStream in = null;
		FileOutputStream os = null;
		try {
			log("filename:"+filename);
			log("newfilename:"+newfilename);
			FTPClient.changeWorkingDirectory(filename.substring(11,filename.lastIndexOf("/")+1));
			log("pwd:"+FTPClient.printWorkingDirectory());
			java.io.File outfile = new java.io.File(newfilename);
			
			
			if (!outfile.getParentFile().exists()) {
				outfile.getParentFile().mkdirs();
			}
			if (outfile.exists()) {
				outfile.delete();
			}
			os = new FileOutputStream(outfile);
			FTPClient.retrieveFile(filename.substring(filename.lastIndexOf("/") + 1), os);
			//==================================
//			in = FTPClient.retrieveFileStream(filename.substring(filename.lastIndexOf("/") + 1));
//			if (in != null) {
//                BufferedReader br = new BufferedReader(new InputStreamReader(
//                        in, "GB2312"));
//                String data = null;
//                while ((data = br.readLine()) != null) {
//                    result.append(data + "#");
//                }
//            } else {
//                logger.error("读取文件出错!");
//                throw new CommonException("读取文件出错!");
//            }
			//=============================
//			byte[] bytes = new byte[1024];
//			int c;
//			while ((c = is.read(bytes)) != -1) {
//				os.write(bytes, 0, c);
//				result = result + c;
//			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

/*  by Mike Test
	public long downloadFile(String filename, String newfilename) {
		long result = 0;
		TelnetInputStream is = null;
		FileOutputStream os = null;
		try {
			Log.log("filename:"+filename);
			Log.log("newfilename:"+newfilename);
			//boolean mikeFlag=FTPClient.changeWorkingDirectory(new String("/home/posp/online/file/Rpt/20130911/".getBytes(),"UTF-8"));
			boolean mikeFlag=FTPClient.changeWorkingDirectory("online/file/Rpt/20130911/");
			System.out.println("===MikeFlag"+mikeFlag);
			Log.log("pwd:"+FTPClient.printWorkingDirectory());
			//is = FTPClient.get(filename);
			java.io.File outfile = new java.io.File(newfilename);
			
			
			if (!outfile.getParentFile().exists()) {
				outfile.getParentFile().mkdirs();
			}
			if (outfile.exists()) {
				outfile.delete();
			}
			os = new FileOutputStream(outfile);
//			FTPClient.retrieveFile(filename.substring(filename.lastIndexOf("/") + 1), os);
			FTPClient.retrieveFile("dayRisk_20130911.xls",os);
			/*byte[] bytes = new byte[1024];
			int c;
			while ((c = is.read(bytes)) != -1) {
				os.write(bytes, 0, c);
				result = result + c;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	*/
	protected void log(String info) {
		log.info(info);
	}

	/**
	 * 取得相对于当前连接目录的某个目录下所有文件列表
	 * 
	 * @param path
	 * @return
	 */
	/*public List getFileList(String path) {
		List list = new ArrayList();
		DataInputStream dis;
		try {
			dis = new DataInputStream(FTPClient.nameList(this.path + path));
			String filename = "";
			while ((filename = dis.readLine()) != null) {
				list.add(filename);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}*/

	public static void main(String[] args) {
//		FtpUtil ftp = new FtpUtil("192.168.1.10","-1", "posp", "posp");
		
	//	ftp.connectServer();
		/*boolean result = ftp.upload(
				"C:/Documents and Settings/ipanel/桌面/java/Hibernate_HQL.docx",
				"amuse/audioTest/music/Hibernate_HQL.docx");*/
		//System.out.println(result ? "上传成功！" : "上传失败！");
//		boolean mikeFlag=false;
//		try {
//			ftp.connectServer();
//			mikeFlag = ftp.FTPClient.changeWorkingDirectory("online/file/Rpt/20130911/");
//			System.out.println("目录："+ftp.FTPClient.printWorkingDirectory());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println("==:"+mikeFlag);
//		
//		FileOutputStream os;
//		try {
//			os = new FileOutputStream("d://tt.xls");
//			System.out.println("==录："+ftp.FTPClient.printWorkingDirectory());
//			System.out.println(ftp.FTPClient.retrieveFile("dayRisk20130911.xls",os));
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//		
//		
//		ftp.closeServer();
		/**
		 * FTP远程命令列表 USER PORT RETR ALLO DELE SITE XMKD CDUP FEAT PASS PASV STOR
		 * REST CWD STAT RMD XCUP OPTS ACCT TYPE APPE RNFR XCWD HELP XRMD STOU
		 * AUTH REIN STRU SMNT RNTO LIST NOOP PWD SIZE PBSZ QUIT MODE SYST ABOR
		 * NLST MKD XPWD MDTM PROT
		 * 在服务器上执行命令,如果用sendServer来执行远程命令(不能执行本地FTP命令)的话，所有FTP命令都要加上\r\n
		 * FTPClient.sendServer("XMKD /test/bb\r\n"); //执行服务器上的FTP命令
		 * FTPClient.readServerResponse一定要在sendServer后调用
		 * nameList("/test")获取指目录下的文件列表 XMKD建立目录，当目录存在的情况下再次创建目录时报错 XRMD删除目录
		 * DELE删除文件
		 */
	}

}
