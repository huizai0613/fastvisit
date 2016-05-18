package cn.ahyxy.fastvisit.baseui;

import cn.ahyxy.fastvisit.weight.dialog.DialogHelper;
import cn.ahyxy.fastvisit.weight.dialog.WaitDialog;

/**
 * Created by zack on 2016/5/18.
 */
public abstract class BaseFragment extends SupportFragment {
    private WaitDialog _waitDialog;

    public void hideWaitDialog() {
        if (_waitDialog != null) {
            try {
                _waitDialog.dismiss();
                _waitDialog = null;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        if (_waitDialog != null && _waitDialog.isShowing()) {
            _waitDialog.dismiss();
        }
        _waitDialog = null;
        super.onDestroy();
    }

    public WaitDialog showWaitDialog(String message) {
        if (_waitDialog == null) {
            _waitDialog = DialogHelper.getWaitDialog(getActivity(), message);
        }
        if (_waitDialog != null) {
            _waitDialog.setMessage(message);
            _waitDialog.show();
        }
        return _waitDialog;
    }

}
