package cn.ahyxy.fastvisit.baseui.list;

import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

import cn.ahyxy.fastvisit.R;
import cn.ahyxy.fastvisit.base.BaseCallBackJsonArray;
import cn.ahyxy.fastvisit.base.BaseCallBackJsonObject;
import cn.ahyxy.fastvisit.baseui.titlebar.TitleBarActivity;
import cn.ahyxy.fastvisit.bean.base.BaseBean;
import cn.ahyxy.fastvisit.weight.EmptyLayout;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.loadmore.LoadMoreContainer;
import in.srain.cube.views.ptr.loadmore.LoadMoreHandler;
import in.srain.cube.views.ptr.loadmore.LoadMoreListViewContainer;

/**
 * Created by yexiangyu on 16/3/8.
 */
@ContentView(R.layout.activity_list)
public abstract class BaseListActivity<T extends BaseBean> extends TitleBarActivity
{
    @ViewInject(R.id.load_more_list_view_ptr_frame)
    public PtrClassicFrameLayout mPtrFrameLayout;
    @ViewInject(R.id.load_more_list_view_container)
    protected LoadMoreListViewContainer loadMoreListViewContainer;
    @ViewInject(R.id.list_view)
    protected ListView mListView;
    @ViewInject(R.id.empty_view)
    public EmptyLayout mEmptyLayout;
    protected int page = 0; // 分页索引

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initData()
    {
        super.initData();
        mEmptyLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
        page = 0;
        isRefresh = true;
        getDataFronServer();
    }

    @Override
    public void initWidget()
    {
        super.initWidget();
        intitListView();
        baseCallBackJsonObject = new BaseCallBackJsonObject(mContext, this)
        {
            @Override
            public void onErrorJson(Throwable ex, boolean isOnCallback)
            {
                BaseListActivity.this.onErrorJson(ex, isOnCallback);
            }

            @Override
            public void onSuccessJsonObject(JSONObject resultOBJ)
            {
                mEmptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
                mPtrFrameLayout.refreshComplete();
                if (resultOBJ != null) {
                    jsonData = resultOBJ;
                    JSONArray result = resultOBJ.optJSONArray("list");
                    if (result != null) {
                        int length = result.length();
                        if (length > 0) {
                            if (isRefresh) {
                                jsonObjects = new ArrayList<JSONObject>();
                                for (int i = 0; i < result.length(); i++) {
                                    jsonObjects.add(result.optJSONObject(i));
                                }
                                if (jsonObjects.size() < 10) {
                                    loadMoreListViewContainer.loadMoreFinish(false, false);
                                } else {
                                    loadMoreListViewContainer.loadMoreFinish(false, true);
                                }
                                parasData();
                                successData();
                                mListView.setAdapter(mAdapter);
                            } else {
                                for (int i = 0; i < result.length(); i++) {
                                    jsonObjects.add(result.optJSONObject(i));
                                }
                                parasData();
                                mAdapter.notifyDataSetChanged();
                                loadMoreListViewContainer.loadMoreFinish(false, true);
                            }
                        } else {
                            if (isRefresh) {
                                mEmptyLayout.setErrorType(EmptyLayout.NODATA);
                            } else {
                                loadMoreListViewContainer.loadMoreFinish(false, false);
                            }

                        }
                    } else {
                        if (isRefresh) {
                            mEmptyLayout.setErrorType(EmptyLayout.NODATA);
                        } else {
                            loadMoreListViewContainer.loadMoreFinish(false, false);
                        }
                    }
                }
            }
        };
        baseCallBackJsonArray = new BaseCallBackJsonArray(mContext, this)
        {
            @Override
            public void onErrorJson(Throwable ex, boolean isOnCallback)
            {
                BaseListActivity.this.onErrorJson(ex, isOnCallback);
            }

            @Override
            public void onSuccessJsonArray(JSONArray result)
            {
                mEmptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
                mPtrFrameLayout.refreshComplete();
                if (result != null) {
                    int length = result.length();
                    if (length > 0) {
                        if (isRefresh) {
                            jsonObjects = new ArrayList<JSONObject>();
                            for (int i = 0; i < result.length(); i++) {
                                jsonObjects.add(result.optJSONObject(i));
                            }
                            if (jsonObjects.size() < 10) {
                                loadMoreListViewContainer.loadMoreFinish(false, false);
                            } else {
                                loadMoreListViewContainer.loadMoreFinish(false, true);
                            }
                            parasData();
                            successData();
                            mListView.setAdapter(mAdapter);
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

    protected void intitListView()
    {
        mPtrFrameLayout.setLoadingMinTime(1000);
        mPtrFrameLayout.setPtrHandler(new PtrHandler()
        {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header)
            {
                // here check list view, not content.
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mListView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame)
            {
                refresh();
            }
        });

        // load more container

        loadMoreListViewContainer.useDefaultHeader();

        loadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler()
        {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer)
            {
                loadMore();
            }
        });

        mEmptyLayout.setOnLayoutClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mEmptyLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
                page = 0;
                isRefresh = true;
                getDataFronServer();
            }
        });

    }


    /**
     * 自動刷新
     */
    protected void startRefresh()
    {
        if (mEmptyLayout != null) {
            mEmptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
        }
        mPtrFrameLayout.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                mPtrFrameLayout.autoRefresh(true);
            }
        }, 250);
    }

    /**
     * 上拉加載更多
     */
    private void loadMore()
    {
        isRefresh = false;
        LogUtil.i("開始下拉刷新");
        page++;
        getDataFronServer();
    }

    /**
     * 下拉刷新
     */
    protected void refresh()
    {
        isRefresh = true;
        LogUtil.i("開始下拉刷新");
        page = 0;
        getDataFronServer();
    }


    /**
     * 获取网络数据
     */
    protected abstract void getDataFronServer();

    protected abstract void successData();

    protected abstract void parasData();

    protected abstract void onErrorJson(Throwable ex, boolean isOnCallback);

    /**
     * 获取网络数据完成
     */
    protected void getDataFronServerDone(boolean emptyResult, boolean hasMore)
    {
        mPtrFrameLayout.refreshComplete();
        loadMoreListViewContainer.loadMoreFinish(emptyResult, hasMore);
    }


    private boolean isRefresh;
    protected ArrayList<JSONObject> jsonObjects;
    protected JSONObject jsonData;
    protected BaseAdapter mAdapter;
    protected BaseCallBackJsonObject baseCallBackJsonObject;
    protected BaseCallBackJsonArray baseCallBackJsonArray;
}
