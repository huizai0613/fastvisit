package cn.ahyxy.fastvisit;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.DbManager;
import org.xutils.common.util.DensityUtil;
import org.xutils.common.util.LogUtil;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import cn.ahyxy.fastvisit.app.AppContext;
import cn.ahyxy.fastvisit.app.DataManager.UserManager;
import cn.ahyxy.fastvisit.app.ui.friend.model.Friend;
import cn.ahyxy.fastvisit.baseui.BaseActivity;
import cn.ahyxy.fastvisit.baseui.LsFragmentTabHost;
import cn.ahyxy.fastvisit.baseui.titlebar.TitleBar;
import cn.ahyxy.fastvisit.baseui.uiim.KJActivityStack;
import cn.ahyxy.fastvisit.weight.MainTab;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;


@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity implements TabHost.OnTabChangeListener, RongIM.UserInfoProvider {

    @ViewInject(android.R.id.tabhost)
    private LsFragmentTabHost mTabHost;
    private int position;
    private TitleBar instance;

    @Override
    public void onTabChanged(String tabId)
    {
        instance.rest();
        if (MainTab.values()[0].getResName().equals(tabId)) {
            instance.setTitlebarMTv("有信", "#000000");
        } else if (MainTab.values()[1].getResName().equals(tabId)) {
            instance.setTitlebarMTv(MainTab.values()[1].getResName(), "#000000");
            instance.setTitlebarRightIv(R.mipmap.icon_r, null);
            instance.setTitlebarLeftTv("合肥", "#EA6800", R.mipmap.icon_l, null);
            instance.getTitlebarLeftTv().setPadding(20, 0, 20, 0);
        } else if (MainTab.values()[2].getResName().equals(tabId)) {
            instance.setTitlebarMTv(getString(R.string.app_name), "#000000");
            instance.setTitlebarRightIv(R.mipmap.icon_r, null);
            instance.setTitlebarLeftTv("合肥", "#EA6800", R.mipmap.icon_l, null);
            instance.getTitlebarLeftTv().setPadding(20, 0, 20, 0);
        } else {
            instance.setTitlebarMTv("我的", "#000000");
        }
    }

    @Override
    public UserInfo getUserInfo(String userId) {
        try {
            DbManager dbManager = AppContext.getDbmanager();
            Friend friend = dbManager.findById(Friend.class, userId);
            if (friend == null) {
                return null;
            }
            UserInfo userInfo = new UserInfo(friend.getUserId(), friend.getNickname(), Uri.parse(friend.getPortrait()));
            return userInfo;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface OnTabReselectedListener
    {
        void onTabSelected();
    }

    @Override
    public void initData()
    {
        super.initData();
        position = getIntent().getIntExtra("POSITION", 2);

    }

    @Override
    public void initWidget()
    {
        super.initWidget();
        instance = TitleBar.getInstance(mBaseActivity);
//        instance.getmRoomView().setVisibility(View.GONE);

        connect(UserManager.getUserBean().getToken());
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

    /**
     * 建立与融云服务器的连接
     *
     * @param token
     */
    private void connect(String token)
    {
//    token = "paLzacUpQpDDYGmNmCjLiP/pFkUL4qvorKCHCwQDbPz+Uv87iOovqoFDS8X6AfbcsO2xZOebnzJTsb0FtMVg/g==";
        LogUtil.d("connect token:" + token);
        if (getApplicationInfo().packageName.equals(AppContext.getCurProcessName(getApplicationContext()))) {

            /**
             * IMKit SDK调用第二步,建立与服务器的连接
             */
            RongIM.connect(token, new RongIMClient.ConnectCallback()
            {

                /**
                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
                 */
                @Override
                public void onTokenIncorrect()
                {

                    LogUtil.d("--onTokenIncorrect");
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token
                 */
                @Override
                public void onSuccess(String userid)
                {
                    RongIM.setUserInfoProvider(MainActivity.this, true);
                    LogUtil.d("--onSuccess" + userid);
//                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                    finish();
                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode)
                {
                    LogUtil.d("--onError" + errorCode);
                }
            });
        }
    }
}