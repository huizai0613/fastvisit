package cn.ahyxy.fastvisit.app.ui.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONObject;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.ahyxy.fastvisit.R;
import cn.ahyxy.fastvisit.app.DataManager.DataManager;
import cn.ahyxy.fastvisit.app.DataManager.UserManager;
import cn.ahyxy.fastvisit.app.bean.PostProductBean;
import cn.ahyxy.fastvisit.base.BaseCallBackJsonObject;
import cn.ahyxy.fastvisit.baseui.BaseFragment;
import cn.ahyxy.fastvisit.utils.ToastUtils;

public class OrderManageFragment extends BaseFragment {
    @ViewInject(R.id.tv_outlet_name)
    private TextView nameTextView;
    @ViewInject(R.id.tv_outlet_address)
    private TextView addressTextView;
    @ViewInject(R.id.tv_product_category)
    private TextView categoryTextView;
    @ViewInject(R.id.tv_product_count)
    private TextView countTextView;
    @ViewInject(R.id.rb_order_manage_cash)
    private RadioButton cashRadioButton;
    @ViewInject(R.id.rb_order_manage_credit)
    private RadioButton creditRadioButton;
    @ViewInject(R.id.tv_order_manage_total)
    private TextView totalTextView;
    @ViewInject(R.id.lv_product)
    private ListView listView;
    private Bundle bundle;
    private ProductAdapter productAdapter;

    public static OrderManageFragment newInstance(Bundle bundle) {
        OrderManageFragment fragment = new OrderManageFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public OrderManageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bundle = getArguments();
        }
    }

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return inflater.inflate(R.layout.fragment_order_manage, container, false);
    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        productAdapter = new ProductAdapter();
        listView.setAdapter(productAdapter);
        nameTextView.setText(bundle.getString("t_name"));
        addressTextView.setText(bundle.getString("t_address"));
        categoryTextView.setText(bundle.getString("cate_name"));
        ArrayList<PostProductBean> postProductBeen = bundle.getParcelableArrayList("products");
        if (postProductBeen != null) {
            countTextView.setText(getString(R.string.product_count_format, postProductBeen.size()));
        }
        productAdapter.setData(postProductBeen);
        updateTotal(postProductBeen);
    }

    @Event(value = {R.id.btn_order_commit})
    private void onViewClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_order_commit:
                commitOrder();
                break;
        }
    }

    private void commitOrder() {
        showWaitDialog(getString(R.string.doing_commit));
        HashMap<String, String> map = new HashMap<>();
        map.put("s_id", String.valueOf(UserManager.getUserBean().getId()));
        map.put("t_id", String.valueOf(bundle.getInt("id")));
        map.put("d_id", String.valueOf(UserManager.getUserBean().getD_id()));
        int count = 0;
        double total = 0;
        List<PostProductBean> postList = new ArrayList<>();
        for (PostProductBean bean : productAdapter.getList()) {
            if (bean.getCount() > 0) {
                count += bean.getCount();
                total += Double.valueOf(bean.getPriceNumber()) * bean.getCount();
                postList.add(bean);
            }
        }
        map.put("count", String.valueOf(count));
        map.put("money", String.valueOf(total));
        map.put("info", new Gson().toJson(postList));
        if (cashRadioButton.isChecked()) {
            map.put("payment", cashRadioButton.getText().toString());
        } else if (creditRadioButton.isChecked()) {
            map.put("payment", creditRadioButton.getText().toString());
        }
        map.put("remark", "");
        LogUtil.d(map.toString());
        DataManager.commitOrder(map, new BaseCallBackJsonObject(getContext()) {
            @Override
            public void onErrorJson(Throwable ex, boolean isOnCallback) {
                hideWaitDialog();
            }

            @Override
            public void onSuccessJsonObject(JSONObject result) {
                hideWaitDialog();
                if (getContext() != null) {
                    ToastUtils.Infotoast(getContext(), getString(R.string.commit_success));
                    getActivity().setResult(Activity.RESULT_OK);
                    getActivity().finish();
                }
            }
        });
    }

    private void updateTotal(List<PostProductBean> postProductBeen) {
        float total = 0;
        for (PostProductBean bean : postProductBeen) {
            total += bean.getCount() * Double.valueOf(bean.getPriceNumber());
        }
        totalTextView.setText(getString(R.string.product_price_format, total));
    }

    public class ProductAdapter extends BaseAdapter implements View.OnClickListener {
        private List<PostProductBean> list = new ArrayList<>();

        public ProductAdapter() {
        }

        public void setData(List<PostProductBean> list) {
            if (list == null || list.isEmpty()) {
                return;
            }
            this.list.clear();
            this.list.addAll(list);
            notifyDataSetChanged();
        }

        public List<PostProductBean> getList() {
            return list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public PostProductBean getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(parent.getContext(), R.layout.list_item_product, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.position = position;
            PostProductBean bean = getItem(position);
            viewHolder.name.setText(bean.getName());
            String subtitle = parent.getContext().getString(R.string.product_subtitle_format, bean.getSpec_name(), bean.getUnit_name());
            viewHolder.specUnitName.setText(subtitle);

            viewHolder.price.setText(parent.getContext().getString(R.string.product_price_format, bean.getPriceNumber()));

            viewHolder.subtract.setTag(position);
            viewHolder.subtract.setOnClickListener(this);
            viewHolder.count.setText(String.valueOf(bean.getCount()));

            viewHolder.add.setTag(position);
            viewHolder.add.setOnClickListener(this);
            viewHolder.count.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String count = "0";
                    if (!TextUtils.isEmpty(s)) {
                        count = s.toString();
                    }
                    LogUtil.d("position:" + viewHolder.position + ", count:" + count + ", s:" + s);
                    getItem(viewHolder.position).setCount(Integer.valueOf(count));
                    updateTotal(list);
                }
            });
            return convertView;
        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            int position = -1;
            int count = 0;
            switch (id) {
                case R.id.iv_product_subtract:
                    position = (int) v.getTag();
                    if (position >= 0 && getItem(position) != null) {
                        count = getItem(position).getCount();
                    }
                    if (count > 0) {
                        count--;
                    }
                    getItem(position).setCount(count);
                    break;
                case R.id.iv_product_add:
                    position = (int) v.getTag();
                    if (position >= 0 && getItem(position) != null) {
                        count = getItem(position).getCount();
                    }
                    count++;
                    getItem(position).setCount(count);
                    break;
            }
            LogUtil.d("position:" + position + ", count:" + count);
            updateTotal(list);
            notifyDataSetChanged();
        }

        private class ViewHolder {
            private TextView name;
            private TextView specUnitName;
            private TextView price;
            private ImageView subtract;
            private EditText count;
            private ImageView add;
            private int position;

            public ViewHolder(View view) {
                name = (TextView) view.findViewById(R.id.tv_product_name);
                specUnitName = (TextView) view.findViewById(R.id.tv_product_subtitle);
                price = (TextView) view.findViewById(R.id.tv_product_price);
                subtract = (ImageView) view.findViewById(R.id.iv_product_subtract);
                count = (EditText) view.findViewById(R.id.et_product_count);
                add = (ImageView) view.findViewById(R.id.iv_product_add);
            }
        }
    }
}
