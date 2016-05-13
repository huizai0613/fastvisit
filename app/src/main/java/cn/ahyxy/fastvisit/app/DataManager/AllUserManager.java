package cn.ahyxy.fastvisit.app.DataManager;

import org.xutils.common.util.LogUtil;
import org.xutils.x;

import cn.ahyxy.fastvisit.app.DataManager.parameter.AllUserParam;
import cn.ahyxy.fastvisit.app.DataManager.parameter.PublicPatam;
import cn.ahyxy.fastvisit.base.BaseCallBackJsonArray;
import cn.ahyxy.fastvisit.base.BaseCallBackJsonObject;

/**
 * Created by yexiangyu on 16/5/12.
 */
public class AllUserManager {
    public static void getAllUser(String dId, String id, BaseCallBackJsonArray baseCallBackJsonArray) {
        LogUtil.d("getAllUser d_id:" + dId + ", id:" + id);
        x.http().post(new AllUserParam(dId, id), baseCallBackJsonArray);
    }

}
