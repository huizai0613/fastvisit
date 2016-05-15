package cn.ahyxy.fastvisit.app.DataManager.parameter;

import cn.ahyxy.fastvisit.base.BaseRequestParam;

/**
 * Created by yexiangyu on 16/5/12.
 */
public class RegisterParam extends BaseRequestParam
{
    public RegisterParam(String account)
    {
        super("reg/verify_code");
        addBodyParameter("tel", account);
    }
}
