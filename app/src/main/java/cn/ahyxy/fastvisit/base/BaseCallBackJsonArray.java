package cn.ahyxy.fastvisit.base;

import android.content.Context;

import cn.ahyxy.fastvisit.baseui.LsSimpleHomeFragment;
import cn.ahyxy.fastvisit.baseui.list.BaseListActivity;
import cn.ahyxy.fastvisit.weight.EmptyLayout;

/**
 * Created by yexiangyu on 16/3/24.
 */
public abstract class BaseCallBackJsonArray extends BaseCallBack implements OnSuccessJsonArray
{
    public BaseCallBackJsonArray(Context mContext)
    {
        super(mContext);
    }

    public BaseCallBackJsonArray(Context mContext, EmptyLayout emptyLayout)
    {
        super(mContext, emptyLayout);
    }

    public BaseCallBackJsonArray(Context mContext, LsSimpleHomeFragment fragment)
    {
        super(mContext, fragment);
    }

    public BaseCallBackJsonArray(Context mContext, BaseListActivity fragment)
    {
        super(mContext, fragment);
    }
}
