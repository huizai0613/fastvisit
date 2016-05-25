package cn.ahyxy.fastvisit.app.DataManager.parameter;

import java.util.HashMap;

import cn.ahyxy.fastvisit.base.BaseRequestTokenParam;

/**
 * Created by zack on 2016/5/25.
 */
public class UploadLocationParam extends BaseRequestTokenParam {
    public UploadLocationParam(HashMap<String, String> map) {
        super("index/up_locate");
        addParam(map);
    }
}
