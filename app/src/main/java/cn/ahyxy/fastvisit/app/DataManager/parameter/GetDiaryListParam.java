package cn.ahyxy.fastvisit.app.DataManager.parameter;

import cn.ahyxy.fastvisit.base.BaseRequestTokenParam;

/**
 * Created by zack on 2016/5/25.
 */
public class GetDiaryListParam extends BaseRequestTokenParam {
    public GetDiaryListParam(String id) {
        super("index/get_diary");
        addParameter("id", id);
    }
}
