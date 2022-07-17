package com.ziroom.suzaku.main.utils;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author libingsi
 * @date 2021/6/22 14:57
 */
@Slf4j
public class DateUtils {

    public final static String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    private final static String FORMAT_DATETIME_2 = "yyyy/MM/dd HH:mm:ss";
    private final static String FORMAT_DATETIME_ISO = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private final static String FORMAT_DATE = "yyyy-MM-dd";
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(FORMAT_DATETIME);

    public static long stringToLong(String strTime) {
        Date date = stringToDate(strTime, FORMAT_DATETIME);
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date);
            return currentTime;
        }

    }

    public static long dateToLong(Date date) {
        return date.getTime();
    }

    /**
     * 加num分钟
     * 日期后的时间
     * @param dateTime
     * @param num
     * @return
     */
    public static String addMinute(String dateTime,long num){
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATETIME);
        Date time = stringToDate(dateTime,FORMAT_DATETIME);
        long newNum = num*60*1000;
        Date afterDate = new Date( time.getTime() + newNum);
        return sdf.format(afterDate);
    }

    /**
     * 减num分钟
     * 日期后的时间
     * @param dateTime
     * @param num
     * @return
     */
    public static String subMinute(String dateTime,long num){
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATETIME);
        Date time = stringToDate(dateTime,FORMAT_DATETIME);
        long newNum = num*60*1000;
        Date afterDate = new Date( time.getTime() - newNum);
        return sdf.format(afterDate);
    }

    public static Date stringToDate(String strTime, String formatType) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (Exception e) {
        }

        return date;
    }

    public static LocalDateTime dateToTime(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        String str = localDate.format(DateTimeFormatter.ofPattern(FORMAT_DATE)) + " 00:00:00";
        return LocalDateTime.parse(str, DateTimeFormatter.ofPattern(FORMAT_DATETIME));
    }

    /**
     * 将String转成LocalDateTime
     *
     * @param datetime
     * @return
     */
    public static LocalDateTime stringToLocalDateTime(String datetime) {

        DateTimeFormatter df = null;
        String datetimeStr = null;
            df = null;
            datetimeStr = datetime;
            if (datetime.length() <= 10) {
                datetimeStr = datetime + " 00:00:00";
            }
            if(datetimeStr.contains("-")){
                df = DateTimeFormatter.ofPattern(FORMAT_DATETIME);
            } else {

                df = DateTimeFormatter.ofPattern(FORMAT_DATETIME_2);
            }

            LocalDateTime parse = LocalDateTime.parse(datetimeStr, df);

        return parse;
    }

    /**
     * 将LocalDateTime转成String
     *
     * @param datetime
     * @return
     */
    public static String localDateTimeToString(LocalDateTime datetime) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(FORMAT_DATETIME);
        return df.format(datetime);
    }

    public static LocalDateTime nowDateTime() {
        return LocalDateTime.parse(getDateTime(), DateTimeFormatter.ofPattern(FORMAT_DATETIME));
    }

    public static LocalDate timeToDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        String str = localDateTime.format(DateTimeFormatter.ofPattern(FORMAT_DATE));
        return LocalDate.parse(str, DateTimeFormatter.ofPattern(FORMAT_DATE));
    }

    /**
     * 日期格式字符串转换成unix时间戳
     *
     * @param dateStr 字符串日期
     * @return
     * @author liyang
     */
    public static String date2TimeStamp(String dateStr) {
        String  delimiter = "-";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATETIME_2);
            if (dateStr.indexOf(delimiter) > 0) {
                SimpleDateFormat sdfe = new SimpleDateFormat(FORMAT_DATETIME);
                Date parse = sdfe.parse(dateStr);
                dateStr = sdf.format(parse);
            }
            return String.valueOf(sdf.parse(dateStr).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Unix时间戳转换成指定格式日期字符串
     *
     * @param timeStamp 时间戳 如："1588041211";
     *                  要格式化的格式 默认："yyyy/MM/dd HH:mm:ss"; 2020/04/28 10:33:31
     * @author liyang
     */
    public static String timeStamp2Date(String timeStamp) {
        Long timestamp = Long.parseLong(timeStamp) * 1000;
        String date = new SimpleDateFormat(FORMAT_DATETIME, Locale.CHINA).format(new Date(timestamp));
        return date;
    }
    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getNowDateTime() {
        return DATETIME_FORMATTER.format(LocalDateTime.now());
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getDateTime() {
        return DATETIME_FORMATTER.format(LocalDateTime.now());
    }

    /**
     * date 类型转换成 LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime dateToLozcalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime;
    }

    /**
     * 两个日期之间的时间差：分钟
     * @param startTime
     * @param endTime
     * @return
     */
    public static long dateDiffMin(String startTime, String endTime)  {
        long nd = 1000*24*60*60;
        long nh = 1000*60*60;
        long nm = 1000*60;
        long min = 0;
        long diff = stringToLong(endTime) - stringToLong(startTime);
        long day = diff/nd;
        long hour = diff%nd/nh;
        min = diff%nd%nh/nm;
        long time = day*24*60+hour*60+min;
        return time ;
    }

    public static String stringToFormatString(String dateTime){
        if (StringUtils.isBlank(dateTime)){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
        Date date = null;
        try {
            date = sdf.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf.format(date);
    }


    /**
     * 根据format类型将LocalDateTime转成String
     *
     * @param datetime
     * @param format
     * @return
     */
    public static String localDateTimeToString(LocalDateTime datetime,String format) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return df.format(datetime);
    }


    /**
     * 给需要序列化的实体增加LocalDateTime类型的支持
     * @param objectMapper
     */
    public static void setLocalDateTimeSerializer(ObjectMapper objectMapper){
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addSerializer(LocalDateTime.class,  new LocalDateTimeSerializer());
        objectMapper.registerModule(timeModule);
    }

    /**
     * 给需要反序列化的实体增加LocalDateTime类型的支持
     * @param objectMapper
     */
    public static void setLocalDateTimeDeserializer(ObjectMapper objectMapper){

        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        objectMapper.registerModule(timeModule);

    }
    static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
        @Override
        public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeNumber(localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
        }
    }

    /**
     * 时间戳反序列化时间
     */
    static class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            Long timestamp = jsonParser.getLongValue();
            return LocalDateTime.ofEpochSecond(timestamp / 1000, 0, ZoneOffset.ofHours(8));
        }


    }

    /**
     * 获取当天日期
     *
     * @return
     */
    public static String getToday() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DATETIME);
        String today = dateFormat.format(now);
        return today;
    }

    /**
     * 获取年
     *
     * @return
     */
    public static int getTodayYear() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.YEAR);
    }

    /**
     * 获取月
     *
     * @return
     */
    public static int getTodayMonth() {
        Calendar now = Calendar.getInstance();
        return (now.get(Calendar.MONTH) + 1);
    }

    /**
     * 获取日
     *
     * @return
     */
    public static int getTodayDay() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取时
     *
     * @return
     */
    public static int getTodayHour() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取分
     *
     * @return
     */
    public static int getTodayMinute() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.MINUTE);
    }

    /**
     * 获取秒
     *
     * @return
     */
    public static int getTodaySecond() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.SECOND);
    }

    /**
     * 转换mongo db iso 日期
     * @param dateStr
     * @return
     */
    public static Date dateToISODate(Date dateStr) {
        Date parse = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATETIME_ISO);
            parse = format.parse(format.format(dateStr));
        } catch (ParseException e) {
            log.info("e",e);
        }
        return parse;
    }

    public static String dateToStrLong(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    public static String dealDateFormat(String oldDate) {
        Date date1 = null;
        DateFormat df2 = null;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = df.parse(oldDate);
            SimpleDateFormat df1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
            date1 = df1.parse(date.toString());
            df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {

            e.printStackTrace();
        }
        return df2 != null ? df2.format(date1) : null;
    }

    public static Date getMericoDateTime(String date) {
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM,Locale.CHINA);
        Date date1 = null;
        try {
            date1 = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    public static Date dateFormate(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_DATETIME);
        Date dateTime = null;
        try {
            dateTime = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateTime;
    }

    /**
     * 根据format类型将dateDate转成String
     * @param dateDate
     * @param format
     * @return
     */
    public static String formatData(Date dateDate,String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    public static Date stringToDate(String time) {
        return stringToDate(time, FORMAT_DATE);
    }

    public static String stringToDate2(String time, String pattern)  {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf.format(date);
    }


    /**
     * 中国标准时间转换
     * @param utcTime
     * @return
     */
    public static String formatStrUTCToDateStr(String utcTime) {
        SimpleDateFormat dff1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS",Locale.CHINA);
        SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = null;
        try {
            date1 = dff1.parse(utcTime);
        } catch (ParseException e) {
            log.info("e",e);
        }
        return dff.format(date1);
    }

    /**
     * 时间戳转换,并增加一天
     * @param time
     * @return
     */
    public static String timeStampDate(String time) {
        Long timeLong = Long.parseLong(time);
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATETIME);
        Date date = null;
        Calendar cal=Calendar.getInstance();
        try {
            date = sdf.parse(sdf.format(timeLong));
            cal.setTime(date);
            cal.add(Calendar.DATE, 1);
        } catch (ParseException e) {
            log.info("e",e);
        }
        return sdf.format(cal.getTime());
    }

    /**
     * 是否是今天
     * @param date
     * @param startDate
     * @return
     */
    public static boolean isNow(String date,String startDate) {
        Date nowDate = dateFormate(date);
        Date start = dateFormate(startDate);
        return DateUtil.isSameDay(nowDate, start);
    }


    public static void main(String[] args) throws Exception {
        String time = "2021-11-24 00:00:00";
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATETIME);
        String format = sdf.format(now);
        System.out.println(isNow(format,time));
    }
}
