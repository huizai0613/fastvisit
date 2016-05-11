package cn.ahyxy.fastvisit.utils;

import android.app.Activity;

import java.io.Serializable;

import cn.ahyxy.fastvisit.baseui.BaseActivity;


/**
 * Created by YeXiangYu on 15-10-22.
 */
public abstract class JumpLoginResultListener implements Serializable
{

    private Activity activity;
    private Boolean isCallBack;


    @Deprecated
    public JumpLoginResultListener(BaseActivity activity)
    {
        this.activity = activity;
    }

    public JumpLoginResultListener()
    {
    }

    public void setIsCallBack(Boolean isCallBack)
    {
        this.isCallBack = isCallBack;
    }

    public void setActivity(Activity activity)
    {
        this.activity = activity;
    }



    public void callBack()
    {
        if (isCallBack) {
            activity.runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    jumpLoginResult();
                }
            });
        }
    }


    public void execute()
    {
        activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                jumpLoginResult();
            }
        });
    }


    public abstract void jumpLoginResult();
}
