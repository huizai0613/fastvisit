package cn.ahyxy.fastvisit.app.DataManager.parameter;

import cn.ahyxy.fastvisit.base.BaseRequestTokenParam;

/**
 * Created by zack on 2016/5/21.
 */
public class HotProductsParam extends BaseRequestTokenParam {
    public HotProductsParam(String dId) {
        super("index/hot_commodity");
        addParameter("d_id", dId);
    }
}
