package cn.ahyxy.fastvisit.app.DataManager.parameter;

import java.util.HashMap;

import cn.ahyxy.fastvisit.base.BaseRequestTokenParam;

/**
 * Created by zack on 2016/5/25.
 */
public class AddNewDiaryParam extends BaseRequestTokenParam {
    public AddNewDiaryParam(HashMap<String, String> map) {
        super("index/add_diary");
        addParam(map);
    }
}
