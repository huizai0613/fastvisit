package cn.ahyxy.fastvisit.weight;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;


public abstract class SlideImagePageAdapter<T> extends RecyclingPagerAdapter
{
    protected final int mItemLayoutId;
    protected LayoutInflater mInflater;
    protected Collection<T> mDatas;
    protected ViewPager mViewPage;

    public SlideImagePageAdapter(ViewPager view, Collection<T> datas, int itemLayoutId)
    {
        this.mInflater = LayoutInflater.from(view.getContext());
        this.mViewPage = view;
        this.mDatas = datas;
        this.mItemLayoutId = itemLayoutId;
    }

    public void refresh(Collection<T> datas)
    {
        if (datas == null) {
            datas = new ArrayList<T>(0);
        }
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container)
    {
        final AdapterHolder viewHolder = getViewHolder(position, convertView,
                container);
        convert(viewHolder, getItem(position));
        return viewHolder.getConvertView();
    }

    public T getItem(int position)
    {
        if (mDatas instanceof List) {
            return ((List<T>) mDatas).get(position);
        } else if (mDatas instanceof Set) {
            return new ArrayList<T>(mDatas).get(position);
        } else {
            return null;
        }
    }

    @Override
    public int getCount()
    {
        return mDatas.size();
    }

    private AdapterHolder getViewHolder(int position, View convertView,
                                        ViewGroup parent)
    {
        return AdapterHolder.get(convertView, parent, mItemLayoutId, position);
    }

    public abstract void convert(AdapterHolder helper, T item);

    public void convert(AdapterHolder helper, T item, int position)
    {
        convert(helper, getItem(position));
    }

    public interface OnSlidImageItemClickListener<T>
    {
        public void onSlideImageItemClick(View view, T item);
    }
}
