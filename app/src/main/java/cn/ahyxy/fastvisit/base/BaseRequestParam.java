package cn.ahyxy.fastvisit.base;

import org.xutils.http.RequestParams;

/**
 * Created by yexiangyu on 16/3/16.
 */

public class BaseRequestParam extends RequestParams
{

    public BaseRequestParam(String... url)
    {
//yx.hflongdie.com/api/login/login
        super("http://yx.hflongdie.com/api/" + url[0]);
        addQueryStringParameter("g", "Api");
        addQueryStringParameter("m", "systemApi");
    }
}
