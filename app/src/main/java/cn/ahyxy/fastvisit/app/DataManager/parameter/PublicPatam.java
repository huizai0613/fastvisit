package cn.ahyxy.fastvisit.app.DataManager.parameter;

import cn.ahyxy.fastvisit.base.BaseRequestTokenParam;

/**
 * Created by yexiangyu on 16/5/12.
 */
public class PublicPatam extends BaseRequestTokenParam
{
    public PublicPatam(String id)
    {
        super("index/getnotice");
        addParameter("d_id", id);
    }
}
