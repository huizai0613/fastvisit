package cn.ahyxy.fastvisit.baseui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import cn.ahyxy.fastvisit.bean.base.BaseBean;

/**
 * Created by yexiangyu on 16/3/9.
 */
public class BaseListAdapter<T extends BaseBean> extends BaseAdapter implements
        AbsListView.OnScrollListener
{
    protected LayoutInflater mInflater;
    private Collection<T> baseBeans;
    private ViewHolder viewHolder;
    private int layoutID;
    protected boolean isScrolling;
    protected AbsListView mList;

    private AbsListView.OnScrollListener listener;

    public void addOnScrollListener(AbsListView.OnScrollListener l)
    {
        this.listener = l;
    }

    public BaseListAdapter(AbsListView view, Collection<T> mDatas, int itemLayoutId)
    {
        this.mInflater = LayoutInflater.from(view.getContext());
        if (mDatas == null) {
            mDatas = new ArrayList<T>(0);
        }
        this.baseBeans = mDatas;
        this.layoutID = itemLayoutId;
        this.mList = view;
        mList.setOnScrollListener(this);
    }

    @Override
    public int getCount()
    {
        return baseBeans.size();
    }

    @Override
    public T getItem(int position)
    {
        if (baseBeans instanceof List) {
            return ((List<T>) baseBeans).get(position);
        } else if (baseBeans instanceof Set) {
            return new ArrayList<T>(baseBeans).get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        final ViewHolder viewHolder = ViewHolder.get(convertView, parent, layoutID, position);
        viewHolder.bindData2View(convertView, getItem(position));
        return viewHolder.getConvertView();
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState)
    {
        // 设置是否滚动的状态
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            isScrolling = false;
//            this.notifyDataSetChanged();
        } else {
            isScrolling = true;
        }
        if (listener != null) {
            listener.onScrollStateChanged(view, scrollState);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount)
    {
        if (listener != null) {
            listener.onScroll(view, firstVisibleItem, visibleItemCount,
                    totalItemCount);
        }
    }
}
