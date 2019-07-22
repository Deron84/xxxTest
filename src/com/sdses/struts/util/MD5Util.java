package com.sdses.struts.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class MD5Util {
	 private static Log log = LogFactory.getLog(MD5Util.class);
	 /**
     * 计算文件的MD5码
     * @param file
     * @return
     */
    public static String getMD5(File file) {
        FileInputStream fis = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            fis = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int length = -1;
            log.info("--> Calculate Start------- ");
            while ((length = fis.read(buffer)) != -1) {
                md.update(buffer, 0, length);
            }
            log.info("--> Calculate End---- ");
            return bytesToString(md.digest());
        } catch (IOException ex) {
            Logger.getLogger(MD5Util.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(MD5Util.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(MD5Util.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public static String bytesToString(byte[] b) {
        StringBuffer midVar = new StringBuffer("");
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            midVar.append(hex.toLowerCase());
        }
        return midVar.toString();
    }
}
