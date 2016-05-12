package cn.ahyxy.fastvisit.app.DataManager;

import org.xutils.x;

import cn.ahyxy.fastvisit.app.DataManager.parameter.LoginParam;
import cn.ahyxy.fastvisit.base.BaseCallBackJsonObject;

/**
 * Created by yexiangyu on 16/4/23.
 */
public class UserManager
{

    public static void login(String account, String pwd,  BaseCallBackJsonObject callBackJsonObject)
    {
        x.http().post(new LoginParam(account, pwd), callBackJsonObject);
    }
}
