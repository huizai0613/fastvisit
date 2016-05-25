package cn.ahyxy.fastvisit;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.common.util.LogUtil;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;

import cn.ahyxy.fastvisit.app.AppContext;
import cn.ahyxy.fastvisit.app.DataManager.DataManager;
import cn.ahyxy.fastvisit.app.DataManager.UserManager;
import cn.ahyxy.fastvisit.app.ui.friend.model.Friend;
import cn.ahyxy.fastvisit.base.BaseCallBackJsonObject;
import cn.ahyxy.fastvisit.baseui.BaseActivity;
import cn.ahyxy.fastvisit.baseui.LsFragmentTabHost;
import cn.ahyxy.fastvisit.baseui.titlebar.TitleBar;
import cn.ahyxy.fastvisit.baseui.uiim.KJActivityStack;
import cn.ahyxy.fastvisit.weight.MainTab;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;


@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity implements TabHost.OnTabChangeListener, RongIM.UserInfoProvider
{

    @ViewInject(android.R.id.tabhost)
    private LsFragmentTabHost mTabHost;
    private int position;
    private TitleBar instance;

    @Override
    public void onTabChanged(String tabId)
    {
        instance.rest();
        if (MainTab.values()[0].getResName().equals(tabId)) {
            instance.setTitlebarMTv("消息", "#000000");
            instance.setTitlebarRightIv(R.mipmap.icon_r, null);
            instance.setTitlebarLeftTv("合肥", "#EA6800", R.mipmap.icon_l, null);
            instance.getTitlebarLeftTv().setPadding(20, 0, 20, 0);
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
    public UserInfo getUserInfo(String userId)
    {
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

        mLocationClient = new LocationClient(AppContext.getInstance());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        initLocation();
        mLocationClient.start();
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

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 60000 * 20; // 20min
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            if (location.getLocType() == BDLocation.TypeGpsLocation || location.getLocType() == BDLocation.TypeOffLineLocation || location.getLocType() == BDLocation.TypeNetWorkLocation) {// GPS定位结果
                HashMap<String, String> map = new HashMap<>();
                map.put("s_id", String.valueOf(UserManager.getUserBean().getId()));
                map.put("d_id", String.valueOf(UserManager.getUserBean().getD_id()));
                map.put("s_x", String.valueOf(location.getLongitude()));
                map.put("s_y", String.valueOf(location.getLatitude()));
                map.put("s_address", location.getAddrStr());
                DataManager.uploadLocation(map, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        LogUtil.d("onSuccess:" + result);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        LogUtil.d("onError:" + ex);
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                        LogUtil.d("onCanceled:" + cex);
                    }

                    @Override
                    public void onFinished() {
                        LogUtil.d("onFinished");
                    }
                });
            } else if (location.getLocType() == BDLocation.TypeServerError) {
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
            }
        }
    }
}