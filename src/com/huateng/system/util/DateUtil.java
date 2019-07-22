package com.huateng.system.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * @author zhaozc
 * @Modify 2014-10-16
 */
public class DateUtil {
	/**
	 * 
	 * 注意：SimpleDateFormat存在线程安全的问题，谨慎使用，最好使用apache-commons的类
	 * 
	 * 
	 */
	
	/**
	 * 日期格式化字符串 yyyyMMddHHmmss
	 */
	private static final String DATEFORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	/**
	 * 默认的日期个好似话字符串 yyyy-MM-dd HH:mm:ss
	 */
	public static final String DATEFORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
	
	public static final String DATEFORMAT_MMDDHHMM = "MM-dd HH:mm";
	
	public static final String PATTERN_YMD = "yyyy-MM-dd";

	public static final long ONE_DAY = 86400000l;
	
	public static final int ONE_DAY_INT = 86400;
	
	public static String[] weekOfDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};  

	/**
	* @Title: add
	* @Description: 日期累计方法
	* @param date
	* @param type 1年；2月；3周
	* @param value
	* @return
	* @return Date
	* @throws
	*/
	public static Date add(Date date,int type,int value){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			switch (type) {
			case 1:
				calendar.add(Calendar.YEAR, value);
				break;
			case 2:
				calendar.add(Calendar.MONTH, value);
				break;
			case 3:
				calendar.add(Calendar.WEDNESDAY, value);
				break;
			default:
				calendar.add(Calendar.MONTH, value);
				break;
			}
			
			return calendar.getTime();
	 }
	
	public static String getDate(SimpleDateFormat sdf) {
		return sdf.format(new Date());
	}

	public static Date strToDate(String str, String format) {
		Date date = null;
		if (str != null) {
			try {
				SimpleDateFormat df = new SimpleDateFormat(format);
				date = df.parse(str);
			} catch (Exception e) {
				
			}
		}
		return date;
	}
	public static Date strToDate(String str, String format,String regex) {
		Date date = null;
		if (str != null) {
			if (str.matches(regex)) {
				try {
					SimpleDateFormat df = new SimpleDateFormat(format);
					date = df.parse(str);
				} catch (Exception e) {
					
				}
			}
		}
		return date;
	}

	public static Date strToDate(String str, SimpleDateFormat sdf) {
		Date date = null;
		if (str != null) {
			try {
				date = sdf.parse(str);
			} catch (Exception e) {
			}
		}
		return date;
	}

	public static String dateToStr(Date date, String format) {
		String str = null;
		if (date != null)
			try {
				SimpleDateFormat df = new SimpleDateFormat(format);
				str = df.format(date);
			} catch (Exception e) {
			}
		return str;
	}

	public static String dateToStr(Date date, SimpleDateFormat df) {
		String str = null;
		if (date != null)
			try {
				str = df.format(date);
			} catch (Exception e) {
			}
		return str;
	}

	/**
	 * 
	 * @param date
	 * @param i
	 * @return
	 */
	public static Date add(Date date, int i) {
		date = new Date(date.getTime() + i * ONE_DAY);
		return date;
	}

	/**
	 * 
	 * @param date
	 * @param i
	 * @return
	 */
	public static Date add(Date date, long sec) {
		date = new Date(date.getTime() + sec * 1000);
		return date;
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static Date add(Date date) {
		return add(date, 1);
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static Date sub(Date date) {
		return add(date, -1);
	}

	/**
	 * 根据当前的年份和月份取得开始日期和截止日期
	 * 每月的第一天和最后一天
	 * @param year
	 * @param weekOfYear
	 * @return
	 */
	public static List<Date> getDatesOfMonth(){
		List<Date> list = new ArrayList<Date>();
		
		Calendar cal =Calendar.getInstance();
		
		cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		list.add(cal.getTime());
		
		cal.set(Calendar.DATE, cal.getMaximum(Calendar.DATE));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 998);
		list.add(cal.getTime());
		
		return list;
	}
	/**
	 * 根据年份和月份取得开始日期和截止日期
	 * 每月的第一天和最后一天
	 * @param year
	 * @param weekOfYear
	 * @return
	 */
	public static List<Date> getDatesOfMonth(Integer year, Integer monthOfYear){
		List<Date> list = new ArrayList<Date>();
		
		Calendar cal =Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, monthOfYear-1);
		
		cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		list.add(cal.getTime());
		
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 998);
		list.add(cal.getTime());
		
		return list;
	}
	public static Date obj2Date(Object obj){
		if (obj!=null&&obj instanceof Date) {
			Date date = (Date)obj;
			return date;
		}else {
			return null;
		}
	}
	public static Timestamp obj2Timestamp(Object obj){
		if (obj!=null&&obj instanceof Timestamp) {
			Timestamp date = (Timestamp)obj;
			return date;
		}else {
			return null;
		}
	}
	/**
	 * 文字转时间
	 * @param type
	 * @return
	 */
	public static String wordToDate(String type){
		Date date = new Date();
		if ("1".equals(type)) {
			date = new Date();
		}else if("2".equals(type)) {
			date =  add(new Date(),-7);
		}else if("3".equals(type)) {
			date =  getTime(Calendar.MONTH, -1);
		}else if("4".equals(type)) {
			date =  getTime(Calendar.MONTH, -6);
		}else {
			date = new Date();
		}
		String s = dateToStr(date, "yyyyMMdd");
		return s;
	}
	/**
	 * 
	 * @param field Calendar.DAY_OF_MONTH
	 * @param up 2
	 * @return
	 */
	public static Date getTime(int field,int up){
		Calendar c = Calendar.getInstance();
//		int t = c.get(field)+add;
//		c.set(field, t);
//		c.roll(field, up);
		c.add(field, up);
//		System.out.println(format.format(c.getTime()));
		return new Date(c.getTimeInMillis());
	}
	public static long getTimeMillis(int field,int up){
		Calendar c = Calendar.getInstance();
		c.add(field, up);
		return c.getTimeInMillis();
	}
	/**
	 * mysql 数据库createtime,updatetime(19)专用方法
	 * @param date
	 * @return
	 */
	public static String getDBTime(Date date){
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return f.format(date);
	}
	public static int getYear(int up){
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		year = year+up;
		return year;
	}
	public static int getDay(int up){
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DATE);
		day = day+up;
		return day;
	}
	public static long getYearTime(int year){
		Calendar c = Calendar.getInstance();
		c.set(year,0,1,0,0,0);
		return c.getTimeInMillis();
	}
	
	/** * 获取指定日期是星期几
	  * 参数为null时表示获取当前日期是星期几
	  * @param date
	  * @return
	*/
	public static String getWeekOfDate(Date date) {      
	          
	    Calendar calendar = Calendar.getInstance();      
	    if(date != null){        
	         calendar.setTime(date);      
	    }        
	    int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;      
	    if (w < 0){        
	        w = 0;      
	    }      
	    return DateUtil.weekOfDays[w];    

	}
	/**
	 * 获取某天的零点时间
	 * @param time
	 * @return
	 */
	public static long getDayStart(long time){
		long t = (time+28800000)%86400000;
		return time-t;
	}
	/**
	 * 获取下一年（月，日，小时，分）的毫秒时间
	 * Calendar.DATE
	 * @param date
	 */
	public static long add(long time,int field,int amount){
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		c.add(field, amount);
		return c.getTimeInMillis();
	}
	/**
	 * 获取当前到明天某点的剩余时间毫秒数
	 * @return
	 */
	public static long getTimeLeft(int point){
		long time = System.currentTimeMillis();
		long zero_today = DateUtil.getDayStart(time);
		long zero_tomorrow = DateUtil.add(zero_today, Calendar.DATE, 1);
		long start_tomorrow = DateUtil.add(zero_tomorrow, Calendar.HOUR, point);
		long time_left = start_tomorrow-time;
		return time_left;
	}
	/**
	 * 判断当前时间是否是某个时间点周围的时间
	 * 如：当年是否是7:50-8:10时间段内（poin-8,range-600）
	 * @param point 小时
	 * @param range 秒
	 * @return
	 */
	public static boolean inTimeRange(int point,int range){
		
		long time = System.currentTimeMillis();
		long zero_today = DateUtil.getDayStart(time);
		long start_tomorrow = DateUtil.add(zero_today, Calendar.HOUR, point);
		long time_min = DateUtil.add(start_tomorrow, Calendar.SECOND, -range);
		long time_max = DateUtil.add(start_tomorrow, Calendar.SECOND, range);
		if (time>time_min&&time<time_max) {
			return true;
		}else{
			return false;
		}
		
	}
	/**
	 * 获得当前时间30秒前的时间
	 */
   public static Date get30BeforeDate(){
	   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = new GregorianCalendar();
		Date date = new Date();
//		System.out.println("系统当前时间      ："+df.format(date));
		c.setTime(date);//设置参数时间
		c.add(Calendar.SECOND,-30);//把日期往后增加SECOND 秒.整数往后推,负数往前移动
		date=c.getTime(); //这个时间就是日期往后推一天的结果
		return date;
		//String str = df.format(date);
   }
	public static void main(String[] args) {
//		wordToDate("4");
//		getYear();
//		System.out.println(new Date(getYearTime(2015)));
		
//		String time = DateUtil.dateToStr(new Date(), DateUtil.YMd);
//		long datelong = DateUtil.DateToInt(time, "yyyy-MM-dd");
//		System.out.println(datelong);
//		
//		String date = DateUtil.TimeStampDate("1431915806", "yyyy-MM-dd");
//		System.out.println(date);
		
//		System.out.println(strToDate("2016-01-09", "yyyy-MM-dd"));
		
//		System.out.println(timeToSecond(System.currentTimeMillis()));
//		System.out.println(System.currentTimeMillis());
		
		/*String a = secondToString(1506047323);
		System.out.println(a);
		System.out.println(DateUtil.getDayStart(System.currentTimeMillis()));*/
		//secondToString(1506046313);
		
//		System.out.println(getWeekOfDate(new Date()));
//		System.out.println(getWeekOfDate(strToDate("20171205", "yyyyMMdd")));
//		System.out.println(getWeekOfDate(strToDate("", "yyyyMMdd")));
		
//		System.out.println(secondToHHmmss(44500));
		
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Calendar c = new GregorianCalendar();
//		Date date = new Date();
//		System.out.println("系统当前时间      ："+df.format(date));
//		c.setTime(date);//设置参数时间
//		c.add(Calendar.SECOND,-30);//把日期往后增加SECOND 秒.整数往后推,负数往前移动
//		date=c.getTime(); //这个时间就是日期往后推一天的结果
//		String str = df.format(date);
//		System.out.println("系统前30秒时间："+str);
	}
	
}