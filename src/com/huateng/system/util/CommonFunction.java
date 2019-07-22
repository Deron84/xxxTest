package com.huateng.system.util;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.xwork.StringUtils;

import com.huateng.common.Constants;
import com.huateng.common.StringUtil;
import com.huateng.commquery.dao.ICommQueryDAO;
import com.huateng.dao.common.SqlDao;

/**
 * Title:通用方法
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-3-9
 * 
 * Company: Shanghai Huateng Software Systems Co., Ltd.
 * 
 * @author
 * 
 * @version 1.0
 */
public class CommonFunction {

	private static SimpleDateFormat showDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	private static SimpleDateFormat sysDateFormat = new SimpleDateFormat(
			"yyyyMMddHHmmss");

	private static SimpleDateFormat sysTimeFormat = new SimpleDateFormat(
			"HHmmss");

	private static SimpleDateFormat sysDateFormat8 = new SimpleDateFormat(
			"yyyyMMdd");

	private static SimpleDateFormat showDateFormatZHCN = new SimpleDateFormat(
			"yyyy年MM月dd日 HH时mm分ss秒");
	
	public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private static ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil
			.getBean("CommQueryDAO");

	private static SqlDao sqlDao = (SqlDao) ContextUtil.getBean("sqlDao");

	/**
	 * 获得32位随机数
	 * 
	 * @return
	 */
	public static String generateString(int length) {  
		StringBuffer sb = new StringBuffer();  
        Random random = new Random();  
        for (int i = 0; i < length; i++) {  
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));  
        }  
        return sysDateFormat.format(new Date())+sb.toString();
    }  
	/**
	 * 获得系统当前日期时间
	 * 
	 * @return
	 */
	public static String getCurrentDateTime() {
		return sysDateFormat.format(new Date());
	}

	/**
	 * 获得系统当前时间
	 * 
	 * @return
	 */
	public static String getCurrentTime() {
		return sysTimeFormat.format(new Date());
	}

	/**
	 * 获得系统当前日期
	 * 
	 * @return
	 */
	public static String getCurrentDate() {
		return sysDateFormat8.format(new Date());
	}

	/**
	 * 获得向前系统时间用于显示
	 * 
	 * @return
	 */
	public static String getCurrentDateTimeForShow() {
		return showDateFormat.format(new Date());
	}

	/**
	 * 获得中文系统时间
	 * 
	 * @return
	 */
	public static String getCurrentDateTimeZHCN() {
		return showDateFormatZHCN.format(new Date());
	}

	/**
	 * 获得给定时间的前一天
	 * 
	 * @param date
	 *            格式为yyMMdd exp 20120301
	 * @return String yyMMdd exp 20120229
	 */
	public static String getDateYestoday(String date) {
		Calendar cal = Calendar.getInstance();
		date = date.trim();
		int year = Integer.parseInt(date.substring(0, 4));
		int month = Integer.parseInt(date.substring(4, 6));
		int day = Integer.parseInt(date.substring(6, date.length()));
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DATE, day);
		// 日期的前一天
		cal.add(Calendar.DATE, -1);

		String mon = String.valueOf(cal.get(Calendar.MONTH) + 1);
		if (mon.length() == 1) {
			mon = "0" + mon;
		}
		String d = String.valueOf(cal.get(Calendar.DATE));
		if (d.length() == 1) {
			d = "0" + d;
		}
		String yestoday = String.valueOf(cal.get(Calendar.YEAR)) + mon + d;
		return yestoday.trim();
	}

	/**
	 * 填补字符串
	 * 
	 * @param str
	 * @param fill
	 * @param len
	 * @param isEnd
	 * @return
	 */
	public static String fillString(String str, char fill, int len,
			boolean isEnd) {

		int fillLen = len - str.getBytes().length;
		if (len <= 0) {
			return str;
		}
		for (int i = 0; i < fillLen; i++) {
			if (isEnd) {
				str += fill;
			} else {
				str = fill + str;
			}
		}
		return str;
	}

	/**
	 * 填补字符串(中文字符扩充)
	 * 
	 * @param str
	 * @param fill
	 * @param len
	 * @param isEnd
	 * @return
	 */
	public static String fillStringForChinese(String str, char fill, int len,
			boolean isEnd) {
		int num = 0;
		Pattern p = Pattern.compile("^[\u4e00-\u9fa5]");
		for (int i = 0; i < str.length(); i++) {
			Matcher m = p.matcher(str.substring(i, i + 1));
			if (m.find()) {
				num++;
			}
		}
		int fillLen = len - (str.length() + num);
		if (len <= 0) {
			return str;
		}
		for (int i = 0; i < fillLen; i++) {
			if (isEnd) {
				str += fill;
			} else {
				str = fill + str;
			}
		}
		return str;
	}

	/**
	 * 根据数据库中定义的长度扩充
	 * 
	 * @param str
	 * @param fill
	 * @param len
	 * @param isEnd
	 * @return 2011-8-26下午12:45:37
	 */
	public static String fillStringByDB(String str, char fill, int len,
			boolean isEnd) {

		String sql = "select lengthb('" + str + "') from dual";

		List list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		int length = 0;
		if (null != list && !list.isEmpty() && !StringUtil.isNull(list.get(0))) {
			BigDecimal bg = (BigDecimal) list.get(0);
			length = bg.intValue();
		}

		for (int i = 0; i < len - length; i++) {
			if (isEnd) {
				str += fill;
			} else {
				str = fill + str;
			}
		}

		return str;
	}

	/**
	 * 获得本行及一下机构MAP
	 * 
	 * @param brhId
	 * @param brhMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getBelowBrhMap(String brhId,
			Map<String, String> brhMap) {
		String sql = "SELECT BRH_ID,BRH_NAME FROM TBL_BRH_INFO WHERE UP_BRH_ID = '"
				+ brhId + "' ";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		for (Object[] brhInfo : dataList) {
			String tempBrhId = brhInfo[0].toString();
			String tempBrhName = brhInfo[1].toString();
			brhMap.put(tempBrhId, tempBrhName);
			brhMap = getBelowBrhMap(tempBrhId, brhMap);
		}
		return brhMap;
	}

	/**
	 * 获得本行及一下机构MAP
	 * 
	 * @param brhId
	 * @param brhMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getBelowBrhMap(Map<String, String> brhMap) {
		String sql = "SELECT BRH_ID,BRH_NAME FROM TBL_BRH_INFO WHERE BRH_ID in "
				+ getBelowBrhInfo(brhMap)
				+ " or UP_BRH_ID in "
				+ getBelowBrhInfo(brhMap) + " ";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		if (brhMap.size() == dataList.size()) {
			return brhMap;
		}
		for (Object[] brhInfo : dataList) {
			String tempBrhId = brhInfo[0].toString();
			String tempBrhName = brhInfo[1].toString();
			brhMap.put(tempBrhId, tempBrhName);
		}
		brhMap = getBelowBrhMap(brhMap);
		return brhMap;
	}

	/** 根据机构号获取当前机构及其下属机构的集合 add by 樊东东 一个层级查询sql搞定、不用像上面递归 */
	public static Map<String, String> getBelowBrhMap(String brhId) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		final String sql = "SELECT BRH_ID K ,BRH_NAME V FROM TBL_BRH_INFO START WITH BRH_ID='"
				+ StringUtils.trim(brhId)
				+ "' CONNECT BY PRIOR BRH_ID=UP_BRH_ID ORDER BY BRH_LEVEL,BRH_ID";
		List list = sqlDao.queryForList(sql);
		if (list != null && list.size() > 0) {
			for (Object obj : list) {
				Map<String, String> record = (Map<String, String>) obj;
				map.put(record.get("K"), record.get("V"));
			}
		}
		return map;
	}

	/** 根据操作员编号获得本行机构id */
	public static String getBrhIdByOprId(String oprId) {
		final String sql = "select BRH_ID from TBL_OPR_INFO where OPR_ID='"
				+ oprId + "'";
		String brhId = sqlDao.queryForObjectAndCast(sql, String.class);
		return brhId;
	}

	/** 根据操作员编号获得所属分行机构id */
	public static String getBranchIdByOprId(String oprId) {
		final String sql = "select (case when b.brh_level='2' then b.up_brh_id else o.brh_id end) as brh_id from TBL_OPR_INFO o inner join tbl_brh_info b on o.brh_id=b.brh_id  where o.OPR_ID='"
				+ oprId + "'";
		// System.out.println("PrintSQL:" + sql);
		String brhId = sqlDao.queryForObjectAndCast(sql, String.class);
		return brhId;
	}

	/** 根据机构id获得所属分行机构id */
	public static String getBranchIdByBrhId(String brhId) {
		final String sql = "select (case when brh_level=2 then up_brh_id when brh_level=1 then brh_id else '' end) from tbl_brh_info where brh_id='"
				+ brhId + "'";
		String branchId = sqlDao.queryForObjectAndCast(sql, String.class);
		return branchId;
	}

	/**
	 * 获得本行及下属机构编号信息
	 * 
	 * @param brhMap
	 * @return
	 */
	public static String getBelowBrhInfo(Map<String, String> brhMap) {
		String belowBrhInfo = "(";
		Iterator<String> iter = brhMap.keySet().iterator();
		while (iter.hasNext()) {
			String brhId = iter.next();
			belowBrhInfo += "'" + brhId + "'";
			if (iter.hasNext()) {
				belowBrhInfo += ",";
			}
		}
		belowBrhInfo += ")";
		return belowBrhInfo;
	}

	/**
	 * 获得指定日期的偏移日期
	 * 
	 * @param refDate
	 *            参照日期
	 * @param offSize
	 *            偏移日期
	 * @return
	 */
	public static String getOffSizeDate(String refDate, String offSize) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(Integer.parseInt(refDate.substring(0, 4)),
				Integer.parseInt(refDate.substring(4, 6)) - 1,
				Integer.parseInt(refDate.substring(6, 8)));
		calendar.add(Calendar.DATE, Integer.parseInt(offSize));
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		String retDate = String.valueOf(calendar.get(Calendar.DATE));
		if (Integer.parseInt(month) < 10) {
			month = "0" + month;
		}
		if (Integer.parseInt(retDate) < 10) {
			retDate = "0" + retDate;
		}
		return year + month + retDate;
	}

	/**
	 * 将金额元转分
	 * 
	 * @param str
	 * @return
	 */
	public static String transYuanToFen(String str) {
		if (str == null || "".equals(str.trim()))
			return "";
		BigDecimal bigDecimal = new BigDecimal(str.trim());
		return bigDecimal.movePointRight(2).toString();
	}

	/**
	 * 将金额元转分
	 * 
	 * @param str
	 * @return
	 */
	public static String transYuanToFen(double mon) {

		BigDecimal bigDecimal = new BigDecimal(mon);
		return bigDecimal.movePointRight(2).toString();
	}

	/**
	 * 将金额 原 前面补零，方便放入数据库 char(12)表示分
	 * 
	 * @param str
	 * @return
	 */
	public static String transYuanToChar12(double mon) {

		BigDecimal bigDecimal = new BigDecimal(mon);
		String str = bigDecimal.movePointRight(2).toString();
		return CommonFunction.fillString(str, '0', 12, false);
	}

	/**
	 * 将金额 原 前面补零，方便放入数据库 char(12)表示分
	 * 
	 * @param str
	 * @return
	 */
	public static String transYuanToChar12(String mon) {

		BigDecimal bigDecimal = new BigDecimal(mon);
		String str = bigDecimal.movePointRight(2).toString();
		return CommonFunction.fillString(str, '0', 12, false);
	}

	/**
	 * 将金额分转元
	 * 
	 * @param str
	 * @return
	 */
	public static String transFenToYuan(String str) {
		if (str == null || "".equals(str.trim()))
			return "";
		BigDecimal bigDecimal = new BigDecimal(str.trim());
		return bigDecimal.movePointLeft(2).toString();
	}
	
	/**
     * 将克（g）转换为千克（kg）
     * 
     * @param str
     * @return
     */
    public static String transGToKg(String str) {
        if (str == null || "".equals(str.trim()))
            return "";
        BigDecimal bigDecimal = new BigDecimal(str.trim());
        return bigDecimal.movePointLeft(3).toString();
    }
    
    /**
     * 将分/g转换为元/kg
     * 
     * @param str
     * @return
     */
    public static String transFgToYkg(String str) {
        if (str == null || "".equals(str.trim()))
            return "";
        BigDecimal bigDecimal = new BigDecimal(str.trim());
        return bigDecimal.movePointRight(1).toString();
    }

    /**
     * 将分/kg转换为元/kg
     * 
     * @param str
     * @return
     */
    public static String transFkgToYkg(String str) {
        if (str == null || "".equals(str.trim()))
            return "";
        BigDecimal bigDecimal = new BigDecimal(str.trim());
        return bigDecimal.movePointLeft(2).toString();
    }
	/**
	 * 获得指定个数的随机数组合
	 * 
	 * @param len
	 * @return 2010-8-19上午10:51:15
	 */
	public static String getRandomNum(int len) {
		String ran = "";
		Random random = new Random();
		for (int i = 0; i < len; i++) {
			ran += String.valueOf(random.nextInt(10));
		}
		return ran;
	}

	/**
	 * 判断字符串是否全部由数字组成
	 * 
	 * @param str
	 * @return 2010-8-26下午02:20:28
	 */
	public static boolean isMoney(String str) {

		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i)))
				return false;
		}
		return true;
	}

	/**
	 * 判断字符串是否全部由数字组成
	 * 
	 * @param str
	 * @return 2010-8-26下午02:20:28
	 */
	public static boolean isAllDigit(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i)))
				return false;
		}
		return true;
	}

	public static Date getCurrentTs() {
		Date now = new Date();
		return new Timestamp(now.getTime());
	}

	public static ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}

	public static void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		CommonFunction.commQueryDAO = commQueryDAO;
	}

	/**
	 * trim给定对象的field 仅对private field String有效(不含static)
	 * 
	 * @param obj
	 * @return 2011-6-22下午03:46:12
	 */
	public static Object trimObject(Object obj) {

		try {
			Method[] methods = obj.getClass().getMethods();
			for (Method m : methods) {
				if (m.getName().startsWith("get")) {
					// 允许个别字段转换失败
					try {
						if (String.class == m.getReturnType()) {
							String value = (String) m.invoke(obj,
									new Object[] {});
							if (!StringUtil.isNull(value)) {
								obj.getClass()
										.getMethod(
												"s" + m.getName().substring(1),
												String.class)
										.invoke(obj, StringUtils.trim(value));
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 获得指定个数转换为Double
	 * 
	 * @param len
	 * @return 2010-8-19上午10:51:15
	 */

	public static Double getDValue(String value, Double _default) {
		if (StringUtil.isNotEmpty(value)) {
			return Double.valueOf(value);
		}
		return _default;
	}

	/**
	 * 获得指定个数转换为BigDecimal
	 * 
	 * @param len
	 * @return 2010-8-19上午10:51:15
	 */
	public static BigDecimal getBValue(String value, BigDecimal _default) {
		if (StringUtil.isNotEmpty(value)) {
			try {
				return new BigDecimal(value.trim());
			} catch (Exception ex) {
				return _default;
			}
		} else
			return _default;
	}

	/**
	 * 获得指定个数转换为int
	 * 
	 * @param len
	 * @return 2010-8-19上午10:51:15
	 */
	public static Integer getInt(String value, int _default) {
		if (StringUtil.isNotEmpty(value)) {
			try {
				return Integer.parseInt(value.trim());
			} catch (Exception ex) {
				return _default;
			}
		} else
			return _default;
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static String formate8Date(String str) {
		if (str.length() == 8) {
			return str.substring(0, 4) + "-" + str.substring(4, 6) + "-"
					+ str.substring(6, 8);
		}
		return str;
	}

	public static String getCurrDate(String format) {
		SimpleDateFormat formater = new SimpleDateFormat(format);
		return formater.format(new Date());
	}

	/**
	 * 16进制
	 * 
	 * @param c
	 * @return 2011-7-27上午11:50:28
	 */
	private static byte toByte(char c) {
		byte b = (byte) "0123456789abcdef".indexOf(c);
		return b;
	}

	/**
	 * 16进制转BCD
	 * 
	 * @param hex
	 * @return 2011-7-27上午11:49:20
	 */
	public static byte[] hexStringToByte(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}

	/**
	 * BCD转成16进制
	 * 
	 * @param bArray
	 * @return 2011-7-27上午11:47:56
	 */
	public static String bytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}

	public static String urlToRoleId(String url) {
		try {
			String[] array = url.split("/");
			String[] result = array[array.length - 1].split("\\.");
			String res = result[0];
			return res;
		} catch (Exception e) {
			return url;
		}
	}

	public static String transMoney(double n) {
		try {
			String[] fraction = { "角", "分" };
			String[] digit = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
			String[][] unit = { { "元", "万", "亿" }, { "", "拾", "佰", "仟" } };

			String head = n < 0 ? "负" : "";
			n = Math.abs(n);

			String s = "";

			for (int i = 0; i < fraction.length; i++) {
				s += (digit[(int) (Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i])
						.replaceAll("(零.)+", "");
			}
			if (s.length() < 1) {
				s = "整";
			}
			int integerPart = (int) Math.floor(n);

			for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
				String p = "";
				for (int j = 0; j < unit[1].length && n > 0; j++) {
					p = digit[integerPart % 10] + unit[1][j] + p;
					integerPart = integerPart / 10;
				}
				s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零")
						+ unit[0][i] + s;
			}
			return head
					+ s.replaceAll("(零.)*零元", "元").replaceAll("(零.)+", "")
							.replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String insertString(String src, String fill) {
		String tmp = "";
		for (int i = 0; i < src.length(); i++) {
			tmp += fill;
			tmp += src.substring(i, i + 1);
		}
		return tmp;
	}

	/** 包装sql成为查询总数的sql */
	public static String getCountSql(final String sql) {
		return "select count(1) from (" + sql + ")";
	}

	/** 根据sql和begin记录、页记录数获得Object[] */
	public static Object[] getSqlQueryObjectArray(final String sql, int begin) {
		Object[] ret = new Object[2];
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, begin, Constants.QUERY_RECORD_COUNT);
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				CommonFunction.getCountSql(sql));
		ret[0] = dataList;
		ret[1] = count;
		return ret;
	}

	/** 将查询条件包装到单引号里面并在后面加上逗号,适用于条件多的查询语句拼装，一个两个的就不用了 */
	public static String getStringWithDYHTwoSide(String str) {
		return "'" + str + "',";
	}

	/** 将查询条件包装到单引号里面,适用于条件多的查询语句拼装，一个两个的就不用了 */
	public static String getStringWithDYHTwoSideWithNoDH(String str) {
		return "'" + str + "'";
	}

	/** 将查询条件包装到百分号里面 两边 */
	public static String getStringWithBFHTwoSide(String str) {
		return " '%" + str + "%' ";
	}

	/** 将查询条件包装到百分号里面 左边 */
	public static String getStringWithBFHLeftSide(String str) {
		return " '%" + str + "' ";
	}

	/** 将查询条件包装到百分号里面 右边 */
	public static String getStringWithBFHRightSide(String str) {
		return " '" + str + "%' ";
	}

	/** 获取当前日期90天前的日期 */
	public static String getNinetyDaysAgo() {
		Calendar cl = GregorianCalendar.getInstance();
		cl.add(Calendar.DAY_OF_YEAR, -90);
		Date date = cl.getTime();
		return new SimpleDateFormat("yyyyMMdd").format(date);
	}

	/** 获取当前日期180天前的日期 */
	public static String get180DaysAgo() {
		Calendar cl = GregorianCalendar.getInstance();
		cl.add(Calendar.DAY_OF_YEAR, -180);
		Date date = cl.getTime();
		return new SimpleDateFormat("yyyyMMdd").format(date);
	}

	/**
	 * 转换日期格式
	 * 
	 * @return
	 */
	public static String getDateStartStr(Date date) {
		return sysDateFormat8.format(date) + "000000";
	}

	/**
	 * 转换日期格式
	 * 
	 * @return
	 */
	public static String getDateEndStr(Date date) {
		return sysDateFormat8.format(date) + "235959";
	}

	public static String addMonthToString(String dateString, String format,
			int monthNumber) {
		Date vdate = stringToDate(dateString, format);
		vdate = addMonth(vdate, monthNumber);
		String vdates = dateToString(vdate, format);
		return vdates;
	}

	public static Date addMonth(Date date, int monthNumber) {
		if (date == null || monthNumber == 0)
			return date;
		Calendar vcal = Calendar.getInstance();
		vcal.setTime(date);
		vcal.add(Calendar.MONTH, monthNumber);
		date = vcal.getTime();
		return date;
	}

	public static String dateToString(Date date, String format) {
		if (date == null) {
			return null;
		}
		if (format == null || format.equalsIgnoreCase("")) {
			// throw new FrameException("传入参数中的[时间格式]为空");
			return null;
		}

		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}

	public static Date stringToDate(String dateString, String format) {
		if (dateString == null)
			return null;
		Hashtable<Integer, String> h = new Hashtable<Integer, String>();
		String javaFormat = new String();
		if (format.indexOf("yyyy") != -1)
			h.put(new Integer(format.indexOf("yyyy")), "yyyy");
		else if (format.indexOf("yy") != -1)
			h.put(new Integer(format.indexOf("yy")), "yy");
		if (format.indexOf("MM") != -1)
			h.put(new Integer(format.indexOf("MM")), "MM");
		else if (format.indexOf("mm") != -1)
			h.put(new Integer(format.indexOf("mm")), "MM");
		if (format.indexOf("dd") != -1)
			h.put(new Integer(format.indexOf("dd")), "dd");
		if (format.indexOf("hh24") != -1)
			h.put(new Integer(format.indexOf("hh24")), "HH");
		else if (format.indexOf("hh") != -1) {
			h.put(new Integer(format.indexOf("hh")), "HH");
		} else if (format.indexOf("HH") != -1) {
			h.put(new Integer(format.indexOf("HH")), "HH");
		}
		if (format.indexOf("mi") != -1)
			h.put(new Integer(format.indexOf("mi")), "mm");
		else if (format.indexOf("mm") != -1 && h.containsValue("HH"))
			h.put(new Integer(format.lastIndexOf("mm")), "mm");
		if (format.indexOf("ss") != -1)
			h.put(new Integer(format.indexOf("ss")), "ss");
		if (format.indexOf("SSS") != -1)
			h.put(new Integer(format.indexOf("SSS")), "SSS");

		for (int intStart = 0; format.indexOf("-", intStart) != -1; intStart++) {
			intStart = format.indexOf("-", intStart);
			h.put(new Integer(intStart), "-");
		}
		for (int intStart = 0; format.indexOf(".", intStart) != -1; intStart++) {
			intStart = format.indexOf(".", intStart);
			h.put(new Integer(intStart), ".");
		}
		for (int intStart = 0; format.indexOf("/", intStart) != -1; intStart++) {
			intStart = format.indexOf("/", intStart);
			h.put(new Integer(intStart), "/");
		}

		for (int intStart = 0; format.indexOf(" ", intStart) != -1; intStart++) {
			intStart = format.indexOf(" ", intStart);
			h.put(new Integer(intStart), " ");
		}

		for (int intStart = 0; format.indexOf(":", intStart) != -1; intStart++) {
			intStart = format.indexOf(":", intStart);
			h.put(new Integer(intStart), ":");
		}

		if (format.indexOf("年") != -1)
			h.put(new Integer(format.indexOf("年")), "年");
		if (format.indexOf("月") != -1)
			h.put(new Integer(format.indexOf("月")), "月");
		if (format.indexOf("日") != -1)
			h.put(new Integer(format.indexOf("日")), "日");
		if (format.indexOf("时") != -1)
			h.put(new Integer(format.indexOf("时")), "时");
		if (format.indexOf("分") != -1)
			h.put(new Integer(format.indexOf("分")), "分");
		if (format.indexOf("秒") != -1)
			h.put(new Integer(format.indexOf("秒")), "秒");
		int i = 0;
		while (h.size() != 0) {
			Enumeration<Integer> e = h.keys();
			int n = 0;
			while (e.hasMoreElements()) {
				i = ((Integer) e.nextElement()).intValue();
				if (i >= n)
					n = i;
			}
			String temp = (String) h.get(new Integer(n));
			h.remove(new Integer(n));
			javaFormat = temp + javaFormat;
		}
		SimpleDateFormat df = new SimpleDateFormat(javaFormat);
		df.setLenient(false);// 这个的功能是不把1996-13-3 转换为1997-1-3
		Date myDate = new Date();
		try {
			myDate = df.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return myDate;
	}
}
