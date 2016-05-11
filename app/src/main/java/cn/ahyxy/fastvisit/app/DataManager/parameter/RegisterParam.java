package cn.ahyxy.fastvisit.app.DataManager.parameter;

import cn.ahyxy.fastvisit.base.BaseRequestParam;

/**
 * Created by yexiangyu on 16/4/23.
 */
public class RegisterParam extends BaseRequestParam
{
    public RegisterParam(String shopId,String account,String code)
    {
        super("reg/reg");
        addBodyParameter("tel",account );
        addBodyParameter("tel",account );
        addBodyParameter("tel",account );
    }
}
