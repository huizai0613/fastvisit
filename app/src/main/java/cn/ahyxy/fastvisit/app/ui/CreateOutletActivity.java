package cn.ahyxy.fastvisit.app.ui;

import org.xutils.view.annotation.ContentView;

import cn.ahyxy.fastvisit.R;
import cn.ahyxy.fastvisit.baseui.BaseActivity;
import cn.ahyxy.fastvisit.baseui.titlebar.TitleBar;

@ContentView(R.layout.activity_create_outlet)
public class CreateOutletActivity extends BaseActivity {

    @Override
    public void initWidget() {
        super.initWidget();
        TitleBar instance = TitleBar.getInstance(mBaseActivity);
        instance.initDefaultBackTitle(mBaseActivity, getString(R.string.create_outlet));
    }
}
