package cn.ahyxy.fastvisit.app.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.ahyxy.fastvisit.R;
import cn.ahyxy.fastvisit.baseui.SupportFragment;

public class OutletCreateFragment extends SupportFragment {


    public OutletCreateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return inflater.inflate(R.layout.fragment_outlet_create, container, false);
    }

}
