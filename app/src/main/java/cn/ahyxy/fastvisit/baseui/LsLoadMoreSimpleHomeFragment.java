package cn.ahyxy.fastvisit.baseui;

import android.view.View;
import android.widget.BaseAdapter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;

import java.util.ArrayList;

import cn.ahyxy.fastvisit.R;
import cn.ahyxy.fastvisit.base.BaseCallBackJsonArray;
import in.srain.cube.views.ptr.loadmore.LoadMoreContainer;
import in.srain.cube.views.ptr.loadmore.LoadMoreHandler;
import in.srain.cube.views.ptr.loadmore.LoadMoreListViewContainer;


public abstract class LsLoadMoreSimpleHomeFragment extends LsSimpleHomeFragment
{

    protected LoadMoreListViewContainer loadMoreListViewContainer;

    protected ArrayList<JSONObject> jsonObjects;
    protected BaseAdapter mAdapter;

    protected BaseCallBackJsonArray baseCallBackJsonArray;

    @Override
    protected void initWidget(View parentView)
    {
        super.initWidget(parentView);
        loadMoreListViewContainer = bindView(R.id.load_more_list_view_container);
        loadMoreListViewContainer.useDefaultHeader();

        loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler()
        {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer)
            {
                loadMore();
            }
        });

        baseCallBackJsonArray = new BaseCallBackJsonArray(mActivity, this)
        {
            @Override
            public void onErrorJson(Throwable ex, boolean isOnCallback)
            {
                mPtrClassicFrameLayout.refreshComplete();
                loadMoreListViewContainer.loadMoreFinish(true, false);
                LsLoadMoreSimpleHomeFragment.this.onErrorJson(ex, isOnCallback);
            }

            @Override
            public void onSuccessJsonArray(JSONArray result)
            {
                if (result != null) {
                    int length = result.length();
                    if (length > 0) {
                        if (isRefresh) {
                            mPtrClassicFrameLayout.refreshComplete();
                            jsonObjects = new ArrayList<JSONObject>();
                            for (int i = 0; i < result.length(); i++) {
                                jsonObjects.add(result.optJSONObject(i));
                            }
                            if (jsonObjects.size() < 10) {
                                loadMoreListViewContainer.loadMoreFinish(false, false);
                            } else {
                                loadMoreListViewContainer.loadMoreFinish(false, true);
                            }
                            successData();
                            mContentListView.setAdapter(mAdapter);
                        } else {
                            for (int i = 0; i < result.length(); i++) {
                                jsonObjects.add(result.optJSONObject(i));
                            }
                            mAdapter.notifyDataSetChanged();
                            loadMoreListViewContainer.loadMoreFinish(false, true);
                        }
                    } else {
                        if (isRefresh) {
                            loadMoreListViewContainer.loadMoreFinish(true, false);
                        } else {
                            loadMoreListViewContainer.loadMoreFinish(false, false);
                        }
                    }
                } else {
                    if (isRefresh) {
                        loadMoreListViewContainer.loadMoreFinish(true, false);
                    } else {
                        loadMoreListViewContainer.loadMoreFinish(false, false);
                    }
                }
            }
        };
    }


    protected void loadMore()
    {
        LogUtil.d("开始加载下一页...");
        isRefresh = false;
        page++;
        getDataFronServer();
    }


    protected abstract void successData();

    protected abstract void onErrorJson(Throwable ex, boolean isOnCallback);
}
