package cn.ahyxy.fastvisit.app.ui;

import android.view.View;
import android.widget.EditText;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cn.ahyxy.fastvisit.R;
import cn.ahyxy.fastvisit.baseui.BaseActivity;
import cn.ahyxy.fastvisit.baseui.titlebar.TitleBar;

/**
 * Created by yexiangyu on 16/5/11.
 */
@ContentView(R.layout.activity_push)
public class PushActivity extends BaseActivity
{

    @ViewInject(R.id.et_address)
    private EditText address;
    @ViewInject(R.id.et_name)
    private EditText name;
    @ViewInject(R.id.et_content)
    private EditText content;
    @ViewInject(R.id.et_comment)
    private EditText comment;

    @Override
    public void initWidget()
    {
        super.initWidget();
        TitleBar instance = TitleBar.getInstance(mBaseActivity);
        instance.initDefaultBackTitle(mBaseActivity, "品牌推广");
    }

    @Override
    public void initData()
    {
        super.initData();

    }

    @Event(value = R.id.submit_btn)
    public void submit(View view)
    {
//提交
        showWaitDialog("请稍后....");
    }
}
