package cn.ahyxy.fastvisit.app.DataManager.parameter;

import cn.ahyxy.fastvisit.base.BaseRequestTokenParam;

/**
 * Created by zack on 2016/5/22.
 */
public class TaskBrandParam extends BaseRequestTokenParam {
    public TaskBrandParam(String id) {
        super("index/task_brand");
        addParameter("id", id);
    }
}
