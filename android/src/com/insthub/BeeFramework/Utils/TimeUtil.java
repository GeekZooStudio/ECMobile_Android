package com.insthub.BeeFramework.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.lang.Math;

/**
 * User: howie
 * Date: 13-5-11
 * Time: 下午4:09
 */
public class TimeUtil {

    public static String timeAgo(String timeStr)
    {
        Date date = null;
        try
        {
            SimpleDateFormat format =   new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss Z" );
            date = format.parse(timeStr);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            return "";
        }


        long timeStamp = date.getTime();

        Date currentTime = new Date();
        long currentTimeStamp = currentTime.getTime();
        long seconds = (currentTimeStamp - timeStamp)/1000;

        long minutes = Math.abs(seconds/60);
        long hours = Math.abs(minutes/60);
        long days = Math.abs(hours/24);



        if ( seconds <= 15 )
        {
            return "刚刚";
        }
        else if ( seconds < 60 )
        {
            return seconds+"秒前";
        }
        else if ( seconds < 120 )
        {
            return"1分钟前";
        }
        else if ( minutes < 60 )
        {
            return minutes+"分钟前";
        }
        else if ( minutes < 120 )
        {
            return "1小时前";
        }
        else if ( hours < 24 )
        {
            return hours +"小时前";
        }
        else if ( hours < 24 * 2 )
        {
            return "1天前";
        }
        else if ( days < 30 )
        {
            return days+"天前" ;
        }
        else
        {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
            String dateString = formatter.format(date);
            return dateString;
        }

    }

    public static String timeLeft(String timeStr)
    {
        Date date = null;
        try
        {
            SimpleDateFormat format =   new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss Z" );
            date = format.parse(timeStr);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            return "";
        }


        long timeStamp = date.getTime();

        Date currentTime = new Date();
        long currentTimeStamp = currentTime.getTime();

        long total_seconds = (timeStamp - currentTimeStamp)/1000;

        if (total_seconds <= 0)
        {
            return  "";
        }

        long days = Math.abs(total_seconds/(24*60*60));

        long hours = Math.abs((total_seconds - days*24*60*60)/(60*60));
        long minutes = Math.abs((total_seconds - days*24*60*60 - hours*60*60)/60);
        long seconds = Math.abs((total_seconds - days*24*60*60 - hours*60*60 -minutes*60));
        String leftTime;
        if (days > 0)
        {
            leftTime = days+"天" + hours + "小时" + minutes +"分" +seconds+"秒";
        }
        else if (hours > 0)
        {
            leftTime = hours + "小时" + minutes +"分" +seconds+"秒";
        }
        else if (minutes > 0)
        {
            leftTime = minutes +"分" +seconds+"秒";
        }
        else if (seconds > 0)
        {
            leftTime = seconds+"秒";
        }
        else
        {
            leftTime = "0秒";
        }

         return leftTime;
    }
}
