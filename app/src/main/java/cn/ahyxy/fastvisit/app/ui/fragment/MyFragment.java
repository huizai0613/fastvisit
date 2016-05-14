package cn.ahyxy.fastvisit.app.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xutils.ex.DbException;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;

import cn.ahyxy.fastvisit.KJConfig;
import cn.ahyxy.fastvisit.R;
import cn.ahyxy.fastvisit.app.AppContext;
import cn.ahyxy.fastvisit.app.bean.UserBean;
import cn.ahyxy.fastvisit.app.ui.LoginActivity;
import cn.ahyxy.fastvisit.baseui.LsSimpleHomeFragment;
import cn.ahyxy.fastvisit.baseui.uiim.KJActivityStack;
import cn.ahyxy.fastvisit.utils.PhoneUtils;
import cn.ahyxy.fastvisit.utils.PreferenceHelper;

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
    @ViewInject(R.id.btn_logout)
    private View btnLogout;


    @Event(value = {R.id.my_phone_box, R.id.my_pwd_box, R.id.my_recommend_box, R.id.btn_logout})
    private void clickBox(View view)
    {
        switch (view.getId()) {
            case R.id.my_phone_box://拨打客服
                PhoneUtils.makeCall("11111111111", mActivity);
                break;
            case R.id.my_pwd_box://修改密码
                break;
            case R.id.my_recommend_box://推荐应用
                shareMsg("有薪快访", "有薪快访", "有薪快访", null);
                break;
            case R.id.btn_logout://退出
                try {
                    AppContext.getDbmanager().delete(UserBean.class);
                } catch (DbException e) {
                    e.printStackTrace();
                }
                PreferenceHelper.write(mActivity, KJConfig.PREFERENCENAME, KJConfig.ISAUTOLOGIN, false);
                PreferenceHelper.readString(mActivity, KJConfig.PREFERENCENAME, KJConfig.USERTOKEN, "");

                KJActivityStack.create().finishAllActivity();
                mActivity.showActivity(mActivity, LoginActivity.class);
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
    }


    @Override
    protected void getDataFronServer()
    {

    }

    @Override
    public void onTabSelected()
    {

    }

    /**
     * 分享功能
     * <p/>
     * 上下文
     *
     * @param activityTitle Activity的名字
     * @param msgTitle      消息标题
     * @param msgText       消息内容
     * @param imgPath       图片路径，不分享图片则传null
     */
    public void shareMsg(String activityTitle, String msgTitle, String msgText,
                         String imgPath)
    {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (imgPath == null || imgPath.equals("")) {
            intent.setType("text/plain"); // 纯文本
        } else {
            File f = new File(imgPath);
            if (f != null && f.exists() && f.isFile()) {
                intent.setType("image/jpg");
                Uri u = Uri.fromFile(f);
                intent.putExtra(Intent.EXTRA_STREAM, u);
            }
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, activityTitle));
    }

}
