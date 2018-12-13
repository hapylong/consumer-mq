package com.iqb.consumer.mq.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类 
 * @Copyright 北京爱钱帮财富科技有限公司
 * @author jack
 * @Date 2015年12月21日-下午2:02:58
 */
public class DateTools { 
	/** 格式化年月日 yyyyMMdd **/
	private static SimpleDateFormat ymdDf = new SimpleDateFormat("yyyyMMdd");
	/** 格式化年月日 yyyyMMdd **/
	private static SimpleDateFormat ymdDf1 = new SimpleDateFormat("yyyy-MM-dd");
	/** 格式化时分秒 HHmmss **/
	private static SimpleDateFormat hmsDf = new SimpleDateFormat("HHmmss");
	
	/** 格式化年月日 yyyyMMddHHmmss**/
	private static SimpleDateFormat ymdhmsDf = new SimpleDateFormat("yyyyMMddHHmmss");
	
	/** 格式化年月日 yyyy-MM-dd HH:mm:ss**/
	private static SimpleDateFormat ymdhmsDfformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	//时间戳
	public static String timestamp= String.valueOf(new Date().getTime());

	/**
	 * 取当前日期 - yyyyMMdd
	 * 
	 * @return
	 */
	public static String getTRXDATE() {
		return ymdDf.format(new Date());
	}
	/**
	 * 取当前时分秒 - HHmmss
	 * 
	 * @return
	 */
	public static String getTRXTIME() {
		return hmsDf.format(new Date());
	}
	
	/**
	 * 取当前时间 - yyyyMMddHHmmss
	 * 
	 * @return
	 */
	public static String getYmdhmsTime(){
		return ymdhmsDf.format(new Date());
	}
	
	/**
	 * 取当前时间   yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getYYYYMMDDMMHHSS()
    {
        return ymdhmsDfformat.format(new Date());
    }
	
	/**
	 * 取当前时间   yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getYYYYMMDDMMHHSS(Date date)
    {
        return ymdhmsDfformat.format(date);
    }
	
	/**
	 * 字符串转换为 unix_timestamp
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static long getUnixTimestampTime(String dateStr) throws ParseException{
		
        Date date = ymdDf.parse(dateStr);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return new Long(cal.getTimeInMillis()/1000).intValue();
	}
	
	/**
	 * 字符串转换为 unix_timestamp
	 * @param dateStr  yyyy-MM-dd  
	 * @return
	 * @throws ParseException
	 */
	public static long getUnixTimestampTime2(String dateStr) throws ParseException{
		
        Date date = ymdDf1.parse(dateStr);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return new Long(cal.getTimeInMillis()/1000).intValue();
	}
	
	/**
	 * 字符串转换为 unix_timestamp
	 * @param dateStr  yyyy-MM-dd HH:mm:ss
	 * @return
	 * @throws ParseException
	 */
	public static long getUnixTimestampTime3(String dateStr) throws ParseException{
		
        Date date = ymdhmsDfformat.parse(dateStr);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return new Long(cal.getTimeInMillis()/1000).intValue();
	}
	
	
	/**
	 * 活期系统日期的前一天日期 格式：yyyy-MM-dd
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static String getSystemYesterday(){
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(new Date());//把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
		Date dBefore = calendar.getTime();   //得到前一天的时间
		return ymdDf1.format(dBefore);
	}
}