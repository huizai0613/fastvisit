package cn.ahyxy.fastvisit.app.ui;

import android.app.Dialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.EditText;

import org.json.JSONObject;
import org.xutils.common.util.DensityUtil;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cn.ahyxy.fastvisit.KJConfig;
import cn.ahyxy.fastvisit.MainActivity;
import cn.ahyxy.fastvisit.R;
import cn.ahyxy.fastvisit.app.AppContext;
import cn.ahyxy.fastvisit.app.DataManager.UserManager;
import cn.ahyxy.fastvisit.app.bean.UserBean;
import cn.ahyxy.fastvisit.base.BaseCallBackJsonObject;
import cn.ahyxy.fastvisit.baseui.BaseActivity;
import cn.ahyxy.fastvisit.utils.PreferenceHelper;
import cn.ahyxy.fastvisit.utils.StringUtils;
import cn.ahyxy.fastvisit.utils.ToastUtils;
import cn.ahyxy.fastvisit.weight.SizeChangeLinearLayout;
import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by yexiangyu on 16/4/19.
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity
{
    @ViewInject(R.id.edt_pwd)
    private EditText edtPws;
    @ViewInject(R.id.sizechangeL)
    private SizeChangeLinearLayout sizechangeL;
    @ViewInject(R.id.edt_username)
    private EditText edtUserName;
    @ViewInject(R.id.login_auto)
    private CheckBox loginAuto;
    @ViewInject(R.id.login_but)
    private FancyButton login_but;
    private Dialog dialog;


    @Override
    public void initWidget()
    {
        super.initWidget();
        controlKeyboardLayout(sizechangeL, login_but);
    }

    @Event(value = {R.id.login_but, R.id.login_register, R.id.login_find})
    private void event(View view)
    {
        switch (view.getId()) {
            case R.id.login_but://登陆
                login();
                break;
            case R.id.login_register://注册
                dialog = new Dialog(this, R.style.Dialog);
                dialog.setContentView(R.layout.registerselect_dialog);
                dialog.getWindow().getDecorView().findViewById(R.id.one_register).setOnClickListener(this);
                dialog.getWindow().getDecorView().findViewById(R.id.two_register).setOnClickListener(this);
                dialog.show();
                break;
            case R.id.login_find://找回密码
                findPassWorld();
                break;
        }
    }

    @Override
    public void widgetClick(View v)
    {
        super.widgetClick(v);

        switch (v.getId()) {
            case R.id.one_register:
                dialog.dismiss();
                register(RegisterActivity.NOMAL);
                break;
            case R.id.two_register:
                dialog.dismiss();
                register(RegisterActivity.MANAGER);
                break;
        }


    }

    //找回密码
    private void findPassWorld()
    {

    }

    //注册
    private void register(int mode)
    {
        Bundle bundle = new Bundle();
        bundle.putInt("MODE", mode);
        showActivity(mBaseActivity, RegisterActivity.class, bundle);
    }

    //登陆方法
    private void login()
    {
        String account = edtUserName.getText().toString();
        String pwd = edtPws.getText().toString();

        if (StringUtils.isEmpty(account)) {
            ToastUtils.Infotoast(mContext, "账号不可为空");
            return;
        }
        if (StringUtils.isEmpty(pwd)) {
            ToastUtils.Infotoast(mContext, "密码不可为空");
            return;
        }

        showWaitDialog("登陆中...");
        UserManager.login(account, pwd, new BaseCallBackJsonObject(mContext, mBaseActivity)
        {
            @Override
            public void onErrorJson(Throwable ex, boolean isOnCallback)
            {
                PreferenceHelper.write(mContext, KJConfig.PREFERENCENAME, KJConfig.USERTOKEN, "");
            }

            @Override
            public void onSuccessJsonObject(JSONObject result)
            {
                UserBean userBean = new UserBean();
                userBean.parserBean(result);
                UserManager.setUserBean(userBean);
                try {
                    AppContext.getDbmanager().save(userBean);
                    PreferenceHelper.write(mContext, KJConfig.PREFERENCENAME, KJConfig.ISAUTOLOGIN, loginAuto.isChecked());
                    PreferenceHelper.write(mContext, KJConfig.PREFERENCENAME, KJConfig.USERTOKEN, userBean.getToken());
                    skipActivity(mBaseActivity, MainActivity.class);
                } catch (DbException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    /**
     * @param root         最外层布局，需要调整的布局
     * @param scrollToView 被键盘遮挡的scrollToView，滚动root,使scrollToView在root可视区域的底部
     */
    private void controlKeyboardLayout(final View root, final View scrollToView)
    {
        root.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener()
                {
                    @Override
                    public void onGlobalLayout()
                    {
                        Rect rect = new Rect();
                        // 获取root在窗体的可视区域
                        root.getWindowVisibleDisplayFrame(rect);
                        // 获取root在窗体的不可视区域高度(被其他View遮挡的区域高度)
                        int rootInvisibleHeight = root.getRootView()
                                .getHeight() - rect.bottom;
                        // 若不可视区域高度大于100，则键盘显示
                        if (rootInvisibleHeight > 100) {
                            int[] location = new int[2];
                            // 获取scrollToView在窗体的坐标
                            scrollToView.getLocationInWindow(location);
                            // 计算root滚动高度，使scrollToView在可见区域
                            int srollHeight = (location[1]
                                    + scrollToView.getHeight() + DensityUtil.dip2px(10f)
                            ) - rect.bottom;
                            root.scrollTo(0, srollHeight);
                        } else {
                            // 键盘隐藏
                            root.scrollTo(0, 0);
                        }
                    }
                });
    }


}
