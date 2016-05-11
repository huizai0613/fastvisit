package cn.ahyxy.fastvisit.baseui.titlebar;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;

import cn.ahyxy.fastvisit.baseui.BaseActivity;


/**
 * Created by yexiangyu on 16/3/8.
 */
public abstract class TitleBarActivity extends BaseActivity
{
    protected TitleBar titleBar;

    protected final Handler mMainLoopHandler = new Handler(
            Looper.getMainLooper());


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initWidget()
    {
        titleBar = TitleBar.getInstance(this);
        super.initWidget();
    }

    @Override
    public void widgetClick(View v)
    {
        super.widgetClick(v);
        switch (v.getId()) {
            default:
                break;
        }
    }


    private static int count = 0;

    private final Runnable timerRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            count = 0;
        }
    };

}
