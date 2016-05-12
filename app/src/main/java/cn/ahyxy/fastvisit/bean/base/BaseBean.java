package cn.ahyxy.fastvisit.bean.base;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import cn.ahyxy.fastvisit.bean.error.NetRequestException;
import cn.ahyxy.fastvisit.bean.error.SelfError;

/**
 * Created by yexiangyu on 16/3/9.
 */
public abstract class BaseBean<T> implements Serializable
{

    public static JSONObject checkJson(String jsonStr) throws JSONException, NetRequestException
    {
        JSONObject jsonObject = new JSONObject(jsonStr);

        int code = jsonObject.optInt("code", 0);

        if (code != 0) {
            SelfError error = new SelfError();
            error.parseErrorJSON(jsonObject);
            throw new NetRequestException(error);
        }

        return jsonObject.optJSONObject("data");
    }

    public abstract T parserBean(JSONObject jsonObject);

}
