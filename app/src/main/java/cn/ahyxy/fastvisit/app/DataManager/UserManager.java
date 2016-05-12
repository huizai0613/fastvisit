package cn.ahyxy.fastvisit.app.DataManager;

import org.xutils.x;

import cn.ahyxy.fastvisit.app.DataManager.parameter.LoginParam;
import cn.ahyxy.fastvisit.app.bean.UserBean;
import cn.ahyxy.fastvisit.base.BaseCallBackJsonObject;

/**
 * Created by yexiangyu on 16/4/23.
 */
public class UserManager
{

    private static UserBean userBean;

    public static void login(String account, String pwd, BaseCallBackJsonObject callBackJsonObject)
    {
        x.http().post(new LoginParam(account, pwd), callBackJsonObject);
    }

    public static void setUserBean(UserBean userBean)
    {
        UserManager.userBean = userBean;
    }

    public static UserBean getUserBean()
    {
        return userBean;
    }
}
