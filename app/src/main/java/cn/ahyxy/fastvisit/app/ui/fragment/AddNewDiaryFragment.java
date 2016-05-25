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

public class AddNewDiaryFragment extends BaseFragment {
    @ViewInject(R.id.edt_diary_problem)
    private EditText problemEditText;
    @ViewInject(R.id.edt_diary_profited)
    private EditText profitedEditText;
    @ViewInject(R.id.edt_diary_plan)
    private EditText planEditText;

    public AddNewDiaryFragment() {
        // Required empty public constructor
    }

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return inflater.inflate(R.layout.fragment_add_new_diary, container, false);
    }

    @Event(value = {R.id.btn_diary_save})
    private void onViewClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_diary_save:
                addNewDiary();
                break;
        }
    }

    private void addNewDiary() {
        String errorStr = getString(R.string.error_field_required);

        problemEditText.setError(null);
        profitedEditText.setError(null);
        planEditText.setError(null);

        String problem = problemEditText.getText().toString();
        if (TextUtils.isEmpty(problem)) {
            problemEditText.setError(errorStr);
            return;
        }

        String profited = profitedEditText.getText().toString();
        if (TextUtils.isEmpty(profited)) {
            profitedEditText.setError(errorStr);
            return;
        }

        String plan = planEditText.getText().toString();
        if (TextUtils.isEmpty(plan)) {
            planEditText.setError(errorStr);
            return;
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("s_id", String.valueOf(UserManager.getUserBean().getId()));
        map.put("d_id", String.valueOf(UserManager.getUserBean().getD_id()));
        map.put("problem", problem);
        map.put("profited", profited);
        map.put("plan", plan);
        showWaitDialog(getString(R.string.saving));
        DataManager.addNewDiary(map, new BaseCallBackJsonObject(getContext()) {
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
