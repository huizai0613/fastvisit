package cn.ahyxy.fastvisit.app.DataManager;

import org.xutils.ex.DbException;
import org.xutils.x;

import cn.ahyxy.fastvisit.app.AppContext;
import cn.ahyxy.fastvisit.app.DataManager.parameter.CheckPositionParam;
import cn.ahyxy.fastvisit.app.DataManager.parameter.LoginParam;
import cn.ahyxy.fastvisit.app.DataManager.parameter.RegisterParam;
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

    public static void getRegisterCode(String account, BaseCallBackJsonObject callBackJsonObject)
    {
        x.http().post(new RegisterParam(account), callBackJsonObject);
    }

    public static void checkPosition(String id, String s_x, String s_y, String address, String type, BaseCallBackJsonObject callBackJsonObject)
    {
        x.http().post(new CheckPositionParam(id, s_x, s_y, address, type), callBackJsonObject);
    }

    public static void setUserBean(UserBean userBean)
    {
        UserManager.userBean = userBean;
    }

    public static UserBean getUserBean()
    {
        if (userBean == null) {
            try {
                userBean = (UserBean) AppContext.getDbmanager().findAll(UserBean.class).get(0);
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
        return userBean;
    }


}
