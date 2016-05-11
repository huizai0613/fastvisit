/*
 * Copyright (c) 2014, 张涛.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.ahyxy.fastvisit.baseui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.xutils.x;

import java.lang.ref.SoftReference;

import cn.ahyxy.fastvisit.R;
import cn.ahyxy.fastvisit.baseui.uiim.I_BroadcastReg;
import cn.ahyxy.fastvisit.baseui.uiim.I_KJActivity;
import cn.ahyxy.fastvisit.baseui.uiim.I_SkipActivity;
import cn.ahyxy.fastvisit.baseui.uiim.KJActivityStack;
import cn.ahyxy.fastvisit.utils.KJLoger;


/**
 * @author kymjs (http://www.kymjs.com/) on 11/19/15.
 */
public abstract class SupportActivity extends AppCompatActivity implements
        View.OnClickListener, I_BroadcastReg, I_KJActivity, I_SkipActivity
{

    public static final int WHICH_MSG = 0X37210;

    protected Activity mActivity;
    private ThreadDataCallBack callback;
    private KJActivityHandle threadHandle = new KJActivityHandle(this);

    /**
     * Activity状态
     */
    public int activityState = DESTROY;

    /**
     * 一个私有回调类，线程中初始化数据完成后的回调
     */
    private interface ThreadDataCallBack
    {
        void onSuccess();
    }


    private static class KJActivityHandle extends Handler
    {
        private final SoftReference<SupportActivity> mOuterInstance;

        KJActivityHandle(SupportActivity outer)
        {
            mOuterInstance = new SoftReference<>(outer);
        }

        // 当线程中初始化的数据初始化完成后，调用回调方法
        @Override
        public void handleMessage(android.os.Message msg)
        {
            SupportActivity aty = mOuterInstance.get();
            if (msg.what == WHICH_MSG && aty != null) {
                aty.callback.onSuccess();
            }
        }
    }

    /**
     * 如果调用了initDataFromThread()，则当数据初始化完成后将回调该方法。
     */
    protected void threadDataInited()
    {
    }

    /**
     * 在线程中初始化数据，注意不能在这里执行UI操作
     */
    @Override
    public void initDataFromThread()
    {
        callback = new ThreadDataCallBack()
        {
            @Override
            public void onSuccess()
            {
                threadDataInited();
            }
        };
    }

    @Override
    public void initData()
    {
    }

    @Override
    public void initWidget()
    {
    }

    // 仅仅是为了代码整洁点
    private void initializer()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                initDataFromThread();
                threadHandle.sendEmptyMessage(WHICH_MSG);
            }
        }).start();
        initData();
        initWidget();
    }

    /**
     * listened widget's click method
     */
    @Override
    public void widgetClick(View v)
    {
    }

    @Override
    public void onClick(View v)
    {
        widgetClick(v);
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T bindView(int id)
    {
        return (T) findViewById(id);
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T bindView(int id, boolean click)
    {
        T view = (T) findViewById(id);
        if (click) {
            view.setOnClickListener(this);
        }
        return view;
    }

    @Override
    public void registerBroadcast()
    {
    }

    @Override
    public void unRegisterBroadcast()
    {
    }


    /***************************************************************************
     * print Activity callback methods
     ***************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mActivity = this;
        KJActivityStack.create().addActivity(this);
        KJLoger.state(this.getClass().getName(), "---------onCreat ");
        x.view().inject(this);
        initializer();
        registerBroadcast();

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        KJLoger.state(this.getClass().getName(), "---------onStart ");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        activityState = RESUME;
        KJLoger.state(this.getClass().getName(), "---------onResume ");
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        activityState = PAUSE;
        KJLoger.state(this.getClass().getName(), "---------onPause ");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        activityState = STOP;
        KJLoger.state(this.getClass().getName(), "---------onStop ");
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        KJLoger.state(this.getClass().getName(), "---------onRestart ");
    }

    @Override
    protected void onDestroy()
    {
        unRegisterBroadcast();
        activityState = DESTROY;
        KJLoger.state(this.getClass().getName(), "---------onDestroy ");
        super.onDestroy();
        KJActivityStack.create().finishActivity(this);
        callback = null;
        threadHandle = null;
        mActivity = null;
    }

    /**
     * skip to @param(cls)，and call @param(aty's) finish() method
     */
    @Override
    public void skipActivity(Activity aty, Class<?> cls)
    {
        showActivity(aty, cls);
        aty.overridePendingTransition(R.anim.activity_in,
                R.anim.activity_out);
        aty.finish();
    }

    /**
     * skip to @param(cls)，and call @param(aty's) finish() method
     */
    @Override
    public void skipActivity(Activity aty, Intent it)
    {
        showActivity(aty, it);
        aty.overridePendingTransition(R.anim.activity_in,
                R.anim.activity_out);
        aty.finish();
    }

    /**
     * skip to @param(cls)，and call @param(aty's) finish() method
     */
    @Override
    public void skipActivity(Activity aty, Class<?> cls, Bundle extras)
    {
        showActivity(aty, cls, extras);
        aty.overridePendingTransition(R.anim.activity_in,
                R.anim.activity_out);
        aty.finish();
    }

    /**
     * show to @param(cls)，but can't finish activity
     */
    @Override
    public void showActivity(Activity aty, Class<?> cls)
    {
        Intent intent = new Intent();
        intent.setClass(aty, cls);
        aty.startActivity(intent);
        aty.overridePendingTransition(R.anim.activity_in,
                R.anim.activity_out);
    }

    /**
     * show to @param(cls)，but can't finish activity
     */
    @Override
    public void showActivity(Activity aty, Intent it)
    {
        aty.startActivity(it);
    }

    /**
     * show to @param(cls)，but can't finish activity
     */
    @Override
    public void showActivity(Activity aty, Class<?> cls, Bundle extras)
    {
        Intent intent = new Intent();
        intent.putExtras(extras);
        intent.setClass(aty, cls);
        aty.startActivity(intent);
        aty.overridePendingTransition(R.anim.activity_in,
                R.anim.activity_out);
    }


}
