package cn.ahyxy.fastvisit.app.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.TextView;

import org.json.JSONArray;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cn.ahyxy.fastvisit.R;
import cn.ahyxy.fastvisit.app.DataManager.DataManager;
import cn.ahyxy.fastvisit.app.DataManager.UserManager;
import cn.ahyxy.fastvisit.app.bean.PostProductBean;
import cn.ahyxy.fastvisit.app.bean.ProductBean;
import cn.ahyxy.fastvisit.app.ui.OrderManageActivity;
import cn.ahyxy.fastvisit.base.BaseCallBackJsonArray;
import cn.ahyxy.fastvisit.baseui.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductListFragment extends BaseFragment {
    public static final int REQUEST_CODE = 1;
    @ViewInject(R.id.tv_product_category)
    private TextView categoryTextView;
    @ViewInject(R.id.tv_product_count)
    private TextView countTextView;
    @ViewInject(R.id.lv_product)
    private ListView listView;
    private Bundle bundle;
    private ProductAdapter productAdapter;

    public static ProductListFragment newInstance(Bundle bundle) {
        ProductListFragment fragment = new ProductListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public ProductListFragment() {
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
        return inflater.inflate(R.layout.fragment_product_list, container, false);
    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        productAdapter = new ProductAdapter(bundle.getString("cate_one"));
        listView.setAdapter(productAdapter);

        getProductList();
    }

    @Event(value = {R.id.btn_product_next})
    private void onViewClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_product_next:
                next();
                break;
        }
    }

    private void next() {
        Map<Integer, Integer> countMap = productAdapter.getCountMap();
        ArrayList<PostProductBean> postProductList = new ArrayList<>();
        for (Integer key : countMap.keySet()) {
            int value = countMap.get(key);
            if (value > 0) {
                PostProductBean bean = productAdapter.getItem(key);
                bean.setCount(value);
                postProductList.add(bean);
            }
        }
        bundle.putParcelableArrayList("products", postProductList);
        Intent intent = new Intent(getActivity(), OrderManageActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_CODE);
    }

    private void getProductList() {
        showWaitDialog(getString(R.string.error_view_loading));
        DataManager.getProductList(String.valueOf(UserManager.getUserBean().getD_id()),
                new BaseCallBackJsonArray(getContext()) {
                    @Override
                    public void onErrorJson(Throwable ex, boolean isOnCallback) {
                        hideWaitDialog();
                    }

                    @Override
                    public void onSuccessJsonArray(JSONArray result) {
                        hideWaitDialog();
                        productAdapter.setData(DataManager.jsonArrayToProductList(result));
                        categoryTextView.setText(bundle.getString("cate_name"));
                        countTextView.setText(getString(R.string.product_count_format, productAdapter.getCount()));
                    }
                });
    }

    public static class ProductAdapter extends BaseAdapter implements View.OnClickListener {
        private String cateId;
        private List<ProductBean> list = new ArrayList<>();
        private Map<Integer, Integer> countMap = new TreeMap<>();

        public ProductAdapter(String cateId) {
            this.cateId = cateId;
        }

        public void setData(List<ProductBean> list) {
            if (list == null || list.isEmpty()) {
                return;
            }
            this.list.clear();
            this.list.addAll(list);
            notifyDataSetChanged();
        }

        public Map<Integer, Integer> getCountMap() {
            return countMap;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public ProductBean getItem(int position) {
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
            ProductBean bean = getItem(position);
            viewHolder.name.setText(bean.getName());
            String subtitle = parent.getContext().getString(R.string.product_subtitle_format, bean.getSpec_name(), bean.getUnit_name());
            viewHolder.specUnitName.setText(subtitle);
            List<ProductBean.PriceEntity> priceEntities = bean.getPrice();
            for (ProductBean.PriceEntity entity : priceEntities) {
                if (cateId.equals(entity.getTcid())) {
                    String price = entity.getPrice();
                    bean.setPriceNumber(price);
                    viewHolder.price.setText(parent.getContext().getString(R.string.product_price_format, price));
                    break;
                }
            }

            viewHolder.subtract.setTag(position);
            viewHolder.subtract.setOnClickListener(this);
            if (countMap.get(position) != null) {
                viewHolder.count.setText(String.valueOf(countMap.get(position)));
            } else {
                viewHolder.count.setText("0");
            }

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
                    countMap.put(viewHolder.position, Integer.valueOf(count));
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
                    if (position >= 0 && countMap.get(position) != null) {
                        count = countMap.get(position);
                    }
                    if (count > 0) {
                        count--;
                    }
                    countMap.put(position, count);
                    break;
                case R.id.iv_product_add:
                    position = (int) v.getTag();
                    if (position >= 0 && countMap.get(position) != null) {
                        count = countMap.get(position);
                    }
                    count++;
                    countMap.put(position, count);
                    break;
            }
            LogUtil.d("position:" + position + ", count:" + count);
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
