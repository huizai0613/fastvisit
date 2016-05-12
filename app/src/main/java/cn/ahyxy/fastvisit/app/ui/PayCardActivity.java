package cn.ahyxy.fastvisit.app.ui;

import android.graphics.Color;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import cn.ahyxy.fastvisit.R;
import cn.ahyxy.fastvisit.baseui.BaseActivity;
import cn.ahyxy.fastvisit.baseui.titlebar.TitleBar;
import cn.ahyxy.fastvisit.utils.DateUtils;
import cn.ahyxy.fastvisit.weight.simplifyspan.SimplifySpanBuild;
import cn.ahyxy.fastvisit.weight.simplifyspan.unit.SpecialTextUnit;

/**
 * Created by yexiangyu on 16/5/12.
 */
@ContentView(R.layout.activity_paycard)
public class PayCardActivity extends BaseActivity
{
    @ViewInject(R.id.baidu_map)
    private MapView mMap;
    @ViewInject(R.id.par_card_time)
    private TextView parCardTime;
    @ViewInject(R.id.par_card_address)
    private TextView parCardAddress;
    @ViewInject(R.id.btn_qd)
    private TextView btnQd;


    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    private BaiduMap map;
    private SimplifySpanBuild payStatus;

    @Override
    public void initWidget()
    {
        super.initWidget();
        TitleBar titleBar = TitleBar.getInstance(mBaseActivity);
        titleBar.initBackTitle(mBaseActivity,"考勤打卡","返回",R.mipmap.action_bar_back);
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        // 开启定位图层
        map = mMap.getMap();
        map.setMaxAndMinZoomLevel(19, 12);
        map.setBuildingsEnabled(true);
        map.setMyLocationEnabled(true);
        initLocation();
        mLocationClient.start();
        parCardTime.setText(DateUtils.StringData());
        btnQd.setText("签     到\n" + DateUtils.formatCurTime(DateUtils.FORMATHM));

    }

    public class MyLocationListener implements BDLocationListener
    {

        private boolean isSetZoom;

        @Override
        public void onReceiveLocation(BDLocation location)
        {
            //Receive Location
            if (location.getLocType() == BDLocation.TypeGpsLocation || location.getLocType() == BDLocation.TypeOffLineLocation || location.getLocType() == BDLocation.TypeNetWorkLocation) {// GPS定位结果
                payStatus = new SimplifySpanBuild(mContext, parCardAddress);
                payStatus.appendSpecialUnit(new SpecialTextUnit("您所在位置 : ", Color.parseColor("#000000"), 12));
                payStatus.appendSpecialUnit(new SpecialTextUnit(location.getAddrStr(), Color.parseColor("#20A4E8"), 12));
                parCardAddress.setText(payStatus.build());

                // 构造定位数据
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                                // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(100).latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();
                // 设置定位数据
                map.setMyLocationData(locData);
                // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
                MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, false, null);
                map.setMyLocationConfigeration(config);

                if (!isSetZoom) {
                    isSetZoom = true;
                    map.setMapStatus(MapStatusUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 18));
                } else {
                    map.setMapStatus(MapStatusUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
                }


            } else if (location.getLocType() == BDLocation.TypeServerError) {
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
            }
        }
    }

    private void initLocation()
    {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 60000;
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

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMap.onDestroy();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMap.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMap.onPause();
    }
}
