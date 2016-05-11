package cn.ahyxy.fastvisit.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Utils
{
    private static long lastClickTime;





    /**
     * 检查是否有网络
     */
    public static boolean isNetworkAvailable(Context context) {
        NetworkInfo info = getNetworkInfo(context);

        return info != null && info.isAvailable();
    }

    /**
     * 检查是否是WIFI
     */
    public static boolean isWifi(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI)
                return true;
        }
        return false;
    }

    /**
     * 检查是否是移动网络
     */
    public static boolean isMobile(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE)
                return true;
        }
        return false;
    }

    public static NetworkInfo getNetworkInfo(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    public static double getRetainDecimal(double value, int length) {
        BigDecimal b = new BigDecimal(value);
        return b.setScale(length, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    // Convert Unix timestamp to normal date style
    public static String timeStamp2Date(String timestampString) {
        Long timestamp = Long.parseLong(timestampString) * 1000;
        return new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm")
                .format(new java.util.Date(timestamp));
    }

    public static String timeStamp2DateList(String timestampString) {
        Long timestamp = Long.parseLong(timestampString) * 1000;
        return new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm")
                .format(new java.util.Date(timestamp));
    }

    public static String timeStamp2DateUnList(String timestampString) {
        Long timestamp = Long.parseLong(timestampString) * 1000;
        return new java.text.SimpleDateFormat("MM/dd HH:mm")
                .format(new java.util.Date(timestamp));
    }

    public static String timeStamp3Date(String timestampString) {
        Long timestamp = Long.parseLong(timestampString) * 1000;
        return new java.text.SimpleDateFormat("MM/dd HH:mm")
                .format(new java.util.Date(timestamp));
    }

    public static String timeStamp4Date(String timestampString) {
        Long timestamp = Long.parseLong(timestampString) * 1000;
        return new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm")
                .format(new java.util.Date(timestamp));
    }

    public static String timeStamp5Date(String timestampString) {
        Long timestamp = Long.parseLong(timestampString) * 1000;
        return new java.text.SimpleDateFormat("yyyy/MM/dd")
                .format(new java.util.Date(timestamp));
    }

    public static String timeStampFreshDateList(String timestampString) {
        Long timestamp = Long.parseLong(timestampString) * 1000;
        return new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                .format(new java.util.Date(timestamp));
    }

    public static boolean isJSONObject(String json) {
        try {
            JSONObject jb = new JSONObject(json);
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void showSoftInput(Context mContext, View edittext) {
        edittext.setFocusable(true);
        edittext.setFocusableInTouchMode(true);
        // 请求获得焦点
        edittext.requestFocus();
        InputMethodManager imm = (InputMethodManager) edittext.
                getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        // 显示键盘
        imm.showSoftInput(edittext, 0);
    }

    public static void hideSoftInputFromWindow(Activity mActivity) {
        View view = mActivity.getWindow().peekDecorView();
        if (view != null && view.getWindowToken() != null) {
            InputMethodManager imm = (InputMethodManager) mActivity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    //倒序排列
    public static LinkedHashMap<String, Long> sortMapWithValue(Map<String, Long> oldMap) {
        ArrayList<Map.Entry<String, Long>> list = new ArrayList<Map.Entry<String, Long>>(oldMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Long>>()
        {
            @Override
            public int compare(Map.Entry<String, Long> lhs, Map.Entry<String, Long> rhs)
            {
                if (lhs.getValue().equals(rhs.getValue())) {
                    return 0;
                } else if (lhs.getValue() - rhs.getValue() > 0) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        LinkedHashMap<String, Long> newMap = new LinkedHashMap<String, Long>();
        for (int i = 0; i < list.size(); i++) {
            newMap.put(list.get(i).getKey(), list.get(i).getValue());
        }
        return newMap;
    }


    /**
     * 判断是否连击
     *
     * @param view .setTag(System.currentTimeMillis())
     * @return
     */
    public static boolean isFastDoubleClick(View view) {
        if (view == null || view.getTag() == null) {
            return false;
        }
        if (view.getTag() instanceof Long) {
            long time = (Long) view.getTag();
            if (time - lastClickTime < 4000) {
                return true;
            }
            lastClickTime = time;
        }
        return false;
    }

    public interface uploadImgCallback {
        void uploadFinish(final String path);
    }
}
