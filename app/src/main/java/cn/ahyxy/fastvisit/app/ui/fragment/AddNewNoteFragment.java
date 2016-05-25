package cn.ahyxy.fastvisit.app.ui.fragment;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.json.JSONObject;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;

import cn.ahyxy.fastvisit.R;
import cn.ahyxy.fastvisit.app.DataManager.DataManager;
import cn.ahyxy.fastvisit.app.DataManager.UserManager;
import cn.ahyxy.fastvisit.base.BaseCallBackJsonObject;
import cn.ahyxy.fastvisit.baseui.BaseFragment;
import cn.ahyxy.fastvisit.utils.ToastUtils;
import mehdi.sakout.fancybuttons.FancyButton;

public class AddNewNoteFragment extends BaseFragment {
    @ViewInject(R.id.edt_note_title)
    private EditText titleEditText;
    @ViewInject(R.id.edt_note_content)
    private EditText contentEditText;

    public AddNewNoteFragment() {
        // Required empty public constructor
    }

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return inflater.inflate(R.layout.fragment_add_new_note, container, false);
    }

    @Event(value = {R.id.btn_note_save})
    private void onViewClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_note_save:
                addNewNote();
                break;
        }
    }

    private void addNewNote() {
        String errorStr = getString(R.string.error_field_required);

        titleEditText.setError(null);
        contentEditText.setError(null);

        String title = titleEditText.getText().toString();
        if (TextUtils.isEmpty(title)) {
            titleEditText.setError(errorStr);
            return;
        }

        String content = contentEditText.getText().toString();
        if (TextUtils.isEmpty(content)) {
            contentEditText.setError(errorStr);
            return;
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("s_id", String.valueOf(UserManager.getUserBean().getId()));
        map.put("d_id", String.valueOf(UserManager.getUserBean().getD_id()));
        map.put("title", title);
        map.put("content", content);
        showWaitDialog(getString(R.string.saving));
        DataManager.addNewNote(map, new BaseCallBackJsonObject(getContext()) {
            @Override
            public void onErrorJson(Throwable ex, boolean isOnCallback) {
                hideWaitDialog();
            }

            @Override
            public void onSuccessJsonObject(JSONObject result) {
                hideWaitDialog();
                if (getContext() != null) {
                    ToastUtils.Infotoast(getContext(), getString(R.string.save_success));
                    getActivity().finish();
                }
            }
        });
    }
}
