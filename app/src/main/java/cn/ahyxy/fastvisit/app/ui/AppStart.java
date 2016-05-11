package cn.ahyxy.fastvisit.app.ui;

import android.os.Handler;

import org.xutils.view.annotation.ContentView;

import cn.ahyxy.fastvisit.KJConfig;
import cn.ahyxy.fastvisit.R;
import cn.ahyxy.fastvisit.baseui.BaseActivity;
import cn.ahyxy.fastvisit.utils.PreferenceHelper;

/**
 * Created by yexiangyu on 16/4/14.
 */
@ContentView(R.layout.app_start)
public class AppStart extends BaseActivity
{

    @Override
    public void initWidget()
    {
        super.initWidget();


        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                boolean isFirst = !PreferenceHelper.readBoolean(mContext, KJConfig.PREFERENCENAME, "isFirst");

                if (isFirst) {
                    PreferenceHelper.write(mContext, KJConfig.PREFERENCENAME, "isFirst", true);
                    skipActivity(mActivity, AppGuide.class);
                } else {
                    skipActivity(mActivity, LoginActivity.class);
                }
            }
        }, 2000);


    }
}
