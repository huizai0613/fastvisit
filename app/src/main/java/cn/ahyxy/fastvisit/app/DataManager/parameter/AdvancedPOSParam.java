package cn.ahyxy.fastvisit.app.DataManager.parameter;

import cn.ahyxy.fastvisit.base.BaseRequestTokenParam;

/**
 * Created by zack on 2016/5/18.
 */
public class AdvancedPOSParam extends BaseRequestTokenParam {
    public AdvancedPOSParam(String id) {
        super("index/top_terminal");
        addParameter("id", id);
    }
}
