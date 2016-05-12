package cn.ahyxy.fastvisit.app.ui;

import android.widget.TextView;

import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import cn.ahyxy.fastvisit.R;
import cn.ahyxy.fastvisit.app.DataManager.SimpleManager;
import cn.ahyxy.fastvisit.app.DataManager.UserManager;
import cn.ahyxy.fastvisit.base.BaseCallBackJsonObject;
import cn.ahyxy.fastvisit.baseui.BaseActivity;
import cn.ahyxy.fastvisit.baseui.titlebar.TitleBar;

/**
 * Created by yexiangyu on 16/5/11.
 */
@ContentView(R.layout.activity_public)
public class PublicActivity extends BaseActivity
{

    @ViewInject(R.id.public_content)
    private TextView publicContent;

    @Override
    public void initWidget()
    {
        super.initWidget();
        TitleBar instance = TitleBar.getInstance(mBaseActivity);
        instance.initDefaultBackTitle(mBaseActivity, "通知广播");
    }

    @Override
    public void initData()
    {
        super.initData();
        showWaitDialog("请稍后....");
        SimpleManager.getPublic(UserManager.getUserBean().getD_id() + "", new BaseCallBackJsonObject(mContext, mBaseActivity)
        {
            @Override
            public void onErrorJson(Throwable ex, boolean isOnCallback)
            {

            }

            @Override
            public void onSuccessJsonObject(JSONObject result)
            {
                result.toString();

            }
        });

    }
}
