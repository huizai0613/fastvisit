package cn.ahyxy.fastvisit.app.DataManager.parameter;

import cn.ahyxy.fastvisit.base.BaseRequestTokenParam;

/**
 * Created by yexiangyu on 16/5/12.
 */
public class CheckPositionParam extends BaseRequestTokenParam
{
    public CheckPositionParam(String id, String s_x, String s_y, String address, String type)
    {
        super("index/sign");
        addBodyParameter("s_id", id);
        addBodyParameter("s_x", s_x);
        addBodyParameter("s_y", s_y);
        addBodyParameter("address", address);
        addBodyParameter("type", type);
    }
}
