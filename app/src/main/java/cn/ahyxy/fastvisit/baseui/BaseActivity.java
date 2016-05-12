package cn.ahyxy.fastvisit.baseui;


import android.content.Context;
import android.os.Bundle;

import cn.ahyxy.fastvisit.app.AppContext;
import cn.ahyxy.fastvisit.weight.dialog.DialogHelper;
import cn.ahyxy.fastvisit.weight.dialog.WaitDialog;

/**
 * Created by yexiangyu on 16/3/8.
 */
public abstract class BaseActivity extends SupportActivity
{
    private boolean _isVisible;
    private WaitDialog _waitDialog;

    protected Context mContext;
    protected BaseActivity mBaseActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        mContext = AppContext.getInstance();
        _isVisible = true;
        mBaseActivity = this;
        super.onCreate(savedInstanceState);
    }

    public void animaFinish()
    {
        //TODO 以后可能加上动画效果
        this.finish();
//        overridePendingTransition(R.anim.activity_out,
//                R.anim.activity_in);
    }

    public void hideWaitDialog()
    {
        if (_isVisible && _waitDialog != null) {
            try {
                _waitDialog.dismiss();
                _waitDialog = null;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public WaitDialog showWaitDialog(String message)
    {
        if (_isVisible) {
            if (_waitDialog == null) {
                _waitDialog = DialogHelper.getWaitDialog(this, message);
            }
            if (_waitDialog != null) {
                _waitDialog.setMessage(message);
                _waitDialog.show();
            }
            return _waitDialog;
        }
        return null;
    }

    @Override
    public void onBackPressed()
    {
        animaFinish();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mBaseActivity = null;
    }
}
