package cn.ahyxy.fastvisit.app.DataManager.parameter;

import cn.ahyxy.fastvisit.base.BaseRequestTokenParam;

/**
 * Created by zack on 2016/5/22.
 */
public class GetProductListParam extends BaseRequestTokenParam {
    public GetProductListParam(String dId) {
        super("index/get_commodity");
        addParameter("d_id", dId);
    }
}
