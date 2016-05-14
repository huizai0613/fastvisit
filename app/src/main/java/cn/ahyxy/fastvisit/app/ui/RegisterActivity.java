package cn.ahyxy.fastvisit.app.ui;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cn.ahyxy.fastvisit.R;
import cn.ahyxy.fastvisit.app.DataManager.UserManager;
import cn.ahyxy.fastvisit.base.BaseCallBackJsonObject;
import cn.ahyxy.fastvisit.baseui.BaseActivity;
import cn.ahyxy.fastvisit.utils.StringUtils;
import cn.ahyxy.fastvisit.utils.ToastUtils;
import cn.ahyxy.fastvisit.weight.SizeChangeLinearLayout;
import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by yexiangyu on 16/4/20.
 */
@ContentView(R.layout.activity_register)
public class RegisterActivity extends BaseActivity
{

    private static final int GET_ID_CODE_RETAIN = 60 * 1000;
    private static final int GET_ID_CODE_SUCCESS = 0x1001;
    private static final int GET_ID_CODE_FAIL = 0X1002;

    public static final int MANAGER = 0;
    public static final int NOMAL = 1;

    @ViewInject(R.id.sizechangeL)
    private SizeChangeLinearLayout sizechangeL;
    @ViewInject(R.id.edt_shopid)
    private EditText edtShopID;
    @ViewInject(R.id.edt_phone)
    private EditText edtPhone;
    @ViewInject(R.id.edt_code)
    private EditText edtCode;
    @ViewInject(R.id.register_note_check)
    private CheckBox registerNoteCheck;
    @ViewInject(R.id.register_btn)
    private FancyButton registerBtn;
    @ViewInject(R.id.register_code)
    private FancyButton registerCode;
    private int mode;
    private CountDownTimer mCountDownTimer;


    private Handler myHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch (msg.what) {
                case GET_ID_CODE_SUCCESS:



                    break;
                case GET_ID_CODE_FAIL:
                    mCountDownTimer.cancel();
                    mCountDownTimer.onFinish();
                    ToastUtils.Infotoast(mContext, "获取验证码失败！请稍候重试");
                    break;
            }
            super.handleMessage(msg);
        }
    };


    @Override
    public void initWidget()
    {
        super.initWidget();
        registerCode.setTextSize(12);
        controlKeyboardLayout(sizechangeL, registerBtn);
    }

    @Override
    public void onDestroy()
    {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
        super.onDestroy();
    }

    @Override
    public void initData()
    {
        super.initData();
        mCountDownTimer = new GetIdCodeCountDownTimer(GET_ID_CODE_RETAIN, 1 * 1000);
        mode = getIntent().getIntExtra("MODE", NOMAL);

    }

    @Event(value = {R.id.register_btn, R.id.register_note, R.id.register_code, R.id.register_close, R.id.register_note_check})
    private void eventClick(View view)
    {
        switch (view.getId()) {
            case R.id.register_btn://注册
                break;
            case R.id.register_note://查看协议
                break;
            case R.id.register_code://获取验证码

                String phone = edtPhone.getText().toString();

                if (StringUtils.isPhone(phone)) {
                    mCountDownTimer.start();
                    UserManager.getRegisterCode(phone, new BaseCallBackJsonObject(mContext, mBaseActivity)
                    {
                        @Override
                        public void onErrorJson(Throwable ex, boolean isOnCallback)
                        {
                            Message msg = myHandler.obtainMessage(GET_ID_CODE_FAIL, "");
                            myHandler.sendMessage(msg);
                        }

                        @Override
                        public void onSuccessJsonObject(JSONObject result)
                        {
                            Message msg = myHandler.obtainMessage(GET_ID_CODE_SUCCESS, result);
                            myHandler.sendMessage(msg);
                        }
                    });
                } else {
                    ToastUtils.Infotoast(mContext, "请填写正确的手机号码");
                }

                break;
            case R.id.register_close://关闭
                animaFinish();
                break;
            case R.id.register_note_check:
                if (registerNoteCheck.isChecked()) {
                    registerBtn.setBackgroundColor(Color.parseColor("#D35F00"));
                    registerBtn.setBorderColor(Color.parseColor("#D35F00"));
                    registerBtn.setEnabled(true);
                } else {
                    registerBtn.setBackgroundColor(Color.parseColor("#C4C4C4"));
                    registerBtn.setBorderColor(Color.parseColor("#C4C4C4"));
                    registerBtn.setEnabled(false);
                }
                break;
        }

    }

    /**
     * @param root         最外层布局，需要调整的布局
     * @param scrollToView 被键盘遮挡的scrollToView，滚动root,使scrollToView在root可视区域的底部
     */
    private void controlKeyboardLayout(final View root, final View scrollToView)
    {
//        root.getViewTreeObserver().addOnGlobalLayoutListener(
//                new ViewTreeObserver.OnGlobalLayoutListener()
//                {
//                    @Override
//                    public void onGlobalLayout()
//                    {
//                        Rect rect = new Rect();
//                        // 获取root在窗体的可视区域
//                        root.getWindowVisibleDisplayFrame(rect);
//                        // 获取root在窗体的不可视区域高度(被其他View遮挡的区域高度)
//                        int rootInvisibleHeight = root.getRootView()
//                                .getHeight() - rect.bottom;
//                        // 若不可视区域高度大于100，则键盘显示
//                        if (rootInvisibleHeight > 100) {
//                            int[] location = new int[2];
//                            // 获取scrollToView在窗体的坐标
//                            scrollToView.getLocationInWindow(location);
//                            // 计算root滚动高度，使scrollToView在可见区域
//                            int srollHeight = (location[1]
//                                    + scrollToView.getHeight() + DensityUtil.dip2px(10f)
//                            ) - rect.bottom;
//                            root.scrollTo(0, srollHeight);
//                        } else {
//                            // 键盘隐藏
//                            root.scrollTo(0, 0);
//                        }
//                    }
//                });
    }

    private class GetIdCodeCountDownTimer extends CountDownTimer
    {

        public GetIdCodeCountDownTimer(long millisInFuture, long countDownInterval)
        {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l)
        {
            registerCode.setText(l / 1000 + "s后重新获取");
            registerCode.setEnabled(false);
            registerCode.setBackgroundColor(Color.parseColor("#975200"));
        }

        @Override
        public void onFinish()
        {
            registerCode.setBackgroundColor(Color.parseColor("#D35F00"));
            registerCode.setText("获取验证码");
            registerCode.setEnabled(true);
        }
    }
}
