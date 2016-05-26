package cn.ahyxy.fastvisit.app.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.xutils.view.annotation.ViewInject;

import java.lang.reflect.Type;
import java.util.List;

import cn.ahyxy.fastvisit.R;
import cn.ahyxy.fastvisit.app.DataManager.DataManager;
import cn.ahyxy.fastvisit.app.DataManager.UserManager;
import cn.ahyxy.fastvisit.app.bean.POSBean;
import cn.ahyxy.fastvisit.app.ui.OutletManageDetailActivity;
import cn.ahyxy.fastvisit.base.BaseCallBackJsonArray;
import cn.ahyxy.fastvisit.baseui.BaseFragment;
import cn.ahyxy.fastvisit.baseui.SupportFragment;

public class OutletManageFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    @ViewInject(R.id.lv_outlet_manage)
    private ListView listView;

    private OutletSearchFragment.ResultAdapter resultAdapter;
    public OutletManageFragment() {
        // Required empty public constructor
    }

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return inflater.inflate(R.layout.fragment_outlet_manage, container, false);
    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        resultAdapter = new OutletSearchFragment.ResultAdapter();
        listView.setAdapter(resultAdapter);
        listView.setOnItemClickListener(this);
        getOutletList();
    }

    private void getOutletList() {
        showWaitDialog(getString(R.string.error_view_loading));
        DataManager.getOutletList(String.valueOf(UserManager.getUserBean().getId()), new BaseCallBackJsonArray(getContext()) {
            @Override
            public void onErrorJson(Throwable ex, boolean isOnCallback) {
                hideWaitDialog();
            }

            @Override
            public void onSuccessJsonArray(JSONArray result) {
                hideWaitDialog();
                if (resultAdapter != null) {
                    resultAdapter.setData(DataManager.jsonArrayToPOSBeanList(result));
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        POSBean bean = resultAdapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putString("cate_id", String.valueOf(bean.getCate_one()));
        bundle.putString("t_id", String.valueOf(bean.getId()));
        bundle.putString("t_name", bean.getT_name());
        bundle.putString("t_address", bean.getT_address());
        bundle.putString("cate_name", bean.getCate_name());
        Intent intent = new Intent(getActivity(), OutletManageDetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
