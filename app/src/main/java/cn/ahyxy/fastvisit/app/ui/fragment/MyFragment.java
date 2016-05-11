package cn.ahyxy.fastvisit.app.ui.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cn.ahyxy.fastvisit.R;
import cn.ahyxy.fastvisit.baseui.LsSimpleHomeFragment;
import cn.ahyxy.fastvisit.baseui.titlebar.TitleBar;

/**
 * Created by yexiangyu on 16/5/9.
 */
public class MyFragment extends LsSimpleHomeFragment
{

    @ViewInject(R.id.my_heard)
    private ImageView myHeard;
    @ViewInject(R.id.my_phone)
    private TextView myPhone;
    @ViewInject(R.id.my_email)
    private ImageView myEmail;
    @ViewInject(R.id.my_complay)
    private ImageView myComplay;
    @ViewInject(R.id.star_box)
    private LinearLayout starBox;


    @Event(value = {R.id.my_phone_box, R.id.my_pwd_box, R.id.my_recommend_box})
    private void clickBox(View view)
    {
        switch (view.getId()) {
            case R.id.my_phone_box://拨打客服
                break;
            case R.id.my_pwd_box://修改密码
                break;
            case R.id.my_recommend_box://推荐应用
                break;
        }

    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_my;
    }

    @Override
    protected void initWidget(View parentView)
    {
        super.initWidget(parentView);
        TitleBar titleBar = TitleBar.getInstance(parentView);
        titleBar.setTitlebarMTv("我的", "#000000");
    }


    @Override
    protected void getDataFronServer()
    {

    }

    @Override
    public void onTabSelected()
    {

    }
}
