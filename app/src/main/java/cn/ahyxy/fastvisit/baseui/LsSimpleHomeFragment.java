package cn.ahyxy.fastvisit.baseui;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ScrollView;

import org.simple.eventbus.EventBus;
import org.xutils.common.util.LogUtil;

import cn.ahyxy.fastvisit.MainActivity;
import cn.ahyxy.fastvisit.R;
import cn.ahyxy.fastvisit.baseui.titlebar.TitleBar;
import cn.ahyxy.fastvisit.utils.StringUtils;
import cn.ahyxy.fastvisit.weight.EmptyLayout;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;


public abstract class LsSimpleHomeFragment extends SupportFragment implements MainActivity.OnTabReselectedListener
{
    protected String mLastUpdateTime = "";
    protected boolean mUserDataChanged = false;
    public PtrClassicFrameLayout mPtrClassicFrameLayout;
    public EmptyLayout mErrorView;
    protected ListView mContentListView;
    protected ScrollView mContentScrollView;
    private LayoutInflater layoutInflater;
    protected TitleBar mTitleBar;
    protected BaseActivity mActivity;
    protected int page;
    protected boolean isRefresh;
    protected int type = 0;

    public static final int SCROLLVIEWTYPE = 0;
    public static final int LISTVIEWTYPE = 1;

    protected boolean isCanPullRefresh = true;
    private PtrHandler mPtrHandler = new PtrHandler()
    {
        @Override
        public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header)
        {
            return isCanPullRefresh && PtrDefaultHandler.checkContentCanBePulledDown(frame, type == SCROLLVIEWTYPE ? mContentScrollView : mContentListView, header);
        }

        @Override
        public void onRefreshBegin(PtrFrameLayout frame)
        {
            refreshData();
        }
    };

    protected void refreshData()
    {
        mErrorView.setErrorType(EmptyLayout.HIDE_LAYOUT);
        LogUtil.d("开始下拉刷新...");
        page = 0;
        isRefresh = true;
        getDataFronServer();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mActivity = (BaseActivity) getActivity();
        if (fragmentRootView == null) {
            registerBroadcast();
            return super.onCreateView(inflater, container, savedInstanceState);
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) fragmentRootView.getParent();
            if (parent != null) {
                parent.removeView(fragmentRootView);
            }
            return fragmentRootView;
        }
    }

    @Override
    protected void initWidget(View parentView)
    {

        mPtrClassicFrameLayout = bindView(R.id.refresh);
        if (mPtrClassicFrameLayout != null) {
            mPtrClassicFrameLayout.setLoadingMinTime(1000);
            mPtrClassicFrameLayout.setPtrHandler(mPtrHandler);
        }
        switch (type) {
            case SCROLLVIEWTYPE:
                mContentScrollView = bindView(R.id.content_scrollview);
                break;
            case LISTVIEWTYPE:
                mContentListView = bindView(R.id.content_scrollview);
                break;
        }
        mErrorView = bindView(R.id.error_layout, true);
        if (mErrorView != null) {
            mErrorView.setOnLayoutClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mErrorView.setErrorType(EmptyLayout.NETWORK_LOADING);
                    isRefresh = true;
                    page = 0;
                    getDataFronServer();
                }
            });
        }
        super.initWidget(parentView);
    }


    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container,
                                Bundle bundle)
    {
        layoutInflater = inflater;
        fragmentRootView = View.inflate(getActivity(), getLayoutId(), null);
        mTitleBar = TitleBar.getInstance(fragmentRootView);
        registerBroadcast();
        return fragmentRootView;
    }

    protected abstract int getLayoutId();

    @Override
    public void onResume()
    {
        if (needRefresh()) {
            refresh();
        }
        super.onResume();
    }

    /**
     * 满不满足当前需要更新数据（这个用于在再次进入此界面时(onResume()方法)调用）
     *
     * @return
     */
    protected boolean needRefresh()
    {
        //判断子类是否需要自动刷新机制，同时这里判断用户信息是否更改，以及上次刷新时间是否已经过了自动刷新时间
        return (needAutoRefresh()) && (mUserDataChanged || (StringUtils.isEmpty(mLastUpdateTime) || StringUtils.calDateDifferent(mLastUpdateTime, StringUtils.getCurTimeStr()) > getAutoRefreshTime()));
    }

    /***
     * 自动刷新的时间
     * <p/>
     * 默认：自动刷新的时间为5分钟
     *
     * @return
     * @author feizhao
     */
    protected long getAutoRefreshTime()
    {
        return 5 * 1000;
    }

    /**
     * 是否需要自动刷新
     *
     * @return 此fragment需不需要进入界面自动更新
     */
    protected boolean needAutoRefresh()
    {
        return true;
    }

    protected void updateLastRefreshTime()
    {
        this.mLastUpdateTime = StringUtils.getCurTimeStr();
    }

    public void updateUserDataChanged(boolean changed)
    {
        this.mUserDataChanged = changed;
    }

    protected void refresh()
    {
        page = 0;
        isRefresh = true;
        if (mErrorView != null) {
            mErrorView.setErrorType(EmptyLayout.NETWORK_LOADING);
        }
        getDataFronServer();
        updateLastRefreshTime();
        updateUserDataChanged(false);
    }


    protected void starRefresh()
    {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                mPtrClassicFrameLayout.autoRefresh();
            }
        }, 500);
    }


    protected abstract void getDataFronServer();

    @Override
    public void registerBroadcast()
    {
        EventBus.getDefault().register(this);
        super.registerBroadcast();
    }

    @Override
    public void unRegisterBroadcast()
    {
        EventBus.getDefault().unregister(this);
        super.unRegisterBroadcast();
    }

}
