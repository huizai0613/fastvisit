package cn.ahyxy.fastvisit.app.DataManager.parameter;

import cn.ahyxy.fastvisit.base.BaseRequestParam;

/**
 * Created by yexiangyu on 16/5/12.
 */
public class AllUserParam extends BaseRequestParam {
    public AllUserParam(String dId, String id, String token) {
        super("index/getalluser");
        addBodyParameter("d_id", dId);
        addBodyParameter("id", id);
        addBodyParameter("token", token);
    }
}
