package cn.ahyxy.fastvisit.app.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
import cn.ahyxy.fastvisit.app.ui.OutletManageDetailActivity;
import cn.ahyxy.fastvisit.base.BaseCallBackJsonArray;
import cn.ahyxy.fastvisit.baseui.BaseFragment;
import cn.ahyxy.fastvisit.utils.ToastUtils;

public class AdvancedPOSFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    @ViewInject(R.id.lv_advanced_pos)
    private ListView listView;
    private MyAdapter myAdapter;

    public AdvancedPOSFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        myAdapter = new MyAdapter();
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(this);
        getPOSList();
    }

    private void getPOSList() {
        showWaitDialog(getString(R.string.error_view_loading));
        DataManager.getAdvancedPOS(String.valueOf(UserManager.getUserBean().getId()),
                new BaseCallBackJsonArray(getActivity()) {
                    @Override
                    public void onErrorJson(Throwable ex, boolean isOnCallback) {
                        if (getContext() != null) {
                            ToastUtils.Errortoast(getContext(), getString(R.string.error_json));
                            hideWaitDialog();
                        }
                    }

                    @Override
                    public void onSuccessJsonArray(JSONArray result) {
                        hideWaitDialog();
                        if (myAdapter != null) {
                            myAdapter.setData(DataManager.jsonArrayToPOSBeanList(result));
                        }
                    }
                });
    }

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return inflater.inflate(R.layout.fragment_advanced_pos, container, false);
    }

    private static int[] starResIDs = new int[]{R.drawable.star6, R.drawable.star0_5, R.drawable.star1,
            R.drawable.star1_5, R.drawable.star2, R.drawable.star2_5, R.drawable.star3, R.drawable.star3_5,
            R.drawable.star4, R.drawable.star4_5, R.drawable.star5};

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        POSBean bean = myAdapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putString("cate_id", String.valueOf(bean.getCate_one()));
        bundle.putString("t_id", String.valueOf(bean.getId()));
        bundle.putString("t_name", bean.getT_name());
        bundle.putString("t_address", bean.getT_address());
        bundle.putString("cate_name", bean.getCate_name());
        Intent intent = new Intent(getActivity(), OutletManageDetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private static class MyAdapter extends BaseAdapter {
        private List<POSBean> list = new ArrayList<>();

        public void setData(List<POSBean> list) {
            this.list.clear();
            if (list != null) {
                this.list.addAll(list);
            }
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return this.list.size();
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
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(parent.getContext(), R.layout.list_item_advanced_pos, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            POSBean posBean = getItem(position);
            holder.name.setText(posBean.getT_name());
            holder.address.setText(posBean.getT_address());
            int level = posBean.getT_level();
            if (level >= 0 && level < starResIDs.length) {
                holder.star.setImageResource(starResIDs[level]);
            }
            return convertView;
        }

        private static class ViewHolder {
            TextView name;
            ImageView star;
            TextView address;

            public ViewHolder(View view) {
                name = (TextView) view.findViewById(R.id.tv_advanced_pos_name);
                star = (ImageView) view.findViewById(R.id.iv_advanced_pos_star);
                address = (TextView) view.findViewById(R.id.tv_outlet_address);
            }
        }
    }
}
