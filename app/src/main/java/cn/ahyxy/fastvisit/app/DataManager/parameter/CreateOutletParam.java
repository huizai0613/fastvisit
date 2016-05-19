package cn.ahyxy.fastvisit.app.DataManager.parameter;

import cn.ahyxy.fastvisit.base.BaseRequestTokenParam;

/**
 * Created by zack on 2016/5/19.
 */
public class CreateOutletParam extends BaseRequestTokenParam {
    public CreateOutletParam(String dId, String cateOne, String cateTwo, String tX, String tY, String id,
                             String name, String address, String contactName, String tel, String remark) {
        super("index/add_terminal");
        addParameter("d_id", dId);
        addParameter("cate_one", cateOne);
        addParameter("cate_two", cateTwo);
        addParameter("t_x", tX);
        addParameter("t_y", tY);
        addParameter("add_saleman", id);
        addParameter("t_name",name);
        addParameter("t_address", address);
        addParameter("contact_name", contactName);
        addParameter("contact_tel", tel);
        addParameter("t_remark", remark);
    }
}
