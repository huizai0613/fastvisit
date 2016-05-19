package cn.ahyxy.fastvisit.app.DataManager.parameter;

import cn.ahyxy.fastvisit.base.BaseRequestTokenParam;

/**
 * Created by zack on 2016/5/19.
 */
public class GetOutletCategoriesParam extends BaseRequestTokenParam {
    public GetOutletCategoriesParam(String dId) {
        super("index/get_terminal_cate");
        addParameter("d_id", dId);
    }
}
