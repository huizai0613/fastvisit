package cn.ahyxy.fastvisit.app.ui;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.xutils.view.annotation.ContentView;

import cn.ahyxy.fastvisit.R;
import cn.ahyxy.fastvisit.app.ui.fragment.OutletManageDetailFragment;
import cn.ahyxy.fastvisit.app.ui.fragment.OutletManageFragment;
import cn.ahyxy.fastvisit.baseui.BaseActivity;
import cn.ahyxy.fastvisit.baseui.titlebar.TitleBar;

@ContentView(R.layout.activity_with_fragment)
public class OutletManageDetailActivity extends BaseActivity {

    @Override
    public void initWidget() {
        super.initWidget();
        TitleBar instance = TitleBar.getInstance(mBaseActivity);
        instance.initDefaultBackTitle(mBaseActivity, getString(R.string.manage_detail));

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, OutletManageDetailFragment.newInstance(getIntent().getExtras()));
        transaction.commit();
    }
}
