package cn.ahyxy.fastvisit.app.ui;

import android.graphics.Color;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.EditText;

import org.xutils.common.util.DensityUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cn.ahyxy.fastvisit.R;
import cn.ahyxy.fastvisit.baseui.BaseActivity;
import cn.ahyxy.fastvisit.weight.SizeChangeLinearLayout;
import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by yexiangyu on 16/4/20.
 */
@ContentView(R.layout.activity_register)
public class RegisterActivity extends BaseActivity
{
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


    @Override
    public void initWidget()
    {
        super.initWidget();
        registerCode.setTextSize(12);
        controlKeyboardLayout(sizechangeL, registerBtn);
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
