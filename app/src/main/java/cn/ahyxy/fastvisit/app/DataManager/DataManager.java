package cn.ahyxy.fastvisit.app.DataManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.ahyxy.fastvisit.app.DataManager.parameter.AdvancedPOSParam;
import cn.ahyxy.fastvisit.app.DataManager.parameter.AllUserParam;
import cn.ahyxy.fastvisit.app.DataManager.parameter.OutletSearchHotParam;
import cn.ahyxy.fastvisit.app.DataManager.parameter.OutletSearchResultParam;
import cn.ahyxy.fastvisit.app.bean.POSBean;
import cn.ahyxy.fastvisit.base.BaseCallBackJsonArray;

/**
 * Created by yexiangyu on 16/5/12.
 */
public class DataManager {
    public static void getAllUser(String dId, String id, BaseCallBackJsonArray baseCallBackJsonArray) {
        LogUtil.d("getAllUser d_id:" + dId + ", id:" + id);
        x.http().post(new AllUserParam(dId, id), baseCallBackJsonArray);
    }

    public static void getAdvancedPOS(String id, BaseCallBackJsonArray baseCallBackJsonArray) {
        LogUtil.d("getAdvancedPOS id:" + id);
        x.http().post(new AdvancedPOSParam(id), baseCallBackJsonArray);
    }

    public static void getOutletSearchHot(String id, BaseCallBackJsonArray baseCallBackJsonArray) {
        LogUtil.d("getOutletSearchHot id:" + id);
        x.http().post(new OutletSearchHotParam(id), baseCallBackJsonArray);
    }

    public static void getOutletSearchResult(String id, String keyword, BaseCallBackJsonArray baseCallBackJsonArray) {
        LogUtil.d("getOutletSearchResult id:" + id + ", keyword:" + keyword);
        x.http().post(new OutletSearchResultParam(id, keyword), baseCallBackJsonArray);
    }

    public static List<POSBean> jsonArrayToPOSBeanList(JSONArray result) {
        List<POSBean> posBeanList = new ArrayList<>();
        int length = result.length();
        try {
            for (int i = 0; i < length; i++) {
                POSBean posBean = new POSBean();
                JSONObject jsonObject = result.getJSONObject(i);
                posBean.setT_name(jsonObject.getString("t_name"));
                posBean.setT_address(jsonObject.getString("t_address"));
                posBean.setT_level(jsonObject.getInt("t_level"));
                posBeanList.add(posBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return posBeanList;
    }
}
