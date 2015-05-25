package com.liyan.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class CommonUtils {

    public static final Log LOG = LogFactory.getLog(CommonUtils.class);

    public static final String WORD_SEED = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    public static final String FORMAT_DATE = "yyyy-MM-dd";

    public static boolean isEmpty(Object param) {
        if (param == null)
            return true;
        else if (param instanceof Integer)
            return ((Integer) param).intValue() == 0;
        else if (param instanceof String)
            return "".equals((String) param);
        else
            return false;
    }

    public static String currentDateTimeString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_DATE_TIME);
        return simpleDateFormat.format(new Date());
    }

    public static String currentDateString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_DATE);
        return simpleDateFormat.format(new Date());
    }
    
    public static String nullToBlank(Object source) {
    	return source == null ? "" : source.toString();
    }
    public static String nullToZeroStr(String source) {
    	return source .equals("") ? "0" : source;
    }
    public static int nullToZero(Object obj) {
    	return (Integer) (obj == null ? 0 : obj);
    }
    public static int stringToInt(String source) {
        try {
            return Integer.parseInt(source);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    public static boolean isInteger(String str) {    
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");    
        return pattern.matcher(str).matches();    
    }  
    public static boolean intStringToBoolean(String source) {
        return stringToInt(source) > 0 ? true : false;
    }

    public static String getYearFromDateString(String dateString) {
        try {
            return dateString.substring(0, 4);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getMonthFromDateString(String dateString) {
        try {
            //return dateString.substring(5, 7);
        	return new Integer(dateString.substring(5, 7)).toString();
        } catch (Exception e) {
            return null;
        }
    }

    public static String getDayFromDateString(String dateString) {
        try {
            //return dateString.substring(8, 10);
        	return new Integer(dateString.substring(8, 10)).toString();
        } catch (Exception e) {
            return null;
        }
    }
    
    public static String getDateFromDatetimeString(String datetimeString) {
        try {
            return datetimeString.substring(0, 10);
        } catch (Exception e) {
            return null;
        }
    }

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

    public static String generateString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(WORD_SEED.charAt(random.nextInt(WORD_SEED.length())));
        }
        return sb.toString();
    }

    public static String getFileExtension(String fileNameWithExtension) {
        return fileNameWithExtension.lastIndexOf(".") == -1
                ? null
                : fileNameWithExtension.substring(fileNameWithExtension.lastIndexOf("."));
    }

    public static String generateFileName(String fileNameWithExtension, int destFileNameLength) {
        return generateString(destFileNameLength) + getFileExtension(fileNameWithExtension);
    }

    @SuppressWarnings("unchecked")
    public static void copyPropertiesIgnoreNullValue(Object dest, Object source) {
        try {
            Map<String, Object> propertyMap = BeanUtils.describe(source);
            Map<String, Object> tmpMap = new HashMap<String, Object>(propertyMap);
            Set<String> propertyKeySet = propertyMap.keySet();

            for (String propertyKey : propertyKeySet) {
                Object value = propertyMap.get(propertyKey);
                if (value instanceof Collection<?>)
                	tmpMap.remove(propertyKey);
                if (value == null)
                    tmpMap.remove(propertyKey);
            }

            BeanUtils.populate(dest, tmpMap);
            //System.out.println(dest);

        } catch (IllegalAccessException e) {
            if (LOG.isWarnEnabled())
                LOG.warn(String.format("%s:%s", CommonUtils.class.getName(), "copyPropertiesIgnoreNullValue"));
        } catch (InvocationTargetException e) {
            if (LOG.isWarnEnabled())
                LOG.warn(String.format("%s:%s", CommonUtils.class.getName(), "copyPropertiesIgnoreNullValue"));
        } catch (NoSuchMethodException e) {
            if (LOG.isWarnEnabled())
                LOG.warn(String.format("%s:%s", CommonUtils.class.getName(), "copyPropertiesIgnoreNullValue"));
        }
    }
    
    public static boolean isNumberByString(String s){
		Pattern pattern = Pattern.compile("\\d+.?\\d+");
		Matcher matcher = pattern.matcher(s);
		if (matcher.find()){
			if(s.equals(matcher.group())){
				return true;
			}
		}
		return false;
    }

	public static String generateFile(String path, File image,
			String imageFileName) throws Exception{
		
		//创建文件夹
		File imgPath = new File(path);
		if (!imgPath.exists()) {
			imgPath.mkdirs();
	    }
		File fileDat = new File(path + "\\"+imageFileName);
		
		FileOutputStream fos = new FileOutputStream(fileDat);
        if(image!=null){
        	
			// 建立文件上传流
	        FileInputStream fis = new FileInputStream(image);
	        byte[] buffer = new byte[1024];
	        int len = 0;
	        while ((len = fis.read(buffer)) > 0) {
	            fos.write(buffer, 0, len);
	        }
			fos.close();
			fis.close();
        }
		return path+"\\"+imageFileName ;
	}
	public static void deleteFile(File file){ 
		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
			} else if (file.isDirectory()) {
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					deleteFile(files[i]);
				}
			}
			file.delete();
		}
	}
}
