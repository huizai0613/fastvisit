package cn.ahyxy.fastvisit.base;

import cn.ahyxy.fastvisit.app.DataManager.UserManager;
import cn.ahyxy.fastvisit.utils.StringUtils;

/**
 * Created by yexiangyu on 16/3/16.
 */

public class BaseRequestTokenParam extends BaseRequestParam
{


    public BaseRequestTokenParam(String... url)
    {
        super(url[0]);
        String token = UserManager.getUserBean().getToken();
        if (!StringUtils.isEmpty(token))
            addBodyParameter("token", token);

    }
}
