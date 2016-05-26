package cn.ahyxy.fastvisit.app.ui.fragment;


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
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cn.ahyxy.fastvisit.R;
import cn.ahyxy.fastvisit.app.DataManager.DataManager;
import cn.ahyxy.fastvisit.app.DataManager.UserManager;
import cn.ahyxy.fastvisit.app.bean.OutletCommodityBean;
import cn.ahyxy.fastvisit.app.bean.ProductBean;
import cn.ahyxy.fastvisit.base.BaseCallBackJsonArray;
import cn.ahyxy.fastvisit.baseui.BaseFragment;

public class OutletManageDetailFragment extends BaseFragment {
    @ViewInject(R.id.tv_outlet_name)
    private TextView nameTextView;
    @ViewInject(R.id.tv_outlet_address)
    private TextView addressTextView;
    @ViewInject(R.id.tv_product_category)
    private TextView categoryTextView;
    @ViewInject(R.id.tv_product_count)
    private TextView countTextView;
    @ViewInject(R.id.lv_product)
    private ListView listView;
    private Bundle bundle;
    private ProductAdapter productAdapter;

    public static OutletManageDetailFragment newInstance(Bundle bundle) {
        OutletManageDetailFragment fragment = new OutletManageDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public OutletManageDetailFragment() {
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
        return inflater.inflate(R.layout.fragment_outlet_manage_detail, container, false);
    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);

        productAdapter = new ProductAdapter(bundle.getString("cate_id"));
        listView.setAdapter(productAdapter);
        nameTextView.setText(bundle.getString("t_name"));
        addressTextView.setText(bundle.getString("t_address"));
        categoryTextView.setText(bundle.getString("cate_name"));
//        ArrayList<OutletCommodityBean> postProductBeen = bundle.getParcelableArrayList("products");
//        if (postProductBeen != null) {
//            countTextView.setText(getString(R.string.product_count_format, postProductBeen.size()));
//        }
//        productAdapter.setData(postProductBeen);
        getTerminalCommodityList(bundle.getString("t_id"));
    }

    private void getTerminalCommodityList(String tId) {
        showWaitDialog(getString(R.string.error_view_loading));
        DataManager.getTerminalCommodity(
                String.valueOf(UserManager.getUserBean().getD_id()),
                tId, new BaseCallBackJsonArray(getContext()) {
                    @Override
                    public void onErrorJson(Throwable ex, boolean isOnCallback) {
                        hideWaitDialog();
                    }

                    @Override
                    public void onSuccessJsonArray(JSONArray result) {
                        hideWaitDialog();
                        if (getContext() != null) {
                            productAdapter.setData(DataManager.jsonArrayToOutletCommodityList(result));
                            countTextView.setText(getString(R.string.product_count_format, productAdapter.getCount()));
                        }
                    }
                });
    }

    public class ProductAdapter extends BaseAdapter {
        private List<OutletCommodityBean> list = new ArrayList<>();
        private String cateId;

        public ProductAdapter(String cateId) {
            this.cateId = cateId;
        }

        public void setData(List<OutletCommodityBean> list) {
            if (list == null || list.isEmpty()) {
                return;
            }
            this.list.clear();
            this.list.addAll(list);
            notifyDataSetChanged();
        }

        public List<OutletCommodityBean> getList() {
            return list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public OutletCommodityBean getItem(int position) {
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
            OutletCommodityBean bean = getItem(position);
            viewHolder.name.setText(bean.getCommodity().getName());
            String subtitle = parent.getContext().getString(R.string.product_subtitle_format,
                    bean.getCommodity().getSpec_name(), bean.getCommodity().getUnit_name());
            viewHolder.specUnitName.setText(subtitle);

            List<ProductBean.PriceEntity> priceEntities = bean.getCommodity().getPrice();
            for (ProductBean.PriceEntity entity : priceEntities) {
                if (cateId.equals(entity.getTcid())) {
                    String price = entity.getPrice();
                    viewHolder.price.setText(parent.getContext().getString(R.string.product_price_format, price));
                    break;
                }
            }
            viewHolder.subtract.setVisibility(View.GONE);
            viewHolder.count.setVisibility(View.GONE);
            viewHolder.add.setVisibility(View.GONE);
            return convertView;
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
