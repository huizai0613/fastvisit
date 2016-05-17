package cn.ahyxy.fastvisit.app.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.ahyxy.fastvisit.R;
import cn.ahyxy.fastvisit.baseui.SupportFragment;

public class OrderManageFragment extends SupportFragment {


    public OrderManageFragment() {
        // Required empty public constructor
    }


    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return inflater.inflate(R.layout.fragment_order_manage, container, false);
    }

}
