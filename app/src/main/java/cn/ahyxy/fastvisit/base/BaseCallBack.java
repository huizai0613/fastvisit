package cn.ahyxy.fastvisit.base;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;

import cn.ahyxy.fastvisit.baseui.BaseActivity;
import cn.ahyxy.fastvisit.baseui.LsSimpleHomeFragment;
import cn.ahyxy.fastvisit.baseui.list.BaseListActivity;
import cn.ahyxy.fastvisit.utils.ToastUtils;
import cn.ahyxy.fastvisit.weight.EmptyLayout;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;

/**
 * Created by yexiangyu on 16/3/16.
 */
public abstract class BaseCallBack implements Callback.CommonCallback<String>
{
    private final Context context;
    private EmptyLayout emptyLayout;
    private PtrClassicFrameLayout mPtrClassicFrameLayout;
    private BaseActivity baseActivity;

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

    public BaseCallBack(Context mContext, BaseActivity mBaseActivity)
    {
        this.context = mContext;
        baseActivity = mBaseActivity;
    }

    @Override
    public void onSuccess(String result)
    {
        try {
            LogUtil.d(result);
            JSONObject resultJsonObject = new JSONObject(result);
            if (baseActivity != null) {
                baseActivity.hideWaitDialog();
            }
            if (emptyLayout != null) {
                emptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            }

            if (mPtrClassicFrameLayout != null) {
                mPtrClassicFrameLayout.refreshComplete();
            }

            int status = resultJsonObject.optInt("status", 1);
            if (status != 1) {
                JSONObject jsonObject = resultJsonObject.optJSONObject("error_response");
                if (jsonObject != null) {
                    ToastUtils.Errortoast(context, jsonObject.optString("msg"));
                }
                onError(new Throwable("json status error :status=" + status), false);
                return;
            }

            if (this instanceof OnSuccessJsonArray) {
                ((OnSuccessJsonArray) this).onSuccessJsonArray(resultJsonObject.optJSONArray("data"));
            } else if (this instanceof OnSuccessJsonObject) {
                ((OnSuccessJsonObject) this).onSuccessJsonObject(resultJsonObject.optJSONObject("data"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback)
    {
        if (baseActivity != null) {
            baseActivity.hideWaitDialog();
        }
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
