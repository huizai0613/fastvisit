package cn.ahyxy.fastvisit.app.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.xutils.view.annotation.ViewInject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.ahyxy.fastvisit.R;
import cn.ahyxy.fastvisit.app.DataManager.DataManager;
import cn.ahyxy.fastvisit.app.DataManager.UserManager;
import cn.ahyxy.fastvisit.app.bean.TaskBrandBean;
import cn.ahyxy.fastvisit.base.BaseCallBackJsonArray;
import cn.ahyxy.fastvisit.baseui.BaseFragment;

public class TaskBrandFragment extends BaseFragment {
    @ViewInject(R.id.lv_outlet_manage)
    private ListView listView;
    private ResultAdapter resultAdapter;

    public TaskBrandFragment() {
        // Required empty public constructor
    }

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return inflater.inflate(R.layout.fragment_outlet_manage, container, false);
    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        resultAdapter = new ResultAdapter();
        listView.setAdapter(resultAdapter);
        getTaskBrandList();
    }

    private void getTaskBrandList() {
        showWaitDialog(getString(R.string.error_view_loading));
        DataManager.getTaskBrandList(String.valueOf(UserManager.getUserBean().getId()), new BaseCallBackJsonArray(getContext()) {
            @Override
            public void onErrorJson(Throwable ex, boolean isOnCallback) {
                hideWaitDialog();
            }

            @Override
            public void onSuccessJsonArray(JSONArray result) {
                hideWaitDialog();
                if (getContext() != null) {
                    resultAdapter.setData(DataManager.jsonArrayToTaskBrandList(result));
                }
            }
        });
    }
    private static abstract class BaseTaskBrandAdapter extends BaseAdapter {
        private List<TaskBrandBean> list = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        public void setData(List<TaskBrandBean> list) {
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
        public TaskBrandBean getItem(int position) {
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
                convertView = View.inflate(parent.getContext(), R.layout.list_item_task_brand, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            TaskBrandBean taskBrandBean = getItem(position);
            holder.name.setText(taskBrandBean.getName());
            holder.subject.setText(taskBrandBean.getSubject());
            Date startDate = new Date(taskBrandBean.getStart_time() * 1000l);
            Date endDate = new Date(taskBrandBean.getEnd_time() * 1000l);
            holder.time.setText(dateFormat.format(startDate) + "-" + dateFormat.format(endDate));

            return convertView;
        }

        private static class ViewHolder {
            ImageView icon;
            TextView name;
            TextView subject;
            TextView time;

            public ViewHolder(View view) {
                icon = (ImageView) view.findViewById(R.id.iv_task_brand_icon);
                name = (TextView) view.findViewById(R.id.tv_task_brand_name);
                subject = (TextView) view.findViewById(R.id.tv_task_brand_subject);
                time = (TextView) view.findViewById(R.id.tv_task_brand_time);
            }
        }
    }
}
