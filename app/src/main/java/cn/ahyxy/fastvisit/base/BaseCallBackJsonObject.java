package cn.ahyxy.fastvisit.base;

import android.content.Context;

import cn.ahyxy.fastvisit.baseui.LsSimpleHomeFragment;
import cn.ahyxy.fastvisit.baseui.list.BaseListActivity;
import cn.ahyxy.fastvisit.weight.EmptyLayout;

/**
 * Created by yexiangyu on 16/3/24.
 */
public abstract class BaseCallBackJsonObject extends BaseCallBack implements OnSuccessJsonObject
{
    public BaseCallBackJsonObject(Context mContext)
    {
        super(mContext);
    }

    public BaseCallBackJsonObject(Context mContext,EmptyLayout emptyLayout)
    {
        super(mContext,emptyLayout);
    }

    public BaseCallBackJsonObject(Context mContext, LsSimpleHomeFragment fragment)
    {
        super(mContext, fragment);
    }

    public BaseCallBackJsonObject(Context mContext, BaseListActivity fragment)
    {
        super(mContext, fragment);
    }
}
