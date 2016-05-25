package cn.ahyxy.fastvisit.app.DataManager.parameter;

import java.util.HashMap;

import cn.ahyxy.fastvisit.base.BaseRequestTokenParam;

/**
 * Created by zack on 2016/5/24.
 */
public class AddNewNoteParam extends BaseRequestTokenParam {
    public AddNewNoteParam(HashMap<String, String> map) {
        super("index/add_memo");
        addParam(map);
    }
}
