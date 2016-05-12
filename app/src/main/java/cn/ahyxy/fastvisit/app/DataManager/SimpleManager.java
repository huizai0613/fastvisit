package cn.ahyxy.fastvisit.app.DataManager;

import org.xutils.x;

import cn.ahyxy.fastvisit.app.DataManager.parameter.PublicPatam;
import cn.ahyxy.fastvisit.base.BaseCallBackJsonObject;

/**
 * Created by yexiangyu on 16/5/12.
 */
public class SimpleManager
{
    public static void getPublic(String id, BaseCallBackJsonObject callBackJsonObject)
    {
        x.http().post(new PublicPatam(id), callBackJsonObject);
    }

}
