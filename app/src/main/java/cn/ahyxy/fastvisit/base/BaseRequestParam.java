package cn.ahyxy.fastvisit.base;

import org.xutils.http.RequestParams;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by yexiangyu on 16/3/16.
 */

public class BaseRequestParam extends RequestParams
{

    public BaseRequestParam(String... url)
    {
        super("http://yx.hflongdie.com/api/" + url[0]);
    }

    protected void addParam(HashMap<String, String> param)
    {
        Set<Map.Entry<String, String>> entries = param.entrySet();

        for (Map.Entry<String, String> entry : entries) {
            addBodyParameter(entry.getKey(), entry.getValue());
        }
    }
}
