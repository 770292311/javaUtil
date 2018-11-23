/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: DateUtil
 * Author:   adm
 * Date:     2018/11/14 15:34
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.java.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author adm
 * @create 2018/11/14
 * @since 1.0.0
 */
public class DateUtil {
    private static final SimpleDateFormat  sdf=new SimpleDateFormat("yyyy-MM-dd");
    private static Calendar calendar = Calendar.getInstance();
    /**获取给定时间date前dayAgo前后的时间
     * @param date 给定时间
     * @param dayAmount 前后时间
     */
    public static String getTimeAmount(String date,int dayAmount){
        Date time = null;
        try {
            calendar.setTime(sdf.parse(date));
            calendar.add(Calendar.DAY_OF_MONTH,dayAmount);
            time = calendar.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sdf.format(time);
    }

    /**获取上月的第一天
     * @return
     */
    public static String getLastMonthFirstDay(){
        Date currentDate = new Date();
        calendar.setTime(currentDate);
        calendar.add(Calendar.MONTH,-1);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        Date time = calendar.getTime();
        return sdf.format(time);
    }

    /**获取上月最后一天
     * @return
     */
    public static String getLastMonthLastDay(){
        Date currentDate = new Date();
        calendar.setTime(currentDate);
        calendar.set(Calendar.DAY_OF_MONTH,0);
        Date time = calendar.getTime();
        return sdf.format(time);
    }

    /**获取只当日期的月份的最后一天
     * @param date
     * @return
     */
    public static String getGivenTimeLastDay(String date){
        String resTime="";
        try {
            Date parseDate = sdf.parse(date);
            calendar.setTime(parseDate);
            calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            Date resDate = calendar.getTime();

            resTime = sdf.format(resDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return resTime;
    }

    /**两个时间相差的天数
     * @param date1
     * @param date2
     * @return
     */
    public static int getDifferOfTwoDate(String date1,String date2){
        int differ=0;
        try {
            Date time1 = sdf.parse(date1);
            Date time2 = sdf.parse(date2);
            calendar.setTime(time1);
            int day1 = calendar.get(Calendar.DAY_OF_YEAR);
            calendar.setTime(time2);
            int day2 = calendar.get(Calendar.DAY_OF_YEAR);
            differ = Math.abs(day2-day1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return differ;
    }
    public static void main(String[] args) throws ParseException {
        int differ = getDifferOfTwoDate("2018-05-01", "2018-12-02");
        System.out.println(differ);
    }


}

