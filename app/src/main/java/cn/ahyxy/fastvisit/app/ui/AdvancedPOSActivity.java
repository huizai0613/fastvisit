package cn.ahyxy.fastvisit.app.ui;

import android.support.v4.app.FragmentTransaction;

import org.xutils.view.annotation.ContentView;

import cn.ahyxy.fastvisit.R;
import cn.ahyxy.fastvisit.app.ui.fragment.AdvancedPOSFragment;
import cn.ahyxy.fastvisit.app.ui.fragment.OutletManageFragment;
import cn.ahyxy.fastvisit.baseui.BaseActivity;
import cn.ahyxy.fastvisit.baseui.titlebar.TitleBar;

@ContentView(R.layout.activity_with_fragment)
public class AdvancedPOSActivity extends BaseActivity {

    @Override
    public void initWidget() {
        super.initWidget();
        TitleBar instance = TitleBar.getInstance(mBaseActivity);
        instance.initDefaultBackTitle(mBaseActivity, getString(R.string.advanced_pos));

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, new AdvancedPOSFragment());
        transaction.commit();
    }
}
