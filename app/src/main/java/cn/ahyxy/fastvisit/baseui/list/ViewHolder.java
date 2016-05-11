package cn.ahyxy.fastvisit.baseui.list;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.ahyxy.fastvisit.bean.base.BaseBean;

/**
 * Created by yexiangyu on 16/3/9.
 */
public class ViewHolder<T extends BaseBean>
{

    private final SparseArray<View> mViews;
    private final int mPosition;
    private final View mConvertView;

    private ViewHolder(ViewGroup parent, int layoutId, int position)
    {
        this.mPosition = position;
        this.mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(parent.getContext()).inflate(
                layoutId, parent, false);
        // setTag
        mConvertView.setTag(this);
    }

    /**
     * 拿到一个ViewHolder对象
     *
     * @param convertView
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */
    public static ViewHolder get(View convertView, ViewGroup parent,
                                 int layoutId, int position)
    {
        if (convertView == null) {
            return new ViewHolder(parent, layoutId, position);
        } else {
            return (ViewHolder) convertView.getTag();
        }
    }

    /**
     * 拿到全部View
     *
     * @return
     */
    public SparseArray<View> getAllView()
    {
        return mViews;
    }

    public View getConvertView()
    {
        return mConvertView;
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId)
    {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public int getPosition()
    {
        return mPosition;
    }

    public void bindData2View(View convertView, T data)
    {

    }

    ;
}
