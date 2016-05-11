package cn.ahyxy.fastvisit;

import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.common.util.DensityUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import cn.ahyxy.fastvisit.baseui.BaseActivity;
import cn.ahyxy.fastvisit.baseui.LsFragmentTabHost;
import cn.ahyxy.fastvisit.baseui.uiim.KJActivityStack;
import cn.ahyxy.fastvisit.weight.MainTab;


@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity implements TabHost.OnTabChangeListener
{

    @ViewInject(android.R.id.tabhost)
    private LsFragmentTabHost mTabHost;
    private int position;

    @Override
    public void onTabChanged(String tabId)
    {

    }

    public interface OnTabReselectedListener
    {
        void onTabSelected();
    }

    @Override
    public void initData()
    {
        super.initData();
        position = getIntent().getIntExtra("POSITION", 0);

    }

    @Override
    public void initWidget()
    {
        super.initWidget();
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.setOnTabChangedListener(this);
        if (android.os.Build.VERSION.SDK_INT > 10) {
            mTabHost.getTabWidget().setShowDividers(0);
        }
        MainTab[] tabs = MainTab.values();
        final int size = tabs.length;
        for (int i = 0; i < size; i++) {
            MainTab mainTab = tabs[i];
            TabHost.TabSpec tab = mTabHost.newTabSpec(mainTab.getResName());

            View indicator = LayoutInflater.from(getApplicationContext())
                    .inflate(R.layout.tab_indicator, null);

            TextView title = (TextView) indicator.findViewById(R.id.tab_title);
            Drawable drawable = this.getResources().getDrawable(
                    mainTab.getResIcon());
            drawable.setBounds(0, 0, DensityUtil.dip2px(22), DensityUtil.dip2px(22));
            title.setCompoundDrawables(null, drawable, null,
                    null);
            title.setText(mainTab.getResName());
            title.setGravity(Gravity.CENTER);
            tab.setIndicator(indicator);
            tab.setContent(new TabHost.TabContentFactory()
            {

                @Override
                public View createTabContent(String tag)
                {
                    return new View(MainActivity.this);
                }
            });
            mTabHost.addTab(tab, mainTab.getClz(), null);
        }
        mTabHost.setCurrentTab(position);
    }

    int i;
    long front;
    long later;

    public void shutDown()
    {
        i++;
        if (i < 2) {
            Toast.makeText(this, "再点一次退出程序", Toast.LENGTH_SHORT).show();
            front = System.currentTimeMillis();
            return;
        }
        if (i >= 2) {
            later = System.currentTimeMillis();
            if (later - front > 2000) {
                Toast.makeText(this, "再点一次退出程序", Toast.LENGTH_SHORT).show();
                front = System.currentTimeMillis();
                i = 1;
            } else {

                // File videoCachePath =
                // CommonUtils.getVideoCachePath(mInstance);
                // File videoCachePath
                finish();
                KJActivityStack.create().finishAllActivity();
                i = 0;
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        shutDown();
    }
}