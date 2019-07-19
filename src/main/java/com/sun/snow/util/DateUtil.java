package com.sun.snow.util;


import org.apache.tomcat.jni.Local;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;


/**
 * @Author: lish
 * @Description:
 * @Date: Create in 2019/7/17
 */
public class DateUtil {


    /***
    * @Author: lish
    * @Date: 2019/7/17
    * @Description: 获取间隔的时间
    * @Param:  * @param time 传入的时间
    * @param flag 0查询之前的时间与现在的间隔 1查询之后的时间与现在的间隔
    */
    public static int getDays(String time,String format,int flag){
        int days = 0;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            Date paramDate = dateFormat.parse(time);
            Date nowDate = new Date();
            if (0 == flag){
                days = (int) ((nowDate.getTime() - paramDate.getTime()) / (1000*3600*24));
            }else if (1 == flag){
                days = (int) ((paramDate.getTime() - nowDate.getTime()) / (1000*3600*24));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    public static void main(String[] args) {
        /*String dayAfterTommorrow = "20140116";
        LocalDate formatted = LocalDate.parse(dayAfterTommorrow,
                DateTimeFormatter.BASIC_ISO_DATE);
        System.out.println(formatted);
        LocalDate paramDate = LocalDate.now();
        System.out.println(paramDate);
        Period periodToNextJavaRelease = Period.between(formatted, paramDate);
        System.out.println(periodToNextJavaRelease.getDays());*/
        int days = getDays("2016-02-20","YYYY-MM-dd",0);
        System.out.println(days);
    }
}
