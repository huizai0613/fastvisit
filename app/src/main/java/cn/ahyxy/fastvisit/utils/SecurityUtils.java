package cn.ahyxy.fastvisit.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密解密类
 * <p/>
 * Created by guxy on 15/8/10.
 */

public class SecurityUtils
{
    public final static String HEX_CHARS = "0123456789abcdef";

    /**
     * 字符串MD5加密
     *
     * @param text 传入字符串
     * @return 加密结果
     */
    public static String md5Encode(String text)
    {
        try {
            return md5Encode(text.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * MD5加密
     *
     * @param bytes 传入数组
     * @return 加密结果
     */
    public static String md5Encode(byte[] bytes)
    {
        byte[] hash = new byte[0];

        try {
            hash = MessageDigest.getInstance("MD5").digest(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return asHexString(hash);
    }

    /**
     * Byte 数组转hex字符串
     *
     * @param bytes 传入数组
     * @return 转换结果
     */
    public static String asHexString(byte[] bytes)
    {
        StringBuilder sb = new StringBuilder();

        for (byte b : bytes) {
            b &= 0xFF;
            sb.append(HEX_CHARS.charAt((b >> 4) & 0xF));
            sb.append(HEX_CHARS.charAt(b & 0xF));
        }

        return sb.toString();
    }

}
