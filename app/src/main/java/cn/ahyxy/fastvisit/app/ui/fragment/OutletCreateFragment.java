package cn.ahyxy.fastvisit.app.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cn.ahyxy.fastvisit.R;
import cn.ahyxy.fastvisit.app.AppContext;
import cn.ahyxy.fastvisit.app.DataManager.DataManager;
import cn.ahyxy.fastvisit.app.DataManager.UserManager;
import cn.ahyxy.fastvisit.app.bean.OutletCategoryBean;
import cn.ahyxy.fastvisit.app.ui.ProductListActivity;
import cn.ahyxy.fastvisit.base.BaseCallBackJsonArray;
import cn.ahyxy.fastvisit.base.BaseCallBackJsonObject;
import cn.ahyxy.fastvisit.baseui.BaseFragment;
import cn.ahyxy.fastvisit.utils.ToastUtils;

public class OutletCreateFragment extends BaseFragment implements AdapterView.OnItemSelectedListener {
    @ViewInject(R.id.edt_outlet_name)
    private EditText nameEditText;
    @ViewInject(R.id.spinner_outlet_categories)
    private Spinner mainCategoriesSpinner;
    @ViewInject(R.id.spinner_outlet_sub_categories)
    private Spinner subCategoriesSpinner;
    @ViewInject(R.id.edt_outlet_address)
    private EditText addressEditText;
    @ViewInject(R.id.edt_outlet_contact)
    private EditText contactEditText;
    @ViewInject(R.id.edt_outlet_phone_number)
    private EditText phoneNumberEditText;
    @ViewInject(R.id.edt_outlet_remark)
    private EditText remarkEditText;

    private List<OutletCategoryBean> outletCategoryBeanList = new ArrayList<>();
    private MyArrayAdapter mainCategoryArrayAdapter;
    private MyArrayAdapter subCategoryArrayAdapter;

    public OutletCreateFragment() {
        // Required empty public constructor
    }

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return inflater.inflate(R.layout.fragment_outlet_create, container, false);
    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        TelephonyManager tMgr = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tMgr.getLine1Number();
        LogUtil.d("phoneNumber:" + mPhoneNumber);
        phoneNumberEditText.setText(mPhoneNumber);

        mLocationClient = new LocationClient(AppContext.getInstance());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        initLocation();
        mLocationClient.start();

        mainCategoryArrayAdapter = new MyArrayAdapter(getContext(), android.R.layout.simple_spinner_item);
        mainCategoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mainCategoriesSpinner.setAdapter(mainCategoryArrayAdapter);
        mainCategoriesSpinner.setOnItemSelectedListener(this);
        subCategoryArrayAdapter = new MyArrayAdapter(getContext(), android.R.layout.simple_spinner_item);
        subCategoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subCategoriesSpinner.setAdapter(subCategoryArrayAdapter);
        getCategoriesFromServer();
    }

    private void getCategoriesFromServer() {
        DataManager.getOrderList(String.valueOf(UserManager.getUserBean().getId()),
                new BaseCallBackJsonArray(getContext()) {
                    @Override
                    public void onErrorJson(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onSuccessJsonArray(JSONArray result) {

                    }
                });
        showWaitDialog(getString(R.string.error_view_loading));
        DataManager.getOutletCategories(String.valueOf(UserManager.getUserBean().getD_id()),
                new BaseCallBackJsonArray(getContext()) {

                    @Override
                    public void onSuccessJsonArray(JSONArray result) {
                        hideWaitDialog();
                        outletCategoryBeanList = DataManager.jsonArrayToPOSCategoryList(result);
                        mainCategoryArrayAdapter.setData(outletCategoryBeanList, OutletCategoryBean.MAIN_CATEGORY_P_ID);
                        int position = mainCategoriesSpinner.getSelectedItemPosition();
                        updateSubCategoriesSpinner(position);
                    }

                    @Override
                    public void onErrorJson(Throwable ex, boolean isOnCallback) {
                        hideWaitDialog();
                    }
                });
    }

    @Event(value = {R.id.btn_outlet_save, R.id.btn_outlet_next})
    private void onViewClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_outlet_save:
            case R.id.btn_outlet_next:
                createOutlet(id);
                break;
        }
    }

    private void createOutlet(final int id) {
        final Bundle bundle = new Bundle();
        String errorStr = getString(R.string.error_field_required);
        String name = nameEditText.getText().toString();

        nameEditText.setError(null);
        addressEditText.setError(null);
        contactEditText.setError(null);
        phoneNumberEditText.setError(null);

        if (TextUtils.isEmpty(name)) {
            nameEditText.setError(errorStr);
            return;
        }
        bundle.putString("t_name", name);

        String cateOne = "";
        String cateName = "";
        int mainPosition = mainCategoriesSpinner.getSelectedItemPosition();
        LogUtil.d("position:" + mainPosition);
        OutletCategoryBean mainBean = mainCategoryArrayAdapter.getMyItem(mainPosition);
        if (mainBean != null) {
            cateOne = String.valueOf(mainBean.getId());
            cateName = mainBean.getCate_name();
        }
        String cateTwo = "";
        int subPosition = subCategoriesSpinner.getSelectedItemPosition();
        LogUtil.d("subPosition:" + subPosition);
        OutletCategoryBean subBean = subCategoryArrayAdapter.getMyItem(subPosition);
        if (subBean != null) {
            cateTwo = String.valueOf(subBean.getId());
        }
        LogUtil.d("cateOne:" + cateOne + ", cateTwo:" + cateTwo);
        bundle.putString("cate_one", cateOne);
        bundle.putString("cate_name", cateName);

        String address = addressEditText.getText().toString();
        if (TextUtils.isEmpty(address)) {
            addressEditText.setError(errorStr);
            ToastUtils.Errortoast(getContext(), getString(R.string.location_failed));
            return;
        }
        bundle.putString("t_address", address);

        String contact = contactEditText.getText().toString();
        if (TextUtils.isEmpty(contact)) {
            contactEditText.setError(errorStr);
            return;
        }
        bundle.putString("contact_name", contact);

        String phoneNumber = phoneNumberEditText.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            phoneNumberEditText.setError(errorStr);
            return;
        }
        bundle.putString("contact_tel", phoneNumber);

        String remark = remarkEditText.getText().toString();
        bundle.putString("t_remark", remark);

        showWaitDialog(getString(R.string.error_view_loading));
        DataManager.createOutlet(String.valueOf(UserManager.getUserBean().getD_id()), cateOne, cateTwo,
                String.valueOf(bdLocation.getLongitude()), String.valueOf(bdLocation.getLatitude()),
                String.valueOf(UserManager.getUserBean().getId()), name, address, contact, phoneNumber, remark,
                new BaseCallBackJsonObject(getContext()) {

                    @Override
                    public void onSuccessJsonObject(JSONObject result) {
                        hideWaitDialog();
                        if (getContext() != null) {
                            ToastUtils.Infotoast(getContext(), getString(R.string.create_success));
                            if (id == R.id.btn_outlet_save) {
                                getActivity().finish();
                            } else if (id == R.id.btn_outlet_next) {
                                Intent intent = new Intent(getContext(), ProductListActivity.class);
                                try {
                                    bundle.putInt("id", result.getInt("id"));
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    getActivity().finish();
                                } catch (JSONException e) {
                                    ToastUtils.Errortoast(getContext(), getString(R.string.error_json));
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    @Override
                    public void onErrorJson(Throwable ex, boolean isOnCallback) {
                        hideWaitDialog();
                    }
                });
    }

    private void updateSubCategoriesSpinner(int mainPosition) {
        LogUtil.d("position:" + mainPosition);
        OutletCategoryBean bean = mainCategoryArrayAdapter.getMyItem(mainPosition);
        subCategoryArrayAdapter.setData(outletCategoryBeanList, bean.getId());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent == mainCategoriesSpinner) {
            updateSubCategoriesSpinner(position);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private static class MyArrayAdapter extends ArrayAdapter<String> {
        private List<OutletCategoryBean> list = new ArrayList<>();

        public MyArrayAdapter(Context context, int resource) {
            super(context, resource);
        }

        private void setData(List<OutletCategoryBean> list, int p_id) {
            clear();
            if (list == null || list.isEmpty()) {
                return;
            }
            List<String> strings = new ArrayList<>();
            for (OutletCategoryBean bean : list) {
                if (bean.getP_id() == p_id) {
                    this.list.add(bean);
                    strings.add(bean.getCate_name());
                }
            }
            addAll(strings);
        }

        public OutletCategoryBean getMyItem(int position) {
            if (0 <= position && position < getCount()) {
                return list.get(position);
            }
            return null;
        }
    }

    private void initLocation() {
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

    public BDLocation bdLocation;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            if (location.getLocType() == BDLocation.TypeGpsLocation || location.getLocType() == BDLocation.TypeOffLineLocation || location.getLocType() == BDLocation.TypeNetWorkLocation) {// GPS定位结果
                addressEditText.setText(location.getAddrStr());
                addressEditText.setEnabled(false);
                bdLocation = location;
            } else if (location.getLocType() == BDLocation.TypeServerError) {
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
            }
        }
    }
}
