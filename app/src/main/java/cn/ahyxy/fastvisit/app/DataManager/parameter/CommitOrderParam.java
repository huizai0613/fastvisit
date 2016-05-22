package cn.ahyxy.fastvisit.app.DataManager.parameter;

import java.util.HashMap;

import cn.ahyxy.fastvisit.base.BaseRequestTokenParam;

/**
 * Created by zack on 2016/5/22.
 */
public class CommitOrderParam extends BaseRequestTokenParam {
    public CommitOrderParam(HashMap<String, String> map) {
        super("index/orders");
        addParam(map);
    }
}
