package com.huateng.common;

import java.util.Random;


public class CommonUtil {
	
    /** 
     * 获取随机字母数字组合 
     *  
     * @param length 
     *            字符串长度 
     * @return 
     */
    public static String getRandomCharAndNumr(Integer length) {
        String str = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            boolean b = random.nextBoolean();
            if (b) { // 字符串  
                //     int choice = random.nextBoolean() ? 65 : 97; //取得65大写字母还是97小写字母  
                int choice = 65;
                str += (char) (choice + random.nextInt(6));
            } else { // 数字  
                str += String.valueOf(random.nextInt(10));
            }
        }
        return str;
    }
    /**
     * 
     * //TODO 获取加密密钥
     *
     * @param ckey
     * @return
     * @throws Exception
     */
    public static String encryKey(String term_no, String key) {
        int startInt = (term_no.substring(1, 2)).getBytes()[0] & 0xFF;
        int endInt = (term_no.substring(term_no.length() - 2, term_no.length() - 1)).getBytes()[0] & 0xFF;
        String beforeVar = key.substring(startInt - 1, startInt + 3);
        String afterVar = key.substring(endInt - 1, endInt + 3);
        return beforeVar + term_no + afterVar;
    }
	 /**
     * 16进制表示的字符串转换为字节数组
     *
     * @param s 16进制表示的字符串
     * @return byte[] 字节数组
     */
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] b = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个字节
            b[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return b;
    }
    /**
     * //TODO  16进制字符串
     *
     * @param message
     * @return
     * @throws Exception
     */
    public static String strToHexStr(byte[] bytes) throws Exception {
        StringBuffer retVal = new StringBuffer("");
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            retVal.append("" + hex.toUpperCase());
        }
        return retVal.toString();
    }
    /**
     * SM4加密
     *
     * @param plainText 加密数据
     * @param key  byte[] 16字节
     * @return
     * @throws Exception
     */
     public static byte[] encrySm4(byte[] plainText, byte[] key) throws Exception {
         int ENCRYPT = 1;
         SMS4 sm4 = new SMS4();
         byte[] key2 = null;
         byte[] ret = null;
         byte[] out = null;
         byte[] input = plainText;
         int p = 16 - input.length % 16;
         if (p == 16) {
             p = 0;
         }
         ret = new byte[input.length + p];
         System.arraycopy(input, 0, ret, 0, input.length);
         for (int i = 0; i < p; i++) {
             ret[input.length + i] = (byte) p;
         }
         key2 = key;
         out = new byte[ret.length];
         sm4.sms4(ret, ret.length, key2, out, ENCRYPT);
         sm4 = null;
         key2 = null;
         ret = null;
         input = null;
         return out;
     }
    /**
     *  SM4解密
     *
     * @param out 解密数据
     * @param key byte[] 16字节
     * @return
     * @throws Exception
     */
    public static byte[] decrySm4(byte[] out, byte[] key) throws Exception {
        int DECRYPT = 0;
        SMS4 sm4 = new SMS4();
        int i = 0;
        int datalen = 0;
        byte[] ret = null;
        byte[] input = null;
        byte[] key2 = key;
        input = new byte[out.length];
        sm4.sms4(out, out.length, key2, input, DECRYPT);
        int filllen = input[input.length - 1];
        for (i = 0; i < filllen; i++) {
            if (input[input.length - 1 - i] != filllen) break;
        }
        if (i == filllen) {
            datalen = input.length - filllen;
        } else {
            datalen = input.length;
        }
        ret = new byte[datalen];
        System.arraycopy(input, 0, ret, 0, datalen);
        sm4 = null;
        input = null;
        key2 = null;
        return ret;
    }
}
