/* @(#) StringUtil.java
 * Copyright(c) 2008 HUATENG. All Right Reserver.
 * 2008-11-28 create by LEO.YAN
 */
package com.huateng.common;

import java.security.MessageDigest;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * 字符串工具类 String Utilities.
 */
public class StringUtil {

	private static final String algorithm = "MD5";

	/**
	 * 判断object是否为空
	 * 
	 * @param object
	 *            Object对象
	 * @return 布尔倄1�7
	 */
	public static boolean isNull(Object object) {
		if (object instanceof String) {
			return StringUtil.isEmpty(object.toString());
		}
		return object == null;
	}
	public static int toInt(Object obj) {
		if (obj == null) {
			return 0;
		}
		String s = obj.toString();
		if (s != null && !s.trim().equals("")) {
			if (s.matches("\\d+")) {
				return Integer.parseInt(s);
			}
		}
		return 0;
	}
	/**
	 * Checks if string is null or empty.
	 * 
	 * @param value
	 *            The string to be checked
	 * @return True if string is null or empty, otherwise false.
	 */
	public static boolean isEmpty(final String value) {
		return value == null || value.trim().length() == 0
				|| "null".endsWith(value);
	}

	/**
	 * Converts <code>null</code> to empty string, otherwise returns it
	 * directly.
	 * 
	 * @param string
	 *            The nullable string
	 * @return empty string if passed in string is null, or original string
	 *         without any change
	 */
	public static String null2String(Object obj) {
		return obj == null ? "" : obj.toString();
	}

	public static String null2String(String str) {
		return str == null ? "" : str;
	}

	/**
	 * 填充字符
	 * 
	 * @param value
	 * @param len
	 * @param fillValue
	 * @return
	 */
	public static String fillValue(String value, int len, char fillValue) {
		String str = (value == null) ? "" : value.trim();
		StringBuffer result = new StringBuffer();
		result.append(str);
		int paramLen = str.length();
		if (paramLen < len) {
			for (int i = 0; i < len - paramLen; i++) {
				result.append(fillValue);
			}
		}
		return result.toString();
	}

	/**
	 * 在value后变插入count次appendValue
	 * 
	 * @param value
	 * @param count
	 *            插入的次敄1�7
	 * @param appendValue
	 * @return
	 */
	public static String appendValue(String value, int count, String appendValue) {
		if (count < 1) {
			return value;
		}
		StringBuffer result = new StringBuffer();
		result.append(value);
		for (int i = 0; i < count; i++) {
			result.append(appendValue);
		}
		return result.toString();
	}

	/**
	 * 填充字符
	 * 
	 * @param value
	 * @param len
	 * @param fillValue
	 * @return
	 */
	public static String beforFillValue(String value, int len, char fillValue) {
		String str = (value == null) ? "" : value.trim();
		StringBuffer result = new StringBuffer();
		int paramLen = str.length();
		if (paramLen < len) {
			for (int i = 0; i < len - paramLen; i++) {
				result.append(fillValue);
			}
		}
		result.append(str);
		return result.toString();
	}

	/**
	 * 格式化金预1�7
	 * 
	 * @param amount
	 *            金额
	 * @return
	 */
	public static String convertAmount(String amount) {
		String str = String.valueOf(Double.parseDouble(amount));
		int pos = str.indexOf(".");
		int len = str.length();
		if (len - pos < 3) {
			return str.substring(0, pos + 2) + "0";
		} else {
			return str.substring(0, pos + 3);
		}
	}

	/**
	 * currency fomate
	 * 
	 * @param currency
	 * @return
	 */
	public static String formatCurrency(String currency) {
		if ((null == currency) || "".equals(currency)
				|| "null".equals(currency)) {
			return "";
		}

		NumberFormat usFormat = NumberFormat.getCurrencyInstance(Locale.CHINA);
		try {
			return usFormat.format(Double.parseDouble(currency));
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 根据separator拆分text
	 * 
	 * @param text
	 *            霄1�7拆分的字符串 String
	 * @param separator
	 *            拆分表达弄1�7 String
	 * @return String[]
	 */
	public static String[] split(String text, String separator) {
		return split(text, separator, -1);
	}

	/**
	 * Splits the provided text into a list, based on a given separator. The
	 * separator is not included in the returned String array. The maximum
	 * number of splits to perfom can be controlled. A null separator will cause
	 * parsing to be on whitespace.
	 * <p>
	 * <p>
	 * This is useful for quickly splitting a string directly into an array of
	 * tokens, instead of an enumeration of tokens (as
	 * <code>StringTokenizer</code> does).
	 * 
	 * @param str
	 *            The string to parse.
	 * @param separator
	 *            Characters used as the delimiters. If <code>null</code>,
	 *            splits on whitespace.
	 * @param max
	 *            The maximum number of elements to include in the list. A zero
	 *            or negative value implies no limit.
	 * @return an array of parsed Strings
	 */
	public static String[] split(String str, String separator, int max) {
		StringTokenizer tok = null;
		if (separator == null) {
			// Null separator means we're using StringTokenizer's default
			// delimiter, which comprises all whitespace characters.
			tok = new StringTokenizer(str);
		} else {
			tok = new StringTokenizer(str, separator);
		}

		int listSize = tok.countTokens();
		if (max > 0 && listSize > max) {
			listSize = max;
		}

		String[] list = new String[listSize];
		int i = 0;
		int lastTokenBegin = 0;
		int lastTokenEnd = 0;
		while (tok.hasMoreTokens()) {
			if (max > 0 && i == listSize - 1) {
				String endToken = tok.nextToken();
				lastTokenBegin = str.indexOf(endToken, lastTokenEnd);
				list[i] = str.substring(lastTokenBegin);
				break;
			}
			list[i] = tok.nextToken();
			lastTokenBegin = str.indexOf(list[i], lastTokenEnd);
			lastTokenEnd = lastTokenBegin + list[i].length();
			i++;
		}
		return list;
	}

	/**
	 * Replace all occurances of a string within another string.
	 * 
	 * @param text
	 *            text to search and replace in
	 * @param repl
	 *            String to search for
	 * @param with
	 *            String to replace with
	 * @return the text with any replacements processed
	 * @see #replace(String text, String repl, String with, int max)
	 */
	public static String replace(String text, String repl, String with) {
		return replace(text, repl, with, -1);
	}

	/**
	 * Replace a string with another string inside a larger string, for the
	 * first <code>max</code> values of the search string. A <code>null</code>
	 * reference is passed to this method is a no-op.
	 * 
	 * @param text
	 *            text to search and replace in
	 * @param repl
	 *            String to search for
	 * @param with
	 *            String to replace with
	 * @param max
	 *            maximum number of values to replace, or <code>-1</code> if
	 *            no maximum
	 * @return the text with any replacements processed
	 * @throws NullPointerException
	 *             if repl is null
	 */
	private static String replace(String text, String repl, String with, int max) {
		if (text == null) {
			return null;
		}
		StringBuffer buf = new StringBuffer(text.length());
		int start = 0;
		int end = text.indexOf(repl, start);
		while (end != -1) {
			buf.append(text.substring(start, end)).append(with);
			start = end + repl.length();

			if (--max == 0) {
				break;
			}
			end = text.indexOf(repl, start);
		}
		buf.append(text.substring(start));
		return buf.toString();
	}

	public static String first2Upper(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	/**
	 * 
	 * @param Object[]
	 *            input: Object[]: String[] result={"TYHR0001","TYHR0002"}
	 *            delim: "," output: "'TYHR0001','TYHR0002'"
	 * 
	 * @param Object[]
	 * @return String
	 */

	public static String arrayToDelimitedString(Object[] arr, String delim) {

		if (arr == null || arr.length == 0) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			if (i > 0) {
				sb.append(delim);
			}
			sb.append('\'');
			sb.append(arr[i]);
			sb.append('\'');
		}
		return sb.toString();
	}

	/**
	 * e.g: String[] result={"TYHR0001","TYHR0002"}; split=","; return:
	 * str="TYHR0001,TYHR0002";
	 * 
	 * @param Object[]
	 * @param split
	 * @return String
	 */
	public static String arrayToStr(Object[] arr, char split) {
		if (arr == null || arr.length == 0) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			if (i > 0) {
				sb.append(split);
			}
			sb.append(arr[i]);

		}
		return sb.toString();
	}

	/**
	 * 将数组的每个元素后加入split，然后组成字符串返回
	 * 
	 * @param arr
	 *            字符串数组1�7
	 * @param split
	 *            插入字符
	 * @return
	 */
	public static String arrayToStr(Object[] arr, String split) {
		if (arr == null || arr.length == 0) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			if (i > 0) {
				sb.append(split);
			}
			sb.append(arr[i]);

		}
		return sb.toString();
	}

	/**
	 * ȡ�õ�ǰϵͳ获取本地时间
	 * 
	 * @param style
	 *            yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getcurrdate(String style) {
		Date currDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(style); // "yyyy-MM-dd
		// HH:mm:ss"
		String strdate = sdf.format(currDate);
		return strdate;
	}

	/**
	 * 密码加密
	 * 
	 * @param password
	 * @return
	 */
	public static String encodePassword(String password) {
		byte[] unencodedPassword = password.getBytes();

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(algorithm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		md.reset();
		// call the update method one or more times
		// (useful when you don't know the size of your data, eg. stream)
		md.update(unencodedPassword);
		// now calculate the hash
		byte[] encodedPassword = md.digest();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < encodedPassword.length; i++) {
			if ((encodedPassword[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
		}
		return buf.toString();

	}

	/**
	 * 得到字符串中某个字符出现的次敄1�7
	 * 
	 * @param str
	 * @param c
	 * @return
	 */
	public static int getCharCount(String str, char c) {

		int count = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == c)
				count++;
		}
		return count;
	}

	public static boolean isNumeric(String str) {
		if(isNull(str))
			return false;
		for (int i = 0; i<str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	public static String trim(String str) {
		if(str == null) {
			return "";
		}
		return str.trim().replaceAll(" ", "");
	}
	
	public static String substr(String str,int index) {
		if(str.length() < index) {
			return str;
		}
		str = trim(str).substring(index);
		return str;
	}
	
	public static String substr(String str,int beginIndex,int endIndex) {
		if(str.length() < beginIndex || str.length() < endIndex || beginIndex > endIndex) {
			return str;
		}
		return trim(str).substring(beginIndex, endIndex);
	}
	
	/**
	 * 判断信息是否为空
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		if(str != null && !"".equals(str.trim()))
			return true;
		else
			return false;
	}
	public static String toString(Object obj) {
		if (obj != null && !obj.toString().trim().equals("")) {
			return obj.toString();
		} else {
			return "";
		}
	}

	public static String toString(Object obj, String init) {
		if (obj != null && !obj.toString().trim().equals("")) {
			return obj.toString();
		} else {
			return init;
		}
	}
}
