package cn.ahyxy.fastvisit.app.DataManager.parameter;

import cn.ahyxy.fastvisit.base.BaseRequestTokenParam;

/**
 * Created by zack on 2016/5/18.
 */
public class OutletSearchResultParam extends BaseRequestTokenParam {
    public OutletSearchResultParam(String id, String keyword) {
        super("index/search_terminal");
        addParameter("id", id);
        addParameter("keyword", keyword);
    }
}
