package cn.ahyxy.fastvisit.app.DataManager.parameter;

import cn.ahyxy.fastvisit.base.BaseRequestTokenParam;

/**
 * Created by zack on 2016/5/18.
 */
public class OutletSearchHotParam extends BaseRequestTokenParam {
    public OutletSearchHotParam(String id) {
        super("index/hot_terminal");
        addParameter("id", id);
    }
}
