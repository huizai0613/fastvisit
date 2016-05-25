package cn.ahyxy.fastvisit.app.DataManager.parameter;

import cn.ahyxy.fastvisit.base.BaseRequestTokenParam;

/**
 * Created by zack on 2016/5/24.
 */
public class GetNoteListParam extends BaseRequestTokenParam {
    public GetNoteListParam(String id) {
        super("index/get_memo");
        addParameter("id", id);
    }
}
