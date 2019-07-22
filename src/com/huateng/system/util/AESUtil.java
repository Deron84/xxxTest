package com.huateng.system.util;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * 类描述：AES加解密处理 com.watcher.util AESUtil Created by 78098 on 2016年7月1日. version
 * 1.0
 */
public class AESUtil {
    private String algorithm = "AES";

    private String charset = "UTF-8";

    private int keyLength = 128;

    int index = 20;

    String pk = "SDSES+" + this.index;

    public String getRealCount(String encryptedStr) {
        try {
            String defaultInfo = new String(decryptByAES(parseHexStr2Byte(encryptedStr), this.pk), charset);
            String header = defaultInfo.split(",")[0];
            header = new String(decryptByAES(parseHexStr2Byte(header), this.pk), charset);
            int index = Integer.parseInt(header) - this.index;
            return defaultInfo.split(",")[index];
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * AES加密
     * 
     * @param content
     *            String,要加密的串
     * @param secretKey
     *            String,加密密钥
     * @return 加密后的字节数组
     */
    public byte[] encryptByAES(String content, String secretKey) {
        try {
            byte[] encodeKey = initSecretKey(secretKey);
            SecretKeySpec keySpec = new SecretKeySpec(encodeKey, algorithm);// 可以使用此类来根据一个字节数组构造一个
                                                                            // SecretKey
            Cipher cipher = Cipher.getInstance(algorithm);// Cipher完成加密
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            return cipher.doFinal(content.getBytes(charset));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES解密
     * 
     * @param content
     *            byte[],要解密的字节数组
     * @param secretKey
     *            String,加密密钥
     * @return 解密后的字节数组
     */
    public byte[] decryptByAES(byte[] content, String secretKey) {
        try {
            byte[] encodeKey = initSecretKey(secretKey);
            for (byte b : encodeKey) {
                System.out.print(b + " ");
            }
            System.out.println("");
            SecretKeySpec keySpec = new SecretKeySpec(encodeKey, algorithm);// 可以使用此类来根据一个字节数组构造一个
                                                                            // SecretKey
            Cipher cipher = Cipher.getInstance(algorithm);// Cipher完成解密
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            return cipher.doFinal(content);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 密钥处理，此方法需返回一个长度为16的字节数组（即：密钥）
     * 
     * @param secretKey
     *            String,用于生成密钥的字符串
     * @return 16字节的密钥
     */
    private byte[] initSecretKey(String secretKey) {
        return new byte[] { -75, 93, 101, -81, 44, -52, 7, -25, 6, -44, -35, 99, -127, 126, -77, -7 };
        /*	try {// 该过程可替换，最终返回一个长度为16的字节数组即可
        		KeyGenerator key = KeyGenerator.getInstance(algorithm);// KeyGenerator提供密钥生成器的功能
        		SecureRandom random = new SecureRandom(secretKey.getBytes(charset));// 生成随机源
        		key.init(keyLength, random);// 初始化密钥生成器，使密钥大小为128位
        		SecretKey secretkey = key.generateKey();// SecretKey负责保存对称密钥
        		byte[] encodeKey = secretkey.getEncoded();
        		return encodeKey;
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
        	return null;*/
    }

    /**
     * 字节数组转为十六进制
     * 
     * @param buf
     *            byte[]，二进制字节数组
     * @return String，返回十六进制串
     * 
     */
    private String parseByte2HexStr(byte[] buf) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 十六进制串转为字节数组
     * 
     * @param hexStr
     *            String，十六进制串
     * @return byte[],返回字节数组
     */
    private byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

}
