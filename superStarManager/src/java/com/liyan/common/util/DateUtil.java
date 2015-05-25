package com.liyan.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class DateUtil {

    public static final Log LOG = LogFactory.getLog(DateUtil.class);

    public static final String WORD_SEED = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    public static final String FORMAT_DATE = "yyyy-MM-dd";
	/**
	 * 将时间字符串转换成时间对象
	 * @param dateStr	时间字符串
	 * @return	Date时间对象
	 */
	public static Date stringToDate(String dateStr) throws Exception{
		DateFormat df = null;
		try{
			dateStr = dateStr.trim();
			if(dateStr.length()==10 || dateStr.length()==9 || dateStr.length()==8 ){
				df = new SimpleDateFormat(FORMAT_DATE);
			}else if(dateStr.length()==19){
				df = new SimpleDateFormat(FORMAT_DATE_TIME);
			}
		}catch(Exception e){
		}
		return df==null?null:df.parse(dateStr);
	}
	/**
	 * 将时间对象转换成时间字符串
	 * @param date	时间对象
	 * @return String 时间字符串
	 */
	public static String dateTimeToString(Date date){
		String dateStr = "";
		try{
			if(date!=null){
				DateFormat df = new SimpleDateFormat(FORMAT_DATE_TIME);
				dateStr = df.format(date);
			}
		}catch(Exception e){
		}
		return dateStr;
	}
	/**
	 * 将时间对象转换成时间字符串
	 * @param date	时间对象
	 * @return String 时间字符串
	 */
	public static String dateToString(Date date){
		String dateStr = "";
		try{
			if(date!=null){
				DateFormat df = new SimpleDateFormat(FORMAT_DATE);
				dateStr = df.format(date);
			}
		}catch(Exception e){
		}
		return dateStr;
	}
    /**
     * 取当前时间字符串
     * @return 取当前时间字符串
     */
    public static String getCurrentDateTimeString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_DATE_TIME);
        return simpleDateFormat.format(new Date());
    }

    /**
     * 取当前日期字符串
     * @return　取当前日期字符串
     */
    public static String getCurrentDateString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_DATE);
        return simpleDateFormat.format(new Date());
    }

    /**
     * 取给定日期的年份
     * @param date 日期
     * @return 给定日期的年份
     */
    public static String getYearFromDateString(String date) {
        try {
            return date.substring(0, 4);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 取给定日期的月份
     * @param date 日期
     * @return 给定日期的月份
     */
    public static String getMonthFromDateString(String dateTimeString) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_DATE_TIME);

        Date date = null;
        try {
            date = simpleDateFormat.parse(dateTimeString);
        } catch (ParseException e) {
            date = new Date();
            LOG.warn(String.format("Parsing dateTimeString: %s error, using current datetime", dateTimeString));
        }
        
        calendar.setTime(date);
        return String.valueOf(calendar.get(Calendar.MONTH)+1);
    }

    /**
     * 取给定日期是当月的几号
     * @param date 日期
     * @return 给定日期是当月的几号
     */
    public static String getDayFromDateString(String dateTimeString) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_DATE_TIME);

        Date date = null;
        try {
            date = simpleDateFormat.parse(dateTimeString);
        } catch (ParseException e) {
            date = new Date();
            LOG.warn(String.format("Parsing dateTimeString: %s error, using current datetime", dateTimeString));
        }
        
        calendar.setTime(date);
        return String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
    }
    

    /**
     * 取给定日期是当天的几点
     * @param date 日期
     * @return 给定日期是当月的几点
     */
    public static String getHourFromDateString(String dateTimeString) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_DATE_TIME);

        Date date = null;
        try {
            date = simpleDateFormat.parse(dateTimeString);
        } catch (ParseException e) {
            date = new Date();
            LOG.warn(String.format("Parsing dateTimeString: %s error, using current datetime", dateTimeString));
        }
        
        calendar.setTime(date);
        return String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
    }    

    /**
     * 取给定日期是当前小时的几分
     * @param dateTimeString 日期
     * @return 给定日期是当前小时的几分
     */
    public static String getMinuteFromDateString(String dateTimeString) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_DATE_TIME);

        Date date = null;
        try {
            date = simpleDateFormat.parse(dateTimeString);
        } catch (ParseException e) {
            date = new Date();
            LOG.warn(String.format("Parsing dateTimeString: %s error, using current datetime", dateTimeString));
        }
        
        calendar.setTime(date);
        return String.valueOf(calendar.get(Calendar.MINUTE));
        
    } 
    
    /**
     * 取给定日期是当前分钟的几秒
     * @param dateTimeString 日期
     * @return 给定日期是当前分钟的几秒
     */
    public static String getSecondFromDateString(String dateTimeString) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_DATE_TIME);

        Date date = null;
        try {
            date = simpleDateFormat.parse(dateTimeString);
        } catch (ParseException e) {
            date = new Date();
            LOG.warn(String.format("Parsing dateTimeString: %s error, using current datetime", dateTimeString));
        }
        
        calendar.setTime(date);
        return String.valueOf(calendar.get(Calendar.SECOND));
        
    }  
    
    /**
     * 在给定的时间上添加给定的天数
     * @param daysToAdd 加几天
     * @param dateTimeString 给定的时间
     * @return 加上给定天数的时间
     */
    public static String addDay(Integer daysToAdd, String dateTimeString) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_DATE_TIME);

        Date date = null;
        try {
            date = simpleDateFormat.parse(dateTimeString);
        } catch (ParseException e) {
            date = new Date();
            LOG.warn(String.format("Parsing dateTimeString: %s error, using current datetime", dateTimeString));
        }

        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, daysToAdd);

        return simpleDateFormat.format(calendar.getTime());
    }
    
    /**
     * 取给定的二个日期之间差几年
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @return 年差
     */
    public static long getYearBetween(String beginDate, String endDate) {
    	SimpleDateFormat df = new SimpleDateFormat(FORMAT_DATE);
    	Date bDate = null;
    	Date nDate = null;
    	try {
    		bDate = df.parse(beginDate);
    		nDate = df.parse(endDate);
    	} catch (ParseException e) {
            LOG.warn(String.format("Parsing dateTimeString: %s,%s error", beginDate, endDate));
        }	
        long ms = nDate.getTime() - bDate.getTime();
        long year = (ms/(24*60*60*1000))/365;
        return year;
    }
    
    /**
     * 取给定的二个时间之间的时间段描述
     * @param beginDateTime 开始时间
     * @param endDateTime 结束时间
     * @return
     */
    public static String getDateStrBetween(String beginDateTime, String endDateTime) {
    	SimpleDateFormat df = new SimpleDateFormat(FORMAT_DATE_TIME);
    	Date bDate = null;
    	Date nDate = null;
    	try {
    		bDate = df.parse(beginDateTime);
    		nDate = df.parse(endDateTime);
    	} catch (ParseException e) {
            LOG.warn(String.format("Parsing dateTimeString: %s,%s error", beginDateTime, endDateTime));
        }	
        Long ms = nDate.getTime() - bDate.getTime();
        Long year = (ms/(24*60*60*1000))/365;
        Long day = ms/(24*60*60*1000);
		Long hour = (ms/(60*60*1000) - day*24);
        Long min = ((ms/(60*1000)) - day*24*60 - hour*60);
        Long s = (ms/1000 - day*24*60*60 - hour*60*60 - min*60);
        String result = "";
        if(year > 0) {
        	result = year.toString() + "年前";
        } else if(day > 0) {
        	result = day.toString() + "天前";
        } else if(hour > 0) {
        	result = hour.toString() + "小时" + min.toString() + "分钟前";
        } else if(min > 0) {
        	result = min.toString() + "分钟前";
        } else if(s >0) {
        	result = s.toString() + "秒钟前";
        }
        return result;
    }
    
    /**
     * 在给定的时间上添加给定的月数
     * @param monthsToAdd 加几月
     * @param dateTimeString 给定的时间
     * @return 加上给定月数的时间
     */
    public static String addMonth(Integer monthsToAdd, String dateTimeString) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_DATE_TIME);

        Date date = null;
        try {
            date = simpleDateFormat.parse(dateTimeString);
        } catch (ParseException e) {
            date = new Date();
            LOG.warn(String.format("Parsing dateTimeString: %s error, using current datetime", dateTimeString));
        }

        calendar.setTime(date);
        calendar.add(Calendar.MONTH, monthsToAdd);

        return simpleDateFormat.format(calendar.getTime());
    }
    
    /**
     * 在给定的时间上添加给定的周数
     * @param monthsToAdd 加几周
     * @param dateTimeString 给定的时间
     * @return 加上给定周数的时间
     */
    public static String addWeek(Integer weeksToAdd, String dateTimeString) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_DATE_TIME);

        Date date = null;
        try {
            date = simpleDateFormat.parse(dateTimeString);
        } catch (ParseException e) {
            date = new Date();
            LOG.warn(String.format("Parsing dateTimeString: %s error, using current datetime", dateTimeString));
        }

        calendar.setTime(date);
        calendar.add(Calendar.WEEK_OF_MONTH, weeksToAdd);

        return simpleDateFormat.format(calendar.getTime());
    }
    
    /**
     * 在给定的时间上添加给定的小时数
     * @param monthsToAdd 加几小时
     * @param dateTimeString 给定的时间
     * @return 加上给定小时数的时间
     */
    public static String addHour(Integer hoursToAdd, String dateTimeString) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_DATE_TIME);

        Date date = null;
        try {
            date = simpleDateFormat.parse(dateTimeString);
        } catch (ParseException e) {
            date = new Date();
            LOG.warn(String.format("Parsing dateTimeString: %s error, using current datetime", dateTimeString));
        }

        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hoursToAdd);

        return simpleDateFormat.format(calendar.getTime());
    }
    
    /**
     * 在给定的时间上添加给定的分钟数
     * @param monthsToAdd 加几分钟
     * @param dateTimeString 给定的时间
     * @return 加上给定分钟数的时间
     */
    public static String addMinute(Integer minuteToAdd, String dateTimeString) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_DATE_TIME);

        Date date = null;
        try {
            date = simpleDateFormat.parse(dateTimeString);
        } catch (ParseException e) {
            date = new Date();
            LOG.warn(String.format("Parsing dateTimeString: %s error, using current datetime", dateTimeString));
        }

        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minuteToAdd);

        return simpleDateFormat.format(calendar.getTime());
    }
    
    /**
     * 在给定的时间上添加给定的秒钟数
     * @param monthsToAdd 加几秒钟
     * @param dateTimeString 给定的时间
     * @return 加上给定秒钟数的时间
     */
    public static String addSecond(Integer secondToAdd, String dateTimeString) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_DATE_TIME);

        Date date = null;
        try {
            date = simpleDateFormat.parse(dateTimeString);
        } catch (ParseException e) {
            date = new Date();
            LOG.warn(String.format("Parsing dateTimeString: %s error, using current datetime", dateTimeString));
        }

        calendar.setTime(date);
        calendar.add(Calendar.SECOND, secondToAdd);

        return simpleDateFormat.format(calendar.getTime());
    }
    
    /**
     * 在给定的时间上添加给定的天数
     * @param daysToAdd 加几天
     * @param dateTimeString 给定的时间
     * @return 加上给定天数的时间
     */
    public static String addDayFormatDate(Integer daysToAdd, String dateTimeString) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_DATE);

        Date date = null;
        try {
            date = simpleDateFormat.parse(dateTimeString);
        } catch (ParseException e) {
            date = new Date();
            LOG.warn(String.format("Parsing dateTimeString: %s error, using current datetime", dateTimeString));
        }

        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, daysToAdd);

        return simpleDateFormat.format(calendar.getTime());
    }
    /**
	 * 取出当年的 第N周的 开始日期 和结束日期
	 * @param year 年
	 * @param week 第N周
	 * @return 开始日期 和结束日期
	 */
	public static String getDayInWeek(int year,int week){
		String firstDay ; 
		String lastDay ;
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		Calendar calFirstDayOfTheYear = new GregorianCalendar(year,Calendar.JANUARY, 1);

    	calFirstDayOfTheYear.add(Calendar.DATE, 7 * (week-1));

    	int dayOfWeek = calFirstDayOfTheYear.get(Calendar.DAY_OF_WEEK); 

    	Calendar calFirstDayInWeek = (Calendar)calFirstDayOfTheYear.clone();
    	calFirstDayInWeek.add(Calendar.DATE, 
    			calFirstDayOfTheYear.getActualMinimum(Calendar.DAY_OF_WEEK) - dayOfWeek);

	    Date firstDayInWeek = calFirstDayInWeek.getTime();

	    //System.out.println(year + "年第" + week + "个礼拜的第一天是" + df.format(firstDayInWeek));
	    firstDay = df.format(firstDayInWeek);
	    

	    Calendar calLastDayInWeek = (Calendar)calFirstDayOfTheYear.clone();

	    calLastDayInWeek.add(Calendar.DATE, 

	    		calFirstDayOfTheYear.getActualMaximum(Calendar.DAY_OF_WEEK) - dayOfWeek);

	    Date lastDayInWeek = calLastDayInWeek.getTime();

	    //System.out.println(year + "年第" + week + "个礼拜的最后一天是" + df.format(lastDayInWeek));
	    lastDay = df.format(lastDayInWeek);
	    return firstDay+","+lastDay;
	}
	/**
	 * 根据给定日期字符串 取出 当前日期为第N周
	 * @param date 日期时间字符串
	 * @return 第N周
	 */
	public static int getWeek(String date){
		int week =0; 
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date parseDate;
		try {
			parseDate = sdf.parse(date);
			Calendar cal = Calendar.getInstance();
			cal.setTime(parseDate);
			week = cal.get(Calendar.WEEK_OF_YEAR);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return week;
	}

	
	/**
	 * 根据年份，周数取得该周起始和结束日期
	 * @param year 年份
	 * @param n 周数
	 * @return 起始和结束日期的数组
	 */
	public static String[] getWeek(int year, int n){
		String[] result = new String[2];
		
		//先滚动到该年
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		
		//滚动到周:
		c.set(Calendar.WEEK_OF_YEAR,n);
		//得到该周第一天:
		c.set(Calendar.DAY_OF_WEEK,1);
		result[0] = String.valueOf(new SimpleDateFormat(FORMAT_DATE).format(c.getTime()));
		//最后一天:
		c.set(Calendar.DAY_OF_WEEK,7);
		result[1] = String.valueOf(new SimpleDateFormat(FORMAT_DATE).format(c.getTime()));
		
		return result;
	}
	
	/**
	 * 根据年份，月数取得该月起始和结束日期
	 * @param year 年份
	 * @param n 月数
	 * @return 起始和结束日期的数组
	 */
	public static String[] getMonth(int year, int n){
		String[] result = new String[2];
		
		//先滚动到该年
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		
		//滚动到月:
		c.set(Calendar.MONTH, n - 1);
		//得到该月第一天:
		c.set(Calendar.DAY_OF_MONTH, 1);
		result[0] = String.valueOf(new SimpleDateFormat(FORMAT_DATE).format(c.getTime()));
		//最后一天:
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		result[1] = String.valueOf(new SimpleDateFormat(FORMAT_DATE).format(c.getTime()));
		
		return result;
	}
	
	/**
	 * 根据年份，季度数取得该季度起始和结束日期
	 * @param year 年份
	 * @param n 季度数
	 * @return 起始和结束日期的数组
	 */
	public static String[] getSeason(int year, int n){
		String[] result = new String[2];
		
		//先滚动到该年
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		
		//滚动到月:
		c.set(Calendar.MONTH, 3 * (n - 1));
		//得到该月第一天:
		c.set(Calendar.DAY_OF_MONTH, 1);
		result[0] = String.valueOf(new SimpleDateFormat(FORMAT_DATE).format(c.getTime()));
		//最后一天:
		c.set(Calendar.MONTH, n * 3 - 1);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		result[1] = String.valueOf(new SimpleDateFormat(FORMAT_DATE).format(c.getTime()));
		
		return result;
	}
	
	
}
