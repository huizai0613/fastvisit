package cn.ahyxy.fastvisit.app.DataManager.parameter;

import cn.ahyxy.fastvisit.base.BaseRequestTokenParam;

/**
 * Created by zack on 2016/5/22.
 */
public class GetOutletListParam extends BaseRequestTokenParam {
    public GetOutletListParam(String id) {
        super("index/task_terminal");
        addParameter("id", id);
    }
}
