package cn.ahyxy.fastvisit.app.DataManager.parameter;

import cn.ahyxy.fastvisit.base.BaseRequestTokenParam;

/**
 * Created by zack on 2016/5/27.
 */
public class GetTerminalCommodityParam extends BaseRequestTokenParam {
    public GetTerminalCommodityParam(String dId, String tId) {
        super("index/get_terminal_commodity");
        addParameter("d_id", dId);
        addParameter("t_id", tId);
    }
}
