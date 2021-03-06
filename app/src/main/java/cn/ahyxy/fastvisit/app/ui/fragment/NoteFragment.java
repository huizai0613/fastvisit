package cn.ahyxy.fastvisit.app.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.ahyxy.fastvisit.R;
import cn.ahyxy.fastvisit.app.DataManager.DataManager;
import cn.ahyxy.fastvisit.app.DataManager.UserManager;
import cn.ahyxy.fastvisit.app.bean.NoteBean;
import cn.ahyxy.fastvisit.app.ui.AddNewNoteActivity;
import cn.ahyxy.fastvisit.base.BaseCallBackJsonArray;
import cn.ahyxy.fastvisit.baseui.BaseFragment;

public class NoteFragment extends BaseFragment {
    @ViewInject(R.id.lv_note)
    private ListView listView;
    private ResultAdapter resultAdapter;

    public NoteFragment() {
        // Required empty public constructor
    }

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return inflater.inflate(R.layout.fragment_note, container, false);
    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        resultAdapter = new ResultAdapter();
        listView.setAdapter(resultAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        getNoteList();
    }

    @Event(value = {R.id.btn_add_new})
    private void onViewClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_add_new:
                Intent intent = new Intent(getContext(), AddNewNoteActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void getNoteList() {
        showWaitDialog(getString(R.string.error_view_loading));
        DataManager.getNoteList(String.valueOf(UserManager.getUserBean().getId()),
                new BaseCallBackJsonArray(getContext()) {
                    @Override
                    public void onErrorJson(Throwable ex, boolean isOnCallback) {
                        hideWaitDialog();
                    }

                    @Override
                    public void onSuccessJsonArray(JSONArray result) {
                        hideWaitDialog();
                        resultAdapter.setData(DataManager.jsonArrayToNoteList(result));
                    }
                });
    }

    private static abstract class BaseTaskBrandAdapter extends BaseAdapter {
        private List<NoteBean> list = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        public void setData(List<NoteBean> list) {
            this.list.clear();
            if (list != null) {
                this.list.addAll(list);
            }
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public NoteBean getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
    }

    public static class ResultAdapter extends BaseTaskBrandAdapter {
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(parent.getContext(), R.layout.list_item_note, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            NoteBean NoteBean = getItem(position);
            holder.title.setText(NoteBean.getTitle());
            holder.content.setText(NoteBean.getContent());
            Date date = new Date(NoteBean.getCreatetime() * 1000l);
            holder.time.setText(dateFormat.format(date));

            return convertView;
        }

        private static class ViewHolder {
            CheckBox checkBox;
            TextView title;
            TextView content;
            TextView time;

            public ViewHolder(View view) {
                checkBox = (CheckBox) view.findViewById(R.id.cb_note_select);
                title = (TextView) view.findViewById(R.id.tv_note_title);
                content = (TextView) view.findViewById(R.id.tv_note_content);
                time = (TextView) view.findViewById(R.id.tv_note_time);
            }
        }
    }
}
