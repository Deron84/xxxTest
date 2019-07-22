package com.rail.bo.pub;  
/**  
* @author syl
* @version 创建时间：2019年5月31日 下午1:57:30  
* @desc
*/
public class FileTypeUtil {
	
	public static boolean isImg(String fileType){
		String imgTypes = "bmp,jpg,jpeg,png,tif,gif,pcx,tga,exif,fpx,svg,psd,cdr,pcd,dxf,ufo,eps,ai,raw,WMF,webp";
		if(imgTypes.indexOf(fileType)== -1 ){
			return false;
		}
		return true;
	}
	public static boolean isVideo(String fileType){
		String vedioTypes  =  "mp4|flv|avi|rm|rmvb|wmv";
		if(vedioTypes.indexOf(fileType)== -1 ){
			return false;
		}
		return true;
	}
	

}

