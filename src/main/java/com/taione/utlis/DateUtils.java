package com.taione.utlis;


import com.sun.xml.internal.ws.util.UtilException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DateUtils {

    public static final String DATE_PATTERN = "yyyyMMdd";
    public static final String DEFAULT_PATTERN = "yyyyMMddHHmmss";
    public static final String YEAR_MON_DAY_HOUR = "yyyyMMddHH";
    public static final String YEAR_MON_DAY_HOUR_MIN = "yyyyMMddHHmm";
    public static final String DEFAULT_DATE_PATTERN = "yyyyMMdd";
    public static final String CHINA_DATE_PATTERN = "yyyy-MM-dd";
    public static final String CHINA_YEAR_MIN="yyyy-MM-dd HH:mm:ss";


    private DateUtils() {
    }

    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    public static LocalDate currDate() {
        return LocalDate.now();
    }
    
    public static LocalTime currTime() {
        return LocalTime.now();
    }

    public static String format(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(DATE_FORMAT);
    }

    public static LocalDateTime parse(String localDateTimeStr, String pattern) {
        DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(localDateTimeStr, DATE_FORMAT);
    }

    public static String format(LocalDate localDate, String pattern) {
        DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern(pattern);
        return localDate.format(DATE_FORMAT);
    }

    public static LocalDate parseDate(String localDateStr, String pattern) {
        DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(localDateStr, DATE_FORMAT);
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        Instant instant = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static Date toDate(LocalDateTime datetime) {
        if (datetime == null) {
            return null;
        }
        Instant instant = datetime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public static Long toTimeSign(LocalDateTime datetime, String pattern) {
        if (datetime == null) {
            return null;
        }
        return Long.valueOf(DateUtils.format(datetime, pattern));
    }
    
    public static Long toTimeSign(LocalDate date, String pattern) {
        if (date == null) {
            return null;
        }
        return Long.valueOf(DateUtils.format(date, pattern));
    }

    public static Boolean between(LocalDateTime checkTime, LocalDateTime startTime, LocalDateTime endTime) {
        return (checkTime.isAfter(startTime) && checkTime.isBefore(endTime))
                || checkTime.isEqual(startTime) || checkTime.isEqual(endTime);
    }

    public static Long minusMin(LocalDateTime big, LocalDateTime little) {
        return (toDate(big).getTime() - toDate(little).getTime()) / 60000;
    }

    /**
     * 获取日期相差天数
     * @param date1
     * @param date2
     * @return
     */
    public static int getBetweenDay(Date date1, Date date2) {  
    Calendar d1 = new GregorianCalendar();  
    d1.setTime(date1);  
    Calendar d2 = new GregorianCalendar();  
    d2.setTime(date2);  
    int days = d2.get(Calendar.DAY_OF_YEAR)- d1.get(Calendar.DAY_OF_YEAR);  
    int y2 = d2.get(Calendar.YEAR);  
    if (d1.get(Calendar.YEAR) != y2) {  
        do {  
            days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);  
            d1.add(Calendar.YEAR, 1);  
            } while (d1.get(Calendar.YEAR) != y2);  
        }  
        return days;  
    }  
    
    /**
     * 获取本周的周一
     */
    public static LocalDate getMondayInThisWeek(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        int day = dayOfWeek.getValue();
        return date.minusDays(day - 1);
    }

    /**
     * 获取本周的周末
     */
    public static LocalDate getSundayInThisWeek(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        int day = dayOfWeek.getValue();
        int sunday = day - 7;
        return date.plusDays(Math.abs(sunday));
    }

    /**
     * 获取日期是否为同一天
     */
    public static boolean areSameDay(Date dateA,Date dateB) {
        Calendar calDateA = Calendar.getInstance();
        calDateA.setTime(dateA);
        Calendar calDateB = Calendar.getInstance();
        calDateB.setTime(dateB);
        return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)
                && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)
                &&  calDateA.get(Calendar.DAY_OF_MONTH) == calDateB.get(Calendar.DAY_OF_MONTH);
    }
    

    public static Boolean notBefore(LocalDateTime little, LocalDateTime big) {
        return little.isAfter(big) || little.isEqual(big);
    }
    /**
     * 把日期转为数字
     */
    public static Integer toNumber(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        String c=sdf.format(date);
        return Integer.parseInt(c);
    }
    /**
     * 获取月份数字
     */
    public static Integer toMonthTime(Date date){
         SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
         String c=sdf.format(date);
         return Integer.parseInt(c);
     }
    
    /**
     * 获取上周周末
     * @param
     */
    public static Date getLastWeekSunday(){

        Calendar date=Calendar.getInstance(Locale.CHINA);

        date.setFirstDayOfWeek(Calendar.MONDAY);//将每周第一天设为星期一，默认是星期天

        date.add(Calendar.WEEK_OF_MONTH,-1);//周数减一，即上周

        date.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);//日子设为星期天

        return date.getTime();
    }
    
    /**
     * 获取上周周末
     * @param
     */
    public static Date getLastWeekSunday(LocalDateTime opDate){
        
        Calendar date=Calendar.getInstance(Locale.CHINA);
        if (opDate!=null) {
            date.setTime(toDate(opDate));
        }
        date.setFirstDayOfWeek(Calendar.MONDAY);//将每周第一天设为星期一，默认是星期天
        
        date.add(Calendar.WEEK_OF_MONTH,-1);//周数减一，即上周
        
        date.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);//日子设为星期天
        
        return date.getTime();
    }
    
    /**
     * 获取上个月最后一天
     * @return
     */
    public static Date getMonthLastDay(){
        Calendar calendar = Calendar.getInstance();  
        int month = calendar.get(Calendar.MONTH);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));  
        return calendar.getTime();
    }
    
    /**
     * 获取上个月最后一天
     * @return
     */
    public static Date getMonthLastDay(LocalDateTime opDate){
        Calendar calendar = Calendar.getInstance();
        if (opDate!=null) {
            calendar.setTime(toDate(opDate));
        }
        int month = calendar.get(Calendar.MONTH);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));  
        return calendar.getTime();
    }
    /**
     * 获取本月第一天
     * @return
     */
    public static Date getMonthFirstDay(){
        Calendar c = Calendar.getInstance();    
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        return c.getTime();
    }
    /**
     * 通过偏移量获取本月第一天
     * @return
     */
    public static Date getMonthFirstDay(Integer offsetDays){
        Calendar c = Calendar.getInstance();
        if (offsetDays!=null && offsetDays.compareTo(0)>0) {
            c.setTime(toDate(now().minusDays(offsetDays)));
        }
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        return c.getTime();
    }
    public static Date getYesterDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return date;
    }
    /**
     * 获取本周周末
     * @return
     */
    public static Date getSundayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
         day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 7);
        return c.getTime();
       }
    
    /**
     * 通过偏移量获取本周周末
     * @return
     */
    public static Date getSundayOfThisWeek(Integer offsetDays) {
        Calendar c = Calendar.getInstance();
        if (offsetDays!=null && offsetDays.compareTo(0)>0) {
            c.setTime(toDate(now().minusDays(offsetDays)));
        }
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 7);
        return c.getTime();
    }

    /**
     * 将字符串转化为日期
     * @return
     */
    public static Date stringtoDate(String date, String pattern) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(DateUtils.CHINA_DATE_PATTERN);
        return format.parse(date);
    }

    public static Date getMondayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
         day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 1);
        return c.getTime();
       }
    
    
    public static Date getStratTime(String str) throws ParseException{
        String substring = str.substring(0,15);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date parse = sdf.parse(substring);
        return  parse;
    }
    public static Date getEndTime(String str) throws ParseException{
        String substring = str.substring(18);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.parse(substring);
    }
    public static void main(String[] args) throws IOException, UtilException, ParseException {
       /* String date = "2017-03-21 23:59:59";
        List<String> strings = retentionDate(date);
        for (String string : strings) {
            System.out.println(string);
        }*/
       Random r = new Random();

        int num = Math.abs(r.nextInt(15));
        System.out.println(num);
        /*Date date = DateUtils.stringtoDate("2012-02-01 23:59:59", DateUtils.CHINA_DATE_PATTERN);
        System.out.println(date.toString());
       *//* int day = 30;
        for(int i = 0; i < day; i++){
            LocalDateTime ldt = DateUtils.now().minusDays(30L).plusDays(i);
            String format = DateUtils.format(ldt,DateUtils.CHINA_DATE_PATTERN);
            System.out.println(format);
        }*//*
        Calendar cal = Calendar.getInstance();
        String start = "2012-02-01";
        String end = "2012-03-02";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dBegin = sdf.parse(start);
        Date dEnd = sdf.parse(end);
        List<String> lDate = findDates(dBegin, dEnd);
        for (String datez : lDate)
        {
            System.out.println(datez);
        }*/

    }
    public static List<String> findDates(Date dBegin, Date dEnd) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<String> lDate = new ArrayList();
        lDate.add(sdf.format(dBegin));
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(sdf.format(calBegin.getTime()));
        }
        return lDate;
    }



    public static String format(Date Time) {
        if(Time ==null){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(Time);
    }
    /**
     * 获取本月最后一天
     * @return
     */
    public static Date getLastDayOfThisMonth() {   
        Calendar calendar = Calendar.getInstance();   
        calendar.set(Calendar.DAY_OF_MONTH, calendar   
                .getActualMaximum(Calendar.DAY_OF_MONTH));
//        calendar.set( Calendar.DATE, 1);
//        calendar.roll(Calendar.DATE, - 1);
        Date date = calendar.getTime();
        return date;   
    }
    
    public static int getCommissionYXRQ(Integer validYear) {
        if(validYear == null) validYear = 0;
        Calendar nowc = Calendar.getInstance();
        nowc.set(Calendar.YEAR, nowc.get(Calendar.YEAR)+validYear);
        return toNumber(nowc.getTime());
    }

    public static LocalTime parseTime(String localTimeStr, String pattern) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern);
        return LocalTime.parse(localTimeStr, format);
    }
    /**
     * 传入一个日期，获取当前日期的次日，2天，....日期
     */
    public static  List<String> retentionDate(String date) throws ParseException {
        List<String> dates = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date parse = sdf.parse(date);
        LocalDateTime time = toLocalDateTime(parse);
        dates.add(format(time.plusDays(1L),DateUtils.CHINA_DATE_PATTERN));
        dates.add(format(time.plusDays(2L),DateUtils.CHINA_DATE_PATTERN));
        dates.add(format(time.plusDays(3L),DateUtils.CHINA_DATE_PATTERN));
        dates.add(format(time.plusDays(4L),DateUtils.CHINA_DATE_PATTERN));
        dates.add(format(time.plusDays(5L),DateUtils.CHINA_DATE_PATTERN));
        dates.add(format(time.plusDays(6L),DateUtils.CHINA_DATE_PATTERN));
        dates.add(format(time.plusDays(7L),DateUtils.CHINA_DATE_PATTERN));
        dates.add(format(time.plusDays(15L),DateUtils.CHINA_DATE_PATTERN));
        dates.add(format(time.plusDays(29L),DateUtils.CHINA_DATE_PATTERN));
        return dates;
    }
}
