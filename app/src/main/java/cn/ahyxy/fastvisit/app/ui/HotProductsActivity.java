package cn.ahyxy.fastvisit.app.ui;

import android.support.v4.app.FragmentTransaction;

import org.xutils.view.annotation.ContentView;

import cn.ahyxy.fastvisit.R;
import cn.ahyxy.fastvisit.app.ui.fragment.HotProductsFragment;
import cn.ahyxy.fastvisit.app.ui.fragment.NoteFragment;
import cn.ahyxy.fastvisit.baseui.BaseActivity;
import cn.ahyxy.fastvisit.baseui.titlebar.TitleBar;

@ContentView(R.layout.activity_with_fragment)
public class HotProductsActivity extends BaseActivity {

    @Override
    public void initWidget() {
        super.initWidget();
        TitleBar instance = TitleBar.getInstance(mBaseActivity);
        instance.initDefaultBackTitle(mBaseActivity, getString(R.string.hot_products));

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, new HotProductsFragment());
        transaction.commit();
    }
}
