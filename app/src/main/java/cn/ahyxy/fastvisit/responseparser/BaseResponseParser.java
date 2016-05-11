package cn.ahyxy.fastvisit.responseparser;

import org.json.JSONException;
import org.xutils.common.util.LogUtil;
import org.xutils.http.app.ResponseParser;
import org.xutils.http.request.UriRequest;

import java.lang.reflect.Type;

import cn.ahyxy.fastvisit.bean.base.BaseBean;
import cn.ahyxy.fastvisit.bean.error.NetRequestException;


/**
 * Created by yexiangyu on 16/3/9.
 */
public abstract class BaseResponseParser<T extends BaseBean> implements ResponseParser
{
    @Override
    public void checkResponse(UriRequest request) throws Throwable
    {
        //请求前一步调用
        LogUtil.i(request.toString());//打印请求接口
    }

    @Override
    public Object parse(Type resultType, Class<?> resultClass, String result) throws Throwable
    {
        LogUtil.d(result);
        return parseObject(resultType, resultClass, result);
    }

    protected abstract T parseObject(Type resultType, Class<?> resultClass, String result) throws JSONException, NetRequestException;
}
