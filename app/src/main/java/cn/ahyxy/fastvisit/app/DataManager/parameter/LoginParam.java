package cn.ahyxy.fastvisit.app.DataManager.parameter;

import cn.ahyxy.fastvisit.base.BaseRequestParam;

/**
 * Created by yexiangyu on 16/5/12.
 */
public class LoginParam extends BaseRequestParam
{
    public LoginParam(String account, String pwd)
    {
        super("login/login");
        addBodyParameter("username", account);
        addBodyParameter("password", pwd);
    }
}
