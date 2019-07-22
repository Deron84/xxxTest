package com.huateng.system.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class EncryptUtils {
	
	private static final String KEY = "96D0028878D58C89";
	
	private static final String DES = "DES";
	
	private static byte[] encrypt (byte[] src, byte[] key) throws Exception{
		
		SecureRandom sr = new SecureRandom();
		
		DESKeySpec keySpec = new DESKeySpec(key);
		
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey secretKey = keyFactory.generateSecret(keySpec);
		
		Cipher cipher = Cipher.getInstance(DES);
		
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);
		
		
		return cipher.doFinal(src);
	}
	
	private static byte[] decrypt (byte[] src, byte[] key) throws Exception{
		
		SecureRandom sr = new SecureRandom();
		
		DESKeySpec keySpec = new DESKeySpec(key);
		
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey secretKey = keyFactory.generateSecret(keySpec);
		
		Cipher cipher = Cipher.getInstance(DES);
		
		cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);
		
		
		return cipher.doFinal(src);
	}
	
	
	public final static String encrypt(String data) throws Exception{
		
		return byte2hex(encrypt(data.getBytes(), KEY.getBytes()));
		
	}
	
	public final static String decrypt(String data) throws Exception{
		
		return new String(decrypt(hex2byte(data.getBytes()), KEY.getBytes()));
		
	}
	
	private static String byte2hex(byte[] bArray){
		
		StringBuffer sb = new StringBuffer();

		String stmp = "";
		for (int n = 0; n < bArray.length; n++) {
			stmp = java.lang.Integer.toHexString(bArray[n] & 0xFF);
			if (stmp.length() == 1) {
				sb.append("0").append(stmp);
			} else {
				sb.append(stmp);
			}
		}
		
	    return sb.toString().toUpperCase();
	}
	
	private static byte[] hex2byte(byte[] bArray) {
		
		if (bArray.length % 2 != 0) {
			throw new IllegalArgumentException("长度不是偶数");	
		}
		byte[] b = new byte[bArray.length / 2];
		
		for (int n = 0; n < bArray.length; n += 2) {
			String item = new String(bArray, n, 2);
			b[n/2] = (byte)Integer.parseInt(item, 16);
		}
		return b;
	}
}
