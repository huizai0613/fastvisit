package cn.ahyxy.fastvisit.app.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.ahyxy.fastvisit.R;
import cn.ahyxy.fastvisit.baseui.SupportFragment;

public class AdvancedPOSFragment extends SupportFragment {

    public AdvancedPOSFragment() {
        // Required empty public constructor
    }

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return inflater.inflate(R.layout.fragment_advanced_po, container, false);
    }
}
