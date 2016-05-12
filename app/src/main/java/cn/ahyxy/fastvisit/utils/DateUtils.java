package cn.ahyxy.fastvisit.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class DateUtils
{
    public static final String FORMAT = "yyyy-MM-dd HH:mm";
    public static final String FORMAT1 = "yyyy/MM/dd HH:mm";
    public static final String FORMATHM = "HH:mm";

    static long TM_ZERO = new Date(0, 0, 1).getTime();
    static long TM_DAY = 3600 * 24 * 1000;
    private static final int YEAR = 365 * 24 * 60 * 60;// 年
    private static final int MONTH = 30 * 24 * 60 * 60;// 月
    private static final int DAY = 24 * 60 * 60;// 天
    private static final int HOUR = 60 * 60;// 小时
    private static final int MINUTE = 60;// 分钟

    public static Date getToday()
    {
        return getDayOf(0);
    }

    public static Date getTomorrow()
    {
        return getDayOf(1);
    }

    public static Date getYestoday()
    {
        return getDayOf(-1);

    }

    public static Date getDayOf(int day)
    {
        long time = timestamp();
        time -= (time - TM_ZERO) % TM_DAY;
        time += day * TM_DAY;
        return new Date(time);
    }

    public static Date getNow()
    {
        return new Date(timestamp());
    }

    public static Date getTime(long time)
    {
        return new Date(time);
    }

    public static long timestamp()
    {
        return System.currentTimeMillis();
    }

    public static long timestamp(Date d)
    {
        return d.getTime();
    }

    public static long timediff(long time)
    {
        return Math.abs(timestamp() - time);
    }

    public static String formatTime(long time, String fmt)
    {
        SimpleDateFormat f = new SimpleDateFormat(fmt);
        String ret = "";
        ret = f.format(new Date(time * 1000));
        return ret;
    }

    public static String formatCurTime(String fmt)
    {
        SimpleDateFormat f = new SimpleDateFormat(fmt);
        String ret = "";
        ret = f.format(getNow());
        return ret;
    }

    public static String StringData()
    {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if ("1".equals(mWay)) {
            mWay = "天";
        } else if ("2".equals(mWay)) {
            mWay = "一";
        } else if ("3".equals(mWay)) {
            mWay = "二";
        } else if ("4".equals(mWay)) {
            mWay = "三";
        } else if ("5".equals(mWay)) {
            mWay = "四";
        } else if ("6".equals(mWay)) {
            mWay = "五";
        } else if ("7".equals(mWay)) {
            mWay = "六";
        }
        return mYear + "-" + mMonth + "-" + mDay + " 星期" + mWay;
    }


}
