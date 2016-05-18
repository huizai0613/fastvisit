package cn.ahyxy.fastvisit.app.ui.fragment;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cn.ahyxy.fastvisit.R;
import cn.ahyxy.fastvisit.app.DataManager.DataManager;
import cn.ahyxy.fastvisit.app.DataManager.UserManager;
import cn.ahyxy.fastvisit.app.bean.POSBean;
import cn.ahyxy.fastvisit.base.BaseCallBackJsonArray;
import cn.ahyxy.fastvisit.baseui.BaseFragment;
import cn.ahyxy.fastvisit.baseui.SupportFragment;

public class OutletSearchFragment extends BaseFragment implements TextView.OnEditorActionListener, AdapterView.OnItemClickListener, TextWatcher {
    @ViewInject(R.id.et_outlet_search)
    private EditText searchEditText;
    @ViewInject(R.id.ll_outlet_search_hot)
    private LinearLayout hotLayout;
    @ViewInject(R.id.lv_outlet_search_hot)
    private ListView hotListView;
    @ViewInject(R.id.ll_outlet_search_result)
    private LinearLayout resultLayout;
    @ViewInject(R.id.lv_outlet_search_result)
    private ListView resultListView;

    private HotAdapter hotAdapter;


    public OutletSearchFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        searchEditText.setOnEditorActionListener(this);
        searchEditText.addTextChangedListener(this);
        hotAdapter = new HotAdapter();
        hotListView.setAdapter(hotAdapter);
        hotListView.setOnItemClickListener(this);
        getSearchHot();
    }

    private void getSearchHot() {
        showWaitDialog(getString(R.string.error_view_loading));
        DataManager.getOutletSearchHot(String.valueOf(UserManager.getUserBean().getId()), new BaseCallBackJsonArray(getContext()) {
            @Override
            public void onErrorJson(Throwable ex, boolean isOnCallback) {
                hideWaitDialog();
            }

            @Override
            public void onSuccessJsonArray(JSONArray result) {
                hideWaitDialog();
                if (hotAdapter != null) {
                    hotAdapter.setData(DataManager.jsonArrayToPOSBeanList(result));
                }
            }
        });
    }

    private void onSearchAction(String keyword) {
        showWaitDialog(getString(R.string.error_view_loading));
        DataManager.getOutletSearchResult(String.valueOf(UserManager.getUserBean().getId()), keyword, new BaseCallBackJsonArray(getContext()) {
            @Override
            public void onErrorJson(Throwable ex, boolean isOnCallback) {
                hideWaitDialog();
            }

            @Override
            public void onSuccessJsonArray(JSONArray result) {
                hideWaitDialog();
                if (hotAdapter != null) {
                    hotAdapter.setData(DataManager.jsonArrayToPOSBeanList(result));
                }
            }
        });
    }

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return inflater.inflate(R.layout.fragment_outlet_search, container, false);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                actionId == EditorInfo.IME_ACTION_DONE ||
                event.getAction() == KeyEvent.ACTION_DOWN &&
                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            onSearchAction(v.getText().toString());
            return true;
        }
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent == hotListView) {
            onSearchAction(hotAdapter.getItem(position).getT_name());
        } else if (parent == resultListView) {
            //TODO navigation
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() == 0) {
            hotLayout.setVisibility(View.VISIBLE);
            resultLayout.setVisibility(View.GONE);
        }
    }

    private static class HotAdapter extends BaseAdapter {
        private List<POSBean> list = new ArrayList<>();

        public void setData(List<POSBean> list) {
            this.list.clear();
            this.list.addAll(list);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public POSBean getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView;
            if (convertView == null) {
                textView = new TextView(parent.getContext());
                AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                textView.setGravity(Gravity.CENTER_HORIZONTAL);
                textView.setTextSize(parent.getContext().getResources().getDimension(R.dimen.font_small));
                int padding = parent.getContext().getResources().getDimensionPixelOffset(R.dimen.spacing_tiny);
                textView.setPadding(padding, padding, padding, padding);
                textView.setLayoutParams(layoutParams);
            } else {
                textView = (TextView) convertView;
            }
            int colorId = (position % 2 == 0) ? R.color.blue_text : R.color.green_little;
            textView.setTextColor(parent.getContext().getResources().getColor(colorId));
            textView.setText(getItem(position).getT_name());
            return textView;
        }
    }

    private static class ResultAdapter extends HotAdapter {
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}
