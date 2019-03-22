package zhouxu.site.jmx.plugin.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期转换工具
 *
 * @author zhouxu
 * @date 2019/03/21 16:57
 **/
public final class DateUtils {

    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

    private static final String TIME_FORMATE = "yyyy-MM-dd  HH:mm:ss";

    private DateUtils() {
        throw new AssertionError("不允许实例化此对象");
    }
    /**
     * 获取秒时间忽略毫秒
     * @param millisecond
     * @return long
     * @throws:
     * @author zhouxu
     * @date 2019/3/22 14:31
     */
    public static long getSecends(Long millisecond ){
        String timestamp = String.valueOf(millisecond/1000)+"000";
        return Long.valueOf(timestamp);
    }

    /**
     * 获取字符串时间
     * @param millisecond
     * @return java.lang.String
     * @throws:
     * @author zhouxu
     * @date 2019/3/22 14:31
     */
    public static String format(Long millisecond ){
        if(millisecond==null){
            return null;
        }
        Date date = new Date(millisecond);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_FORMATE);
        return simpleDateFormat.format(date);
    }

    /**
     * 获取字符串时间
     * @param date
     * @return java.lang.String
     * @throws:
     * @author zhouxu
     * @date 2019/3/22 14:32
     */
    public static String format(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_FORMATE);
        return simpleDateFormat.format(date);
    }

    /**
     * 获取字符串时间,剔除不同其他类型
     * @param object
     * @return java.lang.String
     * @throws:
     * @author zhouxu
     * @date 2019/3/22 14:32
     */
    public static String format(Object object){
        if(!(object instanceof Date)){
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_FORMATE);
        return simpleDateFormat.format(object);
    }

    /**
     * 获取utc时间
     * @param utcStr
     * @return java.lang.Long
     * @throws:
     * @author zhouxu
     * @date 2019/3/22 14:32
     */
    public static Long getUtcSecends(String utcStr){
        int index =utcStr.indexOf('.');
        if(index!=-1){
            utcStr = utcStr.substring(0,index)+"Z";
        }
        // influxdb默认到
        String utcFormat="yyyy-MM-dd'T'HH:mm:ss'Z'";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(utcFormat);
        Date date = null;
        try {
            date = simpleDateFormat.parse(utcStr);
        } catch (ParseException e) {
            logger.error("", e);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 8);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取秒时间忽略毫秒
     * @param date
     * @return long
     * @throws:
     * @author zhouxu
     * @date 2019/3/22 14:32
     */
    public static long getSecends(Date date){
        if (null == date) {
            return 0L;
        }
        String timestamp = String.valueOf(date.getTime()/1000)+"000";
        return Long.valueOf(timestamp);
    }

    /**
     * 获取昨天时间
     * @param date
     * @return java.util.Date
     * @throws:
     * @author zhouxu
     * @date 2019/3/22 14:33
     */
    public static Date getYesterday(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,-1);
        date = calendar.getTime();
        return date;
    }

    /**
     * 获取几天前时间
     * @param dateStr
	 * @param ago
     * @return java.util.Date
     * @throws:
     * @author zhouxu
     * @date 2019/3/22 14:33
     */
    public static Date getSeveralDaysAgo(String dateStr,int ago) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_FORMATE);
        Date date = simpleDateFormat.parse(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,-1*ago);
        date = calendar.getTime();
        return date;
    }

    /**
     * 获取昨天时间
     * @param dateStr
     * @return java.util.Date
     * @throws:
     * @author zhouxu
     * @date 2019/3/22 14:33
     */
    public static Date getYesterday(String dateStr) throws ParseException {
        return getSeveralDaysAgo(dateStr,1);
    }

    /**
     * 获取昨天时间
     * @param dateStr
     * @return java.util.Date
     * @throws: ParseException
     * @author zhouxu
     * @date 2019/3/22 14:33
     */
    public static Date getAWakeAgo(String dateStr) throws ParseException {
        return getSeveralDaysAgo(dateStr,7);
    }

    /**
     * 字符串转Date
     * @param dateStr
     * @return java.util.Date
     * @throws:
     * @author zhouxu
     * @date 2019/3/22 14:34
     */
    public static Date parse(String dateStr) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_FORMATE);
        final String key = ":";
        if(dateStr.indexOf(key) == -1) {
            //未设置时/分/秒时规整到凌晨
            dateStr+=" 0:0:0";
        }
        return simpleDateFormat.parse(dateStr);
    }

    /**
     * english 时间字符串转chiness时间字符串
     * @param dateStr
     * @return java.lang.String
     * @throws:
     * @author zhouxu
     * @date 2019/3/22 14:34
     */
    public static String formatEn2ch(String dateStr) throws ParseException {
        if(dateStr == null || "".equals(dateStr)) {
            return "";
        }
        SimpleDateFormat enSimpleDateFormat= new SimpleDateFormat("MMM d, yyyy h:m:s aa", Locale.ENGLISH);
        Date parse = enSimpleDateFormat.parse(dateStr);
        SimpleDateFormat chSimpleDateFormat= new SimpleDateFormat(TIME_FORMATE);
        return chSimpleDateFormat.format(parse);
    }
} 
