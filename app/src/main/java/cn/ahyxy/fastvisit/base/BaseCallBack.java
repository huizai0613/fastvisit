package cn.ahyxy.fastvisit.base;

import android.content.Context;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;

import cn.ahyxy.fastvisit.baseui.LsSimpleHomeFragment;
import cn.ahyxy.fastvisit.baseui.list.BaseListActivity;
import cn.ahyxy.fastvisit.utils.ToastUtils;
import cn.ahyxy.fastvisit.weight.EmptyLayout;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;

/**
 * Created by yexiangyu on 16/3/16.
 */
public abstract class BaseCallBack implements Callback.CommonCallback<JSONObject>
{
    private final Context context;
    private EmptyLayout emptyLayout;
    private PtrClassicFrameLayout mPtrClassicFrameLayout;

    public BaseCallBack(Context mContext)
    {
        this.context = mContext;
    }

    public BaseCallBack(Context mContext, LsSimpleHomeFragment fragment)
    {
        this.emptyLayout = fragment.mErrorView;
        this.mPtrClassicFrameLayout = fragment.mPtrClassicFrameLayout;
        this.context = mContext;
    }
    public BaseCallBack(Context mContext, EmptyLayout mErrorView)
    {
        this.emptyLayout = mErrorView;
        this.context = mContext;
    }


    public BaseCallBack(Context mContext, BaseListActivity activity)
    {
        this.emptyLayout = activity.mEmptyLayout;
        this.mPtrClassicFrameLayout = activity.mPtrFrameLayout;
        this.context = mContext;
    }

    @Override
    public void onSuccess(JSONObject result)
    {
        if (emptyLayout != null)
            emptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);

        if (mPtrClassicFrameLayout != null) {
            mPtrClassicFrameLayout.refreshComplete();
        }
        LogUtil.d(result.toString());

        int status = result.optInt("status", 1);
        if (status != 1) {
            JSONObject jsonObject = result.optJSONObject("error_response");
            if (jsonObject != null) {
                ToastUtils.Errortoast(context, jsonObject.optString("msg"));
            }
            onError(new Throwable("json status error :status=" + status), false);
            return;
        }

        if (this instanceof OnSuccessJsonArray) {
            ((OnSuccessJsonArray) this).onSuccessJsonArray(result.optJSONArray("data"));
        } else if (this instanceof OnSuccessJsonObject) {
            ((OnSuccessJsonObject) this).onSuccessJsonObject(result.optJSONObject("data"));
        }

    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback)
    {
        if (mPtrClassicFrameLayout != null) {
            mPtrClassicFrameLayout.refreshComplete();
        }
        if (emptyLayout != null)
            emptyLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
        LogUtil.d(ex.getMessage());
        onErrorJson(ex, isOnCallback);
    }

    @Override
    public void onCancelled(CancelledException cex)
    {

    }

    @Override
    public void onFinished()
    {

    }

    public abstract void onErrorJson(Throwable ex, boolean isOnCallback);
}
