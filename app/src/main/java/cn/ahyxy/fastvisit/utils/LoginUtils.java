package cn.ahyxy.fastvisit.utils;

import android.app.Activity;
import android.content.Intent;


/**
 * Created by YeXiangYu on 15-10-22.
 */
public class LoginUtils
{

    private static JumpLoginResultListener jumpLoginResultListener;
    private static LoginUtils mLoginUtils;


    public static JumpLoginResultListener getJumpLoginResultListener()
    {
        return jumpLoginResultListener;
    }

    public static void setJumpLoginResultListener(JumpLoginResultListener jumpLoginResultListener)
    {
        LoginUtils.jumpLoginResultListener = jumpLoginResultListener;
    }


    /**
     * 登陆检测方法,没有登陆会自动跳转
     *
     * @param mContext
     * @param isCallBack              是否在成功后执行回调方法
     * @param jumpLoginResultListener 回调或者状态登陆的时候直接执行其中方法
     */
    public static void checkLoginCallBack(Activity mContext, boolean isCallBack, JumpLoginResultListener jumpLoginResultListener)
    {
        jumpLoginResultListener.setActivity(mContext);
        jumpLoginResultListener.setIsCallBack(isCallBack);
//        if (UserManage.isLogin()) {
//            jumpLoginResultListener.execute();
//        } else {
//            jumpToLogin(mContext, jumpLoginResultListener);
//        }
    }


    private static void jumpToLogin(Activity mContext, JumpLoginResultListener jumpLoginResultListener)
    {
        LoginUtils.jumpLoginResultListener = jumpLoginResultListener;
        Intent intent = new Intent();
//        intent.putExtra("MODE", LoginActivity.CALLBACKLOGINMODE);
//        intent.setClass(mContext, LoginActivity.class);
        mContext.startActivity(intent);
    }

}
