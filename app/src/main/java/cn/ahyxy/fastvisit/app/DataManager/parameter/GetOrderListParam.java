package cn.ahyxy.fastvisit.app.DataManager.parameter;

import cn.ahyxy.fastvisit.base.BaseRequestTokenParam;

/**
 * Created by zack on 2016/5/22.
 */
public class GetOrderListParam extends BaseRequestTokenParam {
    public GetOrderListParam(String id) {
        super("index/get_order");
        addParameter("id", id);
    }
}
