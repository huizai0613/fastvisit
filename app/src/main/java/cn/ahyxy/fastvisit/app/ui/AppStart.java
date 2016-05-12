package cn.ahyxy.fastvisit.app.ui;

import android.os.Handler;

import org.xutils.view.annotation.ContentView;

import cn.ahyxy.fastvisit.KJConfig;
import cn.ahyxy.fastvisit.MainActivity;
import cn.ahyxy.fastvisit.R;
import cn.ahyxy.fastvisit.baseui.BaseActivity;
import cn.ahyxy.fastvisit.utils.PreferenceHelper;
import cn.ahyxy.fastvisit.utils.StringUtils;

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
                boolean isFirst = !PreferenceHelper.readBoolean(mContext, KJConfig.PREFERENCENAME, KJConfig.ISFRIST);
                if (isFirst) {
                    PreferenceHelper.write(mContext, KJConfig.PREFERENCENAME, KJConfig.ISFRIST, true);
                    skipActivity(mBaseActivity, AppGuide.class);
                } else {
                    if (PreferenceHelper.readBoolean(mContext, KJConfig.PREFERENCENAME, KJConfig.ISAUTOLOGIN) && !StringUtils.isEmpty(PreferenceHelper.readString(mContext, KJConfig.PREFERENCENAME, KJConfig.USERTOKEN))) {
                        skipActivity(mBaseActivity, MainActivity.class);
                    } else {
                        skipActivity(mBaseActivity, LoginActivity.class);
                    }
                }
            }
        }, 2000);


    }
}
