package cn.ahyxy.fastvisit.bean.error;

import android.app.Activity;

import org.json.JSONObject;

import cn.ahyxy.fastvisit.bean.base.BaseBean;
import cn.ahyxy.fastvisit.utils.ToastUtils;


public class SelfError extends BaseBean
{

    public static final String COMERRORINFO = "网络未知错误!请稍候!";

    private int errorId;
    private String errorInfo;

    public int getErrorId()
    {
        return errorId;
    }

    public void setErrorId(int errorId)
    {
        this.errorId = errorId;
    }

    public String getErrorInfo()
    {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo)
    {
        this.errorInfo = errorInfo;
    }

    public SelfError()
    {
        super();
    }

    public SelfError(String errorInfo)
    {
        super();
        this.errorInfo = errorInfo;
    }

    public SelfError(int errorId, String errorInfo)
    {
        super();
        this.errorId = errorId;
        this.errorInfo = errorInfo;
    }

    public void print(Activity context)
    {
        ToastUtils.Errortoast(context, errorInfo);
    }

    public SelfError parseErrorJSON(JSONObject jsonObj)
    {
        errorId = jsonObj.optInt("code");
        errorInfo = jsonObj.optString("msg");

        return this;
    }

    @Override
    public Object parserBean(JSONObject jsonObject)
    {
        return null;
    }
}
