package cn.ahyxy.fastvisit.app.DataManager.parameter;

import cn.ahyxy.fastvisit.base.BaseRequestTokenParam;

/**
 * Created by yexiangyu on 16/5/12.
 */
public class AllUserParam extends BaseRequestTokenParam {
    public AllUserParam(String dId, String id) {
        super("index/getalluser");
        addBodyParameter("d_id", dId);
        addBodyParameter("id", id);
    }
}
